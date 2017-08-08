package org.dawnsci.surfacescatter;

public enum ReflectivityFluxParametersAliasEnum {

	FLUX ("flux", "ionc1", "adc2"),
	FLUX_SCANNED_VARIABLE ("flux scanned variable", "qsdcd", "qdcd_"),
	NULL(null, null, null); 
	
	private String fluxVariable;
	private String fluxAlias;
	private String fluxSecondAlias;
	
	
	ReflectivityFluxParametersAliasEnum(String aV, 
				   	   					String aA,
				   	   					String aA2){
		
		fluxVariable = aV;
		fluxAlias = aA;
		fluxSecondAlias = aA2;
		
	}
	
	public String getFluxVariable(){
		return fluxVariable;
	}
	
	public String getFluxAlias(){
		return fluxAlias;
	}
	
	public String getFluxSecondAlias(){
		return fluxSecondAlias;
	}
	
	public void setFluxAlias(String g){
		this.fluxAlias = g;
	}
	

}
