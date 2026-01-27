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
 * Basic class for describing an electron analyzer.
 * This concept is related to term `12.59`_ of the ISO 18115-1:2023 standard.
 * .. _12.59: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.59
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays<ul>
 * <li><b>nfa</b>
 * Number of fast axes (axes acquired simultaneously, without scanning a
 * physical quantity)</li>
 * <li><b>nsa</b>
 * Number of slow axes (axes acquired while scanning a physical quantity)</li>
 * <li><b>n_transmission_function</b>
 * Number of data points in the transmission function.</li></ul></p>
 *
 */
public interface NXelectronanalyzer extends NXcomponent {

	public static final String NX_NAME_ATTRIBUTE_SHORT_NAME = "short_name";
	public static final String NX_WORK_FUNCTION = "work_function";
	public static final String NX_VOLTAGE_RANGE = "voltage_range";
	public static final String NX_FAST_AXES = "fast_axes";
	public static final String NX_SLOW_AXES = "slow_axes";
	/**
	 * Free text description of the type of the detector
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Free text description of the type of the detector
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Free text description of the type of the detector
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Free text description of the type of the detector
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Name or model of the equipment
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Name or model of the equipment
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Name or model of the equipment
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Name or model of the equipment
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Acronym or other shorthand name
	 *
	 * @return  the value.
	 */
	public String getNameAttributeShort_name();

	/**
	 * Acronym or other shorthand name
	 *
	 * @param short_nameValue the short_nameValue
	 */
	public void setNameAttributeShort_name(String short_nameValue);

	/**
	 * Work function of the electron analyzer.
	 * The work function of a uniform surface of a conductor is the minimum energy required to remove
	 * an electron from the interior of the solid to a vacuum level immediately outside the solid surface.
	 * The kinetic energy :math:E_K of a photoelectron emitted from an energy level with binding energy
	 * :math:`E_B` below the Fermi level is given by :math:`E_K = h\nu - E_B - W = h\nu - E_B - e \phi_{\mathrm{sample}}`.
	 * Here, :math:`W = e \phi_{\mathrm{sample}}` is the work function of the sample surface, which is directly proportional
	 * to the potential difference :math:`\phi_{\mathrm{sample}}` between the electrochemical potential of electrons in
	 * the bulk and the electrostatic potential of an electron in the vacuum just outside the surface.
	 * In PES measurements, the sample and the spectrometer (with work function :math:`W_{\mathrm{spectr.}} = e \phi_{\mathrm{spectr.}}`)
	 * are electrically connected and therefore their Fermi levels are aligned. Due to the difference in local
	 * vacuum level between the sample and spectrometer, there however exists an electric potential difference (contact potential)
	 * :math:`\Delta\phi = \phi_{\mathrm{sample}} - \phi_{\mathrm{spectr.}}`. The measured kinetic energy of a
	 * photoelectron in PES is therefore given by :math:`E_K^{\mathrm{meas.}} = E_K + e\Delta\phi = h\nu - E_B - e\phi_{\mathrm{spectr.}}`.
	 * Hence, the measured kinetic energy :math:`E_K^{\mathrm{meas.}}` of a photoelectron is independent
	 * of the sample work function. Nonetheless, the work function :math:`\phi_{\mathrm{spectr.}}` needs to be known to
	 * accurately determine the binding energy scale.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWork_function();

	/**
	 * Work function of the electron analyzer.
	 * The work function of a uniform surface of a conductor is the minimum energy required to remove
	 * an electron from the interior of the solid to a vacuum level immediately outside the solid surface.
	 * The kinetic energy :math:E_K of a photoelectron emitted from an energy level with binding energy
	 * :math:`E_B` below the Fermi level is given by :math:`E_K = h\nu - E_B - W = h\nu - E_B - e \phi_{\mathrm{sample}}`.
	 * Here, :math:`W = e \phi_{\mathrm{sample}}` is the work function of the sample surface, which is directly proportional
	 * to the potential difference :math:`\phi_{\mathrm{sample}}` between the electrochemical potential of electrons in
	 * the bulk and the electrostatic potential of an electron in the vacuum just outside the surface.
	 * In PES measurements, the sample and the spectrometer (with work function :math:`W_{\mathrm{spectr.}} = e \phi_{\mathrm{spectr.}}`)
	 * are electrically connected and therefore their Fermi levels are aligned. Due to the difference in local
	 * vacuum level between the sample and spectrometer, there however exists an electric potential difference (contact potential)
	 * :math:`\Delta\phi = \phi_{\mathrm{sample}} - \phi_{\mathrm{spectr.}}`. The measured kinetic energy of a
	 * photoelectron in PES is therefore given by :math:`E_K^{\mathrm{meas.}} = E_K + e\Delta\phi = h\nu - E_B - e\phi_{\mathrm{spectr.}}`.
	 * Hence, the measured kinetic energy :math:`E_K^{\mathrm{meas.}}` of a photoelectron is independent
	 * of the sample work function. Nonetheless, the work function :math:`\phi_{\mathrm{spectr.}}` needs to be known to
	 * accurately determine the binding energy scale.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param work_functionDataset the work_functionDataset
	 */
	public DataNode setWork_function(IDataset work_functionDataset);

	/**
	 * Work function of the electron analyzer.
	 * The work function of a uniform surface of a conductor is the minimum energy required to remove
	 * an electron from the interior of the solid to a vacuum level immediately outside the solid surface.
	 * The kinetic energy :math:E_K of a photoelectron emitted from an energy level with binding energy
	 * :math:`E_B` below the Fermi level is given by :math:`E_K = h\nu - E_B - W = h\nu - E_B - e \phi_{\mathrm{sample}}`.
	 * Here, :math:`W = e \phi_{\mathrm{sample}}` is the work function of the sample surface, which is directly proportional
	 * to the potential difference :math:`\phi_{\mathrm{sample}}` between the electrochemical potential of electrons in
	 * the bulk and the electrostatic potential of an electron in the vacuum just outside the surface.
	 * In PES measurements, the sample and the spectrometer (with work function :math:`W_{\mathrm{spectr.}} = e \phi_{\mathrm{spectr.}}`)
	 * are electrically connected and therefore their Fermi levels are aligned. Due to the difference in local
	 * vacuum level between the sample and spectrometer, there however exists an electric potential difference (contact potential)
	 * :math:`\Delta\phi = \phi_{\mathrm{sample}} - \phi_{\mathrm{spectr.}}`. The measured kinetic energy of a
	 * photoelectron in PES is therefore given by :math:`E_K^{\mathrm{meas.}} = E_K + e\Delta\phi = h\nu - E_B - e\phi_{\mathrm{spectr.}}`.
	 * Hence, the measured kinetic energy :math:`E_K^{\mathrm{meas.}}` of a photoelectron is independent
	 * of the sample work function. Nonetheless, the work function :math:`\phi_{\mathrm{spectr.}}` needs to be known to
	 * accurately determine the binding energy scale.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getWork_functionScalar();

	/**
	 * Work function of the electron analyzer.
	 * The work function of a uniform surface of a conductor is the minimum energy required to remove
	 * an electron from the interior of the solid to a vacuum level immediately outside the solid surface.
	 * The kinetic energy :math:E_K of a photoelectron emitted from an energy level with binding energy
	 * :math:`E_B` below the Fermi level is given by :math:`E_K = h\nu - E_B - W = h\nu - E_B - e \phi_{\mathrm{sample}}`.
	 * Here, :math:`W = e \phi_{\mathrm{sample}}` is the work function of the sample surface, which is directly proportional
	 * to the potential difference :math:`\phi_{\mathrm{sample}}` between the electrochemical potential of electrons in
	 * the bulk and the electrostatic potential of an electron in the vacuum just outside the surface.
	 * In PES measurements, the sample and the spectrometer (with work function :math:`W_{\mathrm{spectr.}} = e \phi_{\mathrm{spectr.}}`)
	 * are electrically connected and therefore their Fermi levels are aligned. Due to the difference in local
	 * vacuum level between the sample and spectrometer, there however exists an electric potential difference (contact potential)
	 * :math:`\Delta\phi = \phi_{\mathrm{sample}} - \phi_{\mathrm{spectr.}}`. The measured kinetic energy of a
	 * photoelectron in PES is therefore given by :math:`E_K^{\mathrm{meas.}} = E_K + e\Delta\phi = h\nu - E_B - e\phi_{\mathrm{spectr.}}`.
	 * Hence, the measured kinetic energy :math:`E_K^{\mathrm{meas.}}` of a photoelectron is independent
	 * of the sample work function. Nonetheless, the work function :math:`\phi_{\mathrm{spectr.}}` needs to be known to
	 * accurately determine the binding energy scale.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param work_function the work_function
	 */
	public DataNode setWork_functionScalar(Double work_functionValue);

	/**
	 * Voltage range of the power supply. This influences the noise of the supply
	 * and thereby the energy resolution.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVoltage_range();

	/**
	 * Voltage range of the power supply. This influences the noise of the supply
	 * and thereby the energy resolution.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param voltage_rangeDataset the voltage_rangeDataset
	 */
	public DataNode setVoltage_range(IDataset voltage_rangeDataset);

	/**
	 * Voltage range of the power supply. This influences the noise of the supply
	 * and thereby the energy resolution.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getVoltage_rangeScalar();

	/**
	 * Voltage range of the power supply. This influences the noise of the supply
	 * and thereby the energy resolution.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param voltage_range the voltage_range
	 */
	public DataNode setVoltage_rangeScalar(Double voltage_rangeValue);

	/**
	 * Energy resolution of the analyzer with the current setting. May be linked from an
	 * NXcalibration.
	 *
	 * @return  the value.
	 */
	public NXresolution getEnergy_resolution();

	/**
	 * Energy resolution of the analyzer with the current setting. May be linked from an
	 * NXcalibration.
	 *
	 * @param energy_resolutionGroup the energy_resolutionGroup
	 */
	public void setEnergy_resolution(NXresolution energy_resolutionGroup);

	/**
	 * Momentum resolution of the electron analyzer (FWHM)
	 *
	 * @return  the value.
	 */
	public NXresolution getMomentum_resolution();

	/**
	 * Momentum resolution of the electron analyzer (FWHM)
	 *
	 * @param momentum_resolutionGroup the momentum_resolutionGroup
	 */
	public void setMomentum_resolution(NXresolution momentum_resolutionGroup);

	/**
	 * Angular resolution of the electron analyzer (FWHM)
	 *
	 * @return  the value.
	 */
	public NXresolution getAngular_resolution();

	/**
	 * Angular resolution of the electron analyzer (FWHM)
	 *
	 * @param angular_resolutionGroup the angular_resolutionGroup
	 */
	public void setAngular_resolution(NXresolution angular_resolutionGroup);

	/**
	 * Spatial resolution of the electron analyzer (Airy disk radius)
	 * This concept is related to term `10.14`_ of the ISO 18115-1:2023 standard.
	 * .. _10.14: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:10.15
	 *
	 * @return  the value.
	 */
	public NXresolution getSpatial_resolution();

	/**
	 * Spatial resolution of the electron analyzer (Airy disk radius)
	 * This concept is related to term `10.14`_ of the ISO 18115-1:2023 standard.
	 * .. _10.14: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:10.15
	 *
	 * @param spatial_resolutionGroup the spatial_resolutionGroup
	 */
	public void setSpatial_resolution(NXresolution spatial_resolutionGroup);

	/**
	 * List of the axes that are acquired simultaneously by the detector.
	 * These refer only to the experimental variables recorded by the electron analyzer.
	 * Other variables such as temperature, manipulator angles etc. can be labeled as fast or slow in the data.
	 * The fast axes should be listed in order of decreasing speed if they describe the same physical quantity
	 * or different components of the same quantity (e.g., ['kx', 'ky'] or ['detector_x', 'detector_y']).
	 * However, axes representing different physical quantities (e.g., ['energy', 'kx']) do not need to be ordered
	 * by speed.
	 * .. csv-table:: Examples
	 * :header: "Mode", "fast_axes", "slow_axes"
	 * "Hemispherical in ARPES mode", "['energy', 'kx']",""
	 * "Hemispherical with channeltron, sweeping energy mode", "", [\"energy\"]
	 * "Tof", "['energy', 'kx', 'ky']",""
	 * "Momentum microscope, spin-resolved", "['energy', 'kx', 'ky']", "['spin up-down', 'spin left-right']"
	 * Axes may be less abstract than this, i.e. ['detector_x', 'detector_y'].
	 * If energy_scan_mode=sweep, fast_axes: ['energy', 'kx']; slow_axes: ['energy'] is allowed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: nfa;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFast_axes();

	/**
	 * List of the axes that are acquired simultaneously by the detector.
	 * These refer only to the experimental variables recorded by the electron analyzer.
	 * Other variables such as temperature, manipulator angles etc. can be labeled as fast or slow in the data.
	 * The fast axes should be listed in order of decreasing speed if they describe the same physical quantity
	 * or different components of the same quantity (e.g., ['kx', 'ky'] or ['detector_x', 'detector_y']).
	 * However, axes representing different physical quantities (e.g., ['energy', 'kx']) do not need to be ordered
	 * by speed.
	 * .. csv-table:: Examples
	 * :header: "Mode", "fast_axes", "slow_axes"
	 * "Hemispherical in ARPES mode", "['energy', 'kx']",""
	 * "Hemispherical with channeltron, sweeping energy mode", "", [\"energy\"]
	 * "Tof", "['energy', 'kx', 'ky']",""
	 * "Momentum microscope, spin-resolved", "['energy', 'kx', 'ky']", "['spin up-down', 'spin left-right']"
	 * Axes may be less abstract than this, i.e. ['detector_x', 'detector_y'].
	 * If energy_scan_mode=sweep, fast_axes: ['energy', 'kx']; slow_axes: ['energy'] is allowed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: nfa;
	 * </p>
	 *
	 * @param fast_axesDataset the fast_axesDataset
	 */
	public DataNode setFast_axes(IDataset fast_axesDataset);

	/**
	 * List of the axes that are acquired simultaneously by the detector.
	 * These refer only to the experimental variables recorded by the electron analyzer.
	 * Other variables such as temperature, manipulator angles etc. can be labeled as fast or slow in the data.
	 * The fast axes should be listed in order of decreasing speed if they describe the same physical quantity
	 * or different components of the same quantity (e.g., ['kx', 'ky'] or ['detector_x', 'detector_y']).
	 * However, axes representing different physical quantities (e.g., ['energy', 'kx']) do not need to be ordered
	 * by speed.
	 * .. csv-table:: Examples
	 * :header: "Mode", "fast_axes", "slow_axes"
	 * "Hemispherical in ARPES mode", "['energy', 'kx']",""
	 * "Hemispherical with channeltron, sweeping energy mode", "", [\"energy\"]
	 * "Tof", "['energy', 'kx', 'ky']",""
	 * "Momentum microscope, spin-resolved", "['energy', 'kx', 'ky']", "['spin up-down', 'spin left-right']"
	 * Axes may be less abstract than this, i.e. ['detector_x', 'detector_y'].
	 * If energy_scan_mode=sweep, fast_axes: ['energy', 'kx']; slow_axes: ['energy'] is allowed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: nfa;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getFast_axesScalar();

	/**
	 * List of the axes that are acquired simultaneously by the detector.
	 * These refer only to the experimental variables recorded by the electron analyzer.
	 * Other variables such as temperature, manipulator angles etc. can be labeled as fast or slow in the data.
	 * The fast axes should be listed in order of decreasing speed if they describe the same physical quantity
	 * or different components of the same quantity (e.g., ['kx', 'ky'] or ['detector_x', 'detector_y']).
	 * However, axes representing different physical quantities (e.g., ['energy', 'kx']) do not need to be ordered
	 * by speed.
	 * .. csv-table:: Examples
	 * :header: "Mode", "fast_axes", "slow_axes"
	 * "Hemispherical in ARPES mode", "['energy', 'kx']",""
	 * "Hemispherical with channeltron, sweeping energy mode", "", [\"energy\"]
	 * "Tof", "['energy', 'kx', 'ky']",""
	 * "Momentum microscope, spin-resolved", "['energy', 'kx', 'ky']", "['spin up-down', 'spin left-right']"
	 * Axes may be less abstract than this, i.e. ['detector_x', 'detector_y'].
	 * If energy_scan_mode=sweep, fast_axes: ['energy', 'kx']; slow_axes: ['energy'] is allowed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: nfa;
	 * </p>
	 *
	 * @param fast_axes the fast_axes
	 */
	public DataNode setFast_axesScalar(String fast_axesValue);

	/**
	 * List of the axes that are acquired by scanning a physical parameter, listed in
	 * order of decreasing speed. See fast_axes for examples.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: nsa;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSlow_axes();

	/**
	 * List of the axes that are acquired by scanning a physical parameter, listed in
	 * order of decreasing speed. See fast_axes for examples.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: nsa;
	 * </p>
	 *
	 * @param slow_axesDataset the slow_axesDataset
	 */
	public DataNode setSlow_axes(IDataset slow_axesDataset);

	/**
	 * List of the axes that are acquired by scanning a physical parameter, listed in
	 * order of decreasing speed. See fast_axes for examples.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: nsa;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSlow_axesScalar();

	/**
	 * List of the axes that are acquired by scanning a physical parameter, listed in
	 * order of decreasing speed. See fast_axes for examples.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: nsa;
	 * </p>
	 *
	 * @param slow_axes the slow_axes
	 */
	public DataNode setSlow_axesScalar(String slow_axesValue);

	/**
	 * Transmission function of the electron analyzer.
	 * The transmission function (TF) specifies the detection efficiency per solid angle for electrons of
	 * different kinetic energy passing through the electron analyzer. It depends on the spectrometer
	 * geometry as well as operation settings such as lens mode and pass energy.
	 * The transmission function is usually given as relative intensity vs. kinetic energy.
	 * The TF is used for calibration of the intensity scale in quantitative XPS. Without proper
	 * transmission correction, a comparison of results measured from the same sample using different
	 * operating modes for an instrument would show significant variations in signal intensity for the same
	 * kinetic energies.
	 * This concept is related to term `7.15`_ of the ISO 18115-1:2023 standard.
	 * .. _7.15: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:7.15
	 *
	 * @return  the value.
	 */
	public NXdata getTransmission_function();

	/**
	 * Transmission function of the electron analyzer.
	 * The transmission function (TF) specifies the detection efficiency per solid angle for electrons of
	 * different kinetic energy passing through the electron analyzer. It depends on the spectrometer
	 * geometry as well as operation settings such as lens mode and pass energy.
	 * The transmission function is usually given as relative intensity vs. kinetic energy.
	 * The TF is used for calibration of the intensity scale in quantitative XPS. Without proper
	 * transmission correction, a comparison of results measured from the same sample using different
	 * operating modes for an instrument would show significant variations in signal intensity for the same
	 * kinetic energies.
	 * This concept is related to term `7.15`_ of the ISO 18115-1:2023 standard.
	 * .. _7.15: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:7.15
	 *
	 * @param transmission_functionGroup the transmission_functionGroup
	 */
	public void setTransmission_function(NXdata transmission_functionGroup);

	/**
	 * Describes the electron collection (spatial and momentum imaging) column
	 *
	 * @return  the value.
	 */
	public NXcollectioncolumn getCollectioncolumn();

	/**
	 * Describes the electron collection (spatial and momentum imaging) column
	 *
	 * @param collectioncolumnGroup the collectioncolumnGroup
	 */
	public void setCollectioncolumn(NXcollectioncolumn collectioncolumnGroup);

	/**
	 * Get a NXcollectioncolumn node by name:
	 * <ul>
	 * <li>
	 * Describes the electron collection (spatial and momentum imaging) column</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcollectioncolumn for that node.
	 */
	public NXcollectioncolumn getCollectioncolumn(String name);

	/**
	 * Set a NXcollectioncolumn node by name:
	 * <ul>
	 * <li>
	 * Describes the electron collection (spatial and momentum imaging) column</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param collectioncolumn the value to set
	 */
	public void setCollectioncolumn(String name, NXcollectioncolumn collectioncolumn);

	/**
	 * Get all NXcollectioncolumn nodes:
	 * <ul>
	 * <li>
	 * Describes the electron collection (spatial and momentum imaging) column</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcollectioncolumn for that node.
	 */
	public Map<String, NXcollectioncolumn> getAllCollectioncolumn();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Describes the electron collection (spatial and momentum imaging) column</li>
	 * </ul>
	 *
	 * @param collectioncolumn the child nodes to add
	 */

	public void setAllCollectioncolumn(Map<String, NXcollectioncolumn> collectioncolumn);


	/**
	 * Describes the energy dispersion section
	 *
	 * @return  the value.
	 */
	public NXenergydispersion getEnergydispersion();

	/**
	 * Describes the energy dispersion section
	 *
	 * @param energydispersionGroup the energydispersionGroup
	 */
	public void setEnergydispersion(NXenergydispersion energydispersionGroup);

	/**
	 * Get a NXenergydispersion node by name:
	 * <ul>
	 * <li>
	 * Describes the energy dispersion section</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXenergydispersion for that node.
	 */
	public NXenergydispersion getEnergydispersion(String name);

	/**
	 * Set a NXenergydispersion node by name:
	 * <ul>
	 * <li>
	 * Describes the energy dispersion section</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param energydispersion the value to set
	 */
	public void setEnergydispersion(String name, NXenergydispersion energydispersion);

	/**
	 * Get all NXenergydispersion nodes:
	 * <ul>
	 * <li>
	 * Describes the energy dispersion section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXenergydispersion for that node.
	 */
	public Map<String, NXenergydispersion> getAllEnergydispersion();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Describes the energy dispersion section</li>
	 * </ul>
	 *
	 * @param energydispersion the child nodes to add
	 */

	public void setAllEnergydispersion(Map<String, NXenergydispersion> energydispersion);


	/**
	 * Describes the spin dispersion section
	 *
	 * @return  the value.
	 */
	public NXspindispersion getSpindispersion();

	/**
	 * Describes the spin dispersion section
	 *
	 * @param spindispersionGroup the spindispersionGroup
	 */
	public void setSpindispersion(NXspindispersion spindispersionGroup);

	/**
	 * Get a NXspindispersion node by name:
	 * <ul>
	 * <li>
	 * Describes the spin dispersion section</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXspindispersion for that node.
	 */
	public NXspindispersion getSpindispersion(String name);

	/**
	 * Set a NXspindispersion node by name:
	 * <ul>
	 * <li>
	 * Describes the spin dispersion section</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param spindispersion the value to set
	 */
	public void setSpindispersion(String name, NXspindispersion spindispersion);

	/**
	 * Get all NXspindispersion nodes:
	 * <ul>
	 * <li>
	 * Describes the spin dispersion section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXspindispersion for that node.
	 */
	public Map<String, NXspindispersion> getAllSpindispersion();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Describes the spin dispersion section</li>
	 * </ul>
	 *
	 * @param spindispersion the child nodes to add
	 */

	public void setAllSpindispersion(Map<String, NXspindispersion> spindispersion);


	/**
	 * Describes the electron detector
	 *
	 * @return  the value.
	 */
	public NXdetector getDetector();

	/**
	 * Describes the electron detector
	 *
	 * @param detectorGroup the detectorGroup
	 */
	public void setDetector(NXdetector detectorGroup);

	/**
	 * Get a NXdetector node by name:
	 * <ul>
	 * <li>
	 * Describes the electron detector</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdetector for that node.
	 */
	public NXdetector getDetector(String name);

	/**
	 * Set a NXdetector node by name:
	 * <ul>
	 * <li>
	 * Describes the electron detector</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param detector the value to set
	 */
	public void setDetector(String name, NXdetector detector);

	/**
	 * Get all NXdetector nodes:
	 * <ul>
	 * <li>
	 * Describes the electron detector</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdetector for that node.
	 */
	public Map<String, NXdetector> getAllDetector();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Describes the electron detector</li>
	 * </ul>
	 *
	 * @param detector the child nodes to add
	 */

	public void setAllDetector(Map<String, NXdetector> detector);


	/**
	 * Deflectors outside the main optics ensembles described by the subclasses
	 *
	 * @return  the value.
	 */
	public NXdeflector getDeflector();

	/**
	 * Deflectors outside the main optics ensembles described by the subclasses
	 *
	 * @param deflectorGroup the deflectorGroup
	 */
	public void setDeflector(NXdeflector deflectorGroup);

	/**
	 * Get a NXdeflector node by name:
	 * <ul>
	 * <li>
	 * Deflectors outside the main optics ensembles described by the subclasses</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public NXdeflector getDeflector(String name);

	/**
	 * Set a NXdeflector node by name:
	 * <ul>
	 * <li>
	 * Deflectors outside the main optics ensembles described by the subclasses</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param deflector the value to set
	 */
	public void setDeflector(String name, NXdeflector deflector);

	/**
	 * Get all NXdeflector nodes:
	 * <ul>
	 * <li>
	 * Deflectors outside the main optics ensembles described by the subclasses</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public Map<String, NXdeflector> getAllDeflector();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Deflectors outside the main optics ensembles described by the subclasses</li>
	 * </ul>
	 *
	 * @param deflector the child nodes to add
	 */

	public void setAllDeflector(Map<String, NXdeflector> deflector);


	/**
	 * Individual lenses outside the main optics ensembles described by the subclasses
	 *
	 * @return  the value.
	 */
	public NXelectromagnetic_lens getElectromagnetic_lens();

	/**
	 * Individual lenses outside the main optics ensembles described by the subclasses
	 *
	 * @param electromagnetic_lensGroup the electromagnetic_lensGroup
	 */
	public void setElectromagnetic_lens(NXelectromagnetic_lens electromagnetic_lensGroup);

	/**
	 * Get a NXelectromagnetic_lens node by name:
	 * <ul>
	 * <li>
	 * Individual lenses outside the main optics ensembles described by the subclasses</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXelectromagnetic_lens for that node.
	 */
	public NXelectromagnetic_lens getElectromagnetic_lens(String name);

	/**
	 * Set a NXelectromagnetic_lens node by name:
	 * <ul>
	 * <li>
	 * Individual lenses outside the main optics ensembles described by the subclasses</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param electromagnetic_lens the value to set
	 */
	public void setElectromagnetic_lens(String name, NXelectromagnetic_lens electromagnetic_lens);

	/**
	 * Get all NXelectromagnetic_lens nodes:
	 * <ul>
	 * <li>
	 * Individual lenses outside the main optics ensembles described by the subclasses</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXelectromagnetic_lens for that node.
	 */
	public Map<String, NXelectromagnetic_lens> getAllElectromagnetic_lens();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual lenses outside the main optics ensembles described by the subclasses</li>
	 * </ul>
	 *
	 * @param electromagnetic_lens the child nodes to add
	 */

	public void setAllElectromagnetic_lens(Map<String, NXelectromagnetic_lens> electromagnetic_lens);


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
	 * Get a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public NXfabrication getFabrication(String name);

	/**
	 * Set a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param fabrication the value to set
	 */
	public void setFabrication(String name, NXfabrication fabrication);

	/**
	 * Get all NXfabrication nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public Map<String, NXfabrication> getAllFabrication();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param fabrication the child nodes to add
	 */

	public void setAllFabrication(Map<String, NXfabrication> fabrication);


	/**
	 * Any other resolution not explicitly named in this base class.
	 *
	 * @return  the value.
	 */
	public NXresolution getResolution();

	/**
	 * Any other resolution not explicitly named in this base class.
	 *
	 * @param resolutionGroup the resolutionGroup
	 */
	public void setResolution(NXresolution resolutionGroup);

	/**
	 * Get a NXresolution node by name:
	 * <ul>
	 * <li>
	 * Energy resolution of the analyzer with the current setting. May be linked from an
	 * NXcalibration.</li>
	 * <li>
	 * Momentum resolution of the electron analyzer (FWHM)</li>
	 * <li>
	 * Angular resolution of the electron analyzer (FWHM)</li>
	 * <li>
	 * Spatial resolution of the electron analyzer (Airy disk radius)
	 * This concept is related to term `10.14`_ of the ISO 18115-1:2023 standard.
	 * .. _10.14: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:10.15</li>
	 * <li>
	 * Any other resolution not explicitly named in this base class.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXresolution for that node.
	 */
	public NXresolution getResolution(String name);

	/**
	 * Set a NXresolution node by name:
	 * <ul>
	 * <li>
	 * Energy resolution of the analyzer with the current setting. May be linked from an
	 * NXcalibration.</li>
	 * <li>
	 * Momentum resolution of the electron analyzer (FWHM)</li>
	 * <li>
	 * Angular resolution of the electron analyzer (FWHM)</li>
	 * <li>
	 * Spatial resolution of the electron analyzer (Airy disk radius)
	 * This concept is related to term `10.14`_ of the ISO 18115-1:2023 standard.
	 * .. _10.14: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:10.15</li>
	 * <li>
	 * Any other resolution not explicitly named in this base class.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param resolution the value to set
	 */
	public void setResolution(String name, NXresolution resolution);

	/**
	 * Get all NXresolution nodes:
	 * <ul>
	 * <li>
	 * Energy resolution of the analyzer with the current setting. May be linked from an
	 * NXcalibration.</li>
	 * <li>
	 * Momentum resolution of the electron analyzer (FWHM)</li>
	 * <li>
	 * Angular resolution of the electron analyzer (FWHM)</li>
	 * <li>
	 * Spatial resolution of the electron analyzer (Airy disk radius)
	 * This concept is related to term `10.14`_ of the ISO 18115-1:2023 standard.
	 * .. _10.14: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:10.15</li>
	 * <li>
	 * Any other resolution not explicitly named in this base class.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXresolution for that node.
	 */
	public Map<String, NXresolution> getAllResolution();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Energy resolution of the analyzer with the current setting. May be linked from an
	 * NXcalibration.</li>
	 * <li>
	 * Momentum resolution of the electron analyzer (FWHM)</li>
	 * <li>
	 * Angular resolution of the electron analyzer (FWHM)</li>
	 * <li>
	 * Spatial resolution of the electron analyzer (Airy disk radius)
	 * This concept is related to term `10.14`_ of the ISO 18115-1:2023 standard.
	 * .. _10.14: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:10.15</li>
	 * <li>
	 * Any other resolution not explicitly named in this base class.</li>
	 * </ul>
	 *
	 * @param resolution the child nodes to add
	 */

	public void setAllResolution(Map<String, NXresolution> resolution);


}
