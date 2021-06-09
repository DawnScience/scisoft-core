/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Objects;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;

/**
 * This is a cache that uses the retrieves the nearest neighbour metadata for a
 * moving beam scan. It uses the datasets highlighted in the model to cache both
 * the scan axes from the current scan and the calibration scan that has been
 * split into frames.
 * <p>
 * When the metadata is retrieved from the cache it does a nearest neighbour
 * comparison to determine where to pull the cached metadata from in the list.
 * <p>
 * Note: no bound checking is performed yet to ensure scan falls within the
 * calibrated area.
 */
public class NearestMetadataCache extends AbstractMovingBeamMetadataCache {
	private Dataset calibrationX;
	private Dataset calibrationY;

	/**
	 * Construct the metadata cache
	 * 
	 * @param slice slice of data to process; assumed to have a
	 * SliceFromSeriesMetadata object containing SliceInformation and
	 * SourceInformation set.
	 * @param model corresponding model to the
	 * {@link NearestDiffractionMetadataImportOperation}
	 * @param dataFilePath path to the scan being processed
	 * @throws Exception if an error is raised during the cache construction
	 */
	public NearestMetadataCache(IDataset slice, NearestDiffractionMetadataImportModel model, String dataFilePath)
			throws Exception {

		//super();

		axes = slice.getFirstMetadata(AxesMetadata.class);
		sourceAxesNames = new String[] { model.getPositionZeroDataset(), model.getPositionOneDataset() };

		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);
		isLive = ssm.getSourceInfo().isLive();

		IDataHolder cData = LoaderFactory.getData(model.getFilePath(), true, null);
		IDataHolder scanData = LoaderFactory.getData(dataFilePath, null);

		// here the cached datasets should be the scan's axis datasets
		cachedSourceXPositions = scanData.getLazyDataset(sourceAxesNames[0]);
		cachedSourceYPositions = scanData.getLazyDataset(sourceAxesNames[1]);

		// get flat views of the calibrated position datasets
		calibrationX = DatasetUtils.sliceAndConvertLazyDataset(cData.getLazyDataset(sourceAxesNames[0])).flatten();
		calibrationY = DatasetUtils.sliceAndConvertLazyDataset(cData.getLazyDataset(sourceAxesNames[1])).flatten();

		if (Objects.isNull(calibrationX) || Objects.isNull(calibrationY)) {
			throw new CacheConstructionException(
					"could not retrieve specified calibration position datasets from scan");
		}

		File cFolder = new File(model.getCalibsFolder());
		String[] cFrames = cFolder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.matches(model.getRegex());
			}
		});

		if (cFrames.length == 0)
			throw new CacheConstructionException("No frames matching expression in selected folder");
		Arrays.sort(cFrames, 0, cFrames.length);

		String datasetName = (Objects.nonNull(model.getDetectorDataset())) ? model.getDetectorDataset()
				: ssm.getDatasetName();

		Arrays.stream(cFrames, 0, cFrames.length).forEachOrdered(s -> {
			try {
				String cPath = cFolder.getAbsolutePath() + File.separator + s;
				metadata.add(NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(cPath, ssm.getParent(),
						datasetName));
			} catch (DatasetException e) {
				throw new RuntimeException("Unable to retrieve metadata for: " + s);
			}
		});
		if (Objects.isNull(metadata.get(0)))
			throw new CacheConstructionException("Error loading metadata");

	}

	@Override
	public IDiffractionMetadata getDiffractionMetadata(SliceFromSeriesMetadata ssm) throws DatasetException {
		return metadata.get(getAbsoluteNeighbourPosition(ssm));

	}

	@Override
	public ReferencePosition2DMetadata getPositionMeta(SliceFromSeriesMetadata ssm) throws DatasetException {

		ReferencePosition2DMetadata pmeta;

		int pos = getAbsoluteNeighbourPosition(ssm);
		pmeta = new ReferencePosition2DMetadata();
		pmeta.setReferencePosition(DatasetFactory.createFromObject(
				new double[] { calibrationX.getElementDoubleAbs(pos), calibrationY.getElementDoubleAbs(pos) }));
		return pmeta;
	}

	private int getAbsoluteNeighbourPosition(SliceFromSeriesMetadata ssm) throws DatasetException {
		
		Dataset xdiff = Maths.subtract(ssm.getMatchingSlice(cachedSourceXPositions),calibrationX.clone());
		Dataset ydiff = Maths.subtract(ssm.getMatchingSlice(cachedSourceYPositions), calibrationY.clone());
		xdiff.ipower(2);
		ydiff.ipower(2);

		xdiff.iadd(ydiff);
		return xdiff.argMin(false); // find minimum on d^2
	}

}
