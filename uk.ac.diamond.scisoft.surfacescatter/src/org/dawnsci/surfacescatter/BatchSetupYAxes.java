package org.dawnsci.surfacescatter;

public class BatchSetupYAxes {

	private AxisEnums.yAxes y;
	private boolean use = false;
	
	public BatchSetupYAxes (AxisEnums.yAxes y, boolean use ){
	
		this.y=y;
		this.use= use; 
	}
	
	public AxisEnums.yAxes getY() {
		return y;
	}

	public void setyS(AxisEnums.yAxes y) {
		this.y = y;
	}

	public boolean isUse() {
		return use;
	}

	public void setUse(boolean use) {
		this.use = use;
	}
	
}
