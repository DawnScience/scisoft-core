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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Base class to store state and (meta)data of events for electron microscopy.
 * Event-related (meta)data, typically measured datasets like images and spectra.
 * To avoid repetitively storing static instrument-related metadata,
 * the dynamic (meta)data that typically changes for each image and spectrum
 * is split from the static (meta)data.
 * Which temporal granularity is adequate to log events depends on the situation and
 * research question. Using a model which enables a collection of events offers
 * the most flexible way to cater for both experiments with controlled electron
 * beams in a real microscope or the simulation of such experiments or
 * individual aspects of such experiments.
 * Electron microscopes are dynamic. Scientists often report that microscopes
 * *perform differently* across sessions. That *they* perform differently from
 * one day or another. In some cases, root causes for performance differences
 * are unclear. Users of the instrument may consider such conditions impractical,
 * or *too poor*, and thus abort their session. Alternatively, users may try to
 * bring the microscope into a state where conditions are considered better
 * or of whatever high enough quality for starting or continuing the measurement.
 * In all these use cases it is useful to have a mechanism whereby time-dependent
 * data of the instrument state can be stored and documented in an representation
 * that facilitates interoperability. This is the idea behind this base class.
 * :ref:`NXem_event_data` represents an instance to describe and serialize flexibly
 * whatever is considered a time interval during which the instrument is
 * considered stable enough for allowing any working on tasks with it.
 * Examples of such tasks are the collecting of data (images and spectra) or
 * the calibrating the instrument or individual of its components. Users may wish to take
 * only a single scan or image and complete their session thereafter.
 * Alternatively, users are working for much longer time at the instrument,
 * perform recalibrations in between and take several scans (of different
 * ROIs on the specimen), or they explore the state of the microscope for
 * service or maintenance tasks.
 * :ref:`NXem_event_data` serves the harmonization and documentation of these cases:
 * * Firstly, via a header section whose purpose is to contextualize
 * and identify the event instance in time.
 * * Secondly, via a data and metadata section where individual data
 * collections can be stored in a standardized representation.
 * We are aware of the fact that given the variety how an electron microscope
 * is used, there is a need for a flexible and adaptive documentation system.
 * At the same time we are also convinced though that just because one has
 * different requirements for some specific aspect under the umbrella of settings
 * to an electron microscope, this does not necessarily warrant that one has to
 * cook up an own data schema.
 * Instead, the electron microscopy community should work towards reusing schema
 * components as frequently as possible. This will enable that there is at all
 * not only a value of harmonizing electron microscopy research content but also
 * there is a technical possibility to build services around such harmonized data.
 * Arguably it is oftentimes tricky to specify a clear time interval when the
 * microscope is *stable enough*. Take for instance the acquisition of an image
 * or a stack of spectra. Having to deal with instabilities is a common theme in
 * electron microscopy practice. Numerical protocols can be used during data
 * post-processing to correct for some of the instabilities.
 * A few exemplar references provide an overview on the subject:
 * * `C. Ophus et al. <https://dx.doi.org/10.1016/j.ultramic.2015.12.002>`_
 * * `B. Berkels et al. <https://doi.org/10.1016/j.ultramic.2018.12.016>`_
 * * `L. Jones et al. <https://link.springer.com/article/10.1186/s40679-015-0008-4>`_
 * For specific simulation purposes, mainly in an effort to digitally repeat or simulate
 * the experiment (digital twin), it is tempting to consider dynamics of the instrument,
 * implemented as time-dependent functional descriptions of e.g. lens excitations,
 * beam shape functions, trajectories of groups of electrons and ions, or detector noise models.
 * This also warrants to document the time-dependent details of individual components
 * of the microscope via the here implemented class :ref:`NXem_event_data`.

 */
public class NXem_event_dataImpl extends NXobjectImpl implements NXem_event_data {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_USER,
		NexusBaseClass.NX_EM_INSTRUMENT,
		NexusBaseClass.NX_IMAGE,
		NexusBaseClass.NX_SPECTRUM);

	public NXem_event_dataImpl() {
		super();
	}

	public NXem_event_dataImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXem_event_data.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EM_EVENT_DATA;
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
	public Dataset getIdentifier_event() {
		return getDataset(NX_IDENTIFIER_EVENT);
	}

	@Override
	public Long getIdentifier_eventScalar() {
		return getLong(NX_IDENTIFIER_EVENT);
	}

	@Override
	public DataNode setIdentifier_event(IDataset identifier_eventDataset) {
		return setDataset(NX_IDENTIFIER_EVENT, identifier_eventDataset);
	}

	@Override
	public DataNode setIdentifier_eventScalar(Long identifier_eventValue) {
		return setField(NX_IDENTIFIER_EVENT, identifier_eventValue);
	}

	@Override
	public Dataset getIdentifier_sample() {
		return getDataset(NX_IDENTIFIER_SAMPLE);
	}

	@Override
	public String getIdentifier_sampleScalar() {
		return getString(NX_IDENTIFIER_SAMPLE);
	}

	@Override
	public DataNode setIdentifier_sample(IDataset identifier_sampleDataset) {
		return setDataset(NX_IDENTIFIER_SAMPLE, identifier_sampleDataset);
	}

	@Override
	public DataNode setIdentifier_sampleScalar(String identifier_sampleValue) {
		return setString(NX_IDENTIFIER_SAMPLE, identifier_sampleValue);
	}

	@Override
	public Dataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public NXuser getUser() {
		// dataNodeName = NX_USER
		return getChild("user", NXuser.class);
	}

	@Override
	public void setUser(NXuser userGroup) {
		putChild("user", userGroup);
	}

	@Override
	public NXuser getUser(String name) {
		return getChild(name, NXuser.class);
	}

	@Override
	public void setUser(String name, NXuser user) {
		putChild(name, user);
	}

	@Override
	public Map<String, NXuser> getAllUser() {
		return getChildren(NXuser.class);
	}

	@Override
	public void setAllUser(Map<String, NXuser> user) {
		setChildren(user);
	}

	@Override
	public NXem_instrument getEm_instrument() {
		// dataNodeName = NX_EM_INSTRUMENT
		return getChild("em_instrument", NXem_instrument.class);
	}

	@Override
	public void setEm_instrument(NXem_instrument em_instrumentGroup) {
		putChild("em_instrument", em_instrumentGroup);
	}

	@Override
	public NXem_instrument getEm_instrument(String name) {
		return getChild(name, NXem_instrument.class);
	}

	@Override
	public void setEm_instrument(String name, NXem_instrument em_instrument) {
		putChild(name, em_instrument);
	}

	@Override
	public Map<String, NXem_instrument> getAllEm_instrument() {
		return getChildren(NXem_instrument.class);
	}

	@Override
	public void setAllEm_instrument(Map<String, NXem_instrument> em_instrument) {
		setChildren(em_instrument);
	}

	@Override
	public NXimage getImage() {
		// dataNodeName = NX_IMAGE
		return getChild("image", NXimage.class);
	}

	@Override
	public void setImage(NXimage imageGroup) {
		putChild("image", imageGroup);
	}

	@Override
	public NXimage getImage(String name) {
		return getChild(name, NXimage.class);
	}

	@Override
	public void setImage(String name, NXimage image) {
		putChild(name, image);
	}

	@Override
	public Map<String, NXimage> getAllImage() {
		return getChildren(NXimage.class);
	}

	@Override
	public void setAllImage(Map<String, NXimage> image) {
		setChildren(image);
	}

	@Override
	public NXspectrum getSpectrum() {
		// dataNodeName = NX_SPECTRUM
		return getChild("spectrum", NXspectrum.class);
	}

	@Override
	public void setSpectrum(NXspectrum spectrumGroup) {
		putChild("spectrum", spectrumGroup);
	}

	@Override
	public NXspectrum getSpectrum(String name) {
		return getChild(name, NXspectrum.class);
	}

	@Override
	public void setSpectrum(String name, NXspectrum spectrum) {
		putChild(name, spectrum);
	}

	@Override
	public Map<String, NXspectrum> getAllSpectrum() {
		return getChildren(NXspectrum.class);
	}

	@Override
	public void setAllSpectrum(Map<String, NXspectrum> spectrum) {
		setChildren(spectrum);
	}

}
