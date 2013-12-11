/*-
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

import java.io.Serializable;

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

/**
 * Class that wrappers the Fermi function from Fermi-Dirac distribution
 * y(x) = scale / (exp((x - mu)/kT) + 1) + C
 */
public class Fermi extends AFunction implements Serializable {
	
	private static final String cname = "Fermi";
	private static final String[] paramNames = new String[]{"mu", "kT", "scale", "Constant"};
	private static final String cdescription = "y(x) = scale / (exp((x - mu)/kT) + 1) + C";
	private static final double[] params = new double[]{0,0,0,0};

	private double mu, kT, scale, C;


	public Fermi(){
		this(params);
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

		getParameter(0).setLimits(minMu, maxMu);
		getParameter(0).setValue((minMu + maxMu) / 2.0);

		getParameter(1).setLimits(minkT, maxkT);
		getParameter(1).setValue((minkT + maxkT) / 2.0);
		
		getParameter(2).setLimits(minScale, maxScale);
		getParameter(2).setValue((minScale + maxScale) / 2.0);
		
		getParameter(3).setLimits(minC, maxC);
		getParameter(3).setValue((minC + maxC) / 2.0);

		setNames();
	}

	private void setNames() {
		name = cname;
		description = cdescription;
		for (int i = 0; i < paramNames.length; i++) {
			IParameter p = getParameter(i);
			p.setName(paramNames[i]);
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
