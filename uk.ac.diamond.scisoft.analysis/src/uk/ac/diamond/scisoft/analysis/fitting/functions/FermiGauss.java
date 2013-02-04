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

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 * Class that wrappers the Fermi function from Fermi-Dirac distribution
 * y(x) = scale / (exp((x - mu)/kT) + 1) + C
 */
public class FermiGauss extends AFunction implements Serializable{
	
	private static final Logger logger = LoggerFactory
			.getLogger(FermiGauss.class);
	
	private static final double K2EV_CONVERSION_FACTOR = 8.6173324e-5;

	private static String cname = "Fermi * Gaussian";

	private static String[] paramNames = new String[]{"mu", "temperature", "BG_slope", "FE_step_height", "Constant", "FWHM"};

	private double mu, kT, scaleM, scaleC, C, temperature, fwhm;

	private static String cdescription = "y(x) = (scale / (exp((x - mu)/kT) + 1) + C) * exp(-((x)^2)/(2*sigma^2))";

	private static double[] params = new double[]{0,0,0,0,0,0};

	
	public FermiGauss(){
		this(params);
	}

	public FermiGauss(double... params) {
		super(params);
		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
	}

	public FermiGauss(IParameter[] params) {
		super(params);
		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
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

		super(6);

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

		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
	}

	private void calcCachedParameters() {
		mu = getParameterValue(0);
		temperature = getParameterValue(1);
		scaleM = getParameterValue(2);
		scaleC = getParameterValue(3);
		C = getParameterValue(4);
		fwhm = getParameterValue(5);
		
		markParametersClean();
	}
	
	
	@Override
	public double val(double... values)  {
		if (areParametersDirty())
			calcCachedParameters();
		

		AbstractDataset fermiDS = getFermiDS(new DoubleDataset(values, new int[] {values.length}));
		return fermiDS.getDouble(0);
	}
	
	@Override
	public DoubleDataset makeDataset(IDataset... values) {
		calcCachedParameters();
		
		IDataset xAxis = values[0];
		
		AbstractDataset fermiDS = getFermiDS(xAxis);
		
		if (fwhm == 0.0) return new DoubleDataset(fermiDS);
		
		double localSigma = Math.abs(fwhm/2.35482); // convert to sigma
		
		DoubleDataset conv = DoubleDataset.ones(xAxis.getShape());
		conv.setName("Convolution");
		
		for (int i = 0; i < conv.getShape()[0]; i++) {
			Gaussian gauss = new Gaussian(xAxis.getDouble(i), localSigma, 1.0);
			DoubleDataset gaussDS = gauss.makeDataset(xAxis);
			gaussDS.idivide(gaussDS.sum());
			
			gaussDS.imultiply(fermiDS);
			
			conv.set(gaussDS.sum(), i);
		}
		
		
		return conv;
	}
	
	public AbstractDataset getFermiDS(IDataset xAxis) {
		calcCachedParameters();
		kT = k2eV(temperature);
		Fermi fermi = new Fermi(mu,kT, 1.0, 0.0);
		StraightLine sl = new StraightLine(new double[] {scaleM, scaleC});
		AbstractDataset fermiDS = fermi.makeDataset(xAxis);
		DoubleDataset slDS = sl.makeDataset(Maths.subtract(xAxis,mu));
		fermiDS.imultiply(slDS);
		fermiDS.iadd(C);
		return fermiDS;
	}
	
	
	/**
	 * Method to approximate a gaussisan FWHM from an appparant temperature,
	 * @param realTemperaure the real temperature the sample is at
	 * @return the width of the fermi edge which needs to be considered for fitting
	 */
	public double approximateFWHM(double realTemperaure) {

		if (getParameterValue(1) < realTemperaure) {
			logger.warn("Fitted temperature was below the real temperature");
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
