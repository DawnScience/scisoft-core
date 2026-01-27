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
 * Computational geometry description of a set of cylinders or (truncated) cones.
 * The radius can either be defined in the radii field or by filling the upper_cap_radii
 * and lower_cap_radii fields respectively. The latter field case can
 * thus be used to represent (truncated) cones.
 * It is possible to define only one of the cap_radii fields
 * to represent half-open cylinder.

 */
public class NXcg_cylinderImpl extends NXcg_primitiveImpl implements NXcg_cylinder {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcg_cylinderImpl() {
		super();
	}

	public NXcg_cylinderImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_cylinder.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_CYLINDER;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getHeight() {
		return getDataset(NX_HEIGHT);
	}

	@Override
	public Number getHeightScalar() {
		return getNumber(NX_HEIGHT);
	}

	@Override
	public DataNode setHeight(IDataset heightDataset) {
		return setDataset(NX_HEIGHT, heightDataset);
	}

	@Override
	public DataNode setHeightScalar(Number heightValue) {
		return setField(NX_HEIGHT, heightValue);
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

	@Override
	public Dataset getUpper_cap_radii() {
		return getDataset(NX_UPPER_CAP_RADII);
	}

	@Override
	public Number getUpper_cap_radiiScalar() {
		return getNumber(NX_UPPER_CAP_RADII);
	}

	@Override
	public DataNode setUpper_cap_radii(IDataset upper_cap_radiiDataset) {
		return setDataset(NX_UPPER_CAP_RADII, upper_cap_radiiDataset);
	}

	@Override
	public DataNode setUpper_cap_radiiScalar(Number upper_cap_radiiValue) {
		return setField(NX_UPPER_CAP_RADII, upper_cap_radiiValue);
	}

	@Override
	public Dataset getLower_cap_radii() {
		return getDataset(NX_LOWER_CAP_RADII);
	}

	@Override
	public Number getLower_cap_radiiScalar() {
		return getNumber(NX_LOWER_CAP_RADII);
	}

	@Override
	public DataNode setLower_cap_radii(IDataset lower_cap_radiiDataset) {
		return setDataset(NX_LOWER_CAP_RADII, lower_cap_radiiDataset);
	}

	@Override
	public DataNode setLower_cap_radiiScalar(Number lower_cap_radiiValue) {
		return setField(NX_LOWER_CAP_RADII, lower_cap_radiiValue);
	}

	@Override
	public Dataset getLateral_surface_area() {
		return getDataset(NX_LATERAL_SURFACE_AREA);
	}

	@Override
	public Number getLateral_surface_areaScalar() {
		return getNumber(NX_LATERAL_SURFACE_AREA);
	}

	@Override
	public DataNode setLateral_surface_area(IDataset lateral_surface_areaDataset) {
		return setDataset(NX_LATERAL_SURFACE_AREA, lateral_surface_areaDataset);
	}

	@Override
	public DataNode setLateral_surface_areaScalar(Number lateral_surface_areaValue) {
		return setField(NX_LATERAL_SURFACE_AREA, lateral_surface_areaValue);
	}

	@Override
	public Dataset getUpper_cap_surface_area() {
		return getDataset(NX_UPPER_CAP_SURFACE_AREA);
	}

	@Override
	public Number getUpper_cap_surface_areaScalar() {
		return getNumber(NX_UPPER_CAP_SURFACE_AREA);
	}

	@Override
	public DataNode setUpper_cap_surface_area(IDataset upper_cap_surface_areaDataset) {
		return setDataset(NX_UPPER_CAP_SURFACE_AREA, upper_cap_surface_areaDataset);
	}

	@Override
	public DataNode setUpper_cap_surface_areaScalar(Number upper_cap_surface_areaValue) {
		return setField(NX_UPPER_CAP_SURFACE_AREA, upper_cap_surface_areaValue);
	}

	@Override
	public Dataset getLower_cap_surface_area() {
		return getDataset(NX_LOWER_CAP_SURFACE_AREA);
	}

	@Override
	public Number getLower_cap_surface_areaScalar() {
		return getNumber(NX_LOWER_CAP_SURFACE_AREA);
	}

	@Override
	public DataNode setLower_cap_surface_area(IDataset lower_cap_surface_areaDataset) {
		return setDataset(NX_LOWER_CAP_SURFACE_AREA, lower_cap_surface_areaDataset);
	}

	@Override
	public DataNode setLower_cap_surface_areaScalar(Number lower_cap_surface_areaValue) {
		return setField(NX_LOWER_CAP_SURFACE_AREA, lower_cap_surface_areaValue);
	}

	@Override
	public Dataset getTotal_surface_area() {
		return getDataset(NX_TOTAL_SURFACE_AREA);
	}

	@Override
	public Number getTotal_surface_areaScalar() {
		return getNumber(NX_TOTAL_SURFACE_AREA);
	}

	@Override
	public DataNode setTotal_surface_area(IDataset total_surface_areaDataset) {
		return setDataset(NX_TOTAL_SURFACE_AREA, total_surface_areaDataset);
	}

	@Override
	public DataNode setTotal_surface_areaScalar(Number total_surface_areaValue) {
		return setField(NX_TOTAL_SURFACE_AREA, total_surface_areaValue);
	}

}
