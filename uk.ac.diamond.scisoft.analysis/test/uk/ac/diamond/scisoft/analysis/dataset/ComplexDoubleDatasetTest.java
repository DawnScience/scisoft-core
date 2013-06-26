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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

public class ComplexDoubleDatasetTest {
	@Test
	public void testConstructor() {
		double[] da = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		ComplexDoubleDataset a = new ComplexDoubleDataset(da);

		assertEquals(AbstractDataset.COMPLEX128, a.getDtype());
		assertEquals(2, a.getElementsPerItem());
		assertEquals(16, a.getItemsize());

		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i*2, a.getElementDoubleAbs(it.index), 1e-5*i);
		}

		ComplexDoubleDataset b = new ComplexDoubleDataset(da, 3, 2);

		it = b.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i*2, b.getElementDoubleAbs(it.index), 1e-5*i);
		}

		List<Complex> list = new ArrayList<Complex>();
		list.add(new Complex(0.5, 1.0));
		ComplexDoubleDataset z = ComplexDoubleDataset.createFromObject(list);
		assertEquals(0.5, z.getComplex(0).getReal(), 1e-6);
		assertEquals(1.0, z.getComplex(0).getImaginary(), 1e-6);

		AbstractDataset aa = Maths.abs(a);
		assertEquals(AbstractDataset.FLOAT64, aa.getDtype());
		assertEquals(1, aa.getElementsPerItem());
		assertEquals(8, aa.getItemsize());		
	}

	@Test
	public void testTake() {
		AbstractDataset a = AbstractDataset.arange(12, AbstractDataset.COMPLEX128);
		AbstractDataset t;
		System.out.println(a);

		t = a.take(new int[] {0, 2, 4}, 0);
		System.out.println(t);

		a.setShape(new int[] {3,4});
		System.out.println(a);

		t = a.take(new int[] {0}, 0);
		System.out.println(t);

		t = a.take(new int[] {1}, 0);
		System.out.println(t);

		t = a.take(new int[] {2}, 0);
		System.out.println(t);

		t = a.take(new int[] {0}, 1);
		System.out.println(t);

		t = a.take(new int[] {1}, 1);
		System.out.println(t);

		t = a.take(new int[] {2}, 1);
		System.out.println(t);

		t = a.take(new int[] {3}, 1);
		System.out.println(t);

	}

	@Test
	public void testStats() {
		AbstractDataset a = AbstractDataset.arange(12, AbstractDataset.COMPLEX128);
		assertEquals(5.5, ((Complex) a.mean()).getReal(), 1e-6);
		assertEquals(0., ((Complex) a.mean()).getImaginary(), 1e-6);
		assertEquals(13., a.variance().doubleValue(), 1e-6);
		assertEquals(3.6055512754639891, a.stdDeviation().doubleValue(), 1e-6);

		a.iadd(new Complex(0,0.5));
		assertEquals(5.5, ((Complex) a.mean()).getReal(), 1e-6);
		assertEquals(0.5, ((Complex) a.mean()).getImaginary(), 1e-6);
//		assertEquals(13., a.var().doubleValue(), 1e-6);
//		assertEquals(3.6055512754639891, a.std().doubleValue(), 1e-6);

	}
}
