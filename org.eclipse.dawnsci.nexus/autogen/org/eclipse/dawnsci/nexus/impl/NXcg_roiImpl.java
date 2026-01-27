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


import org.eclipse.dawnsci.nexus.*;

/**
 * Base class for a region-of-interest (ROI) bound by geometric primitives.
 * So-called region-of-interest(s) (ROIs) are typically used to describe a
 * region in space (and time) where an observation is made or for which
 * a computer simulation is performed with given boundary conditions.

 */
public class NXcg_roiImpl extends NXobjectImpl implements NXcg_roi {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_ELLIPSOID,
		NexusBaseClass.NX_CG_CYLINDER,
		NexusBaseClass.NX_CG_PARALLELOGRAM,
		NexusBaseClass.NX_CG_HEXAHEDRON,
		NexusBaseClass.NX_CG_POLYHEDRON);

	public NXcg_roiImpl() {
		super();
	}

	public NXcg_roiImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_roi.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_ROI;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public NXcg_parallelogram getCg_parallelogram() {
		// dataNodeName = NX_CG_PARALLELOGRAM
		return getChild("cg_parallelogram", NXcg_parallelogram.class);
	}

	@Override
	public void setCg_parallelogram(NXcg_parallelogram cg_parallelogramGroup) {
		putChild("cg_parallelogram", cg_parallelogramGroup);
	}

	@Override
	public NXcg_parallelogram getCg_parallelogram(String name) {
		return getChild(name, NXcg_parallelogram.class);
	}

	@Override
	public void setCg_parallelogram(String name, NXcg_parallelogram cg_parallelogram) {
		putChild(name, cg_parallelogram);
	}

	@Override
	public Map<String, NXcg_parallelogram> getAllCg_parallelogram() {
		return getChildren(NXcg_parallelogram.class);
	}

	@Override
	public void setAllCg_parallelogram(Map<String, NXcg_parallelogram> cg_parallelogram) {
		setChildren(cg_parallelogram);
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

}
