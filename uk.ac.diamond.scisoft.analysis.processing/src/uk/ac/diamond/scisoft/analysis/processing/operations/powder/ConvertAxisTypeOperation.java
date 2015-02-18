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
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.operations.oned.RangeIntegration1DModel;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class ConvertAxisTypeOperation extends AbstractOperation<ConvertAxisTypeModel, OperationData> {
//public class RangeIntegration1DOperation extends AbstractOperation<RangeIntegration1DModel, OperationData> {

	
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
		List<AxesMetadata> metaList = null;

		try {
			metaList = input.getMetadata(AxesMetadata.class);
			if (metaList == null || metaList.isEmpty())
				return null;
		} catch (Exception e) {
			return null;
		}

		AxesMetadata am = metaList.get(0);
		if (am == null)
			return null;

		ILazyDataset[] axes =  am.getAxes();
		
		if ((axes != null) || (axes[0] !=null)) {
			Dataset oldXAxis = (Dataset)axes[0];
			Dataset newXAxis = DatasetFactory.zeros(oldXAxis);

			String axisName = oldXAxis.getName();
			XAxis axisType = XAxis.ANGLE;//model.getAxisType();
			Double wavelength;
			if (model.isUseCalibratedWavelength()){
				//PASS
//				String path = getSliceSeriesMetadata(input).getSourceInfo().getFilePath();
//				System.out.println(path);
//			//	photon_energy = LoaderFactory.getDataSet(getSliceSeriesMetadata(input).getSourceInfo().getFilePath(), "/entry1/instrument/monochromator/energy", null);
			} else {
				wavelength = model.getUserWavelength();
			}
			for (int i = 0; i < oldXAxis.getSize()-1; i++) {
				Double xAxisVal = oldXAxis.getDouble(i);
				if (axisName == "q") {
					if (axisType == XAxis.ANGLE) {
//						newXAxis.set(convertQToTwoTheta(xAxisVal, wavelength), i);
						throw new OperationException(this, "Currently unsupported.");
					} else if (axisType == XAxis.RESOLUTION){
						newXAxis.set(convertQAndDSpacing(xAxisVal), i);
					}
				} else if (axisName == "2theta") {
					if (axisType == XAxis.Q) {
//						newXAxis.set(convertTwoThetaToQ(xAxisVal, wavelength), i);
						throw new OperationException(this, "Currently unsupported.");
					} else if (axisType == XAxis.RESOLUTION) {
//						newXAxis.set(convertTwoThetaToDSpacing(xAxisVal, wavelength), i);
						throw new OperationException(this, "Currently unsupported.");
					}
				} else if (axisName == "d-spacing") {
					if (axisType == XAxis.ANGLE) {

					} else if (axisType == XAxis.Q) {
						newXAxis.set(convertQAndDSpacing(oldXAxis.getDouble(i)), i);
					} 
				} else {
					throw new OperationException(this, "Input axis type unrecognised or not supported.");
				}
			}
			am.setAxis(0, newXAxis);
			input.setMetadata(am);
			return new OperationData(input);
		} else {
			throw new OperationException(this, "Axes in this dataset cannot be converted");
		}
	}

	private double calcThetaInRadians(double tthVal) {
		return Math.toRadians(tthVal/2);
	}
	
	private double calcTwoThetaInDegrees(double thRadians) {
		return 2*Math.toDegrees(thRadians);
	}
	
	private double convertQToTwoTheta(double qVal, double lambda){
		Double thRadians = Math.asin((qVal*lambda)/(4*Math.PI));
		return calcTwoThetaInDegrees(thRadians);
	}
	
	private double convertTwoThetaToQ(double tthVal, double lambda) {
		return (4*Math.PI/lambda)*Math.sin(calcThetaInRadians(tthVal));
	}
	
	private double convertDSpacingToTwoTheta(double dVal, double lambda) {
		Double thRadians = Math.asin(lambda/(2*dVal));
		return calcTwoThetaInDegrees(thRadians); 
	}
	
	private double convertTwoThetaToDSpacing(double tthVal, double lambda) {
		return lambda/(2*Math.sin(calcThetaInRadians(tthVal)));
	}
	
	private double convertQAndDSpacing(double qdVal) {
		return (2*Math.PI)/qdVal;
	}
}
