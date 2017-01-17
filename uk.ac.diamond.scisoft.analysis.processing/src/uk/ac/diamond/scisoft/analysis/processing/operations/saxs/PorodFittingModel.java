/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


// @author Tim Snow


// The operation to take a region of reduced SAXS data, obtain a Porod plot and fit, as well as
// information that, ultimately, provides structural information
public class PorodFittingModel extends AbstractOperationModel {

	// Let's give the user a fixed choice on the integration range so they don't go too nuts...
	enum PlotView {
		IQ4_Q(1),
		LOG_LOG(2);
		
		private final int plotView;
		
		PlotView(int plotView) {
			this.plotView = plotView;
		}
		
		public int getPlotView() {
			return this.plotView;
		}
		
		@Override
		public String toString() {
			switch (this.plotView) {
				case 1:		return String.format("I * q^4 vs q");
				case 2:		return String.format("Log(I) vs Log(q)");
				default:	return String.format("Error!");
			}
		}
	}
		
	
	// Should we be be showing the I*q^4 plot or the log plot?
	@OperationModelField(label = "Plot style", hint = "Pick whether to display the I * q^4 vs q plot or the log(I) vs log(q) plot on screen", fieldPosition = 1)
	private PlotView plotView = PlotView.IQ4_Q;

	// Now the getters and setters
	public PlotView getPlotView() {
		return plotView;
	}

	public void setPlotView(PlotView plotView) {
		firePropertyChange("PlotView", this.plotView, this.plotView = plotView);
	}
	
	
	// Get the region of interest for where to perform the Guinier analysis
	@OperationModelField(rangevalue = RangeType.XRANGE, label = "Plot region of interest", hint = "Two q values, start and end, separated by a comma, for example 0.02,0.2. The values should match the axis. If you delete the text the whole dataset will be evaluated", fieldPosition = 2)
	private double[] porodRange = null;

	// Setting up the getters and setters along the way
	public double[] getPorodRange() {
		return porodRange;
	}
	public void setPorodRange(double [] porodRange) {
		firePropertyChange("porodRange", this.porodRange, this.porodRange = porodRange);
	}
}