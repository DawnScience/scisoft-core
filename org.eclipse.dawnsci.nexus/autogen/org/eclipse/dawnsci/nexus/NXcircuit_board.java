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

package org.eclipse.dawnsci.nexus;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Circuit board with e.g. ADC and/or DAC electronic components.
 * Currently used to store the settings of the so-called magboards used in
 * Nion electron microscopes but likely this could be a useful base class for
 * substantially more use cases where details at a deep technical instrument design
 * level are relevant or important.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcircuit_board extends NXobject {

	public static final String NX_RELAY = "relay";
	/**
	 * TBD by Nion Co.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRelay();

	/**
	 * TBD by Nion Co.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param relayDataset the relayDataset
	 */
	public DataNode setRelay(IDataset relayDataset);

	/**
	 * TBD by Nion Co.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRelayScalar();

	/**
	 * TBD by Nion Co.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param relay the relay
	 */
	public DataNode setRelayScalar(Number relayValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXdac getDac();

	/**
	 *
	 * @param dacGroup the dacGroup
	 */
	public void setDac(NXdac dacGroup);

	/**
	 * Get a NXdac node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdac for that node.
	 */
	public NXdac getDac(String name);

	/**
	 * Set a NXdac node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param dac the value to set
	 */
	public void setDac(String name, NXdac dac);

	/**
	 * Get all NXdac nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdac for that node.
	 */
	public Map<String, NXdac> getAllDac();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param dac the child nodes to add
	 */

	public void setAllDac(Map<String, NXdac> dac);


	/**
	 *
	 * @return  the value.
	 */
	public NXadc getAdc();

	/**
	 *
	 * @param adcGroup the adcGroup
	 */
	public void setAdc(NXadc adcGroup);

	/**
	 * Get a NXadc node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXadc for that node.
	 */
	public NXadc getAdc(String name);

	/**
	 * Set a NXadc node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param adc the value to set
	 */
	public void setAdc(String name, NXadc adc);

	/**
	 * Get all NXadc nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXadc for that node.
	 */
	public Map<String, NXadc> getAllAdc();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param adc the child nodes to add
	 */

	public void setAllAdc(Map<String, NXadc> adc);


}
