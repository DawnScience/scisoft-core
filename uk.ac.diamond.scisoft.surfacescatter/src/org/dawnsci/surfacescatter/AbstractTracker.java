package org.dawnsci.surfacescatter;

import org.dawnsci.boofcv.BoofCVImageTrackerServiceCreator;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.SliceND;

public abstract class AbstractTracker {
	
	private DirectoryModel drm;
	private int selection;
	private int k;
	private int trackingMarker;
	private IImageTracker tracker = null;
	private IImageTracker trackerInitialCopy = null;
	private double[] location;
	private double[] initialLocation;
	private FrameModel frame;
	private IDataset input;
	private double[] seedLocation;
	private boolean isTheTrackingMarkerNot3 = true;
	
	
	public void init(DirectoryModel drm, 
					 int trackingMarker,
					 int k, // dat number
					 int selection) { //selection is the frame number
		
		this.drm = drm;
		this.k = k;
		this.selection = selection;
		
		frame = drm.getFms().get(selection);
		input = DatasetFactory.createFromObject(0);
		try{
			input = frame.getRawImageData().getSlice(new SliceND (frame.getRawImageData().getShape())).squeeze();
		}
		catch (Exception i){
			
		}
		
		if (trackingMarker == 3){
			isTheTrackingMarkerNot3 = false;
		}
		
	}
	
	public IImageTracker getTracker() {
		return tracker;
	}

	public void setTracker(IImageTracker tracker) {
		this.tracker = tracker;
	}

	public IImageTracker getTrackerInitialCopy() {
		return trackerInitialCopy;
	}

	public void setTrackerInitialCopy(IImageTracker trackerInitialCopy) {
		this.trackerInitialCopy = trackerInitialCopy;
	}

	public void init(DirectoryModel drm, 
					 int trackingMarker,
					 int k, // dat number
					 double[] seedLocation,
					 int selection) { //selection is the frame number
	
		init(drm, trackingMarker, k, selection);
		this.seedLocation =seedLocation;
	
	}
	
	public void reset() {
		tracker = null;
		drm.setTracker(null);
		drm.setInitialTracker(null);
	}
	
	
	protected int[] getBackgroundLenPt() {
		return drm.getBackgroundLenPt()[0];
	}
	
	
	public void interpolationRoutine(){
		
		int[] len = new int[2];
		int[] pt = new int[2];
		
		if (isTheTrackingMarkerNot3){
			len = new int[] {(int) Math.round(drm.getInterpolatedLenPts().get(selection)[0][0]),(int) Math.round(drm.getInterpolatedLenPts().get(selection)[0][1])};
			pt = new int[] {(int) Math.round(drm.getInterpolatedLenPts().get(selection)[1][0]),(int) Math.round(drm.getInterpolatedLenPts().get(selection)[1][1])};
			
			location = LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][]{len,pt});
			
		}
		else{
			int[] localPt = drm.getInitialLenPt()[1];
			int[] localLen = drm.getInitialLenPt()[0];
			
			
			double[] localLocation = LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] {localLen, localPt});
			
			drm.addLocationList(frame.getDatNo(),
					drm.getNoOfImagesInDatFile(frame.getDatNo()), 
					k, 
					localLocation);
			drm.addLocationList(frame.getDatNo(),
								drm.getNoOfImagesInDatFile(frame.getDatNo()), 
								k, 
								localLocation);
			
			drm.getLenPtForEachDat()[frame.getDatNo()] = new int[][]{len, pt};
			
			location =localLocation;
		}
	}
	
	public void initialiseTracker(){
		int[] len = drm.getLenPtForEachDat()[frame.getDatNo()][0];
		int[] pt = drm.getLenPtForEachDat()[frame.getDatNo()][1];
		
		tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
		trackerInitialCopy = BoofCVImageTrackerServiceCreator.createImageTrackerService();

		initialLocation = LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] {len, pt});
		
		if(seedLocation == null){
			initialLocation = seedLocation;
		}
		
		try {
			tracker.initialize(input, initialLocation, TrackingMethodology.toTT(frame.getTrackingMethodology()));
			trackerInitialCopy.initialize(input, initialLocation, TrackingMethodology.toTT(frame.getTrackingMethodology()));
		} catch (Exception e) {

		}

		drm.getInputForEachDat()[frame.getDatNo()] =input;
		
		location = initialLocation;
		
		drm.setTracker(tracker);
		drm.setInitialTracker(trackerInitialCopy);
		drm.addTrackerLocationList(frame.getImageNumber(),
								initialLocation);
		drm.setTrackerCoordinates(location);

		
		
	}
	
	public void followUp(){
		try{
			if(drm.getTracker() != null){
				location = drm.getTracker().track(input);
								
			}
			
			if (location != null) {
					drm.setTrackerCoordinates(location);
					drm.addTrackerLocationList(frame.getImageNumber(),
							   					location);
					drm.setTracker(tracker);
				
			}
			
			else{
				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
				tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], 
								   drm.getTrackerCoordinates(), 
								   TrackingMethodology.toTT(frame.getTrackingMethodology()));
				location = tracker.track(input);
				
				if (location != null) {
					drm.setTrackerCoordinates(location);
					drm.addTrackerLocationList(frame.getImageNumber(),
											   location);
					drm.setTracker(tracker);
				}
			}
			
			if (location == null){
				double[] l = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
						  drm.getSortedX(), 
						  drm.getInitialLenPt()[0],
						  selection);
				location = l; 
			}
			
			if (location != null) {
				drm.setTrackerCoordinates(location);
					
				int[][] newLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
						
				drm.getLenPtForEachDat()[frame.getDatNo()] = newLenPt;
						
				drm.getInputForEachDat()[frame.getDatNo()] = input;
				drm.addTrackerLocationList(selection, location);
			}
			
			drm.setTracker(tracker);
		}
		catch(Exception h){
			System.out.println(h.getMessage());
		}
	
	}
	
	public void restartInOppositeDirection(){
	
		if (drm.getInputForEachDat()[frame.getDatNo()]==null) {
			
			int[] len = drm.getInitialLenPt()[0];
			int[] pt = drm.getInitialLenPt()[1];
			
			double[] lp = LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] {len,pt});
			
			initialLocation = lp;
			
			if(seedLocation != null){
				initialLocation = seedLocation;
			}
			
			if (drm.getInitialTracker() == null){	
				tracker =null;
				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
				
				try {
					
					double[] l = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
							  drm.getSortedX(), 
							  drm.getInitialLenPt()[0],
							  selection);
					
					location = l; 
					
					tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], 
							 		   location, 
							 		   TrackingMethodology.toTT(frame.getTrackingMethodology()));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			else{
				tracker = drm.getInitialTracker();				
			}
			
			try {
				location = tracker.track(input);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (location != null) {
				drm.setTrackerCoordinates(location);
				drm.addTrackerLocationList(frame.getImageNumber(),
							   location);
			}
			
			drm.setTracker(tracker);
			drm.getInputForEachDat()[frame.getDatNo()] = input;

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
					tracker.initialize(drm.getInputForEachDat()[frame.getDatNo()], 
					 		   		   location, 
					 		           TrackingMethodology.toTT(frame.getTrackingMethodology()));
					
					location = tracker.track(input);
					
					if (location != null) {
						drm.setTrackerCoordinates(location);
						drm.addTrackerLocationList(selection, location);
						drm.setTracker(tracker);
					}
				}
				
				if (location == null){
					double[] l = TrackerLocationInterpolation.trackerInterpolationInterpolator0(drm.getTrackerLocationList(), 
							  drm.getSortedX(), 
							  drm.getInitialLenPt()[0],
							  selection);
					
					location = l; 
					
				}
				if (location != null) {
					
					drm.setTrackerCoordinates(location);
					int[][] newLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
					
					drm.getLenPtForEachDat()[frame.getDatNo()] = newLenPt;
					
					drm.getInputForEachDat()[frame.getDatNo()] = input;
					drm.addTrackerLocationList(selection, location);
					
				}
				
				if (location == null) {
					location = drm.getTrackerCoordinates();
				}
				
			} catch (Exception e) {

			}
		
			drm.setTracker(tracker);
			
		}	
	}
	
	public void setPostTrackParameters(){
		
		int[] localPt = new int[] {(int) (location[0]), (int) (location[1])};
		int[] localLen = drm.getInitialLenPt()[0];
		int[][] localLenPt = new int[][]{localLen,localPt};
		
		double[] localLocation = LocationLenPtConverterUtils.lenPtToLocationConverter(localLenPt);
		 
		drm.addLocationList(frame.getDatNo(),
							drm.getNoOfImagesInDatFile(frame.getDatNo()), 
							k, 
							localLocation);
				
		drm.getLenPtForEachDat()[frame.getDatNo()] = localLenPt;
		drm.getInputForEachDat()[frame.getDatNo()] =input;
		
		frame.setRoiLocation(localLocation);
	}
	
	public void initialiseRestart(){
		if (drm.getInputForEachDat()[frame.getDatNo()] == null) {	
			initialiseTracker();
		}
			
		else {
			followUp();					
		}
		
	}

	public int getTrackingMarker() {
		return trackingMarker;
	}

	public void setTrackingMarker(int trackingMarker) {
		this.trackingMarker = trackingMarker;
	}
	
	public IDataset getInput() {
		return input;
	}

	public void setInput(IDataset input) {
		this.input = input;
	}

	public boolean isTheTrackingMarkerNot3() {
		return isTheTrackingMarkerNot3;
	}

	public void setTheTrackingMarkerNot3(boolean isTheTrackingMarkerNot3) {
		this.isTheTrackingMarkerNot3 = isTheTrackingMarkerNot3;
	}

	public FrameModel getFrame() {
		return frame;
	}

	public void setFrame(FrameModel frame) {
		this.frame = frame;
	}

	public double[] getInitialLocation() {
		return initialLocation;
	}

	public void setInitialLocation(double[] initialLocation) {
		this.initialLocation = initialLocation;
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
	}


}
