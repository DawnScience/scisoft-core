package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;

public interface IPeakFindingData {

	/**
	 * Adds IPeakFinder, specified by unique ID, to the active IPeakFinders 
	 * collection in this instance
	 * @param id Unique string (e.g. Fully Qualified Class Name - FQCN)
	 * @throws Exception in case peak finder already active
	 */
	public void activatePeakFinder(String id) throws Exception;

	/**
	 * Removes IPeakFinder, specified by unique ID, from active IPeakFinders
	 * collection in this instance
	 * @param id unique string (e.g. FQCN)
	 * @throws Exception in case the peak finder is not already active
	 */
	public void deactivatePeakFinder(String id) throws Exception;
	
	/**
	 * Returns a collection containing the IPeakFinders which are set active 
	 * in this instance
	 * @return 
	 */
	public Collection<String> getActivePeakFinders();
	
	/**
	 * Reports whether there are active peakFinders
	 */
	public boolean hasActivePeakFinders();
	
	/**
	 * Sets all the parameters of a specified peak finder using a supplied map 
	 * of parameter names and values. Method checks that parameter value types 
	 * are consistent with their expected types.
	 * @param pfID String ID (FQCN) of peak finder
	 * @param pfParameters Map of parameter name keys and new parameter values
	 * @throws Exception If Number type of any of the parameters is not 
	 *         consistent with the expected type; change will not be accepted.
	 *         Alternatively if the peak finder has never been marked active
	 */
	public void setPFParametersByPeakFinder(String pfID, 
			Map<String, IPeakFinderParameter> pfParameters) throws Exception;
	
	/**
	 * Sets the value of a specified parameter in a named peak finder to a  
	 * given value. Method checks parameter value type is consistent with the 
	 * expected type
	 * @param pfID String ID (FQCN) of peak finder
	 * @param paramName Name of peak finder parameter
	 * @param paramValue New value of parameter
	 * @throws Exception If Number type of the parameter is not consistent 
	 *         with the expected type; change will not be accepted. 
	 *         Alternatively if the peak finder has never been marked active or
	 *         the parameter name does not exist.
	 */
	public void setPFParameterByName(String pfID, String paramName, 
			Number paramValue) throws Exception;
	
	/**
	 * Returns a map containing IDs of all peak finders which have been 
	 * activated in the lifetime of this instance and maps of all their 
	 * parameters with associated values. 
	 * @return Map<String peak finder IDs, Map<parameter name, parameter>>
	 * @throws If no peak finders have ever been made active
	 */
	public Map<String, Map<String, IPeakFinderParameter>> getAllPFParameters() 
			throws Exception;
	
	/**
	 * Returns a map containing the names and values of the parameters of this 
	 * peak finder.
	 * @param pfID String ID (FQCN) pf peak finder
	 * @return Map<parameter names, parameter>
	 * @throws Exception If peak finder pfID has never been marked active
	 */
	public Map<String, IPeakFinderParameter> getPFParametersByPeakFinder(String pfID) 
			throws Exception;
	
	/**
	 * Returns the value of a named parameter from a specified peak finder.
	 * @param pfID String ID (FQCN) of peak finder
	 * @param paramName String name of the parameter
	 * @return PeakFinderParameter containing value, isInt logic and name
	 * @throws Exception If peak finder pfID has never been marked active or if
	 *         parameter name does not exist
	 */
	public IPeakFinderParameter getPFParameterByName(String pfID, String paramName) 
			throws Exception;
	
	/**
	 * Returns set of all the string names of the parameters associated with 
	 * this peak finder
	 * @param pfID String ID (FQCN) of peak finder
	 * @return Set containing parameter names
	 * @throws Exception If peak finder pfID has never been marked active
	 */
	public Set<String> getPFParameterNamesByPeakFinder(String pfID) 
			throws Exception;
	
	/**
	 * Set all the data on this IPeakFindingData object which might change 
	 * between findPeaks calls
	 * @param xData
	 * @param yData
	 * @param nPeaks maximum number of peaks
	 */
	public void setData(IDataset xData, IDataset yData, Integer nPeaks);
	
	/**
	 * See {@link #setData(IDataset, IDataset, Integer)} method
	 * @param xData
	 * @param yData
	 */
	public void setData(IDataset xData, IDataset yData);
	
	/**
	 * Set the input x-axis data in which to find peaks
	 * @param xData
	 */
	public void setXData(IDataset xData);
	/**
	 * Set the input y-axis data in which to find peaks
	 * @param yData
	 */
	public void setYData(IDataset yData);
	
	/**
	 * Get the current data of this IPeakFindingData object (not nPeaks)
	 * @throws Exception when no data has been set in this object
	 */
	public IDataset[] getData() throws Exception;
	
	/**
	 * Reports whether data has been set in this instance.
	 */
	public boolean hasData();
	
	/**
	 * Set the maximum number of peaks which will be found when this instance 
	 * is submitted to findPeaks
	 * @param nPeaks
	 */
	public void setNPeaks(Integer nPeaks);
	
	/**
	 * Get the maximum number of peaks which will be found
	 */
	public Integer getNPeaks();
	
	/**
	 * Sets the peaks which were found in the data and using the settings of this object,
	 * by the IPeakFindingService and stores them 
	 * @param newFoundPeaks
	 */
	public void setPeaks(Map<String, Map<Integer, Double>> newFoundPeaks);
	
	/**
	 * Returns a map with the unique IPeakFinder ID as the key and values which 
	 * are the results of the IPeakFinder findPeaks() method.
	 * @return Map of IPeakFinderIDs and IPeakFinder found peaks
	 * @throws Exception in case no IPeakFinder result is found
	 */
	public Map<String, Map<Integer, Double>> getPeaks() throws Exception;

	/**
	 * Returns a map with key peak position and value significance for the 
	 * IPeakFinder specified by the unique ID.
	 * @param id Unique IPeakFinder ID (e.g. FQCN)
	 * @return Map of IPeakFinderIDs and IPeakFinder found peaks
	 * @throws Exception in case no peakfinder result is found
	 */
	public Map<Integer, Double> getPeaks(String id) throws Exception;
}
