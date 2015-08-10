/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.xpdf.XPDFProcessor;

public class XPDFProcessingOperation extends
		AbstractOperation<XPDFProcessingModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

//		System.err.println("*****************************************");
//		System.err.println("Welcome to XPDF Processing: The Operation");
//		System.err.println("*****************************************");
		
		XPDFProcessor proc = new XPDFProcessor(input);
		// set intermediate results until the python script evaporates
		proc.setIntermediateResult("ABSCOR", DatasetUtils.convertToDataset(input));
		// Auxiliary data not yet calculated in Java
		// Background trace metadata. The beam causes the background, after all
		String xyFilePath = "/home/rkl37156/ceria_dean_data/self_scattering.xy";
		// Load the background from the designated xy file
		proc.setIntermediateResult("self_scattering", 
				DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath, "Column_2").getSlice()));
		// Background trace metadata. The beam causes the background, after all
		xyFilePath = "/home/rkl37156/ceria_dean_data/fsquaredofx.xy";
		// Load the background from the designated xy file
		proc.setIntermediateResult("fsquaredofx", 
				DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath, "Column_2").getSlice()));

		
		// Get the parameters from the model
		proc.setR(model.getrMin(), model.getrMax(), model.getrStep());
		proc.setTophatWidth(model.getTophatWidth());
		proc.setLorchWidth(model.getLorchWidth());

		// Generate the data
		IDataset dofr = proc.getDofr();
		// add the x-axis
		Dataset r = proc.getR();
		AxesMetadataImpl ax = new AxesMetadataImpl(1);
		ax.addAxis(0, r);
		dofr.addMetadata(ax);
		
		return new OperationData(dofr);
		
	}
	
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFProcessingOperation";
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
