/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import org.dawb.common.services.ServiceManager;
import org.eclipse.dawnsci.analysis.api.image.IImageFilterService;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.MetadataFactory;

public class DilateMaskOperation extends AbstractOperation<DilateMaskModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.DilateMaskOperation";
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		IDataset mask = DatasetUtils.convertToDataset(getFirstMask(input));
		
		if (mask == null) throw new OperationException(this, "No mask to dilate!");
		
		IImageFilterService service = null;
		
		try {
			service = (IImageFilterService)ServiceManager.getService(IImageFilterService.class);
		} catch (Exception e) {
			throw new OperationException(this, "Could not get image processing service");
		}

		if (service == null) throw new OperationException(this, "Could not get image processing service");
		
		IDataset not = Comparisons.logicalNot(mask);
		
		for (int i = 0; i < ((DilateMaskModel)model).getNumberOfPixelsToDilate();i++) {
			not= service.filterDilate(not, true);
		}
		
		not = Comparisons.logicalNot(not);
		
		MaskMetadata mm;
		try {
			mm = MetadataFactory.createMetadata(MaskMetadata.class, not);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		input.setMetadata(mm);
		
		return new OperationData(input);
		
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
