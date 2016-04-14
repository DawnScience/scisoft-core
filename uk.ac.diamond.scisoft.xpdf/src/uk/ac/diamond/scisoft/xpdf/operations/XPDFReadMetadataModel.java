package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class XPDFReadMetadataModel extends AbstractOperationModel {

	@OperationModelField(label = "Read sample information")
	private boolean readSampleInfo = false;
	@OperationModelField(label = "Read container information", enableif = "false")
	private boolean readContainerInfo = false;
	@OperationModelField(label = "Read container data", enableif = "false")
	private boolean readContainerData = false;
	@OperationModelField(label = "Read beam information")
	private boolean readBeamInfo = false;
	@OperationModelField(label = "Read beam data", enableif = "false")
	private boolean readBeamData = false;
	@OperationModelField(label = "Read detector information")
	private boolean readDetectorInfo = false;
	@OperationModelField(label = "Read detector calibration")
	private boolean readDetectorCal = true;
	@OperationModelField(label = "Read mask")
	private boolean readMask = true;
	
	public boolean isReadSampleInfo() {
		return readSampleInfo;
	}
	public void setReadSampleInfo(boolean readSampleInfo) {
		firePropertyChange("readSampleInfo", readSampleInfo, this.readSampleInfo = readSampleInfo);
	}
	public boolean isReadContainerInfo() {
		return readContainerInfo;
	}
	public void setReadContainerInfo(boolean readContainerInfo) {
		firePropertyChange("readContainerInfo", readContainerInfo, this.readContainerInfo = readContainerInfo);
	}
	public boolean isReadContainerData() {
		return readContainerData;
	}
	public void setReadContainerData(boolean readContainerData) {
		firePropertyChange("readContainerData", readContainerData, this.readContainerData = readContainerData);
	}
	public boolean isReadBeamInfo() {
		return readBeamInfo;
	}
	public void setReadBeamInfo(boolean readBeamInfo) {
		firePropertyChange("readBeamInfo", readBeamInfo, this.readBeamInfo = readBeamInfo);
	}
	public boolean isReadBeamData() {
		return readBeamData;
	}
	public void setReadBeamData(boolean readBeamData) {
		firePropertyChange("readBeamData", readBeamData, this.readBeamData = readBeamData);
	}
	public boolean isReadDetectorInfo() {
		return readDetectorInfo;
	}
	public void setReadDetectorInfo(boolean readDetectorInfo) {
		firePropertyChange("readDetectorInfo", readDetectorInfo, this.readDetectorInfo = readDetectorInfo);
	}
	public boolean isReadDetectorCal() {
		return readDetectorCal;
	}
	public void setReadDetectorCal(boolean readDetectorCal) {
		firePropertyChange("readDetectorCal", readDetectorCal, this.readDetectorCal = readDetectorCal);
	}
	public boolean isReadMask() {
		return readMask;
	}
	public void setReadMask(boolean readMask) {
		firePropertyChange("readMask", readMask, this.readMask = readMask);
	}
	
	public boolean isReadAnyXPDFMetadata() {
		return readSampleInfo || readBeamData || readBeamInfo || readContainerData || readContainerInfo || readDetectorInfo;
	}
	
}
