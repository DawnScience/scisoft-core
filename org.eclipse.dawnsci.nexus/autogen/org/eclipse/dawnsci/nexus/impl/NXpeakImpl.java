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
 * Base class for describing a peak, its functional form, and support values
 * i.e., the discretization points at which the function has been evaluated.

 */
public class NXpeakImpl extends NXobjectImpl implements NXpeak {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_FIT_FUNCTION);

	public NXpeakImpl() {
		super();
	}

	public NXpeakImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpeak.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PEAK;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getLabel() {
		return getDataset(NX_LABEL);
	}

	@Override
	public String getLabelScalar() {
		return getString(NX_LABEL);
	}

	@Override
	public DataNode setLabel(IDataset labelDataset) {
		return setDataset(NX_LABEL, labelDataset);
	}

	@Override
	public DataNode setLabelScalar(String labelValue) {
		return setString(NX_LABEL, labelValue);
	}

	@Override
	public NXdata getData() {
		// dataNodeName = NX_DATA
		return getChild("data", NXdata.class);
	}

	@Override
	public void setData(NXdata dataGroup) {
		putChild("data", dataGroup);
	}

	@Override
	public NXfit_function getFunction() {
		// dataNodeName = NX_FUNCTION
		return getChild("function", NXfit_function.class);
	}

	@Override
	public void setFunction(NXfit_function functionGroup) {
		putChild("function", functionGroup);
	}

	@Override
	public Dataset getTotal_area() {
		return getDataset(NX_TOTAL_AREA);
	}

	@Override
	public Number getTotal_areaScalar() {
		return getNumber(NX_TOTAL_AREA);
	}

	@Override
	public DataNode setTotal_area(IDataset total_areaDataset) {
		return setDataset(NX_TOTAL_AREA, total_areaDataset);
	}

	@Override
	public DataNode setTotal_areaScalar(Number total_areaValue) {
		return setField(NX_TOTAL_AREA, total_areaValue);
	}

}
