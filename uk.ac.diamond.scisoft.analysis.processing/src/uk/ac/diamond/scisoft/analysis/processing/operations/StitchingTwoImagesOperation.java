/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.image.IImageStitchingProcess;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.OperationServiceLoader;

public class StitchingTwoImagesOperation extends AbstractOperation<StitchingTwoImagesModel, OperationData> {

	private final Logger logger = LoggerFactory.getLogger(StitchingTwoImagesOperation.class);
	private IImageStitchingProcess imageStitchingService;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.StitchingTwoImagesOperation";
	}

	@Override
	public OperationData process(IDataset dataset, IMonitor monitor) {
		// get series metadata (will not be null), to check we are from the same
		// parent and whether we have hit the final image
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(dataset);

		String filePath = ((StitchingTwoImagesModel) model).getFilePath();
		String dataName = ((StitchingTwoImagesModel) model).getDataName();
		double xTransl = ((StitchingTwoImagesModel) model).getxTransl();
		double yTransl = ((StitchingTwoImagesModel) model).getyTransl();

//		boolean useFeatureAssociation = ((StitchingTwoImagesModel) model).isFeatureAssociated();
		IDataset stitched = null;
		IDataset imageB = null;
		if (imageStitchingService == null)
			imageStitchingService = OperationServiceLoader.getImageStitchingService();
		try {
			imageB = LoaderFactory.getDataSet(filePath, dataName, monitor).squeeze();
			stitched = imageStitchingService.stitch(dataset, imageB, new double[] {-xTransl, -yTransl});
		} catch (Exception e) {
			logger.error("Error running stitching process:", e);
		}

		SliceFromSeriesMetadata outsmm = ssm.clone();
		for (int i = 0; i < ssm.getParent().getRank(); i++) {
			if (!outsmm.isDataDimension(i))
				outsmm.reducedDimensionToSingular(i);
		}
		stitched.setMetadata(outsmm);
		return new OperationData(stitched);
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
