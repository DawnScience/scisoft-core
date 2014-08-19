/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.LazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.dataset.ShortDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.io.ILazyLoader;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import static org.junit.Assert.*;

public class SliceableMetadataTest {

	ILazyDataset createRandomLazyDataset(String name, final int[] shape, final int dtype) {
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
	public void testSlicingMetadata() {
		final int[] shape = new int[] {1, 2, 3, 4};
		ILazyDataset ld = createRandomLazyDataset("Metadata1", shape, Dataset.INT32);

		final DoubleDataset[] dda = new DoubleDataset[] {Random.randn(shape), Random.randn(shape),};

		List<ShortDataset> sdl = new ArrayList<>();
		sdl.add((ShortDataset) Random.randn(shape).cast(Dataset.INT16));
		sdl.add((ShortDataset) Random.randn(shape).cast(Dataset.INT16));

		Map<String, BooleanDataset> bdm = new HashMap<String, BooleanDataset>();
		bdm.put("1", (BooleanDataset) Random.randn(shape).cast(Dataset.BOOL));
		bdm.put("2", (BooleanDataset) Random.randn(shape).cast(Dataset.BOOL));
		
		List<DoubleDataset[]> l2 = new ArrayList<DoubleDataset[]>();
		l2.add(new DoubleDataset[]{Random.randn(shape)});
		l2.add(new DoubleDataset[]{Random.randn(shape)});
		
		SliceableTestMetadata md = new SliceableTestMetadata(ld, dda, sdl, bdm, l2);

		ILazyDataset dataset = createRandomLazyDataset("Main", shape, Dataset.INT32);
		dataset.addMetadata(md);

		try {
			SliceableTestMetadata tmd = dataset.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(md, tmd);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertEquals(2, tmd.getListOfArrays().size());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		Slice[] slice = new Slice[] {null, new Slice(1), null, new Slice(null, null, 2)};
		ILazyDataset sliced = dataset.getSliceView(slice);

		assertArrayEquals(new int[] {1, 1, 3, 2}, sliced.getShape());
		try {
			SliceableTestMetadata tmd = sliced.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertEquals(2, tmd.getListOfArrays().size());
			assertArrayEquals(sliced.getShape(), tmd.getLazyDataset().getShape());
			assertArrayEquals(sliced.getShape(), tmd.getArray()[0].getShape());
			assertArrayEquals(sliced.getShape(), tmd.getList().get(0).getShape());
			assertArrayEquals(sliced.getShape(), tmd.getMap().get("1").getShape());
			assertArrayEquals(sliced.getShape(), tmd.getListOfArrays().get(0)[0].getShape());
			
			//test original unchanged
			tmd = dataset.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertArrayEquals(shape, tmd.getLazyDataset().getShape());
			assertArrayEquals(shape, tmd.getArray()[0].getShape());
			assertArrayEquals(shape, tmd.getList().get(0).getShape());
			assertArrayEquals(shape, tmd.getMap().get("1").getShape());
			assertArrayEquals(shape, tmd.getListOfArrays().get(0)[0].getShape());
			
			IDataset slice2 = sliced.getSlice();
			tmd = slice2.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertArrayEquals(sliced.getShape(), tmd.getLazyDataset().getShape());
			assertArrayEquals(sliced.getShape(), tmd.getArray()[0].getShape());
			assertArrayEquals(sliced.getShape(), tmd.getList().get(0).getShape());
			assertArrayEquals(sliced.getShape(), tmd.getMap().get("1").getShape());
			assertArrayEquals(sliced.getShape(), tmd.getListOfArrays().get(0)[0].getShape());
			
			//test original unchanged
			tmd = dataset.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertArrayEquals(shape, tmd.getLazyDataset().getShape());
			assertArrayEquals(shape, tmd.getArray()[0].getShape());
			assertArrayEquals(shape, tmd.getList().get(0).getShape());
			assertArrayEquals(shape, tmd.getMap().get("1").getShape());
			assertArrayEquals(shape, tmd.getListOfArrays().get(0)[0].getShape());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		SubMetadata smd = new SubMetadata(ld, dda, sdl, bdm, l2);
		dataset.setMetadata(smd);
		sliced = dataset.getSliceView(slice);

		try {
			SubMetadata tmd = dataset.getMetadata(SubMetadata.class).get(0);
			assertEquals(smd, tmd);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertEquals(2, tmd.getListOfArrays().size());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		assertArrayEquals(new int[] {1, 1, 3, 2}, sliced.getShape());
		try {
			SubMetadata tmd = sliced.getMetadata(SubMetadata.class).get(0);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertArrayEquals(sliced.getShape(), tmd.getLazyDataset().getShape());
			assertArrayEquals(sliced.getShape(), tmd.getArray()[0].getShape());
			assertArrayEquals(sliced.getShape(), tmd.getList().get(0).getShape());
			assertArrayEquals(sliced.getShape(), tmd.getMap().get("1").getShape());
			assertArrayEquals(sliced.getShape(), tmd.getLazyDataset2().getShape());
			assertArrayEquals(sliced.getShape(), tmd.getListOfArrays().get(0)[0].getShape());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

	}
	
	@Test
	public void testSlicingSameRankDifferentShapeMetadata() {
		
		final int[] result1 = new int[] {1, 1, 1, 1};
		final int[] result2 = new int[] {1, 1, 3, 1};
		
		final int[] shape = new int[] {1, 2, 3, 4};
		ILazyDataset ld = createRandomLazyDataset("Metadata1", shape, Dataset.INT32);

		final int [] partial1 = new int[] {1, 1, 1, 4};
		final DoubleDataset[] dda = new DoubleDataset[] {Random.randn(partial1), Random.randn(partial1),};

		final int [] partial2 = new int[] {1, 1, 3, 1};
		List<ShortDataset> sdl = new ArrayList<>();
		sdl.add((ShortDataset) Random.randn(partial2).cast(Dataset.INT16));
		sdl.add((ShortDataset) Random.randn(partial2).cast(Dataset.INT16));

		final int [] partial3 = new int[] {1, 2, 1, 1};
		Map<String, BooleanDataset> bdm = new HashMap<String, BooleanDataset>();
		bdm.put("1", (BooleanDataset) Random.randn(partial3).cast(Dataset.BOOL));
		bdm.put("2", (BooleanDataset) Random.randn(partial3).cast(Dataset.BOOL));

		List<DoubleDataset[]> l2 = new ArrayList<DoubleDataset[]>();
		l2.add(new DoubleDataset[]{Random.randn(partial1)});
		l2.add(new DoubleDataset[]{Random.randn(partial1)});
		
		SliceableTestMetadata md = new SliceableTestMetadata(ld, dda, sdl, bdm, l2);

		ILazyDataset dataset = createRandomLazyDataset("Main", shape, Dataset.INT32);
		dataset.addMetadata(md);
		
		try {
			SliceableTestMetadata tmd = dataset.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(md, tmd);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertEquals(2, tmd.getListOfArrays().size());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		Slice[] slice = new Slice[] {null, new Slice(1), null, new Slice(1)};
		ILazyDataset sliced = dataset.getSliceView(slice);

		assertArrayEquals(new int[] {1, 1, 3, 1}, sliced.getShape());
		try {
			SliceableTestMetadata tmd = sliced.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertArrayEquals(result2, tmd.getLazyDataset().getShape());
			assertArrayEquals(result1, tmd.getArray()[0].getShape());
			assertArrayEquals(result2, tmd.getList().get(0).getShape());
			assertArrayEquals(result1, tmd.getMap().get("1").getShape());
			assertArrayEquals(result1, tmd.getListOfArrays().get(0)[0].getShape());
			
			//test original unchanged
			tmd = dataset.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertArrayEquals(shape, tmd.getLazyDataset().getShape());
			assertArrayEquals(partial1, tmd.getArray()[0].getShape());
			assertArrayEquals(partial2, tmd.getList().get(0).getShape());
			assertArrayEquals(partial3, tmd.getMap().get("1").getShape());
			assertArrayEquals(partial1, tmd.getListOfArrays().get(0)[0].getShape());
			
			IDataset slice2 = sliced.getSlice();
			tmd = slice2.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertArrayEquals(result2, tmd.getLazyDataset().getShape());
			assertArrayEquals(result1, tmd.getArray()[0].getShape());
			assertArrayEquals(result2, tmd.getList().get(0).getShape());
			assertArrayEquals(result1, tmd.getMap().get("1").getShape());
			assertArrayEquals(result1, tmd.getListOfArrays().get(0)[0].getShape());
			
			//test original unchanged
			tmd = dataset.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertArrayEquals(shape, tmd.getLazyDataset().getShape());
			assertArrayEquals(partial1, tmd.getArray()[0].getShape());
			assertArrayEquals(partial2, tmd.getList().get(0).getShape());
			assertArrayEquals(partial3, tmd.getMap().get("1").getShape());
			assertArrayEquals(partial1, tmd.getListOfArrays().get(0)[0].getShape());
			
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}
		
		

	}
	
	@Test
	public void testSlicingSameRankDifferentShapeMetadataFail() {
		
		final int[] result1 = new int[] {1, 1, 1, 2};
		final int[] result2 = new int[] {1, 1, 3, 2};
		
		final int[] shape = new int[] {1, 2, 3, 4};
		ILazyDataset ld = createRandomLazyDataset("Metadata1", shape, Dataset.INT32);

		final int [] partial1 = new int[] {1, 1, 1, 4};
		final DoubleDataset[] dda = new DoubleDataset[] {Random.randn(partial1), Random.randn(partial1),};

		final int [] partial2 = new int[] {1, 1, 3, 4};
		List<ShortDataset> sdl = new ArrayList<>();
		sdl.add((ShortDataset) Random.randn(partial2).cast(Dataset.INT16));
		sdl.add((ShortDataset) Random.randn(partial2).cast(Dataset.INT16));

		final int [] partial3 = new int[] {1, 5, 1, 1};
		Map<String, BooleanDataset> bdm = new HashMap<String, BooleanDataset>();
		bdm.put("1", (BooleanDataset) Random.randn(partial3).cast(Dataset.BOOL));
		bdm.put("2", (BooleanDataset) Random.randn(partial3).cast(Dataset.BOOL));
		
		List<DoubleDataset[]> l2 = new ArrayList<DoubleDataset[]>();
		l2.add(new DoubleDataset[]{Random.randn(shape)});
		l2.add(new DoubleDataset[]{Random.randn(shape)});

		SliceableTestMetadata md = new SliceableTestMetadata(ld, dda, sdl, bdm, l2);

		ILazyDataset dataset = createRandomLazyDataset("Main", shape, Dataset.INT32);
		dataset.addMetadata(md);
		
		try {
			SliceableTestMetadata tmd = dataset.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(md, tmd);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		Slice[] slice = new Slice[] {null, new Slice(1), null, new Slice(0,2)};
		ILazyDataset sliced = dataset.getSliceView(slice);

		assertArrayEquals(new int[] {1, 1, 3, 2}, sliced.getShape());
		try {
			SliceableTestMetadata tmd = sliced.getMetadata(SliceableTestMetadata.class).get(0);
			assertEquals(2, tmd.getArray().length);
			assertEquals(2, tmd.getList().size());
			assertEquals(2, tmd.getMap().size());
			assertArrayEquals(new int[] {1, 1, 3, 2}, tmd.getLazyDataset().getShape());
			assertArrayEquals(result1, tmd.getArray()[0].getShape());
			assertArrayEquals(result2, tmd.getList().get(0).getShape());
			//not sliced, shape not compatible
			assertArrayEquals(partial3, tmd.getMap().get("1").getShape());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

	}

}
