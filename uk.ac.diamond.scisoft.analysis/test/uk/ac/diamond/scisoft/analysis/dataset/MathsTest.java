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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.math.complex.Complex;
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
