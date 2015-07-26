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
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeakFindingDataFactory {

	private static final Logger logger = LoggerFactory.getLogger(PeakFindingDataFactory.class);
	
	/**
	 * Create a peak finding data object which can be passed to the findPeaks method of the peak finding service.
	 * @param peakFindServ Service to which the peak finding data will be run on
	 * @param activeFinders Peak finders to use
	 * @param nPeaks Number of peaks which are sought
	 * @param xData X axis data
	 * @param yData Y axis data
	 * @return
	 */
	public static IPeakFindingData createPeakFindingData(IPeakFindingService peakFindServ,
			Collection<String> activeFinders, Integer nPeaks, IDataset xData, IDataset yData) {
		
		IPeakFindingData pfDTO = new PeakFindingData(peakFindServ);
		
		//Activate the peak finders requested
		for (String pfID : activeFinders) {
			try {
				pfDTO.activatePeakFinder(pfID);
			} catch (IllegalArgumentException ex){
				logger.warn("Peak finder "+pfID+"already set active. Ignoring.");
				continue;
			}
		}
		
		//Set the data which will be searched for peaks and the number of peaks
		pfDTO.setData(xData, yData, nPeaks);
		
		return pfDTO;
	}
	
	/**
	 * Create a set of peak finding parameters for a specific peak finder. 
	 * Queries the given peak finding service for the template parameter set of
	 * the given peak finder.
	 * @param pfServ IPeakFindingService to query
	 * @param pfID PeakFinder ID (FQCN) for which parameter set is being 
	 *        created
	 * @param pValues Map containing name Strings (keys) and Number values of 
	 *        the parameters
	 * @return Map<String names of parameters, IPeakFinderParameter objects>
	 * @throws Exception if the pfID is not found in those registered with the
	 *         service
	 */
	public static Map<String, IPeakFinderParameter> createParameterSet(IPeakFindingService pfServ, String pfID, Map<String, Number> pValues) 
			throws Exception {
		Map<String, IPeakFinderParameter> paramSet = new TreeMap<String, IPeakFinderParameter>(pfServ.getPeakFinderParameters(pfID));
		
		return createParameterSet(paramSet, pValues);
	}
	
	/**
	 * Create a set of peak finding parameters for a specific peak finder. Uses
	 * the given PeakFindingData object to determine the service to query for 
	 * the template parameter set. N.B. This expects an instance of 
	 * PeakFindingData not IPeakFindingData, since getPFService is not part of 
	 * the API. 
	 * @param pfDTO PeakFindingData object to use to determine the service
	 * @param pfID PeakFinder ID (FQCN) for which parameter set is being 
	 *        created
	 * @param pValues Map containing name Strings (keys) and Number values of 
	 *        the parameters  
	 * @return Map<String names of parameters, IPeakFinderParameter objects>
	 * @throws Exception if the pfID is not found in those registered with the
	 *         service
	 */
	public static Map<String, IPeakFinderParameter> createParameterSet(PeakFindingData pfDTO, String pfID, Map<String, Number> pValues) 
			throws Exception {
		
		IPeakFindingService pfServ = pfDTO.getPFService();
		return createParameterSet(pfServ, pfID, pValues);
	}
	
	/**
	 * From a given template parameter set and a map of string names and numbers, sets new values
	 * @param paramSet
	 * @param pValues
	 * @return
	 * @throws Exception
	 */
	private static Map<String, IPeakFinderParameter> createParameterSet(Map<String, IPeakFinderParameter> paramSet, Map<String, Number> pValues) throws Exception {
		for (Map.Entry<String, Number> param : pValues.entrySet()) {
			String pName = param.getKey();
			Number pValue = param.getValue();
			
			paramSet.get(pName).setValue(pValue);
		}
		return paramSet;
	}
}
