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

import org.eclipse.dawnsci.nexus.*;

/**
 * Metadata and settings of an electron microscope for scans and images.
 * The need for such a structuring of data is evident from the fact that
 * electron microscopes are dynamic. Oftentimes it suffices to calibrate the
 * instrument at the start of the session. Subsequently, data
 * (images, spectra, etc.) can be collected. Users may wish to take only
 * a single scan or image and complete their microscope session; however
 * frequently users spend much longer at the microscope, recalibrate,
 * and take multiple data items (scans, images, spectra) each coming
 * with own detector and on-the-fly processing settings and calibration.
 * For the single data item use case one may argue that the need for additional
 * grouping is redundant. Instead, the metadata could equally be stored inside
 * the respective groups of the top-level mandatory NXinstrument group.
 * On the flip side, even for a session with a single image it would also not
 * harm to nest the data.
 * In fact, oftentimes scientists feel that there is a need to store details
 * about eventual drift of the specimen in its holder (if such data is available)
 * or record changes to the lens excitations caused or apertures used.
 * Although current microscopes are usually equipped with stabilization
 * systems for many of the individual components, it can still be useful
 * to store time-dependent data in detail.
 * Another reason if not a need for more finely granularizable options for
 * storing time-dependent data, is that over the course of a session one may
 * reconfigure the microscope. What is a reconfiguration? This could be the
 * change of an aperture mode because a scientist may first collect an image
 * with some aperture and then choose a different one. As the aperture affects
 * the electron beam it will affect the system.
 * Let aside for a moment the technology and business models, an EM could be
 * monitored (and will likely become so more in the future) for streaming out
 * spatio-temporal details about its components, locations of objects
 * and eventually applied stimuli and positioning of the specimen.
 * Some snapshot or integrated data from this stream are relevant for
 * understanding signal genesis and electron/ion beam paths. In such a generic
 * case it might be necessary to sync these streaming data with those intervals
 * in time when specific measurements are taken (spectra collected,
 * images taken, diffraction images indexed on-the-fly).
 * Theoretically, an instrument and specimen should be considered as dynamic.
 * Scientists often report or feel (difficult to quantify) observations that
 * microscopes *perform differently* across sessions, without sometimes being
 * able to identify clear root causes. Users of the instrument may consider
 * such conditions impractical and thus either abort their session or try to
 * bring the microscope first into a state where conditions are considered
 * stable and of high enough quality for collecting data.
 * In all these cases it is practical to store time-dependent data of the
 * instrument state not in the respective instrument component groups
 * of the top-level NXinstrument but in a sort of a log of event data.
 * This is the idea behind the NXevent_data_em snapshot containers.
 * The base class requires a start time and an optional end time.
 * The end time should be added to represent a time interval
 * (remind the idea of the instrument state stream) during which the
 * scientist considered the microscope (especially ebeam and specimen)
 * as stable enough.
 * For specific simulation purposes, mainly in an effort to digitally repeat
 * or simulate the experiment, it is tempting to consider dynamics of the
 * instrument, implemented as time-dependent functional descriptions of
 * e.g. lens excitations, beam shape functions, trajectories of groups of
 * electrons, or detector noise models.
 * For now the preferred strategy to handle these cases is through
 * customizations of the specific fields within NXevent_data_em instances.
 * Another alternative could be to sample finer, eventually dissimilarly along
 * the time axis; however this may cause situations where an NXevent_data_em
 * instance does not contain specific measurements
 * (i.e. images, spectra of scientific relevance).
 * In this case one should better go for a customized application definition
 * with a functional property description inside members (fields or groups)
 * in NXevent_data_em instances or resort to a specific application definition
 * which documents metadata for tracking explicitly electrons
 * (with ray-tracing based descriptors/computational geometry descriptors)
 * or tracking of wave bundles.
 * This perspective on more subtle time-dependent considerations of electron
 * microscopy can be advantageous also for storing details of time-dependent
 * additional components that are coupled to and/or synced with instrument.
 * Examples include cutting-edge experiments where the electron beam gets
 * coupled/excited by e.g. lasers. In this case, the laser unit should be
 * registered under the top-level NXinstrument section. Its spatio-temporal
 * details could be stored inside respective groups of the NXinstrument.
 * 
 */
public class NXevent_data_emImpl extends NXobjectImpl implements NXevent_data_em {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_IMAGE_SET_EM_SE,
		NexusBaseClass.NX_IMAGE_SET_EM_BSE,
		NexusBaseClass.NX_IMAGE_SET_EM_ECCI,
		NexusBaseClass.NX_IMAGE_SET_EM_BF,
		NexusBaseClass.NX_IMAGE_SET_EM_DF,
		NexusBaseClass.NX_IMAGE_SET_EM_ADF,
		NexusBaseClass.NX_IMAGE_SET_EM_KIKUCHI,
		NexusBaseClass.NX_IMAGE_SET_EM_DIFFRAC,
		NexusBaseClass.NX_SPECTRUM_SET_EM_XRAY,
		NexusBaseClass.NX_SPECTRUM_SET_EM_EELS,
		NexusBaseClass.NX_SPECTRUM_SET_EM_AUGER,
		NexusBaseClass.NX_SPECTRUM_SET_EM_CATHODOLUM,
		NexusBaseClass.NX_IMAGE_SET_EM_RONCHIGRAM,
		NexusBaseClass.NX_IMAGE_SET_EM_CHAMBER,
		NexusBaseClass.NX_EBEAM_COLUMN,
		NexusBaseClass.NX_IBEAM_COLUMN,
		NexusBaseClass.NX_SCANBOX_EM,
		NexusBaseClass.NX_SCANBOX_EM,
		NexusBaseClass.NX_OPTICAL_SYSTEM_EM,
		NexusBaseClass.NX_USER);

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
	public IDataset getStart_time() {
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
	public IDataset getEnd_time() {
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
	public IDataset getEvent_identifier() {
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
	public IDataset getEvent_type() {
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
	public IDataset getDetector_identifier() {
		return getDataset(NX_DETECTOR_IDENTIFIER);
	}

	@Override
	public String getDetector_identifierScalar() {
		return getString(NX_DETECTOR_IDENTIFIER);
	}

	@Override
	public DataNode setDetector_identifier(IDataset detector_identifierDataset) {
		return setDataset(NX_DETECTOR_IDENTIFIER, detector_identifierDataset);
	}

	@Override
	public DataNode setDetector_identifierScalar(String detector_identifierValue) {
		return setString(NX_DETECTOR_IDENTIFIER, detector_identifierValue);
	}

	@Override
	public NXimage_set_em_se getImage_set_em_se() {
		// dataNodeName = NX_IMAGE_SET_EM_SE
		return getChild("image_set_em_se", NXimage_set_em_se.class);
	}

	@Override
	public void setImage_set_em_se(NXimage_set_em_se image_set_em_seGroup) {
		putChild("image_set_em_se", image_set_em_seGroup);
	}

	@Override
	public NXimage_set_em_se getImage_set_em_se(String name) {
		return getChild(name, NXimage_set_em_se.class);
	}

	@Override
	public void setImage_set_em_se(String name, NXimage_set_em_se image_set_em_se) {
		putChild(name, image_set_em_se);
	}

	@Override
	public Map<String, NXimage_set_em_se> getAllImage_set_em_se() {
		return getChildren(NXimage_set_em_se.class);
	}
	
	@Override
	public void setAllImage_set_em_se(Map<String, NXimage_set_em_se> image_set_em_se) {
		setChildren(image_set_em_se);
	}

	@Override
	public NXimage_set_em_bse getImage_set_em_bse() {
		// dataNodeName = NX_IMAGE_SET_EM_BSE
		return getChild("image_set_em_bse", NXimage_set_em_bse.class);
	}

	@Override
	public void setImage_set_em_bse(NXimage_set_em_bse image_set_em_bseGroup) {
		putChild("image_set_em_bse", image_set_em_bseGroup);
	}

	@Override
	public NXimage_set_em_bse getImage_set_em_bse(String name) {
		return getChild(name, NXimage_set_em_bse.class);
	}

	@Override
	public void setImage_set_em_bse(String name, NXimage_set_em_bse image_set_em_bse) {
		putChild(name, image_set_em_bse);
	}

	@Override
	public Map<String, NXimage_set_em_bse> getAllImage_set_em_bse() {
		return getChildren(NXimage_set_em_bse.class);
	}
	
	@Override
	public void setAllImage_set_em_bse(Map<String, NXimage_set_em_bse> image_set_em_bse) {
		setChildren(image_set_em_bse);
	}

	@Override
	public NXimage_set_em_ecci getImage_set_em_ecci() {
		// dataNodeName = NX_IMAGE_SET_EM_ECCI
		return getChild("image_set_em_ecci", NXimage_set_em_ecci.class);
	}

	@Override
	public void setImage_set_em_ecci(NXimage_set_em_ecci image_set_em_ecciGroup) {
		putChild("image_set_em_ecci", image_set_em_ecciGroup);
	}

	@Override
	public NXimage_set_em_ecci getImage_set_em_ecci(String name) {
		return getChild(name, NXimage_set_em_ecci.class);
	}

	@Override
	public void setImage_set_em_ecci(String name, NXimage_set_em_ecci image_set_em_ecci) {
		putChild(name, image_set_em_ecci);
	}

	@Override
	public Map<String, NXimage_set_em_ecci> getAllImage_set_em_ecci() {
		return getChildren(NXimage_set_em_ecci.class);
	}
	
	@Override
	public void setAllImage_set_em_ecci(Map<String, NXimage_set_em_ecci> image_set_em_ecci) {
		setChildren(image_set_em_ecci);
	}

	@Override
	public NXimage_set_em_bf getImage_set_em_bf() {
		// dataNodeName = NX_IMAGE_SET_EM_BF
		return getChild("image_set_em_bf", NXimage_set_em_bf.class);
	}

	@Override
	public void setImage_set_em_bf(NXimage_set_em_bf image_set_em_bfGroup) {
		putChild("image_set_em_bf", image_set_em_bfGroup);
	}

	@Override
	public NXimage_set_em_bf getImage_set_em_bf(String name) {
		return getChild(name, NXimage_set_em_bf.class);
	}

	@Override
	public void setImage_set_em_bf(String name, NXimage_set_em_bf image_set_em_bf) {
		putChild(name, image_set_em_bf);
	}

	@Override
	public Map<String, NXimage_set_em_bf> getAllImage_set_em_bf() {
		return getChildren(NXimage_set_em_bf.class);
	}
	
	@Override
	public void setAllImage_set_em_bf(Map<String, NXimage_set_em_bf> image_set_em_bf) {
		setChildren(image_set_em_bf);
	}

	@Override
	public NXimage_set_em_df getImage_set_em_df() {
		// dataNodeName = NX_IMAGE_SET_EM_DF
		return getChild("image_set_em_df", NXimage_set_em_df.class);
	}

	@Override
	public void setImage_set_em_df(NXimage_set_em_df image_set_em_dfGroup) {
		putChild("image_set_em_df", image_set_em_dfGroup);
	}

	@Override
	public NXimage_set_em_df getImage_set_em_df(String name) {
		return getChild(name, NXimage_set_em_df.class);
	}

	@Override
	public void setImage_set_em_df(String name, NXimage_set_em_df image_set_em_df) {
		putChild(name, image_set_em_df);
	}

	@Override
	public Map<String, NXimage_set_em_df> getAllImage_set_em_df() {
		return getChildren(NXimage_set_em_df.class);
	}
	
	@Override
	public void setAllImage_set_em_df(Map<String, NXimage_set_em_df> image_set_em_df) {
		setChildren(image_set_em_df);
	}

	@Override
	public NXimage_set_em_adf getImage_set_em_adf() {
		// dataNodeName = NX_IMAGE_SET_EM_ADF
		return getChild("image_set_em_adf", NXimage_set_em_adf.class);
	}

	@Override
	public void setImage_set_em_adf(NXimage_set_em_adf image_set_em_adfGroup) {
		putChild("image_set_em_adf", image_set_em_adfGroup);
	}

	@Override
	public NXimage_set_em_adf getImage_set_em_adf(String name) {
		return getChild(name, NXimage_set_em_adf.class);
	}

	@Override
	public void setImage_set_em_adf(String name, NXimage_set_em_adf image_set_em_adf) {
		putChild(name, image_set_em_adf);
	}

	@Override
	public Map<String, NXimage_set_em_adf> getAllImage_set_em_adf() {
		return getChildren(NXimage_set_em_adf.class);
	}
	
	@Override
	public void setAllImage_set_em_adf(Map<String, NXimage_set_em_adf> image_set_em_adf) {
		setChildren(image_set_em_adf);
	}

	@Override
	public NXimage_set_em_kikuchi getImage_set_em_kikuchi() {
		// dataNodeName = NX_IMAGE_SET_EM_KIKUCHI
		return getChild("image_set_em_kikuchi", NXimage_set_em_kikuchi.class);
	}

	@Override
	public void setImage_set_em_kikuchi(NXimage_set_em_kikuchi image_set_em_kikuchiGroup) {
		putChild("image_set_em_kikuchi", image_set_em_kikuchiGroup);
	}

	@Override
	public NXimage_set_em_kikuchi getImage_set_em_kikuchi(String name) {
		return getChild(name, NXimage_set_em_kikuchi.class);
	}

	@Override
	public void setImage_set_em_kikuchi(String name, NXimage_set_em_kikuchi image_set_em_kikuchi) {
		putChild(name, image_set_em_kikuchi);
	}

	@Override
	public Map<String, NXimage_set_em_kikuchi> getAllImage_set_em_kikuchi() {
		return getChildren(NXimage_set_em_kikuchi.class);
	}
	
	@Override
	public void setAllImage_set_em_kikuchi(Map<String, NXimage_set_em_kikuchi> image_set_em_kikuchi) {
		setChildren(image_set_em_kikuchi);
	}

	@Override
	public NXimage_set_em_diffrac getImage_set_em_diffrac() {
		// dataNodeName = NX_IMAGE_SET_EM_DIFFRAC
		return getChild("image_set_em_diffrac", NXimage_set_em_diffrac.class);
	}

	@Override
	public void setImage_set_em_diffrac(NXimage_set_em_diffrac image_set_em_diffracGroup) {
		putChild("image_set_em_diffrac", image_set_em_diffracGroup);
	}

	@Override
	public NXimage_set_em_diffrac getImage_set_em_diffrac(String name) {
		return getChild(name, NXimage_set_em_diffrac.class);
	}

	@Override
	public void setImage_set_em_diffrac(String name, NXimage_set_em_diffrac image_set_em_diffrac) {
		putChild(name, image_set_em_diffrac);
	}

	@Override
	public Map<String, NXimage_set_em_diffrac> getAllImage_set_em_diffrac() {
		return getChildren(NXimage_set_em_diffrac.class);
	}
	
	@Override
	public void setAllImage_set_em_diffrac(Map<String, NXimage_set_em_diffrac> image_set_em_diffrac) {
		setChildren(image_set_em_diffrac);
	}

	@Override
	public NXspectrum_set_em_xray getSpectrum_set_em_xray() {
		// dataNodeName = NX_SPECTRUM_SET_EM_XRAY
		return getChild("spectrum_set_em_xray", NXspectrum_set_em_xray.class);
	}

	@Override
	public void setSpectrum_set_em_xray(NXspectrum_set_em_xray spectrum_set_em_xrayGroup) {
		putChild("spectrum_set_em_xray", spectrum_set_em_xrayGroup);
	}

	@Override
	public NXspectrum_set_em_xray getSpectrum_set_em_xray(String name) {
		return getChild(name, NXspectrum_set_em_xray.class);
	}

	@Override
	public void setSpectrum_set_em_xray(String name, NXspectrum_set_em_xray spectrum_set_em_xray) {
		putChild(name, spectrum_set_em_xray);
	}

	@Override
	public Map<String, NXspectrum_set_em_xray> getAllSpectrum_set_em_xray() {
		return getChildren(NXspectrum_set_em_xray.class);
	}
	
	@Override
	public void setAllSpectrum_set_em_xray(Map<String, NXspectrum_set_em_xray> spectrum_set_em_xray) {
		setChildren(spectrum_set_em_xray);
	}

	@Override
	public NXspectrum_set_em_eels getSpectrum_set_em_eels() {
		// dataNodeName = NX_SPECTRUM_SET_EM_EELS
		return getChild("spectrum_set_em_eels", NXspectrum_set_em_eels.class);
	}

	@Override
	public void setSpectrum_set_em_eels(NXspectrum_set_em_eels spectrum_set_em_eelsGroup) {
		putChild("spectrum_set_em_eels", spectrum_set_em_eelsGroup);
	}

	@Override
	public NXspectrum_set_em_eels getSpectrum_set_em_eels(String name) {
		return getChild(name, NXspectrum_set_em_eels.class);
	}

	@Override
	public void setSpectrum_set_em_eels(String name, NXspectrum_set_em_eels spectrum_set_em_eels) {
		putChild(name, spectrum_set_em_eels);
	}

	@Override
	public Map<String, NXspectrum_set_em_eels> getAllSpectrum_set_em_eels() {
		return getChildren(NXspectrum_set_em_eels.class);
	}
	
	@Override
	public void setAllSpectrum_set_em_eels(Map<String, NXspectrum_set_em_eels> spectrum_set_em_eels) {
		setChildren(spectrum_set_em_eels);
	}

	@Override
	public NXspectrum_set_em_auger getSpectrum_set_em_auger() {
		// dataNodeName = NX_SPECTRUM_SET_EM_AUGER
		return getChild("spectrum_set_em_auger", NXspectrum_set_em_auger.class);
	}

	@Override
	public void setSpectrum_set_em_auger(NXspectrum_set_em_auger spectrum_set_em_augerGroup) {
		putChild("spectrum_set_em_auger", spectrum_set_em_augerGroup);
	}

	@Override
	public NXspectrum_set_em_auger getSpectrum_set_em_auger(String name) {
		return getChild(name, NXspectrum_set_em_auger.class);
	}

	@Override
	public void setSpectrum_set_em_auger(String name, NXspectrum_set_em_auger spectrum_set_em_auger) {
		putChild(name, spectrum_set_em_auger);
	}

	@Override
	public Map<String, NXspectrum_set_em_auger> getAllSpectrum_set_em_auger() {
		return getChildren(NXspectrum_set_em_auger.class);
	}
	
	@Override
	public void setAllSpectrum_set_em_auger(Map<String, NXspectrum_set_em_auger> spectrum_set_em_auger) {
		setChildren(spectrum_set_em_auger);
	}

	@Override
	public NXspectrum_set_em_cathodolum getSpectrum_set_em_cathodolum() {
		// dataNodeName = NX_SPECTRUM_SET_EM_CATHODOLUM
		return getChild("spectrum_set_em_cathodolum", NXspectrum_set_em_cathodolum.class);
	}

	@Override
	public void setSpectrum_set_em_cathodolum(NXspectrum_set_em_cathodolum spectrum_set_em_cathodolumGroup) {
		putChild("spectrum_set_em_cathodolum", spectrum_set_em_cathodolumGroup);
	}

	@Override
	public NXspectrum_set_em_cathodolum getSpectrum_set_em_cathodolum(String name) {
		return getChild(name, NXspectrum_set_em_cathodolum.class);
	}

	@Override
	public void setSpectrum_set_em_cathodolum(String name, NXspectrum_set_em_cathodolum spectrum_set_em_cathodolum) {
		putChild(name, spectrum_set_em_cathodolum);
	}

	@Override
	public Map<String, NXspectrum_set_em_cathodolum> getAllSpectrum_set_em_cathodolum() {
		return getChildren(NXspectrum_set_em_cathodolum.class);
	}
	
	@Override
	public void setAllSpectrum_set_em_cathodolum(Map<String, NXspectrum_set_em_cathodolum> spectrum_set_em_cathodolum) {
		setChildren(spectrum_set_em_cathodolum);
	}

	@Override
	public NXimage_set_em_ronchigram getImage_set_em_ronchigram() {
		// dataNodeName = NX_IMAGE_SET_EM_RONCHIGRAM
		return getChild("image_set_em_ronchigram", NXimage_set_em_ronchigram.class);
	}

	@Override
	public void setImage_set_em_ronchigram(NXimage_set_em_ronchigram image_set_em_ronchigramGroup) {
		putChild("image_set_em_ronchigram", image_set_em_ronchigramGroup);
	}

	@Override
	public NXimage_set_em_ronchigram getImage_set_em_ronchigram(String name) {
		return getChild(name, NXimage_set_em_ronchigram.class);
	}

	@Override
	public void setImage_set_em_ronchigram(String name, NXimage_set_em_ronchigram image_set_em_ronchigram) {
		putChild(name, image_set_em_ronchigram);
	}

	@Override
	public Map<String, NXimage_set_em_ronchigram> getAllImage_set_em_ronchigram() {
		return getChildren(NXimage_set_em_ronchigram.class);
	}
	
	@Override
	public void setAllImage_set_em_ronchigram(Map<String, NXimage_set_em_ronchigram> image_set_em_ronchigram) {
		setChildren(image_set_em_ronchigram);
	}

	@Override
	public NXimage_set_em_chamber getImage_set_em_chamber() {
		// dataNodeName = NX_IMAGE_SET_EM_CHAMBER
		return getChild("image_set_em_chamber", NXimage_set_em_chamber.class);
	}

	@Override
	public void setImage_set_em_chamber(NXimage_set_em_chamber image_set_em_chamberGroup) {
		putChild("image_set_em_chamber", image_set_em_chamberGroup);
	}

	@Override
	public NXimage_set_em_chamber getImage_set_em_chamber(String name) {
		return getChild(name, NXimage_set_em_chamber.class);
	}

	@Override
	public void setImage_set_em_chamber(String name, NXimage_set_em_chamber image_set_em_chamber) {
		putChild(name, image_set_em_chamber);
	}

	@Override
	public Map<String, NXimage_set_em_chamber> getAllImage_set_em_chamber() {
		return getChildren(NXimage_set_em_chamber.class);
	}
	
	@Override
	public void setAllImage_set_em_chamber(Map<String, NXimage_set_em_chamber> image_set_em_chamber) {
		setChildren(image_set_em_chamber);
	}

	@Override
	public NXebeam_column getEbeam_column() {
		// dataNodeName = NX_EBEAM_COLUMN
		return getChild("ebeam_column", NXebeam_column.class);
	}

	@Override
	public void setEbeam_column(NXebeam_column ebeam_columnGroup) {
		putChild("ebeam_column", ebeam_columnGroup);
	}

	@Override
	public NXebeam_column getEbeam_column(String name) {
		return getChild(name, NXebeam_column.class);
	}

	@Override
	public void setEbeam_column(String name, NXebeam_column ebeam_column) {
		putChild(name, ebeam_column);
	}

	@Override
	public Map<String, NXebeam_column> getAllEbeam_column() {
		return getChildren(NXebeam_column.class);
	}
	
	@Override
	public void setAllEbeam_column(Map<String, NXebeam_column> ebeam_column) {
		setChildren(ebeam_column);
	}

	@Override
	public NXibeam_column getIbeam_column() {
		// dataNodeName = NX_IBEAM_COLUMN
		return getChild("ibeam_column", NXibeam_column.class);
	}

	@Override
	public void setIbeam_column(NXibeam_column ibeam_columnGroup) {
		putChild("ibeam_column", ibeam_columnGroup);
	}

	@Override
	public NXibeam_column getIbeam_column(String name) {
		return getChild(name, NXibeam_column.class);
	}

	@Override
	public void setIbeam_column(String name, NXibeam_column ibeam_column) {
		putChild(name, ibeam_column);
	}

	@Override
	public Map<String, NXibeam_column> getAllIbeam_column() {
		return getChildren(NXibeam_column.class);
	}
	
	@Override
	public void setAllIbeam_column(Map<String, NXibeam_column> ibeam_column) {
		setChildren(ibeam_column);
	}

	@Override
	public NXscanbox_em getEbeam_deflector() {
		// dataNodeName = NX_EBEAM_DEFLECTOR
		return getChild("ebeam_deflector", NXscanbox_em.class);
	}

	@Override
	public void setEbeam_deflector(NXscanbox_em ebeam_deflectorGroup) {
		putChild("ebeam_deflector", ebeam_deflectorGroup);
	}

	@Override
	public NXscanbox_em getIbeam_deflector() {
		// dataNodeName = NX_IBEAM_DEFLECTOR
		return getChild("ibeam_deflector", NXscanbox_em.class);
	}

	@Override
	public void setIbeam_deflector(NXscanbox_em ibeam_deflectorGroup) {
		putChild("ibeam_deflector", ibeam_deflectorGroup);
	}

	@Override
	public NXoptical_system_em getOptical_system_em() {
		// dataNodeName = NX_OPTICAL_SYSTEM_EM
		return getChild("optical_system_em", NXoptical_system_em.class);
	}

	@Override
	public void setOptical_system_em(NXoptical_system_em optical_system_emGroup) {
		putChild("optical_system_em", optical_system_emGroup);
	}

	@Override
	public NXoptical_system_em getOptical_system_em(String name) {
		return getChild(name, NXoptical_system_em.class);
	}

	@Override
	public void setOptical_system_em(String name, NXoptical_system_em optical_system_em) {
		putChild(name, optical_system_em);
	}

	@Override
	public Map<String, NXoptical_system_em> getAllOptical_system_em() {
		return getChildren(NXoptical_system_em.class);
	}
	
	@Override
	public void setAllOptical_system_em(Map<String, NXoptical_system_em> optical_system_em) {
		setChildren(optical_system_em);
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

}
