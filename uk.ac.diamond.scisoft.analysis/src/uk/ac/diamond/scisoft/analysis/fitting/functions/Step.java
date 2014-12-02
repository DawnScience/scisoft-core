/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;


/**
 * Class that wraps the double-step function: 
 * 
 * <pre>
 *            _______
 *           |       |
 *       ____|       |_______
 * _____|                    |_________
 * 
 * </pre>
 * 
 * 
 * Parameters are
 * <ol>
 *   <li>base</li>
 *   <li>start of outer step</li>
 *   <li>end of outer step</li>
 *   <li>step from base</li>
 *   <li>step from outer</li>
 *   <li>width of inner step as fraction of outer step</li>
 *   <li>offset of inner step as fraction of remaining outer step (0 = left justified, 1 = right)</li>
 * </ol>
 */
public class Step extends AFunction {
	private static String NAME = "Step";

	public Step() {
		super(new double[]{0,0,0,0,0,0,0});
		name = NAME;
	}

	public Step(IParameter... params) {
		super(params);
		name = NAME;
	}

	/**
	 * Constructor that allows for the positioning of all the parameter bounds
	 * 
	 * @param minY
	 *            minimum Y value
	 * @param maxY
	 *            maximum Y value
	 * @param minX1
	 *            minimum X1 value
	 * @param maxX1
	 *            maximum X1 value
	 * @param minX2
	 *            minimum X2 value
	 * @param maxX2
	 *            maximum X2 value
	 * @param minH1
	 *            minimum height of outer peak/trough
	 * @param maxH1
	 *            maximum height outer peak/trough
	 * @param minH2
	 *            minimum height of inner peak/trough
	 * @param maxH2
	 *            maximum height inner peak/trough
	 * @param minW
	 *            minimum proportional width of inner peak/trough ( 0 < width < 1 )
	 * @param maxW
	 *            maximum proportional width of inner peak/trough ( 0 < width < 1 )
	 * @param minPos
	 *            minimum position of inner peak/trough with respect to outer one ( 0 (left) <= pos <= 1 (right))
	 * @param maxPos
	 *            maximum position of inner peak/trough with respect to outer one ( 0 (left) <= pos <= 1 (right))
	 */
	public Step(double minY, double maxY, double minX1, double maxX1, double minX2, double maxX2, double minH1,
			double maxH1, double minH2, double maxH2, double minW, double maxW, double minPos, double maxPos) {

		super(7);

		IParameter p;
		p = getParameter(0);
		p.setLowerLimit(minY);
		p.setUpperLimit(maxY);
		p.setValue((minY + maxY) / 2.0);

		p = getParameter(1);
		p.setLowerLimit(minX1);
		p.setUpperLimit(maxX1);
		p.setValue((minX1 + maxX1) / 2.0);

		p = getParameter(2);
		p.setLowerLimit(minX2);
		p.setUpperLimit(maxX2);
		p.setValue((minX2 + maxX2) / 2.0);

		p = getParameter(3);
		p.setLowerLimit(minH1);
		p.setUpperLimit(maxH1);
		p.setValue((minH1 + maxH1) / 2.0);

		p = getParameter(4);
		p.setLowerLimit(minH2);
		p.setUpperLimit(maxH2);
		p.setValue((minH2 + maxH2) / 2.0);

		p = getParameter(5);
		p.setLowerLimit(minW);
		p.setUpperLimit(maxW);
		p.setValue((minW + maxW) / 2.0);

		p = getParameter(6);
		p.setLowerLimit(minPos);
		p.setUpperLimit(maxPos);
		p.setValue((minPos + maxPos) / 2.0);

		name = NAME;
	}

	double base, pmin, pmax, width, start, outer, inner;
	private void calcCachedParameters() {
		base = getParameterValue(0);
		pmin = getParameterValue(1);
		pmax = getParameterValue(2);

		// Calculate width and start of inner peak
		width = (pmax - pmin) * getParameterValue(5);
		start = pmin + (pmax - pmin - width) * getParameterValue(6);
		outer = base + getParameterValue(3);
		inner = outer + getParameterValue(4);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double position = values[0];

		// Test if outside outer peak
		if (position <= pmin || position >= pmax) {
			return base;
		}

		// Inside outer peak; now test if outside inner peak
		if (position <= start || position >= start + width) {
			return outer;
		}

		// Inside outer and inner peak
		return inner;
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty())
			calcCachedParameters();

		it.reset();
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		while (it.hasNext()) {
			double position = coords[0];

			double x;
			if (position <= pmin || position >= pmax) { // Test if outside outer peak
				x = base;
			} else if (position <= start || position >= start + width) { // Inside outer peak; now test if outside inner peak
				x = outer;
			} else { // Inside outer and inner peak
				x = inner;
			}
			buffer[i++] = x;
		}
	}

	public double getBase() {
		return base;
	}

	public void setBase(double base) {
		this.base = base;
	}

	public double getPmin() {
		return pmin;
	}

	public void setPmin(double pmin) {
		this.pmin = pmin;
	}

	public double getPmax() {
		return pmax;
	}

	public void setPmax(double pmax) {
		this.pmax = pmax;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getOuter() {
		return outer;
	}

	public void setOuter(double outer) {
		this.outer = outer;
	}

	public double getInner() {
		return inner;
	}

	public void setInner(double inner) {
		this.inner = inner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(base);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(inner);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(outer);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pmax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pmin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(start);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(width);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Step other = (Step) obj;
		if (Double.doubleToLongBits(base) != Double.doubleToLongBits(other.base))
			return false;
		if (Double.doubleToLongBits(inner) != Double.doubleToLongBits(other.inner))
			return false;
		if (Double.doubleToLongBits(outer) != Double.doubleToLongBits(other.outer))
			return false;
		if (Double.doubleToLongBits(pmax) != Double.doubleToLongBits(other.pmax))
			return false;
		if (Double.doubleToLongBits(pmin) != Double.doubleToLongBits(other.pmin))
			return false;
		if (Double.doubleToLongBits(start) != Double.doubleToLongBits(other.start))
			return false;
		if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width))
			return false;
		return true;
	}
}
