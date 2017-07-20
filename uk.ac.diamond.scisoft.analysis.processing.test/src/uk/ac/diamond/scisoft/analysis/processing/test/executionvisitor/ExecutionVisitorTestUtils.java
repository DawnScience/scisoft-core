/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import java.util.Arrays;

import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LazyDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

public class ExecutionVisitorTestUtils {



public static ILazyDataset getLazyDataset(int[] dsShape, int withAxes) {
		
		int size = 1;
		
		for (int i : dsShape) size *= i;
		
		Dataset d = DatasetFactory.createRange(IntegerDataset.class, size);
		d.setShape(dsShape);
	
		ILazyDataset lz = LazyDataset.createLazyDataset(d);
		
		if (withAxes > 0) {
			AxesMetadata am = null;
			try {
				am = MetadataFactory.createMetadata(AxesMetadata.class, dsShape.length);
				for (int j = 0; j < withAxes; j++) {
					for (int i = 0; i < dsShape.length; i++) {
						Dataset ax = DatasetFactory.createRange(ShortDataset.class, 0, dsShape[i], 1);
						ax.iadd(j);
						int[] shape = new int[dsShape.length];
						Arrays.fill(shape, 1);
						shape[i] = dsShape[i];
						ax.setShape(shape);
						if (j == 0) ax.setName("Axis_" + String.valueOf(i));
						else ax.setName("Axis_" + String.valueOf(i)+"_"+String.valueOf(j));
						am.addAxis(i, ax);
					}
				}
			} catch (MetadataException e) {
				// do nothing
			}

			lz.setMetadata(am);
		}
		
		
		lz.setName("mainlazy");
		
		return lz;
	}
	
}
