/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import static uk.ac.diamond.scisoft.analysis.diffraction.PixelSplitter.addToDatasets;

import javax.vecmath.Vector3d;

import org.eclipse.january.dataset.DoubleDataset;

/**
 * This does not split pixel but places its value in the nearest voxel
 */
public class NonSplitter implements PixelSplitter {
	@Override
	public void splitValue(DoubleDataset volume, DoubleDataset weight, final double[] vsize, Vector3d dh, int[] pos, double value) {
		int idx = volume.get1DIndex(pos);
		addToDatasets(idx, volume, value, weight, 1);
	}

	@Override
	public NonSplitter clone() {
		return new NonSplitter();
	}
}