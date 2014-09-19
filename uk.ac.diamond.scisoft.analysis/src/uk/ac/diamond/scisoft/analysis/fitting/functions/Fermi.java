/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

/**
 * Class that wrappers the Fermi function from Fermi-Dirac distribution
 * y(x) = scale / (exp((x - mu)/kT) + 1) + C
 */
public class Fermi extends AFunction implements Serializable {
	
	private static final String NAME = "Fermi";
	private static final String DESC = "y(x) = scale / (exp((x - mu)/kT) + 1) + C";
	private static final String[] PARAM_NAMES = new String[]{"mu", "kT", "scale", "Constant"};
	private static final double[] PARAMS = new double[]{0,0,0,0};

	private double mu, kT, scale, C;

	public Fermi(){
		this(PARAMS);
	}

	public Fermi(double... params) {
		super(params);

		setNames();
	}

	public Fermi(IParameter... params) {
		super(params);

		setNames();
	}

	/**
	 * Constructor that allows for the positioning of all the parameter bounds
	 * 
	 * @param minMu
	 *            minimum Mu value
	 * @param maxMu
	 *            maximum Mu value
	 * @param minkT
	 *            minimum kT value
	 * @param maxkT
	 *            maximum kT value
	 * @param minScale
	 *            minimum scale value
	 * @param maxScale
	 *            maximum scale value
	 * @param minC
	 *            minimum C value
	 * @param maxC
	 *            maximum C value
	 */
	public Fermi(double minMu, double maxMu, double minkT, double maxkT,
					double minScale, double maxScale, double minC, double maxC) {

		super(4);

		IParameter p;
		p = getParameter(0);
		p.setLimits(minMu, maxMu);
		p.setValue((minMu + maxMu) / 2.0);

		p = getParameter(1);
		p.setLimits(minkT, maxkT);
		p.setValue((minkT + maxkT) / 2.0);

		p = getParameter(2);
		p.setLimits(minScale, maxScale);
		p.setValue((minScale + maxScale) / 2.0);

		p = getParameter(3);
		p.setLimits(minC, maxC);
		p.setValue((minC + maxC) / 2.0);

		setNames();
	}

	private void setNames() {
		name = NAME;
		description = DESC;
		for (int i = 0; i < PARAM_NAMES.length; i++) {
			IParameter p = getParameter(i);
			p.setName(PARAM_NAMES[i]);
		}
	}

	private void calcCachedParameters() {
		mu = getParameterValue(0);
		kT = getParameterValue(1);
		scale = getParameterValue(2);
		C = getParameterValue(3);

		setDirty(false);
	}


	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double position = values[0];
		
		double arg = (position - mu) / kT;
		
		return scale/(Math.exp(arg) + 1.0) + C;
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
			
			double arg = (position - mu) / kT;
			
			buffer[i++] = scale/(Math.exp(arg) + 1.0) + C;
		}
	}
}
