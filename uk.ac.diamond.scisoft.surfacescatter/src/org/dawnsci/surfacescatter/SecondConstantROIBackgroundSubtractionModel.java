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
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.swt.widgets.Composite;

public class SecondConstantROIBackgroundSubtractionModel extends AbstractOperationModel {
	
	private double[] trackerCoordinates = {100,100,110,100,110,100,110,110};
	private int loopNo = 0;
	private IDataset input;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private IRectangularROI initialBackgroundROI = new RectangularROI(10,10,50,50,0);
	private int[][] backgroundLenPt;
	
	public int[][] getBackgroundLenPt() {
		return backgroundLenPt;
	}

	public void setBackgroundLenPt(int[][] backgroundLenPt) {
		this.backgroundLenPt = backgroundLenPt;
	}
	
	public IRectangularROI getInitialBackgroundROI() {
		return initialBackgroundROI;
	}

	public void setInitialBackgroundROI(IRectangularROI initialBackgroundROI) {
		this.initialBackgroundROI = initialBackgroundROI;
	}

	@OperationModelField(label="backgroundROI", hint = "backgroundROI?" )
	private IRectangularROI backgroundROI = new RectangularROI(10,10,50,50,0);;
	
	public IRectangularROI getBackgroundROI() {
		return backgroundROI;
	}

	public void setBackgroundROI(IRectangularROI backgroundROI) {
		IRectangularROI bounds = backgroundROI.getBounds();
		int[] len = bounds.getIntLengths();
		int[] pt = bounds.getIntPoint();
		int[][] lenpt = new int[2][];
		lenpt[0]=len;
		lenpt[1]=pt;
		this.setBackgroundLenPt(lenpt);
		firePropertyChange("backgroundROI", this.backgroundROI, this.backgroundROI = backgroundROI);
	}
	
	
	@OperationModelField(label="Tracker Reset", hint = "Reset Tracker?" )
	private int trackerReset = 0;
	
	
	@OperationModelField(label="First PlottingSystem", hint = "First PlottingSystem? (pS)" )
	private IPlottingSystem<Composite> plottingSystem = null;
	
	
	public IPlottingSystem<Composite> getPlottingSystem() {
		return plottingSystem;
	}

	public void setPlottingSystem(IPlottingSystem<Composite> plottingSystem) {
		firePropertyChange("plottingSystem", this.plottingSystem, this.plottingSystem = plottingSystem);
	}
	
	@OperationModelField(label="Second PlottingSystem", hint = "Second PlottingSystem? (ssvspS)" )
	private IPlottingSystem<Composite> sPlottingSystem = null;
	
	
	public IPlottingSystem<Composite> getSPlottingSystem() {
		return sPlottingSystem;
	}

	public void setSPlottingSystem(IPlottingSystem<Composite> plottingSystem) {
		firePropertyChange("sPlottingSystem", this.sPlottingSystem, this.sPlottingSystem = plottingSystem);
	}
	

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

	
	
	
}

//TEST
