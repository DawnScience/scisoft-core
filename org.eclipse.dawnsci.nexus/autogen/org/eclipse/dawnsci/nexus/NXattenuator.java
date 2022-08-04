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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * A device that reduces the intensity of a beam by attenuation.
 * If uncertain whether to use :ref:`NXfilter` (band-pass filter)
 * or :ref:`NXattenuator` (reduces beam intensity), then choose
 * :ref:`NXattenuator`.
 * 
 */
public interface NXattenuator extends NXobject {

	public static final String NX_DISTANCE = "distance";
	public static final String NX_TYPE = "type";
	public static final String NX_THICKNESS = "thickness";
	public static final String NX_SCATTERING_CROSS_SECTION = "scattering_cross_section";
	public static final String NX_ABSORPTION_CROSS_SECTION = "absorption_cross_section";
	public static final String NX_ATTENUATOR_TRANSMISSION = "attenuator_transmission";
	public static final String NX_STATUS = "status";
	public static final String NX_STATUS_ATTRIBUTE_TIME = "time";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Distance from sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDistance();
	
	/**
	 * Distance from sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param distanceDataset the distanceDataset
	 */
	public DataNode setDistance(IDataset distanceDataset);

	/**
	 * Distance from sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDistanceScalar();

	/**
	 * Distance from sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param distance the distance
	 */
	public DataNode setDistanceScalar(Double distanceValue);

	/**
	 * Type or composition of attenuator, e.g. polythene
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
	/**
	 * Type or composition of attenuator, e.g. polythene
	 * 
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type or composition of attenuator, e.g. polythene
	 * 
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type or composition of attenuator, e.g. polythene
	 * 
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Thickness of attenuator along beam direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getThickness();
	
	/**
	 * Thickness of attenuator along beam direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param thicknessDataset the thicknessDataset
	 */
	public DataNode setThickness(IDataset thicknessDataset);

	/**
	 * Thickness of attenuator along beam direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getThicknessScalar();

	/**
	 * Thickness of attenuator along beam direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param thickness the thickness
	 */
	public DataNode setThicknessScalar(Double thicknessValue);

	/**
	 * Scattering cross section (coherent+incoherent)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CROSS_SECTION
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getScattering_cross_section();
	
	/**
	 * Scattering cross section (coherent+incoherent)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CROSS_SECTION
	 * </p>
	 * 
	 * @param scattering_cross_sectionDataset the scattering_cross_sectionDataset
	 */
	public DataNode setScattering_cross_section(IDataset scattering_cross_sectionDataset);

	/**
	 * Scattering cross section (coherent+incoherent)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CROSS_SECTION
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getScattering_cross_sectionScalar();

	/**
	 * Scattering cross section (coherent+incoherent)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CROSS_SECTION
	 * </p>
	 * 
	 * @param scattering_cross_section the scattering_cross_section
	 */
	public DataNode setScattering_cross_sectionScalar(Double scattering_cross_sectionValue);

	/**
	 * Absorption cross section
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CROSS_SECTION
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getAbsorption_cross_section();
	
	/**
	 * Absorption cross section
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CROSS_SECTION
	 * </p>
	 * 
	 * @param absorption_cross_sectionDataset the absorption_cross_sectionDataset
	 */
	public DataNode setAbsorption_cross_section(IDataset absorption_cross_sectionDataset);

	/**
	 * Absorption cross section
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CROSS_SECTION
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getAbsorption_cross_sectionScalar();

	/**
	 * Absorption cross section
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CROSS_SECTION
	 * </p>
	 * 
	 * @param absorption_cross_section the absorption_cross_section
	 */
	public DataNode setAbsorption_cross_sectionScalar(Double absorption_cross_sectionValue);

	/**
	 * The nominal amount of the beam that gets through
	 * (transmitted intensity)/(incident intensity)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getAttenuator_transmission();
	
	/**
	 * The nominal amount of the beam that gets through
	 * (transmitted intensity)/(incident intensity)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 * 
	 * @param attenuator_transmissionDataset the attenuator_transmissionDataset
	 */
	public DataNode setAttenuator_transmission(IDataset attenuator_transmissionDataset);

	/**
	 * The nominal amount of the beam that gets through
	 * (transmitted intensity)/(incident intensity)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getAttenuator_transmissionScalar();

	/**
	 * The nominal amount of the beam that gets through
	 * (transmitted intensity)/(incident intensity)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 * 
	 * @param attenuator_transmission the attenuator_transmission
	 */
	public DataNode setAttenuator_transmissionScalar(Double attenuator_transmissionValue);

	/**
	 * In or out or moving of the beam
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>in</b> </li>
	 * <li><b>out</b> </li>
	 * <li><b>moving</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getStatus();
	
	/**
	 * In or out or moving of the beam
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>in</b> </li>
	 * <li><b>out</b> </li>
	 * <li><b>moving</b> </li></ul></p>
	 * </p>
	 * 
	 * @param statusDataset the statusDataset
	 */
	public DataNode setStatus(IDataset statusDataset);

	/**
	 * In or out or moving of the beam
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>in</b> </li>
	 * <li><b>out</b> </li>
	 * <li><b>moving</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getStatusScalar();

	/**
	 * In or out or moving of the beam
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>in</b> </li>
	 * <li><b>out</b> </li>
	 * <li><b>moving</b> </li></ul></p>
	 * </p>
	 * 
	 * @param status the status
	 */
	public DataNode setStatusScalar(String statusValue);

	/**
	 * time stamp for this observation
	 * 
	 * @return  the value.
	 */
	public Date getStatusAttributeTime();
	
	/**
	 * time stamp for this observation
	 * 
	 * @param timeValue the timeValue
	 */
	public void setStatusAttributeTime(Date timeValue);

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
