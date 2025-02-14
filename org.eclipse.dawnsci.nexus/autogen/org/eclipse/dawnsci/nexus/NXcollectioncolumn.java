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
 * Subclass of NXelectronanalyser to describe the electron collection column of a
 * photoelectron analyser.
 *
 */
public interface NXcollectioncolumn extends NXobject {

	public static final String NX_SCHEME = "scheme";
	public static final String NX_EXTRACTOR_VOLTAGE = "extractor_voltage";
	public static final String NX_EXTRACTOR_CURRENT = "extractor_current";
	public static final String NX_WORKING_DISTANCE = "working_distance";
	public static final String NX_MODE = "mode";
	public static final String NX_PROJECTION = "projection";
	public static final String NX_MAGNIFICATION = "magnification";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Scheme of the electron collection lens, i.e. standard, deflector, PEEM, momentum
	 * microscope, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getScheme();

	/**
	 * Scheme of the electron collection lens, i.e. standard, deflector, PEEM, momentum
	 * microscope, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param schemeDataset the schemeDataset
	 */
	public DataNode setScheme(IDataset schemeDataset);

	/**
	 * Scheme of the electron collection lens, i.e. standard, deflector, PEEM, momentum
	 * microscope, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSchemeScalar();

	/**
	 * Scheme of the electron collection lens, i.e. standard, deflector, PEEM, momentum
	 * microscope, etc.
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
	public Dataset getMode();

	/**
	 * Labelling of the lens setting in use.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param modeDataset the modeDataset
	 */
	public DataNode setMode(IDataset modeDataset);

	/**
	 * Labelling of the lens setting in use.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getModeScalar();

	/**
	 * Labelling of the lens setting in use.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param mode the mode
	 */
	public DataNode setModeScalar(String modeValue);

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
	 * Specifies the position of the collectioncolumn by pointing to the last
	 * transformation in the transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * Specifies the position of the collectioncolumn by pointing to the last
	 * transformation in the transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Specifies the position of the collectioncolumn by pointing to the last
	 * transformation in the transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Specifies the position of the collectioncolumn by pointing to the last
	 * transformation in the transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.</li>
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
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.</li>
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
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.</li>
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
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


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
	public NXlens_em getLens_em();

	/**
	 * Individual lenses in the collection column section
	 *
	 * @param lens_emGroup the lens_emGroup
	 */
	public void setLens_em(NXlens_em lens_emGroup);

	/**
	 * Get a NXlens_em node by name:
	 * <ul>
	 * <li>
	 * Individual lenses in the collection column section</li>
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
	 * Individual lenses in the collection column section</li>
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
	 * Individual lenses in the collection column section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public Map<String, NXlens_em> getAllLens_em();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual lenses in the collection column section</li>
	 * </ul>
	 *
	 * @param lens_em the child nodes to add
	 */

	public void setAllLens_em(Map<String, NXlens_em> lens_em);


}
