/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;

import uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders.PeakFinderParameter;

public class PeakFindingDataFactory {
	
	/**
	 * Create a set of peak finding parameters from a set of names, booleans 
	 * and values. 
	 * @param pNames String name of the parameter
	 * @param pIsInts Whether the parameter is an integer
	 * @param pValues A numerical value for the parameter
	 * @return Map containing the set of parameters
	 * @throws Exception If the number of names/isInts/values supplied is 
	 *         different
	 */
	public static Map<String, IPeakFinderParameter> createParameterSet(String[] pNames, Boolean[] pIsInts, Number[] pValues) 
			throws Exception {
		Map<String, IPeakFinderParameter> outputPSet = new TreeMap<String, IPeakFinderParameter>();
		
		int i = 0, nParams = pNames.length;
		if ((pIsInts.length != nParams) || (pValues.length != nParams)) throw new Exception("Lengths of supplied isInts ("+pIsInts.length+") or values ("+pValues.length+") differs from expected ("+nParams+")");
		
		while (i < nParams) {
			outputPSet.put(pNames[i], new PeakFinderParameter(pNames[i], pIsInts[i], pValues[i]));
			i++;
		}
		
		return outputPSet;
	}

}
