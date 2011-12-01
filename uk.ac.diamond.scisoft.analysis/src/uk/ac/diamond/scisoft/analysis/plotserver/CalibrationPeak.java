/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
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