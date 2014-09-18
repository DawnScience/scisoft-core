package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.dawb.common.services.IImageFilterService;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;

public class MeanFilterOperation extends AbstractSimpleImageOperation<KernelWidthModel> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.MeanFilterOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset,
			IImageFilterService service) {
		
		return service.filterMean(dataset,((KernelWidthModel)model).getWidth());
	}

}
