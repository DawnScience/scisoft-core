/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

/**
 *
 */
public class MapToPolarAndSimpleIntegrateTest extends TestCase {
	int[] shape = new int[] {500,500}; 
	AbstractDataset d = AbstractDataset.zeros(shape, AbstractDataset.FLOAT32);
	
	boolean interpolate = false; // use simple integration algorithm
	double racc = 1e-2; // set relative accuracy within 1.0%

	/**
	 */
	@Override
	public void setUp() {
		d.fill(1.);
	}

	/**
	 * 
	 */
	@Test
	public void testMapToPolarAndSimpleIntegrate() {
		int xcentre = 250;
		int ycentre = 250;
		double rmin = 50.;
		double rmax = 200.;
		double sphi = 0.;
		double ephi = 45.;
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(xcentre,ycentre,rmin,sphi,rmax,ephi,1.,true); // eighth of annulus
		AbstractDataset mask = AbstractDataset.ones(new int[] {500,500}, AbstractDataset.INT8);
		mask.setSlice(0, new int[] {260,310}, new int[] {270, 320}, new int[] {1,1});
		mp.setMask(mask);
		mp.setClip(true);
		mp.setInterpolate(interpolate);
		List<AbstractDataset> dsets = mp.value(d);
        
		double answer = Math.PI*(200.*200. - 50.*50.)/8. - 100.;
		assertEquals(answer, ((Number) dsets.get(0).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(1).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(2).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(3).sum()).doubleValue(), answer*racc);
	}

	/**
	 * test radial integration
	 */
	@Test
	public void testMapToPolarAndSimpleIntegrateRadial() {
		int xcentre = 250;
		int ycentre = 250;
		double rmin = 50.;
		double rmax = 200.;
		double sphi = 0.;
		double ephi = 45.;
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(xcentre,ycentre,rmin,sphi,rmax,ephi,1.,true); // eighth of annulus
		
		for (int i = 0; i < shape[0]; i++)
			for (int j = 0; j < shape[1]; j++) {
				int dx = i - xcentre;
				int dy = j - ycentre;
				double val = Math.sqrt(dx*dx + dy*dy);
				d.set(val, new int[] {j,i});
			}
		mp.setMask(null);
		mp.setClip(false);
		mp.setInterpolate(interpolate);
		List<AbstractDataset> dsets = mp.value(d);
		for (int i = 0; i < dsets.get(1).getShape()[0]; i++) {
			double answer = rmin + i; 
			double val = dsets.get(1).getDouble(new int[] {i}) / dsets.get(3).getDouble(new int[] {i});
			assertEquals(answer, val, answer*racc);
		}
	}

	/**
	 * test azimuthal integration
	 */
	@Test
	public void testMapToPolarAndSimpleIntegrateAzimuthal() {
		int xcentre = 250;
		int ycentre = 250;
		double rmin = 50.;
		double rmax = 200.;
		double sphi = 45.;
		double ephi = 90.;
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(xcentre,ycentre,rmin,sphi,rmax,ephi,1.,true); // eighth of annulus
		for (int i = 0; i < shape[0]; i++)
			for (int j = 0; j < shape[1]; j++) {
				int dx = i - xcentre;
				int dy = j - ycentre;
				double phi = Math.atan2(dy, dx);
				d.set(Math.toDegrees(phi), new int[] {j,i});
			}
		mp.setMask(null);
		mp.setClip(true);
		mp.setInterpolate(interpolate);
		List<AbstractDataset> dsets = mp.value(d);
		double dphi = Math.toDegrees(1./rmax);
		for (int i = 0; i < dsets.get(0).getShape()[0]; i++) {
			double answer = sphi + dphi*i; 
			double val = dsets.get(0).getDouble(new int[] {i}) / dsets.get(2).getDouble(new int[] {i});
			assertEquals(answer, val, answer*racc);
		}
		
	}

	/**
	 * test clipping
	 */
	@Test
	public void testMapToPolarAndSimpleIntegrate2() {
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(360,360,50.,0.,200.,45., 1., true); // eighth of annulus
		AbstractDataset mask = AbstractDataset.ones(new int[] {500,500}, AbstractDataset.INT8);
		mask.setSlice(0, new int[] {370,480}, new int[] {380, 490}, new int[] {1,1});
		mp.setMask(mask);
		mp.setClip(true);
		mp.setInterpolate(interpolate);
		List<AbstractDataset> dsets = mp.value(d);

		double answer = 140.*140./2. - Math.PI*(50.*50.)/8. - 100.;
		assertEquals(answer, ((Number) dsets.get(0).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(1).sum()).doubleValue(), answer*racc);
	}

	/**
	 * test over branch cut
	 */
	@Test
	public void testMapToPolarAndSimpleIntegrate3() {
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(250,250,50.,22.5,200.,-22.5, 1., true); // eighth of annulus
		AbstractDataset mask = AbstractDataset.ones(new int[] {500,500}, AbstractDataset.INT8);
		mask.setSlice(0, new int[] {245,410}, new int[] {255, 420}, new int[] {1,1});
		mp.setMask(mask);
		mp.setClip(true);
		mp.setInterpolate(interpolate);
		List<AbstractDataset> dsets = mp.value(d);

		double answer = Math.PI*(200.*200. - 50.*50.)/8. - 100.;
		assertEquals(answer, ((Number) dsets.get(0).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(1).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(2).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(3).sum()).doubleValue(), answer*racc);
	}

}
