/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.visitor;


import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.RunningAverage;


public class AveragingOutputExecutionVisitor implements IExecutionVisitor {

	private RunningAverage average;
	
	@Override
	public void init(IOperation<? extends IOperationModel, ? extends OperationData>[] series, ILazyDataset dataset)
			throws Exception {
	}

	@Override
	public void close() throws Exception {

	}

	@Override
	public void notify(IOperation<? extends IOperationModel, ? extends OperationData> intermediateData,
			OperationData data) {

	}

	@Override
	public void executed(OperationData result, IMonitor monitor) throws Exception {
		if (result == null) return;
		if (average == null) average = new RunningAverage(result.getData());
	}
	
	public Dataset getAverage(){
		return average == null ? null : average.getCurrentAverage();
	}

}
