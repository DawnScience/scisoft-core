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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Base class to describe the delocalization of point-like objects on a grid.
 * Such a procedure is for instance used in image processing and e.g. atom probe
 * microscopy (APM) to discretize a point cloud onto a grid to enable e.g.
 * computing of point density, composition, or concentration values, obtain
 * scalar fields, and compute gradients of these fields.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_p</b>
 * Number of points/objects.</li>
 * <li><b>n_m</b>
 * Number of mark data per point/object.</li>
 * <li><b>n_atoms</b>
 * Number of atoms in the whitelist.</li>
 * <li><b>n_isotopes</b>
 * Number of isotopes in the whitelist.</li></ul></p>
 *
 */
public interface NXdelocalization extends NXobject {

	public static final String NX_GRID = "grid";
	public static final String NX_OBJECTS = "objects";
	public static final String NX_WEIGHTING_MODEL = "weighting_model";
	public static final String NX_ELEMENT_WHITELIST = "element_whitelist";
	public static final String NX_ISOTOPE_WHITELIST = "isotope_whitelist";
	public static final String NX_MARK = "mark";
	public static final String NX_WEIGHT = "weight";
	/**
	 * Reference or link to the grid on which the delocalization is applied.
	 *
	 * @return  the value.
	 */
	public IDataset getGrid();

	/**
	 * Reference or link to the grid on which the delocalization is applied.
	 *
	 * @param gridDataset the gridDataset
	 */
	public DataNode setGrid(IDataset gridDataset);

	/**
	 * Reference or link to the grid on which the delocalization is applied.
	 *
	 * @return  the value.
	 */
	public String getGridScalar();

	/**
	 * Reference or link to the grid on which the delocalization is applied.
	 *
	 * @param grid the grid
	 */
	public DataNode setGridScalar(String gridValue);

	/**
	 * Reference or link to the points which are delocalized on the grid.
	 *
	 * @return  the value.
	 */
	public IDataset getObjects();

	/**
	 * Reference or link to the points which are delocalized on the grid.
	 *
	 * @param objectsDataset the objectsDataset
	 */
	public DataNode setObjects(IDataset objectsDataset);

	/**
	 * Reference or link to the points which are delocalized on the grid.
	 *
	 * @return  the value.
	 */
	public String getObjectsScalar();

	/**
	 * Reference or link to the points which are delocalized on the grid.
	 *
	 * @param objects the objects
	 */
	public DataNode setObjectsScalar(String objectsValue);

	/**
	 * The weighting model specifies how mark data are mapped to a weight per point.
	 * For atom probe microscopy (APM) as an example, different models are used which
	 * account differently for the multiplicity of an ion/atom:
	 * * default, points all get the same weight 1.;
	 * for APM this is equivalent to ion species
	 * * atomic_decomposition, points get as much weight as they have atoms
	 * of a type in element_whitelist,
	 * * isotope_decomposition, points get as much weight as they have
	 * isotopes of a type in isotope_whitelist.
	 * This description shows an example that could be reinterpreted for
	 * similar such data processing steps in other fields of science.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>default</b> </li>
	 * <li><b>atomic_decomposition</b> </li>
	 * <li><b>isotope_decomposition</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWeighting_model();

	/**
	 * The weighting model specifies how mark data are mapped to a weight per point.
	 * For atom probe microscopy (APM) as an example, different models are used which
	 * account differently for the multiplicity of an ion/atom:
	 * * default, points all get the same weight 1.;
	 * for APM this is equivalent to ion species
	 * * atomic_decomposition, points get as much weight as they have atoms
	 * of a type in element_whitelist,
	 * * isotope_decomposition, points get as much weight as they have
	 * isotopes of a type in isotope_whitelist.
	 * This description shows an example that could be reinterpreted for
	 * similar such data processing steps in other fields of science.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>default</b> </li>
	 * <li><b>atomic_decomposition</b> </li>
	 * <li><b>isotope_decomposition</b> </li></ul></p>
	 * </p>
	 *
	 * @param weighting_modelDataset the weighting_modelDataset
	 */
	public DataNode setWeighting_model(IDataset weighting_modelDataset);

	/**
	 * The weighting model specifies how mark data are mapped to a weight per point.
	 * For atom probe microscopy (APM) as an example, different models are used which
	 * account differently for the multiplicity of an ion/atom:
	 * * default, points all get the same weight 1.;
	 * for APM this is equivalent to ion species
	 * * atomic_decomposition, points get as much weight as they have atoms
	 * of a type in element_whitelist,
	 * * isotope_decomposition, points get as much weight as they have
	 * isotopes of a type in isotope_whitelist.
	 * This description shows an example that could be reinterpreted for
	 * similar such data processing steps in other fields of science.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>default</b> </li>
	 * <li><b>atomic_decomposition</b> </li>
	 * <li><b>isotope_decomposition</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getWeighting_modelScalar();

	/**
	 * The weighting model specifies how mark data are mapped to a weight per point.
	 * For atom probe microscopy (APM) as an example, different models are used which
	 * account differently for the multiplicity of an ion/atom:
	 * * default, points all get the same weight 1.;
	 * for APM this is equivalent to ion species
	 * * atomic_decomposition, points get as much weight as they have atoms
	 * of a type in element_whitelist,
	 * * isotope_decomposition, points get as much weight as they have
	 * isotopes of a type in isotope_whitelist.
	 * This description shows an example that could be reinterpreted for
	 * similar such data processing steps in other fields of science.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>default</b> </li>
	 * <li><b>atomic_decomposition</b> </li>
	 * <li><b>isotope_decomposition</b> </li></ul></p>
	 * </p>
	 *
	 * @param weighting_model the weighting_model
	 */
	public DataNode setWeighting_modelScalar(String weighting_modelValue);

	/**
	 * A list of elements (via proton number) to consider for the atomic_decomposition
	 * weighting model. Elements must exist in the periodic table of elements.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_atoms;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getElement_whitelist();

	/**
	 * A list of elements (via proton number) to consider for the atomic_decomposition
	 * weighting model. Elements must exist in the periodic table of elements.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_atoms;
	 * </p>
	 *
	 * @param element_whitelistDataset the element_whitelistDataset
	 */
	public DataNode setElement_whitelist(IDataset element_whitelistDataset);

	/**
	 * A list of elements (via proton number) to consider for the atomic_decomposition
	 * weighting model. Elements must exist in the periodic table of elements.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_atoms;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getElement_whitelistScalar();

	/**
	 * A list of elements (via proton number) to consider for the atomic_decomposition
	 * weighting model. Elements must exist in the periodic table of elements.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_atoms;
	 * </p>
	 *
	 * @param element_whitelist the element_whitelist
	 */
	public DataNode setElement_whitelistScalar(Long element_whitelistValue);

	/**
	 * A list of isotopes to consider for the isotope_decomposition weighting model.
	 * Isotopes must exist in the nuclid table. Entries in the fastest changing
	 * dimension should be the pair of proton (first entry) and neutron number
	 * (second entry).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_isotopes; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIsotope_whitelist();

	/**
	 * A list of isotopes to consider for the isotope_decomposition weighting model.
	 * Isotopes must exist in the nuclid table. Entries in the fastest changing
	 * dimension should be the pair of proton (first entry) and neutron number
	 * (second entry).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_isotopes; 2: 2;
	 * </p>
	 *
	 * @param isotope_whitelistDataset the isotope_whitelistDataset
	 */
	public DataNode setIsotope_whitelist(IDataset isotope_whitelistDataset);

	/**
	 * A list of isotopes to consider for the isotope_decomposition weighting model.
	 * Isotopes must exist in the nuclid table. Entries in the fastest changing
	 * dimension should be the pair of proton (first entry) and neutron number
	 * (second entry).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_isotopes; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIsotope_whitelistScalar();

	/**
	 * A list of isotopes to consider for the isotope_decomposition weighting model.
	 * Isotopes must exist in the nuclid table. Entries in the fastest changing
	 * dimension should be the pair of proton (first entry) and neutron number
	 * (second entry).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_isotopes; 2: 2;
	 * </p>
	 *
	 * @param isotope_whitelist the isotope_whitelist
	 */
	public DataNode setIsotope_whitelistScalar(Long isotope_whitelistValue);

	/**
	 * Attribute data for each member of the point cloud. For APM these are the
	 * ion species labels generated via ranging. The number of mark data per
	 * point is 1 in the case for atom probe.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n_p; 2: n_m;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getMark();

	/**
	 * Attribute data for each member of the point cloud. For APM these are the
	 * ion species labels generated via ranging. The number of mark data per
	 * point is 1 in the case for atom probe.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n_p; 2: n_m;
	 * </p>
	 *
	 * @param markDataset the markDataset
	 */
	public DataNode setMark(IDataset markDataset);

	/**
	 * Attribute data for each member of the point cloud. For APM these are the
	 * ion species labels generated via ranging. The number of mark data per
	 * point is 1 in the case for atom probe.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n_p; 2: n_m;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMarkScalar();

	/**
	 * Attribute data for each member of the point cloud. For APM these are the
	 * ion species labels generated via ranging. The number of mark data per
	 * point is 1 in the case for atom probe.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n_p; 2: n_m;
	 * </p>
	 *
	 * @param mark the mark
	 */
	public DataNode setMarkScalar(Number markValue);

	/**
	 * Weighting factor with which the integrated intensity per grid cell is
	 * multiplied specifically for each point. For APM the weight are positive
	 * integer values, specifically the multiplicity of the ion,
	 * according to the details of the weighting_model.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWeight();

	/**
	 * Weighting factor with which the integrated intensity per grid cell is
	 * multiplied specifically for each point. For APM the weight are positive
	 * integer values, specifically the multiplicity of the ion,
	 * according to the details of the weighting_model.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param weightDataset the weightDataset
	 */
	public DataNode setWeight(IDataset weightDataset);

	/**
	 * Weighting factor with which the integrated intensity per grid cell is
	 * multiplied specifically for each point. For APM the weight are positive
	 * integer values, specifically the multiplicity of the ion,
	 * according to the details of the weighting_model.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getWeightScalar();

	/**
	 * Weighting factor with which the integrated intensity per grid cell is
	 * multiplied specifically for each point. For APM the weight are positive
	 * integer values, specifically the multiplicity of the ion,
	 * according to the details of the weighting_model.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param weight the weight
	 */
	public DataNode setWeightScalar(Number weightValue);

}
