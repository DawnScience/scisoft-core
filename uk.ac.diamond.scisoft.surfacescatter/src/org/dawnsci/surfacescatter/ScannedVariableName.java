package org.dawnsci.surfacescatter;

public enum ScannedVariableName {

	SCANNED_VARIABLE_NAME("Scanned Variable", "Scanned Variable");
	
	private String setName;
	private String defaultName;

	ScannedVariableName(String a, String b) {

		this.setName = a;
		this.defaultName = b;

	}
	
	public String getDefaultName() {
		return defaultName;
	}

	public String getSetName() {
		return setName;
	}
	
	public void setSetName(String in) {
		setName = in;
	}

	
	public String getName() {
		
		if(SCANNED_VARIABLE_NAME.getSetName().equals(null)) {
			return SCANNED_VARIABLE_NAME.getDefaultName();
		}
		
		return SCANNED_VARIABLE_NAME.getSetName();
	}
	
	
}
