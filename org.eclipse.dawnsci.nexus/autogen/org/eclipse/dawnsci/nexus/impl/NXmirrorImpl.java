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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * A beamline mirror or supermirror.
 * 
 */
public class NXmirrorImpl extends NXobjectImpl implements NXmirror {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_SHAPE,
		NexusBaseClass.NX_DATA);

	public NXmirrorImpl() {
		super();
	}

	public NXmirrorImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmirror.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MIRROR;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	public NXgeometry getGeometry(String name) {
		return getChild(name, NXgeometry.class);
	}

	@Override
	public void setGeometry(String name, NXgeometry geometry) {
		putChild(name, geometry);
	}

	@Override
	public Map<String, NXgeometry> getAllGeometry() {
		return getChildren(NXgeometry.class);
	}
	
	@Override
	public void setAllGeometry(Map<String, NXgeometry> geometry) {
		setChildren(geometry);
	}

	@Override
	public IDataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public IDataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getIncident_angle() {
		return getDataset(NX_INCIDENT_ANGLE);
	}

	@Override
	public Double getIncident_angleScalar() {
		return getDouble(NX_INCIDENT_ANGLE);
	}

	@Override
	public DataNode setIncident_angle(IDataset incident_angleDataset) {
		return setDataset(NX_INCIDENT_ANGLE, incident_angleDataset);
	}

	@Override
	public DataNode setIncident_angleScalar(Double incident_angleValue) {
		return setField(NX_INCIDENT_ANGLE, incident_angleValue);
	}

	@Override
	public NXdata getReflectivity() {
		// dataNodeName = NX_REFLECTIVITY
		return getChild("reflectivity", NXdata.class);
	}

	@Override
	public void setReflectivity(NXdata reflectivityGroup) {
		putChild("reflectivity", reflectivityGroup);
	}

	@Override
	public IDataset getBend_angle_x() {
		return getDataset(NX_BEND_ANGLE_X);
	}

	@Override
	public Double getBend_angle_xScalar() {
		return getDouble(NX_BEND_ANGLE_X);
	}

	@Override
	public DataNode setBend_angle_x(IDataset bend_angle_xDataset) {
		return setDataset(NX_BEND_ANGLE_X, bend_angle_xDataset);
	}

	@Override
	public DataNode setBend_angle_xScalar(Double bend_angle_xValue) {
		return setField(NX_BEND_ANGLE_X, bend_angle_xValue);
	}

	@Override
	public IDataset getBend_angle_y() {
		return getDataset(NX_BEND_ANGLE_Y);
	}

	@Override
	public Double getBend_angle_yScalar() {
		return getDouble(NX_BEND_ANGLE_Y);
	}

	@Override
	public DataNode setBend_angle_y(IDataset bend_angle_yDataset) {
		return setDataset(NX_BEND_ANGLE_Y, bend_angle_yDataset);
	}

	@Override
	public DataNode setBend_angle_yScalar(Double bend_angle_yValue) {
		return setField(NX_BEND_ANGLE_Y, bend_angle_yValue);
	}

	@Override
	public IDataset getInterior_atmosphere() {
		return getDataset(NX_INTERIOR_ATMOSPHERE);
	}

	@Override
	public String getInterior_atmosphereScalar() {
		return getString(NX_INTERIOR_ATMOSPHERE);
	}

	@Override
	public DataNode setInterior_atmosphere(IDataset interior_atmosphereDataset) {
		return setDataset(NX_INTERIOR_ATMOSPHERE, interior_atmosphereDataset);
	}

	@Override
	public DataNode setInterior_atmosphereScalar(String interior_atmosphereValue) {
		return setString(NX_INTERIOR_ATMOSPHERE, interior_atmosphereValue);
	}

	@Override
	public IDataset getExternal_material() {
		return getDataset(NX_EXTERNAL_MATERIAL);
	}

	@Override
	public String getExternal_materialScalar() {
		return getString(NX_EXTERNAL_MATERIAL);
	}

	@Override
	public DataNode setExternal_material(IDataset external_materialDataset) {
		return setDataset(NX_EXTERNAL_MATERIAL, external_materialDataset);
	}

	@Override
	public DataNode setExternal_materialScalar(String external_materialValue) {
		return setString(NX_EXTERNAL_MATERIAL, external_materialValue);
	}

	@Override
	public IDataset getM_value() {
		return getDataset(NX_M_VALUE);
	}

	@Override
	public Double getM_valueScalar() {
		return getDouble(NX_M_VALUE);
	}

	@Override
	public DataNode setM_value(IDataset m_valueDataset) {
		return setDataset(NX_M_VALUE, m_valueDataset);
	}

	@Override
	public DataNode setM_valueScalar(Double m_valueValue) {
		return setField(NX_M_VALUE, m_valueValue);
	}

	@Override
	public IDataset getSubstrate_material() {
		return getDataset(NX_SUBSTRATE_MATERIAL);
	}

	@Override
	public String getSubstrate_materialScalar() {
		return getString(NX_SUBSTRATE_MATERIAL);
	}

	@Override
	public DataNode setSubstrate_material(IDataset substrate_materialDataset) {
		return setDataset(NX_SUBSTRATE_MATERIAL, substrate_materialDataset);
	}

	@Override
	public DataNode setSubstrate_materialScalar(String substrate_materialValue) {
		return setString(NX_SUBSTRATE_MATERIAL, substrate_materialValue);
	}

	@Override
	public IDataset getSubstrate_density() {
		return getDataset(NX_SUBSTRATE_DENSITY);
	}

	@Override
	public Double getSubstrate_densityScalar() {
		return getDouble(NX_SUBSTRATE_DENSITY);
	}

	@Override
	public DataNode setSubstrate_density(IDataset substrate_densityDataset) {
		return setDataset(NX_SUBSTRATE_DENSITY, substrate_densityDataset);
	}

	@Override
	public DataNode setSubstrate_densityScalar(Double substrate_densityValue) {
		return setField(NX_SUBSTRATE_DENSITY, substrate_densityValue);
	}

	@Override
	public IDataset getSubstrate_thickness() {
		return getDataset(NX_SUBSTRATE_THICKNESS);
	}

	@Override
	public Double getSubstrate_thicknessScalar() {
		return getDouble(NX_SUBSTRATE_THICKNESS);
	}

	@Override
	public DataNode setSubstrate_thickness(IDataset substrate_thicknessDataset) {
		return setDataset(NX_SUBSTRATE_THICKNESS, substrate_thicknessDataset);
	}

	@Override
	public DataNode setSubstrate_thicknessScalar(Double substrate_thicknessValue) {
		return setField(NX_SUBSTRATE_THICKNESS, substrate_thicknessValue);
	}

	@Override
	public IDataset getCoating_material() {
		return getDataset(NX_COATING_MATERIAL);
	}

	@Override
	public String getCoating_materialScalar() {
		return getString(NX_COATING_MATERIAL);
	}

	@Override
	public DataNode setCoating_material(IDataset coating_materialDataset) {
		return setDataset(NX_COATING_MATERIAL, coating_materialDataset);
	}

	@Override
	public DataNode setCoating_materialScalar(String coating_materialValue) {
		return setString(NX_COATING_MATERIAL, coating_materialValue);
	}

	@Override
	public IDataset getSubstrate_roughness() {
		return getDataset(NX_SUBSTRATE_ROUGHNESS);
	}

	@Override
	public Double getSubstrate_roughnessScalar() {
		return getDouble(NX_SUBSTRATE_ROUGHNESS);
	}

	@Override
	public DataNode setSubstrate_roughness(IDataset substrate_roughnessDataset) {
		return setDataset(NX_SUBSTRATE_ROUGHNESS, substrate_roughnessDataset);
	}

	@Override
	public DataNode setSubstrate_roughnessScalar(Double substrate_roughnessValue) {
		return setField(NX_SUBSTRATE_ROUGHNESS, substrate_roughnessValue);
	}

	@Override
	public IDataset getCoating_roughness() {
		return getDataset(NX_COATING_ROUGHNESS);
	}

	@Override
	public Double getCoating_roughnessScalar() {
		return getDouble(NX_COATING_ROUGHNESS);
	}

	@Override
	public DataNode setCoating_roughness(IDataset coating_roughnessDataset) {
		return setDataset(NX_COATING_ROUGHNESS, coating_roughnessDataset);
	}

	@Override
	public DataNode setCoating_roughnessScalar(Double coating_roughnessValue) {
		return setField(NX_COATING_ROUGHNESS, coating_roughnessValue);
	}

	@Override
	public IDataset getEven_layer_material() {
		return getDataset(NX_EVEN_LAYER_MATERIAL);
	}

	@Override
	public String getEven_layer_materialScalar() {
		return getString(NX_EVEN_LAYER_MATERIAL);
	}

	@Override
	public DataNode setEven_layer_material(IDataset even_layer_materialDataset) {
		return setDataset(NX_EVEN_LAYER_MATERIAL, even_layer_materialDataset);
	}

	@Override
	public DataNode setEven_layer_materialScalar(String even_layer_materialValue) {
		return setString(NX_EVEN_LAYER_MATERIAL, even_layer_materialValue);
	}

	@Override
	public IDataset getEven_layer_density() {
		return getDataset(NX_EVEN_LAYER_DENSITY);
	}

	@Override
	public Double getEven_layer_densityScalar() {
		return getDouble(NX_EVEN_LAYER_DENSITY);
	}

	@Override
	public DataNode setEven_layer_density(IDataset even_layer_densityDataset) {
		return setDataset(NX_EVEN_LAYER_DENSITY, even_layer_densityDataset);
	}

	@Override
	public DataNode setEven_layer_densityScalar(Double even_layer_densityValue) {
		return setField(NX_EVEN_LAYER_DENSITY, even_layer_densityValue);
	}

	@Override
	public IDataset getOdd_layer_material() {
		return getDataset(NX_ODD_LAYER_MATERIAL);
	}

	@Override
	public String getOdd_layer_materialScalar() {
		return getString(NX_ODD_LAYER_MATERIAL);
	}

	@Override
	public DataNode setOdd_layer_material(IDataset odd_layer_materialDataset) {
		return setDataset(NX_ODD_LAYER_MATERIAL, odd_layer_materialDataset);
	}

	@Override
	public DataNode setOdd_layer_materialScalar(String odd_layer_materialValue) {
		return setString(NX_ODD_LAYER_MATERIAL, odd_layer_materialValue);
	}

	@Override
	public IDataset getOdd_layer_density() {
		return getDataset(NX_ODD_LAYER_DENSITY);
	}

	@Override
	public Double getOdd_layer_densityScalar() {
		return getDouble(NX_ODD_LAYER_DENSITY);
	}

	@Override
	public DataNode setOdd_layer_density(IDataset odd_layer_densityDataset) {
		return setDataset(NX_ODD_LAYER_DENSITY, odd_layer_densityDataset);
	}

	@Override
	public DataNode setOdd_layer_densityScalar(Double odd_layer_densityValue) {
		return setField(NX_ODD_LAYER_DENSITY, odd_layer_densityValue);
	}

	@Override
	public IDataset getLayer_thickness() {
		return getDataset(NX_LAYER_THICKNESS);
	}

	@Override
	public Double getLayer_thicknessScalar() {
		return getDouble(NX_LAYER_THICKNESS);
	}

	@Override
	public DataNode setLayer_thickness(IDataset layer_thicknessDataset) {
		return setDataset(NX_LAYER_THICKNESS, layer_thicknessDataset);
	}

	@Override
	public DataNode setLayer_thicknessScalar(Double layer_thicknessValue) {
		return setField(NX_LAYER_THICKNESS, layer_thicknessValue);
	}

	@Override
	public NXshape getShape() {
		// dataNodeName = NX_SHAPE
		return getChild("shape", NXshape.class);
	}

	@Override
	public void setShape(NXshape shapeGroup) {
		putChild("shape", shapeGroup);
	}

	@Override
	public NXdata getFigure_data() {
		// dataNodeName = NX_FIGURE_DATA
		return getChild("figure_data", NXdata.class);
	}

	@Override
	public void setFigure_data(NXdata figure_dataGroup) {
		putChild("figure_data", figure_dataGroup);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
