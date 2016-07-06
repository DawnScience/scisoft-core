/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.api.processing.model.ValueModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.internal.MaskMetadataImpl;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationBean;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class MaskOutliersInQOperation extends AbstractOperation<MaskOutliersInQModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.mask.MaskOutliersInQOperation";
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
		
		DiffractionMetadata meta = input.getFirstMetadata(DiffractionMetadata.class);
		
		if (meta == null) throw new OperationException(this, "Does not contain calibration information!");
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setUsePixelSplitting(false);
		bean.setxAxis(XAxis.Q);
		bean.setAzimuthalIntegration(true);
		bean.setTo1D(true);
		bean.setShape(input.getShape());
		PixelIntegrationCache lcache = new PixelIntegrationCache(meta, bean);
		
		IDataset mask = null;
		
		if (input.getFirstMetadata(MaskMetadata.class) != null) {
			MaskMetadata mm = input.getFirstMetadata(MaskMetadata.class);
			if (mm.getMask() != null) {
				mask = mm.getMask();
			}
		}
		
		Dataset calculateOutlierMask = PixelIntegration.calculateOutlierMask(DatasetUtils.convertToDataset(input), mask, lcache, model.getScale(),model.isLow(),model.isHigh());
		
		input.setMetadata(new MaskMetadataImpl(calculateOutlierMask));
		
		return new OperationData(input);
	}

}
