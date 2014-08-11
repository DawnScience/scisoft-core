package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;

public class MultiplicativeIntensityCorrectionOperation extends
		AbstractOperation {

	Dataset correction;
	MultiplicativeIntensityCorrectionModel model;
	IDiffractionMetadata metadata;
	
	@Override
	public String getId() {
		return this.getClass().getName();
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(slice);
		
		if (metadata == null || !metadata.equals(md)) {
			metadata = md;
			correction = null;
		}
		
		if (correction == null) correction = calculateCorrectionArray(slice, metadata);
		
		Dataset corrected = Maths.multiply(slice,correction);
		
		copyMetadata(slice, corrected);
		
		return new OperationData(corrected);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	private Dataset calculateCorrectionArray(IDataset data, IDiffractionMetadata md) {

		Dataset cor = DatasetFactory.ones(data.getShape(), AbstractDataset.FLOAT64);

		Dataset tth = PixelIntegrationUtils.generate2ThetaArrayRadians(data.getShape(), md);

		if (model.isApplySolidAngleCorrection()) {
			PixelIntegrationUtils.solidAngleCorrection(cor,tth);
		}

		if (model.isApplyPolarisationCorrection()) {
			AbstractDataset az = PixelIntegrationUtils.generateAzimuthalArray(data.getShape(), md, true);
			az.iadd(Math.toRadians(model.getPolarisationAngularOffset()));
			PixelIntegrationUtils.polarisationCorrection(cor, tth, az, model.getPolarisationFactor());
		}

		if (model.isAppyDetectorTransmissionCorrection()) {
			PixelIntegrationUtils.detectorTranmissionCorrection(cor, tth, model.getTransmittedFraction());
		}

		return cor;
	}

}
