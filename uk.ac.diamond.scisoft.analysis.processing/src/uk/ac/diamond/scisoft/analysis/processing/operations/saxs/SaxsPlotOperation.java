/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.SaxsPlotModel.SaxsAnalysisPlotType;

/**
 * An Operation to make the various SAXS plots in the Processing perspective
 * 
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
public class SaxsPlotOperation extends AbstractOperation<SaxsPlotModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.SaxsPlotOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

	SaxsAnalysisPlotType plotType = model.getPlotType();
	
	// Get the old axis, raise an exception if it is not present. This is
	// assumed to be q
	Dataset oldAxis;
	try {
		AxesMetadata axes = input.getFirstMetadata(AxesMetadata.class);
		oldAxis = DatasetUtils.sliceAndConvertLazyDataset(axes.getAxis(0)[0]);
	} catch (Exception e) {
		throw new OperationException(this, "Error getting axis: " + e.toString());
	}
	
	Dataset oldData = DatasetUtils.convertToDataset(input);
	
	Dataset newAxis;
	Dataset newData;
	
	switch (plotType) {
	case LOGNORM_PLOT:
//		x, log y
		newAxis = oldAxis;
		newData = Maths.log10(oldData);
		break;
	case LOGLOG_PLOT:
//		log x, log y
		newAxis = Maths.log10(oldAxis);
		newData = Maths.log10(oldData);
		break;
	case GUINIER_PLOT:
//		x², log y
		newAxis = Maths.square(oldAxis);
		newData = Maths.log10(oldData);
		break;
	case POROD_PLOT:
//		x, x⁴y
		newAxis = oldAxis;
		newData = Maths.multiply(Maths.square(Maths.square(oldAxis)), oldData);
		break;
	case KRATKY_PLOT:
//		x, x²y
		newAxis = oldAxis;
		newData = Maths.multiply(Maths.square(oldAxis), oldData);
		break;
	case ZIMM_PLOT:
//		x², 1/y
		newAxis = Maths.square(oldAxis);
		newData = Maths.divide(1, oldData);
		break;
	case DEBYE_BUECHE_PLOT:
//		x², 1/√y
		newAxis = Maths.square(oldAxis);
		newData = Maths.divide(1, Maths.sqrt(oldData));
		break;
	default:
		newAxis = oldAxis;
		newData = oldData;
		break;
	}

	AxesMetadata newAxesM;
	try {
		newAxesM = MetadataFactory.createMetadata(AxesMetadata.class, 1);
	} catch (MetadataException e) {
		throw new OperationException(this, e);
	}
	newAxesM.setAxis(0, newAxis);
	newData.addMetadata(newAxesM);
	
	return new OperationData(newData);
	
	}

}
