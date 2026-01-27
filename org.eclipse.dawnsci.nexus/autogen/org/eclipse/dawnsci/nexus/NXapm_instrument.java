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

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Base class for instrument-related details of a real or simulated
 * atom probe tomograph or field-ion microscope.
 * For collecting data and experiments which are simulations of an atom probe
 * microscope or a session with such instrument use the :ref:`NXapm` application definition
 * and the :ref:`NXapm_event_data` groups it provides.
 * This base class implements the concept of :ref:`NXapm` whereby (meta)data are distinguished
 * whether these typically change during a session, so-called dynamic, or not, so-called static metadata.
 * This design allows to store e.g. hardware related concepts only once instead of demanding
 * that each image or spectrum from the session needs to be stored also with the static metadata.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>p</b>
 * Number of pulses collected in between start_time and end_time
 * inside a parent instance of :ref:`NXapm_event_data`.</li></ul></p>
 *
 */
public interface NXapm_instrument extends NXinstrument {

	public static final String NX_TYPE = "type";
	public static final String NX_LOCATION = "location";
	public static final String NX_FLIGHT_PATH = "flight_path";
	public static final String NX_COMMENT = "comment";
	/**
	 * Which type of instrument.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Inspico</b> </li>
	 * <li><b>3DAP</b> </li>
	 * <li><b>LAWATAP</b> </li>
	 * <li><b>LEAP 3000 Si</b> </li>
	 * <li><b>LEAP 3000X Si</b> </li>
	 * <li><b>LEAP 3000 HR</b> </li>
	 * <li><b>LEAP 3000X HR</b> </li>
	 * <li><b>LEAP 4000 Si</b> </li>
	 * <li><b>LEAP 4000X Si</b> </li>
	 * <li><b>LEAP 4000 HR</b> </li>
	 * <li><b>LEAP 4000X HR</b> </li>
	 * <li><b>LEAP 5000 XS</b> </li>
	 * <li><b>LEAP 5000 XR</b> </li>
	 * <li><b>LEAP 5000 R</b> </li>
	 * <li><b>EIKOS</b> </li>
	 * <li><b>EIKOS-UV</b> </li>
	 * <li><b>LEAP 6000 XR</b> </li>
	 * <li><b>LEAP INVIZO</b> </li>
	 * <li><b>Photonic AP</b> </li>
	 * <li><b>TeraSAT</b> </li>
	 * <li><b>TAPHR</b> </li>
	 * <li><b>Modular AP</b> </li>
	 * <li><b>Titanium APT</b> </li>
	 * <li><b>Extreme UV APT</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Which type of instrument.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Inspico</b> </li>
	 * <li><b>3DAP</b> </li>
	 * <li><b>LAWATAP</b> </li>
	 * <li><b>LEAP 3000 Si</b> </li>
	 * <li><b>LEAP 3000X Si</b> </li>
	 * <li><b>LEAP 3000 HR</b> </li>
	 * <li><b>LEAP 3000X HR</b> </li>
	 * <li><b>LEAP 4000 Si</b> </li>
	 * <li><b>LEAP 4000X Si</b> </li>
	 * <li><b>LEAP 4000 HR</b> </li>
	 * <li><b>LEAP 4000X HR</b> </li>
	 * <li><b>LEAP 5000 XS</b> </li>
	 * <li><b>LEAP 5000 XR</b> </li>
	 * <li><b>LEAP 5000 R</b> </li>
	 * <li><b>EIKOS</b> </li>
	 * <li><b>EIKOS-UV</b> </li>
	 * <li><b>LEAP 6000 XR</b> </li>
	 * <li><b>LEAP INVIZO</b> </li>
	 * <li><b>Photonic AP</b> </li>
	 * <li><b>TeraSAT</b> </li>
	 * <li><b>TAPHR</b> </li>
	 * <li><b>Modular AP</b> </li>
	 * <li><b>Titanium APT</b> </li>
	 * <li><b>Extreme UV APT</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Which type of instrument.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Inspico</b> </li>
	 * <li><b>3DAP</b> </li>
	 * <li><b>LAWATAP</b> </li>
	 * <li><b>LEAP 3000 Si</b> </li>
	 * <li><b>LEAP 3000X Si</b> </li>
	 * <li><b>LEAP 3000 HR</b> </li>
	 * <li><b>LEAP 3000X HR</b> </li>
	 * <li><b>LEAP 4000 Si</b> </li>
	 * <li><b>LEAP 4000X Si</b> </li>
	 * <li><b>LEAP 4000 HR</b> </li>
	 * <li><b>LEAP 4000X HR</b> </li>
	 * <li><b>LEAP 5000 XS</b> </li>
	 * <li><b>LEAP 5000 XR</b> </li>
	 * <li><b>LEAP 5000 R</b> </li>
	 * <li><b>EIKOS</b> </li>
	 * <li><b>EIKOS-UV</b> </li>
	 * <li><b>LEAP 6000 XR</b> </li>
	 * <li><b>LEAP INVIZO</b> </li>
	 * <li><b>Photonic AP</b> </li>
	 * <li><b>TeraSAT</b> </li>
	 * <li><b>TAPHR</b> </li>
	 * <li><b>Modular AP</b> </li>
	 * <li><b>Titanium APT</b> </li>
	 * <li><b>Extreme UV APT</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Which type of instrument.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Inspico</b> </li>
	 * <li><b>3DAP</b> </li>
	 * <li><b>LAWATAP</b> </li>
	 * <li><b>LEAP 3000 Si</b> </li>
	 * <li><b>LEAP 3000X Si</b> </li>
	 * <li><b>LEAP 3000 HR</b> </li>
	 * <li><b>LEAP 3000X HR</b> </li>
	 * <li><b>LEAP 4000 Si</b> </li>
	 * <li><b>LEAP 4000X Si</b> </li>
	 * <li><b>LEAP 4000 HR</b> </li>
	 * <li><b>LEAP 4000X HR</b> </li>
	 * <li><b>LEAP 5000 XS</b> </li>
	 * <li><b>LEAP 5000 XR</b> </li>
	 * <li><b>LEAP 5000 R</b> </li>
	 * <li><b>EIKOS</b> </li>
	 * <li><b>EIKOS-UV</b> </li>
	 * <li><b>LEAP 6000 XR</b> </li>
	 * <li><b>LEAP INVIZO</b> </li>
	 * <li><b>Photonic AP</b> </li>
	 * <li><b>TeraSAT</b> </li>
	 * <li><b>TAPHR</b> </li>
	 * <li><b>Modular AP</b> </li>
	 * <li><b>Titanium APT</b> </li>
	 * <li><b>Extreme UV APT</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXfabrication getFabrication();

	/**
	 *
	 * @param fabricationGroup the fabricationGroup
	 */
	public void setFabrication(NXfabrication fabricationGroup);

	/**
	 * Location of the lab or place where the instrument is installed. Using GEOREF is
	 * preferred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLocation();

	/**
	 * Location of the lab or place where the instrument is installed. Using GEOREF is
	 * preferred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param locationDataset the locationDataset
	 */
	public DataNode setLocation(IDataset locationDataset);

	/**
	 * Location of the lab or place where the instrument is installed. Using GEOREF is
	 * preferred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getLocationScalar();

	/**
	 * Location of the lab or place where the instrument is installed. Using GEOREF is
	 * preferred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param location the location
	 */
	public DataNode setLocationScalar(String locationValue);

	/**
	 * Nominal flight path
	 * The value can be extracted from the CAnalysis.CSpatial.fFlightPath
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFlight_path();

	/**
	 * Nominal flight path
	 * The value can be extracted from the CAnalysis.CSpatial.fFlightPath
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param flight_pathDataset the flight_pathDataset
	 */
	public DataNode setFlight_path(IDataset flight_pathDataset);

	/**
	 * Nominal flight path
	 * The value can be extracted from the CAnalysis.CSpatial.fFlightPath
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getFlight_pathScalar();

	/**
	 * Nominal flight path
	 * The value can be extracted from the CAnalysis.CSpatial.fFlightPath
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param flight_path the flight_path
	 */
	public DataNode setFlight_pathScalar(Double flight_pathValue);

	/**
	 * Device which reduces ToF differences of ions in ToF experiments.
	 * For atom probe the reflectron can be considered an energy compensation device.
	 * Such a device can be realized technically e.g. with a Poschenrieder lens.
	 * Consult the following U.S. patents for further details:
	 * * 3863068 and 6740872 for the reflectron
	 * * 8134119 for the curved reflectron
	 *
	 * @return  the value.
	 */
	public NXcomponent getReflectron();

	/**
	 * Device which reduces ToF differences of ions in ToF experiments.
	 * For atom probe the reflectron can be considered an energy compensation device.
	 * Such a device can be realized technically e.g. with a Poschenrieder lens.
	 * Consult the following U.S. patents for further details:
	 * * 3863068 and 6740872 for the reflectron
	 * * 8134119 for the curved reflectron
	 *
	 * @param reflectronGroup the reflectronGroup
	 */
	public void setReflectron(NXcomponent reflectronGroup);

	/**
	 * A counter electrode of the LEAP 6000 series atom probes.
	 *
	 * @return  the value.
	 */
	public NXcomponent getDecelerate_electrode();

	/**
	 * A counter electrode of the LEAP 6000 series atom probes.
	 *
	 * @param decelerate_electrodeGroup the decelerate_electrodeGroup
	 */
	public void setDecelerate_electrode(NXcomponent decelerate_electrodeGroup);

	/**
	 * A local electrode guiding the ion flight path.
	 * Also called counter or extraction electrode.
	 *
	 * @return  the value.
	 */
	public NXcomponent getLocal_electrode();

	/**
	 * A local electrode guiding the ion flight path.
	 * Also called counter or extraction electrode.
	 *
	 * @param local_electrodeGroup the local_electrodeGroup
	 */
	public void setLocal_electrode(NXcomponent local_electrodeGroup);

	/**
	 * Detector for taking raw time-of-flight and ion/hit impact positions data.
	 *
	 * @return  the value.
	 */
	public NXdetector getIon_detector();

	/**
	 * Detector for taking raw time-of-flight and ion/hit impact positions data.
	 *
	 * @param ion_detectorGroup the ion_detectorGroup
	 */
	public void setIon_detector(NXdetector ion_detectorGroup);

	/**
	 * Laser- and/or voltage-pulsing device to trigger ion removal.
	 * When the base class NXapm_instrument is used in the NXapm
	 * application definition, the values for the following fields:
	 * * pulse_frequency
	 * * pulse_fraction
	 * * pulse_voltage
	 * * pulse_number
	 * * standing_voltage
	 * * pulse_energy
	 * * incidence_vector
	 * * pinhole_position
	 * * spot_position
	 * should be recorded in the order of, and assumed associated,
	 * with the pulse_id in an instance of :ref:`NXapm_event_data`.
	 *
	 * @return  the value.
	 */
	public NXcomponent getPulser();

	/**
	 * Laser- and/or voltage-pulsing device to trigger ion removal.
	 * When the base class NXapm_instrument is used in the NXapm
	 * application definition, the values for the following fields:
	 * * pulse_frequency
	 * * pulse_fraction
	 * * pulse_voltage
	 * * pulse_number
	 * * standing_voltage
	 * * pulse_energy
	 * * incidence_vector
	 * * pinhole_position
	 * * spot_position
	 * should be recorded in the order of, and assumed associated,
	 * with the pulse_id in an instance of :ref:`NXapm_event_data`.
	 *
	 * @param pulserGroup the pulserGroup
	 */
	public void setPulser(NXcomponent pulserGroup);
	// Unprocessed group:fabrication
	// Unprocessed group:sourceID

	/**
	 *
	 * @return  the value.
	 */
	public NXmanipulator getStage();

	/**
	 *
	 * @param stageGroup the stageGroup
	 */
	public void setStage(NXmanipulator stageGroup);
	// Unprocessed group:temperature_sensor

	/**
	 *
	 * @return  the value.
	 */
	public NXcomponent getAnalysis_chamber();

	/**
	 *
	 * @param analysis_chamberGroup the analysis_chamberGroup
	 */
	public void setAnalysis_chamber(NXcomponent analysis_chamberGroup);
	// Unprocessed group:pressure_sensor

	/**
	 *
	 * @return  the value.
	 */
	public NXcomponent getBuffer_chamber();

	/**
	 *
	 * @param buffer_chamberGroup the buffer_chamberGroup
	 */
	public void setBuffer_chamber(NXcomponent buffer_chamberGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXcomponent getLoad_lock_chamber();

	/**
	 *
	 * @param load_lock_chamberGroup the load_lock_chamberGroup
	 */
	public void setLoad_lock_chamber(NXcomponent load_lock_chamberGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXpump getGetter_pump();

	/**
	 *
	 * @param getter_pumpGroup the getter_pumpGroup
	 */
	public void setGetter_pump(NXpump getter_pumpGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXpump getRoughening_pump();

	/**
	 *
	 * @param roughening_pumpGroup the roughening_pumpGroup
	 */
	public void setRoughening_pump(NXpump roughening_pumpGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXpump getTurbomolecular_pump();

	/**
	 *
	 * @param turbomolecular_pumpGroup the turbomolecular_pumpGroup
	 */
	public void setTurbomolecular_pump(NXpump turbomolecular_pumpGroup);

	/**
	 * Free-text field for additional comments.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getComment();

	/**
	 * Free-text field for additional comments.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param commentDataset the commentDataset
	 */
	public DataNode setComment(IDataset commentDataset);

	/**
	 * Free-text field for additional comments.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getCommentScalar();

	/**
	 * Free-text field for additional comments.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param comment the comment
	 */
	public DataNode setCommentScalar(String commentValue);

	/**
	 * Relevant quantities during a measurement with a LEAP system as were
	 * suggested by `T. Blum et al. <https://doi.org/10.1002/9781119227250.ch18>`_.
	 *
	 * @return  the value.
	 */
	public NXparameters getControl();

	/**
	 * Relevant quantities during a measurement with a LEAP system as were
	 * suggested by `T. Blum et al. <https://doi.org/10.1002/9781119227250.ch18>`_.
	 *
	 * @param controlGroup the controlGroup
	 */
	public void setControl(NXparameters controlGroup);

}
