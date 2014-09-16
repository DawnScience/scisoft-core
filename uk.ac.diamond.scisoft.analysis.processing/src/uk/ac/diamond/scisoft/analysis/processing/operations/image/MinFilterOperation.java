package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.dawb.common.services.IImageFilterService;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

public class MinFilterOperation extends AbstractSimpleImageOperation<KernelWidthModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.MinFilterOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset,
			IImageFilterService service) {

		return service.filterMin(dataset,((KernelWidthModel)model).getWidth());
	}
}
