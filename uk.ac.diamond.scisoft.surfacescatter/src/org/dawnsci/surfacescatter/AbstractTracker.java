package org.dawnsci.surfacescatter;

public abstract class AbstractTracker {
	
	private DirectoryModel drm;
	
	public void init(DirectoryModel drm, int trackingMarker) {
		this.drm = drm;
	}
	
	public void reset() {
		//TODO implement
	}
	
	public abstract void track();
	
	protected int[] getSomething() {
		return drm.getBackgroundLenPt()[0];
	}
 
}
