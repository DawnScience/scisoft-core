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
 *
 */
public interface NXevent_data_em extends NXobject {

	public static final String NX_START_TIME = "start_time";
	public static final String NX_END_TIME = "end_time";
	public static final String NX_EVENT_IDENTIFIER = "event_identifier";
	public static final String NX_EVENT_TYPE = "event_type";
	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval started. If the user wishes to specify an
	 * interval of time that the snapshot should represent during which the instrument
	 * was stable and configured using specific settings and calibrations,
	 * the start_time is the start (left bound of the time interval) while
	 * the end_time specifies the end (right bound) of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getStart_time();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval started. If the user wishes to specify an
	 * interval of time that the snapshot should represent during which the instrument
	 * was stable and configured using specific settings and calibrations,
	 * the start_time is the start (left bound of the time interval) while
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
	 * when the snapshot time interval started. If the user wishes to specify an
	 * interval of time that the snapshot should represent during which the instrument
	 * was stable and configured using specific settings and calibrations,
	 * the start_time is the start (left bound of the time interval) while
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
	 * when the snapshot time interval started. If the user wishes to specify an
	 * interval of time that the snapshot should represent during which the instrument
	 * was stable and configured using specific settings and calibrations,
	 * the start_time is the start (left bound of the time interval) while
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
	public IDataset getEnd_time();

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
	 * Reference to a specific state and setting of the microscope.
	 *
	 * @return  the value.
	 */
	public IDataset getEvent_identifier();

	/**
	 * Reference to a specific state and setting of the microscope.
	 *
	 * @param event_identifierDataset the event_identifierDataset
	 */
	public DataNode setEvent_identifier(IDataset event_identifierDataset);

	/**
	 * Reference to a specific state and setting of the microscope.
	 *
	 * @return  the value.
	 */
	public String getEvent_identifierScalar();

	/**
	 * Reference to a specific state and setting of the microscope.
	 *
	 * @param event_identifier the event_identifier
	 */
	public DataNode setEvent_identifierScalar(String event_identifierValue);

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
	 * This field may also be used for storing additional information about the event.
	 *
	 * @return  the value.
	 */
	public IDataset getEvent_type();

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
	 * This field may also be used for storing additional information about the event.
	 *
	 * @param event_typeDataset the event_typeDataset
	 */
	public DataNode setEvent_type(IDataset event_typeDataset);

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
	 * This field may also be used for storing additional information about the event.
	 *
	 * @return  the value.
	 */
	public String getEvent_typeScalar();

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
	 * This field may also be used for storing additional information about the event.
	 *
	 * @param event_type the event_type
	 */
	public DataNode setEvent_typeScalar(String event_typeValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXimage_set getImage_set();

	/**
	 *
	 * @param image_setGroup the image_setGroup
	 */
	public void setImage_set(NXimage_set image_setGroup);

	/**
	 * Get a NXimage_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set for that node.
	 */
	public NXimage_set getImage_set(String name);

	/**
	 * Set a NXimage_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param image_set the value to set
	 */
	public void setImage_set(String name, NXimage_set image_set);

	/**
	 * Get all NXimage_set nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXimage_set for that node.
	 */
	public Map<String, NXimage_set> getAllImage_set();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param image_set the child nodes to add
	 */

	public void setAllImage_set(Map<String, NXimage_set> image_set);


	/**
	 *
	 * @return  the value.
	 */
	public NXspectrum_set getSpectrum_set();

	/**
	 *
	 * @param spectrum_setGroup the spectrum_setGroup
	 */
	public void setSpectrum_set(NXspectrum_set spectrum_setGroup);

	/**
	 * Get a NXspectrum_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXspectrum_set for that node.
	 */
	public NXspectrum_set getSpectrum_set(String name);

	/**
	 * Set a NXspectrum_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param spectrum_set the value to set
	 */
	public void setSpectrum_set(String name, NXspectrum_set spectrum_set);

	/**
	 * Get all NXspectrum_set nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXspectrum_set for that node.
	 */
	public Map<String, NXspectrum_set> getAllSpectrum_set();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param spectrum_set the child nodes to add
	 */

	public void setAllSpectrum_set(Map<String, NXspectrum_set> spectrum_set);


	/**
	 *
	 * @return  the value.
	 */
	public NXinstrument getInstrument();

	/**
	 *
	 * @param instrumentGroup the instrumentGroup
	 */
	public void setInstrument(NXinstrument instrumentGroup);

	/**
	 * Get a NXinstrument node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXinstrument for that node.
	 */
	public NXinstrument getInstrument(String name);

	/**
	 * Set a NXinstrument node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param instrument the value to set
	 */
	public void setInstrument(String name, NXinstrument instrument);

	/**
	 * Get all NXinstrument nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXinstrument for that node.
	 */
	public Map<String, NXinstrument> getAllInstrument();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param instrument the child nodes to add
	 */

	public void setAllInstrument(Map<String, NXinstrument> instrument);


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
	public NXinteraction_vol_em getInteraction_vol_em();

	/**
	 *
	 * @param interaction_vol_emGroup the interaction_vol_emGroup
	 */
	public void setInteraction_vol_em(NXinteraction_vol_em interaction_vol_emGroup);

	/**
	 * Get a NXinteraction_vol_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXinteraction_vol_em for that node.
	 */
	public NXinteraction_vol_em getInteraction_vol_em(String name);

	/**
	 * Set a NXinteraction_vol_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param interaction_vol_em the value to set
	 */
	public void setInteraction_vol_em(String name, NXinteraction_vol_em interaction_vol_em);

	/**
	 * Get all NXinteraction_vol_em nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXinteraction_vol_em for that node.
	 */
	public Map<String, NXinteraction_vol_em> getAllInteraction_vol_em();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param interaction_vol_em the child nodes to add
	 */

	public void setAllInteraction_vol_em(Map<String, NXinteraction_vol_em> interaction_vol_em);


}
