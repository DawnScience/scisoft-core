/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

public class SumOperation extends AbstractOperation<EmptyModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.SumOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		Dataset in = DatasetUtils.convertToDataset(input);
		
		Dataset sqrt = Maths.sqrt(in);
		Dataset inputSum = DatasetFactory.createFromObject(in.sum());
		Dataset sqrtSum = DatasetFactory.createFromObject(sqrt.sum());
		Dataset inputSumError = null;
		Dataset inputSqrtSumError = null;
		if (in.hasErrors()) {
			inputSumError = Maths.multiply(in.getErrors().rootMeanSquare(true), Math.sqrt(in.getSize()));
			inputSumError.setName("dsum");
			inputSqrtSumError = Maths.sqrt(DatasetFactory.createFromObject(in.getErrors().sum()));
			inputSqrtSumError.setName("dsum_of_sqrt");
		}
		inputSum.setErrors(inputSumError);
		inputSum.setName("sum");
		sqrtSum.setErrors(inputSqrtSumError);
		sqrtSum.setName("sum_of_sqrt");
	
		return new OperationData(input, inputSum, sqrtSum);
		
	}

}
