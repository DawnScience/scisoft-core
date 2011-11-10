/*-
 * Copyright © 2010 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset.function;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

public class BicubicInterpolatorTest {

	@Test
	public void testBicubicInterpolate() {
		double[][] p = new double[4][4];
		p[0][0] = 0.0; p[0][1] = 0.0; p[0][2] = 0.0; p[0][3] = 0.0;
		p[1][0] = 0.0; p[1][1] = 1.0; p[1][2] = 1.0; p[1][3] = 0.0;
		p[2][0] = 0.0; p[2][1] = 1.0; p[2][2] = 1.0; p[2][3] = 0.0;
		p[3][0] = 0.0; p[3][1] = 0.0; p[3][2] = 0.0; p[3][3] = 0.0;
		
		BicubicInterpolator bicube = new BicubicInterpolator(new int[] {1,2});
		
		
		bicube.calculateParameters(p);
		
		assertEquals(1.265,bicube.bicubicInterpolate (0.5, 0.5),0.001);		
		
	}

	@Test
	public void testGenerateSurroundingPoints() {
		
		DoubleDataset ds = DoubleDataset.arange(0.0, 9.0, 1.0);
		ds = (DoubleDataset) ds.reshape(3,3);
		
		BicubicInterpolator bicube = new BicubicInterpolator(new int[] {20,20});
		double[][] val = bicube.generateSurroundingPoints(0, 0, ds);
		
		assertEquals(0.0, val[0][0], 0.1);	
		assertEquals(0.0, val[1][1], 0.1);	
		assertEquals(4.0, val[2][2], 0.1);	
		assertEquals(8.0, val[3][3], 0.1);	
		
		val = bicube.generateSurroundingPoints(1, 1, ds);
		
		assertEquals(0.0, val[0][0], 0.1);	
		assertEquals(4.0, val[1][1], 0.1);	
		assertEquals(8.0, val[2][2], 0.1);	
		assertEquals(8.0, val[3][3], 0.1);	
		
	}
	
	@Test
	public void testValue() {
		DoubleDataset ds = DoubleDataset.arange(0.0, 9.0, 1.0);
		ds = (DoubleDataset) ds.reshape(3,3);
		
		BicubicInterpolator bicube = new BicubicInterpolator(new int[] {5,5});
		List<AbstractDataset> ds2 = bicube.value(ds);
		
		ds2.get(0).peakToPeak();
		
	}

}
