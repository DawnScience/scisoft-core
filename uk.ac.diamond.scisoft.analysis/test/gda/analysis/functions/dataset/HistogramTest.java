/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

package gda.analysis.functions.dataset;

import org.junit.Test;

import gda.analysis.DataSet;
import junit.framework.TestCase;

/**
 *
 */
public class HistogramTest extends TestCase {

	DataSet d = null;
	
	/**
	 */
	@Override
	public void setUp() {
		d = DataSet.arange(1.0, 2048.0, 1.0);
	}
	
	/**
	 * 
	 */
	@Test
	public void testHistogram() {
		Histogram histo = new Histogram(2048);
		
		DataSet pd = d.exec(histo).get(0);
		
		assertEquals(2048, pd.getSize(),0); // within 0.5% accuracy
		assertEquals(1,pd.get(1),0);
		assertEquals(1,pd.get(512),0);
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram2() {
		Histogram histo = new Histogram(1024);
		DataSet pd = d.exec(histo).get(0);
		assertEquals(1024,pd.getSize(),0);
		assertEquals(2,pd.get(1),0);
		assertEquals(2,pd.get(512),0);
	}
	
	/**
	 * 
	 */
	@Test
	public void testHistogram3() {
		Histogram histo = new Histogram(256);
		DataSet pd = d.exec(histo).get(0);
		assertEquals(256,pd.getSize(),0);
		assertEquals(8,pd.get(1),0);
		assertEquals(8,pd.get(128),0);
	}
	
	/**
	 * 
	 */
	@Test
	public void testHistogram4() {
		Histogram histo = new Histogram(205);
		DataSet pd = d.exec(histo).get(0);
		assertEquals(205,pd.getSize(),0);
		assertEquals(10,pd.get(1),0);
		assertEquals(10,pd.get(128),0);		
	}
	
	/**
	 * 
	 */
	@Test
	public void testHistogram5() {
		Histogram histo = new Histogram(1024,1.0,1024.0);
		histo.setIgnoreOutliers(false);

		DataSet pd = d.exec(histo).get(0);
		assertEquals(1024,pd.getSize(),0);
		assertEquals(1,pd.get(1),0);
		assertEquals(1,pd.get(512),0);
		assertEquals(1024,pd.get(1023),0);
	}	
	
	/**
	 * 
	 */
	@Test
	public void testHistogram6() {
		Histogram histo = new Histogram(1024,2.0,1024.0);
		histo.setIgnoreOutliers(false);
		DataSet pd = d.exec(histo).get(0);
		assertEquals(1024,pd.getSize(),0);
		assertEquals(2,pd.get(0),0);
		assertEquals(1,pd.get(512),0);
		assertEquals(1024,pd.get(1023),0);
	}	
	
	/**
	 * 
	 */
	@Test
	public void testHistogram7() {
		Histogram histo = new Histogram(1024,2.0,1024.0,true);
		DataSet pd = d.exec(histo).get(0);
		assertEquals(1024,pd.getSize(),0);
		assertEquals(1,pd.get(0),0);
		assertEquals(1,pd.get(512),0);
		assertEquals(1,pd.get(1023),0);
	}
	
}
