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
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationBean;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationCache;

@Atomic
public class AzimuthalPixelIntegrationOperation<T extends AzimuthalPixelIntegrationModel> extends AbstractPixelIntegrationOperation<AzimuthalPixelIntegrationModel> {


	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected void setAxes(IDataset data, List<Dataset> out) {

		AxesMetadataImpl amd = new AxesMetadataImpl(1);
		amd.setAxis(0, out.get(0));
		data.setMetadata(amd);
		
	}

	@Override
	protected IPixelIntegrationCache getCache(
			PixelIntegrationModel model, IDiffractionMetadata md) {

		IPixelIntegrationCache lcache = cache;
		if (lcache == null) {
			synchronized(this) {
				lcache = cache;
				if (lcache == null) {
					PixelIntegrationBean bean = new PixelIntegrationBean();
					bean.setUsePixelSplitting(model.isPixelSplitting());
					if (model.getNumberOfBins() != null) bean.setNumberOfBinsRadial(model.getNumberOfBins());
					bean.setxAxis(((AzimuthalPixelIntegrationModel)model).getAxisType());
					bean.setRadialRange(model.getRadialRange());
					bean.setAzimuthalRange(model.getAzimuthalRange());
					bean.setAzimuthalIntegration(true);
					bean.setTo1D(true);
					bean.setLog(model.isLogRadial());
					cache = lcache = new PixelIntegrationCache(metadata, bean);
				}
			}
		}
		return lcache;
	}
}
