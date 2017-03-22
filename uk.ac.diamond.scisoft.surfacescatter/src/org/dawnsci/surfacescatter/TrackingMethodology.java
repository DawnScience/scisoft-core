package org.dawnsci.surfacescatter;

import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;

public class TrackingMethodology {

	public enum TrackerType1 {
		TLD,
		CIRCULANT,
		SPARSEFLOW,
		MEANSHIFTCOMANICIU2003,
		INTERPOLATION;
	}

	public static String toString(TrackerType1 tt){
		
		switch(tt){
			case TLD:
				return "TLD";
			case CIRCULANT:
				return "Circulant";
			case SPARSEFLOW:
				return "Sparse Flow";
			case MEANSHIFTCOMANICIU2003:
				return "Mean Shift";
			case INTERPOLATION:
				return "Interpolation";
		}
		return null;
	}
	
	public static TrackerType1 toTracker1(String in){
			
		if (in.equals("TLD")){
			return TrackerType1.TLD;
		}
		else if (in.equals("Circulant")){
			return TrackerType1.CIRCULANT;
		}
		else if (in.equals("Sparse Flow")){
			return TrackerType1.SPARSEFLOW;
		}
		else if (in.equals("MEANSHIFTCOMANICIU2003")){
			return TrackerType1.MEANSHIFTCOMANICIU2003;
		}
		else if (in.equals("Interpolation")){
			return TrackerType1.INTERPOLATION;
		}
		return null;
	}

	
	public static TrackerType1 intToTracker1(int in){
		
		if (in == 0){
			return TrackerType1.TLD;
		}
		else if (in == 1){
			return TrackerType1.CIRCULANT;
		}
		else if (in == 2){
			return TrackerType1.SPARSEFLOW;
		}
		else if (in == 3){
			return TrackerType1.MEANSHIFTCOMANICIU2003;
		}
		else if (in == 4){
			return TrackerType1.INTERPOLATION;
		}
		return null;
	}

	public static TrackerType toTT (TrackerType1 tt){
		
		switch(tt){
			case TLD:
				return TrackerType.TLD;
			case CIRCULANT:
				return TrackerType.CIRCULANT;
			case SPARSEFLOW:
				return TrackerType.SPARSEFLOW;
			case MEANSHIFTCOMANICIU2003:
				return TrackerType.MEANSHIFTCOMANICIU2003;
			case INTERPOLATION:
				return null;
		}
		return null;
	}

}
