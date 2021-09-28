/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import javax.vecmath.Vector3d;

import org.eclipse.january.dataset.DoubleDataset;

/**
 * This does not split pixel but places its value in the nearest voxel
 */
public class NonSplitter implements PixelSplitter {
	protected DoubleDataset output;
	protected DoubleDataset weight;

	@Override
	public void setDatasets(DoubleDataset output, DoubleDataset weight) {
		this.output = output;
		this.weight = weight;
	}

	/**
	 * Add values to datasets at given index
	 * @param index
	 * @param va value
	 * @param vb value
	 */
	void addToDatasets(final int index, double va, double vb) {
		output.setAbs(index, output.getAbs(index) + va);
		weight.setAbs(index, weight.getAbs(index) + vb);
	}

	@Override
	public void splitValue(final double[] vsize, Vector3d dh, int[] pos, double value) {
		int idx = output.get1DIndex(pos);
		addToDatasets(idx, value, 1);
	}

	@Override
	public boolean doesSpread() {
		return false;
	}

	@Override
	public NonSplitter clone() {
		NonSplitter c = new NonSplitter();
		c.output = output;
		c.weight = weight;
		return c;
	}
}