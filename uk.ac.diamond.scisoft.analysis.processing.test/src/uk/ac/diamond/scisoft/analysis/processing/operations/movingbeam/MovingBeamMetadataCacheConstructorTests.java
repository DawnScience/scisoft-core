/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.io.IOException;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceViewIterator;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class MovingBeamMetadataCacheConstructorTests extends AbstractMovingBeamMetadataCacheTest {
	private AbstractMovingBeamMetadataCache cache;
	private ILazyDataset detectorData;
	private static String calibrationDataNodePath =  "/entry/calibration_data";

	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();
	private static Slice[] refSlice = new Slice[] { new Slice(0, 1, 1), new Slice(0, 1, 1), new Slice(null),
			new Slice(null) };

	public MovingBeamMetadataCacheConstructorTests() {
		super();
	}

	@Test
	public void testNeighbourCacheConstruction() {
		try {
			buildScanFile(tmp);
			writeNeighbourCalibrationFiles(tmp);
			readDataFromScanFile();

			NearestDiffractionMetadataImportModel model = new NearestDiffractionMetadataImportModel();
			model.setDetectorDataset(calibrationDataNodePath+"/data");
			model.setCalibsFolder(iFrameFolder.getAbsolutePath());
			model.setFilePath(scanFile.getAbsolutePath());
			model.setPositionZeroDataset(XAxisPath);
			model.setPositionOneDataset(YAxisPath);
			model.setRegex(".*-frame_\\d\\d\\d.nxs");

			SliceND frame = new SliceND(detectorData.getShape(), refSlice);
			SliceViewIterator it = new SliceViewIterator(detectorData, frame, 2, 3);
			it.reset();
			IDataset d = DatasetUtils.sliceAndConvertLazyDataset(it.next());

			cache = new NearestMetadataCache(d, model, scanFile.getAbsolutePath());

			validateCacheMetadataReturn(d, 0);

		} catch (DatasetException e) {
			Assert.fail("Error retrieving dataset");
		} catch (IOException e1) {
			Assert.fail("Error writing single frame calibration");
		} catch (Exception e2) {
			Assert.fail("Error constructing cache");
		}

	}

	@Test
	public void testSingleFrameCacheConstruction() {
		try {
			buildScanFile(tmp);
			writeSingleCalibrationFile(tmp);
		} catch (IOException e) {
			Assert.fail("Error writing single frame calibration");
		}

		readDataFromScanFile();
		DiffractionMetadataImportModel model = new DiffractionMetadataImportModel();

		model.setFilePath(calibrationFile.get(0).getAbsolutePath());
		model.setDetectorDataset(calibrationDataNodePath + "/data");
		model.setPositionZeroDataset(calibrationDataNodePath+"/"+pNames.get(0));
		model.setPositionOneDataset(calibrationDataNodePath +"/" + pNames.get(1));

		try {

			SliceND frame = new SliceND(detectorData.getShape(), refSlice);
			SliceViewIterator it = new SliceViewIterator(detectorData, frame, 2, 3);
			it.reset();

			IDataset d = DatasetUtils.sliceAndConvertLazyDataset(it.next());

			cache = new MovingBeamOffsetMetadataCache(d, model, scanFile.getAbsolutePath());

			validateCacheMetadataReturn(d, 0);

		} catch (DatasetException e) {
			Assert.fail("Error retrieving dataset");
		} catch (Exception e) {
			Assert.fail("Error trying to read scan file");
		}

	}

	private void validateCacheMetadataReturn(IDataset d, int correctMetadataId) {
		try {
			IDiffractionMetadata dOffset = cache
					.getDiffractionMetadata(d.getFirstMetadata(SliceFromSeriesMetadata.class));
			IDiffractionMetadata actual = metadatas.get(0);
			boolean testPass = actual.getDetector2DProperties().equals(dOffset.getDetector2DProperties());
			Assert.assertTrue("Metadata retireved from the cache does not match the expected metadata", testPass);
		} catch (DatasetException e) {
			Assert.fail("Error retrieving metadata from cache");
		}
	}

	private void readDataFromScanFile() {
		try {
			IDataHolder dh = LoaderFactory.getData(scanFile.getAbsolutePath());
			detectorData = dh.getLazyDataset(dataPath);

			SourceInformation si = new SourceInformation(scanFile.getAbsolutePath(), dataPath, detectorData);
			SliceFromSeriesMetadata ssm = new SliceFromSeriesMetadata(si);
			detectorData.addMetadata(ssm);

		} catch (Exception e) {
			Assert.fail("Error trying to read scan file: " + e.toString());
		}
	}

}
