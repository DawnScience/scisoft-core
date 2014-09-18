package uk.ac.diamond.scisoft.analysis.processing.test;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;

import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;

public class DiffractionMetadataTestImportOperation extends AbstractOperation<IOperationModel, OperationData> {

	IDiffractionMetadata meta;
	
	public DiffractionMetadataTestImportOperation() {
		DetectorProperties dp = DetectorProperties.getDefaultDetectorProperties(1000,1000);
		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(1);
		meta = new DiffractionMetadata("", dp, ce);
	}
	
	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		
		slice.addMetadata(meta);
		return new OperationData(slice);
	}

	@Override
	public void setModel(IOperationModel parameters) {

	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	public String getId() {
		return this.getClass().getName();
	}
	
}
