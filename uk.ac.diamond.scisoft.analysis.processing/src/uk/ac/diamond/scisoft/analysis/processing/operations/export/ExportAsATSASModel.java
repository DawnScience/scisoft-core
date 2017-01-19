package uk.ac.diamond.scisoft.analysis.processing.operations.export;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class ExportAsATSASModel extends AbstractOperationModel {

	// All ATSAS files end in .dat so we shall force this
	private final String extension = "dat";

	public String getExtension() {
		return extension;
	}
	
	@OperationModelField(hint="Path to output directory", file = FileType.EXISTING_FOLDER, label = "Output Directory")
	private String outputDirectoryPath = "";
	@OperationModelField(label = "Include explicit location", hint = "Include the explicit (slice) location of the data in the full dataset as part of the filename")
	private boolean includeSliceName = false;
	@OperationModelField(label = "Pad with zeros", hint = "Leave blank for no padding", min = 1)
	private Integer zeroPad = 5;
	@OperationModelField(label = "Suffix", hint = "Custom suffix to be appended to the file name, leave blank for no suffix")
	private String suffix = "";
	@OperationModelField(label = "In separate folder", hint = "Put text files in new folder named after input data file")
	private boolean makeFolder = false;

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
	
	public boolean isMakeFolder() {
		return makeFolder;
	}

	public void setMakeFolder(boolean makeFolder) {
		firePropertyChange("makeFolder", this.makeFolder, this.makeFolder = makeFolder);
	}
}
