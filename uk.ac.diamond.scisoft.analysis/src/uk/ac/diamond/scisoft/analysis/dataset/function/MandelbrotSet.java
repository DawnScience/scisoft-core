/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;

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
	public List<AbstractDataset> value(IDataset... axes) {
		AbstractDataset xaxis, yaxis;

		if (axes.length < 2) {
			throw new IllegalArgumentException("Need two axes");
		}
		xaxis = DatasetUtils.convertToAbstractDataset(axes[0]);
		yaxis = DatasetUtils.convertToAbstractDataset(axes[1]);
		if (xaxis.getRank() != 1 || yaxis.getRank() != 1) {
			throw new IllegalArgumentException("Need both axes to be 1d datasets");
		}

		IntegerDataset count = new IntegerDataset(yaxis.getShape()[0], xaxis.getShape()[0]);

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

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
