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

import java.math.BigInteger;

import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.ComplexDoubleDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.InterfaceUtils;
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

		a = DatasetFactory.createRange(12).reshape(2, 6);
		b = PythonUtils.getSlice(a, new PyInteger(-1));
		shape = b.getShape();
		assertEquals(1, shape.length);
		assertEquals(6, shape[0]);

		b = PythonUtils.getSlice(a, new PyTuple(new PyInteger(-1)));
		shape = b.getShape();
		assertEquals(1, shape.length);
		assertEquals(6, shape[0]);

		a = DatasetFactory.createRange(60).reshape(2, 5, 3, 2);
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

		b = PythonUtils.getSlice(a, new PyTuple(new PySlice(), Py.Ellipsis, Py.None, new PyInteger(-1)));
		assertArrayEquals(new int[] {2, 5, 3, 1}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(Py.Ellipsis, Py.None, new PySlice(), new PyInteger(-1)));
		assertArrayEquals(new int[] {2, 5, 1, 3}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PySlice(), Py.None, Py.Ellipsis, new PyInteger(-1)));
		assertArrayEquals(new int[] {2, 1, 5, 3}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PySlice(), Py.None, Py.Ellipsis, Py.None, new PyInteger(-1)));
		assertArrayEquals(new int[] {2, 1, 5, 3, 1}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(new PySlice(), Py.None, Py.Ellipsis, new PyInteger(-1), Py.None));
		assertArrayEquals(new int[] {2, 1, 5, 3, 1}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(Py.None, Py.Ellipsis, new PyInteger(-1)));
		assertArrayEquals(new int[] {1, 2, 5, 3}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(Py.None, Py.None, Py.Ellipsis, new PyInteger(-1)));
		assertArrayEquals(new int[] {1, 1, 2, 5, 3}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(Py.None, Py.None, Py.None, Py.Ellipsis, new PyInteger(-1)));
		assertArrayEquals(new int[] {1, 1, 1, 2, 5, 3}, b.getShape());

		b = PythonUtils.getSlice(a, new PyTuple(Py.None, Py.Ellipsis, Py.None, new PyInteger(-1)));
		assertArrayEquals(new int[] {1, 2, 5, 3, 1}, b.getShape());

		try {
			b = PythonUtils.getSlice(a, new PyTuple(Py.None, Py.Ellipsis, Py.Ellipsis));
			fail("Should have thrown an IAE");
		} catch (IllegalArgumentException e) {
			// do nothing
		}

		try {
			b = PythonUtils.getSlice(a, new PyTuple(Py.None, Py.Ellipsis, Py.Space));
			fail("Should have thrown an IAE");
		} catch (IllegalArgumentException e) {
			// do nothing
		}

		b = PythonUtils.getSlice(a, new PyList(new PyObject[] {new PySlice(), Py.None, Py.Ellipsis, new PyInteger(-1), Py.None}));
		assertArrayEquals(new int[] {2, 1, 5, 3, 1}, b.getShape());

		a = DatasetFactory.createFromObject(1);
		b = PythonUtils.getSlice(a, new PyTuple(Py.Ellipsis));
		shape = b.getShape();
		assertArrayEquals(new int[0], shape);
	}

	@Test
	public void testSetSlice() throws DatasetException {
		Dataset a;

		a = DatasetFactory.createRange(IntegerDataset.class, 12).reshape(2, 6);
		PythonUtils.setSlice(a, 1, new PyInteger(-1));
		assertEquals(0, a.getInt(0, 0));
		assertEquals(5, a.getInt(0, 5));
		assertEquals(1, a.getInt(1, 0));
		assertEquals(1, a.getInt(1, 5));
		PythonUtils.setSlice(a, DatasetFactory.createRange(IntegerDataset.class, 6), new PyInteger(-1));
		assertEquals(0, a.getInt(0, 0));
		assertEquals(5, a.getInt(0, 5));
		assertEquals(0, a.getInt(1, 0));
		assertEquals(5, a.getInt(1, 5));

		a = DatasetFactory.createRange(IntegerDataset.class, 12).reshape(2, 6);
		PythonUtils.setSlice(a, 1, new PyTuple(new PySlice(), new PyInteger(-1)));
		assertEquals(0, a.getInt(0, 0));
		assertEquals(1, a.getInt(0, 5));
		assertEquals(6, a.getInt(1, 0));
		assertEquals(1, a.getInt(1, 5));

		a = DatasetFactory.createRange(IntegerDataset.class, 12).reshape(2, 6);
		PythonUtils.setSlice(a, DatasetFactory.createRange(IntegerDataset.class, 2), new PyTuple(new PySlice(), new PyInteger(-1)));
		assertEquals(0, a.getInt(0, 0));
		assertEquals(0, a.getInt(0, 5));
		assertEquals(6, a.getInt(1, 0));
		assertEquals(1, a.getInt(1, 5));

		try {
			PythonUtils.setSlice(a, DatasetFactory.createRange(IntegerDataset.class, 5), new PyInteger(-1));
			fail("Should have thrown an IAE");
		} catch (IllegalArgumentException e) {
		}

		a = DatasetFactory.createRange(IntegerDataset.class, 12).reshape(2, 6);
		PythonUtils.setSlice(a, 1, new PyInteger(-1));
		PythonUtils.setSlice(a, DatasetFactory.createRange(IntegerDataset.class, 6).reshape(1, 6), new PyInteger(-1));
		assertEquals(0, a.getInt(1, 0));
		assertEquals(5, a.getInt(1, 5));

		PythonUtils.setSlice(a, 1, new PyInteger(-1));
		PythonUtils.setSlice(a, DatasetFactory.createRange(IntegerDataset.class, 6).reshape(1, 1, 6), new PyInteger(-1));
		assertEquals(0, a.getInt(1, 0));
		assertEquals(5, a.getInt(1, 5));

		try {
			PythonUtils.setSlice(a, DatasetFactory.createRange(IntegerDataset.class, 6).reshape(6, 1), new PyInteger(-1));
			fail("Should have thrown an IAE");
		} catch (IllegalArgumentException e) {
		}

		PythonUtils.setSlice(a, 1, new PyInteger(-1));
		PythonUtils.setSlice(a, DatasetFactory.createRange(IntegerDataset.class, 6), new PyTuple(new PySlice(), new PySlice()));
		assertEquals(0, a.getInt(1, 0));
		assertEquals(5, a.getInt(1, 5));


		// set complex
		ComplexDoubleDataset z = DatasetFactory.createComplexDataset(ComplexDoubleDataset.class, new double[] {1, 3, 5}, new double[] {2, 4, 6});
		PythonUtils.setSlice(z.getRealView(), DatasetFactory.createFromObject(DoubleDataset.class, new double[] {-1, -3, -5.5}), new PyTuple());
		assertEquals(-1, z.getInt(0));
		assertEquals(-3, z.getInt(1));
		assertEquals(-5, z.getInt(2));
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

	@Test
	public void testGetDType() {
		checkSame(true);
		checkSame((byte) 1);
		checkSame((short) 1);
		checkSame(1);
		checkSame(1);
		checkSame(1.f);
		checkSame(1l);
		checkSame(1.d);
		checkSame(new boolean[] {false, true});
		checkSame(new byte[] {0, 1});
		checkSame(new short[] {0, 1});
		checkSame(new int[] {0, 1});
		checkSame(new float[] {0, 1});
		checkSame(new long[] {0, 1});
		checkSame(new double[] {0, 1});

		Class<? extends Dataset> c = InterfaceUtils.getInterface(1l);
		assertEquals(c, PythonUtils.getInterfaceFromObject(BigInteger.valueOf(1)));
		assertEquals(c, PythonUtils.getInterfaceFromObject(new Object[] {BigInteger.valueOf(1)}));
		assertEquals(c, PythonUtils.getInterfaceFromObject(new BigInteger[] {BigInteger.valueOf(1)}));
	}

	private void checkSame(Object o) {
		assertEquals(InterfaceUtils.getInterface(o), PythonUtils.getInterfaceFromObject(o));
	}

	@Test
	public void testConcatenate() {
		// cope with different types and zero-sized shape
		Dataset[] ins = {DatasetFactory.ones(IntegerDataset.class, 2, 4), DatasetFactory.ones(1, 4)};
		Dataset out = PythonUtils.concatenate(ins, 0);
		assertEquals(DoubleDataset.class, out.getClass());
		assertArrayEquals(new int[] {3, 4}, out.getShapeRef());

		ins[0] = ins[1];
		ins[1] = DatasetFactory.ones(IntegerDataset.class, 0, 4);
		out = PythonUtils.concatenate(ins, 0);
		assertEquals(DoubleDataset.class, out.getClass());
		assertArrayEquals(new int[] {1, 4}, out.getShapeRef());

		ins[0] = DatasetFactory.ones(IntegerDataset.class, 0, 4);
		out = PythonUtils.concatenate(ins, 0);
		assertEquals(IntegerDataset.class, out.getClass());
		assertArrayEquals(new int[] {0, 4}, out.getShapeRef());

		ins[0] = DatasetFactory.ones(DoubleDataset.class, 0, 4);
		out = PythonUtils.concatenate(ins, 0);
		assertEquals(DoubleDataset.class, out.getClass());
		assertArrayEquals(new int[] {0, 4}, out.getShapeRef());

		out = ins[0];
		ins[0] = ins[1];
		ins[1] = out;
		out = PythonUtils.concatenate(ins, 0);
		assertEquals(DoubleDataset.class, out.getClass());
		assertArrayEquals(new int[] {0, 4}, out.getShapeRef());
	}
}
