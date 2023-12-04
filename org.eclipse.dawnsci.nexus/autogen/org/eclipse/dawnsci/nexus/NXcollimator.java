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
 * A beamline collimator.
 * 
 */
public interface NXcollimator extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_SOLLER_ANGLE = "soller_angle";
	public static final String NX_DIVERGENCE_X = "divergence_x";
	public static final String NX_DIVERGENCE_Y = "divergence_y";
	public static final String NX_FREQUENCY = "frequency";
	public static final String NX_BLADE_THICKNESS = "blade_thickness";
	public static final String NX_BLADE_SPACING = "blade_spacing";
	public static final String NX_ABSORBING_MATERIAL = "absorbing_material";
	public static final String NX_TRANSMITTING_MATERIAL = "transmitting_material";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * position, shape and size
	 * 
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the collimator and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();
	
	/**
	 * position, shape and size
	 * 
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the collimator and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * position, shape and size</li>
	 * </ul>
	 * 
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the collimator and NXoff_geometry to describe its shape instead
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public NXgeometry getGeometry(String name);
	
	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * position, shape and size</li>
	 * </ul>
	 * 
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the collimator and NXoff_geometry to describe its shape instead
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry);
	
	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li>
	 * position, shape and size</li>
	 * </ul>
	 * 
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the collimator and NXoff_geometry to describe its shape instead
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * position, shape and size</li>
	 * </ul>
	 * 
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the collimator and NXoff_geometry to describe its shape instead
	 * @param geometry the child nodes to add 
	 */
	
	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry);
	

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Soller</b> </li>
	 * <li><b>radial</b> </li>
	 * <li><b>oscillating</b> </li>
	 * <li><b>honeycomb</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Soller</b> </li>
	 * <li><b>radial</b> </li>
	 * <li><b>oscillating</b> </li>
	 * <li><b>honeycomb</b> </li></ul></p>
	 * </p>
	 * 
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Soller</b> </li>
	 * <li><b>radial</b> </li>
	 * <li><b>oscillating</b> </li>
	 * <li><b>honeycomb</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Soller</b> </li>
	 * <li><b>radial</b> </li>
	 * <li><b>oscillating</b> </li>
	 * <li><b>honeycomb</b> </li></ul></p>
	 * </p>
	 * 
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Angular divergence of Soller collimator
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSoller_angle();
	
	/**
	 * Angular divergence of Soller collimator
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param soller_angleDataset the soller_angleDataset
	 */
	public DataNode setSoller_angle(IDataset soller_angleDataset);

	/**
	 * Angular divergence of Soller collimator
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSoller_angleScalar();

	/**
	 * Angular divergence of Soller collimator
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param soller_angle the soller_angle
	 */
	public DataNode setSoller_angleScalar(Double soller_angleValue);

	/**
	 * divergence of collimator in local x direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDivergence_x();
	
	/**
	 * divergence of collimator in local x direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param divergence_xDataset the divergence_xDataset
	 */
	public DataNode setDivergence_x(IDataset divergence_xDataset);

	/**
	 * divergence of collimator in local x direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDivergence_xScalar();

	/**
	 * divergence of collimator in local x direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param divergence_x the divergence_x
	 */
	public DataNode setDivergence_xScalar(Double divergence_xValue);

	/**
	 * divergence of collimator in local y direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDivergence_y();
	
	/**
	 * divergence of collimator in local y direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param divergence_yDataset the divergence_yDataset
	 */
	public DataNode setDivergence_y(IDataset divergence_yDataset);

	/**
	 * divergence of collimator in local y direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDivergence_yScalar();

	/**
	 * divergence of collimator in local y direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param divergence_y the divergence_y
	 */
	public DataNode setDivergence_yScalar(Double divergence_yValue);

	/**
	 * Frequency of oscillating collimator
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFrequency();
	
	/**
	 * Frequency of oscillating collimator
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @param frequencyDataset the frequencyDataset
	 */
	public DataNode setFrequency(IDataset frequencyDataset);

	/**
	 * Frequency of oscillating collimator
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFrequencyScalar();

	/**
	 * Frequency of oscillating collimator
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @param frequency the frequency
	 */
	public DataNode setFrequencyScalar(Double frequencyValue);

	/**
	 * Log of frequency
	 * 
	 * @return  the value.
	 */
	public NXlog getFrequency_log();
	
	/**
	 * Log of frequency
	 * 
	 * @param frequency_logGroup the frequency_logGroup
	 */
	public void setFrequency_log(NXlog frequency_logGroup);

	/**
	 * blade thickness
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getBlade_thickness();
	
	/**
	 * blade thickness
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param blade_thicknessDataset the blade_thicknessDataset
	 */
	public DataNode setBlade_thickness(IDataset blade_thicknessDataset);

	/**
	 * blade thickness
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getBlade_thicknessScalar();

	/**
	 * blade thickness
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param blade_thickness the blade_thickness
	 */
	public DataNode setBlade_thicknessScalar(Double blade_thicknessValue);

	/**
	 * blade spacing
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getBlade_spacing();
	
	/**
	 * blade spacing
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param blade_spacingDataset the blade_spacingDataset
	 */
	public DataNode setBlade_spacing(IDataset blade_spacingDataset);

	/**
	 * blade spacing
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getBlade_spacingScalar();

	/**
	 * blade spacing
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param blade_spacing the blade_spacing
	 */
	public DataNode setBlade_spacingScalar(Double blade_spacingValue);

	/**
	 * name of absorbing material
	 * 
	 * @return  the value.
	 */
	public IDataset getAbsorbing_material();
	
	/**
	 * name of absorbing material
	 * 
	 * @param absorbing_materialDataset the absorbing_materialDataset
	 */
	public DataNode setAbsorbing_material(IDataset absorbing_materialDataset);

	/**
	 * name of absorbing material
	 * 
	 * @return  the value.
	 */
	public String getAbsorbing_materialScalar();

	/**
	 * name of absorbing material
	 * 
	 * @param absorbing_material the absorbing_material
	 */
	public DataNode setAbsorbing_materialScalar(String absorbing_materialValue);

	/**
	 * name of transmitting material
	 * 
	 * @return  the value.
	 */
	public IDataset getTransmitting_material();
	
	/**
	 * name of transmitting material
	 * 
	 * @param transmitting_materialDataset the transmitting_materialDataset
	 */
	public DataNode setTransmitting_material(IDataset transmitting_materialDataset);

	/**
	 * name of transmitting material
	 * 
	 * @return  the value.
	 */
	public String getTransmitting_materialScalar();

	/**
	 * name of transmitting material
	 * 
	 * @param transmitting_material the transmitting_material
	 */
	public DataNode setTransmitting_materialScalar(String transmitting_materialValue);

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
	 * Assuming a collimator with a "flat" entry surface, the reference plane is the plane which contains this surface. The reference
	 * point of the collimator in the x and y axis is the centre of the collimator entry surface on that plane. The reference plane is orthogonal
	 * to the z axis and the location of this plane is the reference point on the z axis. The collimator faces negative z values.
	 * .. image:: collimator/collimator.png
	 * :width: 40%
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
	 * Assuming a collimator with a "flat" entry surface, the reference plane is the plane which contains this surface. The reference
	 * point of the collimator in the x and y axis is the centre of the collimator entry surface on that plane. The reference plane is orthogonal
	 * to the z axis and the location of this plane is the reference point on the z axis. The collimator faces negative z values.
	 * .. image:: collimator/collimator.png
	 * :width: 40%
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
	 * Assuming a collimator with a "flat" entry surface, the reference plane is the plane which contains this surface. The reference
	 * point of the collimator in the x and y axis is the centre of the collimator entry surface on that plane. The reference plane is orthogonal
	 * to the z axis and the location of this plane is the reference point on the z axis. The collimator faces negative z values.
	 * .. image:: collimator/collimator.png
	 * :width: 40%
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
	 * Assuming a collimator with a "flat" entry surface, the reference plane is the plane which contains this surface. The reference
	 * point of the collimator in the x and y axis is the centre of the collimator entry surface on that plane. The reference plane is orthogonal
	 * to the z axis and the location of this plane is the reference point on the z axis. The collimator faces negative z values.
	 * .. image:: collimator/collimator.png
	 * :width: 40%
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
