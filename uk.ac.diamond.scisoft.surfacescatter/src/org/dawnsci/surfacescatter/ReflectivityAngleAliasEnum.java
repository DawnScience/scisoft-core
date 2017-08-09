package org.dawnsci.surfacescatter;

public enum ReflectivityAngleAliasEnum {
	
	THETA ("theta", "dcdtheta"),
	Q("q", "qdcd"),
	NULL(null, null); 
	
	private String angleVariable;
	private String angleAlias;
	
	ReflectivityAngleAliasEnum(String aV, 
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
