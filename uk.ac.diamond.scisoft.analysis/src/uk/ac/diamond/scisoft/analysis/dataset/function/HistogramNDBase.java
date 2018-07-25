/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import org.eclipse.january.dataset.IDataset;


/**
 * Base for multi-dimensional histogram
 */
abstract class HistogramNDBase extends HistogramBase {

	protected int[] nbins;
	protected double[] bMax;
	protected double[] bMin;
	protected BinEdges[] binEdges;

	/**
	 * Set bin edges
	 * <p>
	 * This overrides the number of bin per dimension set in the constructor
	 * @param edges
	 */
	public void setBinEdges(IDataset... edges) {
		binEdges = new BinEdges[edges.length];
		nbins = new int[edges.length];
		for (int i = 0; i < edges.length; i++) {
			nbins[i] = edges[i].getSize() - 1;
			binEdges[i]  = new BinEdges(edges[i]);
		}
	}

	/**
	 * Set maximum values for bins
	 * @param max
	 */
	public void setBinMaxima(double... max) {
		this.bMax = max;
	}

	/**
	 * Set minimum values for bins
	 * @param min
	 */
	public void setBinMinima(double... min) {
		this.bMin = min;
	}
}
