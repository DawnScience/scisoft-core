package org.dawnsci.surfacescatter;

public class OverlapAttenuationObject {

	
	private int odoNumber;
	private double attenuationFactorCorrected;
	private double attenuationFactorRaw;
	private double attenuationFactorFhkl;
	private boolean modified = false;
	
	
	public OverlapAttenuationObject(){
		
	}
	
	public OverlapAttenuationObject(int odoNumber,
									double attenuationFactorCorrected,
									double attenuationFactorRaw,
									double attenuationFactorFhkl,
									boolean modified){
		
		this.attenuationFactorCorrected = attenuationFactorCorrected;
		this.attenuationFactorRaw = attenuationFactorRaw;
		this.attenuationFactorFhkl = attenuationFactorFhkl;
		this.odoNumber = odoNumber;
		this.modified = modified;
		
	}
	
	public int getOdoNumber() {
		return odoNumber;
	}
	public void setOdoNumber(int odoNumber) {
		this.odoNumber = odoNumber;
	}
	public double getAttenuationFactorCorrected() {
		return attenuationFactorCorrected;
	}
	public void setAttenuationFactorCorrected(double attenuationFactorCorrected) {
		this.attenuationFactorCorrected = attenuationFactorCorrected;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
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
