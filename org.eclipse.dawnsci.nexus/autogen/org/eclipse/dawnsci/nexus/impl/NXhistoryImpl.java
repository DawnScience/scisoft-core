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
 * A set of activities that occurred to a physical entity prior/during experiment.
 * Ideally, a full report of the previous operations (or links to a chain of operations).
 * Alternatively, notes allow for additional descriptors in any format.

 */
public class NXhistoryImpl extends NXobjectImpl implements NXhistory {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_ACTIVITY,
		NexusBaseClass.NX_NOTE);

	public NXhistoryImpl() {
		super();
	}

	public NXhistoryImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXhistory.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_HISTORY;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXactivity getActivity() {
		// dataNodeName = NX_ACTIVITY
		return getChild("activity", NXactivity.class);
	}

	@Override
	public void setActivity(NXactivity activityGroup) {
		putChild("activity", activityGroup);
	}

	@Override
	public NXactivity getActivity(String name) {
		return getChild(name, NXactivity.class);
	}

	@Override
	public void setActivity(String name, NXactivity activity) {
		putChild(name, activity);
	}

	@Override
	public Map<String, NXactivity> getAllActivity() {
		return getChildren(NXactivity.class);
	}

	@Override
	public void setAllActivity(Map<String, NXactivity> activity) {
		setChildren(activity);
	}

	@Override
	public Dataset getIdentifiername() {
		return getDataset(NX_IDENTIFIERNAME);
	}

	@Override
	public String getIdentifiernameScalar() {
		return getString(NX_IDENTIFIERNAME);
	}

	@Override
	public DataNode setIdentifiername(IDataset identifiernameDataset) {
		return setDataset(NX_IDENTIFIERNAME, identifiernameDataset);
	}

	@Override
	public DataNode setIdentifiernameScalar(String identifiernameValue) {
		return setString(NX_IDENTIFIERNAME, identifiernameValue);
	}

	@Override
	public NXnote getNote() {
		// dataNodeName = NX_NOTE
		return getChild("note", NXnote.class);
	}

	@Override
	public void setNote(NXnote noteGroup) {
		putChild("note", noteGroup);
	}

	@Override
	public NXnote getNote(String name) {
		return getChild(name, NXnote.class);
	}

	@Override
	public void setNote(String name, NXnote note) {
		putChild(name, note);
	}

	@Override
	public Map<String, NXnote> getAllNote() {
		return getChildren(NXnote.class);
	}

	@Override
	public void setAllNote(Map<String, NXnote> note) {
		setChildren(note);
	}

}
