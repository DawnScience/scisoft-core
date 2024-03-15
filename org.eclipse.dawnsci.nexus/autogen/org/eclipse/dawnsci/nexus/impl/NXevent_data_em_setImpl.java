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


import org.eclipse.dawnsci.nexus.*;

/**
 * Container to hold NXevent_data_em instances of an electron microscope session.
 * An event is a time interval during which the microscope was configured,
 * considered stable, and used for characterization.

 */
public class NXevent_data_em_setImpl extends NXobjectImpl implements NXevent_data_em_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_EVENT_DATA_EM);

	public NXevent_data_em_setImpl() {
		super();
	}

	public NXevent_data_em_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXevent_data_em_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EVENT_DATA_EM_SET;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXevent_data_em getEvent_data_em() {
		// dataNodeName = NX_EVENT_DATA_EM
		return getChild("event_data_em", NXevent_data_em.class);
	}

	@Override
	public void setEvent_data_em(NXevent_data_em event_data_emGroup) {
		putChild("event_data_em", event_data_emGroup);
	}

	@Override
	public NXevent_data_em getEvent_data_em(String name) {
		return getChild(name, NXevent_data_em.class);
	}

	@Override
	public void setEvent_data_em(String name, NXevent_data_em event_data_em) {
		putChild(name, event_data_em);
	}

	@Override
	public Map<String, NXevent_data_em> getAllEvent_data_em() {
		return getChildren(NXevent_data_em.class);
	}

	@Override
	public void setAllEvent_data_em(Map<String, NXevent_data_em> event_data_em) {
		setChildren(event_data_em);
	}

}
