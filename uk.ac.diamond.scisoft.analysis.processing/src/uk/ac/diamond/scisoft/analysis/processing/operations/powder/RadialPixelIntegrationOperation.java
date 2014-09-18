package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration1D;
import uk.ac.diamond.scisoft.analysis.metadata.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;

public class RadialPixelIntegrationOperation extends AzimuthalPixelIntegrationOperation {

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected AbstractPixelIntegration createIntegrator(
			PixelIntegrationModel model, IDiffractionMetadata md) {
		AbstractPixelIntegration integ = super.createIntegrator(model, md);
		((AbstractPixelIntegration1D)integ).setAzimuthalIntegration(false);
		return integ;
	}

}
