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
 * A neutron velocity selector
 * 
 */
public interface NXvelocity_selector extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_ROTATION_SPEED = "rotation_speed";
	public static final String NX_RADIUS = "radius";
	public static final String NX_SPWIDTH = "spwidth";
	public static final String NX_LENGTH = "length";
	public static final String NX_NUM = "num";
	public static final String NX_TWIST = "twist";
	public static final String NX_TABLE = "table";
	public static final String NX_HEIGHT = "height";
	public static final String NX_WIDTH = "width";
	public static final String NX_WAVELENGTH = "wavelength";
	public static final String NX_WAVELENGTH_SPREAD = "wavelength_spread";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * velocity selector type
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
	/**
	 * velocity selector type
	 * 
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * velocity selector type
	 * 
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * velocity selector type
	 * 
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * velocity selector rotation speed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getRotation_speed();
	
	/**
	 * velocity selector rotation speed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @param rotation_speedDataset the rotation_speedDataset
	 */
	public DataNode setRotation_speed(IDataset rotation_speedDataset);

	/**
	 * velocity selector rotation speed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getRotation_speedScalar();

	/**
	 * velocity selector rotation speed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @param rotation_speed the rotation_speed
	 */
	public DataNode setRotation_speedScalar(Double rotation_speedValue);

	/**
	 * radius at beam centre
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getRadius();
	
	/**
	 * radius at beam centre
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param radiusDataset the radiusDataset
	 */
	public DataNode setRadius(IDataset radiusDataset);

	/**
	 * radius at beam centre
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getRadiusScalar();

	/**
	 * radius at beam centre
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param radius the radius
	 */
	public DataNode setRadiusScalar(Double radiusValue);

	/**
	 * spoke width at beam centre
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSpwidth();
	
	/**
	 * spoke width at beam centre
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param spwidthDataset the spwidthDataset
	 */
	public DataNode setSpwidth(IDataset spwidthDataset);

	/**
	 * spoke width at beam centre
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSpwidthScalar();

	/**
	 * spoke width at beam centre
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param spwidth the spwidth
	 */
	public DataNode setSpwidthScalar(Double spwidthValue);

	/**
	 * rotor length
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getLength();
	
	/**
	 * rotor length
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param lengthDataset the lengthDataset
	 */
	public DataNode setLength(IDataset lengthDataset);

	/**
	 * rotor length
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getLengthScalar();

	/**
	 * rotor length
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param length the length
	 */
	public DataNode setLengthScalar(Double lengthValue);

	/**
	 * number of spokes/lamella
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getNum();
	
	/**
	 * number of spokes/lamella
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @param numDataset the numDataset
	 */
	public DataNode setNum(IDataset numDataset);

	/**
	 * number of spokes/lamella
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getNumScalar();

	/**
	 * number of spokes/lamella
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @param num the num
	 */
	public DataNode setNumScalar(Long numValue);

	/**
	 * twist angle along axis
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getTwist();
	
	/**
	 * twist angle along axis
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param twistDataset the twistDataset
	 */
	public DataNode setTwist(IDataset twistDataset);

	/**
	 * twist angle along axis
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getTwistScalar();

	/**
	 * twist angle along axis
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param twist the twist
	 */
	public DataNode setTwistScalar(Double twistValue);

	/**
	 * offset vertical angle
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getTable();
	
	/**
	 * offset vertical angle
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param tableDataset the tableDataset
	 */
	public DataNode setTable(IDataset tableDataset);

	/**
	 * offset vertical angle
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getTableScalar();

	/**
	 * offset vertical angle
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param table the table
	 */
	public DataNode setTableScalar(Double tableValue);

	/**
	 * input beam height
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getHeight();
	
	/**
	 * input beam height
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param heightDataset the heightDataset
	 */
	public DataNode setHeight(IDataset heightDataset);

	/**
	 * input beam height
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getHeightScalar();

	/**
	 * input beam height
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param height the height
	 */
	public DataNode setHeightScalar(Double heightValue);

	/**
	 * input beam width
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getWidth();
	
	/**
	 * input beam width
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param widthDataset the widthDataset
	 */
	public DataNode setWidth(IDataset widthDataset);

	/**
	 * input beam width
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getWidthScalar();

	/**
	 * input beam width
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param width the width
	 */
	public DataNode setWidthScalar(Double widthValue);

	/**
	 * wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getWavelength();
	
	/**
	 * wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @param wavelengthDataset the wavelengthDataset
	 */
	public DataNode setWavelength(IDataset wavelengthDataset);

	/**
	 * wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getWavelengthScalar();

	/**
	 * wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @param wavelength the wavelength
	 */
	public DataNode setWavelengthScalar(Double wavelengthValue);

	/**
	 * deviation FWHM /Wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getWavelength_spread();
	
	/**
	 * deviation FWHM /Wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @param wavelength_spreadDataset the wavelength_spreadDataset
	 */
	public DataNode setWavelength_spread(IDataset wavelength_spreadDataset);

	/**
	 * deviation FWHM /Wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getWavelength_spreadScalar();

	/**
	 * deviation FWHM /Wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @param wavelength_spread the wavelength_spread
	 */
	public DataNode setWavelength_spreadScalar(Double wavelength_spreadValue);

	/**
	 * 
	 * @return  the value.
	 */
	public NXgeometry getGeometry();
	
	/**
	 * 
	 * @param geometryGroup the geometryGroup
	 */
	public void setGeometry(NXgeometry geometryGroup);

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
