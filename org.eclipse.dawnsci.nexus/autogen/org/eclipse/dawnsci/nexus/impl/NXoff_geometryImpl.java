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

import org.eclipse.dawnsci.nexus.*;

/**
 * Geometry (shape) description.
 * The format closely matches the Object File Format (OFF) which can be output
 * by most CAD software.
 * It can be used to describe the shape of any beamline component, including detectors.
 * In the case of detectors it can be used to define the shape of a single pixel, or,
 * if the pixel shapes are non-uniform, to describe the shape of the whole detector.
 * 
 */
public class NXoff_geometryImpl extends NXobjectImpl implements NXoff_geometry {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXoff_geometryImpl() {
		super();
	}

	public NXoff_geometryImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXoff_geometry.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_OFF_GEOMETRY;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getVertices() {
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
	public IDataset getWinding_order() {
		return getDataset(NX_WINDING_ORDER);
	}

	@Override
	public Long getWinding_orderScalar() {
		return getLong(NX_WINDING_ORDER);
	}

	@Override
	public DataNode setWinding_order(IDataset winding_orderDataset) {
		return setDataset(NX_WINDING_ORDER, winding_orderDataset);
	}

	@Override
	public DataNode setWinding_orderScalar(Long winding_orderValue) {
		return setField(NX_WINDING_ORDER, winding_orderValue);
	}

	@Override
	public IDataset getFaces() {
		return getDataset(NX_FACES);
	}

	@Override
	public Long getFacesScalar() {
		return getLong(NX_FACES);
	}

	@Override
	public DataNode setFaces(IDataset facesDataset) {
		return setDataset(NX_FACES, facesDataset);
	}

	@Override
	public DataNode setFacesScalar(Long facesValue) {
		return setField(NX_FACES, facesValue);
	}

	@Override
	public IDataset getDetector_faces() {
		return getDataset(NX_DETECTOR_FACES);
	}

	@Override
	public Long getDetector_facesScalar() {
		return getLong(NX_DETECTOR_FACES);
	}

	@Override
	public DataNode setDetector_faces(IDataset detector_facesDataset) {
		return setDataset(NX_DETECTOR_FACES, detector_facesDataset);
	}

	@Override
	public DataNode setDetector_facesScalar(Long detector_facesValue) {
		return setField(NX_DETECTOR_FACES, detector_facesValue);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
