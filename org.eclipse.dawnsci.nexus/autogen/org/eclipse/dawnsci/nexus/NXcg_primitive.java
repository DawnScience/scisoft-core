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
 * Computational geometry description of a set of primitives in Euclidean space.
 * Primitives must neither be degenerated nor self-intersect.
 * Individual primitives can differ in their properties (e.g. size, shape, rotation).
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality of the embedding space.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of members.</li></ul></p>
 *
 */
public interface NXcg_primitive extends NXobject {

	public static final String NX_DEPENDS_ON = "depends_on";
	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_INDEX_OFFSET = "index_offset";
	public static final String NX_INDICES = "indices";
	public static final String NX_CENTER = "center";
	public static final String NX_IS_CENTER_OF_MASS = "is_center_of_mass";
	public static final String NX_SHAPE = "shape";
	public static final String NX_LENGTH = "length";
	public static final String NX_WIDTH = "width";
	public static final String NX_HEIGHT = "height";
	public static final String NX_IS_CLOSED = "is_closed";
	public static final String NX_VOLUME = "volume";
	public static final String NX_AREA = "area";
	public static final String NX_ORIENTATION = "orientation";
	public static final String NX_IS_MESH = "is_mesh";
	public static final String NX_IS_TRIANGLE_MESH = "is_triangle_mesh";
	public static final String NX_IS_SURFACE_MESH = "is_surface_mesh";
	public static final String NX_IS_GEODESIC_MESH = "is_geodesic_mesh";
	public static final String NX_DESCRIPTION = "description";
	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` in which these primitives
	 * are defined.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` in which these primitives
	 * are defined.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` in which these primitives
	 * are defined.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` in which these primitives
	 * are defined.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * The dimensionality of the primitive set with value up to d.
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
	 * The dimensionality of the primitive set with value up to d.
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
	 * The dimensionality of the primitive set with value up to d.
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
	 * The dimensionality of the primitive set with value up to d.
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
	 * The cardinality of the primitive set. Value should be equal to c.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCardinality();

	/**
	 * The cardinality of the primitive set. Value should be equal to c.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinalityDataset the cardinalityDataset
	 */
	public DataNode setCardinality(IDataset cardinalityDataset);

	/**
	 * The cardinality of the primitive set. Value should be equal to c.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCardinalityScalar();

	/**
	 * The cardinality of the primitive set. Value should be equal to c.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinality the cardinality
	 */
	public DataNode setCardinalityScalar(Long cardinalityValue);

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the set differs from zero.
	 * Indices can be used as identifiers and thus names of instances.
	 * Identifiers can be defined either implicitly or explicitly.
	 * For implicit indexing identifiers are defined on the interval
	 * :math:`[index\_offset, index\_offset + c - 1]`.
	 * Therefore, implicit identifier are completely defined by the value of
	 * index_offset and cardinality. For example if identifier run from
	 * -2 to 3 the value for index_offset is -2.
	 * For explicit indexing the field identifier has to be used.
	 * Fortran-/Matlab- and C-/Python-style indexing have specific implicit
	 * identifier conventions where index_offset is 1 and 0 respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndex_offset();

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the set differs from zero.
	 * Indices can be used as identifiers and thus names of instances.
	 * Identifiers can be defined either implicitly or explicitly.
	 * For implicit indexing identifiers are defined on the interval
	 * :math:`[index\_offset, index\_offset + c - 1]`.
	 * Therefore, implicit identifier are completely defined by the value of
	 * index_offset and cardinality. For example if identifier run from
	 * -2 to 3 the value for index_offset is -2.
	 * For explicit indexing the field identifier has to be used.
	 * Fortran-/Matlab- and C-/Python-style indexing have specific implicit
	 * identifier conventions where index_offset is 1 and 0 respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param index_offsetDataset the index_offsetDataset
	 */
	public DataNode setIndex_offset(IDataset index_offsetDataset);

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the set differs from zero.
	 * Indices can be used as identifiers and thus names of instances.
	 * Identifiers can be defined either implicitly or explicitly.
	 * For implicit indexing identifiers are defined on the interval
	 * :math:`[index\_offset, index\_offset + c - 1]`.
	 * Therefore, implicit identifier are completely defined by the value of
	 * index_offset and cardinality. For example if identifier run from
	 * -2 to 3 the value for index_offset is -2.
	 * For explicit indexing the field identifier has to be used.
	 * Fortran-/Matlab- and C-/Python-style indexing have specific implicit
	 * identifier conventions where index_offset is 1 and 0 respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIndex_offsetScalar();

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the set differs from zero.
	 * Indices can be used as identifiers and thus names of instances.
	 * Identifiers can be defined either implicitly or explicitly.
	 * For implicit indexing identifiers are defined on the interval
	 * :math:`[index\_offset, index\_offset + c - 1]`.
	 * Therefore, implicit identifier are completely defined by the value of
	 * index_offset and cardinality. For example if identifier run from
	 * -2 to 3 the value for index_offset is -2.
	 * For explicit indexing the field identifier has to be used.
	 * Fortran-/Matlab- and C-/Python-style indexing have specific implicit
	 * identifier conventions where index_offset is 1 and 0 respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param index_offset the index_offset
	 */
	public DataNode setIndex_offsetScalar(Long index_offsetValue);

	/**
	 * Identifier of each member for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndices();

	/**
	 * Identifier of each member for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param indicesDataset the indicesDataset
	 */
	public DataNode setIndices(IDataset indicesDataset);

	/**
	 * Identifier of each member for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIndicesScalar();

	/**
	 * Identifier of each member for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param indices the indices
	 */
	public DataNode setIndicesScalar(Long indicesValue);

	/**
	 * The center of each primitive
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCenter();

	/**
	 * The center of each primitive
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param centerDataset the centerDataset
	 */
	public DataNode setCenter(IDataset centerDataset);

	/**
	 * The center of each primitive
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCenterScalar();

	/**
	 * The center of each primitive
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param center the center
	 */
	public DataNode setCenterScalar(Number centerValue);

	/**
	 * True if the center is a center of mass.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_center_of_mass();

	/**
	 * True if the center is a center of mass.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_center_of_massDataset the is_center_of_massDataset
	 */
	public DataNode setIs_center_of_mass(IDataset is_center_of_massDataset);

	/**
	 * True if the center is a center of mass.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_center_of_massScalar();

	/**
	 * True if the center is a center of mass.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_center_of_mass the is_center_of_mass
	 */
	public DataNode setIs_center_of_massScalar(Boolean is_center_of_massValue);

	/**
	 * Shape of each primitive
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getShape();

	/**
	 * Shape of each primitive
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param shapeDataset the shapeDataset
	 */
	public DataNode setShape(IDataset shapeDataset);

	/**
	 * Shape of each primitive
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getShapeScalar();

	/**
	 * Shape of each primitive
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param shape the shape
	 */
	public DataNode setShapeScalar(Number shapeValue);

	/**
	 * Length of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Length of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Length of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Length of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Width of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Width of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Width of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Width of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Height of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Height of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Height of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * Height of each primitive
	 * Often the term is associated with the assumption that one
	 * edge is parallel to an axis of the coordinate system.
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
	 * True if primitive is closed such that it has properties like area or volume.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_closed();

	/**
	 * True if primitive is closed such that it has properties like area or volume.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_closedDataset the is_closedDataset
	 */
	public DataNode setIs_closed(IDataset is_closedDataset);

	/**
	 * True if primitive is closed such that it has properties like area or volume.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_closedScalar();

	/**
	 * True if primitive is closed such that it has properties like area or volume.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_closed the is_closed
	 */
	public DataNode setIs_closedScalar(Boolean is_closedValue);

	/**
	 * Volume of each primitive.
	 * Set to NaN if does not apply for primitives for which is_closed is False.
	 * Volume is an N-D concept for values of dimensionality larger than 1,
	 * Area is an alias for the two-dimensional case.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVolume();

	/**
	 * Volume of each primitive.
	 * Set to NaN if does not apply for primitives for which is_closed is False.
	 * Volume is an N-D concept for values of dimensionality larger than 1,
	 * Area is an alias for the two-dimensional case.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param volumeDataset the volumeDataset
	 */
	public DataNode setVolume(IDataset volumeDataset);

	/**
	 * Volume of each primitive.
	 * Set to NaN if does not apply for primitives for which is_closed is False.
	 * Volume is an N-D concept for values of dimensionality larger than 1,
	 * Area is an alias for the two-dimensional case.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getVolumeScalar();

	/**
	 * Volume of each primitive.
	 * Set to NaN if does not apply for primitives for which is_closed is False.
	 * Volume is an N-D concept for values of dimensionality larger than 1,
	 * Area is an alias for the two-dimensional case.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param volume the volume
	 */
	public DataNode setVolumeScalar(Number volumeValue);

	/**
	 * Alias for surface_area of each primitive.
	 * Set to NaN if does not apply for primitives for which is_closed is False.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getArea();

	/**
	 * Alias for surface_area of each primitive.
	 * Set to NaN if does not apply for primitives for which is_closed is False.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param areaDataset the areaDataset
	 */
	public DataNode setArea(IDataset areaDataset);

	/**
	 * Alias for surface_area of each primitive.
	 * Set to NaN if does not apply for primitives for which is_closed is False.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAreaScalar();

	/**
	 * Alias for surface_area of each primitive.
	 * Set to NaN if does not apply for primitives for which is_closed is False.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param area the area
	 */
	public DataNode setAreaScalar(Number areaValue);

	/**
	 * Direction unit vector which points along the
	 * longest principal axis of each primitive.
	 * Use the depends_on attribute to specify in which coordinate system
	 * these direction unit vectors are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrientation();

	/**
	 * Direction unit vector which points along the
	 * longest principal axis of each primitive.
	 * Use the depends_on attribute to specify in which coordinate system
	 * these direction unit vectors are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param orientationDataset the orientationDataset
	 */
	public DataNode setOrientation(IDataset orientationDataset);

	/**
	 * Direction unit vector which points along the
	 * longest principal axis of each primitive.
	 * Use the depends_on attribute to specify in which coordinate system
	 * these direction unit vectors are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOrientationScalar();

	/**
	 * Direction unit vector which points along the
	 * longest principal axis of each primitive.
	 * Use the depends_on attribute to specify in which coordinate system
	 * these direction unit vectors are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param orientation the orientation
	 */
	public DataNode setOrientationScalar(Number orientationValue);

	/**
	 * Do the primitives define a mesh.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_mesh();

	/**
	 * Do the primitives define a mesh.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_meshDataset the is_meshDataset
	 */
	public DataNode setIs_mesh(IDataset is_meshDataset);

	/**
	 * Do the primitives define a mesh.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_meshScalar();

	/**
	 * Do the primitives define a mesh.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_mesh the is_mesh
	 */
	public DataNode setIs_meshScalar(Boolean is_meshValue);

	/**
	 * Do the primitives define a triangle mesh or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_triangle_mesh();

	/**
	 * Do the primitives define a triangle mesh or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_triangle_meshDataset the is_triangle_meshDataset
	 */
	public DataNode setIs_triangle_mesh(IDataset is_triangle_meshDataset);

	/**
	 * Do the primitives define a triangle mesh or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_triangle_meshScalar();

	/**
	 * Do the primitives define a triangle mesh or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_triangle_mesh the is_triangle_mesh
	 */
	public DataNode setIs_triangle_meshScalar(Boolean is_triangle_meshValue);

	/**
	 * Do the primitives discretize the surface of an object or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_surface_mesh();

	/**
	 * Do the primitives discretize the surface of an object or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_surface_meshDataset the is_surface_meshDataset
	 */
	public DataNode setIs_surface_mesh(IDataset is_surface_meshDataset);

	/**
	 * Do the primitives discretize the surface of an object or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_surface_meshScalar();

	/**
	 * Do the primitives discretize the surface of an object or not.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_surface_mesh the is_surface_mesh
	 */
	public DataNode setIs_surface_meshScalar(Boolean is_surface_meshValue);

	/**
	 * Do the primitives define a geodesic mesh or not.
	 * A geodesic surface mesh is a triangulated surface mesh with metadata which
	 * can be used as an approximation to describe the surface of a sphere.
	 * Triangulation of spheres are commonly used in Materials Science
	 * for quantifying texture of materials, i.e. the relative rotation of
	 * crystals to sample directions.
	 * For additional details or an introduction into the topic of geodesic meshes
	 * see (from which specifically the section on subdivision schemes is relevant).
	 * * `E. S. Popko and C. J. Kitrick <https://doi.org/10.1201/9781003134114>`_
	 * Earth scientists have specific demands and different views about what should
	 * be included in such a base class, given that nested geodesic meshes are a key
	 * component of climate modelling software. For now we propose to use this
	 * base class as a container for organizing data related to geodesic meshes.
	 * Specifically an instance of this base class should detail the rule set how
	 * e.g. a geodesic (surface) mesh was instantiated as there are many
	 * possibilities to do so.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_geodesic_mesh();

	/**
	 * Do the primitives define a geodesic mesh or not.
	 * A geodesic surface mesh is a triangulated surface mesh with metadata which
	 * can be used as an approximation to describe the surface of a sphere.
	 * Triangulation of spheres are commonly used in Materials Science
	 * for quantifying texture of materials, i.e. the relative rotation of
	 * crystals to sample directions.
	 * For additional details or an introduction into the topic of geodesic meshes
	 * see (from which specifically the section on subdivision schemes is relevant).
	 * * `E. S. Popko and C. J. Kitrick <https://doi.org/10.1201/9781003134114>`_
	 * Earth scientists have specific demands and different views about what should
	 * be included in such a base class, given that nested geodesic meshes are a key
	 * component of climate modelling software. For now we propose to use this
	 * base class as a container for organizing data related to geodesic meshes.
	 * Specifically an instance of this base class should detail the rule set how
	 * e.g. a geodesic (surface) mesh was instantiated as there are many
	 * possibilities to do so.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_geodesic_meshDataset the is_geodesic_meshDataset
	 */
	public DataNode setIs_geodesic_mesh(IDataset is_geodesic_meshDataset);

	/**
	 * Do the primitives define a geodesic mesh or not.
	 * A geodesic surface mesh is a triangulated surface mesh with metadata which
	 * can be used as an approximation to describe the surface of a sphere.
	 * Triangulation of spheres are commonly used in Materials Science
	 * for quantifying texture of materials, i.e. the relative rotation of
	 * crystals to sample directions.
	 * For additional details or an introduction into the topic of geodesic meshes
	 * see (from which specifically the section on subdivision schemes is relevant).
	 * * `E. S. Popko and C. J. Kitrick <https://doi.org/10.1201/9781003134114>`_
	 * Earth scientists have specific demands and different views about what should
	 * be included in such a base class, given that nested geodesic meshes are a key
	 * component of climate modelling software. For now we propose to use this
	 * base class as a container for organizing data related to geodesic meshes.
	 * Specifically an instance of this base class should detail the rule set how
	 * e.g. a geodesic (surface) mesh was instantiated as there are many
	 * possibilities to do so.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_geodesic_meshScalar();

	/**
	 * Do the primitives define a geodesic mesh or not.
	 * A geodesic surface mesh is a triangulated surface mesh with metadata which
	 * can be used as an approximation to describe the surface of a sphere.
	 * Triangulation of spheres are commonly used in Materials Science
	 * for quantifying texture of materials, i.e. the relative rotation of
	 * crystals to sample directions.
	 * For additional details or an introduction into the topic of geodesic meshes
	 * see (from which specifically the section on subdivision schemes is relevant).
	 * * `E. S. Popko and C. J. Kitrick <https://doi.org/10.1201/9781003134114>`_
	 * Earth scientists have specific demands and different views about what should
	 * be included in such a base class, given that nested geodesic meshes are a key
	 * component of climate modelling software. For now we propose to use this
	 * base class as a container for organizing data related to geodesic meshes.
	 * Specifically an instance of this base class should detail the rule set how
	 * e.g. a geodesic (surface) mesh was instantiated as there are many
	 * possibilities to do so.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_geodesic_mesh the is_geodesic_mesh
	 */
	public DataNode setIs_geodesic_meshScalar(Boolean is_geodesic_meshValue);

	/**
	 * Possibility to store details such as when primitives form a (specific) type
	 * of mesh such as geodesic meshes.
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Possibility to store details such as when primitives form a (specific) type
	 * of mesh such as geodesic meshes.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Possibility to store details such as when primitives form a (specific) type
	 * of mesh such as geodesic meshes.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Possibility to store details such as when primitives form a (specific) type
	 * of mesh such as geodesic meshes.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_unit_normal getVertex_normal();

	/**
	 *
	 * @param vertex_normalGroup the vertex_normalGroup
	 */
	public void setVertex_normal(NXcg_unit_normal vertex_normalGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_unit_normal getEdge_normal();

	/**
	 *
	 * @param edge_normalGroup the edge_normalGroup
	 */
	public void setEdge_normal(NXcg_unit_normal edge_normalGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_unit_normal getFace_normal();

	/**
	 *
	 * @param face_normalGroup the face_normalGroup
	 */
	public void setFace_normal(NXcg_unit_normal face_normalGroup);

}
