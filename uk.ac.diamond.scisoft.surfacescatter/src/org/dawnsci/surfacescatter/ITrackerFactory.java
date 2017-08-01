package org.dawnsci.surfacescatter;

import java.util.function.Function;


public enum ITrackerFactory implements Function<ITrackerFactoryObject, TrackerHolder>{

	TLD((ITrackerFactoryObject itfo) -> TrackerProducer.trackerProducer(itfo.getInput(), itfo.getLocation(), itfo.getTt())),
	SPLINE_INTERPOLATION((ITrackerFactoryObject itfo) -> null),
	SET_POSITIONS((ITrackerFactoryObject itfo) -> null);
	
	private final Function<ITrackerFactoryObject, TrackerHolder> iT;
	
	private ITrackerFactory(Function<ITrackerFactoryObject, TrackerHolder> itthis){
		this.iT = itthis;
	}
	
	@Override
	public TrackerHolder apply(ITrackerFactoryObject t) {
		
		return iT.apply(t);
	}

	
	
}
