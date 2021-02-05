/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;

import uk.ac.diamond.scisoft.analysis.fitting.functions.PeakType;

public class Generic1DDatasetCreator {

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
	
	static final DoubleDataset xAxis = DatasetFactory.createRange(0, dataRange, 1.);

	private static DoubleDataset createDataset(IFunction f) {
		f.setParameterValues(peakPos, defaultFWHM, defaultArea);
		return DatasetUtils.cast(DoubleDataset.class, f.calculateValues(xAxis));
	}

	public static DoubleDataset createDataset(PeakType type) {
		return createDataset(PeakType.createPeak(type, 0, dataRange, maxFWHM, maxArea));
	}
}
