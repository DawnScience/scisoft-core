package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.dawb.common.services.IImageFilterService;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;

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
