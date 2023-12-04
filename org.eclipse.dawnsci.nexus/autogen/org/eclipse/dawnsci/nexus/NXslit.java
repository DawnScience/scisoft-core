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
 * A simple slit.
 * For more complex geometries, :ref:`NXaperture` should be used.
 * 
 */
public interface NXslit extends NXobject {

	public static final String NX_DEPENDS_ON = "depends_on";
	public static final String NX_X_GAP = "x_gap";
	public static final String NX_Y_GAP = "y_gap";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Points to the path of the last element in the geometry chain that places
	 * this object in space.
	 * When followed through that chain is supposed to end at an element depending
	 * on "." i.e. the origin of the coordinate system.
	 * If desired the location of the slit can also be described relative to
	 * an NXbeam, which will allow a simple description of a non-centred slit.
	 * The reference plane of the slit is orthogonal to the z axis and includes the
	 * surface that is the entry surface of the slit. The reference point of the slit
	 * is the centre of the slit opening in the x and y axis on the reference plane.
	 * The reference point on the z axis is the reference plane.
	 * .. image:: slit/slit.png
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
	 * an NXbeam, which will allow a simple description of a non-centred slit.
	 * The reference plane of the slit is orthogonal to the z axis and includes the
	 * surface that is the entry surface of the slit. The reference point of the slit
	 * is the centre of the slit opening in the x and y axis on the reference plane.
	 * The reference point on the z axis is the reference plane.
	 * .. image:: slit/slit.png
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
	 * an NXbeam, which will allow a simple description of a non-centred slit.
	 * The reference plane of the slit is orthogonal to the z axis and includes the
	 * surface that is the entry surface of the slit. The reference point of the slit
	 * is the centre of the slit opening in the x and y axis on the reference plane.
	 * The reference point on the z axis is the reference plane.
	 * .. image:: slit/slit.png
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
	 * an NXbeam, which will allow a simple description of a non-centred slit.
	 * The reference plane of the slit is orthogonal to the z axis and includes the
	 * surface that is the entry surface of the slit. The reference point of the slit
	 * is the centre of the slit opening in the x and y axis on the reference plane.
	 * The reference point on the z axis is the reference plane.
	 * .. image:: slit/slit.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Size of the gap opening in the first dimension of the local
	 * coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getX_gap();
	
	/**
	 * Size of the gap opening in the first dimension of the local
	 * coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param x_gapDataset the x_gapDataset
	 */
	public DataNode setX_gap(IDataset x_gapDataset);

	/**
	 * Size of the gap opening in the first dimension of the local
	 * coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getX_gapScalar();

	/**
	 * Size of the gap opening in the first dimension of the local
	 * coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param x_gap the x_gap
	 */
	public DataNode setX_gapScalar(Number x_gapValue);

	/**
	 * Size of the gap opening in the second dimension of the local
	 * coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getY_gap();
	
	/**
	 * Size of the gap opening in the second dimension of the local
	 * coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param y_gapDataset the y_gapDataset
	 */
	public DataNode setY_gap(IDataset y_gapDataset);

	/**
	 * Size of the gap opening in the second dimension of the local
	 * coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getY_gapScalar();

	/**
	 * Size of the gap opening in the second dimension of the local
	 * coordinate system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param y_gap the y_gap
	 */
	public DataNode setY_gapScalar(Number y_gapValue);

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
	

}
