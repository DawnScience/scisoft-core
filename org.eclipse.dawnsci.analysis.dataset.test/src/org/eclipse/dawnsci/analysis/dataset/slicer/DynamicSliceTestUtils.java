package org.eclipse.dawnsci.analysis.dataset.slicer;

import org.eclipse.dawnsci.analysis.dataset.MockDynamicLazyDataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IntegerDataset;

public class DynamicSliceTestUtils {
	
	public static MockDynamicLazyDataset getData(int max) {
		DoubleDataset d = DatasetFactory.createRange(DoubleDataset.class,max);
		d.setShape(new int[]{max,1,1});
		d = (DoubleDataset)DatasetUtils.tile(d, new int[]{1, 10, 11});
		
		int[][] shapes = new int[max][];
		
		for (int i = 0; i < max; i++) {
			shapes[i] = new int[] {i+1,10,11};
		}
		
		return new MockDynamicLazyDataset(shapes, d);
	}
	
	public static MockDynamicLazyDataset getKey(int max) {
		IntegerDataset key = DatasetFactory.zeros(IntegerDataset.class, new int[]{max});
		
		int[][] shapes = new int[max][];
		
		for (int i = 0; i < max; i++) {
			shapes[i] = new int[] {i+1};
		}
		
		return new MockDynamicLazyDataset(shapes, key);
	}
	
	public static MockDynamicLazyDataset getFinished() {
		IntegerDataset finished = DatasetFactory.zeros(IntegerDataset.class, new int[]{1});
		return new MockDynamicLazyDataset(new int[][]{new int[]{1}}, finished);
	}

}
