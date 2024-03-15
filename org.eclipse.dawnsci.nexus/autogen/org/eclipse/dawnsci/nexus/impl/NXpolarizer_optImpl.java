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
 * An optical polarizer.
 * Information on the properties of polarizer is provided e.g.
 * [here](https://www.rp-photonics.com/polarizers.html).

 */
public class NXpolarizer_optImpl extends NXobjectImpl implements NXpolarizer_opt {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SHAPE,
		NexusBaseClass.NX_SAMPLE,
		NexusBaseClass.NX_SAMPLE);

	public NXpolarizer_optImpl() {
		super();
	}

	public NXpolarizer_optImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpolarizer_opt.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_POLARIZER_OPT;
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
	public IDataset getType_other() {
		return getDataset(NX_TYPE_OTHER);
	}

	@Override
	public String getType_otherScalar() {
		return getString(NX_TYPE_OTHER);
	}

	@Override
	public DataNode setType_other(IDataset type_otherDataset) {
		return setDataset(NX_TYPE_OTHER, type_otherDataset);
	}

	@Override
	public DataNode setType_otherScalar(String type_otherValue) {
		return setString(NX_TYPE_OTHER, type_otherValue);
	}

	@Override
	public IDataset getPolarizer_angle() {
		return getDataset(NX_POLARIZER_ANGLE);
	}

	@Override
	public Number getPolarizer_angleScalar() {
		return getNumber(NX_POLARIZER_ANGLE);
	}

	@Override
	public DataNode setPolarizer_angle(IDataset polarizer_angleDataset) {
		return setDataset(NX_POLARIZER_ANGLE, polarizer_angleDataset);
	}

	@Override
	public DataNode setPolarizer_angleScalar(Number polarizer_angleValue) {
		return setField(NX_POLARIZER_ANGLE, polarizer_angleValue);
	}

	@Override
	public IDataset getAcceptance_angle() {
		return getDataset(NX_ACCEPTANCE_ANGLE);
	}

	@Override
	public Number getAcceptance_angleScalar() {
		return getNumber(NX_ACCEPTANCE_ANGLE);
	}

	@Override
	public DataNode setAcceptance_angle(IDataset acceptance_angleDataset) {
		return setDataset(NX_ACCEPTANCE_ANGLE, acceptance_angleDataset);
	}

	@Override
	public DataNode setAcceptance_angleScalar(Number acceptance_angleValue) {
		return setField(NX_ACCEPTANCE_ANGLE, acceptance_angleValue);
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
	public IDataset getExtinction_ratio() {
		return getDataset(NX_EXTINCTION_RATIO);
	}

	@Override
	public Double getExtinction_ratioScalar() {
		return getDouble(NX_EXTINCTION_RATIO);
	}

	@Override
	public DataNode setExtinction_ratio(IDataset extinction_ratioDataset) {
		return setDataset(NX_EXTINCTION_RATIO, extinction_ratioDataset);
	}

	@Override
	public DataNode setExtinction_ratioScalar(Double extinction_ratioValue) {
		return setField(NX_EXTINCTION_RATIO, extinction_ratioValue);
	}

	@Override
	public IDataset getReflection() {
		return getDataset(NX_REFLECTION);
	}

	@Override
	public Double getReflectionScalar() {
		return getDouble(NX_REFLECTION);
	}

	@Override
	public DataNode setReflection(IDataset reflectionDataset) {
		return setDataset(NX_REFLECTION, reflectionDataset);
	}

	@Override
	public DataNode setReflectionScalar(Double reflectionValue) {
		return setField(NX_REFLECTION, reflectionValue);
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
