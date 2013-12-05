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

import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;




/**
 * PseudoVoigt Class
 */
public class PseudoVoigt extends APeak implements IPeak {
	private static final String cname = "PseudoVoigt";
	private static final String[] paramNames = new String[]{"posn", "l_fwhm", "area", "g_fwhm", "mix"};
	private static final String cdescription = "y(x) = Pseudo Voigt";
	private static final double[] params = new double[]{0,0,0,0,0};

	public PseudoVoigt() {
		this(params[0], params[1], params[2], params[3], params[4]);
	}

	/**
	 * 
	 * @param position
	 * @param gaussianFWHM
	 * @param lorentzianFWHM
	 * @param area
	 * @param mix
	 */
	public PseudoVoigt(double position, double gaussianFWHM, double lorentzianFWHM, double area, double mix) {
		super(position, gaussianFWHM, lorentzianFWHM, area, mix);

		setNames();
	}
	
	/**
	 * Initialise with set parameters
	 * @param params Position, GaussianFWHM, LorentzianFWHM, Area, Mix(0-1)
	 */
	public PseudoVoigt(IParameter... params) {
		super(params);

		setNames();
	}

	private static final int FWHMG = AREA + 1;
	private static final int MIX = FWHMG + 1;

	public PseudoVoigt(IdentifiedPeak peakParameters) {
		super(5);

		// Position
		IParameter p;
		p = getParameter(POSN);
		double range = peakParameters.getMaxXVal()-peakParameters.getMinXVal();
		p.setValue(peakParameters.getPos());
		p.setLimits(peakParameters.getMinXVal(), peakParameters.getMaxXVal());

		// Lorentzian FWHM
		p = getParameter(FWHM);
		p.setLimits(0, range);
		double width = peakParameters.getFWHM();
		p.setValue(width);

		// Area
		// better fitting is generally found if sigma expands into the peak.
		p = getParameter(AREA);
		p.setLimits(0, peakParameters.getHeight()*range*4);
		p.setValue(peakParameters.getArea()/2);
		
		// Gaussian FWHM
		p = getParameter(FWHMG);
		p.setLimits(0, range);
		p.setValue(width);
		
		// Mix
		p = getParameter(MIX);
		p.setValue(0.5);
		p.setLimits(0.0, 1.0);

		setNames();
	}

	/**
	 * @param minPos
	 * @param maxPos
	 * @param maxFWHM
	 * @param maxArea
	 */
	public PseudoVoigt(double minPos, double maxPos, double maxFWHM, double maxArea) {
		super(5);

		internalSetPeakParameters(minPos, maxPos, maxFWHM, maxArea);

		IParameter p;
		// Gaussian FWHM
		p = getParameter(FWHMG);
		p.setLimits(0.0, maxFWHM);
		p.setValue(maxFWHM / 5.0);
		
		// Mix
		p = getParameter(MIX);
		p.setValue(0.5);
		p.setLimits(0.0, 1.0);

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

	@Override
	public void setParameterValues(double... params) {
		super.setParameterValues(params);
		if (params.length > FWHM && params.length <= FWHMG) {
			double width = params[FWHM];
			double mix = 2 * getParameterValue(MIX);
			getParameter(FWHM).setValue(width * mix);
			getParameter(FWHMG).setValue(width * (2 - mix));
		}
	}

	private static final double CONST = Math.sqrt(Math.PI / Math.log(2.));

	double pos, halfwg, halfwl, mixing, height;
	private void calcCachedParameters() {
		pos = getParameter(POSN).getValue();
		halfwl = getParameterValue(FWHM) / 2.0;
		halfwg = getParameterValue(FWHMG) / 2.0;
		mixing = getParameter(MIX).getValue();

		height = getParameterValue(AREA) / (halfwl * Math.PI * mixing +
				halfwg * CONST * (1 - mixing));

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double delta = values[0] - pos;
		// Lorentzian part
		double dist = delta / halfwl;
		double ex = mixing / (dist * dist + 1);
		// Gaussian part
		double arg = delta / halfwg;
		ex += (1 - mixing) * Math.exp(- arg * arg);

		return ex * height;
	}

	@Override
	public double getFWHM() {
		if (isDirty())
			calcCachedParameters();

		double w = 2*(halfwl + halfwg);
		AbstractDataset x = DatasetUtils.linSpace(pos - w, pos + w, 200, AbstractDataset.FLOAT64);
		DoubleDataset data = makeDataset(x);
		data = (DoubleDataset) Maths.abs(data);
		List<Double> crossings = DatasetUtils.crossings(x, data, height / 2);
		if (crossings.size() < 2)
			return Double.NaN;

		double width = crossings.get(1).doubleValue() - crossings.get(0).doubleValue();
		return width;
	}

	@Override
	public double getHeight() {
		if (isDirty())
			calcCachedParameters();

		return height;
	}
}
