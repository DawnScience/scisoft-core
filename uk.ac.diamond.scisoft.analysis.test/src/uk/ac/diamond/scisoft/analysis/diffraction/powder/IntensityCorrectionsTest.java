/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import static org.junit.Assert.*;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Comparisons;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.junit.Test;

public class IntensityCorrectionsTest extends AbstractPixelIntegrationTestBase {
	
	@Test
	public void testSolidAngle() {
		
		IDiffractionMetadata md = getDiffractionMetadata();
		
		int[] shape = new int[]{2048,2048};
		DoubleDataset correctionArray = new DoubleDataset(shape);
		correctionArray.iadd(1);
		
		Dataset tth = PixelIntegrationUtils.generate2ThetaArrayRadians(md);
		
		long start = System.currentTimeMillis();
		PixelIntegrationUtils.solidAngleCorrection(correctionArray, tth);
		long stop = System.currentTimeMillis();
		
		System.out.println(stop-start);
		
		DoubleDataset correctionArray2 = new DoubleDataset(shape);
		correctionArray2.iadd(1);
		
		start = System.currentTimeMillis();
		IndexIterator it = correctionArray2.getIterator();
		
		while (it.hasNext()) correctionArray2.setAbs(it.index, PixelIntegrationUtils.solidAngleCorrection(correctionArray2.getAbs(it.index), tth.getElementDoubleAbs(it.index)));
		stop = System.currentTimeMillis();
		
		System.out.println(stop-start);
		
		assertArrayEquals(correctionArray.getData(), correctionArray2.getData(), 0);
		
	}
	
	@Test
	public void testPolarisation() {
		
		IDiffractionMetadata md = getDiffractionMetadata();
		
		int[] shape = new int[]{2048,2048};
		DoubleDataset correctionArray = new DoubleDataset(shape);
		correctionArray.iadd(1);
		
		Dataset tth = PixelIntegrationUtils.generate2ThetaArrayRadians(md);
		Dataset angle = PixelIntegrationUtils.generateAzimuthalArray(md, true);
		
		long start = System.currentTimeMillis();
		PixelIntegrationUtils.polarisationCorrection(correctionArray, tth, angle, 0.9);
		long stop = System.currentTimeMillis();
		
		System.out.println(stop-start);
		System.out.println(correctionArray.getAbs(0));
		
		DoubleDataset correctionArray2 = new DoubleDataset(shape);
		correctionArray2.iadd(1);
		start = System.currentTimeMillis();
		IndexIterator it = correctionArray.getIterator();
		double val = 0;
		while (it.hasNext()) {
			val = PixelIntegrationUtils.polarisationCorrection(correctionArray2.getAbs(it.index), tth.getElementDoubleAbs(it.index), angle.getElementDoubleAbs(it.index), 0.9);
			correctionArray2.setAbs(it.index, val);
		}
		stop = System.currentTimeMillis();
		assertArrayEquals(correctionArray.getData(), correctionArray2.getData(), 1e-12);
	}

	@Test
	public void testTransmission() {
		
		IDiffractionMetadata md = getDiffractionMetadata();
		
		int[] shape = new int[]{2048,2048};
		DoubleDataset correctionArray = new DoubleDataset(shape);
		correctionArray.iadd(1);
		
		Dataset tth = PixelIntegrationUtils.generate2ThetaArrayRadians(md);
		
		long start = System.currentTimeMillis();
		PixelIntegrationUtils.detectorTranmissionCorrection(correctionArray, tth, 0.17);
		long stop = System.currentTimeMillis();
		
		System.out.println(stop-start);
		
		DoubleDataset correctionArray2 = new DoubleDataset(shape);
		correctionArray2.iadd(1);
		
		start = System.currentTimeMillis();
		IndexIterator it = correctionArray2.getIterator();
		
		while (it.hasNext()) correctionArray2.setAbs(it.index, PixelIntegrationUtils.detectorTranmissionCorrection(correctionArray2.getAbs(it.index), tth.getElementDoubleAbs(it.index),0.17));
		stop = System.currentTimeMillis();
		
		System.out.println(stop-start);
		assertArrayEquals(correctionArray.getData(), correctionArray2.getData(), 0);
		
	}
	
}
