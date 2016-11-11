/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.Arrays;

import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;

public class SurfacePixelIntegrationCache implements IPixelIntegrationCache {

	private IDiffractionMetadata dMeta;
	
	private Dataset[] yArray;
	private Dataset[] xArray;
	
	private Dataset yAxis;
	private Dataset xAxis;
	
	private DoubleDataset binEdgesY = null;
	private DoubleDataset binEdgesX = null;
	
	private double[] xRange;
	private double[] yRange;
	
	private int[] shape;
	
	private int binsPara, binsPerp;
	
	public SurfacePixelIntegrationCache(IDiffractionMetadata dMeta, int[] shape, double pitchDegrees, double rollDegrees, int binsPara, int binsPerp) {
		this.dMeta = dMeta;
		this.shape = shape;
		
		this.binsPara = binsPara;
		this.binsPerp = binsPerp;
		initialize(Math.toRadians(pitchDegrees), Math.toRadians(rollDegrees));
		
	}
	
	private void initialize(double pitch, double roll) {
		SurfaceQ sq = new SurfaceQ();
		
		//perpendicular[0,1] parrallel[2,3]
		Dataset[] arrays = SurfaceQ.generateMinMaxParallelPerpendicularArrays(shape, new QSpace(dMeta.getDetector2DProperties(), dMeta.getDiffractionCrystalEnvironment()), new Vector3d(-Math.sin(roll), Math.cos(roll)*Math.cos(pitch), -Math.sin(pitch)));
		// xArray is ±q∥
		xArray = new Dataset[]{arrays[2], arrays[3]};
		// yArray is q⊥
		yArray = new Dataset[]{arrays[0], arrays[1]};
		
		xRange = new double[]{xArray[0].min().doubleValue(),xArray[0].max().doubleValue()};
		yRange = new double[]{0., yArray[0].max().doubleValue()};
		
		binEdgesX = calculateBins(xArray, this.binsPara, false);
		binEdgesY = calculateBins(yArray, this.binsPerp, true);
		
		xAxis = calculateAxis(this.binsPara, binEdgesX, "q_par");
		yAxis = calculateAxis(this.binsPerp, binEdgesY, "q_per");
	}
	
	@Override
	public Dataset[] getXAxisArray() {
		return xArray;
	}

	@Override
	public Dataset[] getYAxisArray() {
		return yArray;
	}

	@Override
	public double getXBinEdgeMax() {
		return binEdgesX.getAbs(binEdgesX.getSize()-1);
	}

	@Override
	public double getXBinEdgeMin() {
		return binEdgesX.getAbs(0);
	}

	@Override
	public double getYBinEdgeMax() {
		return binEdgesY.getAbs(binEdgesY.getSize()-1);
	}

	@Override
	public double getYBinEdgeMin() {
		return binEdgesY.getAbs(0);
	}

	@Override
	public int getNumberOfBinsXAxis() {
		return this.binsPara;
	}

	@Override
	public int getNumberOfBinsYAxis() {
		return this.binsPerp;
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
		return true;
	}

	@Override
	public boolean isTo1D() {
		return false;
	}

	@Override
	public boolean sanitise() {
		return true;
	}

	@Override
	public boolean provideLookup() {
		return false;
	}
	
	private static Dataset calculateAxis(int nBins, DoubleDataset binEdges, String name){
		
		Dataset axis = null;

		
		axis = Maths.add(binEdges.getSlice(new int[]{1}, null ,null), binEdges.getSlice(null, new int[]{-1},null));
		axis.idivide(2);

		axis.setName(name);

		return axis;
	}
	
	private static DoubleDataset calculateBins(Dataset[] arrays, int numBins, boolean positiveOnly) {
		
		double min = Double.MAX_VALUE;
		double max = -Double.MAX_VALUE;
		
		for (Dataset a : arrays) {

			Dataset data = a;
			
			double n = data.min(true).doubleValue();
			double x = data.max(true).doubleValue();
			min = n < min ? n : min;
			max = x > max ? x : max;
		}
		//default range corresponds to bin edges
		return (DoubleDataset) DatasetFactory.createLinearSpace( ((positiveOnly) ? 0. : min), max, numBins + 1, Dataset.FLOAT64);
	}

}
