package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class SubtractBackgroundImageModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter the path to the background file", file = FileType.EXISTING_FILE, label = "Background File")
	private String filePath = "";

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}
	
}
