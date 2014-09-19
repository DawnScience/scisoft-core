/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

import uk.ac.diamond.scisoft.analysis.SDAPlotter;

/**
 *
 */
public class TestRMIPlotClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DoubleDataset yAxis = new DoubleDataset(new double[]{3,1,2,5,4,6,7,9,8,10}, 10);
		try {
			SDAPlotter.plot("Plot 1", "TEST", yAxis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
