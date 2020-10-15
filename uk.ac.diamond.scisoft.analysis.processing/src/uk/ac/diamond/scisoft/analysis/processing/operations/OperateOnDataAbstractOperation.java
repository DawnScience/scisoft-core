/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ShapeUtils;

import uk.ac.diamond.scisoft.analysis.processing.operations.internaldata.InternalDataModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public abstract class OperateOnDataAbstractOperation<T extends InternalDataModel> extends AbstractOperation<T, OperationData> {
	
	@Override
	public final OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public final OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		Dataset inputData = DatasetUtils.convertToDataset(input);
		
		String dataPath = getFilePath(input);
		
		IDataset val = ProcessingUtils.getMatchingValue(this, input, dataPath, model.getDatasetName());
		
		Dataset output = doMathematics(inputData, DatasetUtils.convertToDataset(val));
		// copy metadata, except for the error metadata
		copyMetadata(input, output);
		
		return new OperationData(output);
		}
	
	protected abstract Dataset doMathematics(Dataset a, Dataset b); 
	
	protected abstract String getFilePath(IDataset input);
	
}
