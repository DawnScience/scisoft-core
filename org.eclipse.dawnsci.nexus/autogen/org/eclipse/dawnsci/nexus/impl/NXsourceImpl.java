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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * The neutron or x-ray storage ring/facility.
 * 
 */
public class NXsourceImpl extends NXobjectImpl implements NXsource {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_GEOMETRY,
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
	public String getNameAttributeShort_name() {
		return getAttrString(NX_NAME, NX_NAME_ATTRIBUTE_SHORT_NAME);
	}

	@Override
	public void setNameAttributeShort_name(String short_nameValue) {
		setAttribute(NX_NAME, NX_NAME_ATTRIBUTE_SHORT_NAME, short_nameValue);
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
	public IDataset getProbe() {
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
	public IDataset getPower() {
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
	public IDataset getEmittance_x() {
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
	public IDataset getEmittance_y() {
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
	public IDataset getSigma_x() {
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
	public IDataset getSigma_y() {
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
	public IDataset getFlux() {
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
	public IDataset getEnergy() {
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
	public IDataset getCurrent() {
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
	public IDataset getVoltage() {
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
	public IDataset getFrequency() {
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
	public IDataset getTarget_material() {
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
	public IDataset getNumber_of_bunches() {
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
	public IDataset getBunch_length() {
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
	public IDataset getBunch_distance() {
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
	public IDataset getPulse_width() {
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
	public IDataset getMode() {
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
	public IDataset getTop_up() {
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
	public IDataset getLast_fill() {
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
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
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
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
