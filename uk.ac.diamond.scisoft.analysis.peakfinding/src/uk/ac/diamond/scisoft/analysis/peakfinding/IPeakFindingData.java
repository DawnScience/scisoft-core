package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Collection;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;

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
