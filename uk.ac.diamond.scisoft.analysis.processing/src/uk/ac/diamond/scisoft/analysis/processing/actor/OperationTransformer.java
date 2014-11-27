package uk.ac.diamond.scisoft.analysis.processing.actor;

import java.util.List;

import org.dawb.passerelle.common.actors.AbstractDataMessageTransformer;
import org.dawb.passerelle.common.message.MessageUtils;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.message.DataMessageComponent;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.api.slice.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.api.slice.SourceInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

import com.isencia.passerelle.actor.ProcessingException;

/**
 * Transformer to run an operation. 
 * 
 * @author fcp94556
 *
 */
public class OperationTransformer extends AbstractDataMessageTransformer {
	
	private static final Logger logger = LoggerFactory.getLogger(OperationTransformer.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 4261989437774965670L;

	private IOperation<IOperationModel, OperationData> operation;
	private IOperationContext                          context;

	public OperationTransformer(CompositeEntity container, String name)	throws NameDuplicationException, IllegalActionException {
		super(container, createNonNullName(name));
		// TODO We could have attributes in here for editing the operation or operation model.
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
			
			OperationData tmp = operation.execute(data, context.getMonitor());
			
			data = operation.isPassUnmodifiedData() ? data : tmp.getData();

			final ILazyDataset info = msg.getSlice();
			final SliceFromSeriesMetadata ssm = info.getMetadata(SliceFromSeriesMetadata.class).get(0);
			SourceInformation ssource = null;
			try {
				ssource = context.getData().getMetadata(SliceFromSeriesMetadata.class).get(0).getSourceInfo();
			}catch (Exception e) {
				logger.error("Source not obtainable. Hope this is just a unit test...");
			}
			SliceFromSeriesMetadata fullssm = new SliceFromSeriesMetadata(ssource, ssm.getShapeInfo(), ssm.getSliceInfo());
			tmp.getData().setMetadata(fullssm);
			if (context.getVisitor()!=null) {
				context.getVisitor().notify(operation, tmp); // Optionally send intermediate result
			
			    if (output.getWidth()<1) { // We have reached the end.
			    	OperationData odata = new OperationData(data);
			    	if (!operation.isPassUnmodifiedData()) odata.setAuxData(tmp.getAuxData());
			    	
			    	context.getVisitor().executed(odata, context.getMonitor()); // Send result.
					if (context.getMonitor() != null) context.getMonitor().worked(1);
					
					logger.debug(info.getName()+" ran in: " +(System.currentTimeMillis()-msg.getTime())/1000. + " s : Thread" +Thread.currentThread().toString());
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

	@Override
	protected String getOperationName() {
		return operation.getName();
	}

	public IOperation<IOperationModel, OperationData> getOperation() {
		return operation;
	}

	public void setOperation(IOperation<IOperationModel, OperationData> operation) {
		this.operation = operation;
	}

	public IOperationContext getContext() {
		return context;
	}

	public void setContext(IOperationContext context) {
		this.context = context;
	}

}
