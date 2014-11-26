package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class Crop1dModel extends AbstractOperationModel {
	
	@OperationModelField(hint="Set beginning of range to crop data to", label = "Beginning")
	private Double start = null;
	
	@OperationModelField(hint="Set end of range to crop data to", label = "End")
	private Double end = null;
	
	public Double getStart() {
		return start;
	}

	public void setStart(Double start) {
		firePropertyChange("start", this.start, this.start = start);	
	}

	public Double getEnd() {
		return end;
	}

	public void setEnd(Double end) {
		firePropertyChange("end", this.end, this.end = end);
	}



}
