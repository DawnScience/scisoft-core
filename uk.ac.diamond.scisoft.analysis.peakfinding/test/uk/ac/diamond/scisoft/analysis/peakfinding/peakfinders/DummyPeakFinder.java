/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;

public class DummyPeakFinder extends AbstractPeakFinder implements IPeakFinder {
	
	private final String NAME = "Dummy peak finder";
	private static Map<Integer, Double>fakePeakPosnsSigs;

	@Override
	public Map<Integer, Double> findPeaks(IDataset xData, IDataset yData, Integer nPeaks) {
		return getFakePeaks();
	}

	@Override
	protected void setName() {
		this.name = NAME;

	}
	
	public void setPeaks(Map<Integer, Double> peakPosnsSigs) {
		fakePeakPosnsSigs = peakPosnsSigs;
	}
	
	public static Map<Integer, Double> getFakePeaks() {
		if (fakePeakPosnsSigs == null) {
			fakePeakPosnsSigs = new TreeMap<Integer, Double>();
			fakePeakPosnsSigs.put(1, 0.6);
			fakePeakPosnsSigs.put(2, 1.2);
			fakePeakPosnsSigs.put(3, 0.9);
			fakePeakPosnsSigs.put(5, 1.7);
			fakePeakPosnsSigs.put(7, 2.5);
			fakePeakPosnsSigs.put(11, 0.9);
			fakePeakPosnsSigs.put(13, 0.6);
		}
		return fakePeakPosnsSigs;
	}

}
