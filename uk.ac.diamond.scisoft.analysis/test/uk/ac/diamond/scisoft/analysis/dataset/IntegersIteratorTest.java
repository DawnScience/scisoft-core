/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntegersIteratorTest {
	AbstractDataset a, b;

	@Before
	public void setUpClass() {
		a = new DoubleDataset(new double[] { 0, 1, 3, 5, -7, -9 });
		b = new DoubleDataset(new double[] { 0.01, 1.2, 2.9, 5, -7.1, -9 });
	}

	@Test
	public void testShapes() {
		IntegerDataset s = new IntegerDataset(2, 3, 4);
		IntegerDataset t = new IntegerDataset(2, 3, 4);
		IntegerDataset u = new IntegerDataset(2, 3, 4);

		IntegersIterator iter;
		int[] shape;

		shape = new int[] {10, 20, 30};
		iter = new IntegersIterator(new IntegerDataset[] {s, t, u}, shape);
		Assert.assertArrayEquals("Shape", new int[] {2, 3, 4}, iter.getShape());
		
		iter = new IntegersIterator(new IntegerDataset[] {null, t, u}, shape);
		Assert.assertArrayEquals("Shape", new int[] {10, 2, 3, 4}, iter.getShape());

		iter = new IntegersIterator(new IntegerDataset[] {s, null, u}, shape);
		Assert.assertArrayEquals("Shape", new int[] {2, 3, 4, 20}, iter.getShape());

		iter = new IntegersIterator(new IntegerDataset[] {s, t, null}, shape);
		Assert.assertArrayEquals("Shape", new int[] {2, 3, 4, 30}, iter.getShape());

		shape = new int[] {10, 20, 30, 40, 50};
		iter = new IntegersIterator(new IntegerDataset[] {s, t, u}, shape);
		Assert.assertArrayEquals("Shape", new int[] {2, 3, 4, 40, 50}, iter.getShape());
		
		iter = new IntegersIterator(new IntegerDataset[] {null, t, u}, shape);
		Assert.assertArrayEquals("Shape", new int[] {10, 2, 3, 4, 40, 50}, iter.getShape());

		iter = new IntegersIterator(new IntegerDataset[] {s, null, u}, shape);
		Assert.assertArrayEquals("Shape", new int[] {2, 3, 4, 20, 40, 50}, iter.getShape());

		iter = new IntegersIterator(new IntegerDataset[] {s, t, null}, shape);
		Assert.assertArrayEquals("Shape", new int[] {2, 3, 4, 30, 40, 50}, iter.getShape());
}

	@Test
	public void testEqualTo() {
		AbstractDataset c = a.clone().reshape(2, 3);
		IntegerDataset s = new IntegerDataset(new int[] {0, 1, 0}, null);
		IntegerDataset t = new IntegerDataset(new int[] {0, 2, 1}, null);

		List<Double> inds = new ArrayList<Double>();

		IntegersIterator iter = new IntegersIterator(new IntegerDataset[] {s, t}, c.shape);
		int[] pos = iter.getPos();
		while (iter.hasNext())
			inds.add(c.getDouble(pos));
		checkDatasets((DoubleDataset) AbstractDataset.createFromList(inds),
				new DoubleDataset(new double[] {0, -9, 1}));
		inds.clear();

		iter = new IntegersIterator(new IntegerDataset[] {s, null}, c.shape);
		pos = iter.getPos();
		while (iter.hasNext())
			inds.add(c.getDouble(pos));
		checkDatasets((DoubleDataset) AbstractDataset.createFromList(inds),
				new DoubleDataset(new double[] {0, 1, 3, 5, -7, -9, 0, 1, 3}));
		inds.clear();

		iter = new IntegersIterator(new IntegerDataset[] {null, t}, c.shape);
		pos = iter.getPos();
		while (iter.hasNext())
			inds.add(c.getDouble(pos));
		checkDatasets((DoubleDataset) AbstractDataset.createFromList(inds),
				new DoubleDataset(new double[] {0, 3, 1, 5, -9, -7}));
		inds.clear();


		s = new IntegerDataset(new int[] {0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0}, 2, 3, 2);
		t = new IntegerDataset(new int[] {2, 1, 1, 0, 1, 2, 1, 0, 0, 0, 1, 0}, 2, 3, 2);

		iter = new IntegersIterator(new IntegerDataset[] {s, t}, c.shape);
		pos = iter.getPos();
		while (iter.hasNext())
			inds.add(c.getDouble(pos));
		checkDatasets((DoubleDataset) AbstractDataset.createFromList(inds),
				new DoubleDataset(new double[] {3, -7, 1, 0, -7, 3, -7, 5, 5, 0, 1, 0}));
		inds.clear();

		iter = new IntegersIterator(new IntegerDataset[] {s, null}, c.shape);
		pos = iter.getPos();
		while (iter.hasNext())
			inds.add(c.getDouble(pos));
		checkDatasets((DoubleDataset) AbstractDataset.createFromList(inds),
				new DoubleDataset(new double[] {0, 1, 3, 5, -7, -9, 0, 1, 3, 0, 1, 3, 5, -7, -9, 0, 1, 3, 5, -7, -9, 5, -7, -9, 5, -7, -9, 0, 1, 3, 0, 1, 3, 0, 1, 3}));
		inds.clear();

		iter = new IntegersIterator(new IntegerDataset[] {null, t}, c.shape);
		pos = iter.getPos();
		while (iter.hasNext())
			inds.add(c.getDouble(pos));
		checkDatasets((DoubleDataset) AbstractDataset.createFromList(inds),
				new DoubleDataset(new double[] {3, 1, 1, 0, 1, 3, 1, 0, 0, 0, 1, 0, -9, -7, -7, 5, -7, -9, -7, 5, 5, 5, -7, 5}));
		inds.clear();
	}

	private void checkDatasets(DoubleDataset calc, DoubleDataset expected) {
		TestUtils.assertDatasetEquals(calc, expected, 0.1, 1e-5);
	}
}
