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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.Signal;

/**
 * Class that wrappers the Fermi function from Fermi-Dirac distribution
 * y(x) = scale / (exp((x - mu)/kT) + 1) + C
 */
public class FermiGauss extends AFunction implements Serializable{
	
	private static final double TWO_SQRT_TWO_LN_TWO = 2.35482;

	private static final int NUMBER_OF_PARAMETERS = 6;

	private static final Logger LOGGER = LoggerFactory.getLogger(FermiGauss.class);
	
	private static final double K2EV_CONVERSION_FACTOR = 8.6173324e-5;

	private static final String cname = "Fermi * Gaussian";
	private static final String[] paramNames = new String[]{"mu", "temperature", "BG_slope", "FE_step_height", "Constant", "FWHM"};
	private static final String cdescription = "y(x) = (scale / (exp((x - mu)/kT) + 1) + C) * exp(-((x)^2)/(2*sigma^2))";
	private static final double[] params = new double[] { 0, 0, 0, 0, 0, 0 };
	
	private double mu, kT, scaleM, scaleC, offset, temperature, fwhm;

	public FermiGauss() {
		this(params);
	}

	public FermiGauss(double... params) {
		super(params);

		setNames();
	}

	public FermiGauss(IParameter... params) {
		super(params);

		setNames();
	}

	/**
	 * Construction which allows setting of all the bounds
	 * @param minMu
	 * @param maxMu
	 * @param minkT
	 * @param maxkT
	 * @param minScaleM
	 * @param maxScaleM
	 * @param minScaleC
	 * @param maxScaleC
	 * @param minC
	 * @param maxC
	 * @param minSigma
	 * @param maxSigma
	 */
	public FermiGauss(double minMu, double maxMu, double minkT, double maxkT,
					double minScaleM, double maxScaleM, double minScaleC, double maxScaleC,
					double minC, double maxC, double minSigma, double maxSigma) {

		super(NUMBER_OF_PARAMETERS);

		getParameter(0).setLimits(minMu, maxMu);
		getParameter(0).setValue((minMu + maxMu) / 2.0);

		getParameter(1).setLimits(minkT, maxkT);
		getParameter(1).setValue((minkT + maxkT) / 2.0);
		
		getParameter(2).setLimits(minScaleM, maxScaleM);
		getParameter(2).setValue((minScaleM + maxScaleM) / 2.0);
		
		getParameter(3).setLimits(minScaleC, maxScaleC);
		getParameter(3).setValue((minScaleC + maxScaleC) / 2.0);
		
		getParameter(4).setLimits(minC, maxC);
		getParameter(4).setValue((minC + maxC) / 2.0);
		
		getParameter(5).setLimits(minSigma, maxSigma);
		getParameter(5).setValue((minSigma + maxSigma) / 2.0);

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
		temperature = getParameterValue(1);
		scaleM = getParameterValue(2);
		scaleC = getParameterValue(3);
		offset = getParameterValue(4);
		fwhm = getParameterValue(5);

		setDirty(false);
	}
	
	
	@Override
	public double val(double... values)  {
		if (isDirty()) {
			calcCachedParameters();
		}

		AbstractDataset fermiDS = getFermiDS(new DoubleDataset(values, new int[] {values.length}));
		return fermiDS.getDouble(0);
	}
	
	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty()) {
			calcCachedParameters();
		}
		
		AbstractDataset xAxis = (AbstractDataset) it.getValues()[0];
		
		AbstractDataset fermiDS = getFermiDS(xAxis);
		
		if (fwhm == 0.0) {
			data.fill(fermiDS);
			return;
		}
		
		double localSigma = Math.abs(fwhm/TWO_SQRT_TWO_LN_TWO); // convert to sigma
		
		Gaussian gauss = new Gaussian((double)xAxis.mean(), localSigma, 1.0);
		DoubleDataset gaussDS = gauss.calculateValues(xAxis);
		gaussDS = (DoubleDataset) Maths.divide(gaussDS, gaussDS.sum());
		int length = fermiDS.getShapeRef()[0];
		DoubleDataset s1 = DoubleDataset.ones(length*2-1);
		s1.setSlice(fermiDS.getDouble(0), new int[] {0}, new int[] {length/2}, new int[] {1});
		s1.setSlice(fermiDS, new int[] {length/2}, new int[] {length*3/2}, new int[] {1});
		s1.setSlice(fermiDS.getDouble(length-1), new int[] {length*3/2}, new int[] {length*2-1}, new int[] {1});
		
		data.fill(Signal.convolveForOverlap(s1, gaussDS, null));
	}

	public AbstractDataset getFermiDS(IDataset xAxis) {
		if (isDirty()) {
			calcCachedParameters();
		}

		kT = k2eV(temperature);
		Fermi fermi = new Fermi(mu,kT, 1.0, 0.0);
		StraightLine sl = new StraightLine(new double[] {scaleM, scaleC});
		AbstractDataset fermiDS = fermi.calculateValues(xAxis);
		DoubleDataset slDS = sl.calculateValues(Maths.subtract(xAxis,mu));
		fermiDS.imultiply(slDS);
		fermiDS.iadd(offset);
		return fermiDS;
	}
	
	/**
	 * Method to approximate a Gaussisan FWHM from an apparent temperature,
	 * @param realTemperaure the real temperature the sample is at
	 * @return the width of the Fermi edge which needs to be considered for fitting
	 */
	public double approximateFWHM(double realTemperaure) {

		if (getParameterValue(1) < realTemperaure) {
			LOGGER.warn("Fitted temperature was below the real temperature");
			getParameter(1).setValue(realTemperaure);
			getParameter(5).setValue(0.0);
			return k2eV(realTemperaure)*8;
		}
		
		double fitEnergy = k2eV(getParameterValue(1));
		double realEnergy = k2eV(realTemperaure);
		double fwhm = fitEnergy*fitEnergy - realEnergy*realEnergy;
		fwhm = Math.sqrt(fwhm);
		getParameter(1).setValue(realTemperaure);
		getParameter(5).setValue(fwhm);
		
		return fitEnergy*6;
	}
	
	private double k2eV(double temperature) {
		return temperature*K2EV_CONVERSION_FACTOR;
	}	
}
