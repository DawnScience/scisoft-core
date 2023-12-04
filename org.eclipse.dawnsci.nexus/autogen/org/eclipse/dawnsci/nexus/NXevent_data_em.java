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
public interface NXevent_data_em extends NXobject {

	public static final String NX_START_TIME = "start_time";
	public static final String NX_END_TIME = "end_time";
	public static final String NX_EVENT_IDENTIFIER = "event_identifier";
	public static final String NX_EVENT_TYPE = "event_type";
	public static final String NX_DETECTOR_IDENTIFIER = "detector_identifier";
	/**
	 * ISO 8601 time code with local time zone offset to UTC information included when the snapshot time interval started.
	 * If the user wishes to specify an interval of time that the snapshot should represent during which the
	 * instrument was stable and configured using specific settings and calibrations, the start_time is the
	 * start (left bound of the time interval) while the end_time specifies the end (right bound) of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getStart_time();
	
	/**
	 * ISO 8601 time code with local time zone offset to UTC information included when the snapshot time interval started.
	 * If the user wishes to specify an interval of time that the snapshot should represent during which the
	 * instrument was stable and configured using specific settings and calibrations, the start_time is the
	 * start (left bound of the time interval) while the end_time specifies the end (right bound) of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param start_timeDataset the start_timeDataset
	 */
	public DataNode setStart_time(IDataset start_timeDataset);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included when the snapshot time interval started.
	 * If the user wishes to specify an interval of time that the snapshot should represent during which the
	 * instrument was stable and configured using specific settings and calibrations, the start_time is the
	 * start (left bound of the time interval) while the end_time specifies the end (right bound) of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Date getStart_timeScalar();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included when the snapshot time interval started.
	 * If the user wishes to specify an interval of time that the snapshot should represent during which the
	 * instrument was stable and configured using specific settings and calibrations, the start_time is the
	 * start (left bound of the time interval) while the end_time specifies the end (right bound) of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param start_time the start_time
	 */
	public DataNode setStart_timeScalar(Date start_timeValue);

	/**
	 * ISO 8601 time code with local time zone offset to UTC included when the snapshot time interval ended.
	 * If the user does not wish to specify a time interval, end_time should have the same value as start_time.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEnd_time();
	
	/**
	 * ISO 8601 time code with local time zone offset to UTC included when the snapshot time interval ended.
	 * If the user does not wish to specify a time interval, end_time should have the same value as start_time.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param end_timeDataset the end_timeDataset
	 */
	public DataNode setEnd_time(IDataset end_timeDataset);

	/**
	 * ISO 8601 time code with local time zone offset to UTC included when the snapshot time interval ended.
	 * If the user does not wish to specify a time interval, end_time should have the same value as start_time.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Date getEnd_timeScalar();

	/**
	 * ISO 8601 time code with local time zone offset to UTC included when the snapshot time interval ended.
	 * If the user does not wish to specify a time interval, end_time should have the same value as start_time.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param end_time the end_time
	 */
	public DataNode setEnd_timeScalar(Date end_timeValue);

	/**
	 * Reference to a specific state and setting of the microscope components.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEvent_identifier();
	
	/**
	 * Reference to a specific state and setting of the microscope components.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param event_identifierDataset the event_identifierDataset
	 */
	public DataNode setEvent_identifier(IDataset event_identifierDataset);

	/**
	 * Reference to a specific state and setting of the microscope components.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getEvent_identifierScalar();

	/**
	 * Reference to a specific state and setting of the microscope components.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
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
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
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
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
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
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
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
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param event_type the event_type
	 */
	public DataNode setEvent_typeScalar(String event_typeValue);

	/**
	 * The detector or set of detectors that was used to collect this signal.
	 * The name of the detector has to match the names used for available
	 * detectors, i.e. if the instrument has an *ebsd_camera*
	 * named detector, instances of NXimage_em_kikuchi should use
	 * *ebsd_camera* as the detector name.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDetector_identifier();
	
	/**
	 * The detector or set of detectors that was used to collect this signal.
	 * The name of the detector has to match the names used for available
	 * detectors, i.e. if the instrument has an *ebsd_camera*
	 * named detector, instances of NXimage_em_kikuchi should use
	 * *ebsd_camera* as the detector name.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param detector_identifierDataset the detector_identifierDataset
	 */
	public DataNode setDetector_identifier(IDataset detector_identifierDataset);

	/**
	 * The detector or set of detectors that was used to collect this signal.
	 * The name of the detector has to match the names used for available
	 * detectors, i.e. if the instrument has an *ebsd_camera*
	 * named detector, instances of NXimage_em_kikuchi should use
	 * *ebsd_camera* as the detector name.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDetector_identifierScalar();

	/**
	 * The detector or set of detectors that was used to collect this signal.
	 * The name of the detector has to match the names used for available
	 * detectors, i.e. if the instrument has an *ebsd_camera*
	 * named detector, instances of NXimage_em_kikuchi should use
	 * *ebsd_camera* as the detector name.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param detector_identifier the detector_identifier
	 */
	public DataNode setDetector_identifierScalar(String detector_identifierValue);

	/**
	 * 
	 * @return  the value.
	 */
	public NXimage_set_em_se getImage_set_em_se();
	
	/**
	 * 
	 * @param image_set_em_seGroup the image_set_em_seGroup
	 */
	public void setImage_set_em_se(NXimage_set_em_se image_set_em_seGroup);

	/**
	 * Get a NXimage_set_em_se node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set_em_se for that node.
	 */
	public NXimage_set_em_se getImage_set_em_se(String name);
	
	/**
	 * Set a NXimage_set_em_se node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param image_set_em_se the value to set
	 */
	public void setImage_set_em_se(String name, NXimage_set_em_se image_set_em_se);
	
	/**
	 * Get all NXimage_set_em_se nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXimage_set_em_se for that node.
	 */
	public Map<String, NXimage_set_em_se> getAllImage_set_em_se();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param image_set_em_se the child nodes to add 
	 */
	
	public void setAllImage_set_em_se(Map<String, NXimage_set_em_se> image_set_em_se);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXimage_set_em_bse getImage_set_em_bse();
	
	/**
	 * 
	 * @param image_set_em_bseGroup the image_set_em_bseGroup
	 */
	public void setImage_set_em_bse(NXimage_set_em_bse image_set_em_bseGroup);

	/**
	 * Get a NXimage_set_em_bse node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set_em_bse for that node.
	 */
	public NXimage_set_em_bse getImage_set_em_bse(String name);
	
	/**
	 * Set a NXimage_set_em_bse node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param image_set_em_bse the value to set
	 */
	public void setImage_set_em_bse(String name, NXimage_set_em_bse image_set_em_bse);
	
	/**
	 * Get all NXimage_set_em_bse nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXimage_set_em_bse for that node.
	 */
	public Map<String, NXimage_set_em_bse> getAllImage_set_em_bse();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param image_set_em_bse the child nodes to add 
	 */
	
	public void setAllImage_set_em_bse(Map<String, NXimage_set_em_bse> image_set_em_bse);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXimage_set_em_ecci getImage_set_em_ecci();
	
	/**
	 * 
	 * @param image_set_em_ecciGroup the image_set_em_ecciGroup
	 */
	public void setImage_set_em_ecci(NXimage_set_em_ecci image_set_em_ecciGroup);

	/**
	 * Get a NXimage_set_em_ecci node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set_em_ecci for that node.
	 */
	public NXimage_set_em_ecci getImage_set_em_ecci(String name);
	
	/**
	 * Set a NXimage_set_em_ecci node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param image_set_em_ecci the value to set
	 */
	public void setImage_set_em_ecci(String name, NXimage_set_em_ecci image_set_em_ecci);
	
	/**
	 * Get all NXimage_set_em_ecci nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXimage_set_em_ecci for that node.
	 */
	public Map<String, NXimage_set_em_ecci> getAllImage_set_em_ecci();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param image_set_em_ecci the child nodes to add 
	 */
	
	public void setAllImage_set_em_ecci(Map<String, NXimage_set_em_ecci> image_set_em_ecci);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXimage_set_em_bf getImage_set_em_bf();
	
	/**
	 * 
	 * @param image_set_em_bfGroup the image_set_em_bfGroup
	 */
	public void setImage_set_em_bf(NXimage_set_em_bf image_set_em_bfGroup);

	/**
	 * Get a NXimage_set_em_bf node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set_em_bf for that node.
	 */
	public NXimage_set_em_bf getImage_set_em_bf(String name);
	
	/**
	 * Set a NXimage_set_em_bf node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param image_set_em_bf the value to set
	 */
	public void setImage_set_em_bf(String name, NXimage_set_em_bf image_set_em_bf);
	
	/**
	 * Get all NXimage_set_em_bf nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXimage_set_em_bf for that node.
	 */
	public Map<String, NXimage_set_em_bf> getAllImage_set_em_bf();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param image_set_em_bf the child nodes to add 
	 */
	
	public void setAllImage_set_em_bf(Map<String, NXimage_set_em_bf> image_set_em_bf);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXimage_set_em_df getImage_set_em_df();
	
	/**
	 * 
	 * @param image_set_em_dfGroup the image_set_em_dfGroup
	 */
	public void setImage_set_em_df(NXimage_set_em_df image_set_em_dfGroup);

	/**
	 * Get a NXimage_set_em_df node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set_em_df for that node.
	 */
	public NXimage_set_em_df getImage_set_em_df(String name);
	
	/**
	 * Set a NXimage_set_em_df node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param image_set_em_df the value to set
	 */
	public void setImage_set_em_df(String name, NXimage_set_em_df image_set_em_df);
	
	/**
	 * Get all NXimage_set_em_df nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXimage_set_em_df for that node.
	 */
	public Map<String, NXimage_set_em_df> getAllImage_set_em_df();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param image_set_em_df the child nodes to add 
	 */
	
	public void setAllImage_set_em_df(Map<String, NXimage_set_em_df> image_set_em_df);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXimage_set_em_adf getImage_set_em_adf();
	
	/**
	 * 
	 * @param image_set_em_adfGroup the image_set_em_adfGroup
	 */
	public void setImage_set_em_adf(NXimage_set_em_adf image_set_em_adfGroup);

	/**
	 * Get a NXimage_set_em_adf node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set_em_adf for that node.
	 */
	public NXimage_set_em_adf getImage_set_em_adf(String name);
	
	/**
	 * Set a NXimage_set_em_adf node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param image_set_em_adf the value to set
	 */
	public void setImage_set_em_adf(String name, NXimage_set_em_adf image_set_em_adf);
	
	/**
	 * Get all NXimage_set_em_adf nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXimage_set_em_adf for that node.
	 */
	public Map<String, NXimage_set_em_adf> getAllImage_set_em_adf();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param image_set_em_adf the child nodes to add 
	 */
	
	public void setAllImage_set_em_adf(Map<String, NXimage_set_em_adf> image_set_em_adf);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXimage_set_em_kikuchi getImage_set_em_kikuchi();
	
	/**
	 * 
	 * @param image_set_em_kikuchiGroup the image_set_em_kikuchiGroup
	 */
	public void setImage_set_em_kikuchi(NXimage_set_em_kikuchi image_set_em_kikuchiGroup);

	/**
	 * Get a NXimage_set_em_kikuchi node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set_em_kikuchi for that node.
	 */
	public NXimage_set_em_kikuchi getImage_set_em_kikuchi(String name);
	
	/**
	 * Set a NXimage_set_em_kikuchi node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param image_set_em_kikuchi the value to set
	 */
	public void setImage_set_em_kikuchi(String name, NXimage_set_em_kikuchi image_set_em_kikuchi);
	
	/**
	 * Get all NXimage_set_em_kikuchi nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXimage_set_em_kikuchi for that node.
	 */
	public Map<String, NXimage_set_em_kikuchi> getAllImage_set_em_kikuchi();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param image_set_em_kikuchi the child nodes to add 
	 */
	
	public void setAllImage_set_em_kikuchi(Map<String, NXimage_set_em_kikuchi> image_set_em_kikuchi);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXimage_set_em_diffrac getImage_set_em_diffrac();
	
	/**
	 * 
	 * @param image_set_em_diffracGroup the image_set_em_diffracGroup
	 */
	public void setImage_set_em_diffrac(NXimage_set_em_diffrac image_set_em_diffracGroup);

	/**
	 * Get a NXimage_set_em_diffrac node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set_em_diffrac for that node.
	 */
	public NXimage_set_em_diffrac getImage_set_em_diffrac(String name);
	
	/**
	 * Set a NXimage_set_em_diffrac node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param image_set_em_diffrac the value to set
	 */
	public void setImage_set_em_diffrac(String name, NXimage_set_em_diffrac image_set_em_diffrac);
	
	/**
	 * Get all NXimage_set_em_diffrac nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXimage_set_em_diffrac for that node.
	 */
	public Map<String, NXimage_set_em_diffrac> getAllImage_set_em_diffrac();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param image_set_em_diffrac the child nodes to add 
	 */
	
	public void setAllImage_set_em_diffrac(Map<String, NXimage_set_em_diffrac> image_set_em_diffrac);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXspectrum_set_em_xray getSpectrum_set_em_xray();
	
	/**
	 * 
	 * @param spectrum_set_em_xrayGroup the spectrum_set_em_xrayGroup
	 */
	public void setSpectrum_set_em_xray(NXspectrum_set_em_xray spectrum_set_em_xrayGroup);

	/**
	 * Get a NXspectrum_set_em_xray node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXspectrum_set_em_xray for that node.
	 */
	public NXspectrum_set_em_xray getSpectrum_set_em_xray(String name);
	
	/**
	 * Set a NXspectrum_set_em_xray node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param spectrum_set_em_xray the value to set
	 */
	public void setSpectrum_set_em_xray(String name, NXspectrum_set_em_xray spectrum_set_em_xray);
	
	/**
	 * Get all NXspectrum_set_em_xray nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXspectrum_set_em_xray for that node.
	 */
	public Map<String, NXspectrum_set_em_xray> getAllSpectrum_set_em_xray();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param spectrum_set_em_xray the child nodes to add 
	 */
	
	public void setAllSpectrum_set_em_xray(Map<String, NXspectrum_set_em_xray> spectrum_set_em_xray);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXspectrum_set_em_eels getSpectrum_set_em_eels();
	
	/**
	 * 
	 * @param spectrum_set_em_eelsGroup the spectrum_set_em_eelsGroup
	 */
	public void setSpectrum_set_em_eels(NXspectrum_set_em_eels spectrum_set_em_eelsGroup);

	/**
	 * Get a NXspectrum_set_em_eels node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXspectrum_set_em_eels for that node.
	 */
	public NXspectrum_set_em_eels getSpectrum_set_em_eels(String name);
	
	/**
	 * Set a NXspectrum_set_em_eels node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param spectrum_set_em_eels the value to set
	 */
	public void setSpectrum_set_em_eels(String name, NXspectrum_set_em_eels spectrum_set_em_eels);
	
	/**
	 * Get all NXspectrum_set_em_eels nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXspectrum_set_em_eels for that node.
	 */
	public Map<String, NXspectrum_set_em_eels> getAllSpectrum_set_em_eels();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param spectrum_set_em_eels the child nodes to add 
	 */
	
	public void setAllSpectrum_set_em_eels(Map<String, NXspectrum_set_em_eels> spectrum_set_em_eels);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXspectrum_set_em_auger getSpectrum_set_em_auger();
	
	/**
	 * 
	 * @param spectrum_set_em_augerGroup the spectrum_set_em_augerGroup
	 */
	public void setSpectrum_set_em_auger(NXspectrum_set_em_auger spectrum_set_em_augerGroup);

	/**
	 * Get a NXspectrum_set_em_auger node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXspectrum_set_em_auger for that node.
	 */
	public NXspectrum_set_em_auger getSpectrum_set_em_auger(String name);
	
	/**
	 * Set a NXspectrum_set_em_auger node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param spectrum_set_em_auger the value to set
	 */
	public void setSpectrum_set_em_auger(String name, NXspectrum_set_em_auger spectrum_set_em_auger);
	
	/**
	 * Get all NXspectrum_set_em_auger nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXspectrum_set_em_auger for that node.
	 */
	public Map<String, NXspectrum_set_em_auger> getAllSpectrum_set_em_auger();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param spectrum_set_em_auger the child nodes to add 
	 */
	
	public void setAllSpectrum_set_em_auger(Map<String, NXspectrum_set_em_auger> spectrum_set_em_auger);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXspectrum_set_em_cathodolum getSpectrum_set_em_cathodolum();
	
	/**
	 * 
	 * @param spectrum_set_em_cathodolumGroup the spectrum_set_em_cathodolumGroup
	 */
	public void setSpectrum_set_em_cathodolum(NXspectrum_set_em_cathodolum spectrum_set_em_cathodolumGroup);

	/**
	 * Get a NXspectrum_set_em_cathodolum node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXspectrum_set_em_cathodolum for that node.
	 */
	public NXspectrum_set_em_cathodolum getSpectrum_set_em_cathodolum(String name);
	
	/**
	 * Set a NXspectrum_set_em_cathodolum node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param spectrum_set_em_cathodolum the value to set
	 */
	public void setSpectrum_set_em_cathodolum(String name, NXspectrum_set_em_cathodolum spectrum_set_em_cathodolum);
	
	/**
	 * Get all NXspectrum_set_em_cathodolum nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXspectrum_set_em_cathodolum for that node.
	 */
	public Map<String, NXspectrum_set_em_cathodolum> getAllSpectrum_set_em_cathodolum();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param spectrum_set_em_cathodolum the child nodes to add 
	 */
	
	public void setAllSpectrum_set_em_cathodolum(Map<String, NXspectrum_set_em_cathodolum> spectrum_set_em_cathodolum);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXimage_set_em_ronchigram getImage_set_em_ronchigram();
	
	/**
	 * 
	 * @param image_set_em_ronchigramGroup the image_set_em_ronchigramGroup
	 */
	public void setImage_set_em_ronchigram(NXimage_set_em_ronchigram image_set_em_ronchigramGroup);

	/**
	 * Get a NXimage_set_em_ronchigram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set_em_ronchigram for that node.
	 */
	public NXimage_set_em_ronchigram getImage_set_em_ronchigram(String name);
	
	/**
	 * Set a NXimage_set_em_ronchigram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param image_set_em_ronchigram the value to set
	 */
	public void setImage_set_em_ronchigram(String name, NXimage_set_em_ronchigram image_set_em_ronchigram);
	
	/**
	 * Get all NXimage_set_em_ronchigram nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXimage_set_em_ronchigram for that node.
	 */
	public Map<String, NXimage_set_em_ronchigram> getAllImage_set_em_ronchigram();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param image_set_em_ronchigram the child nodes to add 
	 */
	
	public void setAllImage_set_em_ronchigram(Map<String, NXimage_set_em_ronchigram> image_set_em_ronchigram);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXimage_set_em_chamber getImage_set_em_chamber();
	
	/**
	 * 
	 * @param image_set_em_chamberGroup the image_set_em_chamberGroup
	 */
	public void setImage_set_em_chamber(NXimage_set_em_chamber image_set_em_chamberGroup);

	/**
	 * Get a NXimage_set_em_chamber node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage_set_em_chamber for that node.
	 */
	public NXimage_set_em_chamber getImage_set_em_chamber(String name);
	
	/**
	 * Set a NXimage_set_em_chamber node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param image_set_em_chamber the value to set
	 */
	public void setImage_set_em_chamber(String name, NXimage_set_em_chamber image_set_em_chamber);
	
	/**
	 * Get all NXimage_set_em_chamber nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXimage_set_em_chamber for that node.
	 */
	public Map<String, NXimage_set_em_chamber> getAllImage_set_em_chamber();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param image_set_em_chamber the child nodes to add 
	 */
	
	public void setAllImage_set_em_chamber(Map<String, NXimage_set_em_chamber> image_set_em_chamber);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXebeam_column getEbeam_column();
	
	/**
	 * 
	 * @param ebeam_columnGroup the ebeam_columnGroup
	 */
	public void setEbeam_column(NXebeam_column ebeam_columnGroup);

	/**
	 * Get a NXebeam_column node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXebeam_column for that node.
	 */
	public NXebeam_column getEbeam_column(String name);
	
	/**
	 * Set a NXebeam_column node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param ebeam_column the value to set
	 */
	public void setEbeam_column(String name, NXebeam_column ebeam_column);
	
	/**
	 * Get all NXebeam_column nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXebeam_column for that node.
	 */
	public Map<String, NXebeam_column> getAllEbeam_column();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param ebeam_column the child nodes to add 
	 */
	
	public void setAllEbeam_column(Map<String, NXebeam_column> ebeam_column);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXibeam_column getIbeam_column();
	
	/**
	 * 
	 * @param ibeam_columnGroup the ibeam_columnGroup
	 */
	public void setIbeam_column(NXibeam_column ibeam_columnGroup);

	/**
	 * Get a NXibeam_column node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXibeam_column for that node.
	 */
	public NXibeam_column getIbeam_column(String name);
	
	/**
	 * Set a NXibeam_column node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param ibeam_column the value to set
	 */
	public void setIbeam_column(String name, NXibeam_column ibeam_column);
	
	/**
	 * Get all NXibeam_column nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXibeam_column for that node.
	 */
	public Map<String, NXibeam_column> getAllIbeam_column();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param ibeam_column the child nodes to add 
	 */
	
	public void setAllIbeam_column(Map<String, NXibeam_column> ibeam_column);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXscanbox_em getEbeam_deflector();
	
	/**
	 * 
	 * @param ebeam_deflectorGroup the ebeam_deflectorGroup
	 */
	public void setEbeam_deflector(NXscanbox_em ebeam_deflectorGroup);

	/**
	 * 
	 * @return  the value.
	 */
	public NXscanbox_em getIbeam_deflector();
	
	/**
	 * 
	 * @param ibeam_deflectorGroup the ibeam_deflectorGroup
	 */
	public void setIbeam_deflector(NXscanbox_em ibeam_deflectorGroup);

	/**
	 * 
	 * @return  the value.
	 */
	public NXoptical_system_em getOptical_system_em();
	
	/**
	 * 
	 * @param optical_system_emGroup the optical_system_emGroup
	 */
	public void setOptical_system_em(NXoptical_system_em optical_system_emGroup);

	/**
	 * Get a NXoptical_system_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXoptical_system_em for that node.
	 */
	public NXoptical_system_em getOptical_system_em(String name);
	
	/**
	 * Set a NXoptical_system_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param optical_system_em the value to set
	 */
	public void setOptical_system_em(String name, NXoptical_system_em optical_system_em);
	
	/**
	 * Get all NXoptical_system_em nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXoptical_system_em for that node.
	 */
	public Map<String, NXoptical_system_em> getAllOptical_system_em();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param optical_system_em the child nodes to add 
	 */
	
	public void setAllOptical_system_em(Map<String, NXoptical_system_em> optical_system_em);
	

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
	

}
