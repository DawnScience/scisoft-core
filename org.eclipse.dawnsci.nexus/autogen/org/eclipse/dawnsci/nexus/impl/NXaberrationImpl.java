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
 * Quantified aberration coefficient in an aberration_model.

 */
public class NXaberrationImpl extends NXobjectImpl implements NXaberration {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXaberrationImpl() {
		super();
	}

	public NXaberrationImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXaberration.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ABERRATION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getMagnitude() {
		return getDataset(NX_MAGNITUDE);
	}

	@Override
	public Double getMagnitudeScalar() {
		return getDouble(NX_MAGNITUDE);
	}

	@Override
	public DataNode setMagnitude(IDataset magnitudeDataset) {
		return setDataset(NX_MAGNITUDE, magnitudeDataset);
	}

	@Override
	public DataNode setMagnitudeScalar(Double magnitudeValue) {
		return setField(NX_MAGNITUDE, magnitudeValue);
	}

	@Override
	public Dataset getUncertainty() {
		return getDataset(NX_UNCERTAINTY);
	}

	@Override
	public Double getUncertaintyScalar() {
		return getDouble(NX_UNCERTAINTY);
	}

	@Override
	public DataNode setUncertainty(IDataset uncertaintyDataset) {
		return setDataset(NX_UNCERTAINTY, uncertaintyDataset);
	}

	@Override
	public DataNode setUncertaintyScalar(Double uncertaintyValue) {
		return setField(NX_UNCERTAINTY, uncertaintyValue);
	}

	@Override
	public Dataset getUncertainty_model() {
		return getDataset(NX_UNCERTAINTY_MODEL);
	}

	@Override
	public String getUncertainty_modelScalar() {
		return getString(NX_UNCERTAINTY_MODEL);
	}

	@Override
	public DataNode setUncertainty_model(IDataset uncertainty_modelDataset) {
		return setDataset(NX_UNCERTAINTY_MODEL, uncertainty_modelDataset);
	}

	@Override
	public DataNode setUncertainty_modelScalar(String uncertainty_modelValue) {
		return setString(NX_UNCERTAINTY_MODEL, uncertainty_modelValue);
	}

	@Override
	public Dataset getDelta_time() {
		return getDataset(NX_DELTA_TIME);
	}

	@Override
	public Double getDelta_timeScalar() {
		return getDouble(NX_DELTA_TIME);
	}

	@Override
	public DataNode setDelta_time(IDataset delta_timeDataset) {
		return setDataset(NX_DELTA_TIME, delta_timeDataset);
	}

	@Override
	public DataNode setDelta_timeScalar(Double delta_timeValue) {
		return setField(NX_DELTA_TIME, delta_timeValue);
	}

	@Override
	public Dataset getAngle() {
		return getDataset(NX_ANGLE);
	}

	@Override
	public Double getAngleScalar() {
		return getDouble(NX_ANGLE);
	}

	@Override
	public DataNode setAngle(IDataset angleDataset) {
		return setDataset(NX_ANGLE, angleDataset);
	}

	@Override
	public DataNode setAngleScalar(Double angleValue) {
		return setField(NX_ANGLE, angleValue);
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
	public Dataset getAlias() {
		return getDataset(NX_ALIAS);
	}

	@Override
	public String getAliasScalar() {
		return getString(NX_ALIAS);
	}

	@Override
	public DataNode setAlias(IDataset aliasDataset) {
		return setDataset(NX_ALIAS, aliasDataset);
	}

	@Override
	public DataNode setAliasScalar(String aliasValue) {
		return setString(NX_ALIAS, aliasValue);
	}

}
