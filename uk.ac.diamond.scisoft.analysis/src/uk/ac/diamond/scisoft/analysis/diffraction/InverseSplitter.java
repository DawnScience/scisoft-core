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
	protected DoubleDataset output;
	protected DoubleDataset weight;

	@Override
	public void setDatasets(DoubleDataset output, DoubleDataset weight) {
		this.output = output;
		this.weight = weight;
	}

	/**
	 * Weight function of distance squared
	 * @param squaredDistance 
	 * @return 1/distance
	 */
	protected double calcWeight(double squaredDistance) {
		return squaredDistance == 0 ? Double.POSITIVE_INFINITY : 1. / Math.sqrt(squaredDistance);
	}

	double[] weights = new double[8];

	@Override
	public boolean doesSpread() {
		return true;
	}

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
		weights[1] = calcWeight(dxs + dys + czs);
		weights[2] = calcWeight(dxs + cys + dzs);
		weights[3] = calcWeight(dxs + cys + czs);
		weights[4] = calcWeight(cxs + dys + dzs);
		weights[5] = calcWeight(cxs + dys + czs);
		weights[6] = calcWeight(cxs + cys + dzs);
		weights[7] = calcWeight(cxs + cys + czs);

		double tw = weights[1] + weights[2] + weights[3] + weights[4] + weights[5] + weights[6] + weights[7];
		if (Double.isInfinite(weights[0])) {
			weights[0] = 1e3 * tw; // make voxel an arbitrary factor larger
		}
		double factor = 1./(weights[0] + tw);
		for (int i = 0; i < weights.length; i++) {
			weights[i] *= factor;
		}
	}

	@Override
	public InverseSplitter clone() {
		InverseSplitter c = new InverseSplitter();
		c.output = output;
		c.weight = weight;
		return c;
	}

	/**
	 * Add values to datasets at given index
	 * @param index
	 * @param va value
	 * @param vb value
	 */
	void addToDatasets(final int index, double va, double vb) {
		output.setAbs(index, output.getAbs(index) + va);
		weight.setAbs(index, weight.getAbs(index) + vb);
	}

	@Override
	public void splitValue(final double[] vsize, Vector3d dh, int[] pos, double value) {
		calcWeights(vsize, dh.x, dh.y, dh.z);
		int[] vShape = output.getShapeRef();
		final int lMax = vShape[0];
		final int mMax = vShape[1];
		final int nMax = vShape[2];
		final int idx = output.get1DIndex(pos);

		double w;

		w = weights[0];
		int i = idx;
		addToDatasets(i, w * value, w);

		final int n = pos[2] + 1;
		final boolean nPOInRange = n >= 0 && n < nMax;
		if (nPOInRange) {
			w = weights[1];
			if (w > 0) {
				addToDatasets(i + 1, w * value, w);
			}
		}

		final int m = pos[1] + 1;
		final boolean mPOInRange = m >= 0 && m < mMax;
		if (mPOInRange) {
			w = weights[2];
			i = nMax + i;
			if (w > 0) {
				addToDatasets(i, w * value, w);
			}

			if (nPOInRange) {
				w = weights[3];
				if (w > 0) {
					addToDatasets(i + 1, w * value, w);
				}
			}
		}

		final int l = pos[0] + 1;
		if (l >= 0 && l < lMax) {
			w = weights[4];
			i = mMax * nMax + idx;
			if (w > 0) {
				addToDatasets(i, w * value, w);
			}

			if (nPOInRange) {
				w = weights[5];
				if (w > 0) {
					addToDatasets(i + 1, w * value, w);
				}
			}

			if (mPOInRange) {
				w = weights[6];
				i = nMax + i;
				if (w > 0) {
					addToDatasets(i, w * value, w);
				}

				if (nPOInRange) {
					w = weights[7];
					if (w > 0) {
						addToDatasets(i + 1, w * value, w);
					}
				}
			}
		}
	}
}