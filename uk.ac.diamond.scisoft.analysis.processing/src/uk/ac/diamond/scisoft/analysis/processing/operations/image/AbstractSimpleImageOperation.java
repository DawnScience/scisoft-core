/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.image.IImageFilterService;
import org.eclipse.dawnsci.analysis.api.image.IImageTransform;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.osgi.services.ServiceProvider;

public abstract class AbstractSimpleImageOperation <T extends IOperationModel> extends AbstractOperation<T, OperationData> {

	protected IImageFilterService imageFilterService;
	protected IImageTransform imageTransformService;

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		if (imageFilterService == null)
			imageFilterService = ServiceProvider.getService(IImageFilterService.class);
		if (imageTransformService == null)
			imageTransformService = ServiceProvider.getService(IImageTransform.class);

		IDataset out = processImage(input, monitor);
		copyMetadata(input, out);
		return new OperationData(out);
	}

	public abstract IDataset processImage(IDataset dataset, IMonitor monitor);

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	protected IImageFilterService getImageFilterService() {
		return imageFilterService;
	}

	protected IImageTransform getImageTransformService() {
		return imageTransformService;
	}
}
