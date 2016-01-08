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
 * This class basically wraps the function y(x) = c
 */
public class Offset extends AFunction {
	private static final String NAME = "Offset";
	private static final String DESCRIPTION = "y(x) = c";
	private static final String[] PARAM_NAMES = new String[]{"C"};

	/**
	 * Constructor which simply creates the right number of parameters, but probably isn't that much good
	 */
	public Offset() {
		super(1);
		setNames();
	}
	
	/**
	 * This constructor should always be kept just in case, very useful for automated systems
	 * @param params
	 */
	public Offset(double[] params) {
		super(params);
		setNames();
	}

	public Offset(IParameter... params) {
		super(params);
		setNames();
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

		IParameter p = getParameter(0);
		p.setValue((minOffset + maxOffset) / 2.0);
		p.setLowerLimit(minOffset);
		p.setUpperLimit(maxOffset);

		setNames();
	}

	private void setNames() {
		name = NAME;
		description = DESCRIPTION;
		for (int i = 0; i < PARAM_NAMES.length; i++) {
			IParameter p = getParameter(i);
			p.setName(PARAM_NAMES[i]);
		}
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
