package org.dawnsci.surfacescatter;

public class OverlapAttenuationObject {

	
	private int odoNumber;
	private double attenuationfactor;
	
	
	public OverlapAttenuationObject(){
		
	}
	
	public OverlapAttenuationObject(int odoNumber,
									double attenuationFactor){
		
		this.attenuationfactor = attenuationFactor;
		this.odoNumber = odoNumber;
		
	}
	
	public int getOdoNumber() {
		return odoNumber;
	}
	public void setOdoNumber(int odoNumber) {
		this.odoNumber = odoNumber;
	}
	public double getAttenuationfactor() {
		return attenuationfactor;
	}
	public void setAttenuationfactor(double attenuationfactor) {
		this.attenuationfactor = attenuationfactor;
	}
	
}
