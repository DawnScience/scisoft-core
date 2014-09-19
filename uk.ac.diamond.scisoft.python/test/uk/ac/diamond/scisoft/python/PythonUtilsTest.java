/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.python;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.python.core.Py;
import org.python.core.PyInteger;
import org.python.core.PySlice;
import org.python.core.PySystemState;
import org.python.core.PyTuple;

public class PythonUtilsTest {
	@BeforeClass
	public static void setup() {
		PySystemState.initialize(); // needed to define some statics like None, Ellipsis, etc
	}

	@Test
	public void testGetSlice() {
		Dataset a;
		IDataset b;
		int[] shape;

		a = DatasetFactory.createRange(12, Dataset.FLOAT64).reshape(2, 6);
		b = PythonUtils.getSlice(a, new PyInteger(-1));
		shape = b.getShape();
		assertEquals(1, shape.length);
		assertEquals(6, shape[0]);

		b = PythonUtils.getSlice(a, new PyTuple(new PyInteger(-1)));
		shape = b.getShape();
		assertEquals(1, shape.length);
		assertEquals(6, shape[0]);

		a = DatasetFactory.createRange(60, Dataset.FLOAT64).reshape(2, 5, 3, 2);
		b = PythonUtils.getSlice(a, new PyTuple(new PyInteger(-1)));
		assertArrayEquals(new int[] {5, 3, 2}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PyInteger(-1), new PySlice(), new PySlice()));
		assertArrayEquals(new int[] {5, 3, 2}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PyInteger(-1), new PySlice(), new PySlice(), new PySlice()));
		assertArrayEquals(new int[] {5, 3, 2}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PyInteger(-1), new PySlice(), new PySlice(new PyInteger(1), Py.None, Py.None), new PySlice()));
		assertArrayEquals(new int[] {5, 2, 2}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PyInteger(-1), Py.Ellipsis));
		assertArrayEquals(new int[] {5, 3, 2}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(Py.Ellipsis, new PyInteger(-1)));
		assertArrayEquals(new int[] {2, 5, 3}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PyInteger(1), Py.Ellipsis, new PyInteger(-1)));
		assertArrayEquals(new int[] {5, 3}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PyInteger(-1), Py.None));
		assertArrayEquals(new int[] {1, 5, 3, 2}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PySlice(), Py.None));
		assertArrayEquals(new int[] {2, 1, 5, 3, 2}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PySlice(), Py.None, new PyInteger(-1)));
		assertArrayEquals(new int[] {2, 1, 3, 2}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(Py.Ellipsis, new PyInteger(-1), Py.None));
		assertArrayEquals(new int[] {2, 5, 3, 1}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PySlice(), Py.None, Py.Ellipsis, new PyInteger(-1)));
		assertArrayEquals(new int[] {2, 1, 5, 3}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PySlice(), Py.None, Py.Ellipsis, Py.None, new PyInteger(-1)));
		assertArrayEquals(new int[] {2, 1, 5, 3, 1}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PySlice(), Py.None, Py.Ellipsis, new PyInteger(-1), Py.None));
		assertArrayEquals(new int[] {2, 1, 5, 3, 1}, b.getShape());

		a = DatasetFactory.createFromObject(1);
		b = PythonUtils.getSlice(a, new PyTuple(Py.Ellipsis));
		shape = b.getShape();
		assertArrayEquals(new int[0], shape);

	}

}
