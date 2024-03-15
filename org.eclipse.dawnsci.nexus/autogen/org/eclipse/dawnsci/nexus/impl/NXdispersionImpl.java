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
 * A dispersion denoting a sum of different dispersions.
 * All NXdispersion_table and NXdispersion_function groups will be added together
 * to form a single dispersion.

 */
public class NXdispersionImpl extends NXobjectImpl implements NXdispersion {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DISPERSION_TABLE,
		NexusBaseClass.NX_DISPERSION_FUNCTION);

	public NXdispersionImpl() {
		super();
	}

	public NXdispersionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdispersion.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DISPERSION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getModel_name() {
		return getDataset(NX_MODEL_NAME);
	}

	@Override
	public String getModel_nameScalar() {
		return getString(NX_MODEL_NAME);
	}

	@Override
	public DataNode setModel_name(IDataset model_nameDataset) {
		return setDataset(NX_MODEL_NAME, model_nameDataset);
	}

	@Override
	public DataNode setModel_nameScalar(String model_nameValue) {
		return setString(NX_MODEL_NAME, model_nameValue);
	}

	@Override
	public NXdispersion_table getDispersion_table() {
		// dataNodeName = NX_DISPERSION_TABLE
		return getChild("dispersion_table", NXdispersion_table.class);
	}

	@Override
	public void setDispersion_table(NXdispersion_table dispersion_tableGroup) {
		putChild("dispersion_table", dispersion_tableGroup);
	}

	@Override
	public NXdispersion_table getDispersion_table(String name) {
		return getChild(name, NXdispersion_table.class);
	}

	@Override
	public void setDispersion_table(String name, NXdispersion_table dispersion_table) {
		putChild(name, dispersion_table);
	}

	@Override
	public Map<String, NXdispersion_table> getAllDispersion_table() {
		return getChildren(NXdispersion_table.class);
	}

	@Override
	public void setAllDispersion_table(Map<String, NXdispersion_table> dispersion_table) {
		setChildren(dispersion_table);
	}

	@Override
	public NXdispersion_function getDispersion_function() {
		// dataNodeName = NX_DISPERSION_FUNCTION
		return getChild("dispersion_function", NXdispersion_function.class);
	}

	@Override
	public void setDispersion_function(NXdispersion_function dispersion_functionGroup) {
		putChild("dispersion_function", dispersion_functionGroup);
	}

	@Override
	public NXdispersion_function getDispersion_function(String name) {
		return getChild(name, NXdispersion_function.class);
	}

	@Override
	public void setDispersion_function(String name, NXdispersion_function dispersion_function) {
		putChild(name, dispersion_function);
	}

	@Override
	public Map<String, NXdispersion_function> getAllDispersion_function() {
		return getChildren(NXdispersion_function.class);
	}

	@Override
	public void setAllDispersion_function(Map<String, NXdispersion_function> dispersion_function) {
		setChildren(dispersion_function);
	}

}
