package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class ThresholdMaskModel extends AbstractOperationModel {

	@OperationModelField(min=0, max=65000, hint="The upper intensity, above which the mask will mask out the value.\n\nIf you delete the value no upper intensity will be used for the mask.")
	private Double upper;
	
	@OperationModelField(min=0, max=65000, hint="The lower intensity, below which the mask will mask out the value.\n\nIf you delete the value no lower intensity will be used for the mask.")
	private Double lower;

	public ThresholdMaskModel() {
		this.upper = null;
		this.lower = null;
	}
	public ThresholdMaskModel(double upper, double lower) {
		this();
		this.upper = upper;
		this.lower = lower;
	}

	public Double getUpper() {
		return upper;
	}

	public void setUpper(Double upper) {
		this.upper = upper;
	}

	public Double getLower() {
		return lower;
	}

	public void setLower(Double lower) {
		this.lower = lower;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lower == null) ? 0 : lower.hashCode());
		result = prime * result + ((upper == null) ? 0 : upper.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThresholdMaskModel other = (ThresholdMaskModel) obj;
		if (lower == null) {
			if (other.lower != null)
				return false;
		} else if (!lower.equals(other.lower))
			return false;
		if (upper == null) {
			if (other.upper != null)
				return false;
		} else if (!upper.equals(other.upper))
			return false;
		return true;
	}

}
