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
import java.util.LinkedHashMap;

class HKL implements Serializable {
	private LinkedHashMap<String,Integer> hkl;
	
	HKL(int h, int k, int l) {
		super();
		
		hkl = new LinkedHashMap<String, Integer>(3);
		hkl.put("h", h);
		hkl.put("k", k);
		hkl.put("l", l);
	}
	
	public Integer getIndex(String key) {
		return hkl.get(key);
	}
}

public class CalibrationPeak implements Serializable {
	private double peakPos;
	private double twoTheta;
	private double dSpacing;
	private HKL reflection;

	public CalibrationPeak(double peakPos, double tTheta, double dSpacing, int[] reflection) {
		super();
		this.peakPos = peakPos;
		this.twoTheta = tTheta;
		this.dSpacing = dSpacing;
		this.reflection = new HKL(reflection[0], reflection[1], reflection[2]);
	}

	public double getPeakPos() {
		return peakPos;
	}

	public double getTwoTheta() {
		return twoTheta;
	}

	public double getDSpacing() {
		return dSpacing;
	}

	public Integer getIndex(String key) {
		return reflection.getIndex(key);
	}
	
}
