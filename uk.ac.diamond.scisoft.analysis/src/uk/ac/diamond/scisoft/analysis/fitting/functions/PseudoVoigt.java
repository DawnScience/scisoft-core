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


import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;


/**
 * PseudoVoigt Class
 */
public class PseudoVoigt extends APeak implements IPeak{
	private static final double FWHM_TO_SIGMA = 1./Math.sqrt(8.*Math.log(2.));
	private static String cname = "PseudoVoigt";

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
		name = cname;
	}
	
	/**
	 * Initialise with set parameters
	 * @param params Position, GaussianFWHM, LorentzianFWHM, Area, Mix(0-1)
	 */
	public PseudoVoigt(IParameter[] params) {
		super(params);
		name = cname;
	}

	public PseudoVoigt(IdentifiedPeak peakParameters){
		super(5);

		// Position
		double range = peakParameters.getMaxXVal()-peakParameters.getMinXVal();
		getParameter(0).setValue(peakParameters.getPos());
		getParameter(0).setLowerLimit(peakParameters.getMinXVal());//-range);
		getParameter(0).setUpperLimit(peakParameters.getMaxXVal());//+range);

		// Gaussian FWHM
		getParameter(1).setLowerLimit(0);
		getParameter(1).setUpperLimit(range*2);
		getParameter(1).setValue(peakParameters.getFWHM()/2);

		// Lorentzian FWHM
		getParameter(2).setLowerLimit(0);
		getParameter(2).setUpperLimit(range*2);
		getParameter(2).setValue(peakParameters.getFWHM()/2);
		
		// Area
		// better fitting is generally found if sigma expands into the peak.
		getParameter(3).setLowerLimit(0);
		getParameter(3).setUpperLimit((peakParameters.getHeight()*2)*(range*2));
		getParameter(3).setValue(peakParameters.getArea()/2);
		
		// Mix
		getParameter(4).setValue(0.5);
		getParameter(4).setLowerLimit(0.0);
		getParameter(4).setUpperLimit(1.0);

		name = cname;
	}
	/**
	 * @param minPos
	 * @param maxPos
	 * @param max_FWHM
	 * @param max_Area
	 */
	public PseudoVoigt(double minPos, double maxPos, double max_FWHM, double max_Area) {
		super(5);

		// Position
		getParameter(0).setValue((minPos + maxPos) / 2.0);
		getParameter(0).setLowerLimit(minPos);
		getParameter(0).setUpperLimit(maxPos);

		// Gaussian FWHM
		getParameter(1).setLowerLimit(0.0);
		getParameter(1).setUpperLimit(max_FWHM);
		getParameter(1).setValue(max_FWHM / 5.0);

		// Lorentzian FWHM
		getParameter(2).setLowerLimit(0.0);
		getParameter(2).setUpperLimit(max_FWHM);
		getParameter(2).setValue(max_FWHM / 5.0);

		// Area
		getParameter(3).setLowerLimit(0.0);
		getParameter(3).setUpperLimit(max_Area);
		getParameter(3).setValue(max_Area / 2.0);

		// Mix
		getParameter(4).setLowerLimit(0.0);
		getParameter(4).setUpperLimit(1.0);
		getParameter(4).setValue(0.5);

		name = cname;
	}

	public PseudoVoigt createPeakFunction(double minPosition, double maxPosition, double maxArea, double maxFWHM){
		return new PseudoVoigt(minPosition, maxPosition, maxFWHM, maxArea);
	}
	
	double pos, sigma, gamma, mixing, gsq, top, norm;
	private void calcCachedParameters() {
		pos = getParameter(0).getValue();
		sigma = getParameter(1).getValue() * FWHM_TO_SIGMA;
		gamma = getParameter(2).getValue() / 2.0;
		double area = getParameter(3).getValue();
		mixing = getParameter(4).getValue();

		norm = mixing * area / Math.sqrt(2.0 * Math.PI * sigma * sigma);

		gsq = gamma * gamma;
		top = (1. - mixing) * gamma * area / Math.PI;

		markParametersClean();
	}

	@Override
	public double val(double... values) {
		if (areParametersDirty())
			calcCachedParameters();

		double position = values[0];

		// Lorentzian part
		double dist = position - pos;
		double y = top / ( dist * dist + gsq);

		// Gaussian part
		double arg = dist / sigma;
		double ex = Math.exp(-0.5 * arg * arg);
		y += norm * ex;

		return y;
	}

	@Override
	public double getArea() {
		return getParameter(3).getValue();
	}

	@Override
	public double getFWHM() {
		double min = getParameterValue(0)-(getParameterValue(1)+getParameterValue(2));
		double max = getParameterValue(0)+(getParameterValue(1)+getParameterValue(2));
		DoubleDataset data = makeDataset(DatasetUtils.linSpace(min,max, 200, AbstractDataset.FLOAT64));
		data = (DoubleDataset) Maths.abs(data);
		double halfMaxV = data.max().doubleValue()/2;
		List<Double> crossings = DatasetUtils.crossings(data, halfMaxV);
		double width = (crossings.get(1).doubleValue()-crossings.get(0).doubleValue());
		double scale = ((max-min)/200.0);
		double result = width*scale;
		return result;
	}

	@Override
	public double getPosition() {
		return getParameter(0).getValue();
	}

	public double getMaxValue(){
		return gamma;
		
	}
}
