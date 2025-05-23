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
 * A spin flipper.

 */
public class NXflipperImpl extends NXobjectImpl implements NXflipper {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXflipperImpl() {
		super();
	}

	public NXflipperImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXflipper.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_FLIPPER;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public Dataset getFlip_turns() {
		return getDataset(NX_FLIP_TURNS);
	}

	@Override
	public Double getFlip_turnsScalar() {
		return getDouble(NX_FLIP_TURNS);
	}

	@Override
	public DataNode setFlip_turns(IDataset flip_turnsDataset) {
		return setDataset(NX_FLIP_TURNS, flip_turnsDataset);
	}

	@Override
	public DataNode setFlip_turnsScalar(Double flip_turnsValue) {
		return setField(NX_FLIP_TURNS, flip_turnsValue);
	}

	@Override
	public Dataset getComp_turns() {
		return getDataset(NX_COMP_TURNS);
	}

	@Override
	public Double getComp_turnsScalar() {
		return getDouble(NX_COMP_TURNS);
	}

	@Override
	public DataNode setComp_turns(IDataset comp_turnsDataset) {
		return setDataset(NX_COMP_TURNS, comp_turnsDataset);
	}

	@Override
	public DataNode setComp_turnsScalar(Double comp_turnsValue) {
		return setField(NX_COMP_TURNS, comp_turnsValue);
	}

	@Override
	public Dataset getGuide_turns() {
		return getDataset(NX_GUIDE_TURNS);
	}

	@Override
	public Double getGuide_turnsScalar() {
		return getDouble(NX_GUIDE_TURNS);
	}

	@Override
	public DataNode setGuide_turns(IDataset guide_turnsDataset) {
		return setDataset(NX_GUIDE_TURNS, guide_turnsDataset);
	}

	@Override
	public DataNode setGuide_turnsScalar(Double guide_turnsValue) {
		return setField(NX_GUIDE_TURNS, guide_turnsValue);
	}

	@Override
	public Dataset getFlip_current() {
		return getDataset(NX_FLIP_CURRENT);
	}

	@Override
	public Double getFlip_currentScalar() {
		return getDouble(NX_FLIP_CURRENT);
	}

	@Override
	public DataNode setFlip_current(IDataset flip_currentDataset) {
		return setDataset(NX_FLIP_CURRENT, flip_currentDataset);
	}

	@Override
	public DataNode setFlip_currentScalar(Double flip_currentValue) {
		return setField(NX_FLIP_CURRENT, flip_currentValue);
	}

	@Override
	public Dataset getComp_current() {
		return getDataset(NX_COMP_CURRENT);
	}

	@Override
	public Double getComp_currentScalar() {
		return getDouble(NX_COMP_CURRENT);
	}

	@Override
	public DataNode setComp_current(IDataset comp_currentDataset) {
		return setDataset(NX_COMP_CURRENT, comp_currentDataset);
	}

	@Override
	public DataNode setComp_currentScalar(Double comp_currentValue) {
		return setField(NX_COMP_CURRENT, comp_currentValue);
	}

	@Override
	public Dataset getGuide_current() {
		return getDataset(NX_GUIDE_CURRENT);
	}

	@Override
	public Double getGuide_currentScalar() {
		return getDouble(NX_GUIDE_CURRENT);
	}

	@Override
	public DataNode setGuide_current(IDataset guide_currentDataset) {
		return setDataset(NX_GUIDE_CURRENT, guide_currentDataset);
	}

	@Override
	public DataNode setGuide_currentScalar(Double guide_currentValue) {
		return setField(NX_GUIDE_CURRENT, guide_currentValue);
	}

	@Override
	public Dataset getThickness() {
		return getDataset(NX_THICKNESS);
	}

	@Override
	public Double getThicknessScalar() {
		return getDouble(NX_THICKNESS);
	}

	@Override
	public DataNode setThickness(IDataset thicknessDataset) {
		return setDataset(NX_THICKNESS, thicknessDataset);
	}

	@Override
	public DataNode setThicknessScalar(Double thicknessValue) {
		return setField(NX_THICKNESS, thicknessValue);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
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

}
