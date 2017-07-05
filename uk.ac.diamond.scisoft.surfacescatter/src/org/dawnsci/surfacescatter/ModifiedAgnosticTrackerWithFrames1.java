package org.dawnsci.surfacescatter;

public class ModifiedAgnosticTrackerWithFrames1 extends AbstractTracker{

	
	public ModifiedAgnosticTrackerWithFrames1(DirectoryModel drm,								
			  								  int trackingMarker, 
			  								  int k,
			  								  double[] seedLocation,
			  								  int selection) {


		super.init(drm,								
			  trackingMarker, 
			  k,
			  seedLocation,
			  selection);
		
		runTrack(trackingMarker);
	}
	
	public ModifiedAgnosticTrackerWithFrames1(DirectoryModel drm,								
											  int trackingMarker, 
											  int k,
											  int selection) {


		super.init(drm,								
				   trackingMarker, 
				   k,
			 	   selection);
		
		runTrack(trackingMarker);
	}
	
	private void runTrack(int trackingMarker){
		
		if(super.getFrame().getTrackingMethodology() != TrackingMethodology.TrackerType1.INTERPOLATION 
				&& super.getFrame().getTrackingMethodology() != TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION
				&& super.getFrame().getTrackingMethodology() != TrackingMethodology.TrackerType1.USE_SET_POSITIONS){
			
			
			switch(trackingMarker){
				
				case 0 :	
					initialiseRestart();	
					break;
							
				case 1:
					initialiseRestart();
					break;
						
				case 2:
					restartInOppositeDirection();
					break;
				default:
						//blank, no trackingMarkers >3 should enter this class
			}
		
		}
			
		else if(super.getFrame().getTrackingMethodology() == TrackingMethodology.TrackerType1.INTERPOLATION 
					|| super.getFrame().getTrackingMethodology() == TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION){
			///////start the interpolation tracker
			
					interpolationRoutine();
					
		}
		
		if (super.isTheTrackingMarkerNot3()){
			setPostTrackParameters();
			
		}
		
		
	}
}
