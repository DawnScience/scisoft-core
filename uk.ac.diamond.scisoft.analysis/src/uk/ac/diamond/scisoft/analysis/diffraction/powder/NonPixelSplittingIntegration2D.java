/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.FloatDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;

public class NonPixelSplittingIntegration2D extends AbstractPixelIntegration2D {
	
	
	public NonPixelSplittingIntegration2D(IDiffractionMetadata metadata) {
		super(metadata);
	}
	
	public NonPixelSplittingIntegration2D(IDiffractionMetadata metadata, int numBinsQ, int numBinsChi) {
		super(metadata, numBinsQ,numBinsChi);
	}

	@Override
	public List<Dataset> integrate(IDataset dataset) {
		
		//TODO test shape of axis array
		if (radialArray == null) {
			generateRadialArray(dataset.getShape(), true);
		}
		
		if (azimuthalArray == null) {
			generateAzimuthalArray(qSpace.getDetectorProperties().getBeamCentreCoords(), dataset.getShape());
		}

		List<Dataset> result = new ArrayList<Dataset>();

		Dataset mt = mask;
		if (mask != null && !Arrays.equals(mask.getShape(),dataset.getShape())) throw new IllegalArgumentException("Mask shape does not match dataset shape");
		
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

		//TODO early exit if spans are z

		IntegerDataset histo = (IntegerDataset) DatasetFactory.zeros(new int[]{nBinsChi,nbins}, Dataset.INT32);
		FloatDataset intensity = (FloatDataset) DatasetFactory.zeros(new int[]{nBinsChi,nbins},Dataset.FLOAT32);

		Dataset a = DatasetUtils.convertToDataset(radialArray[0]);
		Dataset b = DatasetUtils.convertToDataset(dataset);
		IndexIterator iter = a.getIterator();

		while (iter.hasNext()) {

			final double valq = a.getElementDoubleAbs(iter.index);
			final double sig = b.getElementDoubleAbs(iter.index);
			final double chi = azimuthalArray[0].getElementDoubleAbs(iter.index);
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

		processAndAddToResult(intensity, histo, result,radialRange, dataset.getName());

		return result;
	}
}
