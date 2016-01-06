package uk.ac.diamond.scisoft.analysis.processing.actor.actors;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dawb.passerelle.actors.data.config.IOperationModelInstanceProvider;
import org.dawb.passerelle.actors.data.config.OperationModelParameter;
import org.dawb.passerelle.common.actors.AbstractDataMessageTransformer;
import org.dawb.passerelle.common.message.MessageUtils;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.message.DataMessageComponent;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import uk.ac.diamond.scisoft.analysis.processing.actor.Activator;

import com.isencia.passerelle.actor.ProcessingException;
import com.isencia.passerelle.util.ptolemy.IAvailableChoices;
import com.isencia.passerelle.util.ptolemy.StringChoiceParameter;

/**
 * Transformer to run an operation. 
 * 
 * @author Matthew Gerring
 *
 */
public class OperationTransformer extends AbstractDataMessageTransformer implements IOperationModelInstanceProvider{
	
	
	private static final Logger logger = LoggerFactory.getLogger(OperationTransformer.class);

	private static IOperationService oservice;
	public static void setOperationService(IOperationService s) {
		oservice = s;
	}
	// Because actors can be instantiated outside OSGI by ptolemy,
	// it can be that oservice is not set
	private static IOperationService getOperationService() {
		if (oservice==null) {
			oservice = (IOperationService)Activator.getService(IOperationService.class);
		}
		return oservice;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4261989437774965670L;

	private IOperation<? extends IOperationModel, ? extends OperationData> operation;
	private IOperationContext                          context;   // May be null.
	
	/**
	 * 
	 */
	public final StringChoiceParameter                 operationId; 
	/**
	 * 
	 */
	public final OperationModelParameter               model;

	public OperationTransformer(CompositeEntity container, String name)	throws NameDuplicationException, IllegalActionException {
		
		super(container, createNonNullName(name));
		
		operationId = new StringChoiceParameter(this, "Operation", new IAvailableChoices() {		
			@Override
			public String[] getChoices() {
                try {
                	Collection<String> ops = getOperationService().getRegisteredOperations();
                	return ops.toArray(new String[ops.size()]);
                } catch (Exception ne) {
                	return new String[]{"Please select a Data File"};
                }
			}
			@Override
			public Map<String,String> getVisibleChoices() {
                try {
                  	Collection<String>  ops = getOperationService().getRegisteredOperations();
                	Map<String, String> ret = new HashMap<String, String>(ops.size());
                	for (String id : ops) ret.put(id, getOperationService().getName(id));
                	return ret;
               } catch (Exception ne) {
                	return null;
                }
			}
		}, 1 << 2); // Single selection bit
		setDescription(operationId, Requirement.ESSENTIAL, VariableHandling.NONE, "The id of the operation that you would like to use.");
		registerConfigurableParameter(operationId);


		model = new OperationModelParameter(this, "Model");
		setDescription(model, Requirement.ESSENTIAL, VariableHandling.NONE, "The model for the operation we are running.\n\nThis model will be saved and edited with the same table available in the processing perspective.");
		registerConfigurableParameter(model);

	}

	@Override
	public Class<? extends IOperationModel> getModelClass() throws Exception {
		final String opId = operationId.getExpression();
		if (opId !=null) return getOperationService().getModelClass(opId);
		return null;
 	}

	private static long nullNameCount = 0;
	private static String createNonNullName(String name) {
		if (name!=null) return name;
		return "OperationTransformer_"+(++nullNameCount);
	}

	@Override
	protected DataMessageComponent getTransformedMessage(List<DataMessageComponent> cache) throws ProcessingException {
		
		try {
			DataMessageComponent msg = MessageUtils.mergeAll(cache);
			
			IDataset data = (IDataset)msg.getList().values().iterator().next();
			
			
			SliceFromSeriesMetadata fullssm = null;
			try {
				SourceInformation ssource = data.getMetadata(SliceFromSeriesMetadata.class).get(0).getSourceInfo();
				final SliceFromSeriesMetadata ssm = data.getMetadata(SliceFromSeriesMetadata.class).get(0);
				fullssm = new SliceFromSeriesMetadata(ssource, ssm.getSliceInfo());
				data.setMetadata(fullssm);
				
			}catch (Exception e) {
				logger.error("Source not obtainable. Hope this is just a unit test...");
			}
			

			if (operation==null) operation = createOperation();
			OperationData tmp = operation.execute(data, context!=null ? context.getMonitor() : null);

			if (tmp == null) {
				//if null after all operations, still need to execute with null
				//so the visitor knows a pass has finished
				
				if (output.getWidth()<1 && context!=null && context.getVisitor()!=null) {
					context.getVisitor().executed(null, context.getMonitor());
					if (context.getMonitor() != null) context.getMonitor().worked(1);
					logger.debug("null output ran in: " +(System.currentTimeMillis()-msg.getTime())/1000. + " s : Thread" +Thread.currentThread().toString());
				}
				
				return null;
			}
			
			data = operation.isPassUnmodifiedData() ? data : tmp.getData();

			List<SliceFromSeriesMetadata> md = tmp.getData().getMetadata(SliceFromSeriesMetadata.class);
			
			if (fullssm!=null && (md == null || md.isEmpty())) tmp.getData().setMetadata(fullssm);
			
			if (context!=null && context.getVisitor()!=null) {
				context.getVisitor().notify(operation, tmp); // Optionally send intermediate result
			
			    if (output.getWidth()<1) { // We have reached the end.
			    	OperationData odata = new OperationData(data);
			    	if (!operation.isPassUnmodifiedData()) odata.setAuxData(tmp.getAuxData());
			    	
			    	context.getVisitor().executed(odata, context.getMonitor()); // Send result.
					if (context.getMonitor() != null) {
						context.getMonitor().worked(1);
						String update = "";
						if (fullssm != null) {
							try {
							String filePath = fullssm.getFilePath();
							File f = new File(filePath);
							String name = f.getName();
							String slice = Slice.createString(fullssm.getSliceFromInput());
							update = name+ " ["+ slice + "] " + operation.getName();
							} catch (Exception e) {
								logger.error("Could not update progress: " + e.getMessage());
							}
						}
						context.getMonitor().subTask(update);
						
					}
					
					logger.debug(data.getName()+" ran in: " +(System.currentTimeMillis()-msg.getTime())/1000. + " s : Thread" +Thread.currentThread().toString());
			    }
			}
			
			// Construct a DataMessageComponent to pass on.
			msg.clearList();
			msg.setList(data);
			return msg;
			
		} catch (Exception ne) {
			throw createDataMessageException(ne.getMessage(), ne);
		}
	}

	private IOperation<? extends IOperationModel, ? extends OperationData> createOperation() throws Exception {
        IOperation<IOperationModel, OperationData> op      = (IOperation<IOperationModel, OperationData>)getOperationService().create(operationId.getExpression());
        IOperationModel omod = model.getValue(getOperationService().getModelClass(op.getId()));
        op.setModel(omod);
        return op;
	}

	@Override
	protected String getOperationName() {
		return operation.getName();
	}

	public IOperation<? extends IOperationModel, ? extends OperationData> getOperation() {
		return operation;
	}

	public void setOperation(IOperation<? extends IOperationModel, ? extends OperationData> operation) {
		this.operation = operation;
		
		operationId.setExpression(operation.getId());
		model.setValue(operation.getModel());
	}

	public IOperationContext getContext() {
		return context;
	}

	public void setContext(IOperationContext context) {
		this.context = context;
	}

}
