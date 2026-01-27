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
 * Chemical composition of a sample or a set of things.

 */
public class NXchemical_compositionImpl extends NXobjectImpl implements NXchemical_composition {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_ATOM);

	public NXchemical_compositionImpl() {
		super();
	}

	public NXchemical_compositionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXchemical_composition.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CHEMICAL_COMPOSITION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getNormalization() {
		return getDataset(NX_NORMALIZATION);
	}

	@Override
	public String getNormalizationScalar() {
		return getString(NX_NORMALIZATION);
	}

	@Override
	public DataNode setNormalization(IDataset normalizationDataset) {
		return setDataset(NX_NORMALIZATION, normalizationDataset);
	}

	@Override
	public DataNode setNormalizationScalar(String normalizationValue) {
		return setString(NX_NORMALIZATION, normalizationValue);
	}

	@Override
	public Dataset getTotal() {
		return getDataset(NX_TOTAL);
	}

	@Override
	public Number getTotalScalar() {
		return getNumber(NX_TOTAL);
	}

	@Override
	public DataNode setTotal(IDataset totalDataset) {
		return setDataset(NX_TOTAL, totalDataset);
	}

	@Override
	public DataNode setTotalScalar(Number totalValue) {
		return setField(NX_TOTAL, totalValue);
	}

	@Override
	public NXatom getElement() {
		// dataNodeName = NX_ELEMENT
		return getChild("element", NXatom.class);
	}

	@Override
	public void setElement(NXatom elementGroup) {
		putChild("element", elementGroup);
	}

}
