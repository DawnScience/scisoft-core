package uk.ac.diamond.scisoft.analysis.processing.operations.export;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class ExportAsText1DModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter the path to output directory", file = FileType.EXISTING_FOLDER, label = "Output Directory")
	private String outputDirectoryPath = "";
	@OperationModelField(hint="Enter the extension that should be used for the output file", label = "File Extension")
	private String extension = "dat";
	@OperationModelField(label = "Include explicit location", hint = "Select to include the explicit (slice) location of the data in the full dataset as part of the filename")
	private boolean includeSliceName = false;
	@OperationModelField(label = "Pad with zeros", hint = "Leave blank for no padding", min = 1)
	private Integer zeroPad = 5;
	@OperationModelField(label = "Suffix", hint = "Add a custom suffix to be appended to the file name, leave blank for no suffix")
	private String suffix = "";

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		firePropertyChange("extension", this.extension, this.extension = extension);
	}

	public boolean isIncludeSliceName() {
		return includeSliceName;
	}

	public void setIncludeSliceName(boolean includeSliceName) {
		firePropertyChange("includeSliceName", this.includeSliceName, this.includeSliceName = includeSliceName);
	}

	public Integer getZeroPad() {
		return zeroPad;
	}

	public void setZeroPad(Integer zeroPad) {
		firePropertyChange("zeroPad", this.zeroPad, this.zeroPad = zeroPad);
	}

	public String getOutputDirectoryPath() {
		return outputDirectoryPath;
	}

	public void setOutputDirectoryPath(String outputDirectoryPath) {
		firePropertyChange("outputDirectoryPath", this.outputDirectoryPath, this.outputDirectoryPath = outputDirectoryPath);
	}
	
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		firePropertyChange("suffix", this.suffix, this.suffix = suffix);
	}
	
}
