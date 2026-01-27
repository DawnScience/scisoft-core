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
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Computational geometry description of a set of ellipsoids.

 */
public class NXcg_ellipsoidImpl extends NXcg_primitiveImpl implements NXcg_ellipsoid {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcg_ellipsoidImpl() {
		super();
	}

	public NXcg_ellipsoidImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_ellipsoid.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_ELLIPSOID;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getSemi_axes_value() {
		return getDataset(NX_SEMI_AXES_VALUE);
	}

	@Override
	public Number getSemi_axes_valueScalar() {
		return getNumber(NX_SEMI_AXES_VALUE);
	}

	@Override
	public DataNode setSemi_axes_value(IDataset semi_axes_valueDataset) {
		return setDataset(NX_SEMI_AXES_VALUE, semi_axes_valueDataset);
	}

	@Override
	public DataNode setSemi_axes_valueScalar(Number semi_axes_valueValue) {
		return setField(NX_SEMI_AXES_VALUE, semi_axes_valueValue);
	}

	@Override
	public Dataset getSemi_axes_values() {
		return getDataset(NX_SEMI_AXES_VALUES);
	}

	@Override
	public Number getSemi_axes_valuesScalar() {
		return getNumber(NX_SEMI_AXES_VALUES);
	}

	@Override
	public DataNode setSemi_axes_values(IDataset semi_axes_valuesDataset) {
		return setDataset(NX_SEMI_AXES_VALUES, semi_axes_valuesDataset);
	}

	@Override
	public DataNode setSemi_axes_valuesScalar(Number semi_axes_valuesValue) {
		return setField(NX_SEMI_AXES_VALUES, semi_axes_valuesValue);
	}

	@Override
	public Dataset getRadius() {
		return getDataset(NX_RADIUS);
	}

	@Override
	public Number getRadiusScalar() {
		return getNumber(NX_RADIUS);
	}

	@Override
	public DataNode setRadius(IDataset radiusDataset) {
		return setDataset(NX_RADIUS, radiusDataset);
	}

	@Override
	public DataNode setRadiusScalar(Number radiusValue) {
		return setField(NX_RADIUS, radiusValue);
	}

	@Override
	public Dataset getRadii() {
		return getDataset(NX_RADII);
	}

	@Override
	public Number getRadiiScalar() {
		return getNumber(NX_RADII);
	}

	@Override
	public DataNode setRadii(IDataset radiiDataset) {
		return setDataset(NX_RADII, radiiDataset);
	}

	@Override
	public DataNode setRadiiScalar(Number radiiValue) {
		return setField(NX_RADII, radiiValue);
	}

}
