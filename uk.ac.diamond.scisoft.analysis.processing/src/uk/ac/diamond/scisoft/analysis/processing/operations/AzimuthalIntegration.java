package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.metadata.MaskMetadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

public class AzimuthalIntegration extends AbstractIntegrationOperation<SectorIntegrationModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.azimuthalIntegration";
	}
	
	@Override
    public String getName() {
		return "Sector Azimuthal Profile";
	}


	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		
		Dataset mask = null;
		try {
			List<MaskMetadata> maskMetadata = slice.getMetadata(MaskMetadata.class);
			if (maskMetadata != null && !maskMetadata.isEmpty()) {
				mask = DatasetUtils.convertToDataset(maskMetadata.get(0).getMask());
			}
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		SectorROI sector = (SectorROI)getRegion();
		
		
		final Dataset[] profile = ROIProfile.sector((Dataset)slice, mask, sector, false, true, false);
		
		Dataset integral = profile[1];
		integral.setName("Azimuthal Profile "+sector.getName());
		

		// If not symmetry profile[3] is null, otherwise plot it.
	    if (profile.length>=4 && profile[3]!=null && sector.hasSeparateRegions()) {
	    	
			throw new OperationException(this, "Symmetry as separate dataset not currently supported!");
	    	
	    } else {
	    	return new OperationData(integral, mask, sector);
	    }

	}
	
}
