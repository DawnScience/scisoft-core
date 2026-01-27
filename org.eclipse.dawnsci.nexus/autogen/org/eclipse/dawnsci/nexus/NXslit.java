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
 * A simple slit.
 * For more complex geometries, :ref:`NXaperture` should be used.
 *
 */
public interface NXslit extends NXcomponent {

	public static final String NX_X_GAP = "x_gap";
	public static final String NX_Y_GAP = "y_gap";
	/**
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
	public Dataset getDepends_on();

	/**
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
	public Dataset getX_gap();

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
	public Dataset getY_gap();

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

}
