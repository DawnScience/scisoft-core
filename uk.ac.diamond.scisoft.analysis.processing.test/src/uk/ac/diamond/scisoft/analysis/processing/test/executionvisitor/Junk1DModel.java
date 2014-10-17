package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

public class Junk1DModel extends AbstractOperationModel {

	private int xDim = 20;
	
	public int getxDim() {
		return xDim;
	}
	
	public void setxDim(int xDim) {
		firePropertyChange("xDim", this.xDim, this.xDim = xDim);
	}
	
	
}
