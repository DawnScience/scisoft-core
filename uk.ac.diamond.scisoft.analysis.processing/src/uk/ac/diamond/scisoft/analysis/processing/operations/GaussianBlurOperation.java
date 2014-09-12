package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.dawb.common.services.IImageProcessingService;
import org.dawb.common.services.ServiceManager;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

public class GaussianBlurOperation extends AbstractOperation<GaussianBlurModel,OperationData> {

	
	IImageProcessingService service = null;
	
	@Override
	public String getId() {
		return this.getClass().getName();
	}

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
		
		IDataset blur = service.filterGaussianBlur(slice, -1, ((GaussianBlurModel)model).getGaussianWidth());
		
		copyMetadata(slice, blur);

		return new OperationData(blur);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
