/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;

import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;

@Atomic
public class SubtractExternalFrameOperation extends AbstractExternalFrameOperation {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractExternalFrameOperation";
	}

	@Override
	protected Dataset performOperation(Dataset input, Dataset other) {
		return ErrorPropagationUtils.subtractWithUncertainty(DatasetUtils.convertToDataset(input), other);
	}
}
