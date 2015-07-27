/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.EmptyModel;

@Atomic
public class MinMaxNormalize extends AbstractOperation<EmptyModel, OperationData> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.MinMaxNormalize";
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		// Minimum and range
		double minim = (Double) input.min(true);
		double range = (Double) input.max(true) - minim;
		
		// Subtract and divide to normalize
		Dataset output = Maths.subtract(input, minim);
		output.idivide(range);

		// Propagate errors. Ignores the error on the minimum and maximum values.
		ILazyDataset el = input.getError();
		IDataset eb = null;
		if (el != null) eb = el.getSlice();
		
		if (eb != null) {
			output.setError(Maths.divide(eb, range));
		}

		// Transfer axes
		copyMetadata(input, output);
		
		return new OperationData(output);
		
	}
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}
