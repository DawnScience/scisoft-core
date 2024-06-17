/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Tim Snow


package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.io.Serializable;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.processing.scalar.AddScalarOperation;
import uk.ac.diamond.scisoft.analysis.processing.scalar.DivideScalarOperation;
import uk.ac.diamond.scisoft.analysis.processing.scalar.MutliplyScalarOperation;
import uk.ac.diamond.scisoft.analysis.processing.scalar.ScalarModel;
import uk.ac.diamond.scisoft.analysis.processing.scalar.SubtractScalarOperation;



@Atomic
public class FrameMathsFromPeakOperation extends AbstractOperation<FrameMathsFromPeakModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.twod.FrameMathsFromPeakOperation";
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
	public OperationData process(IDataset inputDataset, IMonitor monitor) throws OperationException {
		// First find the peak and crop around it
		CropAroundPeak2DModel cropAroundPeak2DModel = new CropAroundPeak2DModel();
		cropAroundPeak2DModel.setXBoxLength(model.getXBoxLength());
		cropAroundPeak2DModel.setYBoxLength(model.getYBoxLength());
		
		CropAroundPeak2DOperation cropAroundPeak2DOperation = new CropAroundPeak2DOperation();
		cropAroundPeak2DOperation.setModel(cropAroundPeak2DModel);
		OperationData cropData = cropAroundPeak2DOperation.execute(inputDataset, monitor);
		IDataset outputDataset = cropData.getData();

		/// Find the sum of this value
		Dataset datasetToSum = DatasetUtils.cast(DoubleDataset.class, outputDataset);
		Double intensitySum = (Double) datasetToSum.sum(true);
		
		// Manipulate the frame accordingly
		ScalarModel scalarModel = new ScalarModel();
		scalarModel.setValue(intensitySum);
		OperationData returnData = null;

		switch (model.getMathematicalOperator()) {
		
			case ADD:		AddScalarOperation addScalarOperation = new AddScalarOperation();
							addScalarOperation.setModel(scalarModel);
							returnData = addScalarOperation.execute(inputDataset, monitor);
							break;
						
			case SUBTRACT:	SubtractScalarOperation subtractScalarOperation = new SubtractScalarOperation();
							subtractScalarOperation.setModel(scalarModel);
							returnData = subtractScalarOperation.execute(inputDataset, monitor);
							break;
						
			case DIVIDE:	DivideScalarOperation divideScalarOperation = new DivideScalarOperation();
							divideScalarOperation.setModel(scalarModel);
							returnData = divideScalarOperation.execute(inputDataset, monitor);
							break;
						
			case MULTIPLY:	MutliplyScalarOperation multiplyScalarOperation = new MutliplyScalarOperation();
							multiplyScalarOperation.setModel(scalarModel);
							returnData = multiplyScalarOperation.execute(inputDataset, monitor);
							break;
						
			default:		throw new OperationException(this, "The mathematical operator selected is not supported.");

		}

		Serializable[] cropAuxData = cropData.getAuxData();
		Serializable[] auxData = Arrays.copyOf(cropAuxData, cropAuxData.length + 1);
		auxData[cropAuxData.length] = ProcessingUtils.createNamedDataset(intensitySum, "integrated_peak");
		returnData.setAuxData(auxData);
		return returnData;
	}
}