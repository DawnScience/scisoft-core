/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.DimensionMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration1D;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.NonPixelSplittingIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelSplittingIntegration;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.metadata.OriginMetadataImpl;


public class AzimuthalPixelIntegrationOperation extends AbstractPixelIntegrationOperation<AzimuthalPixelIntegrationModel> {


	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected void setAxes(IDataset data, ILazyDataset[] axes, int[] dataDims, List<Dataset> out) {

		if (axes == null) {
			AxesMetadataImpl amd = new AxesMetadataImpl(1);
			amd.setAxis(0, new ILazyDataset[] {out.get(0)});
			data.setMetadata(amd);
			
//			final int[] newDataDims = new int[]{dataDims[0]};
			
//			OriginMetadataImpl omd = new OriginMetadataImpl(null, null, newDataDims);
//			data.setMetadata(omd);
			
			return;
		}
		
		int rank = axes.length-1;
		AxesMetadataImpl amd = new AxesMetadataImpl(rank);
		int[] shape = new int[rank];
		
//		int[] newDataDims = new int[1];
		
		boolean first = true;
		for (int i = 0; i < axes.length; i++) {
			boolean contained = false;
			for (int j : dataDims) {
				if (i == j){
					contained = true;
					if (first) {
						Dataset ds = out.get(0);
						Arrays.fill(shape, 1);
						shape[i] = ds.getShape()[0];
						ds.setShape(shape);
//						newDataDims[0] = i;
						amd.setAxis(i, new ILazyDataset[]{ds});
						first = false;
					}
					break;
				}

			}
			if (!contained) {
				ILazyDataset sq = axes[i].squeeze();
				Arrays.fill(shape, 1);
					if (sq.getShape().length == 0) sq.setShape(shape);
				amd.setAxis(i, new ILazyDataset[] {sq});
			}
		}
		
//		OriginMetadataImpl omd = new OriginMetadataImpl(null, null, newDataDims);
//		data.setMetadata(omd);

		data.setMetadata(amd);
		
	}

	@Override
	protected AbstractPixelIntegration createIntegrator(
			PixelIntegrationModel model, IDiffractionMetadata md) {
		
		AbstractPixelIntegration integ = null;
		
		if (model.isPixelSplitting()) {
			integ = new PixelSplittingIntegration(md, model.getNumberOfBins());
		} else {
			integ = new NonPixelSplittingIntegration(md, model.getNumberOfBins());
		}
		
		integ.setAxisType(((AzimuthalPixelIntegrationModel)model).getAxisType());
		
		if (model.getRadialRange() == null) integ.setRadialRange(null);
		else integ.setRadialRange(model.getRadialRange().clone());
		
		if (model.getAzimuthalRange() == null) integ.setAzimuthalRange(null);
		else integ.setAzimuthalRange(model.getAzimuthalRange().clone());
		
		
		((AbstractPixelIntegration1D)integ).setAzimuthalIntegration(true);
		
		return integ;
	}

}
