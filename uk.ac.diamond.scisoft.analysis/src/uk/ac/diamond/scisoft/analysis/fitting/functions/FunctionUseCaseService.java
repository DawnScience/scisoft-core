/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class FunctionUseCaseService {
	
	
	private static final Map<String, String> VALIDUSECASES = new HashMap<String, String>();
	private static final Map<String, Set<String>> FUNCTIONUCIDS = new HashMap<String, Set<String>>();

	
	/**
	 * Register a valid use case with a particular UCID and a friendly name.
	 * @param ucid
	 * @param name
	 */
	public static void registerUseCase(String ucid, String name) {
		VALIDUSECASES.put(ucid, name);
	}
	
	/**
	 * Register the possible use cases available to a function, checking they are
	 * valid. 
	 * @param functionName
	 * @param functionUCIDs
	 * @throws Exception 
	 */
	public static void setFunctionUseCases(String functionName, Set<String> functionUCIDs) throws Exception {
		Set<String> checkedUCIDs = new HashSet<String>();
		for (String ucid : functionUCIDs) {
			if (!VALIDUSECASES.containsKey(ucid)) {
				//TODO There might be some better way to handle this rather than just throwing an exception (e.g. warning & continue?) 
				throw new Exception("There is no use case registered with the name "+ucid+". Check the extension point.");
			}
			checkedUCIDs.add(ucid);
		}
		FUNCTIONUCIDS.put(functionName, checkedUCIDs);
	}
	
	/**
	 * Get all of the use cases available to a given function
	 * @param functionName
	 */
	public static Set<String> getFunctionUseCases(String functionName) {
		return FUNCTIONUCIDS.get(functionName);
	}
	
	/**
	 * Return true when a function has the specified use case id
	 * @param functionName
	 * @param ucid
	 * @return boolean
	 */
	public static boolean functionHasUseCase(String functionName, String ucid) {
		return FUNCTIONUCIDS.get(functionName).contains(ucid);
	}
	
	//TODO Provide a getFunctionUseCasesFromClass method

}
