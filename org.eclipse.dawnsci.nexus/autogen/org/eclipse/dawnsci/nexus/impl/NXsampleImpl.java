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

import java.util.Date;
import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Any information on the sample.
 * This could include scanned variables that
 * are associated with one of the data dimensions, e.g. the magnetic field, or
 * logged data, e.g. monitored temperature vs elapsed time.
 * 
 */
public class NXsampleImpl extends NXobjectImpl implements NXsample {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_BEAM,
		NexusBaseClass.NX_SAMPLE_COMPONENT,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_ENVIRONMENT,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_ENVIRONMENT,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_POSITIONER);

	public NXsampleImpl() {
		super();
	}

	public NXsampleImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXsample.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SAMPLE;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getName() {
		return getDataset(NX_NAME);
	}

	@Override
	public String getNameScalar() {
		return getString(NX_NAME);
	}

	@Override
	public DataNode setName(IDataset nameDataset) {
		return setDataset(NX_NAME, nameDataset);
	}

	@Override
	public DataNode setNameScalar(String nameValue) {
		return setString(NX_NAME, nameValue);
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
	public IDataset getElectric_field() {
		return getDataset(NX_ELECTRIC_FIELD);
	}

	@Override
	public Double getElectric_fieldScalar() {
		return getDouble(NX_ELECTRIC_FIELD);
	}

	@Override
	public DataNode setElectric_field(IDataset electric_fieldDataset) {
		return setDataset(NX_ELECTRIC_FIELD, electric_fieldDataset);
	}

	@Override
	public DataNode setElectric_fieldScalar(Double electric_fieldValue) {
		return setField(NX_ELECTRIC_FIELD, electric_fieldValue);
	}

	@Override
	public String getElectric_fieldAttributeDirection() {
		return getAttrString(NX_ELECTRIC_FIELD, NX_ELECTRIC_FIELD_ATTRIBUTE_DIRECTION);
	}

	@Override
	public void setElectric_fieldAttributeDirection(String directionValue) {
		setAttribute(NX_ELECTRIC_FIELD, NX_ELECTRIC_FIELD_ATTRIBUTE_DIRECTION, directionValue);
	}

	@Override
	public IDataset getMagnetic_field() {
		return getDataset(NX_MAGNETIC_FIELD);
	}

	@Override
	public Double getMagnetic_fieldScalar() {
		return getDouble(NX_MAGNETIC_FIELD);
	}

	@Override
	public DataNode setMagnetic_field(IDataset magnetic_fieldDataset) {
		return setDataset(NX_MAGNETIC_FIELD, magnetic_fieldDataset);
	}

	@Override
	public DataNode setMagnetic_fieldScalar(Double magnetic_fieldValue) {
		return setField(NX_MAGNETIC_FIELD, magnetic_fieldValue);
	}

	@Override
	public String getMagnetic_fieldAttributeDirection() {
		return getAttrString(NX_MAGNETIC_FIELD, NX_MAGNETIC_FIELD_ATTRIBUTE_DIRECTION);
	}

	@Override
	public void setMagnetic_fieldAttributeDirection(String directionValue) {
		setAttribute(NX_MAGNETIC_FIELD, NX_MAGNETIC_FIELD_ATTRIBUTE_DIRECTION, directionValue);
	}

	@Override
	public IDataset getStress_field() {
		return getDataset(NX_STRESS_FIELD);
	}

	@Override
	public Double getStress_fieldScalar() {
		return getDouble(NX_STRESS_FIELD);
	}

	@Override
	public DataNode setStress_field(IDataset stress_fieldDataset) {
		return setDataset(NX_STRESS_FIELD, stress_fieldDataset);
	}

	@Override
	public DataNode setStress_fieldScalar(Double stress_fieldValue) {
		return setField(NX_STRESS_FIELD, stress_fieldValue);
	}

	@Override
	public String getStress_fieldAttributeDirection() {
		return getAttrString(NX_STRESS_FIELD, NX_STRESS_FIELD_ATTRIBUTE_DIRECTION);
	}

	@Override
	public void setStress_fieldAttributeDirection(String directionValue) {
		setAttribute(NX_STRESS_FIELD, NX_STRESS_FIELD_ATTRIBUTE_DIRECTION, directionValue);
	}

	@Override
	public IDataset getPressure() {
		return getDataset(NX_PRESSURE);
	}

	@Override
	public Double getPressureScalar() {
		return getDouble(NX_PRESSURE);
	}

	@Override
	public DataNode setPressure(IDataset pressureDataset) {
		return setDataset(NX_PRESSURE, pressureDataset);
	}

	@Override
	public DataNode setPressureScalar(Double pressureValue) {
		return setField(NX_PRESSURE, pressureValue);
	}

	@Override
	public IDataset getChanger_position() {
		return getDataset(NX_CHANGER_POSITION);
	}

	@Override
	public Long getChanger_positionScalar() {
		return getLong(NX_CHANGER_POSITION);
	}

	@Override
	public DataNode setChanger_position(IDataset changer_positionDataset) {
		return setDataset(NX_CHANGER_POSITION, changer_positionDataset);
	}

	@Override
	public DataNode setChanger_positionScalar(Long changer_positionValue) {
		return setField(NX_CHANGER_POSITION, changer_positionValue);
	}

	@Override
	public IDataset getUnit_cell_abc() {
		return getDataset(NX_UNIT_CELL_ABC);
	}

	@Override
	public Double getUnit_cell_abcScalar() {
		return getDouble(NX_UNIT_CELL_ABC);
	}

	@Override
	public DataNode setUnit_cell_abc(IDataset unit_cell_abcDataset) {
		return setDataset(NX_UNIT_CELL_ABC, unit_cell_abcDataset);
	}

	@Override
	public DataNode setUnit_cell_abcScalar(Double unit_cell_abcValue) {
		return setField(NX_UNIT_CELL_ABC, unit_cell_abcValue);
	}

	@Override
	public IDataset getUnit_cell_alphabetagamma() {
		return getDataset(NX_UNIT_CELL_ALPHABETAGAMMA);
	}

	@Override
	public Double getUnit_cell_alphabetagammaScalar() {
		return getDouble(NX_UNIT_CELL_ALPHABETAGAMMA);
	}

	@Override
	public DataNode setUnit_cell_alphabetagamma(IDataset unit_cell_alphabetagammaDataset) {
		return setDataset(NX_UNIT_CELL_ALPHABETAGAMMA, unit_cell_alphabetagammaDataset);
	}

	@Override
	public DataNode setUnit_cell_alphabetagammaScalar(Double unit_cell_alphabetagammaValue) {
		return setField(NX_UNIT_CELL_ALPHABETAGAMMA, unit_cell_alphabetagammaValue);
	}

	@Override
	public IDataset getUnit_cell() {
		return getDataset(NX_UNIT_CELL);
	}

	@Override
	public Double getUnit_cellScalar() {
		return getDouble(NX_UNIT_CELL);
	}

	@Override
	public DataNode setUnit_cell(IDataset unit_cellDataset) {
		return setDataset(NX_UNIT_CELL, unit_cellDataset);
	}

	@Override
	public DataNode setUnit_cellScalar(Double unit_cellValue) {
		return setField(NX_UNIT_CELL, unit_cellValue);
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
	public IDataset getSample_orientation() {
		return getDataset(NX_SAMPLE_ORIENTATION);
	}

	@Override
	public Double getSample_orientationScalar() {
		return getDouble(NX_SAMPLE_ORIENTATION);
	}

	@Override
	public DataNode setSample_orientation(IDataset sample_orientationDataset) {
		return setDataset(NX_SAMPLE_ORIENTATION, sample_orientationDataset);
	}

	@Override
	public DataNode setSample_orientationScalar(Double sample_orientationValue) {
		return setField(NX_SAMPLE_ORIENTATION, sample_orientationValue);
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
	public IDataset getUb_matrix() {
		return getDataset(NX_UB_MATRIX);
	}

	@Override
	public Double getUb_matrixScalar() {
		return getDouble(NX_UB_MATRIX);
	}

	@Override
	public DataNode setUb_matrix(IDataset ub_matrixDataset) {
		return setDataset(NX_UB_MATRIX, ub_matrixDataset);
	}

	@Override
	public DataNode setUb_matrixScalar(Double ub_matrixValue) {
		return setField(NX_UB_MATRIX, ub_matrixValue);
	}

	@Override
	public IDataset getMass() {
		return getDataset(NX_MASS);
	}

	@Override
	public Double getMassScalar() {
		return getDouble(NX_MASS);
	}

	@Override
	public DataNode setMass(IDataset massDataset) {
		return setDataset(NX_MASS, massDataset);
	}

	@Override
	public DataNode setMassScalar(Double massValue) {
		return setField(NX_MASS, massValue);
	}

	@Override
	public IDataset getDensity() {
		return getDataset(NX_DENSITY);
	}

	@Override
	public Double getDensityScalar() {
		return getDouble(NX_DENSITY);
	}

	@Override
	public DataNode setDensity(IDataset densityDataset) {
		return setDataset(NX_DENSITY, densityDataset);
	}

	@Override
	public DataNode setDensityScalar(Double densityValue) {
		return setField(NX_DENSITY, densityValue);
	}

	@Override
	public IDataset getRelative_molecular_mass() {
		return getDataset(NX_RELATIVE_MOLECULAR_MASS);
	}

	@Override
	public Double getRelative_molecular_massScalar() {
		return getDouble(NX_RELATIVE_MOLECULAR_MASS);
	}

	@Override
	public DataNode setRelative_molecular_mass(IDataset relative_molecular_massDataset) {
		return setDataset(NX_RELATIVE_MOLECULAR_MASS, relative_molecular_massDataset);
	}

	@Override
	public DataNode setRelative_molecular_massScalar(Double relative_molecular_massValue) {
		return setField(NX_RELATIVE_MOLECULAR_MASS, relative_molecular_massValue);
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
	public IDataset getSituation() {
		return getDataset(NX_SITUATION);
	}

	@Override
	public String getSituationScalar() {
		return getString(NX_SITUATION);
	}

	@Override
	public DataNode setSituation(IDataset situationDataset) {
		return setDataset(NX_SITUATION, situationDataset);
	}

	@Override
	public DataNode setSituationScalar(String situationValue) {
		return setString(NX_SITUATION, situationValue);
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
	public IDataset getPreparation_date() {
		return getDataset(NX_PREPARATION_DATE);
	}

	@Override
	public Date getPreparation_dateScalar() {
		return getDate(NX_PREPARATION_DATE);
	}

	@Override
	public DataNode setPreparation_date(IDataset preparation_dateDataset) {
		return setDataset(NX_PREPARATION_DATE, preparation_dateDataset);
	}

	@Override
	public DataNode setPreparation_dateScalar(Date preparation_dateValue) {
		return setDate(NX_PREPARATION_DATE, preparation_dateValue);
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
	public NXbeam getBeam() {
		// dataNodeName = NX_BEAM
		return getChild("beam", NXbeam.class);
	}

	@Override
	public void setBeam(NXbeam beamGroup) {
		putChild("beam", beamGroup);
	}

	@Override
	public NXbeam getBeam(String name) {
		return getChild(name, NXbeam.class);
	}

	@Override
	public void setBeam(String name, NXbeam beam) {
		putChild(name, beam);
	}

	@Override
	public Map<String, NXbeam> getAllBeam() {
		return getChildren(NXbeam.class);
	}
	
	@Override
	public void setAllBeam(Map<String, NXbeam> beam) {
		setChildren(beam);
	}

	@Override
	public NXsample_component getSample_componentGroup() {
		// dataNodeName = NX_SAMPLE_COMPONENTGROUP
		return getChild("sample_componentGroup", NXsample_component.class);
	}

	@Override
	public void setSample_componentGroup(NXsample_component sample_componentGroupGroup) {
		putChild("sample_componentGroup", sample_componentGroupGroup);
	}

	@Override
	public NXsample_component getSample_componentGroup(String name) {
		return getChild(name, NXsample_component.class);
	}

	@Override
	public void setSample_componentGroup(String name, NXsample_component sample_componentGroup) {
		putChild(name, sample_componentGroup);
	}

	@Override
	public Map<String, NXsample_component> getAllSample_componentGroup() {
		return getChildren(NXsample_component.class);
	}
	
	@Override
	public void setAllSample_componentGroup(Map<String, NXsample_component> sample_componentGroup) {
		setChildren(sample_componentGroup);
	}

	@Override
	public IDataset getComponent() {
		return getDataset(NX_COMPONENT);
	}

	@Override
	public String getComponentScalar() {
		return getString(NX_COMPONENT);
	}

	@Override
	public DataNode setComponent(IDataset componentDataset) {
		return setDataset(NX_COMPONENT, componentDataset);
	}

	@Override
	public DataNode setComponentScalar(String componentValue) {
		return setString(NX_COMPONENT, componentValue);
	}

	@Override
	public IDataset getSample_component() {
		return getDataset(NX_SAMPLE_COMPONENT);
	}

	@Override
	public String getSample_componentScalar() {
		return getString(NX_SAMPLE_COMPONENT);
	}

	@Override
	public DataNode setSample_component(IDataset sample_componentDataset) {
		return setDataset(NX_SAMPLE_COMPONENT, sample_componentDataset);
	}

	@Override
	public DataNode setSample_componentScalar(String sample_componentValue) {
		return setString(NX_SAMPLE_COMPONENT, sample_componentValue);
	}

	@Override
	public IDataset getConcentration() {
		return getDataset(NX_CONCENTRATION);
	}

	@Override
	public Double getConcentrationScalar() {
		return getDouble(NX_CONCENTRATION);
	}

	@Override
	public DataNode setConcentration(IDataset concentrationDataset) {
		return setDataset(NX_CONCENTRATION, concentrationDataset);
	}

	@Override
	public DataNode setConcentrationScalar(Double concentrationValue) {
		return setField(NX_CONCENTRATION, concentrationValue);
	}

	@Override
	public IDataset getVolume_fraction() {
		return getDataset(NX_VOLUME_FRACTION);
	}

	@Override
	public Double getVolume_fractionScalar() {
		return getDouble(NX_VOLUME_FRACTION);
	}

	@Override
	public DataNode setVolume_fraction(IDataset volume_fractionDataset) {
		return setDataset(NX_VOLUME_FRACTION, volume_fractionDataset);
	}

	@Override
	public DataNode setVolume_fractionScalar(Double volume_fractionValue) {
		return setField(NX_VOLUME_FRACTION, volume_fractionValue);
	}

	@Override
	public IDataset getScattering_length_density() {
		return getDataset(NX_SCATTERING_LENGTH_DENSITY);
	}

	@Override
	public Double getScattering_length_densityScalar() {
		return getDouble(NX_SCATTERING_LENGTH_DENSITY);
	}

	@Override
	public DataNode setScattering_length_density(IDataset scattering_length_densityDataset) {
		return setDataset(NX_SCATTERING_LENGTH_DENSITY, scattering_length_densityDataset);
	}

	@Override
	public DataNode setScattering_length_densityScalar(Double scattering_length_densityValue) {
		return setField(NX_SCATTERING_LENGTH_DENSITY, scattering_length_densityValue);
	}

	@Override
	public IDataset getUnit_cell_class() {
		return getDataset(NX_UNIT_CELL_CLASS);
	}

	@Override
	public String getUnit_cell_classScalar() {
		return getString(NX_UNIT_CELL_CLASS);
	}

	@Override
	public DataNode setUnit_cell_class(IDataset unit_cell_classDataset) {
		return setDataset(NX_UNIT_CELL_CLASS, unit_cell_classDataset);
	}

	@Override
	public DataNode setUnit_cell_classScalar(String unit_cell_classValue) {
		return setString(NX_UNIT_CELL_CLASS, unit_cell_classValue);
	}

	@Override
	public IDataset getSpace_group() {
		return getDataset(NX_SPACE_GROUP);
	}

	@Override
	public String getSpace_groupScalar() {
		return getString(NX_SPACE_GROUP);
	}

	@Override
	public DataNode setSpace_group(IDataset space_groupDataset) {
		return setDataset(NX_SPACE_GROUP, space_groupDataset);
	}

	@Override
	public DataNode setSpace_groupScalar(String space_groupValue) {
		return setString(NX_SPACE_GROUP, space_groupValue);
	}

	@Override
	public IDataset getPoint_group() {
		return getDataset(NX_POINT_GROUP);
	}

	@Override
	public String getPoint_groupScalar() {
		return getString(NX_POINT_GROUP);
	}

	@Override
	public DataNode setPoint_group(IDataset point_groupDataset) {
		return setDataset(NX_POINT_GROUP, point_groupDataset);
	}

	@Override
	public DataNode setPoint_groupScalar(String point_groupValue) {
		return setString(NX_POINT_GROUP, point_groupValue);
	}

	@Override
	public IDataset getPath_length() {
		return getDataset(NX_PATH_LENGTH);
	}

	@Override
	public Double getPath_lengthScalar() {
		return getDouble(NX_PATH_LENGTH);
	}

	@Override
	public DataNode setPath_length(IDataset path_lengthDataset) {
		return setDataset(NX_PATH_LENGTH, path_lengthDataset);
	}

	@Override
	public DataNode setPath_lengthScalar(Double path_lengthValue) {
		return setField(NX_PATH_LENGTH, path_lengthValue);
	}

	@Override
	public IDataset getPath_length_window() {
		return getDataset(NX_PATH_LENGTH_WINDOW);
	}

	@Override
	public Double getPath_length_windowScalar() {
		return getDouble(NX_PATH_LENGTH_WINDOW);
	}

	@Override
	public DataNode setPath_length_window(IDataset path_length_windowDataset) {
		return setDataset(NX_PATH_LENGTH_WINDOW, path_length_windowDataset);
	}

	@Override
	public DataNode setPath_length_windowScalar(Double path_length_windowValue) {
		return setField(NX_PATH_LENGTH_WINDOW, path_length_windowValue);
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
	public NXdata getTransmission() {
		// dataNodeName = NX_TRANSMISSION
		return getChild("transmission", NXdata.class);
	}

	@Override
	public void setTransmission(NXdata transmissionGroup) {
		putChild("transmission", transmissionGroup);
	}

	@Override
	public NXlog getTemperatureLog() {
		// dataNodeName = NX_TEMPERATURELOG
		return getChild("temperatureLog", NXlog.class);
	}

	@Override
	public void setTemperatureLog(NXlog temperatureLogGroup) {
		putChild("temperatureLog", temperatureLogGroup);
	}

	@Override
	@Deprecated
	public NXlog getTemperature_log() {
		// dataNodeName = NX_TEMPERATURE_LOG
		return getChild("temperature_log", NXlog.class);
	}

	@Override
	@Deprecated
	public void setTemperature_log(NXlog temperature_logGroup) {
		putChild("temperature_log", temperature_logGroup);
	}

	@Override
	public NXenvironment getTemperature_env() {
		// dataNodeName = NX_TEMPERATURE_ENV
		return getChild("temperature_env", NXenvironment.class);
	}

	@Override
	public void setTemperature_env(NXenvironment temperature_envGroup) {
		putChild("temperature_env", temperature_envGroup);
	}

	@Override
	public NXlog getMagnetic_fieldLog() {
		// dataNodeName = NX_MAGNETIC_FIELDLOG
		return getChild("magnetic_fieldLog", NXlog.class);
	}

	@Override
	public void setMagnetic_fieldLog(NXlog magnetic_fieldLogGroup) {
		putChild("magnetic_fieldLog", magnetic_fieldLogGroup);
	}

	@Override
	@Deprecated
	public NXlog getMagnetic_field_log() {
		// dataNodeName = NX_MAGNETIC_FIELD_LOG
		return getChild("magnetic_field_log", NXlog.class);
	}

	@Override
	@Deprecated
	public void setMagnetic_field_log(NXlog magnetic_field_logGroup) {
		putChild("magnetic_field_log", magnetic_field_logGroup);
	}

	@Override
	public NXenvironment getMagnetic_field_env() {
		// dataNodeName = NX_MAGNETIC_FIELD_ENV
		return getChild("magnetic_field_env", NXenvironment.class);
	}

	@Override
	public void setMagnetic_field_env(NXenvironment magnetic_field_envGroup) {
		putChild("magnetic_field_env", magnetic_field_envGroup);
	}

	@Override
	public IDataset getExternal_dac() {
		return getDataset(NX_EXTERNAL_DAC);
	}

	@Override
	public Double getExternal_dacScalar() {
		return getDouble(NX_EXTERNAL_DAC);
	}

	@Override
	public DataNode setExternal_dac(IDataset external_dacDataset) {
		return setDataset(NX_EXTERNAL_DAC, external_dacDataset);
	}

	@Override
	public DataNode setExternal_dacScalar(Double external_dacValue) {
		return setField(NX_EXTERNAL_DAC, external_dacValue);
	}

	@Override
	public NXlog getExternal_adc() {
		// dataNodeName = NX_EXTERNAL_ADC
		return getChild("external_adc", NXlog.class);
	}

	@Override
	public void setExternal_adc(NXlog external_adcGroup) {
		putChild("external_adc", external_adcGroup);
	}

	@Override
	public IDataset getShort_title() {
		return getDataset(NX_SHORT_TITLE);
	}

	@Override
	public String getShort_titleScalar() {
		return getString(NX_SHORT_TITLE);
	}

	@Override
	public DataNode setShort_title(IDataset short_titleDataset) {
		return setDataset(NX_SHORT_TITLE, short_titleDataset);
	}

	@Override
	public DataNode setShort_titleScalar(String short_titleValue) {
		return setString(NX_SHORT_TITLE, short_titleValue);
	}

	@Override
	public IDataset getRotation_angle() {
		return getDataset(NX_ROTATION_ANGLE);
	}

	@Override
	public Double getRotation_angleScalar() {
		return getDouble(NX_ROTATION_ANGLE);
	}

	@Override
	public DataNode setRotation_angle(IDataset rotation_angleDataset) {
		return setDataset(NX_ROTATION_ANGLE, rotation_angleDataset);
	}

	@Override
	public DataNode setRotation_angleScalar(Double rotation_angleValue) {
		return setField(NX_ROTATION_ANGLE, rotation_angleValue);
	}

	@Override
	public IDataset getX_translation() {
		return getDataset(NX_X_TRANSLATION);
	}

	@Override
	public Double getX_translationScalar() {
		return getDouble(NX_X_TRANSLATION);
	}

	@Override
	public DataNode setX_translation(IDataset x_translationDataset) {
		return setDataset(NX_X_TRANSLATION, x_translationDataset);
	}

	@Override
	public DataNode setX_translationScalar(Double x_translationValue) {
		return setField(NX_X_TRANSLATION, x_translationValue);
	}

	@Override
	public IDataset getDistance() {
		return getDataset(NX_DISTANCE);
	}

	@Override
	public Double getDistanceScalar() {
		return getDouble(NX_DISTANCE);
	}

	@Override
	public DataNode setDistance(IDataset distanceDataset) {
		return setDataset(NX_DISTANCE, distanceDataset);
	}

	@Override
	public DataNode setDistanceScalar(Double distanceValue) {
		return setField(NX_DISTANCE, distanceValue);
	}

	@Override
	public NXpositioner getPositioner() {
		// dataNodeName = NX_POSITIONER
		return getChild("positioner", NXpositioner.class);
	}

	@Override
	public void setPositioner(NXpositioner positionerGroup) {
		putChild("positioner", positionerGroup);
	}

	@Override
	public NXpositioner getPositioner(String name) {
		return getChild(name, NXpositioner.class);
	}

	@Override
	public void setPositioner(String name, NXpositioner positioner) {
		putChild(name, positioner);
	}

	@Override
	public Map<String, NXpositioner> getAllPositioner() {
		return getChildren(NXpositioner.class);
	}
	
	@Override
	public void setAllPositioner(Map<String, NXpositioner> positioner) {
		setChildren(positioner);
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
