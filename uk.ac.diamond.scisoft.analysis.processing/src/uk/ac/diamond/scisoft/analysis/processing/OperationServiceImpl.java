/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.ISliceConfiguration;
import org.eclipse.dawnsci.analysis.api.processing.InvalidRankException;
import org.eclipse.dawnsci.analysis.api.processing.OperationCategory;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.api.slice.SliceVisitor;
import org.eclipse.dawnsci.analysis.api.slice.Slicer;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.metadata.OriginMetadataImpl;

/**
 * Do not use this class externally. Instead get the IOperationService
 * from OSGI.
 * 
 * @author Matthew Gerring
 *
 */
public class OperationServiceImpl implements IOperationService {
	
	enum ExecutionType {
		SERIES, PARALLEL, GRAPH;
	}
	
	static {
		System.out.println("Starting operation service");
	}
	
	// Generic gone mad ; ah - hahaha...
	private Map<String, IOperation<? extends IOperationModel, ? extends OperationData>>              operations;
	private Map<String, Class<? extends IOperationModel>>                                            models;
	private Map<String, OperationCategory>                                                           categoryId;
	private Map<String, Collection<IOperation<? extends IOperationModel, ? extends OperationData>>>  categoryOp;
	private Map<String, String>                                                                      opIdCategory;
	
	private final static Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	public OperationServiceImpl() {
		// Intentionally do nothing
	}

	/**
	 * Uses the Slicer.visitAll(...) method which conversions also use to process
	 * stacks out of the rich dataset passed in.
	 */
	@Override
	public void executeSeries(final ISliceConfiguration dataset,final IMonitor monitor, final IExecutionVisitor visitor, final IOperation<? extends IOperationModel, ? extends OperationData>... series) throws OperationException {
        execute(dataset, monitor, visitor, series, ExecutionType.SERIES);
	}


	@Override
	public void executeParallelSeries(ISliceConfiguration dataset,final IMonitor monitor, IExecutionVisitor visitor, IOperation<? extends IOperationModel, ? extends OperationData>... series) throws OperationException {
        execute(dataset, monitor, visitor, series, ExecutionType.PARALLEL);
	}

	private long parallelTimeout;
	
	private void execute(final ISliceConfiguration dataset,final IMonitor monitor, final IExecutionVisitor v, final IOperation<? extends IOperationModel, ? extends OperationData>[] series, ExecutionType type) throws OperationException {
		
		final IExecutionVisitor visitor = v ==null ? new IExecutionVisitor.Stub() : v;
		
		if (type==ExecutionType.GRAPH) {
			throw new OperationException(series[0], "The edges are needed to execute a graph using ptolemy!");
		}
		
		Map<Integer, String> slicing = dataset.getSlicing();
		if (slicing==null) slicing = Collections.emptyMap();
		for (Iterator<Integer> iterator = slicing.keySet().iterator(); iterator.hasNext();) {
			Integer dim = iterator.next();
			if ("".equals(slicing.get(dim))) iterator.remove();
		}
		
		OriginMetadata om = null;
		
		try {
			om = dataset.getData().getMetadata(OriginMetadata.class).get(0);
		} catch (Exception e1) {
			logger.warn("No origin metadata in operation input!");
		}
		
		final OriginMetadataImpl originMetadata = (OriginMetadataImpl)om;
		// determine data axes to populate origin metadata
		final int[] dataDims = Slicer.getDataDimensions(dataset.getData().getShape(), slicing);
			
		try {
			// We check the pipeline ranks are ok
	        final IDataset firstSlice = Slicer.getFirstSlice(dataset.getData(), slicing);
			validate(firstSlice, series);
			// Create the slice visitor
			SliceVisitor sv = new SliceVisitor() {
				
				@Override
				public void visit(IDataset slice, Slice[] slices, int[] shape) throws Exception {
					
					OriginMetadataImpl innerOm = originMetadata;
					
					if (monitor != null && monitor.isCancelled()) return;
					if (innerOm == null){ 
						innerOm = new OriginMetadataImpl(dataset.getData(), slices, dataDims,"",dataset.getData().getName());
						slice.setMetadata(innerOm);
					} else {
						innerOm.setCurrentSlice(slices);
						slice.setMetadata(innerOm);
					}
					
					String path = innerOm.getFilePath();
					if (path == null) path = "";
					
					OperationData  data = new OperationData(slice, (Serializable[])null);
					long start = System.currentTimeMillis();
					for (IOperation i : series) {
						
						if (monitor!=null) {
							monitor.subTask(path +" : " + i.getName());
						}
						
						OperationData tmp = i.execute(data.getData(), monitor);

						visitor.notify(i, tmp, slices, shape, dataDims); // Optionally send intermediate result
						data = i.isPassUnmodifiedData() ? data : tmp;
					}
					logger.debug("Slice ran in: " +(System.currentTimeMillis()-start)/1000. + " s : Thread" +Thread.currentThread().toString());

					visitor.executed(data, monitor, slices, shape, dataDims); // Send result.
					if (monitor != null) monitor.worked(1);
				}

				@Override
				public boolean isCancelled() {
					return monitor!=null ? monitor.isCancelled() : false;
				}
			};
			
			visitor.init(series, originMetadata);
			long start = System.currentTimeMillis();
			// Jake's slicing from the conversion tool is now in Slicer.
			if (type==ExecutionType.SERIES) {
				Slicer.visitAll(dataset.getData(), slicing, "Slice", sv);
				
			} else if (type==ExecutionType.PARALLEL) {
				Slicer.visitAllParallel(dataset.getData(), slicing, "Slice", sv, parallelTimeout>0 ? parallelTimeout : 5000);
				
			} else {
				throw new OperationException(series[0], "The edges are needed to execute a graph using ptolemy!");
			}
			logger.debug("Data ran in: " +(System.currentTimeMillis()-start)/1000. + " s");
			
		} catch (OperationException o) {
			throw o;
		} catch (Exception e) {
			throw new OperationException(null, e);
		} finally {
			if (visitor != null)
				try {
					visitor.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	/**
	 * Checks that the pipeline passed in has a reasonable rank (for instance)
	 * 
	 * @param firstSlice - may be null, image assumed if it is
	 * @param series
	 */
	public void validate(
			IDataset firstSlice,
			IOperation<? extends IOperationModel, ? extends OperationData>... series)
			throws InvalidRankException, OperationException {
		       
//		if (firstSlice==null) firstSlice = Random.rand(new int[]{1024, 1024});
        if (series[0].getInputRank()==OperationRank.SAME) {
        	throw new InvalidRankException(series[0], "The input rank may not be "+OperationRank.SAME);
        }
        
        int[] squeezedShape = null;
        
        if (firstSlice != null) squeezedShape = AbstractDataset.squeezeShape(firstSlice.getShape(), false);
        
        if (series[0].getInputRank().isDiscrete() && firstSlice != null) {
        	
	        if (squeezedShape.length != series[0].getInputRank().getRank()) {
	        	InvalidRankException e = new InvalidRankException(series[0], "The slicing results in a dataset of rank "+firstSlice.getRank()+" but the input rank of '"+series[0].getDescription()+"' is "+series[0].getInputRank().getRank());
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
        	
	        for (int i = 1; i < series.length; i++) {
	        	OperationRank input = series[i].getInputRank();
	        	if (input == OperationRank.ANY)  input = firstRank;
	        	if (!input.isCompatibleWith(output)) {
	        		throw new InvalidRankException(series[i], "The output of '"+series[i-1].getName()+"' is not compatible with the input of '"+series[i].getName()+"'.");
	        	}
	        	output = series[i].getOutputRank();
	        	if (output == OperationRank.SAME) output = input;
	        	if (series[i].isPassUnmodifiedData()) output = input;
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

	public long getParallelTimeout() {
		return parallelTimeout;
	}

	public void setParallelTimeout(long parallelTimeout) {
		this.parallelTimeout = parallelTimeout;
	}

	// Reads the declared operations from extension point, if they have not been already.
	private synchronized void checkOperations() throws CoreException {
		if (operations!=null) return;
		
		operations = new HashMap<String, IOperation<? extends IOperationModel, ? extends OperationData>>(31);
		models     = new HashMap<String, Class<? extends IOperationModel>>(31);
		categoryOp = new HashMap<String, Collection<IOperation<? extends IOperationModel, ? extends OperationData>>>(7);		
		categoryId = new HashMap<String, OperationCategory>(7);
		opIdCategory = new HashMap<String, String>();
		
		IConfigurationElement[] eles = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.dawnsci.analysis.api.operation");
		for (IConfigurationElement e : eles) {
	    	if (!e.getName().equals("category")) continue;
			final String     id   = e.getAttribute("id");
			final String     name = e.getAttribute("name");
			final String     icon = e.getAttribute("icon");
			categoryId.put(id, new OperationCategory(name, icon, id));		
		}
		
		eles = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.dawnsci.analysis.api.operation");
		for (IConfigurationElement e : eles) {
	    	if (!e.getName().equals("operation")) continue;
			final String     id = e.getAttribute("id");
			IOperation<? extends IOperationModel, ? extends OperationData> op = null;
			try {
				op = (IOperation<? extends IOperationModel, ? extends OperationData>)e.createExecutableExtension("class");
			} catch (Exception e1) {
				e1.printStackTrace();
				continue;
			}
			
			operations.put(id, op);
			
			final String     catId= e.getAttribute("category");
			opIdCategory.put(id, catId);
			
			if (catId!=null) {
				Collection<IOperation<? extends IOperationModel, ? extends OperationData>> ops = categoryOp.get(catId);
				if (ops==null) {
					ops = new TreeSet<IOperation<? extends IOperationModel,? extends OperationData>>(new AbstractOperation.OperationComparitor());
					categoryOp.put(catId, ops);
				}
				ops.add(op);
			}

			if (op instanceof AbstractOperation) {
				final String name = e.getAttribute("name");
				AbstractOperation<? extends IOperationModel, ? extends OperationData> aop = (AbstractOperation<? extends IOperationModel, ? extends OperationData>)op;
				aop.setName(name);
				
				final String desc = e.getAttribute("description");
				if (desc!=null) aop.setDescription(desc);

			}
			
			final String     model = e.getAttribute("model");
			if (model!=null && !"".equals(model)) {
				models.put(id, ((IOperationModel)e.createExecutableExtension("model")).getClass());
			}
			
		}
	}
	

	@Override
	public Class<? extends IOperationModel> getModelClass(String operationId) throws Exception {
		return models.get(operationId);
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
		for (String id : operations.keySet()) {
			IOperation<? extends IOperationModel, ? extends OperationData> operation = operations.get(id);
			if (matches(id, operation, regex)) {
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
		
		for (String id : operations.keySet()) {
			IOperation<? extends IOperationModel, ? extends OperationData> operation = operations.get(id);
			if (matches(id, operation, regex)) {
				return operation;
			}
		}
		return null;
	}

     /**
	 * NOTE the regex will be matched as follows on the id of the operation:
	 * 1. if matching on the id
	 * 2. if matching the description in lower case.
	 * 3. if indexOf the regex in the id is >0
	 * 4. if indexOf the regex in the description is >0
	 */
	private boolean matches(String id, IOperation<? extends IOperationModel, ? extends OperationData> operation, String regex) {
		if (id.matches(regex)) return true;
		final String description = operation.getDescription();
		if (description.matches(regex)) return true;
		if (id.indexOf(regex)>0) return true;
		if (description.indexOf(regex)>0) return true;
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
		final TreeMap<String, Collection<IOperation<? extends IOperationModel,? extends OperationData>>> cats = new TreeMap<String, Collection<IOperation<? extends IOperationModel,? extends OperationData>>>();
		
		for (String catId : categoryId.keySet()) {
			
			final OperationCategory cat = categoryId.get(catId);
			final Collection<IOperation<? extends IOperationModel,? extends OperationData>> group = categoryOp.get(catId);
			
			cats.put(cat.getName(), group);
		}
		
		final LinkedHashMap<String, Collection<IOperation<? extends IOperationModel,? extends OperationData>>> ret = new LinkedHashMap<String, Collection<IOperation<? extends IOperationModel,? extends OperationData>>>();
		ret.putAll(cats);
		
		// Now add all those with no category
		final TreeSet<IOperation<? extends IOperationModel,? extends OperationData>> uncategorized = new TreeSet<IOperation<? extends IOperationModel,? extends OperationData>>(new AbstractOperation.OperationComparitor());
		for (String id : operations.keySet()) {
			final IOperation op = operations.get(id);
			if (op instanceof AbstractOperation) {
				AbstractOperation<IOperationModel, OperationData> aop = (AbstractOperation<IOperationModel, OperationData>)op;
				if (getCategory(aop.getId())==null) uncategorized.add(aop);
			}
		}
		
		ret.put("", uncategorized);
		
		return ret;
	}

	@Override
	public IOperation<? extends IOperationModel, ? extends OperationData> create(String operationId) throws Exception {
		checkOperations();
		IOperation<? extends IOperationModel, ? extends OperationData> op = operations.get(operationId).getClass().newInstance();
		if (op instanceof AbstractOperation) {
			AbstractOperation<? extends IOperationModel, ? extends OperationData> aop = (AbstractOperation<? extends IOperationModel, ? extends OperationData>)op;
			aop.setName(operations.get(operationId).getName());
			aop.setDescription(operations.get(operationId).getDescription());
			try {
				Class modelType = aop.getModelClass();
				((IOperation)aop).setModel((IOperationModel)modelType.newInstance());
			} catch (Exception e) {
				logger.debug("Could not add model",e);
			}
		}
		return op;
	}

	@Override
	public void createOperations(ClassLoader cl, String pakage) throws Exception {
		
		final List<Class<?>> classes = ClassUtils.getClassesForPackage(cl, pakage);
		for (Class<?> class1 : classes) {
			if (Modifier.isAbstract(class1.getModifiers())) continue;
			if (IOperation.class.isAssignableFrom(class1)) {
				
				IOperation<? extends IOperationModel, ? extends OperationData> op = (IOperation<? extends IOperationModel, ? extends OperationData>) class1.newInstance();
				if (operations==null) operations = new HashMap<String, IOperation<? extends IOperationModel, ? extends OperationData>>(31);
				
				operations.put(op.getId(), op);

			}
		}
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
	public void executeSeries(
			ISliceConfiguration dataset,
			IOperation<? extends IOperationModel, ? extends OperationData>... series)
			throws OperationException {
		executeSeries(dataset, null, null, series);
	}

	@Override
	public void executeParallelSeries(
			ISliceConfiguration dataset,
			IOperation<? extends IOperationModel, ? extends OperationData>... series)
			throws OperationException {
		executeSeries(dataset, null, null, series);
		
	}


 }
