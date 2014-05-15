/*
 * Copyright 2011 Diamond Light Source Ltd.
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

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;

/**
 *
 */
public class MapToPolarAndIntegrateTest extends TestCase {
	int[] shape = new int[] {500,500}; 
	AbstractDataset d = AbstractDataset.ones(shape, AbstractDataset.FLOAT32);
	AbstractDataset a = AbstractDataset.ones(shape, AbstractDataset.FLOAT32);
	
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
		AbstractDataset mask = AbstractDataset.ones(new int[] {500,500}, Dataset.INT8);
		mask.setSlice(0, new int[] {260,310}, new int[] {270, 320}, new int[] {1,1});
		mp.setMask(mask);
		List<AbstractDataset> dsets = mp.value(d);
        
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
		
		for (int i = 0; i < shape[0]; i++)
			for (int j = 0; j < shape[1]; j++) {
				int dx = i - xcentre;
				int dy = j - ycentre;
				double val = Math.sqrt(dx*dx + dy*dy);
				d.set(val, new int[] {j,i});
			}
		mp.setMask(null);
		mp.setClip(false);
		List<AbstractDataset> dsets = mp.value(d);
		List<AbstractDataset> asets = mp.value(a);
		for (int i = 0; i < dsets.get(1).getShape()[0]; i++) {
			double answer = rmin + i/dpp; 
			double val = dsets.get(1).getDouble(new int[] {i}) / asets.get(1).getDouble(new int[] {i});
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
		for (int i = 0; i < shape[0]; i++)
			for (int j = 0; j < shape[1]; j++) {
				int dx = i - xcentre;
				int dy = j - ycentre;
				double phi = Math.atan2(dy, dx);
				d.set(Math.toDegrees(phi), new int[] {j,i});
			}
		mp.setMask(null);
		mp.setClip(true);
		List<AbstractDataset> dsets = mp.value(d);
		List<AbstractDataset> asets = mp.value(a);
		double dphi = Math.toDegrees(1./(rmax*dpp));
		for (int i = 0; i < dsets.get(0).getShape()[0]; i++) {
			double answer = sphi + dphi*i; 
			double val = dsets.get(0).getDouble(new int[] {i}) / asets.get(0).getDouble(new int[] {i});
			assertEquals(answer, val, answer*racc);
		}
		
	}

	/**
	 * test clipping
	 */
	@Test
	public void testMapToPolarAndIntegrate2() {
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(360,360,50.,0.,200.,45., dpp, true); // eighth of annulus
		AbstractDataset mask = AbstractDataset.ones(new int[] {500,500}, Dataset.INT8);
		mask.setSlice(0, new int[] {370,480}, new int[] {380, 490}, new int[] {1,1});
		mp.setMask(mask);
		List<AbstractDataset> dsets = mp.value(d);

		double answer = 140.*140./2. - Math.PI*(50.*50.)/8. - 100.;
		assertEquals(answer, ((Number) dsets.get(0).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(1).sum()).doubleValue(), answer*racc);
	}

	/**
	 * test over branch cut
	 */
	@Test
	public void testMapToPolarAndIntegrate3() {
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(250,250,50.,22.5,200.,-22.5, dpp, true); // eighth of annulus
		AbstractDataset mask = AbstractDataset.ones(new int[] {500,500}, Dataset.INT8);
		mask.setSlice(0, new int[] {245,410}, new int[] {255, 420}, new int[] {1,1});
		mp.setMask(mask);
		List<AbstractDataset> dsets = mp.value(d);

		double answer = Math.PI*(200.*200. - 50.*50.)/8. - 100.;
		assertEquals(answer, ((Number) dsets.get(0).sum()).doubleValue(), answer*racc);
		assertEquals(answer, ((Number) dsets.get(1).sum()).doubleValue(), answer*racc);
	}

}
