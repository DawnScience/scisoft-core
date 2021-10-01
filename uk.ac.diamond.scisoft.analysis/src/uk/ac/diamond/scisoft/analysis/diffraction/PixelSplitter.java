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
 * Intensity value splitter that splits an image pixel value and adds the pieces to close-by pixels/voxels
 */
public interface PixelSplitter extends Cloneable {
	/**
	 * Set datasets for splitter to use
	 * @param output dataset that holds the output values
	 * @param weight dataset that holds the relative contributions from each pixel
	 */
	public void setDatasets(DoubleDataset output, DoubleDataset weight);

	/**
	 * @return {output, weight}
	 */
	public DoubleDataset[] getDatasets();

	/**
	 * Spread a pixel intensity value over voxels near position
	 * @param dh offset in reciprocal space from voxel corner
	 * @param pos position in volume
	 * @param value pixel intensity to split
	 */
	public void splitValue(Vector3d dh, int[] pos, double value);

	/**
	 * @return true if pixel is spread over 8 voxels
	 */
	public boolean doesSpread();

	/**
	 * @return shape of output dataset
	 */
	public int[] getShape();

	public PixelSplitter clone();

	class BaseSplitter implements PixelSplitter {
		protected DoubleDataset output;
		protected DoubleDataset weight;

		@Override
		public void setDatasets(DoubleDataset output, DoubleDataset weight) {
			this.output = output;
			this.weight = weight;
		}

		@Override
		public DoubleDataset[] getDatasets() {
			return new DoubleDataset[] {output, weight};
		}

		@Override
		public int[] getShape() {
			return output == null ? null : output.getShapeRef();
		}

		@Override
		public boolean doesSpread() {
			return false;
		}

		@Override
		public void splitValue(Vector3d dh, int[] pos, double value) {
		}

		@Override
		public BaseSplitter clone() {
			return new BaseSplitter();
		}
	}

	/**
	 * This does not split pixel but places its value in the nearest voxel
	 */
	public static class NonSplitter extends BaseSplitter {
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
		public void splitValue(Vector3d dh, int[] pos, double value) {
			int idx = output.get1DIndex(pos);
			addToDatasets(idx, value, 1);
		}

		@Override
		public boolean doesSpread() {
			return false;
		}

		@Override
		public NonSplitter clone() {
			NonSplitter c = new NonSplitter();
			c.output = output;
			c.weight = weight;
			return c;
		}
	}

	/**
	 * Split pixel over eight voxels with weight determined by f/distance
	 */
	public static class InverseSplitter extends BaseSplitter {
		private double f;

		InverseSplitter() {
		}

		public InverseSplitter(double f) {
			this.f = f;
		}

		/**
		 * Weight function of distance squared
		 * @param squaredDistance 
		 * @return 1/distance
		 */
		protected double calcWeight(double squaredDistance) {
			return squaredDistance == 0 ? Double.POSITIVE_INFINITY : f / Math.sqrt(squaredDistance);
		}

		double[] weights = new double[8];

		@Override
		public boolean doesSpread() {
			return true;
		}

		/**
		 * Calculate weights
		 * @param dx displacement in x from position
		 * @param dy displacement in y from position
		 * @param dz displacement in z from position
		 */
		void calcWeights(double dx, double dy, double dz) {
			final double dxs = dx * dx;
			final double dys = dy * dy;
			final double dzs = dz * dz;
			final double cx = 1 - dx;
			final double cy = 1 - dy;
			final double cz = 1 - dz;
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
			InverseSplitter c = new InverseSplitter(f);
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
		public void splitValue(Vector3d dh, int[] pos, double value) {
			calcWeights(dh.x, dh.y, dh.z);
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

	/**
	 * Split pixel over eight voxels with weight determined by exp(-log(2)*(distance/hm))
	 */
	public static class ExponentialSplitter extends InverseSplitter {
		private double f;

		public ExponentialSplitter(double hm) {
			f = Math.log(2) / hm;
		}

		@Override
		public ExponentialSplitter clone() {
			ExponentialSplitter c = new ExponentialSplitter(1);
			c.output = output;
			c.weight = weight;
			c.f = f;
			return c;
		}

		@Override
		protected double calcWeight(double ds) {
			return Math.exp(-Math.sqrt(ds)*f);
		}
	}

	/**
	 * Split pixel over eight voxels with weight determined by exp(-log(2)*(distance/hwhm)^2)
	 */
	public static class GaussianSplitter extends InverseSplitter {
		private double f;

		public GaussianSplitter(double hwhm) {
			f = Math.log(2) / (hwhm * hwhm);
		}

		@Override
		public GaussianSplitter clone() {
			GaussianSplitter c = new GaussianSplitter(1);
			c.output = output;
			c.weight = weight;
			c.f = f;
			return c;
		}

		@Override
		protected double calcWeight(double ds) {
			return Math.exp(-ds*f);
		}
	}

	/**
	 * Split pixel over four pixels with weight determined by f/distance
	 */
	public static class InverseSplitter2D extends InverseSplitter {
		double f;
		double[] weights2D = new double[4];

		InverseSplitter2D() {
		}

		public InverseSplitter2D(double f) {
			this.f = f;
		}

		@Override
		public boolean doesSpread() {
			return true;
		}

		/**
		 * Calculate weights
		 * @param dx displacement in x from first voxel
		 * @param dy displacement in y from first voxel
		 * @param dy displacement in z from first voxel
		 */
		void calcWeights2D(double dx, double dy, double dz) {
			final double dxs = dx * dx;
			final double dys = dy * dy;
			final double dzs = dz * dz;
			final double cx = 1 - dx;
			final double cy = 1 - dy;
			final double cz = 1 - dz;
			final double cxs = cx * cx;
			final double cys = cy * cy;
			final double czs = cz * cz;

			weights2D[0] = calcWeight(dxs + dys + dzs) + calcWeight(dxs + dys + czs);
			weights2D[1] = calcWeight(dxs + cys + dzs) + calcWeight(dxs + cys + czs);
			weights2D[2] = calcWeight(cxs + dys + dzs) + calcWeight(cxs + dys + czs);
			weights2D[3] = calcWeight(cxs + cys + dzs) + calcWeight(cxs + cys + czs);

			double tw = weights2D[1] + weights2D[2] + weights2D[3];
			if (Double.isInfinite(weights2D[0])) {
				weights2D[0] = 1e3 * tw; // make voxel an arbitrary factor larger
			}
			double factor = 1./(weights2D[0] + tw);
			for (int i = 0; i < weights2D.length; i++) {
				weights2D[i] *= factor;
			}
		}

		@Override
		public InverseSplitter2D clone() {
			InverseSplitter2D c = new InverseSplitter2D(f);
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
		public void splitValue(Vector3d dh, int[] pos, double value) {
			calcWeights2D(dh.x, dh.y, dh.z);
			int[] vShape = output.getShapeRef();
			final int lMax = vShape[0];
			final int mMax = vShape[1];
			final int idx = output.get1DIndex(pos);

			double w;

			w = weights2D[0];
			int i = idx;
			addToDatasets(i, w * value, w);

			final int m = pos[1] + 1;
			final boolean mPOInRange = m >= 0 && m < mMax;
			if (mPOInRange) {
				w = weights2D[1];
				if (w > 0) {
					addToDatasets(i + 1, w * value, w);
				}
			}

			final int l = pos[0] + 1;
			if (l >= 0 && l < lMax) {
				w = weights2D[2];
				i = mMax + i;
				if (w > 0) {
					addToDatasets(i, w * value, w);
				}

				if (mPOInRange) {
					w = weights2D[3];
					if (w > 0) {
						addToDatasets(i + 1, w * value, w);
					}
				}
			}
		}
	}

	/**
	 * Split pixel over four pixels with weight determined by exp(-log(2)*(distance/hm))
	 */
	public static class ExponentialSplitter2D extends InverseSplitter2D {
		private double f;

		public ExponentialSplitter2D(double hm) {
			f = Math.log(2) / hm;
		}

		@Override
		public ExponentialSplitter2D clone() {
			ExponentialSplitter2D c = new ExponentialSplitter2D(1);
			c.output = output;
			c.weight = weight;
			c.f = f;
			return c;
		}

		@Override
		protected double calcWeight(double ds) {
			return Math.exp(-Math.sqrt(ds)*f);
		}
	}

	/**
	 * Split pixel over four pixels with weight determined by exp(-log(2)*(distance/hwhm)^2)
	 */
	public static class GaussianSplitter2D extends InverseSplitter2D {
		private double f;

		public GaussianSplitter2D(double hwhm) {
			f = Math.log(2) / (hwhm * hwhm);
		}

		@Override
		public GaussianSplitter2D clone() {
			GaussianSplitter2D c = new GaussianSplitter2D(1);
			c.output = output;
			c.weight = weight;
			c.f = f;
			return c;
		}

		@Override
		protected double calcWeight(double ds) {
			return Math.exp(-ds*f);
		}
	}

	/**
	 * Create a pixel splitter of given name
	 * @param is2D 
	 * @param splitter name
	 * @param p parameter
	 * @return pixel splitter
	 */
	static PixelSplitter createSplitter(boolean is2D, String splitter, double p) {
		if (splitter == null || splitter.isEmpty() || splitter.equals("nearest")) {
			return new NonSplitter();
		} else if (splitter.equals("gaussian")) {
			return is2D ? new GaussianSplitter2D(p) : new GaussianSplitter(p);
		} else if (splitter.equals("negexp")) {
			return is2D ? new ExponentialSplitter2D(p) : new ExponentialSplitter(p);
		} else if (splitter.equals("inverse")) {
			return is2D ? new InverseSplitter2D() : new InverseSplitter();
		} 
	
		throw new IllegalArgumentException("Splitter is not known");
	}
}
