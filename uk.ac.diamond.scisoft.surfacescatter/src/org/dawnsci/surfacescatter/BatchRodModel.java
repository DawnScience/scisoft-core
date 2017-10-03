package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class BatchRodModel {
	
	
	private ArrayList<BatchRodDataTransferObject> brdtoList;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	public ArrayList<BatchRodDataTransferObject> getBrdtoList() {
		
		if(brdtoList == null){
			brdtoList = new ArrayList<>();
		}
		
		return brdtoList;
	}

	public void setBrdtoList(ArrayList<BatchRodDataTransferObject> brdto) {
		this.brdtoList = brdto;
		firePropertyChange("brdtoArray", this.brdtoList,
				this.brdtoList= brdto);
	}
	
	public void addToBrdtoList(BatchRodDataTransferObject brdto){
		if(brdtoList == null){
			brdtoList = new ArrayList<>();
		}
		
		brdtoList.add(brdto);
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
	
}
