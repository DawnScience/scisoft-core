package org.dawnsci.surfacescatter;

public class SimpleTracker extends AbstractTracker {

	private double[] seed;

	public void init(DirectoryModel drm, int trackingMarker) {
		super.init(drm, trackingMarker);
		
		//TODO init stuff specific to this Tracker
	}
	
	public void setSeed(double[] seed) {
		this.seed = seed;
	}

	@Override
	public void track() {
		// TODO Auto-generated method stub
	}

}
