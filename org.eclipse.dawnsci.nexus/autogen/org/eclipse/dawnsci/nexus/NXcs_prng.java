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
 * Computer science description of pseudo-random number generator.
 * The purpose of this base class is to identify if exactly the same sequence can be
 * reproduced, like for a PRNG or not, like for a true physically random source.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcs_prng extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_SEED = "seed";
	public static final String NX_WARMUP = "warmup";
	/**
	 * Physical approach or algorithm whereby random numbers are generated.
	 * Different approaches for generating random numbers with a computer exists.
	 * Some use a dedicated physical device whose the state is unpredictable
	 * physically. Some use a strategy of mangling information from the system
	 * clock. Also in this case the sequence is not reproducible without having
	 * additional pieces of information.
	 * In most cases though so-called pseudo-random number generator (PRNG)
	 * algorithms are used. These yield a deterministic sequence of practically
	 * randomly appearing numbers. These algorithms differ in their quality in
	 * how random the resulting sequences actually are, i.e. sequentially
	 * uncorrelated. Nowadays one of the most commonly used algorithm is the
	 * MersenneTwister (mt19937).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>physical</b> </li>
	 * <li><b>system_clock</b> </li>
	 * <li><b>mt19937</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Physical approach or algorithm whereby random numbers are generated.
	 * Different approaches for generating random numbers with a computer exists.
	 * Some use a dedicated physical device whose the state is unpredictable
	 * physically. Some use a strategy of mangling information from the system
	 * clock. Also in this case the sequence is not reproducible without having
	 * additional pieces of information.
	 * In most cases though so-called pseudo-random number generator (PRNG)
	 * algorithms are used. These yield a deterministic sequence of practically
	 * randomly appearing numbers. These algorithms differ in their quality in
	 * how random the resulting sequences actually are, i.e. sequentially
	 * uncorrelated. Nowadays one of the most commonly used algorithm is the
	 * MersenneTwister (mt19937).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>physical</b> </li>
	 * <li><b>system_clock</b> </li>
	 * <li><b>mt19937</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Physical approach or algorithm whereby random numbers are generated.
	 * Different approaches for generating random numbers with a computer exists.
	 * Some use a dedicated physical device whose the state is unpredictable
	 * physically. Some use a strategy of mangling information from the system
	 * clock. Also in this case the sequence is not reproducible without having
	 * additional pieces of information.
	 * In most cases though so-called pseudo-random number generator (PRNG)
	 * algorithms are used. These yield a deterministic sequence of practically
	 * randomly appearing numbers. These algorithms differ in their quality in
	 * how random the resulting sequences actually are, i.e. sequentially
	 * uncorrelated. Nowadays one of the most commonly used algorithm is the
	 * MersenneTwister (mt19937).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>physical</b> </li>
	 * <li><b>system_clock</b> </li>
	 * <li><b>mt19937</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Physical approach or algorithm whereby random numbers are generated.
	 * Different approaches for generating random numbers with a computer exists.
	 * Some use a dedicated physical device whose the state is unpredictable
	 * physically. Some use a strategy of mangling information from the system
	 * clock. Also in this case the sequence is not reproducible without having
	 * additional pieces of information.
	 * In most cases though so-called pseudo-random number generator (PRNG)
	 * algorithms are used. These yield a deterministic sequence of practically
	 * randomly appearing numbers. These algorithms differ in their quality in
	 * how random the resulting sequences actually are, i.e. sequentially
	 * uncorrelated. Nowadays one of the most commonly used algorithm is the
	 * MersenneTwister (mt19937).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>physical</b> </li>
	 * <li><b>system_clock</b> </li>
	 * <li><b>mt19937</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Name of the PRNG implementation and version. If such information is not
	 * available or if the PRNG type was set to other the DOI to the publication
	 * or the source code should be given.
	 *
	 * @return  the value.
	 */
	public NXprogram getProgram();

	/**
	 * Name of the PRNG implementation and version. If such information is not
	 * available or if the PRNG type was set to other the DOI to the publication
	 * or the source code should be given.
	 *
	 * @param programGroup the programGroup
	 */
	public void setProgram(NXprogram programGroup);

	/**
	 * Get a NXprogram node by name:
	 * <ul>
	 * <li>
	 * Name of the PRNG implementation and version. If such information is not
	 * available or if the PRNG type was set to other the DOI to the publication
	 * or the source code should be given.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXprogram for that node.
	 */
	public NXprogram getProgram(String name);

	/**
	 * Set a NXprogram node by name:
	 * <ul>
	 * <li>
	 * Name of the PRNG implementation and version. If such information is not
	 * available or if the PRNG type was set to other the DOI to the publication
	 * or the source code should be given.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param program the value to set
	 */
	public void setProgram(String name, NXprogram program);

	/**
	 * Get all NXprogram nodes:
	 * <ul>
	 * <li>
	 * Name of the PRNG implementation and version. If such information is not
	 * available or if the PRNG type was set to other the DOI to the publication
	 * or the source code should be given.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXprogram for that node.
	 */
	public Map<String, NXprogram> getAllProgram();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Name of the PRNG implementation and version. If such information is not
	 * available or if the PRNG type was set to other the DOI to the publication
	 * or the source code should be given.</li>
	 * </ul>
	 *
	 * @param program the child nodes to add
	 */

	public void setAllProgram(Map<String, NXprogram> program);


	/**
	 * Parameter of the PRNG controlling its initialization
	 * and thus controlling the specific sequence generated.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSeed();

	/**
	 * Parameter of the PRNG controlling its initialization
	 * and thus controlling the specific sequence generated.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param seedDataset the seedDataset
	 */
	public DataNode setSeed(IDataset seedDataset);

	/**
	 * Parameter of the PRNG controlling its initialization
	 * and thus controlling the specific sequence generated.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getSeedScalar();

	/**
	 * Parameter of the PRNG controlling its initialization
	 * and thus controlling the specific sequence generated.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param seed the seed
	 */
	public DataNode setSeedScalar(Long seedValue);

	/**
	 * Number of initial draws from the PRNG after its initialized with the seed.
	 * These initial draws are typically discarded in an effort to equilibrate the
	 * sequence. If no warmup was performed or if warmup procedures are unclear,
	 * users should set the value to zero.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWarmup();

	/**
	 * Number of initial draws from the PRNG after its initialized with the seed.
	 * These initial draws are typically discarded in an effort to equilibrate the
	 * sequence. If no warmup was performed or if warmup procedures are unclear,
	 * users should set the value to zero.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param warmupDataset the warmupDataset
	 */
	public DataNode setWarmup(IDataset warmupDataset);

	/**
	 * Number of initial draws from the PRNG after its initialized with the seed.
	 * These initial draws are typically discarded in an effort to equilibrate the
	 * sequence. If no warmup was performed or if warmup procedures are unclear,
	 * users should set the value to zero.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getWarmupScalar();

	/**
	 * Number of initial draws from the PRNG after its initialized with the seed.
	 * These initial draws are typically discarded in an effort to equilibrate the
	 * sequence. If no warmup was performed or if warmup procedures are unclear,
	 * users should set the value to zero.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param warmup the warmup
	 */
	public DataNode setWarmupScalar(Long warmupValue);

}
