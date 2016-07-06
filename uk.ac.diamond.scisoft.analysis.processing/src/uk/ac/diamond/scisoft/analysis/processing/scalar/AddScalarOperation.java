/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.scalar;

import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

public class AddScalarOperation extends AbstractScalarMathsOperation<ScalarModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.scalar.AddScalarOperation";
	}

	@Override
	protected IDataset doMaths(IDataset data, double value) {
		
		IDataset output = data.getSlice();
		DatasetUtils.convertToDataset(output).iadd(value);
		
		return output;
	}

}
