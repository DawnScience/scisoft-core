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
 * Intensity value splitter that splits an image pixel value and adds the pieces to close-by voxels
 */
public interface PixelSplitter extends Cloneable {
	/**
	 * Spread a pixel intensity value over voxels near position
	 * @param volume dataset that holds the voxel values
	 * @param weight dataset that holds the relative contributions from each pixel
	 * @param vsize voxel size in reciprocal space
	 * @param dh offset in reciprocal space from voxel corner
	 * @param pos position in volume
	 * @param value pixel intensity to split
	 */
	public void splitValue(DoubleDataset volume, DoubleDataset weight, final double[] vsize, final Vector3d dh, final int[] pos, final double value);

	/**
	 * @return true if pixel is spread over 8 voxels
	 */
	public boolean doesSpread();

	public PixelSplitter clone();

	/**
	 * Add values to datasets at given index
	 * @param index
	 * @param da dataset
	 * @param va value
	 * @param db dataset
	 * @param vb value
	 */
	static void addToDatasets(final int index, final DoubleDataset da, double va, final DoubleDataset db, double vb) {
		da.setAbs(index, da.getAbs(index) + va);
		db.setAbs(index, db.getAbs(index) + vb);
	}

	/**
	 * Create a pixel splitter of given name
	 * @param splitter name
	 * @param p parameter
	 * @return pixel splitter
	 */
	static PixelSplitter createSplitter(String splitter, double p) {
		if (splitter == null || splitter.isEmpty() || splitter.equals("nearest")) {
			return new NonSplitter();
		} else if (splitter.equals("gaussian")) {
			return new GaussianSplitter(p);
		} else if (splitter.equals("negexp")) {
			return new ExponentialSplitter(p);
		} else if (splitter.equals("inverse")) {
			return new InverseSplitter();
		} 
	
		throw new IllegalArgumentException("Splitter is not known");
	}
}
