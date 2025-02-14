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
 * Subclass of NXinstrument to describe a photoelectron analyser.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays<ul>
 * <li><b>nfa</b>
 * Number of fast axes (axes acquired symultaneously, without scanning a pysical
 * quantity)</li>
 * <li><b>nsa</b>
 * Number of slow axes (axes acquired scanning a pysical quantity)</li></ul></p>
 *
 */
public interface NXelectronanalyser extends NXobject {

	public static final String NX_DESCRIPTION = "description";
	public static final String NX_NAME = "name";
	public static final String NX_NAME_ATTRIBUTE_SHORT_NAME = "short_name";
	public static final String NX_ENERGY_RESOLUTION = "energy_resolution";
	public static final String NX_MOMENTUM_RESOLUTION = "momentum_resolution";
	public static final String NX_ANGULAR_RESOLUTION = "angular_resolution";
	public static final String NX_SPATIAL_RESOLUTION = "spatial_resolution";
	public static final String NX_FAST_AXES = "fast_axes";
	public static final String NX_SLOW_AXES = "slow_axes";
	public static final String NX_DEPENDS_ON = "depends_on";
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
	 * Energy resolution of the electron analyser (FWHM of gaussian broadening)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEnergy_resolution();

	/**
	 * Energy resolution of the electron analyser (FWHM of gaussian broadening)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param energy_resolutionDataset the energy_resolutionDataset
	 */
	public DataNode setEnergy_resolution(IDataset energy_resolutionDataset);

	/**
	 * Energy resolution of the electron analyser (FWHM of gaussian broadening)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getEnergy_resolutionScalar();

	/**
	 * Energy resolution of the electron analyser (FWHM of gaussian broadening)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param energy_resolution the energy_resolution
	 */
	public DataNode setEnergy_resolutionScalar(Double energy_resolutionValue);

	/**
	 * Momentum resolution of the electron analyser (FWHM)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVENUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMomentum_resolution();

	/**
	 * Momentum resolution of the electron analyser (FWHM)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVENUMBER
	 * </p>
	 *
	 * @param momentum_resolutionDataset the momentum_resolutionDataset
	 */
	public DataNode setMomentum_resolution(IDataset momentum_resolutionDataset);

	/**
	 * Momentum resolution of the electron analyser (FWHM)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVENUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMomentum_resolutionScalar();

	/**
	 * Momentum resolution of the electron analyser (FWHM)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVENUMBER
	 * </p>
	 *
	 * @param momentum_resolution the momentum_resolution
	 */
	public DataNode setMomentum_resolutionScalar(Double momentum_resolutionValue);

	/**
	 * Angular resolution of the electron analyser (FWHM)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAngular_resolution();

	/**
	 * Angular resolution of the electron analyser (FWHM)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param angular_resolutionDataset the angular_resolutionDataset
	 */
	public DataNode setAngular_resolution(IDataset angular_resolutionDataset);

	/**
	 * Angular resolution of the electron analyser (FWHM)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAngular_resolutionScalar();

	/**
	 * Angular resolution of the electron analyser (FWHM)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param angular_resolution the angular_resolution
	 */
	public DataNode setAngular_resolutionScalar(Double angular_resolutionValue);

	/**
	 * Spatial resolution of the electron analyser (Airy disk radius)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSpatial_resolution();

	/**
	 * Spatial resolution of the electron analyser (Airy disk radius)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param spatial_resolutionDataset the spatial_resolutionDataset
	 */
	public DataNode setSpatial_resolution(IDataset spatial_resolutionDataset);

	/**
	 * Spatial resolution of the electron analyser (Airy disk radius)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSpatial_resolutionScalar();

	/**
	 * Spatial resolution of the electron analyser (Airy disk radius)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param spatial_resolution the spatial_resolution
	 */
	public DataNode setSpatial_resolutionScalar(Double spatial_resolutionValue);

	/**
	 * List of the axes that are acquired simultaneously by the detector.
	 * These refer only to the experimental variables recorded by the electron analyser.
	 * Other variables such as temperature, manipulator angles etc. are labeled as fast or slow in the data.
	 * .. csv-table:: Examples
	 * :header: "Mode", "fast_axes", "slow_axes"
	 * Hemispherical in ARPES mode, "['energy', 'kx']",""
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
	 * These refer only to the experimental variables recorded by the electron analyser.
	 * Other variables such as temperature, manipulator angles etc. are labeled as fast or slow in the data.
	 * .. csv-table:: Examples
	 * :header: "Mode", "fast_axes", "slow_axes"
	 * Hemispherical in ARPES mode, "['energy', 'kx']",""
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
	 * These refer only to the experimental variables recorded by the electron analyser.
	 * Other variables such as temperature, manipulator angles etc. are labeled as fast or slow in the data.
	 * .. csv-table:: Examples
	 * :header: "Mode", "fast_axes", "slow_axes"
	 * Hemispherical in ARPES mode, "['energy', 'kx']",""
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
	 * These refer only to the experimental variables recorded by the electron analyser.
	 * Other variables such as temperature, manipulator angles etc. are labeled as fast or slow in the data.
	 * .. csv-table:: Examples
	 * :header: "Mode", "fast_axes", "slow_axes"
	 * Hemispherical in ARPES mode, "['energy', 'kx']",""
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
	 * Refers to the last transformation specifying the positon of the manipulator in
	 * the NXtransformations chain.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * Refers to the last transformation specifying the positon of the manipulator in
	 * the NXtransformations chain.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Refers to the last transformation specifying the positon of the manipulator in
	 * the NXtransformations chain.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Refers to the last transformation specifying the positon of the manipulator in
	 * the NXtransformations chain.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the electron analyser as a component in the instrument. Conventions
	 * from the NXtransformations base class are used. In principle, the McStas
	 * coordinate system is used. The first transformation has to point either to
	 * another component of the system or . (for pointing to the reference frame) to
	 * relate it relative to the experimental setup. Typically, the components of a
	 * system should all be related relative to each other and only one component
	 * should relate to the reference coordinate system.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the electron analyser as a component in the instrument. Conventions
	 * from the NXtransformations base class are used. In principle, the McStas
	 * coordinate system is used. The first transformation has to point either to
	 * another component of the system or . (for pointing to the reference frame) to
	 * relate it relative to the experimental setup. Typically, the components of a
	 * system should all be related relative to each other and only one component
	 * should relate to the reference coordinate system.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the electron analyser as a component in the instrument. Conventions
	 * from the NXtransformations base class are used. In principle, the McStas
	 * coordinate system is used. The first transformation has to point either to
	 * another component of the system or . (for pointing to the reference frame) to
	 * relate it relative to the experimental setup. Typically, the components of a
	 * system should all be related relative to each other and only one component
	 * should relate to the reference coordinate system.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the electron analyser as a component in the instrument. Conventions
	 * from the NXtransformations base class are used. In principle, the McStas
	 * coordinate system is used. The first transformation has to point either to
	 * another component of the system or . (for pointing to the reference frame) to
	 * relate it relative to the experimental setup. Typically, the components of a
	 * system should all be related relative to each other and only one component
	 * should relate to the reference coordinate system.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the electron analyser as a component in the instrument. Conventions
	 * from the NXtransformations base class are used. In principle, the McStas
	 * coordinate system is used. The first transformation has to point either to
	 * another component of the system or . (for pointing to the reference frame) to
	 * relate it relative to the experimental setup. Typically, the components of a
	 * system should all be related relative to each other and only one component
	 * should relate to the reference coordinate system.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the electron analyser as a component in the instrument. Conventions
	 * from the NXtransformations base class are used. In principle, the McStas
	 * coordinate system is used. The first transformation has to point either to
	 * another component of the system or . (for pointing to the reference frame) to
	 * relate it relative to the experimental setup. Typically, the components of a
	 * system should all be related relative to each other and only one component
	 * should relate to the reference coordinate system.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


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
	 * Deflectors outside the main optics ensambles described by the subclasses
	 *
	 * @return  the value.
	 */
	public NXdeflector getDeflector();

	/**
	 * Deflectors outside the main optics ensambles described by the subclasses
	 *
	 * @param deflectorGroup the deflectorGroup
	 */
	public void setDeflector(NXdeflector deflectorGroup);

	/**
	 * Get a NXdeflector node by name:
	 * <ul>
	 * <li>
	 * Deflectors outside the main optics ensambles described by the subclasses</li>
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
	 * Deflectors outside the main optics ensambles described by the subclasses</li>
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
	 * Deflectors outside the main optics ensambles described by the subclasses</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public Map<String, NXdeflector> getAllDeflector();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Deflectors outside the main optics ensambles described by the subclasses</li>
	 * </ul>
	 *
	 * @param deflector the child nodes to add
	 */

	public void setAllDeflector(Map<String, NXdeflector> deflector);


	/**
	 * Individual lenses outside the main optics ensambles described by the subclasses
	 *
	 * @return  the value.
	 */
	public NXlens_em getLens_em();

	/**
	 * Individual lenses outside the main optics ensambles described by the subclasses
	 *
	 * @param lens_emGroup the lens_emGroup
	 */
	public void setLens_em(NXlens_em lens_emGroup);

	/**
	 * Get a NXlens_em node by name:
	 * <ul>
	 * <li>
	 * Individual lenses outside the main optics ensambles described by the subclasses</li>
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
	 * Individual lenses outside the main optics ensambles described by the subclasses</li>
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
	 * Individual lenses outside the main optics ensambles described by the subclasses</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public Map<String, NXlens_em> getAllLens_em();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual lenses outside the main optics ensambles described by the subclasses</li>
	 * </ul>
	 *
	 * @param lens_em the child nodes to add
	 */

	public void setAllLens_em(Map<String, NXlens_em> lens_em);


}
