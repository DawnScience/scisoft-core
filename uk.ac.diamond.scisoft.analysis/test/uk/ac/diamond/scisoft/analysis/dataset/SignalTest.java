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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.*;

import org.junit.Test;

public class SignalTest {

	/**
	 * Comes from the Ticket SCI#1275
	 */
	@Test
	public void testSimpleConvolveFilters() {
		AbstractDataset f = AbstractDataset.zeros(new int[] {10,10}, AbstractDataset.ARRAYFLOAT64);
		AbstractDataset g = AbstractDataset.zeros(new int[] {3,3}, AbstractDataset.ARRAYFLOAT64);
		
		
		// test against a null filter
		f.iadd(2);
		g.set(1, 1, 1);
		
		AbstractDataset result = Signal.convolve(f, g, null);
		
		assertEquals("Arrays are not equal area",  (Double) f.sum(), (Double) result.sum(), 0.1); 
		
		// set against a zeroed filter
		g.set(0, 1, 1);
		
		result = Signal.convolve(f, g, null);
		
		assertEquals("Array not zero size",  0.0, (Double) result.sum(), 0.1); 	
		
		// set against a doubling filter
		g.set(2, 1, 1);
		
		result = Signal.convolve(f, g, null);
		
		assertEquals("convolved array not twice the size",  (Double)f.sum()*2.0, (Double) result.sum(), 0.1); 
	}
	
	/**
	 * Comes from the Ticket SCI#1275
	 */
	@Test
	public void testConvolveFilters() {
		AbstractDataset f = AbstractDataset.zeros(new int[] {10,10}, AbstractDataset.ARRAYFLOAT64);
		AbstractDataset g = AbstractDataset.zeros(new int[] {3,3}, AbstractDataset.ARRAYFLOAT64);
		
		// test against a null filter
		f.iadd(1);
		g.set(1, 1, 1);
		g.set(1, 0, 1);
		g.set(1, 2, 1);
		g.set(1, 1, 0);
		g.set(1, 1, 2);
		
		AbstractDataset result = Signal.convolve(f, g, null);
		
		assertEquals("Element (0,0) is not correct",  0, result.getDouble(0,0), 0.1); 
		assertEquals("Element (1,1) is not correct",  3, result.getDouble(1,1), 0.1); 
		assertEquals("Element (2,2) is not correct",  5, result.getDouble(2,2), 0.1); 
		assertEquals("Element (3,3) is not correct",  5, result.getDouble(3,3), 0.1); 
		assertEquals("Element (6,6) is not correct",  5, result.getDouble(6,6), 0.1); 
		assertEquals("Element (9,9) is not correct",  5, result.getDouble(9,9), 0.1); 
		assertEquals("Element (10,10) is not correct",  3, result.getDouble(10,10), 0.1); 
		assertEquals("Element (11,11) is not correct",  0, result.getDouble(11,11), 0.1); 
	}
	
	/**
	 * Comes from the Ticket SCI#1275
	 */
	@Test
	public void testConvolve() {
		AbstractDataset one_d = AbstractDataset.zeros(new int[] {10}, AbstractDataset.ARRAYFLOAT64);
		AbstractDataset two_d = AbstractDataset.zeros(new int[] {10,10}, AbstractDataset.ARRAYFLOAT64);
		AbstractDataset three_d = AbstractDataset.zeros(new int[] {10,10,10}, AbstractDataset.ARRAYFLOAT64);
		
		@SuppressWarnings("unused")
		AbstractDataset result;
		
		try {
			result = Signal.convolve(one_d, one_d, null);
		} catch (Exception e) {
			fail("Should be able to convolve 2 1D arrays");
		}
		
		try {
			result = Signal.convolve(three_d, three_d, null);
		} catch (Exception e) {
			fail("Should be able to convolve 2 3D arrays");
		}
		
		try {
			result = Signal.convolve(one_d, two_d, null);
		} catch (IllegalArgumentException e) {
			// this is the correct exception in this case
		} catch (Exception e) {
			e.printStackTrace();
			fail("convolving 2 differnt shaped arrays should raise an IllegalArgumentException");
		}
		
		try {
			result = Signal.convolve(one_d, three_d, null);
		} catch (IllegalArgumentException e) {
			// this is the correct exception in this case
		} catch (Exception e) {
			e.printStackTrace();
			fail("convolving 2 differnt shaped arrays should raise an IllegalArgumentException");
		}
		
		try {
			result = Signal.convolve(two_d, three_d, null);
		} catch (IllegalArgumentException e) {
			// this is the correct exception in this case
		} catch (Exception e) {
			e.printStackTrace();
			fail("convolving 2 differnt shaped arrays should raise an IllegalArgumentException");
		}
		
		try {
			result = Signal.convolve(three_d, two_d, null);
		} catch (IllegalArgumentException e) {
			// this is the correct exception in this case
		} catch (Exception e) {
			e.printStackTrace();
			fail("convolving 2 differnt shaped arrays should raise an IllegalArgumentException");
		}
		
		try {
			result = Signal.convolve(three_d, one_d, null);
		} catch (IllegalArgumentException e) {
			// this is the correct exception in this case
		} catch (Exception e) {
			e.printStackTrace();
			fail("convolving 2 differnt shaped arrays should raise an IllegalArgumentException");
		}
		
	}
	
}
