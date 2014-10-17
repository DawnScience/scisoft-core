package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;


public class Junk2Dto2Dmodel extends Junk1DModel {

	
	private int yDim = 30;
	
	
	public int getyDim() {
		return yDim;
	}

	
	public void setyDim(int yDim) {
		firePropertyChange("yDim", this.yDim, this.yDim = yDim);
	}
	
}
