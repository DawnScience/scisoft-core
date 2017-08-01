package org.dawnsci.surfacescatter;

import org.dawnsci.boofcv.BoofCVImageTrackerServiceCreator;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;
import org.eclipse.january.dataset.IDataset;

public class TLDTracker implements ITracker{

	private IImageTracker tracker;
	
	@Override
	public IImageTracker initialise(IDataset input, double[] location, TrackerType tt) {
		
		
		tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
		try {
			tracker.initialize(input, location, tt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tracker;
	}
	
	
	

	@Override
	public double[] track(IDataset input) {
		
		double[] r = new double[8];
		
		try {
			r = tracker.track(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	
	@Override
	public IImageTracker getTracker() {
		return tracker;
	}

	

}
