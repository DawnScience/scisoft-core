/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

public class FunctionOperation extends AbstractOperation<FunctionModel, OperationData> {

	private IFunction      function;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.functionOperation";
	}
	
	@Override
    public String getName() {
		return "Function";
	}


	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		return new OperationData(getFunction().calculateValues(slice));
	}

	private IFunction getFunction() {
        if (function!=null) return function;
        return model.getFunction();
	}

	@Override
	public void setModel(FunctionModel model) {
		super.setModel(model);
		try {
			this.function = (IFunction)model.get("function");
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			function = null;
		}
	}
	
	public OperationRank getInputRank() {
		return OperationRank.ANY; 
	}
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

}
