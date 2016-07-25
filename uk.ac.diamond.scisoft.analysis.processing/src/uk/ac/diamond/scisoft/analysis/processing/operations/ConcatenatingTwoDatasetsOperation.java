/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

//import org.apache.commons.lang.ArrayUtils;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation2D;
import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation2D.BicubicInterpolationOutput;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class ConcatenatingTwoDatasetsOperation extends AbstractOperation<ConcatenatingTwoDatasetsModel, OperationData> {

	private final Logger logger = LoggerFactory.getLogger(ConcatenatingTwoDatasetsOperation.class);

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.ConcatenatingTwoDatasetsOperation";
	}

	@Override
	public OperationData process(IDataset dataset, IMonitor monitor) throws OperationException {
		// get series metadata (will not be null), to check we are from the same
		// parent and whether we have hit the final image
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(dataset);

		String filePath = model.getFilePath();
		int axis = model.getAxis().getAxis();
		
		//get the name of the incoming dataset
		String dataName = null;
		if (model.getDatasetName().isEmpty()) {
			dataName = ssm.getDatasetName();
		} else {
			dataName = model.getDatasetName();
		}
		IDataset dataset_new = null;
		
		try {
			dataset_new = LoaderFactory.getDataSet(filePath, dataName, monitor);
		} catch (NullPointerException e) {
			// dataName looks invalid
			if (model.getDatasetName().isEmpty()) {
				throw new OperationException(this, "Previously used dataset name not found in file");
			} else {
				throw new OperationException(this, "Dataset name not found in file");
			}
		} catch (Exception e) {
			logger.error("LoaderFactory.getDataSet exception", e);
			throw new OperationException(this, e);
		}
		
		// get axis metadata
		Dataset axes_old0 = null;
		Dataset axes_old1 = null;
		Dataset axes_new0 = null;
		Dataset axes_new1 = null;
		Dataset axes_concat0 = null;
		Dataset axes_concat1 = null;
		
		try {
			AxesMetadata axes_old = dataset.getMetadata(AxesMetadata.class).get(0);
			axes_old0 = DatasetUtils.convertToDataset(axes_old.getAxis(0)[0].getSlice());
			axes_old1 = DatasetUtils.convertToDataset(axes_old.getAxis(1)[0].getSlice());
			AxesMetadata axes_new = dataset_new.getMetadata(AxesMetadata.class).get(0);
			axes_new0 = getAxisDataset(axes_new.getAxis(0), axes_old0.getName());
			axes_new1 = getAxisDataset(axes_new.getAxis(1), axes_old1.getName());
			//ensure we are dealing with 2D datasets, to avoid trouble later
			
		} catch (Exception e) {
			// no axes were found which makes concatenation a lot easier!
			//System.out.println("Exception: " + e.getMessage());
		}
	
		Dataset axes_concat_mean_old0 = null;
		Dataset axes_concat_mean_old1 = null;
		Dataset axes_concat_mean_new0 = null;
		Dataset axes_concat_mean_new1 = null;
		
		if (axes_old0 != null && axes_new0 != null && axes_old1 != null && axes_new1 != null) {
			// axes were found, let's concatenate
			try {
				axes_concat0 = DatasetUtils.concatenate(new IDataset[]{axes_old0, axes_new0}, axis); //ecc
				axes_concat1 = DatasetUtils.concatenate(new IDataset[]{axes_old1, axes_new1}, axis); //bcc
				
				if (axis == 1) {
					axes_concat_mean_old0 = axes_concat0.mean(1);
					axes_concat_mean_old1 = axes_concat1.mean(0);
		
					axes_concat_mean_new0 = axes_concat_mean_old0;
					axes_concat_mean_new1 = DatasetFactory.createLinearSpace(axes_concat_mean_old1.getDouble(0), axes_concat_mean_old1.getDouble(axes_concat_mean_old1.getSize()-1), axes_concat_mean_old1.getSize(), Dataset.FLOAT64);
				} else {
					axes_concat_mean_old0 = axes_concat0.mean(0);
					axes_concat_mean_old1 = axes_concat1.mean(1);
		
					axes_concat_mean_new0 = DatasetFactory.createLinearSpace(axes_concat_mean_old0.getDouble(0), axes_concat_mean_old0.getDouble(axes_concat_mean_old0.getSize()-1), axes_concat_mean_old0.getSize(), Dataset.FLOAT64);
					axes_concat_mean_new1 = axes_concat_mean_old1;
				}
			} catch (IllegalArgumentException e) {
				// concatenate exception -> abort
				e.printStackTrace();
				throw new OperationException(this, e.getMessage() + ". Check concatenation axis!");
			}
		}
		
		Dataset data_concat = null;
		try {
			data_concat = DatasetUtils.concatenate(new IDataset[]{dataset, dataset_new}, axis);
		} catch (IllegalArgumentException e) {
				// concatenate exception -> abort
				throw new OperationException(this, e);
		}
			
		if (axes_old0 != null && axes_new0 != null && axes_old1 != null && axes_new1 != null) {
			data_concat = Interpolation2D.bicubicInterpolation(axes_concat_mean_old0, axes_concat_mean_old1, data_concat, axes_concat_mean_new0, axes_concat_mean_new1, BicubicInterpolationOutput.TWOD);
			AxesMetadata axma;
			try {
				axma = MetadataFactory.createMetadata(AxesMetadata.class, 2);
			} catch (MetadataException e) {
				throw new OperationException(this, e);
			}
			//restore 2D nature of axes through concatenating the 1D axes
			//this will need to be revised when a user wants to use this feature with 1D axes for a 2D dataset!!!
			IDataset[] temp = new IDataset[data_concat.getShape()[1]];
			Arrays.fill(temp, axes_concat_mean_new0.reshape(new int[]{axes_concat_mean_new0.getSize(), 1}));
			Dataset axes_def0 = DatasetUtils.concatenate(temp, 1);
			//remove [:,:] from the end of the name
			String axes_name_old0 = axes_old0.getName();
			axes_def0.setName(axes_name_old0.substring(0, axes_name_old0.length() - 5));
			axma.setAxis(0, axes_def0);
			temp = new IDataset[data_concat.getShape()[0]];
			Arrays.fill(temp, axes_concat_mean_new1.reshape(new int[]{1, axes_concat_mean_new1.getSize()}));
			Dataset axes_def1 = DatasetUtils.concatenate(temp, 0);
			//remove [:,:] from the end of the name
			String axes_name_old1 = axes_old1.getName();
			axes_def1.setName(axes_name_old1.substring(0, axes_name_old1.length() - 5));
			axma.setAxis(1, axes_def1);
			data_concat.addMetadata(axma);
		}
		
		SliceFromSeriesMetadata outsmm = ssm.clone();
		for (int i = 0; i < ssm.getParent().getRank(); i++) {
			if (!outsmm.isDataDimension(i))
				outsmm.reducedDimensionToSingular(i);
		}
		data_concat.addMetadata(outsmm);
		return new OperationData(data_concat);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	private Dataset getAxisDataset(ILazyDataset[] datasets, String datasetName) {
		datasetName = datasetName.substring(0, datasetName.indexOf('['));
		//System.out.println("datasetName: " + datasetName);
		Dataset rv = null;
		for (int i = 0 ; i < datasets.length ; i++) {
			//System.out.println(datasets[i].getName());
			if (datasets[i].getName().equals(datasetName)) {
				try {
					rv = DatasetUtils.convertToDataset(datasets[i].getSlice());
				} catch (DatasetException e) {
					throw new OperationException(this, e);
				}
				break;
			}
		}
		
		return rv;
	}
	
}
