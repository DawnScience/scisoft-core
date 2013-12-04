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

package uk.ac.diamond.scisoft.analysis.fitting;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Lorentzian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PearsonVII;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PseudoVoigt;

public class Generic1DDatasetCreater {
	
	static final int dataRange = 100;
	static final double peakPos = 50.0;
	static final double defaultFWHM = 20.0;
	static final double defaultArea = 25.0;
	static final double maxFWHM = 40.0;
	static final double maxArea = 50.0;
	static final double delta = 0.51;
	static final double lambda = 0.1;
	
	static final boolean backgroundDominated = true;
	static final boolean autoStopping = true;
	static final double threshold = 0.10;
	static final int numPeaks = -1;
	static final int smoothing = 5;
	
	static final double accuracy = 0.0001;
	
	static final DoubleDataset xAxis = (DoubleDataset) AbstractDataset.arange(0, dataRange, 1, AbstractDataset.FLOAT64);

	private static DoubleDataset createDataset(IFunction f) {
		CompositeFunction comp = new CompositeFunction();
		comp.addFunction(f);
		f.setParameterValues(peakPos, defaultFWHM, defaultArea);
		return comp.makeDataset(xAxis);
	}
	
	public static DoubleDataset createGaussianDataset() {
		return createDataset(new Gaussian(0, dataRange, maxFWHM, maxArea));
	}

	public static DoubleDataset createLorentzianDataset() {
		return createDataset(new Lorentzian(0, dataRange, maxFWHM, maxArea));
	}

	public static DoubleDataset createPearsonVII() {
		return createDataset(new PearsonVII(0, dataRange, maxFWHM, maxArea));
	}

	public static DoubleDataset createPseudoVoigt() {
		return createDataset(new PseudoVoigt(0, dataRange, maxFWHM, maxArea));
	}
}
