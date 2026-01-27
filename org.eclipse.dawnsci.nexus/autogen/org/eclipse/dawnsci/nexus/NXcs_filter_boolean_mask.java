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
import org.eclipse.january.dataset.Dataset;

/**
 * Base class for packing and unpacking booleans.
 * The field mask should be constructed from packing a vector of booleans
 * (a bitfield) into unsigned integers with bytesize bitdepth. Padding to
 * an integer number of such integers is assumed.
 * Thereby, this base class can be used to inform software about necessary modulo
 * operations to decode the mask to recover e.g. set membership of objects in sets
 * whose membership has been encoded as a vector of booleans.
 * This is useful e.g. when processing object sets such as point cloud data.
 * If e.g. a spatial filter has been applied to a set of points, we may wish to document
 * memory-space efficiently which points were analyzed. An array of boolean values
 * is one option to achieve this. A value is true if the point is included and false otherwise.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_objs</b>
 * Number of entries (e.g. number of points or objects).</li>
 * <li><b>bitdepth</b>
 * Number of bits assumed for the container datatype used.</li>
 * <li><b>n_total</b>
 * Length of mask considering the eventual need for padding.</li></ul></p>
 *
 */
public interface NXcs_filter_boolean_mask extends NXobject {

	public static final String NX_DEPENDS_ON = "depends_on";
	public static final String NX_NUMBER_OF_OBJECTS = "number_of_objects";
	public static final String NX_BITDEPTH = "bitdepth";
	public static final String NX_MASK = "mask";
	/**
	 * Possibility to refer to which set this mask applies.
	 * If depends_on is not provided, it is assumed that the mask
	 * applies to its direct parent.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * Possibility to refer to which set this mask applies.
	 * If depends_on is not provided, it is assumed that the mask
	 * applies to its direct parent.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Possibility to refer to which set this mask applies.
	 * If depends_on is not provided, it is assumed that the mask
	 * applies to its direct parent.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Possibility to refer to which set this mask applies.
	 * If depends_on is not provided, it is assumed that the mask
	 * applies to its direct parent.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Number of objects represented by the mask.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_objects();

	/**
	 * Number of objects represented by the mask.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_objectsDataset the number_of_objectsDataset
	 */
	public DataNode setNumber_of_objects(IDataset number_of_objectsDataset);

	/**
	 * Number of objects represented by the mask.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_objectsScalar();

	/**
	 * Number of objects represented by the mask.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_objects the number_of_objects
	 */
	public DataNode setNumber_of_objectsScalar(Long number_of_objectsValue);

	/**
	 * Number of bits assumed matching on a default datatype.
	 * (e.g. 8 bits for a C-style uint8).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBitdepth();

	/**
	 * Number of bits assumed matching on a default datatype.
	 * (e.g. 8 bits for a C-style uint8).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param bitdepthDataset the bitdepthDataset
	 */
	public DataNode setBitdepth(IDataset bitdepthDataset);

	/**
	 * Number of bits assumed matching on a default datatype.
	 * (e.g. 8 bits for a C-style uint8).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getBitdepthScalar();

	/**
	 * Number of bits assumed matching on a default datatype.
	 * (e.g. 8 bits for a C-style uint8).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param bitdepth the bitdepth
	 */
	public DataNode setBitdepthScalar(Long bitdepthValue);

	/**
	 * The content of the mask. If padding is used,
	 * padding bits have to be set to 0.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMask();

	/**
	 * The content of the mask. If padding is used,
	 * padding bits have to be set to 0.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param maskDataset the maskDataset
	 */
	public DataNode setMask(IDataset maskDataset);

	/**
	 * The content of the mask. If padding is used,
	 * padding bits have to be set to 0.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getMaskScalar();

	/**
	 * The content of the mask. If padding is used,
	 * padding bits have to be set to 0.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param mask the mask
	 */
	public DataNode setMaskScalar(Long maskValue);

}
