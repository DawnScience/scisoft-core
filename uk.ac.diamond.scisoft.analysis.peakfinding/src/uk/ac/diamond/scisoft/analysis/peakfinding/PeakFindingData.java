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
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;

public class PeakFindingData implements IPeakFindingData {
	
	/*
	 * These variables determine how the PeakFindingData object interacts with
	 * the PeakFindingService
	 */
	private final IPeakFindingService peakFindServ;
	private Set<String> activePeakFinders = new TreeSet<String>();
	private Map<String, Map<String, IPeakFinderParameter>> pfParamsStore = new TreeMap<String, Map<String, IPeakFinderParameter>>();
	private Integer nPeaks;	
	private IDataset[] searchData = new IDataset[2];
	
	/*
	 * These variables are populated only after peak finding has been done
	 */
	private Map<String, Map<Integer, Double>> allIdentifiedPeakPosns = new TreeMap<String, Map<Integer, Double>>();
	
	
	public PeakFindingData(IPeakFindingService serv) {
		this.peakFindServ = serv;
	}
	
	/**
	 * Returns a reference to the peak finding service which this 
	 * IPeakFindingData object is associated with
	 * @return
	 */
	public IPeakFindingService getPFService() {
		return peakFindServ;
	}
	
	@Override
	public void activatePeakFinder(String id) {
		if (!peakFindServ.getRegisteredPeakFinders().contains(id)) {
			throw new NullPointerException(id+" not registered with peak finding service");
		}
		else if (activePeakFinders.contains(id)) {
			throw new IllegalArgumentException(id+" already set active");
		} else {
			activePeakFinders.add(id);
			
			//Add the peak finder parameters to the store iff it's not there already
			if (!pfParamsStore.containsKey(id)) {
				pfParamsStore.put(id, peakFindServ.getPeakFinderParameters(id));
			}
		}
	}

	@Override
	public void deactivatePeakFinder(String id) {
		if (activePeakFinders.contains(id)) {
			activePeakFinders.remove(id);
		} else {
			throw new IllegalArgumentException(id+" not set active");
		}
	}

	@Override
	public Collection<String> getActivePeakFinders() {
		return activePeakFinders;
	}

	@Override
	public boolean hasActivePeakFinders() {
		return !activePeakFinders.isEmpty();
	}

	@Override
	public void setPFParametersByPeakFinder(String pfID,
			Map<String, IPeakFinderParameter> newPFParams) {
		//Get Peak Finder parameter set		
		Map<String, IPeakFinderParameter> currPFParams = getPFParametersByPeakFinder(pfID);
		
		for (Map.Entry<String, IPeakFinderParameter> pfParam : newPFParams.entrySet()) {
			//Check parameter names in new param set match
			checkParamNameMatch(pfParam.getKey(), newPFParams);
			
			//Set values
			setParameterInParamSet(pfParam.getKey(), pfParam.getValue().getValue(), currPFParams);
		}
		//Put parameter set back to store
		pfParamsStore.put(pfID, currPFParams);
		
	}

	@Override
	public void setPFParameterByName(String pfID, String paramName,
			Number paramValue) {
		//Get Peak Finder parameter set
		Map<String, IPeakFinderParameter> currPFParams = getPFParametersByPeakFinder(pfID);
		
		//Set parameter & put set back to store
		setParameterInParamSet(paramName, paramValue, currPFParams);
		pfParamsStore.put(pfID, currPFParams);

	}
	
	private void setParameterInParamSet(String pName, 
			Number pNewValue, Map<String, IPeakFinderParameter> pfParams) {
		//Check the parameter is already in the keyset
		checkParamNameMatch(pName, pfParams);
		
		//Set the parameter value
		pfParams.get(pName).setValue(pNewValue);
	}

	@Override
	public Map<String, Map<String, IPeakFinderParameter>> getAllPFParameters() {
		return pfParamsStore;
	}

	@Override
	public Map<String, IPeakFinderParameter> getPFParametersByPeakFinder(String pfID) {
		checkPFParamsStored(pfID);
		return pfParamsStore.get(pfID);
	}

	@Override
	public IPeakFinderParameter getPFParameterByName(String pfID, String paramName) {
		Map<String, IPeakFinderParameter> pfParams = getPFParametersByPeakFinder(pfID);
		checkParamInPFSet(paramName, pfParams);
		return pfParams.get(paramName);
	}
	
	@Override
	public Number getPFParameterValueByName(String pfID, String paramName) {
		IPeakFinderParameter pfParam = getPFParameterByName(pfID, paramName);
		return pfParam.getValue();
	}

	@Override
	public Boolean getPFParameterIsIntByName(String pfID, String paramName) {
		IPeakFinderParameter pfParam = getPFParameterByName(pfID, paramName);
		return pfParam.isInt();
	}
		
	@Override
	public Set<String> getPFParameterNamesByPeakFinder(String pfID) {
		checkPFParamsStored(pfID);
		Map<String, IPeakFinderParameter> pfParams = pfParamsStore.get(pfID);
		for (Map.Entry<String, IPeakFinderParameter> entry  : pfParams.entrySet()) {
			checkParamNameMatch(entry.getKey(), pfParams);
		}
		return pfParams.keySet();
		
	}

	/**
	 * Checks whether the specified peak finder ID is in the set
	 * @param pfID ID (FQCN) string of peak finder
	 * @param keyset Set (keyset) which should contain this pfID
	 * @throws NullPointerException When peak finder is not in 
	 *         the keyset; i.e. never activated
	 */
	private void checkPFParamsStored(String pfID) {
		if (pfParamsStore.containsKey(pfID)) return;
		throw new NullPointerException("Peak finder "+pfID+" has never been activated");
	}
	
	/**
	 * Checks whether the specified parameter name is in the set
	 * @param paramName String name of parameter
	 * @param paramset Map which should contain this name
	 * @throws NullPointerException When peak finder is not in 
	 *         the keyset; i.e. never activated
	 */	
	private void checkParamInPFSet(String paramName, Map<String, IPeakFinderParameter> paramSet) {
		if (paramSet.containsKey(paramName)) {
			checkParamNameMatch(paramName, paramSet);
			return;
		} 
		throw new NullPointerException("No parameter name "+paramName+" found");
	}
	
	/**
	 * Check that the parameter name in the keyset of the map is the same as 
	 * the name given in the parameter object 
	 * @param paramName paramName String name of parameter
	 * @param paramset Map which should contain this name
	 * @throws IllegalArgumentException If the names do not match
	 */
	private void checkParamNameMatch(String paramName, Map<String, IPeakFinderParameter> paramSet) {
		if (paramSet.get(paramName).getName().equals(paramName)) return;
		throw new IllegalArgumentException("Peak finder parameter name mismatch; expecting: "+paramName+" was: "+paramSet.get(paramName).getName());
	}

	@Override
	public void setData(IDataset xData, IDataset yData, Integer nPeaks) {
		this.searchData[0] = xData;
		this.searchData[1] = yData;
		this.nPeaks = nPeaks;
	}

	@Override
	public void setData(IDataset xData, IDataset yData) {
		setData(xData, yData, null);
	}

	@Override
	public void setXData(IDataset xData) {
		setData(xData, null, null);
	}

	@Override
	public void setYData(IDataset yData) {
		setData(null, yData, null);
	}

	@Override
	public IDataset[] getData() {
		return searchData;
	}

	@Override
	public boolean hasData() {
		if ((searchData[0] == null) ||  (searchData[1] == null) || 
				(searchData[0].getSize() == 0) || (searchData[1].getSize() == 0)) {
			return false;
		}
		return true;
	}

	@Override
	public void setNPeaks(Integer nPeaks) {
		setData(null, null, nPeaks);
	}

	@Override
	public Integer getNPeaks() {
		return nPeaks;
	}

	@Override
	public void setPeaks(Map<String, Map<Integer, Double>> newFoundPeaks) {
		this.allIdentifiedPeakPosns = newFoundPeaks;
	}
	
	@Override
	public Map<String, Map<Integer, Double>> getPeaks() {
		if (allIdentifiedPeakPosns == null || allIdentifiedPeakPosns.isEmpty()) throw new NullPointerException("No peaks found. Need to run findPeaks()");
		return allIdentifiedPeakPosns;
	}

	@Override
	public Map<Integer, Double> getPeaks(String id) {
		if (allIdentifiedPeakPosns == null || allIdentifiedPeakPosns.isEmpty()) throw new NullPointerException("No peaks found. Need to run findPeaks(...)");
		if (!allIdentifiedPeakPosns.keySet().contains(id)) throw new IllegalArgumentException(id+" was not active when findPeaks() was called");
		return allIdentifiedPeakPosns.get(id);
	}

}
