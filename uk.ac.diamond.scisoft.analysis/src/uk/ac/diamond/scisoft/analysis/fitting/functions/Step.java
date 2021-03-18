/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.dataset.DoubleDataset;


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
	private static final long serialVersionUID = -2632855678062562995L;

	private static String NAME = "Step";
	private static String DESC = "A step function with inner and outer levels."
			+ "\n    y(x) = base,                 if x < pmin or x > pmax,"
			+ "\n         = base + outer + inner, if x in [left, right),"
			+ "\n         = base + outer,         otherwise"
			+ "\nwhere left = pmin + offset*(pmax - pmin - width), width = (pmax - pmin)*frac"
			+ "\nand right = left + width.";

	private static final String[] PARAM_NAMES = new String[] { "base", "pmin", "pmax", "outer", "inner", 
			"frac", "offset" };

	public Step() {
		super(new double[]{0,0,0,0,0,0,0});
	}

	public Step(IParameter... params) {
		super(params);
	}

	public Step(double[] params) {
		super(params);
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

		super(PARAM_NAMES.length);

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
	}

	@Override
	public int getNoOfParameters() {
		return PARAM_NAMES.length;
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	private transient double base, pmin, pmax, width, start, outer, inner;
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
}
