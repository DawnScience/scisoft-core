package uk.ac.diamond.scisoft.analysis.powder.indexer;

import java.util.Collection;
import java.util.Map;


//TODO: service setup for indexer path?

/**
 * @author Dean P. Ottewell
 *
 */
public interface IPowderIndexerService {
	
	/**
	 * Get the name of a specified PeakFinder
	 * @param id Should be unique string from extension point, e.g. fully 
	 * qualified class name (FQCN)
	 * @return String
	 * @throws IllegalArgumentException
	 */
	public String getIndexerName(String id);

	/**
	 * Returns the collection of IDs of all peak finders registered with the TODO: or THis service? service
	 * @return
	 */
	public Collection<String> getRegisteredIndexers();
	

	/**
	 * Get the (default) set of parameters associated with given IPeakFinder ID
	 * @param id Unique string (e.g. FQCN)
	 * @return Map<name string of parameter, parameter value>
	 * @throws IllegalArgumentException
	 */
	public Map<String, IPowderIndexerParam> getIndexerParameters(String id);
	
	/**
	 * Register all full implementations of IPeakFinders in a package
	 * to the service. This is to avoid using extension points, so useful
	 * for unit tests
	 * @param ClassLoader cl 
	 * @param String package from which to load IPeakFinders
	 * @throws Exception
	 */
	public void addIndexersByClass(ClassLoader cl, String pakage) throws ClassNotFoundException,IllegalAccessException,InstantiationException;
	
	/**
	 * Register all all IPeakFinders found by extensions points with the
	 * service.
	 */
	public void addPowderIndexersByExtension();
	
}
