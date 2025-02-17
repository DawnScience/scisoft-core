/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.scalar;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;

@Atomic
public class MutliplyScalarOperation extends AbstractScalarMathsOperation<ScalarModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.scalar.MutliplyScalarOperation";
	}

	@Override
	protected IDataset doMaths(IDataset data, double value) {
		return ErrorPropagationUtils.multiplyWithUncertainty(DatasetUtils.convertToDataset(data),DatasetFactory.createFromObject(value));
	}

}
