package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.dawb.common.services.IImageProcessingService;
import org.dawb.common.services.ServiceManager;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

public abstract class AbstractSimpleImageOperation<T extends IOperationModel> extends AbstractOperation<IOperationModel,OperationData> {

	IImageProcessingService service = null;

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		if (service == null) {
			try { 
				service = (IImageProcessingService)ServiceManager.getService(IImageProcessingService.class);
			} catch (Exception e) {
				throw new OperationException(this, "Could not get image processing service");
			}
		}
		
		IDataset out = processImage(slice, service);
		copyMetadata(slice, out);
		return new OperationData(out);
	}
	
	public abstract IDataset processImage(IDataset dataset, IImageProcessingService service);

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
