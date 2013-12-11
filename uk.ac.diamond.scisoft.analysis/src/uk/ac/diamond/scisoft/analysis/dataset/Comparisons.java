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

import java.util.ArrayList;
import java.util.List;

/**
 * Comparison and logical methods
 */
public class Comparisons {
	/**
	 * Compare item-wise for whether a's element is equal b's
	 * <p>
	 * For multi-element items, comparison is true if all elements in an item
	 * are equal. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @return dataset where item is true if a == b
	 */
	public static BooleanDataset equalTo(AbstractDataset a, AbstractDataset b) {
		a.checkCompatibility(b);

		final BooleanDataset r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();
		final IndexIterator itb = b.getIterator();

		final int as = a.getElementsPerItem();
		final int bs = b.getElementsPerItem();

		int i = 0;
		if (as > bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double db = b.getElementDoubleAbs(itb.index);
				for (int j = 0; br && j < as; j++) {
					br &= a.getElementDoubleAbs(ita.index + j) == db;
				}
				r.setAbs(i++, br);
			}
		} else if (as < bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double da = a.getElementDoubleAbs(ita.index);
				for (int j = 0; br && j < bs; j++) {
					br &= da == b.getElementDoubleAbs(itb.index + j);
				}
				r.setAbs(i++, br);
			}
		} else {
			if (as == 1) {
				while (ita.hasNext() && itb.hasNext()) {
					r.setAbs(i++, a.getElementDoubleAbs(ita.index) == b.getElementDoubleAbs(itb.index));
				}
			} else {
				boolean br = true;
				while (ita.hasNext() && itb.hasNext()) {
					for (int j = 0; br && j < bs; j++) {
						br &= a.getElementDoubleAbs(ita.index + j) == b.getElementDoubleAbs(itb.index + j);
					}
					r.setAbs(i++, br);
				}
			}
		}
		return r;
	}

	/**
	 * Compare item-wise for whether a's element is equal b's
	 * <p>
	 * For multi-element items, comparison is true if all elements in an item
	 * are equal. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @return dataset where item is true if a == b
	 */
	public static BooleanDataset equalTo(Object a, Object b) {
		BooleanDataset r = null;

		if (a instanceof AbstractDataset) {
			AbstractDataset ad = (AbstractDataset) a;
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = equalTo(ad, bd);
			} else {
				r = new BooleanDataset(ad.shape);

				final IndexIterator ita = ad.getIterator();

				final int as = ad.getElementsPerItem();

				int i = 0;
				if (as == 1) {
					final double bv = AbstractDataset.toReal(b);
					while (ita.hasNext()) {
						r.setAbs(i++, ad.getElementDoubleAbs(ita.index) == bv);
					}
				} else {
					if (a instanceof ComplexFloatDataset || a instanceof ComplexDoubleDataset) {
						final double bv = AbstractDataset.toReal(b);
						final double bi = AbstractDataset.toImag(b);

						while (ita.hasNext()) {
							boolean br = ad.getElementDoubleAbs(ita.index) == bv;
							if (br)
								br &= ad.getElementDoubleAbs(ita.index + 1) == bi;
							r.setAbs(i++, br);
						}
						
					} else {
						final double[] bv = AbstractCompoundDataset.toDoubleArray(b, as);
						while (ita.hasNext()) {
							boolean br = true;
							for (int j = 0; br && j < as; j++) {
								br &= ad.getElementDoubleAbs(ita.index + j) == bv[j];
							}
							r.setAbs(i++, br);
						}
					}
				}
			}
		} else {
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = equalTo(bd, a);
			} else {
				throw new IllegalArgumentException("Both arguments are not datasets");
			}
		}

		return r;
	}

	/**
	 * Compare item-wise for whether a's element is almost equal to b's
	 * <p>
	 * For multi-element items, comparison is true if all elements in an item
	 * are equal up to a tolerance. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @param relTolerance
	 * @param absTolerance
	 * @return dataset where item is true if abs(a - b) <= absTol + relTol*max(abs(a),abs(b))
	 */
	public static BooleanDataset almostEqualTo(AbstractDataset a, AbstractDataset b, double relTolerance, double absTolerance) {
		a.checkCompatibility(b);

		final BooleanDataset r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();
		final IndexIterator itb = b.getIterator();

		final int as = a.getElementsPerItem();
		final int bs = b.getElementsPerItem();

		int i = 0;
		if (as > bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double db = b.getElementDoubleAbs(itb.index);
				final double adb = Math.abs(db);
				for (int j = 0; br && j < as; j++) {
					final double da = a.getElementDoubleAbs(ita.index + j);
					br &= Math.abs(da - db) <= absTolerance + relTolerance*Math.max(Math.abs(da), adb);
				}
				r.setAbs(i++, br);
			}			
		} else if (as < bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double da = a.getElementDoubleAbs(ita.index);
				final double ada = Math.abs(da);
				for (int j = 0; br && j < bs; j++) {
					final double db = b.getElementDoubleAbs(itb.index + j);
					br &= Math.abs(da - db) <= absTolerance + relTolerance*Math.max(ada, Math.abs(db));
				}
				r.setAbs(i++, br);
			}
		} else {
			if (as == 1) {
				while (ita.hasNext() && itb.hasNext()) {
					final double da = a.getElementDoubleAbs(ita.index);
					final double db = b.getElementDoubleAbs(itb.index);
					r.setAbs(i++, Math.abs(da - db) <= absTolerance + relTolerance*Math.max(Math.abs(da), Math.abs(db)));
				}
			} else {
				boolean br = true;
				while (ita.hasNext() && itb.hasNext()) {
					for (int j = 0; br && j < bs; j++) {
						final double da = a.getElementDoubleAbs(ita.index + j);
						final double db = b.getElementDoubleAbs(itb.index + j);
						br &= Math.abs(da - db) <= absTolerance + relTolerance*Math.max(Math.abs(da), Math.abs(db));
					}
					r.setAbs(i++, br);
				}
			}
		}
		return r;
	}

	/**
	 * Compare item-wise for whether a's element is almost equal to b's
	 * <p>
	 * For multi-element items, comparison is true if all elements in an item
	 * are equal up to a tolerance. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @param relTolerance
	 * @param absTolerance
	 * @return dataset where item is true if abs(a - b) <= absTol + relTol*max(abs(a),abs(b))
	 */
	public static BooleanDataset almostEqualTo(Object a, Object b, double relTolerance, double absTolerance) {
		BooleanDataset r = null;

		if (a instanceof AbstractDataset) {
			AbstractDataset ad = (AbstractDataset) a;
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = almostEqualTo(ad, bd, relTolerance, absTolerance);
			} else {
				r = new BooleanDataset(ad.shape);

				final IndexIterator ita = ad.getIterator();

				final int as = ad.getElementsPerItem();

				int i = 0;
				if (as == 1) {
					final double bv = AbstractDataset.toReal(b);
					while (ita.hasNext()) {
						final double av = ad.getElementDoubleAbs(ita.index);
						r.setAbs(i++, Math.abs(av - bv) <= absTolerance + relTolerance*Math.max(Math.abs(av), Math.abs(bv)));
					}
				} else {
					if (a instanceof ComplexFloatDataset || a instanceof ComplexDoubleDataset) {
						final double bv = AbstractDataset.toReal(b);
						final double abv = Math.abs(bv);
						final double bi = AbstractDataset.toImag(b);
						final double abi = Math.abs(bi);

						while (ita.hasNext()) {
							double av = ad.getElementDoubleAbs(ita.index);
							boolean br = Math.abs(av - bv) <= absTolerance + relTolerance*Math.max(Math.abs(av), abv);
							if (br) {
								av = ad.getElementDoubleAbs(ita.index + 1);
								br &= Math.abs(av - bi) <= absTolerance + relTolerance*Math.max(Math.abs(av), abi);
							}
							r.setAbs(i++, br);
						}
					} else {
						final double[] bv = AbstractCompoundDataset.toDoubleArray(b, as);
						final double[] abv = new double[as];
						for (int j = 0; j < as; j++) {
							abv[j] = Math.abs(bv[j]);
						}
						while (ita.hasNext()) {
							boolean br = true;
							for (int j = 0; br && j < as; j++) {
								double av = ad.getElementDoubleAbs(ita.index + j);
								br &= Math.abs(av - bv[j]) <= absTolerance + relTolerance*Math.max(Math.abs(av), abv[j]);
							}
							r.setAbs(i++, br);
						}
					}
				}
			}
		} else {
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = almostEqualTo(bd, a, relTolerance, absTolerance);
			} else {
				throw new IllegalArgumentException("Both arguments are not datasets");
			}
		}

		return r;
	}

	/**
	 * Compare item-wise for whether a's element is greater than b's
	 * <p>
	 * For multi-element items, comparison is true if all elements in an item
	 * are greater. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @return dataset where item is true if a > b
	 */
	public static BooleanDataset greaterThan(AbstractDataset a, AbstractDataset b) {
		a.checkCompatibility(b);

		final BooleanDataset r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();
		final IndexIterator itb = b.getIterator();

		final int as = a.getElementsPerItem();
		final int bs = b.getElementsPerItem();

		int i = 0;
		if (as > bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double db = b.getElementDoubleAbs(itb.index);
				for (int j = 0; br && j < as; j++) {
					br &= a.getElementDoubleAbs(ita.index + j) > db;
				}
				r.setAbs(i++, br);
			}
		} else if (as < bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double da = a.getElementDoubleAbs(ita.index);
				for (int j = 0; br && j < bs; j++) {
					br &= da > b.getElementDoubleAbs(itb.index + j);
				}
				r.setAbs(i++, br);
			}
		} else {
			if (as == 1) {
				while (ita.hasNext() && itb.hasNext()) {
					r.setAbs(i++, a.getElementDoubleAbs(ita.index) > b.getElementDoubleAbs(itb.index));
				}
			} else {
				boolean br = true;
				while (ita.hasNext() && itb.hasNext()) {
					for (int j = 0; br && j < bs; j++) {
						br &= a.getElementDoubleAbs(ita.index + j) > b.getElementDoubleAbs(itb.index + j);
					}
					r.setAbs(i++, br);
				}
			}
		}
		return r;
	}

	public static BooleanDataset greaterThan(Object a, Object b) {
		BooleanDataset r = null;

		if (a instanceof AbstractDataset) {
			AbstractDataset ad = (AbstractDataset) a;
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = equalTo(ad, bd);
			} else {
				r = new BooleanDataset(ad.shape);

				final IndexIterator ita = ad.getIterator();

				final int as = ad.getElementsPerItem();
				final double bv = AbstractDataset.toReal(b);

				int i = 0;
				if (as == 1) {
					while (ita.hasNext()) {
						r.setAbs(i++, ad.getElementDoubleAbs(ita.index) > bv);
					}
				} else {
					boolean br = true;
					while (ita.hasNext()) {
						for (int j = 0; br && j < as; j++) {
							br &= ad.getElementDoubleAbs(ita.index + j) > bv;
						}
						r.setAbs(i++, br);
					}
				}
			}
		} else {
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = lessThan(bd, a);
			} else {
				throw new IllegalArgumentException("Both arguments are not datasets");
			}
		}

		return r;
	}

	/**
	 * Compare item-wise for whether a's element is greater than or equal to b's
	 * <p>
	 * For multi-element items, comparison is true if all elements in an item
	 * are greater or equal. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @return dataset where item is true if a >= b
	 */
	public static BooleanDataset greaterThanOrEqualTo(AbstractDataset a, AbstractDataset b) {
		a.checkCompatibility(b);

		final BooleanDataset r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();
		final IndexIterator itb = b.getIterator();

		final int as = a.getElementsPerItem();
		final int bs = b.getElementsPerItem();

		int i = 0;
		if (as > bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double db = b.getElementDoubleAbs(itb.index);
				for (int j = 0; br && j < as; j++) {
					br &= a.getElementDoubleAbs(ita.index + j) >= db;
				}
				r.setAbs(i++, br);
			}
		} else if (as < bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double da = a.getElementDoubleAbs(ita.index);
				for (int j = 0; br && j < bs; j++) {
					br &= da >= b.getElementDoubleAbs(itb.index + j);
				}
				r.setAbs(i++, br);
			}
		} else {
			if (as == 1) {
				while (ita.hasNext() && itb.hasNext()) {
					r.setAbs(i++, a.getElementDoubleAbs(ita.index) >= b.getElementDoubleAbs(itb.index));
				}
			} else {
				boolean br = true;
				while (ita.hasNext() && itb.hasNext()) {
					for (int j = 0; br && j < bs; j++) {
						br &= a.getElementDoubleAbs(ita.index + j) >= b.getElementDoubleAbs(itb.index + j);
					}
					r.setAbs(i++, br);
				}
			}
		}
		return r;
	}

	public static BooleanDataset greaterThanOrEqualTo(Object a, Object b) {
		BooleanDataset r = null;

		if (a instanceof AbstractDataset) {
			AbstractDataset ad = (AbstractDataset) a;
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = equalTo(ad, bd);
			} else {
				r = new BooleanDataset(ad.shape);

				final IndexIterator ita = ad.getIterator();

				final int as = ad.getElementsPerItem();
				final double bv = AbstractDataset.toReal(b);

				int i = 0;
				if (as == 1) {
					while (ita.hasNext()) {
						r.setAbs(i++, ad.getElementDoubleAbs(ita.index) >= bv);
					}
				} else {
					boolean br = true;
					while (ita.hasNext()) {
						for (int j = 0; br && j < as; j++) {
							br &= ad.getElementDoubleAbs(ita.index + j) >= bv;
						}
						r.setAbs(i++, br);
					}
				}
			}
		} else {
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = lessThanOrEqualTo(bd, a);
			} else {
				throw new IllegalArgumentException("Both arguments are not datasets");
			}
		}

		return r;
	}

	/**
	 * Compare item-wise for whether a's element is less than b's
	 * <p>
	 * For multi-element items, comparison is true if all elements in an item
	 * are lesser. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @return dataset where item is true if a < b
	 */
	public static BooleanDataset lessThan(AbstractDataset a, AbstractDataset b) {
		a.checkCompatibility(b);

		final BooleanDataset r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();
		final IndexIterator itb = b.getIterator();

		final int as = a.getElementsPerItem();
		final int bs = b.getElementsPerItem();

		int i = 0;
		if (as > bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double db = b.getElementDoubleAbs(itb.index);
				for (int j = 0; br && j < as; j++) {
					br &= a.getElementDoubleAbs(ita.index + j) < db;
				}
				r.setAbs(i++, br);
			}
		} else if (as < bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double da = a.getElementDoubleAbs(ita.index);
				for (int j = 0; br && j < bs; j++) {
					br &= da < b.getElementDoubleAbs(itb.index + j);
				}
				r.setAbs(i++, br);
			}
		} else {
			if (as == 1) {
				while (ita.hasNext() && itb.hasNext()) {
					r.setAbs(i++, a.getElementDoubleAbs(ita.index) < b.getElementDoubleAbs(itb.index));
				}
			} else {
				boolean br = true;
				while (ita.hasNext() && itb.hasNext()) {
					for (int j = 0; br && j < bs; j++) {
						br &= a.getElementDoubleAbs(ita.index + j) < b.getElementDoubleAbs(itb.index + j);
					}
					r.setAbs(i++, br);
				}
			}
		}
		return r;
	}

	public static BooleanDataset lessThan(Object a, Object b) {
		BooleanDataset r = null;

		if (a instanceof AbstractDataset) {
			AbstractDataset ad = (AbstractDataset) a;
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = equalTo(ad, bd);
			} else {
				r = new BooleanDataset(ad.shape);

				final IndexIterator ita = ad.getIterator();

				final int as = ad.getElementsPerItem();
				final double bv = AbstractDataset.toReal(b);

				int i = 0;
				if (as == 1) {
					while (ita.hasNext()) {
						r.setAbs(i++, ad.getElementDoubleAbs(ita.index) < bv);
					}
				} else {
					boolean br = true;
					while (ita.hasNext()) {
						for (int j = 0; br && j < as; j++) {
							br &= ad.getElementDoubleAbs(ita.index + j) < bv;
						}
						r.setAbs(i++, br);
					}
				}
			}
		} else {
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = greaterThan(bd, a);
			} else {
				throw new IllegalArgumentException("Both arguments are not datasets");
			}
		}

		return r;
	}

	/**
	 * Compare item-wise for whether a's element is less than or equal to b's
	 * <p>
	 * For multi-element items, comparison is true if all elements in an item
	 * are lesser or equal. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @return dataset where item is true if a <= b
	 */
	public static BooleanDataset lessThanOrEqualTo(AbstractDataset a, AbstractDataset b) {
		a.checkCompatibility(b);

		final BooleanDataset r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();
		final IndexIterator itb = b.getIterator();

		final int as = a.getElementsPerItem();
		final int bs = b.getElementsPerItem();

		int i = 0;
		if (as > bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double db = b.getElementDoubleAbs(itb.index);
				for (int j = 0; br && j < as; j++) {
					br &= a.getElementDoubleAbs(ita.index + j) <= db;
				}
				r.setAbs(i++, br);
			}
		} else if (as < bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double da = a.getElementDoubleAbs(ita.index);
				for (int j = 0; br && j < bs; j++) {
					br &= da <= b.getElementDoubleAbs(itb.index + j);
				}
				r.setAbs(i++, br);
			}
		} else {
			if (as == 1) {
				while (ita.hasNext() && itb.hasNext()) {
					r.setAbs(i++, a.getElementDoubleAbs(ita.index) <= b.getElementDoubleAbs(itb.index));
				}
			} else {
				boolean br = true;
				while (ita.hasNext() && itb.hasNext()) {
					for (int j = 0; br && j < bs; j++) {
						br &= a.getElementDoubleAbs(ita.index + j) <= b.getElementDoubleAbs(itb.index + j);
					}
					r.setAbs(i++, br);
				}
			}
		}
		return r;
	}

	public static BooleanDataset lessThanOrEqualTo(Object a, Object b) {
		BooleanDataset r = null;

		if (a instanceof AbstractDataset) {
			AbstractDataset ad = (AbstractDataset) a;
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = equalTo(ad, bd);
			} else {
				r = new BooleanDataset(ad.shape);

				final IndexIterator ita = ad.getIterator();

				final int as = ad.getElementsPerItem();
				final double bv = AbstractDataset.toReal(b);

				int i = 0;
				if (as == 1) {
					while (ita.hasNext()) {
						r.setAbs(i++, ad.getElementDoubleAbs(ita.index) <= bv);
					}
				} else {
					boolean br = true;
					while (ita.hasNext()) {
						for (int j = 0; br && j < as; j++) {
							br &= ad.getElementDoubleAbs(ita.index + j) <= bv;
						}
						r.setAbs(i++, br);
					}
				}
			}
		} else {
			if (b instanceof AbstractDataset) {
				AbstractDataset bd = (AbstractDataset) b;
				r = greaterThanOrEqualTo(bd, a);
			} else {
				throw new IllegalArgumentException("Both arguments are not datasets");
			}
		}

		return r;
	}

	/**
	 * 
	 * @param a
	 * @param lo lower bound
	 * @param hi upper bound
	 * @return dataset where item is true if l <= a <= h
	 */
	public static BooleanDataset withinRange(AbstractDataset a, double lo, double hi) {
		if (lo >= hi) {
			throw new IllegalArgumentException("Lower bound must be less than upper bound");
		}

		BooleanDataset r = null;

		r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();

		final int as = a.getElementsPerItem();

		int i = 0;
		if (as == 1) {
			while (ita.hasNext()) {
				double x = a.getElementDoubleAbs(ita.index);
				r.setAbs(i++, x >= lo && x <= hi);
			}
		} else {
			boolean br = true;
			while (ita.hasNext()) {
				for (int j = 0; br && j < as; j++) {
					double x = a.getElementDoubleAbs(ita.index + j);
					br &= x >= lo && x <= hi;
					if (!br) // shortcut
						break;
				}
				r.setAbs(i++, br);
			}
		}
		return r;
	}

	/**
	 * Compare item-wise for whether a's element is almost equal to b's
	 * <p>
	 * For multi-element items, comparison is true if all elements in an item
	 * are equal up to a tolerance. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @param relTolerance
	 * @param absTolerance
	 * @return true if all items satisfy abs(a - b) <= absTol + relTol*max(abs(a),abs(b))
	 */
	public static boolean allCloseTo(AbstractDataset a, AbstractDataset b, double relTolerance, double absTolerance) {
		a.checkCompatibility(b);
	
		final IndexIterator ita = a.getIterator();
		final IndexIterator itb = b.getIterator();
	
		final int as = a.getElementsPerItem();
		final int bs = b.getElementsPerItem();
	
		if (as > bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double db = b.getElementDoubleAbs(itb.index);
				final double adb = Math.abs(db);
				for (int j = 0; br && j < as; j++) {
					final double da = a.getElementDoubleAbs(ita.index + j);
					if (Math.abs(da - db) > absTolerance + relTolerance*Math.max(Math.abs(da), adb))
						return false;
				}
			}			
		} else if (as < bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = true;
				final double da = a.getElementDoubleAbs(ita.index);
				final double ada = Math.abs(da);
				for (int j = 0; br && j < bs; j++) {
					final double db = b.getElementDoubleAbs(itb.index + j);
					if (Math.abs(da - db) > absTolerance + relTolerance*Math.max(ada, Math.abs(db)))
						return false;
				}
			}
		} else {
			if (as == 1) {
				while (ita.hasNext() && itb.hasNext()) {
					final double da = a.getElementDoubleAbs(ita.index);
					final double db = b.getElementDoubleAbs(itb.index);
					if (Math.abs(da - db) > absTolerance + relTolerance*Math.max(Math.abs(da), Math.abs(db)))
						return false;
				}
			} else {
				boolean br = true;
				while (ita.hasNext() && itb.hasNext()) {
					for (int j = 0; br && j < bs; j++) {
						final double da = a.getElementDoubleAbs(ita.index + j);
						final double db = b.getElementDoubleAbs(itb.index + j);
						if (Math.abs(da - db) > absTolerance + relTolerance*Math.max(Math.abs(da), Math.abs(db)))
							return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * @param a
	 * @return true if all elements are true
	 */
	public static boolean allTrue(AbstractDataset a) {
		final IndexIterator it = a.getIterator();
		final int as = a.getElementsPerItem();

		if (as == 1) {
			while (it.hasNext()) {
				if (!a.getElementBooleanAbs(it.index))
					return false;
			}
		} else {
			while (it.hasNext()) {
				for (int j = 0; j < as; j++) {
					if (!a.getElementBooleanAbs(it.index + j))
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * @param a
	 * @return true if all elements are true
	 */
	public static BooleanDataset allTrue(AbstractDataset a, int axis) {
		axis = a.checkAxis(axis);

		int rank = a.getRank();
		int[] oshape = a.getShape();
		int alen = oshape[axis];
		oshape[axis] = 1;

		int[] nshape = AbstractDataset.squeezeShape(oshape, false);

		BooleanDataset result = new BooleanDataset(nshape);

		IndexIterator qiter = result.getIterator(true);
		int[] qpos = qiter.getPos();
		int[] spos = oshape;

		while (qiter.hasNext()) {
			int i = 0;
			for (; i < axis; i++) {
				spos[i] = qpos[i];
			}
			spos[i++] = 0;
			for (; i < rank; i++) {
				spos[i] = qpos[i-1];
			}

			boolean br = true;
			for (int j = 0; br && j < alen; j++) {
				spos[axis] = j;
				br &= a.getBoolean(spos);
			}
			result.set(br, qpos);
		}
		return result;
	}

	/**
	 * @param a
	 * @return true if any element is true
	 */
	public static boolean anyTrue(AbstractDataset a) {
		final IndexIterator it = a.getIterator();
		final int as = a.getElementsPerItem();

		if (as == 1) {
			while (it.hasNext()) {
				if (a.getElementBooleanAbs(it.index))
					return true;
			}
		} else {
			while (it.hasNext()) {
				for (int j = 0; j < as; j++) {
					if (a.getElementBooleanAbs(it.index + j))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param a
	 * @return true if any element is true
	 */
	public static BooleanDataset anyTrue(AbstractDataset a, int axis) {
		axis = a.checkAxis(axis);

		int rank = a.getRank();
		int[] oshape = a.getShape();
		int alen = oshape[axis];
		oshape[axis] = 1;

		int[] nshape = AbstractDataset.squeezeShape(oshape, false);

		BooleanDataset result = new BooleanDataset(nshape);

		IndexIterator qiter = result.getIterator(true);
		int[] qpos = qiter.getPos();
		int[] spos = oshape;

		while (qiter.hasNext()) {
			int i = 0;
			for (; i < axis; i++) {
				spos[i] = qpos[i];
			}
			spos[i++] = 0;
			for (; i < rank; i++) {
				spos[i] = qpos[i-1];
			}

			boolean br = false;
			for (int j = 0; !br && j < alen; j++) {
				spos[axis] = j;
				br |= a.getBoolean(spos);
			}
			result.set(br, qpos);
		}
		return result;
	}

	/**
	 * Negate item-wise
	 * <p>
	 * For multi-element items, negation is false if all elements in a pair of items
	 * are true.
	 * @param a
	 * @return dataset where item is true when a is false
	 */
	public static BooleanDataset logicalNot(AbstractDataset a) {
		final BooleanDataset r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();

		final int as = a.getElementsPerItem();

		int i = 0;
		if (as == 1) {
			while (ita.hasNext()) {
				r.setAbs(i++, !a.getElementBooleanAbs(ita.index));
			}
		} else {
			boolean br = true;
			while (ita.hasNext()) {
				for (int j = 0; j < as; j++) {
					br &= a.getElementBooleanAbs(ita.index + j);
				}
				r.setAbs(i++, !br);
			}
		}
		return r;
	}

	/**
	 * Compare item-wise for whether a's item is true and b's true too.
	 * <p>
	 * For multi-element items, comparison is true if all elements in a pair of items
	 * are true. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @return dataset where item is true if a && b is true
	 */
	public static BooleanDataset logicalAnd(AbstractDataset a, AbstractDataset b) {
		a.checkCompatibility(b);

		final BooleanDataset r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();
		final IndexIterator itb = b.getIterator();

		final int as = a.getElementsPerItem();
		final int bs = b.getElementsPerItem();

		int i = 0;
		if (as > bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = b.getElementBooleanAbs(itb.index);
				for (int j = 0; j < as; j++) {
					br &= a.getElementBooleanAbs(ita.index + j);
				}
				r.setAbs(i++, br);
			}			
		} else if (as < bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = a.getElementBooleanAbs(ita.index);
				for (int j = 0; j < as; j++) {
					br &= b.getElementBooleanAbs(ita.index + j);
				}
				r.setAbs(i++, br);
			}
		} else {
			if (as == 1) {
				while (ita.hasNext() && itb.hasNext()) {
					r.setAbs(i++, a.getElementBooleanAbs(ita.index) && b.getElementBooleanAbs(itb.index));
				}
			} else {
				boolean br = true;
				while (ita.hasNext() && itb.hasNext()) {
					for (int j = 0; br && j < bs; j++) {
						br &= a.getElementBooleanAbs(ita.index + j) && b.getElementBooleanAbs(itb.index + j);
					}
					r.setAbs(i++, br);
				}
			}
		}
		return r;
	}

	/**
	 * Compare item-wise for whether a's item is true or b's true.
	 * <p>
	 * For multi-element items, comparison is true if any elements in a pair of items
	 * are true. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @return dataset where item is true if a || b is true
	 */
	public static BooleanDataset logicalOr(AbstractDataset a, AbstractDataset b) {
		a.checkCompatibility(b);

		final BooleanDataset r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();
		final IndexIterator itb = b.getIterator();

		final int as = a.getElementsPerItem();
		final int bs = b.getElementsPerItem();

		int i = 0;
		if (as > bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = b.getElementBooleanAbs(itb.index);
				for (int j = 0; j < as; j++) {
					br |= a.getElementBooleanAbs(ita.index + j);
				}
				r.setAbs(i++, br);
			}			
		} else if (as < bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = a.getElementBooleanAbs(ita.index);
				for (int j = 0; j < as; j++) {
					br |= b.getElementBooleanAbs(ita.index + j);
				}
				r.setAbs(i++, br);
			}
		} else {
			if (as == 1) {
				while (ita.hasNext() && itb.hasNext()) {
					r.setAbs(i++, a.getElementBooleanAbs(ita.index) || b.getElementBooleanAbs(itb.index));
				}
			} else {
				boolean br = true;
				while (ita.hasNext() && itb.hasNext()) {
					for (int j = 0; br && j < bs; j++) {
						br |= a.getElementBooleanAbs(ita.index + j) || b.getElementBooleanAbs(itb.index + j);
					}
					r.setAbs(i++, br);
				}
			}
		}
		return r;
	}

	/**
	 * Compare item-wise for whether a's item is true or b's true exclusively.
	 * <p>
	 * For multi-element items, comparison is true if one element in a pair of items
	 * is true. Where the datasets have mismatched item sizes, the first element
	 * of the dataset with smaller items is used for comparison.
	 * @param a
	 * @param b
	 * @return dataset where item is true if a ^ b is true
	 */
	public static BooleanDataset logicalXor(AbstractDataset a, AbstractDataset b) {
		a.checkCompatibility(b);

		final BooleanDataset r = new BooleanDataset(a.shape);

		final IndexIterator ita = a.getIterator();
		final IndexIterator itb = b.getIterator();

		final int as = a.getElementsPerItem();
		final int bs = b.getElementsPerItem();

		int i = 0;
		if (as > bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = b.getElementBooleanAbs(itb.index);
				for (int j = 0; j < as; j++) {
					br ^= a.getElementBooleanAbs(ita.index + j);
				}
				r.setAbs(i++, br);
			}			
		} else if (as < bs) {
			while (ita.hasNext() && itb.hasNext()) {
				boolean br = a.getElementBooleanAbs(ita.index);
				for (int j = 0; j < as; j++) {
					br ^= b.getElementBooleanAbs(ita.index + j);
				}
				r.setAbs(i++, br);
			}
		} else {
			if (as == 1) {
				while (ita.hasNext() && itb.hasNext()) {
					r.setAbs(i++, a.getElementBooleanAbs(ita.index) ^ b.getElementBooleanAbs(itb.index));
				}
			} else {
				boolean br = true;
				while (ita.hasNext() && itb.hasNext()) {
					for (int j = 0; br && j < bs; j++) {
						br ^= a.getElementBooleanAbs(ita.index + j) ^ b.getElementBooleanAbs(itb.index + j);
					}
					r.setAbs(i++, br);
				}
			}
		}
		return r;
	}

	/**
	 * Create a list of indices of positions where items are non-zero
	 * @param a
	 * @return list of indices as integer datasets
	 */
	public static List<IntegerDataset> nonZero(AbstractDataset a) {
		final int rank = a.getRank();
		final List<List<Integer>> indices = new ArrayList<List<Integer>>();
		List<IntegerDataset> indexList = new ArrayList<IntegerDataset>();

		if (rank == 0)
			return indexList;

		for (int j = 0; j < rank; j++) {
			indices.add(new ArrayList<Integer>());
		}

		final IndexIterator iter = a.getIterator(true);
		final int[] pos = iter.getPos();
		while (iter.hasNext()) {
			if (a.getElementBooleanAbs(iter.index)) {
				for (int j = 0; j < rank; j++) {
					indices.get(j).add(pos[j]);
				}
			}
		}

	
		final int length = indices.get(0).size();
		if (length > 0 ) {
			for (int j = 0; j < rank; j++) {
				indexList.add((IntegerDataset) AbstractDataset.createFromList(indices.get(j)));
			}
		}
		return indexList;
	}

	/**
	 * Check item-wise for whether any a's elements are Not-a-Numbers
	 * <p>
	 * For multi-element items, check is true if any elements in an item is Not-a-Number.
	 * @param a
	 * @return dataset where item is true if any of its elements are NaNs
	 */
	public static BooleanDataset isNaN(AbstractDataset a) {
		BooleanDataset r = null;

		r = new BooleanDataset(a.shape);

		if (!a.hasFloatingPointElements()) {
			return r;
		}

		final IndexIterator ita = a.getIterator();

		final int as = a.getElementsPerItem();

		int i = 0;
		if (as == 1) {
			while (ita.hasNext()) {
				r.setAbs(i++, Double.isNaN(a.getElementDoubleAbs(ita.index)));
			}
		} else {
			if (a instanceof ComplexFloatDataset || a instanceof ComplexDoubleDataset) {
				while (ita.hasNext()) {
					r.setAbs(i++, Double.isNaN(a.getElementDoubleAbs(ita.index)) || Double.isNaN(a.getElementDoubleAbs(ita.index + 1)));
				}
			} else {
				while (ita.hasNext()) {
					boolean br = false;
					for (int j = 0; j < as; j++) {
						if (Double.isNaN(a.getElementDoubleAbs(ita.index + j))) {
							br = true;
							break;
						}
					}
					r.setAbs(i++, br);
				}
			}
		}

		return r;
	}

	/**
	 * Check item-wise for whether any a's elements are infinite
	 * <p>
	 * For multi-element items, check is true if any elements in an item is infinite
	 * @param a
	 * @return dataset where item is true if any of its elements are infinite
	 */
	public static BooleanDataset isInfinite(AbstractDataset a) {
		BooleanDataset r = null;

		r = new BooleanDataset(a.shape);

		if (!a.hasFloatingPointElements()) {
			return r;
		}

		final IndexIterator ita = a.getIterator();

		final int as = a.getElementsPerItem();

		int i = 0;
		if (as == 1) {
			while (ita.hasNext()) {
				r.setAbs(i++, Double.isInfinite(a.getElementDoubleAbs(ita.index)));
			}
		} else {
			if (a instanceof ComplexFloatDataset || a instanceof ComplexDoubleDataset) {
				while (ita.hasNext()) {
					r.setAbs(i++, Double.isInfinite(a.getElementDoubleAbs(ita.index)) || Double.isInfinite(a.getElementDoubleAbs(ita.index + 1)));
				}
			} else {
				while (ita.hasNext()) {
					boolean br = false;
					for (int j = 0; j < as; j++) {
						if (Double.isInfinite(a.getElementDoubleAbs(ita.index + j))) {
							br = true;
							break;
						}
					}
					r.setAbs(i++, br);
				}
			}
		}

		return r;
	}

	/**
	 * Check item-wise for whether any a's elements are positive infinite
	 * <p>
	 * For multi-element items, the check is true if any elements in an item is positive infinite
	 * @param a
	 * @return dataset where items are true if any of its elements are positive infinite
	 */
	public static BooleanDataset isPositiveInfinite(AbstractDataset a) {
		return isEqual(a, Double.POSITIVE_INFINITY);
	}

	/**
	 * Check item-wise for whether any a's elements are negative infinite
	 * <p>
	 * For multi-element items, the check is true if any elements in an item is negative infinite
	 * @param a
	 * @return dataset where items are true if any of its elements are negative infinite
	 */
	public static BooleanDataset isNegativeInfinite(AbstractDataset a) {
		return isEqual(a, Double.NEGATIVE_INFINITY);
	}

	/**
	 * Check item-wise for whether any a's elements match given item
	 * <p>
	 * For multi-element items, the check is true if any elements in an item matches
	 * @param a
	 * @param match
	 * @return dataset where items are true if any of its elements match
	 */
	private static BooleanDataset isEqual(AbstractDataset a, final double match) {
		BooleanDataset r = null;

		r = new BooleanDataset(a.shape);

		if (!a.hasFloatingPointElements()) {
			return r;
		}

		final IndexIterator ita = a.getIterator();

		final int as = a.getElementsPerItem();

		int i = 0;
		if (as == 1) {
			while (ita.hasNext()) {
				final double rv = a.getElementDoubleAbs(ita.index);
				r.setAbs(i++, rv == match);
			}
		} else {
			if (a instanceof ComplexFloatDataset || a instanceof ComplexDoubleDataset) {
				while (ita.hasNext()) {
					final double rv = a.getElementDoubleAbs(ita.index);
					final double iv = a.getElementDoubleAbs(ita.index + 1);
					r.setAbs(i++, (rv == match) || (iv == match));
				}
			} else {
				while (ita.hasNext()) {
					boolean br = false;
					for (int j = 0; j < as; j++) {
						final double rv = a.getElementDoubleAbs(ita.index + j);
						if (rv == match) {
							br = true;
							break;
						}
					}
					r.setAbs(i++, br);
				}
			}
		}

		return r;
	}

	/**
	 * Check item-wise for whether any a's elements are finite (or not infinite and not Not-a-Number)
	 * <p>
	 * For multi-element items, check is true if any elements in an item is finite
	 * @param a
	 * @return dataset where item is true if any of its elements are finite
	 */
	public static BooleanDataset isFinite(AbstractDataset a) {
		BooleanDataset r = null;

		r = new BooleanDataset(a.shape);

		if (!a.hasFloatingPointElements()) {
			r.fill(true);
			return r;
		}

		final IndexIterator ita = a.getIterator();

		final int as = a.getElementsPerItem();

		int i = 0;
		if (as == 1) {
			while (ita.hasNext()) {
				final double rv = a.getElementDoubleAbs(ita.index);
				r.setAbs(i++, !(Double.isInfinite(rv) || Double.isNaN(rv)));
			}
		} else {
			if (a instanceof ComplexFloatDataset || a instanceof ComplexDoubleDataset) {
				while (ita.hasNext()) {
					final double rv = a.getElementDoubleAbs(ita.index);
					final double iv = a.getElementDoubleAbs(ita.index + 1);
					r.setAbs(i++, !(Double.isInfinite(rv) || Double.isNaN(rv) || Double.isInfinite(iv) || Double.isNaN(iv)));
				}
			} else {
				while (ita.hasNext()) {
					boolean br = false;
					for (int j = 0; j < as; j++) {
						final double rv = a.getElementDoubleAbs(ita.index + j);
						if (!(Double.isInfinite(rv) || Double.isNaN(rv))) {
							br = true;
							break;
						}
					}
					r.setAbs(i++, br);
				}
			}
		}

		return r;
	}
}
