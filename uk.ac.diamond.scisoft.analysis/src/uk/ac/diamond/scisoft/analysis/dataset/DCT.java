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

import edu.emory.mathcs.jtransforms.dct.DoubleDCT_1D;
import edu.emory.mathcs.jtransforms.dct.DoubleDCT_2D;
import edu.emory.mathcs.jtransforms.dct.DoubleDCT_3D;
import edu.emory.mathcs.jtransforms.dct.FloatDCT_1D;
import edu.emory.mathcs.jtransforms.dct.FloatDCT_2D;
import edu.emory.mathcs.jtransforms.dct.FloatDCT_3D;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// TODO fast path for defaults?
//      axes and shape correspondence

/**
 * Class to hold methods to compute Discrete Cosine Transforms (DCT)
 * 
 */
public class DCT {
	/**
	 * Setup the logging facilities
	 */
	transient protected static final Logger logger = LoggerFactory.getLogger(DCT.class);

	/**
	 * forward 1D Discrete Cosine Transform (DCT-II)
	 * @param a dataset
	 * @return new dataset holding transform
	 */
	public static AbstractDataset dct(final AbstractDataset a) {
		return dct(a, a.shape[a.shape.length - 1], -1);
	}

	/**
	 * forward 1D Discrete Cosine Transform (DCT-II)
	 * @param a dataset
	 * @param n number of points
	 * @param axis (negative numbers refer to axes from end, eg. -1 is last axis)
	 * @return new dataset holding transform
	 */
	public static AbstractDataset dct(final AbstractDataset a, final int n, int axis) {
		if (n <= 0) {
			logger.error("number of points should be greater than zero");
			throw new IllegalArgumentException("number of points should be greater than zero");
		}
		axis = a.checkAxis(axis);

		return dct1d(a, n, axis);
	}

	/**
	 * forward 2D Discrete Cosine Transform (DCT-II)
	 * @param a dataset
	 * @param s shape of DCT dataset (if null, use whole dataset)
	 * @param axes for DCT (if null, default as [-2,-1])
	 * @return new dataset holding transform
	 */
	public static AbstractDataset dct2(final AbstractDataset a, int[] s, int[] axes) {
		int rank = a.getRank();
		if (rank < 2) {
			logger.error("dataset should be at least 2 dimensional");
			throw new IllegalArgumentException("dataset should be at least 2 dimensional");
		}
		if (axes == null) {
			axes = new int[] {rank-2, rank-1};
		} else if (axes.length != 2) {
			logger.error("axes should have two entries");
			throw new IllegalArgumentException("axes should have two entries");
		}
		if (s == null) {
			s = new int[2];
			int[] shape = a.getShape();
			s[0] = shape[axes[0]];
			s[1] = shape[axes[1]];
		} else if (s.length < 2) {
			logger.error("shape should not have more than 2 dimensions");
			throw new IllegalArgumentException("shape should not have more than 2 dimensions");
		}
		return dctn(a, s, axes);
	}

	/**
	 * forward nD Discrete Cosine Transform (DCT-II)
	 * @param a dataset
	 * @param s shape of DCT dataset (if null, use whole dataset)
	 * @param axes for DCT (if null, default as [..., -1])
	 * @return new dataset holding transform
	 */
	public static AbstractDataset dctn(final AbstractDataset a, int[] s, int[] axes) {
		int[] shape = a.getShape();
		int rank = shape.length;
		AbstractDataset result = null;

		if (s == null) {
			if (axes == null) {
				s = shape;
				axes = new int[rank];
				for (int i = 0; i < rank; i++)
					axes[i] = i;
			} else {
				s = new int[axes.length];
				Arrays.sort(axes);
				for (int i = 0; i < axes.length; i++) {
					axes[i] = a.checkAxis(axes[i]);

					s[i] = shape[axes[i]];
				}
			}
		} else {
			if (s.length > rank) {
				logger.error("shape of DCT should not have more dimensions than dataset");
				throw new IllegalArgumentException("shape of DCT should not have more dimensions than dataset");
			}
			if (axes == null) {
				axes = new int[s.length];
				for (int i = 0; i < s.length; i++)
					axes[i] = rank - s.length + i;
			} else {
				if (s.length != axes.length) {
					logger.error("shape of DCT should have same rank as axes");
					throw new IllegalArgumentException("shape of DCT should have same rank as axes");
				}
			}
		}

		if (s.length > 3) {
			logger.error("DCT across more than 3 dimensions are not supported");
			throw new IllegalArgumentException("DCT across more than 3 dimensions are not supported");
		}

		for (int i = 0; i < axes.length; i++) {
			if (s[i] <= 0) {
				logger.error("dimensions should be greater than zero");
				throw new IllegalArgumentException("dimensions should be greater than zero");
			}
			axes[i] = a.checkAxis(axes[i]);
		}

		switch (s.length) {
		case 1:
			result = dct1d(a, s[0], axes[0]);
			break;
		case 2:
			result = dct2d(a, s, axes);
			break;
		case 3:
			result = dct3d(a, s, axes);
			break;
		}

		return result;
	}

	private static int[] newShape(final int[] shape, final int[] s, final int[] axes) {
		int[] nshape = shape.clone();

		for (int i = 0; i < s.length; i++) {
			nshape[axes[i]] = s[i];
		}
		return nshape;
	}

	private static AbstractDataset dct1d(final AbstractDataset a, final int n, final int axis) {
		AbstractDataset result = null;
		AbstractDataset dest = null;

		int[] shape;
		PositionIterator pi;
		int[] pos;
		boolean[] hit;

		switch (a.getDtype()) {
		case AbstractDataset.FLOAT32:
			FloatDCT_1D ffft = new FloatDCT_1D(n);
			shape = a.getShape().clone();
			shape[axis] = n;
			result = new FloatDataset(shape);
			dest = new FloatDataset(new int[] {n});
			float[] fdata = (float[]) dest.odata;
			pi = a.getPositionIterator(axis);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(fdata, 0.f);
				a.copyItemsFromAxes(pos, hit, dest);
				ffft.forward(fdata, true);
				result.setItemsOnAxes(pos, hit, fdata);
			}
			break;
		case AbstractDataset.FLOAT64:
			DoubleDCT_1D dfft = new DoubleDCT_1D(n);
			shape = a.getShape().clone();
			shape[axis] = n;
			result = new DoubleDataset(shape);
			dest = new DoubleDataset(new int[] {n});
			double[] ddata = (double[]) dest.odata;
			pi = a.getPositionIterator(axis);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(ddata, 0.);
				a.copyItemsFromAxes(pos, hit, dest);
				dfft.forward(ddata, true);
				result.setItemsOnAxes(pos, hit, ddata);
			}
			break;
		default:
			logger.warn("Non-float dataset not yet supported");
			break;
		}

		return result;
	}

	private static AbstractDataset dct2d(final AbstractDataset a, final int[] s, final int[] axes) {
		AbstractDataset result = null;
		AbstractDataset dest = null;

		PositionIterator pi;
		int[] pos;
		boolean[] hit;

		switch (a.getDtype()) {
		case AbstractDataset.FLOAT32:
			FloatDCT_2D ffft = new FloatDCT_2D(s[0], s[1]);
			float[] fdata = null;
			result = new FloatDataset(newShape(a.shape, s, axes));
			dest = new FloatDataset(s);
			fdata = (float[]) dest.odata;
			pi = a.getPositionIterator(axes);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(fdata, 0.f);
				a.copyItemsFromAxes(pos, hit, dest);
				ffft.forward(fdata, true);
				result.setItemsOnAxes(pos, hit, fdata);
			}
			break;
		case AbstractDataset.FLOAT64:
			DoubleDCT_2D dfft = new DoubleDCT_2D(s[0], s[1]);
			double[] ddata = null;
			result = new DoubleDataset(newShape(a.shape, s, axes));
			dest = new DoubleDataset(s);
			ddata = (double[]) dest.odata;
			pi = a.getPositionIterator(axes);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(ddata, 0.);
				a.copyItemsFromAxes(pos, hit, dest);
				dfft.forward(ddata, true);
				result.setItemsOnAxes(pos, hit, ddata);
			}
			break;
		default:
			logger.warn("Non-float dataset not yet supported");
			break;
		}

		return result;
	}

	private static AbstractDataset dct3d(final AbstractDataset a, final int[] s, final int[] axes) {
		AbstractDataset result = null;
		AbstractDataset dest = null;

		PositionIterator pi;
		int[] pos;
		boolean[] hit;

		switch (a.getDtype()) {
		case AbstractDataset.FLOAT32:
			FloatDCT_3D ffft = new FloatDCT_3D(s[0], s[1], s[2]);

			float[] fdata = null;
			result = new FloatDataset(newShape(a.shape, s, axes));
			dest = new FloatDataset(s);
			fdata = (float[]) dest.odata;
			pi = a.getPositionIterator(axes);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(fdata, 0.f);
				a.copyItemsFromAxes(pos, hit, dest);
				ffft.forward(fdata, true);
				result.setItemsOnAxes(pos, hit, fdata);
			}
			break;
		case AbstractDataset.FLOAT64:
			DoubleDCT_3D dfft = new DoubleDCT_3D(s[0], s[1], s[2]);

			double[] ddata = null;
			result = new DoubleDataset(newShape(a.shape, s, axes));
			dest = new DoubleDataset(s);
			ddata = (double[]) dest.odata;
			pi = a.getPositionIterator(axes);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(ddata, 0.);
				a.copyItemsFromAxes(pos, hit, dest);
				dfft.forward(ddata, true);
				result.setItemsOnAxes(pos, hit, ddata);
			}
			break;
		default:
			logger.warn("Non-float dataset not yet supported");
			break;
		}

		return result;
	}

	/**
	 * inverse 1D Discrete Cosine Transform (DCT-III)
	 * @param a dataset
	 * @return new dataset holding transform
	 */
	public static AbstractDataset idct(final AbstractDataset a) {
		return idct(a, a.shape[a.shape.length-1], -1);
	}

	/**
	 * inverse 1D Discrete Cosine Transform (DCT-III)
	 * @param a dataset
	 * @param n number of points
	 * @param axis (negative numbers refer to axes from end, eg. -1 is last axis)
	 * @return new dataset holding transform
	 */
	public static AbstractDataset idct(final AbstractDataset a, final int n, int axis) {
		if (n <= 0) {
			logger.error("number of points should be greater than zero");
			throw new IllegalArgumentException("number of points should be greater than zero");
		}
		axis = a.checkAxis(axis);

		return idct1d(a, n, axis);
	}

	/**
	 * inverse 2D Discrete Cosine Transform (DCT-III)
	 * @param a dataset
	 * @param s shape of DCT dataset (if null, use whole dataset)
	 * @param axes for DCT (default as [-2,-1])
	 * @return new dataset holding transform
	 */
	public static AbstractDataset idct2(final AbstractDataset a, int[] s, int[] axes) {
		int rank = a.getRank();
		if (rank < 2) {
			logger.error("dataset should be at least 2 dimensional");
			throw new IllegalArgumentException("dataset should be at least 2 dimensional");
		}
		if (axes == null) {
			axes = new int[] {rank-2, rank-1};
		} else if (axes.length != 2) {
			logger.error("axes should have two entries");
			throw new IllegalArgumentException("axes should have two entries");
		}
		if (s == null) {
			s = new int[2];
			int[] shape = a.getShape();
			s[0] = shape[axes[0]];
			s[1] = shape[axes[1]];
		} else if (s.length < 2) {
			logger.error("shape should not have more than 2 dimensions");
			throw new IllegalArgumentException("shape should not have more than 2 dimensions");
		}
		return idctn(a, s, axes);
	}

	/**
	 * inverse nD Discrete Cosine Transform (DCT-III)
	 * @param a dataset
	 * @param s shape of DCT dataset (if null, use whole dataset)
	 * @param axes for DCT (if null, default as [..., -1])
	 * @return new dataset holding transform
	 */
	public static AbstractDataset idctn(final AbstractDataset a, int[] s, int[] axes) {
		int[] shape = a.getShape();
		int rank = shape.length;
		AbstractDataset result = null;

		if (s == null) {
			if (axes == null) {
				s = shape;
				axes = new int[rank];
				for (int i = 0; i < rank; i++)
					axes[i] = i;
			} else {
				s = new int[axes.length];
				Arrays.sort(axes);
				for (int i = 0; i < axes.length; i++) {
					axes[i] = a.checkAxis(axes[i]);
					s[i] = shape[axes[i]];
				}
			}
		} else {
			if (s.length > rank) {
				logger.error("shape of DCT should not have more dimensions than dataset");
				throw new IllegalArgumentException("shape of DCT should not have more dimensions than dataset");
			}
			if (axes == null) {
				axes = new int[s.length];
				for (int i = 0; i < s.length; i++)
					axes[i] = rank - s.length + i;
			} else {
				if (s.length != axes.length) {
					logger.error("shape of DCT should have same rank as axes");
					throw new IllegalArgumentException("shape of DCT should have same rank as axes");
				}
			}
		}

		if (s.length > 3) {
			logger.error("DCT across more than 3 dimensions are not supported");
			throw new IllegalArgumentException("DCT across more than 3 dimensions are not supported");
		}

		for (int i = 0; i < axes.length; i++) {
			if (s[i] <= 0) {
				logger.error("dimensions should be greater than zero");
				throw new IllegalArgumentException("dimensions should be greater than zero");
			}
			axes[i] = a.checkAxis(axes[i]);
		}

		switch (s.length) {
		case 1:
			result = idct1d(a, s[0], axes[0]);
			break;
		case 2:
			result = idct2d(a, s, axes);
			break;
		case 3:
			result = idct3d(a, s, axes);
			break;
		}

		return result;
	}

	private static AbstractDataset idct1d(final AbstractDataset a, final int n, final int axis) {
		AbstractDataset result = null;
		AbstractDataset dest = null;

		int[] shape;
		PositionIterator pi;
		int[] pos;
		boolean[] hit;

		switch (a.getDtype()) {
		case AbstractDataset.FLOAT32:
			FloatDCT_1D ffft = new FloatDCT_1D(n);
			float[] fdata = null;
			shape = a.getShape().clone();
			shape[axis] = n;
			result = new FloatDataset(shape);
			dest = new FloatDataset(new int[] {n});
			fdata = (float[]) dest.odata;
			pi = a.getPositionIterator(axis);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(fdata, 0.f);
				a.copyItemsFromAxes(pos, hit, dest);
				ffft.inverse(fdata, true);
				result.setItemsOnAxes(pos, hit, fdata);
			}
			break;
		case AbstractDataset.FLOAT64:
			DoubleDCT_1D dfft = new DoubleDCT_1D(n);
			double[] ddata = null;
			shape = a.getShape().clone();
			shape[axis] = n;
			result = new DoubleDataset(shape);
			dest = new DoubleDataset(new int[] {n});
			ddata = (double[]) dest.odata;
			pi = a.getPositionIterator(axis);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(ddata, 0.);
				a.copyItemsFromAxes(pos, hit, dest);
				dfft.inverse(ddata, true);
				result.setItemsOnAxes(pos, hit, ddata);
			}
			break;
		default:
			logger.warn("Non-complex dataset not yet supported");
			break;
		}

		return result;
	}

	private static AbstractDataset idct2d(final AbstractDataset a, final int[] s, final int[] axes) {
		AbstractDataset result = null;
		AbstractDataset dest = null;

		PositionIterator pi;
		int[] pos;
		boolean[] hit;

		switch (a.getDtype()) {
		case AbstractDataset.FLOAT32:
			FloatDCT_2D ffft = new FloatDCT_2D(s[0], s[1]);
			float[] fdata = null;
			result = new FloatDataset(newShape(a.shape, s, axes));
			dest = new FloatDataset(s);
			fdata = (float[]) dest.odata;
			pi = a.getPositionIterator(axes);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(fdata, 0.f);
				a.copyItemsFromAxes(pos, hit, dest);
				ffft.inverse(fdata, true);
				result.setItemsOnAxes(pos, hit, fdata);
			}
			break;
		case AbstractDataset.FLOAT64:
			DoubleDCT_2D dfft = new DoubleDCT_2D(s[0], s[1]);
			double[] ddata = null;
			result = new DoubleDataset(newShape(a.shape, s, axes));
			dest = new DoubleDataset(s);
			ddata = (double[]) dest.odata;
			pi = a.getPositionIterator(axes);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(ddata, 0.);
				a.copyItemsFromAxes(pos, hit, dest);
				dfft.inverse(ddata, true);
				result.setItemsOnAxes(pos, hit, ddata);
			}
			break;
		default:
			logger.warn("Non-complex dataset not yet supported");
			break;
		}

		return result;
	}

	private static AbstractDataset idct3d(final AbstractDataset a, final int[] s, final int[] axes) {
		AbstractDataset result = null;
		AbstractDataset dest = null;

		PositionIterator pi;
		int[] pos;
		boolean[] hit;

		switch (a.getDtype()) {
		case AbstractDataset.FLOAT32:
			FloatDCT_3D ffft = new FloatDCT_3D(s[0], s[1], s[2]);
			float[] fdata = null;
			result = new FloatDataset(newShape(a.shape, s, axes));
			dest = new FloatDataset(s);
			fdata = (float[]) dest.odata;
			pi = a.getPositionIterator(axes);
			pos = pi.getPos();
			hit = pi.getOmit();
			while (pi.hasNext()) {
				Arrays.fill(fdata, 0.f);
				a.copyItemsFromAxes(pos, hit, dest);
				ffft.inverse(fdata, true);
				result.setItemsOnAxes(pos, hit, fdata);
			}
			break;
		case AbstractDataset.FLOAT64:
				DoubleDCT_3D dfft = new DoubleDCT_3D(s[0], s[1], s[2]);
				double[] ddata = null;
				result = new DoubleDataset(newShape(a.shape, s, axes));
				dest = new DoubleDataset(s);
				ddata = (double[]) dest.odata;
				pi = a.getPositionIterator(axes);
				pos = pi.getPos();
				hit = pi.getOmit();
				while (pi.hasNext()) {
					Arrays.fill(ddata, 0.);
					a.copyItemsFromAxes(pos, hit, dest);
					dfft.inverse(ddata, true);
					result.setItemsOnAxes(pos, hit, ddata);
				}
				break;
		default:
			logger.warn("Non-complex dataset not yet supported");
			break;
		}

		return result;
	}

	/**
	 * Shift zero-frequency component to centre of dataset
	 * @param a
	 * @param axes (if null, then shift all axes)
	 * @return shifted dataset
	 */
	public static AbstractDataset dctshift(final AbstractDataset a, int[] axes) {
		int alen;
		if (axes == null) {
			alen = a.getRank();
			axes = new int[alen];
			for (int i = 0; i < alen; i++)
				axes[i] = i;
		} else {
			alen = axes.length;
			for (int i = 0; i < alen; i++)
				axes[i] = a.checkAxis(axes[i]);
		}

		AbstractDataset result = a;
		int[] indices;
		for (int i = 0; i < alen; i++) {
			int axis = axes[i];
			int n = a.shape[axis];
			int p = (n+1)/2;
			logger.info("Shift {} by {}", axis, p);
			indices = new int[n];
			for (int j = 0; j < n; j++) {
				if (j < n - p)
					indices[j] = p + j;
				else
					indices[j] = j - n + p;
			}

			result = result.take(indices, axis);
		}

		return result;
	}

	/**
	 * Shift zero-frequency component to centre of dataset
	 * @param a
	 * @param axes (if null, then shift all axes)
	 * @return shifted dataset
	 */
	public static AbstractDataset idctshift(final AbstractDataset a, int[] axes) {
		int alen;
		if (axes == null) {
			alen = a.getRank();
			axes = new int[alen];
			for (int i = 0; i < alen; i++)
				axes[i] = i;
		} else {
			alen = axes.length;
			for (int i = 0; i < alen; i++)
				axes[i] = a.checkAxis(axes[i]);
		}

		AbstractDataset result = a;
		int[] indices;
		for (int i = 0; i < alen; i++) {
			int axis = axes[i];
			int n = a.shape[axis];
			int p = n - (n+1)/2;
			logger.info("Shift {} by {}", axis, p);
			indices = new int[n];
			for (int j = 0; j < n; j++) {
				if (j < n - p)
					indices[j] = p + j;
				else
					indices[j] = j - n + p;
			}

			result = result.take(indices, axis);
		}

		return result;
	}

}
