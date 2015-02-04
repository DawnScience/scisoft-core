/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.image.IImageFilterService;
import org.eclipse.dawnsci.analysis.api.image.IImageTransform;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.OperationServiceLoader;

public abstract class AbstractSimpleImageOperation <T extends IOperationModel> extends AbstractOperation<IOperationModel, OperationData> {

	private IImageFilterService imageFilterService;
	private IImageTransform imageTransformService;

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		if (imageFilterService == null)
			imageFilterService = new OperationServiceLoader().getImageFilterService();
		if (imageTransformService == null)
			imageTransformService = new OperationServiceLoader().getImageTransformService();

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
