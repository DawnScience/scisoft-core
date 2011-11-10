/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis;

import gda.analysis.functions.AFunction;
import gda.analysis.functions.CompositeFunction;
import gda.analysis.functions.FunctionOutput;
import gda.analysis.functions.Gaussian;
import gda.analysis.functions.GaussianND;
import gda.analysis.functions.IFunction;
import gda.analysis.functions.Offset;
import gda.analysis.functions.Parameter;
import gda.analysis.utils.GeneticAlg;
import gda.analysis.utils.IOptimizer;
import gda.analysis.utils.NelderMead;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.python.modules.math;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class GaussianFitter implements Serializable {
	/**
	 * Setup the logging facilities
	 */
	transient protected static final Logger logger = LoggerFactory.getLogger(GaussianFitter.class);

	/**
	 * @param data 1D dataset
	 * @param width size of the region that a gaussian will be fitted to
	 * @param step the size of the increment for the starting position
	 * @return list of peak positions
	 */
	public static List<Double> fitGaussian1D(DataSet data, int width, int step){
		List<FunctionOutput> gaussianPeaks = fitGaussianTo1DDataset(data,width,step);
		return removeDuplicateGaussian(gaussianPeaks, data.getSize());
	}

	/**
	 * This method take a 1D dataset and fits a number of gaussian peaks. The
	 * dataset will be subsampled and for each of the subsamples a gaussian will
	 * be fitted.
	 * 
	 * @param data : 1D dataset 	 
	 * @param width : size of the region that a gaussian will be fitted to
	 * @param step : the size of the increment for the starting position
	 * @return Composite function containing the information about each gaussian
	 */
	public static List<FunctionOutput> fitGaussianTo1DDataset(DataSet data, int width, int step) {
		if (data.getRank() != 1)
			return null;

		List<FunctionOutput> allPeaks = new ArrayList<FunctionOutput>();

		// slice data
		DataSet sliceData;
		FunctionOutput funcOut = null;
		//int width = width;// (int) Math.floor(0.1 * data.dataSize());
		//int step = s;//(int) Math.floor(0.05 * data.dataSize());
		int end;

		double pos;
		double FWHM;
		double area;

		double fittedPos;
		double fittedFWHM;
		double fittedArea;
		
		// Values for sanity check
		double totalArea = data.getSize() * data.range();
		
		for (int i = 0; (i + step) < data.getSize(); i += step) {
			if ((i + width) > data.getSize())
				end = data.getSize();
			else
				end = i + width;

			sliceData = data.getSlice(i, end);
			DataSet xAxis = DataSet.arange(i, (double) end);
			pos = xAxis.get(sliceData.maxPos()[0]);
			FWHM = xAxis.range() / 4.0;
			area = FWHM * sliceData.range();
			
			try {
				double [] dataArray = data.doubleArray();
				Arrays.sort(dataArray);
				double median = dataArray[(int)math.floor(dataArray.length/2)];
				funcOut = nelderMeadFit(new DataSet[] {xAxis}, sliceData, 0.0001,
						new Gaussian(pos, FWHM, area), new Offset(median*0.95,median));

				// sanity check of peaks
				// funcOut.getFunction().disp();
				fittedPos = funcOut.getParameterValue(0);
				fittedFWHM = funcOut.getParameterValue(1);
				fittedArea = funcOut.getParameterValue(2);

				if (fittedPos > i && fittedPos < end
						&& fittedFWHM < 1.5*width && fittedArea < totalArea) { 
					allPeaks.add(funcOut);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < allPeaks.size(); i++) {
			Parameter[] params = allPeaks.get(i).getFunction().getParameters();
			logger.info("Peak {} as {} with width {} and ampl {}", new Object[] { i, params[0].getValue()/2,
					params[1].getValue(), params[2].getValue()});
		}

		return allPeaks;
	}

	private static List<Double> removeDuplicateGaussian(List<FunctionOutput> allPeaks, double width){
		List<Double> peakPositions = new ArrayList<Double>();

		for (int j = 0; j < allPeaks.size(); j++) {
			IFunction peak1 = allPeaks.get(j).getFunction();

			//double value = peak1.val(peak1.getParameterValue(0));
			double peak1position = peak1.getParameterValue(0);
			double peak1FWHM = peak1.getParameterValue(1);
			if(peak1FWHM>width){
				continue;
			}
			int k = j + 1;
			for (; k < allPeaks.size(); k++) {
				IFunction peak2 = allPeaks.get(k).getFunction();
				double peak2position = peak2.getParameterValue(0);
				double peak2FWHM = peak2.getParameterValue(1);
				double epsilon = Math.min(peak1FWHM, peak2FWHM) / 2;
				if (Math.abs(peak1position - peak2position) < epsilon) {
					logger.info("Peaks {} and {} are paired", j, k);
					peakPositions.add((peak1position + peak2position) / 2);
					j = k;
					break;
				}
			}
			if (k == allPeaks.size()) {
				logger.info("Solo peak {}", j);
				peakPositions.add(peak1position);
			}
		}

//		for (double p : peakPositions) {
//			System.out.println(p);
//		}
		return peakPositions;
	}

	/**
	 * This function takes a pair of datasets and some other inputs, and then
	 * fits the function specified using the method specified. It also plots the
	 * output in the DataVector window.
	 * 
	 * @param coords
	 *            The DataSet containing all the x values of the data
	 * @param data
	 *            The DataSet containing all the y values of the data
	 * @param accuracy of fit
	 * @param functions
	 *            A list of functions which inherit from AFunction which are
	 *            used to make up the function to be fit.
	 * @return A FunctionOutput object which records all the output parameters
	 * @throws Exception
	 */
	private static FunctionOutput nelderMeadFit(DataSet[] coords, DataSet data,
			double accuracy, AFunction... functions) throws Exception {

		CompositeFunction comp = new CompositeFunction();
		IOptimizer Optimizer = new NelderMead(accuracy);

		for (int i = 0; i < functions.length; i++) {
			comp.addFunction(functions[i]);
		}

		// call the optimisation routine
		Optimizer.Optimize(coords, data, comp);

		FunctionOutput result = new FunctionOutput(comp);

		result.setChiSquared(comp.residual(true, data, coords));

		result.setAreaUnderFit(comp.makeDataSet(coords).sum()
				* ((coords[0].max() - coords[0].min()) / coords[0].getSize()));

		return result;

	}

	private static FunctionOutput geneticAlgorithmFit(DataSet[] coords, DataSet data,
			double accuracy, AFunction... functions) throws Exception {

		CompositeFunction comp = new CompositeFunction();

		IOptimizer Optimizer = new GeneticAlg(accuracy);

		for (int i = 0; i < functions.length; i++) {
			comp.addFunction(functions[i]);
		}

		// call the optimisation routine
		Optimizer.Optimize(coords, data, comp);

		FunctionOutput result = new FunctionOutput(comp);

		result.setChiSquared(comp.residual(true, data, coords));

		result.setAreaUnderFit(comp.makeDataSet(coords).sum()
				* ((coords[0].max() - coords[0].min()) / coords[0].getSize()));

		return result;

	}

	/**
	 * @param data 2D dataset
	 * @param width max width
	 * @param max maximum amplitude
	 * @param pos position of starting point (can be null, then centre of dataset used)
	 * @return position of fitted peak
	 */
	public static List<Double> fitGaussian2D(DataSet data, double width, double max, double[] pos) {
		List<FunctionOutput> gaussianPeaks = fitGaussianTo2DDataset(data, width, max, pos);
		List<Double> peakPositions = new ArrayList<Double>();

		if (gaussianPeaks.size() > 0) {
			FunctionOutput out = gaussianPeaks.get(0);
			peakPositions.add(out.getParameterValue(0));
			peakPositions.add(out.getParameterValue(1));
		}
		return peakPositions;
	}

	/**
	 * This method takes a 2D dataset and fits a single Gaussian to it
	 * 
	 * @param data 2D dataset
	 * @param width max width
	 * @param max maximum amplitude
	 * @param pos initial estimate of peak position
	 * @return a single function output in a list
	 */
	public static List<FunctionOutput> fitGaussianTo2DDataset(DataSet data, double width, double max, double[] pos) {
		if (data.getRank() != 2)
			return null;

		List<FunctionOutput> allPeaks = new ArrayList<FunctionOutput>();

		FunctionOutput funcOut = null;

		double fittedPos0;
		double fittedPos1;
		double fittedSig0;
		double fittedSig1;

		int[] shape = data.getShape();
		DataSet c0 = DataSet.arange(shape[0]);
		DataSet c1 = DataSet.arange(shape[1]);
		try {
			double [] dataArray = data.doubleArray();
			Arrays.sort(dataArray);
			double median = dataArray[(int)math.floor(dataArray.length/2)];
			double maxvol = 2.*max*25.*width*width;
			GaussianND g = new GaussianND(maxvol, new double[] {0, 0}, new double[] {shape[0], shape[1]}, 5*width);
			logger.info("Starting peak value {}", g.getPeakValue());

			if (pos != null) {
				g.setPeakPosition(pos);
			} else {
				double[] centre = new double[] {shape[0]/2., shape[1]/2.};
				g.setPeakPosition(centre);
			}

			try {
//				funcOut = nelderMeadFit(new DataSet[] {c0, c1}, data, 0.0001, g, new Offset(median/10.,median));
				funcOut = geneticAlgorithmFit(new DataSet[] {c0, c1}, data, 0.1, g, new Offset(median/100.,median));
			} catch (IllegalArgumentException e) {
				logger.error("Problem with fitting!!!");
				return allPeaks;
			}

			logger.info("Fitted peak value {}", g.getPeakValue());

			// sanity check of peaks
			// funcOut.getFunction().disp();
			fittedPos0 = funcOut.getParameterValue(0);
			fittedPos1 = funcOut.getParameterValue(1);
			fittedSig0 = Math.sqrt(funcOut.getParameterValue(3));
			fittedSig1 = Math.sqrt(funcOut.getParameterValue(4));

			if (fittedPos0 < shape[0] && fittedPos1 < shape[1]
					&& fittedSig0 < 1.5*width && fittedSig1 < 1.5*width) { 
				allPeaks.add(funcOut);
			} else {
				logger.info("Ignoring peak at {}, {} with width {}, {} and vol {}", new Object[] {fittedPos0, fittedPos1, fittedSig0, fittedSig1, funcOut.getParameterValue(2)});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < allPeaks.size(); i++) {
			Parameter[] params = allPeaks.get(i).getFunction().getParameters();
			logger.info("Peak {} as {}, {} with width {}, {} and vol {}", new Object[] { i, params[0].getValue(),
					params[1].getValue(), Math.sqrt(params[3].getValue()), Math.sqrt(params[4].getValue()), params[2].getValue()});
		}

		return allPeaks;
	}

}