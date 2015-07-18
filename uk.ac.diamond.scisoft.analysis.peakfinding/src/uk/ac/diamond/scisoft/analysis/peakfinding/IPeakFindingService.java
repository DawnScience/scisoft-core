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

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;


public interface IPeakFindingService {
	
	/**
	 * Get the name of a specified PeakFinder
	 * @param id Should be unique string from extension point, e.g. fully 
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
	 * @param id Unique string (e.g. FQCN)
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
	 * Calls the findPeaks method of each of the active IPeakFinders and record
	 * found peaks to internal store.
	 */
	public Map<String, Map<Integer, Double>> findPeaks(IPeakFindingData peakFindingData) throws Exception;
}
