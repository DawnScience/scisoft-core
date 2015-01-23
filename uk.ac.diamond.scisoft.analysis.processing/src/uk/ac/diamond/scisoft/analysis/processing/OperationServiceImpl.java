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
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunnerService;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.InvalidRankException;
import org.eclipse.dawnsci.analysis.api.processing.OperationCategory;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.Slicer;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.dawnsci.hdf5.operation.HierarchicalFileExecutionVisitor;
import org.eclipse.dawnsci.macro.api.IMacroService;
import org.eclipse.dawnsci.macro.api.MacroEventObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Do not use this class externally. Instead get the IOperationService
 * from OSGI.
 * 
 * @author Matthew Gerring
 *
 */
public class OperationServiceImpl implements IOperationService {
	
	private static IOperationRunnerService rservice;
	public static void setOperationRunner(IOperationRunnerService s) {
		rservice = s;
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
			throw new OperationException(null, "No operation list defined, call setSerices(...) with something meaningful please!");
		}
		
		Map<Integer, String> slicing = context.getSlicing();
		if (slicing==null) slicing = Collections.emptyMap();
		for (Iterator<Integer> iterator = slicing.keySet().iterator(); iterator.hasNext();) {
			Integer dim = iterator.next();
			if ("".equals(slicing.get(dim))) iterator.remove();
		}
		context.setSlicing(slicing);
		
		// We check the pipeline ranks are ok
		try {
	        final IDataset firstSlice = Slicer.getFirstSlice(context.getData(), context.getSlicing());
			validate(firstSlice, context.getSeries());
	
			List<SliceFromSeriesMetadata> meta = firstSlice.getMetadata(SliceFromSeriesMetadata.class);
			SliceFromSeriesMetadata ssm = meta!=null && meta.size()>0 ? meta.get(0) : null;
			
			SourceInformation ssource = null;
			
			try {
				 ssource = context.getData().getMetadata(SliceFromSeriesMetadata.class).get(0).getSourceInfo();
			} catch (Exception e) {
				logger.error("Source not obtainable. Hope this is just a unit test...");
			}
			
			try {
				SliceFromSeriesMetadata fullssm = new SliceFromSeriesMetadata(ssource, ssm.getSliceInfo());
				context.getData().setMetadata(fullssm);
			} catch (Exception e) {
				logger.error("Unable to set slice from service metadata on full data.");
			}
			
			IOperationRunner runner = rservice.getRunner(context.getExecutionType());
			runner.init(context);
			runner.execute();
			
			// We send some macro commands, to tell people how to drive the service with
			// macros.
			sendMacroCommands(context);

			
		} catch (OperationException o) {
			throw o;
		} catch (Exception e) {
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
		}

	}
	
	private static int count = 0;
	/**
	 * Constructs a macro by mirroring the context into the python layer. 
	 */
	private void sendMacroCommands(IOperationContext context) {
		
		IMacroService mservice = (IMacroService)Activator.getService(IMacroService.class);
		if (mservice==null) return;
		
		try {
			MacroEventObject evt = new MacroEventObject(this);
			evt.setJythonAllowed(false); // We create CPython specific commands.
			
			evt.setPythonCommand("\n# Recording macro for operation pipeline, warning not all operations work from macro.");
			evt.append("oservice = dnp.plot.getService('"+IOperationService.class.getName()+"')");
			evt.append("context  = oservice.createContext()");
			
			evt.append("\n# Create the data and slice information");
			// Send over the data slice for this run
			String filePath = context.getFilePath();
			String dataPath = context.getDatasetPath();
	        if (context.getData()!=null) {
	    		ILazyDataset lz = context.getData();
	    		List<SliceFromSeriesMetadata> md = lz.getMetadata(SliceFromSeriesMetadata.class);
	            
	    		if (md!=null) { // This means they cannot open up the workflow and have it run directly.
	        		SourceInformation sinfo = md.get(0).getSourceInfo();
	        		if (sinfo!=null) {
	        			filePath = sinfo.getFilePath();
	        			dataPath = sinfo.getDatasetName();
	        		}
	    		}
	        }
	        filePath = filePath.replace('\\', '/');
			evt.append("context.setFilePath('"+filePath+"')");
			evt.append("context.setDatasetPath('"+dataPath+"')");
			evt.append("context.setSlicing("+evt.getMap(context.getSlicing())+")");
			
			// Send over the operations
			createOperationCommands(context, evt);
			
			// Send over the execution settings
			evt.append("\n# Setup the execution");
    		evt.append("context.setParallelTimeout("+context.getParallelTimeout()+")");
			evt.append("context.setPoolSize("+context.getPoolSize()+")");
			evt.append("context.setExecutionType('"+context.getExecutionType()+"')");
			
			evt.append("java_import(jvm, '"+HierarchicalFileExecutionVisitor.class.getPackage().getName()+".*')\n");
			String outputPath = filePath.substring(0, filePath.lastIndexOf('.'))+"_processed"+(++count)+".nxs";
			evt.append("visitor = jvm.HierarchicalFileExecutionVisitor('"+outputPath+""+"')");
			evt.append("context.setVisitor(visitor)");
			
			evt.append("\n# Execute the pipeline (commented out for now)");
			evt.append("# oservice.execute(context)");
			
			mservice.publish(evt);
			
		} catch (Exception ne) {
			ne.printStackTrace();
		}
	}

	private String createOperationCommands(IOperationContext context, MacroEventObject evt) throws Exception {
		
		evt.append("\n# Setup the operations, this requires that models be created which is harder python to understand");
		evt.append("from py4j.java_gateway import JavaGateway, java_import");
		evt.append("gateway = JavaGateway()");
		evt.append("jvm = gateway.jvm");
		
		StringBuilder opList = new StringBuilder();
		IOperation[] ops = context.getSeries();
		for (int i = 0; i < ops.length; i++) {
			IOperation op = ops[i];
			String opName = "operation"+i;
			evt.append(opName+" = oservice.create('"+op.getId()+"')");
			
			final AbstractOperationModel model = (AbstractOperationModel)op.getModel();
			evt.append(model.createMacroCommands("model"));
			evt.append(opName+".setModel(model)");
			opList.append(opName);
			if (i < ops.length-1)opList.append(','); 
		}
		evt.append("context.setSeries(["+opList+"])");
		return opList.toString();
	}

	/**
	 * Checks that the pipeline passed in has a reasonable rank (for instance)
	 * 
	 * @param firstSlice - may be null, image assumed if it is
	 * @param series
	 */
	public void validate( IDataset firstSlice,
			              IOperation<? extends IOperationModel, ? extends OperationData>... series) throws InvalidRankException, OperationException {
		       
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
		if (models.containsKey(operationId)) {
			return models.get(operationId);
		}
		IOperation<? extends IOperationModel, ? extends OperationData> op = create(operationId);
		if (op instanceof AbstractOperation) {
			return ((AbstractOperation)op).getModelClass();
		}
		return null; // Normally one of the above lines would throw an exception before this.
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
 }
