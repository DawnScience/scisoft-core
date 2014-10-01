package uk.ac.diamond.scisoft.analysis.processing.operations.export;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class ExportAsText1DModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter the path to output directory", file = FileType.EXISTING_FOLDER, label = "Select Output Directory:")
	private String outputDirectoryPath = "";

	public String getOutputDirectoryPath() {
		return outputDirectoryPath;
	}

	public void setOutputDirectoryPath(String outputDirectoryPath) {
		firePropertyChange("outputDirectoryPath", this.outputDirectoryPath, this.outputDirectoryPath = outputDirectoryPath);
	}
	
}
