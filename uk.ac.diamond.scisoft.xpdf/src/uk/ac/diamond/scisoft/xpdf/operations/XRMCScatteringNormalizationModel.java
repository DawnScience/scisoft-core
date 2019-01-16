package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class XRMCScatteringNormalizationModel extends AbstractOperationModel {

	@OperationModelField(label = "Correct for pixel solid angle", description = "Normalize the XRMC count data by the solid angle subtended by each pixel")
	private boolean normalizeOmega = true;
	
	public void setNormalizeOmega(boolean normega) {
		firePropertyChange("normalizeOmega", this.normalizeOmega, this.normalizeOmega = normega);
	}
	
	public boolean isNormalizeOmega() {
		return this.normalizeOmega;
	}
}
