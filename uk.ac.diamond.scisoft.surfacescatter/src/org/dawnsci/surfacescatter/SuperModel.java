package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.TreeMap;
import org.dawnsci.surfacescatter.MethodSettingEnum.MethodSetting;
import org.dawnsci.surfacescatter.ProcessingMethodsEnum.ProccessingMethod;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;

public class SuperModel {

	private String[] filepaths;
	private int selection = 0;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private IDataset splicedCurveX;
	private IDataset splicedCurveY;
	private IDataset splicedCurveYFhkl;
	private MethodSetting correctionSelection = MethodSetting.SXRD;
	private IDataset splicedCurveYError;
	private IDataset splicedCurveYFhklError;
	private IDataset splicedCurveYErrorMax;
	private IDataset splicedCurveYErrorMin;
	private IDataset splicedCurveYFhklErrorMax;
	private IDataset splicedCurveYFhklErrorMin;
	private ILazyDataset imageStack;
	private Dataset sortedX; // this the scanned variable, e.g. l or qdcd
	private Dataset nullImage;
	@SuppressWarnings("rawtypes")
	private TreeMap sortedImages;
	private ILazyDataset[] images;
	private int[] filepathsSortedArray;
	private int sliderPos;
	private ArrayList<Double> xList;
	private ArrayList<Double> yList;
	private ArrayList<Double> yListError;
	private ArrayList<Double> yListFhkl;
	private ArrayList<Double> yListFhklError;
	private ArrayList<Double> yListRawIntensity;
	private ArrayList<Double> yListRawIntensityError;
	private ArrayList<IDataset> outputDatArray;
	private ArrayList<IDataset> backgroundDatArray;
	private ArrayList<double[]> locationList; 
	private int[][] initialLenPt = new int[][] {{50, 50}, {10, 10}};
	private boolean errorDisplayFlag = true;
	private IRectangularROI backgroundROI = new RectangularROI(10,10,50,50,0);
	private int[][] backgroundLenPt;
	private RectangularROI backgroundBox;
	private IImageTracker tracker;
	private IImageTracker initialTracker;
	private ArrayList<double[]> trackerLocationList;
	private ArrayList<Double> trackerKList;
	private IDataset temporaryBackgroundHolder; // in1Background
	private Boolean trackerOn = false;
	private int[][] boxOffsetLenPt;
	private int[][] permanentBoxOffsetLenPt;
	private int[][] permanentBackgroundLenPt;
	private ArrayList<Integer> imageRefList;
	private TreeMap<Integer, Dataset> som;
	private int numberOfImages;
	private String imageFolderPath = null;
	private int startFrame;
	private ArrayList<Double> lorentzCorrection;
	private ArrayList<Double> areaCorrection;
	private ArrayList<Double> polarisation;
	private double currentPolarisationCorrection;
	private double currentLorentzCorrection;
	private double currentAreaCorrection;
	private double currentRawIntensity;
	private IDataset splicedCurveYRaw;
	private IDataset splicedCurveYRawError;
	private String saveFolder;
	private ArrayList<Double> reflectivityAreaCorrection;
	private double currentReflectivityAreaCorrection;
	private ArrayList<Double> reflectivityFluxCorrection;
	private double currentReflectivityFluxCorrection;
	private ProccessingMethod processingMethodSelection = ProcessingMethodsEnum.ProccessingMethod.AUTOMATIC;
	
	public ProccessingMethod getProcessingMethodSelection() {
		return processingMethodSelection;
	}

	public void setProcessingMethodSelection(ProcessingMethodsEnum.ProccessingMethod processingMethodSelection) {
		this.processingMethodSelection = processingMethodSelection;
	}

	public ArrayList<Double> getReflectivityFluxCorrection() {
		return reflectivityFluxCorrection;
	}

	public double getCurrentReflectivityAreaCorrection() {
		return currentReflectivityAreaCorrection;
	}

	public void setCurrentReflectivityAreaCorrection(double currentReflectivityAreaCorrection) {
		this.currentReflectivityAreaCorrection = currentReflectivityAreaCorrection;
	}

	public double getCurrentReflectivityFluxCorrection() {
		return currentReflectivityFluxCorrection;
	}

	public void setCurrentReflectivityFluxCorrection(double currentReflectivityFluxCorrection) {
		this.currentReflectivityFluxCorrection = currentReflectivityFluxCorrection;
	}

	public void setReflectivityFluxCorrection(ArrayList<Double> reflectivityFluxCorrection) {
		this.reflectivityFluxCorrection = reflectivityFluxCorrection;
	}

	public IDataset getSplicedCurveYRaw() {
		return splicedCurveYRaw;
	}

	public void setSplicedCurveYRaw(IDataset splicedCurveYRaw) {
		this.splicedCurveYRaw = splicedCurveYRaw;
	}

	public IDataset getSplicedCurveYRawError() {
		return splicedCurveYRawError;
	}

	public void setSplicedCurveYRawError(IDataset splicedCurveYRawError) {
		this.splicedCurveYRawError = splicedCurveYRawError;
	}

	public ArrayList<Double> getAreaCorrection() {
		return areaCorrection;
	}

	public void setAreaCorrection(ArrayList<Double> areaCorrection) {
		this.areaCorrection = areaCorrection;
	}


	public ArrayList<Double> getPolarisation() {
		return polarisation;
	}

	public void setPolarisation(ArrayList<Double> polarisation) {
		this.polarisation = polarisation;
	}

	public ArrayList<Double> getLorentzCorrection() {
		return lorentzCorrection;
	}

	public void setLorentzCorrection(ArrayList<Double> lorentzCorrection) {
		this.lorentzCorrection = lorentzCorrection;
	}

	public int getStartFrame() {
		return startFrame;
	}

	public void setStartFrame(int startFrame) {
		this.startFrame = startFrame;
	}

	public String getImageFolderPath() {
		return imageFolderPath;
	}

	public void setImageFolderPath(String imageFolderPath) {
		this.imageFolderPath = imageFolderPath;
	}

	public int getNumberOfImages() {
		return numberOfImages;
	}

	public void setNumberOfImages(int numberOfImages) {
		this.numberOfImages = numberOfImages;
	}

	public TreeMap<Integer, Dataset> getSom() {
		return som;
	}

	public void setSom(TreeMap<Integer, Dataset> som) {
		this.som = som;
	}

	public ArrayList<Integer> getImageRefList() {
		return imageRefList;
	}

	public void setImageRefList(ArrayList<Integer> imageRefList) {
		this.imageRefList = imageRefList;
	}

	public Boolean getTrackerOn() {
		return trackerOn;
	}

	public void setTrackerOn(Boolean trackerOn) {
		this.trackerOn = trackerOn;
	}

	public IDataset getTemporaryBackgroundHolder() {
		return temporaryBackgroundHolder;
	}

	public void setTemporaryBackgroundHolder(IDataset temporaryBackgroundHolder) {
		this.temporaryBackgroundHolder = temporaryBackgroundHolder;
	}

	public void resetTrackers(){
		tracker = null;
		initialTracker = null;
	}
	
	public int[][] getBackgroundLenPt() {
		return backgroundLenPt;
	}

	public void setBackgroundLenPt(int[][] backgroundLenPt) {
		this.backgroundLenPt = backgroundLenPt;
	}

	public RectangularROI getBackgroundBox() {
		return backgroundBox;
	}

	public void setBackgroundBox(RectangularROI backgroundBox) {
		this.backgroundBox = backgroundBox;
	}
	
	public IROI getBackgroundROI(){
		return backgroundROI;
	}
	
	public void setBackgroundROI(IROI backgroundROI){
		IRectangularROI bounds = backgroundROI.getBounds();
		int[] len = bounds.getIntLengths();
		int[] pt = bounds.getIntPoint();
		int[][] lenpt = new int[2][];
		lenpt[0]=len;
		lenpt[1]=pt;
		firePropertyChange("backgroundROI", this.backgroundROI, this.backgroundROI= (IRectangularROI) backgroundROI);
		this.setBackgroundLenPt(lenpt);
		firePropertyChange("backgroundLenPt", this.backgroundLenPt, this.backgroundLenPt= lenpt);	
	}
	
	public void resetAll(){
		
		xList =null;
		yList =null;
		yListFhkl =null;
		yListRawIntensity =null;
		outputDatArray =null;
		backgroundDatArray = null;
		trackerLocationList = null;
		trackerKList = null;
		locationList = null;
		polarisation = null;
		lorentzCorrection = null;
		areaCorrection = null;
		reflectivityAreaCorrection = null;
		reflectivityFluxCorrection = null;
		
	}
	
	public ArrayList<IDataset> getOutputDatArray() {
		return outputDatArray;
	}
	public void setOutputDatArray(ArrayList<IDataset> outputDatArray) {
		this.outputDatArray = outputDatArray;
	}
	
	public ArrayList<IDataset> getBackgroundDatArray() {
		return backgroundDatArray;
	}
	public void setBackgroundDatArray(ArrayList<IDataset> backgroundDatArray) {
		this.backgroundDatArray = backgroundDatArray;
	}
	
	public void addOutputDatArray(IDataset in){
		if (outputDatArray==null){
			outputDatArray = new ArrayList<IDataset>();
		}
		
		ArrayList<IDataset> outputDatArray1 = new ArrayList<IDataset>();
		outputDatArray1 = (ArrayList<IDataset>) outputDatArray.clone();
		outputDatArray1.add(in);
		firePropertyChange("outputDatArray", this.outputDatArray,
				this.outputDatArray= outputDatArray1);
	}
	
	public void addOutputDatArray(int l, int k, IDataset in){
		if (outputDatArray==null){
			outputDatArray = new ArrayList<IDataset>();
			for (int i = 0; i < l; i++) {
				outputDatArray.add(DatasetFactory.zeros(new int[] {2,2}));
				}
		}
		
		if (outputDatArray.size() == 0){
			outputDatArray = new ArrayList<IDataset>();
			for (int i = 0; i < l; i++) {
				outputDatArray.add(DatasetFactory.zeros(new int[] {2,2}));
				}
		}
		
		
		ArrayList<IDataset> outputDatArray1 = new ArrayList<IDataset>();
		outputDatArray1 = (ArrayList<IDataset>) outputDatArray.clone();
		outputDatArray1.set(k,in);
		firePropertyChange("outputDatArray", this.outputDatArray,
				this.outputDatArray= outputDatArray1);
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
	

	public IDataset yIDataset(){
		if (yList==null){
			yList = new ArrayList<Double>();
		}

		ArrayList<Double> yListc = (ArrayList<Double>) yList.clone();
		
		ArrayList<Double> zero = new ArrayList<Double>();
		
		zero.add(0.0);
		
		yListc.removeAll(zero);
		
		IDataset yOut = DatasetFactory.createFromList(yListc);
		return yOut;
	}
	
	public IDataset yIDatasetError(){
		if (yListError==null){
			yListError = new ArrayList<Double>();
		}

		ArrayList<Double> yListc = (ArrayList<Double>) yListError.clone();
		
		ArrayList<Double> zero = new ArrayList<Double>();
		
		zero.add(0.0);
		
		yListc.removeAll(zero);
		
		IDataset yOut = DatasetFactory.createFromList(yListc);
		return yOut;
	}
	
	public IDataset yIDatasetFhkl(){
		if (yListFhkl==null){
			yListFhkl = new ArrayList<Double>();
		}

		ArrayList<Double> yListFhklc =  (ArrayList<Double>) yListFhkl.clone();
		
		ArrayList<Double> zero = new ArrayList<Double>();
		
		zero.add(0.0);
		
		yListFhklc.removeAll(zero);
		
		IDataset yOut = DatasetFactory.createFromList(yListFhklc);
		
		return yOut;
	}
	

	public IDataset yIDatasetFhklError(){
		if (yListFhklError==null){
			yListFhklError = new ArrayList<Double>();
		}

		ArrayList<Double> yListFhklc =  (ArrayList<Double>) yListFhklError.clone();
		
		ArrayList<Double> zero = new ArrayList<Double>();
		
		zero.add(0.0);
		
		yListFhklc.removeAll(zero);
		
		IDataset yOut = DatasetFactory.createFromList(yListFhklc);
		
		return yOut;
	}

	
	public IDataset xIDataset(){
		if (xList==null){
			xList = new ArrayList<Double>();
		}
		
		ArrayList<Double> xListc = (ArrayList<Double>) xList.clone();
		
		ArrayList<Double> zero = new ArrayList<Double>();
		
		zero.add(0.0);
		
		xListc.removeAll(zero);
		
		IDataset xOut = DatasetFactory.createFromList(xListc);
		
		return xOut;
	}

	public ArrayList<Double> getyList() {
		return yList;
	}
	
	public void setyList(ArrayList<Double> yList) {
		this.yList = yList;
		yListError = new ArrayList<Double>();
		
		for(int i = 0; i <yList.size(); i++){
			yListError.add(Math.sqrt(yList.get(i)));
		}
		
		firePropertyChange("yList", this.yList,
				this.yList= yList);
	}
	
	public ArrayList<Double> getxList() {
		return xList;
		
	}
	
	public void setxList(ArrayList<Double> xList) {
		this.xList = xList;
		firePropertyChange("xList", this.xList,
				this.xList= xList);
	}
	
	public void addyList(double y){
		if (yList==null){
			yList = new ArrayList<Double>();
		}
		if (yListError==null){
			yListError = new ArrayList<Double>();
		}

		ArrayList<Double> yList1 = new ArrayList<Double>();
		yList1 = (ArrayList<Double>) yList.clone();
		yList1.add(y);
		
		ArrayList<Double> yListError2 = new ArrayList<Double>();
		yListError2 = (ArrayList<Double>) yListError.clone();
		yListError2.add(Math.sqrt(y));
		
		yListError = yListError2;
		
		firePropertyChange("yList", this.yList,
				this.yList= yList1);
	}
	
	public void addyList(int l, int k, double y){
		
		if (yList==null){
			yList = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yList.add(0.0);
				}
		}
		
		if (yList.size() == 0){
			yList = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yList.add(0.0);
				}
		}
		
		if (yListError==null){
			yListError = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListError.add(0.0);
				}
		}
		

		if (yListError.size() == 0){
			yListError = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListError.add(0.0);
				}
		}
		
		
		ArrayList<Double> yList2 = new ArrayList<Double>();
		
		yList2 = (ArrayList<Double>) yListError.clone();
		yList2.set(k,Math.sqrt(y));
		
		yListError = yList2;
		
		ArrayList<Double> yList1 = new ArrayList<Double>();
		yList1 = (ArrayList<Double>) yList.clone();
		yList1.set(k, y);
		
		firePropertyChange("yList", this.yList,
				this.yList= yList1);
	}
	
	public void addLorentz(double y){
		if (lorentzCorrection==null){
			lorentzCorrection= new ArrayList<Double>();
		}
		
		ArrayList<Double> lorentzCorrection1 = new ArrayList<Double>();
		lorentzCorrection1 = (ArrayList<Double>) lorentzCorrection.clone();
		lorentzCorrection1.add(y);
		
	
		firePropertyChange("lorentzCorrection", this.lorentzCorrection,
				this.lorentzCorrection= lorentzCorrection1);
	}
	
	public void addLorentz(int l, int k, double y){
		
		if (lorentzCorrection==null){
			lorentzCorrection= new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				lorentzCorrection.add(0.0);
				}
		}
		
		if (lorentzCorrection.size() == 0){
			lorentzCorrection = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				lorentzCorrection.add(0.0);
				}
		}
				
		ArrayList<Double> lorentzCorrection1 = new ArrayList<Double>();
		lorentzCorrection1 = (ArrayList<Double>) lorentzCorrection.clone();
		lorentzCorrection1.set(k, y);
		
		firePropertyChange("lorentzCorrection", this.lorentzCorrection,
				this.lorentzCorrection= lorentzCorrection1);
	}
	
	public void addPolarisation(double y){
		if (polarisation==null){
			polarisation= new ArrayList<Double>();
		}
		
		ArrayList<Double> polarisation1 = new ArrayList<Double>();
		polarisation1 = (ArrayList<Double>) polarisation.clone();
		polarisation1.add(y);
		
	
		firePropertyChange("polarisation", this.polarisation,
				this.polarisation= polarisation1);
	}
	
	public void addPolarisation(int l, int k, double y){
		
		if (polarisation==null){
			polarisation= new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				polarisation.add(0.0);
				}
		}
		
		if (polarisation.size() == 0){
			polarisation = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				polarisation.add(0.0);
				}
		}
				
		ArrayList<Double> polarisation1 = new ArrayList<Double>();
		polarisation1 = (ArrayList<Double>) polarisation.clone();
		polarisation1.set(k, y);
		
		firePropertyChange("polarisation", this.polarisation,
				this.polarisation= polarisation1);
	}
	
	public void addAreaCorrection(double y){
		if (areaCorrection==null){
			areaCorrection= new ArrayList<Double>();
		}
		
		ArrayList<Double> areaCorrection1 = new ArrayList<Double>();
		areaCorrection1 = (ArrayList<Double>) areaCorrection.clone();
		areaCorrection1.add(y);
		
	
		firePropertyChange("areaCorrection", this.areaCorrection,
				this.areaCorrection= areaCorrection1);
	}
	
	public void addAreaCorrection(int l, int k, double y){
		
		if (areaCorrection==null){
			areaCorrection= new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				areaCorrection.add(0.0);
				}
		}
		
		if (areaCorrection.size() == 0){
			areaCorrection = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				areaCorrection.add(0.0);
				}
		}
				
		ArrayList<Double> areaCorrection1 = new ArrayList<Double>();
		areaCorrection1 = (ArrayList<Double>) areaCorrection.clone();
		areaCorrection1.set(k, y);
		
		firePropertyChange("areaCorrection", this.areaCorrection,
				this.areaCorrection= areaCorrection1);
	}
		
	public void yListReset(){
		
		yList = null;
		yListFhkl = null;
		outputDatArray = null;
		
	}
	
	public void addyListFhkl(double y){
		
		if (yListFhkl==null){
			yListFhkl = new ArrayList<Double>();
		}
		
		if (yListFhklError==null){
			yListFhklError = new ArrayList<Double>();
		}

		ArrayList<Double> yListFhklError1 = new ArrayList<Double>();
		yListFhklError1 = (ArrayList<Double>) yListFhklError.clone();
		yListFhklError1.add(Math.sqrt(y));
		
		yListFhklError = yListFhklError1;
		
		ArrayList<Double> yList1 = new ArrayList<Double>();
		yList1 = (ArrayList<Double>) yListFhkl.clone();
		yList1.add(y);
		
		firePropertyChange("yListFhkl", this.yListFhkl,
				this.yListFhkl= yList1);
	}
	
	public void addyListFhkl(int l, int k, double y){
		
		if (yListFhkl==null){
			yListFhkl = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListFhkl.add(0.0);
				}
		}
		
		if (yListFhkl.size() == 0){
			yListFhkl = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListFhkl.add(0.0);
				}
		}
		
		
		if (yListFhklError==null){
			yListFhklError = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListFhklError.add(0.0);
				}
		}
		
		if (yListFhklError.size() == 0){
			yListFhklError = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListFhklError.add(0.0);
				}
		}
		
		ArrayList<Double> yList1 = new ArrayList<Double>();
		
		yList1 = (ArrayList<Double>) yListFhkl.clone();
		yList1.set(k,y);
		
		ArrayList<Double> yList2 = new ArrayList<Double>();
		
		yList2 = (ArrayList<Double>) yListFhklError.clone();
		yList2.set(k,Math.sqrt(y));
		
		yListFhklError = yList2;
		
		firePropertyChange("yListFhkl", this.yListFhkl,
				this.yListFhkl= yList1);
	}

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
				  xList.add(0.0);
				}
		}
		
		if (xList.size() == 0){
			xList = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  xList.add(0.0);
				}
		}
		
		
		ArrayList<Double> xList1 = new ArrayList<Double>();
		xList1 = (ArrayList<Double>) xList.clone();
		xList1.set(k, x);
		firePropertyChange("xList", this.xList,
				this.xList= xList1);
	}
	
	public ArrayList<Double> getyListError() {
		return yListError;
	}

	public void setyListError(ArrayList<Double> yListError) {
		this.yListError = yListError;
	}

	public ArrayList<Double> getyListFhklError() {
		return yListFhklError;
	}

	public ArrayList<Double> getyListFhkl() {
		return yListFhkl;
	}

	public void setyListFhkl(ArrayList<Double> yListFhkl) {
		this.yListFhkl = yListFhkl;
		yListFhklError = new ArrayList<Double>();
		
		for(int i = 0; i <yListFhkl.size(); i++){
			yListFhklError.add(Math.sqrt(yListFhkl.get(i)));
		}
	}
	
	public void setyListFhklError(ArrayList<Double> yListFhklError) {
		this.yListFhklError = yListFhklError;
	}
	
	public void clearFilepaths(){
		filepaths = null;
	}
	
	public String[] getFilepaths() {
		return filepaths;
	}
	
	public void resetSplicedCurves(){
		splicedCurveX = null;
		splicedCurveY = null;
		splicedCurveYError = null;
		splicedCurveYErrorMax = null;
		splicedCurveYErrorMin = null;
		splicedCurveYFhkl = null;
		splicedCurveYFhklError = null;
		splicedCurveYFhklErrorMax = null;
		splicedCurveYFhklErrorMin = null;
	}

	public void setFilepaths(String[] filepaths) {
		this.filepaths = filepaths;
	}

	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		firePropertyChange("selection", this.selection, this.selection= selection);
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

	public IDataset getSplicedCurveX() {		
		return splicedCurveX;
	}

	public void setSplicedCurveX(IDataset splicedCurveX) {
		this.splicedCurveX = splicedCurveX;
	}

	public IDataset getSplicedCurveY() {
		splicedCurveY.setErrors(splicedCurveYError);
		return splicedCurveY;
	}

	public void setSplicedCurveY(IDataset splicedCurveY) {
		this.splicedCurveY = splicedCurveY;
	}

	public IDataset getSplicedCurveYFhkl() {
		splicedCurveYFhkl.setErrors(splicedCurveYFhklError);
		return splicedCurveYFhkl;
	}

	public void setSplicedCurveYFhkl(IDataset splicedCurveYFhkl) {
		this.splicedCurveYFhkl = splicedCurveYFhkl;
	}

	public MethodSetting getCorrectionSelection() {
		return correctionSelection;
	}

	public void setCorrectionSelection(MethodSetting correctionSelection) {
		this.correctionSelection = correctionSelection;
	}

	public IDataset getSplicedCurveYError() {
		return splicedCurveYError;
	}

	public void setSplicedCurveYError(IDataset splicedCurveYError) {
		this.splicedCurveYError = splicedCurveYError;
	}

	public IDataset getSplicedCurveYFhklError() {
		return splicedCurveYFhklError;
	}

	public void setSplicedCurveYFhklError(IDataset splicedCurveYFhklError) {
		this.splicedCurveYFhklError = splicedCurveYFhklError;
	}

	public IDataset getSplicedCurveYErrorMax() {
		return splicedCurveYErrorMax;
	}

	public void setSplicedCurveYErrorMax(IDataset splicedCurveYErrorMax) {
		this.splicedCurveYErrorMax = splicedCurveYErrorMax;
	}

	public IDataset getSplicedCurveYErrorMin() {
		return splicedCurveYErrorMin;
	}

	public void setSplicedCurveYErrorMin(IDataset splicedCurveYErrorMin) {
		this.splicedCurveYErrorMin = splicedCurveYErrorMin;
	}

	public IDataset getSplicedCurveYFhklErrorMax() {
		return splicedCurveYFhklErrorMax;
	}

	public void setSplicedCurveYFhklErrorMax(IDataset splicedCurveYFhklErrorMax) {
		this.splicedCurveYFhklErrorMax = splicedCurveYFhklErrorMax;
	}

	public IDataset getSplicedCurveYFhklErrorMin() {
		return splicedCurveYFhklErrorMin;
	}

	public void setSplicedCurveYFhklErrorMin(IDataset splicedCurveYFhklErrorMin) {
		this.splicedCurveYFhklErrorMin = splicedCurveYFhklErrorMin;
	}

	public ILazyDataset getImageStack() {
		return imageStack;
	}

	public void setImageStack(ILazyDataset imageStack) {
		this.imageStack = imageStack;
	}

	public Dataset getSortedX() {
		return sortedX;
	}

	public void setSortedX(Dataset sortedX) {
		this.sortedX = sortedX;
	}

	public Dataset getNullImage() {
		return nullImage;
	}

	public void setNullImage(Dataset nullImage) {
		this.nullImage = nullImage;
	}

	public TreeMap getSortedImages() {
		return sortedImages;
	}

	public void setSortedImages(TreeMap sortedImages) {
		this.sortedImages = sortedImages;
	}

	public ILazyDataset[] getImages() {
		return images;
	}

	public void setImages(ILazyDataset[] images) {
		this.images = images;
	}

	public int[] getFilepathsSortedArray() {
		return filepathsSortedArray;
	}

	public void setFilepathsSortedArray(int[] filepathsSortedArray) {
		this.filepathsSortedArray = filepathsSortedArray;
	}

	public int getSliderPos() {
		return sliderPos;
	}

	public void setSliderPos(int sliderPos) {
		this.sliderPos = sliderPos;
	}

	public ArrayList<double[]> getLocationList() {
		return locationList;
	}

	public void setLocationList(ArrayList<double[]> locationList) {
		this.locationList = locationList;
	}
	
	
	public void addLocationList(double[] in){
		
		if (locationList==null){
			locationList = new ArrayList<double[]>();
		}
		
		ArrayList<double[]> locationList1 = new ArrayList<double[]>();
		locationList1 = (ArrayList<double[]>) locationList.clone();
		locationList1.add(in);
		firePropertyChange("locationList", this.locationList,
				this.locationList= locationList1);
	}
	
	public void addLocationList(int k, int[] in){
		double[] inDouble = new double[in.length]; 
		for (int i = 0; i<in.length; i++){
			inDouble[i] = (double)in[i];
		}
		addLocationList(k, inDouble);
		
	}
	public void addLocationList(int k, double[] in){
		if (locationList==null){
			locationList = new ArrayList<double[]>();
		
			for (int i = 0; i < images.length; i++) {
				locationList.add(new double[] {0,0,0,0,0,0,0,0});
				}
		}
				
		ArrayList<double[]> locationList1 = new ArrayList<double[]>();
		locationList1 = (ArrayList<double[]>) locationList.clone();
		locationList1.set(k,in);
		firePropertyChange("locationList", this.locationList,
				this.locationList= locationList1);
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
		
			for (int i = 0; i < images.length; i++) {
				trackerLocationList.add(new double[] {0,0,0,0,0,0,0,0});
				}
		}
		
		if (trackerLocationList.size() == 0){
			trackerLocationList = new ArrayList<double[]>();
		
			for (int i = 0; i < images.length; i++) {
				trackerLocationList.add(new double[] {0,0,0,0,0,0,0,0});
				}
		}
		
//		System.out.println("Added tracker location at k = " + k);
		
		ArrayList<double[]> trackerLocationList1 = new ArrayList<double[]>();
		trackerLocationList1 = (ArrayList<double[]>) trackerLocationList.clone();
		trackerLocationList1.set(k,in);
		firePropertyChange("trackerLocationList", this.trackerLocationList,
				this.trackerLocationList= trackerLocationList1);
	}
	
	public void addtrackerKList(double in){
		
		if (trackerKList==null){
			trackerKList = new ArrayList<Double>();
		}
		
		ArrayList<Double> trackerKList1 = new ArrayList<Double>();
		trackerKList1 = (ArrayList<Double>) trackerKList.clone();
		trackerKList1.add(in);
		firePropertyChange("trackerKList", this.trackerKList,
				this.trackerKList= trackerKList1);
	}
	
	public void addtrackerKList(int k, double in){
		if (trackerKList==null){
			trackerKList = new ArrayList<Double>();
		
			for (int i = 0; i < images.length; i++) {
				trackerKList.add((double) 2000000);
				}
		}
		
		
		if (trackerKList.size() == 0){
			trackerKList = new ArrayList<Double>();
		
			for (int i = 0; i < images.length; i++) {
				trackerKList.add((double) 2000000);
				}
		}
		
//		System.out.println("Added tracker location at k = " + k);
		
		ArrayList<Double> trackerKList1 = new ArrayList<Double>();
		trackerKList1 = (ArrayList<Double>) trackerKList.clone();
		trackerKList1.set(k,in);
		firePropertyChange("trackerKList", this.trackerKList,
				this.trackerKList= trackerKList1);
	}
	

	public int[][] getInitialLenPt() {
		return initialLenPt;
	}

	public void setInitialLenPt(int[][] initialLenPt) {
		this.initialLenPt = initialLenPt;
	}

	public boolean isErrorDisplayFlag() {
		return errorDisplayFlag;
	}

	public void setErrorDisplayFlag(boolean errorDisplayFlag) {
		this.errorDisplayFlag = errorDisplayFlag;
	}

	public IImageTracker getTracker() {
		return tracker;
	}

	public void setTracker(IImageTracker tracker) {
		this.tracker = tracker;
	}

	public IImageTracker getInitialTracker() {
		return initialTracker;
	}

	public void setInitialTracker(IImageTracker initialTracker) {
		this.initialTracker = initialTracker;
	}

	public ArrayList<double[]> getTrackerLocationList() {
		return trackerLocationList;
	}

	public void setTrackerLocationList(ArrayList<double[]> trackerLocationList) {
		this.trackerLocationList = trackerLocationList;
	}

	public ArrayList<Double> getTrackerKList() {
		return trackerKList;
	}

	public void setTrackerKList(ArrayList<Double> trackerKList) {
		this.trackerKList = trackerKList;
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

	public double getCurrentPolarisationCorrection() {
		return currentPolarisationCorrection;
	}

	public void setCurrentPolarisationCorrection(double currentPolarisationCorrection) {
		this.currentPolarisationCorrection = currentPolarisationCorrection;
	}

	public double getCurrentLorentzCorrection() {
		return currentLorentzCorrection;
	}

	public void setCurrentLorentzCorrection(double currentLorentzCorrection) {
		this.currentLorentzCorrection = currentLorentzCorrection;
	}

	public double getCurrentAreaCorrection() {
		return currentAreaCorrection;
	}

	public void setCurrentAreaCorrection(double currentAreaCorrection) {
		this.currentAreaCorrection = currentAreaCorrection;
	}

	public double getCurrentRawIntensity() {
		return currentRawIntensity;
	}

	public void setCurrentRawIntensity(double currentRawIntensity) {
		this.currentRawIntensity = currentRawIntensity;
	}
	
	
	public void  addYListRawIntensity(double y){
		
		addToDataArray(yListRawIntensity,
					   yListRawIntensityError,
					   y);
		
		firePropertyChange("yListRawIntensity", this.yListRawIntensity,
				this.yListRawIntensity = yListRawIntensity);
		
	}
	
	
	public void  addYListRawIntensity(int l ,
									  int k,
									  double y){
		
		dataArrayListManager(yListRawIntensity,
					         yListRawIntensityError,
					         l,
					         k,
					         y);
		
		firePropertyChange("yListRawIntensity", this.yListRawIntensity,
				this.yListRawIntensity= yListRawIntensity);
		
	}
	
	
	public void addToDataArray(ArrayList<Double> dataArray,
							   ArrayList<Double> dataArrayError,
							   double y){
		
		if (dataArray==null){
			dataArray = new ArrayList<Double>();
		}
		
		if (dataArrayError==null){
			dataArrayError = new ArrayList<Double>();
		}

		ArrayList<Double> dataArrayError1 = new ArrayList<Double>();
		dataArrayError1 = (ArrayList<Double>) dataArrayError.clone();
		dataArrayError1.add(Math.sqrt(y));
		
		dataArrayError = dataArrayError1;
		
		ArrayList<Double> yList1 = new ArrayList<Double>();
		yList1 = (ArrayList<Double>) dataArray.clone();
		yList1.add(y);
		
		dataArray= yList1;

	}
	
	public void addToDataArray(ArrayList<Double> dataArray,
							   double y){

		if (dataArray==null){
			dataArray = new ArrayList<Double>();
		}
		
		ArrayList<Double> yList1 = new ArrayList<Double>();
		yList1 = (ArrayList<Double>) dataArray.clone();
		yList1.add(y);
		
		dataArray= yList1;
		
	}
	
	public static void dataArrayListManager(ArrayList<Double> dataArray,
											ArrayList<Double> dataArrayError,
											int l, 
											int k, 
											double y){
		
		if (dataArray==null){
			dataArray = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				dataArray.add(0.0);
				}
		}
		
		if (dataArray.size() == 0){
			dataArray = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				dataArray.add(0.0);
				}
		}
		
		
		if (dataArrayError==null){
			dataArrayError = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				dataArrayError.add(0.0);
				}
		}
		
		if (dataArrayError.size() == 0){
			dataArrayError = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				dataArrayError.add(0.0);
				}
		}
		
		ArrayList<Double> yList1 = new ArrayList<Double>();
		
		yList1 = (ArrayList<Double>) dataArray.clone();
		yList1.set(k,y);
		dataArray = yList1;
		
		ArrayList<Double> yList2 = new ArrayList<Double>();
		
		yList2 = (ArrayList<Double>) dataArrayError.clone();
		yList2.set(k,Math.sqrt(y));
		
		dataArrayError = yList2;

	}
	
	public static void dataArrayListManager(ArrayList<Double> dataArray,
											int l, 
											int k, 
											double y){

		if (dataArray==null){
			dataArray = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				dataArray.add(0.0);
			}
		}
		
		if (dataArray.size() == 0){
			dataArray = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				dataArray.add(0.0);
			}
		}
		
		ArrayList<Double> yList1 = new ArrayList<Double>();
		
		yList1 = (ArrayList<Double>) dataArray.clone();
		yList1.set(k,y);
		dataArray = yList1;
	}
	

	public String getSaveFolder() {
		return saveFolder;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

	public ArrayList<Double> getReflectivityAreaCorrection() {
		return reflectivityAreaCorrection;
	}

	public void setReflectivityAreaCorrection(ArrayList<Double> reflectivityAreaCorrection) {
		this.reflectivityAreaCorrection = reflectivityAreaCorrection;
	}
	
	public void  addReflectivityAreaCorrection(double y){
		
		addToDataArray(reflectivityAreaCorrection,
					   y);
		
		firePropertyChange("reflectivityAreaCorrection", this.reflectivityAreaCorrection,
				this.reflectivityAreaCorrection= reflectivityAreaCorrection);
		
	}
	
	
	public void  addReflectivityAreaCorrection(int l ,
											   int k,
											   double y){
		
		dataArrayListManager(reflectivityAreaCorrection,
					         l,
					         k,
					         y);
		
		firePropertyChange("reflectivityAreaCorrection", this.reflectivityAreaCorrection,
				this.reflectivityAreaCorrection= reflectivityAreaCorrection);
		
	}
	
	public void  addReflectivityFluxCorrection(double y){
		
		addToDataArray(reflectivityFluxCorrection,
					   y);
		
		firePropertyChange("reflectivityFluxCorrection", this.reflectivityFluxCorrection,
				this.reflectivityFluxCorrection = reflectivityFluxCorrection);
		
	}
	
	
	public void  addReflectivityFluxCorrection(int l ,
											   int k,
											   double y){
		
		dataArrayListManager(reflectivityFluxCorrection,
					         l,
					         k,
					         y);
		
		firePropertyChange("reflectivityFluxCorrection", this.reflectivityFluxCorrection,
				this.reflectivityFluxCorrection= reflectivityFluxCorrection);
		
	}

	public int[][] getPermanentBackgroundLenPt() {
		return permanentBackgroundLenPt;
	}

	public void setPermanentBackgroundLenPt(int[][] permanentBackgroundLenPt) {
		this.permanentBackgroundLenPt = permanentBackgroundLenPt;
	}
	
	
	
}
