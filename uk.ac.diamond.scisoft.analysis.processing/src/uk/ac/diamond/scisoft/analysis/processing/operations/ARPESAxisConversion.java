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

public class ARPESAxisConversion extends AbstractOperation<ARPESAxisConversionModel, OperationData> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.ARPESAxisConversion";
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		
		// get the photon energy
		IDataset photon_energy;
		try {
			photon_energy = LoaderFactory.getDataSet(getSliceSeriesMetadata(slice).getSourceInfo().getFilePath(), "/entry1/instrument/monochromator/energy", null);
		} catch (Exception e) {
			throw new OperationException(this, "There is no photon energy specified in the data file");
		}
		
		IDataset result = slice.clone();
		copyMetadata(slice, result);
		
		AxesMetadata axesMetadata;
		try {
			axesMetadata = result.getMetadata(AxesMetadata.class).get(0);
		} catch (Exception e) {
			throw new OperationException(this, "Cannot find appropriate Axes in the data file");
		}
		double k = Math.sqrt(photon_energy.getDouble(0) - model.getWorkFunction())*0.51232;
		
		for (ILazyDataset axis : axesMetadata.getAxis(0)) {
			if (axis != null) {
				if (axis.getName().startsWith("sapolar")) {
					IDataset axisData = axis.getSlice(new Slice(null));
					Dataset kx = Maths.multiply(Maths.sin(Maths.toRadians(axisData)), k);
					kx.iadd(model.getKxOffset());
					kx.setName("kx");
					
					try {
						axesMetadata.setAxis(0, kx, axis);
						break;
					} catch (Exception e) {
						throw new OperationException(this, e);
					}
				}
			}
		}
		
		for (ILazyDataset axis : axesMetadata.getAxis(1)) {
			if (axis != null) {
				if (axis.getName().startsWith("angle")) {
					IDataset axisData = axis.getSlice(new Slice(null));
					Dataset ky = Maths.multiply(Maths.sin(Maths.toRadians(axisData)), k);
					ky.iadd(model.getKyOffset());
					ky.setName("ky");
					try {
						axesMetadata.setAxis(1, ky, axis);
						break;
					} catch (Exception e) {
						throw new OperationException(this, e);
					}
				}
			}
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
