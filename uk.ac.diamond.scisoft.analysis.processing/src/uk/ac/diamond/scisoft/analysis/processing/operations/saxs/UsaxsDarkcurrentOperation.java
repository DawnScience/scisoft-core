/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from uk.ac.diamond.scisoft
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;

// Importing the logger!
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@author Tim Snow


public class UsaxsDarkcurrentOperation extends AbstractOperation<UsaxsDarkcurrentModel, OperationData> {
	
	
	// First, set up a logger
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UsaxsDarkcurrentOperation.class);
	
	
	// Then the private variables for this class
	private double darkcurrentValue;
	private String storedDarkcurrentFilePath;
	private String storedDarkcurrentDatasetPath;
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.UsaxsDarkcurrentOperation";
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
		// First pull through the required locations as strings
		String darkcurrentFilePath = model.getDarkcurrentScanFilepath();
		String darkcurrentDatasetPath = model.getDatasetLocation();
		
		// If these match stored values, we can omit working out the darkcurrent value
		if ((this.storedDarkcurrentFilePath != darkcurrentFilePath) && (this.storedDarkcurrentDatasetPath != darkcurrentDatasetPath)) {
			// If not, then extract out the darkcurrent dataset
			Dataset darkcurrentDataset = (Dataset) ProcessingUtils.getDataset(this, darkcurrentFilePath, darkcurrentDatasetPath);
			
			// and find the average value in that dataset, i.e. the darkcurrent itself
			this.darkcurrentValue = Maths.divide(darkcurrentDataset.sum(null), darkcurrentDataset.getSize()).getDouble();
			
			// Then set the filepath and dataset paths in this class to match the location of where the darkcurrent was obtained from
			this.storedDarkcurrentFilePath = darkcurrentFilePath;
			this.storedDarkcurrentDatasetPath = darkcurrentDatasetPath;
		}
		
		// With the value in hand, or stored, let's subtract this from the input data
		Dataset output = Maths.subtract(input, darkcurrentValue);
		copyMetadata(input, output);
		
		// and return it!
		return new OperationData(output);
	}
}
