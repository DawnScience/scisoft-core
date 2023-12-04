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

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Container for reporting a set of secondary electron images.
 * Secondary electron images are one of the most important signal especially
 * for scanning electron microscopy in materials science and engineering, for
 * analyses of surface topography, getting an overview of the analysis region,
 * or fractography.
 * <p><b>Symbols:</b> <ul>
 * <li><b>n_images</b> 
 * Number of images.</li>
 * <li><b>n_y</b> 
 * Number of pixels along the slow scan direction (often y).</li>
 * <li><b>n_x</b> 
 * Number of pixels along the fast scan direction (often x).</li></ul></p>
 * 
 */
public interface NXimage_set_em_se extends NXobject {

	public static final String NX_ROI = "roi";
	public static final String NX_DWELL_TIME = "dwell_time";
	public static final String NX_NUMBER_OF_FRAMES_AVERAGED = "number_of_frames_averaged";
	public static final String NX_BIAS_VOLTAGE = "bias_voltage";
	/**
	 * Collected secondary electron images (eventually as an image stack).
	 * 
	 * @return  the value.
	 */
	public NXdata getData();
	
	/**
	 * Collected secondary electron images (eventually as an image stack).
	 * 
	 * @param dataGroup the dataGroup
	 */
	public void setData(NXdata dataGroup);

	/**
	 * Get a NXdata node by name:
	 * <ul>
	 * <li>
	 * Collected secondary electron images (eventually as an image stack).</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdata for that node.
	 */
	public NXdata getData(String name);
	
	/**
	 * Set a NXdata node by name:
	 * <ul>
	 * <li>
	 * Collected secondary electron images (eventually as an image stack).</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param data the value to set
	 */
	public void setData(String name, NXdata data);
	
	/**
	 * Get all NXdata nodes:
	 * <ul>
	 * <li>
	 * Collected secondary electron images (eventually as an image stack).</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXdata for that node.
	 */
	public Map<String, NXdata> getAllData();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Collected secondary electron images (eventually as an image stack).</li>
	 * </ul>
	 * 
	 * @param data the child nodes to add 
	 */
	
	public void setAllData(Map<String, NXdata> data);
	

	/**
	 * Physical dimensions of the region-of-interest on
	 * the specimen surface which the image covers.
	 * The first and second number are the coordinates for the
	 * minimum edge, the third and fourth number are the coordinates
	 * for the maximum edge of the rectangular ROI.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_images; 2: 4;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getRoi();
	
	/**
	 * Physical dimensions of the region-of-interest on
	 * the specimen surface which the image covers.
	 * The first and second number are the coordinates for the
	 * minimum edge, the third and fourth number are the coordinates
	 * for the maximum edge of the rectangular ROI.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_images; 2: 4;
	 * </p>
	 * 
	 * @param roiDataset the roiDataset
	 */
	public DataNode setRoi(IDataset roiDataset);

	/**
	 * Physical dimensions of the region-of-interest on
	 * the specimen surface which the image covers.
	 * The first and second number are the coordinates for the
	 * minimum edge, the third and fourth number are the coordinates
	 * for the maximum edge of the rectangular ROI.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_images; 2: 4;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getRoiScalar();

	/**
	 * Physical dimensions of the region-of-interest on
	 * the specimen surface which the image covers.
	 * The first and second number are the coordinates for the
	 * minimum edge, the third and fourth number are the coordinates
	 * for the maximum edge of the rectangular ROI.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_images; 2: 4;
	 * </p>
	 * 
	 * @param roi the roi
	 */
	public DataNode setRoiScalar(Number roiValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDwell_time();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param dwell_timeDataset the dwell_timeDataset
	 */
	public DataNode setDwell_time(IDataset dwell_timeDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDwell_timeScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param dwell_time the dwell_time
	 */
	public DataNode setDwell_timeScalar(Double dwell_timeValue);

	/**
	 * How many frames were averaged to obtain the image.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_images;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getNumber_of_frames_averaged();
	
	/**
	 * How many frames were averaged to obtain the image.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_images;
	 * </p>
	 * 
	 * @param number_of_frames_averagedDataset the number_of_frames_averagedDataset
	 */
	public DataNode setNumber_of_frames_averaged(IDataset number_of_frames_averagedDataset);

	/**
	 * How many frames were averaged to obtain the image.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_images;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getNumber_of_frames_averagedScalar();

	/**
	 * How many frames were averaged to obtain the image.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_images;
	 * </p>
	 * 
	 * @param number_of_frames_averaged the number_of_frames_averaged
	 */
	public DataNode setNumber_of_frames_averagedScalar(Long number_of_frames_averagedValue);

	/**
	 * Bias voltage applied to the e.g. Faraday cage in front of the
	 * SE detector to attract or repell secondary electrons below
	 * a specific kinetic energy.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getBias_voltage();
	
	/**
	 * Bias voltage applied to the e.g. Faraday cage in front of the
	 * SE detector to attract or repell secondary electrons below
	 * a specific kinetic energy.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @param bias_voltageDataset the bias_voltageDataset
	 */
	public DataNode setBias_voltage(IDataset bias_voltageDataset);

	/**
	 * Bias voltage applied to the e.g. Faraday cage in front of the
	 * SE detector to attract or repell secondary electrons below
	 * a specific kinetic energy.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getBias_voltageScalar();

	/**
	 * Bias voltage applied to the e.g. Faraday cage in front of the
	 * SE detector to attract or repell secondary electrons below
	 * a specific kinetic energy.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @param bias_voltage the bias_voltage
	 */
	public DataNode setBias_voltageScalar(Double bias_voltageValue);

	/**
	 * Container to store relevant settings affecting beam path,
	 * magnification, and working_distance
	 * 
	 * @return  the value.
	 */
	public NXoptical_system_em getOptical_system_em();
	
	/**
	 * Container to store relevant settings affecting beam path,
	 * magnification, and working_distance
	 * 
	 * @param optical_system_emGroup the optical_system_emGroup
	 */
	public void setOptical_system_em(NXoptical_system_em optical_system_emGroup);

	/**
	 * Get a NXoptical_system_em node by name:
	 * <ul>
	 * <li>
	 * Container to store relevant settings affecting beam path,
	 * magnification, and working_distance</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXoptical_system_em for that node.
	 */
	public NXoptical_system_em getOptical_system_em(String name);
	
	/**
	 * Set a NXoptical_system_em node by name:
	 * <ul>
	 * <li>
	 * Container to store relevant settings affecting beam path,
	 * magnification, and working_distance</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param optical_system_em the value to set
	 */
	public void setOptical_system_em(String name, NXoptical_system_em optical_system_em);
	
	/**
	 * Get all NXoptical_system_em nodes:
	 * <ul>
	 * <li>
	 * Container to store relevant settings affecting beam path,
	 * magnification, and working_distance</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXoptical_system_em for that node.
	 */
	public Map<String, NXoptical_system_em> getAllOptical_system_em();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Container to store relevant settings affecting beam path,
	 * magnification, and working_distance</li>
	 * </ul>
	 * 
	 * @param optical_system_em the child nodes to add 
	 */
	
	public void setAllOptical_system_em(Map<String, NXoptical_system_em> optical_system_em);
	

	/**
	 * Scan rotation is an option offer by most control software.
	 * 
	 * @return  the value.
	 */
	public NXprocess getScan_rotation();
	
	/**
	 * Scan rotation is an option offer by most control software.
	 * 
	 * @param scan_rotationGroup the scan_rotationGroup
	 */
	public void setScan_rotation(NXprocess scan_rotationGroup);

	/**
	 * Tilt correction is an option offered by most control software of SEMs
	 * to apply an on-the-fly processing of the image to correct for
	 * the excursion of objects when characterized with SE images
	 * on samples whose surface normal is tilted about an axis perpendicular
	 * to the optical axis.
	 * 
	 * @return  the value.
	 */
	public NXprocess getTilt_correction();
	
	/**
	 * Tilt correction is an option offered by most control software of SEMs
	 * to apply an on-the-fly processing of the image to correct for
	 * the excursion of objects when characterized with SE images
	 * on samples whose surface normal is tilted about an axis perpendicular
	 * to the optical axis.
	 * 
	 * @param tilt_correctionGroup the tilt_correctionGroup
	 */
	public void setTilt_correction(NXprocess tilt_correctionGroup);

	/**
	 * Dynamic focus is an option offered by most control software of SEMs
	 * to keep the image also focused when probing locations on the specimens
	 * beyond those for which the lens system was calibrated.
	 * 
	 * @return  the value.
	 */
	public NXprocess getDynamic_focus();
	
	/**
	 * Dynamic focus is an option offered by most control software of SEMs
	 * to keep the image also focused when probing locations on the specimens
	 * beyond those for which the lens system was calibrated.
	 * 
	 * @param dynamic_focusGroup the dynamic_focusGroup
	 */
	public void setDynamic_focus(NXprocess dynamic_focusGroup);

}
