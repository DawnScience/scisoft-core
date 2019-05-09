package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class InterpolateXRMCFromGDModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter the file name for the calibration of the target detector", file = FileType.EXISTING_FILE, label = "Target detector calibration file")
	private String detectorFileName = null;
	
	@OperationModelField(hint="Enter the dataset name for the calibration of the target detector", dataset = "detectorFileName", label = "Target detector dataset")
	private String detectorDataset = null;

	@OperationModelField(hint="Set a fixed detector roll value to use in the interpolation", label = "Fixed detector roll", unit = "°")
	private Double fixedRoll = null;
	
	public String getDetectorFileName() {
		return this.detectorFileName;
	}
	public String getDetectorDataset() {
		return this.detectorDataset;
	}
	public Double getFixedRoll() {
		return this.fixedRoll;
	}
	
	public void setDetectorFileName(String fileName) {
		firePropertyChange("detectorFileName", this.detectorFileName, this.detectorFileName = fileName);
	}
	public void setDetectorDataset(String dataset) {
		firePropertyChange("detectorDataset", this.detectorDataset, this.detectorDataset = dataset);
	}
	public void setFixedRoll(Double roll) {
		firePropertyChange("fixedRoll", this.fixedRoll, this.fixedRoll = roll);
	}
}
