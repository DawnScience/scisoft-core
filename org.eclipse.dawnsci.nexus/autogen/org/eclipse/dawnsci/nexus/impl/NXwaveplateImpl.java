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
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * A waveplate or retarder.

 */
public class NXwaveplateImpl extends NXobjectImpl implements NXwaveplate {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SAMPLE,
		NexusBaseClass.NX_SAMPLE);

	public NXwaveplateImpl() {
		super();
	}

	public NXwaveplateImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXwaveplate.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_WAVEPLATE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public Dataset getOther_type() {
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
	public Dataset getRetardance() {
		return getDataset(NX_RETARDANCE);
	}

	@Override
	public String getRetardanceScalar() {
		return getString(NX_RETARDANCE);
	}

	@Override
	public DataNode setRetardance(IDataset retardanceDataset) {
		return setDataset(NX_RETARDANCE, retardanceDataset);
	}

	@Override
	public DataNode setRetardanceScalar(String retardanceValue) {
		return setString(NX_RETARDANCE, retardanceValue);
	}

	@Override
	public Dataset getWavelengths() {
		return getDataset(NX_WAVELENGTHS);
	}

	@Override
	public Number getWavelengthsScalar() {
		return getNumber(NX_WAVELENGTHS);
	}

	@Override
	public DataNode setWavelengths(IDataset wavelengthsDataset) {
		return setDataset(NX_WAVELENGTHS, wavelengthsDataset);
	}

	@Override
	public DataNode setWavelengthsScalar(Number wavelengthsValue) {
		return setField(NX_WAVELENGTHS, wavelengthsValue);
	}

	@Override
	public Dataset getDiameter() {
		return getDataset(NX_DIAMETER);
	}

	@Override
	public Double getDiameterScalar() {
		return getDouble(NX_DIAMETER);
	}

	@Override
	public DataNode setDiameter(IDataset diameterDataset) {
		return setDataset(NX_DIAMETER, diameterDataset);
	}

	@Override
	public DataNode setDiameterScalar(Double diameterValue) {
		return setField(NX_DIAMETER, diameterValue);
	}

	@Override
	public Dataset getClear_aperture() {
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
	public Dataset getReflectance() {
		return getDataset(NX_REFLECTANCE);
	}

	@Override
	public Number getReflectanceScalar() {
		return getNumber(NX_REFLECTANCE);
	}

	@Override
	public DataNode setReflectance(IDataset reflectanceDataset) {
		return setDataset(NX_REFLECTANCE, reflectanceDataset);
	}

	@Override
	public DataNode setReflectanceScalar(Number reflectanceValue) {
		return setField(NX_REFLECTANCE, reflectanceValue);
	}

}
