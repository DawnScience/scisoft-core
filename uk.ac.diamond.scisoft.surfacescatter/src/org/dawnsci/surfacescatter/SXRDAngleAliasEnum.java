package org.dawnsci.surfacescatter;

public enum SXRDAngleAliasEnum {
	
	ALPHA("alpha", "alpha"),
	DELTA("delta", "delta"),
	GAMMA ("gamma", "gamma"),
	OMEGA ("omega", "omega"),
	CHI ("chi", "chi"),
	PHI ("phi", "phi"),
	NULL(null, null);
	
	private String angleVariable;
	private String angleAlias;
	
	SXRDAngleAliasEnum(String aV, 
				   	   String aA){
		
		angleVariable = aV;
		angleAlias = aA;
		
	}
	
	public String getAngleVariable(){
		return angleVariable;
	}
	
	public String getAngleAlias(){
		return angleAlias;
	}
	
	public void setAngleAlias(String g){
		this.angleAlias = g;
	}
	
}
