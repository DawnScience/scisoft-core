/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;

/**
 * Function acts as a 2D interpolator using cubic interpolation
 */
public class BicubicInterpolator implements DatasetToDatasetFunction {

	private int[] shape;

	/**
	 * Create a bicubic interpolation
	 * @param extendBorders if true, extend interpolation beyond borders
	 * @param newShape new output shape
	 */
	public BicubicInterpolator(boolean extendBorders, int... newShape) {
		if (newShape == null || newShape.length != 2) {
			throw new IllegalArgumentException("Shape must be 2D");
		}
		shape = newShape;
		this.extendBorders = extendBorders;
	}

	/**
	 * Create a bicubic interpolation. Equivalent to {@code BicubicInterpolator(false, newShape)}
	 * @param newShape new output shape
	 */
	public BicubicInterpolator(int... newShape) {
		this(false, newShape);
	}

	// parameters used with the interpolation, for speeding up the process
	private double a00, a01, a02, a03;
	private double a10, a11, a12, a13;
	private double a20, a21, a22, a23;
	private double a30, a31, a32, a33;

	private boolean extendBorders;

	/**
	 * @param p array containing the values of the 16 surrounding points
	 */
	protected void calculateParameters(double[] p) {
		a00 = p[1*4+1];
		a01 = -.5*p[1*4+0] + .5*p[1*4+2];
		a02 = p[1*4+0] - 2.5*p[1*4+1] + 2*p[1*4+2] - .5*p[1*4+3];
		a03 = -.5*p[1*4+0] + 1.5*p[1*4+1] - 1.5*p[1*4+2] + .5*p[1*4+3];
		a10 = -.5*p[0*4+1] + .5*p[2*4+1];
		a11 = .25*p[0*4+0] - .25*p[0*4+2] - .25*p[2*4+0] + .25*p[2*4+2];
		a12 = -.5*p[0*4+0] + 1.25*p[0*4+1] - p[0*4+2] + .25*p[0*4+3] + .5*p[2*4+0] - 1.25*p[2*4+1] + p[2*4+2] - .25*p[2*4+3];
		a13 = .25*p[0*4+0] - .75*p[0*4+1] + .75*p[0*4+2] - .25*p[0*4+3] - .25*p[2*4+0] + .75*p[2*4+1] - .75*p[2*4+2] + .25*p[2*4+3];
		a20 = p[0*4+1] - 2.5*p[1*4+1] + 2*p[2*4+1] - .5*p[3*4+1];
		a21 = -.5*p[0*4+0] + .5*p[0*4+2] + 1.25*p[1*4+0] - 1.25*p[1*4+2] - p[2*4+0] + p[2*4+2] + .25*p[3*4+0] - .25*p[3*4+2];
		a22 = p[0*4+0] - 2.5*p[0*4+1] + 2*p[0*4+2] - .5*p[0*4+3] - 2.5*p[1*4+0] + 6.25*p[1*4+1] - 5*p[1*4+2] + 1.25*p[1*4+3] + 2*p[2*4+0] - 5*p[2*4+1] + 4*p[2*4+2] - p[2*4+3] - .5*p[3*4+0] + 1.25*p[3*4+1] - p[3*4+2] + .25*p[3*4+3];
		a23 = -.5*p[0*4+0] + 1.5*p[0*4+1] - 1.5*p[0*4+2] + .5*p[0*4+3] + 1.25*p[1*4+0] - 3.75*p[1*4+1] + 3.75*p[1*4+2] - 1.25*p[1*4+3] - p[2*4+0] + 3*p[2*4+1] - 3*p[2*4+2] + p[2*4+3] + .25*p[3*4+0] - .75*p[3*4+1] + .75*p[3*4+2] - .25*p[3*4+3];
		a30 = -.5*p[0*4+1] + 1.5*p[1*4+1] - 1.5*p[2*4+1] + .5*p[3*4+1];
		a31 = .25*p[0*4+0] - .25*p[0*4+2] - .75*p[1*4+0] + .75*p[1*4+2] + .75*p[2*4+0] - .75*p[2*4+2] - .25*p[3*4+0] + .25*p[3*4+2];
		a32 = -.5*p[0*4+0] + 1.25*p[0*4+1] - p[0*4+2] + .25*p[0*4+3] + 1.5*p[1*4+0] - 3.75*p[1*4+1] + 3*p[1*4+2] - .75*p[1*4+3] - 1.5*p[2*4+0] + 3.75*p[2*4+1] - 3*p[2*4+2] + .75*p[2*4+3] + .5*p[3*4+0] - 1.25*p[3*4+1] + p[3*4+2] - .25*p[3*4+3];
		a33 = .25*p[0*4+0] - .75*p[0*4+1] + .75*p[0*4+2] - .25*p[0*4+3] - .75*p[1*4+0] + 2.25*p[1*4+1] - 2.25*p[1*4+2] + .75*p[1*4+3] + .75*p[2*4+0] - 2.25*p[2*4+1] + 2.25*p[2*4+2] - .75*p[2*4+3] - .25*p[3*4+0] + .75*p[3*4+1] - .75*p[3*4+2] + .25*p[3*4+3];
	}

	/**
	 * initially nicked from "http://www.paulinternet.nl/?page=bicubic"
	 * 
	 * Interpolates the value of a point in a two dimensional surface using bicubic interpolation.
	 * The value is calculated using the position of the point and the values of the 16 surrounding points.
	 * Note that the returned value can be more or less than any of the values of the surrounding points. 
	 * 
	 * @param x The horizontal distance between the point and the four points left of it, between 0 and 1
	 * @param y The vertical distance between the point and the four points below it, between 0 and 1
	 * @return the interpolated value
	 */
	public double bicubicInterpolate(double x, double y) {
		double x2 = x * x;
		double x3 = x2 * x;
		double y2 = y * y;
		double y3 = y2 * y;

		double bc;
		bc  = x3 * (a33 * y3 + a32 * y2 + a31 * y + a30);
		bc += x2 * (a23 * y3 + a22 * y2 + a21 * y + a20);
		bc += x  * (a13 * y3 + a12 * y2 + a11 * y + a10);
		return bc + a03 * y3 + a02 * y2 + a01 * y + a00;
	}

	double[] result = new double[4*4];

	protected double[] generateSurroundingPoints(int x, int y, final Dataset ds, final int[] dShape) {
		x--; // start to left
		y--; // and below
		int k = 0;
		int pmax = dShape[0] - 1;
		int qmax = dShape[1] - 1;
		for (int i = 0; i < 4; i++) {
			int p = x + i;
			if (p < 0) {
				p = 0;
			} else if (p > pmax) {
				p = pmax;
			}

			for (int j = 0; j < 4; j++)  {
				int q = y + j;
				if (q < 0) {
					q = 0;
				} else if (q > qmax) {
					q = qmax;
				}
				result[k++] = ds.getDouble(p, q);
			}
		}
		return result;
	}

	/**
	 * Calculate parameters for given point in dataset
	 * @param x
	 * @param y
	 * @param ds
	 */
	public void calculateParameters(int x, int y, final Dataset ds) {
		calculateParameters(generateSurroundingPoints(x, y, ds, ds.getShapeRef()));
	}

	private transient double osx, osy;
	private transient int ofx, ofy;
	private transient int oi, oj;

	/**
	 * Set input shape before using {@link #interpolate(int, int, Dataset)} if dataset
	 * has different shape (to avoid boundary issues)
	 * @param iShape
	 */
	public void setInputShape(int... iShape) {
		if (extendBorders) {
			osx = iShape[0] / (double) shape[0];
			ofx = ((int) (1/osx - 1))/2; // offset to left to centre sample points
			osy = iShape[1] / (double) shape[1];
			ofy = ((int) (1/osy - 1))/2;
		} else {
			osx = (iShape[0] - 1)/ (shape[0] - 1.0);
			ofx = 0;
			osy = (iShape[1] - 1)/ (shape[1] - 1.0);
			ofy = 0;
		}
	}

	/**
	 * Interpolate at given coordinates
	 * @param x
	 * @param y
	 * @param ds original dataset
	 * @return bicubic interpolated value
	 */
	public double interpolate(int x, int y, Dataset ds) {
		if (osx == 0) {
			setInputShape(ds.getShapeRef());
			calculateParameters(oi, oj, ds);
		}

		double cdx = osx * (x - ofx);
		int ci = (int) cdx;
		double cdy = osy * (y - ofy);
		int cj = (int) cdy;

		if (ci != oi || cj != oj) {
			oi = ci;
			oj = cj;
			calculateParameters(oi, oj, ds);
		}

		cdx -= ci;
		cdy -= cj;
		if (cdx == 0 && cdy == 0) {
			return ds.getDouble(ci, cj);
		}
		return bicubicInterpolate(cdx, cdy);
	}

	@Override
	public List<Dataset> value(IDataset... datasets) {

		List<Dataset> result = new ArrayList<Dataset>();

		for (IDataset ds : datasets) {
			Dataset d = DatasetUtils.convertToDataset(ds);
			final int[] dShape = d.getShapeRef();
			if (dShape == null || dShape.length != 2) {
				throw new IllegalArgumentException("Shape must be 2D");
			}

			DoubleDataset dds = DatasetFactory.zeros(shape);

			// calculate the new step size
			double dx, dy;
			int ox, oy;
			if (extendBorders) {
				dx = dShape[0] / (double) shape[0];
				ox = ((int) (1/dx - 1))/2; // offset to left to centre sample points
				dy = dShape[1] / (double) shape[1];
				oy = ((int) (1/dy - 1))/2;
			} else {
				dx = (dShape[0] - 1)/ (shape[0] - 1.0);
				ox = 0;
				dy = (dShape[1] - 1)/ (shape[1] - 1.0);
				oy = 0;
			}

			for (int x = 0; x < shape[0]; x++) {
				double xpos = (x - ox)*dx; // scaled position in given dataset
				int i = (int) xpos;
				xpos -= i;

				int lj = -1; // last j
				for (int y = 0; y < shape[1]; y++) {
					double ypos = (y - oy)*dy;
					int j = (int) ypos;
					ypos -= j;
					if (j != lj) { // only calculate parameters when moving on to new point
						calculateParameters(generateSurroundingPoints(i, j, d, dShape));
						lj = j;
					}
					double v;
					if (xpos == 0 && ypos == 0) {
						v = d.getDouble(i, j);
					} else {
						v = bicubicInterpolate(xpos, ypos);
					}
					dds.setItem(v, x, y);
				}
			}

			result.add(dds);
		}

		return result;
	}
}
