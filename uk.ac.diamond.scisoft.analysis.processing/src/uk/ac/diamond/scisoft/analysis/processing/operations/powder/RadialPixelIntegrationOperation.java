package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration1D;

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
