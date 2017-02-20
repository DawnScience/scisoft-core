/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Lorentzian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Offset;

public class PeakyData {
	
	private static Double xAxisMax = 30.; 
	private static DoubleDataset xAxisRange = (DoubleDataset) DatasetFactory.createRange(0, xAxisMax, 0.01, Dataset.FLOAT64);
	
	public static Add makeGauPeak() {
		Add peak = new Add();
		
		Gaussian g1 = new Gaussian(0.3785*xAxisMax, 0.0200*xAxisMax, 50.);
		peak.addFunction(g1);
		
		return peak;
	}
	
	public static Add makeGauLorPeaks() {
		Add peaks = new Add();
		
		Gaussian g1 = new Gaussian(0.1080*xAxisMax, 0.0200*xAxisMax, 50.);
		Gaussian g2 = new Gaussian(0.4934*xAxisMax, 0.0520*xAxisMax, 47.);
		Gaussian g3 = new Gaussian(0.7780*xAxisMax, 0.0264*xAxisMax, 8.51);
		
		Lorentzian l1 = new Lorentzian(0.1840*xAxisMax, 0.0178*xAxisMax, 32.58);
		Lorentzian l2 = new Lorentzian(0.5716*xAxisMax, 0.0364*xAxisMax, 25.58);
		Lorentzian l3 = new Lorentzian(0.9504*xAxisMax, 0.0104*xAxisMax, 6.57);
		
		peaks.addFunction(g1);
		peaks.addFunction(g2);
		peaks.addFunction(g3);
		peaks.addFunction(l1);
		peaks.addFunction(l2);
		peaks.addFunction(l3);
				
		return peaks;
	}
	
	public static Add makeOffsetGauLorPeaks() {
		Offset offsetBkg = new Offset();
		offsetBkg.setParameterValues(5.7);
		
		Add profileFn = makeGauLorPeaks();
		profileFn.addFunction(offsetBkg);
		
		return profileFn;
	}
	
	public static DoubleDataset waveGen(DoubleDataset xData, Double fx, Double fs, Double scale){
		xData = (DoubleDataset) Maths.multiply(xData, 2);
		xData = (DoubleDataset) Maths.multiply(xData, Math.PI);
		xData = (DoubleDataset) Maths.multiply(xData, fx/fs);
		xData = (DoubleDataset) Maths.multiply(xData, scale);
		xData = (DoubleDataset) Maths.sin(xData);
		return xData;
	}
	
	public static DoubleDataset intialisePeakData(DoubleDataset xData) {
		DoubleDataset wave1 = waveGen(xData, 2., 1., 1.);
		DoubleDataset wave2 = waveGen(xData, 20., 1., 0.6);
		DoubleDataset wave3 = waveGen(xData, 1., 2., 0.8);

		DoubleDataset yData = wave1.iadd(wave2).iadd(wave3);
		return yData;
	}
	
	//TODO: use the add for functions to gneerate wave peaks
	private DoubleDataset makeSinusoidalWavePeakData(DoubleDataset xData){	
		DoubleDataset wave1 = waveGen(xData, 2., 1., 1.);
		DoubleDataset wave2 = waveGen(xData, 20., 1., 0.6);
		DoubleDataset wave3 = waveGen(xData, 1., 2., 0.8);
		
		DoubleDataset yData = wave1.iadd(wave2).iadd(wave3);
		return yData;
	}

	public static Double getxAxisMax() {
		return xAxisMax;
	}

	public static void setxAxisMax(Double xAxisMax) {
		PeakyData.xAxisMax = xAxisMax;
		PeakyData.xAxisRange = (DoubleDataset) DatasetFactory.createRange(0, xAxisMax, 0.01, Dataset.FLOAT64);
	}
	
	public static DoubleDataset getxAxisRange() {
		return xAxisRange;
	}
	
	
		

}
