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
 * An X-ray lens, typically at a synchrotron X-ray beam line.
 * Based on information provided by Gerd Wellenreuther (DESY).
 * 
 */
public interface NXxraylens extends NXobject {

	public static final String NX_LENS_GEOMETRY = "lens_geometry";
	public static final String NX_SYMMETRIC = "symmetric";
	public static final String NX_CYLINDRICAL = "cylindrical";
	public static final String NX_FOCUS_TYPE = "focus_type";
	public static final String NX_LENS_THICKNESS = "lens_thickness";
	public static final String NX_LENS_LENGTH = "lens_length";
	public static final String NX_CURVATURE = "curvature";
	public static final String NX_APERTURE = "aperture";
	public static final String NX_NUMBER_OF_LENSES = "number_of_lenses";
	public static final String NX_LENS_MATERIAL = "lens_material";
	public static final String NX_GAS = "gas";
	public static final String NX_GAS_PRESSURE = "gas_pressure";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Geometry of the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>paraboloid</b> </li>
	 * <li><b>spherical</b> </li>
	 * <li><b>elliptical</b> </li>
	 * <li><b>hyperbolical</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getLens_geometry();
	
	/**
	 * Geometry of the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>paraboloid</b> </li>
	 * <li><b>spherical</b> </li>
	 * <li><b>elliptical</b> </li>
	 * <li><b>hyperbolical</b> </li></ul></p>
	 * </p>
	 * 
	 * @param lens_geometryDataset the lens_geometryDataset
	 */
	public DataNode setLens_geometry(IDataset lens_geometryDataset);

	/**
	 * Geometry of the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>paraboloid</b> </li>
	 * <li><b>spherical</b> </li>
	 * <li><b>elliptical</b> </li>
	 * <li><b>hyperbolical</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getLens_geometryScalar();

	/**
	 * Geometry of the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>paraboloid</b> </li>
	 * <li><b>spherical</b> </li>
	 * <li><b>elliptical</b> </li>
	 * <li><b>hyperbolical</b> </li></ul></p>
	 * </p>
	 * 
	 * @param lens_geometry the lens_geometry
	 */
	public DataNode setLens_geometryScalar(String lens_geometryValue);

	/**
	 * Is the device symmetric?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSymmetric();
	
	/**
	 * Is the device symmetric?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @param symmetricDataset the symmetricDataset
	 */
	public DataNode setSymmetric(IDataset symmetricDataset);

	/**
	 * Is the device symmetric?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Boolean getSymmetricScalar();

	/**
	 * Is the device symmetric?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @param symmetric the symmetric
	 */
	public DataNode setSymmetricScalar(Boolean symmetricValue);

	/**
	 * Is the device cylindrical?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getCylindrical();
	
	/**
	 * Is the device cylindrical?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @param cylindricalDataset the cylindricalDataset
	 */
	public DataNode setCylindrical(IDataset cylindricalDataset);

	/**
	 * Is the device cylindrical?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Boolean getCylindricalScalar();

	/**
	 * Is the device cylindrical?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @param cylindrical the cylindrical
	 */
	public DataNode setCylindricalScalar(Boolean cylindricalValue);

	/**
	 * Orientation of the cylinder axis.
	 * 
	 * @return  the value.
	 */
	public NXnote getCylinder_orientation();
	
	/**
	 * Orientation of the cylinder axis.
	 * 
	 * @param cylinder_orientationGroup the cylinder_orientationGroup
	 */
	public void setCylinder_orientation(NXnote cylinder_orientationGroup);

	/**
	 * The type of focus of the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>line</b> </li>
	 * <li><b>point</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFocus_type();
	
	/**
	 * The type of focus of the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>line</b> </li>
	 * <li><b>point</b> </li></ul></p>
	 * </p>
	 * 
	 * @param focus_typeDataset the focus_typeDataset
	 */
	public DataNode setFocus_type(IDataset focus_typeDataset);

	/**
	 * The type of focus of the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>line</b> </li>
	 * <li><b>point</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getFocus_typeScalar();

	/**
	 * The type of focus of the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>line</b> </li>
	 * <li><b>point</b> </li></ul></p>
	 * </p>
	 * 
	 * @param focus_type the focus_type
	 */
	public DataNode setFocus_typeScalar(String focus_typeValue);

	/**
	 * Thickness of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getLens_thickness();
	
	/**
	 * Thickness of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param lens_thicknessDataset the lens_thicknessDataset
	 */
	public DataNode setLens_thickness(IDataset lens_thicknessDataset);

	/**
	 * Thickness of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getLens_thicknessScalar();

	/**
	 * Thickness of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param lens_thickness the lens_thickness
	 */
	public DataNode setLens_thicknessScalar(Double lens_thicknessValue);

	/**
	 * Length of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getLens_length();
	
	/**
	 * Length of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param lens_lengthDataset the lens_lengthDataset
	 */
	public DataNode setLens_length(IDataset lens_lengthDataset);

	/**
	 * Length of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getLens_lengthScalar();

	/**
	 * Length of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param lens_length the lens_length
	 */
	public DataNode setLens_lengthScalar(Double lens_lengthValue);

	/**
	 * Radius of the curvature as measured in the middle of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getCurvature();
	
	/**
	 * Radius of the curvature as measured in the middle of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param curvatureDataset the curvatureDataset
	 */
	public DataNode setCurvature(IDataset curvatureDataset);

	/**
	 * Radius of the curvature as measured in the middle of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getCurvatureScalar();

	/**
	 * Radius of the curvature as measured in the middle of the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param curvature the curvature
	 */
	public DataNode setCurvatureScalar(Double curvatureValue);

	/**
	 * Diameter of the lens.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getAperture();
	
	/**
	 * Diameter of the lens.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param apertureDataset the apertureDataset
	 */
	public DataNode setAperture(IDataset apertureDataset);

	/**
	 * Diameter of the lens.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getApertureScalar();

	/**
	 * Diameter of the lens.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param aperture the aperture
	 */
	public DataNode setApertureScalar(Double apertureValue);

	/**
	 * Number of lenses that make up the compound lens.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getNumber_of_lenses();
	
	/**
	 * Number of lenses that make up the compound lens.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param number_of_lensesDataset the number_of_lensesDataset
	 */
	public DataNode setNumber_of_lenses(IDataset number_of_lensesDataset);

	/**
	 * Number of lenses that make up the compound lens.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getNumber_of_lensesScalar();

	/**
	 * Number of lenses that make up the compound lens.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param number_of_lenses the number_of_lenses
	 */
	public DataNode setNumber_of_lensesScalar(Long number_of_lensesValue);

	/**
	 * Material used to make the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getLens_material();
	
	/**
	 * Material used to make the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param lens_materialDataset the lens_materialDataset
	 */
	public DataNode setLens_material(IDataset lens_materialDataset);

	/**
	 * Material used to make the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getLens_materialScalar();

	/**
	 * Material used to make the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param lens_material the lens_material
	 */
	public DataNode setLens_materialScalar(String lens_materialValue);

	/**
	 * Gas used to fill the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getGas();
	
	/**
	 * Gas used to fill the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param gasDataset the gasDataset
	 */
	public DataNode setGas(IDataset gasDataset);

	/**
	 * Gas used to fill the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getGasScalar();

	/**
	 * Gas used to fill the lens
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param gas the gas
	 */
	public DataNode setGasScalar(String gasValue);

	/**
	 * Gas pressure in the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getGas_pressure();
	
	/**
	 * Gas pressure in the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * </p>
	 * 
	 * @param gas_pressureDataset the gas_pressureDataset
	 */
	public DataNode setGas_pressure(IDataset gas_pressureDataset);

	/**
	 * Gas pressure in the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getGas_pressureScalar();

	/**
	 * Gas pressure in the lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * </p>
	 * 
	 * @param gas_pressure the gas_pressure
	 */
	public DataNode setGas_pressureScalar(Double gas_pressureValue);

	/**
	 * This group describes the shape of the beam line component
	 * 
	 * @return  the value.
	 */
	public NXoff_geometry getOff_geometry();
	
	/**
	 * This group describes the shape of the beam line component
	 * 
	 * @param off_geometryGroup the off_geometryGroup
	 */
	public void setOff_geometry(NXoff_geometry off_geometryGroup);

	/**
	 * Get a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public NXoff_geometry getOff_geometry(String name);
	
	/**
	 * Set a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param off_geometry the value to set
	 */
	public void setOff_geometry(String name, NXoff_geometry off_geometry);
	
	/**
	 * Get all NXoff_geometry nodes:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public Map<String, NXoff_geometry> getAllOff_geometry();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 * 
	 * @param off_geometry the child nodes to add 
	 */
	
	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry);
	

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
	 * .. todo::
	 * Add a definition for the reference point of a x-ray lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDepends_on();
	
	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a x-ray lens.
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
	 * .. todo::
	 * Add a definition for the reference point of a x-ray lens.
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
	 * .. todo::
	 * Add a definition for the reference point of a x-ray lens.
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
