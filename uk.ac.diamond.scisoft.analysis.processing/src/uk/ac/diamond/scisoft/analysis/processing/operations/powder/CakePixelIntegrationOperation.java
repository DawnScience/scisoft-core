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

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationBean;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationCache;

@Atomic
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
		
		AxesMetadata amd;
		try {
			amd = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		Dataset first = out.get(1);
		Dataset second = out.get(0);
		amd.setAxis(0, first);
		amd.setAxis(1, second);
		data.setMetadata(amd);
		return;

	}
	
	protected IPixelIntegrationCache buildCache(CakePixelIntegrationModel model, IDiffractionMetadata md, int[] shape) {
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setUsePixelSplitting(model.isPixelSplitting());
		if (model.getNumberOfBins()!=null)bean.setNumberOfBinsRadial(model.getNumberOfBins());
		if (model.getNumberOfBins2ndAxis() != null) bean.setNumberOfBinsAzimuthal(model.getNumberOfBins2ndAxis());
		bean.setxAxis(model.getAxisType());
		bean.setRadialRange(model.getRadialRange());
		bean.setAzimuthalRange(model.getAzimuthalRange());
		bean.setAzimuthalIntegration(true);
		bean.setTo1D(false);
		bean.setShape(shape);
		bean.setLog(model.isLogRadial());
		bean.setSanitise(model.getSanitise());
		return new PixelIntegrationCache(md, bean);
	}
}
