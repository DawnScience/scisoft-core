/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.examples;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class ExampleExternalDataNormalisationOperation extends
		AbstractOperation<ExampleExternalDataModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.examples.ExampleExternalDataNormalisationOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		//will not be null
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		ILazyDataset lz = ProcessingUtils.getLazyDataset(this, model.getFilePath(), model.getDatasetName());

		IDataset val;
		try {
			val = ssm.getMatchingSlice(lz).squeeze();
		} catch (DatasetException e) {
			throw new OperationException(this, e);
		}
		
		if (val.getRank() != 0) throw new OperationException(this, "External data shape invalid");
		
		Dataset out = Maths.divide(input, val.getDouble());
		
		return new OperationData(out);
	}
}
