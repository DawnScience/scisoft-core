/*-
 *******************************************************************************
 * Copyright (c) 2015 Diamond Light Source Ltd.
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

import org.eclipse.dawnsci.nexus.*;

/**
 * Geometry description for cylindrical shapes.
 * This class can be used in place of ``NXoff_geometry`` when an exact
 * representation for cylinders is preferred.
 * For example, for Helium-tube, neutron detectors.
 * It can be used to describe the shape of any beamline component, including detectors.
 * In the case of detectors it can be used to define the shape of a single pixel, or,
 * if the pixel shapes are non-uniform, to describe the shape of the whole detector.
 * 
 */
public class NXcylindrical_geometryImpl extends NXobjectImpl implements NXcylindrical_geometry {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcylindrical_geometryImpl() {
		super();
	}

	public NXcylindrical_geometryImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcylindrical_geometry.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CYLINDRICAL_GEOMETRY;
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
	public DataNode setVertices(IDataset vertices) {
		return setDataset(NX_VERTICES, vertices);
	}

	@Override
	public DataNode setVerticesScalar(Number vertices) {
		return setField(NX_VERTICES, vertices);
	}

	@Override
	public IDataset getCylinders() {
		return getDataset(NX_CYLINDERS);
	}

	@Override
	public Long getCylindersScalar() {
		return getLong(NX_CYLINDERS);
	}

	@Override
	public DataNode setCylinders(IDataset cylinders) {
		return setDataset(NX_CYLINDERS, cylinders);
	}

	@Override
	public DataNode setCylindersScalar(Long cylinders) {
		return setField(NX_CYLINDERS, cylinders);
	}

	@Override
	public IDataset getDetector_number() {
		return getDataset(NX_DETECTOR_NUMBER);
	}

	@Override
	public Long getDetector_numberScalar() {
		return getLong(NX_DETECTOR_NUMBER);
	}

	@Override
	public DataNode setDetector_number(IDataset detector_number) {
		return setDataset(NX_DETECTOR_NUMBER, detector_number);
	}

	@Override
	public DataNode setDetector_numberScalar(Long detector_number) {
		return setField(NX_DETECTOR_NUMBER, detector_number);
	}

}
