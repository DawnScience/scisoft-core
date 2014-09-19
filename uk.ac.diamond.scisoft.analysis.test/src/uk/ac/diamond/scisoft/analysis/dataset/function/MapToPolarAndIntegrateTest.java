/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.List;

import junit.framework.TestCase;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Test;

/**
 *
 */
public class MapToPolarAndIntegrateTest extends TestCase {
	int[] shape = new int[] {500,500}; 
	Dataset d = DatasetFactory.ones(shape, Dataset.FLOAT32);
	Dataset a = DatasetFactory.ones(shape, Dataset.FLOAT32);
	
	double racc = 1e-3; // set relative accuracy within 0.1%
	double dpp = 10.;

	/**
	 * 
	 */
	@Test
	public void testMapToPolarAndIntegrate() {
		int xcentre = 250;
		int ycentre = 250;
		double rmin = 50.;
		double rmax = 200.;
		double sphi = 0.;
		double ephi = 45.;
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(xcentre,ycentre,rmin,sphi,rmax,ephi,dpp,true); // eighth of annulus
		Dataset mask = DatasetFactory.ones(new int[] {500,500}, Dataset.INT8);
		mask.setSlice(0, new int[] {260,310}, new int[] {270, 320}, new int[] {1,1});
		mp.setMask(mask);
		List<? extends Dataset> dsets = mp.value(d);
        
		double answer = Math.PI*(200.*200. - 50.*50.)/8. - 100.;
		assertEquals(answer, ((Number) dsets.get(0).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(1).sum()).doubleValue(), answer*racc);
	}

	/**
	 * test radial integration
	 */
	@Test
	public void testMapToPolarAndIntegrateRadial() {
		int xcentre = 250;
		int ycentre = 250;
		double rmin = 50.;
		double rmax = 200.;
		double sphi = 0.;
		double ephi = 45.;
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(xcentre,ycentre,rmin,sphi,rmax,ephi,dpp,true); // eighth of annulus
		Dataset dc = d.clone();
		for (int i = 0; i < shape[0]; i++) {
			for (int j = 0; j < shape[1]; j++) {
				int dx = i - xcentre;
				int dy = j - ycentre;
				double val = Math.sqrt(dx*dx + dy*dy);
				dc.set(val, j, i);
			}
		}
		mp.setMask(null);
		mp.setClip(false);
		List<? extends Dataset> dsets = mp.value(dc);
		List<? extends Dataset> asets = mp.value(a);
		for (int i = 0, imax = dsets.get(1).getSize(); i < imax; i++) {
			double answer = rmin + i/dpp; 
			double val = dsets.get(1).getDouble(i) / asets.get(1).getDouble(i);
			assertEquals(answer, val, answer*racc);
		}
	}

	/**
	 * test azimuthal integration
	 */
	@Test
	public void testMapToPolarAndIntegrateAzimuthal() {
		int xcentre = 250;
		int ycentre = 250;
		double rmin = 50.;
		double rmax = 200.;
		double sphi = 45.;
		double ephi = 90.;
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(xcentre,ycentre,rmin,sphi,rmax,ephi,dpp,true); // eighth of annulus
		Dataset dc = d.clone();
		for (int i = 0; i < shape[0]; i++) {
			for (int j = 0; j < shape[1]; j++) {
				int dx = i - xcentre;
				int dy = j - ycentre;
				double phi = Math.atan2(dy, dx);
				dc.set(Math.toDegrees(phi), j, i);
			}
		}
		mp.setMask(null);
		mp.setClip(true);
		List<? extends Dataset> dsets = mp.value(dc);
		List<? extends Dataset> asets = mp.value(a);
		double dphi = Math.toDegrees(1./(rmax*dpp));
		for (int i = 0, imax = dsets.get(0).getSize(); i < imax; i++) {
			double answer = sphi + dphi*i; 
			double val = dsets.get(0).getDouble(i) / asets.get(0).getDouble(i);
			assertEquals(answer, val, answer*racc);
		}
	}

	/**
	 * test clipping
	 */
	@Test
	public void testMapToPolarAndIntegrate2() {
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(360,360,50.,0.,200.,45., dpp, true); // eighth of annulus
		Dataset mask = DatasetFactory.ones(new int[] {500,500}, Dataset.INT8);
		mask.setSlice(0, new int[] {370,480}, new int[] {380, 490}, new int[] {1,1});
		mp.setMask(mask);
		List<? extends Dataset> dsets = mp.value(d);

		double answer = 140.*140./2. - Math.PI*(50.*50.)/8. - 100.;
		assertEquals(answer, ((Number) dsets.get(0).sum()).doubleValue(), answer*racc*10);
		assertEquals(answer, ((Number) dsets.get(1).sum()).doubleValue(), answer*racc*10);
	}

	/**
	 * test over branch cut
	 */
	@Test
	public void testMapToPolarAndIntegrate3() {
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(250,250,50.,22.5,200.,-22.5, dpp, true); // eighth of annulus
		Dataset mask = DatasetFactory.ones(new int[] {500,500}, Dataset.INT8);
		mask.setSlice(0, new int[] {245,410}, new int[] {255, 420}, new int[] {1,1});
		mp.setMask(mask);
		List<? extends Dataset> dsets = mp.value(d);

		double answer = Math.PI*(200.*200. - 50.*50.)/8. - 100.;
		assertEquals(answer, ((Number) dsets.get(0).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(1).sum()).doubleValue(), answer*racc);
	}

}
