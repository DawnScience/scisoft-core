/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.io.ILoaderService;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.IMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.osgi.services.ServiceProvider;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;

@Atomic
public class ConvertToQOperation extends AbstractOperationBase<EmptyModel, OperationData>  {

	static final Dataset FOURPI = DatasetFactory.createFromObject(4 * Math.PI);
	private static transient final Logger logger = LoggerFactory.getLogger(ConvertToQOperation.class);

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.ConvertToQOperation";
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
	
	@Override
	public OperationData execute(IDataset data, IMonitor monitor) {
		IDataset view = data.getSliceView();

		AxesMetadata axesMetadata = view.getFirstMetadata(AxesMetadata.class);
		if (axesMetadata == null) throw new OperationException(this, "No metadata found!");
		
		ILazyDataset [] thetaAxis = axesMetadata.getAxes();
		Dataset thetaDeg;
		try {
			thetaDeg = DatasetUtils.sliceAndConvertLazyDataset(thetaAxis[0]);
		} catch (DatasetException sliceAxisError) {
			throw new OperationException(this, sliceAxisError);
		}
		
		// Get the value and uncertainty to be added to it
		IMetadata metadata;
		Serializable metaValue = 12.5;
		try {
			String filePath = data.getFirstMetadata(SliceFromSeriesMetadata.class).getFilePath();
			metadata = ServiceProvider.getService(ILoaderService.class).getMetadata(filePath, null);
			metaValue = metadata.getMetaValue("dcm1energy");
		} catch (Exception e) {
			// Really this should never happen, this is a common value for i07 and is in place to 
			// aid testing of the function
			logger.error("No value for the radiation energy was found so a default of 12.5 keV was used");
		}
		
		// Convert from degrees to radians		
		Dataset theta = ErrorPropagationUtils.multiplyWithUncertainty(thetaDeg, DatasetFactory.createFromObject(Math.toRadians(1)));

		Dataset lambdaD = DatasetFactory.createFromObject(DiffractionCrystalEnvironment.calculateWavelength(Double.parseDouble(metaValue.toString())));
		Dataset q = ErrorPropagationUtils.multiplyWithUncertainty(ErrorPropagationUtils.divideWithUncertainty(ErrorPropagationUtils.sineWithUncertainty(theta), lambdaD), FOURPI);
				
		// Reconstruct everything that was taken down before
		q.setName("Q");
		axesMetadata.setAxis(0, q);
		axesMetadata.addAxis(0, thetaDeg);
		view.setMetadata(axesMetadata);
						
		return new OperationData(view);
	}
}