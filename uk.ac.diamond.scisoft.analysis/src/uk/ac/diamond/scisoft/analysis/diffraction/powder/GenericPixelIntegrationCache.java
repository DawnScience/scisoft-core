/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.Arrays;

import javax.vecmath.Vector2d;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IntegerDataset;

public class GenericPixelIntegrationCache implements IPixelIntegrationCache {

	Dataset e1; // Values of the output coordinates (e1,e2) on the input image grid. Two dimensional Datasets.
	Dataset e2;
	Dataset e1Axis; // Axes of the coordinates of the output grid. One dimensional Datasets
	Dataset e2Axis;
	
	static final int TL=1, T=2, TR=4, R=8, BR=16, B=32, BL=64, L=128;
	static final int[] edgesVertices = new int[] {TL, T, TR, R, BR, B, BL, L};
	static final int TLC = BL|L|TL|T|TR;
	static final int TRC = TL|T|TR|R|BR;
	static final int BRC = TR|R|BR|B|BL;
	static final int BLC = BR|B|BL|L|TL;
	static final int[] vertexExtrapolation = new int[] {TLC, TRC, BRC, BLC};
	static final int[] vertexXMod = new int[] {0, 1, 1, 0}; // indices are either 0*nx + 0 or 1*nx - 1
	static final int[] vertexYMod = new int[] {0, 0, 1, 1}; // similarly for y
	static final int NVERTEX = 4;
	
	static final int TE = TL|T|TR;
	static final int RE = TR|R|BR;
	static final int BE = BR|B|BL;
	static final int LE = BL|L|TL;
	static final int[] edgeExtrapolation = new int[] {TE, RE, BE, LE};
	
	
	boolean isReady;
	
	// Arrays for the PixelIntegrationCache interface
	Dataset e1Min; // Minimum value of the first output coordinate on the input grid.
	Dataset e1Max; // Maximum value of the first output coordinate on the input grid.
	Dataset e2Min; // Minimum value of the second output coordinate on the input grid.
	Dataset e2Max; // Minimum value of the second output coordinate on the input grid.

	Dataset e1BinEdges; // Edges of the bins on the e1 output axis
	Dataset e2BinEdges; // Edges of the bins on the e1 output axis
	
	public GenericPixelIntegrationCache() {
		e1 = null;
		e2 = null;
		e1Axis = null;
		e2Axis = null;
		isReady = false;
	}		
	
	public GenericPixelIntegrationCache(Dataset e1, Dataset e2, Dataset e1Axis, Dataset e2Axis) {
		this();
		this.e1 = e1;
		this.e2 = e2;
		this.e1Axis = e1Axis;
		this.e2Axis = e2Axis;
		
		// Consistency check
		if (e1.getShape().length == 2 && e2.getShape().length == 2 && Arrays.equals(e1.getShape(), e2.getShape())) {
			initialize();
		}
	}
	
	@Override
	public Dataset[] getXAxisArray() {
		return new Dataset[] {e1Min, e1Max};
	}

	@Override
	public Dataset[] getYAxisArray() {
		return new Dataset[] {e2Min, e2Max};
	}

	@Override
	public double getXBinEdgeMax() {
		return Math.max(getXBin0End(), getXBinNEnd());
	}

	@Override
	public double getXBinEdgeMin() {
		return Math.min(getXBin0End(), getXBinNEnd());
	}

	@Override
	public double getYBinEdgeMax() {
		return Math.max(getYBin0End(), getYBinNEnd());
	}

	@Override
	public double getYBinEdgeMin() {
		return Math.min(getYBin0End(), getYBinNEnd());
	}

	@Override
	public int getNumberOfBinsXAxis() {
		return e1Axis.getSize();
	}

	@Override
	public int getNumberOfBinsYAxis() {
		return e2Axis.getSize();
	}

	@Override
	public double[] getYAxisRange() {
		return new double[] {e2Axis.getDouble(0), e2Axis.getDouble(e2Axis.getSize()-1)};
	}

	@Override
	public double[] getXAxisRange() {
		return new double[] {e1Axis.getDouble(0), e1Axis.getDouble(e1Axis.getSize()-1)};
	}

	@Override
	public Dataset getXAxis() {
		return e1Axis;
	}

	@Override
	public Dataset getYAxis() {
		return e2Axis;
	}

	@Override
	public boolean isPixelSplitting() {
		return true;
	}

	@Override
	public boolean isTo1D() {
		return false;
	}

	@Override
	public boolean sanitise() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean provideLookup() {
		// TODO Auto-generated method stub
		return false;
	}

	private void initialize() {
		int nx = e1.getShape()[0];
		int ny = e1.getShape()[1];

		// Get the maximum/minimum coordinate values by inter-/extrapolation
		IntegerDataset e1ExtrapolationMask = DatasetFactory.zeros(e1, IntegerDataset.class);
		IntegerDataset e2ExtrapolationMask = DatasetFactory.zeros(e2, IntegerDataset.class);
		
		markLiminalExtrapolation(e1ExtrapolationMask, nx, ny);		
		markLiminalExtrapolation(e2ExtrapolationMask, nx, ny);		
		
		Vector2d minmax;
		
		e1Min = DatasetFactory.zeros(nx, ny);
		e1Max = DatasetFactory.zeros(nx, ny);
		e2Min = DatasetFactory.zeros(nx, ny);
		e2Max = DatasetFactory.zeros(nx, ny);
		
		// Loop over every data point, getting the min and max values at that point
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				minmax = polatedMinMaxValues(i, j, e1, e1ExtrapolationMask.getInt(i, j));
				e1Min.set(minmax.x, i, j);
				e1Max.set(minmax.y, i, j);

				minmax = polatedMinMaxValues(i, j, e2, e2ExtrapolationMask.getInt(i, j));
				e2Min.set(minmax.x, i, j);
				e2Max.set(minmax.y, i, j);
			}
		}
		
		isReady = true;
	}

	private void markLiminalExtrapolation(Dataset masks, int nx, int ny) {
		// Mark the edge masks
		for (int i = 1; i < nx-1; i++) {
			masks.set(TL|T|TR, i, 0);
			masks.set(BR|B|BL, i, ny-1);
		}
		for (int j = 1; j < ny-1; j++) {
			masks.set(BL|L|TL, 0, j);
			masks.set(TR|R|BR, nx-1, j);
		}
		// Mark the corner masks
		masks.set(BL|L|TL|T|TR, 0, 0);
		masks.set(TL|T|TR|R|BR, nx-1, 0);
		masks.set(TR|R|BR|B|BL, nx-1, ny-1);
		masks.set(BR|B|BL|L|TL, 0, ny-1);
	}
	
	private Vector2d polatedMinMaxValues(int i, int j, Dataset y, int extrapolate) {
		double min = y.getDouble(i, j);
		double max = y.getDouble(i, j);
		
		// For each edge or vertex, get the extrapolated value at that point
		for (int iev = 0; iev < edgesVertices.length; iev++) {
			int ev = edgesVertices[iev];
			// Extrapolation test
			if ( (ev & extrapolate) != 0)
				continue;
			double eValue = interpolate(i, j, y, iev);   
			min = Math.min(min, eValue);
			max = Math.max(max, eValue);
		}
		return new Vector2d(min, max);
	}
	
	private double interpolate(int i, int j, Dataset y, int ev) {
		final int[] deltaXArray = new int[] {-1, 0, +1, +1, +1, 0, -1, -1};
		final int[] deltaYArray = new int[] {-1, -1, -1, 0, +1, +1, +1, 0};
		final double pointWeight = 0.5;
		final double otherWeight = 0.5;

		int deltaX = deltaXArray[ev];
		int deltaY = deltaYArray[ev];
		
		double pointValue = y.getDouble(i, j);
		double otherValue = y.getDouble(i + deltaX, j + deltaY);
		
		return pointWeight * pointValue + otherWeight * otherValue; // No point in writing a function for this
	}

	private double getXBin0End() {
		return 3./2.*e1Axis.getDouble(0) - 1./2.*e1Axis.getDouble(1);
	}

	private double getXBinNEnd() {
		int nx = e1Axis.getSize();
		return 3./2.*e1Axis.getDouble(nx-1) - 1./2.*e1Axis.getDouble(nx-2);
	}

	private double getYBinNEnd() {
		int ny = e2Axis.getSize();
		return 3./2.*e2Axis.getDouble(ny-1) - 1./2.*e2Axis.getDouble(ny-2);
	}
	
	private double getYBin0End() {
		return 3./2.*e2Axis.getDouble(0) - 1./2.*e2Axis.getDouble(1);
	}
	
}
