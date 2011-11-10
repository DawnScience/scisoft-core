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

import java.util.Arrays;

public class LinearAlgebra {

	private static final int CROSSOVERPOINT = 16; // point at which using slice iterators for inner loop is faster 

	/**
	 * Calculate the tensor dot product over given axes. This is the sum of products of elements selected
	 * from the given axes in each dataset
	 * @param a
	 * @param b
	 * @param axisa axis dimension in a to sum over (can be -ve)
	 * @param axisb axis dimension in b to sum over (can be -ve)
	 * @return tensor dot product
	 */
	public static AbstractDataset tensorDotProduct(final AbstractDataset a, final AbstractDataset b, final int axisa, final int axisb) {
		// this is slower for summing lengths < ~15
		final int[] ashape = a.shape;
		final int[] bshape = b.shape;
		final int arank = ashape.length;
		final int brank = bshape.length;
		int aaxis = axisa;
		if (aaxis < 0)
			aaxis += arank;
		if (aaxis < 0 || aaxis >= arank)
			throw new IllegalArgumentException("Summing axis outside valid rank of 1st dataset");

		if (ashape[aaxis] < CROSSOVERPOINT) { // faster to use position iteration
			return tensorDotProduct(a, b, new int[] {axisa}, new int[] {axisb});
		}
		int baxis = axisb;
		if (baxis < 0)
			baxis += arank;
		if (baxis < 0 || baxis >= arank)
			throw new IllegalArgumentException("Summing axis outside valid rank of 2nd dataset");

		final boolean[] achoice = new boolean[arank];
		final boolean[] bchoice = new boolean[brank];
		Arrays.fill(achoice, true);
		Arrays.fill(bchoice, true);
		achoice[aaxis] = false; // flag which axes not to iterate over
		bchoice[baxis] = false;

		final boolean[] notachoice = new boolean[arank];
		final boolean[] notbchoice = new boolean[brank];
		notachoice[aaxis] = true; // flag which axes to iterate over
		notbchoice[baxis] = true;

		int drank = arank + brank - 2;
		int[] dshape = new int[drank];
		int d = 0;
		for (int i = 0; i < arank; i++) {
			if (achoice[i])
				dshape[d++] = ashape[i];
		}
		for (int i = 0; i < brank; i++) {
			if (bchoice[i])
				dshape[d++] = bshape[i];
		}
		int dtype = AbstractDataset.getBestDType(a.getDtype(), b.getDtype());
		AbstractDataset data = AbstractDataset.zeros(dshape, dtype);

		SliceIterator ita = a.getSliceIteratorFromAxes(null, achoice);
		int l = 0;
		final int[] apos = ita.getPos();
		while (ita.hasNext()) {
			SliceIterator itb = b.getSliceIteratorFromAxes(null, bchoice);
			final int[] bpos = itb.getPos();
			while (itb.hasNext()) {
				SliceIterator itaa = a.getSliceIteratorFromAxes(apos, notachoice);
				SliceIterator itba = b.getSliceIteratorFromAxes(bpos, notbchoice);
				double sum = 0.0;
				double com = 0.0;
				while (itaa.hasNext() && itba.hasNext()) {
					final double y = a.getElementDoubleAbs(itaa.index) * b.getElementDoubleAbs(itba.index) - com;
					final double t = sum + y;
					com = (t - sum) - y;
					sum = t;
				}
				data.setObjectAbs(l++, sum);
			}
		}

		return data;
	}

	/**
	 * Calculate the tensor dot product over given axes. This is the sum of products of elements selected
	 * from the given axes in each dataset
	 * @param a
	 * @param b
	 * @param axisa axis dimensions in a to sum over (can be -ve)
	 * @param axisb axis dimensions in b to sum over (can be -ve)
	 * @return tensor dot product
	 */
	public static AbstractDataset tensorDotProduct(final AbstractDataset a, final AbstractDataset b, final int[] axisa, final int[] axisb) {
		if (axisa.length != axisb.length) {
			throw new IllegalArgumentException("Numbers of summing axes must be same");
		}
		final int[] ashape = a.shape;
		final int[] bshape = b.shape;
		final int arank = ashape.length;
		final int brank = bshape.length;
		final int[] aaxes = new int[axisa.length];
		final int[] baxes = new int[axisa.length];
		for (int i = 0; i < axisa.length; i++) {
			int n;

			n = axisa[i];
			if (n < 0) n += arank;
			if (n < 0 || n >= arank)
				throw new IllegalArgumentException("Summing axis outside valid rank of 1st dataset");
			aaxes[i] = n;

			n = axisb[i];
			if (n < 0) n += brank;
			if (n < 0 || n >= brank)
				throw new IllegalArgumentException("Summing axis outside valid rank of 2nd dataset");
			baxes[i] = n;

			if (ashape[aaxes[i]] != bshape[n])
				throw new IllegalArgumentException("Summing axes do not have matching lengths");
		}

		final boolean[] achoice = new boolean[arank];
		final boolean[] bchoice = new boolean[brank];
		Arrays.fill(achoice, true);
		Arrays.fill(bchoice, true);
		for (int i = 0; i < aaxes.length; i++) { // flag which axes to iterate over
			achoice[aaxes[i]] = false;
			bchoice[baxes[i]] = false;
		}

		int drank = arank + brank - 2*aaxes.length;
		int[] dshape = new int[drank];
		int d = 0;
		for (int i = 0; i < arank; i++) {
			if (achoice[i])
				dshape[d++] = ashape[i];
		}
		for (int i = 0; i < brank; i++) {
			if (bchoice[i])
				dshape[d++] = bshape[i];
		}
		int dtype = AbstractDataset.getBestDType(a.getDtype(), b.getDtype());
		AbstractDataset data = AbstractDataset.zeros(dshape, dtype);

		SliceIterator ita = a.getSliceIteratorFromAxes(null, achoice);
		int l = 0;
		final int[] apos = ita.getPos();
		while (ita.hasNext()) {
			SliceIterator itb = b.getSliceIteratorFromAxes(null, bchoice);
			final int[] bpos = itb.getPos();
			while (itb.hasNext()) {
				double sum = 0.0;
				double com = 0.0;
				apos[aaxes[aaxes.length - 1]] = -1;
				bpos[baxes[aaxes.length - 1]] = -1;
				while (true) { // step through summing axes
					int e = aaxes.length - 1;
					for (; e >= 0; e--) {
						int ai = aaxes[e];
						int bi = baxes[e];

						apos[ai]++;
						bpos[bi]++;
						if (apos[ai] == ashape[ai]) {
							apos[ai] = 0;
							bpos[bi] = 0;
						} else
							break;
					}
					if (e == -1) break;
					final double y = a.getDouble(apos) * b.getDouble(bpos) - com;
					final double t = sum + y;
					com = (t - sum) - y;
					sum = t;
				}
				data.setObjectAbs(l++, sum);
			}
		}

		return data;
	}

	/**
	 * Calculate the dot product of two datasets. When <b>b</b> is a 1D dataset, the sum product over
	 * the last axis of <b>a</b> and <b>b</b> is returned. Where <b>a</b> is also a 1D dataset, a "scalar" dataset
	 * is returned. If <b>b</b> is 2D or higher, its second-to-last axis is used
	 * @param a
	 * @param b
	 * @return dot product
	 */
	public static AbstractDataset dotProduct(AbstractDataset a, AbstractDataset b) {
		if (b.getRank() < 2)
			return tensorDotProduct(a, b, -1, 0);
		return tensorDotProduct(a, b, -1, -2);
	}
}
