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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.complex.Complex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MathsTest {
	private final static int SSTEP = 15;
	private final static int SITER = 3;
	private final static double ABSERRD = 1e-8;
	private final static double ABSERRF = 1e-5;
	private final static double RELERR = 1e-5;
	private final static int ISIZEA = 2;
	private final static int ISIZEB = 3;
	private final static int MAXISIZE = Math.max(ISIZEA, ISIZEB);

	@Before
	public void setUpClass() {
		classes = new LinkedHashMap<String, Integer>();
//		classes.put("Boolean", AbstractDataset.BOOL);
		classes.put("Byte", AbstractDataset.INT8);
		classes.put("Short", AbstractDataset.INT16);
		classes.put("Integer", AbstractDataset.INT32);
		classes.put("Long", AbstractDataset.INT64);
		classes.put("Float", AbstractDataset.FLOAT32);
		classes.put("Double", AbstractDataset.FLOAT64);
		classes.put("ComplexF", AbstractDataset.COMPLEX64);
		classes.put("ComplexD", AbstractDataset.COMPLEX128);
		classes.put("ArrayB", AbstractDataset.ARRAYINT8);
		classes.put("ArrayS", AbstractDataset.ARRAYINT16);
		classes.put("ArrayI", AbstractDataset.ARRAYINT32);
		classes.put("ArrayL", AbstractDataset.ARRAYINT64);
		classes.put("ArrayF", AbstractDataset.ARRAYFLOAT32);
		classes.put("ArrayD", AbstractDataset.ARRAYFLOAT64);
	}

	private Map<String, Integer> classes;

	private void checkDatasets(Object a, Object b, AbstractDataset c, AbstractDataset d) {
		Assert.assertNotNull(c);
		Assert.assertNotNull(d);
		Assert.assertEquals("Dtype does not match", c.getDtype(), d.getDtype());
		Assert.assertEquals("Size does not match", c.getSize(), d.getSize());
		Assert.assertEquals("ISize does not match", c.getElementsPerItem(), d.getElementsPerItem());
		Assert.assertArrayEquals("Shape does not match", c.getShape(), d.getShape());

		final IndexIterator ci = c.getIterator(true);
		final IndexIterator di = d.getIterator();
		final int is = c.getElementsPerItem();

		final double abserr = (c.getDtype() == AbstractDataset.FLOAT32 ||
				c.getDtype() == AbstractDataset.COMPLEX64 ||
				c.getDtype() == AbstractDataset.ARRAYFLOAT32) ? ABSERRF : ABSERRD;

		if (is == 1) {
			while (ci.hasNext() && di.hasNext()) {
				double av = c.getElementDoubleAbs(ci.index);
				double bv = d.getElementDoubleAbs(di.index);
				double tol = Math.max(abserr, Math.abs(av*RELERR));
				if (Math.abs(av - bv) > tol) {
					if (a != null) {
						if (a instanceof AbstractDataset)
							System.err.printf("A was %s ", ((AbstractDataset) a).getString(ci.getPos()));
						else
							System.err.printf("A was %s ", a);
					}
					if (b != null) {
						if (b instanceof AbstractDataset)
							System.err.printf("B was %s ", ((AbstractDataset) b).getString(ci.getPos()));
						else
							System.err.printf("B was %s ", b);
					}
					System.err.printf("\n");
				}
				Assert.assertEquals("Value does not match at " + Arrays.toString(ci.getPos()) + ", with tol " + tol + ": ",
						av, bv, tol);
			}
		} else {
			while (ci.hasNext() && di.hasNext()) {
				for (int j = 0; j < is; j++) {
					double av = c.getElementDoubleAbs(ci.index + j);
					double bv = d.getElementDoubleAbs(di.index + j);
					double tol = Math.max(abserr, Math.abs(av*RELERR));
					if (Math.abs(av - bv) > tol) {
						if (a != null) {
							if (a instanceof AbstractDataset)
								System.err.printf("A was %s ", ((AbstractDataset) a).getString(ci.getPos()));
							else
								System.err.printf("A was %s ", a);
						}
						if (b != null) {
							if (b instanceof AbstractDataset)
								System.err.printf("B was %s ", ((AbstractDataset) b).getString(ci.getPos()));
							else
								System.err.printf("B was %s ", b);
						}
						System.err.printf("\n");
					}
					Assert.assertEquals("Value does not match at " + Arrays.toString(ci.getPos()) + "; " + j +
							", with tol " + tol + ": ", av, bv, tol);
				}
			}
		}
	}

	@Test
	public void testAddition() {
		AbstractDataset a, b, c = null, d = null;
		Complex zv = new Complex(-3.5, 0);
		final double dv = zv.getReal();
		long start;
		int n;

		for (String dn : classes.keySet()) {
			final int dtype = classes.get(dn);
			Random.seed(12735L);
			for (String en : classes.keySet()) {
				final int etype = classes.get(en);

				System.out.println("Adding " + dn + " to " + en);

				n = 32;
				for (int i = 0; i < SITER; i++) {
					if (dtype < AbstractDataset.ARRAYINT8) {
						a = Random.randn(n).imultiply(100);
						a = a.cast(dtype);
					} else {
						AbstractDataset[] aa = new AbstractDataset[ISIZEA];
						for (int j = 0; j < ISIZEA; j++) {
							aa[j] = Random.randn(n).imultiply(100);
						}
						a = DatasetUtils.cast(aa, dtype);
					}
					if (etype < AbstractDataset.ARRAYINT8) {
						b = Random.randn(n).imultiply(100);
						b = b.cast(etype);
					} else {
						AbstractDataset[] ab = new AbstractDataset[ISIZEB];
						for (int j = 0; j < ISIZEB; j++) {
							ab[j] = Random.randn(n).imultiply(100);
						}
						b = DatasetUtils.cast(ab, etype);
					}

					start = -System.nanoTime();
					try {
						c = Maths.add(a, b);
					} catch (IllegalArgumentException e) {
						System.out.println("Could not perform this operation: " + e.getMessage());
						continue;
					}
					start += System.nanoTime();
					double ntime = ((double) start) / c.getSize();

					d = AbstractDataset.zeros(c);
					start = -System.nanoTime();
					IndexIterator ita = a.getIterator();
					IndexIterator itb = b.getIterator();
					int j = 0;
					if ((dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& (etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).add((Complex) b
											.getObjectAbs(itb.index)));
							j += is;
						}
					} else if ((dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& !(etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).add(new Complex(b.getElementDoubleAbs(itb.index), 0)));
							j += is;
						}
					} else if (!(dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& (etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, new Complex(a.getElementDoubleAbs(ita.index), 0).add((Complex) b.getObjectAbs(itb.index)));
							j += is;
						}
					} else {
						if (dtype < AbstractDataset.ARRAYINT8 && etype < AbstractDataset.ARRAYINT8) {
							while (ita.hasNext() && itb.hasNext()) {
								d.setObjectAbs(j++, ((Number) a.getObjectAbs(ita.index)).doubleValue()
												+ ((Number) b.getObjectAbs(itb.index)).doubleValue());
							}
						} else {
							final double[] answer = new double[MAXISIZE];
							final int is = d.getElementsPerItem();
							if (a.getElementsPerItem() < is) {
								while (ita.hasNext() && itb.hasNext()) {
									final double da = a.getElementDoubleAbs(ita.index);
									for (int k = 0; k < ISIZEB; k++) {
										answer[k] = da + b.getElementDoubleAbs(itb.index + k);
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							} else if (b.getElementsPerItem() < is) {
								while (ita.hasNext() && itb.hasNext()) {
									final double db = b.getElementDoubleAbs(itb.index);
									for (int k = 0; k < ISIZEA; k++) {
										answer[k] = a.getElementDoubleAbs(ita.index + k) + db;
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							} else {
								while (ita.hasNext() && itb.hasNext()) {
									for (int k = 0; k < is; k++) {
										answer[k] = a.getElementDoubleAbs(ita.index + k)
												+ b.getElementDoubleAbs(itb.index + k);
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							}
						}
					}
					if (d == null)
						break;
					start += System.nanoTime();
					double otime = ((double) start) / d.getSize();

					System.out.printf("Time taken by add for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.
							* (otime - ntime) / otime);

					checkDatasets(a, b, c, d);

					n *= SSTEP;
				}
			}

			Random.seed(12735L);
			n = 32;
			System.out.println("Adding constant to " + dn);
			for (int i = 0; i < SITER; i++) {
				if (dtype < AbstractDataset.ARRAYINT8) {
					a = Random.randn(n);
					a.imultiply(100);
					a = a.cast(dtype);
				} else {
					AbstractDataset[] aa = new AbstractDataset[ISIZEA];
					for (int j = 0; j < ISIZEA; j++) {
						aa[j] = Random.randn(n).imultiply(100);
					}
					a = DatasetUtils.cast(aa, dtype);
				}

				start = -System.nanoTime();
				c = Maths.add(a, dv);
				start += System.nanoTime();
				double ntime = ((double) start)/c.getSize();

				d = AbstractDataset.zeros(c);
				start = -System.nanoTime();
				IndexIterator ita = a.getIterator();
				int j = 0;
				if (dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128) {
					final int is = d.getElementsPerItem();
					while (ita.hasNext()) {
						d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).add(zv));
						j += is;
					}
				} else {
					if (dtype < AbstractDataset.ARRAYINT8) {
						while (ita.hasNext()) {
							d.setObjectAbs(j++, ((Number) a.getObjectAbs(ita.index)).doubleValue() + dv);
						}
					} else {
						final double[] answer = new double[ISIZEA];
						while (ita.hasNext()) {
							for (int k = 0; k < ISIZEA; k++) {
								answer[k] = a.getElementDoubleAbs(ita.index + k) + dv;
							}
							d.setObjectAbs(j, answer);
							j += ISIZEA;
						}
					}
				}
				if (d == null)
					break;
				start += System.nanoTime();
				double otime = ((double) start)/d.getSize();

				System.out.printf("Time taken by add for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.*(otime - ntime)/otime);

				checkDatasets(a, dv, c, d);

				n *= SSTEP;
			}
		}
	}

	@Test
	public void testSubtraction() {
		AbstractDataset a, b, c = null, d = null;
		Complex zv = new Complex(-3.5, 0);
		final double dv = zv.getReal();
		long start;
		int n;

		for (String dn : classes.keySet()) {
			final int dtype = classes.get(dn);
			Random.seed(12735L);
			for (String en : classes.keySet()) {
				final int etype = classes.get(en);

				System.out.println("Subtracting " + dn + " to " + en);

				n = 32;
				for (int i = 0; i < SITER; i++) {
					if (dtype < AbstractDataset.ARRAYINT8) {
						a = Random.randn(n).imultiply(100);
						a = a.cast(dtype);
					} else {
						AbstractDataset[] aa = new AbstractDataset[ISIZEA];
						for (int j = 0; j < ISIZEA; j++) {
							aa[j] = Random.randn(n).imultiply(100);
						}
						a = DatasetUtils.cast(aa, dtype);
					}
					if (etype < AbstractDataset.ARRAYINT8) {
						b = Random.randn(n).imultiply(100);
						b = b.cast(etype);
					} else {
						AbstractDataset[] ab = new AbstractDataset[ISIZEB];
						for (int j = 0; j < ISIZEB; j++) {
							ab[j] = Random.randn(n).imultiply(100);
						}
						b = DatasetUtils.cast(ab, etype);
					}

					start = -System.nanoTime();
					try {
						c = Maths.subtract(a, b);
					} catch (IllegalArgumentException e) {
						System.out.println("Could not perform this operation: " + e.getMessage());
						continue;
					}
					start += System.nanoTime();
					double ntime = ((double) start) / c.getSize();

					d = AbstractDataset.zeros(c);
					start = -System.nanoTime();
					IndexIterator ita = a.getIterator();
					IndexIterator itb = b.getIterator();
					int j = 0;
					if ((dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& (etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).subtract((Complex) b
											.getObjectAbs(itb.index)));
							j += is;
						}
					} else if ((dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& !(etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).subtract(new Complex(b.getElementDoubleAbs(itb.index), 0)));
							j += is;
						}
					} else if (!(dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& (etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, new Complex(a.getElementDoubleAbs(ita.index), 0).subtract((Complex) b.getObjectAbs(itb.index)));
							j += is;
						}
					} else {
						if (dtype < AbstractDataset.ARRAYINT8 && etype < AbstractDataset.ARRAYINT8) {
							while (ita.hasNext() && itb.hasNext()) {
								d.setObjectAbs(j++, ((Number) a.getObjectAbs(ita.index)).doubleValue()
												- ((Number) b.getObjectAbs(itb.index)).doubleValue());
							}
						} else {
							final double[] answer = new double[MAXISIZE];
							final int is = d.getElementsPerItem();

							if (a.getElementsPerItem() < is) {
								while (ita.hasNext() && itb.hasNext()) {
									final double da = a.getElementDoubleAbs(ita.index);
									for (int k = 0; k < ISIZEB; k++) {
										answer[k] = da - b.getElementDoubleAbs(itb.index + k);
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							} else if (b.getElementsPerItem() < is) {
								while (ita.hasNext() && itb.hasNext()) {
									final double db = b.getElementDoubleAbs(itb.index);
									for (int k = 0; k < ISIZEA; k++) {
										answer[k] = a.getElementDoubleAbs(ita.index + k) - db;
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							} else {
								while (ita.hasNext() && itb.hasNext()) {
									for (int k = 0; k < is; k++) {
										answer[k] = a.getElementDoubleAbs(ita.index + k)
												- b.getElementDoubleAbs(itb.index + k);
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							}
						}
					}
					if (d == null)
						break;
					start += System.nanoTime();
					double otime = ((double) start) / d.getSize();

					System.out.printf("Time taken by sub for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.
							* (otime - ntime) / otime);

					checkDatasets(a, b, c, d);

					n *= SSTEP;
				}
			}

			Random.seed(12735L);
			n = 32;
			System.out.println("Subtracting constant from " + dn);
			for (int i = 0; i < SITER; i++) {
				if (dtype < AbstractDataset.ARRAYINT8) {
					a = Random.randn(n);
					a.imultiply(100);
					a = a.cast(dtype);
				} else {
					AbstractDataset[] aa = new AbstractDataset[ISIZEA];
					for (int j = 0; j < ISIZEA; j++) {
						aa[j] = Random.randn(n).imultiply(100);
					}
					a = DatasetUtils.cast(aa, dtype);
				}

				start = -System.nanoTime();
				c = Maths.subtract(a, dv);
				start += System.nanoTime();
				double ntime = ((double) start)/c.getSize();

				d = AbstractDataset.zeros(c);
				start = -System.nanoTime();
				IndexIterator ita = a.getIterator();
				int j = 0;
				if (dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128) {
					final int is = d.getElementsPerItem();
					while (ita.hasNext()) {
						d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).subtract(zv));
						j += is;
					}
				} else {
					if (dtype < AbstractDataset.ARRAYINT8) {
						while (ita.hasNext()) {
							d.setObjectAbs(j++, ((Number) a.getObjectAbs(ita.index)).doubleValue() - dv);
						}
					} else {
						final double[] answer = new double[ISIZEA];
						while (ita.hasNext()) {
							for (int k = 0; k < ISIZEA; k++) {
								answer[k] = a.getElementDoubleAbs(ita.index + k) - dv;
							}
							d.setObjectAbs(j, answer);
							j += ISIZEA;
						}
					}
				}
				if (d == null)
					break;
				start += System.nanoTime();
				double otime = ((double) start)/d.getSize();

				System.out.printf("Time taken by add for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.*(otime - ntime)/otime);

				checkDatasets(a, dv, c, d);

				n *= SSTEP;
			}

			Random.seed(12735L);
			n = 32;
			System.out.println("Subtracting " + dn + " from constant");
			for (int i = 0; i < SITER; i++) {
				if (dtype < AbstractDataset.ARRAYINT8) {
					a = Random.randn(n);
					a.imultiply(100);
					a = a.cast(dtype);
				} else {
					AbstractDataset[] aa = new AbstractDataset[ISIZEA];
					for (int j = 0; j < ISIZEA; j++) {
						aa[j] = Random.randn(n).imultiply(100);
					}
					a = DatasetUtils.cast(aa, dtype);
				}

				start = -System.nanoTime();
				c = Maths.subtract(dv, a);
				start += System.nanoTime();
				double ntime = ((double) start)/c.getSize();

				d = AbstractDataset.zeros(c);
				start = -System.nanoTime();
				IndexIterator ita = a.getIterator();
				int j = 0;
				if (dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128) {
					final int is = d.getElementsPerItem();
					while (ita.hasNext()) {
						d.setObjectAbs(j, zv.subtract((Complex) a.getObjectAbs(ita.index)));
						j += is;
					}
				} else {
					if (dtype < AbstractDataset.ARRAYINT8) {
						while (ita.hasNext()) {
							d.setObjectAbs(j++, dv - ((Number) a.getObjectAbs(ita.index)).doubleValue());
						}
					} else {
						final double[] answer = new double[ISIZEA];
						while (ita.hasNext()) {
							for (int k = 0; k < ISIZEA; k++) {
								answer[k] = dv - a.getElementDoubleAbs(ita.index + k);
							}
							d.setObjectAbs(j, answer);
							j += ISIZEA;
						}
					}
				}
				if (d == null)
					break;
				start += System.nanoTime();
				double otime = ((double) start)/d.getSize();

				System.out.printf("Time taken by sub for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.*(otime - ntime)/otime);

				checkDatasets(dv, a, c, d);

				n *= SSTEP;
			}
		}
	}

	@Test
	public void testMultiplication() {
		AbstractDataset a, b, c = null, d = null;
		Complex zv = new Complex(-3.5, 0);
		final double dv = zv.getReal();
		long start;
		int n;

		for (String dn : classes.keySet()) {
			final int dtype = classes.get(dn);
			Random.seed(12735L);

			for (String en : classes.keySet()) {
				final int etype = classes.get(en);

				System.out.println("Multiplying " + dn + " by " + en);

				n = 32;
				for (int i = 0; i < SITER; i++) {
					if (dtype < AbstractDataset.ARRAYINT8) {
						a = Random.randn(n).imultiply(100);
						a = a.cast(dtype);
					} else {
						AbstractDataset[] aa = new AbstractDataset[ISIZEA];
						for (int j = 0; j < ISIZEA; j++) {
							aa[j] = Random.randn(n).imultiply(100);
						}
						a = DatasetUtils.cast(aa, dtype);
					}
					if (etype < AbstractDataset.ARRAYINT8) {
						b = Random.randn(n).imultiply(100);
						b = b.cast(etype);
					} else {
						AbstractDataset[] ab = new AbstractDataset[ISIZEB];
						for (int j = 0; j < ISIZEB; j++) {
							ab[j] = Random.randn(n).imultiply(100);
						}
						b = DatasetUtils.cast(ab, etype);
					}

					start = -System.nanoTime();
					try {
						c = Maths.multiply(a, b);
					} catch (IllegalArgumentException e) {
						System.out.println("Could not perform this operation: " + e.getMessage());
						continue;
					}
					start += System.nanoTime();
					double ntime = ((double) start) / c.getSize();

					d = AbstractDataset.zeros(c);
					start = -System.nanoTime();
					IndexIterator ita = a.getIterator();
					IndexIterator itb = b.getIterator();
					int j = 0;
					if ((dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& (etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).multiply((Complex) b
											.getObjectAbs(itb.index)));
							j += is;
						}
					} else if ((dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& !(etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).multiply(b.getElementDoubleAbs(itb.index)));
							j += is;
						}
					} else if (!(dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& (etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, new Complex(a.getElementDoubleAbs(ita.index), 0).multiply((Complex) b.getObjectAbs(itb.index)));
							j += is;
						}
					} else {
						if (dtype < AbstractDataset.ARRAYINT8 && etype < AbstractDataset.ARRAYINT8) {
							while (ita.hasNext() && itb.hasNext()) {
								d.setObjectAbs(j++, ((Number) a.getObjectAbs(ita.index)).doubleValue()
												* ((Number) b.getObjectAbs(itb.index)).doubleValue());
							}
						} else {
							final double[] answer = new double[MAXISIZE];
							final int is = d.getElementsPerItem();

							if (a.getElementsPerItem() < is) {
								while (ita.hasNext() && itb.hasNext()) {
									final double da = a.getElementDoubleAbs(ita.index);
									for (int k = 0; k < ISIZEB; k++) {
										answer[k] = da * b.getElementDoubleAbs(itb.index + k);
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							} else if (b.getElementsPerItem() < is) {
								while (ita.hasNext() && itb.hasNext()) {
									final double db = b.getElementDoubleAbs(itb.index);
									for (int k = 0; k < ISIZEA; k++) {
										answer[k] = a.getElementDoubleAbs(ita.index + k) * db;
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							} else {
								while (ita.hasNext() && itb.hasNext()) {
									for (int k = 0; k < is; k++) {
										answer[k] = a.getElementDoubleAbs(ita.index + k)
												* b.getElementDoubleAbs(itb.index + k);
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							}
						}
					}
					if (d == null)
						break;
					start += System.nanoTime();
					double otime = ((double) start) / d.getSize();

					System.out.printf("Time taken by mul for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.
							* (otime - ntime) / otime);

					checkDatasets(a, b, c, d);

					n *= SSTEP;
				}
			}

			Random.seed(12735L);
			n = 32;
			System.out.println("Multiplying constant with " + dn);
			for (int i = 0; i < SITER; i++) {
				if (dtype < AbstractDataset.ARRAYINT8) {
					a = Random.randn(n);
					a.imultiply(100);
					a = a.cast(dtype);
				} else {
					AbstractDataset[] aa = new AbstractDataset[ISIZEA];
					for (int j = 0; j < ISIZEA; j++) {
						aa[j] = Random.randn(n).imultiply(100);
					}
					a = DatasetUtils.cast(aa, dtype);
				}

				start = -System.nanoTime();
				c = Maths.multiply(a, dv);
				start += System.nanoTime();
				double ntime = ((double) start)/c.getSize();

				d = AbstractDataset.zeros(c);
				start = -System.nanoTime();
				IndexIterator ita = a.getIterator();
				int j = 0;
				if (dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128) {
					final int is = d.getElementsPerItem();
					while (ita.hasNext()) {
						d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).multiply(zv));
						j += is;
					}
				} else {
					if (dtype < AbstractDataset.ARRAYINT8) {
						while (ita.hasNext()) {
							d.setObjectAbs(j++, ((Number) a.getObjectAbs(ita.index)).doubleValue() * dv);
						}
					} else {
						final double[] answer = new double[ISIZEA];
						while (ita.hasNext()) {
							for (int k = 0; k < ISIZEA; k++) {
								answer[k] = a.getElementDoubleAbs(ita.index + k) * dv;
							}
							d.setObjectAbs(j, answer);
							j += ISIZEA;
						}
					}
				}
				if (d == null)
					break;
				start += System.nanoTime();
				double otime = ((double) start)/d.getSize();

				System.out.printf("Time taken by mul for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.*(otime - ntime)/otime);

				checkDatasets(a, dv, c, d);

				n *= SSTEP;
			}
		}
	}

	@Test
	public void testDivision() {
		AbstractDataset a, b, c = null, d = null;
		Complex zv = new Complex(-3.5, 0);
		final double dv = zv.getReal();
		long start;
		int n;

		for (String dn : classes.keySet()) {
			final int dtype = classes.get(dn);
			Random.seed(12735L);

			for (String en : classes.keySet()) {
				final int etype = classes.get(en);

				System.out.println("Dividing " + dn + " by " + en);

				n = 32;
				for (int i = 0; i < SITER; i++) {
					if (dtype < AbstractDataset.ARRAYINT8) {
						a = Random.randn(n).imultiply(100);
						a = a.cast(dtype);
					} else {
						AbstractDataset[] aa = new AbstractDataset[ISIZEA];
						for (int j = 0; j < ISIZEA; j++) {
							aa[j] = Random.randn(n).imultiply(100);
						}
						a = DatasetUtils.cast(aa, dtype);
					}
					if (etype < AbstractDataset.ARRAYINT8) {
						b = Random.randn(n).imultiply(100);
						b = b.cast(etype);
					} else {
						AbstractDataset[] ab = new AbstractDataset[ISIZEB];
						for (int j = 0; j < ISIZEB; j++) {
							ab[j] = Random.randn(n).imultiply(100);
						}
						b = DatasetUtils.cast(ab, etype);
					}

					start = -System.nanoTime();
					try {
						c = Maths.divide(a, b);
					} catch (IllegalArgumentException e) {
						System.out.println("Could not perform this operation: " + e.getMessage());
						continue;
					}
					start += System.nanoTime();
					double ntime = ((double) start) / c.getSize();

					d = AbstractDataset.zeros(c);
					start = -System.nanoTime();
					IndexIterator ita = a.getIterator();
					IndexIterator itb = b.getIterator();
					int j = 0;
					if ((dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& (etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).divide((Complex) b
											.getObjectAbs(itb.index)));
							j += is;
						}
					} else if ((dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& !(etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).divide(new Complex(b.getElementDoubleAbs(itb.index), 0)));
							j += is;
						}
					} else if (!(dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& (etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, new Complex(a.getElementDoubleAbs(ita.index), 0).divide((Complex) b.getObjectAbs(itb.index)));
							j += is;
						}
					} else {
						if (dtype < AbstractDataset.ARRAYINT8 && etype < AbstractDataset.ARRAYINT8) {
							while (ita.hasNext() && itb.hasNext()) {
								d.setObjectAbs(j++, ((Number) a.getObjectAbs(ita.index)).doubleValue()
												/ ((Number) b.getObjectAbs(itb.index)).doubleValue());
							}
						} else {
							final double[] answer = new double[MAXISIZE];
							final int is = d.getElementsPerItem();
							final int rtype = d.getDtype();

							if (a.getElementsPerItem() < is) {
								while (ita.hasNext() && itb.hasNext()) {
									final double xa = a.getElementDoubleAbs(ita.index);
									if (rtype == AbstractDataset.ARRAYFLOAT32 || rtype == AbstractDataset.ARRAYFLOAT64) {
										for (int k = 0; k < ISIZEB; k++) {
											answer[k] = xa / b.getElementDoubleAbs(itb.index + k);
										}
									} else {
										for (int k = 0; k < ISIZEB; k++) {
											final double v = xa / b.getElementDoubleAbs(itb.index + k);
											answer[k] = Double.isInfinite(v) || Double.isNaN(v) ? 0 : v;
										}
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							} else if (b.getElementsPerItem() < is) {
								while (ita.hasNext() && itb.hasNext()) {
									final double xb = b.getElementDoubleAbs(itb.index);
									if (rtype == AbstractDataset.ARRAYFLOAT32 || rtype == AbstractDataset.ARRAYFLOAT64) {
										for (int k = 0; k < ISIZEA; k++) {
											answer[k] = a.getElementDoubleAbs(ita.index + k) / xb;
										}
									} else {
										if (xb == 0) {
											for (int k = 0; k < ISIZEA; k++) {
												answer[k] = 0;
											}
										} else {
											for (int k = 0; k < ISIZEA; k++) {
												answer[k] = a.getElementDoubleAbs(ita.index + k) / xb;
											}
										}
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							} else {
								while (ita.hasNext() && itb.hasNext()) {
									if (rtype == AbstractDataset.ARRAYFLOAT32 || rtype == AbstractDataset.ARRAYFLOAT64) {
										double v;
										for (int k = 0; k < is; k++) {
											v = a.getElementDoubleAbs(ita.index + k)
													/ b.getElementDoubleAbs(itb.index + k);
											answer[k] = Double.isInfinite(v) || Double.isNaN(v) ? 0 : v;
										}
									} else {
										double v;
										for (int k = 0; k < is; k++) {
											v = a.getElementDoubleAbs(ita.index + k)
													/ b.getElementDoubleAbs(itb.index + k);
											answer[k] = Double.isInfinite(v) || Double.isNaN(v) ? 0 : v;
										}
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							}
						}
					}
					if (d == null)
						break;
					start += System.nanoTime();
					double otime = ((double) start) / d.getSize();

					System.out.printf("Time taken by div for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.
							* (otime - ntime) / otime);

					checkDatasets(a, b, c, d);

					n *= SSTEP;
				}
			}

			Random.seed(12735L);
			n = 32;
			System.out.println("Dividing " + dn + " by constant");
			for (int i = 0; i < SITER; i++) {
				if (dtype < AbstractDataset.ARRAYINT8) {
					a = Random.randn(n);
					a.imultiply(100);
					a = a.cast(dtype);
				} else {
					AbstractDataset[] aa = new AbstractDataset[ISIZEA];
					for (int j = 0; j < ISIZEA; j++) {
						aa[j] = Random.randn(n).imultiply(100);
					}
					a = DatasetUtils.cast(aa, dtype);
				}

				start = -System.nanoTime();
				c = Maths.divide(a, dv);
				start += System.nanoTime();
				double ntime = ((double) start)/c.getSize();

				d = AbstractDataset.zeros(c);
				start = -System.nanoTime();
				IndexIterator ita = a.getIterator();
				int j = 0;
				if (dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128) {
					final int is = d.getElementsPerItem();
					while (ita.hasNext()) {
						d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).divide(zv));
						j += is;
					}
				} else {
					if (dtype < AbstractDataset.ARRAYINT8) {
						while (ita.hasNext()) {
							d.setObjectAbs(j++, ((Number) a.getObjectAbs(ita.index)).doubleValue() / dv);
						}
					} else {
						final double[] answer = new double[ISIZEA];
						while (ita.hasNext()) {
							for (int k = 0; k < ISIZEA; k++) {
								answer[k] = a.getElementDoubleAbs(ita.index + k) / dv;
							}
							d.setObjectAbs(j, answer);
							j += ISIZEA;
						}
					}
				}
				if (d == null)
					break;
				start += System.nanoTime();
				double otime = ((double) start)/d.getSize();

				System.out.printf("Time taken by div for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.*(otime - ntime)/otime);

				checkDatasets(a, dv, c, d);

				n *= SSTEP;
			}

			Random.seed(12735L);
			n = 32;
			System.out.println("Dividing constant by " + dn);
			for (int i = 0; i < SITER; i++) {
				if (dtype < AbstractDataset.ARRAYINT8) {
					a = Random.randn(n);
					a.imultiply(100);
					a = a.cast(dtype);
				} else {
					AbstractDataset[] aa = new AbstractDataset[ISIZEA];
					for (int j = 0; j < ISIZEA; j++) {
						aa[j] = Random.randn(n).imultiply(100);
					}
					a = DatasetUtils.cast(aa, dtype);
				}

				start = -System.nanoTime();
				c = Maths.divide(dv, a);
				start += System.nanoTime();
				double ntime = ((double) start)/c.getSize();

				d = AbstractDataset.zeros(c);
				start = -System.nanoTime();
				IndexIterator ita = a.getIterator();
				int j = 0;
				if (dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128) {
					final int is = d.getElementsPerItem();
					while (ita.hasNext()) {
						d.setObjectAbs(j, zv.divide((Complex) a.getObjectAbs(ita.index)));
						j += is;
					}
				} else {
					if (dtype < AbstractDataset.ARRAYINT8) {
						while (ita.hasNext()) {
							d.setObjectAbs(j++, dv / ((Number) a.getObjectAbs(ita.index)).doubleValue());
						}
					} else {
						final double[] answer = new double[ISIZEA];
						while (ita.hasNext()) {
							for (int k = 0; k < ISIZEA; k++) {
								answer[k] = dv / a.getElementDoubleAbs(ita.index + k);
							}
							d.setObjectAbs(j, answer);
							j += ISIZEA;
						}
					}
				}
				if (d == null)
					break;
				start += System.nanoTime();
				double otime = ((double) start)/d.getSize();

				System.out.printf("Time taken by div for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.*(otime - ntime)/otime);

				checkDatasets(dv, a, c, d);

				n *= SSTEP;
			}
		}
	}

	@Test
	public void testRemainder() {
		AbstractDataset a, b, c = null, d = null;
		Complex zv = new Complex(-3.5, 0);
		final double dv = zv.getReal();
		long start;
		int n;

		for (String dn : classes.keySet()) {
			final int dtype = classes.get(dn);
			Random.seed(12735L);

			for (String en : classes.keySet()) {
				final int etype = classes.get(en);

				System.out.println("Remaindering " + dn + " by " + en);

				n = 32;
				for (int i = 0; i < SITER; i++) {
					if (dtype < AbstractDataset.ARRAYINT8) {
						a = Random.randn(n).imultiply(100);
						a = a.cast(dtype);
					} else {
						AbstractDataset[] aa = new AbstractDataset[ISIZEA];
						for (int j = 0; j < ISIZEA; j++) {
							aa[j] = Random.randn(n).imultiply(100);
						}
						a = DatasetUtils.cast(aa, dtype);
					}
					if (etype < AbstractDataset.ARRAYINT8) {
						b = Random.randn(n).imultiply(100);
						b = b.cast(etype);
					} else {
						AbstractDataset[] ab = new AbstractDataset[ISIZEB];
						for (int j = 0; j < ISIZEB; j++) {
							ab[j] = Random.randn(n).imultiply(100);
						}
						b = DatasetUtils.cast(ab, etype);
					}

					start = -System.nanoTime();
					try {
						c = Maths.remainder(a, b);
					} catch (IllegalArgumentException e) {
						System.out.println("Could not perform this operation: " + e.getMessage());
						continue;
					} catch (UnsupportedOperationException ue) {
						System.out.println("Could not perform this operation: " + ue.getMessage());
						continue;
					}
					start += System.nanoTime();
					double ntime = ((double) start) / c.getSize();

					d = AbstractDataset.zeros(c);
					start = -System.nanoTime();
					IndexIterator ita = a.getIterator();
					IndexIterator itb = b.getIterator();
					int j = 0;
					if (dtype < AbstractDataset.ARRAYINT8 && etype < AbstractDataset.ARRAYINT8) {
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j++, ((Number) a.getObjectAbs(ita.index)).doubleValue()
											% ((Number) b.getObjectAbs(itb.index)).doubleValue());
						}
					} else {
						final double[] answer = new double[MAXISIZE];
						final int is = d.getElementsPerItem();

						if (a.getElementsPerItem() < is) {
							while (ita.hasNext() && itb.hasNext()) {
								final double xa = a.getElementDoubleAbs(ita.index);
								for (int k = 0; k < ISIZEB; k++) {
									answer[k] = xa % b.getElementDoubleAbs(itb.index + k);
								}
								d.setObjectAbs(j, answer);
								j += is;
							}
						} else if (b.getElementsPerItem() < is) {
							while (ita.hasNext() && itb.hasNext()) {
								final double xb = b.getElementDoubleAbs(itb.index);
								for (int k = 0; k < ISIZEA; k++) {
									answer[k] = a.getElementDoubleAbs(ita.index + k) % xb;
								}
								d.setObjectAbs(j, answer);
								j += is;
							}
						} else {
							while (ita.hasNext() && itb.hasNext()) {
								for (int k = 0; k < is; k++) {
									answer[k] = a.getElementDoubleAbs(ita.index + k) % b.getElementDoubleAbs(itb.index + k);
								}
								d.setObjectAbs(j, answer);
								j += is;
							}
						}
					}
					if (d == null)
						break;
					start += System.nanoTime();
					double otime = ((double) start) / d.getSize();

					System.out.printf("Time taken by rem for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.
							* (otime - ntime) / otime);

					checkDatasets(a, b, c, d);

					n *= SSTEP;
				}
			}

			Random.seed(12735L);
			n = 32;
			System.out.println("Remaindering " + dn + " by constant");
			for (int i = 0; i < SITER; i++) {
				if (dtype < AbstractDataset.ARRAYINT8) {
					a = Random.randn(n);
					a.imultiply(100);
					a = a.cast(dtype);
				} else {
					AbstractDataset[] aa = new AbstractDataset[ISIZEA];
					for (int j = 0; j < ISIZEA; j++) {
						aa[j] = Random.randn(n).imultiply(100);
					}
					a = DatasetUtils.cast(aa, dtype);
				}

				start = -System.nanoTime();
				try {
					c = Maths.remainder(a, dv);
				} catch (IllegalArgumentException e) {
					System.out.println("Could not perform this operation: " + e.getMessage());
					continue;
				} catch (UnsupportedOperationException ue) {
					System.out.println("Could not perform this operation: " + ue.getMessage());
					continue;
				}
				start += System.nanoTime();
				double ntime = ((double) start)/c.getSize();

				d = AbstractDataset.zeros(c);
				start = -System.nanoTime();
				IndexIterator ita = a.getIterator();
				int j = 0;
				if (dtype < AbstractDataset.ARRAYINT8) {
					while (ita.hasNext()) {
						d.setObjectAbs(j++, ((Number) a.getObjectAbs(ita.index)).doubleValue() % dv);
					}
				} else {
					final double[] answer = new double[ISIZEA];
					while (ita.hasNext()) {
						for (int k = 0; k < ISIZEA; k++) {
							answer[k] = a.getElementDoubleAbs(ita.index + k) % dv;
						}
						d.setObjectAbs(j, answer);
						j += ISIZEA;
					}
				}
				start += System.nanoTime();
				double otime = ((double) start)/d.getSize();

				System.out.printf("Time taken by rem for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.*(otime - ntime)/otime);

				checkDatasets(a, dv, c, d);

				n *= SSTEP;
			}

			Random.seed(12735L);
			n = 32;
			System.out.println("Remaindering constant by " + dn);
			for (int i = 0; i < SITER; i++) {
				if (dtype < AbstractDataset.ARRAYINT8) {
					a = Random.randn(n);
					a.imultiply(100);
					a = a.cast(dtype);
				} else {
					AbstractDataset[] aa = new AbstractDataset[ISIZEA];
					for (int j = 0; j < ISIZEA; j++) {
						aa[j] = Random.randn(n).imultiply(100);
					}
					a = DatasetUtils.cast(aa, dtype);
				}

				start = -System.nanoTime();
				try {
					c = Maths.remainder(dv, a);
				} catch (IllegalArgumentException e) {
					System.out.println("Could not perform this operation: " + e.getMessage());
					continue;
				} catch (UnsupportedOperationException ue) {
					System.out.println("Could not perform this operation: " + ue.getMessage());
					continue;
				}
				start += System.nanoTime();
				double ntime = ((double) start)/c.getSize();

				d = AbstractDataset.zeros(c);
				start = -System.nanoTime();
				IndexIterator ita = a.getIterator();
				int j = 0;
				if (dtype < AbstractDataset.ARRAYINT8) {
					while (ita.hasNext()) {
						d.setObjectAbs(j++, dv % ((Number) a.getObjectAbs(ita.index)).doubleValue());
					}
				} else {
					final double[] answer = new double[ISIZEA];
					while (ita.hasNext()) {
						for (int k = 0; k < ISIZEA; k++) {
							answer[k] = dv % a.getElementDoubleAbs(ita.index + k);
						}
						d.setObjectAbs(j, answer);
						j += ISIZEA;
					}
				}
				start += System.nanoTime();
				double otime = ((double) start)/d.getSize();

				System.out.printf("Time taken by rem for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.*(otime - ntime)/otime);

				checkDatasets(dv, a, c, d);

				n *= SSTEP;
			}
		}
	}

	@Test
	public void testPower() {
		AbstractDataset a, b, c = null, d = null;
		Complex zv = new Complex(-3.5, 0);
		final double dv = zv.getReal();
		long start;
		int n;

		for (String dn : classes.keySet()) {
			final int dtype = classes.get(dn);
			Random.seed(12735L);

			for (String en : classes.keySet()) {
				final int etype = classes.get(en);

				System.out.println("Powering " + dn + " by " + en);

				n = 32;
				for (int i = 0; i < SITER; i++) {
					if (dtype < AbstractDataset.ARRAYINT8) {
						a = Random.randn(n).imultiply(100);
						a = a.cast(dtype);
					} else {
						AbstractDataset[] aa = new AbstractDataset[ISIZEA];
						for (int j = 0; j < ISIZEA; j++) {
							aa[j] = Random.randn(n).imultiply(100);
						}
						a = DatasetUtils.cast(aa, dtype);
					}
					if (etype < AbstractDataset.ARRAYINT8) {
						b = Random.randn(n).imultiply(100);
						b = b.cast(etype);
					} else {
						AbstractDataset[] ab = new AbstractDataset[ISIZEB];
						for (int j = 0; j < ISIZEB; j++) {
							ab[j] = Random.randn(n).imultiply(100);
						}
						b = DatasetUtils.cast(ab, etype);
					}

					start = -System.nanoTime();
					try {
						c = Maths.power(a, b);
					} catch (IllegalArgumentException e) {
						System.out.println("Could not perform this operation: " + e.getMessage());
						continue;
					}
					start += System.nanoTime();
					double ntime = ((double) start) / c.getSize();

					d = AbstractDataset.zeros(c);
					start = -System.nanoTime();
					IndexIterator ita = a.getIterator();
					IndexIterator itb = b.getIterator();
					int j = 0;
					if ((dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& (etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).pow((Complex) b
											.getObjectAbs(itb.index)));
							j += is;
						}
					} else if ((dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& !(etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).pow(new Complex(b.getElementDoubleAbs(itb.index), 0)));
							j += is;
						}
					} else if (!(dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128)
							&& (etype == AbstractDataset.COMPLEX64 || etype == AbstractDataset.COMPLEX128)) {
						final int is = d.getElementsPerItem();
						while (ita.hasNext() && itb.hasNext()) {
							d.setObjectAbs(j, new Complex(a.getElementDoubleAbs(ita.index), 0).pow((Complex) b.getObjectAbs(itb.index)));
							j += is;
						}
					} else {
						if (dtype < AbstractDataset.ARRAYINT8 && etype < AbstractDataset.ARRAYINT8) {
							while (ita.hasNext() && itb.hasNext()) {
								d.setObjectAbs(j++, Math.pow(((Number) a.getObjectAbs(ita.index)).doubleValue(),
												((Number) b.getObjectAbs(itb.index)).doubleValue()));
							}
						} else {
							final double[] answer = new double[MAXISIZE];
							final int is = d.getElementsPerItem();
							final int rtype = d.getDtype();

							double v;
							if (a.getElementsPerItem() < is) {
								while (ita.hasNext() && itb.hasNext()) {
									final double xa = a.getElementDoubleAbs(ita.index);
									if (rtype == AbstractDataset.ARRAYFLOAT32 || rtype == AbstractDataset.ARRAYFLOAT64) {
										for (int k = 0; k < ISIZEB; k++) {
											answer[k] = Math.pow(xa, b.getElementDoubleAbs(itb.index + k));
										}
									} else {
										for (int k = 0; k < ISIZEB; k++) {
											v = Math.pow(xa, b.getElementDoubleAbs(itb.index + k));
											answer[k] = Double.isInfinite(v) || Double.isNaN(v) ? 0 : v;
										}
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							} else if (b.getElementsPerItem() < is) {
								while (ita.hasNext() && itb.hasNext()) {
									final double xb = b.getElementDoubleAbs(itb.index);
									if (rtype == AbstractDataset.ARRAYFLOAT32 || rtype == AbstractDataset.ARRAYFLOAT64) {
										for (int k = 0; k < ISIZEA; k++) {
											answer[k] = Math.pow(a.getElementDoubleAbs(ita.index + k), xb);
										}
									} else {
										for (int k = 0; k < ISIZEA; k++) {
											v = Math.pow(a.getElementDoubleAbs(ita.index + k), xb);
											answer[k] = Double.isInfinite(v) || Double.isNaN(v) ? 0 : v;
										}
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							} else {
								while (ita.hasNext() && itb.hasNext()) {
									if (rtype == AbstractDataset.ARRAYFLOAT32 || rtype == AbstractDataset.ARRAYFLOAT64) {
										for (int k = 0; k < is; k++) {
											answer[k] = Math.pow(a.getElementDoubleAbs(ita.index + k),
													b.getElementDoubleAbs(itb.index + k));
										}
									} else {
										for (int k = 0; k < is; k++) {
											v = Math.pow(a.getElementDoubleAbs(ita.index + k),
													b.getElementDoubleAbs(itb.index + k));
											answer[k] = Double.isInfinite(v) || Double.isNaN(v) ? 0 : v;
										}
									}
									d.setObjectAbs(j, answer);
									j += is;
								}
							}
						}
					}
					if (d == null)
						break;
					start += System.nanoTime();
					double otime = ((double) start) / d.getSize();

					System.out.printf("Time taken by pow for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.
							* (otime - ntime) / otime);

					checkDatasets(a, b, c, d);

					n *= SSTEP;
				}
			}

			Random.seed(12735L);
			n = 32;
			System.out.println("Powering " + dn + " by constant");
			for (int i = 0; i < SITER; i++) {
				if (dtype < AbstractDataset.ARRAYINT8) {
					a = Random.randn(n);
					a.imultiply(100);
					a = a.cast(dtype);
				} else {
					AbstractDataset[] aa = new AbstractDataset[ISIZEA];
					for (int j = 0; j < ISIZEA; j++) {
						aa[j] = Random.randn(n).imultiply(100);
					}
					a = DatasetUtils.cast(aa, dtype);
				}

				start = -System.nanoTime();
				c = Maths.power(a, dv);
				start += System.nanoTime();
				double ntime = ((double) start)/c.getSize();

				d = AbstractDataset.zeros(c);
				start = -System.nanoTime();
				IndexIterator ita = a.getIterator();
				int j = 0;
				if (dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128) {
					final int is = d.getElementsPerItem();
					while (ita.hasNext()) {
						d.setObjectAbs(j, ((Complex) a.getObjectAbs(ita.index)).pow(zv));
						j += is;
					}
				} else {
					if (dtype < AbstractDataset.ARRAYINT8) {
						while (ita.hasNext()) {
							d.setObjectAbs(j++, Math.pow(((Number) a.getObjectAbs(ita.index)).doubleValue(), dv));
						}
					} else {
						final double[] answer = new double[ISIZEA];
						while (ita.hasNext()) {
							for (int k = 0; k < ISIZEA; k++) {
								answer[k] = Math.pow(a.getElementDoubleAbs(ita.index + k), dv);
							}
							d.setObjectAbs(j, answer);
							j += ISIZEA;
						}
					}
				}
				if (d == null)
					break;
				start += System.nanoTime();
				double otime = ((double) start)/d.getSize();

				System.out.printf("Time taken by pow for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.*(otime - ntime)/otime);

				checkDatasets(a, dv, c, d);

				n *= SSTEP;
			}

			Random.seed(12735L);
			n = 32;
			System.out.println("Powering constant by " + dn);
			for (int i = 0; i < SITER; i++) {
				if (dtype < AbstractDataset.ARRAYINT8) {
					a = Random.randn(n);
					a.imultiply(100);
					a = a.cast(dtype);
				} else {
					AbstractDataset[] aa = new AbstractDataset[ISIZEA];
					for (int j = 0; j < ISIZEA; j++) {
						aa[j] = Random.randn(n).imultiply(100);
					}
					a = DatasetUtils.cast(aa, dtype);
				}

				start = -System.nanoTime();
				c = Maths.power(dv, a);
				start += System.nanoTime();
				double ntime = ((double) start)/c.getSize();

				d = AbstractDataset.zeros(c);
				start = -System.nanoTime();
				IndexIterator ita = a.getIterator();
				int j = 0;
				if (dtype == AbstractDataset.COMPLEX64 || dtype == AbstractDataset.COMPLEX128) {
					final int is = d.getElementsPerItem();
					while (ita.hasNext()) {
						d.setObjectAbs(j, zv.pow((Complex) a.getObjectAbs(ita.index)));
						j += is;
					}
				} else {
					if (dtype < AbstractDataset.ARRAYINT8) {
						while (ita.hasNext()) {
							d.setObjectAbs(j++, Math.pow(dv, ((Number) a.getObjectAbs(ita.index)).doubleValue()));
						}
					} else {
						final double[] answer = new double[ISIZEA];
						while (ita.hasNext()) {
							for (int k = 0; k < ISIZEA; k++) {
								answer[k] = Math.pow(dv, a.getElementDoubleAbs(ita.index + k));
							}
							d.setObjectAbs(j, answer);
							j += ISIZEA;
						}
					}
				}
				if (d == null)
					break;
				start += System.nanoTime();
				double otime = ((double) start)/d.getSize();

				System.out.printf("Time taken by pow for %s: %s; %s (%.1f%%)\n", n, otime, ntime, 100.*(otime - ntime)/otime);

				checkDatasets(dv, a, c, d);

				n *= SSTEP;
			}
		}
	}

	@Test
	public void testDifference() {
		int[] data = {0,1,3,9,5,10};

		AbstractDataset a = new IntegerDataset(data, null);
		AbstractDataset d = Maths.difference(a, 1, -1);
		int[] tdata;
		tdata = new int[] {1,  2,  6, -4,  5};
		AbstractDataset ta = new IntegerDataset(tdata, null);
		checkDatasets(null, null, d, ta);

		d = Maths.difference(a.getSliceView(new int[] {3}, null, null), 1, -1);
		ta = Maths.difference(a.getSlice(new int[] {3}, null, null), 1, -1);
		checkDatasets(null, null, d, ta);
	}

	@Test
	public void testGradient() {
		double[] data = {1, 2, 4, 7, 11, 16};
		double[] tdata;

		AbstractDataset a = new DoubleDataset(data, null);
		AbstractDataset d = Maths.gradient(a).get(0);
		tdata = new double[] {1., 1.5, 2.5, 3.5, 4.5, 5.};
		AbstractDataset ta = new DoubleDataset(tdata, null);
		checkDatasets(null, null, d, ta);
		Slice[] slices = new Slice[] {new Slice(3)};
		d = Maths.gradient(a.getSliceView(slices)).get(0);
		ta = Maths.gradient(a.getSlice(slices)).get(0);
		checkDatasets(null, null, d, ta);

		
		AbstractDataset b = AbstractDataset.arange(a.getShape()[0], a.getDtype());
		b.imultiply(2);
		tdata = new double[] {0.5 , 0.75, 1.25, 1.75, 2.25, 2.5};
		ta = new DoubleDataset(tdata, null);
		d = Maths.gradient(a, b).get(0);
		checkDatasets(null, null, d, ta);
		d = Maths.gradient(a.getSliceView(slices), b.getSliceView(slices)).get(0);
		ta = Maths.gradient(a.getSlice(slices), b.getSlice(slices)).get(0);
		checkDatasets(null, null, d, ta);
		
		data = new double[] {1, 2, 6, 3, 4, 5};
		a = new DoubleDataset(data, 2, 3);
		List<AbstractDataset> l = Maths.gradient(a);
		tdata = new double[] { 2., 2., -1., 2., 2., -1.};
		ta = new DoubleDataset(tdata, 2, 3);
		checkDatasets(null, null, l.get(0), ta);
		tdata = new double[] { 1., 2.5, 4., 1., 1., 1.};
		ta = new DoubleDataset(tdata, 2, 3);
		checkDatasets(null, null, l.get(1), ta);

		b = AbstractDataset.arange(a.getShape()[0], a.getDtype());
		b.imultiply(2);
		AbstractDataset c = AbstractDataset.arange(a.getShape()[1], a.getDtype());
		c.imultiply(-1.5);

		l = Maths.gradient(a, b, c);
		tdata = new double[] { 2., 2., -1., 2., 2., -1.};
		ta = new DoubleDataset(tdata, 2, 3);
		ta.idivide(2);
		checkDatasets(null, null, l.get(0), ta);
		tdata = new double[] { 1., 2.5, 4., 1., 1., 1.};
		ta = new DoubleDataset(tdata, 2, 3);
		ta.idivide(-1.5);
		checkDatasets(null, null, l.get(1), ta);

	}

	/**
	 * Test rounding
	 */
	@Test
	public void testRounding() {
		DoubleDataset t;
		DoubleDataset x;
		double tol = 1e-6;

		double[] val = { -1.7, -1.5, -1.2, 0.3, 1.4, 1.5, 1.6 };
		t = new DoubleDataset(val);

		double[] resFloor = { -2, -2, -2, 0, 1, 1, 1 };
		x = (DoubleDataset) Maths.floor(t);
		for (int i = 0, imax = t.getSize(); i < imax; i++) {
			assertEquals(resFloor[i], x.get(i), tol);
		}

		double[] resCeil = { -1, -1, -1, 1, 2, 2, 2 };
		x = (DoubleDataset) Maths.ceil(t);
		for (int i = 0, imax = t.getSize(); i < imax; i++) {
			assertEquals(resCeil[i], x.get(i), tol);
		}

		double[] resRint= { -2, -2, -1, 0, 1, 2, 2 };
		x = (DoubleDataset) Maths.rint(t);
		for (int i = 0, imax = t.getSize(); i < imax; i++) {
			assertEquals(resRint[i], x.get(i), tol);
		}
	}
}
