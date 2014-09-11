package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

public class MultiplicativeIntensityCorrectionOperation extends
		AbstractOperation<MultiplicativeIntensityCorrectionModel, OperationData> {

	Dataset correction;
	IDiffractionMetadata metadata;
	PropertyChangeListener listener;
	
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
	
	@Override
	public void setModel(MultiplicativeIntensityCorrectionModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					MultiplicativeIntensityCorrectionOperation.this.correction = null;
				}
			};
		} else {
			model.removePropertyChangeListener(listener);
		}
		
		model.addPropertyChangeListener(listener);
	}
	
	private Dataset calculateCorrectionArray(IDataset data, IDiffractionMetadata md) {
		
		MultiplicativeIntensityCorrectionModel m = (MultiplicativeIntensityCorrectionModel)model;
		
		Dataset cor = DatasetFactory.ones(data.getShape(), Dataset.FLOAT64);

		Dataset tth = PixelIntegrationUtils.generate2ThetaArrayRadians(data.getShape(), md);

		if (m.isApplySolidAngleCorrection()) {
			PixelIntegrationUtils.solidAngleCorrection(cor,tth);
		}

		if (m.isApplyPolarisationCorrection()) {
			Dataset az = PixelIntegrationUtils.generateAzimuthalArray(data.getShape(), md, true);
			az.iadd(Math.toRadians(m.getPolarisationAngularOffset()));
			PixelIntegrationUtils.polarisationCorrection(cor, tth, az, m.getPolarisationFactor());
		}

		if (m.isApplyDetectorTransmissionCorrection()) {
			PixelIntegrationUtils.detectorTranmissionCorrection(cor, tth, m.getTransmittedFraction());
		}

		return cor;
	}

}
