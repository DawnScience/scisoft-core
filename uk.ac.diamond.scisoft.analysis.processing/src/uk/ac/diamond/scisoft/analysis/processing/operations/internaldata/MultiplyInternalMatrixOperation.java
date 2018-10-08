/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.internaldata;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

@Atomic
public class MultiplyInternalMatrixOperation extends AbstractOperation<InternalDataModel, OperationData> {

	private static final Logger logger = LoggerFactory.getLogger(MultiplyInternalMatrixOperation.class);
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.internaldata.MultiplyInternalMatrixOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) {
		String dataPath = input.getFirstMetadata(SliceFromSeriesMetadata.class).getFilePath();

		ILazyDataset lz = ProcessingUtils.getLazyDataset(this, dataPath, model.getDatasetName());
		DoubleDataset A = DatasetUtils.cast(DoubleDataset.class, DatasetUtils.convertToDataset(input));
		DoubleDataset B;
		try {
			B = DatasetUtils.cast(DoubleDataset.class, DatasetUtils.sliceAndConvertLazyDataset(lz));
		} catch (DatasetException e) {
			logger.error("Could not convert lazy dataset to dataset", e);
			throw new OperationException(this, e);
		}
		
		SimpleMatrix matrixA = new SimpleMatrix(A.getShapeRef()[0], A.getShapeRef()[1], true, A.getData());
		SimpleMatrix matrixB = new SimpleMatrix(B.getShapeRef()[0], B.getShapeRef()[1], true, B.getData());
		
		SimpleMatrix matrixC = matrixA.mult(matrixB);
		
		DenseMatrix64F matrix = matrixC.getMatrix();
		
		return new OperationData(DatasetFactory.createFromObject(matrix.getData(), matrix.getNumRows(), matrix.getNumCols()));
	}

}
