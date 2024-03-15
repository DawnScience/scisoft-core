/*-
 *******************************************************************************
 * Copyright (c) 2020 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This file was auto-generated from the NXDL XML definition.
 *******************************************************************************/

package org.eclipse.dawnsci.nexus;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Description and metadata for a single channel from a multi-channel detector.
 * Given an :ref:`NXdata` group linked as part of an NXdetector group that has an axis with named channels (see the
 * example in :ref:`NXdata </NXdata@default_slice-attribute>`), the NXdetector will have a series of NXdetector_channel groups, one for each
 * channel, named CHANNELNAME_channel.
 * Example, given these axes in the NXdata group::
 * @axes = ["image_id", "channel", ".", "."]
 * And this list of channels in the NXdata group::
 * channel = ["threshold_1", "threshold_2", "difference"]
 * The NXdetector group would have three NXdetector_channel groups::
 * detector:NXdetector
 * ...
 * threshold_1_channel:NXdetector_channel
 * threshold_energy = float
 * flatfield = float[i, j]
 * pixel_mask = uint[i, j]
 * flatfield_applied = bool
 * pixel_mask_applied = bool
 * threshold_2_channel:NXdetector_channel
 * threshold_energy = float
 * flatfield = float[i, j]
 * pixel_mask = uint[i, j]
 * flatfield_applied = bool
 * pixel_mask_applied = bool
 * difference_channel:NXdetector_channel
 * threshold_energy = float[2]
 * <p><b>Symbols:</b>
 * These symbols will be used below to illustrate the coordination of the rank and sizes of datasets and the
 * preferred ordering of the dimensions. Each of these are optional (so the rank of the datasets
 * will vary according to the situation) and the general ordering principle is slowest to fastest.
 * The type of each dimension should follow the order of scan points, detector output (e.g. pixels),
 * then time-of-flight (i.e. spectroscopy, spectrometry). Note that the output of a detector is not limited
 * to single values (0D), lists (1D) and images (2D), but three or higher dimensional arrays can be produced
 * by a detector at each trigger.<ul>
 * <li><b>dataRank</b>
 * Rank of the ``data`` field associated with this detector</li>
 * <li><b>nP</b>
 * number of scan points</li>
 * <li><b>i</b>
 * number of detector pixels in the slowest direction</li>
 * <li><b>j</b>
 * number of detector pixels in the second slowest direction</li>
 * <li><b>k</b>
 * number of detector pixels in the third slowest direction</li></ul></p>
 *
 */
public interface NXdetector_channel extends NXobject {

	public static final String NX_THRESHOLD_ENERGY = "threshold_energy";
	public static final String NX_FLATFIELD_APPLIED = "flatfield_applied";
	public static final String NX_FLATFIELD = "flatfield";
	public static final String NX_FLATFIELD_ERRORS = "flatfield_errors";
	public static final String NX_PIXEL_MASK_APPLIED = "pixel_mask_applied";
	public static final String NX_PIXEL_MASK = "pixel_mask";
	public static final String NX_SATURATION_VALUE = "saturation_value";
	public static final String NX_UNDERLOAD_VALUE = "underload_value";
	/**
	 * Energy at which a photon will be recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getThreshold_energy();

	/**
	 * Energy at which a photon will be recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param threshold_energyDataset the threshold_energyDataset
	 */
	public DataNode setThreshold_energy(IDataset threshold_energyDataset);

	/**
	 * Energy at which a photon will be recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getThreshold_energyScalar();

	/**
	 * Energy at which a photon will be recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param threshold_energy the threshold_energy
	 */
	public DataNode setThreshold_energyScalar(Double threshold_energyValue);

	/**
	 * True when the flat field correction has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getFlatfield_applied();

	/**
	 * True when the flat field correction has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param flatfield_appliedDataset the flatfield_appliedDataset
	 */
	public DataNode setFlatfield_applied(IDataset flatfield_appliedDataset);

	/**
	 * True when the flat field correction has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getFlatfield_appliedScalar();

	/**
	 * True when the flat field correction has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param flatfield_applied the flatfield_applied
	 */
	public DataNode setFlatfield_appliedScalar(Boolean flatfield_appliedValue);

	/**
	 * Response of each pixel given a constant input
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: i; 2: j; 3: k;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getFlatfield();

	/**
	 * Response of each pixel given a constant input
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: i; 2: j; 3: k;
	 * </p>
	 *
	 * @param flatfieldDataset the flatfieldDataset
	 */
	public DataNode setFlatfield(IDataset flatfieldDataset);

	/**
	 * Response of each pixel given a constant input
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: i; 2: j; 3: k;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFlatfieldScalar();

	/**
	 * Response of each pixel given a constant input
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: i; 2: j; 3: k;
	 * </p>
	 *
	 * @param flatfield the flatfield
	 */
	public DataNode setFlatfieldScalar(Number flatfieldValue);

	/**
	 * Errors of the flat field correction data.
	 * The form flatfield_error is deprecated.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getFlatfield_errors();

	/**
	 * Errors of the flat field correction data.
	 * The form flatfield_error is deprecated.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param flatfield_errorsDataset the flatfield_errorsDataset
	 */
	public DataNode setFlatfield_errors(IDataset flatfield_errorsDataset);

	/**
	 * Errors of the flat field correction data.
	 * The form flatfield_error is deprecated.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getFlatfield_errorsScalar();

	/**
	 * Errors of the flat field correction data.
	 * The form flatfield_error is deprecated.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param flatfield_errors the flatfield_errors
	 */
	public DataNode setFlatfield_errorsScalar(Double flatfield_errorsValue);

	/**
	 * True when the pixel mask correction has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPixel_mask_applied();

	/**
	 * True when the pixel mask correction has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param pixel_mask_appliedDataset the pixel_mask_appliedDataset
	 */
	public DataNode setPixel_mask_applied(IDataset pixel_mask_appliedDataset);

	/**
	 * True when the pixel mask correction has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getPixel_mask_appliedScalar();

	/**
	 * True when the pixel mask correction has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param pixel_mask_applied the pixel_mask_applied
	 */
	public DataNode setPixel_mask_appliedScalar(Boolean pixel_mask_appliedValue);

	/**
	 * Custom pixel mask for this channel. May include nP as the first dimension for
	 * masks that vary for each scan point.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 2: i; 3: j; 4: k;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPixel_mask();

	/**
	 * Custom pixel mask for this channel. May include nP as the first dimension for
	 * masks that vary for each scan point.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 2: i; 3: j; 4: k;
	 * </p>
	 *
	 * @param pixel_maskDataset the pixel_maskDataset
	 */
	public DataNode setPixel_mask(IDataset pixel_maskDataset);

	/**
	 * Custom pixel mask for this channel. May include nP as the first dimension for
	 * masks that vary for each scan point.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 2: i; 3: j; 4: k;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getPixel_maskScalar();

	/**
	 * Custom pixel mask for this channel. May include nP as the first dimension for
	 * masks that vary for each scan point.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 2: i; 3: j; 4: k;
	 * </p>
	 *
	 * @param pixel_mask the pixel_mask
	 */
	public DataNode setPixel_maskScalar(Long pixel_maskValue);

	/**
	 * The value at which the detector goes into saturation.
	 * Especially common to CCD detectors, the data
	 * is known to be invalid above this value.
	 * For example, given a saturation_value and an underload_value, the valid
	 * pixels are those less than or equal to the saturation_value and greater
	 * than or equal to the underload_value.
	 * The precise type should match the type of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSaturation_value();

	/**
	 * The value at which the detector goes into saturation.
	 * Especially common to CCD detectors, the data
	 * is known to be invalid above this value.
	 * For example, given a saturation_value and an underload_value, the valid
	 * pixels are those less than or equal to the saturation_value and greater
	 * than or equal to the underload_value.
	 * The precise type should match the type of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param saturation_valueDataset the saturation_valueDataset
	 */
	public DataNode setSaturation_value(IDataset saturation_valueDataset);

	/**
	 * The value at which the detector goes into saturation.
	 * Especially common to CCD detectors, the data
	 * is known to be invalid above this value.
	 * For example, given a saturation_value and an underload_value, the valid
	 * pixels are those less than or equal to the saturation_value and greater
	 * than or equal to the underload_value.
	 * The precise type should match the type of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSaturation_valueScalar();

	/**
	 * The value at which the detector goes into saturation.
	 * Especially common to CCD detectors, the data
	 * is known to be invalid above this value.
	 * For example, given a saturation_value and an underload_value, the valid
	 * pixels are those less than or equal to the saturation_value and greater
	 * than or equal to the underload_value.
	 * The precise type should match the type of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param saturation_value the saturation_value
	 */
	public DataNode setSaturation_valueScalar(Number saturation_valueValue);

	/**
	 * The lowest value at which pixels for this detector would be reasonably
	 * measured. The data is known to be invalid below this value.
	 * For example, given a saturation_value and an underload_value, the valid
	 * pixels are those less than or equal to the saturation_value and greater
	 * than or equal to the underload_value.
	 * The precise type should match the type of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getUnderload_value();

	/**
	 * The lowest value at which pixels for this detector would be reasonably
	 * measured. The data is known to be invalid below this value.
	 * For example, given a saturation_value and an underload_value, the valid
	 * pixels are those less than or equal to the saturation_value and greater
	 * than or equal to the underload_value.
	 * The precise type should match the type of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param underload_valueDataset the underload_valueDataset
	 */
	public DataNode setUnderload_value(IDataset underload_valueDataset);

	/**
	 * The lowest value at which pixels for this detector would be reasonably
	 * measured. The data is known to be invalid below this value.
	 * For example, given a saturation_value and an underload_value, the valid
	 * pixels are those less than or equal to the saturation_value and greater
	 * than or equal to the underload_value.
	 * The precise type should match the type of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getUnderload_valueScalar();

	/**
	 * The lowest value at which pixels for this detector would be reasonably
	 * measured. The data is known to be invalid below this value.
	 * For example, given a saturation_value and an underload_value, the valid
	 * pixels are those less than or equal to the saturation_value and greater
	 * than or equal to the underload_value.
	 * The precise type should match the type of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param underload_value the underload_value
	 */
	public DataNode setUnderload_valueScalar(Number underload_valueValue);

}
