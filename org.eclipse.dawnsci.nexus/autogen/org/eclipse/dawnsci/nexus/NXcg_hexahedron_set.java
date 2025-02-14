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
 * Computational geometry description of a set of hexahedra in Euclidean space.
 * The hexahedra do not have to be connected, can have different size,
 * can intersect, and be rotated.
 * This class can also be used to describe cuboids or cubes, axis-aligned or not.
 * The class represents different access and description levels to offer both
 * applied scientists and computational geometry experts to use the same
 * base class but rather their specific view on the data:
 * * Most simple many experimentalists wish to communicate dimensions/the size
 * of specimens. In this case the alignment with axes is not relevant as
 * eventually the only relevant information to convey is the volume of the
 * specimen.
 * * In many cases, take for instance an experiment where a specimen was taken
 * from a specifically deformed piece of material, e.g. cold-rolled,
 * channel-die deformed, the orientation of the specimen edges with the
 * experiment coordinate system can be of very high relevance.
 * Examples include to know which specimen edge is parallel to the rolling,
 * the transverse, or the normal direction.
 * * Sufficient to pinpoint the sample and laboratory/experiment coordinate
 * system, the above-mentioned descriptions are not detailed enough though
 * to create a CAD model of the specimen.
 * * Therefore, groups and fields for an additional, computational-geometry-
 * based view of the hexahedra is offered which serve different computational
 * tasks: storage-oriented simple views or detailed topological/graph-based
 * descriptions.
 * Hexahedra are important geometrical primitives, which are among the most
 * frequently used elements in finite element meshing/modeling.
 * Hexahedra have to be non-degenerated, closed, and built of polygons
 * which are not self-intersecting.
 * The term hexahedra will be used throughout this base class but includes
 * the especially in engineering and more commonly used special cases,
 * cuboid, cube, box, axis-aligned bounding box (AABB), optimal bounding
 * box (OBB).
 * An axis-aligned bounding box is a common data object in
 * computational science and codes to represent a cuboid whose edges
 * are aligned with a coordinate system. As a part of binary trees these
 * are important data objects for time as well as space efficient queries
 * of geometric primitives in techniques like kd-trees.
 * An optimal bounding box is a common data object which provides the best
 * tight fitting box about an arbitrary object. In general such boxes are
 * rotated. Exact and substantially faster in practice approximate algorithms
 * exist for computing optimal or near optimal bounding boxes for point sets.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of hexahedra.</li></ul></p>
 *
 */
public interface NXcg_hexahedron_set extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_SHAPE = "shape";
	public static final String NX_LENGTH = "length";
	public static final String NX_WIDTH = "width";
	public static final String NX_HEIGHT = "height";
	public static final String NX_CENTER = "center";
	public static final String NX_VOLUME = "volume";
	public static final String NX_SURFACE_AREA = "surface_area";
	public static final String NX_FACE_AREA = "face_area";
	public static final String NX_IS_BOX = "is_box";
	public static final String NX_IS_AXIS_ALIGNED = "is_axis_aligned";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDimensionality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>3</b> </li></ul></p>
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
	 * <li><b>3</b> </li></ul></p>
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
	 * <li><b>3</b> </li></ul></p>
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
	public Dataset getCardinality();

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
	 * A qualitative description of each hexahedron/cuboid/cube/box.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getShape();

	/**
	 * A qualitative description of each hexahedron/cuboid/cube/box.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param shapeDataset the shapeDataset
	 */
	public DataNode setShape(IDataset shapeDataset);

	/**
	 * A qualitative description of each hexahedron/cuboid/cube/box.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getShapeScalar();

	/**
	 * A qualitative description of each hexahedron/cuboid/cube/box.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param shape the shape
	 */
	public DataNode setShapeScalar(Number shapeValue);

	/**
	 * Qualifier how one edge is longer than all other edges of the hexahedra.
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
	public Dataset getLength();

	/**
	 * Qualifier how one edge is longer than all other edges of the hexahedra.
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
	 * Qualifier how one edge is longer than all other edges of the hexahedra.
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
	 * Qualifier how one edge is longer than all other edges of the hexahedra.
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
	public Dataset getWidth();

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
	public Dataset getHeight();

	/**
	 * Qualifier often used to describe the length of an edge within
	 * a specific coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param heightDataset the heightDataset
	 */
	public DataNode setHeight(IDataset heightDataset);

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
	public Number getHeightScalar();

	/**
	 * Qualifier often used to describe the length of an edge within
	 * a specific coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param height the height
	 */
	public DataNode setHeightScalar(Number heightValue);

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the hexahedrally-shaped sample/sample part.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCenter();

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the hexahedrally-shaped sample/sample part.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param centerDataset the centerDataset
	 */
	public DataNode setCenter(IDataset centerDataset);

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the hexahedrally-shaped sample/sample part.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCenterScalar();

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the hexahedrally-shaped sample/sample part.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param center the center
	 */
	public DataNode setCenterScalar(Number centerValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVolume();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param volumeDataset the volumeDataset
	 */
	public DataNode setVolume(IDataset volumeDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getVolumeScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param volume the volume
	 */
	public DataNode setVolumeScalar(Number volumeValue);

	/**
	 * Total area (of all six faces) of each hexahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSurface_area();

	/**
	 * Total area (of all six faces) of each hexahedron.
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
	 * Total area (of all six faces) of each hexahedron.
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
	 * Total area (of all six faces) of each hexahedron.
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
	 * Area of each of the six faces of each hexahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 6;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFace_area();

	/**
	 * Area of each of the six faces of each hexahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 6;
	 * </p>
	 *
	 * @param face_areaDataset the face_areaDataset
	 */
	public DataNode setFace_area(IDataset face_areaDataset);

	/**
	 * Area of each of the six faces of each hexahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 6;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFace_areaScalar();

	/**
	 * Area of each of the six faces of each hexahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 6;
	 * </p>
	 *
	 * @param face_area the face_area
	 */
	public DataNode setFace_areaScalar(Number face_areaValue);

	/**
	 * Specifies if the hexahedra represent cuboids or cubes eventually rotated
	 * ones but at least not too exotic six-faced polyhedra.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_box();

	/**
	 * Specifies if the hexahedra represent cuboids or cubes eventually rotated
	 * ones but at least not too exotic six-faced polyhedra.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_boxDataset the is_boxDataset
	 */
	public DataNode setIs_box(IDataset is_boxDataset);

	/**
	 * Specifies if the hexahedra represent cuboids or cubes eventually rotated
	 * ones but at least not too exotic six-faced polyhedra.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_boxScalar();

	/**
	 * Specifies if the hexahedra represent cuboids or cubes eventually rotated
	 * ones but at least not too exotic six-faced polyhedra.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_box the is_box
	 */
	public DataNode setIs_boxScalar(Boolean is_boxValue);

	/**
	 * Only to be used if is_box is present. In this case, this field describes
	 * whether hexahedra are boxes whose primary edges are parallel to the
	 * axes of the Cartesian coordinate system.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_axis_aligned();

	/**
	 * Only to be used if is_box is present. In this case, this field describes
	 * whether hexahedra are boxes whose primary edges are parallel to the
	 * axes of the Cartesian coordinate system.
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
	 * whether hexahedra are boxes whose primary edges are parallel to the
	 * axes of the Cartesian coordinate system.
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
	 * whether hexahedra are boxes whose primary edges are parallel to the
	 * axes of the Cartesian coordinate system.
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
	 * hexahedra. Identifiers are defined either implicitly
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
	public Dataset getIdentifier_offset();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * hexahedra. Identifiers are defined either implicitly
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
	 * hexahedra. Identifiers are defined either implicitly
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
	 * hexahedra. Identifiers are defined either implicitly
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
	 * Integer used to distinguish hexahedra for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier();

	/**
	 * Integer used to distinguish hexahedra for explicit indexing.
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
	 * Integer used to distinguish hexahedra for explicit indexing.
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
	 * Integer used to distinguish hexahedra for explicit indexing.
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
	 * A simple approach to describe the entire set of hexahedra when the
	 * main intention is to store the shape of the hexahedra for visualization.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getHexahedra();

	/**
	 * A simple approach to describe the entire set of hexahedra when the
	 * main intention is to store the shape of the hexahedra for visualization.
	 *
	 * @param hexahedraGroup the hexahedraGroup
	 */
	public void setHexahedra(NXcg_face_list_data_structure hexahedraGroup);

	/**
	 * Disentangled representations of the mesh of specific hexahedra.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getHexahedron();

	/**
	 * Disentangled representations of the mesh of specific hexahedra.
	 *
	 * @param hexahedronGroup the hexahedronGroup
	 */
	public void setHexahedron(NXcg_face_list_data_structure hexahedronGroup);

	/**
	 * Disentangled representation of the planar graph that each hexahedron
	 * represents. Such a description simplifies topological processing
	 * or analyses of mesh primitive operations and neighborhood queries.
	 *
	 * @return  the value.
	 */
	public NXcg_half_edge_data_structure getHexahedron_half_edge();

	/**
	 * Disentangled representation of the planar graph that each hexahedron
	 * represents. Such a description simplifies topological processing
	 * or analyses of mesh primitive operations and neighborhood queries.
	 *
	 * @param hexahedron_half_edgeGroup the hexahedron_half_edgeGroup
	 */
	public void setHexahedron_half_edge(NXcg_half_edge_data_structure hexahedron_half_edgeGroup);

}
