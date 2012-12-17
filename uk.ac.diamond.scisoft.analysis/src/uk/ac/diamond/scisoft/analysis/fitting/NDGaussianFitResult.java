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

package uk.ac.diamond.scisoft.analysis.fitting;

import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;

public class NDGaussianFitResult {

	private double[] pos = null;
	private double[] fwhm = null;
	private double[] area = null;
	private double[] background = null;
	
	public NDGaussianFitResult(AFunction... results) {
		pos = new double[results.length];
		fwhm = new double[results.length];
		area = new double[results.length];
		background = new double[results.length];
		
		for (int i = 0; i < results.length; i++) {
			pos[i] = results[i].getParameterValue(0);
			fwhm[i] = results[i].getParameterValue(1);
			area[i] = results[i].getParameterValue(2);
			background[i] = results[i].getParameterValue(3);
		}
	}

	public double[] getPos() {
		return pos;
	}

	public double[] getFwhm() {
		return fwhm;
	}

	public double[] getArea() {
		return area;
	}

	public double[] getBackground() {
		return background;
	}
	
	

}
