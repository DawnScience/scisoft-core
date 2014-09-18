package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;

public class BoxIntegration extends AbstractIntegrationOperation<BoxIntegrationModel> {


	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.boxIntegration";
	}

	
	@Override
    public String getName() {
		return "Box Integration";
	}
	
	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		
		Dataset mask = null;
		try {
			MaskMetadata maskMetadata = ((MaskMetadata)slice.getMetadata(MaskMetadata.class));
			mask = (Dataset)maskMetadata.getMask().getSlice((Slice[])null);
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		RectangularROI rect = (RectangularROI)getRegion();
		
		
		final Dataset[] profile = ROIProfile.box((Dataset)slice, mask, rect);
		
		Dataset x = profile[0];
		x.setName("Box X Profile "+rect.getName());
		
		Dataset y = profile[1];
		y.setName("Box Y Profile "+rect.getName());
		

		// If not symmetry profile[3] is null, otherwise plot it.
		OperationData ret = new OperationData(x, y);
	    ret.setAuxData(rect);

	    return ret;
	}

}
