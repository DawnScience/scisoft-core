/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;

import uk.ac.diamond.scisoft.analysis.optimize.ApachePolynomial;

@Atomic
public class PolynomialSmoothingOperation extends AbstractOperation<PolynomialSmoothModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.PolynomialSmoothingOperation";
	}

	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		if (model.getPolynomial() > model.getWindow()) throw new OperationException(this,"Polynomial should be less than window size");
		
		ILazyDataset[] firstAxes = getFirstAxes(input);
		IDataset ax;
		if (firstAxes == null || firstAxes[0] == null) {
			ax = DatasetFactory.createRange(input.getShape()[0], Dataset.INT64);
		} else {
			try {
				ax = firstAxes[0].getSlice();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}
		
		Dataset out = DatasetUtils.convertToDataset(input);
		Dataset a = DatasetUtils.convertToDataset(ax);
		
		try {
			out = ApachePolynomial.getPolynomialSmoothed(a, out, model.getWindow(), model.getPolynomial());
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		
		copyMetadata(input, out);
		
		return new OperationData(out);
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
