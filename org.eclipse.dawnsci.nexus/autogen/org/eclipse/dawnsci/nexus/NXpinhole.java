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
 * A simple pinhole.
 * For more complex geometries, :ref:`NXaperture` should be used.
 *
 */
public interface NXpinhole extends NXcomponent {

	public static final String NX_DIAMETER = "diameter";
	/**
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
	public Dataset getDepends_on();

	/**
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
	public Dataset getDiameter();

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

}
