/*
 * Copyright (c) 2012 European Synchrotron Radiation Facility,
 *                    Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */ 
package uk.ac.diamond.scisoft.analysis.crystallography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds HKL data for a calibrant
 */
public class CalibrantSpacing implements Serializable{

	private String    name;
	private List<HKL> hkls;
	
	public CalibrantSpacing() {
		this(null);
	}
	
	public CalibrantSpacing(String name) {
		this.name = name;
		hkls = new ArrayList<HKL>();
	}

	public void clear() {
		if (hkls!=null) hkls.clear();
	}

	public void setHKLs(List<HKL> calibrationData) {
		this.hkls = calibrationData;
	}
	
	public List<HKL> getHKLs() {
		return hkls;
	}

	
	public void addHKL(HKL ring) {
		if (hkls==null) hkls =  new ArrayList<HKL>(7);
		hkls.add(ring);
		if (ring.getRingName()==null) ring.setRingName("Position "+hkls.size());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hkls == null) ? 0 : hkls.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalibrantSpacing other = (CalibrantSpacing) obj;
		if (hkls == null) {
			if (other.hkls != null)
				return false;
		} else if (!hkls.equals(other.hkls))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
