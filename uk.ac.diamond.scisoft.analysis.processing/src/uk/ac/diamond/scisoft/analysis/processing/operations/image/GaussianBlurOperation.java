package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.dawb.common.services.IImageFilterService;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

public class GaussianBlurOperation extends AbstractSimpleImageOperation<KernelWidthModel> {

	
	IImageFilterService service = null;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.GaussianBlurOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset,
			IImageFilterService service) {

		return service.filterGaussianBlur(dataset, -1, ((KernelWidthModel)model).getWidth());
	}
}
