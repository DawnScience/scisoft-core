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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;

public class PixelSplittingIntegration2D extends AbstractPixelIntegration2D {

	public PixelSplittingIntegration2D(IDiffractionMetadata metadata) {
		super(metadata);
	}
	
	public PixelSplittingIntegration2D(IDiffractionMetadata metadata, int numBinsQ, int numBinsChi) {
		super(metadata, numBinsQ, numBinsChi);
	}

	@Override
	public List<AbstractDataset> integrate(IDataset dataset) {

		//Generate radial and azimuthal look-up arrays as required
		//TODO test shape of axis array
		if (radialArray == null) {
			generateRadialArray(dataset.getShape(), false);
		}

		if (azimuthalArray == null) {
			generateMinMaxAzimuthalArray(qSpace.getDetectorProperties().getBeamCentreCoords(),dataset.getShape());
		}

		AbstractDataset mt = mask;
		if (mask != null && !Arrays.equals(mask.getShape(),dataset.getShape())) throw new IllegalArgumentException("Mask shape does not match dataset shape");
		

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();
		if (binEdges == null) {
			binEdges = calculateBins(radialArray,mt,radialRange, nbins);
			binsChi = calculateBins(azimuthalArray,mt,azimuthalRange, nBinsChi);
		}

		final double[] edgesQ = binEdges.getData();
		final double loQ = edgesQ[0];
		final double hiQ = edgesQ[nbins];
		final double spanQ = (hiQ - loQ)/nbins;

		final double[] edgesChi = binsChi.getData();
		final double loChi = edgesChi[0];
		final double hiChi = edgesChi[nBinsChi];
		final double spanChi = (hiChi - loChi)/nBinsChi;

		FloatDataset histo = (FloatDataset)AbstractDataset.zeros(new int[]{nBinsChi,nbins}, Dataset.FLOAT32);
		FloatDataset intensity = (FloatDataset)AbstractDataset.zeros(new int[]{nBinsChi,nbins},Dataset.FLOAT32);
		//			final double[] h = histo.getData();
		//			final double[] in = intensity.getData();
		//			if (spanQ <= 0) {
		//				h[0] = ds.getSize();
		//				result.add(histo);
		//				result.add(bins);
		//				continue;
		//			}

		AbstractDataset a = DatasetUtils.convertToAbstractDataset(radialArray[0]);
		AbstractDataset b = DatasetUtils.convertToAbstractDataset(dataset);
//		PositionIterator iter = b.getPositionIterator();
//
//		int[] pos = iter.getPos();
//		int[] posStop = pos.clone();
		
		IndexIterator iter = a.getIterator();

		while (iter.hasNext()) {

//			posStop[0] = pos[0]+2;
//			posStop[1] = pos[1]+2;
//			AbstractDataset qrange = a.getSlice(pos, posStop, null);
//			AbstractDataset chirange = azimuthalArray.getSlice(pos, posStop, null);
			if (mt != null && !mt.getElementBooleanAbs(iter.index)) continue;
			final double qMax = radialArray[1].getElementDoubleAbs(iter.index);
			final double qMin = radialArray[0].getElementDoubleAbs(iter.index);
			final double chiMax = azimuthalArray[1].getElementDoubleAbs(iter.index);
			final double chiMin = azimuthalArray[0].getElementDoubleAbs(iter.index);

			final double sig = b.getElementDoubleAbs(iter.index);

			if (qMax < loQ || qMin > hiQ) {
				continue;
			} 

			if (chiMax < loChi || chiMin > hiChi) {
				continue;
			} 

			//losing something here? is flooring (int cast best?)

			double minBinExactQ = (qMin-loQ)/spanQ;
			double maxBinExactQ = (qMax-loQ)/spanQ;
			int minBinQ = (int)minBinExactQ;
			int maxBinQ = (int)maxBinExactQ;

			double minBinExactChi = (chiMin-loChi)/spanChi;
			double maxBinExactChi = (chiMax-loChi)/spanChi;
			int minBinChi = (int)minBinExactChi;
			int maxBinChi = (int)maxBinExactChi;

			//FIXME potentially may need to deal with azimuthal arrays with discontinuities (+180/-180 degress etc)

			double iPerPixel = 1/((maxBinExactQ-minBinExactQ)*(maxBinExactChi-minBinExactChi));
			double minFracQ = 1-(minBinExactQ-minBinQ);
			double maxFracQ = maxBinExactQ-maxBinQ;
			double minFracChi = 1-(minBinExactChi-minBinChi);
			double maxFracChi = maxBinExactChi-maxBinChi;

			for (int i = minBinQ ; i <= maxBinQ; i++) {
				if (i < 0 || i >= binEdges.getSize()-1) continue;
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

			}
		}

		processAndAddToResult(intensity, histo, result,radialRange, dataset.getName());

		return result;
	}
}
