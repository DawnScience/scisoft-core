/*
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.metadata;

import static org.junit.Assert.*;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.metadata.ErrorMetadataImpl;
import org.junit.Test;

public class AxesMetadataTest {

	ILazyDataset createRandomLazyDataset(String name, final int dtype, final int... shape) {
		LazyDataset ld = new LazyDataset(name, dtype, shape, new ILazyLoader() {
			final Dataset d = Random.randn(shape).cast(dtype);
			@Override
			public boolean isFileReadable() {
				return true;
			}
			@Override
			public Dataset getDataset(IMonitor mon, int[] shape, int[] start, int[] stop, int[] step)
					throws Exception {
				return d.getSlice(mon, start, stop, step);
			}
		});
		return ld;
	}

	@Test
	public void testAxesMetadata() {
		final int[] shape = new int[] {1, 1, 2, 3, 4, 1, 1};

		int r = shape.length;
		AxesMetadataImpl amd = new AxesMetadataImpl(r);
		for (int i = 0; i < r; i++) {
			DoubleDataset[] array = new DoubleDataset[i + 1];
			for (int j = 0; j < (i + 1) ; j++) {
				array[j] = Random.randn(shape);
			}			
			amd.setAxis(i, array);
		}


		ILazyDataset dataset = createRandomLazyDataset("Main", Dataset.INT32, shape);
		dataset.addMetadata(amd);

		try {
			AxesMetadata tmd = dataset.getMetadata(AxesMetadata.class).get(0);
			assertEquals(amd, tmd);
			assertEquals(r, tmd.getAxes().length);
			for (int i = 0; i < r; i++) {
				assertEquals(i + 1, tmd.getAxis(i).length);
			}
			assertEquals(r, tmd.getAxis(0)[0].getRank());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		dataset.squeeze();
		r = dataset.getRank();
		try {
			AxesMetadata tmd = dataset.getMetadata(AxesMetadata.class).get(0);
			assertEquals(amd, tmd);
			assertEquals(r, tmd.getAxes().length);
			for (int i = 0; i < r; i++) {
				assertEquals(i + 3, tmd.getAxis(i).length);
			}
			assertEquals(r, tmd.getAxis(0)[0].getRank());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		Slice[] slice = new Slice[] {new Slice(1), null, new Slice(null, null, 2)};
		ILazyDataset sliced = dataset.getSliceView(slice);
		int[] nshape = new int[] {1, 3, 2};
		assertArrayEquals(nshape, sliced.getShape());
		try {
			AxesMetadata tmd = sliced.getMetadata(AxesMetadata.class).get(0);
			assertEquals(sliced.getRank(), tmd.getAxes().length);
			assertEquals(3, tmd.getAxis(0).length);
			assertArrayEquals(nshape, tmd.getAxis(0)[0].getShape());
			assertEquals(4, tmd.getAxis(1).length);
			assertArrayEquals(nshape, tmd.getAxis(1)[0].getShape());
			assertEquals(5, tmd.getAxis(2).length);
			assertArrayEquals(nshape, tmd.getAxis(2)[0].getShape());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		nshape = new int[] {3, 2, 1, 1};
		sliced.setShape(nshape);
		try {
			AxesMetadata tmd = sliced.getMetadata(AxesMetadata.class).get(0);
			assertEquals(sliced.getRank(), tmd.getAxes().length);
			assertArrayEquals(nshape, tmd.getAxis(0)[0].getShape());
			assertArrayEquals(null, tmd.getAxis(2));
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		nshape = new int[] {1, 1, 3, 2};
		sliced.setShape(nshape);
		try {
			AxesMetadata tmd = sliced.getMetadata(AxesMetadata.class).get(0);
			assertEquals(sliced.getRank(), tmd.getAxes().length);
			assertArrayEquals(nshape, tmd.getAxis(2)[0].getShape());
			assertArrayEquals(null, tmd.getAxis(0));
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}
	}

	@Test
	public void testAxesMetadataReshape() {
		final int[] shape = new int[] { 1, 2, 3, 1 };
		final int[] reshape = new int[] { 1, 1, 2, 3, 1 };

		int r = shape.length;
		int[] nShape = new int[r];
		AxesMetadataImpl amd = new AxesMetadataImpl(r);
		for (int i = 0; i < r; i++) {
			Arrays.fill(nShape, 1);
			nShape[i] = shape[i];
			DoubleDataset array = Random.randn(nShape);
			amd.setAxis(i, array);
		}
		ILazyDataset dataset = createRandomLazyDataset("Main", Dataset.INT32, shape);
		dataset.addMetadata(amd);
		dataset.setShape(reshape);
	}

	@Test
	public void testAxesMetadataReshapeEmpty() throws Exception {
		final int[] shape = new int[] { 1, 2, 3, 1 };

		int r = shape.length;

		ILazyDataset dataset = createRandomLazyDataset("Main", Dataset.INT32, shape);

		AxesMetadataImpl amd = new AxesMetadataImpl(r);
		dataset.addMetadata(amd);

		ILazyDataset v = dataset.getSliceView();
		v.squeeze();
		assertEquals(2, v.getMetadata(AxesMetadata.class).get(0).getAxes().length);

		IDataset d = v.getSlice(new Slice(1), null);
		assertEquals(2, d.getMetadata(AxesMetadata.class).get(0).getAxes().length);

		final int[] reshape = new int[] { 1, 1, 2, 3, 1 };
		v.setShape(reshape);
		assertEquals(5, v.getMetadata(AxesMetadata.class).get(0).getAxes().length);

		d = v.getSlice((Slice) null, null, new Slice(1));
		assertEquals(5, d.getMetadata(AxesMetadata.class).get(0).getAxes().length);
	}

	@Test
	public void testAxesMetadataRecursion() {
		final int[] shape = new int[] { 1, 2, 3, 1 };

		int r = shape.length;

		ILazyDataset axis = createRandomLazyDataset("axis", Dataset.INT32, 2);
		AxesMetadataImpl amd = new AxesMetadataImpl(1);
		amd.setAxis(0, createRandomLazyDataset("axis2", Dataset.INT32, 2));
		axis.addMetadata(amd);

		amd = new AxesMetadataImpl(r);
		amd.setAxis(1, axis);
		ILazyDataset dataset = createRandomLazyDataset("Main", Dataset.INT32, shape);
		dataset.addMetadata(amd);

		dataset.setShape(2,3,1,1);

		
		ErrorMetadataImpl emd = new ErrorMetadataImpl();
		ILazyDataset axisErr = createRandomLazyDataset("axis2_err", Dataset.INT32, 2);
		emd.setError(axisErr);
		axis.addMetadata(emd);

		amd = new AxesMetadataImpl(1);
		amd.setAxis(0, axis);
		axisErr.addMetadata(amd);

		axisErr.setShape(2,1);
		axisErr.getSliceView(new Slice(1));
		axisErr.getTransposedView();
	}

	@Test
	public void testAxesMetadataTranspose() throws Exception {
		final int[] shape = new int[] { 1, 2, 3, 4 };
		int r = shape.length;
		int[] nShape = new int[r];
		AxesMetadata amd = new AxesMetadataImpl(r);
		for (int i = 0; i < r; i++) {
			Arrays.fill(nShape, 1);
			nShape[i] = shape[i];
			DoubleDataset array = Random.randn(nShape);
			amd.setAxis(i, array);
		}

		Dataset dataset = Random.rand(shape);
		dataset.addMetadata(amd);

		int[] map = new int[] {3, 1, 2, 0};
		Dataset t = dataset.getTransposedView(map);
		assertArrayEquals(new int[]{4, 2, 3, 1}, t.getShape());
		amd = t.getMetadata(AxesMetadata.class).get(0);

		for (int i = 0; i < r; i++) {
			ILazyDataset a = amd.getAxis(i)[0];
			assertEquals(shape[map[i]], a.getSize());
		}
	}

    @Test
    public void testAxesMetadataError() {
           final int[] shape = new int[] { 1, 2, 3, 1 };

           int r = shape.length;

           ILazyDataset axis = createRandomLazyDataset("axis", Dataset.INT32, 2);
           AxesMetadataImpl amd = new AxesMetadataImpl(1);
           amd.setAxis(0, createRandomLazyDataset("axis2", Dataset.INT32, 2));
           axis.addMetadata(amd);

           amd = new AxesMetadataImpl(r);
           amd.setAxis(1, axis);
           ILazyDataset dataset = createRandomLazyDataset("Main", Dataset.INT32, shape);
           dataset.addMetadata(amd);

           dataset.setShape(2,3,1,1);

           ILazyDataset datasetErr = createRandomLazyDataset("dataset_err", Dataset.INT32, 2, 1, 1, 1);
           dataset.setError(datasetErr);

           ILazyDataset axisErr = createRandomLazyDataset("axis2_err", Dataset.INT32, 2);

           amd = new AxesMetadataImpl(1);
           amd.setAxis(0, axis);
           axisErr.addMetadata(amd);
           axis.setError(axisErr);

           ILazyDataset d=dataset.getSliceView();
           d.squeeze();
           IDataset slice = d.getSlice();
           
           assertTrue(slice != null);
    }
    
    @Test
    public void testSliceFromView(){
    	final int[] shape = new int[] { 3, 10, 11};
    	final int[] ashape = new int[] {3};
    	
    	ILazyDataset dataset = createRandomLazyDataset("Main", Dataset.INT32, shape);
    	ILazyDataset ax = createRandomLazyDataset("Axis", Dataset.INT32, ashape);
    	
    	AxesMetadataImpl amd = new AxesMetadataImpl(shape.length);
        amd.setAxis(0, ax);
        dataset.setMetadata(amd); 
        
        ILazyDataset view = dataset.getSliceView(new Slice(1,2),null,null);
        IDataset slice = view.getSlice();
        assertTrue(slice != null);
    }
}
