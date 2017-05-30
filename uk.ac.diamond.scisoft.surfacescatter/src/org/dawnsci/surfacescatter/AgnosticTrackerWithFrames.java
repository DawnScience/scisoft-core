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
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.SliceND;

public class AgnosticTrackerWithFrames {

	private IImageTracker tracker = null;
	private IImageTracker tracker1 = null;
	private double[] location;
	private double[] initialLocation;
	private int[] len;
	private int[] pt;
	private DirectoryModel drm;
	private int DEBUG = 0;
	private boolean isTheTrackingMarkerNot3 = true;
	
	public void TwoDTracking0(DirectoryModel drm,
							  int trackingMarker, 
							  int k,
							  int selection) {
	
		
		len = drm.getInitialLenPt()[0];
		pt = drm.getInitialLenPt()[1];
		
		FrameModel frame = drm.getFms().get(selection);
		IDataset input = DatasetFactory.createFromObject(0);
		
		try {
			input = frame.getRawImageData().getSlice(new SliceND (frame.getRawImageData().getShape())).squeeze();
		} catch (DatasetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		FrameModel fm = 
		
		if(frame.getTrackingMethodology() != TrackingMethodology.TrackerType1.INTERPOLATION 
				&& frame.getTrackingMethodology() != TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION){
			
			if (trackingMarker == 0) {
				if (drm.getInputForEachDat()[frame.getDatNo()] == null) {
					len = drm.getLenPtForEachDat()[frame.getDatNo()][0];
					pt = drm.getLenPtForEachDat()[frame.getDatNo()][1];
	
	//				in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);
					
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();
	
					initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
							(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
							(double) (pt[1] + len[1]) };
					
					
					debug("TwoDTracking0 initialLocation[0]:  " +  initialLocation[0] + "TwoDTrackign3 initialLocation[1]:  " +  initialLocation[1] );
					
					
					
					
					try {
						tracker.initialize(input, initialLocation, TrackingMethodology.toTT(frame.getTrackingMethodology()));
						tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(frame.getTrackingMethodology()));
					} catch (Exception e) {
	
					}
	
					drm.getInputForEachDat()[frame.getDatNo()] =input;
				
					
					location = initialLocation;
					
					drm.setTracker(tracker);
					drm.setInitialTracker(tracker1);
					drm.addTrackerLocationList(frame.getImageNumber(),
									   location);

				}
	
				else {
	
					try {
	
	//					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
	//					tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], model.getTrackerCoordinates(), TrackingMethodology.toTT(frame.getTrackingMethodology()));
						if(drm.getTracker() != null){
							tracker = drm.getTracker();
							location = tracker.track(input);
						}					
						if (location != null) {
							drm.setTrackerCoordinates(location);
							drm.addTrackerLocationList(frame.getImageNumber(),
									   location);
							drm.setTracker(tracker);
						
						}
	
						else{
							tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
							tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], drm.getTrackerCoordinates(), TrackingMethodology.toTT(frame.getTrackingMethodology()));
							location = tracker.track(input);
							if (location != null) {
								drm.setTrackerCoordinates(location);
								drm.addTrackerLocationList(frame.getImageNumber(),
									   location);
								drm.setTracker(tracker);
							}
						}
						
						if (location == null){
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
									  drm.getSortedX(), 
									  drm.getInitialLenPt()[0],
									  selection);
						}
						
						
						
						
						
	//					sm.addLocationList(k, location);
						
						int[] len1 =drm.getLenPtForEachDat()[frame.getDatNo()][0];
	
						int[] newPt = new int[] { (int) location[0], (int) location[1] };
						int[][] newLenPt = new int[2][];
						newLenPt[0] = len1;
						newLenPt[1] = newPt;
						drm.getLenPtForEachDat()[frame.getDatNo()]=(newLenPt);
						drm.getInputForEachDat()[frame.getDatNo()] =input;
	//					sm.addLocationList(k, location);
	//					System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
					} catch (Exception e) {
						debug(
								"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
					}
	
				}
			}
	
			else if (trackingMarker == 1) {
				if (drm.getInputForEachDat()[frame.getDatNo()] == null) {
					len = drm.getLenPtForEachDat()[frame.getDatNo()][0];
					pt = drm.getLenPtForEachDat()[frame.getDatNo()][1];
	
					drm.getInputForEachDat()[frame.getDatNo()]= (input);
					drm.setInitialLenPt(new int[][] {len, pt });
					
				
	//				in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);
					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					tracker1 = BoofCVImageTrackerServiceCreator.createImageTrackerService();
	
					initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
							(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
							(double) (pt[1] + len[1]) };
					try {
						tracker.initialize(input, initialLocation, TrackingMethodology.toTT(frame.getTrackingMethodology()));
						tracker1.initialize(input, initialLocation, TrackingMethodology.toTT(frame.getTrackingMethodology()));
					} catch (Exception e) {
	
					}
					
//					drm.setTrackerCoordinates(location);
//					model.setTrackerCoordinates(
//							new double[] { initialLocation[0], initialLocation[1], initialLocation[2], initialLocation[3],
//									initialLocation[4], initialLocation[5], initialLocation[6], initialLocation[7] });
					drm.getInputForEachDat()[frame.getDatNo()] =input;
					location = initialLocation;
					drm.setTrackerCoordinates(location);
					drm.addTrackerLocationList(frame.getImageNumber(),
									   location);
					drm.setTracker(tracker);
					drm.setInitialTracker(tracker1);
	
	//				sm.addLocationList(k, location);
				}
	
				else {
	
					try {
	
	//					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
	//					tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], model.getTrackerCoordinates(), TrackingMethodology.toTT(frame.getTrackingMethodology()));
						
						if(drm.getTracker() != null){
							tracker = drm.getTracker();
							location = tracker.track(input);
						}
						if (location != null) {
							drm.setTrackerCoordinates(location);
							drm.addTrackerLocationList(frame.getImageNumber(),
									   location);
							drm.setTracker(tracker);
						
						}
	
						else{
							tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
							tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()],drm.getTrackerCoordinates(), TrackingMethodology.toTT(frame.getTrackingMethodology()));
							location = tracker.track(input);
							if (location != null) {
								drm.setTrackerCoordinates(location);
								drm.addTrackerLocationList(frame.getImageNumber(),
									   location);
								drm.setTracker(tracker);
							}
						}
						
						if (location == null){
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
																						  drm.getSortedX(), 
																						  drm.getInitialLenPt()[0],
																						  selection);
						}
						
						
	//					drm.setTracker(tracker);
						
						int[] len1 = drm.getLenPtForEachDat()[frame.getDatNo()][0];
	
						int[] newPt = new int[] { (int) location[0], (int) location[1] };
						int[][] newLenPt = new int[2][];
						newLenPt[0] = len1;
						newLenPt[1] = newPt;
						drm.getLenPtForEachDat()[frame.getDatNo()]=(newLenPt);
						drm.getInputForEachDat()[frame.getDatNo()] =input;
	//					sm.addLocationList(k, location);
	//					System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
					} catch (Exception e) {
						debug(
								"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
					}
	
				}
			}
	
			else if (trackingMarker == 2) {
				if (drm.getInputForEachDat()[frame.getDatNo()] == null) {
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
					
					initialLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
							(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
							(double) (pt[1] + len[1]) };
					
					if (drm.getInitialTracker() == null){	
						tracker =null;
						tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
						
						try {
							
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
									  drm.getSortedX(), 
									  drm.getInitialLenPt()[0],
									  selection);
							
							debug("TwoDTrackign3 interpolated location for trackingMarker = 2 ; location[0]:  " +  location[0] + "    TwoDTrackign3 location[1]:  " +  location[1] );
							
							
							tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], location, TrackingMethodology.toTT(frame.getTrackingMethodology()));
			
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						drm.addTrackerLocationList(k, initialLocation);
					}
					
					else{
						tracker = drm.getInitialTracker();
						
					}
					
					try {
						location = tracker.track(input);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
					
					if (location != null) {
						drm.setTrackerCoordinates(location);
						drm.addTrackerLocationList(frame.getImageNumber(),
									   location);
						drm.setTracker(tracker);
					
					}
	
					else{
						try{
							tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
							tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], drm.getTrackerCoordinates(), TrackingMethodology.toTT(frame.getTrackingMethodology()));
							location = tracker.track(input);
						}
						catch(Exception e){
							debug("Error in tracker initialisation; line ~278");
						}
						if (location != null) {
							drm.setTrackerCoordinates(location);
							drm.addTrackerLocationList(frame.getImageNumber(),
									   location);
							drm.setTracker(tracker);
							}
							
					}
					
					if (location == null){
						location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
								  drm.getSortedX(), 
								  drm.getInitialLenPt()[0],
								  selection);
					}
					
	
					drm.getInputForEachDat()[frame.getDatNo()] =input;
					drm.getInputForEachDat()[frame.getDatNo()] =input;
					drm.setTracker(tracker);
	
					
				}
	
				else {
	
					try {
	
	//					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
	//					tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], model.getTrackerCoordinates(), TrackingMethodology.toTT(frame.getTrackingMethodology()));
						
						if(drm.getTracker() != null){
							tracker = drm.getTracker();
							location = tracker.track(input);
						}
						if (location != null) {
							drm.setTrackerCoordinates(location);
							drm.addTrackerLocationList(frame.getImageNumber(),
									   location);
							drm.setTracker(tracker);
						
						}
	
						else{
							tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
							tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], drm.getTrackerCoordinates(), TrackingMethodology.toTT(frame.getTrackingMethodology()));
							location = tracker.track(input);
							if (location != null) {
								drm.setTrackerCoordinates(location);
								drm.addTrackerLocationList(frame.getImageNumber(),
									   location);
								drm.setTracker(tracker);
							}
						}
						
						if (location == null){
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
									  drm.getSortedX(), 
									  drm.getInitialLenPt()[0],
									  selection);
						}
						
						int[] len1 = drm.getLenPtForEachDat()[frame.getDatNo()][0];
	
						int[] newPt = new int[] { (int) location[0], (int) location[1] };
						int[][] newLenPt = new int[2][];
						newLenPt[0] = len1;
						newLenPt[1] = newPt;
						drm.getLenPtForEachDat()[frame.getDatNo()]=(newLenPt);
						drm.getInputForEachDat()[frame.getDatNo()] =input;
	//					sm.addLocationList(k, location);
	//					System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
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
				
				 
				drm.addLocationList(frame.getDatNo(),
						drm.getNoOfImagesInDatFile(frame.getDatNo()), 
						k, 
						localLocation);
//				dm.addLocationList(model.getDatImages().getShape()[0], k, localLocation);
//				frame.setRoiLocation(location);
				debug("location[0]:  " + location[0] + "  location[1]:  " + location[1] + "  selection" + selection);
				
				int[][] localLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
				
				drm.getLenPtForEachDat()[frame.getDatNo()] = localLenPt;
				
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
				
				drm.addLocationList(frame.getDatNo(),
						drm.getNoOfImagesInDatFile(frame.getDatNo()), 
						k, 
						localLocation);
				drm.addLocationList(frame.getDatNo(),
									drm.getNoOfImagesInDatFile(frame.getDatNo()), 
									k, 
									localLocation);
				
				
				
				drm.getLenPtForEachDat()[frame.getDatNo()] = new int[][]{len, pt};
				
				frame.setRoiLocation(localLocation);
			}
			catch(Exception r){
				
			}
			
			
		}
	}

	public void resetTracker() {
		tracker = null;
		drm.setTracker(null);
		drm.setInitialTracker(null);
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
			input = fm.getRawImageData().getSlice(new SliceND (fm.getRawImageData().getShape())).squeeze();
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
							drm.addTrackerLocationList(fm.getImageNumber(),
									   location);
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
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
																						  drm.getSortedX(), 
																						  drm.getInitialLenPt()[0],
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
//					len = drm.getLenPtForEachDat()[frame.getDatNo()][0];
//					pt = drm.getLenPtForEachDat()[frame.getDatNo()][1];
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
					
//					drm.getInputForEachDat()[frame.getDatNo()] =input;
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
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
																						  drm.getSortedX(), 
																						  drm.getInitialLenPt()[0],
																						  selection);
						}
						
						if (location != null) {
							drm.setTrackerCoordinates(location);
							
							int[][] newLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
							
							drm.getLenPtForEachDat()[fm.getDatNo()] = newLenPt;
							
							drm.getInputForEachDat()[fm.getDatNo()] = input;
							drm.addTrackerLocationList(selection, location);
						}
	
						drm.setTracker(tracker);
						
					} catch (Exception e) {
						debug(
								"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
					}
	
				}
			}
	
			else if (trackingMarker == 2) {
				if (drm.getInputForEachDat()[fm.getDatNo()]==null) {
					
					
					initialLocation = seedLocation;
					
					if (drm.getInitialTracker() == null){	
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
						tracker = drm.getInitialTracker();
						
					}
					
					try {
						location = tracker.track(input);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (location != null) {
						drm.setTrackerCoordinates(location);
						drm.addTrackerLocationList(fm.getImageNumber(),
									   location);
					}
					
					
					drm.setTracker(tracker);
					drm.getInputForEachDat()[fm.getDatNo()] = input;
	//				sm.addLocationList(k, location);
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
							location = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
									  drm.getSortedX(), 
									  drm.getInitialLenPt()[0],
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
	
					drm.setTracker(tracker);
					
				}
			}
	
			if (location == null) {
				location = drm.getTrackerCoordinates();
			}
			
			if (isTheTrackingMarkerNot3){
				
				int[] localPt = new int[] {(int) (location[0]), (int) (location[1])};

				int[] localLen = drm.getInitialLenPt()[0];
				int[] localLocation = new int[] { localPt[0], localPt[1], (localPt[0] + localLen[0]),
						(localPt[1]), localPt[0], localPt[1] + localLen[1], (localPt[0] + localLen[0]),
						(localPt[1] + localLen[1]) };
				
				 
				drm.addLocationList(fm.getDatNo(),
						drm.getNoOfImagesInDatFile(fm.getDatNo()), 
						k, 
						localLocation);
				
				drm.addLocationList(fm.getDatNo(), 
									drm.getNoOfImagesInDatFile(fm.getDatNo()), 
									k, 
									localLocation);
			
				fm.setRoiLocation(localLocation);
				
				int[][] localLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
				
				drm.getLenPtForEachDat()[fm.getDatNo()] = localLenPt;
				
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
						selection, 
						localLocation);
				
				
				drm.getLenPtForEachDat()[fm.getDatNo()] = new int[][] {len,pt};
				
				
				fm.setRoiLocation(localLocation);
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
			input = fm.getRawImageData().getSlice(new SliceND (fm.getRawImageData().getShape())).squeeze();
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
//					drm.setInitialLenPt(new int[][] {len, pt });
			
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
					if(drm.getTracker() != null){
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
						
						//tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], initialLocation, TrackerType.TLD);
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
//				int[] localLen = new int[] {(int) ((int)location[2] - (int)location[0]), (int) ((int)location[5] -(int)location[1])};
				int[] localLen = drm.getInitialLenPt()[0];
				int[] localLocation = new int[] { localPt[0], localPt[1], (localPt[0] + localLen[0]),
						(localPt[1]), localPt[0], localPt[1] + localLen[1], (localPt[0] + localLen[0]),
						(localPt[1] + localLen[1]) };
				
				 
				drm.addLocationList(fm.getDatNo(),
						drm.getNoOfImagesInDatFile(fm.getDatNo()), 
						k, 
						localLocation);
//				dm.addLocationList(model.getDatImages().getShape()[0], k, localLocation);
				
				int a = fm.getDatNo();
				int b = drm.getNoOfImagesInDatFile(a);
				
				drm.addLocationList(a,
									b, 
									k, 
									localLocation);
				
				fm.setRoiLocation(localLocation);
				
			
				int[][] localLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(localLocation);
				
				drm.getLenPtForEachDat()[fm.getDatNo()] = localLenPt;
				
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
				
				fm.setRoiLocation(localLocation);
				
				drm.getLenPtForEachDat()[fm.getDatNo()] = new int[][] {len, pt};
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