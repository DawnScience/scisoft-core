package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

public class AzimuthalIntegration extends AbstractIntegrationOperation {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.azimuthalIntegration";
	}

	@Override
	public IDataset execute(IDataset islice) throws OperationException {
		
		// TODO FIXME This is not right for the operation.
		
		Dataset slice = (Dataset)islice;
		Dataset mask  = (Dataset)data.getMask().getSlice((Slice)null);
		SectorROI sector = (SectorROI)data.getRegions().get(0);
		
		
		final AbstractDataset[] profile = ROIProfile.sector(slice, mask, sector, false, true, false);
		
		AbstractDataset integral = profile[1];
		integral.setName("Azimuthal Profile "+sector.getName());
		

		// If not symmetry profile[3] is null, otherwise plot it.
	    if (profile.length>=4 && profile[3]!=null && sector.hasSeparateRegions()) {
	    	
			throw new OperationException(this, "Symmetry as separate dataset not currently supported!");
	    	
	    } else {
	    	return integral;
	    }

	}
	
}
