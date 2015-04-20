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
import org.eclipse.dawnsci.analysis.api.image.ImageThresholdType;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thresholding image operation using BoofCV thresholding algorithms
 * 
 * @author Baha El Kassaby
 *
 */
public class ThresholdImageOperation extends AbstractSimpleImageOperation<ThresholdImageModel> {

	private final Logger logger = LoggerFactory.getLogger(ThresholdImageOperation.class);

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.ThresholdImageOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset, IMonitor monitor) {
		IImageFilterService service = getImageFilterService();
		float threshold = ((ThresholdImageModel)model).getThreshold();
		ImageThresholdType type = ((ThresholdImageModel)model).getType();
		int radius = ((ThresholdImageModel)model).getRadius();
		boolean down = ((ThresholdImageModel)model).isDown();
		IDataset thresholded = null;
		try {
			switch (type) {
			case GLOBAL_CUSTOM:
				thresholded = service.globalThreshold(dataset, threshold, down, true);
				break;
			case GLOBAL_MEAN:
				thresholded = service.globalMeanThreshold(dataset, down, true);
				break;
			case GLOBAL_OTSU:
				thresholded = service.globalOtsuThreshold(dataset, down, true);
				break;
			case GLOBAL_ENTROPY:
				thresholded = service.globalEntropyThreshold(dataset, down, true);
				break;
			case ADAPTIVE_SQUARE:
				thresholded = service.adaptiveSquareThreshold(dataset, radius, down, true);
				break;
			case ADAPTIVE_GAUSSIAN:
				thresholded = service.adaptiveGaussianThreshold(dataset, radius, down, true);
				break;
			case ADAPTIVE_SAUVOLA:
				thresholded = service.adaptiveSauvolaThreshold(dataset, radius, down, true);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("An error occured while thresholding:", e);
		}
		return thresholded;
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
