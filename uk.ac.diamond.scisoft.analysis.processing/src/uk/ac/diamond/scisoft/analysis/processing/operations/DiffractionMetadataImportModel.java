package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.model.FileType;
import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;

public class DiffractionMetadataImportModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter the path to the calibration NeXus file", file = FileType.EXISTING_FILE, label = "Select Calibration File:")
	String filePath = "";

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}
	
}
