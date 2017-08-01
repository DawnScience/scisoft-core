package org.dawnsci.surfacescatter;

import java.util.function.Function;

import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;

public class TrackingMethodology {

	public enum TrackerType1  implements Function<ITrackerFactoryObject, TrackerHolder>{
		TLD("TLD", TrackerType.TLD, 0, (ITrackerFactoryObject itfo) -> TrackerProducer.trackerProducer(itfo.getInput(), itfo.getLocation(), itfo.getTt())),
//		CIRCULANT,
//		SPARSEFLOW,
//		MEANSHIFTCOMANICIU2003,
//		INTERPOLATION,
		SPLINE_INTERPOLATION("Spline Interpolation", null, 1, null),
		USE_SET_POSITIONS("Use Set Positions", null, 2, null);
	
		
		private String trackerName;
		private TrackerType tt;
		private int trackerNo;
		private final Function<ITrackerFactoryObject, TrackerHolder> iT;
		
		TrackerType1(String tN, 
					 TrackerType ttIn, 
					 int trackNo, 
					 Function<ITrackerFactoryObject, TrackerHolder> itthis){
			
			trackerName = tN;
			tt = ttIn;
			trackerNo = trackNo;
			iT = itthis;
			
		}
		
		@Override
		public TrackerHolder apply(ITrackerFactoryObject t) {
			
			return iT.apply(t);
		}

		
		public String getTrackerName(){
			return trackerName;
		}
		
		public TrackerType getTrackerType(){
			return tt;
		}
		
		public int getTrackerNo(){
			return trackerNo;
		}

	}
	
	public static String toString(TrackerType1 tt){
		
		switch(tt){
			case TLD:
				return "TLD";
//			case CIRCULANT:
//				return "Circulant";
//			case SPARSEFLOW:
//				return "Sparse Flow";
//			case MEANSHIFTCOMANICIU2003:
//				return "Mean Shift";
//			case INTERPOLATION:
//				return "Interpolation";
			case SPLINE_INTERPOLATION:
				return "Spline Interpolation";
			case USE_SET_POSITIONS:
				return "Use Set Positions";
		}
		return null;
	}
	
	public static TrackerType1 toTracker1(String in){
			
		if (in.equals("TLD")){
			return TrackerType1.TLD;
		}
//		else if (in.equals("Circulant")){
//			return TrackerType1.CIRCULANT;
//		}
//		else if (in.equals("Sparse Flow")){
//			return TrackerType1.SPARSEFLOW;
//		}
//		else if (in.equals("MEANSHIFTCOMANICIU2003")){
//			return TrackerType1.MEANSHIFTCOMANICIU2003;
//		}
//		else if (in.equals("Interpolation")){
//			return TrackerType1.INTERPOLATION;
//		}
		else if (in.equals("Spline Interpolation")){
			return TrackerType1.SPLINE_INTERPOLATION;
		}
		else if (in.equals("Use Set Positions")){
			return  TrackerType1.USE_SET_POSITIONS;
		}
		
		return null;
	}

	
	public static TrackerType1 intToTracker1(int in){
		
		if (in == 0){
			return TrackerType1.TLD;
		}
//		else if (in == 1){
//			return TrackerType1.CIRCULANT;
//		}
//		else if (in == 2){
//			return TrackerType1.SPARSEFLOW;
//		}
//		else if (in == 3){
//			return TrackerType1.MEANSHIFTCOMANICIU2003;
//		}
//		else if (in == 4){
//			return TrackerType1.INTERPOLATION;
//		}
		else if (in == 1){
			return TrackerType1.SPLINE_INTERPOLATION;
		}
		else if (in == 2){
			return TrackerType1.USE_SET_POSITIONS;
		}
		return null;
	}

	public static TrackerType toTT (TrackerType1 tt){
		
		switch(tt){
			case TLD:
				return TrackerType.TLD;
//			case CIRCULANT:
//				return TrackerType.CIRCULANT;
//			case SPARSEFLOW:
//				return TrackerType.SPARSEFLOW;
//			case MEANSHIFTCOMANICIU2003:
//				return TrackerType.MEANSHIFTCOMANICIU2003;
//			case INTERPOLATION:
//				return null;
			case SPLINE_INTERPOLATION:
				return null;
			case USE_SET_POSITIONS:
				return null;
			}
		
		return null;
	}

}
