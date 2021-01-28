/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationBean;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunnerService;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.InvalidRankException;
import org.eclipse.dawnsci.analysis.api.processing.OperationCategory;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.DynamicSliceViewIterator;
import org.eclipse.dawnsci.analysis.dataset.slicer.ISliceViewIterator;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceViewIterator;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IDynamicDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.bean.OperationBean;
import uk.ac.diamond.scisoft.analysis.utils.ClassUtils;

/**
 * Do not use this class externally. Instead get the IOperationService
 * from OSGI.
 * 
 * @author Matthew Gerring
 *
 */
public class OperationServiceImpl implements IOperationService {
	
	private static IOperationRunnerService rservice;
	public void setOperationRunner(IOperationRunnerService s) {
		rservice = s;
	}
	
	private Map<String, IOperation<? extends IOperationModel, ? extends OperationData>>              operations;
	private Map<String, Class<? extends IOperationModel>>                                            models;
	private Map<String, OperationCategory>                                                           categoryId;
	private Map<String, Collection<IOperation<? extends IOperationModel, ? extends OperationData>>>  categoryOp;
	private Map<String, String>                                                                      opIdCategory;
	
	private static final int NBYTES = 1024 * 1024;
	
	private final static Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	public OperationServiceImpl() {
		// Intentionally do nothing
	}
	
	public IOperationContext createContext() {
		return new OperationContextImpl();
	}

	/**
	 * Uses the Slicer.visitAll(...) method which conversions also use to process
	 * stacks out of the rich dataset passed in.
	 */
	@Override
	public void execute(final IOperationContext context) throws OperationException {
				
		if (context.getSeries()==null || context.getSeries().length<1) {
			throw new OperationException(null, "No operation list defined, call setSeries(...) with something meaningful please!");
		}
		
		logmem();

		SourceInformation ssource = null;
		try {
			SliceFromSeriesMetadata ssm = context.getData().getFirstMetadata(SliceFromSeriesMetadata.class);
			ssource = ssm == null ? null : ssm.getSourceInfo();
			
			StringBuilder builder = new StringBuilder("Processing ");
			if (ssource != null) {
				builder.append(ssource.getDatasetName());
				builder.append(" from ");
				builder.append(ssource.getFilePath());
				if (ssource.isLive()) {
					builder.append(", live,");
				}
			}
			builder.append(" using the following sequence: ");
			IOperation<? extends IOperationModel, ? extends OperationData>[] series = context.getSeries();
			for (IOperation<?, ?> s : series) {
				builder.append(s.getName());
				builder.append(", ");
			}
			
			logger.info(builder.toString());
			
		} catch (Exception e) {
			logger.error("Could not log processing run", e);
		}
		
		// We check the pipeline ranks are ok
		try {
			
//			modify1DImageContext(context);
	        
	        ISliceViewIterator it = null;
	        
	        if (context.getLiveInfo() == null) it = new SliceViewIterator(context.getData(), context.getSlicing(), context.getDataDimensions());
	        else it = new DynamicSliceViewIterator((IDynamicDataset)context.getData(), context.getLiveInfo().getKeys(), context.getLiveInfo().getComplete(), context.getDataDimensions().length, context.getLiveInfo().isMonitorForOverwrite());
	        
	        if (it instanceof DynamicSliceViewIterator) {
	        	((DynamicSliceViewIterator)it).setMaxTimeout((int)context.getParallelTimeout());
	        }
	        
	        if (!it.hasNext()){
	        	logger.debug("Iterator has no slices ready");
	        	it.reset();
	        	it.hasNext();
	        } else {
	        	logger.debug("Iterator has slices read");
	        }
	        
	        IDataset firstSlice = it.next().getSlice();
	        validate(firstSlice, context.getSeries());
	        
	        
	        if (context.getExecutionType() == ExecutionType.PARALLEL){
	        	IOperation<?,?>[] operationSeries = context.getSeries();
				for (IOperation<?,?> op : operationSeries) {
					Atomic atomic = op.getClass().getAnnotation(Atomic.class);
					if (atomic == null) {
						context.setExecutionType(ExecutionType.SERIES);
						logger.info("Switching to series runner!");
						break;
					}
				}
	        }
	
			// Bug in getMetadata(...) that sometimes the wrong metadata type can be returned.
			SliceFromSeriesMetadata ssm = null;
			try {
				ssm = firstSlice.getFirstMetadata(SliceFromSeriesMetadata.class);
			} catch (Exception e) {
				logger.error("Slice series metadata not obtainable. Hope this is just a unit test...");
			}
			if (ssource == null) {
				logger.warn("Source not obtainable. Hope this is just a unit test...");
			}
			
			try {
				SliceFromSeriesMetadata fullssm = new SliceFromSeriesMetadata(ssource, ssm.getSliceInfo());
				context.getData().setMetadata(fullssm);
			} catch (Exception e) {
				logger.error("Unable to set slice from service metadata on full data.");
			}
			
			for (IOperation<?,?> op : context.getSeries()) op.init();
			
			IOperationRunner runner = rservice.getRunner(context.getExecutionType());
			runner.init(context);
			runner.execute();
			
		} catch (OperationException o) {
			throw o;
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationException(null, e);
		} finally {
			if (context.getVisitor() != null) {
				try {
					context.getVisitor().close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for (IOperation<?,?> op : context.getSeries()) op.dispose();
		}
		
		logmem();
	}
	
	private void logmem() {
		if (logger.isInfoEnabled()) {
			long totalMemory = Runtime.getRuntime().totalMemory();
			long maxMemory = Runtime.getRuntime().maxMemory();
			long freeMemory = Runtime.getRuntime().freeMemory();

			logger.info("Max memory of {} MB, current total {} MB, with {} MB free", maxMemory/NBYTES, totalMemory/NBYTES, freeMemory/NBYTES);
		}
	}

//	private void modify1DImageContext(IOperationContext context) throws Exception{
//		
//		ILazyDataset data = context.getData();
//		int[] maxShape = data.getShape();
//		
//		if (data instanceof IDynamicDataset) {
//			maxShape = ((IDynamicDataset)data).getMaxShape();
//		}
//		
//		int[] dd = context.getDataDimensions();
//		SliceND s = context.getSlicing();
//		if (s == null) {
//			s = new SliceND(data.getShape(),maxShape);
//		}
//		
//		List<Integer> dim = new ArrayList<>();
//		
//		for (int i : dd) {
//			if (i < 0) i = maxShape.length + i;
//			if (maxShape[i] == 1)  {
//				s.setSlice(i, new Slice(0,1,1));
//			} else {
//				dim.add(i);
//			}
//		}
//		
//		if (dd.length == dim.size()) return;
//		
//		
//		context.setSlicing(s);
//		int[] d = new int[dim.size()];
//		
//		for (int i = 0; i < d.length; i++) {
//			d[i] = dim.get(i);
//		}
//		
//		context.setDataDimensions(d);
//	}

	/**
	 * Checks that the pipeline passed in has a reasonable rank (for instance)
	 * 
	 * @param firstSlice - may be null, image assumed if it is
	 * @param series
	 */
	public void validate(IDataset firstSlice,
			             @SuppressWarnings("unchecked") IOperation<? extends IOperationModel, ? extends OperationData>... series) throws InvalidRankException, OperationException {
		       
//		if (firstSlice==null) firstSlice = Random.rand(new int[]{1024, 1024});
        if (series[0].getInputRank()==OperationRank.SAME) {
        	throw new InvalidRankException(series[0], "The input rank may not be "+OperationRank.SAME);
        }
        
        int[] squeezedShape = null;
        
        if (firstSlice != null) squeezedShape = ShapeUtils.squeezeShape(firstSlice.getShape(), false);
        
        SliceFromSeriesMetadata md = firstSlice == null ? null : firstSlice.getFirstMetadata(SliceFromSeriesMetadata.class);
        
        
        if (!(md == null || md.getDataDimensions() == null) && md.getDataDimensions().length != squeezedShape.length) {
        	
        	int[] shape = firstSlice.getShape();
        	
        	squeezedShape = new int[md.getDataDimensions().length];
        	
        	int[] dd = md.getDataDimensions().clone();
        	Arrays.sort(dd);
        	
        	for (int i = 0; i < dd.length; i++) {
        		squeezedShape[i] = shape[dd[i]];
        	}
        	
        }
        
        if (series[0].getInputRank().isDiscrete() && firstSlice != null) {
        	
	        if (squeezedShape.length != series[0].getInputRank().getRank()) {
	        	InvalidRankException e = new InvalidRankException(series[0], "The slicing results in a dataset of rank "+squeezedShape.length+" but the input rank of '"+series[0].getDescription()+"' is "+series[0].getInputRank().getRank());
	            throw e;
	        }
        }
        
        
        OperationRank firstRank = OperationRank.ANY;
        if (firstSlice != null) firstRank = OperationRank.get(squeezedShape.length);
        
        if (series.length > 1) {
        	
        	OperationRank output = series[0].getOutputRank();
        	if (series[0].isPassUnmodifiedData()) output = series[0].getInputRank();        	
        	if (output == OperationRank.SAME) output = firstRank;
        	if (output == OperationRank.ANY)  output = firstRank;
        	
            OperationRank lastNumericalRank = output;
	        for (int i = 1; i < series.length; i++) {
	        	OperationRank input = series[i].getInputRank();
	        	if (!input.isCompatibleWith(output)) {
	        		throw new InvalidRankException(series[i], "The output of '"+series[i-1].getName()+"' is not compatible with the input of '"+series[i].getName()+"'.");
	        	}
	        	output = series[i].getOutputRank();
	        	if ((output == OperationRank.SAME || output == OperationRank.ANY) && input.isDiscrete() == false) output = lastNumericalRank;
	        	else if (output == OperationRank.SAME) output = input;
	        	if (series[i].isPassUnmodifiedData()) output = input;
	        	lastNumericalRank = output;
			}
        }
	}

	protected static boolean isCompatible(final int[] ashape, final int[] bshape) {

		List<Integer> alist = new ArrayList<Integer>();

		for (int a : ashape) {
			if (a > 1) alist.add(a);
		}

		final int imax = alist.size();
		int i = 0;
		for (int b : bshape) {
			if (b == 1)
				continue;
			if (i >= imax || b != alist.get(i++))
				return false;
		}

		return i == imax;
	}

	private void initialize() {
		operations = new LinkedHashMap<>(31);
		models     = new HashMap<>(31);
		categoryOp = new HashMap<>(7);
		categoryId = new HashMap<>(7);
		opIdCategory = new HashMap<>();
	}

	// Reads the declared operations from extension point, if they have not been already.
	private synchronized void checkOperations() throws CoreException {
		if (operations!=null) return;
		
		initialize();
		
		IConfigurationElement[] eles = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.dawnsci.analysis.api.operation");
		for (IConfigurationElement e : eles) {
			if (!e.getName().equals("category")) continue;
			final String     id   = e.getAttribute("id");
			final String     name = e.getAttribute("name");
			final String     icon = e.getAttribute("icon");
			categoryId.put(id, new OperationCategory(name, icon, id));
		}
	
		for (IConfigurationElement e : eles) {
			if (!e.getName().equals("operation")) continue;
			final String id = e.getAttribute("id");
			IOperation<?, ?> op = null;
			try {
				op = (IOperation<?, ?>) e.createExecutableExtension("class");
			} catch (Exception e1) {
				e1.printStackTrace();
				continue;
			}

			operations.put(id, op);

			final String catId = e.getAttribute("category");
			opIdCategory.put(id, catId);

			if (catId != null) {
				Collection<IOperation<? extends IOperationModel, ? extends OperationData>> ops = categoryOp.get(catId);
				if (ops == null) {
					ops = new TreeSet<IOperation<? extends IOperationModel,? extends OperationData>>(new AbstractOperationBase.OperationComparator());
					categoryOp.put(catId, ops);
				}
				ops.add(op);
			}

			final String model;
			if (op instanceof AbstractOperationBase) {
				AbstractOperationBase<?, ?> aop = (AbstractOperationBase<?, ?>) op;
				aop.setName(e.getAttribute("name"));

				final String desc = e.getAttribute("description");
				if (desc!=null) aop.setDescription(desc);
				models.put(id, aop.getModelClass());
			} else {
				model = e.getAttribute("model");
				if (model != null && !model.isEmpty()) {
					models.put(id, ((IOperationModel) e.createExecutableExtension("model")).getClass());
				}
			}
		}
	}
	

	@Override
	public Class<? extends IOperationModel> getModelClass(String operationId) throws Exception {
		return models.get(operationId); // allow null to return
	}

	@Override
	public OperationCategory getCategory(String operationId) {
		String catid = opIdCategory.get(operationId);
		if (catid == null || catid.isEmpty()) return null;
		
		return categoryId.get(catid);
	}


	@Override
	public Collection<IOperation<? extends IOperationModel, ? extends OperationData>> find(String regex) throws Exception {
		checkOperations();
		
		Collection<IOperation<? extends IOperationModel, ? extends OperationData>> ret = new ArrayList<IOperation<? extends IOperationModel, ? extends OperationData>>(3);
		Pattern pattern = Pattern.compile(regex);
		for (String id : operations.keySet()) {
			IOperation<? extends IOperationModel, ? extends OperationData> operation = operations.get(id);
			if (matches(id, operation, pattern)) {
				ret.add(operation);
			}
		}
		return ret;
	}
	
	@Override
	public Collection<IOperation<? extends IOperationModel, ? extends OperationData>> find(OperationRank rank, boolean isInput) throws Exception {
		checkOperations();

		Collection<IOperation<? extends IOperationModel, ? extends OperationData>> ret = new ArrayList<IOperation<? extends IOperationModel, ? extends OperationData>>(3);
		for (String id : operations.keySet()) {
			IOperation<? extends IOperationModel, ? extends OperationData> operation = operations.get(id);
			OperationRank r      = isInput ? operation.getInputRank() : operation.getOutputRank();
			if (rank==r) {
				ret.add(operation);
			}
		}
		return ret;
	}	
	
	@Override
	public IOperation<? extends IOperationModel, ? extends OperationData> findFirst(String regex) throws Exception {
		checkOperations();
		
		Pattern pattern = Pattern.compile(regex);
		for (String id : operations.keySet()) {
			IOperation<? extends IOperationModel, ? extends OperationData> operation = operations.get(id);
			if (matches(id, operation, pattern)) {
				return operation;
			}
		}
		return null;
	}

	/**
	 * NOTE the regex will be matched as follows on the id of the operation:
	 * <ol>
	 * <li>if matching on the id</li>
	 * <li>if matching the description in lower case</li>
	 * <li>if indexOf the regex in the id is >=0</li>
	 * <li>if indexOf the regex in the description is >=0</li>
	 * </ol>
	 */
	private boolean matches(String id, IOperation<? extends IOperationModel, ? extends OperationData> operation, Pattern pattern) {
		if (pattern.matcher(id).matches()) return true;
		final String description = operation.getDescription();
		if (pattern.matcher(description).matches()) return true;
		String regexp = pattern.pattern();
		if (id.indexOf(regexp)>=0) return true;
		if (description.indexOf(regexp)>=0) return true;
		return false;
	}

	@Override
	public Collection<String> getRegisteredOperations() throws Exception {
		checkOperations();
		return operations.keySet();
	}
	
	@Override
	public Map<String, Collection<IOperation<? extends IOperationModel, ? extends OperationData>>> getCategorizedOperations() throws Exception {
		
		checkOperations();
		
		// Sorted alphabetically by category name string
		final TreeMap<String, Collection<IOperation<? extends IOperationModel,? extends OperationData>>> cats = new TreeMap<>();
		
		for (String catId : categoryId.keySet()) {
			
			final OperationCategory cat = categoryId.get(catId);
			final Collection<IOperation<? extends IOperationModel,? extends OperationData>> group = categoryOp.get(catId);
			
			cats.put(cat.getName(), group);
		}
		
		final LinkedHashMap<String, Collection<IOperation<? extends IOperationModel,? extends OperationData>>> ret = new LinkedHashMap<>();
		ret.putAll(cats);
		
		// Now add all those with no category
		final TreeSet<IOperation<? extends IOperationModel,? extends OperationData>> uncategorized = new TreeSet<>(new AbstractOperationBase.OperationComparator());
		for (String id : operations.keySet()) {
			final IOperation<?, ?> op = operations.get(id);
			if (op instanceof AbstractOperationBase) {
				AbstractOperationBase<?, ?> aop = (AbstractOperationBase<?, ?>) op;
				if (getCategory(aop.getId())==null) uncategorized.add(aop);
			}
		}
		
		ret.put("", uncategorized);
		
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IOperation<? extends IOperationModel, ? extends OperationData> create(String operationId) throws Exception {
		checkOperations();
		final IOperation opValue = operations.get(operationId);
		Objects.requireNonNull(opValue, "No operation available for id " + operationId);
		final IOperation op = opValue.getClass().getConstructor().newInstance();
		AbstractOperationBase<?, ?> aop = null;
		if (op instanceof AbstractOperationBase) {
			aop = (AbstractOperationBase<?, ?>) op;
		}

		Class<? extends IOperationModel> modelClass = getModelClass(operationId);
		if (modelClass == null && aop != null) {
			modelClass = aop.getModelClass();
		}

		if (modelClass == null) throw new RuntimeException("Model class not found! All operations require a model");

		op.setModel(modelClass.getConstructor().newInstance());
		if (aop != null) {
			aop.setName(opValue.getName());
			aop.setDescription(opValue.getDescription());
		}
		return op;
	}

	@Override
	public void createOperations(Class<?> c, String pkg) throws Exception {
		List<Class<?>> classes = ClassUtils.getClassesForPackage(c.getClassLoader(), pkg);
		if (classes.isEmpty()) {
			classes = getClassesFromBundle(FrameworkUtil.getBundle(c), pkg.replace('.', '/'));
		}
		if (operations == null) {
			initialize();
		}

		for (Class<?> class1 : classes) {
			if (Modifier.isAbstract(class1.getModifiers())) continue;
			if (IOperation.class.isAssignableFrom(class1)) {
				IOperation<?, ?> op = (IOperation<?, ?>) class1.getConstructor().newInstance();
				
				operations.put(op.getId(), op);
				logger.debug("Adding operation: {}", class1.getName());
				if (op instanceof AbstractOperationBase) {
					Class<? extends IOperationModel> mc = ((AbstractOperationBase<?, ?>) op).getModelClass();
					models.put(op.getId(), mc);
					logger.debug("    model: {}", mc.getName());
				}
			}
		}
	}

	private static final String CLASS_EXT = ".class";
	private static final Pattern INNER_CLASS = Pattern.compile("^.+\\$\\w+\\" + CLASS_EXT + "$");

	private List<Class<?>> getClassesFromBundle(Bundle b, String pkgDir) {
		final List<Class<?>> classes = new ArrayList<Class<?>>();
		BundleWiring wiring = b.adapt(BundleWiring.class);
		
		Collection<String> entries = wiring.listResources(pkgDir, "*" + CLASS_EXT, BundleWiring.LISTRESOURCES_RECURSE);
		for (String e : entries) {
			if (INNER_CLASS.matcher(e).matches()) {
				continue; // skip inner class files
			}
			String c = e.replace('/', '.');
			int i = c.lastIndexOf(CLASS_EXT);
			if (i >= 0) {
				c = c.substring(0, i);
			}
			try {
				classes.add(b.loadClass(c));
			} catch (ClassNotFoundException ex) {
				logger.error("Could not load class {}", c, ex);
			}
		}
		return classes;
	}


	@Override
	public String getName(String id) throws Exception{
		checkOperations();
		return operations.get(id).getName();
	}

	@Override
	public String getDescription(String id) throws Exception {
		checkOperations();
		return operations.get(id).getDescription();
	}

	@Override
	public IOperationBean createBean() {
		return new OperationBean();
	}
 }
