/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import java.io.Serializable;
import java.util.Arrays;

import javax.measure.quantity.Length;

import org.jscience.physics.amount.Amount;

/**
 * This class is a bean to hold d-spacing and, optionally, integer Miller indices (h,k,l).
 * 
 * TODO refactor to DSpacing class (but keep as bean) or wrap/delegate to that
 * 
 * It is not (currently) a class for using HKLs but rather for storing d-spacings with an optional
 * labelling by HKLs.
 * 
 * It does not offer calculation between the values; it just holds them.
 * 
 * Often if d is set, h,k and l are not needed but it is up to the 
 * user of the data to decide what happens with it.
 */
public class HKL implements Serializable, Cloneable {
	
	private int[] hkl;
	private String ringName;
	
	private Amount<Length> d;

	public HKL() {
		hkl = new int[3];
		setDNano(0);
	}

	public HKL(Amount<Length> d) {
		this(-1,-1,-1,d);
	}
	
	@Override
	public HKL clone() {
		HKL ret = new HKL(getH(), getK(), getL(), d);
		return ret;
	}

	/**
	 * Used for calibration standards
	 * @param h
	 * @param k
	 * @param l
	 * @param d
	 */
	public HKL(int h, int k, int l, Amount<Length> d) {
		this.hkl = new int[]{h,k,l};
		this.d   = d!=null ? d.copy() : null;
	}

	public int getH() {
		return hkl[0];
	}
	
	public int getK() {
		return hkl[1];
	}
	
	public int getL() {
		return hkl[2];
	}
	
	public void setH(int h) {
		hkl[0]=h;
	}
	
	public void setK(int k) {
		hkl[1]=k;
	}
	
	public void setL(int l) {
		hkl[2]=l;
	}
	
	public int[] getIndices() {
		return Arrays.copyOf(hkl, hkl.length);
	}
	
	public int getMaxIndex() {
		return Math.max(Math.max(getH(), getK()), getL());
	}
	
	public int getMinIndex() {
		return Math.min(Math.min(getH(), getK()), getL());
	}
	
	@Override
	public String toString() {
		String str = String.format("(%d, %d, %d)", getH(), getK(), getL());
		return str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((d == null) ? 0 : d.hashCode());
		result = prime * result + Arrays.hashCode(hkl);
		result = prime * result + ((ringName == null) ? 0 : ringName.hashCode());
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
		HKL other = (HKL) obj;
		if (d == null) {
			if (other.d != null)
				return false;
		} else if (!d.equals(other.d))
			return false;
		if (!Arrays.equals(hkl, other.hkl))
			return false;
		if (ringName == null) {
			if (other.ringName != null)
				return false;
		} else if (!ringName.equals(other.ringName))
			return false;
		return true;
	}

	public String getRingName() {
		return ringName;
	}

	public void setRingName(String name) {
		this.ringName = name;
	}

	public Amount<Length> getD() {
		return d!=null ? d.copy() : null;
	}

	public void setD(Amount<Length> d) {
		this.d = d!=null ? d.copy() : null;
	}
	
	/**
	 * d in nanometres
	 * @return d in nanometres
	 */
	public double getDNano() {
		if (d==null) return Double.NaN;
		return d.doubleValue(CalibrationStandards.NANOMETRE);
	}

	/**
	 * @param d in nanometres
	 */
	public void setDNano(double d) {
		this.d = Amount.valueOf(d, CalibrationStandards.NANOMETRE);
	}

}
