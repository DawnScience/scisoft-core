package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.IDataset;

public class FourierScalingOutputPackage {

	private double baseFrequency;
	
	private double[][] coefficients = new double[2][];
	
	private double[][] upperCoefficients = new double[2][];
	private double[][] lowerCoefficients = new double[2][];
	
	private double upperBaseFrequency;
	private double lowerBaseFrequency;
	
	private IDataset computedYOutputDataset;
	private double[] correction;
	private double[] runningSumNormArray;
	private double[] rMSLowerHigher;
	
	public FourierScalingOutputPackage(){
		
	}
	
	public FourierScalingOutputPackage(double baseFrequency, 
			double[][] coefficients,
			double[] correction,
			double[] runningSumNormArray,
			double[] rMSLowerHigher,
			IDataset computedYOutputDataset) {
		
		this.baseFrequency = baseFrequency;
		this.coefficients = coefficients;
		this.computedYOutputDataset = computedYOutputDataset;
		this.runningSumNormArray =runningSumNormArray;
		this.rMSLowerHigher = rMSLowerHigher;
		this.correction = correction;
		
	}
	
	public double[][] getUpperCoefficients() {
		return upperCoefficients;
	}

	public void setUpperCoefficients(double[][] upperCoefficients) {
		this.upperCoefficients = upperCoefficients;
	}

	public double[][] getLowerCoefficients() {
		return lowerCoefficients;
	}

	public void setLowerCoefficients(double[][] lowerCoefficients) {
		this.lowerCoefficients = lowerCoefficients;
	}

	public double getUpperBaseFrequency() {
		return upperBaseFrequency;
	}

	public void setUpperBaseFrequency(double upperBaseFrequency) {
		this.upperBaseFrequency = upperBaseFrequency;
	}

	public double getLowerBaseFrequency() {
		return lowerBaseFrequency;
	}

	public void setLowerBaseFrequency(double lowerBaseFrequency) {
		this.lowerBaseFrequency = lowerBaseFrequency;
	}

	
	public double getBaseFrequency() {
		return baseFrequency;
	}
	public void setBaseFrequency(double baseFrequency) {
		this.baseFrequency = baseFrequency;
	}
	public double[][] getCoefficients() {
		return coefficients;
	}
	public void setCoefficients(double[][] coefficients) {
		this.coefficients = coefficients;
	}
	public IDataset getComputedYOutputDataset() {
		return computedYOutputDataset;
	}
	public void setComputedYOutputDataset(IDataset computedYOutputDataset) {
		this.computedYOutputDataset = computedYOutputDataset;
	}

	public double[] getRunningSumNormArray() {
		return runningSumNormArray;
	}

	public void setRunningSumNormArray(double[] runningSumNormArray) {
		this.runningSumNormArray = runningSumNormArray;
	}

	public double[] getCorrection() {
		return correction;
	}

	public void setCorrection(double[] correction) {
		this.correction = correction;
	}

	public double[] getrMSLowerHigher() {
		return rMSLowerHigher;
	}

	public void setrMSLowerHigher(double[] rMSLowerHigher) {
		this.rMSLowerHigher = rMSLowerHigher;
	}

	
}
