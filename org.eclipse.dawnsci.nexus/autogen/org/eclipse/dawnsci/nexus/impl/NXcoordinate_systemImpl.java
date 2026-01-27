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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

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

 */
public class NXcoordinate_systemImpl extends NXobjectImpl implements NXcoordinate_system {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXcoordinate_systemImpl() {
		super();
	}

	public NXcoordinate_systemImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcoordinate_system.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_COORDINATE_SYSTEM;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getOrigin() {
		return getDataset(NX_ORIGIN);
	}

	@Override
	public String getOriginScalar() {
		return getString(NX_ORIGIN);
	}

	@Override
	public DataNode setOrigin(IDataset originDataset) {
		return setDataset(NX_ORIGIN, originDataset);
	}

	@Override
	public DataNode setOriginScalar(String originValue) {
		return setString(NX_ORIGIN, originValue);
	}

	@Override
	public Dataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public Dataset getX_alias() {
		return getDataset(NX_X_ALIAS);
	}

	@Override
	public String getX_aliasScalar() {
		return getString(NX_X_ALIAS);
	}

	@Override
	public DataNode setX_alias(IDataset x_aliasDataset) {
		return setDataset(NX_X_ALIAS, x_aliasDataset);
	}

	@Override
	public DataNode setX_aliasScalar(String x_aliasValue) {
		return setString(NX_X_ALIAS, x_aliasValue);
	}

	@Override
	public Dataset getX_direction() {
		return getDataset(NX_X_DIRECTION);
	}

	@Override
	public String getX_directionScalar() {
		return getString(NX_X_DIRECTION);
	}

	@Override
	public DataNode setX_direction(IDataset x_directionDataset) {
		return setDataset(NX_X_DIRECTION, x_directionDataset);
	}

	@Override
	public DataNode setX_directionScalar(String x_directionValue) {
		return setString(NX_X_DIRECTION, x_directionValue);
	}

	@Override
	public Dataset getX() {
		return getDataset(NX_X);
	}

	@Override
	public Number getXScalar() {
		return getNumber(NX_X);
	}

	@Override
	public DataNode setX(IDataset xDataset) {
		return setDataset(NX_X, xDataset);
	}

	@Override
	public DataNode setXScalar(Number xValue) {
		return setField(NX_X, xValue);
	}

	@Override
	public Dataset getY_alias() {
		return getDataset(NX_Y_ALIAS);
	}

	@Override
	public String getY_aliasScalar() {
		return getString(NX_Y_ALIAS);
	}

	@Override
	public DataNode setY_alias(IDataset y_aliasDataset) {
		return setDataset(NX_Y_ALIAS, y_aliasDataset);
	}

	@Override
	public DataNode setY_aliasScalar(String y_aliasValue) {
		return setString(NX_Y_ALIAS, y_aliasValue);
	}

	@Override
	public Dataset getY_direction() {
		return getDataset(NX_Y_DIRECTION);
	}

	@Override
	public String getY_directionScalar() {
		return getString(NX_Y_DIRECTION);
	}

	@Override
	public DataNode setY_direction(IDataset y_directionDataset) {
		return setDataset(NX_Y_DIRECTION, y_directionDataset);
	}

	@Override
	public DataNode setY_directionScalar(String y_directionValue) {
		return setString(NX_Y_DIRECTION, y_directionValue);
	}

	@Override
	public Dataset getY() {
		return getDataset(NX_Y);
	}

	@Override
	public Number getYScalar() {
		return getNumber(NX_Y);
	}

	@Override
	public DataNode setY(IDataset yDataset) {
		return setDataset(NX_Y, yDataset);
	}

	@Override
	public DataNode setYScalar(Number yValue) {
		return setField(NX_Y, yValue);
	}

	@Override
	public Dataset getZ_alias() {
		return getDataset(NX_Z_ALIAS);
	}

	@Override
	public String getZ_aliasScalar() {
		return getString(NX_Z_ALIAS);
	}

	@Override
	public DataNode setZ_alias(IDataset z_aliasDataset) {
		return setDataset(NX_Z_ALIAS, z_aliasDataset);
	}

	@Override
	public DataNode setZ_aliasScalar(String z_aliasValue) {
		return setString(NX_Z_ALIAS, z_aliasValue);
	}

	@Override
	public Dataset getZ_direction() {
		return getDataset(NX_Z_DIRECTION);
	}

	@Override
	public String getZ_directionScalar() {
		return getString(NX_Z_DIRECTION);
	}

	@Override
	public DataNode setZ_direction(IDataset z_directionDataset) {
		return setDataset(NX_Z_DIRECTION, z_directionDataset);
	}

	@Override
	public DataNode setZ_directionScalar(String z_directionValue) {
		return setString(NX_Z_DIRECTION, z_directionValue);
	}

	@Override
	public Dataset getZ() {
		return getDataset(NX_Z);
	}

	@Override
	public Number getZScalar() {
		return getNumber(NX_Z);
	}

	@Override
	public DataNode setZ(IDataset zDataset) {
		return setDataset(NX_Z, zDataset);
	}

	@Override
	public DataNode setZScalar(Number zValue) {
		return setField(NX_Z, zValue);
	}

	@Override
	public Dataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
	}

	@Override
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}

	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

}
