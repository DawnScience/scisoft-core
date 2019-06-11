/*-
 *******************************************************************************
 * Copyright (c) 2015 Diamond Light Source Ltd.
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
 * Geometry description for cylindrical shapes.
 * This class can be used in place of ``NXoff_geometry`` when an exact
 * representation for cylinders is preferred.
 * For example, for Helium-tube, neutron detectors.
 * It can be used to describe the shape of any beamline component, including detectors.
 * In the case of detectors it can be used to define the shape of a single pixel, or,
 * if the pixel shapes are non-uniform, to describe the shape of the whole detector.
 * <p><b>Symbols:</b> 
 * These symbols will be used below.<ul>
 * <li><b>i</b> 
 * number of vertices required to define all cylinders in the shape</li>
 * <li><b>j</b> 
 * number of cylinders in the shape</li>
 * <li><b>k</b> 
 * number cylinders which are detectors</li></ul></p>
 * 
 */
public interface NXcylindrical_geometry extends NXobject {

	public static final String NX_VERTICES = "vertices";
	public static final String NX_CYLINDERS = "cylinders";
	public static final String NX_DETECTOR_NUMBER = "detector_number";
	/**
	 * List of x,y,z coordinates for vertices.
	 * The origin of the coordinates is the position of the parent component, for
	 * example the NXdetector which the geometry describes.
	 * If the shape describes a single pixel for a detector with uniform pixel shape
	 * then the origin is the position of each pixel as described by the
	 * ``x/y/z_pixel_offset`` datasets in ``NXdetector``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: 3;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getVertices();
	
	/**
	 * List of x,y,z coordinates for vertices.
	 * The origin of the coordinates is the position of the parent component, for
	 * example the NXdetector which the geometry describes.
	 * If the shape describes a single pixel for a detector with uniform pixel shape
	 * then the origin is the position of each pixel as described by the
	 * ``x/y/z_pixel_offset`` datasets in ``NXdetector``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: 3;
	 * </p>
	 * 
	 * @param vertices the vertices
	 */
	public DataNode setVertices(IDataset vertices);

	/**
	 * List of x,y,z coordinates for vertices.
	 * The origin of the coordinates is the position of the parent component, for
	 * example the NXdetector which the geometry describes.
	 * If the shape describes a single pixel for a detector with uniform pixel shape
	 * then the origin is the position of each pixel as described by the
	 * ``x/y/z_pixel_offset`` datasets in ``NXdetector``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: 3;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getVerticesScalar();

	/**
	 * List of x,y,z coordinates for vertices.
	 * The origin of the coordinates is the position of the parent component, for
	 * example the NXdetector which the geometry describes.
	 * If the shape describes a single pixel for a detector with uniform pixel shape
	 * then the origin is the position of each pixel as described by the
	 * ``x/y/z_pixel_offset`` datasets in ``NXdetector``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: 3;
	 * </p>
	 * 
	 * @param vertices the vertices
	 */
	public DataNode setVerticesScalar(Number vertices);

	/**
	 * List of indices of vertices in the ``vertices`` dataset to form each cylinder.
	 * Each cylinder is described by three vertices A, B, C.
	 * First vertex A lies on the cylinder axis and circular face, second point B
	 * on edge of the same face as A, and third point C at the other face and on axis.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: j; 2: 3;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getCylinders();
	
	/**
	 * List of indices of vertices in the ``vertices`` dataset to form each cylinder.
	 * Each cylinder is described by three vertices A, B, C.
	 * First vertex A lies on the cylinder axis and circular face, second point B
	 * on edge of the same face as A, and third point C at the other face and on axis.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: j; 2: 3;
	 * </p>
	 * 
	 * @param cylinders the cylinders
	 */
	public DataNode setCylinders(IDataset cylinders);

	/**
	 * List of indices of vertices in the ``vertices`` dataset to form each cylinder.
	 * Each cylinder is described by three vertices A, B, C.
	 * First vertex A lies on the cylinder axis and circular face, second point B
	 * on edge of the same face as A, and third point C at the other face and on axis.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: j; 2: 3;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getCylindersScalar();

	/**
	 * List of indices of vertices in the ``vertices`` dataset to form each cylinder.
	 * Each cylinder is described by three vertices A, B, C.
	 * First vertex A lies on the cylinder axis and circular face, second point B
	 * on edge of the same face as A, and third point C at the other face and on axis.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: j; 2: 3;
	 * </p>
	 * 
	 * @param cylinders the cylinders
	 */
	public DataNode setCylindersScalar(Long cylinders);

	/**
	 * Maps cylinders in ``cylinder``, by index, with a detector id.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: k;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDetector_number();
	
	/**
	 * Maps cylinders in ``cylinder``, by index, with a detector id.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: k;
	 * </p>
	 * 
	 * @param detector_number the detector_number
	 */
	public DataNode setDetector_number(IDataset detector_number);

	/**
	 * Maps cylinders in ``cylinder``, by index, with a detector id.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: k;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getDetector_numberScalar();

	/**
	 * Maps cylinders in ``cylinder``, by index, with a detector id.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: k;
	 * </p>
	 * 
	 * @param detector_number the detector_number
	 */
	public DataNode setDetector_numberScalar(Long detector_number);

}
