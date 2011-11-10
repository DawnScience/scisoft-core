/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.functions;

import static org.junit.Assert.assertEquals;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;


@RunWith(JUnitParamsRunner.class)
public class PeakProfileTest {
	
	@Test
	@Parameters({
		"Simple Peak,  0.01, 0.0, 100.0, 50.0, 10.0, 2.0",
		"Smaller Area, 0.01, 0.0, 100.0, 50.0, 5.0, 2.0",
		"Smallest Area, 0.01, 0.0, 100.0, 50.0, 2.0, 2.0",
		"Smallest FWHM, 0.01, 0.0, 100.0, 50.0, 10.0, 1.0",
		"Big FWHM, 0.01, 0.0, 100.0, 50.0, 10.0, 5.0",
		"Biggest FWHM, 0.01, 0.0, 100.0, 50.0, 10.0, 10.0",
		"Huge area, 0.01, 0.0, 100.0, 50.0, 1000.0, 2.0",
		"Huge Area - small FWHM - shifted left, 0.01, 0.0, 100.0, 20.0, 1000.0, 1.0",
		"Huge Area - small FWHM - shifted Right, 0.01, 0.0, 100.0, 70.0, 1000.0, 1.0",})
	public void testGaussian(String description, double step, double min, double max, double mean, double area, double fwhm) {
		
		DoubleDataset axis = DoubleDataset.arange(min, max, step);
		Gaussian gaussian = new Gaussian(mean, fwhm, area);
		DoubleDataset data = gaussian.makeDataset(axis);
		
		assertEquals("Gaussain Area for '"+description+"' is not correct", gaussian.getArea(), ((Double)data.sum()*step), step);
		assertEquals("Gaussian Position for '"+description+"' is not correct", gaussian.getPosition(), axis.get(data.maxPos()), step);
		
		double threshold = (Double)data.max()/2.0;
		
		// needs to start on one as otherwise this is not inclusive
		int count = 1;
		for(int i = 0; i < data.getShape()[0]; i++) {
			if ( data.getDouble(i) > threshold ) {
				count++;
			}
		}
		double width = count * step;
		
		assertEquals("Gaussian Width for '"+description+"' is not correct", gaussian.getFWHM(), width, step);		
		
	}
	
	
	@Test	
	@Parameters({
		"Simple Peak,  0.01, 0.0, 10000.0, 5000.0, 10.0, 2.0",
		"Smaller Area, 0.01, 0.0, 10000.0, 5000.0, 5.0, 2.0",
		"Smallest Area, 0.01, 0.0, 10000.0, 5000.0, 2.0, 2.0",
		"Smallest FWHM, 0.01, 0.0, 10000.0, 5000.0, 10.0, 1.0",
		"Big FWHM, 0.01, 0.0, 10000.0, 5000.0, 10.0, 5.0",
		"Biggest FWHM, 0.01, 0.0, 10000.0, 5000.0, 10.0, 10.0",
		"Huge area, 0.1, 0.0, 100000.0, 50000.0, 1000.0, 2.0",
		"Huge Area - small FWHM - shifted left, 0.1, 0.0, 10000.0, 3000.0, 1000.0, 1.0",
		"Huge Area - small FWHM - shifted Right, 0.1, 0.0, 10000.0, 7000.0, 1000.0, 1.0"})
	public void testLorentzian(String description, double step, double min, double max, double mean ,double area ,double fwhm) {
		
		
		DoubleDataset axis = DoubleDataset.arange(min, max, step);
		Lorentzian lorentzian = new Lorentzian(mean, fwhm, area);
		DoubleDataset data = lorentzian.makeDataset(axis);
		
		assertEquals("Lorentzian Area for '"+description+"' is not correct", lorentzian.getArea(), ((Double)data.sum()*step), step);
		assertEquals("Lorentzian Position for '"+description+"' is not correct", lorentzian.getPosition(), axis.get(data.maxPos()), step);
		
		double threshold = (Double)data.max()/2.0;
		
		// needs to start on one as otherwise this is not inclusive
		int count = 1;
		for(int i = 0; i < data.getShape()[0]; i++) {
			if ( data.getDouble(i) > threshold ) {
				count++;
			}
		}
		double width = count * step;
		
		assertEquals("Lorentzian Width for '"+description+"' is not correct", lorentzian.getFWHM(), width, step);		
		
	}
	
	@Test
	@Parameters({
		"Equal Mix - unequal FWHM, 0.01, 0.0, 10000.0, 5000.0, 10.0, 10.0, 5.0, 0.5",
		"Equal Mix - Equal FWHM, 0.1, 0.0, 100000.0, 50000.0, 1000.0, 5.0, 5.0, 0.5",
		"All Gausian, 0.01, 0.0, 10000.0, 5000.0, 10.0, 10.0, 5.0, 0.0",
		"All Lorentzian, 0.01, 0.0, 10000.0, 5000.0, 10.0, 10.0, 5.0, 1.0",
		"Equal mix - small FWHM, 0.01, 0.0, 10000.0, 5000.0, 10.0, 2.0, 2.0, 0.5",})
	public void testPseudoVoigt(String description, double step ,double min ,double max ,double mean ,double area ,double gfwhm ,double lfwhm ,double mix) {
		
		DoubleDataset axis = DoubleDataset.arange(min, max, step);
		PseudoVoigt pseudoVoigt = new PseudoVoigt(mean, gfwhm, lfwhm, area, mix);
		DoubleDataset data = pseudoVoigt.makeDataset(axis);
		
		assertEquals("PseudoVoigt Area for '"+description+"' is not correct", pseudoVoigt.getArea(), ((Double)data.sum()*step), step);
		assertEquals("PseudoVoigt Position for '"+description+"' is not correct", pseudoVoigt.getPosition(), axis.get(data.maxPos()), step);
		
		double threshold = (Double)data.max()/2.0;
		
		// needs to start on one as otherwise this is not inclusive
		int count = 1;
		for(int i = 0; i < data.getShape()[0]; i++) {
			if ( data.getDouble(i) > threshold ) {
				count++;
			}
		}
		double width = count * step;
		
		assertEquals("PseudoVoigt Width for '"+description+"' is not correct", pseudoVoigt.getFWHM(), width, (Math.abs(gfwhm-lfwhm)/2.0)+ step);		
		
	}
	
	@Test
	@Parameters({
		"Simple Peak, 0.01, 0.0, 10000.0, 5000.0, 10.0, 5.0, 1.0",
		"Bigger Peak, 0.1, 0.0, 100000.0, 50000.0, 1000.0, 5.0, 1.0",
		"Small FWHM, 0.01, 0.0, 10000.0, 5000.0, 10.0, 2.0, 1.0",})
	public void testPearsonVII(String description, double step ,double min ,double max ,double mean ,double area ,double fwhm ,double mix) {
		
		DoubleDataset axis = DoubleDataset.arange(min, max, step);
		PearsonVII pearsonVII = new PearsonVII(new double[] {mean, fwhm, mix, area} );
		DoubleDataset data = pearsonVII.makeDataset(axis);
		
		assertEquals("PearsonVII Area for '"+description+"' is not correct", pearsonVII.getArea(), ((Double)data.sum()*step), step);
		assertEquals("PearsonVII Position for '"+description+"' is not correct", pearsonVII.getPosition(), axis.get(data.maxPos()), step);
		
		double threshold = (Double)data.max()/2.0;
		
		// needs to start on one as otherwise this is not inclusive
		int count = 1;
		for(int i = 0; i < data.getShape()[0]; i++) {
			if ( data.getDouble(i) > threshold ) {
				count++;
			}
		}
		double width = count * step;
		
		assertEquals("PearsonVII FWHM for '"+description+"' is not correct", pearsonVII.getFWHM(), width, step);		
		
	}
	
	
	
}
