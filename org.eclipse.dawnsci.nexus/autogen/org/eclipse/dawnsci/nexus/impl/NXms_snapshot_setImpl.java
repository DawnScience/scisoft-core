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
 * A collection of spatiotemporal microstructure data.

 */
public class NXms_snapshot_setImpl extends NXobjectImpl implements NXms_snapshot_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_MS_SNAPSHOT);

	public NXms_snapshot_setImpl() {
		super();
	}

	public NXms_snapshot_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXms_snapshot_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MS_SNAPSHOT_SET;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getComment() {
		return getDataset(NX_COMMENT);
	}

	@Override
	public String getCommentScalar() {
		return getString(NX_COMMENT);
	}

	@Override
	public DataNode setComment(IDataset commentDataset) {
		return setDataset(NX_COMMENT, commentDataset);
	}

	@Override
	public DataNode setCommentScalar(String commentValue) {
		return setString(NX_COMMENT, commentValue);
	}

	@Override
	public Dataset getIdentifier_offset() {
		return getDataset(NX_IDENTIFIER_OFFSET);
	}

	@Override
	public Long getIdentifier_offsetScalar() {
		return getLong(NX_IDENTIFIER_OFFSET);
	}

	@Override
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset) {
		return setDataset(NX_IDENTIFIER_OFFSET, identifier_offsetDataset);
	}

	@Override
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue) {
		return setField(NX_IDENTIFIER_OFFSET, identifier_offsetValue);
	}

	@Override
	public NXms_snapshot getMs_snapshot() {
		// dataNodeName = NX_MS_SNAPSHOT
		return getChild("ms_snapshot", NXms_snapshot.class);
	}

	@Override
	public void setMs_snapshot(NXms_snapshot ms_snapshotGroup) {
		putChild("ms_snapshot", ms_snapshotGroup);
	}

	@Override
	public NXms_snapshot getMs_snapshot(String name) {
		return getChild(name, NXms_snapshot.class);
	}

	@Override
	public void setMs_snapshot(String name, NXms_snapshot ms_snapshot) {
		putChild(name, ms_snapshot);
	}

	@Override
	public Map<String, NXms_snapshot> getAllMs_snapshot() {
		return getChildren(NXms_snapshot.class);
	}

	@Override
	public void setAllMs_snapshot(Map<String, NXms_snapshot> ms_snapshot) {
		setChildren(ms_snapshot);
	}

}
