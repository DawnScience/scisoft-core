/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.oned;


// Imports from uk.ac.diamond.scisoft
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetUtils;

// Importing the logger!
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@author Tim Snow


public class SetAxisOperation extends AbstractOperation<SetAxisModel, OperationData> {
	
	
	// First, set up a logger
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SetAxisOperation.class);
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.SetAxisOperation";
	}
	
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}
	
	
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) {
		String dataPath = input.getFirstMetadata(SliceFromSeriesMetadata.class).getFilePath();
		Dataset xAxis = DatasetUtils.convertToDataset(ProcessingUtils.getDataset(this, dataPath, model.datasetLocation));
		
		AxesMetadata xAxisMetadata = input.getFirstMetadata(AxesMetadata.class);
		xAxisMetadata.setAxis(0, xAxis);
		input.setMetadata(xAxisMetadata);	
		
		// and return it!
		return new OperationData(input);
	}
}
