package org.dawnsci.surfacescatter;

public class AttenuationFactors {

	private double attenuationFactorCorrected;
	private double attenuationFactorRaw;
	private double attenuationFactorFhkl;
	
	
	public AttenuationFactors(double afc, double afr, double aff) {
		this.attenuationFactorCorrected=afc;
		this.attenuationFactorRaw=afr;
		this.attenuationFactorFhkl=aff;
		
	}
	
	public double getAttenuationFactorCorrected() {
		return attenuationFactorCorrected;
	}
	public void setAttenuationFactorCorrected(double attenuationFactorCorrected) {
		this.attenuationFactorCorrected = attenuationFactorCorrected;
	}
	public double getAttenuationFactorRaw() {
		return attenuationFactorRaw;
	}
	public void setAttenuationFactorRaw(double attenuationFactorRaw) {
		this.attenuationFactorRaw = attenuationFactorRaw;
	}
	public double getAttenuationFactorFhkl() {
		return attenuationFactorFhkl;
	}
	public void setAttenuationFactorFhkl(double attenuationFactorFhkl) {
		this.attenuationFactorFhkl = attenuationFactorFhkl;
	}
	
	
}
