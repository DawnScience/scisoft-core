/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.dawnsci.boofcv.BoofCVImageTrackerServiceCreator;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial2D;

/**
 * Cuts out the region of interest and fits it with a 2D polynomial background.
 */

public class AgnosticTrackerHandler {

	private IImageTracker tracker = null;
	private IImageTracker tracker1 = null;
	private Polynomial2D g2;
	private double[] location;
	private double[] initialLocation;
	private double[] trackerInput;
	private Dataset in1;
	private int[] len;
	private int[] pt;
	private SuperModel sm;
	private int DEBUG = 0;
	private boolean isTheTrackingMarkerNot3 = true;

	public void TwoDTracking0(IDataset input, 
								  ExampleModel model,
								  SuperModel sm, 
								  DataModel dm, 
								  int trackingMarker, 
								  int k,
								  int selection) {
	
		this.sm = sm;
		len = model.getLenPt()[0];
		pt = model.getLenPt()[1];
		 
		if (trackingMarker == 0) {
			if (model.getInput() == null) {
				len = model.getLenPt()[0];
				pt = model.getLenPt()[1];

//				in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);
				
				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
				tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();

				initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
						(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
						(double) (pt[1] + len[1]) };
				
				
				debug("TwoDTracking0 initialLocation[0]:  " +  initialLocation[0] + "TwoDTrackign3 initialLocation[1]:  " +  initialLocation[1] );
				
				
				
				
				try {
					tracker.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
					tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
				} catch (Exception e) {

				}

				model.setInput(input);
				location = initialLocation;
				
				sm.setTracker(tracker);
				sm.setInitialTracker(tracker1);
				sm.addTrackerLocationList(selection, location);
//				sm.addLocationList(k, location);
			}

			else {

				try {

//					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
//					tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
					if(sm.getTracker() != null){
						tracker = sm.getTracker();
						location = tracker.track(input);
					}					
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						sm.setTracker(tracker);
					
					}

					else{
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
						location = tracker.track(input);
						if (location != null) {
							model.setTrackerCoordinates(location);
							sm.addTrackerLocationList(selection, location);
							sm.setTracker(tracker);
						}
					}
					
					if (location == null){
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																					  sm.getSortedX(), 
																					  sm.getInitialLenPt()[0],
																					  selection);
					}
					
					
					
					
					
//					sm.addLocationList(k, location);
					
					int[] len1 = model.getLenPt()[0];

					int[] newPt = new int[] { (int) location[0], (int) location[1] };
					int[][] newLenPt = new int[2][];
					newLenPt[0] = len1;
					newLenPt[1] = newPt;
					model.setLenPt(newLenPt);
					model.setInput(input);
//					sm.addLocationList(k, location);
//					System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
				} catch (Exception e) {
					debug(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}

			}
		}

		else if (trackingMarker == 1) {
			if (model.getInput() == null) {
				len = model.getLenPt()[0];
				pt = model.getLenPt()[1];

				dm.setInitialDataset(input);
				dm.setInitialLenPt(new int[][] {len, pt });
				
			
//				in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);
				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
				tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();

				initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
						(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
						(double) (pt[1] + len[1]) };
				try {
					tracker.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
					tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
				} catch (Exception e) {

				}
				model.setTrackerCoordinates(
						new double[] { initialLocation[0], initialLocation[1], initialLocation[2], initialLocation[3],
								initialLocation[4], initialLocation[5], initialLocation[6], initialLocation[7] });
				model.setInput(input);
				location = initialLocation;
				
				sm.addTrackerLocationList(selection, location);
				sm.setTracker(tracker);
				sm.setInitialTracker(tracker1);

//				sm.addLocationList(k, location);
			}

			else {

				try {

//					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
//					tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
					
					if(sm.getTracker() != null){
						tracker = sm.getTracker();
						location = tracker.track(input);
					}
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						sm.setTracker(tracker);
					
					}

					else{
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
						location = tracker.track(input);
						if (location != null) {
							model.setTrackerCoordinates(location);
							sm.addTrackerLocationList(selection, location);
							sm.setTracker(tracker);
						}
					}
					
					if (location == null){
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																					  sm.getSortedX(), 
																					  sm.getInitialLenPt()[0],
																					  selection);
					}
					
					
//					sm.setTracker(tracker);
					
					int[] len1 = model.getLenPt()[0];

					int[] newPt = new int[] { (int) location[0], (int) location[1] };
					int[][] newLenPt = new int[2][];
					newLenPt[0] = len1;
					newLenPt[1] = newPt;
					model.setLenPt(newLenPt);
					model.setInput(input);
//					sm.addLocationList(k, location);
//					System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
				} catch (Exception e) {
					debug(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}

			}
		}

		else if (trackingMarker == 2) {
			if (model.getInput() == null) {
				len = dm.getInitialLenPt()[0];
				pt = dm.getInitialLenPt()[1];
				
				initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
						(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
						(double) (pt[1] + len[1]) };
				
				if (sm.getInitialTracker() == null){	
					tracker =null;
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					
					try {
						
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
								  sm.getSortedX(), 
								  sm.getInitialLenPt()[0],
								  selection);
						
						debug("TwoDTrackign3 interpolated location for trackingMarker = 2 ; location[0]:  " +  location[0] + "    TwoDTrackign3 location[1]:  " +  location[1] );
						
						
						tracker.initialize(dm.getInitialDataset(), location, TrackingMethodology.toTT(model.getTrackerType()));
		
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sm.addTrackerLocationList(k, initialLocation);
				}
				
				else{
					tracker = sm.getInitialTracker();
					
				}
				
				try {
					location = tracker.track(input);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
				
				if (location != null) {
					model.setTrackerCoordinates(location);
					sm.addTrackerLocationList(selection, location);
					sm.setTracker(tracker);
				
				}

				else{
					try{
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
						location = tracker.track(input);
					}
					catch(Exception e){
						debug("Error in tracker initialisation; line ~278");
					}
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						sm.setTracker(tracker);
						}
						
				}
				
				if (location == null){
					location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																				  sm.getSortedX(), 
																				  sm.getInitialLenPt()[0],
																				  selection);
				}
				

				model.setInput(input);

				sm.setTracker(tracker);

				
			}

			else {

				try {

//					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
//					tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
					
					if(sm.getTracker() != null){
						tracker = sm.getTracker();
						location = tracker.track(input);
					}
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						sm.setTracker(tracker);
					
					}

					else{
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
						location = tracker.track(input);
						if (location != null) {
							model.setTrackerCoordinates(location);
							sm.addTrackerLocationList(selection, location);
							sm.setTracker(tracker);
						}
					}
					
					if (location == null){
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																					  sm.getSortedX(), 
																					  sm.getInitialLenPt()[0],
																					  selection);
					}
					
					int[] len1 = model.getLenPt()[0];

					int[] newPt = new int[] { (int) location[0], (int) location[1] };
					int[][] newLenPt = new int[2][];
					newLenPt[0] = len1;
					newLenPt[1] = newPt;
					model.setLenPt(newLenPt);
					model.setInput(input);
//					sm.addLocationList(k, location);
//					System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
				} catch (Exception e) {
					debug(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}

			}
		}

		if (location == null) {
			location = model.getTrackerCoordinates();
		}
		
		if (isTheTrackingMarkerNot3){
			
			int[] localPt = new int[] {(int) (location[0]), (int) (location[1])};
			int[] localLen = new int[] {(int) ((int)location[2] - (int)location[0]), (int) ((int)location[5] -(int)location[1])};
			localLen = sm.getInitialLenPt()[0];
			int[] localLocation = new int[] { localPt[0], localPt[1], (localPt[0] + localLen[0]),
					(localPt[1]), localPt[0], localPt[1] + localLen[1], (localPt[0] + localLen[0]),
					(localPt[1] + localLen[1]) };
			
			 
			sm.addLocationList(selection, localLocation);
			dm.addLocationList(model.getDatImages().getShape()[0], k, localLocation);
			debug("location[0]:  " + location[0] + "  location[1]:  " + location[1] + "  selection" + selection);
		}
	}

	public void resetTracker() {
		tracker = null;
		sm.setTracker(null);
		sm.setInitialTracker(null);
	}
	
	//////////////////////////////The following is only for use with seed locations//////////
	
	
	public void TwoDTracking1(IDataset input, 
								  ExampleModel model,
								  SuperModel sm, 
								  DataModel dm, 
								  int trackingMarker, 
								  int k,
								  double[] seedLocation,
								  int selection) {

		this.sm = sm;
		
		if (trackingMarker == 0) {
			if (model.getInput() == null) {
				
				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
				tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();
				
				initialLocation = seedLocation;
				
				int[] newPt = new int[] { (int) initialLocation[0], (int) initialLocation[1] };
				int[][] newLenPt = new int[2][];
				newLenPt[0] = model.getLenPt()[0];
				newLenPt[1] = newPt;
				model.setLenPt(newLenPt);
								
				try {
					tracker.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
					tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
				} catch (Exception e) {

				}
				
				model.setTrackerCoordinates(initialLocation);
				
				model.setInput(input);
				location = initialLocation;
				sm.setTracker(tracker);
				sm.setInitialTracker(tracker1);
				sm.addTrackerLocationList(selection, location);
			}

			else {

				try {

					if(sm.getTracker() != null){
						tracker = sm.getTracker();
						location = tracker.track(input);
					}
					
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						sm.setTracker(tracker);
					
					}

					else{
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
						location = tracker.track(input);
						if (location != null) {
							model.setTrackerCoordinates(location);
							sm.addTrackerLocationList(selection, location);
							sm.setTracker(tracker);
						}
					}
					
					if (location == null){
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																					  sm.getSortedX(), 
																					  sm.getInitialLenPt()[0],
																					  selection);
					}
					
					
				
					int[] len1 = model.getLenPt()[0];

					int[] newPt = new int[] { (int) location[0], (int) location[1] };
					int[][] newLenPt = new int[2][];
					newLenPt[0] = len1;
					newLenPt[1] = newPt;
					model.setLenPt(newLenPt);
					model.setInput(input);

				} catch (Exception e) {
					debug(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}

			}
		}

		else if (trackingMarker == 1) {
			if (model.getInput() == null) {
				len = model.getLenPt()[0];
				pt = model.getLenPt()[1];

				dm.setInitialDataset(input);
				dm.setInitialLenPt(new int[][] {len, pt });

				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
				tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();

				initialLocation = seedLocation;
//			
				int[] newPt = new int[] { (int) initialLocation[0], (int) initialLocation[1] };
				int[][] newLenPt = new int[2][];
				newLenPt[0] = model.getLenPt()[0];
				newLenPt[1] = newPt;
				model.setLenPt(newLenPt);
				
				
				try {
					tracker.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
					tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
				} catch (Exception e) {
					debug("Failed to initialise tracker in TwoDTracking1");
				}
				model.setTrackerCoordinates(initialLocation);
				model.setInput(input);
				location = initialLocation;
				sm.addTrackerLocationList(selection, location);
				sm.setTracker(tracker);
				sm.setInitialTracker(tracker1);
				
			}

			else {

				try {

					if(sm.getTracker() != null){
						tracker = sm.getTracker();
						location = tracker.track(input);
					}
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						sm.setTracker(tracker);
					
					}

					else{
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
						location = tracker.track(input);
						if (location != null) {
							model.setTrackerCoordinates(location);
							sm.addTrackerLocationList(selection, location);
							sm.setTracker(tracker);
						}
					}
					
					if (location == null){
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																					  sm.getSortedX(), 
																					  sm.getInitialLenPt()[0],
																					  selection);
					}
					
					if (location != null) {
						model.setTrackerCoordinates(location);
						int[] len1 = model.getLenPt()[0];
						int[] newPt = new int[] { (int) location[0], (int) location[1] };
						int[][] newLenPt = new int[2][];
						newLenPt[0] = len1;
						newLenPt[1] = newPt;
						model.setLenPt(newLenPt);
						model.setInput(input);
						sm.addTrackerLocationList(selection, location);
					}

					sm.setTracker(tracker);
					
				} catch (Exception e) {
					debug(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}

			}
		}

		else if (trackingMarker == 2) {
			if (model.getInput() == null) {
				len = dm.getInitialLenPt()[0];
				pt = dm.getInitialLenPt()[1];
				
				initialLocation = seedLocation;
				
				if (sm.getInitialTracker() == null){	
					tracker =null;
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					
					try {
						
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
								  sm.getSortedX(), 
								  sm.getInitialLenPt()[0],
								  selection);
						
						debug("TwoDTrackign3 interpolated location for trackingMarker = 2 ; location[0]:  " +  location[0] + "    TwoDTrackign3 location[1]:  " +  location[1] );
						
						
						tracker.initialize(dm.getInitialDataset(), location, TrackingMethodology.toTT(model.getTrackerType()));
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				else{
					tracker = sm.getInitialTracker();
					
				}
				
				try {
					location = tracker.track(input);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (location != null) {
					model.setTrackerCoordinates(location);
					sm.addTrackerLocationList(selection, location);
				}
				
				
				sm.setTracker(tracker);
				model.setInput(input);
//				sm.addLocationList(k, location);
			}

			else {
				try {
					
					if(sm.getTracker() != null){
						tracker = sm.getTracker();
						location = tracker.track(input);
					}
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						sm.setTracker(tracker);
					
					}

					else{
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
						location = tracker.track(input);
						if (location != null) {
							model.setTrackerCoordinates(location);
							sm.addTrackerLocationList(selection, location);
							sm.setTracker(tracker);
						}
					}
					
					if (location == null){
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																					  sm.getSortedX(), 
																					  sm.getInitialLenPt()[0],
																					  selection);
					}
					if (location != null) {
						model.setTrackerCoordinates(location);
						int[] len1 = model.getLenPt()[0];
						int[] newPt = new int[] { (int) location[0], (int) location[1] };
						int[][] newLenPt = new int[2][];
						newLenPt[0] = len1;
						newLenPt[1] = newPt;
						model.setLenPt(newLenPt);
						model.setInput(input);
						sm.addTrackerLocationList(selection, location);
//						sm.addLocationList(k, location);
					}
					else{
//						sm.addLocationList(k, model.getTrackerCoordinates());
					}
					
//					System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
				} catch (Exception e) {
					debug(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}

				sm.setTracker(tracker);
				
			}
		}

		if (location == null) {
			location = model.getTrackerCoordinates();
		}
		
		if (isTheTrackingMarkerNot3){
			
			int[] localPt = new int[] {(int) (location[0]), (int) (location[1])};
			int[] localLen = new int[] {(int) ((int)location[2] - (int)location[0]), (int) ((int)location[5] -(int)location[1])};
			localLen = sm.getInitialLenPt()[0];
			int[] localLocation = new int[] { localPt[0], localPt[1], (localPt[0] + localLen[0]),
					(localPt[1]), localPt[0], localPt[1] + localLen[1], (localPt[0] + localLen[0]),
					(localPt[1] + localLen[1]) };
			
			 
			sm.addLocationList(selection, localLocation);
			dm.addLocationList(model.getDatImages().getShape()[0], k, localLocation);
		
			debug("location[0]:  " + location[0] + "  location[1]:  " + location[1] + "  selection" + selection);
		}
		
	}
	
	public void TwoDTracking3(SuperModel sm, 
							      IDataset input, 
							      ExampleModel model, 
							      DataModel dm, 
							      int trackingMarker, 
							      int k,
							      int selection) {

		len = sm.getInitialLenPt()[0];
		pt = sm.getInitialLenPt()[1];
		
//		initialLocation = new double[] { (double) pt[1], (double) pt[0], (double) (pt[1] + len[1]), (double) (pt[0]),
//		(double) pt[1], (double) pt[0] + len[0], (double) (pt[1] + len[1]), (double) (pt[0] + len[0]) };
		
		isTheTrackingMarkerNot3 = true;
		
		if (trackingMarker == 3){
			model.setInput(null);
			trackingMarker = 0;
			isTheTrackingMarkerNot3 = false;
		}
		
		
		if (trackingMarker == 0) {
			if (model.getInput() == null) {
				len = sm.getInitialLenPt()[0];
				pt = sm.getInitialLenPt()[1];
		
				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
				tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						
				initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
						(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
						(double) (pt[1] + len[1]) };
				
				
				debug("TwoDTrackign3 initialLocation[0]:  " +  initialLocation[0] + "TwoDTrackign3 initialLocation[1]:  " +  initialLocation[1] );
				
				
				try {
					tracker.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
					tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
				} catch (Exception e) {
					debug("Failed to initialise tracker in TwoDTracking3");
				}
				
				model.setTrackerCoordinates(
						new double[] { initialLocation[0], initialLocation[1], initialLocation[2], initialLocation[3],
								initialLocation[4], initialLocation[5], initialLocation[6], initialLocation[7] });
				model.setInput(input);
				location = initialLocation;
				
				if (isTheTrackingMarkerNot3){
					sm.setTracker(tracker);
				}
				sm.setInitialTracker(tracker1);
				sm.addTrackerLocationList(selection, location);
			}
		
			else {
		
				try {
		
					if(sm.getTracker() != null){
						tracker = sm.getTracker();
						location = tracker.track(input);
					}
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						if (isTheTrackingMarkerNot3){
							sm.setTracker(tracker);
						}
					
					}

					else{
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
						location = tracker.track(input);
						if (location != null) {
							model.setTrackerCoordinates(location);
							sm.addTrackerLocationList(selection, location);
							if (isTheTrackingMarkerNot3){
								sm.setTracker(tracker);
							}
						}
					}
					
					if (location == null){
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																					  sm.getSortedX(), 
																					  sm.getInitialLenPt()[0],
																					  selection);
					}
			
					int[] len1 = model.getLenPt()[0];
			////////////////////AAAAAAAAARRRRRRRRGH??????????????
					int[] newPt = new int[] { (int) location[0], (int) location[1] };
					int[][] newLenPt = new int[2][];
					newLenPt[0] = len1;
					newLenPt[1] = newPt;
					model.setLenPt(newLenPt);
					model.setInput(input);
					//System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
				} catch (Exception e) {
					debug(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}
			}
		}
		
		else if (trackingMarker == 1) {
			if (model.getInput() == null) {
				len = sm.getInitialLenPt()[0];
				pt = sm.getInitialLenPt()[1];
		
				dm.setInitialDataset(input);
				dm.setInitialLenPt(new int[][] {len, pt });
		
				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
				tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();
				
				initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
						(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
						(double) (pt[1] + len[1]) };
				
				
				debug("TwoDTrackign3 initialLocation[0]:  " +  initialLocation[0] + "    TwoDTrackign3 initialLocation[1]:  " +  initialLocation[1] );
				
				try {
					tracker.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
					tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
				} catch (Exception e) {
					debug("Failed to iniitiliase tracker in TwoDTracking3");
				}
				model.setTrackerCoordinates(
						new double[] { initialLocation[0], initialLocation[1], initialLocation[2], initialLocation[3],
								initialLocation[4], initialLocation[5], initialLocation[6], initialLocation[7] });
				model.setInput(input);
				location = initialLocation;
				if (isTheTrackingMarkerNot3){
					sm.setTracker(tracker);
				}
				sm.addTrackerLocationList(selection, location);
				sm.setInitialTracker(tracker1);
			}
		
		else {
		
			try {
				if(sm.getTracker() != null){
					tracker = sm.getTracker();
					location = tracker.track(input);
					
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						if (isTheTrackingMarkerNot3){
							sm.setTracker(tracker);
						}
					}
				}

				else{
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();					
					tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
					location = tracker.track(input);
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						if (isTheTrackingMarkerNot3){
							sm.setTracker(tracker);
						}
					}
				}
				
				if (location == null){
					location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																				  sm.getSortedX(), 
																				  sm.getInitialLenPt()[0],
																				  selection);
				}
				
				int[] len1 = model.getLenPt()[0];
				
				int[] newPt = new int[] { (int) location[0], (int) location[1] };
				int[][] newLenPt = new int[2][];
				newLenPt[0] = len1;
				newLenPt[1] = newPt;
				model.setLenPt(newLenPt);
				model.setInput(input);
				//System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
			
			} catch (Exception e) {
			debug(
					"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
			}
			
			}
		}
		
		else if (trackingMarker == 2) {
			
			if (model.getInput() == null) {
				
				len = dm.getInitialLenPt()[0];
				pt = dm.getInitialLenPt()[1];
							
				
				initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
						(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
						(double) (pt[1] + len[1]) };
				
				debug("trackign mrker 2; TwoDTrackign3 initialLocation[0]:  " +  initialLocation[0] + "    TwoDTrackign3 initialLocation[1]:  " +  initialLocation[1] );
				
				location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
						  sm.getSortedX(), 
						  sm.getInitialLenPt()[0],
						  selection);
				
				debug("TwoDTrackign3 interpolated location for trackingMarker = 2 ; location[0]:  " +  location[0] + "    TwoDTrackign3 location[1]:  " +  location[1] );
				
				
				if (sm.getInitialTracker() != null){
					tracker = sm.getInitialTracker();
					
				}
				else{
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					try {
						
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
								  sm.getSortedX(), 
								  sm.getInitialLenPt()[0],
								  selection);
						
						debug("TwoDTrackign3 interpolated location for trackingMarker = 2 ; location[0]:  " +  location[0] + "    TwoDTrackign3 location[1]:  " +  location[1] );
						
						
						tracker.initialize(dm.getInitialDataset(), location, TrackingMethodology.toTT(model.getTrackerType()));
					} catch (Exception e) {
						debug("Failed to initialise tracker in TwoDTracking3 ");
					}
					
				}
				
				
				
				try {
					
					
					//tracker.initialize(dm.getInitialDataset(), initialLocation, TrackerType.TLD);
					location = tracker.track(input);
					
					
				
				} catch (Exception e) {
				e.printStackTrace();
				}
				
				if (location != null) {
					model.setTrackerCoordinates(location);
					sm.addTrackerLocationList(selection, location);
				}
				
	
				model.setInput(input);
				if (isTheTrackingMarkerNot3){
					sm.setTracker(tracker);
				}
			
			}
			
			else {
			
				try {
				
					if(sm.getTracker() != null){
						tracker = sm.getTracker();
						location = tracker.track(input);
					}
				
				if (location != null) {
					model.setTrackerCoordinates(location);
					sm.addTrackerLocationList(selection, location);
					if (isTheTrackingMarkerNot3){
						sm.setTracker(tracker);
					}
				
				}

				else{
					debug("Generated new tracker in TwoDTracker1");
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
					location = tracker.track(input);
					if (location != null) {
						model.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
						if (isTheTrackingMarkerNot3){
							sm.setTracker(tracker);
						}
					}
				}
				
				if (location == null){
					location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																				  sm.getSortedX(), 
																				  sm.getInitialLenPt()[0],
																				  selection);
				}
				int[] len1 = model.getLenPt()[0];
				///////////////////////////////????????????????
				int[] newPt = new int[] { (int) location[0], (int) location[1] };
				int[][] newLenPt = new int[2][];
				newLenPt[0] = len1;
				newLenPt[1] = newPt;
				model.setLenPt(newLenPt);
				model.setInput(input);
				//System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
				
				} catch (Exception e) {
				debug(
						"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}
				
			}
		}
		
		if (location == null) {
			location = model.getTrackerCoordinates();
		}
		
		if (isTheTrackingMarkerNot3){
		
			int[] localPt = new int[] {(int) (location[0]), (int) (location[1])};
			int[] localLen = new int[] {(int) ((int)location[2] - (int)location[0]), (int) ((int)location[5] -(int)location[1])};
			localLen = sm.getInitialLenPt()[0];
			int[] localLocation = new int[] { localPt[0], localPt[1], (localPt[0] + localLen[0]),
					(localPt[1]), localPt[0], localPt[1] + localLen[1], (localPt[0] + localLen[0]),
					(localPt[1] + localLen[1]) };
			
			 
			sm.addLocationList(selection, localLocation);
			dm.addLocationList(model.getDatImages().getShape()[0], k, localLocation);
			debug("location[0]:  " + location[0] + "  location[1]:  " + location[1] + "  selection" + selection);
		}
	}
		
	private double[] convertLenPttoTrackerInput(double[] len, double[] pt){
		
		return trackerInput = new double[] { (double) pt[1], (double) pt[0], (double) (pt[1] + len[1]),
				(double) (pt[0]), (double) pt[1], (double) pt[0] + len[0], (double) (pt[1] + len[1]),
				(double) (pt[0] + len[0]) };
	}
	
	private double[] convertLenPttoTrackerInput(double[][] lenpt){
		
		double[] len = lenpt[0];
		double[] pt = lenpt[1];
		
		return trackerInput = new double[] { (double) pt[1], (double) pt[0], (double) (pt[1] + len[1]),
				(double) (pt[0]), (double) pt[1], (double) pt[0] + len[0], (double) (pt[1] + len[1]),
				(double) (pt[0] + len[0]) };
	}
	
	
	private int[][] convertTrackerOutputToLenPt(int[] trackeroutput){
		
		int[] len = new int[] {(trackeroutput[7] - trackeroutput[1]),(trackeroutput[6] - trackeroutput[0])} ;
		int[] pt = new int[] {trackeroutput[1], trackeroutput[0]};
		
		return new int[][] {len,pt};
	}
	
	private int[] convertLenPttoTrackerInput(int[] len, int[] pt){
		
		return new int[] { (int) pt[1], (int) pt[0], (int) (pt[1] + len[1]),
				(int) (pt[0]), (int) pt[1], (int) pt[0] + len[0], (int) (pt[1] + len[1]),
				(int) (pt[0] + len[0]) };
	}
	
	private double[] convertLenPttoTrackerInput1(int[] len, int[] pt){
		
		return new double[] { (double) pt[1], (double) pt[0], (double) (pt[1] + len[1]),
				(double) (pt[0]), (double) pt[1], (double) pt[0] + len[0], (double) (pt[1] + len[1]),
				(double) (pt[0] + len[0]) };
	}
	
	
	private int[] convertLenPttoTrackerInput(int[][] lenpt){
		
		int[] len = lenpt[0];
		int[] pt = lenpt[1];
		
		return new int[] {  pt[1], pt[0], (pt[1] + len[1]),
				(pt[0]), pt[1], pt[0] + len[0], (pt[1] + len[1]),
				(int) (pt[0] + len[0]) };
	}
	
	
	private double[][] convertTrackerOutputToLenPt(double[] trackeroutput){
		
		double[] len = new double[] {(trackeroutput[7] - trackeroutput[1]),(trackeroutput[6] - trackeroutput[0])} ;
		double[] pt = new double[] {trackeroutput[1], trackeroutput[0]};
		
		return new double[][] {len,pt};
	}
	
	
	private void debug (String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}
}