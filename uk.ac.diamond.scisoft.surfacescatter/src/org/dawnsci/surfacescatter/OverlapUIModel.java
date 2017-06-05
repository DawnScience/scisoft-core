package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;

public class OverlapUIModel {
	
	private int imageNumber = 0;
	private IROI ROI;
	private int[][] lenpt;
	private IDataset currentImage;
	private ArrayList<IRectangularROI> ROIList = new ArrayList<IRectangularROI>();
	private int ROIevent=0;
	private Dataset[] output;
	private String[] filepaths1;
	private double[] attenuationFactors;
	
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);


	public IROI getROI(){
		return ROI;
	}

	public void setROI(IROI ROI) {
		//this.setLenPt(ROI);
		firePropertyChange("ROI", this.ROI, this.ROI= ROI);
		IRectangularROI bounds = ROI.getBounds();
		int[] len = bounds.getIntLengths();
		int[] pt = bounds.getIntPoint();
		int[][] lenpt = new int[2][];
		lenpt[0]=len;
		lenpt[1]=pt;
		firePropertyChange("ROI", this.ROI, this.ROI= ROI);
		firePropertyChange("lenpt", this.lenpt, this.lenpt= lenpt);
	}
	
	public void setROIList(ArrayList<IRectangularROI> ROIList) {
		firePropertyChange("ROIList", this.ROIList, this.ROIList= ROIList);	
	}
	
	public void setROIListElement(IRectangularROI ROI, int k) {
		
		
		if (ROIList.size() <= k | ROIList.size() !=0){
			ROIList.add(ROI);
			firePropertyChange("ROIevent", this.ROIevent, this.ROIevent= (ROIevent+1));
			firePropertyChange("ROIList", this.ROIList, this.ROIList= ROIList);
		}

		else {
			ROIList.add(ROI);
			firePropertyChange("ROIevent", this.ROIevent, this.ROIevent= (ROIevent+1));
			
			ROIList.set(k, ROI);
			firePropertyChange("ROIList", this.ROIList, this.ROIList= ROIList);
		}
		
		firePropertyChange("ROIevent", this.ROIevent, this.ROIevent= (ROIevent+1));
	}

	public void setROIListElementEst(IRectangularROI ROI, int k) {
	
		ROIList.set(k, ROI);
	
		firePropertyChange("ROIevent", this.ROIevent, this.ROIevent= (ROIevent+1));
		firePropertyChange("ROIList", this.ROIList, this.ROIList= ROIList);

	}
	
	public void setOutput(Dataset[] input){
		output = input;
	}
	
	public Dataset[] getOutput(){
		return output;
	}
	
	public void setFilepaths(String[] filepaths){
		filepaths1 =filepaths;
	}
	
	public String[] getFilepaths(){
		return filepaths1;
	}
	
	public IROI getROIListElement(int k){
		return ROIList.get(k);
	}
	
	public ArrayList<IRectangularROI> getROIList(){
		return ROIList;
	}
	
	public int[][] getLenPt(){
		return lenpt;
	}

	public IDataset getCurrentImage(){
		return currentImage;
	}
	
	public void setCurrentImage(IDataset currentImage){
		firePropertyChange("currentImage", this.currentImage, this.currentImage= currentImage);
	}
	
	public int getImageNumber() {
		return imageNumber;
	}

	public void setImageNumber(int imageNumber) {
		firePropertyChange("imageNumber", this.imageNumber, this.imageNumber= imageNumber);
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

	public double[] getAttenuationFactors() {
		
		if(attenuationFactors == null){
			attenuationFactors = new double[ROIList.size()];
		}
		
		return attenuationFactors;
	}

	public void setAttenuationFactors(double[] attenuationFactors) {
		this.attenuationFactors = attenuationFactors;
	}
	
	
	public void setAttenuationFactors(int pos, double val ) {
		if(attenuationFactors == null){
			attenuationFactors = new double[ROIList.size()];
		}
		attenuationFactors[pos] = val; 

	}
}
