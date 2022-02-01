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

import org.dawnsci.persistence.internal.PersistenceNodeFactory;
import org.eclipse.dawnsci.analysis.api.diffraction.IPowderCalibrationInfo;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.diffraction.powder.NexusCalibrationExportUtils;

/**
 * Utility class used for generating data for testing moving beam processing
 */
public class MovingBeamTestUtils {

	public static final class MovingBeamConstants {
		// TODO These might be more appropriate somewhere else possible
		// NexusCalibrationExportUtils - for now the will do fine here.
		public static String XAXISREFERENCE = "/entry/diffraction/kb_x";
		public static String YAXISREFERENCE = "/entry/diffraction/kb_y";
		public static String DATASETPATH = "/entry/diffraction/data";
		public static String D0PATH = "/entry/calibration_data/dimension_0";
		public static String D1PATH = "/entry/calibration_data/dimension_1";
		public static String ENTRY = "/entry";

	}

	public static void exportCalibratedFrame(IDataset image, IDataset position, IDiffractionMetadata meta,
			IPowderCalibrationInfo info, String filepath, String xDatasetPath, String yDatasetPath) throws Exception {

		PersistenceNodeFactory nodeFac = new PersistenceNodeFactory();

		try (NexusFile nexusFile = new NexusFileHDF5(filepath, false)) {

			GroupNode n = nodeFac.writePowderCalibrationToFile(meta, image, info);

			String d0Name = NexusCalibrationExportUtils.splitDatasetNameAndGroupPath(MovingBeamConstants.D0PATH)[1];
			String d1Name = NexusCalibrationExportUtils.splitDatasetNameAndGroupPath(MovingBeamConstants.D0PATH)[1];

			GroupNode calibrationGroup = n.getGroupNode("calibration_data");
			calibrationGroup.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_AXES,
					new String[] { d0Name, d1Name, NexusConstants.DATA_AXESEMPTY, NexusConstants.DATA_AXESEMPTY }));
			calibrationGroup.addAttribute(TreeFactory.createAttribute(d0Name + NexusConstants.DATA_INDICES_SUFFIX, 0));
			calibrationGroup.addAttribute(TreeFactory.createAttribute(d1Name + NexusConstants.DATA_INDICES_SUFFIX, 1));

			Dataset p0 = DatasetFactory.createFromObject(position.getDouble(0)).reshape(1, 1, 1, 1);
			Dataset p1 = DatasetFactory.createFromObject(position.getDouble(1)).reshape(1, 1, 1, 1);

			nexusFile.createAndOpenToWrite();
			nexusFile.addNode(MovingBeamConstants.ENTRY, n);

			String[] splitXName = NexusCalibrationExportUtils.splitDatasetNameAndGroupPath(xDatasetPath);
			String[] splitYName = NexusCalibrationExportUtils.splitDatasetNameAndGroupPath(yDatasetPath);
			nexusFile.createData(splitXName[0], splitXName[1], p0, true);
			nexusFile.createData(splitYName[0], splitYName[1], p1, true);
			nexusFile.link(xDatasetPath, MovingBeamConstants.D0PATH);
			nexusFile.link(yDatasetPath, MovingBeamConstants.D1PATH);

		}

	}
	
	/**
	 * Save diffraction geometry to a nexus file that would be produced by the PowderCalibration Perspective
	 * @param image
	 * @param meta
	 * @param info
	 * @param filepath
	 * @throws Exception
	 */
	public static void saveToNexusFile(IDataset image, IDiffractionMetadata meta, IPowderCalibrationInfo info,
			String filepath) throws Exception {

		PersistenceNodeFactory pnf = new PersistenceNodeFactory();
		try (NexusFile nexusFile =  new NexusFileHDF5(filepath, false)) {

			GroupNode n = pnf.writePowderCalibrationToFile(meta, image, info);
			nexusFile.createAndOpenToWrite();
			nexusFile.addNode("/entry1", n);

		}
	}

	/**
	 * lazy load a scan file
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static IDataHolder loadScan(File file) throws Exception {
		return AbstractMovingBeamMetadataCache.loadScan(file.getAbsolutePath());
	}

	/**
	 * get a correctly configured {@link DiffractionMetadataImportModel} for a
	 * mock file.
	 * 
	 * @return
	 */
	public static DiffractionMetadataImportModel getConfiguredOffsetCacheModel(File calibratedFile) {

		DiffractionMetadataImportModel lmodel = new DiffractionMetadataImportModel();
		lmodel.setFilePath(calibratedFile.getAbsolutePath());
		lmodel.setPositionZeroDataset(MovingBeamConstants.D0PATH);
		lmodel.setPositionOneDataset(MovingBeamConstants.D1PATH);
		lmodel.setDetectorDataset("/entry/calibration_data/data");
		return lmodel;
	}

}
