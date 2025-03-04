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
 * Metadata and settings of an electron microscope for scans and images.
 * The need for such a structuring of data is evident from the fact that
 * electron microscopes are dynamic. Oftentimes it suffices to calibrate the
 * instrument at the start of the session. Subsequently, data (images, spectra, etc.)
 * can be collected. Users may wish to take only a single scan or image and
 * complete their microscope session; however
 * frequently users are working much longer at the microscope, recalibrate and take
 * multiple data items (scans, images, spectra). Each item comes with own detector
 * and eventually on-the-fly processing settings and calibrations.
 * For the single data item use case one may argue that the need for an additional
 * grouping is redundant. Instead, the metadata could equally be stored inside
 * the respective groups of the top-level mandatory NXinstrument group.
 * On the flip side, even for a session with a single image, it would also not
 * harm to nest the data.
 * In fact, oftentimes scientists feel that there is a need to store details
 * about eventual drift of the specimen in its holder (if such data is available)
 * or record changes to the lens excitations or apertures used and/or changed.
 * Although current microscopes are usually equipped with stabilization
 * systems for many of the individual components, it can still be useful
 * to store time-dependent data in detail.
 * Another reason if not a need for having more finely granularizable options for
 * storing time-dependent data, is that over the course of a session one may
 * reconfigure the microscope. What is a reconfiguration? This could be the
 * change of an aperture mode because a scientist may first collect an image
 * with some aperture and then pick a different value and continue.
 * As the aperture affects the electron beam, it will affect the system.
 * Let aside for a moment the technology and business models, an EM could be
 * monitored (and will likely become so more in the future) for streaming out
 * spatio-temporal details about its components, locations of (hardware components) and objects within the region-of-interest. Eventually external stimuli are applied
 * and the specimen repositioned.
 * Some snapshot or integrated data from this stream are relevant for understanding
 * signal genesis and electron/ion-beam-sample interaction (paths). In such a generic
 * case it might be necessary to sync these streaming data with those intervals
 * in time when specific measurements are taken (spectra collected,
 * images taken, diffraction images indexed on-the-fly).
 * Therefore, both the instrument and specimen should always be considered as dynamic.
 * Scientists often report or feel (difficult to quantify) observations that
 * microscopes *perform differently* across sessions, without sometimes being
 * able to identify clear root causes. Users of the instrument may consider
 * such conditions impractical, or *too poor* and thus either abort their session
 * or try to bring the microscope first into a state where conditions are considered
 * more stable, better, or of some whatever high enough quality to reuse
 * data collection.
 * In all these cases it is practical to have a mechanism how time-dependent data
 * of the instrument state can be stored and documented in a interoperable way.
 * Where should these data be stored? With NeXus these data should not only be
 * stored in the respective instrument component groups of the top-level NXinstrument.
 * The reason is that this group should be reserved for as stable as possible
 * quantities which do not change over the session. Thereby we can avoid to store
 * repetitively that there is a certain gun or detector available but just store
 * the changes. This is exactly what the em_lab group is for inside NXevent_data_em.
 * Ideally, NXevent_data_em are equipped with a start_time and end_time
 * to represent a time interval (remind the idea of the instrument state stream)
 * during which the scientist considered (or practically has to consider)
 * the microscope (especially ebeam and specimen) as stable enough.
 * Arguably it is oftentimes tricky to specify a clear time interval when the
 * microscope is stable enough. Take for instance the acquisition of an image
 * or spectra stack. It is not fully possible (technically) to avoid that even
 * within a single image instabilities of the beam are faced and drift occurs.
 * Maybe in many cases this these instabilities are irrelevant but does this
 * warrant to create a data schema where either the microscope state can only
 * be stored very coarsely or one is forced to store it very finely?
 * This is a question of how one wishes to granularize pieces of information.
 * A possible solution is to consider each probed position, i.e. point in time
 * when the beam was not blanked and thus when the beam illuminates a portion of
 * the material, i.e. the interaction volume, whose signal contributions are then
 * counted by the one or multiple detector(s) as per pixel- or per voxel signal
 * in the region-of-interest.
 * NXevent_data_em in combination with the NXem application definition
 * allows researchers to document this. Noteworty to mention is that we understand
 * that in many cases realizing such a fine temporal and logical granularization
 * and data collection is hard to achieve in practice.
 * A frequently made choice, mainly for convenience, is that drift and scan distortions
 * are considered a feature or inaccuracy of the image and/or spectrum and thus
 * one de facto accepts that the microscope was not as stable as expected during
 * the acquisition of the image. We learn that the idea of a time interval
 * during the microscope session may be interpreted differently by different
 * users. Here we consider the choice to focus on images and spectra, and eventually
 * single position measurements as the smallest granularization level.
 * Which eventually may require to add optional NXprocess instances for respectively
 * collected data to describe the relevant distortions. Nevertheless, the distortions
 * are typically corrected for by numerical protocols. This fact warrants to
 * consider the distortion correction a computational workflow which can be
 * modelled as a chain of NXprocess instances each with own parameters. an own
 * A more detailed overview of such computational steps to cope with scan distortions
 * is available in the literature:
 * * `C. Ophus et al. <https://dx.doi.org/10.1016/j.ultramic.2015.12.002>`_
 * * `B. Berkels et al. <https://doi.org/10.1016/j.ultramic.2018.12.016>`_
 * * `L. Jones et al. <https://link.springer.com/article/10.1186/s40679-015-0008-4>`_
 * For specific simulation purposes, mainly in an effort to digitally repeat
 * or simulate the experiment, it is tempting to consider dynamics of the
 * instrument, implemented as time-dependent functional descriptions of
 * e.g. lens excitations, beam shape functions, trajectories of groups of
 * electrons, or detector noise models.
 * For now the preferred strategy to handle these cases is through
 * customizations of the specific fields within NXevent_data_em instances.
 * Another alternative could be to sample finer, eventually dissimilarly along
 * the time axi; however this may cause situations where an NXevent_data_em
 * instance does not contain specific measurements (i.e. images, spectra of
 * scientific relevance).
 * In this case one should better go for a customized application definition
 * with a functional property description inside members (fields or groups)
 * in NXevent_data_em instances; or resort to a specific offspring application
 * definition of NXem which documents metadata for tracking explicitly electrons
 * (with ray-tracing based descriptors/computational geometry descriptors)
 * or tracking of wave bundles.
 * This perspective on much more subtle time-dependent considerations of electron
 * microscopy can be advantageous also for storing details of time-dependent
 * additional components that are coupled to and/or synced with a microscope.
 * Examples include cutting-edge experiments where the electron beam gets
 * coupled/excited by e.g. lasers. In this case, the laser unit should be
 * registered under the top-level NXinstrument section. Its spatio-temporal
 * details could be stored inside respective additional groups of the NXinstrument
 * though inside instances of here detailed NXevent_data_em.

 */
public class NXevent_data_emImpl extends NXobjectImpl implements NXevent_data_em {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_IMAGE_SET,
		NexusBaseClass.NX_SPECTRUM_SET,
		NexusBaseClass.NX_INSTRUMENT,
		NexusBaseClass.NX_USER,
		NexusBaseClass.NX_INTERACTION_VOL_EM);

	public NXevent_data_emImpl() {
		super();
	}

	public NXevent_data_emImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXevent_data_em.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EVENT_DATA_EM;
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
	public Dataset getEvent_identifier() {
		return getDataset(NX_EVENT_IDENTIFIER);
	}

	@Override
	public String getEvent_identifierScalar() {
		return getString(NX_EVENT_IDENTIFIER);
	}

	@Override
	public DataNode setEvent_identifier(IDataset event_identifierDataset) {
		return setDataset(NX_EVENT_IDENTIFIER, event_identifierDataset);
	}

	@Override
	public DataNode setEvent_identifierScalar(String event_identifierValue) {
		return setString(NX_EVENT_IDENTIFIER, event_identifierValue);
	}

	@Override
	public Dataset getEvent_type() {
		return getDataset(NX_EVENT_TYPE);
	}

	@Override
	public String getEvent_typeScalar() {
		return getString(NX_EVENT_TYPE);
	}

	@Override
	public DataNode setEvent_type(IDataset event_typeDataset) {
		return setDataset(NX_EVENT_TYPE, event_typeDataset);
	}

	@Override
	public DataNode setEvent_typeScalar(String event_typeValue) {
		return setString(NX_EVENT_TYPE, event_typeValue);
	}

	@Override
	public NXimage_set getImage_set() {
		// dataNodeName = NX_IMAGE_SET
		return getChild("image_set", NXimage_set.class);
	}

	@Override
	public void setImage_set(NXimage_set image_setGroup) {
		putChild("image_set", image_setGroup);
	}

	@Override
	public NXimage_set getImage_set(String name) {
		return getChild(name, NXimage_set.class);
	}

	@Override
	public void setImage_set(String name, NXimage_set image_set) {
		putChild(name, image_set);
	}

	@Override
	public Map<String, NXimage_set> getAllImage_set() {
		return getChildren(NXimage_set.class);
	}

	@Override
	public void setAllImage_set(Map<String, NXimage_set> image_set) {
		setChildren(image_set);
	}

	@Override
	public NXspectrum_set getSpectrum_set() {
		// dataNodeName = NX_SPECTRUM_SET
		return getChild("spectrum_set", NXspectrum_set.class);
	}

	@Override
	public void setSpectrum_set(NXspectrum_set spectrum_setGroup) {
		putChild("spectrum_set", spectrum_setGroup);
	}

	@Override
	public NXspectrum_set getSpectrum_set(String name) {
		return getChild(name, NXspectrum_set.class);
	}

	@Override
	public void setSpectrum_set(String name, NXspectrum_set spectrum_set) {
		putChild(name, spectrum_set);
	}

	@Override
	public Map<String, NXspectrum_set> getAllSpectrum_set() {
		return getChildren(NXspectrum_set.class);
	}

	@Override
	public void setAllSpectrum_set(Map<String, NXspectrum_set> spectrum_set) {
		setChildren(spectrum_set);
	}

	@Override
	public NXinstrument getInstrument() {
		// dataNodeName = NX_INSTRUMENT
		return getChild("instrument", NXinstrument.class);
	}

	@Override
	public void setInstrument(NXinstrument instrumentGroup) {
		putChild("instrument", instrumentGroup);
	}

	@Override
	public NXinstrument getInstrument(String name) {
		return getChild(name, NXinstrument.class);
	}

	@Override
	public void setInstrument(String name, NXinstrument instrument) {
		putChild(name, instrument);
	}

	@Override
	public Map<String, NXinstrument> getAllInstrument() {
		return getChildren(NXinstrument.class);
	}

	@Override
	public void setAllInstrument(Map<String, NXinstrument> instrument) {
		setChildren(instrument);
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
	public NXinteraction_vol_em getInteraction_vol_em() {
		// dataNodeName = NX_INTERACTION_VOL_EM
		return getChild("interaction_vol_em", NXinteraction_vol_em.class);
	}

	@Override
	public void setInteraction_vol_em(NXinteraction_vol_em interaction_vol_emGroup) {
		putChild("interaction_vol_em", interaction_vol_emGroup);
	}

	@Override
	public NXinteraction_vol_em getInteraction_vol_em(String name) {
		return getChild(name, NXinteraction_vol_em.class);
	}

	@Override
	public void setInteraction_vol_em(String name, NXinteraction_vol_em interaction_vol_em) {
		putChild(name, interaction_vol_em);
	}

	@Override
	public Map<String, NXinteraction_vol_em> getAllInteraction_vol_em() {
		return getChildren(NXinteraction_vol_em.class);
	}

	@Override
	public void setAllInteraction_vol_em(Map<String, NXinteraction_vol_em> interaction_vol_em) {
		setChildren(interaction_vol_em);
	}

}
