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
 * Base class to describe the delocalization of point-like objects on a grid.
 * Such a procedure is for instance used in image processing and e.g. atom probe
 * microscopy (APM) to discretize a point cloud onto a grid to enable e.g.
 * computing of point density, composition, or concentration values, obtain
 * scalar fields, and compute gradients of these fields.

 */
public class NXdelocalizationImpl extends NXobjectImpl implements NXdelocalization {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXdelocalizationImpl() {
		super();
	}

	public NXdelocalizationImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdelocalization.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DELOCALIZATION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getGrid() {
		return getDataset(NX_GRID);
	}

	@Override
	public String getGridScalar() {
		return getString(NX_GRID);
	}

	@Override
	public DataNode setGrid(IDataset gridDataset) {
		return setDataset(NX_GRID, gridDataset);
	}

	@Override
	public DataNode setGridScalar(String gridValue) {
		return setString(NX_GRID, gridValue);
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
	public Dataset getWeighting_model() {
		return getDataset(NX_WEIGHTING_MODEL);
	}

	@Override
	public String getWeighting_modelScalar() {
		return getString(NX_WEIGHTING_MODEL);
	}

	@Override
	public DataNode setWeighting_model(IDataset weighting_modelDataset) {
		return setDataset(NX_WEIGHTING_MODEL, weighting_modelDataset);
	}

	@Override
	public DataNode setWeighting_modelScalar(String weighting_modelValue) {
		return setString(NX_WEIGHTING_MODEL, weighting_modelValue);
	}

	@Override
	public Dataset getElement_whitelist() {
		return getDataset(NX_ELEMENT_WHITELIST);
	}

	@Override
	public Long getElement_whitelistScalar() {
		return getLong(NX_ELEMENT_WHITELIST);
	}

	@Override
	public DataNode setElement_whitelist(IDataset element_whitelistDataset) {
		return setDataset(NX_ELEMENT_WHITELIST, element_whitelistDataset);
	}

	@Override
	public DataNode setElement_whitelistScalar(Long element_whitelistValue) {
		return setField(NX_ELEMENT_WHITELIST, element_whitelistValue);
	}

	@Override
	public Dataset getIsotope_whitelist() {
		return getDataset(NX_ISOTOPE_WHITELIST);
	}

	@Override
	public Long getIsotope_whitelistScalar() {
		return getLong(NX_ISOTOPE_WHITELIST);
	}

	@Override
	public DataNode setIsotope_whitelist(IDataset isotope_whitelistDataset) {
		return setDataset(NX_ISOTOPE_WHITELIST, isotope_whitelistDataset);
	}

	@Override
	public DataNode setIsotope_whitelistScalar(Long isotope_whitelistValue) {
		return setField(NX_ISOTOPE_WHITELIST, isotope_whitelistValue);
	}

	@Override
	public Dataset getMark() {
		return getDataset(NX_MARK);
	}

	@Override
	public Number getMarkScalar() {
		return getNumber(NX_MARK);
	}

	@Override
	public DataNode setMark(IDataset markDataset) {
		return setDataset(NX_MARK, markDataset);
	}

	@Override
	public DataNode setMarkScalar(Number markValue) {
		return setField(NX_MARK, markValue);
	}

	@Override
	public Dataset getWeight() {
		return getDataset(NX_WEIGHT);
	}

	@Override
	public Number getWeightScalar() {
		return getNumber(NX_WEIGHT);
	}

	@Override
	public DataNode setWeight(IDataset weightDataset) {
		return setDataset(NX_WEIGHT, weightDataset);
	}

	@Override
	public DataNode setWeightScalar(Number weightValue) {
		return setField(NX_WEIGHT, weightValue);
	}

}
