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

import org.eclipse.dawnsci.analysis.api.image.IImageStitchingProcess;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.AggregateDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.osgi.services.ServiceProvider;

public class StitchingImagesOperation extends AbstractOperation<StitchingImagesModel, OperationData> {

	private final Logger logger = LoggerFactory.getLogger(StitchingImagesOperation.class);
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
			imageStack.clear();
			translations.clear();
		}
		counter++;

		imageStack.add(dataset);
		double xTransl = model.getxTransl();
		double yTransl = model.getyTransl();
		translations.add(new double[] { xTransl, yTransl });

		if (counter == ssm.getTotalSlices()) {
			int rows = model.getRows();
			int columns = model.getColumns();
			double fieldOfView = model.getFieldOfView();
			boolean useFeatureAssociation = model.isFeatureAssociated();
			
			if (imageStitchingService == null)
				imageStitchingService = ServiceProvider.getService(IImageStitchingProcess.class);
			try {
				AggregateDataset stack = new AggregateDataset(true, imageStack.toArray(new ILazyDataset[imageStack.size()]));
				double[][][] trans = new double[rows][columns][2];
				int k = 0;
				int kmax = translations.size();
				for (int i = 0; i < rows; i++) {
					double[][] trow = trans[i];
					for (int j = 0; j < columns; j++) {
						double[] entry = trow[j];
						double[] pair = translations.get(k++);
						entry[0] = pair[0];
						entry[1] = pair[1];
						if (k >= kmax) {
							break;
						}
					}
				}
				stitched = imageStitchingService.stitch(stack, rows, columns, fieldOfView, trans, useFeatureAssociation, null, monitor);
			} catch (Exception e) {
				logger.error("Error running stitching process:", e);
			}

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
