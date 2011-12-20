/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;


/**
 * This class basically wraps the function y(x) = c
 */
public class Offset extends AFunction {
	private static String cname = "Offset";

	/**
	 * Constructor which simply creates the right number of parameters, but probably isn't that much good
	 */
	public Offset() {
		super(1);
		name = cname;
	}
	
	/**
	 * This constructor should always be kept just in case, very useful for automated systems
	 * @param params
	 */
	public Offset(double[] params) {
		super(params);
		name = cname;
	}

	public Offset(IParameter[] params) {
		super(params);
		name = cname;
	}

	/**
	 * Constructor which allows the creator to specify the bounds of the parameters
	 * 
	 * @param minOffset
	 *            Minimum value the offset can take
	 * @param maxOffset
	 *            Maximum value the offset can take
	 */
	public Offset(double minOffset, double maxOffset) {
		super(1);

		getParameter(0).setValue((minOffset + maxOffset) / 2.0);
		getParameter(0).setLowerLimit(minOffset);
		getParameter(0).setUpperLimit(maxOffset);

		name = cname;
	}

	@Override
	public double val(double... values) {
		return getParameterValue(0);
	}

	@Override
	public String toString() {
		final StringBuilder out = new StringBuilder();
		out.append(String.format("Offset Position Has Value %f within the bounds [%f,%f]", getParameterValue(0),
				getParameter(0).getLowerLimit(), getParameter(0).getUpperLimit()));
		return out.toString();
	}

	@Override
	public double partialDeriv(int parameter, double... position) {
		return 1;
	}
}
