/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.NonPixelSplittingIntegration2D;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelSplittingIntegration2D;

public class CakePixelIntegrationOperation extends AbstractPixelIntegrationOperation<CakePixelIntegrationModel> {


	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected void setAxes(IDataset data, List<Dataset> out) {
		
		AxesMetadataImpl amd = new AxesMetadataImpl(2);
		Dataset first = out.get(1);
		first.setShape(new int[]{first.getShape()[0], 1});
		Dataset second = out.get(0);
		second.setShape(new int[]{1, second.getShape()[0]});
		amd.setAxis(0, first);
		amd.setAxis(1, second);
		data.setMetadata(amd);
		return;

	}

	@Override
	protected AbstractPixelIntegration createIntegrator(
			PixelIntegrationModel model, IDiffractionMetadata md) {
		
		AbstractPixelIntegration integ = null;
		
		int[] shape = new int[]{md.getDetector2DProperties().getPy(), md.getDetector2DProperties().getPx()};
		int nBins = AbstractPixelIntegration.calculateNumberOfBins(md.getDetector2DProperties().getBeamCentreCoords(), shape);
		int nBins2 = nBins;
		
		if (model.getNumberOfBins() != null) nBins = model.getNumberOfBins();
		if (((CakePixelIntegrationModel)model).getNumberOfBins2ndAxis() != null) nBins2 = ((CakePixelIntegrationModel)model).getNumberOfBins2ndAxis() ;
		
		if (model.isPixelSplitting()) {
			
			integ = new PixelSplittingIntegration2D(md, nBins, nBins2);
		} else {
			integ = new NonPixelSplittingIntegration2D(md, nBins, nBins2);
		}
		
		integ.setAxisType(((CakePixelIntegrationModel)model).getAxisType());
		
		if (model.getRadialRange() == null) integ.setRadialRange(null);
		else integ.setRadialRange(model.getRadialRange().clone());
		
		if (model.getAzimuthalRange() == null) integ.setAzimuthalRange(null);
		else integ.setAzimuthalRange(model.getAzimuthalRange().clone());
		
		return integ;
	}

}
