package org.dawnsci.surfacescatter;

public class OverlapDataModel {
	
	private String lowerDatName;
	private int[] lowerOverlapPositions;
	private double[] lowerOverlapScannedValues;
	private double[] lowerOverlapCorrectedValues;
	private double[] lowerOverlapRawValues;
	private double[] lowerOverlapFhklValues;
	
//	parameters for the quartic used to fit the lower overlap region
	
	private double[] lowerOverlapFitParametersCorrected;
	private double[] lowerOverlapFitParametersRaw;
	private double[] lowerOverlapFitParametersFhkl;
	
	private String upperDatName;
	private int[] upperOverlapPositions;
	private double[] upperOverlapScannedValues;
	private double[] upperOverlapCorrectedValues;
	private double[] upperOverlapRawValues;
	private double[] upperOverlapFhklValues;
	
//	parameters for the quartic used to fit the upper overlap region
	
	private double[] upperOverlapFitParametersCorrected;
	private double[] upperOverlapFitParametersRaw;
	private double[] upperOverlapFitParametersFhkl;
	   
	private double attenuationFactor;
	private double attenuationFactorFhkl;
	private double attenuationFactorRaw;
	
//	parameters for the FFT used to fit the upper overlap region
	
	private double[][] upperFFTFitCoefficientsCorrected;
	private double[][] upperFFTFitCoefficientsRaw;
	private double[][] upperFFTFitCoefficientsFhkl;

	private double upperBaseFrequencyCorrected;
	private double upperBaseFrequencyRaw;
	private double upperBaseFrequencyFhkl;
	
//	parameters for the FFT used to fit the lower overlap region
	
	private double[][] lowerFFTFitCoefficientsCorrected;
	private double[][] lowerFFTFitCoefficientsRaw;
	private double[][] lowerFFTFitCoefficientsFhkl;
	
	private double lowerBaseFrequencyCorrected;
	private double lowerBaseFrequencyRaw;
	private double lowerBaseFrequencyFhkl;
	
	private double attenuationFactorFFT;
	private double attenuationFactorFhklFFT;
	private double attenuationFactorRawFFT;
	
	
	public double getUpperBaseFrequencyCorrected() {
		return upperBaseFrequencyCorrected;
	}
	public void setUpperBaseFrequencyCorrected(double upperBaseFrequencyCorrected) {
		this.upperBaseFrequencyCorrected = upperBaseFrequencyCorrected;
	}
	public double getUpperBaseFrequencyRaw() {
		return upperBaseFrequencyRaw;
	}
	public void setUpperBaseFrequencyRaw(double upperBaseFrequencyRaw) {
		this.upperBaseFrequencyRaw = upperBaseFrequencyRaw;
	}
	public double getUpperBaseFrequencyFhkl() {
		return upperBaseFrequencyFhkl;
	}
	public void setUpperBaseFrequencyFhkl(double upperBaseFrequencyFhkl) {
		this.upperBaseFrequencyFhkl = upperBaseFrequencyFhkl;
	}
	public double getLowerBaseFrequencyRaw() {
		return lowerBaseFrequencyRaw;
	}
	public void setLowerBaseFrequencyRaw(double lowerBaseFrequencyRaw) {
		this.lowerBaseFrequencyRaw = lowerBaseFrequencyRaw;
	}
	public double getLowerBaseFrequencyFhkl() {
		return lowerBaseFrequencyFhkl;
	}
	public void setLowerBaseFrequencyFhkl(double lowerBaseFrequencyFhkl) {
		this.lowerBaseFrequencyFhkl = lowerBaseFrequencyFhkl;
	}
	
	public double[][] getUpperFFTFitCoefficientsCorrected() {
		return upperFFTFitCoefficientsCorrected;
	}
	public void setUpperFFTFitCoefficientsCorrected(double[][] upperFFTFitCoefficientsCorrected) {
		this.upperFFTFitCoefficientsCorrected = upperFFTFitCoefficientsCorrected;
	}
	public double[][] getUpperFFTFitCoefficientsRaw() {
		return upperFFTFitCoefficientsRaw;
	}
	public void setUpperFFTFitCoefficientsRaw(double[][] upperFFTFitCoefficientsRaw) {
		this.upperFFTFitCoefficientsRaw = upperFFTFitCoefficientsRaw;
	}
	public double[][] getUpperFFTFitCoefficientsFhkl() {
		return upperFFTFitCoefficientsFhkl;
	}
	public void setUpperFFTFitCoefficientsFhkl(double[][] upperFFTFitCoefficientsFhkl) {
		this.upperFFTFitCoefficientsFhkl = upperFFTFitCoefficientsFhkl;
	}

	public double[][] getLowerFFTFitCoefficientsCorrected() {
		return lowerFFTFitCoefficientsCorrected;
	}
	public void setLowerFFTFitCoefficientsCorrected(double[][] lowerFFTFitCoefficientsCorrected) {
		this.lowerFFTFitCoefficientsCorrected = lowerFFTFitCoefficientsCorrected;
	}
	public double[][] getLowerFFTFitCoefficientsRaw() {
		return lowerFFTFitCoefficientsRaw;
	}
	public void setLowerFFTFitCoefficientsRaw(double[][] lowerFFTFitCoefficientsRaw) {
		this.lowerFFTFitCoefficientsRaw = lowerFFTFitCoefficientsRaw;
	}
	public double[][] getLowerFFTFitCoefficientsFhkl() {
		return lowerFFTFitCoefficientsFhkl;
	}
	public void setLowerFFTFitCoefficientsFhkl(double[][] lowerFFTFitCoefficientsFhkl) {
		this.lowerFFTFitCoefficientsFhkl = lowerFFTFitCoefficientsFhkl;
	}
	public double getLowerBaseFrequencyCorrected() {
		return lowerBaseFrequencyCorrected;
	}
	public void setLowerBaseFrequencyCorrected(double lowerBaseFrequency) {
		this.lowerBaseFrequencyCorrected = lowerBaseFrequency;
	}
	public double getAttenuationFactorFFT() {
		return attenuationFactorFFT;
	}
	public void setAttenuationFactorFFT(double attenuationFactorFFT) {
		this.attenuationFactorFFT = attenuationFactorFFT;
	}
	public double getAttenuationFactorFhklFFT() {
		return attenuationFactorFhklFFT;
	}
	public void setAttenuationFactorFhklFFT(double attenuationFactorFhklFFT) {
		this.attenuationFactorFhklFFT = attenuationFactorFhklFFT;
	}
	public double getAttenuationFactorRawFFT() {
		return attenuationFactorRawFFT;
	}
	public void setAttenuationFactorRawFFT(double attenuationFactorRawFFT) {
		this.attenuationFactorRawFFT = attenuationFactorRawFFT;
	}
	public double[] getLowerOverlapScannedValues() {
		return lowerOverlapScannedValues;
	}
	public void setLowerOverlapScannedValues(double[] lowerOverlapScannedValues) {
		this.lowerOverlapScannedValues = lowerOverlapScannedValues;
	}
	public double[] getUpperOverlapScannedValues() {
		return upperOverlapScannedValues;
	}
	public void setUpperOverlapScannedValues(double[] upperOverlapScannedValues) {
		this.upperOverlapScannedValues = upperOverlapScannedValues;
	}
	
	public String getLowerDatName() {
		return lowerDatName;
	}
	public void setLowerDatName(String lowerDatName) {
		this.lowerDatName = lowerDatName;
	}
	public int[] getLowerOverlapPositions() {
		return lowerOverlapPositions;
	}
	public void setLowerOverlapPositions(int[] lowerOverlapPositions) {
		this.lowerOverlapPositions = lowerOverlapPositions;
	}
	public double[] getLowerOverlapCorrectedValues() {
		return lowerOverlapCorrectedValues;
	}
	public void setLowerOverlapCorrectedValues(double[] lowerOverlapCorrectedValues) {
		this.lowerOverlapCorrectedValues = lowerOverlapCorrectedValues;
	}
	public double[] getLowerOverlapRawValues() {
		return lowerOverlapRawValues;
	}
	public void setLowerOverlapRawValues(double[] lowerOverlapRawValues) {
		this.lowerOverlapRawValues = lowerOverlapRawValues;
	}
	public double[] getLowerOverlapFhklValues() {
		return lowerOverlapFhklValues;
	}
	public void setLowerOverlapFhklValues(double[] lowerOverlapFhklValues) {
		this.lowerOverlapFhklValues = lowerOverlapFhklValues;
	}
	public double[] getLowerOverlapFitParametersCorrected() {
		return lowerOverlapFitParametersCorrected;
	}
	public void setLowerOverlapFitParametersCorrected(double[] lowerOverlapFitParametersCorrected) {
		this.lowerOverlapFitParametersCorrected = lowerOverlapFitParametersCorrected;
	}
	public double[] getLowerOverlapFitParametersRaw() {
		return lowerOverlapFitParametersRaw;
	}
	public void setLowerOverlapFitParametersRaw(double[] lowerOverlapFitParametersRaw) {
		this.lowerOverlapFitParametersRaw = lowerOverlapFitParametersRaw;
	}
	public double[] getLowerOverlapFitParametersFhkl() {
		return lowerOverlapFitParametersFhkl;
	}
	public void setLowerOverlapFitParametersFhkl(double[] lowerOverlapFitParametersFhkl) {
		this.lowerOverlapFitParametersFhkl = lowerOverlapFitParametersFhkl;
	}
	public String getUpperDatName() {
		return upperDatName;
	}
	public void setUpperDatName(String upperDatName) {
		this.upperDatName = upperDatName;
	}
	public int[] getUpperOverlapPositions() {
		return upperOverlapPositions;
	}
	public void setUpperOverlapPositions(int[] upperOverlapPositions) {
		this.upperOverlapPositions = upperOverlapPositions;
	}
	public double[] getUpperOverlapCorrectedValues() {
		return upperOverlapCorrectedValues;
	}
	public void setUpperOverlapCorrectedValues(double[] upperOverlapCorrectedValues) {
		this.upperOverlapCorrectedValues = upperOverlapCorrectedValues;
	}
	public double[] getUpperOverlapRawValues() {
		return upperOverlapRawValues;
	}
	public void setUpperOverlapRawValues(double[] upperOverlapRawValues) {
		this.upperOverlapRawValues = upperOverlapRawValues;
	}
	public double[] getUpperOverlapFhklValues() {
		return upperOverlapFhklValues;
	}
	public void setUpperOverlapFhklValues(double[] upperOverlapFhklValues) {
		this.upperOverlapFhklValues = upperOverlapFhklValues;
	}
	public double[] getUpperOverlapFitParametersCorrected() {
		return upperOverlapFitParametersCorrected;
	}
	public void setUpperOverlapFitParametersCorrected(double[] upperOverlapFitParametersCorrected) {
		this.upperOverlapFitParametersCorrected = upperOverlapFitParametersCorrected;
	}
	public double[] getUpperOverlapFitParametersRaw() {
		return upperOverlapFitParametersRaw;
	}
	public void setUpperOverlapFitParametersRaw(double[] upperOverlapFitParametersRaw) {
		this.upperOverlapFitParametersRaw = upperOverlapFitParametersRaw;
	}
	public double[] getUpperOverlapFitParametersFhkl() {
		return upperOverlapFitParametersFhkl;
	}
	public void setUpperOverlapFitParametersFhkl(double[] upperOverlapFitParametersFhkl) {
		this.upperOverlapFitParametersFhkl = upperOverlapFitParametersFhkl;
	}
	public double getAttenuationFactor() {
		return attenuationFactor;
	}
	public void setAttenuationFactor(double attenuationFactor) {
		this.attenuationFactor = attenuationFactor;
	}
	public double getAttenuationFactorFhkl() {
		return attenuationFactorFhkl;
	}
	public void setAttenuationFactorFhkl(double attenuationFactorFhkl) {
		this.attenuationFactorFhkl = attenuationFactorFhkl;
	}
	public double getAttenuationFactorRaw() {
		return attenuationFactorRaw;
	}
	public void setAttenuationFactorRaw(double attenuationFactorRaw) {
		this.attenuationFactorRaw = attenuationFactorRaw;
	}
		
}
