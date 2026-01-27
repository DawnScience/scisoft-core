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
 * Base class to detail a coordinate system (CS).
 * Instances of ``NXcoordinate_system`` can be used to describe coordinate systems
 * other than the default `NeXus coordinate system <https://manual.nexusformat.org/design.html#the-nexus-coordinate-system>`_.
 * Whenever possible, all instances of :ref:`NXcoordinate_system`
 * should be used at the top-level (i.e, directly below ``NXentry``)
 * within an application definition or within a NeXus file.
 * ``NXcoordinate_system`` can be part of the transformations chain
 * using :ref:`NXtransformations`, where it acts as a linear
 * change-of-basis transformation (using a 3x3 matrix with the basis
 * vectors ``x``, `y``, and ``z`` as columns).
 * Any group that has an optional ``depends_on`` field or any field
 * that has an optional ``depends_on`` attribute has a fallback when
 * ``depends_on`` is not provided. The fallback behavior involves
 * traversing up the hierarchy until the first ancestor that contains one
 * and only one ``NXcoordinate_system`` group is found. If such an ancestor
 * exists, its coordinate system applies. If none is found or more than
 * one instance of ``NXcoordinate_system`` is found at the same level,
 * then the current coordinate system is not defined with respect to
 * anything else. As an example, if there is only one ``NXcoordinate_system``
 * called "my_coordinate_system" defined directly under ``NXentry``, each
 * optional ``depends_on`` field/attribute that is not defined
 * automatically defaults to ``depends_on=my_coordinate_system``.
 * How many groups of type ``NXcoordinate_system`` should be used in
 * an application definition?
 * * 0; if there is no instance of ``NXcoordinate_system`` across the
 * entire tree, you can use ``depends_on="."`` to state that this
 * transformation depends on the default
 * `NeXus coordinate system <https://manual.nexusformat.org/design.html#the-nexus-coordinate-system>`_
 * (which is the same as the one used by `McStas <https://mcstas.org/>`_).
 * For the sake of clarity, even in this case it is better
 * to be explicit and consistent for every other coordinate system definition
 * to support users with interpreting the content and logic behind
 * every instance of the tree.
 * The default NeXus coordinate system (i.e., the McStas coordinate
 * system) can be expressed as follows:
 * .. code-block::
 * mcstas@NXcoordinate_system
 * x = [1, 0, 0]
 * y = [0, 1, 0]
 * z = [0, 0, 1]
 * @y_direction = "opposite to gravity"
 * @z_direction = "direction of the primary beam"
 * Note that this assumes that the direction of the beam is not defined in the ``NXbeam`` instance.
 * * 1; if only one :ref:`NXcoordinate_system` is defined, it should be
 * placed as high up in the tree hierarchy (ideally right below an
 * instance of NXentry) of the application definition tree as possible.
 * This coordinate system shall be named such that it is clear how this
 * coordinate system is typically referred to in a community. For the
 * NeXus McStas coordinate system, it is advised to call it ``mcstas``
 * for the sake of improved clarity.
 * If this is the case, it is assumed that this ``NXcoordinate_system``
 * overwrites the NeXus default McStas coordinate system, i.e. users
 * can thereby conveniently and explicitly specify the
 * coordinate system that they wish to use.
 * This case has the advantage that it is explicit and offers no
 * ambiguities. However, in reality typically multiple coordinate
 * systems have to be mastered especially for complex multi-signal
 * modality experiments.
 * If this case is realized, the best practice is that in every
 * case where this coordinate system should be referred to the respective
 * group has a ``depends_on`` field, to clearly indicate which
 * specific coordinate systems is referred to.
 * * 2 and more; as soon as more than one :ref:`NXcoordinate_system`
 * is specified somewhere in the tree, different interpretations are
 * possible as to which of these coordinate systems
 * apply or take preference.
 * While these ambiguities should be avoided if possible, the
 * opportunity for multiples instances enables to have coordinate
 * system conventions that are specific for some part of the NeXus
 * tree. This is especially useful for deep groups where
 * multiple scientific methods are combined or cases where having a
 * definition of global conversion tables how to convert between
 * representations in different coordinate systems is not desired
 * or available for now.
 * To resolve the possible ambiguities which specific coordinate systems
 * in an :ref:`NXtransformations` train is referred to, it is even more
 * important to use the ``depends_on`` field in groups and the ``depends_on``
 * attribute in NXtransformations to refer to one of the ``NXcoordinate_system``
 * instances.
 * In the case of two or more instances of ``NXcoordinate_system`` it
 * is advised to specify the relationship between the two coordinate
 * systems by using the :ref:`NXtransformations` group within
 * ``NXcoordinate_system``.
 * In any case, users are encouraged to write explicit and clean
 * ``depends_on`` fields in all groups that encode information for which
 * the interpretation of coordinate systems and transformations is relevant.
 * If these ``depends_on`` instances are not provided or no instance of
 * ``NX_coordinate_system`` exists in the upper part of the hierarchy,
 * the application definition is considered underconstrained. Note that this
 * is the case for all files that were created before ``NXcoordinate_system``
 * was added.
 *
 */
public interface NXcoordinate_system extends NXobject {

	public static final String NX_ORIGIN = "origin";
	public static final String NX_TYPE = "type";
	public static final String NX_X_ALIAS = "x_alias";
	public static final String NX_X_DIRECTION = "x_direction";
	public static final String NX_X = "x";
	public static final String NX_Y_ALIAS = "y_alias";
	public static final String NX_Y_DIRECTION = "y_direction";
	public static final String NX_Y = "y";
	public static final String NX_Z_ALIAS = "z_alias";
	public static final String NX_Z_DIRECTION = "z_direction";
	public static final String NX_Z = "z";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Human-readable field describing where the origin of this CS is.
	 * Exemplar values could be *left corner of the lab bench*, *door handle*
	 * *pinhole through which the electron beam exits the pole piece*,
	 * *barycenter of the triangle*, *center of mass of the stone*.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrigin();

	/**
	 * Human-readable field describing where the origin of this CS is.
	 * Exemplar values could be *left corner of the lab bench*, *door handle*
	 * *pinhole through which the electron beam exits the pole piece*,
	 * *barycenter of the triangle*, *center of mass of the stone*.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param originDataset the originDataset
	 */
	public DataNode setOrigin(IDataset originDataset);

	/**
	 * Human-readable field describing where the origin of this CS is.
	 * Exemplar values could be *left corner of the lab bench*, *door handle*
	 * *pinhole through which the electron beam exits the pole piece*,
	 * *barycenter of the triangle*, *center of mass of the stone*.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getOriginScalar();

	/**
	 * Human-readable field describing where the origin of this CS is.
	 * Exemplar values could be *left corner of the lab bench*, *door handle*
	 * *pinhole through which the electron beam exits the pole piece*,
	 * *barycenter of the triangle*, *center of mass of the stone*.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param origin the origin
	 */
	public DataNode setOriginScalar(String originValue);

	/**
	 * Coordinate system type.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>undefined</b> </li>
	 * <li><b>cartesian</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Coordinate system type.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>undefined</b> </li>
	 * <li><b>cartesian</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Coordinate system type.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>undefined</b> </li>
	 * <li><b>cartesian</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Coordinate system type.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>undefined</b> </li>
	 * <li><b>cartesian</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Opportunity to define an alias for the name of the x-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getX_alias();

	/**
	 * Opportunity to define an alias for the name of the x-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param x_aliasDataset the x_aliasDataset
	 */
	public DataNode setX_alias(IDataset x_aliasDataset);

	/**
	 * Opportunity to define an alias for the name of the x-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getX_aliasScalar();

	/**
	 * Opportunity to define an alias for the name of the x-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param x_alias the x_alias
	 */
	public DataNode setX_aliasScalar(String x_aliasValue);

	/**
	 * Human-readable field telling in which direction the x-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the world reference frame.
	 * Exemplar values could be direction of gravity.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getX_direction();

	/**
	 * Human-readable field telling in which direction the x-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the world reference frame.
	 * Exemplar values could be direction of gravity.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param x_directionDataset the x_directionDataset
	 */
	public DataNode setX_direction(IDataset x_directionDataset);

	/**
	 * Human-readable field telling in which direction the x-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the world reference frame.
	 * Exemplar values could be direction of gravity.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getX_directionScalar();

	/**
	 * Human-readable field telling in which direction the x-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the world reference frame.
	 * Exemplar values could be direction of gravity.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param x_direction the x_direction
	 */
	public DataNode setX_directionScalar(String x_directionValue);

	/**
	 * Basis unit vector along the first axis which spans the coordinate system.
	 * This axis is frequently referred to as the x-axis in Euclidean space and
	 * the i-axis in reciprocal space.
	 * Note that `x``, ``y``, and ``z`` must constitute a basis, i.e., a set of linearly
	 * independent vectors that span the vector space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getX();

	/**
	 * Basis unit vector along the first axis which spans the coordinate system.
	 * This axis is frequently referred to as the x-axis in Euclidean space and
	 * the i-axis in reciprocal space.
	 * Note that `x``, ``y``, and ``z`` must constitute a basis, i.e., a set of linearly
	 * independent vectors that span the vector space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param xDataset the xDataset
	 */
	public DataNode setX(IDataset xDataset);

	/**
	 * Basis unit vector along the first axis which spans the coordinate system.
	 * This axis is frequently referred to as the x-axis in Euclidean space and
	 * the i-axis in reciprocal space.
	 * Note that `x``, ``y``, and ``z`` must constitute a basis, i.e., a set of linearly
	 * independent vectors that span the vector space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getXScalar();

	/**
	 * Basis unit vector along the first axis which spans the coordinate system.
	 * This axis is frequently referred to as the x-axis in Euclidean space and
	 * the i-axis in reciprocal space.
	 * Note that `x``, ``y``, and ``z`` must constitute a basis, i.e., a set of linearly
	 * independent vectors that span the vector space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param x the x
	 */
	public DataNode setXScalar(Number xValue);

	/**
	 * Opportunity to define an alias for the name of the y-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getY_alias();

	/**
	 * Opportunity to define an alias for the name of the y-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param y_aliasDataset the y_aliasDataset
	 */
	public DataNode setY_alias(IDataset y_aliasDataset);

	/**
	 * Opportunity to define an alias for the name of the y-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getY_aliasScalar();

	/**
	 * Opportunity to define an alias for the name of the y-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param y_alias the y_alias
	 */
	public DataNode setY_aliasScalar(String y_aliasValue);

	/**
	 * Human-readable field telling in which direction the y-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the mighty world reference frame.
	 * See docstring of ``x_direction`` for further details.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getY_direction();

	/**
	 * Human-readable field telling in which direction the y-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the mighty world reference frame.
	 * See docstring of ``x_direction`` for further details.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param y_directionDataset the y_directionDataset
	 */
	public DataNode setY_direction(IDataset y_directionDataset);

	/**
	 * Human-readable field telling in which direction the y-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the mighty world reference frame.
	 * See docstring of ``x_direction`` for further details.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getY_directionScalar();

	/**
	 * Human-readable field telling in which direction the y-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the mighty world reference frame.
	 * See docstring of ``x_direction`` for further details.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param y_direction the y_direction
	 */
	public DataNode setY_directionScalar(String y_directionValue);

	/**
	 * Basis unit vector along the second axis which spans the coordinate system.
	 * This axis is frequently referred to as the y-axis in Euclidean space and
	 * the j-axis in reciprocal space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getY();

	/**
	 * Basis unit vector along the second axis which spans the coordinate system.
	 * This axis is frequently referred to as the y-axis in Euclidean space and
	 * the j-axis in reciprocal space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param yDataset the yDataset
	 */
	public DataNode setY(IDataset yDataset);

	/**
	 * Basis unit vector along the second axis which spans the coordinate system.
	 * This axis is frequently referred to as the y-axis in Euclidean space and
	 * the j-axis in reciprocal space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getYScalar();

	/**
	 * Basis unit vector along the second axis which spans the coordinate system.
	 * This axis is frequently referred to as the y-axis in Euclidean space and
	 * the j-axis in reciprocal space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param y the y
	 */
	public DataNode setYScalar(Number yValue);

	/**
	 * Opportunity to define an alias for the name of the z-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getZ_alias();

	/**
	 * Opportunity to define an alias for the name of the z-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param z_aliasDataset the z_aliasDataset
	 */
	public DataNode setZ_alias(IDataset z_aliasDataset);

	/**
	 * Opportunity to define an alias for the name of the z-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getZ_aliasScalar();

	/**
	 * Opportunity to define an alias for the name of the z-axis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param z_alias the z_alias
	 */
	public DataNode setZ_aliasScalar(String z_aliasValue);

	/**
	 * Human-readable field telling in which direction the z-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the mighty world reference frame.
	 * See docstring of x_alias for further details.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getZ_direction();

	/**
	 * Human-readable field telling in which direction the z-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the mighty world reference frame.
	 * See docstring of x_alias for further details.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param z_directionDataset the z_directionDataset
	 */
	public DataNode setZ_direction(IDataset z_directionDataset);

	/**
	 * Human-readable field telling in which direction the z-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the mighty world reference frame.
	 * See docstring of x_alias for further details.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getZ_directionScalar();

	/**
	 * Human-readable field telling in which direction the z-axis points if that
	 * instance of :ref:`NXcoordinate_system` has no reference to any parent and as such
	 * is the mighty world reference frame.
	 * See docstring of x_alias for further details.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param z_direction the z_direction
	 */
	public DataNode setZ_directionScalar(String z_directionValue);

	/**
	 * Basis unit vector along the third axis which spans the coordinate system.
	 * This axis is frequently referred to as the z-axis in Euclidean space and
	 * the k-axis in reciprocal space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getZ();

	/**
	 * Basis unit vector along the third axis which spans the coordinate system.
	 * This axis is frequently referred to as the z-axis in Euclidean space and
	 * the k-axis in reciprocal space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param zDataset the zDataset
	 */
	public DataNode setZ(IDataset zDataset);

	/**
	 * Basis unit vector along the third axis which spans the coordinate system.
	 * This axis is frequently referred to as the z-axis in Euclidean space and
	 * the k-axis in reciprocal space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getZScalar();

	/**
	 * Basis unit vector along the third axis which spans the coordinate system.
	 * This axis is frequently referred to as the z-axis in Euclidean space and
	 * the k-axis in reciprocal space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param z the z
	 */
	public DataNode setZScalar(Number zValue);

	/**
	 * This specifies the relation to another coordinate system by pointing to the last
	 * transformation in the transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * This specifies the relation to another coordinate system by pointing to the last
	 * transformation in the transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * This specifies the relation to another coordinate system by pointing to the last
	 * transformation in the transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * This specifies the relation to another coordinate system by pointing to the last
	 * transformation in the transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Collection of axis-based translations and rotations to describe this coordinate system
	 * with respect to another coordinate system.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Collection of axis-based translations and rotations to describe this coordinate system
	 * with respect to another coordinate system.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe this coordinate system
	 * with respect to another coordinate system.</li>
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
	 * Collection of axis-based translations and rotations to describe this coordinate system
	 * with respect to another coordinate system.</li>
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
	 * Collection of axis-based translations and rotations to describe this coordinate system
	 * with respect to another coordinate system.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe this coordinate system
	 * with respect to another coordinate system.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


}
