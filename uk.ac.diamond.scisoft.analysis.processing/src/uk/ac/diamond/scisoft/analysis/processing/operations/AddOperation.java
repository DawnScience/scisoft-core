/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.ValueModel;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

/**
 * Subtracts either one dataset from another or a scalar value from all values of a dataset.
 * @author Matthew Gerring
 *
 */
public class AddOperation extends AbstractMathsOperation<ValueModel, OperationData> {

	protected IDataset operation(IDataset a, Object value) {
		return a instanceof Dataset ? ((Dataset) a).iadd(value) : Maths.add(a, value);
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.addOperation";
	}

	@Override
    public String getName() {
		return "Add datasets";
	}

}
