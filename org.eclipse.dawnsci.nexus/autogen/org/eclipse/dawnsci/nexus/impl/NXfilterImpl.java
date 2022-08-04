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
 * For band pass beam filters.
 * If uncertain whether to use :ref:`NXfilter` (band-pass filter)
 * or :ref:`NXattenuator` (reduces beam intensity), then use
 * :ref:`NXattenuator`.
 * 
 */
public class NXfilterImpl extends NXobjectImpl implements NXfilter {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_SENSOR);

	public NXfilterImpl() {
		super();
	}

	public NXfilterImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXfilter.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_FILTER;
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
	public IDataset getStatus() {
		return getDataset(NX_STATUS);
	}

	@Override
	public String getStatusScalar() {
		return getString(NX_STATUS);
	}

	@Override
	public DataNode setStatus(IDataset statusDataset) {
		return setDataset(NX_STATUS, statusDataset);
	}

	@Override
	public DataNode setStatusScalar(String statusValue) {
		return setString(NX_STATUS, statusValue);
	}

	@Override
	public NXdata getTransmission() {
		// dataNodeName = NX_TRANSMISSION
		return getChild("transmission", NXdata.class);
	}

	@Override
	public void setTransmission(NXdata transmissionGroup) {
		putChild("transmission", transmissionGroup);
	}

	@Override
	public IDataset getTemperature() {
		return getDataset(NX_TEMPERATURE);
	}

	@Override
	public Double getTemperatureScalar() {
		return getDouble(NX_TEMPERATURE);
	}

	@Override
	public DataNode setTemperature(IDataset temperatureDataset) {
		return setDataset(NX_TEMPERATURE, temperatureDataset);
	}

	@Override
	public DataNode setTemperatureScalar(Double temperatureValue) {
		return setField(NX_TEMPERATURE, temperatureValue);
	}

	@Override
	public NXlog getTemperature_log() {
		// dataNodeName = NX_TEMPERATURE_LOG
		return getChild("temperature_log", NXlog.class);
	}

	@Override
	public void setTemperature_log(NXlog temperature_logGroup) {
		putChild("temperature_log", temperature_logGroup);
	}

	@Override
	public IDataset getThickness() {
		return getDataset(NX_THICKNESS);
	}

	@Override
	public Double getThicknessScalar() {
		return getDouble(NX_THICKNESS);
	}

	@Override
	public DataNode setThickness(IDataset thicknessDataset) {
		return setDataset(NX_THICKNESS, thicknessDataset);
	}

	@Override
	public DataNode setThicknessScalar(Double thicknessValue) {
		return setField(NX_THICKNESS, thicknessValue);
	}

	@Override
	public IDataset getDensity() {
		return getDataset(NX_DENSITY);
	}

	@Override
	public Number getDensityScalar() {
		return getNumber(NX_DENSITY);
	}

	@Override
	public DataNode setDensity(IDataset densityDataset) {
		return setDataset(NX_DENSITY, densityDataset);
	}

	@Override
	public DataNode setDensityScalar(Number densityValue) {
		return setField(NX_DENSITY, densityValue);
	}

	@Override
	public IDataset getChemical_formula() {
		return getDataset(NX_CHEMICAL_FORMULA);
	}

	@Override
	public String getChemical_formulaScalar() {
		return getString(NX_CHEMICAL_FORMULA);
	}

	@Override
	public DataNode setChemical_formula(IDataset chemical_formulaDataset) {
		return setDataset(NX_CHEMICAL_FORMULA, chemical_formulaDataset);
	}

	@Override
	public DataNode setChemical_formulaScalar(String chemical_formulaValue) {
		return setString(NX_CHEMICAL_FORMULA, chemical_formulaValue);
	}

	@Override
	public NXsensor getSensor_type() {
		// dataNodeName = NX_SENSOR_TYPE
		return getChild("sensor_type", NXsensor.class);
	}

	@Override
	public void setSensor_type(NXsensor sensor_typeGroup) {
		putChild("sensor_type", sensor_typeGroup);
	}

	@Override
	public IDataset getUnit_cell_a() {
		return getDataset(NX_UNIT_CELL_A);
	}

	@Override
	public Double getUnit_cell_aScalar() {
		return getDouble(NX_UNIT_CELL_A);
	}

	@Override
	public DataNode setUnit_cell_a(IDataset unit_cell_aDataset) {
		return setDataset(NX_UNIT_CELL_A, unit_cell_aDataset);
	}

	@Override
	public DataNode setUnit_cell_aScalar(Double unit_cell_aValue) {
		return setField(NX_UNIT_CELL_A, unit_cell_aValue);
	}

	@Override
	public IDataset getUnit_cell_b() {
		return getDataset(NX_UNIT_CELL_B);
	}

	@Override
	public Double getUnit_cell_bScalar() {
		return getDouble(NX_UNIT_CELL_B);
	}

	@Override
	public DataNode setUnit_cell_b(IDataset unit_cell_bDataset) {
		return setDataset(NX_UNIT_CELL_B, unit_cell_bDataset);
	}

	@Override
	public DataNode setUnit_cell_bScalar(Double unit_cell_bValue) {
		return setField(NX_UNIT_CELL_B, unit_cell_bValue);
	}

	@Override
	public IDataset getUnit_cell_c() {
		return getDataset(NX_UNIT_CELL_C);
	}

	@Override
	public Double getUnit_cell_cScalar() {
		return getDouble(NX_UNIT_CELL_C);
	}

	@Override
	public DataNode setUnit_cell_c(IDataset unit_cell_cDataset) {
		return setDataset(NX_UNIT_CELL_C, unit_cell_cDataset);
	}

	@Override
	public DataNode setUnit_cell_cScalar(Double unit_cell_cValue) {
		return setField(NX_UNIT_CELL_C, unit_cell_cValue);
	}

	@Override
	public IDataset getUnit_cell_alpha() {
		return getDataset(NX_UNIT_CELL_ALPHA);
	}

	@Override
	public Double getUnit_cell_alphaScalar() {
		return getDouble(NX_UNIT_CELL_ALPHA);
	}

	@Override
	public DataNode setUnit_cell_alpha(IDataset unit_cell_alphaDataset) {
		return setDataset(NX_UNIT_CELL_ALPHA, unit_cell_alphaDataset);
	}

	@Override
	public DataNode setUnit_cell_alphaScalar(Double unit_cell_alphaValue) {
		return setField(NX_UNIT_CELL_ALPHA, unit_cell_alphaValue);
	}

	@Override
	public IDataset getUnit_cell_beta() {
		return getDataset(NX_UNIT_CELL_BETA);
	}

	@Override
	public Double getUnit_cell_betaScalar() {
		return getDouble(NX_UNIT_CELL_BETA);
	}

	@Override
	public DataNode setUnit_cell_beta(IDataset unit_cell_betaDataset) {
		return setDataset(NX_UNIT_CELL_BETA, unit_cell_betaDataset);
	}

	@Override
	public DataNode setUnit_cell_betaScalar(Double unit_cell_betaValue) {
		return setField(NX_UNIT_CELL_BETA, unit_cell_betaValue);
	}

	@Override
	public IDataset getUnit_cell_gamma() {
		return getDataset(NX_UNIT_CELL_GAMMA);
	}

	@Override
	public Double getUnit_cell_gammaScalar() {
		return getDouble(NX_UNIT_CELL_GAMMA);
	}

	@Override
	public DataNode setUnit_cell_gamma(IDataset unit_cell_gammaDataset) {
		return setDataset(NX_UNIT_CELL_GAMMA, unit_cell_gammaDataset);
	}

	@Override
	public DataNode setUnit_cell_gammaScalar(Double unit_cell_gammaValue) {
		return setField(NX_UNIT_CELL_GAMMA, unit_cell_gammaValue);
	}

	@Override
	public IDataset getUnit_cell_volume() {
		return getDataset(NX_UNIT_CELL_VOLUME);
	}

	@Override
	public Double getUnit_cell_volumeScalar() {
		return getDouble(NX_UNIT_CELL_VOLUME);
	}

	@Override
	public DataNode setUnit_cell_volume(IDataset unit_cell_volumeDataset) {
		return setDataset(NX_UNIT_CELL_VOLUME, unit_cell_volumeDataset);
	}

	@Override
	public DataNode setUnit_cell_volumeScalar(Double unit_cell_volumeValue) {
		return setField(NX_UNIT_CELL_VOLUME, unit_cell_volumeValue);
	}

	@Override
	public IDataset getOrientation_matrix() {
		return getDataset(NX_ORIENTATION_MATRIX);
	}

	@Override
	public Double getOrientation_matrixScalar() {
		return getDouble(NX_ORIENTATION_MATRIX);
	}

	@Override
	public DataNode setOrientation_matrix(IDataset orientation_matrixDataset) {
		return setDataset(NX_ORIENTATION_MATRIX, orientation_matrixDataset);
	}

	@Override
	public DataNode setOrientation_matrixScalar(Double orientation_matrixValue) {
		return setField(NX_ORIENTATION_MATRIX, orientation_matrixValue);
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
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
