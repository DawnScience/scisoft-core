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
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

@Atomic
public class MultiplyInternalMatrixOperation<T extends InternalDataModel> extends AbstractOperation<T, OperationData> {

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

	protected IDataset getDatasetFromModel(IDataset input) {
		String dataPath = input.getFirstMetadata(SliceFromSeriesMetadata.class).getFilePath();
		return ProcessingUtils.getDataset(this, dataPath, model.getDatasetName());
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) {

		DoubleDataset A = DatasetUtils.cast(DoubleDataset.class, DatasetUtils.convertToDataset(input));
		DoubleDataset B = DatasetUtils.cast(DoubleDataset.class, getDatasetFromModel(input));
		
		SimpleMatrix matrixA = new SimpleMatrix(A.getShapeRef()[0], A.getShapeRef()[1], true, A.getData());
		SimpleMatrix matrixB = new SimpleMatrix(B.getShapeRef()[0], B.getShapeRef()[1], true, B.getData());
		
		SimpleMatrix matrixC = matrixA.mult(matrixB);
		
		DenseMatrix64F matrix = matrixC.getMatrix();
		
		return new OperationData(DatasetFactory.createFromObject(matrix.getData(), matrix.getNumRows(), matrix.getNumCols()));
	}

}
