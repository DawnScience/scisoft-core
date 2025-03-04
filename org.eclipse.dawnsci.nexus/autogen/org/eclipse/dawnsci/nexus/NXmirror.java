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
 * A beamline mirror or supermirror.
 *
 */
public interface NXmirror extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_INCIDENT_ANGLE = "incident_angle";
	public static final String NX_BEND_ANGLE_X = "bend_angle_x";
	public static final String NX_BEND_ANGLE_Y = "bend_angle_y";
	public static final String NX_INTERIOR_ATMOSPHERE = "interior_atmosphere";
	public static final String NX_EXTERNAL_MATERIAL = "external_material";
	public static final String NX_M_VALUE = "m_value";
	public static final String NX_SUBSTRATE_MATERIAL = "substrate_material";
	public static final String NX_SUBSTRATE_DENSITY = "substrate_density";
	public static final String NX_SUBSTRATE_THICKNESS = "substrate_thickness";
	public static final String NX_COATING_MATERIAL = "coating_material";
	public static final String NX_SUBSTRATE_ROUGHNESS = "substrate_roughness";
	public static final String NX_COATING_ROUGHNESS = "coating_roughness";
	public static final String NX_EVEN_LAYER_MATERIAL = "even_layer_material";
	public static final String NX_EVEN_LAYER_DENSITY = "even_layer_density";
	public static final String NX_ODD_LAYER_MATERIAL = "odd_layer_material";
	public static final String NX_ODD_LAYER_DENSITY = "odd_layer_density";
	public static final String NX_LAYER_THICKNESS = "layer_thickness";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the mirror and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the mirror and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the mirror and NXoff_geometry to describe its shape instead
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public NXgeometry getGeometry(String name);

	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the mirror and NXoff_geometry to describe its shape instead
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry);

	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the mirror and NXoff_geometry to describe its shape instead
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the mirror and NXoff_geometry to describe its shape instead
	 * @param geometry the child nodes to add
	 */

	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry);


	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> 
	 * mirror with a single material as a reflecting surface</li>
	 * <li><b>multi</b> 
	 * mirror with stacked, multiple layers as a reflecting surface</li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> 
	 * mirror with a single material as a reflecting surface</li>
	 * <li><b>multi</b> 
	 * mirror with stacked, multiple layers as a reflecting surface</li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> 
	 * mirror with a single material as a reflecting surface</li>
	 * <li><b>multi</b> 
	 * mirror with stacked, multiple layers as a reflecting surface</li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> 
	 * mirror with a single material as a reflecting surface</li>
	 * <li><b>multi</b> 
	 * mirror with stacked, multiple layers as a reflecting surface</li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * description of this mirror
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * description of this mirror
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * description of this mirror
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * description of this mirror
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIncident_angle();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param incident_angleDataset the incident_angleDataset
	 */
	public DataNode setIncident_angle(IDataset incident_angleDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getIncident_angleScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param incident_angle the incident_angle
	 */
	public DataNode setIncident_angleScalar(Double incident_angleValue);

	/**
	 * Reflectivity as function of wavelength
	 *
	 * @return  the value.
	 */
	public NXdata getReflectivity();

	/**
	 * Reflectivity as function of wavelength
	 *
	 * @param reflectivityGroup the reflectivityGroup
	 */
	public void setReflectivity(NXdata reflectivityGroup);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBend_angle_x();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param bend_angle_xDataset the bend_angle_xDataset
	 */
	public DataNode setBend_angle_x(IDataset bend_angle_xDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBend_angle_xScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param bend_angle_x the bend_angle_x
	 */
	public DataNode setBend_angle_xScalar(Double bend_angle_xValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBend_angle_y();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param bend_angle_yDataset the bend_angle_yDataset
	 */
	public DataNode setBend_angle_y(IDataset bend_angle_yDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBend_angle_yScalar();

	/**
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
	 * The m value for a supermirror, which defines the supermirror
	 * regime in multiples of the critical angle of Nickel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getM_value();

	/**
	 * The m value for a supermirror, which defines the supermirror
	 * regime in multiples of the critical angle of Nickel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param m_valueDataset the m_valueDataset
	 */
	public DataNode setM_value(IDataset m_valueDataset);

	/**
	 * The m value for a supermirror, which defines the supermirror
	 * regime in multiples of the critical angle of Nickel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getM_valueScalar();

	/**
	 * The m value for a supermirror, which defines the supermirror
	 * regime in multiples of the critical angle of Nickel.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param m_value the m_value
	 */
	public DataNode setM_valueScalar(Double m_valueValue);

	/**
	 *
	 * @return  the value.
	 */
	public Dataset getSubstrate_material();

	/**
	 *
	 * @param substrate_materialDataset the substrate_materialDataset
	 */
	public DataNode setSubstrate_material(IDataset substrate_materialDataset);

	/**
	 *
	 * @return  the value.
	 */
	public String getSubstrate_materialScalar();

	/**
	 *
	 * @param substrate_material the substrate_material
	 */
	public DataNode setSubstrate_materialScalar(String substrate_materialValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSubstrate_density();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @param substrate_densityDataset the substrate_densityDataset
	 */
	public DataNode setSubstrate_density(IDataset substrate_densityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSubstrate_densityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @param substrate_density the substrate_density
	 */
	public DataNode setSubstrate_densityScalar(Double substrate_densityValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSubstrate_thickness();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param substrate_thicknessDataset the substrate_thicknessDataset
	 */
	public DataNode setSubstrate_thickness(IDataset substrate_thicknessDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSubstrate_thicknessScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param substrate_thickness the substrate_thickness
	 */
	public DataNode setSubstrate_thicknessScalar(Double substrate_thicknessValue);

	/**
	 *
	 * @return  the value.
	 */
	public Dataset getCoating_material();

	/**
	 *
	 * @param coating_materialDataset the coating_materialDataset
	 */
	public DataNode setCoating_material(IDataset coating_materialDataset);

	/**
	 *
	 * @return  the value.
	 */
	public String getCoating_materialScalar();

	/**
	 *
	 * @param coating_material the coating_material
	 */
	public DataNode setCoating_materialScalar(String coating_materialValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSubstrate_roughness();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param substrate_roughnessDataset the substrate_roughnessDataset
	 */
	public DataNode setSubstrate_roughness(IDataset substrate_roughnessDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSubstrate_roughnessScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param substrate_roughness the substrate_roughness
	 */
	public DataNode setSubstrate_roughnessScalar(Double substrate_roughnessValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCoating_roughness();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param coating_roughnessDataset the coating_roughnessDataset
	 */
	public DataNode setCoating_roughness(IDataset coating_roughnessDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCoating_roughnessScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param coating_roughness the coating_roughness
	 */
	public DataNode setCoating_roughnessScalar(Double coating_roughnessValue);

	/**
	 *
	 * @return  the value.
	 */
	public Dataset getEven_layer_material();

	/**
	 *
	 * @param even_layer_materialDataset the even_layer_materialDataset
	 */
	public DataNode setEven_layer_material(IDataset even_layer_materialDataset);

	/**
	 *
	 * @return  the value.
	 */
	public String getEven_layer_materialScalar();

	/**
	 *
	 * @param even_layer_material the even_layer_material
	 */
	public DataNode setEven_layer_materialScalar(String even_layer_materialValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEven_layer_density();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @param even_layer_densityDataset the even_layer_densityDataset
	 */
	public DataNode setEven_layer_density(IDataset even_layer_densityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getEven_layer_densityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @param even_layer_density the even_layer_density
	 */
	public DataNode setEven_layer_densityScalar(Double even_layer_densityValue);

	/**
	 *
	 * @return  the value.
	 */
	public Dataset getOdd_layer_material();

	/**
	 *
	 * @param odd_layer_materialDataset the odd_layer_materialDataset
	 */
	public DataNode setOdd_layer_material(IDataset odd_layer_materialDataset);

	/**
	 *
	 * @return  the value.
	 */
	public String getOdd_layer_materialScalar();

	/**
	 *
	 * @param odd_layer_material the odd_layer_material
	 */
	public DataNode setOdd_layer_materialScalar(String odd_layer_materialValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOdd_layer_density();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @param odd_layer_densityDataset the odd_layer_densityDataset
	 */
	public DataNode setOdd_layer_density(IDataset odd_layer_densityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOdd_layer_densityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @param odd_layer_density the odd_layer_density
	 */
	public DataNode setOdd_layer_densityScalar(Double odd_layer_densityValue);

	/**
	 * An array describing the thickness of each layer
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLayer_thickness();

	/**
	 * An array describing the thickness of each layer
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param layer_thicknessDataset the layer_thicknessDataset
	 */
	public DataNode setLayer_thickness(IDataset layer_thicknessDataset);

	/**
	 * An array describing the thickness of each layer
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getLayer_thicknessScalar();

	/**
	 * An array describing the thickness of each layer
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param layer_thickness the layer_thickness
	 */
	public DataNode setLayer_thicknessScalar(Double layer_thicknessValue);

	/**
	 * A NXshape group describing the shape of the mirror
	 *
	 * @deprecated Use NXoff_geometry instead
	 * @return  the value.
	 */
	@Deprecated
	public NXshape getShape();

	/**
	 * A NXshape group describing the shape of the mirror
	 *
	 * @deprecated Use NXoff_geometry instead
	 * @param shapeGroup the shapeGroup
	 */
	@Deprecated
	public void setShape(NXshape shapeGroup);

	/**
	 * Numerical description of the surface figure of the mirror.
	 *
	 * @return  the value.
	 */
	public NXdata getFigure_data();

	/**
	 * Numerical description of the surface figure of the mirror.
	 *
	 * @param figure_dataGroup the figure_dataGroup
	 */
	public void setFigure_data(NXdata figure_dataGroup);

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
	 * Given a flat mirror, the reference plane is the plane which contains the "entry" surface of the mirror. The reference
	 * point of the mirror in the x and y axis is the centre of the mirror on that plane. The reference plane is orthogonal
	 * to the z axis and the location of this plane is the reference point on the z axis. The mirror faces negative z values.
	 * .. image:: mirror/mirror.png
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
	 * Given a flat mirror, the reference plane is the plane which contains the "entry" surface of the mirror. The reference
	 * point of the mirror in the x and y axis is the centre of the mirror on that plane. The reference plane is orthogonal
	 * to the z axis and the location of this plane is the reference point on the z axis. The mirror faces negative z values.
	 * .. image:: mirror/mirror.png
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
	 * Given a flat mirror, the reference plane is the plane which contains the "entry" surface of the mirror. The reference
	 * point of the mirror in the x and y axis is the centre of the mirror on that plane. The reference plane is orthogonal
	 * to the z axis and the location of this plane is the reference point on the z axis. The mirror faces negative z values.
	 * .. image:: mirror/mirror.png
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
	 * Given a flat mirror, the reference plane is the plane which contains the "entry" surface of the mirror. The reference
	 * point of the mirror in the x and y axis is the centre of the mirror on that plane. The reference plane is orthogonal
	 * to the z axis and the location of this plane is the reference point on the z axis. The mirror faces negative z values.
	 * .. image:: mirror/mirror.png
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
