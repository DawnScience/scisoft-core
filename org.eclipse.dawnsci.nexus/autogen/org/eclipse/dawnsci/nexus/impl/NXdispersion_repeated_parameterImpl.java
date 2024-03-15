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

import org.eclipse.dawnsci.nexus.*;

/**
 * A repeated parameter for a dispersion function

 */
public class NXdispersion_repeated_parameterImpl extends NXobjectImpl implements NXdispersion_repeated_parameter {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXdispersion_repeated_parameterImpl() {
		super();
	}

	public NXdispersion_repeated_parameterImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdispersion_repeated_parameter.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DISPERSION_REPEATED_PARAMETER;
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
	public IDataset getParameter_units() {
		return getDataset(NX_PARAMETER_UNITS);
	}

	@Override
	public String getParameter_unitsScalar() {
		return getString(NX_PARAMETER_UNITS);
	}

	@Override
	public DataNode setParameter_units(IDataset parameter_unitsDataset) {
		return setDataset(NX_PARAMETER_UNITS, parameter_unitsDataset);
	}

	@Override
	public DataNode setParameter_unitsScalar(String parameter_unitsValue) {
		return setString(NX_PARAMETER_UNITS, parameter_unitsValue);
	}

	@Override
	public IDataset getValues() {
		return getDataset(NX_VALUES);
	}

	@Override
	public Number getValuesScalar() {
		return getNumber(NX_VALUES);
	}

	@Override
	public DataNode setValues(IDataset valuesDataset) {
		return setDataset(NX_VALUES, valuesDataset);
	}

	@Override
	public DataNode setValuesScalar(Number valuesValue) {
		return setField(NX_VALUES, valuesValue);
	}

}
