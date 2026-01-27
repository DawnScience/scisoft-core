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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Radiation source emitting a beam.
 * Examples include particle sources (electrons, neutrons, protons) or sources for electromagnetic radiation (photons).
 * This base class can also be used to describe neutron or x-ray storage ring/facilities.

 */
public class NXsourceImpl extends NXcomponentImpl implements NXsource {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_APERTURE,
		NexusBaseClass.NX_ELECTROMAGNETIC_LENS,
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_FABRICATION,
		NexusBaseClass.NX_OFF_GEOMETRY,
		NexusBaseClass.NX_DATA);

	public NXsourceImpl() {
		super();
	}

	public NXsourceImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXsource.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SOURCE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDistance() {
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
	public Dataset getName() {
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
	public String getNameAttributeShort_name() {
		return getAttrString(NX_NAME, NX_NAME_ATTRIBUTE_SHORT_NAME);
	}

	@Override
	public void setNameAttributeShort_name(String short_nameValue) {
		setAttribute(NX_NAME, NX_NAME_ATTRIBUTE_SHORT_NAME, short_nameValue);
	}

	@Override
	public Dataset getType() {
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
	public Dataset getProbe() {
		return getDataset(NX_PROBE);
	}

	@Override
	public String getProbeScalar() {
		return getString(NX_PROBE);
	}

	@Override
	public DataNode setProbe(IDataset probeDataset) {
		return setDataset(NX_PROBE, probeDataset);
	}

	@Override
	public DataNode setProbeScalar(String probeValue) {
		return setString(NX_PROBE, probeValue);
	}

	@Override
	public Dataset getPower() {
		return getDataset(NX_POWER);
	}

	@Override
	public Double getPowerScalar() {
		return getDouble(NX_POWER);
	}

	@Override
	public DataNode setPower(IDataset powerDataset) {
		return setDataset(NX_POWER, powerDataset);
	}

	@Override
	public DataNode setPowerScalar(Double powerValue) {
		return setField(NX_POWER, powerValue);
	}

	@Override
	public Dataset getEmittance_x() {
		return getDataset(NX_EMITTANCE_X);
	}

	@Override
	public Double getEmittance_xScalar() {
		return getDouble(NX_EMITTANCE_X);
	}

	@Override
	public DataNode setEmittance_x(IDataset emittance_xDataset) {
		return setDataset(NX_EMITTANCE_X, emittance_xDataset);
	}

	@Override
	public DataNode setEmittance_xScalar(Double emittance_xValue) {
		return setField(NX_EMITTANCE_X, emittance_xValue);
	}

	@Override
	public Dataset getEmittance_y() {
		return getDataset(NX_EMITTANCE_Y);
	}

	@Override
	public Double getEmittance_yScalar() {
		return getDouble(NX_EMITTANCE_Y);
	}

	@Override
	public DataNode setEmittance_y(IDataset emittance_yDataset) {
		return setDataset(NX_EMITTANCE_Y, emittance_yDataset);
	}

	@Override
	public DataNode setEmittance_yScalar(Double emittance_yValue) {
		return setField(NX_EMITTANCE_Y, emittance_yValue);
	}

	@Override
	public Dataset getSigma_x() {
		return getDataset(NX_SIGMA_X);
	}

	@Override
	public Double getSigma_xScalar() {
		return getDouble(NX_SIGMA_X);
	}

	@Override
	public DataNode setSigma_x(IDataset sigma_xDataset) {
		return setDataset(NX_SIGMA_X, sigma_xDataset);
	}

	@Override
	public DataNode setSigma_xScalar(Double sigma_xValue) {
		return setField(NX_SIGMA_X, sigma_xValue);
	}

	@Override
	public Dataset getSigma_y() {
		return getDataset(NX_SIGMA_Y);
	}

	@Override
	public Double getSigma_yScalar() {
		return getDouble(NX_SIGMA_Y);
	}

	@Override
	public DataNode setSigma_y(IDataset sigma_yDataset) {
		return setDataset(NX_SIGMA_Y, sigma_yDataset);
	}

	@Override
	public DataNode setSigma_yScalar(Double sigma_yValue) {
		return setField(NX_SIGMA_Y, sigma_yValue);
	}

	@Override
	public Dataset getFlux() {
		return getDataset(NX_FLUX);
	}

	@Override
	public Double getFluxScalar() {
		return getDouble(NX_FLUX);
	}

	@Override
	public DataNode setFlux(IDataset fluxDataset) {
		return setDataset(NX_FLUX, fluxDataset);
	}

	@Override
	public DataNode setFluxScalar(Double fluxValue) {
		return setField(NX_FLUX, fluxValue);
	}

	@Override
	public Dataset getEnergy() {
		return getDataset(NX_ENERGY);
	}

	@Override
	public Double getEnergyScalar() {
		return getDouble(NX_ENERGY);
	}

	@Override
	public DataNode setEnergy(IDataset energyDataset) {
		return setDataset(NX_ENERGY, energyDataset);
	}

	@Override
	public DataNode setEnergyScalar(Double energyValue) {
		return setField(NX_ENERGY, energyValue);
	}

	@Override
	public Dataset getCurrent() {
		return getDataset(NX_CURRENT);
	}

	@Override
	public Double getCurrentScalar() {
		return getDouble(NX_CURRENT);
	}

	@Override
	public DataNode setCurrent(IDataset currentDataset) {
		return setDataset(NX_CURRENT, currentDataset);
	}

	@Override
	public DataNode setCurrentScalar(Double currentValue) {
		return setField(NX_CURRENT, currentValue);
	}

	@Override
	public Dataset getVoltage() {
		return getDataset(NX_VOLTAGE);
	}

	@Override
	public Double getVoltageScalar() {
		return getDouble(NX_VOLTAGE);
	}

	@Override
	public DataNode setVoltage(IDataset voltageDataset) {
		return setDataset(NX_VOLTAGE, voltageDataset);
	}

	@Override
	public DataNode setVoltageScalar(Double voltageValue) {
		return setField(NX_VOLTAGE, voltageValue);
	}

	@Override
	public Dataset getFrequency() {
		return getDataset(NX_FREQUENCY);
	}

	@Override
	public Double getFrequencyScalar() {
		return getDouble(NX_FREQUENCY);
	}

	@Override
	public DataNode setFrequency(IDataset frequencyDataset) {
		return setDataset(NX_FREQUENCY, frequencyDataset);
	}

	@Override
	public DataNode setFrequencyScalar(Double frequencyValue) {
		return setField(NX_FREQUENCY, frequencyValue);
	}

	@Override
	public Dataset getPeriod() {
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
	public Dataset getTarget_material() {
		return getDataset(NX_TARGET_MATERIAL);
	}

	@Override
	public String getTarget_materialScalar() {
		return getString(NX_TARGET_MATERIAL);
	}

	@Override
	public DataNode setTarget_material(IDataset target_materialDataset) {
		return setDataset(NX_TARGET_MATERIAL, target_materialDataset);
	}

	@Override
	public DataNode setTarget_materialScalar(String target_materialValue) {
		return setString(NX_TARGET_MATERIAL, target_materialValue);
	}

	@Override
	public NXnote getNotes() {
		// dataNodeName = NX_NOTES
		return getChild("notes", NXnote.class);
	}

	@Override
	public void setNotes(NXnote notesGroup) {
		putChild("notes", notesGroup);
	}

	@Override
	public NXdata getBunch_pattern() {
		// dataNodeName = NX_BUNCH_PATTERN
		return getChild("bunch_pattern", NXdata.class);
	}

	@Override
	public void setBunch_pattern(NXdata bunch_patternGroup) {
		putChild("bunch_pattern", bunch_patternGroup);
	}

	@Override
	public Dataset getNumber_of_bunches() {
		return getDataset(NX_NUMBER_OF_BUNCHES);
	}

	@Override
	public Long getNumber_of_bunchesScalar() {
		return getLong(NX_NUMBER_OF_BUNCHES);
	}

	@Override
	public DataNode setNumber_of_bunches(IDataset number_of_bunchesDataset) {
		return setDataset(NX_NUMBER_OF_BUNCHES, number_of_bunchesDataset);
	}

	@Override
	public DataNode setNumber_of_bunchesScalar(Long number_of_bunchesValue) {
		return setField(NX_NUMBER_OF_BUNCHES, number_of_bunchesValue);
	}

	@Override
	public Dataset getBunch_length() {
		return getDataset(NX_BUNCH_LENGTH);
	}

	@Override
	public Double getBunch_lengthScalar() {
		return getDouble(NX_BUNCH_LENGTH);
	}

	@Override
	public DataNode setBunch_length(IDataset bunch_lengthDataset) {
		return setDataset(NX_BUNCH_LENGTH, bunch_lengthDataset);
	}

	@Override
	public DataNode setBunch_lengthScalar(Double bunch_lengthValue) {
		return setField(NX_BUNCH_LENGTH, bunch_lengthValue);
	}

	@Override
	public Dataset getBunch_distance() {
		return getDataset(NX_BUNCH_DISTANCE);
	}

	@Override
	public Double getBunch_distanceScalar() {
		return getDouble(NX_BUNCH_DISTANCE);
	}

	@Override
	public DataNode setBunch_distance(IDataset bunch_distanceDataset) {
		return setDataset(NX_BUNCH_DISTANCE, bunch_distanceDataset);
	}

	@Override
	public DataNode setBunch_distanceScalar(Double bunch_distanceValue) {
		return setField(NX_BUNCH_DISTANCE, bunch_distanceValue);
	}

	@Override
	public Dataset getPulse_width() {
		return getDataset(NX_PULSE_WIDTH);
	}

	@Override
	public Double getPulse_widthScalar() {
		return getDouble(NX_PULSE_WIDTH);
	}

	@Override
	public DataNode setPulse_width(IDataset pulse_widthDataset) {
		return setDataset(NX_PULSE_WIDTH, pulse_widthDataset);
	}

	@Override
	public DataNode setPulse_widthScalar(Double pulse_widthValue) {
		return setField(NX_PULSE_WIDTH, pulse_widthValue);
	}

	@Override
	public NXdata getPulse_shape() {
		// dataNodeName = NX_PULSE_SHAPE
		return getChild("pulse_shape", NXdata.class);
	}

	@Override
	public void setPulse_shape(NXdata pulse_shapeGroup) {
		putChild("pulse_shape", pulse_shapeGroup);
	}

	@Override
	public Dataset getMode() {
		return getDataset(NX_MODE);
	}

	@Override
	public String getModeScalar() {
		return getString(NX_MODE);
	}

	@Override
	public DataNode setMode(IDataset modeDataset) {
		return setDataset(NX_MODE, modeDataset);
	}

	@Override
	public DataNode setModeScalar(String modeValue) {
		return setString(NX_MODE, modeValue);
	}

	@Override
	public Dataset getTop_up() {
		return getDataset(NX_TOP_UP);
	}

	@Override
	public Boolean getTop_upScalar() {
		return getBoolean(NX_TOP_UP);
	}

	@Override
	public DataNode setTop_up(IDataset top_upDataset) {
		return setDataset(NX_TOP_UP, top_upDataset);
	}

	@Override
	public DataNode setTop_upScalar(Boolean top_upValue) {
		return setField(NX_TOP_UP, top_upValue);
	}

	@Override
	public Dataset getLast_fill() {
		return getDataset(NX_LAST_FILL);
	}

	@Override
	public Number getLast_fillScalar() {
		return getNumber(NX_LAST_FILL);
	}

	@Override
	public DataNode setLast_fill(IDataset last_fillDataset) {
		return setDataset(NX_LAST_FILL, last_fillDataset);
	}

	@Override
	public DataNode setLast_fillScalar(Number last_fillValue) {
		return setField(NX_LAST_FILL, last_fillValue);
	}

	@Override
	public Date getLast_fillAttributeTime() {
		return getAttrDate(NX_LAST_FILL, NX_LAST_FILL_ATTRIBUTE_TIME);
	}

	@Override
	public void setLast_fillAttributeTime(Date timeValue) {
		setAttribute(NX_LAST_FILL, NX_LAST_FILL_ATTRIBUTE_TIME, timeValue);
	}

	@Override
	public Dataset getWavelength() {
		return getDataset(NX_WAVELENGTH);
	}

	@Override
	public Double getWavelengthScalar() {
		return getDouble(NX_WAVELENGTH);
	}

	@Override
	public DataNode setWavelength(IDataset wavelengthDataset) {
		return setDataset(NX_WAVELENGTH, wavelengthDataset);
	}

	@Override
	public DataNode setWavelengthScalar(Double wavelengthValue) {
		return setField(NX_WAVELENGTH, wavelengthValue);
	}

	@Override
	public Dataset getPulse_energy() {
		return getDataset(NX_PULSE_ENERGY);
	}

	@Override
	public Double getPulse_energyScalar() {
		return getDouble(NX_PULSE_ENERGY);
	}

	@Override
	public DataNode setPulse_energy(IDataset pulse_energyDataset) {
		return setDataset(NX_PULSE_ENERGY, pulse_energyDataset);
	}

	@Override
	public DataNode setPulse_energyScalar(Double pulse_energyValue) {
		return setField(NX_PULSE_ENERGY, pulse_energyValue);
	}

	@Override
	public Dataset getPeak_power() {
		return getDataset(NX_PEAK_POWER);
	}

	@Override
	public Double getPeak_powerScalar() {
		return getDouble(NX_PEAK_POWER);
	}

	@Override
	public DataNode setPeak_power(IDataset peak_powerDataset) {
		return setDataset(NX_PEAK_POWER, peak_powerDataset);
	}

	@Override
	public DataNode setPeak_powerScalar(Double peak_powerValue) {
		return setField(NX_PEAK_POWER, peak_powerValue);
	}

	@Override
	public Dataset getAnode_material() {
		return getDataset(NX_ANODE_MATERIAL);
	}

	@Override
	public String getAnode_materialScalar() {
		return getString(NX_ANODE_MATERIAL);
	}

	@Override
	public DataNode setAnode_material(IDataset anode_materialDataset) {
		return setDataset(NX_ANODE_MATERIAL, anode_materialDataset);
	}

	@Override
	public DataNode setAnode_materialScalar(String anode_materialValue) {
		return setString(NX_ANODE_MATERIAL, anode_materialValue);
	}

	@Override
	public Dataset getFilament_current() {
		return getDataset(NX_FILAMENT_CURRENT);
	}

	@Override
	public Double getFilament_currentScalar() {
		return getDouble(NX_FILAMENT_CURRENT);
	}

	@Override
	public DataNode setFilament_current(IDataset filament_currentDataset) {
		return setDataset(NX_FILAMENT_CURRENT, filament_currentDataset);
	}

	@Override
	public DataNode setFilament_currentScalar(Double filament_currentValue) {
		return setField(NX_FILAMENT_CURRENT, filament_currentValue);
	}

	@Override
	public Dataset getEmission_current() {
		return getDataset(NX_EMISSION_CURRENT);
	}

	@Override
	public Double getEmission_currentScalar() {
		return getDouble(NX_EMISSION_CURRENT);
	}

	@Override
	public DataNode setEmission_current(IDataset emission_currentDataset) {
		return setDataset(NX_EMISSION_CURRENT, emission_currentDataset);
	}

	@Override
	public DataNode setEmission_currentScalar(Double emission_currentValue) {
		return setField(NX_EMISSION_CURRENT, emission_currentValue);
	}

	@Override
	public Dataset getGas_pressure() {
		return getDataset(NX_GAS_PRESSURE);
	}

	@Override
	public Double getGas_pressureScalar() {
		return getDouble(NX_GAS_PRESSURE);
	}

	@Override
	public DataNode setGas_pressure(IDataset gas_pressureDataset) {
		return setDataset(NX_GAS_PRESSURE, gas_pressureDataset);
	}

	@Override
	public DataNode setGas_pressureScalar(Double gas_pressureValue) {
		return setField(NX_GAS_PRESSURE, gas_pressureValue);
	}

	@Override
	public Dataset getPrevious_source() {
		return getDataset(NX_PREVIOUS_SOURCE);
	}

	@Override
	public String getPrevious_sourceScalar() {
		return getString(NX_PREVIOUS_SOURCE);
	}

	@Override
	public DataNode setPrevious_source(IDataset previous_sourceDataset) {
		return setDataset(NX_PREVIOUS_SOURCE, previous_sourceDataset);
	}

	@Override
	public DataNode setPrevious_sourceScalar(String previous_sourceValue) {
		return setString(NX_PREVIOUS_SOURCE, previous_sourceValue);
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
	public NXaperture getAperture() {
		// dataNodeName = NX_APERTURE
		return getChild("aperture", NXaperture.class);
	}

	@Override
	public void setAperture(NXaperture apertureGroup) {
		putChild("aperture", apertureGroup);
	}

	@Override
	public NXaperture getAperture(String name) {
		return getChild(name, NXaperture.class);
	}

	@Override
	public void setAperture(String name, NXaperture aperture) {
		putChild(name, aperture);
	}

	@Override
	public Map<String, NXaperture> getAllAperture() {
		return getChildren(NXaperture.class);
	}

	@Override
	public void setAllAperture(Map<String, NXaperture> aperture) {
		setChildren(aperture);
	}

	@Override
	public NXelectromagnetic_lens getElectromagnetic_lens() {
		// dataNodeName = NX_ELECTROMAGNETIC_LENS
		return getChild("electromagnetic_lens", NXelectromagnetic_lens.class);
	}

	@Override
	public void setElectromagnetic_lens(NXelectromagnetic_lens electromagnetic_lensGroup) {
		putChild("electromagnetic_lens", electromagnetic_lensGroup);
	}

	@Override
	public NXelectromagnetic_lens getElectromagnetic_lens(String name) {
		return getChild(name, NXelectromagnetic_lens.class);
	}

	@Override
	public void setElectromagnetic_lens(String name, NXelectromagnetic_lens electromagnetic_lens) {
		putChild(name, electromagnetic_lens);
	}

	@Override
	public Map<String, NXelectromagnetic_lens> getAllElectromagnetic_lens() {
		return getChildren(NXelectromagnetic_lens.class);
	}

	@Override
	public void setAllElectromagnetic_lens(Map<String, NXelectromagnetic_lens> electromagnetic_lens) {
		setChildren(electromagnetic_lens);
	}

	@Override
	public NXdeflector getDeflector() {
		// dataNodeName = NX_DEFLECTOR
		return getChild("deflector", NXdeflector.class);
	}

	@Override
	public void setDeflector(NXdeflector deflectorGroup) {
		putChild("deflector", deflectorGroup);
	}

	@Override
	public NXdeflector getDeflector(String name) {
		return getChild(name, NXdeflector.class);
	}

	@Override
	public void setDeflector(String name, NXdeflector deflector) {
		putChild(name, deflector);
	}

	@Override
	public Map<String, NXdeflector> getAllDeflector() {
		return getChildren(NXdeflector.class);
	}

	@Override
	public void setAllDeflector(Map<String, NXdeflector> deflector) {
		setChildren(deflector);
	}

	@Override
	public NXfabrication getFabrication() {
		// dataNodeName = NX_FABRICATION
		return getChild("fabrication", NXfabrication.class);
	}

	@Override
	public void setFabrication(NXfabrication fabricationGroup) {
		putChild("fabrication", fabricationGroup);
	}

	@Override
	public NXfabrication getFabrication(String name) {
		return getChild(name, NXfabrication.class);
	}

	@Override
	public void setFabrication(String name, NXfabrication fabrication) {
		putChild(name, fabrication);
	}

	@Override
	public Map<String, NXfabrication> getAllFabrication() {
		return getChildren(NXfabrication.class);
	}

	@Override
	public void setAllFabrication(Map<String, NXfabrication> fabrication) {
		setChildren(fabrication);
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
	public NXdata getDistribution() {
		// dataNodeName = NX_DISTRIBUTION
		return getChild("distribution", NXdata.class);
	}

	@Override
	public void setDistribution(NXdata distributionGroup) {
		putChild("distribution", distributionGroup);
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

}
