package org.dawnsci.surfacescatter;

public class FrameSetupFromNexusTransferObject {

	public int[] boundaryBoxArray;
	public String[] fitPowersArray;
	public String[] trackingMethodArray;
	public String[] backgroundMethodArray;
	public double[][] roiLocationArray;
	
	
	
	public  FrameSetupFromNexusTransferObject (int[] boundaryBoxArray, 
			String[] fitPowersArray,
			String[] trackingMethodArray,
			String[] backgroundMethodArray,
			double[][] roiLocationArray) {
		
		this.boundaryBoxArray = boundaryBoxArray;
		this.fitPowersArray = fitPowersArray;
		this.trackingMethodArray = trackingMethodArray;
		this.backgroundMethodArray = backgroundMethodArray;
		this.roiLocationArray = roiLocationArray;
	}
			
	
	
	public int[] getBoundaryBoxArray() {
		return boundaryBoxArray;
	}
	public void setBoundaryBoxArray(int[] boundaryBoxArray) {
		this.boundaryBoxArray = boundaryBoxArray;
	}
	public String[] getFitPowersArray() {
		return fitPowersArray;
	}
	public void setFitPowersArray(String[] fitPowersArray) {
		this.fitPowersArray = fitPowersArray;
	}
	public String[] getTrackingMethodArray() {
		return trackingMethodArray;
	}
	public void setTrackingMethodArray(String[] trackingMethodArray) {
		this.trackingMethodArray = trackingMethodArray;
	}
	public String[] getBackgroundMethodArray() {
		return backgroundMethodArray;
	}
	public void setBackgroundMethodArray(String[] backgroundMethodArray) {
		this.backgroundMethodArray = backgroundMethodArray;
	}
	public double[][] getRoiLocationArray() {
		return roiLocationArray;
	}
	public void setRoiLocationArray(double[][] roiLocationArray) {
		this.roiLocationArray = roiLocationArray;
	}

	
	
	
}
