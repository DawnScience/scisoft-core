package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.metadata.MaskMetadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

public class BoxIntegration extends AbstractIntegrationOperation {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.boxIntegration";
	}

	
	@Override
    public String getName() {
		return "Box Integration";
	}
	
	@Override
	public OperationData execute(OperationData islice, IMonitor monitor) throws OperationException {
		
		Dataset slice    = (Dataset)islice.getData();
		Dataset mask = null;
		try {
			MaskMetadata maskMetadata = ((MaskMetadata)data.getMetadata(MaskMetadata.class));
			mask = (Dataset)maskMetadata.getMask().getSlice((Slice[])null);
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		RectangularROI rect = (RectangularROI)getRegion();
		
		
		final AbstractDataset[] profile = ROIProfile.box(slice, mask, rect);
		
		AbstractDataset x = profile[0];
		x.setName("Box X Profile "+rect.getName());
		
		AbstractDataset y = profile[1];
		y.setName("Box Y Profile "+rect.getName());
		

		// If not symmetry profile[3] is null, otherwise plot it.
		OperationData ret = new OperationData(x, y);
	    ret.setAuxData(rect);

	    return ret;
	}

}
