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
 * A beam splitter, i.e. a device splitting the light into two or more beams.
 * Information about types and properties of beam splitters is provided e.g.
 * [here](https://www.rp-photonics.com/beam_splitters.html).
 * Use two or more NXbeam_paths to describe the beam paths after the beam
 * splitter. In the dependency chain of the new beam paths, the first elements
 * each point to this beam splitter, as this is the previous element.

 */
public class NXbeam_splitterImpl extends NXobjectImpl implements NXbeam_splitter {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SHAPE,
		NexusBaseClass.NX_SAMPLE,
		NexusBaseClass.NX_SAMPLE);

	public NXbeam_splitterImpl() {
		super();
	}

	public NXbeam_splitterImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXbeam_splitter.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_BEAM_SPLITTER;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public IDataset getOther_type() {
		return getDataset(NX_OTHER_TYPE);
	}

	@Override
	public String getOther_typeScalar() {
		return getString(NX_OTHER_TYPE);
	}

	@Override
	public DataNode setOther_type(IDataset other_typeDataset) {
		return setDataset(NX_OTHER_TYPE, other_typeDataset);
	}

	@Override
	public DataNode setOther_typeScalar(String other_typeValue) {
		return setString(NX_OTHER_TYPE, other_typeValue);
	}

	@Override
	public IDataset getPolarizing() {
		return getDataset(NX_POLARIZING);
	}

	@Override
	public Boolean getPolarizingScalar() {
		return getBoolean(NX_POLARIZING);
	}

	@Override
	public DataNode setPolarizing(IDataset polarizingDataset) {
		return setDataset(NX_POLARIZING, polarizingDataset);
	}

	@Override
	public DataNode setPolarizingScalar(Boolean polarizingValue) {
		return setField(NX_POLARIZING, polarizingValue);
	}

	@Override
	public IDataset getMultiple_outputs() {
		return getDataset(NX_MULTIPLE_OUTPUTS);
	}

	@Override
	public Boolean getMultiple_outputsScalar() {
		return getBoolean(NX_MULTIPLE_OUTPUTS);
	}

	@Override
	public DataNode setMultiple_outputs(IDataset multiple_outputsDataset) {
		return setDataset(NX_MULTIPLE_OUTPUTS, multiple_outputsDataset);
	}

	@Override
	public DataNode setMultiple_outputsScalar(Boolean multiple_outputsValue) {
		return setField(NX_MULTIPLE_OUTPUTS, multiple_outputsValue);
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
	public NXshape getShape(String name) {
		return getChild(name, NXshape.class);
	}

	@Override
	public void setShape(String name, NXshape shape) {
		putChild(name, shape);
	}

	@Override
	public Map<String, NXshape> getAllShape() {
		return getChildren(NXshape.class);
	}

	@Override
	public void setAllShape(Map<String, NXshape> shape) {
		setChildren(shape);
	}
	// Unprocessed group: sketch

	@Override
	public IDataset getSplitting_ratio() {
		return getDataset(NX_SPLITTING_RATIO);
	}

	@Override
	public Number getSplitting_ratioScalar() {
		return getNumber(NX_SPLITTING_RATIO);
	}

	@Override
	public DataNode setSplitting_ratio(IDataset splitting_ratioDataset) {
		return setDataset(NX_SPLITTING_RATIO, splitting_ratioDataset);
	}

	@Override
	public DataNode setSplitting_ratioScalar(Number splitting_ratioValue) {
		return setField(NX_SPLITTING_RATIO, splitting_ratioValue);
	}

	@Override
	public IDataset getClear_aperture() {
		return getDataset(NX_CLEAR_APERTURE);
	}

	@Override
	public Double getClear_apertureScalar() {
		return getDouble(NX_CLEAR_APERTURE);
	}

	@Override
	public DataNode setClear_aperture(IDataset clear_apertureDataset) {
		return setDataset(NX_CLEAR_APERTURE, clear_apertureDataset);
	}

	@Override
	public DataNode setClear_apertureScalar(Double clear_apertureValue) {
		return setField(NX_CLEAR_APERTURE, clear_apertureValue);
	}

	@Override
	public NXsample getSubstrate() {
		// dataNodeName = NX_SUBSTRATE
		return getChild("substrate", NXsample.class);
	}

	@Override
	public void setSubstrate(NXsample substrateGroup) {
		putChild("substrate", substrateGroup);
	}

	@Override
	public NXsample getCoating() {
		// dataNodeName = NX_COATING
		return getChild("coating", NXsample.class);
	}

	@Override
	public void setCoating(NXsample coatingGroup) {
		putChild("coating", coatingGroup);
	}

	@Override
	public IDataset getWavelength_range() {
		return getDataset(NX_WAVELENGTH_RANGE);
	}

	@Override
	public Double getWavelength_rangeScalar() {
		return getDouble(NX_WAVELENGTH_RANGE);
	}

	@Override
	public DataNode setWavelength_range(IDataset wavelength_rangeDataset) {
		return setDataset(NX_WAVELENGTH_RANGE, wavelength_rangeDataset);
	}

	@Override
	public DataNode setWavelength_rangeScalar(Double wavelength_rangeValue) {
		return setField(NX_WAVELENGTH_RANGE, wavelength_rangeValue);
	}

	@Override
	public IDataset getOptical_loss() {
		return getDataset(NX_OPTICAL_LOSS);
	}

	@Override
	public Number getOptical_lossScalar() {
		return getNumber(NX_OPTICAL_LOSS);
	}

	@Override
	public DataNode setOptical_loss(IDataset optical_lossDataset) {
		return setDataset(NX_OPTICAL_LOSS, optical_lossDataset);
	}

	@Override
	public DataNode setOptical_lossScalar(Number optical_lossValue) {
		return setField(NX_OPTICAL_LOSS, optical_lossValue);
	}

	@Override
	public IDataset getIncident_angle() {
		return getDataset(NX_INCIDENT_ANGLE);
	}

	@Override
	public Number getIncident_angleScalar() {
		return getNumber(NX_INCIDENT_ANGLE);
	}

	@Override
	public DataNode setIncident_angle(IDataset incident_angleDataset) {
		return setDataset(NX_INCIDENT_ANGLE, incident_angleDataset);
	}

	@Override
	public DataNode setIncident_angleScalar(Number incident_angleValue) {
		return setField(NX_INCIDENT_ANGLE, incident_angleValue);
	}

	@Override
	public IDataset getDeflection_angle() {
		return getDataset(NX_DEFLECTION_ANGLE);
	}

	@Override
	public Number getDeflection_angleScalar() {
		return getNumber(NX_DEFLECTION_ANGLE);
	}

	@Override
	public DataNode setDeflection_angle(IDataset deflection_angleDataset) {
		return setDataset(NX_DEFLECTION_ANGLE, deflection_angleDataset);
	}

	@Override
	public DataNode setDeflection_angleScalar(Number deflection_angleValue) {
		return setField(NX_DEFLECTION_ANGLE, deflection_angleValue);
	}

	@Override
	public IDataset getAoi_range() {
		return getDataset(NX_AOI_RANGE);
	}

	@Override
	public Number getAoi_rangeScalar() {
		return getNumber(NX_AOI_RANGE);
	}

	@Override
	public DataNode setAoi_range(IDataset aoi_rangeDataset) {
		return setDataset(NX_AOI_RANGE, aoi_rangeDataset);
	}

	@Override
	public DataNode setAoi_rangeScalar(Number aoi_rangeValue) {
		return setField(NX_AOI_RANGE, aoi_rangeValue);
	}

	@Override
	public IDataset getReflectance() {
		return getDataset(NX_REFLECTANCE);
	}

	@Override
	public Double getReflectanceScalar() {
		return getDouble(NX_REFLECTANCE);
	}

	@Override
	public DataNode setReflectance(IDataset reflectanceDataset) {
		return setDataset(NX_REFLECTANCE, reflectanceDataset);
	}

	@Override
	public DataNode setReflectanceScalar(Double reflectanceValue) {
		return setField(NX_REFLECTANCE, reflectanceValue);
	}

	@Override
	public IDataset getTransmission() {
		return getDataset(NX_TRANSMISSION);
	}

	@Override
	public Double getTransmissionScalar() {
		return getDouble(NX_TRANSMISSION);
	}

	@Override
	public DataNode setTransmission(IDataset transmissionDataset) {
		return setDataset(NX_TRANSMISSION, transmissionDataset);
	}

	@Override
	public DataNode setTransmissionScalar(Double transmissionValue) {
		return setField(NX_TRANSMISSION, transmissionValue);
	}

}
