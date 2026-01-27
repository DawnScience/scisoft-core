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
 * Computational geometry description of a set of ellipsoids.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality of the space in which the members are assumed embedded.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of members.</li></ul></p>
 *
 */
public interface NXcg_ellipsoid extends NXcg_primitive {

	public static final String NX_SEMI_AXES_VALUE = "semi_axes_value";
	public static final String NX_SEMI_AXES_VALUES = "semi_axes_values";
	public static final String NX_RADIUS = "radius";
	public static final String NX_RADII = "radii";
	/**
	 * Length of the semi-axes (e.g. semi-major and semi-minor
	 * respectively for an ellipse).
	 * Use if all ellipsoids in the set have the same half-axes.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSemi_axes_value();

	/**
	 * Length of the semi-axes (e.g. semi-major and semi-minor
	 * respectively for an ellipse).
	 * Use if all ellipsoids in the set have the same half-axes.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param semi_axes_valueDataset the semi_axes_valueDataset
	 */
	public DataNode setSemi_axes_value(IDataset semi_axes_valueDataset);

	/**
	 * Length of the semi-axes (e.g. semi-major and semi-minor
	 * respectively for an ellipse).
	 * Use if all ellipsoids in the set have the same half-axes.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSemi_axes_valueScalar();

	/**
	 * Length of the semi-axes (e.g. semi-major and semi-minor
	 * respectively for an ellipse).
	 * Use if all ellipsoids in the set have the same half-axes.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param semi_axes_value the semi_axes_value
	 */
	public DataNode setSemi_axes_valueScalar(Number semi_axes_valueValue);

	/**
	 * Length of the semi-axes if ellipsoids have individually different lengths.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSemi_axes_values();

	/**
	 * Length of the semi-axes if ellipsoids have individually different lengths.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param semi_axes_valuesDataset the semi_axes_valuesDataset
	 */
	public DataNode setSemi_axes_values(IDataset semi_axes_valuesDataset);

	/**
	 * Length of the semi-axes if ellipsoids have individually different lengths.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSemi_axes_valuesScalar();

	/**
	 * Length of the semi-axes if ellipsoids have individually different lengths.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param semi_axes_values the semi_axes_values
	 */
	public DataNode setSemi_axes_valuesScalar(Number semi_axes_valuesValue);

	/**
	 * In the case that all ellipsoids are spheres.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRadius();

	/**
	 * In the case that all ellipsoids are spheres.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param radiusDataset the radiusDataset
	 */
	public DataNode setRadius(IDataset radiusDataset);

	/**
	 * In the case that all ellipsoids are spheres.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRadiusScalar();

	/**
	 * In the case that all ellipsoids are spheres.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param radius the radius
	 */
	public DataNode setRadiusScalar(Number radiusValue);

	/**
	 * In the case that all ellipsoids are spheres whose radii differ.
	 * For a mixture of spheres use semi_axes_values.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRadii();

	/**
	 * In the case that all ellipsoids are spheres whose radii differ.
	 * For a mixture of spheres use semi_axes_values.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param radiiDataset the radiiDataset
	 */
	public DataNode setRadii(IDataset radiiDataset);

	/**
	 * In the case that all ellipsoids are spheres whose radii differ.
	 * For a mixture of spheres use semi_axes_values.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRadiiScalar();

	/**
	 * In the case that all ellipsoids are spheres whose radii differ.
	 * For a mixture of spheres use semi_axes_values.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param radii the radii
	 */
	public DataNode setRadiiScalar(Number radiiValue);

}
