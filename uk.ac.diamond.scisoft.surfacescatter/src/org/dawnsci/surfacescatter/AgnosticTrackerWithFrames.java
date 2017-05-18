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
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.swt.widgets.Composite;



public class AgnosticTrackerWithFrames {

	private IImageTracker tracker = null;
	private IImageTracker tracker1 = null;
	private double[] location;
	private double[] initialLocation;
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
								  int selection,
								  IPlottingSystem<Composite> pS) {
	
		this.sm = sm;
		len = model.getLenPt()[0];
		pt = model.getLenPt()[1];
		 
		
		if(model.getTrackerType() != TrackingMethodology.TrackerType1.INTERPOLATION 
				&& model.getTrackerType() != TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION){
			
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
		
		
		else{
			///////start the interpolation tracker
			
			try{
				
				len = new int[] {(int) Math.round(sm.getInterpolatedLenPts().get(selection)[0][0]),(int) Math.round(sm.getInterpolatedLenPts().get(selection)[0][1])};
				pt = new int[] {(int) Math.round(sm.getInterpolatedLenPts().get(selection)[1][0]),(int) Math.round(sm.getInterpolatedLenPts().get(selection)[1][1])};
				
				double[] localLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
						(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
						(double) (pt[1] + len[1]) };
				
				sm.addLocationList(selection, localLocation);
				dm.addLocationList(model.getDatImages().getShape()[0], k, localLocation);
			}
			catch(Exception r){
				
			}
			
			
		}
	}

	public void resetTracker() {
		tracker = null;
		sm.setTracker(null);
		sm.setInitialTracker(null);
	}
	
	//////////////////////////////The following is only for use with seed locations//////////
	
	
	public void TwoDTracking1(DirectoryModel drm,								
							  int trackingMarker, 
							  int k,
							  double[] seedLocation,
							  int selection) {


		len = drm.getInitialLenPt()[0];
		pt = drm.getInitialLenPt()[1];
		
		FrameModel fm = drm.getFms().get(selection);
		IDataset input = DatasetFactory.createFromObject(0);
		try {
			input = fm.getRawImageData().getSlice(new SliceND (fm.getRawImageData().getShape()));
		} catch (DatasetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		if(fm.getTrackingMethodology() != TrackingMethodology.TrackerType1.INTERPOLATION && 
				fm.getTrackingMethodology() != TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION){
			
			
			if (trackingMarker == 0) {
				if (drm.getInputForEachDat()[fm.getDatNo()] == null) {
					
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					
					initialLocation = seedLocation;
					
					int[][] newLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(initialLocation);
						
					drm.getLenPtForEachDat()[fm.getDatNo()] = newLenPt;
					
					try {
						tracker.initialize(input, initialLocation, TrackingMethodology.toTT(fm.getTrackingMethodology()));
						tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(fm.getTrackingMethodology()));
					} catch (Exception e) {
	
					}
					
					drm.setTrackerCoordinates(initialLocation);
					
					drm.getInputForEachDat()[fm.getDatNo()] = input;
					
					
					location = initialLocation;
					drm.setTracker(tracker);
					drm.setInitialTracker(tracker1);
					drm.addTrackerLocationList(selection, location);
				}
	
				else {
	
					try {
	
						if(drm.getTracker() != null){
							tracker = drm.getTracker();
							location = tracker.track(input);
						}
						
						if (location != null) {
							drm.setTrackerCoordinates(location);
							sm.addTrackerLocationList(selection, location);
							sm.setTracker(tracker);
						
						}
	
						else{
							tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
							tracker.initialize(drm.getInputForEachDat()[fm.getDatNo()],
											   drm.getTrackerCoordinates(), 
											   TrackingMethodology.toTT(fm.getTrackingMethodology()));
							location = tracker.track(input);
							if (location != null) {
								drm.setTrackerCoordinates(location);
								drm.addTrackerLocationList(selection, location);
								drm.setTracker(tracker);
							}
						}
						
						if (location == null){
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
																						  drm.getSortedX(), 
																						  sm.getInitialLenPt()[0],
																						  selection);
						}
						

					
						int[][] newLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
						
//						model.setLenPt(newLenPt);
							
						drm.getLenPtForEachDat()[fm.getDatNo()] = newLenPt;
						
						drm.getInputForEachDat()[fm.getDatNo()] = input;
	
					} catch (Exception e) {
						debug(
								"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
					}
	
				}
			}
	
			else if (trackingMarker == 1) {
				if (drm.getInputForEachDat()[fm.getDatNo()] == null) {
//					len = model.getLenPt()[0];
//					pt = model.getLenPt()[1];
//	
//					dm.setInitialDataset(input);
					
					drm.getInputForEachDat()[fm.getDatNo()] = input;
//					dm.setInitialLenPt(new int[][] {len, pt });
	
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();
	
					initialLocation = seedLocation;
					int[][] newLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(initialLocation);
					
					drm.getLenPtForEachDat()[fm.getDatNo()] = newLenPt;
					drm.setTrackerCoordinates(initialLocation);
							
					try {
						tracker.initialize(input, initialLocation, TrackingMethodology.toTT(fm.getTrackingMethodology()));
						tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(fm.getTrackingMethodology()));
					} catch (Exception e) {
						debug("Failed to initialise tracker in TwoDTracking1");
					}
					
//					model.setInput(input);
					location = initialLocation;
					drm.addTrackerLocationList(selection, location);
					drm.setTracker(tracker);
					drm.setInitialTracker(tracker1);
					
				}
	
				else {
	
					try {
	
						if(drm.getTracker() != null){
							tracker = drm.getTracker();
							location = tracker.track(input);
						}
						if (location != null) {
							drm.setTrackerCoordinates(location);
							drm.addTrackerLocationList(selection, location);
							drm.setTracker(tracker);
						
						}
	
						else{
							tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
							tracker.initialize(drm.getInputForEachDat()[fm.getDatNo()],
									   drm.getTrackerCoordinates(), 
									   TrackingMethodology.toTT(fm.getTrackingMethodology()));
					
							location = tracker.track(input);
							if (location != null) {
								drm.setTrackerCoordinates(location);
								drm.addTrackerLocationList(selection, location);
								drm.setTracker(tracker);
							}
						}
						
						if (location == null){
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																						  sm.getSortedX(), 
																						  sm.getInitialLenPt()[0],
																						  selection);
						}
						
						if (location != null) {
							drm.setTrackerCoordinates(location);
							
							int[][] newLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
							
							drm.getLenPtForEachDat()[fm.getDatNo()] = newLenPt;
							
							drm.getInputForEachDat()[fm.getDatNo()] = input;
							drm.addTrackerLocationList(selection, location);
						}
	
						sm.setTracker(tracker);
						
					} catch (Exception e) {
						debug(
								"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
					}
	
				}
			}
	
			else if (trackingMarker == 2) {
				if (drm.getInputForEachDat()[fm.getDatNo()]==null) {
					
					len =drm.getLenPtForEachDat()[fm.getDatNo()][0];
					pt =drm.getLenPtForEachDat()[fm.getDatNo()][1];
					
					initialLocation = seedLocation;
					
					if (sm.getInitialTracker() == null){	
						tracker =null;
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						
						try {
							
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
									  drm.getSortedX(), 
									  drm.getInitialLenPt()[0],
									  selection);
							
							debug("TwoDTrackign3 interpolated location for trackingMarker = 2 ; location[0]:  " +  location[0] + "    TwoDTrackign3 location[1]:  " +  location[1] );
							
							
							tracker.initialize(drm.getInputForEachDat()[fm.getDatNo()], 
									 		   location, 
									 		   TrackingMethodology.toTT(fm.getTrackingMethodology()));
							
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
						drm.setTrackerCoordinates(location);
						sm.addTrackerLocationList(selection, location);
					}
					
					
					sm.setTracker(tracker);
					drm.getInputForEachDat()[fm.getDatNo()] = input;
	//				sm.addLocationList(k, location);
				}
	
				else {
					try {
						
						if(sm.getTracker() != null){
							tracker = sm.getTracker();
							location = tracker.track(input);
						}
						if (location != null) {
							drm.setTrackerCoordinates(location);
							drm.addTrackerLocationList(selection, location);
							drm.setTracker(tracker);
						
						}
	
						else{
							tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
							tracker.initialize(drm.getInputForEachDat()[fm.getDatNo()], 
							 		   		   location, 
							 		           TrackingMethodology.toTT(fm.getTrackingMethodology()));
							location = tracker.track(input);
							if (location != null) {
								drm.setTrackerCoordinates(location);
								drm.addTrackerLocationList(selection, location);
								drm.setTracker(tracker);
							}
						}
						
						if (location == null){
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(sm.getTrackerLocationList(), 
																						  sm.getSortedX(), 
																						  sm.getInitialLenPt()[0],
																						  selection);
						}
						if (location != null) {
							
							drm.setTrackerCoordinates(location);
							int[][] newLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
							
							drm.getLenPtForEachDat()[fm.getDatNo()] = newLenPt;
							
							drm.getInputForEachDat()[fm.getDatNo()] = input;
							drm.addTrackerLocationList(selection, location);
							
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
				location = drm.getTrackerCoordinates();
			}
			
			if (isTheTrackingMarkerNot3){
				
				int[] localPt = new int[] {(int) (location[0]), (int) (location[1])};
				int[] localLen = new int[] {(int) ((int)location[2] - (int)location[0]), (int) ((int)location[5] -(int)location[1])};
				localLen = sm.getInitialLenPt()[0];
				int[] localLocation = new int[] { localPt[0], localPt[1], (localPt[0] + localLen[0]),
						(localPt[1]), localPt[0], localPt[1] + localLen[1], (localPt[0] + localLen[0]),
						(localPt[1] + localLen[1]) };
				
				 
				sm.addLocationList(selection, localLocation);
				drm.addLocationList(fm.getDatNo(), 
									drm.getNoOfImagesInDatFile(fm.getDatNo()), 
									k, 
									localLocation);
			
				debug("location[0]:  " + location[0] + "  location[1]:  " + location[1] + "  selection" + selection);
			}
		}
			
		else{
				///////start the interpolation tracker
				
			try{
					
				len = new int[] {(int) Math.round(drm.getInterpolatedLenPts().get(selection)[0][0]),(int) Math.round(drm.getInterpolatedLenPts().get(selection)[0][1])};
				pt = new int[] {(int) Math.round(drm.getInterpolatedLenPts().get(selection)[1][0]),(int) Math.round(drm.getInterpolatedLenPts().get(selection)[1][1])};
					
				double[] localLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
							(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
							(double) (pt[1] + len[1]) };
					
//				drm.addLocationList(selection, localLocation);
				
				drm.addLocationList(fm.getDatNo(), 
						drm.getNoOfImagesInDatFile(fm.getDatNo()), 
						selection, 
						localLocation);
				
//				dm.addLocationList(model.getDatImages().getShape()[0], k, localLocation);
			}
			catch(Exception r){
					
			}
				
				
		}
		
	}
	
//	????????????//////////////////////////////////////////////////////////////
	
	
	public void TwoDTracking3(DirectoryModel drm, 
							  int trackingMarker, 
							  int k,
							  int selection) {

		len = drm.getInitialLenPt()[0];
		pt = drm.getInitialLenPt()[1];
		
		FrameModel fm = drm.getFms().get(selection);
		IDataset input = DatasetFactory.createFromObject(0);
		try {
			input = fm.getRawImageData().getSlice(new SliceND (fm.getRawImageData().getShape()));
		} catch (DatasetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		initialLocation = new double[] { (double) pt[1], (double) pt[0], (double) (pt[1] + len[1]), (double) (pt[0]),
//		(double) pt[1], (double) pt[0] + len[0], (double) (pt[1] + len[1]), (double) (pt[0] + len[0]) };
		
		if(fm.getTrackingMethodology() != TrackingMethodology.TrackerType1.INTERPOLATION && fm.getTrackingMethodology() != TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION){
			
			
			
			isTheTrackingMarkerNot3 = true;
			
			if (trackingMarker == 3){
				drm.getInputForEachDat()[fm.getDatNo()] = null;
				trackingMarker = 0;
				isTheTrackingMarkerNot3 = false;
			}
			
			
			if (trackingMarker == 0) {
				if (drm.getInputForEachDat()[fm.getDatNo()] == null) {
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
			
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();
							
					initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
							(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
							(double) (pt[1] + len[1]) };
					
					
					debug("TwoDTrackign3 initialLocation[0]:  " +  initialLocation[0] + "TwoDTrackign3 initialLocation[1]:  " +  initialLocation[1] );
					
					
					try {
						tracker.initialize(input, initialLocation, TrackingMethodology.toTT(fm.getTrackingMethodology()));
						tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(fm.getTrackingMethodology()));
					} catch (Exception e) {
						debug("Failed to initialise tracker in TwoDTracking3");
					}
					
					drm.setTrackerCoordinates(
							new double[] { initialLocation[0], initialLocation[1], initialLocation[2], initialLocation[3],
									initialLocation[4], initialLocation[5], initialLocation[6], initialLocation[7] });
					drm.getInputForEachDat()[fm.getDatNo()] = (input);
					location = initialLocation;
					
					if (isTheTrackingMarkerNot3){
						drm.setTracker(tracker);
					}
					drm.setInitialTracker(tracker1);
					drm.addTrackerLocationList(fm.getImageNumber(),
											   location);
				}
			
				else {
			
					try {
			
						if(drm.getTracker() != null){
							tracker = drm.getTracker();
							location = tracker.track(input);
						}
						if (location != null) {
							drm.setTrackerCoordinates(location);
							drm.addTrackerLocationList(fm.getImageNumber(),
									   location);
							if (isTheTrackingMarkerNot3){
								drm.setTracker(tracker);
							}
						
						}
	
						else{
							tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
							tracker.initialize(drm.getInputForEachDat()[fm.getDatNo()], drm.getTrackerCoordinates(), TrackingMethodology.toTT(fm.getTrackingMethodology()));
							location = tracker.track(input);
							if (location != null) {
								drm.setTrackerCoordinates(location);
								drm.addTrackerLocationList(fm.getImageNumber(),
										   location);
								if (isTheTrackingMarkerNot3){
									drm.setTracker(tracker);
								}
							}
						}
						
						if (location == null){
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
																						  drm.getSortedX(), 
																						  drm.getInitialLenPt()[0],
																						  selection);
						}
				
						int[] len1 = drm.getLenPtForEachDat()[fm.getDatNo()][0];
				////////////////////AAAAAAAAARRRRRRRRGH??????????????
						int[] newPt = new int[] { (int) location[0], (int) location[1] };
						int[][] newLenPt = new int[2][];
						newLenPt[0] = len1;
						newLenPt[1] = newPt;
						drm.getLenPtForEachDat()[fm.getDatNo()] = (newLenPt);
						drm.getInputForEachDat()[fm.getDatNo()] = (input);
						//System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
					} catch (Exception e) {
						debug(
								"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
					}
				}
			}
			
			else if (trackingMarker == 1) {
				if (drm.getInputForEachDat()[fm.getDatNo()] == null) {
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
			
					drm.getInitialDatasetForEachDat()[fm.getDatNo()] = (input);
					drm.setInitialLenPt(new int[][] {len, pt });
			
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					
					initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
							(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
							(double) (pt[1] + len[1]) };
					
					
					debug("TwoDTrackign3 initialLocation[0]:  " +  initialLocation[0] + "    TwoDTrackign3 initialLocation[1]:  " +  initialLocation[1] );
					
					try {
						tracker.initialize(input, initialLocation, TrackingMethodology.toTT(fm.getTrackingMethodology()));
						tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(fm.getTrackingMethodology()));
					} catch (Exception e) {
						debug("Failed to iniitiliase tracker in TwoDTracking3");
					}
					drm.setTrackerCoordinates(
							new double[] { initialLocation[0], initialLocation[1], initialLocation[2], initialLocation[3],
									initialLocation[4], initialLocation[5], initialLocation[6], initialLocation[7] });
					drm.getInputForEachDat()[fm.getDatNo()] = (input);
					location = initialLocation;
					if (isTheTrackingMarkerNot3){
						drm.setTracker(tracker);
					}
					drm.addTrackerLocationList(fm.getImageNumber(),
							   location);
					drm.setInitialTracker(tracker1);
				}
			
			else {
			
				try {
					if(sm.getTracker() != null){
						tracker = drm.getTracker();
						location = tracker.track(input);
						
						if (location != null) {
							drm.setTrackerCoordinates(location);
							drm.addTrackerLocationList(fm.getImageNumber(),
									   location);
							if (isTheTrackingMarkerNot3){
								drm.setTracker(tracker);
							}
						}
					}
	
					else{
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();					
						tracker.initialize(drm.getInputForEachDat()[fm.getDatNo()], drm.getTrackerCoordinates(), TrackingMethodology.toTT(fm.getTrackingMethodology()));
						location = tracker.track(input);
						if (location != null) {
							drm.setTrackerCoordinates(location);
							drm.addTrackerLocationList(fm.getImageNumber(),
									   location);
							if (isTheTrackingMarkerNot3){
								drm.setTracker(tracker);
							}
						}
					}
					
					if (location == null){
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
																					  drm.getSortedX(), 
																					  drm.getInitialLenPt()[0],
																					  selection);
					}
					
					
					int[] len1 = drm.getLenPtForEachDat()[fm.getDatNo()][0];
					
					int[] newPt = new int[] { (int) location[0], (int) location[1] };
					int[][] newLenPt = new int[2][];
					newLenPt[0] = len1;
					newLenPt[1] = newPt;
					drm.getLenPtForEachDat()[fm.getDatNo()] = (newLenPt);
					drm.getInputForEachDat()[fm.getDatNo()]=(input);
					//System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
				
				} catch (Exception e) {
				debug(
						"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}
				
				}
			}
			
			else if (trackingMarker == 2) {
				
				if (drm.getInputForEachDat()[fm.getDatNo()] == null) {
					
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
								
					
					initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
							(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
							(double) (pt[1] + len[1]) };
					
					debug("trackign mrker 2; TwoDTrackign3 initialLocation[0]:  " +  initialLocation[0] + "    TwoDTrackign3 initialLocation[1]:  " +  initialLocation[1] );
					
					location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
							  drm.getSortedX(), 
							  drm.getInitialLenPt()[0],
							  selection);
					
					debug("TwoDTrackign3 interpolated location for trackingMarker = 2 ; location[0]:  " +  location[0] + "    TwoDTrackign3 location[1]:  " +  location[1] );
					
					
					if (drm.getInitialTracker() != null){
						tracker = drm.getInitialTracker();
						
					}
					else{
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						try {
							
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
									  drm.getSortedX(), 
									  drm.getInitialLenPt()[0],
									  selection);
							
							debug("TwoDTrackign3 interpolated location for trackingMarker = 2 ; location[0]:  " +  location[0] + "    TwoDTrackign3 location[1]:  " +  location[1] );
							
							
							tracker.initialize(drm.getInitialDatasetForEachDat()[fm.getDatNo()], location, TrackingMethodology.toTT(fm.getTrackingMethodology()));
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
						drm.setTrackerCoordinates(location);
						drm.addTrackerLocationList(fm.getImageNumber(),
								   location);
					}
					
		
					drm.getInputForEachDat()[fm.getDatNo()] = (input);
					if (isTheTrackingMarkerNot3){
						drm.setTracker(tracker);
					}
				
				}
				
				else {
				
					try {
					
						if(drm.getTracker() != null){
							tracker = drm.getTracker();
							location = tracker.track(input);
						}
					
					if (location != null) {
						drm.setTrackerCoordinates(location);
						drm.addTrackerLocationList(fm.getImageNumber(),
								   location);
						if (isTheTrackingMarkerNot3){
							drm.setTracker(tracker);
						}
					
					}
	
					else{
						debug("Generated new tracker in TwoDTracker1");
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						tracker.initialize(drm.getInputForEachDat()[fm.getDatNo()], drm.getTrackerCoordinates(), TrackingMethodology.toTT(fm.getTrackingMethodology()));
						location = tracker.track(input);
						if (location != null) {
							drm.setTrackerCoordinates(location);
							drm.addTrackerLocationList(fm.getImageNumber(),
									   location);
							if (isTheTrackingMarkerNot3){
								drm.setTracker(tracker);
							}
						}
					}
					
					if (location == null){
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
																					  drm.getSortedX(), 
																					  drm.getInitialLenPt()[0],
																					  selection);
					}
					
					
					int[] len1 = drm.getLenPtForEachDat()[0][0];
					///////////////////////////////????????????????
					int[] newPt = new int[] { (int) location[0], (int) location[1] };
					int[][] newLenPt = new int[2][];
					newLenPt[0] = len1;
					newLenPt[1] = newPt;
					drm.getLenPtForEachDat()[fm.getDatNo()] = (newLenPt);
					drm.getInputForEachDat()[fm.getDatNo()]=(input);
					//System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
					
					} catch (Exception e) {
					debug(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
					}
					
				}
			}
			
			if (location == null) {
				location = drm.getTrackerCoordinates();
			}
			
			if (isTheTrackingMarkerNot3){
			
				int[] localPt = new int[] {(int) (location[0]), (int) (location[1])};
				int[] localLen = new int[] {(int) ((int)location[2] - (int)location[0]), (int) ((int)location[5] -(int)location[1])};
				localLen = drm.getInitialLenPt()[0];
				int[] localLocation = new int[] { localPt[0], localPt[1], (localPt[0] + localLen[0]),
						(localPt[1]), localPt[0], localPt[1] + localLen[1], (localPt[0] + localLen[0]),
						(localPt[1] + localLen[1]) };
				
				 
//				sm.addLocationList(selection, localLocation);
//				dm.addLocationList(model.getDatImages().getShape()[0], k, localLocation);
				
				drm.addLocationList(fm.getDatNo(),
						drm.getNoOfImagesInDatFile(fm.getDatNo()), 
						k, 
						localLocation);
				
				
				debug("location[0]:  " + location[0] + "  location[1]:  " + location[1] + "  selection" + selection);
			}
		}
		
		else{
			///////start the interpolation tracker
			
			try{
				
				len = new int[] {(int) Math.round(drm.getInterpolatedLenPts().get(selection)[0][0]),(int) Math.round(drm.getInterpolatedLenPts().get(selection)[0][1])};
				pt = new int[] {(int) Math.round(drm.getInterpolatedLenPts().get(selection)[1][0]),(int) Math.round(drm.getInterpolatedLenPts().get(selection)[1][1])};
				
				double[] localLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
						(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
						(double) (pt[1] + len[1]) };
				
				drm.addLocationList(fm.getDatNo(),
						drm.getNoOfImagesInDatFile(fm.getDatNo()), 
						k, 
						localLocation);
				
				drm.addLocationList(fm.getDatNo(),
									drm.getNoOfImagesInDatFile(fm.getDatNo()), 
									k, 
									localLocation);
			}
			catch(Exception r){
				
			}
		}	
	}
		

	
	private void debug (String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}
}