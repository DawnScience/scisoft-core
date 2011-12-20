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

package uk.ac.diamond.scisoft.analysis.optimize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import Jama.Matrix;
import Jama.SingularValueDecomposition;

/**
 * Basic least squares solver using SVD method
 */
public class LinearLeastSquares {

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory.getLogger(LinearLeastSquares.class);
	private final double threshold; // threshold ratio 

	/**
	 * Base constructor
	 * 
	 * @param tolerance
	 *            ratio of lowest to highest singular value to allow
	 */
	public LinearLeastSquares(double tolerance) {
		threshold = tolerance;
	}

	/**
	 * Solve linear least square problem defined by
	 * <pre>
	 * A x = b
	 * </pre>
	 * @param matrix <pre>A</pre>
	 * @param data <pre>b</pre>
	 * @param sigmasq estimate of squared error on each data point
	 * @return array of values
	 */
	public double[] solve(AbstractDataset matrix, AbstractDataset data, AbstractDataset sigmasq) {
		if (matrix.getRank() != 2) {
			logger.error("Matrix was not 2D");
			throw new IllegalArgumentException("Matrix was not 2D");
		}

		final int[] shape = matrix.getShape();
		final int dlen = data.getShape()[0];
		if (data.getRank() != 1 && shape[1] != dlen) {
			logger.error("Data was not 1D or else not correct length");
			throw new IllegalArgumentException("Data was not 2D or else not correct length");
		}

		final Matrix X = new Matrix((double [][]) DatasetUtils.createJavaArray(matrix.cast(AbstractDataset.FLOAT64)));
		final Matrix W = new Matrix((double [][]) DatasetUtils.createJavaArray(DatasetUtils.diag(Maths.reciprocal(sigmasq.cast(AbstractDataset.FLOAT64)), 0)));

		final Matrix XtW = X.transpose().times(W);
		final Matrix A = XtW.times(X);

		final SingularValueDecomposition svd = A.svd();

		final double[] values = svd.getSingularValues();


		final Matrix b = new Matrix(dlen, dlen);
		for (int i = 0; i < dlen; i++) {
			b.set(i, 0, data.getDouble(i));
		}

		final Matrix c = svd.getV().transpose().times(XtW.times(b));

		final double limit = threshold*values[0];
		final int vlen = values.length;

		for (int i = 0; i < vlen; i++) {
			final double v = values[i];
			c.set(i, 0, v >= limit ? c.get(i, 0) / v : 0);
		}

		final Matrix d = svd.getU().times(c);

		final double[] result = new double[vlen];
		for (int i = 0; i < vlen; i++) {
			result[i] = d.get(i, 0);
		}

		return result;
	}

}
