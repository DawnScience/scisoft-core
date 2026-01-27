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
 * Electron collection column of an electron analyzer.
 *
 */
public interface NXcollectioncolumn extends NXcomponent {

	public static final String NX_SCHEME = "scheme";
	public static final String NX_EXTRACTOR_VOLTAGE = "extractor_voltage";
	public static final String NX_EXTRACTOR_CURRENT = "extractor_current";
	public static final String NX_WORKING_DISTANCE = "working_distance";
	public static final String NX_LENS_MODE = "lens_mode";
	public static final String NX_PROJECTION = "projection";
	public static final String NX_ANGULAR_ACCEPTANCE = "angular_acceptance";
	public static final String NX_SPATIAL_ACCEPTANCE = "spatial_acceptance";
	public static final String NX_MAGNIFICATION = "magnification";
	/**
	 * Scheme of the electron collection lens, i.e. angular dispersive,
	 * spatial dispersive, momentum dispersive, non-dispersive, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getScheme();

	/**
	 * Scheme of the electron collection lens, i.e. angular dispersive,
	 * spatial dispersive, momentum dispersive, non-dispersive, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param schemeDataset the schemeDataset
	 */
	public DataNode setScheme(IDataset schemeDataset);

	/**
	 * Scheme of the electron collection lens, i.e. angular dispersive,
	 * spatial dispersive, momentum dispersive, non-dispersive, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSchemeScalar();

	/**
	 * Scheme of the electron collection lens, i.e. angular dispersive,
	 * spatial dispersive, momentum dispersive, non-dispersive, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param scheme the scheme
	 */
	public DataNode setSchemeScalar(String schemeValue);

	/**
	 * Voltage applied to the extractor lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getExtractor_voltage();

	/**
	 * Voltage applied to the extractor lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param extractor_voltageDataset the extractor_voltageDataset
	 */
	public DataNode setExtractor_voltage(IDataset extractor_voltageDataset);

	/**
	 * Voltage applied to the extractor lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getExtractor_voltageScalar();

	/**
	 * Voltage applied to the extractor lens
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param extractor_voltage the extractor_voltage
	 */
	public DataNode setExtractor_voltageScalar(Double extractor_voltageValue);

	/**
	 * Current necessary to keep the extractor lens at a set voltage. Variations
	 * indicate leakage, field emission or arc currents to the extractor lens.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getExtractor_current();

	/**
	 * Current necessary to keep the extractor lens at a set voltage. Variations
	 * indicate leakage, field emission or arc currents to the extractor lens.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param extractor_currentDataset the extractor_currentDataset
	 */
	public DataNode setExtractor_current(IDataset extractor_currentDataset);

	/**
	 * Current necessary to keep the extractor lens at a set voltage. Variations
	 * indicate leakage, field emission or arc currents to the extractor lens.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getExtractor_currentScalar();

	/**
	 * Current necessary to keep the extractor lens at a set voltage. Variations
	 * indicate leakage, field emission or arc currents to the extractor lens.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param extractor_current the extractor_current
	 */
	public DataNode setExtractor_currentScalar(Double extractor_currentValue);

	/**
	 * Distance between sample and detector entrance
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWorking_distance();

	/**
	 * Distance between sample and detector entrance
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param working_distanceDataset the working_distanceDataset
	 */
	public DataNode setWorking_distance(IDataset working_distanceDataset);

	/**
	 * Distance between sample and detector entrance
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getWorking_distanceScalar();

	/**
	 * Distance between sample and detector entrance
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param working_distance the working_distance
	 */
	public DataNode setWorking_distanceScalar(Double working_distanceValue);

	/**
	 * Labelling of the lens setting in use.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLens_mode();

	/**
	 * Labelling of the lens setting in use.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param lens_modeDataset the lens_modeDataset
	 */
	public DataNode setLens_mode(IDataset lens_modeDataset);

	/**
	 * Labelling of the lens setting in use.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getLens_modeScalar();

	/**
	 * Labelling of the lens setting in use.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param lens_mode the lens_mode
	 */
	public DataNode setLens_modeScalar(String lens_modeValue);

	/**
	 * The space projected in the angularly dispersive directions, real or reciprocal
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>real</b> </li>
	 * <li><b>reciprocal</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getProjection();

	/**
	 * The space projected in the angularly dispersive directions, real or reciprocal
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>real</b> </li>
	 * <li><b>reciprocal</b> </li></ul></p>
	 * </p>
	 *
	 * @param projectionDataset the projectionDataset
	 */
	public DataNode setProjection(IDataset projectionDataset);

	/**
	 * The space projected in the angularly dispersive directions, real or reciprocal
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>real</b> </li>
	 * <li><b>reciprocal</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getProjectionScalar();

	/**
	 * The space projected in the angularly dispersive directions, real or reciprocal
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>real</b> </li>
	 * <li><b>reciprocal</b> </li></ul></p>
	 * </p>
	 *
	 * @param projection the projection
	 */
	public DataNode setProjectionScalar(String projectionValue);

	/**
	 * Acceptance angle of the collection column.
	 * This concept is related to term `7.4`_ of the ISO 18115-1:2023 standard.
	 * .. _7.4: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:7.4
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAngular_acceptance();

	/**
	 * Acceptance angle of the collection column.
	 * This concept is related to term `7.4`_ of the ISO 18115-1:2023 standard.
	 * .. _7.4: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:7.4
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param angular_acceptanceDataset the angular_acceptanceDataset
	 */
	public DataNode setAngular_acceptance(IDataset angular_acceptanceDataset);

	/**
	 * Acceptance angle of the collection column.
	 * This concept is related to term `7.4`_ of the ISO 18115-1:2023 standard.
	 * .. _7.4: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:7.4
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAngular_acceptanceScalar();

	/**
	 * Acceptance angle of the collection column.
	 * This concept is related to term `7.4`_ of the ISO 18115-1:2023 standard.
	 * .. _7.4: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:7.4
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param angular_acceptance the angular_acceptance
	 */
	public DataNode setAngular_acceptanceScalar(Double angular_acceptanceValue);

	/**
	 * Acceptance length or area of the collection column.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSpatial_acceptance();

	/**
	 * Acceptance length or area of the collection column.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param spatial_acceptanceDataset the spatial_acceptanceDataset
	 */
	public DataNode setSpatial_acceptance(IDataset spatial_acceptanceDataset);

	/**
	 * Acceptance length or area of the collection column.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSpatial_acceptanceScalar();

	/**
	 * Acceptance length or area of the collection column.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param spatial_acceptance the spatial_acceptance
	 */
	public DataNode setSpatial_acceptanceScalar(Double spatial_acceptanceValue);

	/**
	 * The magnification of the electron lens assembly.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMagnification();

	/**
	 * The magnification of the electron lens assembly.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param magnificationDataset the magnificationDataset
	 */
	public DataNode setMagnification(IDataset magnificationDataset);

	/**
	 * The magnification of the electron lens assembly.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMagnificationScalar();

	/**
	 * The magnification of the electron lens assembly.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param magnification the magnification
	 */
	public DataNode setMagnificationScalar(Double magnificationValue);

	/**
	 * The size and position of an aperture inserted in the column, e.g. field aperture
	 * or contrast aperture
	 *
	 * @return  the value.
	 */
	public NXaperture getAperture();

	/**
	 * The size and position of an aperture inserted in the column, e.g. field aperture
	 * or contrast aperture
	 *
	 * @param apertureGroup the apertureGroup
	 */
	public void setAperture(NXaperture apertureGroup);

	/**
	 * Get a NXaperture node by name:
	 * <ul>
	 * <li>
	 * The size and position of an aperture inserted in the column, e.g. field aperture
	 * or contrast aperture</li>
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
	 * The size and position of an aperture inserted in the column, e.g. field aperture
	 * or contrast aperture</li>
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
	 * The size and position of an aperture inserted in the column, e.g. field aperture
	 * or contrast aperture</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXaperture for that node.
	 */
	public Map<String, NXaperture> getAllAperture();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * The size and position of an aperture inserted in the column, e.g. field aperture
	 * or contrast aperture</li>
	 * </ul>
	 *
	 * @param aperture the child nodes to add
	 */

	public void setAllAperture(Map<String, NXaperture> aperture);


	/**
	 * Deflectors in the collection column section
	 *
	 * @return  the value.
	 */
	public NXdeflector getDeflector();

	/**
	 * Deflectors in the collection column section
	 *
	 * @param deflectorGroup the deflectorGroup
	 */
	public void setDeflector(NXdeflector deflectorGroup);

	/**
	 * Get a NXdeflector node by name:
	 * <ul>
	 * <li>
	 * Deflectors in the collection column section</li>
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
	 * Deflectors in the collection column section</li>
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
	 * Deflectors in the collection column section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public Map<String, NXdeflector> getAllDeflector();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Deflectors in the collection column section</li>
	 * </ul>
	 *
	 * @param deflector the child nodes to add
	 */

	public void setAllDeflector(Map<String, NXdeflector> deflector);


	/**
	 * Individual lenses in the collection column section
	 *
	 * @return  the value.
	 */
	public NXelectromagnetic_lens getElectromagnetic_lens();

	/**
	 * Individual lenses in the collection column section
	 *
	 * @param electromagnetic_lensGroup the electromagnetic_lensGroup
	 */
	public void setElectromagnetic_lens(NXelectromagnetic_lens electromagnetic_lensGroup);

	/**
	 * Get a NXelectromagnetic_lens node by name:
	 * <ul>
	 * <li>
	 * Individual lenses in the collection column section</li>
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
	 * Individual lenses in the collection column section</li>
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
	 * Individual lenses in the collection column section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXelectromagnetic_lens for that node.
	 */
	public Map<String, NXelectromagnetic_lens> getAllElectromagnetic_lens();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual lenses in the collection column section</li>
	 * </ul>
	 *
	 * @param electromagnetic_lens the child nodes to add
	 */

	public void setAllElectromagnetic_lens(Map<String, NXelectromagnetic_lens> electromagnetic_lens);


}
