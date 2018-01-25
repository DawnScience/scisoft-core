/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationBean;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractRemappedExternalDataModel.DataType;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class SubtractRemappedExternalDataOperation extends AbstractOperation<SubtractRemappedExternalDataModel, OperationData> {

	protected volatile IPixelIntegrationCache cache;
	protected IDiffractionMetadata metadata;
	
	// Then set up a logger
	private static final Logger logger = LoggerFactory.getLogger(SubtractRemappedExternalDataOperation.class);
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractRemappedExternalDataOperation";
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
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(input);

		if (md == null) throw new OperationException(this, "No detector geometry information!");
		
		if (metadata == null) {
			metadata = md;
			cache = null;
		} else {
			boolean dee = metadata.getDiffractionCrystalEnvironment().equals(md.getDiffractionCrystalEnvironment());
			boolean dpe = metadata.getDetector2DProperties().equals(md.getDetector2DProperties());
			
			if (!dpe || !dee) {
				metadata = md;
				cache = null;
			}
		}
		
		ILazyDataset mask = getFirstMask(input);
		IDataset m = null;
		if (mask != null) {
			try {
				m = mask.getSlice().squeeze();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}

		IPixelIntegrationCache lcache = getCache(metadata, input.getShape());

		// Let's set up a home for some data
		DataHolder loadedData = null;
		IDataset loadedQdataset = null;
		IDataset loadedIdataset = null;
		IDataset loadedEdataset = null;
		
		// Now let's fetch some data!
		if (model.getDataType() == DataType.NeXus) {
			String datasetDataPath = model.getFilePathDataset();
			String basePath = "";
			String[] splitPath = datasetDataPath.split("/");
			for (int loopIter = 0; loopIter < splitPath.length - 1; loopIter ++) {
				basePath += splitPath[loopIter] + "/";
			}
			
			String datasetQPath = basePath + "q";
			String datasetErrorPath = basePath + "errors";

			loadedIdataset = ProcessingUtils.getDataset(this, model.getFilePath(), datasetDataPath);
			loadedQdataset = ProcessingUtils.getDataset(this, model.getFilePath(), datasetQPath);
			try {
				loadedEdataset = ProcessingUtils.getDataset(this, model.getFilePath(), datasetErrorPath);
			} catch (Exception e) {
				logger.info("No error data present in input file");
			}
			
		} else if (model.getDataType() == DataType.DAT) {
			try {
				loadedData = (DataHolder) LoaderFactory.getData(model.getFilePath());
				loadedQdataset = loadedData.getDataset(0).squeezeEnds();
				loadedIdataset = loadedData.getDataset(1).squeezeEnds();
				
				try {
					loadedEdataset = loadedData.getDataset(2).squeezeEnds();
				} catch (Exception e) {
					logger.info("No error data present in input file");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("No file found at designated location");
			}
		}
		
		// With the data in hand, let's integrate it
		IDataset[] reducedData = new IDataset[] {loadedQdataset, loadedIdataset};
		Dataset image = PixelIntegrationUtils.generate2Dfrom1D(reducedData, lcache.getXAxisArray()[0]);
		Dataset error = null;
		
		IndexIterator iterator = image.getIterator();
		
		// Sticking in NaN's for any region outside of our original dataset
		if (loadedEdataset != null) {
			IDataset[] errorData = new IDataset[] {loadedQdataset, loadedEdataset};
			error = PixelIntegrationUtils.generate2Dfrom1D(errorData, lcache.getXAxisArray()[0]);
			
			while (iterator.hasNext()) {
				if (image.getElementDoubleAbs(iterator.index) == 0d) {
					image.setObjectAbs(iterator.index, Double.NaN);
					error.setObjectAbs(iterator.index, Double.NaN);
				}
			
			image.setErrors(error);
			}
		} else {
			while (iterator.hasNext()) {
				if (image.getElementDoubleAbs(iterator.index) == 0d) {
					image.setObjectAbs(iterator.index, Double.NaN);
				}
			}
		}
		
		// Then subtract the loaded data from the original data
		image = ErrorPropagationUtils.subtractWithUncertainty(DatasetUtils.convertToDataset(input), image);
		copyMetadata(input, image);
		
		// And return
		return new OperationData(image);
	}
	
	
	protected IPixelIntegrationCache getCache(IDiffractionMetadata md, int[] shape) {

		IPixelIntegrationCache lcache = cache;
		if (lcache == null) {
			synchronized(this) {
				lcache = cache;
				if (lcache == null) {
					PixelIntegrationBean bean = new PixelIntegrationBean();
					bean.setUsePixelSplitting(false);
					bean.setxAxis(XAxis.Q);
					bean.setAzimuthalIntegration(true);
					bean.setTo1D(true);
					bean.setShape(shape);
					cache = lcache = new PixelIntegrationCache(metadata, bean);
				}
			}
		}
		return lcache;
	}

}
