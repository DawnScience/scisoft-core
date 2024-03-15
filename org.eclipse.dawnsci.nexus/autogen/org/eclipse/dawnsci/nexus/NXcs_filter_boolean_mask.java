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
 * Computer science base class for packing and unpacking booleans.
 * One use case is processing of object sets (like point cloud data).
 * When one applies e.g. a spatial filter to a set of points to define which
 * points are analyzed and which not, it is useful to document which points were
 * taken. One can store this information in a compact manner with an array of
 * boolean values. If the value is True the point is taken, else it is not.
 * If the points are identified by an array of integer identifiers and an
 * arbitrary spatial filtering, the boolean array will be filled with True and False
 * values in an arbitrary manner. Especially when the number of points is large,
 * for instance several thousands and more, some situations can be more efficiently
 * stored if one would not store the boolean array but just list the identifiers
 * of the points taken. For instance if within a set of 1000 points only one point is
 * taken, it would take (naively) 4000 bits to store the array but only 32 bits
 * to store e.g. the ID of that taken point. Of course the 4000 bit field is so
 * sparse that it could be compressed resulting also in a substantial reduction
 * of the storage demands. Therefore boolean masks are useful compact descriptions
 * to store information about set memberships in a compact manner.
 * In general it is true, though, that which representation is best, i.e.
 * most compact (especially when compressed) depends strongly on occupation of
 * the array.
 * This base class just bookkeeps metadata to inform software about necessary
 * modulo operations to decode the set membership of each object. This is useful
 * because the number of objects not necessarily is an integer multiple of the bit depth.
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

	public static final String NX_NUMBER_OF_OBJECTS = "number_of_objects";
	public static final String NX_BITDEPTH = "bitdepth";
	public static final String NX_MASK = "mask";
	public static final String NX_IDENTIFIER = "identifier";
	/**
	 * Number of objects represented by the mask.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumber_of_objects();

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
	public IDataset getBitdepth();

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
	 * The unsigned integer array representing the content of the mask.
	 * If padding is used the padded bits have to be set to 0.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getMask();

	/**
	 * The unsigned integer array representing the content of the mask.
	 * If padding is used the padded bits have to be set to 0.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @param maskDataset the maskDataset
	 */
	public DataNode setMask(IDataset maskDataset);

	/**
	 * The unsigned integer array representing the content of the mask.
	 * If padding is used the padded bits have to be set to 0.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getMaskScalar();

	/**
	 * The unsigned integer array representing the content of the mask.
	 * If padding is used the padded bits have to be set to 0.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @param mask the mask
	 */
	public DataNode setMaskScalar(Long maskValue);

	/**
	 * Link to/or array of identifiers for the objects. The decoded mask is
	 * interpreted consecutively, i.e. the first bit in the mask matches
	 * to the first identifier, the second bit in the mask to the second
	 * identifier and so on and so forth.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Dimensions:</b> 1: n_object;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIdentifier();

	/**
	 * Link to/or array of identifiers for the objects. The decoded mask is
	 * interpreted consecutively, i.e. the first bit in the mask matches
	 * to the first identifier, the second bit in the mask to the second
	 * identifier and so on and so forth.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Dimensions:</b> 1: n_object;
	 * </p>
	 *
	 * @param identifierDataset the identifierDataset
	 */
	public DataNode setIdentifier(IDataset identifierDataset);

	/**
	 * Link to/or array of identifiers for the objects. The decoded mask is
	 * interpreted consecutively, i.e. the first bit in the mask matches
	 * to the first identifier, the second bit in the mask to the second
	 * identifier and so on and so forth.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Dimensions:</b> 1: n_object;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifierScalar();

	/**
	 * Link to/or array of identifiers for the objects. The decoded mask is
	 * interpreted consecutively, i.e. the first bit in the mask matches
	 * to the first identifier, the second bit in the mask to the second
	 * identifier and so on and so forth.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Dimensions:</b> 1: n_object;
	 * </p>
	 *
	 * @param identifier the identifier
	 */
	public DataNode setIdentifierScalar(Long identifierValue);

}
