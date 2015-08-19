/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

public interface IPixelIntegrationCache {

	public Dataset[] getXAxisArray();
	
	public Dataset[] getYAxisArray();
	
	double getXBinEdgeMax();
	
	double getXBinEdgeMin();
	
	double getYBinEdgeMax();
	
	double getYBinEdgeMin();
	
	int getNumberOfBinsXAxis();
	
	int getNumberOfBinsYAxis();
	
	double[] getYAxisRange();
	
	double[] getXAxisRange();
	
	Dataset getXAxis();
	
	Dataset getYAxis();
	
	boolean isPixelSplitting();
	
	boolean isTo1D();
	
	boolean sanitise();
	
	boolean provideLookup();
	
}
