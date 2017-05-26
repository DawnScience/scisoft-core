package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import org.dawnsci.surfacescatter.MethodSettingEnum.MethodSetting;
import org.dawnsci.surfacescatter.TrackingMethodology.TrackerType1;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

public class DirectoryModel {
	
	private ArrayList<ArrayList<Integer>> framesCorespondingToDats;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private ArrayList<Double> xList;
	private ArrayList<Double> qList;
	private ArrayList<ArrayList<Double>> dmxList;
	private ArrayList<FrameModel> fms;
	private TrackerType1 trackingMethodolgy;
	private boolean trackerOn;
	private int[][] initialLenPt =  new int[][] {{50,50},{10,10}};;
	private IImageTracker initialTracker;
	private double[] trackerCoordinates = {100,100,110,100,110,100,110,110};
	private double[] initialTrackerCoordinates = {100,100,110,100,110,100,110,110};
	private IDataset[] inputForEachDat;
	private String[] datFilepaths;
	private IDataset[] initialDatasetForEachDat;
	private int[][][] lenPtForEachDat;
	private ArrayList<ArrayList<double[]>> locationList; 
	private IImageTracker tracker;
	private Dataset sortedX; 
	private Dataset sortedQ;
	private ArrayList<double[][]> interpolatedLenPts;
	private IDataset temporaryBackgroundHolder;
	private int[][] permanentBoxOffsetLenPt;
	private int[][] permanentBackgroundLenPt;
	private int[][] boxOffsetLenPt = new int[][] {{0, 0 },{-25, -25}};
	private IRectangularROI backgroundROI = new RectangularROI(10,10,50,50,0);
	private IROI[] backgroundROIForEachDat;
	private int[][] backgroundLenPt;
	private OutputCurvesDataPackage ocdp;
	private ArrayList<IDataset> outputDatArray;
	private int[] filepathsSortedArray;
	private ArrayList<Integer> imageNoInDatList;
	private double[][] seedLocation;
	private ArrayList<double[]> trackerLocationList;
	private MethodSetting correctionSelection = MethodSetting.SXRD;
	private ArrayList<IDataset> backgroundDatArray;
	private CurveStitchDataPackage csdp;
	private ArrayList<double[][]> interpolatorBoxes;
	private ArrayList<IRegion> interpolatorRegions;
	private ArrayList<OverlapDataModel> overlapDataModels;
	private double currentRawIntensity;

	
	public void addToInterpolatorRegions(IRegion box){
		
		if(interpolatorRegions ==null){
			interpolatorRegions = new ArrayList<>();
		}
		
		interpolatorRegions.add(box);
	}
	

	public ArrayList<IRegion> getInterpolatorRegions() {
		return interpolatorRegions;
	}

	public void setInterpolatorRegions(ArrayList<IRegion> interpolatorRegions) {
		this.interpolatorRegions = interpolatorRegions;
	}
	
	
	public void addToInterpolatorBoxes(double[][] box){
		
		if(interpolatorBoxes == null){
			interpolatorBoxes = new ArrayList<>();
		}
		
		interpolatorBoxes.add(box);
	}
	
	public ArrayList<double[][]> getInterpolatorBoxes() {
		return interpolatorBoxes;
	}

	public void setInterpolatorBoxes(ArrayList<double[][]> interpolatorBoxes) {
		this.interpolatorBoxes = interpolatorBoxes;
	}
	
	public CurveStitchDataPackage getCsdp() {
		if(csdp == null){
			csdp = new CurveStitchDataPackage();
		}
		
		return csdp;
	}

	public void setCsdp(CurveStitchDataPackage csdp) {
		this.csdp = csdp;
	}

	public ArrayList<IDataset> getBackgroundDatArray() {
		return backgroundDatArray;
	}
	
	public void setBackgroundDatArray(ArrayList<IDataset> backgroundDatArray) {
		this.backgroundDatArray = backgroundDatArray;
	}
	
	public void addBackgroundDatArray(IDataset in){
		if (backgroundDatArray==null){
			backgroundDatArray = new ArrayList<IDataset>();
		}
		
		ArrayList<IDataset> backgroundDatArray1 = new ArrayList<IDataset>();
		backgroundDatArray1 = (ArrayList<IDataset>) backgroundDatArray.clone();
		backgroundDatArray1.add(in);
		firePropertyChange("backgroundDatArray", this.backgroundDatArray,
				this.backgroundDatArray= backgroundDatArray1);
	}
	
	public void addBackgroundDatArray(int l, int k, IDataset in){
		
		if (backgroundDatArray==null ){
			backgroundDatArray = new ArrayList<IDataset>();
			for (int i = 0; i < l; i++) {
				backgroundDatArray.add(DatasetFactory.zeros(new int[] {2,2}));
				}
		}
		if (backgroundDatArray.isEmpty()){
			backgroundDatArray = new ArrayList<IDataset>();
			for (int i = 0; i < l; i++) {
				backgroundDatArray.add(DatasetFactory.zeros(new int[] {2,2}));
				}
		}
		
		ArrayList<IDataset> backgroundDatArray1 = new ArrayList<IDataset>();
		backgroundDatArray1 = (ArrayList<IDataset>) backgroundDatArray.clone();
		backgroundDatArray1.set(k,in);
		firePropertyChange("backgroundDatArray", this.backgroundDatArray,
				this.backgroundDatArray= backgroundDatArray1);
	}
	
	public void addTrackerLocationList(double[] in){
		
		if (trackerLocationList==null){
			trackerLocationList = new ArrayList<double[]>();
		}
		
		ArrayList<double[]> trackerLocationList1 = new ArrayList<double[]>();
		trackerLocationList1 = (ArrayList<double[]>) trackerLocationList.clone();
		trackerLocationList1.add(in);
		firePropertyChange("trackerLocationList", this.trackerLocationList,
				this.trackerLocationList= trackerLocationList1);
	}
	
	public void addTrackerLocationList(int k, double[] in){
		if (trackerLocationList==null){
			trackerLocationList = new ArrayList<double[]>();
		
			for (int i = 0; i < fms.size(); i++) {
				trackerLocationList.add(new double[] {0,0,0,0,0,0,0,0});
				}
		}
		
		if (trackerLocationList.size() == 0){
			trackerLocationList = new ArrayList<double[]>();
		
			for (int i = 0; i < fms.size(); i++) {
				trackerLocationList.add(new double[] {0,0,0,0,0,0,0,0});
				}
		}

		ArrayList<double[]> trackerLocationList1 = new ArrayList<double[]>();
		trackerLocationList1 = (ArrayList<double[]>) trackerLocationList.clone();
		trackerLocationList1.set(k,in);
		firePropertyChange("trackerLocationList", this.trackerLocationList,
				this.trackerLocationList= trackerLocationList1);
	}
	
	
	
	public ArrayList<Integer> getImageNoInDatList() {
		return imageNoInDatList;
	}

	public void setImageNoInDatList(ArrayList<Integer> imageNoInDatList) {
		this.imageNoInDatList = imageNoInDatList;
	}

	public void resetAll(){
		
		xList =null;
		dmxList = null;
		ocdp = null;
		csdp = null;
		
		setOutputDatArray(null);
	
		trackerLocationList = null;
//		trackerKList = null;
		locationList = null;
		try{
			for(FrameModel fm : fms){
			
				fm.resetRoiLocation();
			
			}
		}
		catch(Exception h){
			
		}
			
			//		reflectivityAreaCorrection = null;
//		reflectivityFluxCorrection = null;
		
	}
	
	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	public ArrayList<double[]> getTrackerLocationList() {
		return trackerLocationList;
	}

	public void setTrackerLocationList(ArrayList<double[]> trackerLocationList) {
		this.trackerLocationList = trackerLocationList;
	}
	
	public void resetTrackers(){
		tracker = null;
	}
	
	public ArrayList<ArrayList<double[]>> getLocationList() {
		return locationList;
	}

	public void setLocationList(ArrayList<ArrayList<double[]>> locationList) {
		this.locationList = locationList;
	}
	
	public void addLocationList(int n, int l , int k, int[] in){
		double[] inDouble = new double[in.length]; 
		for (int i = 0; i<in.length; i++){
			inDouble[i] = (double)in[i];
		}
		addLocationList(n, l, k, inDouble);
		
	}

	public void addLocationList(int n, int l, int k, double[] location){
		
		if (locationList==null || locationList.isEmpty()){
			
			locationList = new ArrayList<>();
			
			for(int m = 0; m<datFilepaths.length ; m++){
				
				ArrayList<double[]> q = new ArrayList<>();
				locationList.add(q);
				
				int y = this.getNoOfImagesInDatFile(m);
				
				for (int i = 0; i < y; i++) {		
					locationList.get(m).add(new double[]{0,0,0,0,0,0,0,0});
				}
				
			}
			
			if(locationList.get(n).size()<l){
				for (int i = 0; i < l; i++) {		
					locationList.get(n).set(i, new double[]{0,0,0,0,0,0,0,0});
				}
			}
		}
		
		if(locationList.get(n).size()<l){
			for (int i = 0; i < l; i++) {		
				locationList.get(n).add(new double[]{0,0,0,0,0,0,0,0});
			}
		}
		
		ArrayList<double[]> locationList1 = new ArrayList<double[]>();
		locationList1 = (ArrayList<double[]>) locationList.get(n).clone();
		locationList1.set(k, location);
		firePropertyChange("locationList", this.locationList,
				this.locationList.set(n, locationList1));
	}
	

//	public void addTrackerLocationList(int n, int l , int k, int[] in){
//		double[] inDouble = new double[in.length]; 
//		for (int i = 0; i<in.length; i++){
//			inDouble[i] = (double)in[i];
//		}
//		addTrackerLocationList(n, l, k, inDouble);
//		
//	}
//
//	public void addTrackerLocationList(int n, int l, int k, double[] location){
//		
//		if (trackerLocationList==null || trackerLocationList.isEmpty()){
//			
//			trackerLocationList = new ArrayList<>();
//			
//			if(trackerLocationList.get(n) == null){ 
//				trackerLocationList.set(n, new ArrayList<>());
//			}
//			
//			for (int i = 0; i < l; i++) {		
//				trackerLocationList.get(n).add(new double[]{0,0,0,0,0,0,0,0});
//			}
//		}
//		
//		ArrayList<double[]> locationList1 = new ArrayList<double[]>();
//		locationList1 = (ArrayList<double[]>) trackerLocationList.get(n).clone();
//		locationList1.set(k, location);
////		locationList.set(n, locationList1);
//		firePropertyChange("trackerLocationList", this.trackerLocationList,
//				this.trackerLocationList.set(n, locationList1));
//	}
	
	

	public void addxList(double x){
		if (xList==null){
			xList = new ArrayList<Double>();
		}
		ArrayList<Double> xList1 = new ArrayList<Double>();
		xList1 = (ArrayList<Double>) xList.clone();
		xList1.add(x);
		firePropertyChange("xList", this.xList,
				this.xList= xList1);
	}
	
	
	public void addxList(int l, int k, double x){
		if (xList==null){
			xList = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  xList.add(-10000000000.0);
				}
		}
		
		if (xList.size() == 0){
			xList = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  xList.add(-10000000000.0);
				}
		}
		
		
		ArrayList<Double> xList1 = new ArrayList<Double>();
		xList1 = (ArrayList<Double>) xList.clone();
		xList1.set(k, x);
		firePropertyChange("xList", this.xList,
				this.xList= xList1);
	}
	
	
	public ArrayList<ArrayList<Integer>> getFramesCorespondingToDats() {
		return framesCorespondingToDats;
	}

	public void setFramesCorespondingToDats(ArrayList<ArrayList<Integer>> framesCorespondingToDats) {
		this.framesCorespondingToDats = framesCorespondingToDats;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}
	
	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}

	public ArrayList<Double> getxList() {
		return xList;
	}

	public void setxList(ArrayList<Double> xList) {
		this.xList = xList;
	}


	public ArrayList<FrameModel> getFms() {
		return fms;
	}


	public void setFms(ArrayList<FrameModel> fms) {
		this.fms = fms;
	}


	public TrackerType1 getTrackingMethodolgy() {
		return trackingMethodolgy;
	}


	public void setTrackingMethodolgy(TrackerType1 trackingMethodolgy) {
		this.trackingMethodolgy = trackingMethodolgy;
	}


	public boolean isTrackerOn() {
		return trackerOn;
	}


	public void setTrackerOn(boolean trackerOn) {
		this.trackerOn = trackerOn;
	}


	public int[][] getInitialLenPt() {
		return initialLenPt;
	}


	public void setInitialLenPt(int[][] initialLenPt) {
		this.initialLenPt = initialLenPt;
	}


	public IImageTracker getInitialTracker() {
		return initialTracker;
	}


	public void setInitialTracker(IImageTracker initialTracker) {
		this.initialTracker = initialTracker;
	}


	public double[] getTrackerCoordinates() {
		return trackerCoordinates;
	}


	public void setTrackerCoordinates(double[] trackerCoordinates) {
		this.trackerCoordinates = trackerCoordinates;
	}


	public double[] getInitialTrackerCoordinates() {
		return initialTrackerCoordinates;
	}


	public void setInitialTrackerCoordinates(double[] intialTrackerCoordinates) {
		this.initialTrackerCoordinates = intialTrackerCoordinates;
	}


	public IDataset[] getInputForEachDat() {
		return inputForEachDat;
	}


	public void setInputForEachDat(IDataset[] inputForEachDat1) {
		
		if(inputForEachDat == null){
			inputForEachDat = new IDataset[datFilepaths.length];
		}
		
		this.inputForEachDat = inputForEachDat;
	}


	public String[] getDatFilepaths() {
		
		if(inputForEachDat == null){
			inputForEachDat = new IDataset[datFilepaths.length];
		}
		
		return datFilepaths;
	}


	public void setDatFilepaths(String[] datFilepaths) {
		this.datFilepaths = datFilepaths;
	}


	public IDataset[] getInitialDatasetForEachDat() {
		
		if(initialDatasetForEachDat == null){
			initialDatasetForEachDat = new IDataset[datFilepaths.length];
		}
		
		return initialDatasetForEachDat;
	}


	public void setInitialDatasetForEachDat(IDataset[] initialDatasetForEachDat) {
		
		if(this.initialDatasetForEachDat == null){
			this.initialDatasetForEachDat = new IDataset[datFilepaths.length];
		}
		
		this.initialDatasetForEachDat = initialDatasetForEachDat;
	}
	
	
	public int[][][] getLenPtForEachDat() {
		
		if(lenPtForEachDat == null){
			lenPtForEachDat = new int[datFilepaths.length][][];
		}
		
		return lenPtForEachDat;
	}


	public void setLenPtForEachDat(int[][][] lenPtForEachDat) {
		
		if(this.lenPtForEachDat == null){
			lenPtForEachDat = new int[datFilepaths.length][][];
		}
		
		
		this.lenPtForEachDat = lenPtForEachDat;
	}
	
	
	public int getNoOfImagesInDatFile(int n){
		
		int probe = 0;
		
		for(FrameModel fm: fms){
			if(fm.getDatNo() == n ){
				probe++;
			}
		}
		
		return probe;
	}

	public IImageTracker getTracker() {
		return tracker;
	}

	public void setTracker(IImageTracker tracker) {
		this.tracker = tracker;
	}

	public Dataset getSortedX() {
		return sortedX;
	}

	public void setSortedX(Dataset sortedX) {
		this.sortedX = sortedX;
	}

	public ArrayList<double[][]> getInterpolatedLenPts() {
		return interpolatedLenPts;
	}

	public void setInterpolatedLenPts(ArrayList<double[][]> interpolatedLenPts) {
		this.interpolatedLenPts = interpolatedLenPts;
	}

	public IDataset getTemporaryBackgroundHolder() {
		return temporaryBackgroundHolder;
	}

	public void setTemporaryBackgroundHolder(IDataset temporaryBackgroundHolder) {
		this.temporaryBackgroundHolder = temporaryBackgroundHolder;
	}

	public int[][] getBoxOffsetLenPt() {
		return boxOffsetLenPt;
	}

	public void setBoxOffsetLenPt(int[][] boxOffsetLenPt) {
		this.boxOffsetLenPt = boxOffsetLenPt;
	}

	public int[][] getPermanentBoxOffsetLenPt() {
		return permanentBoxOffsetLenPt;
	}

	public void setPermanentBoxOffsetLenPt(int[][] permanentBoxOffsetLenPt) {
		this.permanentBoxOffsetLenPt = permanentBoxOffsetLenPt;
	}

	public IRectangularROI getBackgroundROI() {
		return backgroundROI;
	}

	public void setBackgroundROI(IROI iroi) {
		this.backgroundROI = iroi.getBounds();
	}
	
	public void setBackgroundROI(IRectangularROI iroi) {
		this.backgroundROI = iroi;
	}

	public IROI[] getBackgroundROIForEachDat() {
		
		if(this.backgroundROIForEachDat == null){
			backgroundROIForEachDat = new IROI[datFilepaths.length];
		}
		
		return backgroundROIForEachDat;
	}

	public void setBackgroundROIForEachDat(IROI[] backgroundROIForEachDat) {
		
		if(this.backgroundROIForEachDat == null){
			this.backgroundROIForEachDat = new IROI[datFilepaths.length];
		}
		
		this.backgroundROIForEachDat = backgroundROIForEachDat;
	}

	public int[][] getBackgroundLenPt() {
		return backgroundLenPt;
	}

	public void setBackgroundLenPt(int[][] backgroundLenPt) {
		this.backgroundLenPt = backgroundLenPt;
	}

	public OutputCurvesDataPackage getOcdp() {
		
		if(this.ocdp == null){
			this.ocdp = new OutputCurvesDataPackage();
			ocdp.setNoOfDats(datFilepaths.length);
		}
		
		return ocdp;
	}

	public void setOcdp(OutputCurvesDataPackage ocdp) {
		this.ocdp = ocdp;
	}


	public ArrayList<IDataset> getOutputDatArray() {
		return outputDatArray;
	}


	public void setOutputDatArray(ArrayList<IDataset> outputDatArray) {
		this.outputDatArray = outputDatArray;
	}


	public int[] getFilepathsSortedArray() {
		return filepathsSortedArray;
	}


	public void setFilepathsSortedArray(int[] filepathsSortedArray) {
		this.filepathsSortedArray = filepathsSortedArray;
	}
	
	public ArrayList<ArrayList<Double>> getDmxList() {
		
		if(this.dmxList== null){
			
			this.dmxList= new ArrayList<ArrayList<Double>>();
			
			for(int y=0; y <datFilepaths.length; y++){
				dmxList.add(new ArrayList<Double>());
			}
		}
		
		return dmxList;
	}


	public ArrayList<ArrayList<Double>> addDmxList(int n, ///which dat do we want
												   int k, // posiiton in the dat
												   double result) {
		
		if(this.dmxList== null){
			
			this.dmxList= new ArrayList<ArrayList<Double>>();
			
			for(int y=0; y <datFilepaths.length; y++){
				dmxList.add(new ArrayList<Double>());
				for(int u =0; u<this.getNoOfImagesInDatFile(y);u++ ){
					dmxList.get(y).add(-10000000000.0);
				}
			}
		}
		
		dmxList.get(n).set(k, result);
		
		return dmxList;
	}

	
	public void setDmxList(ArrayList<ArrayList<Double>> dmxList) {
		this.dmxList = dmxList;
	}

	public double[][] getSeedLocation() {
		return seedLocation;
	}

	public void setSeedLocation(double[][] seedLocation) {
		this.seedLocation = seedLocation;
	}

	public void addSeedLocation(int n,  //Dat number
								double[] seed){
		if(seedLocation == null) {
			seedLocation= new double[datFilepaths.length][];
		}
		
		seedLocation[n] = seed;
	}

	public MethodSetting getCorrectionSelection() {
		return correctionSelection;
	}

	public void setCorrectionSelection(MethodSetting correctionSelection) {
		this.correctionSelection = correctionSelection;
	}


	public int[][] getPermanentBackgroundLenPt() {
		return permanentBackgroundLenPt;
	}


	public void setPermanentBackgroundLenPt(int[][] permanentBackgroundLenPt) {
		this.permanentBackgroundLenPt = permanentBackgroundLenPt;
	}
	
	public ArrayList<OverlapDataModel> getOverlapDataModels() {
		
		if(overlapDataModels == null){
			overlapDataModels = csdp.getOverlapDataModels();
		}
		
		return overlapDataModels;
	}

	public void setOverlapDataModels(ArrayList<OverlapDataModel> overlapDataModels) {
		this.overlapDataModels = overlapDataModels;
	}


	public double getCurrentRawIntensity() {
		return currentRawIntensity;
	}


	public void setCurrentRawIntensity(double currentRawIntensity) {
		this.currentRawIntensity = currentRawIntensity;
	}
	
	public Dataset getSortedQ() {
		return sortedQ;
	}


	public void setSortedQ(Dataset sortedQ) {
		this.sortedQ = sortedQ;
	}


	
	
	public void qConversion(double energy, int theta){
		
		qList =null;
		
		IDataset splicedCurveQ  =csdp.getSplicedCurveQ();
		splicedCurveQ =null;
		sortedQ = null;
		
		if(xList !=null){
			
			try{
				if (qList==null){
					qList = new ArrayList<Double>();
					for (int i = 0; i < xList.size(); i++) {
						  qList.add(0.0);
						}
				}
				
				if (qList.size() == 0){
					qList = new ArrayList<Double>();
					for (int i = 0; i < xList.size(); i++) {
						  qList.add(0.0);
						}
				}
			}
			catch(Exception o){
				
			}
			
			try{
				
			
				for(int i = 0 ; i < xList.size(); i++){
					
					double energyJ  = energy*1000*1.602177*Math.pow(10, -19);
					double hc = 1.98644568*Math.pow(10, -25);
					double q = 4*Math.PI* Math.sin(xList.get(i))*energyJ/ hc;
					
					double qA = q/(Math.pow(10, 10));
					try{
						qList.set(i, qA);
					}
					catch(NullPointerException d){
						qList.add(qA);
					}
				}
			}
			catch(Exception p){
				
			}
		}
		
		if(csdp.getSplicedCurveX() !=null){
			
			try{
				if (splicedCurveQ==null){
					splicedCurveQ = DatasetFactory.zeros(csdp.getSplicedCurveX().getShape());
				}
				
				if (splicedCurveQ.getSize() == 0){
					splicedCurveQ = DatasetFactory.zeros(csdp.getSplicedCurveX().getShape());
					
				}
				
				if (splicedCurveQ.getSize()!=csdp.getSplicedCurveX().getSize()){
					splicedCurveQ = DatasetFactory.zeros(csdp.getSplicedCurveX().getShape());
				}
			}
			catch(Exception o){
				
			}
			
			try{
			
				for(int i = 0 ; i < csdp.getSplicedCurveX().getSize(); i++){
					
					double energyJ  = energy*1000*1.602177*Math.pow(10, -19);
					double hc = 1.98644568*Math.pow(10, -25);
					double q = 4*Math.PI* (Math.sin((theta +1 )*csdp.getSplicedCurveX().getDouble(i)))*energyJ/ hc;
					double qA = q/(Math.pow(10, 10));
					
					try{
						splicedCurveQ.set(qA, i);
					}
					catch(NullPointerException d){
					
					}
				}
			}
			catch(Exception p){
				
			}
		}
		
		if(sortedX !=null){
			
			try{
				if (sortedQ==null){
					sortedQ = DatasetFactory.zeros(sortedX.getShape());
				}
				
				if (sortedQ.getSize() == 0){
					sortedQ = DatasetFactory.zeros(sortedX.getShape());
					
				}
				
				if (sortedQ.getSize()!=sortedX.getSize()){
					sortedQ = DatasetFactory.zeros(sortedX.getShape());
				}
			}
			catch(Exception o){
				
			}
			
			try{
			
				for(int i = 0 ; i < sortedX.getSize(); i++){
					
					double energyJ  = energy*1000*1.602177*Math.pow(10, -19);
					double hc = 1.98644568*Math.pow(10, -25);
					double q = 4*Math.PI* (Math.sin((theta +1 )*sortedX.getDouble(i)))*energyJ/ hc;
					
//					IDataset x= sortedX;
					
					double qA = q/(Math.pow(10, 10));
					try{
						sortedQ.set(qA, i);
						fms.get(i).setQ(qA);
					}
					catch(NullPointerException d){
					
					}
				}
			}
			catch(Exception p){
				
			}
		}
		
	}
	
	public IDataset getSplicedCurveQ(){
		return csdp.getSplicedCurveQ();
	}
	
}
