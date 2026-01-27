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
 * For an introduction in the details about aberrations with relevance for electron microscopy
 * see `R. Dunin-Borkowski et al. <https://doi.org/10.1017/9781316337455.022>`_ and
 * `S. J. Pennycock and P. D. Nellist <https://doi.org/10.1007/978-1-4419-7200-2>`_ (page 44ff, and page 118ff)
 * for different definitions available and further details.
 * Table 7-2 of Ibid. publication (page 305ff) documents how to convert from the Nion to the CEOS definitions.
 * Conversion tables are also summarized by `Y. Liao <https://www.globalsino.com/EM/page3740.html>`_ an introduction.
 * The use of the base class is not restricted to electron microscopy but can also be useful for classical optics.

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
	public Number getMagnitudeScalar() {
		return getNumber(NX_MAGNITUDE);
	}

	@Override
	public DataNode setMagnitude(IDataset magnitudeDataset) {
		return setDataset(NX_MAGNITUDE, magnitudeDataset);
	}

	@Override
	public DataNode setMagnitudeScalar(Number magnitudeValue) {
		return setField(NX_MAGNITUDE, magnitudeValue);
	}

	@Override
	public Dataset getMagnitude_errors() {
		return getDataset(NX_MAGNITUDE_ERRORS);
	}

	@Override
	public Number getMagnitude_errorsScalar() {
		return getNumber(NX_MAGNITUDE_ERRORS);
	}

	@Override
	public DataNode setMagnitude_errors(IDataset magnitude_errorsDataset) {
		return setDataset(NX_MAGNITUDE_ERRORS, magnitude_errorsDataset);
	}

	@Override
	public DataNode setMagnitude_errorsScalar(Number magnitude_errorsValue) {
		return setField(NX_MAGNITUDE_ERRORS, magnitude_errorsValue);
	}

	@Override
	public Dataset getMagnitude_errors_model() {
		return getDataset(NX_MAGNITUDE_ERRORS_MODEL);
	}

	@Override
	public String getMagnitude_errors_modelScalar() {
		return getString(NX_MAGNITUDE_ERRORS_MODEL);
	}

	@Override
	public DataNode setMagnitude_errors_model(IDataset magnitude_errors_modelDataset) {
		return setDataset(NX_MAGNITUDE_ERRORS_MODEL, magnitude_errors_modelDataset);
	}

	@Override
	public DataNode setMagnitude_errors_modelScalar(String magnitude_errors_modelValue) {
		return setString(NX_MAGNITUDE_ERRORS_MODEL, magnitude_errors_modelValue);
	}

	@Override
	public Dataset getDelta_time() {
		return getDataset(NX_DELTA_TIME);
	}

	@Override
	public Number getDelta_timeScalar() {
		return getNumber(NX_DELTA_TIME);
	}

	@Override
	public DataNode setDelta_time(IDataset delta_timeDataset) {
		return setDataset(NX_DELTA_TIME, delta_timeDataset);
	}

	@Override
	public DataNode setDelta_timeScalar(Number delta_timeValue) {
		return setField(NX_DELTA_TIME, delta_timeValue);
	}

	@Override
	public Dataset getAngle() {
		return getDataset(NX_ANGLE);
	}

	@Override
	public Number getAngleScalar() {
		return getNumber(NX_ANGLE);
	}

	@Override
	public DataNode setAngle(IDataset angleDataset) {
		return setDataset(NX_ANGLE, angleDataset);
	}

	@Override
	public DataNode setAngleScalar(Number angleValue) {
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
