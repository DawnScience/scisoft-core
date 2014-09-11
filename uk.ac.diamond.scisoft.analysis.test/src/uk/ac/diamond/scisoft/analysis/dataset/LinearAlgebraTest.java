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

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.dataset.LinearAlgebra.NormOrder;

public class LinearAlgebraTest {

	private static boolean close(Number a, double b) {
		double x = a.doubleValue();
		return x == 0 ? Math.abs(b) < 10e-6 : Math.abs(x - b) < 10e-6*x;
	}

	@Test
	public void testTensorDot() {
		Dataset a = DatasetFactory.createRange(60, Dataset.FLOAT32).reshape(3, 4, 5);
		Dataset b = DatasetFactory.createRange(24, Dataset.INT16).reshape(4, 3, 2);

		long start;
		start = -System.nanoTime();
		Dataset c = LinearAlgebra.tensorDotProduct(a, b, new int[] {1, 0}, new int[] {0,1});
		start += System.nanoTime();

		System.out.printf("Time taken %dus\n", start/1000);

		assertArrayEquals("Shape", new int[] {5, 2}, c.getShape());
		assertEquals("Type", Dataset.FLOAT32, c.getDtype());

		Dataset d = new DoubleDataset(new double[] { 4400., 4730.,
			4532.,  4874., 4664., 5018., 4796.,  5162., 4928.,  5306. }, 5, 2);
		assertTrue("Data does not match", d.cast(c.getDtype()).equals(c));

		int n = 16;
		a = DatasetFactory.createRange(20*n, Dataset.FLOAT32).reshape(n, 4, 5);
		b = DatasetFactory.createRange(8*n, Dataset.INT16).reshape(4, n, 2);
		start = -System.nanoTime();
		c = LinearAlgebra.tensorDotProduct(a, b, 0, 1);
		start += System.nanoTime();

		long nstart = -System.nanoTime();
		d = LinearAlgebra.tensorDotProduct(a, b, new int[] {0}, new int[] {1});
		nstart += System.nanoTime();
		System.out.printf("Time taken %dus %dus\n", start/1000, nstart/1000);

		assertTrue("Data does not match", d.equals(c));
	}

	@Test
	public void testDot() {
		Dataset a = DatasetFactory.createRange(10, Dataset.FLOAT32);
		Dataset b = DatasetFactory.createRange(-6, 4, 1, Dataset.INT16);

		long start;
		start = -System.nanoTime();
//		Dataset c = LinearAlgebra.tensorDotProduct(a, b, 0, 0);
		Dataset c = LinearAlgebra.dotProduct(a, b);
		start += System.nanoTime();
		
		long nstart = -System.nanoTime();
		Dataset d = Maths.multiply(a, b);
		Number n = (Number) d.typedSum();
		nstart += System.nanoTime();
		System.out.printf("Time taken %dus %dus\n", start/1000, nstart/1000);
		assertTrue("Data does not match", n.equals(c.getObjectAbs(0)));
		assertTrue("Data does not match", n.equals(c.getObject()));
	}

	@Test
	public void testRandomDot() {
		Dataset a = Random.randn(123.5, 23.4, 100);
//		a = new DoubleDataset(new double[] {166.332, 139.135, 145.899, 112.830, 125.682, 95.614 });

		Dataset aa = Maths.square(a);

		Dataset c = LinearAlgebra.tensorDotProduct(a, a, 0, 0);
		System.nanoTime();

		Number n = (Number) aa.sum();
		assertTrue("Second moment does not match: " + n + " cf " + c.getObject(), close(n, c.getDouble()));

		c = LinearAlgebra.dotProduct(aa, a);
		Dataset d = Maths.multiply(a, aa);
		n = (Number) d.sum();
		assertTrue("Third moment does not match: " + n + " cf " + c.getObject(), close(n, c.getDouble()));

		c = LinearAlgebra.dotProduct(aa, aa);
		d = Maths.multiply(aa, aa);
		n = (Number) d.sum();
		assertTrue("Fourth moment does not match: " + n + " cf " + c.getObject(), close(n, c.getDouble()));
	}


	@Test
	public void testOuter() {
		Dataset a;
		Dataset b;

		a = DoubleDataset.createRange(2);
		b = DoubleDataset.createRange(3);
		TestUtils.assertDatasetEquals(new DoubleDataset(new double[] {0, 0, 0, 0, 1, 2}, 2, 3), LinearAlgebra.outerProduct(a, b),
				1e-12, 1e-12);
		
		a = Random.randn(123.5, 23.4, 10);
		b = Random.randn(-31.2, 12.4, 7);
		Dataset c = LinearAlgebra.outerProduct(a, b);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				assertEquals("", a.getDouble(i)*b.getDouble(j), c.getDouble(i, j), 1e-12);
			}
		}
	}

	@Test
	public void testCross() {
		Dataset a, b, c, d;

		a = new IntegerDataset(new int[] {2, 3, 5}, 3);
		b = new FloatDataset(new float[] {1, 4, 7, 2, 5, 8}, 2, 3);

		c = LinearAlgebra.crossProduct(a, b);
		TestUtils.assertDatasetEquals(new DoubleDataset(new double[] {1, -9, 5, -1, -6, 4}, 2, 3), c, 1e-15, 1e-15);
		d = LinearAlgebra.crossProduct(b, a);
		TestUtils.assertDatasetEquals(Maths.negative(c), d, 1e-15, 1e-15);
		c = LinearAlgebra.crossProduct(a, b, -1, -1, 0);
		TestUtils.assertDatasetEquals(new DoubleDataset(new double[] {1, -1, -9, -6, 5, 4}, 3, 2), c, 1e-15, 1e-15);
		d = LinearAlgebra.crossProduct(b, a, -1, -1, 0);
		TestUtils.assertDatasetEquals(Maths.negative(c), d, 1e-15, 1e-15);

		a = new IntegerDataset(new int[] {2, 3, 0}, 3);
		c = LinearAlgebra.crossProduct(a, b);
		TestUtils.assertDatasetEquals(new DoubleDataset(new double[] {21, -14, 5, 24, -16, 4}, 2, 3), c, 1e-15, 1e-15);
		d = LinearAlgebra.crossProduct(b, a);
		TestUtils.assertDatasetEquals(Maths.negative(c), d, 1e-15, 1e-15);

		a = new IntegerDataset(new int[] {2, 3}, 2);
		c = LinearAlgebra.crossProduct(a, b);
		TestUtils.assertDatasetEquals(new DoubleDataset(new double[] {21, -14, 5, 24, -16, 4}, 2, 3), c, 1e-15, 1e-15);
		d = LinearAlgebra.crossProduct(b, a);
		TestUtils.assertDatasetEquals(Maths.negative(c), d, 1e-15, 1e-15);

		a = new IntegerDataset(new int[] {2, 3, 5}, 3);
		b = new FloatDataset(new float[] {1, 4, 0, 2, 5, 0}, 2, 3);
		c = LinearAlgebra.crossProduct(a, b);
		TestUtils.assertDatasetEquals(new DoubleDataset(new double[] {-20, 5, 5, -25, 10, 4}, 2, 3), c, 1e-15, 1e-15);
		d = LinearAlgebra.crossProduct(b, a);
		TestUtils.assertDatasetEquals(Maths.negative(c), d, 1e-15, 1e-15);

		b = new FloatDataset(new float[] {1, 4, 2, 5}, 2, 2);
		c = LinearAlgebra.crossProduct(a, b);
		TestUtils.assertDatasetEquals(new DoubleDataset(new double[] {-20, 5, 5, -25, 10, 4}, 2, 3), c, 1e-15, 1e-15);
		d = LinearAlgebra.crossProduct(b, a);
		TestUtils.assertDatasetEquals(Maths.negative(c), d, 1e-15, 1e-15);

		a = new IntegerDataset(new int[] {2, 3}, 2);
		b = new FloatDataset(new float[] {1, 4, 2, 5}, 2, 2);
		c = LinearAlgebra.crossProduct(a, b);
		TestUtils.assertDatasetEquals(new DoubleDataset(new double[] {5, 4}, 2), c, 1e-15, 1e-15);
		d = LinearAlgebra.crossProduct(b, a);
		TestUtils.assertDatasetEquals(Maths.negative(c), d, 1e-15, 1e-15);
	}

	@Test
	public void testNorm() {
		Dataset a, b;
		a = DatasetFactory.createRange(9, Dataset.INT32);
		a.isubtract(4);
		b = a.reshape(3, 3);

		NormOrder n = NormOrder.DEFAULT;
		assertEquals(7.745966692414834, LinearAlgebra.norm(a, n), 1e-15);
		assertEquals(7.745966692414834, LinearAlgebra.norm(b, n), 1e-15);
		n = NormOrder.POS_INFINITY;
		assertEquals(4, LinearAlgebra.norm(a, n), 1e-15);
		assertEquals(9, LinearAlgebra.norm(b, n), 1e-15);
		n = NormOrder.NEG_INFINITY;
		assertEquals(0, LinearAlgebra.norm(a, n), 1e-15);
		assertEquals(2, LinearAlgebra.norm(b, n), 1e-15);

		int p = 1;
		assertEquals(20, LinearAlgebra.norm(a, p), 1e-15);
		assertEquals(7, LinearAlgebra.norm(b, p), 1e-15);
		p = -1;
		assertEquals(-4.6566128774142013e-010, LinearAlgebra.norm(a, p), 1e-09);
		assertEquals(6, LinearAlgebra.norm(b, p), 1e-15);
		p = 2;
		assertEquals(7.745966692414834, LinearAlgebra.norm(a, p), 1e-15);
		assertEquals(7.3484692283495345, LinearAlgebra.norm(b, p), 1e-15);
		p = -2;
//		assertTrue(Double.isNaN(LinearAlgebra.norm(a, p))); TODO make behave like NumPy?
		assertEquals(0, LinearAlgebra.norm(a, p), 1e-15);
		assertEquals(1.8570331885190563e-016, LinearAlgebra.norm(b, p), 1e-15);
		p = 3;
		assertEquals(5.8480354764257312, LinearAlgebra.norm(a, p), 1e-15);
		p = -3;
//		assertTrue(Double.isNaN(LinearAlgebra.norm(a, p)));
		assertEquals(0, LinearAlgebra.norm(a, p), 1e-15);
	}
}
