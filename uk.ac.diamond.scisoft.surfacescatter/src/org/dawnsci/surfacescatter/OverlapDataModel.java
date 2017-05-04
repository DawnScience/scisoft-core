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
