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
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.dataset.function.SavitzkyGolay;

public class SavitzkyGolayOperation extends AbstractOperation<SavitzkyGolayModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.SavitzkyGolayOperation";
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
		Dataset rv = null;
		try {
			rv = SavitzkyGolay.filter(input, model.getWindow(), model.getPoly(), model.getDeriv());
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		
		copyMetadata(input, rv);
		
		return new OperationData(rv);
	}

}
