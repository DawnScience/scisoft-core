/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Collection;
import java.util.Map;


public interface IPeakFindingService {
	
	/**
	 * Get the name of a specified PeakFinder
	 * @param String id Should be unique string from extension point, e.g. fully 
	 * qualified class name (FQCN)
	 * @return String
	 * @throws Exception
	 */
	public String getPeakFinderName(String id) throws Exception;

	/**
	 * Returns the collection of IDs of all peak finders registered with the service
	 * @return
	 */
	public Collection<String> getRegisteredPeakFinders() throws Exception;
	
	/**
	 * Get the description of the specified IPeakFinder
	 * @param String id Should be unique string (e.g. FQCN)
	 * @return String
	 * @throws Exception
	 */
	public String getPeakFinderDescription(String id) throws Exception;
	
	/**
	 * Register all full implementations of IPeakFinders in a package
	 * to the service. This is to avoid using extension points, so useful
	 * for unit tests
	 * @param ClassLoader cl 
	 * @param String package from which to load IPeakFinders
	 */
	public void addPeakFindersByClass(ClassLoader cl, String pakage) throws Exception;
	
	/**
	 * Register all all IPeakFinders found by extensions points with the
	 * service.
	 */
	public void addPeakFindersByExtension();
	
	/**
	 * Adds IPeakFinder, specified by unique ID, to the active IPeakFinders 
	 * collection
	 * @param String Unique string (e.g. FQCN)
	 * @throws Exception in case peak finder already active
	 */
	public void activatePeakFinder(String id) throws Exception;
	
	/**
	 * Removes IPeakFinder, specified by unique ID, from active IPeakFinders
	 * collection
	 * @param String unique string (e.g. FQCN)
	 * @throws Exception in case the peak finder is not already active
	 */
	public void deactivatePeakFinder(String id) throws Exception;
	
	/**
	 * Returns a collection containing the IPeakFinders which are set active
	 * @return 
	 */
	public Collection<String> getActivePeakFinders();
	
	/**
	 * Calls the findPeaks method of each of the active IPeakFinders and record
	 * found peaks to internal store.
	 */
	public void findPeaks();
	
	/**
	 * Returns a map with the unique IPeakFinder ID as the key and values which 
	 * are the results of the IPeakFinder findPeaks() method. Specifying a 
	 * string ID will return only the peaks found by one IPeakFinder.
	 * @param String Unique IPeakFinder ID (e.g. FQCN)
	 * @return Map of IPeakFinderIDs and IPeakFinder found peaks
	 * @throws Exception in case no peakfinder result is found
	 */
	public Map<String, Map<Integer, Double>> getPeaks() throws Exception;
	
	public Map<Integer, Double> getPeaks(String id) throws Exception;
}
