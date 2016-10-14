package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.january.dataset.IDataset;

public class SuperModel {

	private String[] filepaths;
	private int selection = 0;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private IDataset splicedCurveX;
	private IDataset splicedCurveY;
	private IDataset splicedCurveYFhkl;
	private int correctionSelection = 0;

	public String[] getFilepaths() {
		return filepaths;
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
	
}
