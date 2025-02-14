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
 * CEOS definitions/model for aberrations of electro-magnetic lenses.
 * See `S. J. Pennycock and P. D. Nellist <https://doi.org/10.1007/978-1-4419-7200-2>`_ (page 44ff, and page 118ff)
 * for different definitions available and further details. Table 7-2 of Ibid.
 * publication (page 305ff) documents how to convert from the NION to the
 * CEOS definitions.

 */
public class NXaberration_model_ceosImpl extends NXobjectImpl implements NXaberration_model_ceos {

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
		NexusBaseClass.NX_ABERRATION);

	public NXaberration_model_ceosImpl() {
		super();
	}

	public NXaberration_model_ceosImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXaberration_model_ceos.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ABERRATION_MODEL_CEOS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getModel() {
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
	public NXaberration getC_1() {
		// dataNodeName = NX_C_1
		return getChild("c_1", NXaberration.class);
	}

	@Override
	public void setC_1(NXaberration c_1Group) {
		putChild("c_1", c_1Group);
	}

	@Override
	public NXaberration getA_1() {
		// dataNodeName = NX_A_1
		return getChild("a_1", NXaberration.class);
	}

	@Override
	public void setA_1(NXaberration a_1Group) {
		putChild("a_1", a_1Group);
	}

	@Override
	public NXaberration getB_2() {
		// dataNodeName = NX_B_2
		return getChild("b_2", NXaberration.class);
	}

	@Override
	public void setB_2(NXaberration b_2Group) {
		putChild("b_2", b_2Group);
	}

	@Override
	public NXaberration getA_2() {
		// dataNodeName = NX_A_2
		return getChild("a_2", NXaberration.class);
	}

	@Override
	public void setA_2(NXaberration a_2Group) {
		putChild("a_2", a_2Group);
	}

	@Override
	public NXaberration getC_3() {
		// dataNodeName = NX_C_3
		return getChild("c_3", NXaberration.class);
	}

	@Override
	public void setC_3(NXaberration c_3Group) {
		putChild("c_3", c_3Group);
	}

	@Override
	public NXaberration getS_3() {
		// dataNodeName = NX_S_3
		return getChild("s_3", NXaberration.class);
	}

	@Override
	public void setS_3(NXaberration s_3Group) {
		putChild("s_3", s_3Group);
	}

	@Override
	public NXaberration getA_3() {
		// dataNodeName = NX_A_3
		return getChild("a_3", NXaberration.class);
	}

	@Override
	public void setA_3(NXaberration a_3Group) {
		putChild("a_3", a_3Group);
	}

	@Override
	public NXaberration getB_4() {
		// dataNodeName = NX_B_4
		return getChild("b_4", NXaberration.class);
	}

	@Override
	public void setB_4(NXaberration b_4Group) {
		putChild("b_4", b_4Group);
	}

	@Override
	public NXaberration getD_4() {
		// dataNodeName = NX_D_4
		return getChild("d_4", NXaberration.class);
	}

	@Override
	public void setD_4(NXaberration d_4Group) {
		putChild("d_4", d_4Group);
	}

	@Override
	public NXaberration getA_4() {
		// dataNodeName = NX_A_4
		return getChild("a_4", NXaberration.class);
	}

	@Override
	public void setA_4(NXaberration a_4Group) {
		putChild("a_4", a_4Group);
	}

	@Override
	public NXaberration getC_5() {
		// dataNodeName = NX_C_5
		return getChild("c_5", NXaberration.class);
	}

	@Override
	public void setC_5(NXaberration c_5Group) {
		putChild("c_5", c_5Group);
	}

	@Override
	public NXaberration getS_5() {
		// dataNodeName = NX_S_5
		return getChild("s_5", NXaberration.class);
	}

	@Override
	public void setS_5(NXaberration s_5Group) {
		putChild("s_5", s_5Group);
	}

	@Override
	public NXaberration getR_5() {
		// dataNodeName = NX_R_5
		return getChild("r_5", NXaberration.class);
	}

	@Override
	public void setR_5(NXaberration r_5Group) {
		putChild("r_5", r_5Group);
	}

	@Override
	public NXaberration getA_6() {
		// dataNodeName = NX_A_6
		return getChild("a_6", NXaberration.class);
	}

	@Override
	public void setA_6(NXaberration a_6Group) {
		putChild("a_6", a_6Group);
	}

}
