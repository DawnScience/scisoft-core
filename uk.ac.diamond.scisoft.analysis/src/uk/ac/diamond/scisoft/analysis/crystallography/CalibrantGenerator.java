/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.measure.quantity.Length;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import org.jscience.physics.amount.Amount;

public class CalibrantGenerator {
	
	public final static Unit<Length> NANO = SI.NANO(SI.METER);
	
	public static enum Cubic {
		SIMPLE,BCC,FCC,DIAMOND;
	}
	
	public static CalibrantSpacing createCubicStandard(String name, double a, int nReflections, Cubic type) {
		
		CalibrantSpacing calibrant = new CalibrantSpacing(name);
		List<HKL> listHKL =  new ArrayList<HKL>();
		int h = 0;
		int k = 0;
		int l = 1;
		
		while (listHKL.size() < nReflections) {
			
			if (isAllowedCubic(h,k,l, type)) {
				HKL hkl = calculateCubicLatticeSpacing(a, h, k, l);
				
				boolean duplicate = false;
				for (HKL i: listHKL) {
					if (i.getDNano() == hkl.getDNano()) {
						duplicate = true;
						break;
					}
				}
				
				if (!duplicate) listHKL.add(hkl);
			}
			
			if (l == k && l == h) {
				l++;
				k = 0;
				h = 0;
			}
			else if (k < l && k == h) {
				k++;
				h = 0;
			}
			else h++;
		}
		
		Collections.sort(listHKL, new Comparator<HKL>() {
			
			@Override
			public int compare(HKL h1, HKL h2) {
				
				return (int)Math.signum((h2.getDNano() - h1.getDNano()));
			}
		});
		
		for (int i = 0; i < listHKL.size(); i++) listHKL.get(i).setRingName("Position " + i);
		
		calibrant.setHKLs(listHKL);
	
		return calibrant;
	}
	
	private static boolean isAllowedCubic(int h, int k, int l, Cubic type){
		
		switch (type) {
		case SIMPLE:
			return true;
		case BCC:
			int testBcc = (h + k + l)%2;
			if (testBcc == 0) return true;
			break;
		case DIAMOND:
			int testd1 = h%2 + k%2 + l%2;
			int testd2 = (h+k+l)%4;
			if (testd1 == 0 && testd2 == 0) return true;
			if (testd1 == 3) return true;
			break;
		case FCC:
			int testFcc = h%2 + k%2 + l%2;
			if (testFcc == 0) return true;
			if (testFcc == 3) return true;
			break;
		default:
			break;
		}

		return false;
	}
	
	public static CalibrantSpacing createRhombohedralStandard(String name, double a, double c, int maxhkl) {

		CalibrantSpacing calibrant = new CalibrantSpacing(name);
		List<HKL> listHKL =  new ArrayList<HKL>();

		for (int l = -maxhkl; l < maxhkl; l++) {
			for (int k = -maxhkl; k < maxhkl; k++) {
				for (int h = -maxhkl; h < maxhkl; h++) {

					if (h==0 && k == 0) continue;
					
					if (!isAllowedRhombohedral(h,k,l)) continue;

					HKL hkl = calculateHexagonalLatticeSpacing(a, c, h, k, l);
					boolean duplicate = false;
					for (HKL i: listHKL) {
						if (i.getDNano() == hkl.getDNano()) {
							duplicate = true;
							break;
						}
					}
					if (duplicate) continue;
					
					listHKL.add(hkl);
				}
			}
		}

		Collections.sort(listHKL, new Comparator<HKL>() {

			@Override
			public int compare(HKL h1, HKL h2) {

				return (int)Math.signum((h2.getDNano() - h1.getDNano()));
			}
		});

		for (int i = 0; i < listHKL.size(); i++) listHKL.get(i).setRingName("Position " + i);

		calibrant.setHKLs(listHKL.subList(0, maxhkl));

		return calibrant;
	}

	private static boolean isAllowedRhombohedral(int h, int k, int l) {
		
		if (h != k && k != l && h != l && h != -k && h != 0 && k != 0 && l != 0)
            if ((-h+k+l)%3 == 0) return true;
		
		 if (l == 0 && h != k && h != 0 && k != 0)
             if ((-h + k)%3 == 0) return true;
		
		 int i = -(h+k);
		 
		 if (h == k && i == -2 * h && h != 0)
             if ((l)%3 == 0) return true;
		 
		 if (h == -k && i == 0 && h != 0 && l != 0)
             if ((h+l)%3 == 0 && (l)%2 == 0) return true;
		 
		 if (h == 0 && k == 0 && i == 0 && l != 0)
             if ((l)%6 == 0) return true;
		 
		 if (h == -k && i == 0 && l == 0 && h != 0)
             if ((h)%3 == 0) return true;
		 
		return false;
	}
	
	private static HKL calculateCubicLatticeSpacing(double a, int h, int k, int l) {

		double d = a/(Math.sqrt((Math.pow(h, 2)+Math.pow(k, 2)+Math.pow(l, 2))));

		return new HKL(h, k, l, Amount.valueOf(d,  NANO));
	}

	private static HKL calculateHexagonalLatticeSpacing(double a, double c, int h, int k, int l) {

		double d = Math.sqrt(1/((4./3.)*(Math.pow(h, 2)+(h*k)+Math.pow(k, 2))/Math.pow(a, 2)+(Math.pow(l, 2)/Math.pow(c, 2))));
		return new HKL(h, k, l, Amount.valueOf(d,  NANO));

	}

}
