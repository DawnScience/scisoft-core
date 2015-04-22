/*-
 * Copyright (c) 2011-2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.image.IImageFilterService;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.image.BlobExtractionModel.ConnectRule;

/**
 * Blob extraction image operation using BoofCV contour/label algorithms
 * 
 * @author Baha El Kassaby
 *
 */
public class BlobExtractionOperation extends AbstractSimpleImageOperation<BlobExtractionModel> {

	private final Logger logger = LoggerFactory.getLogger(BlobExtractionOperation.class);

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.BlobExtractionOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset, IMonitor monitor) {
		IImageFilterService service = getImageFilterService();
		ConnectRule rule = ((BlobExtractionModel)model).getRule();

		IDataset extracted = null;
		try {
			extracted = service.extractBlob(dataset, rule.value());
		} catch (Exception e) {
			logger.error("An error occured while blob extracting:", e);
		}
		return extracted;
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
