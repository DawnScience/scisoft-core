package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;


public class PeakFinderParameter implements IPeakFinderParameter {
	
	private String name;
	private Number value;
	private boolean isInt;
	
	public PeakFinderParameter(String name, Boolean isInt, Number value) throws Exception {
		this.name = name;
		this.isInt = isInt;
		setValue(value);		
	}

	@Override
	public Number getValue() {
		return value;
	}

	@Override
	public void setValue(Number value) throws Exception {
		try {
			if((isInt) && (value == (Integer)value)) this.value = value;
		} catch (Exception e) {
			if (isInt) throw new Exception("Parameter "+name+" should be an Integer, found Double.");
		} finally {
			if (!isInt) this.value = value;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isInt() {
		return isInt;
	}
	
	@Override
	public String toString() {
		return name+" = "+String.valueOf(value);
	}
	
	/*
	 * hashCode() and equals() overrides are heavily based on methods in
	 * u.a.d.s.a.fitting.functions.Parameter
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int temp = name.hashCode();
		result = prime * result + (temp ^ (temp >>> 32));
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
		PeakFinderParameter other = (PeakFinderParameter) obj;
		if (name != other.name)
			return false;
		return true;
	}
}
