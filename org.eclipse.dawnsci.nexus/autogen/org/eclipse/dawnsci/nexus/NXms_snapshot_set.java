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
 * A collection of spatiotemporal microstructure data.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXms_snapshot_set extends NXobject {

	public static final String NX_COMMENT = "comment";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	/**
	 * Is this set describing a measurement or a simulation?
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>measurement</b> </li>
	 * <li><b>simulation</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getComment();

	/**
	 * Is this set describing a measurement or a simulation?
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>measurement</b> </li>
	 * <li><b>simulation</b> </li></ul></p>
	 * </p>
	 *
	 * @param commentDataset the commentDataset
	 */
	public DataNode setComment(IDataset commentDataset);

	/**
	 * Is this set describing a measurement or a simulation?
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>measurement</b> </li>
	 * <li><b>simulation</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getCommentScalar();

	/**
	 * Is this set describing a measurement or a simulation?
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>measurement</b> </li>
	 * <li><b>simulation</b> </li></ul></p>
	 * </p>
	 *
	 * @param comment the comment
	 */
	public DataNode setCommentScalar(String commentValue);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * snapshots. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_offset();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * snapshots. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offsetDataset the identifier_offsetDataset
	 */
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * snapshots. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifier_offsetScalar();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * snapshots. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offset the identifier_offset
	 */
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXms_snapshot getMs_snapshot();

	/**
	 *
	 * @param ms_snapshotGroup the ms_snapshotGroup
	 */
	public void setMs_snapshot(NXms_snapshot ms_snapshotGroup);

	/**
	 * Get a NXms_snapshot node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXms_snapshot for that node.
	 */
	public NXms_snapshot getMs_snapshot(String name);

	/**
	 * Set a NXms_snapshot node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param ms_snapshot the value to set
	 */
	public void setMs_snapshot(String name, NXms_snapshot ms_snapshot);

	/**
	 * Get all NXms_snapshot nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXms_snapshot for that node.
	 */
	public Map<String, NXms_snapshot> getAllMs_snapshot();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param ms_snapshot the child nodes to add
	 */

	public void setAllMs_snapshot(Map<String, NXms_snapshot> ms_snapshot);


}
