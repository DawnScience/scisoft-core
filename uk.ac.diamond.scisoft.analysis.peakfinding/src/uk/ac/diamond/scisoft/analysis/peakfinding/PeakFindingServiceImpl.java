/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;

import uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders.IPeakFinder;

public class PeakFindingServiceImpl implements IPeakFindingService {

	private static final Map<String, Class<? extends IPeakFinder>> PEAKFINDERS;
	private static final List<String> PEAKFINDERNAMES;
	static {
		PEAKFINDERS = new HashMap<String, Class<? extends IPeakFinder>>();
		PEAKFINDERNAMES = new ArrayList<String>();
	}

	@Override
	public String getName(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private void registerPeakFinders() {
		//We only want to run this once 
		if (PEAKFINDERS != null) return;
		
		IConfigurationElement[] elems;
		
		
		
//		PEAKFINDERNAMES.addAll(PEAKFINDERS.keySet());
	}

	public static List<String> getPeakFinders() {
		return PEAKFINDERNAMES;
	}

}
