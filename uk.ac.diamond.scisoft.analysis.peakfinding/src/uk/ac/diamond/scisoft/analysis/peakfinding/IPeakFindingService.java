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


public interface IPeakFindingService {
	
	/**
	 * Get the name of a specified PeakFinder
	 * @param id Should be unique string from extension point, e.g. fully qualified class name
	 * @return String
	 * @throws Exception
	 */
	public String getPeakFinderName(String id) throws Exception;
	
	/**
	 * Get the collection of IDs of all peak finders registered with the service
	 * @return
	 */
	public Collection<String> getRegisteredPeakFinders() throws Exception;
	
	/**
	 * Get the description of the specified PeakFinder
	 * @param id Should be unique string from extension point, e.g. fully qualified class name
	 * @return String
	 * @throws Exception
	 */
	public String getPeakFinderDescription(String id) throws Exception;
	
	/**
	 * Register all full implementations of IPeakFinders in a package
	 * to the service. This is to avoid using extension points, so useful
	 * for unit tests
	 * @param cl 
	 * @param pakage
	 */
	public void addPeakFindersByClass(ClassLoader cl, String pakage) throws Exception;
	
	/**
	 * Register all all peakfinders found by extensions points with
	 * the service.
	 */
	public void addPeakFindersByExtension();

}
