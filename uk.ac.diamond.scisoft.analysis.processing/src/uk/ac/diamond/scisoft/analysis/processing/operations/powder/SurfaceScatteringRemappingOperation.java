/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.util.List;

import javax.naming.InterruptedNamingException;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.SurfacePixelIntegrationCache;

public class SurfaceScatteringRemappingOperation extends AbstractOperation<SurfaceScatteringRemappingModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.powder.SurfaceScatteringRemappingOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		IDiffractionMetadata md = input.getFirstMetadata(IDiffractionMetadata.class);
		
		if (md == null) throw new OperationException(this, "Remapping requires a calibration to be loaded");
		
		int[] shape = input.getShape();
		
		if (model.getBinsPara() != null) shape[1] = model.getBinsPara();
		if (model.getBinsPerp() != null) shape[0] = model.getBinsPerp();
		
		SurfacePixelIntegrationCache cache = 
				new SurfacePixelIntegrationCache(md, input.getShape(), 
					model.getPitch(), model.getRoll(),
					shape[1], shape[0]);
		
		IDataset mask = null;
		
		MaskMetadata mm = input.getFirstMetadata(MaskMetadata.class);
		
		if (mm != null) mask = mm.getMask();
		
		List<Dataset> integrated = PixelIntegration.integrate(input, mask, cache);
		
		AxesMetadata amd;
		try {
			amd = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		Dataset data = integrated.get(1);
		Dataset first = integrated.get(2);
		Dataset second = integrated.get(0);
		amd.setAxis(0, first);
		amd.setAxis(1, second);
		data.setMetadata(amd);
		
		return new OperationData(data);
//		Dataset debugData = cache.getXAxisArray()[0];
//		debugData.setMetadata(amd);
		
//		return new OperationData(debugData);
	}
	


}
