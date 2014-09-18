package uk.ac.diamond.scisoft.analysis.processing.test;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

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
