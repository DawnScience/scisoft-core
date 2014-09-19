/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration1D;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration2D;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.NonPixelSplittingIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.NonPixelSplittingIntegration2D;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelSplittingIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelSplittingIntegration2D;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;

public class PixelIntegrationOperation extends AbstractOperation<PowderIntegrationModel, OperationData> {

	AbstractPixelIntegration integrator;
	IDiffractionMetadata metadata;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.PixelIntegrationOperation";
	}


	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(slice);
		
		if (metadata == null || !metadata.equals(md)) {
			metadata = md;
			integrator = null;
		}
		
		if (integrator == null) integrator = createIntegrator(model, metadata);
		
		ILazyDataset mask = getFirstMask(slice);
		if (mask != null) {
			IDataset m = mask.getSlice().squeeze();
			integrator.setMask((Dataset)m);
		}
		
		ILazyDataset[] axes = getFirstAxes(slice);
		int[] dataDims = getOriginalDataDimensions(slice);
		
		final List<Dataset> out = integrator.integrate(slice);
		
		Dataset data = out.remove(1);
		
		int length = 2;
		if (axes!=null) length = axes.length-1;
		
		AxesMetadataImpl amd = new AxesMetadataImpl(length);
		
		boolean first = true;
		for (int i = 0; i < length; i++) {
			boolean contained = false;
			for (int j : dataDims) {
				if (i == j){
					contained = true;
					if (first) {
						amd.setAxis(i, new ILazyDataset[]{out.get(0)});
						first = false;
					}
					break;
				}
				
			}
			if (!contained) {
				amd.setAxis(i, new ILazyDataset[] {axes!= null ? axes[i] : null});
			}
		}
		
		data.addMetadata(amd);
		
		return new OperationData(data);
	}
	
	private AbstractPixelIntegration createIntegrator(PowderIntegrationModel model, IDiffractionMetadata md) {
		
		switch (model.getIntegrationMode()) {
		case NONSPLITTING:
			integrator = new NonPixelSplittingIntegration(md, model.getNumberOfPrimaryBins());
			break;
		case SPLITTING:
			integrator = new PixelSplittingIntegration(md, model.getNumberOfPrimaryBins());
			break;
		case SPLITTING2D:
			integrator = new PixelSplittingIntegration2D(md, model.getNumberOfPrimaryBins(),model.getNumberOfSecondaryBins());
			break;
		case NONSPLITTING2D:
			integrator = new NonPixelSplittingIntegration2D(md, model.getNumberOfPrimaryBins(),model.getNumberOfSecondaryBins());
			break;
		}
		
		integrator.setAxisType(model.getAxisType());
		
		if (model.getRadialRange() == null) integrator.setRadialRange(null);
		else integrator.setRadialRange(model.getRadialRange().clone());
		
		if (model.getAzimuthalRange() == null) integrator.setAzimuthalRange(null);
		else integrator.setAzimuthalRange(model.getAzimuthalRange().clone());
		
		integrator.setNumberOfBins(model.getNumberOfPrimaryBins());
		
		if (integrator instanceof AbstractPixelIntegration2D) {
			((AbstractPixelIntegration2D)integrator).setNumberOfAzimuthalBins(model.getNumberOfSecondaryBins());
		}
		
		if (integrator instanceof AbstractPixelIntegration1D) {
			((AbstractPixelIntegration1D)integrator).setAzimuthalIntegration(model.isAzimuthal());
		}
		
		return integrator;
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}
