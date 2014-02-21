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

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3d;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Comparisons;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.IRectangularROI;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public abstract class AbstractPixelIntegration implements DatasetToDatasetFunction{
	int nbins;
	Double min = null;
	Double max = null;
	DoubleDataset bins = null;
	AbstractDataset axisArray;
	AbstractDataset mask;
	AbstractDataset maskRoiCached;
	QSpace qSpace = null;
	ROIProfile.XAxis xAxis = XAxis.Q;
	IROI roi = null;
	
	public AbstractPixelIntegration(QSpace qSpace, int numBins) {
		this.qSpace = qSpace;
		this.nbins = numBins;
	}
	
	public AbstractPixelIntegration(QSpace qSpace, int numBins, double lower, double upper)
	{
		this(qSpace, numBins);
		min = lower;
		max = upper;
		if (min > max) {
			throw new IllegalArgumentException("Given lower bound was higher than upper bound");
		}

		bins = (DoubleDataset) DatasetUtils.linSpace(min, max, nbins + 1, AbstractDataset.FLOAT64);
	}

	@Override
	public abstract List<AbstractDataset> value(IDataset... datasets);
	
	/**
	 * Set minimum and maximum edges of histogram bins
	 * @param min
	 * @param max
	 */
	public void setMinMax(double min, double max) {
		this.min = min;
		this.max = max;
		bins = (DoubleDataset) DatasetUtils.linSpace(min, max, nbins + 1, AbstractDataset.FLOAT64);		
	}
	
	public void setAxisType(ROIProfile.XAxis axis) {
		this.xAxis = axis;
	}
	
	protected void generateAxisArray(int[] shape, boolean centre) {
		
		if (qSpace == null) return;
		
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();

		axisArray = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);

		PositionIterator iter = axisArray.getPositionIterator();
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
				value = (2*Math.PI)/q.length();
				break;
			case PIXEL:
				value = Math.hypot(pos[1]-beamCentre[0],pos[0]-beamCentre[1]);
				break; 
			}
			
			if (pos[0] < 2 && pos[1]< 2 && pos[0]+pos[1] < 3) {
				axisArray.toString();
			}
			
			axisArray.set(value, pos);
			
		}
	}
	
	protected Slice[] getSlice() {
		if (roi != null) {
			IRectangularROI bounds = roi.getBounds();
			int[] s = bounds.getIntPoint();
			int e0 = (int)bounds.getLength(0) + s[0];
			int e1 = (int)bounds.getLength(1)+s[1];
			
			s[0] = Math.max(s[0], 0);
			s[0] = Math.min(s[0], axisArray.getShape()[1]);
			s[1] = Math.max(s[1], 0);
			s[1] = Math.min(s[1], axisArray.getShape()[0]);
			e0 = Math.max(e0, 0);
			e0 = Math.min(e0, axisArray.getShape()[1]);
			e1 = Math.max(e1, 0);
			e1 = Math.min(e1, axisArray.getShape()[0]);
			
			Slice s1 = new Slice(s[1], e1, 1);
			Slice s2 = new Slice(s[0], e0, 1);
			return new Slice[]{s1,s2};
		}
		
		return null;
	}
	
	protected void processAndAddToResult(AbstractDataset intensity, AbstractDataset histo, List<AbstractDataset> result, String name) {
		AbstractDataset axis = Maths.add(bins.getSlice(new int[]{1}, null ,null), bins.getSlice(null, new int[]{-1},null));
		axis.idivide(2);
		
		if (xAxis == XAxis.Q) axis.setName("q");
		else axis.setName("2 Theta");
		
		result.add(axis);
		AbstractDataset out = Maths.dividez(intensity, DatasetUtils.cast(histo,AbstractDataset.FLOAT64));
		out.setName(name + "_integrated");
		result.add(out);
	}
	
	public void setMask(AbstractDataset mask) {
		this.mask = mask;
		if (axisArray != null && !Arrays.equals(axisArray.getShape(), mask.getShape())) axisArray = null;
	}
	
	public void setROI(IROI roi) {
		this.roi = roi;
	}
	
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
	
	protected void calculateBins(AbstractDataset ax, AbstractDataset ma) {
		//TODO test for ROIS
		if (ma == null) {
			bins = (DoubleDataset) DatasetUtils.linSpace(ax.min().doubleValue(), ax.max().doubleValue(), nbins + 1, AbstractDataset.FLOAT64);
		} else {
			
			if (Arrays.equals(ma.getShape(), ax.getShape())) {
				AbstractDataset unMaskedVals = DatasetUtils.select(new BooleanDataset[]{(BooleanDataset)DatasetUtils.cast(ma,AbstractDataset.BOOL)}, new Object[]{ax}, Double.NaN);
				bins = (DoubleDataset) DatasetUtils.linSpace(unMaskedVals.min(true).doubleValue(), unMaskedVals.max(true).doubleValue(), nbins + 1, AbstractDataset.FLOAT64);
			} else {
				//extended array for pixel splitting
				BooleanDataset biggerMask = new BooleanDataset(ma.getShape()[0]+1,ma.getShape()[1]+1);
				
				PositionIterator pit = ma.getPositionIterator();
				int[] pos = pit.getPos();
				
				while (pit.hasNext()) biggerMask.set(ma.getObject(pos), pos);
				
				pos[0] = ma.getShape()[0]-1;
				for (int i = 0; i < ma.getShape()[1]; i++) {
					pos[1] = i;
					biggerMask.set(ma.getObject(pos), new int[]{pos[1]+1, i});
				}
				
				pos[1] = ma.getShape()[1]-1;
				for (int i = 0; i < ma.getShape()[0]; i++) {
					pos[0] = i;
					biggerMask.set(ma.getObject(pos), new int[]{pos[0]+1, i});
				}
				
				pos = ma.getShape();
				
				biggerMask.set(ma.getObject(new int[] {pos[0]-1,pos[1]-1}), pos);
				
				AbstractDataset unMaskedVals = DatasetUtils.select(new BooleanDataset[]{(BooleanDataset)DatasetUtils.cast(biggerMask,AbstractDataset.BOOL)}, new Object[]{ax}, Double.NaN);
				bins = (DoubleDataset) DatasetUtils.linSpace(unMaskedVals.min(true).doubleValue(), unMaskedVals.max(true).doubleValue(), nbins + 1, AbstractDataset.FLOAT64);
			}
			
		}
			
	}
}
