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
 * Computational geometry description of a set of parallelograms.
 * This class can also be used to describe rectangles or squares, irrespective
 * whether these are axis-aligned or not. The class represents different
 * access and description levels to embrace applied scientists and computational
 * geometry experts with their different views:
 * * The simplest case is the communication of dimensions aka the size of a
 * region of interest in the 2D plane. In this case, communicating the
 * alignment with axes is maybe not as relevant as it is to report the area
 * of the ROI.
 * * In other cases the extent of the parallelogram is relevant though.
 * * Finally, in CAD models it should be possible to specify the polygons
 * which the parallelograms represent with exact numerical details.
 * Parallelograms are important geometrical primitives as their usage for
 * describing many scanning experiments shows where typically parallelogram-shaped
 * ROIs are scanned across the surface of a sample.
 * The term parallelogram will be used throughout this base class thus including
 * the important special cases rectangle, square, 2D box, axis-aligned bounding box
 * (AABB), or optimal bounding box (OBB) as analogous 2D variants to their 3D
 * counterparts. See :ref:`NXcg_hexahedron` for the generalization in 3D.
 * An axis-aligned bounding box is a common data object in computational science
 * and simulation codes to represent a rectangle whose edges are aligned with the
 * axes of a coordinate system. As a part of binary trees AABBs are important data
 * objects for executing time- as well as space-efficient queries
 * of geometric primitives in techniques like kd-trees.
 * An optimal bounding box is a common data object which provides the best, i.e.
 * most tightly fitting box about an arbitrary object. In general such boxes are
 * rotated. Other than in 3D dimensions, the rotation caliper method offers
 * a rigorous approach to compute an optimal bounding box to a point set in 2D.

 */
public class NXcg_parallelogramImpl extends NXcg_primitiveImpl implements NXcg_parallelogram {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE);

	public NXcg_parallelogramImpl() {
		super();
	}

	public NXcg_parallelogramImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_parallelogram.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_PARALLELOGRAM;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getIs_rectangle() {
		return getDataset(NX_IS_RECTANGLE);
	}

	@Override
	public Boolean getIs_rectangleScalar() {
		return getBoolean(NX_IS_RECTANGLE);
	}

	@Override
	public DataNode setIs_rectangle(IDataset is_rectangleDataset) {
		return setDataset(NX_IS_RECTANGLE, is_rectangleDataset);
	}

	@Override
	public DataNode setIs_rectangleScalar(Boolean is_rectangleValue) {
		return setField(NX_IS_RECTANGLE, is_rectangleValue);
	}

	@Override
	public Dataset getIs_axis_aligned() {
		return getDataset(NX_IS_AXIS_ALIGNED);
	}

	@Override
	public Boolean getIs_axis_alignedScalar() {
		return getBoolean(NX_IS_AXIS_ALIGNED);
	}

	@Override
	public DataNode setIs_axis_aligned(IDataset is_axis_alignedDataset) {
		return setDataset(NX_IS_AXIS_ALIGNED, is_axis_alignedDataset);
	}

	@Override
	public DataNode setIs_axis_alignedScalar(Boolean is_axis_alignedValue) {
		return setField(NX_IS_AXIS_ALIGNED, is_axis_alignedValue);
	}

	@Override
	public NXcg_face_list_data_structure getParallelograms() {
		// dataNodeName = NX_PARALLELOGRAMS
		return getChild("parallelograms", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setParallelograms(NXcg_face_list_data_structure parallelogramsGroup) {
		putChild("parallelograms", parallelogramsGroup);
	}

	@Override
	public NXcg_face_list_data_structure getParallelogramid() {
		// dataNodeName = NX_PARALLELOGRAMID
		return getChild("parallelogramid", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setParallelogramid(NXcg_face_list_data_structure parallelogramidGroup) {
		putChild("parallelogramid", parallelogramidGroup);
	}

}
