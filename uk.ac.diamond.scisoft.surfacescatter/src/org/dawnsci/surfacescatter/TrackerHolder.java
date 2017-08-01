package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.image.IImageTracker;

public class TrackerHolder {

	private ITracker trackerClass;
	private IImageTracker  tracker;
	
	
	TrackerHolder(ITracker iT, IImageTracker iiT){
		
		trackerClass = iT;
		tracker = iiT;
		
	}
	
	
	public ITracker getTrackerClass() {
		return trackerClass;
	}
	public void setTrackerClass(ITracker trackerClass) {
		this.trackerClass = trackerClass;
	}
	public IImageTracker getTracker() {
		return tracker;
	}
	public void setTracker(IImageTracker tracker) {
		this.tracker = tracker;
	}

	
	
}
