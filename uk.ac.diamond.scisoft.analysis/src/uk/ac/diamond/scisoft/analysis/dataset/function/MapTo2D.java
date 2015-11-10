/*
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;

/**
 * Map a N-D dataset from one Cartesian system to another and return that remapped 2D dataset
 */
public class MapTo2D implements DatasetToDatasetFunction {
	private double[] pt;
	private double[] da;
	private double[] db;
	private int d;
	final private int[] s;

	/**
	 * Set up mapping of 2D dataset
	 * 
	 * @param point origin point for 2d dataset
	 * @param step0 direction vector for step in 0th dimension (axis down column)
	 * @param step1 direction vector for step in 1st dimension (axis along row)
	 * @param n0 number of rows
	 * @param n1 number of columns
	 */
	public MapTo2D(double[] point, double[] step0, double[] step1, int n0, int n1) {
		d = point.length;
		if (step0.length != d || step1.length != d) {
			throw new IllegalArgumentException("Specified directions must be of same rank as point");
		}
		pt = point;
		da = step0;
		db = step1;
		s = new int[] {n0, n1};
	}

	/**
	 * The class implements mapping of a Cartesian lattice sampled data (pixels) to another Cartesian grid
	 * 
	 * @param datasets
	 *            input N-D dataset
	 * @return a 2D dataset
	 */
	@Override
	public List<Dataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<Dataset> result = new ArrayList<Dataset>();

		for (IDataset ids : datasets) {
			Dataset ds = DatasetUtils.convertToDataset(ids);
			if (ds.getRank() != d) {
				throw new IllegalArgumentException("Input dataset must be of rank " + d);
			}

			Dataset newmap = DatasetFactory.zeros(s, ds.getDtype());

			double[] p0 = new double[d];
			double[] p = new double[d];
			for (int j = 0; j < s[0]; j++) {
				for (int k = 0; k < d; k++) {
					p0[k] = pt[k] + j * da[k];
				}
				for (int i = 0; i < s[1]; i++) {
					for (int k = 0; k < d; k++) {
						p[k] = p0[k] + i * db[k];
					}
					newmap.set(Maths.interpolate(ds, null, p), j, i);
				}
			}
			result.add(newmap);
		}
		return result;
	}
}
