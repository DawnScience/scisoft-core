/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.bean.OperationBean;

public class OperationBeanTest {
	
	@Test
	public void testDataDimensionForRank() {
		
		OperationBean b = new OperationBean();
		
		int[] dd = {2,3};
		
		b.setDataDimensions(dd);
		
		assertArrayEquals(dd, b.getDataDimensionsForRank(4));
		
		b = new OperationBean();
		
		b.setDataDimensions(new int[] {-2, -1});
		
		assertArrayEquals(dd, b.getDataDimensionsForRank(4));
		
        b = new OperationBean();
		
		b.setScanRank(2);
		
		assertArrayEquals(dd, b.getDataDimensionsForRank(4));
		
		b.setScanRank(1);
		
		assertArrayEquals(new int[] {1,2}, b.getDataDimensionsForRank(3));
		
        b.setScanRank(1);
		
		assertArrayEquals(new int[] {1}, b.getDataDimensionsForRank(2));
		
		b.setScanRank(0);
			
		assertArrayEquals(new int[] {0,1}, b.getDataDimensionsForRank(2));
		
	}

}
