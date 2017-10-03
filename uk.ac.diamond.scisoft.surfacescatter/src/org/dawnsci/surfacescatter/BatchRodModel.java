package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class BatchRodModel {

	private ArrayList<BatchRodDataTransferObject> brdtoList;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private boolean poke = true;
	private boolean updateControl =true;
	private String nxsFolderPath;

	public ArrayList<BatchRodDataTransferObject> getBrdtoList() {

		if (brdtoList == null) {
			brdtoList = new ArrayList<>();
		}

		return brdtoList;
	}

	public void setBrdtoList(ArrayList<BatchRodDataTransferObject> brdto) {
		this.brdtoList = brdto;
		firePropertyChange("brdtoArray", this.brdtoList, this.brdtoList = brdto);
	}

	public void addToBrdtoList(BatchRodDataTransferObject brdto) {
		if (brdtoList == null) {
			brdtoList = new ArrayList<>();
		}

		brdtoList.add(brdto);

		setPoke(!poke);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void removeElementsNotInList(ArrayList<String> itemsToKeepList) {

		updateControl =false;
		
		for (BatchRodDataTransferObject b : brdtoList) {
			boolean notGood = true;

			for (String s : itemsToKeepList) {

				if (s.equals(b.getRodName())) {
					notGood = false;
				}
			}
			if (notGood) {
				brdtoList.remove(b);
			}
		}
		
		updateControl =true;
		setPoke(!poke);

	}

	public void setPoke(boolean poke) {
		firePropertyChange("poke", this.poke, this.poke = poke);

	}

	public boolean isUpdateControl() {
		return updateControl;
	}

	public void setUpdateControl(boolean updateControl) {
		this.updateControl = updateControl;
	}

	public String getNxsFolderPath() {
		return nxsFolderPath;
	}

	public void setNxsFolderPath(String nxsFolderPath) {
		this.nxsFolderPath = nxsFolderPath;
	}

}