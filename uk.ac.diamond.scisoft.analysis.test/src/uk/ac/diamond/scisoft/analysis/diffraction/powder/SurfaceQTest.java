/*
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.SurfaceQ;

public class SurfaceQTest {

	// The sample is at an angle of 1° to the vertical
	static final double alpha = Math.PI/180.;
	DoubleDataset sampleNormal;
	SurfaceQ testQ;
	
	@Before
	public void setUp() throws Exception {
		testQ = new SurfaceQ();
		sampleNormal = (DoubleDataset) DatasetFactory.createFromList(Arrays.asList(0., Math.cos(alpha), -Math.sin(alpha)));
		testQ.setSurfaceNormal(sampleNormal);
	}

	@Test
	public void testQPerpendicularDoubleDatasetDoubleDatasetDouble() {
		double qPerpTrig = 0, qPerpVect = 0;
		classCommonCode(true, qPerpTrig, qPerpVect);
		assertEquals(qPerpTrig, qPerpVect, 1e-3);
	}

	@Test
	public void testQParallelDoubleDatasetDoubleDatasetDouble() {
		double qPerpTrig = 0, qPerpVect = 0;
		classCommonCode(true, qPerpTrig, qPerpVect);
		assertEquals(qPerpTrig, qPerpVect, 1e-3);
	}

	@Test
	public void testQPerpendicularDoubleDatasetDoubleDatasetDoubleDatasetDouble() {
		double qPerpTrig = 0, qPerpVect = 0;
		staticCommonCode(true, qPerpTrig, qPerpVect);
		assertEquals(qPerpTrig, qPerpVect, 1e-3);
	}

	@Test
	public void testQParallelDoubleDatasetDoubleDatasetDoubleDatasetDouble() {
		double qParaTrig = 0, qParaVect = 0;
		staticCommonCode(false, qParaTrig, qParaVect);
		assertEquals(qParaTrig, qParaVect, 1e-3);
	}
	
	void classCommonCode(boolean getPerp, double trig, double vect) {
		// Let the scattered beam lie at (0.15, 0.08, 2.0)
		DoubleDataset scatteredBeam = DatasetFactory.createFromList(DoubleDataset.class, Arrays.asList(0.15, 0.08, 2.0));
		DoubleDataset incidentBeam =  DatasetFactory.createFromList(DoubleDataset.class, Arrays.asList(0.0, 0.0, 1.0));
		double energy = 12.0; // 12 keV ~= 1 Å

		double qPerpTrig = 0, qParaTrig = 0;
		trigCalculation(scatteredBeam, energy, qPerpTrig, qParaTrig);
				
		double qPerpVect = testQ.qPerpendicular(scatteredBeam, incidentBeam, energy),
				qParaVect = testQ.qParallel(scatteredBeam, incidentBeam, energy);

		if (getPerp) {
			trig = qPerpTrig;
			vect = qPerpVect;
		} else {
			trig = qParaTrig;
			vect = qParaVect;
		}
		
	}

	void staticCommonCode(boolean getPerp, double trig, double vect) {
		// Let the scattered beam lie at (0.05, 0.1, 2.0)
		DoubleDataset scatteredBeam = DatasetFactory.createFromList(DoubleDataset.class, Arrays.asList(0.05, 0.1, 2.0));
		DoubleDataset incidentBeam =  DatasetFactory.createFromList(DoubleDataset.class, Arrays.asList(0.0, 0.0, 1.0));
		double energy = 12.0; // 12 keV ~= 1 Å

		double qPerpTrig = 0, qParaTrig = 0;
		trigCalculation(scatteredBeam, energy, qPerpTrig, qParaTrig);
		
		double qPerpVect = SurfaceQ.qPerpendicular(scatteredBeam, incidentBeam, sampleNormal, energy),
				qParaVect = SurfaceQ.qParallel(scatteredBeam, incidentBeam, sampleNormal, energy);

		if (getPerp) {
			trig = qPerpTrig;
			vect = qPerpVect;
		} else {
			trig = qParaTrig;
			vect = qParaVect;
		}
	}

	private static void trigCalculation(DoubleDataset scatteredBeam, double energy, double qPerpTrig, double qParaTrig) {
		// trigonometric answer
		double psi0 = Math.atan2(scatteredBeam.get(0), scatteredBeam.get(2));
		double beta0 = Math.atan2(scatteredBeam.get(1), addQuadrature(scatteredBeam.get(0), scatteredBeam.get(2)));
		double beta = beta0 - alpha * Math.cos(psi0);
		double psi = psi0;
		
		double qx = Math.cos(beta) * Math.cos(psi) - Math.cos(alpha);
		double qy = Math.cos(beta) * Math.sin(psi);
		double qz = Math.sin(beta) + Math.sin(alpha);
		
		double k = energy/1.9732697879296464; // wavenumber in 1/Å
		qx *= k;
		qy *= k;
		qz *= k;
		
		qPerpTrig = qz;
		qParaTrig = addQuadrature(qx, qy);
	}
	
	private static double addQuadrature(double a, double b) {
		return Math.sqrt(a*a + b*b);
	}
	
}
