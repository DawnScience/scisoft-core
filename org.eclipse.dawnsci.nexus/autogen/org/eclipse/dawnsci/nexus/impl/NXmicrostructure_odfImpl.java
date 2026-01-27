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
 * Base class to store an orientation distribution function (ODF).
 * An orientation distribution function is a probability distribution that details how
 * much volume of material has a specific orientation. An ODF is computed from
 * pole figure data in a computational process called `pole figure inversion <https://doi.org/10.1107/S0021889808030112>`_.

 */
public class NXmicrostructure_odfImpl extends NXprocessImpl implements NXmicrostructure_odf {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PARAMETERS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_DATA);

	public NXmicrostructure_odfImpl() {
		super();
	}

	public NXmicrostructure_odfImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmicrostructure_odf.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MICROSTRUCTURE_ODF;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXparameters getConfiguration() {
		// dataNodeName = NX_CONFIGURATION
		return getChild("configuration", NXparameters.class);
	}

	@Override
	public void setConfiguration(NXparameters configurationGroup) {
		putChild("configuration", configurationGroup);
	}

	@Override
	public NXprocess getCharacteristics() {
		// dataNodeName = NX_CHARACTERISTICS
		return getChild("characteristics", NXprocess.class);
	}

	@Override
	public void setCharacteristics(NXprocess characteristicsGroup) {
		putChild("characteristics", characteristicsGroup);
	}

	@Override
	public NXprocess getKth_extrema() {
		// dataNodeName = NX_KTH_EXTREMA
		return getChild("kth_extrema", NXprocess.class);
	}

	@Override
	public void setKth_extrema(NXprocess kth_extremaGroup) {
		putChild("kth_extrema", kth_extremaGroup);
	}

	@Override
	public NXprocess getSampling() {
		// dataNodeName = NX_SAMPLING
		return getChild("sampling", NXprocess.class);
	}

	@Override
	public void setSampling(NXprocess samplingGroup) {
		putChild("sampling", samplingGroup);
	}

	@Override
	public NXdata getPhi_two_plot() {
		// dataNodeName = NX_PHI_TWO_PLOT
		return getChild("phi_two_plot", NXdata.class);
	}

	@Override
	public void setPhi_two_plot(NXdata phi_two_plotGroup) {
		putChild("phi_two_plot", phi_two_plotGroup);
	}

}
