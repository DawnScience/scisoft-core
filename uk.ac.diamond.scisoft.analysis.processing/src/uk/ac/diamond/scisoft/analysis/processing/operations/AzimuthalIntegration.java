package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;

import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;

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
