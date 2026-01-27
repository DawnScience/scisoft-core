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
 * Base class for a spatial filter for objects within a region-of-interest (ROI).
 * Objects can be points, objects composed from other geometric primitives,
 * or objects.

 */
public class NXspatial_filterImpl extends NXparametersImpl implements NXspatial_filter {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_HEXAHEDRON,
		NexusBaseClass.NX_CG_CYLINDER,
		NexusBaseClass.NX_CG_ELLIPSOID,
		NexusBaseClass.NX_CG_POLYHEDRON,
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
	public NXcg_hexahedron getCg_hexahedron() {
		// dataNodeName = NX_CG_HEXAHEDRON
		return getChild("cg_hexahedron", NXcg_hexahedron.class);
	}

	@Override
	public void setCg_hexahedron(NXcg_hexahedron cg_hexahedronGroup) {
		putChild("cg_hexahedron", cg_hexahedronGroup);
	}

	@Override
	public NXcg_hexahedron getCg_hexahedron(String name) {
		return getChild(name, NXcg_hexahedron.class);
	}

	@Override
	public void setCg_hexahedron(String name, NXcg_hexahedron cg_hexahedron) {
		putChild(name, cg_hexahedron);
	}

	@Override
	public Map<String, NXcg_hexahedron> getAllCg_hexahedron() {
		return getChildren(NXcg_hexahedron.class);
	}

	@Override
	public void setAllCg_hexahedron(Map<String, NXcg_hexahedron> cg_hexahedron) {
		setChildren(cg_hexahedron);
	}

	@Override
	public NXcg_cylinder getCg_cylinder() {
		// dataNodeName = NX_CG_CYLINDER
		return getChild("cg_cylinder", NXcg_cylinder.class);
	}

	@Override
	public void setCg_cylinder(NXcg_cylinder cg_cylinderGroup) {
		putChild("cg_cylinder", cg_cylinderGroup);
	}

	@Override
	public NXcg_cylinder getCg_cylinder(String name) {
		return getChild(name, NXcg_cylinder.class);
	}

	@Override
	public void setCg_cylinder(String name, NXcg_cylinder cg_cylinder) {
		putChild(name, cg_cylinder);
	}

	@Override
	public Map<String, NXcg_cylinder> getAllCg_cylinder() {
		return getChildren(NXcg_cylinder.class);
	}

	@Override
	public void setAllCg_cylinder(Map<String, NXcg_cylinder> cg_cylinder) {
		setChildren(cg_cylinder);
	}

	@Override
	public NXcg_ellipsoid getCg_ellipsoid() {
		// dataNodeName = NX_CG_ELLIPSOID
		return getChild("cg_ellipsoid", NXcg_ellipsoid.class);
	}

	@Override
	public void setCg_ellipsoid(NXcg_ellipsoid cg_ellipsoidGroup) {
		putChild("cg_ellipsoid", cg_ellipsoidGroup);
	}

	@Override
	public NXcg_ellipsoid getCg_ellipsoid(String name) {
		return getChild(name, NXcg_ellipsoid.class);
	}

	@Override
	public void setCg_ellipsoid(String name, NXcg_ellipsoid cg_ellipsoid) {
		putChild(name, cg_ellipsoid);
	}

	@Override
	public Map<String, NXcg_ellipsoid> getAllCg_ellipsoid() {
		return getChildren(NXcg_ellipsoid.class);
	}

	@Override
	public void setAllCg_ellipsoid(Map<String, NXcg_ellipsoid> cg_ellipsoid) {
		setChildren(cg_ellipsoid);
	}

	@Override
	public NXcg_polyhedron getCg_polyhedron() {
		// dataNodeName = NX_CG_POLYHEDRON
		return getChild("cg_polyhedron", NXcg_polyhedron.class);
	}

	@Override
	public void setCg_polyhedron(NXcg_polyhedron cg_polyhedronGroup) {
		putChild("cg_polyhedron", cg_polyhedronGroup);
	}

	@Override
	public NXcg_polyhedron getCg_polyhedron(String name) {
		return getChild(name, NXcg_polyhedron.class);
	}

	@Override
	public void setCg_polyhedron(String name, NXcg_polyhedron cg_polyhedron) {
		putChild(name, cg_polyhedron);
	}

	@Override
	public Map<String, NXcg_polyhedron> getAllCg_polyhedron() {
		return getChildren(NXcg_polyhedron.class);
	}

	@Override
	public void setAllCg_polyhedron(Map<String, NXcg_polyhedron> cg_polyhedron) {
		setChildren(cg_polyhedron);
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
