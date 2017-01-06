/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.dataset.IDataset;

public class OneDFittingModel extends AbstractOperationModel {
	
	private double[] trackerCoordinates = {100,100,110,100,110,100,110,110};
	private int loopNo = 0;
	private IDataset input;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	
	
	
	@OperationModelField(label="Tracker Reset", hint = "Reset Tracker?" )
	private int trackerReset = 0;
	
	@OperationModelField(label="Boundary Box Direction", hint = "X or Y direction?" )
	private AnalaysisMethodologies.Methodology  direction = Methodology.Y;
	
	
	public void setInput(IDataset input1){
		input = input1;
	}
	
	public IDataset getInput(){
		return input;
	}
	
	
	public int getTrackerReset() {
		return trackerReset;
	}

	public void setTrackerReset(int trackerReset) {
		firePropertyChange("trackerReset", this.trackerReset, this.trackerReset= trackerReset);
	}

	@OperationModelField(label="Boundary Box size (pixels)", hint = "Size of the boundary box used for background calculation" )
	private int boundaryBox = 20;

	private RectangularROI box = new RectangularROI(100d, 100d, 10d, 10d, 0d);
	
	public int getBoundaryBox() {
		return boundaryBox;
	}

	public void setBoundaryBox(int boundaryBox) {
		firePropertyChange("boundaryBox", this.boundaryBox, this.boundaryBox = boundaryBox);
	}

	public RectangularROI getBox() {
		return box;
	}
	
	public void setBox(RectangularROI box) {
		firePropertyChange("box", this.box, this.box = box);
	}
	
	@OperationModelField(label="Fit power", hint = "Fit power" )
	private FitPower fitPower = FitPower.THREE;
	
	public FitPower getFitPower() {
		return fitPower;
	}

	public void setFitPower(FitPower fitPower) {
		firePropertyChange("fitPower", this.fitPower, this.fitPower= fitPower);
	}
	
	public void setFitPower(int fitPower1) {
		FitPower fitPower = AnalaysisMethodologies.toFitPower(fitPower1);
		firePropertyChange("fitPower", this.fitPower, this.fitPower= fitPower);
	}

	public void setTrackerCoordinates(double[] coords){
		trackerCoordinates = coords;
		
	}
	
	public double[] getTrackerCoordinates(){
		return trackerCoordinates;
	}

	public int getLoopNo() {
		return loopNo;
	}

	public void setLoopNo(int in) {
		loopNo = in;
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
	
	
	@OperationModelField(label="LenPt", hint = "LenPt" )
	private int[][] LenPt;
	
	public int[][] getLenPt() {
		return LenPt;
	}

	public void setLenPt(int[][] lenPt) {
		LenPt = lenPt;
	}

	@OperationModelField(label="initialLenPt", hint = "initialLenPt" )
	private int[][] initialLenPt;
	
	public int[][] getInitialLenPt() {
		return initialLenPt;
	}

	public void setInitialLenPt(int[][] initialLenPt) {
		this.initialLenPt = initialLenPt;
	}

	public Methodology getDirection() {
		return direction;
	}

	public void setDirection(Methodology direction) {
		firePropertyChange("direction", this.direction, this.direction = direction);
	}
	
	
}

//TEST
