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

import junit.framework.TestCase;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.junit.Test;

/**
 *
 */
public class HistogramTest extends TestCase {

	DoubleDataset d = null;

	/**
	 */
	@Override
	public void setUp() {
		d = (DoubleDataset) DatasetFactory.createRange(1.0, 2048.0, 1.0, Dataset.FLOAT64);
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram() {
		Histogram histo = new Histogram(2048);
		Dataset pd = histo.value(d).get(0);

		assertEquals(2048, pd.getSize());
		assertEquals(1, pd.getInt(1));
		assertEquals(1, pd.getInt(512));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram2() {
		Histogram histo = new Histogram(1024);
		Dataset pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(2, pd.getInt(1));
		assertEquals(2, pd.getInt(512));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram3() {
		Histogram histo = new Histogram(256);
		Dataset pd = histo.value(d).get(0);

		assertEquals(256, pd.getSize());
		assertEquals(8, pd.getInt(1));
		assertEquals(8, pd.getInt(128));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram4() {
		Histogram histo = new Histogram(205);
		Dataset pd = histo.value(d).get(0);

		assertEquals(205, pd.getSize());
		assertEquals(10, pd.getInt(1));
		assertEquals(10, pd.getInt(128));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram5() {
		Histogram histo = new Histogram(1024, 1.0, 1024.0);
		histo.setIgnoreOutliers(false);
		Dataset pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(1, pd.getInt(1));
		assertEquals(1, pd.getInt(512));
		assertEquals(1024, pd.getInt(1023));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram6() {
		Histogram histo = new Histogram(1024, 2.0, 1024.0);
		histo.setIgnoreOutliers(false);
		Dataset pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(2, pd.getInt(0));
		assertEquals(1, pd.getInt(512));
		assertEquals(1024, pd.getInt(1023));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram7() {
		Histogram histo = new Histogram(1024, 2.0, 1024.0, true);
		Dataset pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(1, pd.getInt(0));
		assertEquals(1, pd.getInt(512));
		assertEquals(1, pd.getInt(1023));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram8() {
		Histogram histo = new Histogram(50);
		Dataset pd = histo.value(DatasetUtils.linSpace(0, 100, 101, Dataset.INT32)).get(0);

		assertEquals(50, pd.getSize());
		assertEquals(2, pd.getInt(0));
		assertEquals(2, pd.getInt(25));
		assertEquals(3, pd.getInt(49));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramSpeed() {
		long start = 0;

		Histogram h = new Histogram(50);
		Dataset d = DatasetUtils.linSpace(0, 100, 500000, Dataset.FLOAT64);
		
		Dataset a  = null;

//		for (int i = 0; i < 4; i++)
			a = h.value(d).get(0);

		start = -System.nanoTime();
		for (int i = 0; i < 4; i++) {
			h = new Histogram(50);
			a = h.value(d).get(0);
		}
		start += System.nanoTime();

		System.out.printf("H = %s, %sms\n", a.sum().toString(), start*1e-6);
	}
}
