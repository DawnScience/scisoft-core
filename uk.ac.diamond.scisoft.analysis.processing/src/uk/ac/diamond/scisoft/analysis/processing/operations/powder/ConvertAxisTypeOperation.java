/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.PowderDataUtils;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class ConvertAxisTypeOperation extends AbstractOperation<ConvertAxisTypeModel, OperationData> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.powder.ConvertAxisTypeOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) {
		List<AxesMetadata> axesMetaList = null;
		ILazyDataset[] axes;
		AxesMetadata am;

		try {
			axesMetaList = input.getMetadata(AxesMetadata.class);
			if (axesMetaList == null || axesMetaList.isEmpty())
				throw new Exception();
			am = axesMetaList.get(0);
			if (am == null) {
				throw new Exception();
			}
			axes =  am.getAxes();
		} catch (Exception e) {
			throw new OperationException(this, "Could not read axis information from data.");
		}

		if ((axes != null) && (axes[0] !=null)) {
			Dataset oldXAxis = (Dataset)axes[0];

			String axisName = oldXAxis.getName();
			XAxis currentAxisType;
			//FIXME This should be set better in AbstractPixelIntegration
			if (axisName.equals("q")) {
				currentAxisType = XAxis.Q;
			} else if (axisName.equals("2theta")) {
				currentAxisType = XAxis.ANGLE;
			} else if (axisName.equals("d-spacing")) {
				currentAxisType = XAxis.RESOLUTION;
			} else if (axisName.equals("Pixel")) {
				currentAxisType = XAxis.PIXEL;
			} else {
				throw new OperationException(this, "Current axis type is not recognised.");
			}
			
			XAxis targetAxisType = model.getAxisType();
			Double wavelength;
			if (model.isUseCalibratedWavelength()){
				List<DiffractionMetadata> dmdMetaList = null;
				try {
					dmdMetaList = input.getMetadata(DiffractionMetadata.class);
					if (dmdMetaList == null || dmdMetaList.isEmpty())
						throw new Exception();
					DiffractionMetadata dmd = dmdMetaList.get(0);
					if (dmd == null) {
						throw new Exception();
					}
					wavelength = dmd.getDiffractionCrystalEnvironment().getWavelength();
				} catch (Exception e) {
					throw new OperationException(this, "Could not read wavelength from calibration data.");
				}
			} else {
				wavelength = model.getUserWavelength();
			}
			
			Dataset newXAxis;
			try {
				newXAxis = PowderDataUtils.convert1DDataset(oldXAxis, currentAxisType, targetAxisType, wavelength);
			} catch (Exception e) {
				throw new OperationException(this, "Error converting axis dataset.");
			}

			
			am.setAxis(0, newXAxis);
			input.setMetadata(am);
			return new OperationData(input);
			
		} else {
			throw new OperationException(this, "Axes in this dataset cannot be converted");
		}
	}

}
