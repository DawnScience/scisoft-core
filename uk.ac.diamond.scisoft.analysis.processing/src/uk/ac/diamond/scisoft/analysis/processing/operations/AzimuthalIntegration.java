package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.metadata.MaskMetadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

public class AzimuthalIntegration extends AbstractIntegrationOperation {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.azimuthalIntegration";
	}
	
	@Override
    public String getName() {
		return "Azimuthal Integration";
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
		SectorROI sector = (SectorROI)getRegion();
		
		
		final AbstractDataset[] profile = ROIProfile.sector(slice, mask, sector, false, true, false);
		
		AbstractDataset integral = profile[1];
		integral.setName("Azimuthal Profile "+sector.getName());
		

		// If not symmetry profile[3] is null, otherwise plot it.
	    if (profile.length>=4 && profile[3]!=null && sector.hasSeparateRegions()) {
	    	
			throw new OperationException(this, "Symmetry as separate dataset not currently supported!");
	    	
	    } else {
	    	return new OperationData(integral, mask, sector);
	    }

	}
	
}
