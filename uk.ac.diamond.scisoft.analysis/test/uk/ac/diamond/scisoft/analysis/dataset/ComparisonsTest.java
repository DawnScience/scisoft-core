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

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComparisonsTest {
	AbstractDataset a, b;

	@Before
	public void setUpClass() {
		a = new DoubleDataset(new double[] { 0, 1, 3, 5, -7, -9 });
		b = new DoubleDataset(new double[] { 0.01, 1.2, 2.9, 5, -7.1, -9 });
	}

	@Test
	public void testEqualTo() {
		BooleanDataset c = Comparisons.equalTo(a, b);
		BooleanDataset d = new BooleanDataset(new boolean[] {false, false, false, true, false, true});
		checkDatasets(c, d);

		checkDatasets(Comparisons.equalTo(3, a), new BooleanDataset(
				new boolean[] {false, false, true, false, false, false}));

		DoubleDataset ta = new DoubleDataset(new int [] {20, 10});
		ta.fill(Double.NaN);
		DoubleDataset tb = new DoubleDataset(new int [] {20, 9});
		tb.set(0, 0, 9);
		tb.fill(Double.NaN);
		
		BooleanDataset bd = new BooleanDataset(ta.getShape());
		bd.fill(Boolean.FALSE);
		checkDatasets(Comparisons.equalTo(ta, tb), bd);
		ta.fill(Double.POSITIVE_INFINITY);
		tb.fill(Double.POSITIVE_INFINITY);
		bd.fill(Boolean.TRUE);
		checkDatasets(Comparisons.equalTo(ta, tb), bd);

		c = Comparisons.equalTo(new DoubleDataset(new int[]{}).fill(1), 1);
		checkDatasets(c, new BooleanDataset(new boolean[] {true}));
	}

	@Test
	public void testAlmostEqualTo() {
		BooleanDataset c = Comparisons.almostEqualTo(a, b, 0.1, 1e-3);
		BooleanDataset d = new BooleanDataset(new boolean[] {false, false, true, true, true, true});
		checkDatasets(c, d);

		checkDatasets(Comparisons.almostEqualTo(3, a, 0.1, 1e-3), new BooleanDataset(
				new boolean[] {false, false, true, false, false, false}));
	}

	@Test
	public void testGreaterThan() {
		BooleanDataset c = Comparisons.greaterThan(a, b);
		BooleanDataset d = new BooleanDataset(new boolean[] {false, false, true, false, true, false});
		checkDatasets(c, d);

		checkDatasets(Comparisons.greaterThan(3, a), new BooleanDataset(
				new boolean[] {true, true, false, false, true, true}));
	}

	@Test
	public void testGreaterThanOrEqualTo() {
		BooleanDataset c = Comparisons.greaterThanOrEqualTo(a, b);
		BooleanDataset d = new BooleanDataset(new boolean[] {false, false, true, true, true, true});
		checkDatasets(c, d);

		checkDatasets(Comparisons.greaterThanOrEqualTo(3, a), new BooleanDataset(
				new boolean[] {true, true, true, false, true, true}));
}

	@Test
	public void testLessThan() {
		BooleanDataset c = Comparisons.lessThan(a, b);
		BooleanDataset d = new BooleanDataset(new boolean[] {true, true, false, false, false, false});
		checkDatasets(c, d);

		checkDatasets(Comparisons.lessThan(3, a), new BooleanDataset(
				new boolean[] {false, false, false, true, false, false}));
	}

	@Test
	public void testLessThanOrEqualTo() {
		BooleanDataset c = Comparisons.lessThanOrEqualTo(a, b);
		BooleanDataset d = new BooleanDataset(new boolean[] {true, true, false, true, false, true});
		checkDatasets(c, d);

		checkDatasets(Comparisons.lessThanOrEqualTo(3, a), new BooleanDataset(
				new boolean[] {false, false, true, true, false, false}));
	}

	@Test
	public void testAllTrue() {
		Assert.assertFalse(Comparisons.allTrue(a));
		Assert.assertTrue(Comparisons.allTrue(b));
		AbstractDataset c = a.clone().reshape(2, 3);
		checkDatasets(Comparisons.allTrue(c, 0), new BooleanDataset(new boolean[] {false, true, true}));
		AbstractDataset d = b.clone().reshape(2, 3);
		checkDatasets(Comparisons.allTrue(d, 1), new BooleanDataset(new boolean[] {true, true}));
	}

	@Test
	public void testAnyTrue() {
		Assert.assertTrue(Comparisons.anyTrue(a));
		Assert.assertTrue(Comparisons.anyTrue(b));
		Assert.assertFalse(Comparisons.anyTrue(new DoubleDataset(new double[] {0, 0})));
		AbstractDataset c = a.clone().reshape(2, 3);
		checkDatasets(Comparisons.anyTrue(c, 0), new BooleanDataset(new boolean[] {true, true, true}));
		AbstractDataset d = b.clone().reshape(2, 3);
		checkDatasets(Comparisons.anyTrue(d, 1), new BooleanDataset(new boolean[] {true, true}));
		checkDatasets(Comparisons.anyTrue(new DoubleDataset(new double[] {0, 0, 0, 1}).reshape(2,2), 1),
				new BooleanDataset(new boolean[] {false, true}));
	}

	@Test
	public void testNot() {
		checkDatasets(Comparisons.logicalNot(a), new BooleanDataset(
				new boolean[] {true, false, false, false, false, false}));
		checkDatasets(Comparisons.logicalNot(b), new BooleanDataset(
				new boolean[] {false, false, false, false, false, false}));
	}

	@Test
	public void testAnd() {
		BooleanDataset c = Comparisons.logicalAnd(a, b);
		BooleanDataset d = new BooleanDataset(new boolean[] {false, true, true, true, true, true});
		checkDatasets(c, d);
	}

	@Test
	public void testOr() {
		BooleanDataset c = Comparisons.logicalOr(a, b);
		BooleanDataset d = new BooleanDataset(new boolean[] {true, true, true, true, true, true});
		checkDatasets(c, d);
	}

	@Test
	public void testXor() {
		BooleanDataset c = Comparisons.logicalXor(a, b);
		BooleanDataset d = new BooleanDataset(new boolean[] {true, false, false, false, false, false});
		checkDatasets(c, d);
	}

	@Test
	public void testNonZero() {
		AbstractDataset c = a.clone().reshape(2, 3);
		List<IntegerDataset> e = Comparisons.nonZero(c);
		checkDatasets(e.get(0), new IntegerDataset(new int[] {0, 0, 1, 1, 1}, null));
		checkDatasets(e.get(1), new IntegerDataset(new int[] {1, 2, 0, 1, 2}, null));
	}

	@Test
	public void testSelect() {
		AbstractDataset c = a.clone().reshape(2, 3);
		BooleanDataset d = new BooleanDataset(new boolean[] {false, true, false, false, true, false}, 2, 3);

		DoubleDataset e = (DoubleDataset) Comparisons.select(new BooleanDataset[] {d}, new Object[] {c}, -2);
		checkDatasets(e, new DoubleDataset(new double[] {-2, 1, -2, -2, -7, -2}, 2, 3));

		AbstractDataset f = b.clone().reshape(2, 3);
		BooleanDataset g = new BooleanDataset(new boolean[] {false, true, true, false, false, false}, 2, 3);

		e = (DoubleDataset) Comparisons.select(new BooleanDataset[] {d, g}, new AbstractDataset[] {c, f}, -2.5);

		checkDatasets(e, new DoubleDataset(new double[] {-2.5, 1, 2.9, -2.5, -7, -2.5}, 2, 3));
	}

	public void checkDatasets(BooleanDataset calc, BooleanDataset expected) {
		IndexIterator at = calc.getIterator(true);
		IndexIterator bt = expected.getIterator();
		final int is = calc.getElementsPerItem();

		while (at.hasNext() && bt.hasNext()) {
			for (int j = 0; j < is; j++) {
				Assert.assertEquals("Value does not match at " + Arrays.toString(at.getPos()) + "; " + j +
						": ", expected.getAbs(at.index + j), calc.getAbs(bt.index + j));
			}
		}
	}

	public void checkDatasets(IntegerDataset calc, IntegerDataset expected) {
		IndexIterator at = calc.getIterator(true);
		IndexIterator bt = expected.getIterator();
		final int is = calc.getElementsPerItem();

		while (at.hasNext() && bt.hasNext()) {
			for (int j = 0; j < is; j++) {
				Assert.assertEquals("Value does not match at " + Arrays.toString(at.getPos()) + "; " + j +
						": ", expected.getAbs(at.index + j), calc.getAbs(bt.index + j));
			}
		}
	}

	public void checkDatasets(DoubleDataset calc, DoubleDataset expected) {
		IndexIterator at = calc.getIterator(true);
		IndexIterator bt = expected.getIterator();
		final int is = calc.getElementsPerItem();

		while (at.hasNext() && bt.hasNext()) {
			for (int j = 0; j < is; j++) {
				Assert.assertEquals("Value does not match at " + Arrays.toString(at.getPos()) + "; " + j +
						": ", expected.getAbs(at.index + j), calc.getAbs(bt.index + j), 1e-5);
			}
		}
	}

	@Test
	public void testFlags() {
		AbstractDataset c;

		c = AbstractDataset.array(new int[] {0, -1, 1});
		checkDatasets(Comparisons.isFinite(c), new BooleanDataset(new boolean[] {true, true, true}));
		checkDatasets(Comparisons.isInfinite(c), new BooleanDataset(new boolean[] {false, false, false}));
		checkDatasets(Comparisons.isPositiveInfinite(c), new BooleanDataset(new boolean[] {false, false, false}));
		checkDatasets(Comparisons.isNegativeInfinite(c), new BooleanDataset(new boolean[] {false, false, false}));
		checkDatasets(Comparisons.isNaN(c), new BooleanDataset(new boolean[] {false, false, false}));

		c = AbstractDataset.array(new double[] {0, -1, 1});
		checkDatasets(Comparisons.isFinite(c), new BooleanDataset(new boolean[] {true, true, true}));
		checkDatasets(Comparisons.isInfinite(c), new BooleanDataset(new boolean[] {false, false, false}));
		checkDatasets(Comparisons.isPositiveInfinite(c), new BooleanDataset(new boolean[] {false, false, false}));
		checkDatasets(Comparisons.isNegativeInfinite(c), new BooleanDataset(new boolean[] {false, false, false}));
		checkDatasets(Comparisons.isNaN(c), new BooleanDataset(new boolean[] {false, false, false}));

		c = AbstractDataset.array(new double[] {Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY});
		checkDatasets(Comparisons.isFinite(c), new BooleanDataset(new boolean[] {false, false, false}));
		checkDatasets(Comparisons.isInfinite(c), new BooleanDataset(new boolean[] {false, true, true}));
		checkDatasets(Comparisons.isPositiveInfinite(c), new BooleanDataset(new boolean[] {false, false, true}));
		checkDatasets(Comparisons.isNegativeInfinite(c), new BooleanDataset(new boolean[] {false, true, false}));
		checkDatasets(Comparisons.isNaN(c), new BooleanDataset(new boolean[] {true, false, false}));

		c = AbstractDataset.array(new double[] {Double.NaN, -Double.POSITIVE_INFINITY, -Double.NEGATIVE_INFINITY});
		checkDatasets(Comparisons.isFinite(c), new BooleanDataset(new boolean[] {false, false, false}));
		checkDatasets(Comparisons.isInfinite(c), new BooleanDataset(new boolean[] {false, true, true}));
		checkDatasets(Comparisons.isPositiveInfinite(c), new BooleanDataset(new boolean[] {false, false, true}));
		checkDatasets(Comparisons.isNegativeInfinite(c), new BooleanDataset(new boolean[] {false, true, false}));
		checkDatasets(Comparisons.isNaN(c), new BooleanDataset(new boolean[] {true, false, false}));
	}
}
