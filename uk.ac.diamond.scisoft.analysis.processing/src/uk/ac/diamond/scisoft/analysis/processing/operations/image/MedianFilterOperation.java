package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.dawb.common.services.IImageFilterService;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;

public class MedianFilterOperation extends AbstractSimpleImageOperation<KernelWidthModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.MedianFilterOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset,
			IImageFilterService service) {

		return service.filterMedian(dataset,((KernelWidthModel)model).getWidth());
	}

}
