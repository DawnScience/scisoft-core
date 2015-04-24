/*-
 * Copyright (c) 2011-2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.image.IImageStitchingProcess;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.OperationServiceLoader;

public class StitchingImagesOperation extends AbstractOperation<StitchingImagesModel, OperationData> {

	private List<IDataset> imageStack = new ArrayList<IDataset>();
	private int counter;
	private ILazyDataset parent;
	private IImageStitchingProcess imageStitchingService;
	private List<double[]> translations = new ArrayList<double[]>();

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.StitchingImagesOperation";
	}

	@Override
	public OperationData process(IDataset dataset, IMonitor monitor) {
		IDataset stitched = dataset;
		//get series metadata (will not be null), to check we are from the same parent
		//and whether we have hit the final image
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(dataset);

		if (parent != ssm.getParent()) {
			parent = ssm.getParent();
			counter = 0;
		}
		counter++;

		imageStack.add(dataset);
		double xTransl = ((StitchingImagesModel)model).getxTransl();
		double yTransl = ((StitchingImagesModel)model).getyTransl();
		translations.add(new double[] { xTransl, yTransl });

		if (counter == ssm.getTotalSlices()) {
			int rows = ((StitchingImagesModel)model).getRows();
			int columns = ((StitchingImagesModel)model).getColumns();
			double fieldOfView = ((StitchingImagesModel)model).getFieldOfView();
			boolean useFeatureAssociation = ((StitchingImagesModel)model).isFeatureAssociated();
			boolean useGivenTranslations = ((StitchingImagesModel)model).isUseManualTransl();
			
			if (imageStitchingService == null)
				imageStitchingService = OperationServiceLoader.getImageStitchingService();
			stitched = imageStitchingService.stitch(imageStack, rows, columns, fieldOfView, translations, useFeatureAssociation, !useGivenTranslations);

			SliceFromSeriesMetadata outsmm = ssm.clone();
			for (int i = 0; i < ssm.getParent().getRank(); i++) {
				if (!outsmm.isDataDimension(i)) outsmm.reducedDimensionToSingular(i);
			}
			stitched.setMetadata(outsmm);
			return new OperationData(stitched);
		}
		return null;
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
