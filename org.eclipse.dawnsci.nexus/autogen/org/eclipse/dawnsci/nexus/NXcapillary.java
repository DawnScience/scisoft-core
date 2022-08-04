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
 * A capillary lens to focus the X-ray beam.
 * Based on information provided by Gerd Wellenreuther (DESY).
 * 
 */
public interface NXcapillary extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_MANUFACTURER = "manufacturer";
	public static final String NX_MAXIMUM_INCIDENT_ANGLE = "maximum_incident_angle";
	public static final String NX_ACCEPTING_APERTURE = "accepting_aperture";
	public static final String NX_WORKING_DISTANCE = "working_distance";
	public static final String NX_FOCAL_SIZE = "focal_size";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Type of the capillary
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single_bounce</b> </li>
	 * <li><b>polycapillary</b> </li>
	 * <li><b>conical_capillary</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
	/**
	 * Type of the capillary
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single_bounce</b> </li>
	 * <li><b>polycapillary</b> </li>
	 * <li><b>conical_capillary</b> </li></ul></p>
	 * </p>
	 * 
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type of the capillary
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single_bounce</b> </li>
	 * <li><b>polycapillary</b> </li>
	 * <li><b>conical_capillary</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type of the capillary
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single_bounce</b> </li>
	 * <li><b>polycapillary</b> </li>
	 * <li><b>conical_capillary</b> </li></ul></p>
	 * </p>
	 * 
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * The manufacturer of the capillary. This is actually important as
	 * it may have an impact on performance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getManufacturer();
	
	/**
	 * The manufacturer of the capillary. This is actually important as
	 * it may have an impact on performance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param manufacturerDataset the manufacturerDataset
	 */
	public DataNode setManufacturer(IDataset manufacturerDataset);

	/**
	 * The manufacturer of the capillary. This is actually important as
	 * it may have an impact on performance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getManufacturerScalar();

	/**
	 * The manufacturer of the capillary. This is actually important as
	 * it may have an impact on performance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param manufacturer the manufacturer
	 */
	public DataNode setManufacturerScalar(String manufacturerValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getMaximum_incident_angle();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param maximum_incident_angleDataset the maximum_incident_angleDataset
	 */
	public DataNode setMaximum_incident_angle(IDataset maximum_incident_angleDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getMaximum_incident_angleScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param maximum_incident_angle the maximum_incident_angle
	 */
	public DataNode setMaximum_incident_angleScalar(Double maximum_incident_angleValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getAccepting_aperture();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param accepting_apertureDataset the accepting_apertureDataset
	 */
	public DataNode setAccepting_aperture(IDataset accepting_apertureDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getAccepting_apertureScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param accepting_aperture the accepting_aperture
	 */
	public DataNode setAccepting_apertureScalar(Double accepting_apertureValue);

	/**
	 * The gain of the capillary as a function of energy
	 * 
	 * @return  the value.
	 */
	public NXdata getGain();
	
	/**
	 * The gain of the capillary as a function of energy
	 * 
	 * @param gainGroup the gainGroup
	 */
	public void setGain(NXdata gainGroup);

	/**
	 * The transmission of the capillary as a function of energy
	 * 
	 * @return  the value.
	 */
	public NXdata getTransmission();
	
	/**
	 * The transmission of the capillary as a function of energy
	 * 
	 * @param transmissionGroup the transmissionGroup
	 */
	public void setTransmission(NXdata transmissionGroup);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getWorking_distance();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param working_distanceDataset the working_distanceDataset
	 */
	public DataNode setWorking_distance(IDataset working_distanceDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getWorking_distanceScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param working_distance the working_distance
	 */
	public DataNode setWorking_distanceScalar(Double working_distanceValue);

	/**
	 * The focal size in FWHM
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFocal_size();
	
	/**
	 * The focal size in FWHM
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @param focal_sizeDataset the focal_sizeDataset
	 */
	public DataNode setFocal_size(IDataset focal_sizeDataset);

	/**
	 * The focal size in FWHM
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFocal_sizeScalar();

	/**
	 * The focal size in FWHM
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @param focal_size the focal_size
	 */
	public DataNode setFocal_sizeScalar(Double focal_sizeValue);

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

}
