/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.IDataset;

public class ModifiedAgnosticTrackerWithFrames0 extends AbstractTracker {

//	private IImageTracker tracker = null;
	private int[] len;
	private int[] pt;

	
	public ModifiedAgnosticTrackerWithFrames0 (DirectoryModel drm,
							  int trackingMarker, 
							  int k,
							  int selection) {
	
		
		super.init(drm,
			  trackingMarker,
			  k,
			  selection);
		
		FrameModel frame = super.getFrame();

		if(//frame.getTrackingMethodology() != TrackingMethodology.TrackerType1.INTERPOLATION 
//				&& 
				frame.getTrackingMethodology() != TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION
				&& frame.getTrackingMethodology() != TrackingMethodology.TrackerType1.USE_SET_POSITIONS){		
			switch(trackingMarker){
				
				case 0:
					initialiseRestart();
					break;
	
				case 1:
					initialiseRestart();
					break;
				
			
	
				case 2:
					restartInOppositeDirection();
					break;
			}
			
			if (super.isTheTrackingMarkerNot3()){
				 super.setPostTrackParameters();
				
			}
			
		}
		
		
		else if (//frame.getTrackingMethodology() == TrackingMethodology.TrackerType1.INTERPOLATION 
				//|| 
				frame.getTrackingMethodology() == TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION){
			///////start the interpolation tracker
			
			super.interpolationRoutine();
			
		}
	}

}