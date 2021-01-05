/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import javax.vecmath.Vector3d;

import org.eclipse.january.dataset.DoubleDataset;

/**
 * Split pixel over eight voxels with weight determined by 1/distance
 */
public class InverseSplitter implements PixelSplitter {
	/**
	 * Weight function of distance squared
	 * @param squaredDistance 
	 * @return 1/distance
	 */
	protected double calcWeight(double squaredDistance) {
		return squaredDistance == 0 ? Double.POSITIVE_INFINITY : 1. / Math.sqrt(squaredDistance);
	}

	double[] weights = new double[8];
	double factor;

	/**
	 * Calculate weights
	 * @param vd size of voxel
	 * @param dx displacement in x from first voxel
	 * @param dy displacement in y from first voxel
	 * @param dz displacement in z from first voxel
	 */
	void calcWeights(double[] vd, double dx, double dy, double dz) {
		final double dxs = dx * dx;
		final double dys = dy * dy;
		final double dzs = dz * dz;
		final double cx = vd[0] - dx;
		final double cy = vd[1] - dy;
		final double cz = vd[2] - dz;
		final double cxs = cx * cx;
		final double cys = cy * cy;
		final double czs = cz * cz;

		weights[0] = calcWeight(dxs + dys + dzs);
		weights[1] = calcWeight(cxs + dys + dzs);
		weights[2] = calcWeight(dxs + cys + dzs);
		weights[3] = calcWeight(cxs + cys + dzs);
		weights[4] = calcWeight(dxs + dys + czs);
		weights[5] = calcWeight(cxs + dys + czs);
		weights[6] = calcWeight(dxs + cys + czs);
		weights[7] = calcWeight(cxs + cys + czs);

		double tw = weights[1] + weights[2] + weights[3] + weights[4] + weights[5] + weights[6] + weights[7];
		if (Double.isInfinite(weights[0])) {
			weights[0] = 1e3 * tw; // make voxel an arbitrary factor larger
		}
		factor = 1./(weights[0] + tw);
	}

	@Override
	public InverseSplitter clone() {
		return new InverseSplitter();
	}

	@Override
	public void splitValue(DoubleDataset volume, DoubleDataset weight, final double[] vsize, Vector3d dh, int[] pos, double value) {
		calcWeights(vsize, dh.x, dh.y, dh.z);
		int[] vShape = volume.getShapeRef();

		double w;
		int[] lpos = pos.clone();

		w = factor * weights[0];
		InverseSplitter.addToDataset(volume, lpos, w * value);
		InverseSplitter.addToDataset(weight, lpos, w);

		int l = ++lpos[0];
		if (l >= 0 && l < vShape[0]) {
			w = factor * weights[1];
			if (w > 0) {
				InverseSplitter.addToDataset(volume, lpos, w * value);
				InverseSplitter.addToDataset(weight, lpos, w);
			}
		}
		lpos[0]--;

		int m = ++lpos[1];
		if (m >= 0 && m < vShape[1]) {
			w = factor * weights[2];
			if (w > 0) {
				InverseSplitter.addToDataset(volume, lpos, w * value);
				InverseSplitter.addToDataset(weight, lpos, w);
			}

			l = ++lpos[0];
			if (l >= 0 && l < vShape[0]) {
				w = factor * weights[3];
				if (w > 0) {
					InverseSplitter.addToDataset(volume, lpos, w * value);
					InverseSplitter.addToDataset(weight, lpos, w);
				}
			}
			lpos[0]--;
		}
		lpos[1]--;

		int n = ++lpos[2];
		if (n >= 0 && n < vShape[2]) {
			w = factor * weights[4];
			if (w > 0) {
				InverseSplitter.addToDataset(volume, lpos, w * value);
				InverseSplitter.addToDataset(weight, lpos, w);
			}

			l = ++lpos[0];
			if (l >= 0 && l < vShape[0]) {
				w = factor * weights[5];
				if (w > 0) {
					InverseSplitter.addToDataset(volume, lpos, w * value);
					InverseSplitter.addToDataset(weight, lpos, w);
				}
			}
			lpos[0]--;

			m = ++lpos[1];
			if (m >= 0 && m < vShape[1]) {
				w = factor * weights[6];
				if (w > 0) {
					InverseSplitter.addToDataset(volume, lpos, w * value);
					InverseSplitter.addToDataset(weight, lpos, w);
				}

				l = ++lpos[0];
				if (l >= 0 && l < vShape[0]) {
					w = factor * weights[7];
					if (w > 0) {
						InverseSplitter.addToDataset(volume, lpos, w * value);
						InverseSplitter.addToDataset(weight, lpos, w);
					}
				}
			}
		}
	}

	protected static void addToDataset(DoubleDataset d, final int[] pos, double v) {
		final int index = d.get1DIndex(pos);
		d.setAbs(index, d.getAbs(index) + v);
	}
}