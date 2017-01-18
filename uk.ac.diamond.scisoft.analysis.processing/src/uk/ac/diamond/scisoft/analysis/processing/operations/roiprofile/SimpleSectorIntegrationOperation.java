/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;

public class SimpleSectorIntegrationOperation extends AbstractOperation<SimpleSectorIntegrationModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.SimpleSectorIntegrationOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		
		Dataset mask = null;
		
		MaskMetadata mm = input.getFirstMetadata(MaskMetadata.class);
		
		if (mm != null) mask = DatasetUtils.convertToDataset(mm.getMask());
		
		Dataset[] sector = ROIProfile.sector(DatasetUtils.convertToDataset(input), mask, model.getSector(),
				true, false, false, null, null, false);
		
		final Dataset xi = DatasetFactory.createLinearSpace(model.getSector().getRadius(0), model.getSector().getRadius(1), sector[0].getSize(), Dataset.FLOAT64);
		xi.setName("radius");
		
		Dataset s = sector[0];
		s.setName("Integrated");
		
		try {
			AxesMetadata md = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			md.setAxis(0, xi);
			s.setMetadata(md);
		} catch (MetadataException e) {

		}
		
		return new OperationData(s);
	}

}
