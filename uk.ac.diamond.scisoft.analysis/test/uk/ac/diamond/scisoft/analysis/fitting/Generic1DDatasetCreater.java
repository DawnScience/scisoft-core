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

package uk.ac.diamond.scisoft.analysis.fitting;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Lorentzian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PearsonVII;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PseudoVoigt;

public class Generic1DDatasetCreater {
	
	static final int dataRange = 100;
	static final double peakPos = 50.0;
	static final double defaultFWHM = 20.0;
	static final double defaultArea = 50.0;
	static final double delta = 0.5;
	static final double lambda = 0.1;
	
	static final boolean backgroundDominated = true;
	static final boolean autoStopping = true;
	static final double threshold = 0.10;
	static final int numPeaks = -1;
	static final int smoothing = 5;
	
	static final double accuracy = 0.0001;
	
	static final DoubleDataset xAxis = (DoubleDataset) AbstractDataset.arange(0, dataRange, 1, AbstractDataset.FLOAT64);

	
	public static DoubleDataset createGaussianDataset(){
		CompositeFunction comp = new CompositeFunction();
		comp.addFunction(new Gaussian(peakPos, peakPos, defaultFWHM, defaultArea));
		return comp.makeDataset(xAxis);
	}
	
	public static DoubleDataset createPearsonVII(){
		CompositeFunction comp = new CompositeFunction();
		comp.addFunction(new PearsonVII(peakPos, peakPos, defaultFWHM, defaultArea));
		return comp.makeDataset(xAxis);
	}
	
	public static DoubleDataset createLorentzianDataset(){
		CompositeFunction comp = new CompositeFunction();
		comp.addFunction(new Lorentzian(peakPos, peakPos, defaultFWHM, defaultArea));
		return comp.makeDataset(xAxis);
	}
	
	public static DoubleDataset createPseudoVoigt(){
		CompositeFunction comp = new CompositeFunction();
		comp.addFunction(new PseudoVoigt(peakPos, peakPos, defaultFWHM, defaultArea));
		return comp.makeDataset(xAxis);
	}
	
}
