/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;

import uk.ac.diamond.scisoft.analysis.utils.ClassUtils;

public class PeakFindingServiceImpl implements IPeakFindingService {
	
	private final Map<String, PeakFinderInfo> PEAKFINDERS = new HashMap<String, PeakFinderInfo>();
	
	
	
	public PeakFindingServiceImpl() {
		//Intentionally left blank (OSGi).
	}
	
	/**
	 * Checks whether the PEAKFINDERS is populated and if not tries to fill it.
	 */
	private void checkForPeakFinders() {
		if (!PEAKFINDERS.isEmpty()) return;
		addPeakFindersByExtension();
	}
	
	@Override
	public void addPeakFindersByClass(ClassLoader cl, String pakage) throws Exception {
		final List<Class<?>> clazzes = ClassUtils.getClassesForPackage(cl, pakage);
		for (Class<?> clazz : clazzes) {
			if (Modifier.isAbstract(clazz.getModifiers())) continue;
			if (IPeakFinder.class.isAssignableFrom(clazz)) {
				IPeakFinder pf = (IPeakFinder) clazz.newInstance();
				
				registerPeakFinder(null, pf.getName(), null, pf);
			}
		}
	}
	
	@Override
	public void addPeakFindersByExtension() {
		IConfigurationElement[] elems = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.dawnsci.analysis.api.peakfinder");
		for (IConfigurationElement el: elems) {
			if (el.getName().equals("peakFinder")) {
				final String pfID = el.getAttribute("id");
				final String pfNm = el.getAttribute("name");
				final String pfDesc = el.getAttribute("description");
				IPeakFinder pf = null;
				try {
					pf = (IPeakFinder)el.createExecutableExtension("class");
				} catch (Exception ex) {
					ex.printStackTrace();
					continue;
				}
				
				registerPeakFinder(pfID, pfNm, pfDesc, pf);
			}
		}
	}
	
	private void registerPeakFinder(String pfID, String pfNm, String pfDesc, IPeakFinder pf) {
		//In case we're not working from extension points.
		if (pfID == null) {
			pfID = pf.getClass().getName();
		}
		
		PEAKFINDERS.put(pfID, new PeakFinderInfo(pfNm, pfDesc, pf));
	}
	
	@Override
	public String getPeakFinderName(String id) throws Exception {
		checkForPeakFinders();
		return PEAKFINDERS.get(id).getName();
	}

	@Override
	public Collection<String> getRegisteredPeakFinders() {
		checkForPeakFinders();
		return PEAKFINDERS.keySet();
	}

	@Override
	public Map<String, IPeakFinderParameter> getPeakFinderParameters(String id)
			throws Exception {
		checkForPeakFinders();
		IPeakFinder selectedPeakFinder = PEAKFINDERS.get(id).getPeakFinder();
		return selectedPeakFinder.getParameters();
	}

	@Override
	public String getPeakFinderDescription(String id) throws Exception {
		checkForPeakFinders();
		return PEAKFINDERS.get(id).getDescription();
	}
	
	@Override
	public void findPeaks(IPeakFindingData peakFindingData) throws Exception {
		Set<String> activePeakFinders;
		Map<String, Map<String, IPeakFinderParameter>> peakFinderParameters = new TreeMap<String, Map<String, IPeakFinderParameter>>();
		Map<String, Map<Integer, Double>> allFoundPeaks = new TreeMap<String, Map<Integer, Double>>();
		IDataset[] searchData;
		Integer nPeaks = peakFindingData.getNPeaks();
		
		//Get the data to find peaks in
		if (peakFindingData.hasData()){
			searchData = peakFindingData.getData();
		} else {
			throw new Exception("No data set to find peaks in.");
		}
		
		//Get the peak finders to use and their parameters
		if (peakFindingData.hasActivePeakFinders()) {
			activePeakFinders = (Set<String>)peakFindingData.getActivePeakFinders();
			for (String pfID : activePeakFinders) {
				peakFinderParameters.put(pfID, peakFindingData.getPFParametersByPeakFinder(pfID));
			}
		} else {
			throw new Exception("No peak finders set active");
		}
		
		
		Iterator<String> activePeakFindersIter = activePeakFinders.iterator();
		while (activePeakFindersIter.hasNext()) {
			//Get each active IPeakFinder in turn...
			String currID = activePeakFindersIter.next();
			IPeakFinder currPF = PEAKFINDERS.get(currID).getPeakFinder();
			
			//... set new parameters on PeakFinder ...
			Map<String, IPeakFinderParameter> currPFParams = peakFinderParameters.get(currID);
			for (Map.Entry<String, IPeakFinderParameter> pfParam : currPFParams.entrySet()) {
				//Will only set parameters which are in the map (may be others unset)
				currPF.setParameter(pfParam.getKey(), pfParam.getValue());
			}
			
			//... call the findPeaks method, record the result & reset the peak finder parameters
			allFoundPeaks.put(currID, currPF.findPeaks(searchData[0], searchData[1], nPeaks));
			currPF.resetParameters();
		}
		//TODO Add some process here which averages the results of the findPeaks calls
		
		//Finally set the found peaks on the IPeakFindingData DTO
		peakFindingData.setPeaks(allFoundPeaks);
	}

	private class PeakFinderInfo {
		
		private String name;
		private String description;
		private IPeakFinder peakFinder;
		
		public PeakFinderInfo(String nm, String desc, IPeakFinder pf) {
			this.name = nm;
			this.description = desc;
			this.peakFinder = pf;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public IPeakFinder getPeakFinder() {
			return peakFinder;
		}
	}
}
