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

/**
 * A simple pinhole.
 * For more complex geometries, :ref:`NXaperture` should be used.
 *
 */
public interface NXpinhole extends NXobject {

	public static final String NX_DEPENDS_ON = "depends_on";
	public static final String NX_DIAMETER = "diameter";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Points to the path of the last element in the geometry chain that places
	 * this object in space.
	 * When followed through that chain is supposed to end at an element depending
	 * on "." i.e. the origin of the coordinate system.
	 * If desired the location of the slit can also be described relative to
	 * an NXbeam, which will allow a simple description of a non-centred pinhole.
	 * The reference direction of the pinhole is parallel with the z axis. The reference
	 * point of the pinhole is its center in the x and y axis. The reference point on the z axis is the
	 * plane which overlaps the side of the opening of the pin hole pointing towards the source (minus on the z axis).
	 * .. image:: pinhole/pinhole.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDepends_on();

	/**
	 * Points to the path of the last element in the geometry chain that places
	 * this object in space.
	 * When followed through that chain is supposed to end at an element depending
	 * on "." i.e. the origin of the coordinate system.
	 * If desired the location of the slit can also be described relative to
	 * an NXbeam, which will allow a simple description of a non-centred pinhole.
	 * The reference direction of the pinhole is parallel with the z axis. The reference
	 * point of the pinhole is its center in the x and y axis. The reference point on the z axis is the
	 * plane which overlaps the side of the opening of the pin hole pointing towards the source (minus on the z axis).
	 * .. image:: pinhole/pinhole.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Points to the path of the last element in the geometry chain that places
	 * this object in space.
	 * When followed through that chain is supposed to end at an element depending
	 * on "." i.e. the origin of the coordinate system.
	 * If desired the location of the slit can also be described relative to
	 * an NXbeam, which will allow a simple description of a non-centred pinhole.
	 * The reference direction of the pinhole is parallel with the z axis. The reference
	 * point of the pinhole is its center in the x and y axis. The reference point on the z axis is the
	 * plane which overlaps the side of the opening of the pin hole pointing towards the source (minus on the z axis).
	 * .. image:: pinhole/pinhole.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Points to the path of the last element in the geometry chain that places
	 * this object in space.
	 * When followed through that chain is supposed to end at an element depending
	 * on "." i.e. the origin of the coordinate system.
	 * If desired the location of the slit can also be described relative to
	 * an NXbeam, which will allow a simple description of a non-centred pinhole.
	 * The reference direction of the pinhole is parallel with the z axis. The reference
	 * point of the pinhole is its center in the x and y axis. The reference point on the z axis is the
	 * plane which overlaps the side of the opening of the pin hole pointing towards the source (minus on the z axis).
	 * .. image:: pinhole/pinhole.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Size of the circular hole defining the transmitted beam size.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDiameter();

	/**
	 * Size of the circular hole defining the transmitted beam size.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param diameterDataset the diameterDataset
	 */
	public DataNode setDiameter(IDataset diameterDataset);

	/**
	 * Size of the circular hole defining the transmitted beam size.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDiameterScalar();

	/**
	 * Size of the circular hole defining the transmitted beam size.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param diameter the diameter
	 */
	public DataNode setDiameterScalar(Number diameterValue);

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 *
	 * @return  the value.
	 */
	public String getAttributeDefault();

	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 *
	 * @param defaultValue the defaultValue
	 */
	public void setAttributeDefault(String defaultValue);

}
