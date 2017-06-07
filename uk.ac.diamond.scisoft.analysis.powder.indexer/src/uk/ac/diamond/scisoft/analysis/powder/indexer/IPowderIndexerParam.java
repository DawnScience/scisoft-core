package uk.ac.diamond.scisoft.analysis.powder.indexer;

/**
 * The core parameters that are passed to the indexers...
 * 
 * Well What if each indexer had its own interface? then I could just use that as default setters?
 * 
 * @author Dean P. Ottewell
 */
public interface IPowderIndexerParam {
	
	/**
	 * equals() and hashcode() should be overridden since it is important that
	 * there is only one parameter with each name
	 */
	
	/**
	 * Get the name of the parameter
	 * @return String name of parameter
	 */
	public String getName();

	/**
	 * Get value of the parameter
	 * @return Number value of the parameter
	 */
	public Number getValue();

	/**
	 * Set the value of the parameter, assuming is Number value to set. 
	 * @param value New value of the parameter
	 * @throws IllegalArgumentException if value is not correct type (Integer/Double)
	 */
	public void setValue(Number value);
			
	/**
	 * Extract formated parameter 
	 * @return formated string output to fit be passed to appropriate powder indexer
	 * */
	public String formatParam();
	
}
