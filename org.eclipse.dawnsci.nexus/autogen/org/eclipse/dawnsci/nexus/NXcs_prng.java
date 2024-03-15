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
 * Computer science description of pseudo-random number generator.
 * The purpose of such metadata is to identify if exactly the same sequence
 * can be reproduced, like for a PRNG or not (for a true physically random source).
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcs_prng extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_PROGRAM = "program";
	public static final String NX_PROGRAM_ATTRIBUTE_VERSION = "version";
	public static final String NX_SEED = "seed";
	public static final String NX_WARMUP = "warmup";
	/**
	 * Different approaches for generating random numbers with a computer exists.
	 * Some use a dedicated physical device where the state is unpredictable (physically).
	 * Some use a mangling of the system clock (system_clock), where also without
	 * additional pieces of information the sequence is not reproducible.
	 * Some use so-called pseudo-random number generator (PRNG) are used.
	 * These are algorithms which yield a deterministic sequence of practically
	 * randomly appearing numbers. These algorithms different in their quality in
	 * how close the resulting sequences are random.
	 * Nowadays one of the most commonly used algorithm is
	 * the MersenneTwister (mt19937).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>physical</b> </li>
	 * <li><b>system_clock</b> </li>
	 * <li><b>mt19937</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getType();

	/**
	 * Different approaches for generating random numbers with a computer exists.
	 * Some use a dedicated physical device where the state is unpredictable (physically).
	 * Some use a mangling of the system clock (system_clock), where also without
	 * additional pieces of information the sequence is not reproducible.
	 * Some use so-called pseudo-random number generator (PRNG) are used.
	 * These are algorithms which yield a deterministic sequence of practically
	 * randomly appearing numbers. These algorithms different in their quality in
	 * how close the resulting sequences are random.
	 * Nowadays one of the most commonly used algorithm is
	 * the MersenneTwister (mt19937).
	 * <p>
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
	 * Different approaches for generating random numbers with a computer exists.
	 * Some use a dedicated physical device where the state is unpredictable (physically).
	 * Some use a mangling of the system clock (system_clock), where also without
	 * additional pieces of information the sequence is not reproducible.
	 * Some use so-called pseudo-random number generator (PRNG) are used.
	 * These are algorithms which yield a deterministic sequence of practically
	 * randomly appearing numbers. These algorithms different in their quality in
	 * how close the resulting sequences are random.
	 * Nowadays one of the most commonly used algorithm is
	 * the MersenneTwister (mt19937).
	 * <p>
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
	 * Different approaches for generating random numbers with a computer exists.
	 * Some use a dedicated physical device where the state is unpredictable (physically).
	 * Some use a mangling of the system clock (system_clock), where also without
	 * additional pieces of information the sequence is not reproducible.
	 * Some use so-called pseudo-random number generator (PRNG) are used.
	 * These are algorithms which yield a deterministic sequence of practically
	 * randomly appearing numbers. These algorithms different in their quality in
	 * how close the resulting sequences are random.
	 * Nowadays one of the most commonly used algorithm is
	 * the MersenneTwister (mt19937).
	 * <p>
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
	public IDataset getProgram();

	/**
	 * Name of the PRNG implementation and version. If such information is not
	 * available or if the PRNG type was set to other the DOI to the publication
	 * or the source code should be given.
	 *
	 * @param programDataset the programDataset
	 */
	public DataNode setProgram(IDataset programDataset);

	/**
	 * Name of the PRNG implementation and version. If such information is not
	 * available or if the PRNG type was set to other the DOI to the publication
	 * or the source code should be given.
	 *
	 * @return  the value.
	 */
	public String getProgramScalar();

	/**
	 * Name of the PRNG implementation and version. If such information is not
	 * available or if the PRNG type was set to other the DOI to the publication
	 * or the source code should be given.
	 *
	 * @param program the program
	 */
	public DataNode setProgramScalar(String programValue);

	/**
	 * Version and build number, or commit hash.
	 *
	 * @return  the value.
	 */
	public String getProgramAttributeVersion();

	/**
	 * Version and build number, or commit hash.
	 *
	 * @param versionValue the versionValue
	 */
	public void setProgramAttributeVersion(String versionValue);

	/**
	 * Parameter of the PRNG controlling its initialization and thus the specific
	 * sequence of numbers it generates.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSeed();

	/**
	 * Parameter of the PRNG controlling its initialization and thus the specific
	 * sequence of numbers it generates.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param seedDataset the seedDataset
	 */
	public DataNode setSeed(IDataset seedDataset);

	/**
	 * Parameter of the PRNG controlling its initialization and thus the specific
	 * sequence of numbers it generates.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSeedScalar();

	/**
	 * Parameter of the PRNG controlling its initialization and thus the specific
	 * sequence of numbers it generates.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param seed the seed
	 */
	public DataNode setSeedScalar(Number seedValue);

	/**
	 * Number of initial draws from the PRNG which are discarded in an effort
	 * to equilibrate the sequence and make it thus to statistically more random.
	 * If no warmup was performed or if warmup procedures are unclear,
	 * users should set the value to zero.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWarmup();

	/**
	 * Number of initial draws from the PRNG which are discarded in an effort
	 * to equilibrate the sequence and make it thus to statistically more random.
	 * If no warmup was performed or if warmup procedures are unclear,
	 * users should set the value to zero.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param warmupDataset the warmupDataset
	 */
	public DataNode setWarmup(IDataset warmupDataset);

	/**
	 * Number of initial draws from the PRNG which are discarded in an effort
	 * to equilibrate the sequence and make it thus to statistically more random.
	 * If no warmup was performed or if warmup procedures are unclear,
	 * users should set the value to zero.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getWarmupScalar();

	/**
	 * Number of initial draws from the PRNG which are discarded in an effort
	 * to equilibrate the sequence and make it thus to statistically more random.
	 * If no warmup was performed or if warmup procedures are unclear,
	 * users should set the value to zero.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param warmup the warmup
	 */
	public DataNode setWarmupScalar(Number warmupValue);

}
