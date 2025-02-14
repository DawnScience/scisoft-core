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
 * Circuit board with e.g. ADC and/or DAC electronic components.
 * Currently used to store the settings of the so-called magboards used in
 * Nion electron microscopes but likely this could be a useful base class for
 * substantially more use cases where details at a deep technical instrument design
 * level are relevant or important.

 */
public class NXcircuit_boardImpl extends NXobjectImpl implements NXcircuit_board {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DAC,
		NexusBaseClass.NX_ADC);

	public NXcircuit_boardImpl() {
		super();
	}

	public NXcircuit_boardImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcircuit_board.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CIRCUIT_BOARD;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getRelay() {
		return getDataset(NX_RELAY);
	}

	@Override
	public Number getRelayScalar() {
		return getNumber(NX_RELAY);
	}

	@Override
	public DataNode setRelay(IDataset relayDataset) {
		return setDataset(NX_RELAY, relayDataset);
	}

	@Override
	public DataNode setRelayScalar(Number relayValue) {
		return setField(NX_RELAY, relayValue);
	}

	@Override
	public NXdac getDac() {
		// dataNodeName = NX_DAC
		return getChild("dac", NXdac.class);
	}

	@Override
	public void setDac(NXdac dacGroup) {
		putChild("dac", dacGroup);
	}

	@Override
	public NXdac getDac(String name) {
		return getChild(name, NXdac.class);
	}

	@Override
	public void setDac(String name, NXdac dac) {
		putChild(name, dac);
	}

	@Override
	public Map<String, NXdac> getAllDac() {
		return getChildren(NXdac.class);
	}

	@Override
	public void setAllDac(Map<String, NXdac> dac) {
		setChildren(dac);
	}

	@Override
	public NXadc getAdc() {
		// dataNodeName = NX_ADC
		return getChild("adc", NXadc.class);
	}

	@Override
	public void setAdc(NXadc adcGroup) {
		putChild("adc", adcGroup);
	}

	@Override
	public NXadc getAdc(String name) {
		return getChild(name, NXadc.class);
	}

	@Override
	public void setAdc(String name, NXadc adc) {
		putChild(name, adc);
	}

	@Override
	public Map<String, NXadc> getAllAdc() {
		return getChildren(NXadc.class);
	}

	@Override
	public void setAllAdc(Map<String, NXadc> adc) {
		setChildren(adc);
	}

}
