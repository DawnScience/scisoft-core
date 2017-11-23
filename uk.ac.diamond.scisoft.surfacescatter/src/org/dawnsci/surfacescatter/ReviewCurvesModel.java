package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ReviewCurvesModel {

	private ArrayList<CurveStitchDataPackage> csdpList;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private CurveStitchDataPackage csdpLatest;
	private boolean poke;

	public ArrayList<CurveStitchDataPackage> getCsdpList() {
		if (csdpList == null) {
			csdpList = new ArrayList<CurveStitchDataPackage>();
		}
		return csdpList;
	}

	public void setCsdpList(ArrayList<CurveStitchDataPackage> csdpList) {
		this.csdpList = csdpList;
		firePropertyChange("csdpList", this.csdpList, this.csdpList = csdpList);
	}

	public void addToCsdpList(CurveStitchDataPackage csdp) {

		if (csdpList == null) {
			csdpList = new ArrayList<CurveStitchDataPackage>();
			csdpList.add(csdp);
		}

		else if (csdpList.size() == 0) {
			csdpList.add(csdp);
		}

		else {

			csdpList.add(csdp);

		}

		setCsdpLatest(csdp);
		setPoke(!poke);
	}

	public void removeFromCsdpList(CurveStitchDataPackage csdp) {

		boolean changed = false;

		if (csdpList != null) {
			ArrayList<CurveStitchDataPackage> csdpListCopy = new ArrayList<CurveStitchDataPackage>();

			for (int h = 0; h < csdpList.size(); h++) {
				if (csdpList.size() > 0) {
					if (!csdp.getRodName().equals(csdpList.get(h).getRodName())) {
						csdpListCopy.add(csdpList.get(h));
					} else {
						changed = true;
					}
				}
			}

			if (changed) {
				firePropertyChange("csdpList", this.csdpList, this.csdpList = csdpListCopy);
			}
		}
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

	public CurveStitchDataPackage getCsdpLatest() {
		return csdpLatest;
	}

	public void setCsdpLatest(CurveStitchDataPackage csdpLatest) {

		firePropertyChange("csdpLatest", this.csdpLatest, this.csdpLatest = csdpLatest);
		setPoke(!poke);
	}

	public boolean isPoke() {
		return poke;
	}

	public void setPoke(boolean poke) {

		firePropertyChange("poke", this.poke, this.poke = poke);
	}

	public CurveStitchDataPackage getCurveStitchDataPackage(String in) {

		for (CurveStitchDataPackage c : csdpList) {
			if (c.getRodName().equals(in))
				return c;
		}

		return null;
	}

}
