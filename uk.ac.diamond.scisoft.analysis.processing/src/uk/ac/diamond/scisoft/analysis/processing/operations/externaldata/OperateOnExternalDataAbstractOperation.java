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
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public abstract class OperateOnExternalDataAbstractOperation extends
		AbstractOperation<ExternalDataModel, OperationData> {
	
	@Override
	final public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	final public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		// Shamelessly ripped from the old MultiplyExternalDataOperation class
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		Dataset inputData = DatasetUtils.convertToDataset(input);
		
		String externalDataPath = model.getFilePath();
		// If no path is provided, then the external data comes from the
		// data's own file
		if (externalDataPath == null) externalDataPath = ssm.getFilePath();
		
		ILazyDataset lz = ProcessingUtils.getLazyDataset(this, externalDataPath, model.getDatasetName());
		IDataset val = null;
		
		if (AbstractDataset.squeezeShape(lz.getShape(), false).length == 0) {
			// scalar lz
			val = lz.getSlice();
		} else {
			// vector lz
			val = ssm.getMatchingSlice(lz);
		}

		// If a matching val was not found, throw
		if (val == null) throw new OperationException(this, "Dataset " + model.getDatasetName() + " " + Arrays.toString(lz.getShape()) + 
				" not a compatable shape with " + Arrays.toString(ssm.getParent().getShape()));
		val.squeeze();

		// A non-scalar val is an error at this point
		if (val.getRank() != 0) throw new OperationException(this, "External data shape invalid");

		Dataset output = new DoubleDataset();
		output = doMathematics(inputData, val.getDouble());
		// copy metadata, except for the error metadata
		copyMetadata(input, output);
		
		return new OperationData(output);
		}
	
	protected abstract Dataset doMathematics(Dataset a, double b); 
	
}
