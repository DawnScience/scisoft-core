package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class XPDFSubtractBackgroundModel extends AbstractOperationModel {
	@OperationModelField(hint="Subtract the background from the sample data",label = "Subtract sample background?" )
	private boolean subtractSampleBackground = true;
	@OperationModelField(hint="Subtract the background from the container data",label = "Subtract all container backgrounds?" )
	private boolean subtractContainersBackground = true;

	public boolean isSubtractSampleBackground() {
		return subtractSampleBackground;
	}
	public void setSubtractSampleBackground(boolean subtractSampleBackground) {
		firePropertyChange("subtractSampleBackground", this.subtractSampleBackground, this.subtractSampleBackground = subtractSampleBackground);
	}
	public boolean isSubtractContainersBackground() {
		return subtractContainersBackground;
	}
	public void setSubtractContainersBackground(boolean subtractContainersBackground) {
		firePropertyChange("subtractContainersBackground", this.subtractContainersBackground, this.subtractContainersBackground = subtractContainersBackground);
	}
}
