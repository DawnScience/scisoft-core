/*-
 * Copyright 2014 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3d;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public abstract class AbstractPixelIntegration {
	
	int nbins;
	
	double[] radialRange = null;
	double[] azimuthalRange = null;
	
	DoubleDataset binEdges = null;
	
	AbstractDataset[] radialArray;
	AbstractDataset[] azimuthalArray;
	AbstractDataset mask;
	AbstractDataset maskRoiCached;
	
	QSpace qSpace = null;
	ROIProfile.XAxis xAxis = XAxis.Q;
	
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
	
	public abstract List<AbstractDataset> integrate(IDataset dataset);
	
	/**
	 * Set minimum and maximum of radial range
	 * @param range
	 */
	public void setRadialRange(double[] range) {
		
		if (range == null) {
			radialRange = null;
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
	
	public void setAxisType(ROIProfile.XAxis axis) {
		this.xAxis = axis;
		radialArray = null;
		binEdges = null;
	}
	
	public void generateRadialArray(int[] shape, boolean centre) {
		
		if (qSpace == null) return;
		
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();

		AbstractDataset ra = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);

		PositionIterator iter = ra.getPositionIterator();
		int[] pos = iter.getPos();

		while (iter.hasNext()) {
			
			Vector3d q;
			double value = 0;
			//FIXME or not fix me, but I would expect centre to be +0.5, but this
			//clashes with much of the rest of DAWN
			if (centre) q = qSpace.qFromPixelPosition(pos[1], pos[0]);
			else q = qSpace.qFromPixelPosition(pos[1]-0.5, pos[0]-0.5);
			
			switch (xAxis) {
			case ANGLE:
				value = Math.toDegrees(qSpace.scatteringAngle(q));
				break;
			case Q:
				value = q.length();
				break;
			case RESOLUTION:
				value = q.length();
				//value = (2*Math.PI)/q.length();
				break;
			case PIXEL:
				value = Math.hypot(pos[1]-beamCentre[0],pos[0]-beamCentre[1]);
				break; 
			}
			ra.set(value, pos);
		}
		
		radialArray = new AbstractDataset[]{ra};
	}
	
	protected void generateMinMaxRadialArray(int[] shape) {
		
		if (qSpace == null) return;
		
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();

		AbstractDataset radialArrayMax = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);
		AbstractDataset radialArrayMin = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);

		PositionIterator iter = radialArrayMax.getPositionIterator();
		int[] pos = iter.getPos();

		double[] vals = new double[4];
		double w = qSpace.getWavelength();
		while (iter.hasNext()) {
			
			//FIXME or not fix me, but I would expect centre to be +0.5, but this
			//clashes with much of the rest of DAWN
			
			if (xAxis != XAxis.PIXEL) {
				vals[0] = qSpace.qFromPixelPosition(pos[1]-0.5, pos[0]-0.5).length();
				vals[1] = qSpace.qFromPixelPosition(pos[1]+0.5, pos[0]-0.5).length();
				vals[2] = qSpace.qFromPixelPosition(pos[1]-0.5, pos[0]+0.5).length();
				vals[3] = qSpace.qFromPixelPosition(pos[1]+0.5, pos[0]+0.5).length();
			} else {
				vals[0] = Math.hypot(pos[1]-0.5-beamCentre[0], pos[0]-0.5-beamCentre[1]);
				vals[1] = Math.hypot(pos[1]+0.5-beamCentre[0], pos[0]-0.5-beamCentre[1]);
				vals[2] = Math.hypot(pos[1]-0.5-beamCentre[0], pos[0]+0.5-beamCentre[1]);
				vals[3] = Math.hypot(pos[1]+0.5-beamCentre[0], pos[0]+0.5-beamCentre[1]);
			}
			
			Arrays.sort(vals);

			switch (xAxis) {
			case ANGLE:
				radialArrayMax.set(Math.toDegrees(Math.asin(vals[3] * w/(4*Math.PI))*2),pos);
				radialArrayMin.set(Math.toDegrees(Math.asin(vals[0] * w/(4*Math.PI))*2),pos);
				break;
			case Q:
			case PIXEL:
			case RESOLUTION:
				radialArrayMax.set(vals[3],pos);
				radialArrayMin.set(vals[0],pos);
				//value = (2*Math.PI)/q.length();
				break;
			}
		}
		radialArray =  new AbstractDataset[]{radialArrayMin,radialArrayMax};
	}
	
	protected void generateAzimuthalArray(double[] beamCentre, int[] shape,boolean centre) {
		azimuthalArray = new AbstractDataset[]{Maths.toDegrees(PixelIntegrationUtils.generateAzimuthalArrayRadians(beamCentre, shape, centre))};
	}
	
	protected void generateMinMaxAzimuthalArray(double[] beamCentre, int[] shape) {
		AbstractDataset[] out = PixelIntegrationUtils.generateMinMaxAzimuthalArrayRadians(beamCentre, shape);
		azimuthalArray = new AbstractDataset[]{Maths.toDegrees(out[0]),Maths.toDegrees(out[1])};
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
	
	protected void processAndAddToResult(AbstractDataset intensity, AbstractDataset histo, List<AbstractDataset> result,
			 double[] binRange, String name) {
		
		AbstractDataset axis = null;
		
		if (binRange == null) {
			axis = Maths.add(binEdges.getSlice(new int[]{1}, null ,null), binEdges.getSlice(null, new int[]{-1},null));
			axis.idivide(2);
		} else {
			axis = DatasetUtils.linSpace(binRange[0], binRange[1], nbins, AbstractDataset.FLOAT64);
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

	public void setMask(AbstractDataset mask) {
		this.mask = mask;
		maskRoiCached = null;
		if (mask == null) return;
		binEdges = null;
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
	
	protected AbstractDataset mergeMaskAndRoi(int[] shape) {
		
		AbstractDataset out;
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
	
	protected DoubleDataset calculateBins(AbstractDataset[] arrays, AbstractDataset mask, double[] binRange, int numBins) {
		
		if (binRange != null) {
			//range corresponds to bin centres
			double shift = (binRange[1]- binRange[0])/(2*numBins);
			return (DoubleDataset) DatasetUtils.linSpace(binRange[0]-shift, binRange[1]+shift, numBins + 1, AbstractDataset.FLOAT64);
		}
		
			
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;

		for (AbstractDataset a : arrays) {

			AbstractDataset data = a;
			
			if (mask != null) data = DatasetUtils.select(new BooleanDataset[]{(BooleanDataset)DatasetUtils.cast(mask,AbstractDataset.BOOL)}, new Object[]{a}, Double.NaN);

			double n = data.min(true).doubleValue();
			double x = data.max(true).doubleValue();
			min = n < min ? n : min;
			max = x > max ? x : max;
		}

		return (DoubleDataset) DatasetUtils.linSpace(min, max, numBins + 1, AbstractDataset.FLOAT64);
	}
}
