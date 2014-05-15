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

package uk.ac.diamond.scisoft.analysis.dataset;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

public class LinearAlgebraTest {

	private static boolean close(Number a, double b) {
		double x = a.doubleValue();
		return x == 0 ? Math.abs(b) < 10e-6 : Math.abs(x - b) < 10e-6*x;
	}

	@Test
	public void testTensorDot() {
		AbstractDataset a = AbstractDataset.arange(60, Dataset.FLOAT32).reshape(3, 4, 5);
		AbstractDataset b = AbstractDataset.arange(24, Dataset.INT16).reshape(4, 3, 2);

		long start;
		start = -System.nanoTime();
		AbstractDataset c = LinearAlgebra.tensorDotProduct(a, b, new int[] {1, 0}, new int[] {0,1});
		start += System.nanoTime();

		System.out.printf("Time taken %dus\n", start/1000);

		Assert.assertArrayEquals("Shape", new int[] {5, 2}, c.getShape());
		Assert.assertEquals("Type", Dataset.FLOAT32, c.getDtype());

		AbstractDataset d = new DoubleDataset(new double[] { 4400., 4730.,
			4532.,  4874., 4664., 5018., 4796.,  5162., 4928.,  5306. }, 5, 2);
		Assert.assertTrue("Data does not match", d.cast(c.getDtype()).equals(c));

		int n = 16;
		a = AbstractDataset.arange(20*n, Dataset.FLOAT32).reshape(n, 4, 5);
		b = AbstractDataset.arange(8*n, Dataset.INT16).reshape(4, n, 2);
		start = -System.nanoTime();
		c = LinearAlgebra.tensorDotProduct(a, b, 0, 1);
		start += System.nanoTime();

		long nstart = -System.nanoTime();
		d = LinearAlgebra.tensorDotProduct(a, b, new int[] {0}, new int[] {1});
		nstart += System.nanoTime();
		System.out.printf("Time taken %dus %dus\n", start/1000, nstart/1000);

		Assert.assertTrue("Data does not match", d.equals(c));
	}

	@Test
	public void testDot() {
		AbstractDataset a = AbstractDataset.arange(10, Dataset.FLOAT32);
		AbstractDataset b = AbstractDataset.arange(-6, 4, 1, Dataset.INT16);

		long start;
		start = -System.nanoTime();
//		AbstractDataset c = LinearAlgebra.tensorDotProduct(a, b, 0, 0);
		AbstractDataset c = LinearAlgebra.dotProduct(a, b);
		start += System.nanoTime();
		
		long nstart = -System.nanoTime();
		AbstractDataset d = Maths.multiply(a, b);
		Number n = (Number) d.typedSum();
		nstart += System.nanoTime();
		System.out.printf("Time taken %dus %dus\n", start/1000, nstart/1000);
		Assert.assertTrue("Data does not match", n.equals(c.getObjectAbs(0)));
		Assert.assertTrue("Data does not match", n.equals(c.getObject()));
	}

	@Test
	public void testRandomDot() {
		AbstractDataset a = Random.randn(123.5, 23.4, 100);
//		a = new DoubleDataset(new double[] {166.332, 139.135, 145.899, 112.830, 125.682, 95.614 });

		AbstractDataset aa = Maths.square(a);

		AbstractDataset c = LinearAlgebra.tensorDotProduct(a, a, 0, 0);
		System.nanoTime();

		Number n = (Number) aa.sum();
		Assert.assertTrue("Second moment does not match: " + n + " cf " + c.getObject(), close(n, c.getDouble()));

		c = LinearAlgebra.dotProduct(aa, a);
		AbstractDataset d = Maths.multiply(a, aa);
		n = (Number) d.sum();
		Assert.assertTrue("Third moment does not match: " + n + " cf " + c.getObject(), close(n, c.getDouble()));

		c = LinearAlgebra.dotProduct(aa, aa);
		d = Maths.multiply(aa, aa);
		n = (Number) d.sum();
		Assert.assertTrue("Fourth moment does not match: " + n + " cf " + c.getObject(), close(n, c.getDouble()));
	}


	@Test
	public void testOuter() {
		AbstractDataset a;
		AbstractDataset b;

		a = DoubleDataset.arange(2);
		b = DoubleDataset.arange(3);
		TestUtils.assertDatasetEquals(new DoubleDataset(new double[] {0, 0, 0, 0, 1, 2}, 2, 3), LinearAlgebra.outerProduct(a, b),
				1e-12, 1e-12);
		
		a = Random.randn(123.5, 23.4, 10);
		b = Random.randn(-31.2, 12.4, 7);
		AbstractDataset c = LinearAlgebra.outerProduct(a, b);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				Assert.assertEquals("", a.getDouble(i)*b.getDouble(j), c.getDouble(i, j), 1e-12);
			}
		}
	}
}
