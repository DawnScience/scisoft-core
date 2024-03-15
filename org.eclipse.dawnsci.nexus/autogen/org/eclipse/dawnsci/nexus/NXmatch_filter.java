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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Settings of a filter to select or remove entries based on their value.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_values</b>
 * How many different match values does the filter specify.</li></ul></p>
 *
 */
public interface NXmatch_filter extends NXobject {

	public static final String NX_METHOD = "method";
	public static final String NX_MATCH = "match";
	/**
	 * Meaning of the filter:
	 * Whitelist specifies which entries with said value to include.
	 * Entries with all other values will be filtered out.
	 * Blacklist specifies which entries with said value to exclude.
	 * Entries with all other values will be included.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>whitelist</b> </li>
	 * <li><b>blacklist</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getMethod();

	/**
	 * Meaning of the filter:
	 * Whitelist specifies which entries with said value to include.
	 * Entries with all other values will be filtered out.
	 * Blacklist specifies which entries with said value to exclude.
	 * Entries with all other values will be included.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>whitelist</b> </li>
	 * <li><b>blacklist</b> </li></ul></p>
	 * </p>
	 *
	 * @param methodDataset the methodDataset
	 */
	public DataNode setMethod(IDataset methodDataset);

	/**
	 * Meaning of the filter:
	 * Whitelist specifies which entries with said value to include.
	 * Entries with all other values will be filtered out.
	 * Blacklist specifies which entries with said value to exclude.
	 * Entries with all other values will be included.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>whitelist</b> </li>
	 * <li><b>blacklist</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getMethodScalar();

	/**
	 * Meaning of the filter:
	 * Whitelist specifies which entries with said value to include.
	 * Entries with all other values will be filtered out.
	 * Blacklist specifies which entries with said value to exclude.
	 * Entries with all other values will be included.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>whitelist</b> </li>
	 * <li><b>blacklist</b> </li></ul></p>
	 * </p>
	 *
	 * @param method the method
	 */
	public DataNode setMethodScalar(String methodValue);

	/**
	 * Array of values to filter according to method. For example if the filter
	 * specifies [1, 5, 6] and method is whitelist, only entries with values
	 * matching 1, 5 or 6 will be processed. All other entries will be filtered
	 * out.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_values;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getMatch();

	/**
	 * Array of values to filter according to method. For example if the filter
	 * specifies [1, 5, 6] and method is whitelist, only entries with values
	 * matching 1, 5 or 6 will be processed. All other entries will be filtered
	 * out.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_values;
	 * </p>
	 *
	 * @param matchDataset the matchDataset
	 */
	public DataNode setMatch(IDataset matchDataset);

	/**
	 * Array of values to filter according to method. For example if the filter
	 * specifies [1, 5, 6] and method is whitelist, only entries with values
	 * matching 1, 5 or 6 will be processed. All other entries will be filtered
	 * out.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_values;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMatchScalar();

	/**
	 * Array of values to filter according to method. For example if the filter
	 * specifies [1, 5, 6] and method is whitelist, only entries with values
	 * matching 1, 5 or 6 will be processed. All other entries will be filtered
	 * out.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_values;
	 * </p>
	 *
	 * @param match the match
	 */
	public DataNode setMatchScalar(Number matchValue);

}
