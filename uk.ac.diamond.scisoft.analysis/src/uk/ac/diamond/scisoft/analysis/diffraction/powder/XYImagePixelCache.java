/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.Arrays;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;

public class XYImagePixelCache implements IPixelIntegrationCache {

	private Dataset x;
	private Dataset y;
	private double[] xRange;
	private double[] yRange;
	private int nX;
	private int nY;
	private Dataset xAxis;
	private Dataset yAxis;
	
	
	public XYImagePixelCache(Dataset xd, Dataset yd, double[] xRange, double[] yRange, int nX, int nY) {
		this.x = xd;
		this.y = yd;
		double shiftx = (xRange[1]- xRange[0])/(2*nX);
		double shifty = (xRange[1]- xRange[0])/(2*nX);
		this.xRange = xRange.clone();
		Arrays.sort(this.xRange);
		this.xRange[0] -= shiftx;
		this.xRange[1] += shiftx;
		this.yRange = yRange.clone();
		Arrays.sort(this.yRange);
		this.yRange[0] -= shifty;
		this.yRange[1] += shifty;
		this.nX = nX;
		this.nY = nY;
		this.xAxis = calculateAxis(nX, xRange);
		this.yAxis = calculateAxis(nY, yRange);
	}
	
	
	@Override
	public Dataset[] getXAxisArray() {
		return new Dataset[]{x};
	}

	@Override
	public Dataset[] getYAxisArray() {
		return new Dataset[]{y};
	}

	@Override
	public double getXBinEdgeMax() {
		return xRange[1];
	}

	@Override
	public double getXBinEdgeMin() {
		return xRange[0];
	}

	@Override
	public double getYBinEdgeMax() {
		return yRange[1];
	}

	@Override
	public double getYBinEdgeMin() {
		return yRange[0];
	}

	@Override
	public int getNumberOfBinsXAxis() {
		return nX;
	}

	@Override
	public int getNumberOfBinsYAxis() {
		return nY;
	}

	@Override
	public double[] getYAxisRange() {
		return yRange;
	}

	@Override
	public double[] getXAxisRange() {
		return xRange;
	}

	@Override
	public Dataset getXAxis() {
		return xAxis;
	}

	@Override
	public Dataset getYAxis() {
		return yAxis;
	}

	@Override
	public boolean isPixelSplitting() {
		return false;
	}

	@Override
	public boolean isTo1D() {
		return false;
	}
	
	private static Dataset calculateAxis(int nBins, double[] binRange){
		
//		double shift = (binRange[1]- binRange[0])/(2*nBins);
//		return DatasetFactory.createLinearSpace(DoubleDataset.class, binRange[0]-shift, binRange[1]+shift, nBins + 1);
		
		return DatasetFactory.createLinearSpace(DoubleDataset.class, binRange[0], binRange[1], nBins);
	}


	@Override
	public boolean sanitise() {
		return false;
	}


	@Override
	public boolean provideLookup() {
		return true;
	}
}
