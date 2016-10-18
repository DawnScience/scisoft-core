package org.dawnsci.surfacescatter;

import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;

public class TrackingMethodology {

	public enum TrackerType1 {
		TLD,
		CIRCULANT,
		SPARSEFLOW,
		MEANSHIFTCOMANICIU2003;
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
			return TrackerType1.SPARSEFLOW;
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
		}
		return null;
	}

}
