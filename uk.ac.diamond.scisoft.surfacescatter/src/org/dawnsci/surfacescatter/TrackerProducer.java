package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;
import org.eclipse.january.dataset.IDataset;

public class TrackerProducer {

	
	public static TrackerHolder trackerProducer(IDataset input, double[] location, TrackerType tt){
	
		ITracker iT = new TLDTracker();
		
		switch(tt){
			case TLD:
				iT.initialise(input, location, tt);
				break;
			default:
				//defensive
			
		}
		
		return new TrackerHolder(iT, iT.getTracker());
	}
	
}
