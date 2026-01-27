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
 * Computational geometry description of a set of tetrahedra.
 * Among hexahedral elements, tetrahedral elements are one of the most
 * frequently used geometric primitive for meshing and describing volumetric
 * objects in continuum-field simulations.

 */
public class NXcg_tetrahedronImpl extends NXcg_primitiveImpl implements NXcg_tetrahedron {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_HALF_EDGE_DATA_STRUCTURE);

	public NXcg_tetrahedronImpl() {
		super();
	}

	public NXcg_tetrahedronImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_tetrahedron.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_TETRAHEDRON;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getFace_area() {
		return getDataset(NX_FACE_AREA);
	}

	@Override
	public Number getFace_areaScalar() {
		return getNumber(NX_FACE_AREA);
	}

	@Override
	public DataNode setFace_area(IDataset face_areaDataset) {
		return setDataset(NX_FACE_AREA, face_areaDataset);
	}

	@Override
	public DataNode setFace_areaScalar(Number face_areaValue) {
		return setField(NX_FACE_AREA, face_areaValue);
	}

	@Override
	public Dataset getEdge_length() {
		return getDataset(NX_EDGE_LENGTH);
	}

	@Override
	public Number getEdge_lengthScalar() {
		return getNumber(NX_EDGE_LENGTH);
	}

	@Override
	public DataNode setEdge_length(IDataset edge_lengthDataset) {
		return setDataset(NX_EDGE_LENGTH, edge_lengthDataset);
	}

	@Override
	public DataNode setEdge_lengthScalar(Number edge_lengthValue) {
		return setField(NX_EDGE_LENGTH, edge_lengthValue);
	}

	@Override
	public NXcg_face_list_data_structure getTetrahedra() {
		// dataNodeName = NX_TETRAHEDRA
		return getChild("tetrahedra", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setTetrahedra(NXcg_face_list_data_structure tetrahedraGroup) {
		putChild("tetrahedra", tetrahedraGroup);
	}

	@Override
	public NXcg_face_list_data_structure getTetrahedronid() {
		// dataNodeName = NX_TETRAHEDRONID
		return getChild("tetrahedronid", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setTetrahedronid(NXcg_face_list_data_structure tetrahedronidGroup) {
		putChild("tetrahedronid", tetrahedronidGroup);
	}

	@Override
	public NXcg_half_edge_data_structure getTetrahedron_half_edgeid() {
		// dataNodeName = NX_TETRAHEDRON_HALF_EDGEID
		return getChild("tetrahedron_half_edgeid", NXcg_half_edge_data_structure.class);
	}

	@Override
	public void setTetrahedron_half_edgeid(NXcg_half_edge_data_structure tetrahedron_half_edgeidGroup) {
		putChild("tetrahedron_half_edgeid", tetrahedron_half_edgeidGroup);
	}

}
