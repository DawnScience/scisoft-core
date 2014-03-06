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
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;

public class PixelSplittingIntegration2D extends AbstractPixelIntegration {

	int nBinsChi;
	AbstractDataset chiArray;
	DoubleDataset binsChi;
	
	public PixelSplittingIntegration2D(QSpace qSpace, int numBinsQ, int numBinsChi) {
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

			//make one larger to know range of q for lower and right hand edges
			shape[0]++;
			shape[1]++;
			
			generateAxisArray(shape, false);
			chiArray = generateChiArray(qSpace.getDetectorProperties().getBeamCentreCoords(), axisArray.getShape());

		}

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();
		for (IDataset ds : datasets) {
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
			
			DoubleDataset histo = (DoubleDataset)AbstractDataset.zeros(new int[]{nBinsChi,nbins}, AbstractDataset.FLOAT64);
			DoubleDataset intensity = (DoubleDataset)AbstractDataset.zeros(new int[]{nBinsChi,nbins},AbstractDataset.FLOAT64);
//			final double[] h = histo.getData();
//			final double[] in = intensity.getData();
//			if (spanQ <= 0) {
//				h[0] = ds.getSize();
//				result.add(histo);
//				result.add(bins);
//				continue;
//			}

			AbstractDataset a = DatasetUtils.convertToAbstractDataset(axisArray);
			AbstractDataset b = DatasetUtils.convertToAbstractDataset(ds);
			PositionIterator iter = b.getPositionIterator();

			int[] pos = iter.getPos();
			int[] posStop = pos.clone();

			while (iter.hasNext()) {

				posStop[0] = pos[0]+2;
				posStop[1] = pos[1]+2;
				AbstractDataset qrange = a.getSlice(pos, posStop, null);
				AbstractDataset chirange = chiArray.getSlice(pos, posStop, null);

				final double qMax = (Double)qrange.max();
				final double qMin = (Double)qrange.min();
				final double chiMax = (Double)chirange.max();
				final double chiMin = (Double)chirange.min();

				final double sig = b.getDouble(pos);

				if (qMax < loQ && qMin > hiQ) {
					continue;
				} 
				
				if (chiMax < loChi && chiMin > hiChi) {
					continue;
				} 

				//losing something here?

				double minBinExactQ = (qMin-loQ)/spanQ;
				double maxBinExactQ = (qMax-loQ)/spanQ;
				int minBinQ = (int)minBinExactQ;
				int maxBinQ = (int)maxBinExactQ;
				
				double minBinExactChi = (chiMin-loChi)/spanChi;
				double maxBinExactChi = (chiMax-loChi)/spanChi;
				int minBinChi = (int)minBinExactChi;
				int maxBinChi = (int)maxBinExactChi;
				
				//FIXME better deal with the wrap around point
				if (chiMax - chiMin > 270) continue;
				
//				if (minBinQ == maxBinQ && minBinChi == maxBinChi) {
//					int[] setPos = new int[]{minBinChi,minBinQ};
//					double val = histo.get(setPos);
//					histo.set(val+1, setPos);
//					double inVal = intensity.get(setPos);
//					intensity.set(inVal+sig, setPos);
//				} else if  (minBinQ == maxBinQ && minBinChi != maxBinChi){
//					//Spread across chi
//					double iPerPixel = 1/(maxBinExactChi-minBinExactChi);
//
//					double minFrac = 1-(minBinExactChi-minBinChi);
//					double maxFrac = maxBinExactChi-maxBinChi;
//
//					if (minBinChi >= 0) {
//						int[] setPos = new int[]{minBinChi,minBinQ};
//						double val = histo.get(setPos);
//						histo.set(val+iPerPixel*minFrac, setPos);
//						double inVal = intensity.get(setPos);
//						intensity.set(inVal+sig*iPerPixel*minFrac, setPos);
//					}
//
//					if (maxBinChi < binsChi.getSize()-1) {
//						int[] setPos = new int[]{maxBinChi,minBinQ};
//						double val = histo.get(setPos);
//						histo.set(val+iPerPixel*maxFrac, setPos);
//						double inVal = intensity.get(setPos);
//						intensity.set(inVal+sig*iPerPixel*maxFrac, setPos);
//					}
//
//					for (int i = (minBinChi+1); i < maxBinChi; i++) {
//						if (i >= binsChi.getSize() || i < 0) continue; 
//						int[] setPos = new int[]{i,minBinQ};
//						double val = histo.get(setPos);
//						histo.set(val+iPerPixel, setPos);
//						double inVal = intensity.get(setPos);
//						intensity.set(inVal+sig*iPerPixel, setPos);
//					}
//
//				}else if  (minBinQ != maxBinQ && minBinChi == maxBinChi) {
//					//spread across Q
//					double iPerPixel = 1/(maxBinExactQ-minBinExactQ);
//					double minFrac = 1-(minBinExactQ-minBinQ);
//					double maxFrac = maxBinExactQ-maxBinQ;
//
//					if (minBinQ >= 0) {
//						int[] setPos = new int[]{minBinChi,minBinQ};
//						double val = histo.get(setPos);
//						histo.set(val+iPerPixel*minFrac, setPos);
//						double inVal = intensity.get(setPos);
//						intensity.set(inVal+sig*iPerPixel*minFrac, setPos);
//					}
//
//					if (maxBinQ < bins.getSize()-1 && minBinChi > 0 && minBinChi < binsChi.getSize()) {
//						int[] setPos = new int[]{minBinChi,maxBinQ};
//						double val = histo.get(setPos);
//						histo.set(val+iPerPixel*maxFrac, setPos);
//						double inVal = intensity.get(setPos);
//						intensity.set(inVal+sig*iPerPixel*maxFrac, setPos);
//					}
//					for (int i = (minBinQ+1); i < maxBinQ; i++) {
//						if (i >= binsChi.getSize() || i < 0) continue; 
//						int[] setPos = new int[]{minBinChi,i};
//						double val = histo.get(setPos);
//						histo.set(val+iPerPixel, setPos);
//						double inVal = intensity.get(setPos);
//						intensity.set(inVal+sig*iPerPixel, setPos);
//					}
//				} else {
					double iPerPixel = 1/((maxBinExactQ-minBinExactQ)*(maxBinExactChi-minBinExactChi));
					double minFracQ = 1-(minBinExactQ-minBinQ);
					double maxFracQ = maxBinExactQ-maxBinQ;
					double minFracChi = 1-(minBinExactChi-minBinChi);
					double maxFracChi = maxBinExactChi-maxBinChi;

					for (int i = minBinQ ; i <= maxBinQ; i++) {
						if (i < 0 || i >= bins.getSize()-1) continue;
						for (int j = minBinChi; j <= maxBinChi; j++) {
							if (j < 0 || j >= binsChi.getSize()-1) continue;
							
							int[] setPos = new int[]{j,i};
							double val = histo.get(setPos);
							
							double modify = 1;
							
							if (i == minBinQ && minBinQ != maxBinQ) modify *= minFracQ;
							if (i == maxBinQ && minBinQ != maxBinQ) modify *= maxFracQ;
							if (i == minBinChi && minBinChi != maxBinChi) modify *= minFracChi;
							if (i == maxBinChi && minBinChi != maxBinChi) modify *= maxFracChi;
							
							histo.set(val+iPerPixel*modify, setPos);
							double inVal = intensity.get(setPos);
							intensity.set(inVal+sig*iPerPixel*modify, setPos);
						}
//					}
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
