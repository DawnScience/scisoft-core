package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.dawb.common.services.IImageProcessingService;
import org.dawb.common.services.ServiceManager;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;

public class GaussianBlurOperation extends AbstractSimpleImageOperation<KernelWidthModel> {

	
	IImageProcessingService service = null;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.GaussianBlurOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset,
			IImageProcessingService service) {

		return service.filterGaussianBlur(dataset, -1, ((KernelWidthModel)model).getWidth());
	}
}
