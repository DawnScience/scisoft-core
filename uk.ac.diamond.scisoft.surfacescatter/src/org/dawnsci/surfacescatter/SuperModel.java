package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.january.dataset.Dataset;
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
	private TreeMap sortedImages;
	private Dataset[] images;

	
	
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
	
}
