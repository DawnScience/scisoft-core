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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.measure.quantity.Length;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import org.jscience.physics.amount.Amount;

/**
 * Since we cannot have eclipse preferences in this plugin, but
 * we should access them here, we use the user.home to store the 
 * properties and to register them here.
 * 
 * This class is a bean which is persisted to disk and can be deep
 * cloned by the UI when the user is creating another calibrant list.
 */
public class CalibrationStandards implements Serializable{
	
	public final static Unit<Length> NANOMETER = SI.NANO(SI.METER);

	private Map<String, CalibrantSpacing> cal2peaks;	
	private String version;
	private String selectedCalibrant;
	/**
	 * Used for bean contract, use CalibrationStandards.getInstance() instead.
	 */
	public CalibrationStandards() {	
		this.version = null;
		cal2peaks    = null;
	}
	
	public List<String> getCalibrantList() {
		// cal2peaks must be a LinkedHashMap for this to work.
		return new ArrayList<String>(cal2peaks.keySet());
	}
	
	public CalibrantSpacing getCalibrationPeakMap(String calibrant) {
		return cal2peaks.get(calibrant);
	}
	
	public void addCalibrant(CalibrantSpacing cs) {
		cal2peaks.put(cs.getName(), cs);
	}

	/**
	 * Calling this method saves this CalibrationStandards as the persisted one
	 * for all of Dawn. Only call when sure that this is the required standards.
	 * @throws Exception
	 */
	public void save() throws Exception {
		CalibrationFactory.saveCalibrationStandards(this);
	}
	
	/**
	 * Default list of calibrants TODO add whatever Alun needs.
	 * 
	 * @return map
	 */
	static Map<String, CalibrantSpacing> createDefaultCalibrants() {
		LinkedHashMap<String, CalibrantSpacing> tmp = new LinkedHashMap<String, CalibrantSpacing>();
		
		CalibrantSpacing calibrant = new CalibrantSpacing("Collagen Wet");
		calibrant.addHKL(new HKL(0, 0, 1, Amount.valueOf(67.0, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 2, Amount.valueOf(33.5, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 3, Amount.valueOf(22.3, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 4, Amount.valueOf(16.75, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 5, Amount.valueOf(13.4, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 6, Amount.valueOf(11.6, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 7, Amount.valueOf(9.6, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 8, Amount.valueOf(8.4, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 9, Amount.valueOf(7.4, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 10, Amount.valueOf(6.7, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 11, Amount.valueOf(6.1, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 12, Amount.valueOf(5.6, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 13, Amount.valueOf(5.15, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 15, Amount.valueOf(4.46, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 20, Amount.valueOf(3.35, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 21, Amount.valueOf(3.2, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 22, Amount.valueOf(3.05, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 30, Amount.valueOf(2.2, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 35, Amount.valueOf(1.9, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 41, Amount.valueOf(1.6, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 52, Amount.valueOf(1.3, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 71, Amount.valueOf(0.95, NANOMETER)));
		tmp.put(calibrant.getName(), calibrant);
		
		
		calibrant = new CalibrantSpacing("Collagen Dry");
		calibrant.addHKL(new HKL(0, 0, 1,Amount.valueOf(65.3, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 2,Amount.valueOf(32.7, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 3,Amount.valueOf(21.8, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 4,Amount.valueOf(16.3, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 5,Amount.valueOf(13.1, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 6,Amount.valueOf(10.9, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 7,Amount.valueOf(9.33, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 8,Amount.valueOf(8.16, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 9,Amount.valueOf(7.26, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 10, Amount.valueOf(6.53, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 11, Amount.valueOf(5.94, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 12, Amount.valueOf(5.44, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 13, Amount.valueOf(5.02, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 14, Amount.valueOf(4.66, NANOMETER)));
		tmp.put(calibrant.getName(), calibrant);

		
		calibrant = new CalibrantSpacing("Ag Behenate");
		calibrant.addHKL(new HKL(0, 0, 1,Amount.valueOf(5.838     , NANOMETER))); 
		calibrant.addHKL(new HKL(0, 0, 2,Amount.valueOf(2.919     , NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 3,Amount.valueOf(1.946     , NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 4,Amount.valueOf(1.4595    , NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 5,Amount.valueOf(1.1676    , NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 6,Amount.valueOf(0.973     , NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 7,Amount.valueOf(0.834     , NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 8,Amount.valueOf(0.72975   , NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 9,Amount.valueOf(0.64866667, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 10,Amount.valueOf(0.5838    , NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 11,Amount.valueOf(0.53072727, NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 12,Amount.valueOf(0.4865    , NANOMETER)));
		calibrant.addHKL(new HKL(0, 0, 13,Amount.valueOf(0.44907692, NANOMETER)));
		tmp.put(calibrant.getName(), calibrant);
		        
		
		calibrant = new CalibrantSpacing("HDPE");
		calibrant.addHKL(new HKL(1, 1, 0,Amount.valueOf(0.4166, NANOMETER)));
		calibrant.addHKL(new HKL(2, 0, 0,Amount.valueOf(0.378 , NANOMETER)));
		calibrant.addHKL(new HKL(2, 1, 0,Amount.valueOf(0.3014, NANOMETER)));
		calibrant.addHKL(new HKL(0, 2, 0,Amount.valueOf(0.249 , NANOMETER)));
		tmp.put(calibrant.getName(), calibrant);
		
		
		calibrant = new CalibrantSpacing("Silicon");
		calibrant.addHKL(new HKL(1, 1, 1,Amount.valueOf(0.31355, NANOMETER)));
		calibrant.addHKL(new HKL(2, 2, 0,Amount.valueOf(0.19201, NANOMETER)));
		calibrant.addHKL(new HKL(3, 1, 1,Amount.valueOf(0.16374, NANOMETER)));
		calibrant.addHKL(new HKL(2, 2, 2,Amount.valueOf(0.15677, NANOMETER)));
		calibrant.addHKL(new HKL(4, 0, 0,Amount.valueOf(0.13577, NANOMETER)));
		calibrant.addHKL(new HKL(3, 3, 1,Amount.valueOf(0.12459, NANOMETER)));
		calibrant.addHKL(new HKL(4, 2, 2,Amount.valueOf(0.11085, NANOMETER)));
		calibrant.addHKL(new HKL(3, 3, 3,Amount.valueOf(0.10451, NANOMETER)));
		calibrant.addHKL(new HKL(5, 1, 1,Amount.valueOf(0.10451, NANOMETER)));
		calibrant.addHKL(new HKL(4, 4, 0,Amount.valueOf(0.09600, NANOMETER)));
		calibrant.addHKL(new HKL(5, 3, 1,Amount.valueOf(0.09179, NANOMETER)));
		calibrant.addHKL(new HKL(4, 4, 2,Amount.valueOf(0.09051, NANOMETER)));
		calibrant.addHKL(new HKL(6, 2, 0,Amount.valueOf(0.08586, NANOMETER)));
		calibrant.addHKL(new HKL(5, 3, 3,Amount.valueOf(0.08281, NANOMETER)));
		calibrant.addHKL(new HKL(6, 2, 2,Amount.valueOf(0.08187, NANOMETER)));
		calibrant.addHKL(new HKL(4, 4, 4,Amount.valueOf(0.07838, NANOMETER)));
		calibrant.addHKL(new HKL(7, 1, 1,Amount.valueOf(0.07604, NANOMETER)));
		calibrant.addHKL(new HKL(5, 5, 1,Amount.valueOf(0.07604, NANOMETER)));
		calibrant.addHKL(new HKL(6, 4, 2,Amount.valueOf(0.07257, NANOMETER)));		
		tmp.put(calibrant.getName(), calibrant);
		
		return tmp;

	}
	public Map<String, CalibrantSpacing> getCal2peaks() {
		return cal2peaks;
	}
	public String getVersion() {
		return version;
	}
	public void setCal2peaks(Map<String, CalibrantSpacing> cal2peaks) {
		this.cal2peaks = cal2peaks;
		if (selectedCalibrant==null) {
			selectedCalibrant = cal2peaks.keySet().iterator().next();
		}
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public String getSelectedCalibrant() {
		return selectedCalibrant;
	}

	private Collection<CalibrantSelectedListener> listeners;
	
	public void setSelectedCalibrant(String selectedCalibrant) {
		this.selectedCalibrant = selectedCalibrant;
		fireCalibrantSelectionListeners(selectedCalibrant);
	}
	
	protected void fireCalibrantSelectionListeners(String calibrant) {
		if (listeners==null) return;
		final CalibrantSelectionEvent evt = new CalibrantSelectionEvent(this, calibrant);
		for (CalibrantSelectedListener l : listeners) {
			l.calibrantSelectionChanged(evt);
		}
	}

	public void addCalibrantSelectionListener(CalibrantSelectedListener l) {
		if (listeners==null) listeners = new HashSet<CalibrantSelectedListener>(7);
		listeners.add(l);
	}
	public void removeCalibrantSelectionListener(CalibrantSelectedListener l) {
		if (listeners==null) return;
		listeners.remove(l);
	}
	/**
	 * The current selected calibrant.
	 * @return fred
	 */ 
	public CalibrantSpacing getCalibrant() {
		return getCalibrationPeakMap(getSelectedCalibrant());
	}
}
