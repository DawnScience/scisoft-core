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
 * Computational geometry description of a set of polygons in Euclidean space.
 * Polygons are specialized polylines:
 * * A polygon is a geometric primitive that is bounded by a closed polyline
 * * All vertices of this polyline lay in the d-1 dimensional plane.
 * whereas vertices of a polyline do not necessarily lay on a plane.
 * * A polygon has at least three vertices.
 * Each polygon is built from a sequence of vertices (points with identifiers).
 * The members of a set of polygons may have a different number of vertices.
 * Sometimes a collection/set of polygons is referred to as a soup of polygons.
 * As three-dimensional objects, a set of polygons can be used to define the
 * hull of what is effectively a polyhedron; however users are advised to use
 * the specific :ref:`NXcg_polyhedron` base class if they wish to describe closed
 * polyhedra. Even more general complexes can be thought of. An example are the
 * so-called piecewise-linear complexes used in the TetGen library.
 * As these complexes can have holes though, polyhedra without holes are one
 * subclass of such complexes, users should rather design their own base class
 * e.g. NXcg_polytope to describe such even more complex primitives instead
 * of abusing this base class for such purposes.

 */
public class NXcg_polygonImpl extends NXcg_primitiveImpl implements NXcg_polygon {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_HALF_EDGE_DATA_STRUCTURE);

	public NXcg_polygonImpl() {
		super();
	}

	public NXcg_polygonImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_polygon.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_POLYGON;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public NXcg_face_list_data_structure getPolygons() {
		// dataNodeName = NX_POLYGONS
		return getChild("polygons", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setPolygons(NXcg_face_list_data_structure polygonsGroup) {
		putChild("polygons", polygonsGroup);
	}

	@Override
	public NXcg_face_list_data_structure getPolygonid() {
		// dataNodeName = NX_POLYGONID
		return getChild("polygonid", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setPolygonid(NXcg_face_list_data_structure polygonidGroup) {
		putChild("polygonid", polygonidGroup);
	}

	@Override
	public NXcg_half_edge_data_structure getPolygon_half_edgeid() {
		// dataNodeName = NX_POLYGON_HALF_EDGEID
		return getChild("polygon_half_edgeid", NXcg_half_edge_data_structure.class);
	}

	@Override
	public void setPolygon_half_edgeid(NXcg_half_edge_data_structure polygon_half_edgeidGroup) {
		putChild("polygon_half_edgeid", polygon_half_edgeidGroup);
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
	public Dataset getInterior_angle() {
		return getDataset(NX_INTERIOR_ANGLE);
	}

	@Override
	public Number getInterior_angleScalar() {
		return getNumber(NX_INTERIOR_ANGLE);
	}

	@Override
	public DataNode setInterior_angle(IDataset interior_angleDataset) {
		return setDataset(NX_INTERIOR_ANGLE, interior_angleDataset);
	}

	@Override
	public DataNode setInterior_angleScalar(Number interior_angleValue) {
		return setField(NX_INTERIOR_ANGLE, interior_angleValue);
	}

	@Override
	public Dataset getShape() {
		return getDataset(NX_SHAPE);
	}

	@Override
	public Long getShapeScalar() {
		return getLong(NX_SHAPE);
	}

	@Override
	public DataNode setShape(IDataset shapeDataset) {
		return setDataset(NX_SHAPE, shapeDataset);
	}

	@Override
	public DataNode setShapeScalar(Long shapeValue) {
		return setField(NX_SHAPE, shapeValue);
	}

}
