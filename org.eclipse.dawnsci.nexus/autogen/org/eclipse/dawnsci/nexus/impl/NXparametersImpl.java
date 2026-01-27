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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Container for parameters, usually used in processing or analysis.

 */
public class NXparametersImpl extends NXobjectImpl implements NXparameters {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXparametersImpl() {
		super();
	}

	public NXparametersImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXparameters.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PARAMETERS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getParameter(String parameter) {
		return getDataset(parameter);
	}

	@Override
	public Object getParameterScalar(String parameter) {
		return getDataset(parameter).getObject();
	}

	@Override
	public DataNode setParameter(String parameter, IDataset parameterDataset) {
		return setDataset(parameter, parameterDataset);
	}

	@Override
	public DataNode setParameterScalar(String parameter, Object parameterValue) {
		if (parameterValue instanceof Number) {
			return setField(parameter, parameterValue);
		} else if (parameterValue instanceof String) {
			return setString(parameter, (String) parameterValue);
		} else {
			throw new IllegalArgumentException("Value must be String or Number");
		}
	}

	@Override
	public Map<String, Dataset> getAllParameter() {
		return getAllDatasets(); // note: returns all datasets in the group!
	}

	@Override
	public String getParameterAttributeUnits(String parameter) {
		return getAttrString(parameter, NX_PARAMETER_ATTRIBUTE_UNITS);
	}

	@Override
	public void setParameterAttributeUnits(String parameter, String unitsValue) {
		setAttribute(parameter, NX_PARAMETER_ATTRIBUTE_UNITS, unitsValue);
	}

}
