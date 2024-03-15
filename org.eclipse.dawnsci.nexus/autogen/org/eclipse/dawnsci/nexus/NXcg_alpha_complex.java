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
 * Computational geometry description of alpha shapes or wrappings to primitives.
 * For details see:
 * * https://dx.doi.org/10.1109/TIT.1983.1056714 for 2D,
 * * https://dx.doi.org/10.1145/174462.156635 for 3D,
 * * https://dl.acm.org/doi/10.5555/871114 for weighted, and
 * * https://doc.cgal.org/latest/Alpha_shapes_3 for 3D implementation
 * * https://doc.cgal.org/latest/Manual/packages.html#PkgAlphaWrap3 for 3D wrap
 * in CGAL, the Computational Geometry Algorithms Library.
 * As a starting point, we follow the conventions of the CGAL library.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality of the alpha shape, for now 2 or 3.</li>
 * <li><b>n_e</b>
 * The number of edges.</li>
 * <li><b>n_f</b>
 * The number of faces.</li>
 * <li><b>n_c</b>
 * The number of cells.</li></ul></p>
 *
 */
public interface NXcg_alpha_complex extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_TYPE = "type";
	public static final String NX_MODE = "mode";
	public static final String NX_ALPHA = "alpha";
	public static final String NX_OFFSET = "offset";
	/**
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDimensionality();

	/**
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionality the dimensionality
	 */
	public DataNode setDimensionalityScalar(Long dimensionalityValue);

	/**
	 * Specify which general type of alpha shape is computed.
	 * Using for now the CGAL terminology. Basic means (unweighted) alpha shapes.
	 * Alpha_wrapping means meshes created using alpha wrapping procedures.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>convex_hull</b> </li>
	 * <li><b>alpha_shape</b> </li>
	 * <li><b>alpha_wrapping</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getType();

	/**
	 * Specify which general type of alpha shape is computed.
	 * Using for now the CGAL terminology. Basic means (unweighted) alpha shapes.
	 * Alpha_wrapping means meshes created using alpha wrapping procedures.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>convex_hull</b> </li>
	 * <li><b>alpha_shape</b> </li>
	 * <li><b>alpha_wrapping</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Specify which general type of alpha shape is computed.
	 * Using for now the CGAL terminology. Basic means (unweighted) alpha shapes.
	 * Alpha_wrapping means meshes created using alpha wrapping procedures.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>convex_hull</b> </li>
	 * <li><b>alpha_shape</b> </li>
	 * <li><b>alpha_wrapping</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Specify which general type of alpha shape is computed.
	 * Using for now the CGAL terminology. Basic means (unweighted) alpha shapes.
	 * Alpha_wrapping means meshes created using alpha wrapping procedures.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>convex_hull</b> </li>
	 * <li><b>alpha_shape</b> </li>
	 * <li><b>alpha_wrapping</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Specifically when computed with the CGAL, the mode specifies if singular
	 * faces are removed (regularized) of the alpha complex.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>general</b> </li>
	 * <li><b>regularized</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getMode();

	/**
	 * Specifically when computed with the CGAL, the mode specifies if singular
	 * faces are removed (regularized) of the alpha complex.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>general</b> </li>
	 * <li><b>regularized</b> </li></ul></p>
	 * </p>
	 *
	 * @param modeDataset the modeDataset
	 */
	public DataNode setMode(IDataset modeDataset);

	/**
	 * Specifically when computed with the CGAL, the mode specifies if singular
	 * faces are removed (regularized) of the alpha complex.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>general</b> </li>
	 * <li><b>regularized</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getModeScalar();

	/**
	 * Specifically when computed with the CGAL, the mode specifies if singular
	 * faces are removed (regularized) of the alpha complex.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>general</b> </li>
	 * <li><b>regularized</b> </li></ul></p>
	 * </p>
	 *
	 * @param mode the mode
	 */
	public DataNode setModeScalar(String modeValue);

	/**
	 * The alpha, (radius of the alpha-sphere) parameter to be used for alpha
	 * shapes and alpha wrappings.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getAlpha();

	/**
	 * The alpha, (radius of the alpha-sphere) parameter to be used for alpha
	 * shapes and alpha wrappings.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param alphaDataset the alphaDataset
	 */
	public DataNode setAlpha(IDataset alphaDataset);

	/**
	 * The alpha, (radius of the alpha-sphere) parameter to be used for alpha
	 * shapes and alpha wrappings.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAlphaScalar();

	/**
	 * The alpha, (radius of the alpha-sphere) parameter to be used for alpha
	 * shapes and alpha wrappings.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param alpha the alpha
	 */
	public DataNode setAlphaScalar(Number alphaValue);

	/**
	 * The offset distance parameter to be used in addition to alpha
	 * in the case of alpha_wrapping.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getOffset();

	/**
	 * The offset distance parameter to be used in addition to alpha
	 * in the case of alpha_wrapping.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param offsetDataset the offsetDataset
	 */
	public DataNode setOffset(IDataset offsetDataset);

	/**
	 * The offset distance parameter to be used in addition to alpha
	 * in the case of alpha_wrapping.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOffsetScalar();

	/**
	 * The offset distance parameter to be used in addition to alpha
	 * in the case of alpha_wrapping.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param offset the offset
	 */
	public DataNode setOffsetScalar(Number offsetValue);

	/**
	 * Point cloud for which the alpha shape or wrapping should be computed.
	 *
	 * @return  the value.
	 */
	public NXcg_point_set getPoint_set();

	/**
	 * Point cloud for which the alpha shape or wrapping should be computed.
	 *
	 * @param point_setGroup the point_setGroup
	 */
	public void setPoint_set(NXcg_point_set point_setGroup);

	/**
	 * Triangle soup for which the alpha wrapping should be computed.
	 *
	 * @return  the value.
	 */
	public NXcg_triangle_set getTriangle_set();

	/**
	 * Triangle soup for which the alpha wrapping should be computed.
	 *
	 * @param triangle_setGroup the triangle_setGroup
	 */
	public void setTriangle_set(NXcg_triangle_set triangle_setGroup);

	/**
	 * A meshed representation of the resulting shape.
	 *
	 * @return  the value.
	 */
	public NXcg_triangle_set getTriangulation();

	/**
	 * A meshed representation of the resulting shape.
	 *
	 * @param triangulationGroup the triangulationGroup
	 */
	public void setTriangulation(NXcg_triangle_set triangulationGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_tetrahedron_set getInterior_cells();

	/**
	 *
	 * @param interior_cellsGroup the interior_cellsGroup
	 */
	public void setInterior_cells(NXcg_tetrahedron_set interior_cellsGroup);

}
