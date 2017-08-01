package org.dawnsci.surfacescatter;

import org.dawnsci.surfacescatter.TrackingMethodology.TrackerType1;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;
import org.eclipse.january.dataset.IDataset;

public interface ITracker {

	public IImageTracker initialise(IDataset input, double[] location, TrackerType tt);
	
	public double[] track(IDataset input);

	public IImageTracker getTracker();
	
	
	
}
