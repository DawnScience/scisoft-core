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
 * A diffraction grating, as could be used in a soft X-ray monochromator
 * 
 */
public class NXgratingImpl extends NXobjectImpl implements NXgrating {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SHAPE,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXgratingImpl() {
		super();
	}

	public NXgratingImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXgrating.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_GRATING;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getAngles() {
		return getDataset(NX_ANGLES);
	}

	@Override
	public Double getAnglesScalar() {
		return getDouble(NX_ANGLES);
	}

	@Override
	public DataNode setAngles(IDataset anglesDataset) {
		return setDataset(NX_ANGLES, anglesDataset);
	}

	@Override
	public DataNode setAnglesScalar(Double anglesValue) {
		return setField(NX_ANGLES, anglesValue);
	}

	@Override
	public IDataset getPeriod() {
		return getDataset(NX_PERIOD);
	}

	@Override
	public Double getPeriodScalar() {
		return getDouble(NX_PERIOD);
	}

	@Override
	public DataNode setPeriod(IDataset periodDataset) {
		return setDataset(NX_PERIOD, periodDataset);
	}

	@Override
	public DataNode setPeriodScalar(Double periodValue) {
		return setField(NX_PERIOD, periodValue);
	}

	@Override
	public IDataset getDuty_cycle() {
		return getDataset(NX_DUTY_CYCLE);
	}

	@Override
	public Double getDuty_cycleScalar() {
		return getDouble(NX_DUTY_CYCLE);
	}

	@Override
	public DataNode setDuty_cycle(IDataset duty_cycleDataset) {
		return setDataset(NX_DUTY_CYCLE, duty_cycleDataset);
	}

	@Override
	public DataNode setDuty_cycleScalar(Double duty_cycleValue) {
		return setField(NX_DUTY_CYCLE, duty_cycleValue);
	}

	@Override
	public IDataset getDepth() {
		return getDataset(NX_DEPTH);
	}

	@Override
	public Double getDepthScalar() {
		return getDouble(NX_DEPTH);
	}

	@Override
	public DataNode setDepth(IDataset depthDataset) {
		return setDataset(NX_DEPTH, depthDataset);
	}

	@Override
	public DataNode setDepthScalar(Double depthValue) {
		return setField(NX_DEPTH, depthValue);
	}

	@Override
	public IDataset getDiffraction_order() {
		return getDataset(NX_DIFFRACTION_ORDER);
	}

	@Override
	public Long getDiffraction_orderScalar() {
		return getLong(NX_DIFFRACTION_ORDER);
	}

	@Override
	public DataNode setDiffraction_order(IDataset diffraction_orderDataset) {
		return setDataset(NX_DIFFRACTION_ORDER, diffraction_orderDataset);
	}

	@Override
	public DataNode setDiffraction_orderScalar(Long diffraction_orderValue) {
		return setField(NX_DIFFRACTION_ORDER, diffraction_orderValue);
	}

	@Override
	public IDataset getDeflection_angle() {
		return getDataset(NX_DEFLECTION_ANGLE);
	}

	@Override
	public Double getDeflection_angleScalar() {
		return getDouble(NX_DEFLECTION_ANGLE);
	}

	@Override
	public DataNode setDeflection_angle(IDataset deflection_angleDataset) {
		return setDataset(NX_DEFLECTION_ANGLE, deflection_angleDataset);
	}

	@Override
	public DataNode setDeflection_angleScalar(Double deflection_angleValue) {
		return setField(NX_DEFLECTION_ANGLE, deflection_angleValue);
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
