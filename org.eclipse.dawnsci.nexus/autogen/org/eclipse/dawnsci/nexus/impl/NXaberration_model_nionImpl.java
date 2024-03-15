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
 * NION definitions/model for aberrations of electro-magnetic lenses.
 * See `S. J. Pennycock and P. D. Nellist <https://doi.org/10.1007/978-1-4419-7200-2>`_ (page 44ff, and page 118ff)
 * for different definitions available and further details. Table 7-2 of Ibid.
 * publication (page 305ff) documents how to convert from the NION to the
 * CEOS definitions.

 */
public class NXaberration_model_nionImpl extends NXobjectImpl implements NXaberration_model_nion {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_ABERRATION);

	public NXaberration_model_nionImpl() {
		super();
	}

	public NXaberration_model_nionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXaberration_model_nion.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ABERRATION_MODEL_NION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getModel() {
		return getDataset(NX_MODEL);
	}

	@Override
	public String getModelScalar() {
		return getString(NX_MODEL);
	}

	@Override
	public DataNode setModel(IDataset modelDataset) {
		return setDataset(NX_MODEL, modelDataset);
	}

	@Override
	public DataNode setModelScalar(String modelValue) {
		return setString(NX_MODEL, modelValue);
	}

	@Override
	public NXaberration getC_1_0() {
		// dataNodeName = NX_C_1_0
		return getChild("c_1_0", NXaberration.class);
	}

	@Override
	public void setC_1_0(NXaberration c_1_0Group) {
		putChild("c_1_0", c_1_0Group);
	}

	@Override
	public NXaberration getC_1_2_a() {
		// dataNodeName = NX_C_1_2_A
		return getChild("c_1_2_a", NXaberration.class);
	}

	@Override
	public void setC_1_2_a(NXaberration c_1_2_aGroup) {
		putChild("c_1_2_a", c_1_2_aGroup);
	}

	@Override
	public NXaberration getC_1_2_b() {
		// dataNodeName = NX_C_1_2_B
		return getChild("c_1_2_b", NXaberration.class);
	}

	@Override
	public void setC_1_2_b(NXaberration c_1_2_bGroup) {
		putChild("c_1_2_b", c_1_2_bGroup);
	}

	@Override
	public NXaberration getC_2_1_a() {
		// dataNodeName = NX_C_2_1_A
		return getChild("c_2_1_a", NXaberration.class);
	}

	@Override
	public void setC_2_1_a(NXaberration c_2_1_aGroup) {
		putChild("c_2_1_a", c_2_1_aGroup);
	}

	@Override
	public NXaberration getC_2_1_b() {
		// dataNodeName = NX_C_2_1_B
		return getChild("c_2_1_b", NXaberration.class);
	}

	@Override
	public void setC_2_1_b(NXaberration c_2_1_bGroup) {
		putChild("c_2_1_b", c_2_1_bGroup);
	}

	@Override
	public NXaberration getC_2_3_a() {
		// dataNodeName = NX_C_2_3_A
		return getChild("c_2_3_a", NXaberration.class);
	}

	@Override
	public void setC_2_3_a(NXaberration c_2_3_aGroup) {
		putChild("c_2_3_a", c_2_3_aGroup);
	}

	@Override
	public NXaberration getC_2_3_b() {
		// dataNodeName = NX_C_2_3_B
		return getChild("c_2_3_b", NXaberration.class);
	}

	@Override
	public void setC_2_3_b(NXaberration c_2_3_bGroup) {
		putChild("c_2_3_b", c_2_3_bGroup);
	}

	@Override
	public NXaberration getC_3_0() {
		// dataNodeName = NX_C_3_0
		return getChild("c_3_0", NXaberration.class);
	}

	@Override
	public void setC_3_0(NXaberration c_3_0Group) {
		putChild("c_3_0", c_3_0Group);
	}

	@Override
	public NXaberration getC_3_2_a() {
		// dataNodeName = NX_C_3_2_A
		return getChild("c_3_2_a", NXaberration.class);
	}

	@Override
	public void setC_3_2_a(NXaberration c_3_2_aGroup) {
		putChild("c_3_2_a", c_3_2_aGroup);
	}

	@Override
	public NXaberration getC_3_2_b() {
		// dataNodeName = NX_C_3_2_B
		return getChild("c_3_2_b", NXaberration.class);
	}

	@Override
	public void setC_3_2_b(NXaberration c_3_2_bGroup) {
		putChild("c_3_2_b", c_3_2_bGroup);
	}

	@Override
	public NXaberration getC_3_4_a() {
		// dataNodeName = NX_C_3_4_A
		return getChild("c_3_4_a", NXaberration.class);
	}

	@Override
	public void setC_3_4_a(NXaberration c_3_4_aGroup) {
		putChild("c_3_4_a", c_3_4_aGroup);
	}

	@Override
	public NXaberration getC_3_4_b() {
		// dataNodeName = NX_C_3_4_B
		return getChild("c_3_4_b", NXaberration.class);
	}

	@Override
	public void setC_3_4_b(NXaberration c_3_4_bGroup) {
		putChild("c_3_4_b", c_3_4_bGroup);
	}

	@Override
	public NXaberration getC_4_1_a() {
		// dataNodeName = NX_C_4_1_A
		return getChild("c_4_1_a", NXaberration.class);
	}

	@Override
	public void setC_4_1_a(NXaberration c_4_1_aGroup) {
		putChild("c_4_1_a", c_4_1_aGroup);
	}

	@Override
	public NXaberration getC_4_1_b() {
		// dataNodeName = NX_C_4_1_B
		return getChild("c_4_1_b", NXaberration.class);
	}

	@Override
	public void setC_4_1_b(NXaberration c_4_1_bGroup) {
		putChild("c_4_1_b", c_4_1_bGroup);
	}

	@Override
	public NXaberration getC_4_3_a() {
		// dataNodeName = NX_C_4_3_A
		return getChild("c_4_3_a", NXaberration.class);
	}

	@Override
	public void setC_4_3_a(NXaberration c_4_3_aGroup) {
		putChild("c_4_3_a", c_4_3_aGroup);
	}

	@Override
	public NXaberration getC_4_3_b() {
		// dataNodeName = NX_C_4_3_B
		return getChild("c_4_3_b", NXaberration.class);
	}

	@Override
	public void setC_4_3_b(NXaberration c_4_3_bGroup) {
		putChild("c_4_3_b", c_4_3_bGroup);
	}

	@Override
	public NXaberration getC_4_5_a() {
		// dataNodeName = NX_C_4_5_A
		return getChild("c_4_5_a", NXaberration.class);
	}

	@Override
	public void setC_4_5_a(NXaberration c_4_5_aGroup) {
		putChild("c_4_5_a", c_4_5_aGroup);
	}

	@Override
	public NXaberration getC_4_5_b() {
		// dataNodeName = NX_C_4_5_B
		return getChild("c_4_5_b", NXaberration.class);
	}

	@Override
	public void setC_4_5_b(NXaberration c_4_5_bGroup) {
		putChild("c_4_5_b", c_4_5_bGroup);
	}

	@Override
	public NXaberration getC_5_0() {
		// dataNodeName = NX_C_5_0
		return getChild("c_5_0", NXaberration.class);
	}

	@Override
	public void setC_5_0(NXaberration c_5_0Group) {
		putChild("c_5_0", c_5_0Group);
	}

	@Override
	public NXaberration getC_5_2_a() {
		// dataNodeName = NX_C_5_2_A
		return getChild("c_5_2_a", NXaberration.class);
	}

	@Override
	public void setC_5_2_a(NXaberration c_5_2_aGroup) {
		putChild("c_5_2_a", c_5_2_aGroup);
	}

	@Override
	public NXaberration getC_5_2_b() {
		// dataNodeName = NX_C_5_2_B
		return getChild("c_5_2_b", NXaberration.class);
	}

	@Override
	public void setC_5_2_b(NXaberration c_5_2_bGroup) {
		putChild("c_5_2_b", c_5_2_bGroup);
	}

	@Override
	public NXaberration getC_5_4_a() {
		// dataNodeName = NX_C_5_4_A
		return getChild("c_5_4_a", NXaberration.class);
	}

	@Override
	public void setC_5_4_a(NXaberration c_5_4_aGroup) {
		putChild("c_5_4_a", c_5_4_aGroup);
	}

	@Override
	public NXaberration getC_5_4_b() {
		// dataNodeName = NX_C_5_4_B
		return getChild("c_5_4_b", NXaberration.class);
	}

	@Override
	public void setC_5_4_b(NXaberration c_5_4_bGroup) {
		putChild("c_5_4_b", c_5_4_bGroup);
	}

	@Override
	public NXaberration getC_5_6_a() {
		// dataNodeName = NX_C_5_6_A
		return getChild("c_5_6_a", NXaberration.class);
	}

	@Override
	public void setC_5_6_a(NXaberration c_5_6_aGroup) {
		putChild("c_5_6_a", c_5_6_aGroup);
	}

	@Override
	public NXaberration getC_5_6_b() {
		// dataNodeName = NX_C_5_6_B
		return getChild("c_5_6_b", NXaberration.class);
	}

	@Override
	public void setC_5_6_b(NXaberration c_5_6_bGroup) {
		putChild("c_5_6_b", c_5_6_bGroup);
	}

}
