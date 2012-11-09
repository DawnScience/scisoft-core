/*
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

import java.util.LinkedHashMap;
import java.util.Set;

import javax.measure.quantity.Length;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import org.jscience.physics.amount.Amount;


public class CalibrationStandards {

	protected static final LinkedHashMap<String, LinkedHashMap<HKL, Amount<Length>> > cal2peaks = new LinkedHashMap<String, LinkedHashMap<HKL, Amount<Length>> >();
	
	protected static Unit<Length> NANOMETER = SI.NANO(SI.METER);
	
	static {
		
		LinkedHashMap<HKL, Amount<Length>> hkl2peaks = new LinkedHashMap<HKL, Amount<Length>>();
		
		hkl2peaks.put(new HKL(0, 0, 1), Amount.valueOf(67.0, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 2), Amount.valueOf(33.5, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 3), Amount.valueOf(22.3, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 4), Amount.valueOf(16.75, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 5), Amount.valueOf(13.4, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 6), Amount.valueOf(11.6, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 7), Amount.valueOf(9.6, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 8), Amount.valueOf(8.4, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 9), Amount.valueOf(7.4, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 10), Amount.valueOf(6.7, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 11), Amount.valueOf(6.1, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 12), Amount.valueOf(5.6, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 13), Amount.valueOf(5.15, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 15), Amount.valueOf(4.46, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 20), Amount.valueOf(3.35, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 21), Amount.valueOf(3.2, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 22), Amount.valueOf(3.05, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 30), Amount.valueOf(2.2, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 35), Amount.valueOf(1.9, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 41), Amount.valueOf(1.6, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 52), Amount.valueOf(1.3, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 71), Amount.valueOf(0.95, NANOMETER));
		cal2peaks.put("Collagen Wet", (LinkedHashMap<HKL, Amount<Length>>) hkl2peaks.clone()); // SAXS
		hkl2peaks.clear();
		
		hkl2peaks.put(new HKL(0, 0, 1),  Amount.valueOf(65.3, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 2),  Amount.valueOf(32.7, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 3),  Amount.valueOf(21.8, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 4),  Amount.valueOf(16.3, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 5),  Amount.valueOf(13.1, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 6),  Amount.valueOf(10.9, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 7),  Amount.valueOf(9.33, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 8),  Amount.valueOf(8.16, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 9),  Amount.valueOf(7.26, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 10), Amount.valueOf(6.53, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 11), Amount.valueOf(5.94, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 12), Amount.valueOf(5.44, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 13), Amount.valueOf(5.02, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 14), Amount.valueOf(4.66, NANOMETER));
		cal2peaks.put("Collagen Dry", (LinkedHashMap<HKL, Amount<Length>>) hkl2peaks.clone()); // SAXS
		hkl2peaks.clear();
		
		hkl2peaks.put(new HKL(0, 0, 1),  Amount.valueOf(5.838     , NANOMETER)); 
		hkl2peaks.put(new HKL(0, 0, 2),  Amount.valueOf(2.919     , NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 3),  Amount.valueOf(1.946     , NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 4),  Amount.valueOf(1.4595    , NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 5),  Amount.valueOf(1.1676    , NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 6),  Amount.valueOf(0.973     , NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 7),  Amount.valueOf(0.834     , NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 8),  Amount.valueOf(0.72975   , NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 9),  Amount.valueOf(0.64866667, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 10), Amount.valueOf(0.5838    , NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 11), Amount.valueOf(0.53072727, NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 12), Amount.valueOf(0.4865    , NANOMETER));
		hkl2peaks.put(new HKL(0, 0, 13), Amount.valueOf(0.44907692, NANOMETER));
		cal2peaks.put("Ag Behenate", (LinkedHashMap<HKL, Amount<Length>>) hkl2peaks.clone()); // SAXS
		hkl2peaks.clear();
		        
		hkl2peaks.put(new HKL(1, 1, 0), Amount.valueOf(0.4166, NANOMETER));
		hkl2peaks.put(new HKL(2, 0, 0), Amount.valueOf(0.378 , NANOMETER));
		hkl2peaks.put(new HKL(2, 1, 0), Amount.valueOf(0.3014, NANOMETER));
		hkl2peaks.put(new HKL(0, 2, 0), Amount.valueOf(0.249 , NANOMETER));
		cal2peaks.put("HDPE", (LinkedHashMap<HKL, Amount<Length>>) hkl2peaks.clone()); // WAXS
		hkl2peaks.clear();
		
		hkl2peaks.put(new HKL(1, 1, 1), Amount.valueOf(0.31355, NANOMETER));
		hkl2peaks.put(new HKL(2, 2, 0), Amount.valueOf(0.19201, NANOMETER));
		hkl2peaks.put(new HKL(3, 1, 1), Amount.valueOf(0.16374, NANOMETER));
		hkl2peaks.put(new HKL(2, 2, 2), Amount.valueOf(0.15677, NANOMETER));
		hkl2peaks.put(new HKL(4, 0, 0), Amount.valueOf(0.13577, NANOMETER));
		hkl2peaks.put(new HKL(3, 3, 1), Amount.valueOf(0.12459, NANOMETER));
		hkl2peaks.put(new HKL(4, 2, 2), Amount.valueOf(0.11085, NANOMETER));
		hkl2peaks.put(new HKL(3, 3, 3), Amount.valueOf(0.10451, NANOMETER));
		hkl2peaks.put(new HKL(5, 1, 1), Amount.valueOf(0.10451, NANOMETER));
		hkl2peaks.put(new HKL(4, 4, 0), Amount.valueOf(0.09600, NANOMETER));
		hkl2peaks.put(new HKL(5, 3, 1), Amount.valueOf(0.09179, NANOMETER));
		hkl2peaks.put(new HKL(4, 4, 2), Amount.valueOf(0.09051, NANOMETER));
		hkl2peaks.put(new HKL(6, 2, 0), Amount.valueOf(0.08586, NANOMETER));
		hkl2peaks.put(new HKL(5, 3, 3), Amount.valueOf(0.08281, NANOMETER));
		hkl2peaks.put(new HKL(6, 2, 2), Amount.valueOf(0.08187, NANOMETER));
		hkl2peaks.put(new HKL(4, 4, 4), Amount.valueOf(0.07838, NANOMETER));
		hkl2peaks.put(new HKL(7, 1, 1), Amount.valueOf(0.07604, NANOMETER));
		hkl2peaks.put(new HKL(5, 5, 1), Amount.valueOf(0.07604, NANOMETER));
		hkl2peaks.put(new HKL(6, 4, 2), Amount.valueOf(0.07257, NANOMETER));		
		cal2peaks.put("Silicon", (LinkedHashMap<HKL, Amount<Length>>) hkl2peaks.clone()); // WAXS
		hkl2peaks.clear();
	}

	public static Set<String> getCalibrantList() {
		return cal2peaks.keySet();
	}
	
	public static LinkedHashMap<HKL, Amount<Length>> getCalibrationPeakMap(String calibrant) {
		return cal2peaks.get(calibrant);
	}
	
}
