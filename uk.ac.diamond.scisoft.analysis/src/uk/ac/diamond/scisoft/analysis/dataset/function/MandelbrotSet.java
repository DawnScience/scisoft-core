/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
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
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;

/**
 * Calculate a dataset of recursion values where a value less than the given maximum indicate a position in the
 * complex plane is outside the Mandelbrot set
 */
public class MandelbrotSet implements DatasetToDatasetFunction {
	private int maxRecursion; 

	/**
	 * Mandelbrot set
	 * @param maxRecursion maximum recursion
	 */
	public MandelbrotSet(int maxRecursion) {
		this.maxRecursion = maxRecursion;
	}

	/**
-	 * @param axes specify real and imaginary coordinates as two 1d datasets
	 * @return a list containing a dataset of recursion limits
	 */
	@Override
	public List<Dataset> value(IDataset... axes) {
		Dataset xaxis, yaxis;

		if (axes.length < 2) {
			throw new IllegalArgumentException("Need two axes");
		}
		xaxis = DatasetUtils.convertToDataset(axes[0]);
		yaxis = DatasetUtils.convertToDataset(axes[1]);
		if (xaxis.getRank() != 1 || yaxis.getRank() != 1) {
			throw new IllegalArgumentException("Need both axes to be 1d datasets");
		}

		IntegerDataset count = new IntegerDataset(yaxis.getShape()[0], xaxis.getShape()[0]);

		List<Dataset> result = new ArrayList<Dataset>();

		final IndexIterator iy = yaxis.getIterator();
		int n = 0;
		while (iy.hasNext()) {
			final double iv = yaxis.getElementDoubleAbs(iy.index);
			final IndexIterator ix = xaxis.getIterator();
			while (ix.hasNext()) {
				final double rv = xaxis.getElementDoubleAbs(ix.index);

				double x = 0, y = 0;
				int c = -1;
				do {
					double t = x*x - y*y + rv;
					y = 2.*x*y + iv;
					x = t;
				} while (++c < maxRecursion && x*x + y*y <= 4.);
				count.setAbs(n++, c);
			}
		}
		result.add(count);
		return result;
	}
}
