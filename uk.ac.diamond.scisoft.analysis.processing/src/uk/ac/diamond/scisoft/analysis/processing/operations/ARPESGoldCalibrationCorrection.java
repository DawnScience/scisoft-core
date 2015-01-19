/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class ARPESGoldCalibrationCorrection extends AbstractOperation<ARPESGoldCalibrationCorrectionModel, OperationData> {
	
	String filename = "";
	IDataset goldData = null;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.ARPESGoldCalibrationCorrection";
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {
		
		// Load the dataset for gold calibration
		if (!(filename != null && filename.equals(model.getFilePath()) && goldData != null)) {
			try {
				filename = model.getFilePath();
				goldData =  LoaderFactory.getDataSet(model.getFilePath(), "/entry/calibration/fittedMu/data", null);
			} catch (Exception e) {
				throw new OperationException(this, "Not an appropriate calibration file selected");
			}
		}

		Dataset differences = Maths.subtract(goldData, goldData.mean());
		
		AxesMetadata axesMetadata;
		try {
			axesMetadata = input.getMetadata(AxesMetadata.class).get(0);
		} catch (Exception e) {
			throw new OperationException(this, "Metadata such as Axis not available in this dataset");
		}

		IDataset xAxis = null;
		try{
			ILazyDataset axis1Meta = axesMetadata.getAxis(1)[0];
			xAxis = axis1Meta.getSlice(new Slice(null));
		} catch (Exception e) {
			throw new OperationException(this, "Cannot get Energy Axis information");
		}
		
		double meanSteps = (xAxis.max().doubleValue()-xAxis.min().doubleValue())/(float)xAxis.getShape()[1];
		
		Dataset differenceInts = Maths.floor(Maths.divide(differences, meanSteps));
		
		// TODO Should be extracted to a method in interpolation utils
		int[] shape = input.getShape();
		DoubleDataset result = new DoubleDataset(shape);
		for(int y = 0; y < shape[0]; y++) {
			int min = Math.max(differenceInts.getInt(0,y), 0);
			int max = Math.min(shape[1]+differenceInts.getInt(0,y), shape[1]);
			int ref = Math.min(differenceInts.getInt(0,y), 0) * -1;
			for(int xx = min; xx < max; xx++) {
				result.set(input.getObject(y,xx), y,ref);
				ref++;
			}
		}
		
		copyMetadata(input, result);
		
		result.setName("Fermi Edge Corrected");
		
		Dataset newEnergyAxis = Maths.subtract(xAxis, goldData.mean());
		newEnergyAxis.imultiply(-1.0);
		newEnergyAxis.iadd(model.getEnergyOffset());
		newEnergyAxis.setName("Binding Energy");
		
		try {
			axesMetadata = result.getMetadata(AxesMetadata.class).get(0);
			axesMetadata.setAxis(1, newEnergyAxis);
		} catch (Exception e) {
			throw new OperationException(this, e);
		}

		OperationData opData = new OperationData(result);
		
		return opData;
	}
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
