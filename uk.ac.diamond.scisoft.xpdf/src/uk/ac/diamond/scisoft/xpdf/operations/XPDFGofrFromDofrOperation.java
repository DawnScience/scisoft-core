/*
 * Copyright (c) 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Convert back from D(r) to G(r).
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
@Atomic
public class XPDFGofrFromDofrOperation extends AbstractOperation<EmptyModel, OperationData> {

	protected OperationData process(IDataset dofr, IMonitor monitor) throws OperationException {

		// Get the number density from the sample metadata
		if (dofr.getFirstMetadata(XPDFMetadata.class) == null) throw new OperationException(this, "XPDF metadata not found.");
		XPDFMetadata theXPDFMetadata = dofr.getFirstMetadata(XPDFMetadata.class);
		
		if (dofr.getFirstMetadata(AxesMetadata.class) == null) throw new OperationException(this, "Axis metadata not found.");
		Dataset r;
		try {
			r = DatasetUtils.sliceAndConvertLazyDataset(dofr.getFirstMetadata(AxesMetadata.class).getAxes()[0]);
		} catch (DatasetException e) {
			throw new OperationException(this, e);
		}
		
		double numberDensity = theXPDFMetadata.getSample().getNumberDensity();
		Dataset gofr = Maths.divide(Maths.divide(dofr, 4*Math.PI*numberDensity), r);
		
		// Error propagation
		if (dofr.getErrors() != null){
			Dataset dofrErrors;
			try {
				dofrErrors = DatasetUtils.sliceAndConvertLazyDataset(dofr.getErrors());
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
			Dataset gofrErrors = Maths.divide(Maths.divide(dofrErrors, 4*Math.PI*numberDensity), r);
			gofr.setErrors(gofrErrors);
		}
		
		copyMetadata(dofr, gofr);
		
		gofr.setName("G(r)");
		
		return new OperationData(gofr);
	}
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFGofrFromDofrOperation";
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
