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
 * Computational geometry description of a set of polylines.
 * Each polyline is built from a sequence of vertices (points with identifiers).
 * Each polyline must have a start and an end point.
 * The sequence describes the traversal along the polyline when
 * walking from the first to the last vertex.

 */
public class NXcg_polylineImpl extends NXcg_primitiveImpl implements NXcg_polyline {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcg_polylineImpl() {
		super();
	}

	public NXcg_polylineImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_polyline.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_POLYLINE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public Dataset getNumber_of_unique_vertices() {
		return getDataset(NX_NUMBER_OF_UNIQUE_VERTICES);
	}

	@Override
	public Long getNumber_of_unique_verticesScalar() {
		return getLong(NX_NUMBER_OF_UNIQUE_VERTICES);
	}

	@Override
	public DataNode setNumber_of_unique_vertices(IDataset number_of_unique_verticesDataset) {
		return setDataset(NX_NUMBER_OF_UNIQUE_VERTICES, number_of_unique_verticesDataset);
	}

	@Override
	public DataNode setNumber_of_unique_verticesScalar(Long number_of_unique_verticesValue) {
		return setField(NX_NUMBER_OF_UNIQUE_VERTICES, number_of_unique_verticesValue);
	}

	@Override
	public Dataset getNumber_of_total_vertices() {
		return getDataset(NX_NUMBER_OF_TOTAL_VERTICES);
	}

	@Override
	public Long getNumber_of_total_verticesScalar() {
		return getLong(NX_NUMBER_OF_TOTAL_VERTICES);
	}

	@Override
	public DataNode setNumber_of_total_vertices(IDataset number_of_total_verticesDataset) {
		return setDataset(NX_NUMBER_OF_TOTAL_VERTICES, number_of_total_verticesDataset);
	}

	@Override
	public DataNode setNumber_of_total_verticesScalar(Long number_of_total_verticesValue) {
		return setField(NX_NUMBER_OF_TOTAL_VERTICES, number_of_total_verticesValue);
	}

	@Override
	public Dataset getNumber_of_vertices() {
		return getDataset(NX_NUMBER_OF_VERTICES);
	}

	@Override
	public Long getNumber_of_verticesScalar() {
		return getLong(NX_NUMBER_OF_VERTICES);
	}

	@Override
	public DataNode setNumber_of_vertices(IDataset number_of_verticesDataset) {
		return setDataset(NX_NUMBER_OF_VERTICES, number_of_verticesDataset);
	}

	@Override
	public DataNode setNumber_of_verticesScalar(Long number_of_verticesValue) {
		return setField(NX_NUMBER_OF_VERTICES, number_of_verticesValue);
	}

	@Override
	public Dataset getVertices() {
		return getDataset(NX_VERTICES);
	}

	@Override
	public Number getVerticesScalar() {
		return getNumber(NX_VERTICES);
	}

	@Override
	public DataNode setVertices(IDataset verticesDataset) {
		return setDataset(NX_VERTICES, verticesDataset);
	}

	@Override
	public DataNode setVerticesScalar(Number verticesValue) {
		return setField(NX_VERTICES, verticesValue);
	}

	@Override
	public Dataset getVertices_are_unique() {
		return getDataset(NX_VERTICES_ARE_UNIQUE);
	}

	@Override
	public Boolean getVertices_are_uniqueScalar() {
		return getBoolean(NX_VERTICES_ARE_UNIQUE);
	}

	@Override
	public DataNode setVertices_are_unique(IDataset vertices_are_uniqueDataset) {
		return setDataset(NX_VERTICES_ARE_UNIQUE, vertices_are_uniqueDataset);
	}

	@Override
	public DataNode setVertices_are_uniqueScalar(Boolean vertices_are_uniqueValue) {
		return setField(NX_VERTICES_ARE_UNIQUE, vertices_are_uniqueValue);
	}

	@Override
	public Dataset getPolylines() {
		return getDataset(NX_POLYLINES);
	}

	@Override
	public Long getPolylinesScalar() {
		return getLong(NX_POLYLINES);
	}

	@Override
	public DataNode setPolylines(IDataset polylinesDataset) {
		return setDataset(NX_POLYLINES, polylinesDataset);
	}

	@Override
	public DataNode setPolylinesScalar(Long polylinesValue) {
		return setField(NX_POLYLINES, polylinesValue);
	}

}
