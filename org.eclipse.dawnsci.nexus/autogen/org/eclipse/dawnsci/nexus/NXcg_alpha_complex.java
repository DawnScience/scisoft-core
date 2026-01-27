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
 * Computational geometry of alpha complexes (alpha shapes or alpha wrappings) about primitives.
 * For details see:
 * * https://dx.doi.org/10.1109/TIT.1983.1056714 for 2D,
 * * https://dx.doi.org/10.1145/174462.156635 for 3D,
 * * https://dl.acm.org/doi/10.5555/871114 for weighted, and
 * * https://doc.cgal.org/latest/Alpha_shapes_3 for 3D implementation of alpha shapes, and
 * * https://doc.cgal.org/latest/Manual/packages.html#PkgAlphaWrap3 for 3D alpha wrappings
 * in CGAL, the Computational Geometry Algorithms Library respectively.
 * As a starting point, we follow the conventions of the CGAL library.
 * In general, an alpha complex is a not necessarily connected or not necessarily pure complex,
 * i.e. singular faces may exist. The number of cells, faces, and edges depends on how a specific
 * alpha complex is filtered for lower-dimensional simplices. The fields is_regularized and
 * regularization can be used to provide details about regularization procedures.
 *
 */
public interface NXcg_alpha_complex extends NXcg_primitive {

	public static final String NX_TYPE = "type";
	public static final String NX_REGULARIZATION = "regularization";
	public static final String NX_IS_REGULARIZED = "is_regularized";
	public static final String NX_ALPHA = "alpha";
	public static final String NX_OFFSET = "offset";
	/**
	 * Type of alpha complex following the terminology used by CGAL for now.
	 * Alpha_shape means meshes created using one of the alpha_shape algorithm.
	 * Alpha_wrapping means meshes created using the alpha_wrapping algorithm.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>convex_hull</b> </li>
	 * <li><b>alpha_shape</b> </li>
	 * <li><b>alpha_wrapping</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Type of alpha complex following the terminology used by CGAL for now.
	 * Alpha_shape means meshes created using one of the alpha_shape algorithm.
	 * Alpha_wrapping means meshes created using the alpha_wrapping algorithm.
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
	 * Type of alpha complex following the terminology used by CGAL for now.
	 * Alpha_shape means meshes created using one of the alpha_shape algorithm.
	 * Alpha_wrapping means meshes created using the alpha_wrapping algorithm.
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
	 * Type of alpha complex following the terminology used by CGAL for now.
	 * Alpha_shape means meshes created using one of the alpha_shape algorithm.
	 * Alpha_wrapping means meshes created using the alpha_wrapping algorithm.
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
	 * Human-readable description about regularization procedures.
	 *
	 * @return  the value.
	 */
	public Dataset getRegularization();

	/**
	 * Human-readable description about regularization procedures.
	 *
	 * @param regularizationDataset the regularizationDataset
	 */
	public DataNode setRegularization(IDataset regularizationDataset);

	/**
	 * Human-readable description about regularization procedures.
	 *
	 * @return  the value.
	 */
	public String getRegularizationScalar();

	/**
	 * Human-readable description about regularization procedures.
	 *
	 * @param regularization the regularization
	 */
	public DataNode setRegularizationScalar(String regularizationValue);

	/**
	 * Was the alpha complex regularized, i.e. have singular faces been removed, or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_regularized();

	/**
	 * Was the alpha complex regularized, i.e. have singular faces been removed, or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_regularizedDataset the is_regularizedDataset
	 */
	public DataNode setIs_regularized(IDataset is_regularizedDataset);

	/**
	 * Was the alpha complex regularized, i.e. have singular faces been removed, or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_regularizedScalar();

	/**
	 * Was the alpha complex regularized, i.e. have singular faces been removed, or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_regularized the is_regularized
	 */
	public DataNode setIs_regularizedScalar(Boolean is_regularizedValue);

	/**
	 * The alpha parameter, i.e. the squared radius of the alpha-sphere
	 * that is used when computing the alpha complex.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAlpha();

	/**
	 * The alpha parameter, i.e. the squared radius of the alpha-sphere
	 * that is used when computing the alpha complex.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param alphaDataset the alphaDataset
	 */
	public DataNode setAlpha(IDataset alphaDataset);

	/**
	 * The alpha parameter, i.e. the squared radius of the alpha-sphere
	 * that is used when computing the alpha complex.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAlphaScalar();

	/**
	 * The alpha parameter, i.e. the squared radius of the alpha-sphere
	 * that is used when computing the alpha complex.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param alpha the alpha
	 */
	public DataNode setAlphaScalar(Number alphaValue);

	/**
	 * The offset distance parameter used when computing alpha_wrappings.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOffset();

	/**
	 * The offset distance parameter used when computing alpha_wrappings.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param offsetDataset the offsetDataset
	 */
	public DataNode setOffset(IDataset offsetDataset);

	/**
	 * The offset distance parameter used when computing alpha_wrappings.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOffsetScalar();

	/**
	 * The offset distance parameter used when computing alpha_wrappings.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param offset the offset
	 */
	public DataNode setOffsetScalar(Number offsetValue);

	/**
	 * Point cloud serving as input for the computation of the alpha complex.
	 *
	 * @return  the value.
	 */
	public NXcg_point getCg_point();

	/**
	 * Point cloud serving as input for the computation of the alpha complex.
	 *
	 * @param cg_pointGroup the cg_pointGroup
	 */
	public void setCg_point(NXcg_point cg_pointGroup);

	/**
	 * Get a NXcg_point node by name:
	 * <ul>
	 * <li>
	 * Point cloud serving as input for the computation of the alpha complex.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_point for that node.
	 */
	public NXcg_point getCg_point(String name);

	/**
	 * Set a NXcg_point node by name:
	 * <ul>
	 * <li>
	 * Point cloud serving as input for the computation of the alpha complex.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_point the value to set
	 */
	public void setCg_point(String name, NXcg_point cg_point);

	/**
	 * Get all NXcg_point nodes:
	 * <ul>
	 * <li>
	 * Point cloud serving as input for the computation of the alpha complex.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_point for that node.
	 */
	public Map<String, NXcg_point> getAllCg_point();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Point cloud serving as input for the computation of the alpha complex.</li>
	 * </ul>
	 *
	 * @param cg_point the child nodes to add
	 */

	public void setAllCg_point(Map<String, NXcg_point> cg_point);


	/**
	 * Triangle soup serving as input for the computation of the alpha complex.
	 *
	 * @return  the value.
	 */
	public NXcg_triangle getTriangle_soup();

	/**
	 * Triangle soup serving as input for the computation of the alpha complex.
	 *
	 * @param triangle_soupGroup the triangle_soupGroup
	 */
	public void setTriangle_soup(NXcg_triangle triangle_soupGroup);

	/**
	 * Triangle mesh representing the output of the computation, i.e. the alpha complex.
	 *
	 * @return  the value.
	 */
	public NXcg_triangle getAlpha_complex();

	/**
	 * Triangle mesh representing the output of the computation, i.e. the alpha complex.
	 *
	 * @param alpha_complexGroup the alpha_complexGroup
	 */
	public void setAlpha_complex(NXcg_triangle alpha_complexGroup);

	/**
	 * Tetrahedra representing an interior volume of the alpha complex (if such exists).
	 *
	 * @return  the value.
	 */
	public NXcg_tetrahedron getTetrahedralization();

	/**
	 * Tetrahedra representing an interior volume of the alpha complex (if such exists).
	 *
	 * @param tetrahedralizationGroup the tetrahedralizationGroup
	 */
	public void setTetrahedralization(NXcg_tetrahedron tetrahedralizationGroup);

}
