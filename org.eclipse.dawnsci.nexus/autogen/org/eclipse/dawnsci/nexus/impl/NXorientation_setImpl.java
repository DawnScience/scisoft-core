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
 * Details about individual orientations of a set of objects.
 * For a more detailed insight into the discussion of parameterizing
 * orientations in materials science see:
 * * https://doi.org/10.1016/j.matchar.2016.04.008
 * * https://doi.org/10.1088/0965-0393/23/8/083501
 * * https://doi.org/10.1007/978-3-662-09156-2 group-theory of rotations
 * * https://doi.org/10.1016/C2013-0-11769-2 the classical book of H.-J. Bunge

 */
public class NXorientation_setImpl extends NXobjectImpl implements NXorientation_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXorientation_setImpl() {
		super();
	}

	public NXorientation_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXorientation_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ORIENTATION_SET;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}

	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

	@Override
	public Dataset getParameterization() {
		return getDataset(NX_PARAMETERIZATION);
	}

	@Override
	public String getParameterizationScalar() {
		return getString(NX_PARAMETERIZATION);
	}

	@Override
	public DataNode setParameterization(IDataset parameterizationDataset) {
		return setDataset(NX_PARAMETERIZATION, parameterizationDataset);
	}

	@Override
	public DataNode setParameterizationScalar(String parameterizationValue) {
		return setString(NX_PARAMETERIZATION, parameterizationValue);
	}

	@Override
	public Dataset getObjects() {
		return getDataset(NX_OBJECTS);
	}

	@Override
	public String getObjectsScalar() {
		return getString(NX_OBJECTS);
	}

	@Override
	public DataNode setObjects(IDataset objectsDataset) {
		return setDataset(NX_OBJECTS, objectsDataset);
	}

	@Override
	public DataNode setObjectsScalar(String objectsValue) {
		return setString(NX_OBJECTS, objectsValue);
	}

	@Override
	public Dataset getIdentifier_offset() {
		return getDataset(NX_IDENTIFIER_OFFSET);
	}

	@Override
	public Long getIdentifier_offsetScalar() {
		return getLong(NX_IDENTIFIER_OFFSET);
	}

	@Override
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset) {
		return setDataset(NX_IDENTIFIER_OFFSET, identifier_offsetDataset);
	}

	@Override
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue) {
		return setField(NX_IDENTIFIER_OFFSET, identifier_offsetValue);
	}

	@Override
	public Dataset getIdentifier() {
		return getDataset(NX_IDENTIFIER);
	}

	@Override
	public Long getIdentifierScalar() {
		return getLong(NX_IDENTIFIER);
	}

	@Override
	public DataNode setIdentifier(IDataset identifierDataset) {
		return setDataset(NX_IDENTIFIER, identifierDataset);
	}

	@Override
	public DataNode setIdentifierScalar(Long identifierValue) {
		return setField(NX_IDENTIFIER, identifierValue);
	}

	@Override
	public Dataset getOrientation() {
		return getDataset(NX_ORIENTATION);
	}

	@Override
	public Number getOrientationScalar() {
		return getNumber(NX_ORIENTATION);
	}

	@Override
	public DataNode setOrientation(IDataset orientationDataset) {
		return setDataset(NX_ORIENTATION, orientationDataset);
	}

	@Override
	public DataNode setOrientationScalar(Number orientationValue) {
		return setField(NX_ORIENTATION, orientationValue);
	}

}
