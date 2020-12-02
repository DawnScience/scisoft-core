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
 * A fresnel zone plate
 * 
 */
public class NXfresnel_zone_plateImpl extends NXobjectImpl implements NXfresnel_zone_plate {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXfresnel_zone_plateImpl() {
		super();
	}

	public NXfresnel_zone_plateImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXfresnel_zone_plate.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_FRESNEL_ZONE_PLATE;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getFocus_parameters() {
		return getDataset(NX_FOCUS_PARAMETERS);
	}

	@Override
	public Double getFocus_parametersScalar() {
		return getDouble(NX_FOCUS_PARAMETERS);
	}

	@Override
	public DataNode setFocus_parameters(IDataset focus_parametersDataset) {
		return setDataset(NX_FOCUS_PARAMETERS, focus_parametersDataset);
	}

	@Override
	public DataNode setFocus_parametersScalar(Double focus_parametersValue) {
		return setField(NX_FOCUS_PARAMETERS, focus_parametersValue);
	}

	@Override
	public IDataset getOuter_diameter() {
		return getDataset(NX_OUTER_DIAMETER);
	}

	@Override
	public Double getOuter_diameterScalar() {
		return getDouble(NX_OUTER_DIAMETER);
	}

	@Override
	public DataNode setOuter_diameter(IDataset outer_diameterDataset) {
		return setDataset(NX_OUTER_DIAMETER, outer_diameterDataset);
	}

	@Override
	public DataNode setOuter_diameterScalar(Double outer_diameterValue) {
		return setField(NX_OUTER_DIAMETER, outer_diameterValue);
	}

	@Override
	public IDataset getOutermost_zone_width() {
		return getDataset(NX_OUTERMOST_ZONE_WIDTH);
	}

	@Override
	public Double getOutermost_zone_widthScalar() {
		return getDouble(NX_OUTERMOST_ZONE_WIDTH);
	}

	@Override
	public DataNode setOutermost_zone_width(IDataset outermost_zone_widthDataset) {
		return setDataset(NX_OUTERMOST_ZONE_WIDTH, outermost_zone_widthDataset);
	}

	@Override
	public DataNode setOutermost_zone_widthScalar(Double outermost_zone_widthValue) {
		return setField(NX_OUTERMOST_ZONE_WIDTH, outermost_zone_widthValue);
	}

	@Override
	public IDataset getCentral_stop_diameter() {
		return getDataset(NX_CENTRAL_STOP_DIAMETER);
	}

	@Override
	public Double getCentral_stop_diameterScalar() {
		return getDouble(NX_CENTRAL_STOP_DIAMETER);
	}

	@Override
	public DataNode setCentral_stop_diameter(IDataset central_stop_diameterDataset) {
		return setDataset(NX_CENTRAL_STOP_DIAMETER, central_stop_diameterDataset);
	}

	@Override
	public DataNode setCentral_stop_diameterScalar(Double central_stop_diameterValue) {
		return setField(NX_CENTRAL_STOP_DIAMETER, central_stop_diameterValue);
	}

	@Override
	public IDataset getFabrication() {
		return getDataset(NX_FABRICATION);
	}

	@Override
	public String getFabricationScalar() {
		return getString(NX_FABRICATION);
	}

	@Override
	public DataNode setFabrication(IDataset fabricationDataset) {
		return setDataset(NX_FABRICATION, fabricationDataset);
	}

	@Override
	public DataNode setFabricationScalar(String fabricationValue) {
		return setString(NX_FABRICATION, fabricationValue);
	}

	@Override
	public IDataset getZone_height() {
		return getDataset(NX_ZONE_HEIGHT);
	}

	@Override
	public Double getZone_heightScalar() {
		return getDouble(NX_ZONE_HEIGHT);
	}

	@Override
	public DataNode setZone_height(IDataset zone_heightDataset) {
		return setDataset(NX_ZONE_HEIGHT, zone_heightDataset);
	}

	@Override
	public DataNode setZone_heightScalar(Double zone_heightValue) {
		return setField(NX_ZONE_HEIGHT, zone_heightValue);
	}

	@Override
	public IDataset getZone_material() {
		return getDataset(NX_ZONE_MATERIAL);
	}

	@Override
	public String getZone_materialScalar() {
		return getString(NX_ZONE_MATERIAL);
	}

	@Override
	public DataNode setZone_material(IDataset zone_materialDataset) {
		return setDataset(NX_ZONE_MATERIAL, zone_materialDataset);
	}

	@Override
	public DataNode setZone_materialScalar(String zone_materialValue) {
		return setString(NX_ZONE_MATERIAL, zone_materialValue);
	}

	@Override
	public IDataset getZone_support_material() {
		return getDataset(NX_ZONE_SUPPORT_MATERIAL);
	}

	@Override
	public String getZone_support_materialScalar() {
		return getString(NX_ZONE_SUPPORT_MATERIAL);
	}

	@Override
	public DataNode setZone_support_material(IDataset zone_support_materialDataset) {
		return setDataset(NX_ZONE_SUPPORT_MATERIAL, zone_support_materialDataset);
	}

	@Override
	public DataNode setZone_support_materialScalar(String zone_support_materialValue) {
		return setString(NX_ZONE_SUPPORT_MATERIAL, zone_support_materialValue);
	}

	@Override
	public IDataset getCentral_stop_material() {
		return getDataset(NX_CENTRAL_STOP_MATERIAL);
	}

	@Override
	public String getCentral_stop_materialScalar() {
		return getString(NX_CENTRAL_STOP_MATERIAL);
	}

	@Override
	public DataNode setCentral_stop_material(IDataset central_stop_materialDataset) {
		return setDataset(NX_CENTRAL_STOP_MATERIAL, central_stop_materialDataset);
	}

	@Override
	public DataNode setCentral_stop_materialScalar(String central_stop_materialValue) {
		return setString(NX_CENTRAL_STOP_MATERIAL, central_stop_materialValue);
	}

	@Override
	public IDataset getCentral_stop_thickness() {
		return getDataset(NX_CENTRAL_STOP_THICKNESS);
	}

	@Override
	public Double getCentral_stop_thicknessScalar() {
		return getDouble(NX_CENTRAL_STOP_THICKNESS);
	}

	@Override
	public DataNode setCentral_stop_thickness(IDataset central_stop_thicknessDataset) {
		return setDataset(NX_CENTRAL_STOP_THICKNESS, central_stop_thicknessDataset);
	}

	@Override
	public DataNode setCentral_stop_thicknessScalar(Double central_stop_thicknessValue) {
		return setField(NX_CENTRAL_STOP_THICKNESS, central_stop_thicknessValue);
	}

	@Override
	public IDataset getMask_thickness() {
		return getDataset(NX_MASK_THICKNESS);
	}

	@Override
	public Double getMask_thicknessScalar() {
		return getDouble(NX_MASK_THICKNESS);
	}

	@Override
	public DataNode setMask_thickness(IDataset mask_thicknessDataset) {
		return setDataset(NX_MASK_THICKNESS, mask_thicknessDataset);
	}

	@Override
	public DataNode setMask_thicknessScalar(Double mask_thicknessValue) {
		return setField(NX_MASK_THICKNESS, mask_thicknessValue);
	}

	@Override
	public IDataset getMask_material() {
		return getDataset(NX_MASK_MATERIAL);
	}

	@Override
	public String getMask_materialScalar() {
		return getString(NX_MASK_MATERIAL);
	}

	@Override
	public DataNode setMask_material(IDataset mask_materialDataset) {
		return setDataset(NX_MASK_MATERIAL, mask_materialDataset);
	}

	@Override
	public DataNode setMask_materialScalar(String mask_materialValue) {
		return setString(NX_MASK_MATERIAL, mask_materialValue);
	}

	@Override
	public IDataset getSupport_membrane_material() {
		return getDataset(NX_SUPPORT_MEMBRANE_MATERIAL);
	}

	@Override
	public String getSupport_membrane_materialScalar() {
		return getString(NX_SUPPORT_MEMBRANE_MATERIAL);
	}

	@Override
	public DataNode setSupport_membrane_material(IDataset support_membrane_materialDataset) {
		return setDataset(NX_SUPPORT_MEMBRANE_MATERIAL, support_membrane_materialDataset);
	}

	@Override
	public DataNode setSupport_membrane_materialScalar(String support_membrane_materialValue) {
		return setString(NX_SUPPORT_MEMBRANE_MATERIAL, support_membrane_materialValue);
	}

	@Override
	public IDataset getSupport_membrane_thickness() {
		return getDataset(NX_SUPPORT_MEMBRANE_THICKNESS);
	}

	@Override
	public Double getSupport_membrane_thicknessScalar() {
		return getDouble(NX_SUPPORT_MEMBRANE_THICKNESS);
	}

	@Override
	public DataNode setSupport_membrane_thickness(IDataset support_membrane_thicknessDataset) {
		return setDataset(NX_SUPPORT_MEMBRANE_THICKNESS, support_membrane_thicknessDataset);
	}

	@Override
	public DataNode setSupport_membrane_thicknessScalar(Double support_membrane_thicknessValue) {
		return setField(NX_SUPPORT_MEMBRANE_THICKNESS, support_membrane_thicknessValue);
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

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
