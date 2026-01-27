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
import org.eclipse.january.dataset.Dataset;

/**
 * Base class for describing isocontouring/phase-fields in Euclidean space.
 * Iso-contouring algorithms such as Marching Cubes and others are frequently
 * used to segment d-dimensional regions at crossings of a threshold value,
 * the so-called isovalue.
 * In Computational Materials Science phase-field methods are frequently used.
 * Phase-field variables are discretized frequently using regular grids.
 * Isocontour algorithms are often used in such context to pinpoint the
 * locations of microstructural features from this implicit phase-field-
 * variable-value-based description.
 * One of the key intentions of this base class is to provide a starting point
 * for scientists from the phase-field community (condensed-matter physicists,
 * and materials engineers) to incentivize that also phase-field (and other)
 * simulation data can take advantage of NeXus base class to improve
 * interoperability.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality of the description.</li></ul></p>
 *
 */
public interface NXisocontour extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_ISOVALUE = "isovalue";
	/**
	 * The dimensionality of the space in which the isocontour is embedded.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDimensionality();

	/**
	 * The dimensionality of the space in which the isocontour is embedded.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * The dimensionality of the space in which the isocontour is embedded.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * The dimensionality of the space in which the isocontour is embedded.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionality the dimensionality
	 */
	public DataNode setDimensionalityScalar(Long dimensionalityValue);

	/**
	 * The discretized grid on which the iso-contour algorithm operates.
	 *
	 * @return  the value.
	 */
	public NXcg_grid getGrid();

	/**
	 * The discretized grid on which the iso-contour algorithm operates.
	 *
	 * @param gridGroup the gridGroup
	 */
	public void setGrid(NXcg_grid gridGroup);

	/**
	 * The threshold or iso-contour value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIsovalue();

	/**
	 * The threshold or iso-contour value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param isovalueDataset the isovalueDataset
	 */
	public DataNode setIsovalue(IDataset isovalueDataset);

	/**
	 * The threshold or iso-contour value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getIsovalueScalar();

	/**
	 * The threshold or iso-contour value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param isovalue the isovalue
	 */
	public DataNode setIsovalueScalar(Number isovalueValue);

}
