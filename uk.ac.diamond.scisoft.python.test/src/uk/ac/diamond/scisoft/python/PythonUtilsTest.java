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
import static org.junit.Assert.fail;

import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.junit.BeforeClass;
import org.junit.Test;
import org.python.core.CompileMode;
import org.python.core.CompilerFlags;
import org.python.core.Py;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PySlice;
import org.python.core.PySystemState;
import org.python.core.PyTuple;

public class PythonUtilsTest {
	@BeforeClass
	public static void setup() {
		PySystemState.initialize(); // needed to define some statics like None, Ellipsis, etc
	}

	@Test
	public void testGetSlice() throws DatasetException {
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

		b = PythonUtils.getSlice(a, new PyList(new PyObject[] {new PySlice(), Py.None, Py.Ellipsis, new PyInteger(-1), Py.None}));
		assertArrayEquals(new int[] {2, 1, 5, 3, 1}, b.getShape());

		a = DatasetFactory.createFromObject(1);
		b = PythonUtils.getSlice(a, new PyTuple(Py.Ellipsis));
		shape = b.getShape();
		assertArrayEquals(new int[0], shape);

	}

	@Test
	public void testCompiler() throws Exception {
		// check out different compile modes that Jython supports
		testCompile(false);
		testCompile(true);
	}

	private void testCompile(boolean isSingle) throws Exception {
		CompileMode mode = isSingle? CompileMode.single : CompileMode.exec; // NB "eval" is not available in Jython nor documented in CPython
		String filename = "<input>";
		CompilerFlags flags = new CompilerFlags();

		PyObject ret = Py.compile_command_flags("", filename, mode, flags, false);
		System.out.println(ret);

		ret = Py.compile_command_flags("1", filename, mode, flags, false);
		System.out.println(ret);

		ret = Py.compile_command_flags("print 2", filename, mode, flags, false);
		System.out.println(ret);

		ret = Py.compile_command_flags("if True: print 42", filename, mode, flags, false);
		if (isSingle) {
			assertEquals(Py.None, ret);
		}
		System.out.println(ret);

		try {
			ret = Py.compile_command_flags("if True:", filename, mode, flags, false);
			if (isSingle) {
				assertEquals(Py.None, ret);
			} else {
				fail();
			}
			System.out.println(ret);
		} catch (Exception e) {
		}
	}
}
