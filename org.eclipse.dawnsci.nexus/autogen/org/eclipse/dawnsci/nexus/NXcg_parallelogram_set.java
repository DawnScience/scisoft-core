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
 * Computational geometry description of a set of parallelograms in Euclidean space.
 * The parallelograms do not have to be connected, can have different size,
 * can intersect, and be rotated.
 * This class can also be used to describe rectangles or squares, axis-aligned or not.
 * The class represents different access and description levels to offer both
 * applied scientists and computational geometry experts to use the same
 * base class but rather their specific view on the data:
 * * Most simple many experimentalists wish to communicate dimensions/the size
 * of e.g. a region of interest in the 2D plane. In this case the alignment
 * with axes is not relevant as eventually relevant is only the area of the ROI.
 * * In other cases the extent of the parallelogram is relevant though.
 * * Finally in CAD models we would like to specify the polygon
 * which is parallelogram represents.
 * Parallelograms are important geometrical primitives. Not so much because of their
 * uses in nowadays, thanks to improvements in computing power, not so frequently
 * any longer performed 2D simulation. Many scanning experiments probe though
 * parallelogram-shaped ROIs on the surface of samples.
 * Parallelograms have to be non-degenerated, closed, and built of polygons
 * which are not self-intersecting.
 * The term parallelogram will be used throughout this base class but includes
 * the especially in engineering and more commonly used special cases,
 * rectangle, square, 2D box, axis-aligned bounding box (AABB), or
 * optimal bounding box (OBB) but here the analogous 2D cases.
 * An axis-aligned bounding box is a common data object in computational science
 * and codes to represent a rectangle whose edges are aligned with the axes
 * of a coordinate system. As a part of binary trees these are important data
 * objects for time- as well as space-efficient queries
 * of geometric primitives in techniques like kd-trees.
 * An optimal bounding box is a common data object which provides the best
 * tight fitting box about an arbitrary object. In general such boxes are
 * rotated. Other than in 3D dimensions the rotation calipher method offers
 * a rigorous approach to compute optimal bounding boxes in 2D.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of parallelograms.</li></ul></p>
 *
 */
public interface NXcg_parallelogram_set extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_SHAPE = "shape";
	public static final String NX_LENGTH = "length";
	public static final String NX_WIDTH = "width";
	public static final String NX_CENTER = "center";
	public static final String NX_SURFACE_AREA = "surface_area";
	public static final String NX_IS_AXIS_ALIGNED = "is_axis_aligned";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDimensionality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionality the dimensionality
	 */
	public DataNode setDimensionalityScalar(Long dimensionalityValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCardinality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinalityDataset the cardinalityDataset
	 */
	public DataNode setCardinality(IDataset cardinalityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCardinalityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinality the cardinality
	 */
	public DataNode setCardinalityScalar(Long cardinalityValue);

	/**
	 * A qualitative description of each parallelogram/rectangle/square/box.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getShape();

	/**
	 * A qualitative description of each parallelogram/rectangle/square/box.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @param shapeDataset the shapeDataset
	 */
	public DataNode setShape(IDataset shapeDataset);

	/**
	 * A qualitative description of each parallelogram/rectangle/square/box.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getShapeScalar();

	/**
	 * A qualitative description of each parallelogram/rectangle/square/box.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @param shape the shape
	 */
	public DataNode setShapeScalar(Number shapeValue);

	/**
	 * Qualifier how one edge is longer than all the other edge of the parallelogam.
	 * Often the term length is associated with one edge being parallel to
	 * an axis of the coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getLength();

	/**
	 * Qualifier how one edge is longer than all the other edge of the parallelogam.
	 * Often the term length is associated with one edge being parallel to
	 * an axis of the coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lengthDataset the lengthDataset
	 */
	public DataNode setLength(IDataset lengthDataset);

	/**
	 * Qualifier how one edge is longer than all the other edge of the parallelogam.
	 * Often the term length is associated with one edge being parallel to
	 * an axis of the coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getLengthScalar();

	/**
	 * Qualifier how one edge is longer than all the other edge of the parallelogam.
	 * Often the term length is associated with one edge being parallel to
	 * an axis of the coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param length the length
	 */
	public DataNode setLengthScalar(Number lengthValue);

	/**
	 * Qualifier often used to describe the length of an edge within
	 * a specific coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWidth();

	/**
	 * Qualifier often used to describe the length of an edge within
	 * a specific coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param widthDataset the widthDataset
	 */
	public DataNode setWidth(IDataset widthDataset);

	/**
	 * Qualifier often used to describe the length of an edge within
	 * a specific coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getWidthScalar();

	/**
	 * Qualifier often used to describe the length of an edge within
	 * a specific coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param width the width
	 */
	public DataNode setWidthScalar(Number widthValue);

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the parallelogram.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCenter();

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the parallelogram.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @param centerDataset the centerDataset
	 */
	public DataNode setCenter(IDataset centerDataset);

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the parallelogram.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCenterScalar();

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the parallelogram.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @param center the center
	 */
	public DataNode setCenterScalar(Number centerValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSurface_area();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param surface_areaDataset the surface_areaDataset
	 */
	public DataNode setSurface_area(IDataset surface_areaDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSurface_areaScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param surface_area the surface_area
	 */
	public DataNode setSurface_areaScalar(Number surface_areaValue);

	/**
	 * Only to be used if is_box is present. In this case, this field describes
	 * whether parallelograms are rectangles/squares whose primary edges
	 * are parallel to the axes of the Cartesian coordinate system.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIs_axis_aligned();

	/**
	 * Only to be used if is_box is present. In this case, this field describes
	 * whether parallelograms are rectangles/squares whose primary edges
	 * are parallel to the axes of the Cartesian coordinate system.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_axis_alignedDataset the is_axis_alignedDataset
	 */
	public DataNode setIs_axis_aligned(IDataset is_axis_alignedDataset);

	/**
	 * Only to be used if is_box is present. In this case, this field describes
	 * whether parallelograms are rectangles/squares whose primary edges
	 * are parallel to the axes of the Cartesian coordinate system.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_axis_alignedScalar();

	/**
	 * Only to be used if is_box is present. In this case, this field describes
	 * whether parallelograms are rectangles/squares whose primary edges
	 * are parallel to the axes of the Cartesian coordinate system.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_axis_aligned the is_axis_aligned
	 */
	public DataNode setIs_axis_alignedScalar(Boolean is_axis_alignedValue);

	/**
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and mesh data are interpretable.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and mesh data are interpretable.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and mesh data are interpretable.</li>
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
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and mesh data are interpretable.</li>
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
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and mesh data are interpretable.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and mesh data are interpretable.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * parallelograms. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIdentifier_offset();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * parallelograms. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offsetDataset the identifier_offsetDataset
	 */
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * parallelograms. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifier_offsetScalar();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * parallelograms. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offset the identifier_offset
	 */
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue);

	/**
	 * Integer used to distinguish parallelograms for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIdentifier();

	/**
	 * Integer used to distinguish parallelograms for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param identifierDataset the identifierDataset
	 */
	public DataNode setIdentifier(IDataset identifierDataset);

	/**
	 * Integer used to distinguish parallelograms for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifierScalar();

	/**
	 * Integer used to distinguish parallelograms for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param identifier the identifier
	 */
	public DataNode setIdentifierScalar(Long identifierValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXorientation_set getOrientation();

	/**
	 *
	 * @param orientationGroup the orientationGroup
	 */
	public void setOrientation(NXorientation_set orientationGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_unit_normal_set getVertex_normal();

	/**
	 *
	 * @param vertex_normalGroup the vertex_normalGroup
	 */
	public void setVertex_normal(NXcg_unit_normal_set vertex_normalGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_unit_normal_set getEdge_normal();

	/**
	 *
	 * @param edge_normalGroup the edge_normalGroup
	 */
	public void setEdge_normal(NXcg_unit_normal_set edge_normalGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_unit_normal_set getFace_normal();

	/**
	 *
	 * @param face_normalGroup the face_normalGroup
	 */
	public void setFace_normal(NXcg_unit_normal_set face_normalGroup);

	/**
	 * A simple approach to describe the entire set of parallelograms when the
	 * main intention is to store the shape of the parallelograms for visualization.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getParallelograms();

	/**
	 * A simple approach to describe the entire set of parallelograms when the
	 * main intention is to store the shape of the parallelograms for visualization.
	 *
	 * @param parallelogramsGroup the parallelogramsGroup
	 */
	public void setParallelograms(NXcg_face_list_data_structure parallelogramsGroup);

	/**
	 * Disentangled representations of the mesh of specific parallelograms.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getParallelogram();

	/**
	 * Disentangled representations of the mesh of specific parallelograms.
	 *
	 * @param parallelogramGroup the parallelogramGroup
	 */
	public void setParallelogram(NXcg_face_list_data_structure parallelogramGroup);

}
