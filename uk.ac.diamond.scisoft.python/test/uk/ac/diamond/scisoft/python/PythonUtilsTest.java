/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.python;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.python.core.Py;
import org.python.core.PyInteger;
import org.python.core.PySlice;
import org.python.core.PySystemState;
import org.python.core.PyTuple;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

public class PythonUtilsTest {
	@BeforeClass
	public static void setup() {
		PySystemState.initialize(); // needed to define some statics like None, Ellipsis, etc
	}

	@Test
	public void testGetSlice() {
		AbstractDataset a;
		IDataset b;
		int[] shape;

		a = AbstractDataset.arange(12, AbstractDataset.FLOAT64).reshape(2, 6);
		b = PythonUtils.getSlice(a, new PyInteger(-1));
		shape = b.getShape();
		assertEquals(1, shape.length);
		assertEquals(6, shape[0]);

		b = PythonUtils.getSlice(a, new PyTuple(new PyInteger(-1)));
		shape = b.getShape();
		assertEquals(1, shape.length);
		assertEquals(6, shape[0]);

		a = AbstractDataset.arange(60, AbstractDataset.FLOAT64).reshape(2, 5, 3, 2);
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

		a = AbstractDataset.array(1);
		b = PythonUtils.getSlice(a, new PyTuple(Py.Ellipsis));
		shape = b.getShape();
		assertArrayEquals(new int[0], shape);

	}

}
