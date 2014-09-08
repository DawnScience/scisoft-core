/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
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
