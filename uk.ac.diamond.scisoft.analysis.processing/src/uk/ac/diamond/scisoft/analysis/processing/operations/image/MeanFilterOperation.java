package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.dawb.common.services.IImageProcessingService;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

public class MeanFilterOperation extends AbstractSimpleImageOperation<KernelWidthModel> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.MeanFilterOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset,
			IImageProcessingService service) {
		
		return service.filterMean(dataset,((KernelWidthModel)model).getWidth());
	}

}
