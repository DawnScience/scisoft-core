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
import org.eclipse.january.dataset.Dataset;

/**
 * A neutron optical element to direct the path of the beam.
 * :ref:`NXguide` is used by neutron instruments to describe
 * a guide consists of several mirrors building a shape through which
 * neutrons can be guided or directed. The simplest such form is box shaped
 * although elliptical guides are gaining in popularity.
 * The individual parts of a guide usually have common characteristics
 * but there are cases where they are different.
 * For example, a neutron guide might consist of 2 or 4 coated walls or
 * a supermirror bender with multiple, coated vanes.
 * To describe polarizing supermirrors such as used in neutron reflection,
 * it may be necessary to revise this definition of :ref:`NXguide`
 * to include :ref:`NXpolarizer` and/or :ref:`NXmirror`.
 * When even greater complexity exists in the definition of what
 * constitutes a *guide*, it has been suggested that :ref:`NXguide`
 * be redefined as a :ref:`NXcollection` of :ref:`NXmirror` each
 * having their own :ref:`NXgeometry` describing their location(s).
 * For the more general case when describing mirrors, consider using
 * :ref:`NXmirror`.
 * NOTE: The NeXus International Advisory Committee welcomes
 * comments for revision and improvement of
 * this definition of :ref:`NXguide`.
 * <p><b>Symbols:</b><ul>
 * <li><b>nsurf</b>
 * number of reflecting surfaces</li>
 * <li><b>nwl</b>
 * number of wavelengths</li></ul></p>
 *
 */
public interface NXguide extends NXobject {

	public static final String NX_DESCRIPTION = "description";
	public static final String NX_INCIDENT_ANGLE = "incident_angle";
	public static final String NX_BEND_ANGLE_X = "bend_angle_x";
	public static final String NX_BEND_ANGLE_Y = "bend_angle_y";
	public static final String NX_INTERIOR_ATMOSPHERE = "interior_atmosphere";
	public static final String NX_EXTERNAL_MATERIAL = "external_material";
	public static final String NX_M_VALUE = "m_value";
	public static final String NX_SUBSTRATE_MATERIAL = "substrate_material";
	public static final String NX_SUBSTRATE_THICKNESS = "substrate_thickness";
	public static final String NX_COATING_MATERIAL = "coating_material";
	public static final String NX_SUBSTRATE_ROUGHNESS = "substrate_roughness";
	public static final String NX_COATING_ROUGHNESS = "coating_roughness";
	public static final String NX_NUMBER_SECTIONS = "number_sections";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * TODO: Explain what this NXgeometry group means. What is intended here?
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the guid and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 * TODO: Explain what this NXgeometry group means. What is intended here?
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the guid and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * TODO: Explain what this NXgeometry group means. What is intended here?</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the guid and NXoff_geometry to describe its shape instead
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public NXgeometry getGeometry(String name);

	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * TODO: Explain what this NXgeometry group means. What is intended here?</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the guid and NXoff_geometry to describe its shape instead
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry);

	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li>
	 * TODO: Explain what this NXgeometry group means. What is intended here?</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the guid and NXoff_geometry to describe its shape instead
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * TODO: Explain what this NXgeometry group means. What is intended here?</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the guid and NXoff_geometry to describe its shape instead
	 * @param geometry the child nodes to add
	 */

	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry);


	/**
	 * A description of this particular instance of ``NXguide``.
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * A description of this particular instance of ``NXguide``.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * A description of this particular instance of ``NXguide``.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * A description of this particular instance of ``NXguide``.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIncident_angle();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param incident_angleDataset the incident_angleDataset
	 */
	public DataNode setIncident_angle(IDataset incident_angleDataset);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getIncident_angleScalar();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param incident_angle the incident_angle
	 */
	public DataNode setIncident_angleScalar(Double incident_angleValue);

	/**
	 * Reflectivity as function of reflecting surface and wavelength
	 *
	 * @return  the value.
	 */
	public NXdata getReflectivity();

	/**
	 * Reflectivity as function of reflecting surface and wavelength
	 *
	 * @param reflectivityGroup the reflectivityGroup
	 */
	public void setReflectivity(NXdata reflectivityGroup);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBend_angle_x();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param bend_angle_xDataset the bend_angle_xDataset
	 */
	public DataNode setBend_angle_x(IDataset bend_angle_xDataset);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBend_angle_xScalar();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param bend_angle_x the bend_angle_x
	 */
	public DataNode setBend_angle_xScalar(Double bend_angle_xValue);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBend_angle_y();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param bend_angle_yDataset the bend_angle_yDataset
	 */
	public DataNode setBend_angle_y(IDataset bend_angle_yDataset);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBend_angle_yScalar();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param bend_angle_y the bend_angle_y
	 */
	public DataNode setBend_angle_yScalar(Double bend_angle_yValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>helium</b> </li>
	 * <li><b>argon</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getInterior_atmosphere();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>helium</b> </li>
	 * <li><b>argon</b> </li></ul></p>
	 * </p>
	 *
	 * @param interior_atmosphereDataset the interior_atmosphereDataset
	 */
	public DataNode setInterior_atmosphere(IDataset interior_atmosphereDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>helium</b> </li>
	 * <li><b>argon</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getInterior_atmosphereScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>helium</b> </li>
	 * <li><b>argon</b> </li></ul></p>
	 * </p>
	 *
	 * @param interior_atmosphere the interior_atmosphere
	 */
	public DataNode setInterior_atmosphereScalar(String interior_atmosphereValue);

	/**
	 * external material outside substrate
	 *
	 * @return  the value.
	 */
	public Dataset getExternal_material();

	/**
	 * external material outside substrate
	 *
	 * @param external_materialDataset the external_materialDataset
	 */
	public DataNode setExternal_material(IDataset external_materialDataset);

	/**
	 * external material outside substrate
	 *
	 * @return  the value.
	 */
	public String getExternal_materialScalar();

	/**
	 * external material outside substrate
	 *
	 * @param external_material the external_material
	 */
	public DataNode setExternal_materialScalar(String external_materialValue);

	/**
	 * The ``m`` value for a supermirror, which defines the supermirror
	 * regime in multiples of the critical angle of Nickel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getM_value();

	/**
	 * The ``m`` value for a supermirror, which defines the supermirror
	 * regime in multiples of the critical angle of Nickel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param m_valueDataset the m_valueDataset
	 */
	public DataNode setM_value(IDataset m_valueDataset);

	/**
	 * The ``m`` value for a supermirror, which defines the supermirror
	 * regime in multiples of the critical angle of Nickel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getM_valueScalar();

	/**
	 * The ``m`` value for a supermirror, which defines the supermirror
	 * regime in multiples of the critical angle of Nickel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param m_value the m_value
	 */
	public DataNode setM_valueScalar(Double m_valueValue);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSubstrate_material();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param substrate_materialDataset the substrate_materialDataset
	 */
	public DataNode setSubstrate_material(IDataset substrate_materialDataset);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSubstrate_materialScalar();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param substrate_material the substrate_material
	 */
	public DataNode setSubstrate_materialScalar(Double substrate_materialValue);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSubstrate_thickness();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param substrate_thicknessDataset the substrate_thicknessDataset
	 */
	public DataNode setSubstrate_thickness(IDataset substrate_thicknessDataset);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSubstrate_thicknessScalar();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param substrate_thickness the substrate_thickness
	 */
	public DataNode setSubstrate_thicknessScalar(Double substrate_thicknessValue);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCoating_material();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param coating_materialDataset the coating_materialDataset
	 */
	public DataNode setCoating_material(IDataset coating_materialDataset);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCoating_materialScalar();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param coating_material the coating_material
	 */
	public DataNode setCoating_materialScalar(Double coating_materialValue);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSubstrate_roughness();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param substrate_roughnessDataset the substrate_roughnessDataset
	 */
	public DataNode setSubstrate_roughness(IDataset substrate_roughnessDataset);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSubstrate_roughnessScalar();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param substrate_roughness the substrate_roughness
	 */
	public DataNode setSubstrate_roughnessScalar(Double substrate_roughnessValue);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCoating_roughness();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param coating_roughnessDataset the coating_roughnessDataset
	 */
	public DataNode setCoating_roughness(IDataset coating_roughnessDataset);

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCoating_roughnessScalar();

	/**
	 * TODO: documentation needed
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nsurf;
	 * </p>
	 *
	 * @param coating_roughness the coating_roughness
	 */
	public DataNode setCoating_roughnessScalar(Double coating_roughnessValue);

	/**
	 * number of substrate sections (also called ``nsurf`` as an
	 * index in the ``NXguide`` specification)
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_sections();

	/**
	 * number of substrate sections (also called ``nsurf`` as an
	 * index in the ``NXguide`` specification)
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_sectionsDataset the number_sectionsDataset
	 */
	public DataNode setNumber_sections(IDataset number_sectionsDataset);

	/**
	 * number of substrate sections (also called ``nsurf`` as an
	 * index in the ``NXguide`` specification)
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_sectionsScalar();

	/**
	 * number of substrate sections (also called ``nsurf`` as an
	 * index in the ``NXguide`` specification)
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_sections the number_sections
	 */
	public DataNode setNumber_sectionsScalar(Long number_sectionsValue);

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
	 * The entry opening of the guide lies on the reference plane. The center of the opening on that plane is
	 * the reference point on the x and y axis. The reference plane is orthogonal to the z axis and is the
	 * reference point along the z axis. Given no bend in the guide, it is parallel with z axis and extends
	 * in the positive direction of the z axis.
	 * .. image:: guide/guide.png
	 * :width: 40%
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
	 * The entry opening of the guide lies on the reference plane. The center of the opening on that plane is
	 * the reference point on the x and y axis. The reference plane is orthogonal to the z axis and is the
	 * reference point along the z axis. Given no bend in the guide, it is parallel with z axis and extends
	 * in the positive direction of the z axis.
	 * .. image:: guide/guide.png
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
	 * The entry opening of the guide lies on the reference plane. The center of the opening on that plane is
	 * the reference point on the x and y axis. The reference plane is orthogonal to the z axis and is the
	 * reference point along the z axis. Given no bend in the guide, it is parallel with z axis and extends
	 * in the positive direction of the z axis.
	 * .. image:: guide/guide.png
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
	 * The entry opening of the guide lies on the reference plane. The center of the opening on that plane is
	 * the reference point on the x and y axis. The reference plane is orthogonal to the z axis and is the
	 * reference point along the z axis. Given no bend in the guide, it is parallel with z axis and extends
	 * in the positive direction of the z axis.
	 * .. image:: guide/guide.png
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
