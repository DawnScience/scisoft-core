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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;
import org.eclipse.dawnsci.analysis.api.peakfinding.PeakFinderInfo;

public class PeakFindingServiceImpl implements IPeakFindingService {
	
	private Map<String, PeakFinderInfo> peakFinders;

	@Override
	public String getName(String id) throws Exception {
		return peakFinders.get(id).getName();
	}

	@Override
	public String getDescription(String id) throws Exception {
		return peakFinders.get(id).getDescription();
	}

	@Override
	public Collection<String> getRegisteredPeakFinderNames() {
		
		return null;
	}
	
	private void registerPeakfinders() {
		peakFinders = new HashMap<String, PeakFinderInfo>();
		
		IConfigurationElement[] elems = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.dawnsci.analysis.api.peakfinder");
		for (IConfigurationElement el : elems) {
			final String id = el.getAttribute("id");
			final String name = el.getAttribute("name");
			final String desc = el.getAttribute("description");
			
			IPeakFinder pf = null;
			try {
				pf = (IPeakFinder)el.createExecutableExtension("class");
			} catch (Exception ex) {
				ex.printStackTrace();
				continue;
			}
			peakFinders.put(id, new PeakFinderInfo(name, desc, pf));
		}
	}
}