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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

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

 */
public class NXguideImpl extends NXobjectImpl implements NXguide {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_OFF_GEOMETRY,
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXguideImpl() {
		super();
	}

	public NXguideImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXguide.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_GUIDE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	@Deprecated
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	@Deprecated
	public NXgeometry getGeometry(String name) {
		return getChild(name, NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry) {
		putChild(name, geometry);
	}

	@Override
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry() {
		return getChildren(NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry) {
		setChildren(geometry);
	}

	@Override
	public Dataset getDescription() {
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
	public Dataset getIncident_angle() {
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
	public Dataset getBend_angle_x() {
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
	public Dataset getBend_angle_y() {
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
	public Dataset getInterior_atmosphere() {
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
	public Dataset getExternal_material() {
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
	public Dataset getM_value() {
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
	public Dataset getSubstrate_material() {
		return getDataset(NX_SUBSTRATE_MATERIAL);
	}

	@Override
	public Double getSubstrate_materialScalar() {
		return getDouble(NX_SUBSTRATE_MATERIAL);
	}

	@Override
	public DataNode setSubstrate_material(IDataset substrate_materialDataset) {
		return setDataset(NX_SUBSTRATE_MATERIAL, substrate_materialDataset);
	}

	@Override
	public DataNode setSubstrate_materialScalar(Double substrate_materialValue) {
		return setField(NX_SUBSTRATE_MATERIAL, substrate_materialValue);
	}

	@Override
	public Dataset getSubstrate_thickness() {
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
	public Dataset getCoating_material() {
		return getDataset(NX_COATING_MATERIAL);
	}

	@Override
	public Double getCoating_materialScalar() {
		return getDouble(NX_COATING_MATERIAL);
	}

	@Override
	public DataNode setCoating_material(IDataset coating_materialDataset) {
		return setDataset(NX_COATING_MATERIAL, coating_materialDataset);
	}

	@Override
	public DataNode setCoating_materialScalar(Double coating_materialValue) {
		return setField(NX_COATING_MATERIAL, coating_materialValue);
	}

	@Override
	public Dataset getSubstrate_roughness() {
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
	public Dataset getCoating_roughness() {
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
	public Dataset getNumber_sections() {
		return getDataset(NX_NUMBER_SECTIONS);
	}

	@Override
	public Long getNumber_sectionsScalar() {
		return getLong(NX_NUMBER_SECTIONS);
	}

	@Override
	public DataNode setNumber_sections(IDataset number_sectionsDataset) {
		return setDataset(NX_NUMBER_SECTIONS, number_sectionsDataset);
	}

	@Override
	public DataNode setNumber_sectionsScalar(Long number_sectionsValue) {
		return setField(NX_NUMBER_SECTIONS, number_sectionsValue);
	}

	@Override
	public NXoff_geometry getOff_geometry() {
		// dataNodeName = NX_OFF_GEOMETRY
		return getChild("off_geometry", NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(NXoff_geometry off_geometryGroup) {
		putChild("off_geometry", off_geometryGroup);
	}

	@Override
	public NXoff_geometry getOff_geometry(String name) {
		return getChild(name, NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(String name, NXoff_geometry off_geometry) {
		putChild(name, off_geometry);
	}

	@Override
	public Map<String, NXoff_geometry> getAllOff_geometry() {
		return getChildren(NXoff_geometry.class);
	}

	@Override
	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry) {
		setChildren(off_geometry);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

	@Override
	public Dataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
	}

	@Override
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}

	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

}
