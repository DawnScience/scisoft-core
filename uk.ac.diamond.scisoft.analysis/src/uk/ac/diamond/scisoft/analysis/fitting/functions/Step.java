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

package uk.ac.diamond.scisoft.analysis.fitting.functions;


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
	private static String cname = "Step";

	public Step(IParameter[] params) {
		super(params);
		name = cname;
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

		getParameter(0).setLowerLimit(minY);
		getParameter(0).setUpperLimit(maxY);
		getParameter(0).setValue((minY + maxY) / 2.0);

		getParameter(1).setLowerLimit(minX1);
		getParameter(1).setUpperLimit(maxX1);
		getParameter(1).setValue((minX1 + maxX1) / 2.0);

		getParameter(2).setLowerLimit(minX2);
		getParameter(2).setUpperLimit(maxX2);
		getParameter(2).setValue((minX2 + maxX2) / 2.0);

		getParameter(3).setLowerLimit(minH1);
		getParameter(3).setUpperLimit(maxH1);
		getParameter(3).setValue((minH1 + maxH1) / 2.0);

		getParameter(4).setLowerLimit(minH2);
		getParameter(4).setUpperLimit(maxH2);
		getParameter(4).setValue((minH2 + maxH2) / 2.0);

		getParameter(5).setLowerLimit(minW);
		getParameter(5).setUpperLimit(maxW);
		getParameter(5).setValue((minW + maxW) / 2.0);

		getParameter(6).setLowerLimit(minPos);
		getParameter(6).setUpperLimit(maxPos);
		getParameter(6).setValue((minPos + maxPos) / 2.0);

		name = cname;
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

		markParametersClean();
	}

	@Override
	public double val(double... values) {
		if (areParametersDirty())
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

}
