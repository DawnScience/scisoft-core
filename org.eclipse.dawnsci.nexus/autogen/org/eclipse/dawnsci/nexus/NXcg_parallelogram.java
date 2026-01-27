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
 * Computational geometry description of a set of parallelograms.
 * This class can also be used to describe rectangles or squares, irrespective
 * whether these are axis-aligned or not. The class represents different
 * access and description levels to embrace applied scientists and computational
 * geometry experts with their different views:
 * * The simplest case is the communication of dimensions aka the size of a
 * region of interest in the 2D plane. In this case, communicating the
 * alignment with axes is maybe not as relevant as it is to report the area
 * of the ROI.
 * * In other cases the extent of the parallelogram is relevant though.
 * * Finally, in CAD models it should be possible to specify the polygons
 * which the parallelograms represent with exact numerical details.
 * Parallelograms are important geometrical primitives as their usage for
 * describing many scanning experiments shows where typically parallelogram-shaped
 * ROIs are scanned across the surface of a sample.
 * The term parallelogram will be used throughout this base class thus including
 * the important special cases rectangle, square, 2D box, axis-aligned bounding box
 * (AABB), or optimal bounding box (OBB) as analogous 2D variants to their 3D
 * counterparts. See :ref:`NXcg_hexahedron` for the generalization in 3D.
 * An axis-aligned bounding box is a common data object in computational science
 * and simulation codes to represent a rectangle whose edges are aligned with the
 * axes of a coordinate system. As a part of binary trees AABBs are important data
 * objects for executing time- as well as space-efficient queries
 * of geometric primitives in techniques like kd-trees.
 * An optimal bounding box is a common data object which provides the best, i.e.
 * most tightly fitting box about an arbitrary object. In general such boxes are
 * rotated. Other than in 3D dimensions, the rotation caliper method offers
 * a rigorous approach to compute an optimal bounding box to a point set in 2D.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of parallelograms.</li></ul></p>
 *
 */
public interface NXcg_parallelogram extends NXcg_primitive {

	public static final String NX_IS_RECTANGLE = "is_rectangle";
	public static final String NX_IS_AXIS_ALIGNED = "is_axis_aligned";
	/**
	 * To specify which parallelogram is a rectangle.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_rectangle();

	/**
	 * To specify which parallelogram is a rectangle.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_rectangleDataset the is_rectangleDataset
	 */
	public DataNode setIs_rectangle(IDataset is_rectangleDataset);

	/**
	 * To specify which parallelogram is a rectangle.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_rectangleScalar();

	/**
	 * To specify which parallelogram is a rectangle.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_rectangle the is_rectangle
	 */
	public DataNode setIs_rectangleScalar(Boolean is_rectangleValue);

	/**
	 * Only to be used if is_rectangle is present. In this case, this field
	 * describes whether parallelograms are rectangles whose primary edges
	 * are parallel to the axes of the coordinate system.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_axis_aligned();

	/**
	 * Only to be used if is_rectangle is present. In this case, this field
	 * describes whether parallelograms are rectangles whose primary edges
	 * are parallel to the axes of the coordinate system.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_axis_alignedDataset the is_axis_alignedDataset
	 */
	public DataNode setIs_axis_aligned(IDataset is_axis_alignedDataset);

	/**
	 * Only to be used if is_rectangle is present. In this case, this field
	 * describes whether parallelograms are rectangles whose primary edges
	 * are parallel to the axes of the coordinate system.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_axis_alignedScalar();

	/**
	 * Only to be used if is_rectangle is present. In this case, this field
	 * describes whether parallelograms are rectangles whose primary edges
	 * are parallel to the axes of the coordinate system.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_axis_aligned the is_axis_aligned
	 */
	public DataNode setIs_axis_alignedScalar(Boolean is_axis_alignedValue);

	/**
	 * Combined storage of all parallelograms.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getParallelograms();

	/**
	 * Combined storage of all parallelograms.
	 *
	 * @param parallelogramsGroup the parallelogramsGroup
	 */
	public void setParallelograms(NXcg_face_list_data_structure parallelogramsGroup);

	/**
	 * Individual storage of each parallelogram.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getParallelogramid();

	/**
	 * Individual storage of each parallelogram.
	 *
	 * @param parallelogramidGroup the parallelogramidGroup
	 */
	public void setParallelogramid(NXcg_face_list_data_structure parallelogramidGroup);

}
