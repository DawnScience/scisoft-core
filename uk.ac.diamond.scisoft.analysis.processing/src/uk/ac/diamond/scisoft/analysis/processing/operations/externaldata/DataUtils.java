/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyMaths;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class DataUtils {

	public static Dataset getInternalFrameAverage(IDataset input, Integer start, Integer end) throws OperationException {
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		
		ILazyDataset lz = ssm.getParent();

		int s = start == null ? 0 : start;
		int e = end == null ? Integer.MAX_VALUE -1 : end;

		int[] dataDims = ssm.getDataDimensions();
		
		if (input.getRank() == 1 && dataDims.length == 2) {
			dataDims = new int[]{dataDims[0]};
		}
		
		Dataset data = null;
		
		if (lz.getRank() == dataDims.length) {
			data = DatasetUtils.sliceAndConvertLazyDataset(lz);
		} else {
			data = LazyMaths.mean(s, e, lz, dataDims).squeeze();
		}
		
		return data;
	}
	
	public static Dataset getExternalFrameAverage(IDataset input, Integer start, Integer end, String path, String name, IOperation op) throws OperationException {
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		
		ILazyDataset lz = ProcessingUtils.getLazyDataset(op, path, name);

		int s = start == null ? 0 : start;
		int e = end == null ? Integer.MAX_VALUE -1 : end;

		int[] dataDims = ssm.getDataDimensions();
		
		if (input.getRank() == 1 && dataDims.length == 2) {
			dataDims = new int[]{dataDims[0]};
		}
		
		Dataset subtrahend = null;
		
		if (lz.getRank() == dataDims.length) {
			subtrahend = DatasetUtils.sliceAndConvertLazyDataset(lz);
		} else {
			subtrahend = LazyMaths.mean(s, e, lz, dataDims).squeeze();
		}
		
		return subtrahend;
	}
	
	public static Dataset getExternalFrameMatching(IDataset input, Integer start, Integer end, String path, String name, IOperation op) throws OperationException {
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		
		ILazyDataset lz = ProcessingUtils.getLazyDataset(op, path, name);

		if (Arrays.equals(lz.getShape(), ssm.getSourceInfo().getParent().getShape())) {
			return DatasetUtils.convertToDataset(lz.getSlice(ssm.getSliceFromInput())).squeeze();
		} 
		
		return null;
	
	}
	
}
