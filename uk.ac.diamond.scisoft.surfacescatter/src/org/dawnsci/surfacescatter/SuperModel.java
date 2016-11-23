package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

public class SuperModel {

	private String[] filepaths;
	private int selection = 0;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private IDataset splicedCurveX;
	private IDataset splicedCurveY;
	private IDataset splicedCurveYFhkl;
	private int correctionSelection = 0;
	private IDataset splicedCurveYError;
	private IDataset splicedCurveYFhklError;
	private IDataset splicedCurveYErrorMax;
	private IDataset splicedCurveYErrorMin;
	private IDataset splicedCurveYFhklErrorMax;
	private IDataset splicedCurveYFhklErrorMin;
	private Dataset imageStack;
	private Dataset sortedX;
	private Dataset nullImage;
	@SuppressWarnings("rawtypes")
	private TreeMap sortedImages;
	private Dataset[] images;
	private int[] filepathsSortedArray;
	private int sliderPos;
	private ArrayList<Double> xList;
	private ArrayList<Double> yList;
	private ArrayList<Double> yListError;
	private ArrayList<Double> yListFhkl;
	private ArrayList<Double> yListFhklError;
	private ArrayList<IDataset> outputDatArray;
	private ArrayList<IDataset> backgroundDatArray;
	private ArrayList<double[]> locationList; 
	
	
	
	
	public void resetAll(){
		
		xList =null;
		yList =null;
		yListFhkl =null;
		outputDatArray =null;
		backgroundDatArray = null;
//		initialDataset = null;
//		initialLenPt = null;
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
		
		if (yListError==null){
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
		
		if (yListFhklError==null){
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
		return splicedCurveY;
	}

	public void setSplicedCurveY(IDataset splicedCurveY) {
		this.splicedCurveY = splicedCurveY;
	}

	public IDataset getSplicedCurveYFhkl() {
		return splicedCurveYFhkl;
	}

	public void setSplicedCurveYFhkl(IDataset splicedCurveYFhkl) {
		this.splicedCurveYFhkl = splicedCurveYFhkl;
	}

	public int getCorrectionSelection() {
		return correctionSelection;
	}

	public void setCorrectionSelection(int correctionSelection) {
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

	public Dataset getImageStack() {
		return imageStack;
	}

	public void setImageStack(Dataset imageStack) {
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

	public Dataset[] getImages() {
		return images;
	}

	public void setImages(Dataset[] images) {
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
}
