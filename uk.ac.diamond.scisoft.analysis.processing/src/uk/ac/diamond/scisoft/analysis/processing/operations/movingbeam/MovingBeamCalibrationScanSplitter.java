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
import java.util.ArrayList;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.IPowderCalibrationInfo;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;

import uk.ac.diamond.scisoft.analysis.crystallography.CalibrantSpacing;
import uk.ac.diamond.scisoft.analysis.crystallography.CalibrationFactory;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionMetadataUtils;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;
import uk.ac.diamond.scisoft.diffraction.powder.CalibrationOutput;
import uk.ac.diamond.scisoft.diffraction.powder.DiffractionImageData;
import uk.ac.diamond.scisoft.diffraction.powder.MovingBeamCalibrationConfig;
import uk.ac.diamond.scisoft.diffraction.powder.PowderCalibration;
import uk.ac.diamond.scisoft.diffraction.powder.PowderCalibrationInfoImpl;
import uk.ac.diamond.scisoft.diffraction.powder.SimpleCalibrationParameterModel;

public class MovingBeamCalibrationScanSplitter {

	private MovingBeamCalibrationConfig config;
	private SimpleCalibrationParameterModel paramModel;
	private ILazyDataset xReadback;
	private ILazyDataset yReadback;
	private ILazyDataset frameKeys;
	private IDiffractionMetadata diffMeta;

	private static String FRAME = "_frame_";

	public MovingBeamCalibrationScanSplitter(IDataset slice, SplitAndCalibrateFramesModel model,
			MovingBeamCalibrationConfig config) throws RuntimeException {

		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);
		SourceInformation info = ssm.getSourceInfo();

		this.config = config;
		try {
			loadReferenceCalibration();
		} catch (DatasetException e) {
			throw new RuntimeException("Error reading reference calibration geometry", e);
		}

		if (model.getOverrideRoll()) {
			DetectorProperties dp = diffMeta.getDetector2DProperties();
			PowderCalibration.setDetectorFastAxisAngle(dp, model.getNewRoll());
			diffMeta = new DiffractionMetadata("", dp, diffMeta.getDiffractionCrystalEnvironment());
		}

		paramModel = config.getModel();
		paramModel.setIsPointCalibration(true); // should always be a point
												// calibration
		paramModel.setFloatEnergy(config.getFloatEnergy());

		if (info.getFilePath() == null)
			throw new RuntimeException("No file path provided in the SorceInformation");

		if (xReadback == null) {
			try {
				IDataHolder dh = AbstractMovingBeamMetadataCache.loadScan(info.getFilePath());
				xReadback = dh.getLazyDataset(config.getXReferenceAxisPath());
				yReadback = dh.getLazyDataset(config.getYReferenceAxisPath());
				//Currently breaks if hdf file is linked at the /entry level when combined 
				//with old scanning names, when the path is determined from the filename.
				
				String frameKeyPath = AbstractMovingBeamMetadataCache.buildFrameKeysPath(info.getFilePath(),
						config.getScanKeyPath(), !model.getUseOldScanning()); // old
																				// scanning

				frameKeys = dh.getLazyDataset(frameKeyPath);
				if (frameKeys == null)
					frameKeys = dh.getLazyDataset(config.getScanKeyPath()); // last
																			// chance
																			// fallback
																			// using
																			// full
																			// path
																			// specified
																			// in
																			// the
																			// configuration
				if (frameKeys == null)
					throw new Exception("Error reding frame keys from scan.");

				CalibrationFactory.getCalibrationStandards().setSelectedCalibrant(config.getStandard());

			} catch (Exception e) {
				throw new RuntimeException("Error loading scan metadata into/ from DataHolder: " + e.getMessage());
			}
		}
	}

	/**
	 * get the reflections for the calibration standard
	 * 
	 * @return
	 */
	public CalibrantSpacing getCalibrationPeakMap() {
		return CalibrationFactory.getCalibrationStandards().getCalibrationPeakMap(config.getStandard());
	}

	private void loadReferenceCalibration() throws RuntimeException, DatasetException {
		File path = new File(config.getInitialCalibration());
		if (!path.exists())
			throw new RuntimeException("Reference calibration file doesn't exist!");
		diffMeta = NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(path.toString(), null, null);
	}

	/**
	 * return the position of the calibrated reference image
	 * 
	 * @return
	 */
	public double[] getCalibratedPositionOfReference() {
		return config.getReferenceAxesPositionXY();
	}

	/**
	 * Calibrate a frame using the configured settings; the slice must have the
	 * SliceFromSeriesMetadata set prior to passing the slice into this method
	 * 
	 * @param slice
	 */
	public DiffractionImageData calibrateFrameFromSlice(IDataset slice) throws DatasetException {
		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);
		DiffractionImageData dimg = new DiffractionImageData();
		dimg.setImage(slice.clone().squeeze()); // prevent any direct changes on
												// the provided slice dimensions
		diffMeta.getDetector2DProperties();
		CalibrantSpacing hklSpacings = getCalibrationPeakMap();

		double[] pos = getCalibratedPositionOfReference();
		DoubleDataset xrdback = DatasetUtils.cast(DoubleDataset.class, ssm.getMatchingSlice(xReadback));
		DoubleDataset yrdback = DatasetUtils.cast(DoubleDataset.class, ssm.getMatchingSlice(yReadback));

		double[] cpos = new double[] { xrdback.getFloat(), yrdback.getFloat() };

		IDiffractionMetadata frameMeta = DiffractionMetadataUtils.getOffsetMetadata(diffMeta,
				new double[] { cpos[0] - pos[0], cpos[1] - pos[1], 0. });

		dimg.setMetaData(frameMeta);
		PowderCalibration.findPointsOfInterest(dimg, paramModel, null, hklSpacings.getHKLs(), null,
				paramModel.getMaxSearchSize(), paramModel.getnPointsPerRing());

		CalibrationOutput calibOutput = PowderCalibration.manualCalibrateMultipleImagesPoints(dimg,
				hklSpacings.getHKLs(), paramModel, null, null);

		IPowderCalibrationInfo powderInfo = calibOutput.getCalibrationInfo()[0];
		PowderCalibrationInfoImpl correctInfo = new PowderCalibrationInfoImpl(powderInfo.getCalibrantName(),
				powderInfo.getCalibrationImagePath(), powderInfo.getDetectorName());

		double[] dAngstrom = hklSpacings.getHKLs().stream().mapToDouble(hkl -> hkl.getDNano() * 10.).toArray();
		correctInfo.setPostCalibrationInformation("Manual powder diffraction image calibration using point parameters",
				DatasetFactory.createFromObject(DoubleDataset.class, dAngstrom),
				DatasetFactory.createFromList(new ArrayList<Integer>(paramModel.getRingSet())),
				calibOutput.getResidual());
		dimg.setCalibrationInfo(correctInfo);

		return dimg;

	}

	/**
	 * get the predefined construction pattern for a frame's name.
	 * 
	 * @param pathToCalibs - path to the folder where the individual frame
	 * calibrations should be stored
	 * @param ssm - the current position of the slice from within the scan
	 * @param scanName - name for the scan
	 * @return
	 * @throws DatasetException
	 */
	public String getFileNameForFrame(String pathToCalibs, SliceFromSeriesMetadata ssm, String scanName)
			throws RuntimeException {
		try {
			int frameID = getFrameUniqueKey(ssm);
			String cFile = pathToCalibs + File.separator + scanName + FRAME + "%0" + String.valueOf(getPadWithZeros())
					+ "d.nxs";
			return String.format(cFile, frameID);
		} catch (DatasetException e) {
			throw new RuntimeException("Error retrieving scan unique key!");
		}

	}

	/**
	 * get the unique integer key of the frame specified by the SliceFromSeries
	 * Object
	 * 
	 * @param ssm
	 * @return
	 * @throws DatasetException
	 */
	public int getFrameUniqueKey(SliceFromSeriesMetadata ssm) throws DatasetException {
		return DatasetUtils.cast(IntegerDataset.class, ssm.getMatchingSlice(frameKeys)).getInt();
	}

	/**
	 * return the zero padding used for creating individual frame filenames
	 * 
	 * @return
	 */
	public int getPadWithZeros() {
		return config.getPadWithZeros();
	}

	/**
	 * return the current readback position for the point specified in the
	 * SliceFromSeries Object
	 * 
	 * @param ssm
	 * @return
	 * @throws DatasetException
	 */
	public double[] getCalibratedPosition(SliceFromSeriesMetadata ssm) throws DatasetException {
		return new double[] { DatasetUtils.cast(DoubleDataset.class, ssm.getMatchingSlice(xReadback)).getDouble(),
				DatasetUtils.cast(DoubleDataset.class, ssm.getMatchingSlice(yReadback)).getDouble() };
	}

	public MovingBeamCalibrationConfig getConfig() {
		return config;
	}

	public void setConfig(MovingBeamCalibrationConfig config) {
		this.config = config;
	}
	

}
