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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;

public class NonPixelSplittingIntegration2D extends AbstractPixelIntegration {

	int nBinsChi;
	AbstractDataset chiArray;
	DoubleDataset binsChi;
	
	public NonPixelSplittingIntegration2D(QSpace qSpace, int numBinsQ, int numBinsChi) {
		super(qSpace, numBinsQ);
		this.nBinsChi = numBinsChi;
	}

	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;
		
		if (axisArray == null) {

			if (qSpace == null) return null;

			int[] shape = datasets[0].getShape();
			generateAxisArray(shape, false);
			chiArray = generateChiArray(qSpace.getDetectorProperties().getBeamCentreCoords(), axisArray.getShape());

		}

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();
		for (IDataset ds : datasets) {
			
			AbstractDataset mt = mask;
			if (mask != null && !Arrays.equals(mask.getShape(),ds.getShape())) throw new IllegalArgumentException("Mask shape does not match dataset shape");
			
			
			if (bins == null) {
				bins = (DoubleDataset) DatasetUtils.linSpace(axisArray.min().doubleValue(), axisArray.max().doubleValue(), nbins + 1, AbstractDataset.FLOAT64);
				binsChi = (DoubleDataset) DatasetUtils.linSpace(chiArray.min().doubleValue(), chiArray.max().doubleValue(), nBinsChi + 1, AbstractDataset.FLOAT64);
			}
			
			final double[] edgesQ = bins.getData();
			final double loQ = edgesQ[0];
			final double hiQ = edgesQ[nbins];
			final double spanQ = (hiQ - loQ)/nbins;
			
			final double[] edgesChi = binsChi.getData();
			final double loChi = edgesChi[0];
			final double hiChi = edgesChi[nBinsChi];
			final double spanChi = (hiChi - loChi)/nBinsChi;
			
			IntegerDataset histo = (IntegerDataset)AbstractDataset.zeros(new int[]{nBinsChi,nbins}, AbstractDataset.INT32);
			FloatDataset intensity = (FloatDataset)AbstractDataset.zeros(new int[]{nBinsChi,nbins},AbstractDataset.FLOAT32);

			AbstractDataset a = DatasetUtils.convertToAbstractDataset(axisArray);
			AbstractDataset b = DatasetUtils.convertToAbstractDataset(ds);
			IndexIterator iter = a.getIterator();

			while (iter.hasNext()) {
				
				final double valq = a.getElementDoubleAbs(iter.index);
				final double sig = b.getElementDoubleAbs(iter.index);
				final double chi = chiArray.getElementDoubleAbs(iter.index);
				if (mt != null && !mt.getElementBooleanAbs(iter.index)) continue;
				
				if (valq < loQ || valq > hiQ) {
					continue;
				}
				
				if (chi < loChi || chi > hiChi) {
					continue;
				}
				
				int qPos = (int) ((valq-loQ)/spanQ);
				int chiPos = (int) ((chi-loChi)/spanChi);
				
				if(qPos<nbins && chiPos<nBinsChi){
					int cNum = histo.get(chiPos,qPos);
					float cIn = intensity.get(chiPos,qPos);
					histo.set(cNum+1, chiPos,qPos);
					intensity.set(cIn+sig, chiPos,qPos);
				}
				
			}
			
			processAndAddToResult(intensity, histo, result, ds.getName());
		}

		return result;
	}
	
	private AbstractDataset generateChiArray(double[] beamCentre, int[] shape) {
		
		AbstractDataset out = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);
		
		PositionIterator iter = out.getPositionIterator();

		int[] pos = iter.getPos();

		while (iter.hasNext()) {
			out.set(Math.toDegrees(Math.atan2(pos[0]-beamCentre[1],pos[1]-beamCentre[0])), pos);
		}
		
		return out;
	}
	
	@Override
	protected void processAndAddToResult(AbstractDataset intensity, AbstractDataset histo, List<AbstractDataset> result, String name) {
		super.processAndAddToResult(intensity, histo, result, name);
		
		AbstractDataset axis = Maths.add(binsChi.getSlice(new int[]{1}, null ,null), binsChi.getSlice(null, new int[]{-1},null));
		axis.idivide(2);
		
		axis.setName("chi");
		result.add(axis);
		
	}

}
