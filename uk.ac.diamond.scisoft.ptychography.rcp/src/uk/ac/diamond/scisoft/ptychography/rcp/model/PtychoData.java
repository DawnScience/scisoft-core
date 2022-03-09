package uk.ac.diamond.scisoft.ptychography.rcp.model;

public class PtychoData {

	private int level;
	private String name;
	private String defaultValue;
	private String type;
	private boolean isUnique;
	private int lowerLimit;
	private int upperLimit;
	private String shortDoc;
	private String longDoc;
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isUnique() {
		return isUnique;
	}
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}
	public int getLowerLimit() {
		return lowerLimit;
	}
	public void setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public int getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
	}
	public String getShortDoc() {
		return shortDoc;
	}
	public void setShortDoc(String shortDoc) {
		this.shortDoc = shortDoc;
	}
	public String getLongDoc() {
		return longDoc;
	}
	public void setLongDoc(String longDoc) {
		this.longDoc = longDoc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result + (isUnique ? 1231 : 1237);
		result = prime * result + level;
		result = prime * result + ((longDoc == null) ? 0 : longDoc.hashCode());
		result = prime * result + lowerLimit;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((shortDoc == null) ? 0 : shortDoc.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + upperLimit;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PtychoData other = (PtychoData) obj;
		if (defaultValue == null) {
			if (other.defaultValue != null)
				return false;
		} else if (!defaultValue.equals(other.defaultValue))
			return false;
		if (isUnique != other.isUnique)
			return false;
		if (level != other.level)
			return false;
		if (longDoc == null) {
			if (other.longDoc != null)
				return false;
		} else if (!longDoc.equals(other.longDoc))
			return false;
		if (lowerLimit != other.lowerLimit)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (shortDoc == null) {
			if (other.shortDoc != null)
				return false;
		} else if (!shortDoc.equals(other.shortDoc))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (upperLimit != other.upperLimit)
			return false;
		return true;
	}
}
