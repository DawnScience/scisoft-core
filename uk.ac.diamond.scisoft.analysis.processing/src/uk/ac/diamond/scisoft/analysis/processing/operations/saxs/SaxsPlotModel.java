/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

// @author Tim Snow, from Tim Spain's NCD plugin

// To customise the visualisation of a standard scattering plot (I vs q)
public class SaxsPlotModel extends AbstractOperationModel {

	// Let's give the user a fixed choice on the integration range so they don't go too nuts...
	enum SaxsAnalysisPlotType {
		LOGNORM_PLOT(1),
		LOGLOG_PLOT(2),
		GUINIER_PLOT(3),
		POROD_PLOT(4),
		KRATKY_PLOT(5),
		ZIMM_PLOT(6),
		DEBYE_BUECHE_PLOT(7);

		private final int plotType;
		
		SaxsAnalysisPlotType(int plotType) {
			this.plotType = plotType;
		}
		
		public int getFoldsOfSymmetry() {
			return this.plotType;
		}
		
		@Override
		public String toString() {
			switch (this.plotType) {
				case 1:		return String.format("Log / Normal Plot");
				case 2:		return String.format("Log / Log Plot");
				case 3:		return String.format("Guinier Plot");
				case 4:		return String.format("Porod Plot");
				case 5:		return String.format("Kratky Plot");
				case 6:		return String.format("Zimm Plot");
				case 7:		return String.format("Debye Bueche Plot");
				default:	return String.format("Error!");
			}
		}
	}

	
	@OperationModelField(label = "SAXS Plot Tool", hint = "Select the SAXS plot to view")
	private SaxsAnalysisPlotType plotType = SaxsAnalysisPlotType.LOGNORM_PLOT;
	
	public SaxsAnalysisPlotType getPlotType() {
		return plotType;
	}
	
	public void setPlotType(SaxsAnalysisPlotType plotType) {
		firePropertyChange("plotType", this.plotType, this.plotType = plotType);
	}
}
