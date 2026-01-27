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

import java.util.Date;
import java.util.Set;
import java.util.EnumSet;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Base class to store state and (meta)data of events over the course of an atom probe experiment.
 * Having at least one instance for an instance of NXapm is recommended.
 * This base class applies the concept of the :ref:`NXem_event_data` base class to the specific needs
 * of atom probe research. Again static and dynamic quantities are split to avoid a duplication
 * of information. Specifically, the time interval considered is the entire time
 * starting at start_time until end_time during which we assume the pulser triggered pulses.
 * These pulses are identified via the pulse_id field. The point in time when each pulse was
 * fired can be recovered from analyzing start_time and delta_time.
 * Which temporal granularity is adequate depends on the situation and research question.
 * Using a model which enables a collection of events offers the most flexible way to cater for
 * both atom probe experiments or simulation. To monitor the course of an ion extraction experiment
 * (or simulation) it makes sense to track time explicitly via time stamps or implicitly
 * via e.g. a clock inside the instrument, such as the clock of the pulser and respective pulse_id.

 */
public class NXapm_event_dataImpl extends NXobjectImpl implements NXapm_event_data {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_APM_INSTRUMENT);

	public NXapm_event_dataImpl() {
		super();
	}

	public NXapm_event_dataImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXapm_event_data.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APM_EVENT_DATA;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getStart_time() {
		return getDataset(NX_START_TIME);
	}

	@Override
	public Date getStart_timeScalar() {
		return getDate(NX_START_TIME);
	}

	@Override
	public DataNode setStart_time(IDataset start_timeDataset) {
		return setDataset(NX_START_TIME, start_timeDataset);
	}

	@Override
	public DataNode setStart_timeScalar(Date start_timeValue) {
		return setDate(NX_START_TIME, start_timeValue);
	}

	@Override
	public Dataset getEnd_time() {
		return getDataset(NX_END_TIME);
	}

	@Override
	public Date getEnd_timeScalar() {
		return getDate(NX_END_TIME);
	}

	@Override
	public DataNode setEnd_time(IDataset end_timeDataset) {
		return setDataset(NX_END_TIME, end_timeDataset);
	}

	@Override
	public DataNode setEnd_timeScalar(Date end_timeValue) {
		return setDate(NX_END_TIME, end_timeValue);
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
	public Dataset getPulse_id_offset() {
		return getDataset(NX_PULSE_ID_OFFSET);
	}

	@Override
	public Long getPulse_id_offsetScalar() {
		return getLong(NX_PULSE_ID_OFFSET);
	}

	@Override
	public DataNode setPulse_id_offset(IDataset pulse_id_offsetDataset) {
		return setDataset(NX_PULSE_ID_OFFSET, pulse_id_offsetDataset);
	}

	@Override
	public DataNode setPulse_id_offsetScalar(Long pulse_id_offsetValue) {
		return setField(NX_PULSE_ID_OFFSET, pulse_id_offsetValue);
	}

	@Override
	public Dataset getPulse_id() {
		return getDataset(NX_PULSE_ID);
	}

	@Override
	public Long getPulse_idScalar() {
		return getLong(NX_PULSE_ID);
	}

	@Override
	public DataNode setPulse_id(IDataset pulse_idDataset) {
		return setDataset(NX_PULSE_ID, pulse_idDataset);
	}

	@Override
	public DataNode setPulse_idScalar(Long pulse_idValue) {
		return setField(NX_PULSE_ID, pulse_idValue);
	}

	@Override
	public NXapm_instrument getInstrument() {
		// dataNodeName = NX_INSTRUMENT
		return getChild("instrument", NXapm_instrument.class);
	}

	@Override
	public void setInstrument(NXapm_instrument instrumentGroup) {
		putChild("instrument", instrumentGroup);
	}

}
