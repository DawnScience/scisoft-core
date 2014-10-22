package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class SubtractBlankFrameModel extends AbstractOperationModel {
	
	@OperationModelField(min=0,max=1000000, hint="Position of first (or only) blank frame",label = "Set position of first blank:" )
	private int startFrame = 0;
	@OperationModelField(min=0,max=1000000, hint="Position of end blank frame, leave empty to use a single frame",label = "Set position of last blank:" )
	private Integer endFrame = null;
	
	
	public int getStartFrame() {
		return startFrame;
	}

	public void setStartFrame(int startFrame) {
		firePropertyChange("startFrame", this.startFrame, this.startFrame = startFrame);
	}

	public Integer getEndFrame() {
		return endFrame;
	}

	public void setEndFrame(Integer endFrame) {
		firePropertyChange("endFrame", this.endFrame, this.endFrame = endFrame);
	}

}
