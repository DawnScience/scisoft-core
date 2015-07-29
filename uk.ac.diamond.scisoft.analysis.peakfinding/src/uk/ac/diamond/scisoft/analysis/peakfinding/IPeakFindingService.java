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

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;

public interface IPeakFindingService {
	
	/**
	 * Get the name of a specified PeakFinder
	 * @param id Should be unique string from extension point, e.g. fully 
	 * qualified class name (FQCN)
	 * @return String
	 * @throws IllegalArgumentException
	 */
	public String getPeakFinderName(String id);

	/**
	 * Returns the collection of IDs of all peak finders registered with the service
	 * @return
	 */
	public Collection<String> getRegisteredPeakFinders();
	
	/**
	 * Get the description of the specified IPeakFinder ID
	 * @param id Unique string (e.g. FQCN)
	 * @return String
	 * @throws IllegalArgumentException
	 */
	public String getPeakFinderDescription(String id);
	
	/**
	 * Get the (default) set of parameters associated with given IPeakFinder ID
	 * @param id Unique string (e.g. FQCN)
	 * @return Map<name string of parameter, parameter value>
	 * @throws IllegalArgumentException
	 */
	public Map<String, IPeakFinderParameter> getPeakFinderParameters(String id);
	
	/**
	 * Register all full implementations of IPeakFinders in a package
	 * to the service. This is to avoid using extension points, so useful
	 * for unit tests
	 * @param ClassLoader cl 
	 * @param String package from which to load IPeakFinders
	 * @throws Exception
	 */
	public void addPeakFindersByClass(ClassLoader cl, String pakage) throws ClassNotFoundException,IllegalAccessException,InstantiationException;
	
	/**
	 * Register all all IPeakFinders found by extensions points with the
	 * service.
	 */
	public void addPeakFindersByExtension();
	
	/**
	 * Calls the findPeaks method of each of the active IPeakFinders using the 
	 * data and parameters stored wiht the IPeakFindingData DTO and record
	 * found peaks back in the DTO.
	 * @param peakFindingData Data Transfer Object (DTO) containing the 
	 *        configuration for peak finding run
	 * @throws Exception
	 */
	public void findPeaks(IPeakFindingData peakFindingData) throws Exception;
}
