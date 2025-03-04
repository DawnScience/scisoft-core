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
 * Description of peaks, their functional form or measured support.

 */
public class NXpeakImpl extends NXobjectImpl implements NXpeak {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_COLLECTION);

	public NXpeakImpl() {
		super();
	}

	public NXpeakImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpeak.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PEAK;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getLabel() {
		return getDataset(NX_LABEL);
	}

	@Override
	public String getLabelScalar() {
		return getString(NX_LABEL);
	}

	@Override
	public DataNode setLabel(IDataset labelDataset) {
		return setDataset(NX_LABEL, labelDataset);
	}

	@Override
	public DataNode setLabelScalar(String labelValue) {
		return setString(NX_LABEL, labelValue);
	}

	@Override
	public Dataset getPeak_model() {
		return getDataset(NX_PEAK_MODEL);
	}

	@Override
	public String getPeak_modelScalar() {
		return getString(NX_PEAK_MODEL);
	}

	@Override
	public DataNode setPeak_model(IDataset peak_modelDataset) {
		return setDataset(NX_PEAK_MODEL, peak_modelDataset);
	}

	@Override
	public DataNode setPeak_modelScalar(String peak_modelValue) {
		return setString(NX_PEAK_MODEL, peak_modelValue);
	}

	@Override
	public Dataset getPosition() {
		return getDataset(NX_POSITION);
	}

	@Override
	public Number getPositionScalar() {
		return getNumber(NX_POSITION);
	}

	@Override
	public DataNode setPosition(IDataset positionDataset) {
		return setDataset(NX_POSITION, positionDataset);
	}

	@Override
	public DataNode setPositionScalar(Number positionValue) {
		return setField(NX_POSITION, positionValue);
	}

	@Override
	public Dataset getIntensity() {
		return getDataset(NX_INTENSITY);
	}

	@Override
	public Number getIntensityScalar() {
		return getNumber(NX_INTENSITY);
	}

	@Override
	public DataNode setIntensity(IDataset intensityDataset) {
		return setDataset(NX_INTENSITY, intensityDataset);
	}

	@Override
	public DataNode setIntensityScalar(Number intensityValue) {
		return setField(NX_INTENSITY, intensityValue);
	}

	@Override
	public NXcollection getCollection() {
		// dataNodeName = NX_COLLECTION
		return getChild("collection", NXcollection.class);
	}

	@Override
	public void setCollection(NXcollection collectionGroup) {
		putChild("collection", collectionGroup);
	}

	@Override
	public NXcollection getCollection(String name) {
		return getChild(name, NXcollection.class);
	}

	@Override
	public void setCollection(String name, NXcollection collection) {
		putChild(name, collection);
	}

	@Override
	public Map<String, NXcollection> getAllCollection() {
		return getChildren(NXcollection.class);
	}

	@Override
	public void setAllCollection(Map<String, NXcollection> collection) {
		setChildren(collection);
	}

}
