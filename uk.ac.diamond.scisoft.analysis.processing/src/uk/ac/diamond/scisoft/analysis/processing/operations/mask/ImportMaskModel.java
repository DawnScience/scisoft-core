package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.model.FileType;
import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;

public class ImportMaskModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter the path to the mask file", file = FileType.EXISTING_FILE, label = "Select Mask File:")
	String filePath = "";

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}
	
}
