/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gda.analysis.utils.DatasetMaths;

import java.util.Vector;

import org.junit.Test;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PySystemState;

import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

import Jama.Matrix;

/**
 * Basic testing of the DataSet Class
 */
@SuppressWarnings("deprecation")
public class DataSetTest {

	static {
		// This is now needed for Jython 2.5.1 as static PyException type objects are
		// not defined until after first PyException object is constructed
		// (without a trace-back argument). Fixes GDA-2624
		PySystemState.initialize();
	}

	/**
	 * Initial constructor tests
	 */
	@Test
	public void testConstructor1() {

		// first test the basic constructor stability

		try {
			@SuppressWarnings("unused")
			DataSet t1 = new DataSet(100, 100, 100);
		} catch (Exception e) {
			fail("Basic dataset construction failed");
		}

		// initially create a dataset that is far too large
		try {
			@SuppressWarnings("unused")
			DataSet t1 = new DataSet(1000, 1000, 1000, 1000, 1000);
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed from incorrect arguments being passed to the constructor");
		}

		// This one is ok, but it wont fit in memory
		try {
			@SuppressWarnings("unused")
			DataSet t1 = new DataSet(1000, 1000, 1000);
		} catch (OutOfMemoryError e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed, this should give an OutOfMemory error");
		}

		// This one has no size, as one of the dimensions are set to zero.
		try {
			@SuppressWarnings("unused")
			DataSet t1 = new DataSet(254, 457, 451, 0, 124, 124);
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed, this should give an IllegalArgumentException for passing a zero size error");
		}

	}

	/**
	 * This tests another set of constructors
	 */
	@Test
	public void testConstructor2() {

		// now test the dataset(double ...) constructor
		DataSet t1 = new DataSet(10000);
		t1 = DatasetMaths.add(t1, 5.0);
		Vector<DataSet> t2 = new Vector<DataSet>();
		// add new vectors till it falls over
		try {
			for (int i = 0; i < 1000; i++) {
				t2.add(new DataSet(t1.doubleArray()));
			}
		} catch (OutOfMemoryError e) {
			// correct
		} catch (Exception e) {
			fail("wrong type of error caught");
		}
	}

	/**
	 * And another set of constructors
	 */
	@Test
	@SuppressWarnings("unused")
	public void testConstructor3() {

		// now the slightly more complex 2D dataset constructor
		// dataset(width, height, double ...) constructor
		DataSet t1 = new DataSet(100, 100);
		t1 = DatasetMaths.add(t1, 5.0);

		// try to create the dataset with some poor arguments

		try {
			DataSet t3 = new DataSet(1, 0, t1.doubleArray());
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed, this should give an IllegalArgumentException for passing a zero size error");
		}

		try {
			DataSet t3 = new DataSet(0, -10, t1.doubleArray());
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed, this should give an IllegalArgumentException for passing a zero size error");
		}

		try {
			DataSet t3 = new DataSet(-11, 0, t1.doubleArray());
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed, this should give an IllegalArgumentException for passing a zero size error");
		}

		try {
			DataSet t3 = new DataSet(110, 110, t1.doubleArray());
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed, this should give an IllegalArgumentException for passing a zero size error");
		}

		Vector<DataSet> t2 = new Vector<DataSet>();
		// add new correct vectors till it falls over
		try {
			for (int i = 0; i < 1000; i++) {
				t2.add(new DataSet(100, 100, t1.doubleArray()));
			}
		} catch (OutOfMemoryError e) {
			// correct
		} catch (Exception e) {
			fail("wrong type of error caught");
		}
	}

	/**
	 * More constructors
	 */
	@Test
	@SuppressWarnings("unused")
	public void testConstructor4() {
		// generate a matrix to load in
		DataSet t1 = new DataSet(100, 100);
		t1 = DatasetMaths.add(t1, 1.524);
		Matrix m1 = null;

		Vector<DataSet> t2 = new Vector<DataSet>();

		// try and make one with a null matrix
		try {
			DataSet t3 = new DataSet(m1);
		} catch (IllegalArgumentException e) {
			// good
		} catch (Exception e) {
			fail("Wrong type of exception raised");
		}

		m1 = new Matrix(t1.doubleMatrix());

		// now perform the tests.
		try {
			for (int i = 0; i < 1000; i++) {
				t2.add(new DataSet(m1));
			}
		} catch (OutOfMemoryError e) {
			// correct
		} catch (Exception e) {
			fail("wrong type of error caught");
		}

	}

	/**
	 * And more
	 */
	@Test
	@SuppressWarnings("unused")
	public void testConstructor5() {

		DataSet t1 = null;

		// try and make one with a null dataSet
		try {
			DataSet t3 = new DataSet(t1);
		} catch (IllegalArgumentException e) {
			// good
		} catch (Exception e) {
			fail("Wrong type of exception raised");
		}

		t1 = new DataSet(1000);
		t1.set(23.0, 55);
		t1.set(34.0, 105);
		t1.set(45.0, 564);
		t1.set(56.0, 999);

		DataSet t3 = new DataSet(t1);

		// make some checks
		if (Math.abs(t1.get(23) - t3.get(23)) > 0.000001) {
			fail("content of the dataset is corrupt");
		}

		// make some checks
		if (Math.abs(t1.get(105) - t3.get(105)) > 0.000001) {
			fail("content of the dataset is corrupt");
		}
		// make some checks
		if (Math.abs(t1.get(564) - t3.get(564)) > 0.000001) {
			fail("content of the dataset is corrupt");
		}
		// make some checks
		if (Math.abs(t1.get(999) - t3.get(999)) > 0.000001) {
			fail("content of the dataset is corrupt");
		}

		Vector<DataSet> t2 = new Vector<DataSet>();

		// now perform the tests.
		try {
			for (int i = 0; i < 1000; i++) {
				t2.add(new DataSet(t1));
			}
		} catch (OutOfMemoryError e) {
			// correct
		} catch (Exception e) {
			fail("wrong type of error caught");
		}

	}

	/**
	 * Makes sure the size of dataset warnings are correct
	 */
	@Test
	@SuppressWarnings("null")
	public void testDataSize() {
		// check to see if it works first.
		double[] val = { 0.1, 0.2, 0.3, 0.4, 0.5 };
		assertEquals(5, (new DataSet(val)).getSize());

		// given that this works, try to access a null pointer
		DataSet t1 = null;
		try {
			t1 = new DataSet(4, 4, val);
		} catch (Exception e) {
			// do nothing as we are deliberately being bad here
		}

		try {
			t1.getSize();
		} catch (NullPointerException e) {
			// this is correct
		} catch (Exception e) {
			fail("Wrong exception passed");
		}

		// ok, i can't think of a way to actually test the failsafe here
	}

	/**
	 * Tests the fill mechanism
	 */
	@Test
	public void testFill() {
		double x = 123.0;
		int l = 5;

		DataSet d = new DataSet(l);
		d.fill(x);
		for (int i = 0; i < l; i++) {
			assertEquals(x, d.get(i), 1e-8);
		}
	}

	/**
	 * Tests the arrange method
	 */
	@Test
	public void testArange() {
		double[] x = { 0., 1., 2., 3., 4., 5. };
		int l = x.length;

		DataSet d1 = DataSet.arange(6.);
		DataSet d2 = DataSet.arange(1., 7.);
		DataSet d3 = DataSet.arange(0., 3., 0.5);

		for (int i = 0; i < l; i++) {
			assertEquals("DS arange 1", x[i], d1.get(i), 1e-8);
		}
		for (int i = 0; i < l; i++) {
			assertEquals("DS arange 2", x[i] + 1., d2.get(i), 1e-8);
		}
		for (int i = 0; i < l; i++) {
			assertEquals("DS arange 3", 0.5 * x[i], d3.get(i), 1e-8);
		}
	}

	/**
	 * Tests the linspace method
	 */
	@Test
	public void testLinspace() {
		double[] x = { 0., 1., 2., 3., 4., 5. };
		int l = x.length;

		DataSet d1 = DataSet.linspace(0., 5., 6);

		for (int i = 0; i < l; i++) {
			assertEquals("DS linspace 1", x[i], d1.get(i), 1e-8);
		}
	}

	/**
	 * Tests the resize method
	 */
	@Test
	public void testResize() {
		double[][] x = { { 0., 1., 2. }, { 3., 4., 5. } };

		DataSet d1 = DataSet.arange(6.);
		d1.resize(2, 3);

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals("DS reshape 1", x[i][j], d1.get(i, j), 1e-8);
			}
		}

		d1.resize(3, 1);

		for (int i = 0; i < 3; i++) {
			assertEquals("DS reshape 2", i, d1.get(i, 0), 1e-8);
		}

		d1.resize(1, 5);

		for (int i = 0; i < 5; i++) {
			assertEquals("DS reshape 3", i<3 ? i: 0, d1.get(0, i), 1e-8);
		}

		d1.resize(3, 2);
		double[][] y = { { 0., 1.},  { 2., 0. }, { 0., 0. } };
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				assertEquals("DS reshape 4", y[i][j], d1.get(i, j), 1e-8);
			}
		}
	}

	/**
	 * Tests the setShape method
	 */
	@Test
	public void testSetShape() {
		double[][] x = { { 0., 1., 2. }, { 3., 4., 5. } };

		DataSet d1 = DataSet.arange(6.);
		DataSet d2 = new DataSet(x);
		d1.setShape(2, 3);

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals("DS resize 1", d2.get(i, j), d1.get(i, j), 1e-8);
			}
		}
	}

	/**
	 * Tests the append method
	 */
	@Test
	public void testAppend() {
		double[] x = { 0., 1., 2., 3., 4., 5. };
		DataSet d1 = DataSet.arange(3.);
		DataSet d2 = DataSet.arange(3., 6.);
		DataSet d3 = d1.append(d2, 0);

		for (int i = 0; i < x.length; i++) {
			assertEquals("DS Append 1", x[i], d3.get(i), 1e-8);
		}

		d1.setShape(1, 3);
		d2.setShape(1, 3);
		d3 = d1.append(d2, 0);
		DataSet d4 = new DataSet(x);

		d4.setShape(2, 3);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals("DS Append 2", d4.get(i, j), d3.get(i, j), 1e-8);
			}
		}

		d3 = d1.append(d2, 1);
		d4 = new DataSet(x);
		d4.setShape(1, 6);
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 6; j++) {
				assertEquals("DS Append 3", d4.get(i, j), d3.get(i, j), 1e-8);
			}
		}
	}

	/**
	 * Tests the slice method
	 */
	@Test
	public void testSlice2() {
		double[] x = { 0., 1., 2., 3., 4., 5. };
		DataSet d1 = DataSet.arange(10.);
		DataSet d2 = d1.getSlice(new int[] { 0 }, new int[] { 3 }, null);
		for (int i = 0; i < 3; i++) {
			assertEquals("DS Slice2 1", x[i], d2.get(i), 1e-8);
		}
		double[] y = { 5., 6. };
		DataSet d3 = d1.getSlice(new int[] { 5 }, new int[] { -3 }, null);
		for (int i = 0; i < 2; i++) {
			assertEquals("DS Slice2 2", y[i], d3.get(i), 1e-8);
		}
		DataSet d4 = d1.getSlice(new int[] { -5 }, new int[] { 7 }, null);
		for (int i = 0; i < 2; i++) {
			assertEquals("DS Slice2 3", y[i], d4.get(i), 1e-8);
		}
	}

	/**
	 * Tests the slice method
	 */
	@Test
	public void testSlice3() {
		double[] x = { 0., 1., 2., 3., 4., 5. };
		DataSet d1 = DataSet.arange(10.);
		DataSet d2 = d1.getSlice(new int[] { 0 }, new int[] { 3 }, new int[] { 1 });
		for (int i = 0; i < 3; i++) {
			assertEquals("DS Slice3 1", x[i], d2.get(i), 1e-8);
		}
		DataSet d3 = d1.getSlice(new int[] { 0 }, new int[] { 6 }, new int[] { 2 });
		for (int i = 0; i < 3; i++) {
			assertEquals("DS Slice3 2", 2. * x[i], d3.get(i), 1e-8);
		}
		double[] y = { 3., 2., 1. };
		DataSet d4 = d1.getSlice(new int[] { 3 }, new int[] { 0 }, new int[] { -1 });
		for (int i = 0; i < 3; i++) {
			assertEquals("DS Slice3 3", y[i], d4.get(i), 1e-8);
		}
		DataSet d5 = d1.getSlice(new int[] { -4 }, new int[] { 0 }, new int[] { -2 });
		for (int i = 0; i < 3; i++) {
			assertEquals("DS Slice3 4", 2. * y[i], d5.get(i), 1e-8);
		}
		double[] z = { 5., 6. };
		DataSet d6 = d1.getSlice(new int[] { 5 }, new int[] { -3 }, new int[] { 1 });
		for (int i = 0; i < 2; i++) {
			assertEquals("DS Slice3 5", z[i], d6.get(i), 1e-8);
		}
		DataSet d7 = d1.getSlice(new int[] { -5 }, new int[] { 7 }, new int[] { 1 });
		for (int i = 0; i < 2; i++) {
			assertEquals("DS Slice3 6", z[i], d7.get(i), 1e-8);
		}
	}

	/** 
	 * Tests generated from Jira ticket GDA-2258
	 */
	@Test
	public void testGDA2258() {
		
		double data[] = {2.8343e+02,	1.7720e+01,	1.6230e+01,	1.6370e+01,	1.3520e+01};
		
		DataSet test = new DataSet(data);
		
		// check the max value is correct
		assertEquals(283.43, test.max(), 0.001);
		
		// check the max pos is correct
		assertEquals(0, test.maxPos()[0]);
		
		// check the min pos is correct
		assertEquals(4, test.minPos()[0]);

		double data2[] = {	9.8044e+02,	9.6879e+02,	1.3870e+03,	1.3870e+03,	1.3870e+03	};

		DataSet test2 = new DataSet(data2);
		
		// check the max value is correct
		assertEquals(1387.0, test2.max(), 0.001);
		
		// check the max pos is correct
		assertEquals(2, test2.maxPos()[0]);

		// check the min pos is correct
		assertEquals(1, test2.minPos()[0]);

	}

	/** 
	 * Tests generated from Jira ticket GDA-2257
	 */
	@Test
	public void testGDA2257() {
		double data[] = {2.3};

		DataSet test = new DataSet(data);

		assertEquals(2.3,test.max(),0.01);
	}

	/**
	 * Tests for Jira ticket GDA-2270 (negative indices)
	 */
	@Test
	public void testGDA2270() {
		DataSet test = DataSet.arange(10);
		Object t;

		// try getting a negative index
		t = test.__getitem__(-2);
		assertEquals(8., ((Double) t).doubleValue(), 0);

		// try setting a negative index
		test.__setitem__(-2, 0.);
		assertEquals(0., test.get(8), 0);

		test = DataSet.arange(10);
		test.setShape(2,5);
		DataSet u = DataSet.arange(5., 10.);

		// try getting a negative index for 2D dataset
		t = test.__getitem__(-1);
		for (int i = 0; i < u.getDimensions()[0]; i++) {
			assertEquals(u.get(i), ((DataSet) t).get(i), 0);
		}

		// try getting a negative index for 2D dataset
		try {
			test.__setitem__(-1, 0.0);
		} catch (PyException e) {
			if (e.type != Py.NotImplementedError) {
				fail("Wrong type of Python exception");
			}
		} catch (Exception e) {
			fail("Wrong type of exception raised");
		}
		
	}

	/**
	 * Tests for Jira ticket GDA-2271 (out of bound indices)
	 */
	@Test
	public void testGDA2271() {
		DataSet test = DataSet.arange(10);
		@SuppressWarnings("unused")
		Object t;

		// try getting a positive index
		try {
			t = test.__getitem__(10);
		} catch (PyException e) {
			if (e.type != Py.IndexError) {
				fail("Wrong type of Python exception");
			}
			// good
		} catch (Exception e) {
			fail("Wrong type of exception raised");
		}

		// try setting a positive index
		try {
			test.__setitem__(11, 0.);
		} catch (PyException e) {
			if (e.type != Py.IndexError) {
				fail("Wrong type of Python exception");
			}
			// good
		} catch (Exception e) {
			fail("Wrong type of exception raised");
		}

		// try getting a negative index
		try {
			t = test.__getitem__(-11);
		} catch (PyException e) {
			if (e.type != Py.IndexError) {
				fail("Wrong type of Python exception");
			}
			// good
		} catch (Exception e) {
			fail("Wrong type of exception raised");
		}

		// try setting a negative index
		try {
			test.__setitem__(-11, 0.);
		} catch (PyException e) {
			if (e.type != Py.IndexError) {
				fail("Wrong type of Python exception");
			}
			// good
		} catch (Exception e) {
			fail("Wrong type of exception raised");
		}

	}
	
	/**
	 * Tests for SciSoft Ticket #492 (Better Dataset Scaling)
	 */
	@Test
	public void testSS492() {
		
		// A set of basic tests which will make sure the functionality is not altered.
		
		DataSet ds = DataSet.arange(10);
		ds.set(23., 15);
		assertEquals(23., ds.get(15), 0);
		assertEquals(16,ds.getDimensions()[0]);
		
		DataSet ds2 = DataSet.arange(100);
		int[] dims = {10,10};
		ds2.setShape(dims);
		assertEquals(10,ds2.getDimensions()[0]);
		assertEquals(10,ds2.getDimensions()[1]);
		
		ds2.set(66.6, 20,20);
		assertEquals(66.6, ds2.get(20,20), 0);
		for (int i = 0; i < dims[0]; i++) {
			for (int j = 0; j < dims[1]; j++) 
				assertEquals(i*10+j, ds2.get(i,j), 0);
		}
		
		DataSet ds3 = DataSet.arange(1000);
		int[] dims3 = {10,10,10};
		ds3.setShape(dims3);
		assertEquals(10,ds3.getDimensions()[0]);
		assertEquals(10,ds3.getDimensions()[1]);
		assertEquals(10,ds3.getDimensions()[2]);
		
		ds3.set(66.6, 20,20,20);
		assertEquals(66.6, ds3.get(20,20,20), 0);
		for (int i = 0; i < dims3[0]; i++) {
			for (int j = 0; j < dims3[1]; j++) {
				for (int k = 0; k < dims3[2]; k++) 
					assertEquals(i*100+j*10+k, ds3.get(i,j,k), 0);
			}
		}

		DataSet ds4 = DataSet.arange(1);
		
		for(int i = 0; i < 100; i++) {
			ds4.set(i, i);
			assertEquals(i*1.0, ds4.get(i), 0);
		}
		
		DataSet ds5 = DataSet.arange(1000);
		int[] dims5 = {10,10,10};
		ds5.setShape(dims5);
		
		for(int i = 0; i < 100; i++) {
			ds5.set(i, 5,i,5);
			assertEquals(i*1.0, ds5.get(5,i,5), 0);
		}
		
	}

	/**
	 * Tests for squeeze method
	 */
	@Test
	public void testSqueeze() {
		DataSet ds = DataSet.arange(10);
		ds.setShape(2,1,5);

		ds.squeeze();
		assertEquals(2, ds.getDimensions().length);
		assertEquals(2, ds.getDimensions()[0]);
		assertEquals(5, ds.getDimensions()[1]);
	}

	/**
	 * Tests for tile method
	 */
	@Test
	public void testTile() {
		// 1D
		DataSet ds = DataSet.arange(3);

		DataSet ta = DataSet.tile(ds, 2);
		double[] xa = { 0., 1., 2., 0., 1., 2. };

		assertEquals(1, ta.getDimensions().length);
		assertEquals(6, ta.getDimensions()[0]);
		for (int i = 0; i < xa.length; i++) {
			assertEquals(xa[i], ta.getBuffer()[i], 1e-6);
		}

		DataSet tb = DataSet.tile(ds, 1, 2);

		assertEquals(2, tb.getDimensions().length);
		assertEquals(1, tb.getDimensions()[0]);
		assertEquals(6, tb.getDimensions()[1]);
		for (int i = 0; i < xa.length; i++) {
			assertEquals(xa[i], tb.getBuffer()[i], 1e-6);
		}

		DataSet tc = DataSet.tile(ds, 2, 1);

		assertEquals(2, tc.getDimensions().length);
		assertEquals(2, tc.getDimensions()[0]);
		assertEquals(3, tc.getDimensions()[1]);
		for (int i = 0; i < xa.length; i++) {
			assertEquals(xa[i], tc.getBuffer()[i], 1e-6);
		}

		// 2D
		ds = DataSet.arange(6);
		ds.setShape(2,3);
		DataSet td = DataSet.tile(ds, 2);
		double[] xd = { 0., 1., 2., 0., 1., 2., 3., 4., 5., 3., 4., 5. };

		assertEquals(2, td.getDimensions().length);
		assertEquals(2, td.getDimensions()[0]);
		assertEquals(6, td.getDimensions()[1]);
		for (int i = 0; i < xd.length; i++) {
			assertEquals(xd[i], td.getBuffer()[i], 1e-6);
		}

		DataSet te = DataSet.tile(ds, 1, 2);

		assertEquals(2, te.getDimensions().length);
		assertEquals(2, te.getDimensions()[0]);
		assertEquals(6, te.getDimensions()[1]);
		for (int i = 0; i < xd.length; i++) {
			assertEquals(xd[i], te.getBuffer()[i], 1e-6);
		}

		DataSet tf = DataSet.tile(ds, 2, 1);
		double[] xf = { 0., 1., 2., 3., 4., 5., 0., 1., 2., 3., 4., 5. };

		assertEquals(2, tf.getDimensions().length);
		assertEquals(4, tf.getDimensions()[0]);
		assertEquals(3, tf.getDimensions()[1]);
		for (int i = 0; i < xf.length; i++) {
			assertEquals(xf[i], tf.getBuffer()[i], 1e-6);
		}
	}

	/**
	 * Tests for permuteAxes method
	 */
	@Test
	public void testPermuteAxes() {
		// 2D
		DataSet ds = DataSet.arange(6);
		ds.setShape(2,3);

		DataSet ta = DataSet.permuteAxes(ds, 1, 0);
		double[][] xa = { { 0., 1., 2. }, { 3., 4., 5. } };

		assertEquals(2, ta.getDimensions().length);
		assertEquals(3, ta.getDimensions()[0]);
		assertEquals(2, ta.getDimensions()[1]);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals(xa[i][j], ta.get(j, i), 1e-6);
			}
		}

		// 3D
		ds = DataSet.arange(24);
		ds.setShape(2, 3, 4);

		double[][][] xb = { {{ 0., 1., 2., 3.}, {4., 5., 6., 7.}, {8., 9., 10., 11. }},
				{{12., 13., 14., 15.}, {16., 17., 18., 19.}, {20., 21., 22., 23.}} };

		DataSet tb;

		try {
			tb = DataSet.permuteAxes(ds, 0);
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed from incorrect arguments being passed to the constructor");
		}

		try {
			tb = DataSet.permuteAxes(ds, 0, -1, 0);
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed from incorrect arguments being passed to the constructor");
		}

		try {
			tb = DataSet.permuteAxes(ds, 0, 1, 1);
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed from incorrect arguments being passed to the constructor");
		}

		tb = DataSet.permuteAxes(ds, 0, 1, 2);
		assertEquals(3, tb.getDimensions().length);
		assertEquals(2, tb.getDimensions()[0]);
		assertEquals(3, tb.getDimensions()[1]);
		assertEquals(4, tb.getDimensions()[2]);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 4; k++) {
					assertEquals(xb[i][j][k], tb.get(i, j, k), 1e-6);
				}
			}
		}

		tb = DataSet.permuteAxes(ds, 1, 0, 2);
		assertEquals(3, tb.getDimensions().length);
		assertEquals(3, tb.getDimensions()[0]);
		assertEquals(2, tb.getDimensions()[1]);
		assertEquals(4, tb.getDimensions()[2]);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 4; k++) {
					assertEquals(xb[i][j][k], tb.get(j, i, k), 1e-6);
				}
			}
		}

		tb = DataSet.permuteAxes(ds, 2, 0, 1);
		assertEquals(3, tb.getDimensions().length);
		assertEquals(4, tb.getDimensions()[0]);
		assertEquals(2, tb.getDimensions()[1]);
		assertEquals(3, tb.getDimensions()[2]);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 4; k++) {
					assertEquals(xb[i][j][k], tb.get(k, i, j), 1e-6);
				}
			}
		}
	}

	/**
	 * Tests for repeat method
	 */
	@Test
	public void testRepeat() {
		// 2D
		DataSet ds = DataSet.arange(6);
		ds.setShape(2,3);

		double[] xa = { 0., 0., 1., 1., 2., 2., 3., 3., 4., 4., 5., 5. };
		DataSet ta = DataSet.repeat(ds, 2);
		assertEquals(1, ta.getDimensions().length);
		assertEquals(12, ta.getDimensions()[0]);
		for (int i = 0; i < 12; i++) {
			assertEquals(xa[i], ta.get(i), 1e-6);
		}

		double[][] xb = { { 0., 0., 1., 1., 2., 2. }, {  3., 3., 4., 4., 5., 5. }  };
		DataSet tb = DataSet.repeat(ds, 2, 1);
		assertEquals(2, tb.getDimensions().length);
		assertEquals(2, tb.getDimensions()[0]);
		assertEquals(6, tb.getDimensions()[1]);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++) {
				assertEquals(xb[i][j], tb.get(i, j), 1e-6);
			}
		}

		double[][] xc = { { 0., 1., 2. }, { 0., 1., 2. }, {  3., 4., 5. }, {  3., 4., 5. }  };
		DataSet tc = DataSet.repeat(ds, 2, 0);
		assertEquals(2, tc.getDimensions().length);
		assertEquals(4, tc.getDimensions()[0]);
		assertEquals(3, tc.getDimensions()[1]);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals(xc[i][j], tc.get(i, j), 1e-6);
			}
		}

		double[][] xd = { { 0., 1., 2. }, { 0., 1., 2. }, {  3., 4., 5. } };
		DataSet td = DataSet.repeat(ds, new int[] {2, 1}, 0);
		assertEquals(2, td.getDimensions().length);
		assertEquals(3, td.getDimensions()[0]);
		assertEquals(3, td.getDimensions()[1]);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals(xd[i][j], td.get(i, j), 1e-6);
			}
		}

		double[][] xe = { { 0., 1., 1., 2., 2., 2.}, {  3., 4., 4., 5., 5., 5. }  };
		DataSet te = DataSet.repeat(ds, new int[] {1, 2, 3}, 1);
		assertEquals(2, te.getDimensions().length);
		assertEquals(2, te.getDimensions()[0]);
		assertEquals(6, te.getDimensions()[1]);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++) {
				assertEquals(xe[i][j], te.get(i, j), 1e-6);
			}
		}

		double[] xf = { 0., 1., 2., 2., 5., 5., 5. };
		DataSet tf = DataSet.repeat(ds, new int[] {1, 1, 2, 0, 0, 3});
		assertEquals(1, tf.getDimensions().length);
		assertEquals(7, tf.getDimensions()[0]);
		for (int i = 0; i < 7; i++) {
			assertEquals(xf[i], tf.get(i), 1e-6);
		}

		// expanded dataset test
		DataSet dt = DataSet.arange(1024);
		dt.set(0, 1024);

		DataSet tg = DataSet.repeat(dt, 2);
		assertEquals(1, tg.getDimensions().length);
		assertEquals(2050, tg.getDimensions()[0]);
		for (int i = 0; i < 1024; i++) {
			assertEquals(i, tg.get(2*i), 1e-6);
			assertEquals(i, tg.get(2*i+1), 1e-6);
		}

		// expanded 2D dataset test
		dt = DataSet.arange(1024);
		dt.setShape(16, 64);
		dt.set(0., 0, 64);

		tg = DataSet.repeat(dt, 2, 1);
		assertEquals(2, tg.getDimensions().length);
		assertEquals(16, tg.getDimensions()[0]);
		assertEquals(130, tg.getDimensions()[1]);
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 64; j++) {
				assertEquals(i*64+j, tg.get(i, 2*j), 1e-6);
				assertEquals(i*64+j, tg.get(i, 2*j+1), 1e-6);
			}
		}

		tg = DataSet.repeat(dt, 2, 0);
		assertEquals(2, tg.getDimensions().length);
		assertEquals(32, tg.getDimensions()[0]);
		assertEquals(65, tg.getDimensions()[1]);
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 64; j++) {
				assertEquals(i*64+j, tg.get(2*i, j), 1e-6);
				assertEquals(i*64+j, tg.get(2*i+1, j), 1e-6);
			}
		}

		try {
			tf = DataSet.repeat(ds, 0, 3);
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed from incorrect arguments being passed to the constructor");
		}

		try {
			tf = DataSet.repeat(ds, new int[] {2, 1});
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed from incorrect arguments being passed to the constructor");
		}

		try {
			tf = DataSet.repeat(ds, -1);
		} catch (IllegalArgumentException e) {
			// this is correct.
		} catch (Exception e) {
			fail("wrong exception type passed from incorrect arguments being passed to the constructor");
		}
	}

	/**
	 * Test contents functions
	 */
	@Test
	public void testContents() {
		double[] x = { 0, 2., -12.3 };
		double[] y = { Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY };
		double[] z = { 1e14, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY };

		DataSet ta = new DataSet(x);
		assertEquals(false, ta.containsNans());
		assertEquals(false, ta.containsInfs());

		DataSet tb = new DataSet(y);
		assertEquals(true, tb.containsNans());
		assertEquals(true, tb.containsInfs());
		assertEquals(true, Double.isNaN(tb.min())); 
		assertEquals(false, Double.isInfinite(tb.min()));
		assertEquals(true, Double.isNaN(tb.max()));
		assertEquals(false, Double.isInfinite(tb.max()));

		DataSet tc = new DataSet(z);
		assertEquals(true, Double.isInfinite(tc.min()));
		assertEquals(true, Double.isInfinite(tc.max()));

		ta = DataSet.arange(1024);
		ta.set(3., 1024);
		assertEquals(false, ta.containsNans());
		assertEquals(false, ta.containsInfs());
	}
	
	/**
	 * Test auto allocation 
	 */
	@Test
	public void testAutoAllocation() {
		DataSet y = new DataSet(1);
		for(int i=0; i< 1000; i++){
			y.set(i,i);
		}
		IndexIterator iter = y.getIterator();
		int i=0;
		while(iter.hasNext()){
			if( i< 1000){
				assert( i == y.get(iter.index));
			} 
			i++;
		}
		assert(i == 1000);
	}

	/**
	 * Test diff method
	 */
	@Test
	public void testDiff() {
		DataSet x = DataSet.arange(10);
		DataSet dx = DatasetMaths.derivative(x.getIndexDataSet(), x, 1);
		for (int i = 0; i < 9; i++)
			assertEquals(1, dx.get(i), 1e-6);
	}

	/**
	 * Test in-place add method
	 */
	@Test
	public void testIAdd() {
		DataSet x = DataSet.arange(8);
		x.setShape(2,2,2);
		DataSet y = DatasetMaths.add(x, 1);
		x.iadd(y);
		IndexIterator it = x.getIterator();
		while (it.hasNext()) {
			assertEquals(it.index + (it.index + 1.), x.getAbs(it.index), 1e-6);
		}
	}

	/**
	 * Test in-place subtract method
	 */
	@Test
	public void testISubtract() {
		DataSet x = DataSet.arange(8);
		x.setShape(2,2,2);
		DataSet y = DatasetMaths.add(x, 1);
		x.isubtract(y);
		IndexIterator it = x.getIterator();
		while (it.hasNext()) {
			assertEquals(it.index - (it.index + 1.), x.getAbs(it.index), 1e-6);
		}
	}

	/**
	 * Test in-place multiply method
	 */
	@Test
	public void testIMultiply() {
		DataSet x = DataSet.arange(8);
		x.setShape(2,2,2);
		DataSet y = DatasetMaths.add(x, 1);
		x.imultiply(y);
		IndexIterator it = x.getIterator();
		while (it.hasNext()) {
			assertEquals(it.index * (it.index + 1.), x.getAbs(it.index), 1e-6);
		}
	}

	/**
	 * Test in-place divide method
	 */
	@Test
	public void testIDivide() {
		DataSet x = DataSet.arange(8);
		x.setShape(2,2,2);
		DataSet y = DatasetMaths.add(x, 1);
		x.idivide(y);
		IndexIterator it = x.getIterator();
		while (it.hasNext()) {
			assertEquals(it.index / (it.index + 1.), x.getAbs(it.index), 1e-6);
		}
	}
}
