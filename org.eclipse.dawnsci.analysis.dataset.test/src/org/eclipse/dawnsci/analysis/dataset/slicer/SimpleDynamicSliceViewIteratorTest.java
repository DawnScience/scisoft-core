package org.eclipse.dawnsci.analysis.dataset.slicer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.dawnsci.analysis.dataset.MockDynamicLazyDataset;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.junit.Test;

public class SimpleDynamicSliceViewIteratorTest {
	
	@Test
	public void testFail() {
		
		int fastest = 6;
		
		MockDynamicLazyDataset mock = DynamicSliceTestUtils.getData(fastest);
		MockDynamicLazyDataset mockkey = DynamicSliceTestUtils.getKey(fastest);
		
		SimpleDynamicSliceViewIterator iterator = new SimpleDynamicSliceViewIterator(mock, mockkey, 2, 10);
		iterator.setMaxTimeout(1000);

		boolean hasNext = iterator.hasNext();
		
		assertFalse(hasNext);
	}
	
	@Test
	public void testOneStep() throws DatasetException {
		
		int fastest = 6;
		
		MockDynamicLazyDataset mock = DynamicSliceTestUtils.getData(fastest);
		MockDynamicLazyDataset mockkey = DynamicSliceTestUtils.getKey(fastest);
		
		mockkey.getParent().set(1, 0);
		mock.setAllowIncrement(true);
		mockkey.setAllowIncrement(true);
		
		SimpleDynamicSliceViewIterator iterator = new SimpleDynamicSliceViewIterator(mock, mockkey, 2, 10);
		iterator.setMaxTimeout(1000);

		boolean hasNext = iterator.hasNext();
		assertTrue(hasNext);
		iterator.next();
		
		hasNext = iterator.hasNext();
		assertFalse(hasNext);

	}
	
	@Test
	public void testPass() throws DatasetException {
		
		int fastest = 6;
		
		MockDynamicLazyDataset mock = DynamicSliceTestUtils.getData(fastest);
		MockDynamicLazyDataset mockkey = DynamicSliceTestUtils.getKey(fastest);
		
		mockkey.getParent().iadd(1);
		mock.setAllowIncrement(true);
		mockkey.setAllowIncrement(true);
		
		SimpleDynamicSliceViewIterator iterator = new SimpleDynamicSliceViewIterator(mock, mockkey, 2, 10);
		iterator.setMaxTimeout(1000);
		
		for (int i = 0 ; i < fastest-1; i++) {
			boolean hasNext = iterator.hasNext();
			assertTrue(hasNext);
			Dataset slice = DatasetUtils.convertToDataset(iterator.next().getSlice());
			assertEquals(i*10*11, ((Number) slice.sum()).doubleValue(),0.000001);
		}
	}
}
