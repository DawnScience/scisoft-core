package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;
import org.eclipse.january.dataset.IDataset;

public class ITrackerFactoryObject {


	private IDataset input;
	private double[] location;
	private TrackerType tt;
	
	public ITrackerFactoryObject(IDataset input, double[] location, TrackerType tt) {
		// 
		this.input = input;
		this.tt = tt;
		this.location = location;
	}
	
	public IDataset getInput() {
		return input;
	}
	public void setInput(IDataset input) {
		this.input = input;
	}
	public double[] getLocation() {
		return location;
	}
	public void setLocation(double[] location) {
		this.location = location;
	}
	public TrackerType getTt() {
		return tt;
	}
	public void setTt(TrackerType tt) {
		this.tt = tt;
	}
	
}
