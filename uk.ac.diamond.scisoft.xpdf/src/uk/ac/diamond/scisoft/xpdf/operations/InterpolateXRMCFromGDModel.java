package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class InterpolateXRMCFromGDModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter the file name for the calibration of the target detector", file = FileType.EXISTING_FILE, label = "Target detector calibration file")
	private String detectorFileName = null;
	
	public String getDetectorFileName() {
		return this.detectorFileName;
	}
	public void setDetectorFileName(String fileName) {
		firePropertyChange("detectorFileName", this.detectorFileName, this.detectorFileName = fileName);
	}
}
