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
 * Spatial filter to filter entries within a region-of-interest based on their
 * position.

 */
public class NXspatial_filterImpl extends NXobjectImpl implements NXspatial_filter {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_ELLIPSOID_SET,
		NexusBaseClass.NX_CG_CYLINDER_SET,
		NexusBaseClass.NX_CG_HEXAHEDRON_SET,
		NexusBaseClass.NX_CS_FILTER_BOOLEAN_MASK);

	public NXspatial_filterImpl() {
		super();
	}

	public NXspatial_filterImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXspatial_filter.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SPATIAL_FILTER;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getWindowing_method() {
		return getDataset(NX_WINDOWING_METHOD);
	}

	@Override
	public String getWindowing_methodScalar() {
		return getString(NX_WINDOWING_METHOD);
	}

	@Override
	public DataNode setWindowing_method(IDataset windowing_methodDataset) {
		return setDataset(NX_WINDOWING_METHOD, windowing_methodDataset);
	}

	@Override
	public DataNode setWindowing_methodScalar(String windowing_methodValue) {
		return setString(NX_WINDOWING_METHOD, windowing_methodValue);
	}

	@Override
	public NXcg_ellipsoid_set getCg_ellipsoid_set() {
		// dataNodeName = NX_CG_ELLIPSOID_SET
		return getChild("cg_ellipsoid_set", NXcg_ellipsoid_set.class);
	}

	@Override
	public void setCg_ellipsoid_set(NXcg_ellipsoid_set cg_ellipsoid_setGroup) {
		putChild("cg_ellipsoid_set", cg_ellipsoid_setGroup);
	}

	@Override
	public NXcg_ellipsoid_set getCg_ellipsoid_set(String name) {
		return getChild(name, NXcg_ellipsoid_set.class);
	}

	@Override
	public void setCg_ellipsoid_set(String name, NXcg_ellipsoid_set cg_ellipsoid_set) {
		putChild(name, cg_ellipsoid_set);
	}

	@Override
	public Map<String, NXcg_ellipsoid_set> getAllCg_ellipsoid_set() {
		return getChildren(NXcg_ellipsoid_set.class);
	}

	@Override
	public void setAllCg_ellipsoid_set(Map<String, NXcg_ellipsoid_set> cg_ellipsoid_set) {
		setChildren(cg_ellipsoid_set);
	}

	@Override
	public NXcg_cylinder_set getCg_cylinder_set() {
		// dataNodeName = NX_CG_CYLINDER_SET
		return getChild("cg_cylinder_set", NXcg_cylinder_set.class);
	}

	@Override
	public void setCg_cylinder_set(NXcg_cylinder_set cg_cylinder_setGroup) {
		putChild("cg_cylinder_set", cg_cylinder_setGroup);
	}

	@Override
	public NXcg_cylinder_set getCg_cylinder_set(String name) {
		return getChild(name, NXcg_cylinder_set.class);
	}

	@Override
	public void setCg_cylinder_set(String name, NXcg_cylinder_set cg_cylinder_set) {
		putChild(name, cg_cylinder_set);
	}

	@Override
	public Map<String, NXcg_cylinder_set> getAllCg_cylinder_set() {
		return getChildren(NXcg_cylinder_set.class);
	}

	@Override
	public void setAllCg_cylinder_set(Map<String, NXcg_cylinder_set> cg_cylinder_set) {
		setChildren(cg_cylinder_set);
	}

	@Override
	public NXcg_hexahedron_set getCg_hexahedron_set() {
		// dataNodeName = NX_CG_HEXAHEDRON_SET
		return getChild("cg_hexahedron_set", NXcg_hexahedron_set.class);
	}

	@Override
	public void setCg_hexahedron_set(NXcg_hexahedron_set cg_hexahedron_setGroup) {
		putChild("cg_hexahedron_set", cg_hexahedron_setGroup);
	}

	@Override
	public NXcg_hexahedron_set getCg_hexahedron_set(String name) {
		return getChild(name, NXcg_hexahedron_set.class);
	}

	@Override
	public void setCg_hexahedron_set(String name, NXcg_hexahedron_set cg_hexahedron_set) {
		putChild(name, cg_hexahedron_set);
	}

	@Override
	public Map<String, NXcg_hexahedron_set> getAllCg_hexahedron_set() {
		return getChildren(NXcg_hexahedron_set.class);
	}

	@Override
	public void setAllCg_hexahedron_set(Map<String, NXcg_hexahedron_set> cg_hexahedron_set) {
		setChildren(cg_hexahedron_set);
	}

	@Override
	public NXcs_filter_boolean_mask getCs_filter_boolean_mask() {
		// dataNodeName = NX_CS_FILTER_BOOLEAN_MASK
		return getChild("cs_filter_boolean_mask", NXcs_filter_boolean_mask.class);
	}

	@Override
	public void setCs_filter_boolean_mask(NXcs_filter_boolean_mask cs_filter_boolean_maskGroup) {
		putChild("cs_filter_boolean_mask", cs_filter_boolean_maskGroup);
	}

	@Override
	public NXcs_filter_boolean_mask getCs_filter_boolean_mask(String name) {
		return getChild(name, NXcs_filter_boolean_mask.class);
	}

	@Override
	public void setCs_filter_boolean_mask(String name, NXcs_filter_boolean_mask cs_filter_boolean_mask) {
		putChild(name, cs_filter_boolean_mask);
	}

	@Override
	public Map<String, NXcs_filter_boolean_mask> getAllCs_filter_boolean_mask() {
		return getChildren(NXcs_filter_boolean_mask.class);
	}

	@Override
	public void setAllCs_filter_boolean_mask(Map<String, NXcs_filter_boolean_mask> cs_filter_boolean_mask) {
		setChildren(cs_filter_boolean_mask);
	}

}
