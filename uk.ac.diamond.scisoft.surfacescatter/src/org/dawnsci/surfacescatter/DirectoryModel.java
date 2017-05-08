package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class DirectoryModel {
	
	private ArrayList<ArrayList<Integer>> framesCorespondingToDats;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	

	
	
	
	public ArrayList<ArrayList<Integer>> getFramesCorespondingToDats() {
		return framesCorespondingToDats;
	}

	public void setFramesCorespondingToDats(ArrayList<ArrayList<Integer>> framesCorespondingToDats) {
		this.framesCorespondingToDats = framesCorespondingToDats;
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
