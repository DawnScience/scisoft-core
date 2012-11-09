/*-
 * Copyright 2012 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.crystallography;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Changed to have getter and setter methods and no argument constructor
 * so that will function as a bean (e.g. will serialise to XML if needed).
 */
public class HKL implements Serializable {
	
	private int[] hkl;
	
	public HKL() {
		hkl = new int[3];
	}
	
	public HKL(int h, int k, int l) {
		super();
		
		hkl = new int[] {h, k, l};
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
		return hkl;
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
		result = prime * result + Arrays.hashCode(hkl);
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
		if (!Arrays.equals(hkl, other.hkl))
			return false;
		return true;
	}
}
