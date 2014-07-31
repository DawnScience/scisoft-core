package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;
import uk.ac.diamond.scisoft.analysis.roi.IROI;

public class IntegrationModel extends AbstractOperationModel {

	private IROI region;

	public IROI getRegion() {
		return region;
	}

	public void setRegion(IROI region) {
		this.region = region;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((region == null) ? 0 : region.hashCode());
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
		IntegrationModel other = (IntegrationModel) obj;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		return true;
	}
}
