/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;

public class FunctionTestUtils {

	/**
	 * Assert that numerically calculated partial derivatives match that provided by the function's implementation
	 * @param f
	 * @param p
	 * @param values
	 */
	public static void assertPartialDerivEquals(AFunction f, IParameter p, double... values) {
		assertEquals(f.calcNumericalDerivative(AFunction.A_TOLERANCE, AFunction.R_TOLERANCE, p, values),
				f.partialDeriv(p, values), 1e1 * AFunction.A_TOLERANCE);
	}

	/**
	 * Assert that numerically calculated partial derivatives match that provided by the function's implementation
	 * @param f
	 * @param p
	 * @param coords
	 * @return actual values
	 */
	public static Dataset assertPartialDerivEquals(AFunction f, IParameter p, IDataset... coords) {
		CoordinatesIterator it = CoordinatesIterator.createIterator(null, coords);
		DoubleDataset result = DatasetFactory.zeros(DoubleDataset.class, it.getShape());
		f.calcNumericalDerivativeDataset(AFunction.A_TOLERANCE, AFunction.R_TOLERANCE, p, result, it);
		DoubleDataset actual = f.calculatePartialDerivativeValues(p, coords);
		TestUtils.assertDatasetEquals(result, actual, 1e1 * AFunction.R_TOLERANCE, 1e1 * AFunction.A_TOLERANCE);
		return actual;
	}

	public static void checkPartialDerivatives(AFunction f) {
		for (IParameter p : f.getParameters()) {
			assertPartialDerivEquals(f, p, 0);
			assertPartialDerivEquals(f, p, 1);
		}

		DoubleDataset coord = DatasetFactory.createRange(7);
		for (IParameter p : f.getParameters()) {
			Dataset actual = assertPartialDerivEquals(f, p, coord);
			Dataset individual = DatasetFactory.zeros(actual);
			IndexIterator it = coord.getIterator();
			while (it.hasNext()) {
				individual.setObjectAbs(it.index, f.partialDeriv(p, coord.getAbs(it.index)));
			}
			TestUtils.assertDatasetEquals(actual, individual, AFunction.R_TOLERANCE, AFunction.A_TOLERANCE);
		}
	}

	public static void checkValues(AFunction f) {
		DoubleDataset coord = DatasetFactory.createRange(7);
		Dataset actual = f.calculateValues(coord);
		Dataset individual = DatasetFactory.zeros(actual);
		IndexIterator it = coord.getIterator();
		while (it.hasNext()) {
			individual.setObjectAbs(it.index, f.val(coord.getAbs(it.index)));
		}
		TestUtils.assertDatasetEquals(actual, individual, AFunction.R_TOLERANCE, AFunction.A_TOLERANCE);
	}
}
