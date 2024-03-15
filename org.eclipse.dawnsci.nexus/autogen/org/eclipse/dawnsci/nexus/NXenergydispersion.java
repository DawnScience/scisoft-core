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

/**
 * Subclass of NXelectronanalyser to describe the energy dispersion section of a
 * photoelectron analyser.
 *
 */
public interface NXenergydispersion extends NXobject {

	public static final String NX_SCHEME = "scheme";
	public static final String NX_PASS_ENERGY = "pass_energy";
	public static final String NX_CENTER_ENERGY = "center_energy";
	public static final String NX_ENERGY_INTERVAL = "energy_interval";
	public static final String NX_DIAMETER = "diameter";
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
	public IDataset getScheme();

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
	 * Energy of the electrons on the mean path of the analyser. Pass energy for
	 * hemispherics, drift energy for tofs.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPass_energy();

	/**
	 * Energy of the electrons on the mean path of the analyser. Pass energy for
	 * hemispherics, drift energy for tofs.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param pass_energyDataset the pass_energyDataset
	 */
	public DataNode setPass_energy(IDataset pass_energyDataset);

	/**
	 * Energy of the electrons on the mean path of the analyser. Pass energy for
	 * hemispherics, drift energy for tofs.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPass_energyScalar();

	/**
	 * Energy of the electrons on the mean path of the analyser. Pass energy for
	 * hemispherics, drift energy for tofs.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param pass_energy the pass_energy
	 */
	public DataNode setPass_energyScalar(Double pass_energyValue);

	/**
	 * Center of the energy window
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCenter_energy();

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
	 * With a swept scan of m steps it is a 2xm array of windows one for each
	 * measurement point.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getEnergy_interval();

	/**
	 * The interval of transmitted energies. It can be two different things depending
	 * on whether the scan is fixed or swept. With a fixed scan it is a 2 vector
	 * containing the extrema of the transmitted energy window (smaller number first).
	 * With a swept scan of m steps it is a 2xm array of windows one for each
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
	 * With a swept scan of m steps it is a 2xm array of windows one for each
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
	 * With a swept scan of m steps it is a 2xm array of windows one for each
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
	 * Diameter of the dispersive orbit
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDiameter();

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
	 * Way of scanning the energy axis (fixed or sweep).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>fixed</b> </li>
	 * <li><b>sweep</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getEnergy_scan_mode();

	/**
	 * Way of scanning the energy axis (fixed or sweep).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>fixed</b> </li>
	 * <li><b>sweep</b> </li></ul></p>
	 * </p>
	 *
	 * @param energy_scan_modeDataset the energy_scan_modeDataset
	 */
	public DataNode setEnergy_scan_mode(IDataset energy_scan_modeDataset);

	/**
	 * Way of scanning the energy axis (fixed or sweep).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>fixed</b> </li>
	 * <li><b>sweep</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getEnergy_scan_modeScalar();

	/**
	 * Way of scanning the energy axis (fixed or sweep).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>fixed</b> </li>
	 * <li><b>sweep</b> </li></ul></p>
	 * </p>
	 *
	 * @param energy_scan_mode the energy_scan_mode
	 */
	public DataNode setEnergy_scan_modeScalar(String energy_scan_modeValue);

	/**
	 * Length of the tof drift electrode
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTof_distance();

	/**
	 * Length of the tof drift electrode
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param tof_distanceDataset the tof_distanceDataset
	 */
	public DataNode setTof_distance(IDataset tof_distanceDataset);

	/**
	 * Length of the tof drift electrode
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTof_distanceScalar();

	/**
	 * Length of the tof drift electrode
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param tof_distance the tof_distance
	 */
	public DataNode setTof_distanceScalar(Double tof_distanceValue);

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
	public NXlens_em getLens_em();

	/**
	 * Individual lenses in the energy dispersive section
	 *
	 * @param lens_emGroup the lens_emGroup
	 */
	public void setLens_em(NXlens_em lens_emGroup);

	/**
	 * Get a NXlens_em node by name:
	 * <ul>
	 * <li>
	 * Individual lenses in the energy dispersive section</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public NXlens_em getLens_em(String name);

	/**
	 * Set a NXlens_em node by name:
	 * <ul>
	 * <li>
	 * Individual lenses in the energy dispersive section</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param lens_em the value to set
	 */
	public void setLens_em(String name, NXlens_em lens_em);

	/**
	 * Get all NXlens_em nodes:
	 * <ul>
	 * <li>
	 * Individual lenses in the energy dispersive section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public Map<String, NXlens_em> getAllLens_em();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual lenses in the energy dispersive section</li>
	 * </ul>
	 *
	 * @param lens_em the child nodes to add
	 */

	public void setAllLens_em(Map<String, NXlens_em> lens_em);


}
