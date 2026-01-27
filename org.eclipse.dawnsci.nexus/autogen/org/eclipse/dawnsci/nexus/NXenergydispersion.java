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
 * Energy dispersion section of an electron analyzer.
 *
 */
public interface NXenergydispersion extends NXcomponent {

	public static final String NX_SCHEME = "scheme";
	public static final String NX_PASS_ENERGY = "pass_energy";
	public static final String NX_KINETIC_ENERGY = "kinetic_energy";
	public static final String NX_DRIFT_ENERGY = "drift_energy";
	public static final String NX_CENTER_ENERGY = "center_energy";
	public static final String NX_ENERGY_INTERVAL = "energy_interval";
	public static final String NX_DIAMETER = "diameter";
	public static final String NX_RADIUS = "radius";
	public static final String NX_ENERGY_SCAN_MODE = "energy_scan_mode";
	public static final String NX_TOF_DISTANCE = "tof_distance";
	/**
	 * Energy dispersion scheme employed, for example: tof, hemispherical, cylindrical,
	 * mirror, retarding grid, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getScheme();

	/**
	 * Energy dispersion scheme employed, for example: tof, hemispherical, cylindrical,
	 * mirror, retarding grid, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param schemeDataset the schemeDataset
	 */
	public DataNode setScheme(IDataset schemeDataset);

	/**
	 * Energy dispersion scheme employed, for example: tof, hemispherical, cylindrical,
	 * mirror, retarding grid, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSchemeScalar();

	/**
	 * Energy dispersion scheme employed, for example: tof, hemispherical, cylindrical,
	 * mirror, retarding grid, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param scheme the scheme
	 */
	public DataNode setSchemeScalar(String schemeValue);

	/**
	 * Mean kinetic energy of the electrons in this energy-dispersive section of the analyzer.
	 * This term should be used for hemispherical analyzers.
	 * This concept is related to term `12.63`_ of the ISO 18115-1:2023 standard.
	 * .. _12.63: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.63
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPass_energy();

	/**
	 * Mean kinetic energy of the electrons in this energy-dispersive section of the analyzer.
	 * This term should be used for hemispherical analyzers.
	 * This concept is related to term `12.63`_ of the ISO 18115-1:2023 standard.
	 * .. _12.63: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.63
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param pass_energyDataset the pass_energyDataset
	 */
	public DataNode setPass_energy(IDataset pass_energyDataset);

	/**
	 * Mean kinetic energy of the electrons in this energy-dispersive section of the analyzer.
	 * This term should be used for hemispherical analyzers.
	 * This concept is related to term `12.63`_ of the ISO 18115-1:2023 standard.
	 * .. _12.63: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.63
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPass_energyScalar();

	/**
	 * Mean kinetic energy of the electrons in this energy-dispersive section of the analyzer.
	 * This term should be used for hemispherical analyzers.
	 * This concept is related to term `12.63`_ of the ISO 18115-1:2023 standard.
	 * .. _12.63: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.63
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param pass_energy the pass_energy
	 */
	public DataNode setPass_energyScalar(Double pass_energyValue);

	/**
	 * Kinetic energy set for this dispersive section. Can be either the set kinetic energy,
	 * or the whole calibrated energy axis of a scan.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getKinetic_energy();

	/**
	 * Kinetic energy set for this dispersive section. Can be either the set kinetic energy,
	 * or the whole calibrated energy axis of a scan.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param kinetic_energyDataset the kinetic_energyDataset
	 */
	public DataNode setKinetic_energy(IDataset kinetic_energyDataset);

	/**
	 * Kinetic energy set for this dispersive section. Can be either the set kinetic energy,
	 * or the whole calibrated energy axis of a scan.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getKinetic_energyScalar();

	/**
	 * Kinetic energy set for this dispersive section. Can be either the set kinetic energy,
	 * or the whole calibrated energy axis of a scan.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param kinetic_energy the kinetic_energy
	 */
	public DataNode setKinetic_energyScalar(Double kinetic_energyValue);

	/**
	 * Drift energy for time-of-flight energy dispersive elements.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDrift_energy();

	/**
	 * Drift energy for time-of-flight energy dispersive elements.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param drift_energyDataset the drift_energyDataset
	 */
	public DataNode setDrift_energy(IDataset drift_energyDataset);

	/**
	 * Drift energy for time-of-flight energy dispersive elements.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDrift_energyScalar();

	/**
	 * Drift energy for time-of-flight energy dispersive elements.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param drift_energy the drift_energy
	 */
	public DataNode setDrift_energyScalar(Double drift_energyValue);

	/**
	 * Center of the energy window
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCenter_energy();

	/**
	 * Center of the energy window
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param center_energyDataset the center_energyDataset
	 */
	public DataNode setCenter_energy(IDataset center_energyDataset);

	/**
	 * Center of the energy window
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCenter_energyScalar();

	/**
	 * Center of the energy window
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param center_energy the center_energy
	 */
	public DataNode setCenter_energyScalar(Double center_energyValue);

	/**
	 * The interval of transmitted energies. It can be two different things depending
	 * on whether the scan is fixed or swept. With a fixed scan it is a 2 vector
	 * containing the extrema of the transmitted energy window (smaller number first).
	 * With a swept scan of m steps it is a 2xm array of windows, one for each
	 * measurement point.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEnergy_interval();

	/**
	 * The interval of transmitted energies. It can be two different things depending
	 * on whether the scan is fixed or swept. With a fixed scan it is a 2 vector
	 * containing the extrema of the transmitted energy window (smaller number first).
	 * With a swept scan of m steps it is a 2xm array of windows, one for each
	 * measurement point.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param energy_intervalDataset the energy_intervalDataset
	 */
	public DataNode setEnergy_interval(IDataset energy_intervalDataset);

	/**
	 * The interval of transmitted energies. It can be two different things depending
	 * on whether the scan is fixed or swept. With a fixed scan it is a 2 vector
	 * containing the extrema of the transmitted energy window (smaller number first).
	 * With a swept scan of m steps it is a 2xm array of windows, one for each
	 * measurement point.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getEnergy_intervalScalar();

	/**
	 * The interval of transmitted energies. It can be two different things depending
	 * on whether the scan is fixed or swept. With a fixed scan it is a 2 vector
	 * containing the extrema of the transmitted energy window (smaller number first).
	 * With a swept scan of m steps it is a 2xm array of windows, one for each
	 * measurement point.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param energy_interval the energy_interval
	 */
	public DataNode setEnergy_intervalScalar(Double energy_intervalValue);

	/**
	 * Diameter of the dispersive orbit
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDiameter();

	/**
	 * Diameter of the dispersive orbit
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param diameterDataset the diameterDataset
	 */
	public DataNode setDiameter(IDataset diameterDataset);

	/**
	 * Diameter of the dispersive orbit
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDiameterScalar();

	/**
	 * Diameter of the dispersive orbit
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param diameter the diameter
	 */
	public DataNode setDiameterScalar(Double diameterValue);

	/**
	 * Radius of the dispersive orbit
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRadius();

	/**
	 * Radius of the dispersive orbit
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param radiusDataset the radiusDataset
	 */
	public DataNode setRadius(IDataset radiusDataset);

	/**
	 * Radius of the dispersive orbit
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRadiusScalar();

	/**
	 * Radius of the dispersive orbit
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param radius the radius
	 */
	public DataNode setRadiusScalar(Double radiusValue);

	/**
	 * Way of scanning the energy axis
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>fixed_analyzer_transmission</b> 
	 * constant :math:`\Delta E` mode, where the electron retardation (i.e., the fraction of pass energy to
	 * kinetic energy, :math:`R = (E_K - W)/E_p`, is scanned, but the pass energy :math:`E_p` is kept constant.
	 * Here, :math:`W = e \phi` is the spectrometer work function (with the potential difference :math:`\phi`
	 * between the electrochemical potential of electrons in the bulk and the electrostatic potential of an electron in the
	 * vacuum just outside the surface).
	 * This mode is often used in X-ray/ultraviolet photoemission spectroscopy (XPS/UPS) because the energy resolution does
	 * not change with changing energy (due to the constant pass energy).
	 * Synonyms: constant :math:`\Delta E` mode, constant analyzer energy mode, CAE
	 * mode, FAT mode
	 * This concept is related to term `12.64`_ of the ISO 18115-1:2023 standard.
	 * .. _12.64: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.64</li>
	 * <li><b>fixed_retardation_ratio</b> 
	 * constant :math:`\Delta E/E` mode, where the pass energy is scanned such that the electron retardation
	 * ratio is constant. In this mode, electrons of all energies are decelerated with this same
	 * fixed factor. Thus, the pass energy is proportional to the kinetic energy. This mode is often
	 * used in Auger electron spectroscopy (AES) to improve S/N for high-KE electrons, but this
	 * leads to a changing energy resolution (:math:`\Delta E \sim E_p`) at different kinetic energies.
	 * It can however also be used in XPS.
	 * Synonyms: constant :math:`\Delta E/E` mode, constant retardation ratio mode, CRR
	 * mode, FRR mode
	 * This concept is related to term `12.66`_ of the ISO 18115-1:2023 standard.
	 * .. _12.66: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.66</li>
	 * <li><b>fixed_energy</b> 
	 * In the fixed energy (FE) mode, the intensity for one single kinetic energy is measured for a
	 * specified time. This mode is particularly useful during setup or alignment of the
	 * electron analyzer, for analysis of stability of the excitation source or for sample
	 * alignment.
	 * Since the mode measures intensity as a function of time, the difference in channel signals
	 * is not of interest. Therefore, the signals from all channels are summed.
	 * Synonym: FE mode</li>
	 * <li><b>snapshot</b> 
	 * Snapshot mode does not involve an energy scan and instead collects data from all channels of
	 * the detector without averaging. The resulting spectrum reflects the energy distribution of
	 * particles passing through the analyzer using the current settings. This mode is commonly used
	 * to position the detection energy at the maximum of a peak and record the signal, enabling faster
	 * data acquisition within a limited energy range compared to FAT. Snapshot measurements are
	 * particularly suitable for CCD and DLD detectors, which have multiple channels and can accurately
	 * display the peak shape. While five or nine-channel detectors can also be used for snapshot
	 * measurements, their energy resolution is relatively lower.</li>
	 * <li><b>dither</b> 
	 * In dither acquisition mode, the kinetic energy of the analyzer is randomly varied by a small value
	 * around a central value and at fixed pass energy. This reduces or removes inhomogeneities
	 * of the detector efficiency, such as e.g. imposed by a mesh in front of the detector.
	 * Mostly relevant for CCD/DLD type of detectors.</li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEnergy_scan_mode();

	/**
	 * Way of scanning the energy axis
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>fixed_analyzer_transmission</b> 
	 * constant :math:`\Delta E` mode, where the electron retardation (i.e., the fraction of pass energy to
	 * kinetic energy, :math:`R = (E_K - W)/E_p`, is scanned, but the pass energy :math:`E_p` is kept constant.
	 * Here, :math:`W = e \phi` is the spectrometer work function (with the potential difference :math:`\phi`
	 * between the electrochemical potential of electrons in the bulk and the electrostatic potential of an electron in the
	 * vacuum just outside the surface).
	 * This mode is often used in X-ray/ultraviolet photoemission spectroscopy (XPS/UPS) because the energy resolution does
	 * not change with changing energy (due to the constant pass energy).
	 * Synonyms: constant :math:`\Delta E` mode, constant analyzer energy mode, CAE
	 * mode, FAT mode
	 * This concept is related to term `12.64`_ of the ISO 18115-1:2023 standard.
	 * .. _12.64: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.64</li>
	 * <li><b>fixed_retardation_ratio</b> 
	 * constant :math:`\Delta E/E` mode, where the pass energy is scanned such that the electron retardation
	 * ratio is constant. In this mode, electrons of all energies are decelerated with this same
	 * fixed factor. Thus, the pass energy is proportional to the kinetic energy. This mode is often
	 * used in Auger electron spectroscopy (AES) to improve S/N for high-KE electrons, but this
	 * leads to a changing energy resolution (:math:`\Delta E \sim E_p`) at different kinetic energies.
	 * It can however also be used in XPS.
	 * Synonyms: constant :math:`\Delta E/E` mode, constant retardation ratio mode, CRR
	 * mode, FRR mode
	 * This concept is related to term `12.66`_ of the ISO 18115-1:2023 standard.
	 * .. _12.66: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.66</li>
	 * <li><b>fixed_energy</b> 
	 * In the fixed energy (FE) mode, the intensity for one single kinetic energy is measured for a
	 * specified time. This mode is particularly useful during setup or alignment of the
	 * electron analyzer, for analysis of stability of the excitation source or for sample
	 * alignment.
	 * Since the mode measures intensity as a function of time, the difference in channel signals
	 * is not of interest. Therefore, the signals from all channels are summed.
	 * Synonym: FE mode</li>
	 * <li><b>snapshot</b> 
	 * Snapshot mode does not involve an energy scan and instead collects data from all channels of
	 * the detector without averaging. The resulting spectrum reflects the energy distribution of
	 * particles passing through the analyzer using the current settings. This mode is commonly used
	 * to position the detection energy at the maximum of a peak and record the signal, enabling faster
	 * data acquisition within a limited energy range compared to FAT. Snapshot measurements are
	 * particularly suitable for CCD and DLD detectors, which have multiple channels and can accurately
	 * display the peak shape. While five or nine-channel detectors can also be used for snapshot
	 * measurements, their energy resolution is relatively lower.</li>
	 * <li><b>dither</b> 
	 * In dither acquisition mode, the kinetic energy of the analyzer is randomly varied by a small value
	 * around a central value and at fixed pass energy. This reduces or removes inhomogeneities
	 * of the detector efficiency, such as e.g. imposed by a mesh in front of the detector.
	 * Mostly relevant for CCD/DLD type of detectors.</li></ul></p>
	 * </p>
	 *
	 * @param energy_scan_modeDataset the energy_scan_modeDataset
	 */
	public DataNode setEnergy_scan_mode(IDataset energy_scan_modeDataset);

	/**
	 * Way of scanning the energy axis
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>fixed_analyzer_transmission</b> 
	 * constant :math:`\Delta E` mode, where the electron retardation (i.e., the fraction of pass energy to
	 * kinetic energy, :math:`R = (E_K - W)/E_p`, is scanned, but the pass energy :math:`E_p` is kept constant.
	 * Here, :math:`W = e \phi` is the spectrometer work function (with the potential difference :math:`\phi`
	 * between the electrochemical potential of electrons in the bulk and the electrostatic potential of an electron in the
	 * vacuum just outside the surface).
	 * This mode is often used in X-ray/ultraviolet photoemission spectroscopy (XPS/UPS) because the energy resolution does
	 * not change with changing energy (due to the constant pass energy).
	 * Synonyms: constant :math:`\Delta E` mode, constant analyzer energy mode, CAE
	 * mode, FAT mode
	 * This concept is related to term `12.64`_ of the ISO 18115-1:2023 standard.
	 * .. _12.64: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.64</li>
	 * <li><b>fixed_retardation_ratio</b> 
	 * constant :math:`\Delta E/E` mode, where the pass energy is scanned such that the electron retardation
	 * ratio is constant. In this mode, electrons of all energies are decelerated with this same
	 * fixed factor. Thus, the pass energy is proportional to the kinetic energy. This mode is often
	 * used in Auger electron spectroscopy (AES) to improve S/N for high-KE electrons, but this
	 * leads to a changing energy resolution (:math:`\Delta E \sim E_p`) at different kinetic energies.
	 * It can however also be used in XPS.
	 * Synonyms: constant :math:`\Delta E/E` mode, constant retardation ratio mode, CRR
	 * mode, FRR mode
	 * This concept is related to term `12.66`_ of the ISO 18115-1:2023 standard.
	 * .. _12.66: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.66</li>
	 * <li><b>fixed_energy</b> 
	 * In the fixed energy (FE) mode, the intensity for one single kinetic energy is measured for a
	 * specified time. This mode is particularly useful during setup or alignment of the
	 * electron analyzer, for analysis of stability of the excitation source or for sample
	 * alignment.
	 * Since the mode measures intensity as a function of time, the difference in channel signals
	 * is not of interest. Therefore, the signals from all channels are summed.
	 * Synonym: FE mode</li>
	 * <li><b>snapshot</b> 
	 * Snapshot mode does not involve an energy scan and instead collects data from all channels of
	 * the detector without averaging. The resulting spectrum reflects the energy distribution of
	 * particles passing through the analyzer using the current settings. This mode is commonly used
	 * to position the detection energy at the maximum of a peak and record the signal, enabling faster
	 * data acquisition within a limited energy range compared to FAT. Snapshot measurements are
	 * particularly suitable for CCD and DLD detectors, which have multiple channels and can accurately
	 * display the peak shape. While five or nine-channel detectors can also be used for snapshot
	 * measurements, their energy resolution is relatively lower.</li>
	 * <li><b>dither</b> 
	 * In dither acquisition mode, the kinetic energy of the analyzer is randomly varied by a small value
	 * around a central value and at fixed pass energy. This reduces or removes inhomogeneities
	 * of the detector efficiency, such as e.g. imposed by a mesh in front of the detector.
	 * Mostly relevant for CCD/DLD type of detectors.</li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getEnergy_scan_modeScalar();

	/**
	 * Way of scanning the energy axis
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>fixed_analyzer_transmission</b> 
	 * constant :math:`\Delta E` mode, where the electron retardation (i.e., the fraction of pass energy to
	 * kinetic energy, :math:`R = (E_K - W)/E_p`, is scanned, but the pass energy :math:`E_p` is kept constant.
	 * Here, :math:`W = e \phi` is the spectrometer work function (with the potential difference :math:`\phi`
	 * between the electrochemical potential of electrons in the bulk and the electrostatic potential of an electron in the
	 * vacuum just outside the surface).
	 * This mode is often used in X-ray/ultraviolet photoemission spectroscopy (XPS/UPS) because the energy resolution does
	 * not change with changing energy (due to the constant pass energy).
	 * Synonyms: constant :math:`\Delta E` mode, constant analyzer energy mode, CAE
	 * mode, FAT mode
	 * This concept is related to term `12.64`_ of the ISO 18115-1:2023 standard.
	 * .. _12.64: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.64</li>
	 * <li><b>fixed_retardation_ratio</b> 
	 * constant :math:`\Delta E/E` mode, where the pass energy is scanned such that the electron retardation
	 * ratio is constant. In this mode, electrons of all energies are decelerated with this same
	 * fixed factor. Thus, the pass energy is proportional to the kinetic energy. This mode is often
	 * used in Auger electron spectroscopy (AES) to improve S/N for high-KE electrons, but this
	 * leads to a changing energy resolution (:math:`\Delta E \sim E_p`) at different kinetic energies.
	 * It can however also be used in XPS.
	 * Synonyms: constant :math:`\Delta E/E` mode, constant retardation ratio mode, CRR
	 * mode, FRR mode
	 * This concept is related to term `12.66`_ of the ISO 18115-1:2023 standard.
	 * .. _12.66: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.66</li>
	 * <li><b>fixed_energy</b> 
	 * In the fixed energy (FE) mode, the intensity for one single kinetic energy is measured for a
	 * specified time. This mode is particularly useful during setup or alignment of the
	 * electron analyzer, for analysis of stability of the excitation source or for sample
	 * alignment.
	 * Since the mode measures intensity as a function of time, the difference in channel signals
	 * is not of interest. Therefore, the signals from all channels are summed.
	 * Synonym: FE mode</li>
	 * <li><b>snapshot</b> 
	 * Snapshot mode does not involve an energy scan and instead collects data from all channels of
	 * the detector without averaging. The resulting spectrum reflects the energy distribution of
	 * particles passing through the analyzer using the current settings. This mode is commonly used
	 * to position the detection energy at the maximum of a peak and record the signal, enabling faster
	 * data acquisition within a limited energy range compared to FAT. Snapshot measurements are
	 * particularly suitable for CCD and DLD detectors, which have multiple channels and can accurately
	 * display the peak shape. While five or nine-channel detectors can also be used for snapshot
	 * measurements, their energy resolution is relatively lower.</li>
	 * <li><b>dither</b> 
	 * In dither acquisition mode, the kinetic energy of the analyzer is randomly varied by a small value
	 * around a central value and at fixed pass energy. This reduces or removes inhomogeneities
	 * of the detector efficiency, such as e.g. imposed by a mesh in front of the detector.
	 * Mostly relevant for CCD/DLD type of detectors.</li></ul></p>
	 * </p>
	 *
	 * @param energy_scan_mode the energy_scan_mode
	 */
	public DataNode setEnergy_scan_modeScalar(String energy_scan_modeValue);

	/**
	 * Length of the time-of-flight drift electrode
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTof_distance();

	/**
	 * Length of the time-of-flight drift electrode
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param tof_distanceDataset the tof_distanceDataset
	 */
	public DataNode setTof_distance(IDataset tof_distanceDataset);

	/**
	 * Length of the time-of-flight drift electrode
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTof_distanceScalar();

	/**
	 * Length of the time-of-flight drift electrode
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param tof_distance the tof_distance
	 */
	public DataNode setTof_distanceScalar(Double tof_distanceValue);

	/**
	 * Size, position and shape of a slit in dispersive analyzer, e.g. entrance and
	 * exit slits.
	 *
	 * @return  the value.
	 */
	public NXaperture getAperture();

	/**
	 * Size, position and shape of a slit in dispersive analyzer, e.g. entrance and
	 * exit slits.
	 *
	 * @param apertureGroup the apertureGroup
	 */
	public void setAperture(NXaperture apertureGroup);

	/**
	 * Get a NXaperture node by name:
	 * <ul>
	 * <li>
	 * Size, position and shape of a slit in dispersive analyzer, e.g. entrance and
	 * exit slits.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXaperture for that node.
	 */
	public NXaperture getAperture(String name);

	/**
	 * Set a NXaperture node by name:
	 * <ul>
	 * <li>
	 * Size, position and shape of a slit in dispersive analyzer, e.g. entrance and
	 * exit slits.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param aperture the value to set
	 */
	public void setAperture(String name, NXaperture aperture);

	/**
	 * Get all NXaperture nodes:
	 * <ul>
	 * <li>
	 * Size, position and shape of a slit in dispersive analyzer, e.g. entrance and
	 * exit slits.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXaperture for that node.
	 */
	public Map<String, NXaperture> getAllAperture();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Size, position and shape of a slit in dispersive analyzer, e.g. entrance and
	 * exit slits.</li>
	 * </ul>
	 *
	 * @param aperture the child nodes to add
	 */

	public void setAllAperture(Map<String, NXaperture> aperture);


	/**
	 * Deflectors in the energy dispersive section
	 *
	 * @return  the value.
	 */
	public NXdeflector getDeflector();

	/**
	 * Deflectors in the energy dispersive section
	 *
	 * @param deflectorGroup the deflectorGroup
	 */
	public void setDeflector(NXdeflector deflectorGroup);

	/**
	 * Get a NXdeflector node by name:
	 * <ul>
	 * <li>
	 * Deflectors in the energy dispersive section</li>
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
	 * Deflectors in the energy dispersive section</li>
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
	 * Deflectors in the energy dispersive section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public Map<String, NXdeflector> getAllDeflector();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Deflectors in the energy dispersive section</li>
	 * </ul>
	 *
	 * @param deflector the child nodes to add
	 */

	public void setAllDeflector(Map<String, NXdeflector> deflector);


	/**
	 * Individual lenses in the energy dispersive section
	 *
	 * @return  the value.
	 */
	public NXelectromagnetic_lens getElectromagnetic_lens();

	/**
	 * Individual lenses in the energy dispersive section
	 *
	 * @param electromagnetic_lensGroup the electromagnetic_lensGroup
	 */
	public void setElectromagnetic_lens(NXelectromagnetic_lens electromagnetic_lensGroup);

	/**
	 * Get a NXelectromagnetic_lens node by name:
	 * <ul>
	 * <li>
	 * Individual lenses in the energy dispersive section</li>
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
	 * Individual lenses in the energy dispersive section</li>
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
	 * Individual lenses in the energy dispersive section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXelectromagnetic_lens for that node.
	 */
	public Map<String, NXelectromagnetic_lens> getAllElectromagnetic_lens();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual lenses in the energy dispersive section</li>
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


}
