package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;


public class OutputCurvesDataPackage {
	
	private int noOfDats;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private ArrayList<Double> yList;
	private ArrayList<Double> yListError;
	private ArrayList<Double> yListFhkl;
	private ArrayList<Double> yListFhklError;
	private ArrayList<Double> yListRawIntensity;
	private ArrayList<Double> yListRawIntensityError;
	
	
	private ArrayList<IDataset> outputDatArray;
	private ArrayList<IDataset> backgroundDatArray;
	
	
	private ArrayList<ArrayList<Double>> yListForEachDat;
	private ArrayList<ArrayList<Double>> yListErrorForEachDat;
	private ArrayList<ArrayList<Double>> yListFhklForEachDat;
	private ArrayList<ArrayList<Double>> yListFhklErrorForEachDat;
	private ArrayList<ArrayList<Double>> yListRawIntensityForEachDat;
	private ArrayList<ArrayList<Double>> yListRawIntensityErrorForEachDat;
	
	
	
	public void resetAll(){
		
		this.setyList(null);
		this.setyListError(null);
		
		this.setyListFhkl(null);
		this.setyListFhklError(null);
		
		this.setyListRawIntensity(null);
		this.setyListRawIntensityError(null);
		
		outputDatArray =null;
		backgroundDatArray = null;
		
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
	
	
	
	@SuppressWarnings("unchecked")
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
	
	public ArrayList<IDataset> getOutputDatArray() {
		return outputDatArray;
	}

	public void setOutputDatArray(ArrayList<IDataset> outputDatArray) {
		this.outputDatArray = outputDatArray;
	}

	
	
	public void addToYListForEachDat(int n,
									 int m,
								     int l, 
								     int k, 
								     double result){
	
		yListForEachDat = addDoubleToResultsList(n,
												 m,
			     								 l, 
			     								 k, 
			     								 result,
			     								 yListForEachDat);
		
		addToYListErrorForEachDat(n,
								  m,
								  l, 
								  k, 
								  Math.pow(result, 0.5));
	
	}
	
	public void addToYListErrorForEachDat(int n,
										  int m,
									      int l, 
									      int k, 
									      double result){

		yListErrorForEachDat = addDoubleToResultsList(n,
													  m,
													  l, 
													  k, 
													  result,
													  yListErrorForEachDat);

	}

	
	public void addToYListFhklForEachDat(int n,
										 int m,
		     							 int l, 
		     							 int k, 
		     							 double result){

		yListFhklForEachDat = addDoubleToResultsList(n,
													 m,
													 l, 
													 k, 
													 result,
													 yListFhklForEachDat);
		
		
		addToYListFhklErrorForEachDat(n,
									  m,
									  l, 
									  k, 
									  Math.pow(result, 0.5));
	
	}

	public void addToYListFhklErrorForEachDat(int n,
											  int m,
											  int l, 
											  int k, 
											  double result){

		yListFhklErrorForEachDat = addDoubleToResultsList(n,
														  m,
														  l, 
														  k, 
														  result,
														  yListFhklErrorForEachDat);

	}

	
	public void addToYListRawForEachDat(int n, 
										int m,
										int l, 
										int k, 
										double result){

		yListRawIntensityForEachDat = addDoubleToResultsList(n,
													 m,
													 l, 
													 k, 
													 result,
													 yListRawIntensityForEachDat);
		
		
		addToYListRawErrorForEachDat(n,
									 m,
									 l, 
									 k, 
									 Math.pow(result, 0.5));
	
	}

	public void addToYListRawErrorForEachDat(int n, 
											 int m,      
											 int l, 
											 int k, 
											 double result){

		yListRawIntensityErrorForEachDat = addDoubleToResultsList(n,
														  m,
														  l, 
														  k, 
														  result,
														  yListRawIntensityErrorForEachDat);

	}

	
	public ArrayList<ArrayList<Double>> addDoubleToResultsList(int n, //number of the Dat,
															   int m, //total number of  Dats
															   int l, //total number of images in the chosen Dat
															   int k, // position in the dat
															   double result, //what we want to put in
															   ArrayList<ArrayList<Double>> resultList0){
		
		
		
		if (resultList0==null || resultList0.isEmpty()){
			
			resultList0 = new ArrayList<ArrayList<Double>>();
			
			for(int y= 0; y<m; y++){
				resultList0.add(new ArrayList<Double>());
			}
			
			
			if(resultList0.get(n) == null){ 
				resultList0.set(n, new ArrayList<>());
			}
			
			for (int i = 0; i < l; i++) {		
				resultList0.get(n).add(-10000000000.0);
			}
		}
		
		
		if(resultList0.get(n).size()<l){
			for (int i = 0; i < l; i++) {		
				resultList0.get(n).add(-10000000000.0);
			}
		}
		ArrayList<ArrayList<Double>> resultList = (ArrayList<ArrayList<Double>>) resultList0.clone();
		
		resultList.get(n).set(k, result);
		
		return  resultList;
	}
	
	

	public ArrayList<ArrayList<Double>> getyListForEachDat() {
		return yListForEachDat;
	}

	public void setyListForEachDat(ArrayList<ArrayList<Double>> yListForEachDat) {
		this.yListForEachDat = yListForEachDat;
	}

	public ArrayList<ArrayList<Double>> getyListErrorForEachDat() {
		return yListErrorForEachDat;
	}

	public void setyListErrorForEachDat(ArrayList<ArrayList<Double>> yListErrorForEachDat) {
		this.yListErrorForEachDat = yListErrorForEachDat;
	}

	public ArrayList<ArrayList<Double>> getyListFhklForEachDat() {
		return yListFhklForEachDat;
	}

	public void setyListFhklForEachDat(ArrayList<ArrayList<Double>> yListFhklForEachDat) {
		this.yListFhklForEachDat = yListFhklForEachDat;
	}

	public ArrayList<ArrayList<Double>> getyListFhklErrorForEachDat() {
		return yListFhklErrorForEachDat;
	}

	public void setyListFhklErrorForEachDat(ArrayList<ArrayList<Double>> yListFhklErrorForEachDat) {
		this.yListFhklErrorForEachDat = yListFhklErrorForEachDat;
	}

	public ArrayList<ArrayList<Double>> getyListRawIntensityForEachDat() {
		return yListRawIntensityForEachDat;
	}

	public void setyListRawIntensityForEachDat(ArrayList<ArrayList<Double>> yListRawIntensityForEachDat) {
		this.yListRawIntensityForEachDat = yListRawIntensityForEachDat;
	}

	public ArrayList<ArrayList<Double>> getyListRawIntensityErrorForEachDat() {
		return yListRawIntensityErrorForEachDat;
	}

	public void setyListRawIntensityErrorForEachDat(ArrayList<ArrayList<Double>> yListRawIntensityErrorForEachDat) {
		this.yListRawIntensityErrorForEachDat = yListRawIntensityErrorForEachDat;
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}
	
	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
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
		
		ArrayList<ArrayList<Double>> output = dataArrayListManager(yListRawIntensity,
															         yListRawIntensityError,
															         l,
															         k,
															         y);
		
		yListRawIntensity = output.get(0);
		yListRawIntensityError = output.get(1);
		
		firePropertyChange("yListRawIntensity", this.yListRawIntensity,
				this.yListRawIntensity= yListRawIntensity);
		
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
				  yListFhkl.add(-10000000000.0);
				}
		}
		
		if (yListFhkl.size() == 0){
			yListFhkl = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListFhkl.add(-10000000000.0);
				}
		}
		
		
		if (yListFhklError==null){
			yListFhklError = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListFhklError.add(-10000000000.0);
				}
		}
		
		if (yListFhklError.size() == 0){
			yListFhklError = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListFhklError.add(-10000000000.0);
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
				  yList.add(-10000000000.0);
				}
		}
		
		if (yList.size() == 0){
			yList = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yList.add(-10000000000.0);
				}
		}
		
		if (yListError==null){
			yListError = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListError.add(-10000000000.0);
				}
		}
		

		if (yListError.size() == 0){
			yListError = new ArrayList<Double>();
			for (int i = 0; i < l; i++) {
				  yListError.add(-10000000000.0);
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

	public ArrayList<Double> getyList() {
		return yList;
	}

	public void setyList(ArrayList<Double> yList) {
		this.yList = yList;
	}

	public ArrayList<Double> getyListError() {
		return yListError;
	}

	public void setyListError(ArrayList<Double> yListError) {
		this.yListError = yListError;
	}

	public ArrayList<Double> getyListFhkl() {
		return yListFhkl;
	}

	public ArrayList<Double> getyListFhklError() {
		return yListFhklError;
	}

	public ArrayList<Double> getyListRawIntensity() {
		return yListRawIntensity;
	}

	public void setyListRawIntensity(ArrayList<Double> yListRawIntensity) {
		this.yListRawIntensity = yListRawIntensity;
	}

	public ArrayList<Double> getyListRawIntensityError() {
		return yListRawIntensityError;
	}

	public void setyListRawIntensityError(ArrayList<Double> yListRawIntensityError) {
		this.yListRawIntensityError = yListRawIntensityError;
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
	
	public static ArrayList<ArrayList<Double>> dataArrayListManager(ArrayList<Double> dataArray,
			ArrayList<Double> dataArrayError,
			int l, 
			int k, 
			double y){

		if (dataArray==null){
			dataArray = new ArrayList<Double>();
			
			for (int i = 0; i < l; i++) {
			}
		}
		
		if (dataArray.size() == 0){
			dataArray = new ArrayList<Double>();
			
			for (int i = 0; i < l; i++) {
				dataArray.add(-10000000000.0);
			}
		}
		
		if (dataArrayError==null){
			dataArrayError = new ArrayList<Double>();
			
			for (int i = 0; i < l; i++) {
				dataArrayError.add(-10000000000.0);
			}
		}
		
		if (dataArrayError.size() == 0){
			dataArrayError = new ArrayList<Double>();
		
			for (int i = 0; i < l; i++) {
				dataArrayError.add(-10000000000.0);
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
		
		ArrayList<ArrayList<Double>> output = new ArrayList<>();
		
		output.add(yList1);
		output.add(yList2);
		
		return output;
		
	}

	public int getNoOfDats() {
		return noOfDats;
	}

	public void setNoOfDats(int noOfDats) {
		this.noOfDats = noOfDats;
	}
	
}
