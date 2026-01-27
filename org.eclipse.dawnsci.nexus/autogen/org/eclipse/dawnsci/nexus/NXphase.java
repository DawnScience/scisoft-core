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
 * Base class to describe a (thermodynamic) phase as a component of a material.
 * Instances of phases can be crystalline.
 *
 */
public interface NXphase extends NXobject {

	public static final String NX_PHASE_ID = "phase_id";
	public static final String NX_NAME = "name";
	/**
	 * Identifier for each phase.
	 * The value 0 is reserved for the unknown phase that represents the
	 * null-model (no sufficiently significant information available).
	 * In other words, the phase_name is n/a aka notIndexed.
	 * The phase_id value should match with the integer suffix of the
	 * group name which represents that instance in a NeXus/HDF5 file, i.e.
	 * if three phases were used e.g. 0, 1, and 2, three instances of
	 * :ref:`NXphase` named phase0, phase1, and phase2 should be stored
	 * in that HDF5 file.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPhase_id();

	/**
	 * Identifier for each phase.
	 * The value 0 is reserved for the unknown phase that represents the
	 * null-model (no sufficiently significant information available).
	 * In other words, the phase_name is n/a aka notIndexed.
	 * The phase_id value should match with the integer suffix of the
	 * group name which represents that instance in a NeXus/HDF5 file, i.e.
	 * if three phases were used e.g. 0, 1, and 2, three instances of
	 * :ref:`NXphase` named phase0, phase1, and phase2 should be stored
	 * in that HDF5 file.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param phase_idDataset the phase_idDataset
	 */
	public DataNode setPhase_id(IDataset phase_idDataset);

	/**
	 * Identifier for each phase.
	 * The value 0 is reserved for the unknown phase that represents the
	 * null-model (no sufficiently significant information available).
	 * In other words, the phase_name is n/a aka notIndexed.
	 * The phase_id value should match with the integer suffix of the
	 * group name which represents that instance in a NeXus/HDF5 file, i.e.
	 * if three phases were used e.g. 0, 1, and 2, three instances of
	 * :ref:`NXphase` named phase0, phase1, and phase2 should be stored
	 * in that HDF5 file.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getPhase_idScalar();

	/**
	 * Identifier for each phase.
	 * The value 0 is reserved for the unknown phase that represents the
	 * null-model (no sufficiently significant information available).
	 * In other words, the phase_name is n/a aka notIndexed.
	 * The phase_id value should match with the integer suffix of the
	 * group name which represents that instance in a NeXus/HDF5 file, i.e.
	 * if three phases were used e.g. 0, 1, and 2, three instances of
	 * :ref:`NXphase` named phase0, phase1, and phase2 should be stored
	 * in that HDF5 file.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param phase_id the phase_id
	 */
	public DataNode setPhase_idScalar(Long phase_idValue);

	/**
	 * Given name as an alias for identifying this phase.
	 * If the phase_id is 0 and one would like to use
	 * the field name, the value should be n/a or notIndexed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Given name as an alias for identifying this phase.
	 * If the phase_id is 0 and one would like to use
	 * the field name, the value should be n/a or notIndexed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name as an alias for identifying this phase.
	 * If the phase_id is 0 and one would like to use
	 * the field name, the value should be n/a or notIndexed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name as an alias for identifying this phase.
	 * If the phase_id is 0 and one would like to use
	 * the field name, the value should be n/a or notIndexed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXunit_cell getUnit_cell();

	/**
	 *
	 * @param unit_cellGroup the unit_cellGroup
	 */
	public void setUnit_cell(NXunit_cell unit_cellGroup);

	/**
	 * Get a NXunit_cell node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXunit_cell for that node.
	 */
	public NXunit_cell getUnit_cell(String name);

	/**
	 * Set a NXunit_cell node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param unit_cell the value to set
	 */
	public void setUnit_cell(String name, NXunit_cell unit_cell);

	/**
	 * Get all NXunit_cell nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXunit_cell for that node.
	 */
	public Map<String, NXunit_cell> getAllUnit_cell();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param unit_cell the child nodes to add
	 */

	public void setAllUnit_cell(Map<String, NXunit_cell> unit_cell);


	/**
	 *
	 * @return  the value.
	 */
	public NXatom getAtom();

	/**
	 *
	 * @param atomGroup the atomGroup
	 */
	public void setAtom(NXatom atomGroup);

	/**
	 * Get a NXatom node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXatom for that node.
	 */
	public NXatom getAtom(String name);

	/**
	 * Set a NXatom node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param atom the value to set
	 */
	public void setAtom(String name, NXatom atom);

	/**
	 * Get all NXatom nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXatom for that node.
	 */
	public Map<String, NXatom> getAllAtom();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param atom the child nodes to add
	 */

	public void setAllAtom(Map<String, NXatom> atom);


}
