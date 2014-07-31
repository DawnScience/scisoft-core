package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;

public class ThresholdMaskModel extends AbstractOperationModel {

	private double upper, lower;

	public ThresholdMaskModel() {
		
	}
	public ThresholdMaskModel(double upper, double lower) {
		this();
		this.upper = upper;
		this.lower = lower;
	}

	public double getUpper() {
		return upper;
	}

	public void setUpper(double upper) {
		this.upper = upper;
	}

	public double getLower() {
		return lower;
	}

	public void setLower(double lower) {
		this.lower = lower;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(lower);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(upper);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (Double.doubleToLongBits(lower) != Double
				.doubleToLongBits(other.lower))
			return false;
		if (Double.doubleToLongBits(upper) != Double
				.doubleToLongBits(other.upper))
			return false;
		return true;
	}
}
