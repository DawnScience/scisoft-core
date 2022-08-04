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
 * the head node for constructively defined geometry
 * 
 */
public class NXsolid_geometryImpl extends NXobjectImpl implements NXsolid_geometry {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_QUADRIC,
		NexusBaseClass.NX_OFF_GEOMETRY,
		NexusBaseClass.NX_CSG);

	public NXsolid_geometryImpl() {
		super();
	}

	public NXsolid_geometryImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXsolid_geometry.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SOLID_GEOMETRY;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public NXquadric getQuadric() {
		// dataNodeName = NX_QUADRIC
		return getChild("quadric", NXquadric.class);
	}

	@Override
	public void setQuadric(NXquadric quadricGroup) {
		putChild("quadric", quadricGroup);
	}

	@Override
	public NXquadric getQuadric(String name) {
		return getChild(name, NXquadric.class);
	}

	@Override
	public void setQuadric(String name, NXquadric quadric) {
		putChild(name, quadric);
	}

	@Override
	public Map<String, NXquadric> getAllQuadric() {
		return getChildren(NXquadric.class);
	}
	
	@Override
	public void setAllQuadric(Map<String, NXquadric> quadric) {
		setChildren(quadric);
	}

	@Override
	public NXoff_geometry getOff_geometry() {
		// dataNodeName = NX_OFF_GEOMETRY
		return getChild("off_geometry", NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(NXoff_geometry off_geometryGroup) {
		putChild("off_geometry", off_geometryGroup);
	}

	@Override
	public NXoff_geometry getOff_geometry(String name) {
		return getChild(name, NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(String name, NXoff_geometry off_geometry) {
		putChild(name, off_geometry);
	}

	@Override
	public Map<String, NXoff_geometry> getAllOff_geometry() {
		return getChildren(NXoff_geometry.class);
	}
	
	@Override
	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry) {
		setChildren(off_geometry);
	}

	@Override
	public NXcsg getCsg() {
		// dataNodeName = NX_CSG
		return getChild("csg", NXcsg.class);
	}

	@Override
	public void setCsg(NXcsg csgGroup) {
		putChild("csg", csgGroup);
	}

	@Override
	public NXcsg getCsg(String name) {
		return getChild(name, NXcsg.class);
	}

	@Override
	public void setCsg(String name, NXcsg csg) {
		putChild(name, csg);
	}

	@Override
	public Map<String, NXcsg> getAllCsg() {
		return getChildren(NXcsg.class);
	}
	
	@Override
	public void setAllCsg(Map<String, NXcsg> csg) {
		setChildren(csg);
	}

}
