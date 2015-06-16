/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;

public class DummyPeakFinder extends AbstractPeakFinder implements IPeakFinder {
	
	private final String NAME = "Dummy peak finder"; 

	@Override
	public Set<Integer> findPeaks(IDataset xData, IDataset yData, Integer nPeaks) {
		return new TreeSet<Integer>(Arrays.asList(1, 2, 3, 5, 7, 11, 13));
	}

	@Override
	protected void setName() {
		this.name = NAME;

	}

}
