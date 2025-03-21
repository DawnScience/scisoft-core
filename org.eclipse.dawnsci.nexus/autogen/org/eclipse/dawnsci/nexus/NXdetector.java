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

import java.util.Date;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * A detector, detector bank, or multidetector.
 * <p><b>Symbols:</b>
 * These symbols will be used below to illustrate the coordination of the rank and sizes of datasets and the
 * preferred ordering of the dimensions. Each of these are optional (so the rank of the datasets
 * will vary according to the situation) and the general ordering principle is slowest to fastest.
 * The type of each dimension should follow the order of scan points, detector output (e.g. pixels),
 * then time-of-flight (i.e. spectroscopy, spectrometry). Note that the output of a detector is not limited
 * to single values (0D), lists (1D) and images (2), but three or higher dimensional arrays can be produced
 * by a detector at each trigger.<ul>
 * <li><b>nP</b>
 * number of scan points (only present in scanning measurements)</li>
 * <li><b>i</b>
 * number of detector pixels in the first (slowest) direction</li>
 * <li><b>j</b>
 * number of detector pixels in the second (faster) direction</li>
 * <li><b>tof</b>
 * number of bins in the time-of-flight histogram</li></ul></p>
 *
 */
public interface NXdetector extends NXobject {

	public static final String NX_TIME_OF_FLIGHT = "time_of_flight";
	public static final String NX_TIME_OF_FLIGHT_ATTRIBUTE_AXIS = "axis";
	public static final String NX_TIME_OF_FLIGHT_ATTRIBUTE_PRIMARY = "primary";
	public static final String NX_TIME_OF_FLIGHT_ATTRIBUTE_LONG_NAME = "long_name";
	public static final String NX_RAW_TIME_OF_FLIGHT = "raw_time_of_flight";
	public static final String NX_RAW_TIME_OF_FLIGHT_ATTRIBUTE_FREQUENCY = "frequency";
	public static final String NX_DETECTOR_NUMBER = "detector_number";
	public static final String NX_DATA = "data";
	public static final String NX_DATA_ATTRIBUTE_LONG_NAME = "long_name";
	public static final String NX_DATA_ATTRIBUTE_CHECK_SUM = "check_sum";
	public static final String NX_DATA_ERRORS = "data_errors";
	public static final String NX_X_PIXEL_OFFSET = "x_pixel_offset";
	public static final String NX_X_PIXEL_OFFSET_ATTRIBUTE_AXIS = "axis";
	public static final String NX_X_PIXEL_OFFSET_ATTRIBUTE_PRIMARY = "primary";
	public static final String NX_X_PIXEL_OFFSET_ATTRIBUTE_LONG_NAME = "long_name";
	public static final String NX_Y_PIXEL_OFFSET = "y_pixel_offset";
	public static final String NX_Y_PIXEL_OFFSET_ATTRIBUTE_AXIS = "axis";
	public static final String NX_Y_PIXEL_OFFSET_ATTRIBUTE_PRIMARY = "primary";
	public static final String NX_Y_PIXEL_OFFSET_ATTRIBUTE_LONG_NAME = "long_name";
	public static final String NX_Z_PIXEL_OFFSET = "z_pixel_offset";
	public static final String NX_Z_PIXEL_OFFSET_ATTRIBUTE_AXIS = "axis";
	public static final String NX_Z_PIXEL_OFFSET_ATTRIBUTE_PRIMARY = "primary";
	public static final String NX_Z_PIXEL_OFFSET_ATTRIBUTE_LONG_NAME = "long_name";
	public static final String NX_DISTANCE = "distance";
	public static final String NX_POLAR_ANGLE = "polar_angle";
	public static final String NX_AZIMUTHAL_ANGLE = "azimuthal_angle";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_SERIAL_NUMBER = "serial_number";
	public static final String NX_LOCAL_NAME = "local_name";
	public static final String NX_SOLID_ANGLE = "solid_angle";
	public static final String NX_X_PIXEL_SIZE = "x_pixel_size";
	public static final String NX_Y_PIXEL_SIZE = "y_pixel_size";
	public static final String NX_DEAD_TIME = "dead_time";
	public static final String NX_GAS_PRESSURE = "gas_pressure";
	public static final String NX_DETECTION_GAS_PATH = "detection_gas_path";
	public static final String NX_CRATE = "crate";
	public static final String NX_CRATE_ATTRIBUTE_LOCAL_NAME = "local_name";
	public static final String NX_SLOT = "slot";
	public static final String NX_SLOT_ATTRIBUTE_LOCAL_NAME = "local_name";
	public static final String NX_INPUT = "input";
	public static final String NX_INPUT_ATTRIBUTE_LOCAL_NAME = "local_name";
	public static final String NX_TYPE = "type";
	public static final String NX_REAL_TIME = "real_time";
	public static final String NX_START_TIME = "start_time";
	public static final String NX_START_TIME_ATTRIBUTE_START = "start";
	public static final String NX_STOP_TIME = "stop_time";
	public static final String NX_STOP_TIME_ATTRIBUTE_START = "start";
	public static final String NX_CALIBRATION_DATE = "calibration_date";
	public static final String NX_LAYOUT = "layout";
	public static final String NX_COUNT_TIME = "count_time";
	public static final String NX_SEQUENCE_NUMBER = "sequence_number";
	public static final String NX_BEAM_CENTER_X = "beam_center_x";
	public static final String NX_BEAM_CENTER_Y = "beam_center_y";
	public static final String NX_FRAME_START_NUMBER = "frame_start_number";
	public static final String NX_DIAMETER = "diameter";
	public static final String NX_ACQUISITION_MODE = "acquisition_mode";
	public static final String NX_ANGULAR_CALIBRATION_APPLIED = "angular_calibration_applied";
	public static final String NX_ANGULAR_CALIBRATION = "angular_calibration";
	public static final String NX_FLATFIELD_APPLIED = "flatfield_applied";
	public static final String NX_FLATFIELD = "flatfield";
	public static final String NX_FLATFIELD_ERRORS = "flatfield_errors";
	public static final String NX_PIXEL_MASK_APPLIED = "pixel_mask_applied";
	public static final String NX_PIXEL_MASK = "pixel_mask";
	public static final String NX_IMAGE_KEY = "image_key";
	public static final String NX_COUNTRATE_CORRECTION_APPLIED = "countrate_correction_applied";
	public static final String NX_COUNTRATE_CORRECTION_LOOKUP_TABLE = "countrate_correction_lookup_table";
	public static final String NX_VIRTUAL_PIXEL_INTERPOLATION_APPLIED = "virtual_pixel_interpolation_applied";
	public static final String NX_BIT_DEPTH_READOUT = "bit_depth_readout";
	public static final String NX_DETECTOR_READOUT_TIME = "detector_readout_time";
	public static final String NX_TRIGGER_DELAY_TIME = "trigger_delay_time";
	public static final String NX_TRIGGER_DELAY_TIME_SET = "trigger_delay_time_set";
	public static final String NX_TRIGGER_INTERNAL_DELAY_TIME = "trigger_internal_delay_time";
	public static final String NX_TRIGGER_DEAD_TIME = "trigger_dead_time";
	public static final String NX_FRAME_TIME = "frame_time";
	public static final String NX_GAIN_SETTING = "gain_setting";
	public static final String NX_SATURATION_VALUE = "saturation_value";
	public static final String NX_UNDERLOAD_VALUE = "underload_value";
	public static final String NX_NUMBER_OF_CYCLES = "number_of_cycles";
	public static final String NX_SENSOR_MATERIAL = "sensor_material";
	public static final String NX_SENSOR_THICKNESS = "sensor_thickness";
	public static final String NX_THRESHOLD_ENERGY = "threshold_energy";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Total time of flight
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: tof+1;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTime_of_flight();

	/**
	 * Total time of flight
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: tof+1;
	 * </p>
	 *
	 * @param time_of_flightDataset the time_of_flightDataset
	 */
	public DataNode setTime_of_flight(IDataset time_of_flightDataset);

	/**
	 * Total time of flight
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: tof+1;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTime_of_flightScalar();

	/**
	 * Total time of flight
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: tof+1;
	 * </p>
	 *
	 * @param time_of_flight the time_of_flight
	 */
	public DataNode setTime_of_flightScalar(Double time_of_flightValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @return  the value.
	 */
	@Deprecated
	public Long getTime_of_flightAttributeAxis();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @param axisValue the axisValue
	 */
	@Deprecated
	public void setTime_of_flightAttributeAxis(Long axisValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @return  the value.
	 */
	@Deprecated
	public Long getTime_of_flightAttributePrimary();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @param primaryValue the primaryValue
	 */
	@Deprecated
	public void setTime_of_flightAttributePrimary(Long primaryValue);

	/**
	 * Total time of flight
	 *
	 * @return  the value.
	 */
	public String getTime_of_flightAttributeLong_name();

	/**
	 * Total time of flight
	 *
	 * @param long_nameValue the long_nameValue
	 */
	public void setTime_of_flightAttributeLong_name(String long_nameValue);

	/**
	 * In DAQ clock pulses
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_PULSES
	 * <b>Dimensions:</b> 1: tof+1;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRaw_time_of_flight();

	/**
	 * In DAQ clock pulses
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_PULSES
	 * <b>Dimensions:</b> 1: tof+1;
	 * </p>
	 *
	 * @param raw_time_of_flightDataset the raw_time_of_flightDataset
	 */
	public DataNode setRaw_time_of_flight(IDataset raw_time_of_flightDataset);

	/**
	 * In DAQ clock pulses
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_PULSES
	 * <b>Dimensions:</b> 1: tof+1;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getRaw_time_of_flightScalar();

	/**
	 * In DAQ clock pulses
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_PULSES
	 * <b>Dimensions:</b> 1: tof+1;
	 * </p>
	 *
	 * @param raw_time_of_flight the raw_time_of_flight
	 */
	public DataNode setRaw_time_of_flightScalar(Long raw_time_of_flightValue);

	/**
	 * Clock frequency in Hz
	 *
	 * @return  the value.
	 */
	public Number getRaw_time_of_flightAttributeFrequency();

	/**
	 * Clock frequency in Hz
	 *
	 * @param frequencyValue the frequencyValue
	 */
	public void setRaw_time_of_flightAttributeFrequency(Number frequencyValue);

	/**
	 * Identifier for detector (pixels)
	 * Can be multidimensional, if needed
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDetector_number();

	/**
	 * Identifier for detector (pixels)
	 * Can be multidimensional, if needed
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param detector_numberDataset the detector_numberDataset
	 */
	public DataNode setDetector_number(IDataset detector_numberDataset);

	/**
	 * Identifier for detector (pixels)
	 * Can be multidimensional, if needed
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDetector_numberScalar();

	/**
	 * Identifier for detector (pixels)
	 * Can be multidimensional, if needed
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param detector_number the detector_number
	 */
	public DataNode setDetector_numberScalar(Long detector_numberValue);

	/**
	 * Data values from the detector. The rank and dimension ordering should follow a principle of
	 * slowest to fastest measurement axes and may be explicitly specified in application definitions.
	 * Mechanical scanning of objects (e.g. sample position/angle, incident beam energy, etc) tends to be
	 * the slowest part of an experiment and so any such scan axes should be allocated to the first dimensions
	 * of the array. Note that in some cases it may be useful to represent a 2D set of scan points as a single
	 * scan-axis in the data array, especially if the scan pattern doesn't fit a rectangular array nicely.
	 * Repetition of an experiment in a time series tends to be used similar to a slow scan axis
	 * and so will often be in the first dimension of the data array.
	 * The next fastest axes are typically the readout of the detector. A point detector will not add any dimensions
	 * (as it is just a single value per scan point) to the data array, a strip detector will add one dimension, an
	 * imaging detector will add two dimensions (e.g. X, Y axes) and detectors outputting higher dimensional data
	 * will add the corresponding number of dimensions. Note that the detector dimensions don't necessarily have to
	 * be written in order of the actual readout speeds - the slowest to fastest rule principle is only a guide.
	 * Finally, detectors that operate in a time-of-flight mode, such as a neutron spectrometer or a silicon drift
	 * detector (used for X-ray fluorescence) tend to have their dimension(s) added to the last dimensions in the data array.
	 * The type of each dimension should should follow the order of scan points, detector pixels,
	 * then time-of-flight (i.e. spectroscopy, spectrometry). The rank and dimension sizes (see symbol list)
	 * shown here are merely illustrative of coordination between related datasets.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j; 4: tof;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getData();

	/**
	 * Data values from the detector. The rank and dimension ordering should follow a principle of
	 * slowest to fastest measurement axes and may be explicitly specified in application definitions.
	 * Mechanical scanning of objects (e.g. sample position/angle, incident beam energy, etc) tends to be
	 * the slowest part of an experiment and so any such scan axes should be allocated to the first dimensions
	 * of the array. Note that in some cases it may be useful to represent a 2D set of scan points as a single
	 * scan-axis in the data array, especially if the scan pattern doesn't fit a rectangular array nicely.
	 * Repetition of an experiment in a time series tends to be used similar to a slow scan axis
	 * and so will often be in the first dimension of the data array.
	 * The next fastest axes are typically the readout of the detector. A point detector will not add any dimensions
	 * (as it is just a single value per scan point) to the data array, a strip detector will add one dimension, an
	 * imaging detector will add two dimensions (e.g. X, Y axes) and detectors outputting higher dimensional data
	 * will add the corresponding number of dimensions. Note that the detector dimensions don't necessarily have to
	 * be written in order of the actual readout speeds - the slowest to fastest rule principle is only a guide.
	 * Finally, detectors that operate in a time-of-flight mode, such as a neutron spectrometer or a silicon drift
	 * detector (used for X-ray fluorescence) tend to have their dimension(s) added to the last dimensions in the data array.
	 * The type of each dimension should should follow the order of scan points, detector pixels,
	 * then time-of-flight (i.e. spectroscopy, spectrometry). The rank and dimension sizes (see symbol list)
	 * shown here are merely illustrative of coordination between related datasets.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j; 4: tof;
	 * </p>
	 *
	 * @param dataDataset the dataDataset
	 */
	public DataNode setData(IDataset dataDataset);

	/**
	 * Data values from the detector. The rank and dimension ordering should follow a principle of
	 * slowest to fastest measurement axes and may be explicitly specified in application definitions.
	 * Mechanical scanning of objects (e.g. sample position/angle, incident beam energy, etc) tends to be
	 * the slowest part of an experiment and so any such scan axes should be allocated to the first dimensions
	 * of the array. Note that in some cases it may be useful to represent a 2D set of scan points as a single
	 * scan-axis in the data array, especially if the scan pattern doesn't fit a rectangular array nicely.
	 * Repetition of an experiment in a time series tends to be used similar to a slow scan axis
	 * and so will often be in the first dimension of the data array.
	 * The next fastest axes are typically the readout of the detector. A point detector will not add any dimensions
	 * (as it is just a single value per scan point) to the data array, a strip detector will add one dimension, an
	 * imaging detector will add two dimensions (e.g. X, Y axes) and detectors outputting higher dimensional data
	 * will add the corresponding number of dimensions. Note that the detector dimensions don't necessarily have to
	 * be written in order of the actual readout speeds - the slowest to fastest rule principle is only a guide.
	 * Finally, detectors that operate in a time-of-flight mode, such as a neutron spectrometer or a silicon drift
	 * detector (used for X-ray fluorescence) tend to have their dimension(s) added to the last dimensions in the data array.
	 * The type of each dimension should should follow the order of scan points, detector pixels,
	 * then time-of-flight (i.e. spectroscopy, spectrometry). The rank and dimension sizes (see symbol list)
	 * shown here are merely illustrative of coordination between related datasets.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j; 4: tof;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDataScalar();

	/**
	 * Data values from the detector. The rank and dimension ordering should follow a principle of
	 * slowest to fastest measurement axes and may be explicitly specified in application definitions.
	 * Mechanical scanning of objects (e.g. sample position/angle, incident beam energy, etc) tends to be
	 * the slowest part of an experiment and so any such scan axes should be allocated to the first dimensions
	 * of the array. Note that in some cases it may be useful to represent a 2D set of scan points as a single
	 * scan-axis in the data array, especially if the scan pattern doesn't fit a rectangular array nicely.
	 * Repetition of an experiment in a time series tends to be used similar to a slow scan axis
	 * and so will often be in the first dimension of the data array.
	 * The next fastest axes are typically the readout of the detector. A point detector will not add any dimensions
	 * (as it is just a single value per scan point) to the data array, a strip detector will add one dimension, an
	 * imaging detector will add two dimensions (e.g. X, Y axes) and detectors outputting higher dimensional data
	 * will add the corresponding number of dimensions. Note that the detector dimensions don't necessarily have to
	 * be written in order of the actual readout speeds - the slowest to fastest rule principle is only a guide.
	 * Finally, detectors that operate in a time-of-flight mode, such as a neutron spectrometer or a silicon drift
	 * detector (used for X-ray fluorescence) tend to have their dimension(s) added to the last dimensions in the data array.
	 * The type of each dimension should should follow the order of scan points, detector pixels,
	 * then time-of-flight (i.e. spectroscopy, spectrometry). The rank and dimension sizes (see symbol list)
	 * shown here are merely illustrative of coordination between related datasets.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j; 4: tof;
	 * </p>
	 *
	 * @param data the data
	 */
	public DataNode setDataScalar(Number dataValue);

	/**
	 * Title of measurement
	 *
	 * @return  the value.
	 */
	public String getDataAttributeLong_name();

	/**
	 * Title of measurement
	 *
	 * @param long_nameValue the long_nameValue
	 */
	public void setDataAttributeLong_name(String long_nameValue);

	/**
	 * Integral of data as check of data integrity
	 *
	 * @return  the value.
	 */
	public Long getDataAttributeCheck_sum();

	/**
	 * Integral of data as check of data integrity
	 *
	 * @param check_sumValue the check_sumValue
	 */
	public void setDataAttributeCheck_sum(Long check_sumValue);

	/**
	 * The best estimate of the uncertainty in the data value (array size should match the data field). Where
	 * possible, this should be the standard deviation, which has the same units
	 * as the data. The form data_error is deprecated.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j; 4: tof;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getData_errors();

	/**
	 * The best estimate of the uncertainty in the data value (array size should match the data field). Where
	 * possible, this should be the standard deviation, which has the same units
	 * as the data. The form data_error is deprecated.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j; 4: tof;
	 * </p>
	 *
	 * @param data_errorsDataset the data_errorsDataset
	 */
	public DataNode setData_errors(IDataset data_errorsDataset);

	/**
	 * The best estimate of the uncertainty in the data value (array size should match the data field). Where
	 * possible, this should be the standard deviation, which has the same units
	 * as the data. The form data_error is deprecated.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j; 4: tof;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getData_errorsScalar();

	/**
	 * The best estimate of the uncertainty in the data value (array size should match the data field). Where
	 * possible, this should be the standard deviation, which has the same units
	 * as the data. The form data_error is deprecated.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j; 4: tof;
	 * </p>
	 *
	 * @param data_errors the data_errors
	 */
	public DataNode setData_errorsScalar(Number data_errorsValue);

	/**
	 * Offset from the detector center in x-direction.
	 * Can be multidimensional when needed.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getX_pixel_offset();

	/**
	 * Offset from the detector center in x-direction.
	 * Can be multidimensional when needed.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param x_pixel_offsetDataset the x_pixel_offsetDataset
	 */
	public DataNode setX_pixel_offset(IDataset x_pixel_offsetDataset);

	/**
	 * Offset from the detector center in x-direction.
	 * Can be multidimensional when needed.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getX_pixel_offsetScalar();

	/**
	 * Offset from the detector center in x-direction.
	 * Can be multidimensional when needed.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param x_pixel_offset the x_pixel_offset
	 */
	public DataNode setX_pixel_offsetScalar(Double x_pixel_offsetValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @return  the value.
	 */
	@Deprecated
	public Long getX_pixel_offsetAttributeAxis();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @param axisValue the axisValue
	 */
	@Deprecated
	public void setX_pixel_offsetAttributeAxis(Long axisValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @return  the value.
	 */
	@Deprecated
	public Long getX_pixel_offsetAttributePrimary();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @param primaryValue the primaryValue
	 */
	@Deprecated
	public void setX_pixel_offsetAttributePrimary(Long primaryValue);

	/**
	 * x-axis offset from detector center
	 *
	 * @return  the value.
	 */
	public String getX_pixel_offsetAttributeLong_name();

	/**
	 * x-axis offset from detector center
	 *
	 * @param long_nameValue the long_nameValue
	 */
	public void setX_pixel_offsetAttributeLong_name(String long_nameValue);

	/**
	 * Offset from the detector center in the y-direction.
	 * Can be multidimensional when different values are required for each pixel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getY_pixel_offset();

	/**
	 * Offset from the detector center in the y-direction.
	 * Can be multidimensional when different values are required for each pixel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param y_pixel_offsetDataset the y_pixel_offsetDataset
	 */
	public DataNode setY_pixel_offset(IDataset y_pixel_offsetDataset);

	/**
	 * Offset from the detector center in the y-direction.
	 * Can be multidimensional when different values are required for each pixel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getY_pixel_offsetScalar();

	/**
	 * Offset from the detector center in the y-direction.
	 * Can be multidimensional when different values are required for each pixel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param y_pixel_offset the y_pixel_offset
	 */
	public DataNode setY_pixel_offsetScalar(Double y_pixel_offsetValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @return  the value.
	 */
	@Deprecated
	public Long getY_pixel_offsetAttributeAxis();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @param axisValue the axisValue
	 */
	@Deprecated
	public void setY_pixel_offsetAttributeAxis(Long axisValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @return  the value.
	 */
	@Deprecated
	public Long getY_pixel_offsetAttributePrimary();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @param primaryValue the primaryValue
	 */
	@Deprecated
	public void setY_pixel_offsetAttributePrimary(Long primaryValue);

	/**
	 * y-axis offset from detector center
	 *
	 * @return  the value.
	 */
	public String getY_pixel_offsetAttributeLong_name();

	/**
	 * y-axis offset from detector center
	 *
	 * @param long_nameValue the long_nameValue
	 */
	public void setY_pixel_offsetAttributeLong_name(String long_nameValue);

	/**
	 * Offset from the detector center in the z-direction.
	 * Can be multidimensional when different values are required for each pixel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getZ_pixel_offset();

	/**
	 * Offset from the detector center in the z-direction.
	 * Can be multidimensional when different values are required for each pixel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param z_pixel_offsetDataset the z_pixel_offsetDataset
	 */
	public DataNode setZ_pixel_offset(IDataset z_pixel_offsetDataset);

	/**
	 * Offset from the detector center in the z-direction.
	 * Can be multidimensional when different values are required for each pixel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getZ_pixel_offsetScalar();

	/**
	 * Offset from the detector center in the z-direction.
	 * Can be multidimensional when different values are required for each pixel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param z_pixel_offset the z_pixel_offset
	 */
	public DataNode setZ_pixel_offsetScalar(Double z_pixel_offsetValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @return  the value.
	 */
	@Deprecated
	public Long getZ_pixel_offsetAttributeAxis();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @param axisValue the axisValue
	 */
	@Deprecated
	public void setZ_pixel_offsetAttributeAxis(Long axisValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @return  the value.
	 */
	@Deprecated
	public Long getZ_pixel_offsetAttributePrimary();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li></ul></p>
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/436
	 * @param primaryValue the primaryValue
	 */
	@Deprecated
	public void setZ_pixel_offsetAttributePrimary(Long primaryValue);

	/**
	 * y-axis offset from detector center
	 *
	 * @return  the value.
	 */
	public String getZ_pixel_offsetAttributeLong_name();

	/**
	 * y-axis offset from detector center
	 *
	 * @param long_nameValue the long_nameValue
	 */
	public void setZ_pixel_offsetAttributeLong_name(String long_nameValue);

	/**
	 * This is the distance to the previous component in the
	 * instrument; most often the sample. The usage depends on the
	 * nature of the detector: Most often it is the distance of the
	 * detector assembly. But there are irregular detectors. In this
	 * case the distance must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDistance();

	/**
	 * This is the distance to the previous component in the
	 * instrument; most often the sample. The usage depends on the
	 * nature of the detector: Most often it is the distance of the
	 * detector assembly. But there are irregular detectors. In this
	 * case the distance must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @param distanceDataset the distanceDataset
	 */
	public DataNode setDistance(IDataset distanceDataset);

	/**
	 * This is the distance to the previous component in the
	 * instrument; most often the sample. The usage depends on the
	 * nature of the detector: Most often it is the distance of the
	 * detector assembly. But there are irregular detectors. In this
	 * case the distance must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDistanceScalar();

	/**
	 * This is the distance to the previous component in the
	 * instrument; most often the sample. The usage depends on the
	 * nature of the detector: Most often it is the distance of the
	 * detector assembly. But there are irregular detectors. In this
	 * case the distance must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @param distance the distance
	 */
	public DataNode setDistanceScalar(Double distanceValue);

	/**
	 * This is the polar angle of the detector towards the previous
	 * component in the instrument; most often the sample.
	 * The usage depends on the
	 * nature of the detector.
	 * Most often it is the polar_angle of the detector assembly.
	 * But there are irregular detectors.
	 * In this
	 * case, the polar_angle must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPolar_angle();

	/**
	 * This is the polar angle of the detector towards the previous
	 * component in the instrument; most often the sample.
	 * The usage depends on the
	 * nature of the detector.
	 * Most often it is the polar_angle of the detector assembly.
	 * But there are irregular detectors.
	 * In this
	 * case, the polar_angle must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @param polar_angleDataset the polar_angleDataset
	 */
	public DataNode setPolar_angle(IDataset polar_angleDataset);

	/**
	 * This is the polar angle of the detector towards the previous
	 * component in the instrument; most often the sample.
	 * The usage depends on the
	 * nature of the detector.
	 * Most often it is the polar_angle of the detector assembly.
	 * But there are irregular detectors.
	 * In this
	 * case, the polar_angle must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPolar_angleScalar();

	/**
	 * This is the polar angle of the detector towards the previous
	 * component in the instrument; most often the sample.
	 * The usage depends on the
	 * nature of the detector.
	 * Most often it is the polar_angle of the detector assembly.
	 * But there are irregular detectors.
	 * In this
	 * case, the polar_angle must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @param polar_angle the polar_angle
	 */
	public DataNode setPolar_angleScalar(Double polar_angleValue);

	/**
	 * This is the azimuthal angle angle of the detector towards
	 * the previous component in the instrument; most often the sample.
	 * The usage depends on the
	 * nature of the detector.
	 * Most often it is the azimuthal_angle of the detector assembly.
	 * But there are irregular detectors.
	 * In this
	 * case, the azimuthal_angle must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAzimuthal_angle();

	/**
	 * This is the azimuthal angle angle of the detector towards
	 * the previous component in the instrument; most often the sample.
	 * The usage depends on the
	 * nature of the detector.
	 * Most often it is the azimuthal_angle of the detector assembly.
	 * But there are irregular detectors.
	 * In this
	 * case, the azimuthal_angle must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @param azimuthal_angleDataset the azimuthal_angleDataset
	 */
	public DataNode setAzimuthal_angle(IDataset azimuthal_angleDataset);

	/**
	 * This is the azimuthal angle angle of the detector towards
	 * the previous component in the instrument; most often the sample.
	 * The usage depends on the
	 * nature of the detector.
	 * Most often it is the azimuthal_angle of the detector assembly.
	 * But there are irregular detectors.
	 * In this
	 * case, the azimuthal_angle must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAzimuthal_angleScalar();

	/**
	 * This is the azimuthal angle angle of the detector towards
	 * the previous component in the instrument; most often the sample.
	 * The usage depends on the
	 * nature of the detector.
	 * Most often it is the azimuthal_angle of the detector assembly.
	 * But there are irregular detectors.
	 * In this
	 * case, the azimuthal_angle must be specified for each detector pixel.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @param azimuthal_angle the azimuthal_angle
	 */
	public DataNode setAzimuthal_angleScalar(Double azimuthal_angleValue);

	/**
	 * name/manufacturer/model/etc. information
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * name/manufacturer/model/etc. information
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * name/manufacturer/model/etc. information
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * name/manufacturer/model/etc. information
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Serial number for the detector
	 *
	 * @return  the value.
	 */
	public Dataset getSerial_number();

	/**
	 * Serial number for the detector
	 *
	 * @param serial_numberDataset the serial_numberDataset
	 */
	public DataNode setSerial_number(IDataset serial_numberDataset);

	/**
	 * Serial number for the detector
	 *
	 * @return  the value.
	 */
	public String getSerial_numberScalar();

	/**
	 * Serial number for the detector
	 *
	 * @param serial_number the serial_number
	 */
	public DataNode setSerial_numberScalar(String serial_numberValue);

	/**
	 * Local name for the detector
	 *
	 * @return  the value.
	 */
	public Dataset getLocal_name();

	/**
	 * Local name for the detector
	 *
	 * @param local_nameDataset the local_nameDataset
	 */
	public DataNode setLocal_name(IDataset local_nameDataset);

	/**
	 * Local name for the detector
	 *
	 * @return  the value.
	 */
	public String getLocal_nameScalar();

	/**
	 * Local name for the detector
	 *
	 * @param local_name the local_name
	 */
	public DataNode setLocal_nameScalar(String local_nameValue);

	/**
	 * Position and orientation of detector
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the detector and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 * Position and orientation of detector
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the detector and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * Position and orientation of detector</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the detector and NXoff_geometry to describe its shape instead
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public NXgeometry getGeometry(String name);

	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * Position and orientation of detector</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the detector and NXoff_geometry to describe its shape instead
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry);

	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li>
	 * Position and orientation of detector</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the detector and NXoff_geometry to describe its shape instead
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Position and orientation of detector</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the detector and NXoff_geometry to describe its shape instead
	 * @param geometry the child nodes to add
	 */

	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry);


	/**
	 * Solid angle subtended by the detector at the sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_SOLID_ANGLE
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSolid_angle();

	/**
	 * Solid angle subtended by the detector at the sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_SOLID_ANGLE
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param solid_angleDataset the solid_angleDataset
	 */
	public DataNode setSolid_angle(IDataset solid_angleDataset);

	/**
	 * Solid angle subtended by the detector at the sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_SOLID_ANGLE
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSolid_angleScalar();

	/**
	 * Solid angle subtended by the detector at the sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_SOLID_ANGLE
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param solid_angle the solid_angle
	 */
	public DataNode setSolid_angleScalar(Double solid_angleValue);

	/**
	 * Size of each detector pixel. If it is scalar all pixels are the same size.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getX_pixel_size();

	/**
	 * Size of each detector pixel. If it is scalar all pixels are the same size.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param x_pixel_sizeDataset the x_pixel_sizeDataset
	 */
	public DataNode setX_pixel_size(IDataset x_pixel_sizeDataset);

	/**
	 * Size of each detector pixel. If it is scalar all pixels are the same size.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getX_pixel_sizeScalar();

	/**
	 * Size of each detector pixel. If it is scalar all pixels are the same size.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param x_pixel_size the x_pixel_size
	 */
	public DataNode setX_pixel_sizeScalar(Double x_pixel_sizeValue);

	/**
	 * Size of each detector pixel. If it is scalar all pixels are the same size
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getY_pixel_size();

	/**
	 * Size of each detector pixel. If it is scalar all pixels are the same size
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param y_pixel_sizeDataset the y_pixel_sizeDataset
	 */
	public DataNode setY_pixel_size(IDataset y_pixel_sizeDataset);

	/**
	 * Size of each detector pixel. If it is scalar all pixels are the same size
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getY_pixel_sizeScalar();

	/**
	 * Size of each detector pixel. If it is scalar all pixels are the same size
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param y_pixel_size the y_pixel_size
	 */
	public DataNode setY_pixel_sizeScalar(Double y_pixel_sizeValue);

	/**
	 * Detector dead time
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDead_time();

	/**
	 * Detector dead time
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @param dead_timeDataset the dead_timeDataset
	 */
	public DataNode setDead_time(IDataset dead_timeDataset);

	/**
	 * Detector dead time
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDead_timeScalar();

	/**
	 * Detector dead time
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @param dead_time the dead_time
	 */
	public DataNode setDead_timeScalar(Double dead_timeValue);

	/**
	 * Detector gas pressure
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getGas_pressure();

	/**
	 * Detector gas pressure
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param gas_pressureDataset the gas_pressureDataset
	 */
	public DataNode setGas_pressure(IDataset gas_pressureDataset);

	/**
	 * Detector gas pressure
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getGas_pressureScalar();

	/**
	 * Detector gas pressure
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param gas_pressure the gas_pressure
	 */
	public DataNode setGas_pressureScalar(Double gas_pressureValue);

	/**
	 * maximum drift space dimension
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDetection_gas_path();

	/**
	 * maximum drift space dimension
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param detection_gas_pathDataset the detection_gas_pathDataset
	 */
	public DataNode setDetection_gas_path(IDataset detection_gas_pathDataset);

	/**
	 * maximum drift space dimension
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDetection_gas_pathScalar();

	/**
	 * maximum drift space dimension
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param detection_gas_path the detection_gas_path
	 */
	public DataNode setDetection_gas_pathScalar(Double detection_gas_pathValue);

	/**
	 * Crate number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCrate();

	/**
	 * Crate number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param crateDataset the crateDataset
	 */
	public DataNode setCrate(IDataset crateDataset);

	/**
	 * Crate number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCrateScalar();

	/**
	 * Crate number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param crate the crate
	 */
	public DataNode setCrateScalar(Long crateValue);

	/**
	 * Equivalent local term
	 *
	 * @return  the value.
	 */
	public String getCrateAttributeLocal_name();

	/**
	 * Equivalent local term
	 *
	 * @param local_nameValue the local_nameValue
	 */
	public void setCrateAttributeLocal_name(String local_nameValue);

	/**
	 * Slot number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSlot();

	/**
	 * Slot number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param slotDataset the slotDataset
	 */
	public DataNode setSlot(IDataset slotDataset);

	/**
	 * Slot number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getSlotScalar();

	/**
	 * Slot number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param slot the slot
	 */
	public DataNode setSlotScalar(Long slotValue);

	/**
	 * Equivalent local term
	 *
	 * @return  the value.
	 */
	public String getSlotAttributeLocal_name();

	/**
	 * Equivalent local term
	 *
	 * @param local_nameValue the local_nameValue
	 */
	public void setSlotAttributeLocal_name(String local_nameValue);

	/**
	 * Input number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getInput();

	/**
	 * Input number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param inputDataset the inputDataset
	 */
	public DataNode setInput(IDataset inputDataset);

	/**
	 * Input number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getInputScalar();

	/**
	 * Input number of detector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param input the input
	 */
	public DataNode setInputScalar(Long inputValue);

	/**
	 * Equivalent local term
	 *
	 * @return  the value.
	 */
	public String getInputAttributeLocal_name();

	/**
	 * Equivalent local term
	 *
	 * @param local_nameValue the local_nameValue
	 */
	public void setInputAttributeLocal_name(String local_nameValue);

	/**
	 * Description of type such as He3 gas cylinder, He3 PSD, scintillator,
	 * fission chamber, proportion counter, ion chamber, ccd, pixel, image plate,
	 * CMOS, ...
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Description of type such as He3 gas cylinder, He3 PSD, scintillator,
	 * fission chamber, proportion counter, ion chamber, ccd, pixel, image plate,
	 * CMOS, ...
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Description of type such as He3 gas cylinder, He3 PSD, scintillator,
	 * fission chamber, proportion counter, ion chamber, ccd, pixel, image plate,
	 * CMOS, ...
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Description of type such as He3 gas cylinder, He3 PSD, scintillator,
	 * fission chamber, proportion counter, ion chamber, ccd, pixel, image plate,
	 * CMOS, ...
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Group containing the description and metadata for a single channel from a multi-channel
	 * detector.
	 * Given an :ref:`NXdata` group linked as part of an NXdetector group that has an axis with
	 * named channels (see the example in :ref:`NXdata </NXdata@default_slice-attribute>`),
	 * the NXdetector will have a series of NXdetector_channel groups, one for each channel,
	 * named CHANNELNAME_channel.
	 *
	 * @return  the value.
	 */
	public NXdetector_channel getChannelname_channel();

	/**
	 * Group containing the description and metadata for a single channel from a multi-channel
	 * detector.
	 * Given an :ref:`NXdata` group linked as part of an NXdetector group that has an axis with
	 * named channels (see the example in :ref:`NXdata </NXdata@default_slice-attribute>`),
	 * the NXdetector will have a series of NXdetector_channel groups, one for each channel,
	 * named CHANNELNAME_channel.
	 *
	 * @param channelname_channelGroup the channelname_channelGroup
	 */
	public void setChannelname_channel(NXdetector_channel channelname_channelGroup);

	/**
	 * Spectral efficiency of detector with respect to e.g. wavelength
	 *
	 * @return  the value.
	 */
	public NXdata getEfficiency();

	/**
	 * Spectral efficiency of detector with respect to e.g. wavelength
	 *
	 * @param efficiencyGroup the efficiencyGroup
	 */
	public void setEfficiency(NXdata efficiencyGroup);

	/**
	 * Real-time of the exposure (use this if exposure time varies for
	 * each array element, otherwise use ``count_time`` field).
	 * Most often there is a single real time value that is constant across
	 * an entire image frame. In such cases, only a 1-D array is needed.
	 * But there are detectors in which the real time
	 * changes per pixel. In that case, more than one dimension is needed. Therefore
	 * the rank of this field should be less than or equal to (detector rank + 1).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getReal_time();

	/**
	 * Real-time of the exposure (use this if exposure time varies for
	 * each array element, otherwise use ``count_time`` field).
	 * Most often there is a single real time value that is constant across
	 * an entire image frame. In such cases, only a 1-D array is needed.
	 * But there are detectors in which the real time
	 * changes per pixel. In that case, more than one dimension is needed. Therefore
	 * the rank of this field should be less than or equal to (detector rank + 1).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @param real_timeDataset the real_timeDataset
	 */
	public DataNode setReal_time(IDataset real_timeDataset);

	/**
	 * Real-time of the exposure (use this if exposure time varies for
	 * each array element, otherwise use ``count_time`` field).
	 * Most often there is a single real time value that is constant across
	 * an entire image frame. In such cases, only a 1-D array is needed.
	 * But there are detectors in which the real time
	 * changes per pixel. In that case, more than one dimension is needed. Therefore
	 * the rank of this field should be less than or equal to (detector rank + 1).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getReal_timeScalar();

	/**
	 * Real-time of the exposure (use this if exposure time varies for
	 * each array element, otherwise use ``count_time`` field).
	 * Most often there is a single real time value that is constant across
	 * an entire image frame. In such cases, only a 1-D array is needed.
	 * But there are detectors in which the real time
	 * changes per pixel. In that case, more than one dimension is needed. Therefore
	 * the rank of this field should be less than or equal to (detector rank + 1).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP; 2: i; 3: j;
	 * </p>
	 *
	 * @param real_time the real_time
	 */
	public DataNode setReal_timeScalar(Number real_timeValue);

	/**
	 * start time for each frame, with the ``start`` attribute as absolute reference
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStart_time();

	/**
	 * start time for each frame, with the ``start`` attribute as absolute reference
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @param start_timeDataset the start_timeDataset
	 */
	public DataNode setStart_time(IDataset start_timeDataset);

	/**
	 * start time for each frame, with the ``start`` attribute as absolute reference
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getStart_timeScalar();

	/**
	 * start time for each frame, with the ``start`` attribute as absolute reference
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @param start_time the start_time
	 */
	public DataNode setStart_timeScalar(Double start_timeValue);

	/**
	 *
	 * @return  the value.
	 */
	public Date getStart_timeAttributeStart();

	/**
	 *
	 * @param startValue the startValue
	 */
	public void setStart_timeAttributeStart(Date startValue);

	/**
	 * stop time for each frame, with the ``start`` attribute as absolute reference
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStop_time();

	/**
	 * stop time for each frame, with the ``start`` attribute as absolute reference
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @param stop_timeDataset the stop_timeDataset
	 */
	public DataNode setStop_time(IDataset stop_timeDataset);

	/**
	 * stop time for each frame, with the ``start`` attribute as absolute reference
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getStop_timeScalar();

	/**
	 * stop time for each frame, with the ``start`` attribute as absolute reference
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @param stop_time the stop_time
	 */
	public DataNode setStop_timeScalar(Double stop_timeValue);

	/**
	 *
	 * @return  the value.
	 */
	public Date getStop_timeAttributeStart();

	/**
	 *
	 * @param startValue the startValue
	 */
	public void setStop_timeAttributeStart(Date startValue);

	/**
	 * date of last calibration (geometry and/or efficiency) measurements
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCalibration_date();

	/**
	 * date of last calibration (geometry and/or efficiency) measurements
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param calibration_dateDataset the calibration_dateDataset
	 */
	public DataNode setCalibration_date(IDataset calibration_dateDataset);

	/**
	 * date of last calibration (geometry and/or efficiency) measurements
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getCalibration_dateScalar();

	/**
	 * date of last calibration (geometry and/or efficiency) measurements
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param calibration_date the calibration_date
	 */
	public DataNode setCalibration_dateScalar(Date calibration_dateValue);

	/**
	 * summary of conversion of array data to pixels (e.g. polynomial
	 * approximations) and location of details of the calibrations
	 *
	 * @return  the value.
	 */
	public NXnote getCalibration_method();

	/**
	 * summary of conversion of array data to pixels (e.g. polynomial
	 * approximations) and location of details of the calibrations
	 *
	 * @param calibration_methodGroup the calibration_methodGroup
	 */
	public void setCalibration_method(NXnote calibration_methodGroup);

	/**
	 * How the detector is represented
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>point</b> </li>
	 * <li><b>linear</b> </li>
	 * <li><b>area</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLayout();

	/**
	 * How the detector is represented
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>point</b> </li>
	 * <li><b>linear</b> </li>
	 * <li><b>area</b> </li></ul></p>
	 * </p>
	 *
	 * @param layoutDataset the layoutDataset
	 */
	public DataNode setLayout(IDataset layoutDataset);

	/**
	 * How the detector is represented
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>point</b> </li>
	 * <li><b>linear</b> </li>
	 * <li><b>area</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getLayoutScalar();

	/**
	 * How the detector is represented
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>point</b> </li>
	 * <li><b>linear</b> </li>
	 * <li><b>area</b> </li></ul></p>
	 * </p>
	 *
	 * @param layout the layout
	 */
	public DataNode setLayoutScalar(String layoutValue);

	/**
	 * Elapsed actual counting time
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCount_time();

	/**
	 * Elapsed actual counting time
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @param count_timeDataset the count_timeDataset
	 */
	public DataNode setCount_time(IDataset count_timeDataset);

	/**
	 * Elapsed actual counting time
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCount_timeScalar();

	/**
	 * Elapsed actual counting time
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @param count_time the count_time
	 */
	public DataNode setCount_timeScalar(Number count_timeValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXnote getData_file();

	/**
	 *
	 * @param data_fileGroup the data_fileGroup
	 */
	public void setData_file(NXnote data_fileGroup);

	/**
	 * Use this group to provide other data related to this NXdetector group.
	 *
	 * @return  the value.
	 */
	public NXcollection getCollection();

	/**
	 * Use this group to provide other data related to this NXdetector group.
	 *
	 * @param collectionGroup the collectionGroup
	 */
	public void setCollection(NXcollection collectionGroup);

	/**
	 * Get a NXcollection node by name:
	 * <ul>
	 * <li>
	 * Use this group to provide other data related to this NXdetector group.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcollection for that node.
	 */
	public NXcollection getCollection(String name);

	/**
	 * Set a NXcollection node by name:
	 * <ul>
	 * <li>
	 * Use this group to provide other data related to this NXdetector group.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param collection the value to set
	 */
	public void setCollection(String name, NXcollection collection);

	/**
	 * Get all NXcollection nodes:
	 * <ul>
	 * <li>
	 * Use this group to provide other data related to this NXdetector group.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcollection for that node.
	 */
	public Map<String, NXcollection> getAllCollection();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Use this group to provide other data related to this NXdetector group.</li>
	 * </ul>
	 *
	 * @param collection the child nodes to add
	 */

	public void setAllCollection(Map<String, NXcollection> collection);


	/**
	 * In order to properly sort the order of the images taken in (for
	 * example) a tomography experiment, a sequence number is stored with each
	 * image.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSequence_number();

	/**
	 * In order to properly sort the order of the images taken in (for
	 * example) a tomography experiment, a sequence number is stored with each
	 * image.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @param sequence_numberDataset the sequence_numberDataset
	 */
	public DataNode setSequence_number(IDataset sequence_numberDataset);

	/**
	 * In order to properly sort the order of the images taken in (for
	 * example) a tomography experiment, a sequence number is stored with each
	 * image.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getSequence_numberScalar();

	/**
	 * In order to properly sort the order of the images taken in (for
	 * example) a tomography experiment, a sequence number is stored with each
	 * image.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @param sequence_number the sequence_number
	 */
	public DataNode setSequence_numberScalar(Long sequence_numberValue);

	/**
	 * This is the x position where the direct beam would hit the detector.
	 * This is a length and can be outside of the actual
	 * detector. The length can be in physical units or pixels
	 * as documented by the units attribute.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBeam_center_x();

	/**
	 * This is the x position where the direct beam would hit the detector.
	 * This is a length and can be outside of the actual
	 * detector. The length can be in physical units or pixels
	 * as documented by the units attribute.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param beam_center_xDataset the beam_center_xDataset
	 */
	public DataNode setBeam_center_x(IDataset beam_center_xDataset);

	/**
	 * This is the x position where the direct beam would hit the detector.
	 * This is a length and can be outside of the actual
	 * detector. The length can be in physical units or pixels
	 * as documented by the units attribute.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBeam_center_xScalar();

	/**
	 * This is the x position where the direct beam would hit the detector.
	 * This is a length and can be outside of the actual
	 * detector. The length can be in physical units or pixels
	 * as documented by the units attribute.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param beam_center_x the beam_center_x
	 */
	public DataNode setBeam_center_xScalar(Double beam_center_xValue);

	/**
	 * This is the y position where the direct beam would hit the detector.
	 * This is a length and can be outside of the actual
	 * detector. The length can be in physical units or pixels
	 * as documented by the units attribute.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBeam_center_y();

	/**
	 * This is the y position where the direct beam would hit the detector.
	 * This is a length and can be outside of the actual
	 * detector. The length can be in physical units or pixels
	 * as documented by the units attribute.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param beam_center_yDataset the beam_center_yDataset
	 */
	public DataNode setBeam_center_y(IDataset beam_center_yDataset);

	/**
	 * This is the y position where the direct beam would hit the detector.
	 * This is a length and can be outside of the actual
	 * detector. The length can be in physical units or pixels
	 * as documented by the units attribute.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBeam_center_yScalar();

	/**
	 * This is the y position where the direct beam would hit the detector.
	 * This is a length and can be outside of the actual
	 * detector. The length can be in physical units or pixels
	 * as documented by the units attribute.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param beam_center_y the beam_center_y
	 */
	public DataNode setBeam_center_yScalar(Double beam_center_yValue);

	/**
	 * This is the start number of the first frame of a scan. In protein crystallography measurements one
	 * often scans a couple of frames on a give sample, then does something else,
	 * then returns to the same sample and scans some more frames. Each time with
	 * a new data file. This number helps concatenating such measurements.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFrame_start_number();

	/**
	 * This is the start number of the first frame of a scan. In protein crystallography measurements one
	 * often scans a couple of frames on a give sample, then does something else,
	 * then returns to the same sample and scans some more frames. Each time with
	 * a new data file. This number helps concatenating such measurements.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param frame_start_numberDataset the frame_start_numberDataset
	 */
	public DataNode setFrame_start_number(IDataset frame_start_numberDataset);

	/**
	 * This is the start number of the first frame of a scan. In protein crystallography measurements one
	 * often scans a couple of frames on a give sample, then does something else,
	 * then returns to the same sample and scans some more frames. Each time with
	 * a new data file. This number helps concatenating such measurements.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getFrame_start_numberScalar();

	/**
	 * This is the start number of the first frame of a scan. In protein crystallography measurements one
	 * often scans a couple of frames on a give sample, then does something else,
	 * then returns to the same sample and scans some more frames. Each time with
	 * a new data file. This number helps concatenating such measurements.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param frame_start_number the frame_start_number
	 */
	public DataNode setFrame_start_numberScalar(Long frame_start_numberValue);

	/**
	 * The diameter of a cylindrical detector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDiameter();

	/**
	 * The diameter of a cylindrical detector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param diameterDataset the diameterDataset
	 */
	public DataNode setDiameter(IDataset diameterDataset);

	/**
	 * The diameter of a cylindrical detector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDiameterScalar();

	/**
	 * The diameter of a cylindrical detector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param diameter the diameter
	 */
	public DataNode setDiameterScalar(Double diameterValue);

	/**
	 * The acquisition mode of the detector.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>gated</b> </li>
	 * <li><b>triggered</b> </li>
	 * <li><b>summed</b> </li>
	 * <li><b>event</b> </li>
	 * <li><b>histogrammed</b> </li>
	 * <li><b>decimated</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAcquisition_mode();

	/**
	 * The acquisition mode of the detector.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>gated</b> </li>
	 * <li><b>triggered</b> </li>
	 * <li><b>summed</b> </li>
	 * <li><b>event</b> </li>
	 * <li><b>histogrammed</b> </li>
	 * <li><b>decimated</b> </li></ul></p>
	 * </p>
	 *
	 * @param acquisition_modeDataset the acquisition_modeDataset
	 */
	public DataNode setAcquisition_mode(IDataset acquisition_modeDataset);

	/**
	 * The acquisition mode of the detector.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>gated</b> </li>
	 * <li><b>triggered</b> </li>
	 * <li><b>summed</b> </li>
	 * <li><b>event</b> </li>
	 * <li><b>histogrammed</b> </li>
	 * <li><b>decimated</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getAcquisition_modeScalar();

	/**
	 * The acquisition mode of the detector.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>gated</b> </li>
	 * <li><b>triggered</b> </li>
	 * <li><b>summed</b> </li>
	 * <li><b>event</b> </li>
	 * <li><b>histogrammed</b> </li>
	 * <li><b>decimated</b> </li></ul></p>
	 * </p>
	 *
	 * @param acquisition_mode the acquisition_mode
	 */
	public DataNode setAcquisition_modeScalar(String acquisition_modeValue);

	/**
	 * True when the angular calibration has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAngular_calibration_applied();

	/**
	 * True when the angular calibration has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param angular_calibration_appliedDataset the angular_calibration_appliedDataset
	 */
	public DataNode setAngular_calibration_applied(IDataset angular_calibration_appliedDataset);

	/**
	 * True when the angular calibration has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getAngular_calibration_appliedScalar();

	/**
	 * True when the angular calibration has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param angular_calibration_applied the angular_calibration_applied
	 */
	public DataNode setAngular_calibration_appliedScalar(Boolean angular_calibration_appliedValue);

	/**
	 * Angular calibration data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAngular_calibration();

	/**
	 * Angular calibration data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param angular_calibrationDataset the angular_calibrationDataset
	 */
	public DataNode setAngular_calibration(IDataset angular_calibrationDataset);

	/**
	 * Angular calibration data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAngular_calibrationScalar();

	/**
	 * Angular calibration data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param angular_calibration the angular_calibration
	 */
	public DataNode setAngular_calibrationScalar(Double angular_calibrationValue);

	/**
	 * True when the flat field correction has been applied in the
	 * electronics, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFlatfield_applied();

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
	 * Flat field correction data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFlatfield();

	/**
	 * Flat field correction data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param flatfieldDataset the flatfieldDataset
	 */
	public DataNode setFlatfield(IDataset flatfieldDataset);

	/**
	 * Flat field correction data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getFlatfieldScalar();

	/**
	 * Flat field correction data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param flatfield the flatfield
	 */
	public DataNode setFlatfieldScalar(Double flatfieldValue);

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
	public Dataset getFlatfield_errors();

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
	public Dataset getPixel_mask_applied();

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
	 * The 32-bit pixel mask for the detector. Can be either one mask
	 * for the whole dataset (i.e. an array with indices i, j) or
	 * each frame can have its own mask (in which case it would be
	 * an array with indices np, i, j).
	 * Contains a bit field for each pixel to signal dead,
	 * blind or high or otherwise unwanted or undesirable pixels.
	 * They have the following meaning:
	 * .. can't make a table here, a bullet list will have to do for now
	 * * bit 0: gap (pixel with no sensor)
	 * * bit 1: dead
	 * * bit 2: under responding
	 * * bit 3: over responding
	 * * bit 4: noisy
	 * * bit 5: -undefined-
	 * * bit 6: pixel is part of a cluster of problematic pixels (bit set in addition to others)
	 * * bit 7: -undefined-
	 * * bit 8: user defined mask (e.g. around beamstop)
	 * * bits 9-30: -undefined-
	 * * bit 31: virtual pixel (corner pixel with interpolated value)
	 * Normal data analysis software would
	 * not take pixels into account
	 * when a bit in (mask & 0x0000FFFF) is
	 * set. Tag bit in the upper
	 * two bytes would indicate special pixel
	 * properties that normally
	 * would not be a sole reason to reject the
	 * intensity value (unless
	 * lower bits are set.
	 * If the full bit depths is not required, providing a
	 * mask with fewer bits is permissible.
	 * If needed, additional pixel masks can be specified by
	 * including additional entries named pixel_mask_N, where
	 * N is an integer. For example, a general bad pixel mask
	 * could be specified in pixel_mask that indicates noisy
	 * and dead pixels, and an additional pixel mask from
	 * experiment-specific shadowing could be specified in
	 * pixel_mask_2. The cumulative mask is the bitwise OR
	 * of pixel_mask and any pixel_mask_N entries.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPixel_mask();

	/**
	 * The 32-bit pixel mask for the detector. Can be either one mask
	 * for the whole dataset (i.e. an array with indices i, j) or
	 * each frame can have its own mask (in which case it would be
	 * an array with indices np, i, j).
	 * Contains a bit field for each pixel to signal dead,
	 * blind or high or otherwise unwanted or undesirable pixels.
	 * They have the following meaning:
	 * .. can't make a table here, a bullet list will have to do for now
	 * * bit 0: gap (pixel with no sensor)
	 * * bit 1: dead
	 * * bit 2: under responding
	 * * bit 3: over responding
	 * * bit 4: noisy
	 * * bit 5: -undefined-
	 * * bit 6: pixel is part of a cluster of problematic pixels (bit set in addition to others)
	 * * bit 7: -undefined-
	 * * bit 8: user defined mask (e.g. around beamstop)
	 * * bits 9-30: -undefined-
	 * * bit 31: virtual pixel (corner pixel with interpolated value)
	 * Normal data analysis software would
	 * not take pixels into account
	 * when a bit in (mask & 0x0000FFFF) is
	 * set. Tag bit in the upper
	 * two bytes would indicate special pixel
	 * properties that normally
	 * would not be a sole reason to reject the
	 * intensity value (unless
	 * lower bits are set.
	 * If the full bit depths is not required, providing a
	 * mask with fewer bits is permissible.
	 * If needed, additional pixel masks can be specified by
	 * including additional entries named pixel_mask_N, where
	 * N is an integer. For example, a general bad pixel mask
	 * could be specified in pixel_mask that indicates noisy
	 * and dead pixels, and an additional pixel mask from
	 * experiment-specific shadowing could be specified in
	 * pixel_mask_2. The cumulative mask is the bitwise OR
	 * of pixel_mask and any pixel_mask_N entries.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param pixel_maskDataset the pixel_maskDataset
	 */
	public DataNode setPixel_mask(IDataset pixel_maskDataset);

	/**
	 * The 32-bit pixel mask for the detector. Can be either one mask
	 * for the whole dataset (i.e. an array with indices i, j) or
	 * each frame can have its own mask (in which case it would be
	 * an array with indices np, i, j).
	 * Contains a bit field for each pixel to signal dead,
	 * blind or high or otherwise unwanted or undesirable pixels.
	 * They have the following meaning:
	 * .. can't make a table here, a bullet list will have to do for now
	 * * bit 0: gap (pixel with no sensor)
	 * * bit 1: dead
	 * * bit 2: under responding
	 * * bit 3: over responding
	 * * bit 4: noisy
	 * * bit 5: -undefined-
	 * * bit 6: pixel is part of a cluster of problematic pixels (bit set in addition to others)
	 * * bit 7: -undefined-
	 * * bit 8: user defined mask (e.g. around beamstop)
	 * * bits 9-30: -undefined-
	 * * bit 31: virtual pixel (corner pixel with interpolated value)
	 * Normal data analysis software would
	 * not take pixels into account
	 * when a bit in (mask & 0x0000FFFF) is
	 * set. Tag bit in the upper
	 * two bytes would indicate special pixel
	 * properties that normally
	 * would not be a sole reason to reject the
	 * intensity value (unless
	 * lower bits are set.
	 * If the full bit depths is not required, providing a
	 * mask with fewer bits is permissible.
	 * If needed, additional pixel masks can be specified by
	 * including additional entries named pixel_mask_N, where
	 * N is an integer. For example, a general bad pixel mask
	 * could be specified in pixel_mask that indicates noisy
	 * and dead pixels, and an additional pixel mask from
	 * experiment-specific shadowing could be specified in
	 * pixel_mask_2. The cumulative mask is the bitwise OR
	 * of pixel_mask and any pixel_mask_N entries.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getPixel_maskScalar();

	/**
	 * The 32-bit pixel mask for the detector. Can be either one mask
	 * for the whole dataset (i.e. an array with indices i, j) or
	 * each frame can have its own mask (in which case it would be
	 * an array with indices np, i, j).
	 * Contains a bit field for each pixel to signal dead,
	 * blind or high or otherwise unwanted or undesirable pixels.
	 * They have the following meaning:
	 * .. can't make a table here, a bullet list will have to do for now
	 * * bit 0: gap (pixel with no sensor)
	 * * bit 1: dead
	 * * bit 2: under responding
	 * * bit 3: over responding
	 * * bit 4: noisy
	 * * bit 5: -undefined-
	 * * bit 6: pixel is part of a cluster of problematic pixels (bit set in addition to others)
	 * * bit 7: -undefined-
	 * * bit 8: user defined mask (e.g. around beamstop)
	 * * bits 9-30: -undefined-
	 * * bit 31: virtual pixel (corner pixel with interpolated value)
	 * Normal data analysis software would
	 * not take pixels into account
	 * when a bit in (mask & 0x0000FFFF) is
	 * set. Tag bit in the upper
	 * two bytes would indicate special pixel
	 * properties that normally
	 * would not be a sole reason to reject the
	 * intensity value (unless
	 * lower bits are set.
	 * If the full bit depths is not required, providing a
	 * mask with fewer bits is permissible.
	 * If needed, additional pixel masks can be specified by
	 * including additional entries named pixel_mask_N, where
	 * N is an integer. For example, a general bad pixel mask
	 * could be specified in pixel_mask that indicates noisy
	 * and dead pixels, and an additional pixel mask from
	 * experiment-specific shadowing could be specified in
	 * pixel_mask_2. The cumulative mask is the bitwise OR
	 * of pixel_mask and any pixel_mask_N entries.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i; 2: j;
	 * </p>
	 *
	 * @param pixel_mask the pixel_mask
	 */
	public DataNode setPixel_maskScalar(Long pixel_maskValue);

	/**
	 * This field allow to distinguish different types of exposure to the same detector "data" field.
	 * Some techniques require frequent (re-)calibration inbetween measuremnts and this way of
	 * recording the different measurements preserves the chronological order with is important for
	 * correct processing.
	 * This is used for example in tomography (`:ref:`NXtomo`) sample projections,
	 * dark and flat images, a magic number is recorded per frame.
	 * The key is as follows:
	 * * projection (sample) = 0
	 * * flat field = 1
	 * * dark field = 2
	 * * invalid = 3
	 * * background (no sample, but buffer where applicable) = 4
	 * In cases where the data is of type :ref:`NXlog` this can also be an NXlog.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: np;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getImage_key();

	/**
	 * This field allow to distinguish different types of exposure to the same detector "data" field.
	 * Some techniques require frequent (re-)calibration inbetween measuremnts and this way of
	 * recording the different measurements preserves the chronological order with is important for
	 * correct processing.
	 * This is used for example in tomography (`:ref:`NXtomo`) sample projections,
	 * dark and flat images, a magic number is recorded per frame.
	 * The key is as follows:
	 * * projection (sample) = 0
	 * * flat field = 1
	 * * dark field = 2
	 * * invalid = 3
	 * * background (no sample, but buffer where applicable) = 4
	 * In cases where the data is of type :ref:`NXlog` this can also be an NXlog.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: np;
	 * </p>
	 *
	 * @param image_keyDataset the image_keyDataset
	 */
	public DataNode setImage_key(IDataset image_keyDataset);

	/**
	 * This field allow to distinguish different types of exposure to the same detector "data" field.
	 * Some techniques require frequent (re-)calibration inbetween measuremnts and this way of
	 * recording the different measurements preserves the chronological order with is important for
	 * correct processing.
	 * This is used for example in tomography (`:ref:`NXtomo`) sample projections,
	 * dark and flat images, a magic number is recorded per frame.
	 * The key is as follows:
	 * * projection (sample) = 0
	 * * flat field = 1
	 * * dark field = 2
	 * * invalid = 3
	 * * background (no sample, but buffer where applicable) = 4
	 * In cases where the data is of type :ref:`NXlog` this can also be an NXlog.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: np;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getImage_keyScalar();

	/**
	 * This field allow to distinguish different types of exposure to the same detector "data" field.
	 * Some techniques require frequent (re-)calibration inbetween measuremnts and this way of
	 * recording the different measurements preserves the chronological order with is important for
	 * correct processing.
	 * This is used for example in tomography (`:ref:`NXtomo`) sample projections,
	 * dark and flat images, a magic number is recorded per frame.
	 * The key is as follows:
	 * * projection (sample) = 0
	 * * flat field = 1
	 * * dark field = 2
	 * * invalid = 3
	 * * background (no sample, but buffer where applicable) = 4
	 * In cases where the data is of type :ref:`NXlog` this can also be an NXlog.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: np;
	 * </p>
	 *
	 * @param image_key the image_key
	 */
	public DataNode setImage_keyScalar(Long image_keyValue);

	/**
	 * Counting detectors usually are not able to measure all incoming particles,
	 * especially at higher count-rates. Count-rate correction is applied to
	 * account for these errors.
	 * True when count-rate correction has been applied, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCountrate_correction_applied();

	/**
	 * Counting detectors usually are not able to measure all incoming particles,
	 * especially at higher count-rates. Count-rate correction is applied to
	 * account for these errors.
	 * True when count-rate correction has been applied, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param countrate_correction_appliedDataset the countrate_correction_appliedDataset
	 */
	public DataNode setCountrate_correction_applied(IDataset countrate_correction_appliedDataset);

	/**
	 * Counting detectors usually are not able to measure all incoming particles,
	 * especially at higher count-rates. Count-rate correction is applied to
	 * account for these errors.
	 * True when count-rate correction has been applied, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getCountrate_correction_appliedScalar();

	/**
	 * Counting detectors usually are not able to measure all incoming particles,
	 * especially at higher count-rates. Count-rate correction is applied to
	 * account for these errors.
	 * True when count-rate correction has been applied, false otherwise.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param countrate_correction_applied the countrate_correction_applied
	 */
	public DataNode setCountrate_correction_appliedScalar(Boolean countrate_correction_appliedValue);

	/**
	 * The countrate_correction_lookup_table defines the LUT used for count-rate
	 * correction. It maps a measured count :math:`c` to its corrected value
	 * :math:`countrate\_correction\_lookup\_table[c]`.
	 * :math:`m` denotes the length of the table.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCountrate_correction_lookup_table();

	/**
	 * The countrate_correction_lookup_table defines the LUT used for count-rate
	 * correction. It maps a measured count :math:`c` to its corrected value
	 * :math:`countrate\_correction\_lookup\_table[c]`.
	 * :math:`m` denotes the length of the table.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 *
	 * @param countrate_correction_lookup_tableDataset the countrate_correction_lookup_tableDataset
	 */
	public DataNode setCountrate_correction_lookup_table(IDataset countrate_correction_lookup_tableDataset);

	/**
	 * The countrate_correction_lookup_table defines the LUT used for count-rate
	 * correction. It maps a measured count :math:`c` to its corrected value
	 * :math:`countrate\_correction\_lookup\_table[c]`.
	 * :math:`m` denotes the length of the table.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCountrate_correction_lookup_tableScalar();

	/**
	 * The countrate_correction_lookup_table defines the LUT used for count-rate
	 * correction. It maps a measured count :math:`c` to its corrected value
	 * :math:`countrate\_correction\_lookup\_table[c]`.
	 * :math:`m` denotes the length of the table.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 *
	 * @param countrate_correction_lookup_table the countrate_correction_lookup_table
	 */
	public DataNode setCountrate_correction_lookup_tableScalar(Number countrate_correction_lookup_tableValue);

	/**
	 * True when virtual pixel interpolation has been applied, false otherwise.
	 * When virtual pixel interpolation is applied, values of some pixels may
	 * contain interpolated values. For example, to account for space between
	 * readout chips on a module, physical pixels on edges and corners between
	 * chips may have larger sensor areas and counts may be distributed between
	 * their logical pixels.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVirtual_pixel_interpolation_applied();

	/**
	 * True when virtual pixel interpolation has been applied, false otherwise.
	 * When virtual pixel interpolation is applied, values of some pixels may
	 * contain interpolated values. For example, to account for space between
	 * readout chips on a module, physical pixels on edges and corners between
	 * chips may have larger sensor areas and counts may be distributed between
	 * their logical pixels.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param virtual_pixel_interpolation_appliedDataset the virtual_pixel_interpolation_appliedDataset
	 */
	public DataNode setVirtual_pixel_interpolation_applied(IDataset virtual_pixel_interpolation_appliedDataset);

	/**
	 * True when virtual pixel interpolation has been applied, false otherwise.
	 * When virtual pixel interpolation is applied, values of some pixels may
	 * contain interpolated values. For example, to account for space between
	 * readout chips on a module, physical pixels on edges and corners between
	 * chips may have larger sensor areas and counts may be distributed between
	 * their logical pixels.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getVirtual_pixel_interpolation_appliedScalar();

	/**
	 * True when virtual pixel interpolation has been applied, false otherwise.
	 * When virtual pixel interpolation is applied, values of some pixels may
	 * contain interpolated values. For example, to account for space between
	 * readout chips on a module, physical pixels on edges and corners between
	 * chips may have larger sensor areas and counts may be distributed between
	 * their logical pixels.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param virtual_pixel_interpolation_applied the virtual_pixel_interpolation_applied
	 */
	public DataNode setVirtual_pixel_interpolation_appliedScalar(Boolean virtual_pixel_interpolation_appliedValue);

	/**
	 * How many bits the electronics reads per pixel.
	 * With CCD's and single photon counting detectors,
	 * this must not align with traditional integer sizes.
	 * This can be 4, 8, 12, 14, 16, ...
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBit_depth_readout();

	/**
	 * How many bits the electronics reads per pixel.
	 * With CCD's and single photon counting detectors,
	 * this must not align with traditional integer sizes.
	 * This can be 4, 8, 12, 14, 16, ...
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param bit_depth_readoutDataset the bit_depth_readoutDataset
	 */
	public DataNode setBit_depth_readout(IDataset bit_depth_readoutDataset);

	/**
	 * How many bits the electronics reads per pixel.
	 * With CCD's and single photon counting detectors,
	 * this must not align with traditional integer sizes.
	 * This can be 4, 8, 12, 14, 16, ...
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getBit_depth_readoutScalar();

	/**
	 * How many bits the electronics reads per pixel.
	 * With CCD's and single photon counting detectors,
	 * this must not align with traditional integer sizes.
	 * This can be 4, 8, 12, 14, 16, ...
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param bit_depth_readout the bit_depth_readout
	 */
	public DataNode setBit_depth_readoutScalar(Long bit_depth_readoutValue);

	/**
	 * Time it takes to read the detector (typically milliseconds).
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDetector_readout_time();

	/**
	 * Time it takes to read the detector (typically milliseconds).
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param detector_readout_timeDataset the detector_readout_timeDataset
	 */
	public DataNode setDetector_readout_time(IDataset detector_readout_timeDataset);

	/**
	 * Time it takes to read the detector (typically milliseconds).
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDetector_readout_timeScalar();

	/**
	 * Time it takes to read the detector (typically milliseconds).
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param detector_readout_time the detector_readout_time
	 */
	public DataNode setDetector_readout_timeScalar(Double detector_readout_timeValue);

	/**
	 * Time it takes to start exposure after a trigger signal has been received.
	 * This is the reaction time of the detector firmware after receiving the trigger signal
	 * to when the detector starts to acquire the exposure, including any user set delay..
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTrigger_delay_time();

	/**
	 * Time it takes to start exposure after a trigger signal has been received.
	 * This is the reaction time of the detector firmware after receiving the trigger signal
	 * to when the detector starts to acquire the exposure, including any user set delay..
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param trigger_delay_timeDataset the trigger_delay_timeDataset
	 */
	public DataNode setTrigger_delay_time(IDataset trigger_delay_timeDataset);

	/**
	 * Time it takes to start exposure after a trigger signal has been received.
	 * This is the reaction time of the detector firmware after receiving the trigger signal
	 * to when the detector starts to acquire the exposure, including any user set delay..
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTrigger_delay_timeScalar();

	/**
	 * Time it takes to start exposure after a trigger signal has been received.
	 * This is the reaction time of the detector firmware after receiving the trigger signal
	 * to when the detector starts to acquire the exposure, including any user set delay..
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param trigger_delay_time the trigger_delay_time
	 */
	public DataNode setTrigger_delay_timeScalar(Double trigger_delay_timeValue);

	/**
	 * User-specified trigger delay.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTrigger_delay_time_set();

	/**
	 * User-specified trigger delay.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param trigger_delay_time_setDataset the trigger_delay_time_setDataset
	 */
	public DataNode setTrigger_delay_time_set(IDataset trigger_delay_time_setDataset);

	/**
	 * User-specified trigger delay.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTrigger_delay_time_setScalar();

	/**
	 * User-specified trigger delay.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param trigger_delay_time_set the trigger_delay_time_set
	 */
	public DataNode setTrigger_delay_time_setScalar(Double trigger_delay_time_setValue);

	/**
	 * Time it takes to start exposure after a trigger signal has been received.
	 * This is the reaction time of the detector hardware after receiving the
	 * trigger signal to when the detector starts to acquire the exposure.
	 * It forms the lower boundary of the trigger_delay_time when the user
	 * does not request an additional delay.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTrigger_internal_delay_time();

	/**
	 * Time it takes to start exposure after a trigger signal has been received.
	 * This is the reaction time of the detector hardware after receiving the
	 * trigger signal to when the detector starts to acquire the exposure.
	 * It forms the lower boundary of the trigger_delay_time when the user
	 * does not request an additional delay.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param trigger_internal_delay_timeDataset the trigger_internal_delay_timeDataset
	 */
	public DataNode setTrigger_internal_delay_time(IDataset trigger_internal_delay_timeDataset);

	/**
	 * Time it takes to start exposure after a trigger signal has been received.
	 * This is the reaction time of the detector hardware after receiving the
	 * trigger signal to when the detector starts to acquire the exposure.
	 * It forms the lower boundary of the trigger_delay_time when the user
	 * does not request an additional delay.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTrigger_internal_delay_timeScalar();

	/**
	 * Time it takes to start exposure after a trigger signal has been received.
	 * This is the reaction time of the detector hardware after receiving the
	 * trigger signal to when the detector starts to acquire the exposure.
	 * It forms the lower boundary of the trigger_delay_time when the user
	 * does not request an additional delay.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param trigger_internal_delay_time the trigger_internal_delay_time
	 */
	public DataNode setTrigger_internal_delay_timeScalar(Double trigger_internal_delay_timeValue);

	/**
	 * Time during which no new trigger signal can be accepted.
	 * Typically this is the
	 * trigger_delay_time + exposure_time + readout_time.
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTrigger_dead_time();

	/**
	 * Time during which no new trigger signal can be accepted.
	 * Typically this is the
	 * trigger_delay_time + exposure_time + readout_time.
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param trigger_dead_timeDataset the trigger_dead_timeDataset
	 */
	public DataNode setTrigger_dead_time(IDataset trigger_dead_timeDataset);

	/**
	 * Time during which no new trigger signal can be accepted.
	 * Typically this is the
	 * trigger_delay_time + exposure_time + readout_time.
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTrigger_dead_timeScalar();

	/**
	 * Time during which no new trigger signal can be accepted.
	 * Typically this is the
	 * trigger_delay_time + exposure_time + readout_time.
	 * This is important to know for time resolved experiments.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param trigger_dead_time the trigger_dead_time
	 */
	public DataNode setTrigger_dead_timeScalar(Double trigger_dead_timeValue);

	/**
	 * This is time for each frame. This is exposure_time + readout time.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFrame_time();

	/**
	 * This is time for each frame. This is exposure_time + readout time.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @param frame_timeDataset the frame_timeDataset
	 */
	public DataNode setFrame_time(IDataset frame_timeDataset);

	/**
	 * This is time for each frame. This is exposure_time + readout time.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getFrame_timeScalar();

	/**
	 * This is time for each frame. This is exposure_time + readout time.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 *
	 * @param frame_time the frame_time
	 */
	public DataNode setFrame_timeScalar(Double frame_timeValue);

	/**
	 * The gain setting of the detector. This is a detector-specific value
	 * meant to document the gain setting of the detector during data
	 * collection, for detectors with multiple available gain settings.
	 * Examples of gain settings include:
	 * * ``standard``
	 * * ``fast``
	 * * ``auto``
	 * * ``high``
	 * * ``medium``
	 * * ``low``
	 * * ``mixed high to medium``
	 * * ``mixed medium to low``
	 * Developers are encouraged to use one of these terms, or to submit
	 * additional terms to add to the list.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getGain_setting();

	/**
	 * The gain setting of the detector. This is a detector-specific value
	 * meant to document the gain setting of the detector during data
	 * collection, for detectors with multiple available gain settings.
	 * Examples of gain settings include:
	 * * ``standard``
	 * * ``fast``
	 * * ``auto``
	 * * ``high``
	 * * ``medium``
	 * * ``low``
	 * * ``mixed high to medium``
	 * * ``mixed medium to low``
	 * Developers are encouraged to use one of these terms, or to submit
	 * additional terms to add to the list.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param gain_settingDataset the gain_settingDataset
	 */
	public DataNode setGain_setting(IDataset gain_settingDataset);

	/**
	 * The gain setting of the detector. This is a detector-specific value
	 * meant to document the gain setting of the detector during data
	 * collection, for detectors with multiple available gain settings.
	 * Examples of gain settings include:
	 * * ``standard``
	 * * ``fast``
	 * * ``auto``
	 * * ``high``
	 * * ``medium``
	 * * ``low``
	 * * ``mixed high to medium``
	 * * ``mixed medium to low``
	 * Developers are encouraged to use one of these terms, or to submit
	 * additional terms to add to the list.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getGain_settingScalar();

	/**
	 * The gain setting of the detector. This is a detector-specific value
	 * meant to document the gain setting of the detector during data
	 * collection, for detectors with multiple available gain settings.
	 * Examples of gain settings include:
	 * * ``standard``
	 * * ``fast``
	 * * ``auto``
	 * * ``high``
	 * * ``medium``
	 * * ``low``
	 * * ``mixed high to medium``
	 * * ``mixed medium to low``
	 * Developers are encouraged to use one of these terms, or to submit
	 * additional terms to add to the list.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param gain_setting the gain_setting
	 */
	public DataNode setGain_settingScalar(String gain_settingValue);

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
	public Dataset getSaturation_value();

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
	public Dataset getUnderload_value();

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

	/**
	 * CCD images are sometimes constructed by summing
	 * together multiple short exposures in the
	 * electronics. This reduces background etc.
	 * This is the number of short exposures used to sum
	 * images for an image.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_cycles();

	/**
	 * CCD images are sometimes constructed by summing
	 * together multiple short exposures in the
	 * electronics. This reduces background etc.
	 * This is the number of short exposures used to sum
	 * images for an image.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param number_of_cyclesDataset the number_of_cyclesDataset
	 */
	public DataNode setNumber_of_cycles(IDataset number_of_cyclesDataset);

	/**
	 * CCD images are sometimes constructed by summing
	 * together multiple short exposures in the
	 * electronics. This reduces background etc.
	 * This is the number of short exposures used to sum
	 * images for an image.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_cyclesScalar();

	/**
	 * CCD images are sometimes constructed by summing
	 * together multiple short exposures in the
	 * electronics. This reduces background etc.
	 * This is the number of short exposures used to sum
	 * images for an image.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param number_of_cycles the number_of_cycles
	 */
	public DataNode setNumber_of_cyclesScalar(Long number_of_cyclesValue);

	/**
	 * At times, radiation is not directly sensed by the detector.
	 * Rather, the detector might sense the output from some
	 * converter like a scintillator.
	 * This is the name of this converter material.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSensor_material();

	/**
	 * At times, radiation is not directly sensed by the detector.
	 * Rather, the detector might sense the output from some
	 * converter like a scintillator.
	 * This is the name of this converter material.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param sensor_materialDataset the sensor_materialDataset
	 */
	public DataNode setSensor_material(IDataset sensor_materialDataset);

	/**
	 * At times, radiation is not directly sensed by the detector.
	 * Rather, the detector might sense the output from some
	 * converter like a scintillator.
	 * This is the name of this converter material.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSensor_materialScalar();

	/**
	 * At times, radiation is not directly sensed by the detector.
	 * Rather, the detector might sense the output from some
	 * converter like a scintillator.
	 * This is the name of this converter material.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param sensor_material the sensor_material
	 */
	public DataNode setSensor_materialScalar(String sensor_materialValue);

	/**
	 * At times, radiation is not directly sensed by the detector.
	 * Rather, the detector might sense the output from some
	 * converter like a scintillator.
	 * This is the thickness of this converter material.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSensor_thickness();

	/**
	 * At times, radiation is not directly sensed by the detector.
	 * Rather, the detector might sense the output from some
	 * converter like a scintillator.
	 * This is the thickness of this converter material.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param sensor_thicknessDataset the sensor_thicknessDataset
	 */
	public DataNode setSensor_thickness(IDataset sensor_thicknessDataset);

	/**
	 * At times, radiation is not directly sensed by the detector.
	 * Rather, the detector might sense the output from some
	 * converter like a scintillator.
	 * This is the thickness of this converter material.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSensor_thicknessScalar();

	/**
	 * At times, radiation is not directly sensed by the detector.
	 * Rather, the detector might sense the output from some
	 * converter like a scintillator.
	 * This is the thickness of this converter material.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param sensor_thickness the sensor_thickness
	 */
	public DataNode setSensor_thicknessScalar(Double sensor_thicknessValue);

	/**
	 * Single photon counter detectors can be adjusted
	 * for a certain energy range in which they
	 * work optimally. This is the energy setting for this.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getThreshold_energy();

	/**
	 * Single photon counter detectors can be adjusted
	 * for a certain energy range in which they
	 * work optimally. This is the energy setting for this.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param threshold_energyDataset the threshold_energyDataset
	 */
	public DataNode setThreshold_energy(IDataset threshold_energyDataset);

	/**
	 * Single photon counter detectors can be adjusted
	 * for a certain energy range in which they
	 * work optimally. This is the energy setting for this.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getThreshold_energyScalar();

	/**
	 * Single photon counter detectors can be adjusted
	 * for a certain energy range in which they
	 * work optimally. This is the energy setting for this.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param threshold_energy the threshold_energy
	 */
	public DataNode setThreshold_energyScalar(Double threshold_energyValue);

	/**
	 * For use in special cases where the data in NXdetector
	 * is represented in several parts, each with a separate geometry.
	 *
	 * @return  the value.
	 */
	public NXdetector_module getDetector_module();

	/**
	 * For use in special cases where the data in NXdetector
	 * is represented in several parts, each with a separate geometry.
	 *
	 * @param detector_moduleGroup the detector_moduleGroup
	 */
	public void setDetector_module(NXdetector_module detector_moduleGroup);

	/**
	 * Get a NXdetector_module node by name:
	 * <ul>
	 * <li>
	 * For use in special cases where the data in NXdetector
	 * is represented in several parts, each with a separate geometry.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdetector_module for that node.
	 */
	public NXdetector_module getDetector_module(String name);

	/**
	 * Set a NXdetector_module node by name:
	 * <ul>
	 * <li>
	 * For use in special cases where the data in NXdetector
	 * is represented in several parts, each with a separate geometry.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param detector_module the value to set
	 */
	public void setDetector_module(String name, NXdetector_module detector_module);

	/**
	 * Get all NXdetector_module nodes:
	 * <ul>
	 * <li>
	 * For use in special cases where the data in NXdetector
	 * is represented in several parts, each with a separate geometry.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdetector_module for that node.
	 */
	public Map<String, NXdetector_module> getAllDetector_module();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * For use in special cases where the data in NXdetector
	 * is represented in several parts, each with a separate geometry.</li>
	 * </ul>
	 *
	 * @param detector_module the child nodes to add
	 */

	public void setAllDetector_module(Map<String, NXdetector_module> detector_module);

	// Unprocessed choice:pixel_shape
	// Unprocessed choice:detector_shape

	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 *
	 * @return  the value.
	 */
	public String getAttributeDefault();

	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 *
	 * @param defaultValue the defaultValue
	 */
	public void setAttributeDefault(String defaultValue);

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the detector is the center of the first pixel.
	 * In complex geometries the NXoff_geometry groups can be used to provide an unambiguous reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the detector is the center of the first pixel.
	 * In complex geometries the NXoff_geometry groups can be used to provide an unambiguous reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the detector is the center of the first pixel.
	 * In complex geometries the NXoff_geometry groups can be used to provide an unambiguous reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the detector is the center of the first pixel.
	 * In complex geometries the NXoff_geometry groups can be used to provide an unambiguous reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


}
