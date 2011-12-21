/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;

class CalibrationResultsData implements Serializable {
	private AFunction fuction;
	private ArrayList<CalibrationPeak> peakList;
	private double meanCameraLength;
	
	CalibrationResultsData(AFunction calibrationFunction, List<CalibrationPeak> peaks, double meanCameraLength) {
		super();
		this.fuction= calibrationFunction;
		this.peakList = new ArrayList<CalibrationPeak>();
		for(CalibrationPeak p : peaks)
			peakList.add(p);
		this.meanCameraLength = meanCameraLength;
	}
	
	public AFunction getFuction() {
		return fuction;
	}

	public ArrayList<CalibrationPeak> getPeakList() {
		return peakList;
	}

	public double getMeanCameraLength() {
		return meanCameraLength;
	}
}

public class CalibrationResultsBean implements Serializable {
	
	private HashMap<String, CalibrationResultsData> results;

	public CalibrationResultsBean() {
		results = new HashMap<String, CalibrationResultsData>();
	}
	
	public CalibrationResultsBean(String experiment, AFunction calibrationFunction, List<CalibrationPeak> peaks, double meanCameraLength) {
		
		results = new HashMap<String, CalibrationResultsData>();
		CalibrationResultsData newData = new CalibrationResultsData(calibrationFunction, peaks, meanCameraLength); 
		results.put(experiment, newData);
	}
	
	public void putCalibrationResult(String experiment, AFunction calibrationFunction, List<CalibrationPeak> peaks, double meanCameraLength) {
		CalibrationResultsData newData = new CalibrationResultsData(calibrationFunction, peaks, meanCameraLength); 
		results.put(experiment, newData);
	}
	
	public AFunction getFuction(String experiment) {
		return results.get(experiment).getFuction();
	}

	public ArrayList<CalibrationPeak> getPeakList(String experiment) {
		return results.get(experiment).getPeakList();
	}

	public double getMeanCameraLength(String experiment) {
		return results.get(experiment).getMeanCameraLength();
	}
	
	public boolean containsKey(String experiment) {
		return results.containsKey(experiment);
	}
	
	public void clearData(String experiment) {
		results.remove(experiment);
	}
	
	public Set<String> keySet() {
		return results.keySet();
	}
	
	public void clearAllData() {
		results.clear();
	}
	
}
