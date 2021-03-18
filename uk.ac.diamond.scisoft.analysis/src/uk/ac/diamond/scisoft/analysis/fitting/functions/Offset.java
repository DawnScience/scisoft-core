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
 * This class basically wraps the function y(x) = c
 */
public class Offset extends AFunction {
	private static final long serialVersionUID = 5892199348737768467L;

	private static final String NAME = "Offset";
	private static final String DESCRIPTION = "An offset or constant."
			+ "\n    y(x) = c";
	private static final String[] PARAM_NAMES = new String[]{"c"};

	/**
	 * Constructor which simply creates the right number of parameters, but probably isn't that much good
	 */
	public Offset() {
		super(PARAM_NAMES.length);
	}

	/**
	 * This constructor should always be kept just in case, very useful for automated systems
	 * @param params
	 */
	public Offset(double... params) {
		super(params);
	}

	public Offset(IParameter... params) {
		super(params);
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
		super(PARAM_NAMES.length);

		IParameter p = getParameter(0);
		p.setValue((minOffset + maxOffset) / 2.0);
		p.setLowerLimit(minOffset);
		p.setUpperLimit(maxOffset);
	}


	@Override
	public int getNoOfParameters() {
		return PARAM_NAMES.length;
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESCRIPTION, PARAM_NAMES);
	}

	@Override
	public double val(double... values) {
		return getParameterValue(0);
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		data.fill(getParameterValue(0));
	}

	@Override
	public double partialDeriv(IParameter parameter, double... position) {
		if (indexOfParameter(parameter) < 0)
			return 0;
		return 1;
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		data.fill(1);
	}
}
