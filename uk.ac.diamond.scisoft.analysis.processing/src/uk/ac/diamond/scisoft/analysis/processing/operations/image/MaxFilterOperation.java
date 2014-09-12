package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.dawb.common.services.IImageProcessingService;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

public class MaxFilterOperation extends AbstractSimpleImageOperation<KernelWidthModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.MaxFilterOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset,
			IImageProcessingService service) {
		
		return service.filterMax(dataset,((KernelWidthModel)model).getWidth());
	}
}
