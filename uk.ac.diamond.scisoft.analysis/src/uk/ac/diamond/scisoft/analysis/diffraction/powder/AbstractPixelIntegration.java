/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;

import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public abstract class AbstractPixelIntegration {
	
	int nbins;
	
	double[] radialRange = null;
	double[] azimuthalRange = null;
	
	DoubleDataset binEdges = null;
	
	Dataset[] radialArray;
	Dataset[] azimuthalArray;
	Dataset mask;
	Dataset maskRoiCached;
	
	QSpace qSpace = null;
	XAxis xAxis = XAxis.Q;
	
	IROI roi = null;
	
	public AbstractPixelIntegration(IDiffractionMetadata metadata) {
		this.qSpace = new QSpace(metadata.getDetector2DProperties(), 
								 metadata.getDiffractionCrystalEnvironment());
		
		int[] shape = new int[]{metadata.getDetector2DProperties().getPy(), metadata.getDetector2DProperties().getPx()};
		this.nbins = calculateNumberOfBins(metadata.getDetector2DProperties().getBeamCentreCoords(), shape);
	}
	
	public AbstractPixelIntegration(IDiffractionMetadata metadata, int numBins) {
		this.qSpace = new QSpace(metadata.getDetector2DProperties(), 
								 metadata.getDiffractionCrystalEnvironment());
		
		this.nbins = numBins;
	}
	
	public abstract List<Dataset> integrate(IDataset dataset);
	
	/**
	 * Set minimum and maximum of radial range
	 * @param range
	 */
	public void setRadialRange(double[] range) {
		
		if (range == null) {
			radialRange = null;
			binEdges = null;
			return;
		}
		
		if (range.length != 2) throw new IllegalArgumentException("Range array should be of length 2");
		
		if (xAxis == XAxis.RESOLUTION) {
			range[0] = (2*Math.PI)/range[0];
			range[1] = (2*Math.PI)/range[1];
		}
		
		if (range[0] > range[1]) {
			Arrays.sort(range);
		}
		
		radialRange = range;
		binEdges = null;

	}
	
	/**
	 * Set minimum and maximum of azimuthal range (-180 to + 180)
	 * @param range
	 */
	public void setAzimuthalRange(double[] range) {
		
		if (range == null) {
			azimuthalRange = null;
			binEdges = null;
			return;
		}
		
		if (range.length != 2) throw new IllegalArgumentException("Range array should be of length 2");
		
		if (range[0] > range[1]) {
			Arrays.sort(range);
		}
		
		if (range[0] < -180) throw new IllegalArgumentException("Min cannot be less than -Pi");
		if (range[1] > 180) throw new IllegalArgumentException("Max cannot be greater than +Pi");
		
		azimuthalRange = range;
		binEdges = null;
	}
	
	public void setNumberOfBins(int nBins) {
		if (nBins < 1) throw new IllegalArgumentException("Must be 1 or more");
		this.nbins = nBins;
		binEdges = null;
	}
	
	public void setAxisType(XAxis axis) {
		if (this.xAxis == axis) return;
		this.xAxis = axis;
		radialArray = null;
		binEdges = null;
	}
	
	public void generateRadialArray(int[] shape, boolean centre) {
		
		if (qSpace == null) return;
		XAxis x = xAxis == XAxis.RESOLUTION ? XAxis.Q : xAxis;
		
		if (centre) radialArray = new Dataset[]{PixelIntegrationUtils.generateRadialArray(shape, qSpace, x)};
		else radialArray = PixelIntegrationUtils.generateMinMaxRadialArray(shape, qSpace, x);
		
	}
	
	protected void generateAzimuthalArray(double[] beamCentre, int[] shape) {
		azimuthalArray = new Dataset[]{PixelIntegrationUtils.generateAzimuthalArray(beamCentre, shape, false)};
	}
	
	protected void generateMinMaxAzimuthalArray(double[] beamCentre, int[] shape) {
		azimuthalArray = PixelIntegrationUtils.generateMinMaxAzimuthalArray(beamCentre, shape, false);
	}
	
	protected Slice[] getSlice() {
//		if (roi != null) {
//			IRectangularROI bounds = roi.getBounds();
//			int[] s = bounds.getIntPoint();
//			int e0 = (int)bounds.getLength(0) + s[0];
//			int e1 = (int)bounds.getLength(1)+s[1];
//			
//			s[0] = Math.max(s[0], 0);
//			s[0] = Math.min(s[0], radialArray.getShape()[1]);
//			s[1] = Math.max(s[1], 0);
//			s[1] = Math.min(s[1], radialArray.getShape()[0]);
//			e0 = Math.max(e0, 0);
//			e0 = Math.min(e0, radialArray.getShape()[1]);
//			e1 = Math.max(e1, 0);
//			e1 = Math.min(e1, radialArray.getShape()[0]);
//			
//			Slice s1 = new Slice(s[1], e1, 1);
//			Slice s2 = new Slice(s[0], e0, 1);
//			return new Slice[]{s1,s2};
//		}
		
		return null;
	}
	
	protected void processAndAddToResult(Dataset intensity, Dataset histo, List<Dataset> result,
			 double[] binRange, String name) {
		
		Dataset axis = null;
		
		if (binRange == null) {
			axis = Maths.add(binEdges.getSlice(new int[]{1}, null ,null), binEdges.getSlice(null, new int[]{-1},null));
			axis.idivide(2);
		} else {
			axis = DatasetUtils.linSpace(binRange[0], binRange[1], nbins, Dataset.FLOAT64);
		}
		
		switch (xAxis) {
		case Q:
			axis.setName("q");
			break;
		case ANGLE:
			axis.setName("2theta");
			break;
		case RESOLUTION:
			axis = Maths.divide((2*Math.PI), axis);
			axis.setName("d-spacing");
			break;
		case PIXEL:
			axis.setName("pixel");
			break;
		}
		
		intensity.idivide(histo);
		DatasetUtils.makeFinite(intensity);
		
		intensity.setName(name + "_integrated");
		result.add(axis);
		result.add(intensity);
	}

	public void setMask(Dataset mask) {
		this.mask = mask;
		maskRoiCached = null;
		if (mask == null) return;
//		binEdges = null;
		if (radialArray != null && !Arrays.equals(radialArray[0].getShape(), mask.getShape())) radialArray = null;
	}
	
//	public void setROI(IROI roi) {
//		this.roi = roi;
//		maskRoiCached = null;
//	}
	
	public static int calculateNumberOfBins(double[] beamCentre, int[] shape) {
		//within image
		if (beamCentre[1] < shape[0] && beamCentre[1] > 0
				&& beamCentre[0] < shape[1] && beamCentre[0] > 0) {
			double[] farCorner = new double[]{0,0};
			if (beamCentre[1] < shape[0]/2.0) farCorner[0] = shape[0];
			if (beamCentre[0] < shape[1]/2.0) farCorner[1] = shape[1];
			
			return (int)Math.hypot(beamCentre[0]-farCorner[1], beamCentre[1]-farCorner[0]);
		} else if (beamCentre[1] < shape[0] && beamCentre[1] > 0
				&& (beamCentre[0] > shape[1] || beamCentre[0] < 0)) {
				return shape[1];
		} else if (beamCentre[0] < shape[1] && beamCentre[0] > 0
				&& (beamCentre[1] > shape[0] || beamCentre[1] < 0)) {
				return shape[0];
		} else {
			return (int)Math.hypot(shape[1], shape[0]);
		}
	}
	
	protected Dataset mergeMaskAndRoi(int[] shape) {
		
		Dataset out;
		if (mask == null) out = new BooleanDataset(shape);
		else out = mask.clone();
		
		PositionIterator pit = out.getPositionIterator();
		int[] pos = pit.getPos();
		
		while (pit.hasNext()) {
			
			if (mask == null) {
				if (roi.containsPoint(pos[1], pos[0])) out.set(true, pos);
			} else {
				if (!roi.containsPoint(pos[1], pos[0])) out.set(false, pos);
			}
		}
		
		return out;
	}
	
	protected DoubleDataset calculateBins(Dataset[] arrays, Dataset mask, double[] binRange, int numBins) {
		
		if (binRange != null) {
			//range corresponds to bin centres
			double shift = (binRange[1]- binRange[0])/(2*numBins);
			return (DoubleDataset) DatasetUtils.linSpace(binRange[0]-shift, binRange[1]+shift, numBins + 1, Dataset.FLOAT64);
		}
		
			
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;

		for (Dataset a : arrays) {

			Dataset data = a;
			
			if (mask != null) data = DatasetUtils.select(new BooleanDataset[]{(BooleanDataset)DatasetUtils.cast(mask,Dataset.BOOL)}, new Object[]{a}, Double.NaN);

			double n = data.min(true).doubleValue();
			double x = data.max(true).doubleValue();
			min = n < min ? n : min;
			max = x > max ? x : max;
		}

		return (DoubleDataset) DatasetUtils.linSpace(min, max, numBins + 1, Dataset.FLOAT64);
	}
}
