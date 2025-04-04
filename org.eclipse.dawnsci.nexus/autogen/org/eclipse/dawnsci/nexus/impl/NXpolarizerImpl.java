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
 * A spin polarizer.

 */
public class NXpolarizerImpl extends NXobjectImpl implements NXpolarizer {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXpolarizerImpl() {
		super();
	}

	public NXpolarizerImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpolarizer.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_POLARIZER;
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
	public Dataset getComposition() {
		return getDataset(NX_COMPOSITION);
	}

	@Override
	public String getCompositionScalar() {
		return getString(NX_COMPOSITION);
	}

	@Override
	public DataNode setComposition(IDataset compositionDataset) {
		return setDataset(NX_COMPOSITION, compositionDataset);
	}

	@Override
	public DataNode setCompositionScalar(String compositionValue) {
		return setString(NX_COMPOSITION, compositionValue);
	}

	@Override
	public Dataset getReflection() {
		return getDataset(NX_REFLECTION);
	}

	@Override
	public Long getReflectionScalar() {
		return getLong(NX_REFLECTION);
	}

	@Override
	public DataNode setReflection(IDataset reflectionDataset) {
		return setDataset(NX_REFLECTION, reflectionDataset);
	}

	@Override
	public DataNode setReflectionScalar(Long reflectionValue) {
		return setField(NX_REFLECTION, reflectionValue);
	}

	@Override
	public Dataset getEfficiency() {
		return getDataset(NX_EFFICIENCY);
	}

	@Override
	public Double getEfficiencyScalar() {
		return getDouble(NX_EFFICIENCY);
	}

	@Override
	public DataNode setEfficiency(IDataset efficiencyDataset) {
		return setDataset(NX_EFFICIENCY, efficiencyDataset);
	}

	@Override
	public DataNode setEfficiencyScalar(Double efficiencyValue) {
		return setField(NX_EFFICIENCY, efficiencyValue);
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
