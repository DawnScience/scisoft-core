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

import java.util.EventObject;

public class CalibrantSelectionEvent extends EventObject {

	private String calibrant;

	public CalibrantSelectionEvent(Object source, String calibrant) {
		super(source);
		this.calibrant = calibrant;
	}

	public String getCalibrant() {
		return calibrant;
	}

	public void setCalibrant(String calibrant) {
		this.calibrant = calibrant;
	}

}
