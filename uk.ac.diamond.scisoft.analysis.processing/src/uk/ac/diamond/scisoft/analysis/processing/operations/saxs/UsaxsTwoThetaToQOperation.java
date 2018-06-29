/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from javax.measure

//Imports from uk.ac.dawnsci.analysis
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.unit.UnitUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
// Imports from org.eclipse.dawnsci
import org.eclipse.january.DatasetException;
// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.UnitMetadata;
// Importing the logger!
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.uom.NonSI;
import si.uom.SI;
import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.UsaxsTwoThetaToQModel.YawUnits;
import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.UsaxsTwoThetaToQModel.qUnits;
// Imports from uk.ac.diamond.scisoft
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;


//@author Tim Snow


public class UsaxsTwoThetaToQOperation extends AbstractOperation<UsaxsTwoThetaToQModel, OperationData> {
	
	
	// First, set up a logger
	private static final Logger logger = LoggerFactory.getLogger(UsaxsTwoThetaToQOperation.class);
	
	
	// Then the private variables for this class
	private String ENERGYDATASETPATH = "/entry1/instrument/monochromator/energy";
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.UsaxsTwoThetaToQOperation";
	}
	
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}
	
	
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	
	@SuppressWarnings("restriction")
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) {
		// First, find the filepath and then the units of yaw
		SliceFromSeriesMetadata sliceMetadata = getSliceSeriesMetadata(input);
		String fileLocation = sliceMetadata.getFilePath();
		
		YawUnits yawAccuracy = model.getYawAccuracy();
		double powerAdjustor = 0.00;
		
		// Get our x-axis for conversion and our y-axis for later...
		Dataset xAxis = null;
		AxesMetadata xAxisMetadata = null;
		Dataset output = DatasetUtils.convertToDataset(input);
		
		try {
			xAxisMetadata = input.getFirstMetadata(AxesMetadata.class);
			xAxis = DatasetUtils.convertToDataset(xAxisMetadata.getAxis(0)[0].getSlice());
		} catch (DatasetException xAxisError) {
			throw new OperationException(this, xAxisError);
		}
		
		// Then find the correct power to adjust by
		if (yawAccuracy == YawUnits.RADIANS) {
			powerAdjustor = 1.00;
		} else if (yawAccuracy == YawUnits.MILLIRADIANS) {
			powerAdjustor = 1e3;
		} else if (yawAccuracy == YawUnits.MICRORADIANS) {
			powerAdjustor = 1e6;
		} else if (yawAccuracy == YawUnits.NANORADIANS) {
			powerAdjustor = 1e9;
		} else {
			logger.error("Unit conversion error!");
		}
		
		// Now work out the wavelength
		double beamEnergy = ProcessingUtils.getDataset(this, fileLocation, this.ENERGYDATASETPATH).getDouble(0);
		double wavelength = 12.3984193 / beamEnergy;
		
		// Get our axis iterator
		IndexIterator dataIterator = xAxis.getIterator();
		
		// Then loop over the whole dataset, correcting yaw (in radians) to q 
		while (dataIterator.hasNext()) {
			int index = dataIterator.index;
			double yawValue = xAxis.getElementDoubleAbs(index);
			double sinValue = (yawValue / 2) / powerAdjustor; 
			double qValue = (4.0 * Math.PI * Math.sin(sinValue)) / wavelength;
			xAxis.set(qValue, index);
		}
		
		UnitMetadata xAxisUnit = null;
		
		try {
			if (model.getQScale() == qUnits.METERS) {
				xAxisUnit = MetadataFactory.createMetadata(UnitMetadata.class, SI.METRE.inverse());
				xAxis = Maths.multiply(xAxis, 1e10);
				xAxis.setName("q");
			} else if (model.getQScale() == qUnits.NANOMETERS) {
				xAxisUnit = MetadataFactory.createMetadata(UnitMetadata.class, UnitUtils.NANOMETRE.inverse());
				xAxis = Maths.multiply(xAxis, 1e1);
				xAxis.setName("q");
			} else {
				xAxisUnit = MetadataFactory.createMetadata(UnitMetadata.class, NonSI.ANGSTROM.inverse());
				xAxis.setName("q");
			}
		} catch (MetadataException e) {
			logger.error("Could not create unit metadata for axis", e);
			throw new OperationException(this, "Could not create unit metadata for axis", e);
		}
		
		// Configure the output dataset...
		xAxis.setMetadata(xAxisUnit);
		xAxisMetadata.setAxis(0, xAxis);
		output.setMetadata(xAxisMetadata);
		copyMetadata(input, output);
		
		// finally, returning it!
		return new OperationData(output);
	}
}