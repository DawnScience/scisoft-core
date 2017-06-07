package uk.ac.diamond.scisoft.analysis.powder.indexer;


//TODO: change all parameters to be universal


/**
 * @author Dean P. Ottewell
 *
 */
public class PowderIndexerParam implements IPowderIndexerParam {

	private final String name;
	private Number value;	
	
	public PowderIndexerParam(String name, Number value) {
		this.name = name;
		setValue(value);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int temp = name.hashCode();
		result = prime * result + (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Number getValue() {
		return value;
	}

	@Override
	public void setValue(Number value) {
		//TODO: validate?
		this.value = value;
	}
	
	/*
	 * TODO: probably the most important method for the comms
	 * */
	@Override
	public String toString() {
		return name+" = "+String.valueOf(value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowderIndexerParam other = (PowderIndexerParam) obj;
		if (name != other.name)
			return false;
		return true;
	}

	@Override
	public String formatParam() {
		// TODO Auto-generated method stub
		return null;
	}

}
