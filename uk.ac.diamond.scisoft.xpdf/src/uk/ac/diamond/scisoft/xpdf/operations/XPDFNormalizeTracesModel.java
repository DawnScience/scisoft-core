package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class XPDFNormalizeTracesModel extends AbstractOperationModel {
	@OperationModelField(hint="Normalize the sample data, if unnormalized",label = "Normalise sample trace?" )
	private boolean normalizeSample = true;
	@OperationModelField(hint="Normalize all the container data, if unnormalized",label = "Normalise all container traces?" )
	private boolean normalizeContainers = true;
	@OperationModelField(hint="Normalize the beam data, if unnormalized",label = "Normalise empty beam trace?" )
	private boolean normalizeBeam = true;

	public boolean isNormalizeSample() {
		return normalizeSample;
	}
	public void setNormalizeSample(boolean normalizeSample) {
		firePropertyChange("normalizeSample", this.normalizeSample, this.normalizeSample = normalizeSample);
	}
	public boolean isNormalizeContainers() {
		return normalizeContainers;
	}
	public void setNormalizeContainers(boolean normalizeContainers) {
		firePropertyChange("normalizeContainers", this.normalizeContainers, this.normalizeContainers = normalizeContainers);
	}
	public boolean isNormalizeBeam() {
		return normalizeBeam;
	}
	public void setNormalizeBeam(boolean normalizeBeam) {
		firePropertyChange("normalizeBeam", this.normalizeBeam, this.normalizeBeam = normalizeBeam);
	}

}
