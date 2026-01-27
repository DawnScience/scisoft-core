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

package org.eclipse.dawnsci.nexus;

import java.util.Date;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

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
 *
 */
public interface NXem_event_data extends NXobject {

	public static final String NX_START_TIME = "start_time";
	public static final String NX_END_TIME = "end_time";
	public static final String NX_IDENTIFIER_EVENT = "identifier_event";
	public static final String NX_IDENTIFIER_SAMPLE = "identifier_sample";
	public static final String NX_TYPE = "type";
	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval started.
	 * If users wish to specify an interval of time that the snapshot should represent
	 * during which the instrument was stable and configured using specific settings and
	 * calibrations, the start_time is the start (left bound of the time interval) while
	 * the end_time specifies the end (right bound) of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStart_time();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval started.
	 * If users wish to specify an interval of time that the snapshot should represent
	 * during which the instrument was stable and configured using specific settings and
	 * calibrations, the start_time is the start (left bound of the time interval) while
	 * the end_time specifies the end (right bound) of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_timeDataset the start_timeDataset
	 */
	public DataNode setStart_time(IDataset start_timeDataset);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval started.
	 * If users wish to specify an interval of time that the snapshot should represent
	 * during which the instrument was stable and configured using specific settings and
	 * calibrations, the start_time is the start (left bound of the time interval) while
	 * the end_time specifies the end (right bound) of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getStart_timeScalar();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval started.
	 * If users wish to specify an interval of time that the snapshot should represent
	 * during which the instrument was stable and configured using specific settings and
	 * calibrations, the start_time is the start (left bound of the time interval) while
	 * the end_time specifies the end (right bound) of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_time the start_time
	 */
	public DataNode setStart_timeScalar(Date start_timeValue);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEnd_time();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_timeDataset the end_timeDataset
	 */
	public DataNode setEnd_time(IDataset end_timeDataset);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getEnd_timeScalar();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_time the end_time
	 */
	public DataNode setEnd_timeScalar(Date end_timeValue);

	/**
	 * Identifier of a specific state and setting of the microscope.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_event();

	/**
	 * Identifier of a specific state and setting of the microscope.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_eventDataset the identifier_eventDataset
	 */
	public DataNode setIdentifier_event(IDataset identifier_eventDataset);

	/**
	 * Identifier of a specific state and setting of the microscope.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifier_eventScalar();

	/**
	 * Identifier of a specific state and setting of the microscope.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_event the identifier_event
	 */
	public DataNode setIdentifier_eventScalar(Long identifier_eventValue);

	/**
	 * The name of the sample to resolve ambiguities.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_sample();

	/**
	 * The name of the sample to resolve ambiguities.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_sampleDataset the identifier_sampleDataset
	 */
	public DataNode setIdentifier_sample(IDataset identifier_sampleDataset);

	/**
	 * The name of the sample to resolve ambiguities.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getIdentifier_sampleScalar();

	/**
	 * The name of the sample to resolve ambiguities.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_sample the identifier_sample
	 */
	public DataNode setIdentifier_sampleScalar(String identifier_sampleValue);

	/**
	 * Which specific event/measurement type. Examples are:
	 * * In-lens/backscattered electron, usually has quadrants
	 * * Secondary_electron, image, topography, fractography, overview images
	 * * Backscattered_electron, image, Z or channeling contrast (ECCI)
	 * * Bright_field, image, TEM
	 * * Dark_field, image, crystal defects
	 * * Annular dark field, image (medium- or high-angle), TEM
	 * * Diffraction, image, TEM, or a comparable technique in the SEM
	 * * Kikuchi, image, SEM EBSD and TEM diffraction
	 * * X-ray spectra (point, line, surface, volume), composition EDS/EDX(S)
	 * * Electron energy loss spectra for points, lines, surfaces, TEM
	 * * Auger, spectrum, (low Z contrast element composition)
	 * * Cathodoluminescence (optical spectra)
	 * * Ronchigram, image, alignment utility specifically in TEM
	 * * Chamber, e.g. TV camera inside the chamber, education purposes.
	 * This field may also be used for storing additional information
	 * about the event for which there is at the moment no other place.
	 * In the long run such free-text field description should be avoided as
	 * it is difficult to machine-interpret. Instead, an enumeration should
	 * be used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Which specific event/measurement type. Examples are:
	 * * In-lens/backscattered electron, usually has quadrants
	 * * Secondary_electron, image, topography, fractography, overview images
	 * * Backscattered_electron, image, Z or channeling contrast (ECCI)
	 * * Bright_field, image, TEM
	 * * Dark_field, image, crystal defects
	 * * Annular dark field, image (medium- or high-angle), TEM
	 * * Diffraction, image, TEM, or a comparable technique in the SEM
	 * * Kikuchi, image, SEM EBSD and TEM diffraction
	 * * X-ray spectra (point, line, surface, volume), composition EDS/EDX(S)
	 * * Electron energy loss spectra for points, lines, surfaces, TEM
	 * * Auger, spectrum, (low Z contrast element composition)
	 * * Cathodoluminescence (optical spectra)
	 * * Ronchigram, image, alignment utility specifically in TEM
	 * * Chamber, e.g. TV camera inside the chamber, education purposes.
	 * This field may also be used for storing additional information
	 * about the event for which there is at the moment no other place.
	 * In the long run such free-text field description should be avoided as
	 * it is difficult to machine-interpret. Instead, an enumeration should
	 * be used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Which specific event/measurement type. Examples are:
	 * * In-lens/backscattered electron, usually has quadrants
	 * * Secondary_electron, image, topography, fractography, overview images
	 * * Backscattered_electron, image, Z or channeling contrast (ECCI)
	 * * Bright_field, image, TEM
	 * * Dark_field, image, crystal defects
	 * * Annular dark field, image (medium- or high-angle), TEM
	 * * Diffraction, image, TEM, or a comparable technique in the SEM
	 * * Kikuchi, image, SEM EBSD and TEM diffraction
	 * * X-ray spectra (point, line, surface, volume), composition EDS/EDX(S)
	 * * Electron energy loss spectra for points, lines, surfaces, TEM
	 * * Auger, spectrum, (low Z contrast element composition)
	 * * Cathodoluminescence (optical spectra)
	 * * Ronchigram, image, alignment utility specifically in TEM
	 * * Chamber, e.g. TV camera inside the chamber, education purposes.
	 * This field may also be used for storing additional information
	 * about the event for which there is at the moment no other place.
	 * In the long run such free-text field description should be avoided as
	 * it is difficult to machine-interpret. Instead, an enumeration should
	 * be used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Which specific event/measurement type. Examples are:
	 * * In-lens/backscattered electron, usually has quadrants
	 * * Secondary_electron, image, topography, fractography, overview images
	 * * Backscattered_electron, image, Z or channeling contrast (ECCI)
	 * * Bright_field, image, TEM
	 * * Dark_field, image, crystal defects
	 * * Annular dark field, image (medium- or high-angle), TEM
	 * * Diffraction, image, TEM, or a comparable technique in the SEM
	 * * Kikuchi, image, SEM EBSD and TEM diffraction
	 * * X-ray spectra (point, line, surface, volume), composition EDS/EDX(S)
	 * * Electron energy loss spectra for points, lines, surfaces, TEM
	 * * Auger, spectrum, (low Z contrast element composition)
	 * * Cathodoluminescence (optical spectra)
	 * * Ronchigram, image, alignment utility specifically in TEM
	 * * Chamber, e.g. TV camera inside the chamber, education purposes.
	 * This field may also be used for storing additional information
	 * about the event for which there is at the moment no other place.
	 * In the long run such free-text field description should be avoided as
	 * it is difficult to machine-interpret. Instead, an enumeration should
	 * be used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXuser getUser();

	/**
	 *
	 * @param userGroup the userGroup
	 */
	public void setUser(NXuser userGroup);

	/**
	 * Get a NXuser node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXuser for that node.
	 */
	public NXuser getUser(String name);

	/**
	 * Set a NXuser node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param user the value to set
	 */
	public void setUser(String name, NXuser user);

	/**
	 * Get all NXuser nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXuser for that node.
	 */
	public Map<String, NXuser> getAllUser();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param user the child nodes to add
	 */

	public void setAllUser(Map<String, NXuser> user);


	/**
	 *
	 * @return  the value.
	 */
	public NXem_instrument getEm_instrument();

	/**
	 *
	 * @param em_instrumentGroup the em_instrumentGroup
	 */
	public void setEm_instrument(NXem_instrument em_instrumentGroup);

	/**
	 * Get a NXem_instrument node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXem_instrument for that node.
	 */
	public NXem_instrument getEm_instrument(String name);

	/**
	 * Set a NXem_instrument node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param em_instrument the value to set
	 */
	public void setEm_instrument(String name, NXem_instrument em_instrument);

	/**
	 * Get all NXem_instrument nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXem_instrument for that node.
	 */
	public Map<String, NXem_instrument> getAllEm_instrument();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param em_instrument the child nodes to add
	 */

	public void setAllEm_instrument(Map<String, NXem_instrument> em_instrument);


	/**
	 *
	 * @return  the value.
	 */
	public NXimage getImage();

	/**
	 *
	 * @param imageGroup the imageGroup
	 */
	public void setImage(NXimage imageGroup);

	/**
	 * Get a NXimage node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage for that node.
	 */
	public NXimage getImage(String name);

	/**
	 * Set a NXimage node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param image the value to set
	 */
	public void setImage(String name, NXimage image);

	/**
	 * Get all NXimage nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXimage for that node.
	 */
	public Map<String, NXimage> getAllImage();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param image the child nodes to add
	 */

	public void setAllImage(Map<String, NXimage> image);


	/**
	 *
	 * @return  the value.
	 */
	public NXspectrum getSpectrum();

	/**
	 *
	 * @param spectrumGroup the spectrumGroup
	 */
	public void setSpectrum(NXspectrum spectrumGroup);

	/**
	 * Get a NXspectrum node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXspectrum for that node.
	 */
	public NXspectrum getSpectrum(String name);

	/**
	 * Set a NXspectrum node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param spectrum the value to set
	 */
	public void setSpectrum(String name, NXspectrum spectrum);

	/**
	 * Get all NXspectrum nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXspectrum for that node.
	 */
	public Map<String, NXspectrum> getAllSpectrum();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param spectrum the child nodes to add
	 */

	public void setAllSpectrum(Map<String, NXspectrum> spectrum);


}
