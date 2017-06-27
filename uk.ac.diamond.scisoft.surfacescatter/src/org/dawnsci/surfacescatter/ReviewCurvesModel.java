package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ReviewCurvesModel {
	
	private ArrayList<CurveStitchDataPackage> csdpList;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private CurveStitchDataPackage csdpLatest;
	
	public ArrayList<CurveStitchDataPackage> getCsdpList() {
		return csdpList;
	}

	public void setCsdpList(ArrayList<CurveStitchDataPackage> csdpList) {
		this.csdpList = csdpList;
		firePropertyChange("csdpList", this.csdpList,
				this.csdpList= csdpList);
	}
	
	public void addToCsdpList(CurveStitchDataPackage csdp) {
		
		if(csdpList ==null){
			csdpList = new ArrayList<CurveStitchDataPackage>();
		}
		
		ArrayList<CurveStitchDataPackage> csdpListCopy = new ArrayList<CurveStitchDataPackage>();
		
		for(int h = 0; h<csdpList.size(); h++){
			csdpListCopy.add(csdpList.get(h));
		}
		
		csdpListCopy.add(csdp);
		
		setCsdpLatest(csdp);

		
		firePropertyChange("csdpList", this.csdpList,
				this.csdpList= csdpListCopy);
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

	public CurveStitchDataPackage getCsdpLatest() {
		return csdpLatest;
	}

	public void setCsdpLatest(CurveStitchDataPackage csdpLatest) {
		this.csdpLatest = csdpLatest;
	}
	
}
