/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.image;

// Imports from org.eclipse
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.FFT;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;

// @author Tim Snow

//The operation for a DAWN process to perform a 2D FFT on images
public class FourierTransformImageOperation extends AbstractSimpleImageOperation<FourierTransformImageModel> {

	
	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.FourierTransformImageOperation";
	}


	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}


	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
		
	// Now let's define the processImage method
	@Override
	public IDataset processImage(IDataset iDataset, IMonitor monitor) throws OperationException {

		// First we need to create a placeholder for typecasting the data as the FFT algorithm can't take integers (needs floats)
		Dataset floatDataset;
		// Now, let's typecast the data
		floatDataset = DatasetUtils.cast(FloatDataset.class, iDataset);
		
		// Now we need a container for the transformed data
		IDataset transformedData;
		// Now we can do the FFT itself
		transformedData = FFT.fft2(floatDataset, null, null);

		// Strip out the imaginary component of the FFT result
		transformedData = DatasetUtils.cast(FloatDataset.class, transformedData);

		// And then return it!
		return transformedData;
		
	}

	
	// Now let's define the process method
	@Override
	protected OperationData process(IDataset iDataset, IMonitor monitor) throws OperationException {

		// Let's create somewhere for the data
		IDataset transformedData;
		
		// Then use the processImage routine to transform the data
		transformedData = processImage(iDataset, monitor);

		// Now we can make the convert the IDataset to an OperationData(set), ready to return it to DAWN
		OperationData toReturn = new OperationData(transformedData);
		
		// And then return it!
		return toReturn;
	}
	
}
