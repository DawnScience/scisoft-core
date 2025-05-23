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
 * Geometry (shape) description.
 * The format closely matches the Object File Format (OFF) which can be output
 * by most CAD software.
 * It can be used to describe the shape of any beamline component, including detectors.
 * In the case of detectors it can be used to define the shape of a single pixel, or,
 * if the pixel shapes are non-uniform, to describe the shape of the whole detector.
 * <p><b>Symbols:</b>
 * These symbols will be used below.<ul>
 * <li><b>i</b>
 * number of vertices in the shape</li>
 * <li><b>k</b>
 * number of faces in the shape</li>
 * <li><b>l</b>
 * number faces which are detecting surfaces or form the boundary of
 * detecting volumes</li></ul></p>
 *
 */
public interface NXoff_geometry extends NXobject {

	public static final String NX_VERTICES = "vertices";
	public static final String NX_WINDING_ORDER = "winding_order";
	public static final String NX_FACES = "faces";
	public static final String NX_DETECTOR_FACES = "detector_faces";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * List of x,y,z coordinates for vertices.
	 * The origin of the coordinates is the position of the parent component, for
	 * example the NXdetector which the geometry describes.
	 * If the shape describes a single pixel for a detector with uniform pixel
	 * shape then the origin is the position of each pixel as described by the
	 * ``x/y/z_pixel_offset`` datasets in ``NXdetector``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVertices();

	/**
	 * List of x,y,z coordinates for vertices.
	 * The origin of the coordinates is the position of the parent component, for
	 * example the NXdetector which the geometry describes.
	 * If the shape describes a single pixel for a detector with uniform pixel
	 * shape then the origin is the position of each pixel as described by the
	 * ``x/y/z_pixel_offset`` datasets in ``NXdetector``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: 3;
	 * </p>
	 *
	 * @param verticesDataset the verticesDataset
	 */
	public DataNode setVertices(IDataset verticesDataset);

	/**
	 * List of x,y,z coordinates for vertices.
	 * The origin of the coordinates is the position of the parent component, for
	 * example the NXdetector which the geometry describes.
	 * If the shape describes a single pixel for a detector with uniform pixel
	 * shape then the origin is the position of each pixel as described by the
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
	 * If the shape describes a single pixel for a detector with uniform pixel
	 * shape then the origin is the position of each pixel as described by the
	 * ``x/y/z_pixel_offset`` datasets in ``NXdetector``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: i; 2: 3;
	 * </p>
	 *
	 * @param vertices the vertices
	 */
	public DataNode setVerticesScalar(Number verticesValue);

	/**
	 * List of indices of vertices in the ``vertices`` dataset to form each face,
	 * right-hand rule for face normal.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWinding_order();

	/**
	 * List of indices of vertices in the ``vertices`` dataset to form each face,
	 * right-hand rule for face normal.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 *
	 * @param winding_orderDataset the winding_orderDataset
	 */
	public DataNode setWinding_order(IDataset winding_orderDataset);

	/**
	 * List of indices of vertices in the ``vertices`` dataset to form each face,
	 * right-hand rule for face normal.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getWinding_orderScalar();

	/**
	 * List of indices of vertices in the ``vertices`` dataset to form each face,
	 * right-hand rule for face normal.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 *
	 * @param winding_order the winding_order
	 */
	public DataNode setWinding_orderScalar(Long winding_orderValue);

	/**
	 * The start index in ``winding_order`` for each face.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: k;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFaces();

	/**
	 * The start index in ``winding_order`` for each face.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: k;
	 * </p>
	 *
	 * @param facesDataset the facesDataset
	 */
	public DataNode setFaces(IDataset facesDataset);

	/**
	 * The start index in ``winding_order`` for each face.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: k;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getFacesScalar();

	/**
	 * The start index in ``winding_order`` for each face.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: k;
	 * </p>
	 *
	 * @param faces the faces
	 */
	public DataNode setFacesScalar(Long facesValue);

	/**
	 * List of pairs of index in the "faces" dataset and detector id. Face IDs in
	 * the first column, and corresponding detector IDs in the second column.
	 * This dataset should only be used only if the ``NXoff_geometry`` group is
	 * describing a detector.
	 * Note, the face indices must be in ascending order but need not be
	 * consecutive as not every face in faces need be a detecting surface or
	 * boundary of detecting volume.
	 * Can use multiple entries with the same detector id to define detector volumes.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: l; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDetector_faces();

	/**
	 * List of pairs of index in the "faces" dataset and detector id. Face IDs in
	 * the first column, and corresponding detector IDs in the second column.
	 * This dataset should only be used only if the ``NXoff_geometry`` group is
	 * describing a detector.
	 * Note, the face indices must be in ascending order but need not be
	 * consecutive as not every face in faces need be a detecting surface or
	 * boundary of detecting volume.
	 * Can use multiple entries with the same detector id to define detector volumes.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: l; 2: 2;
	 * </p>
	 *
	 * @param detector_facesDataset the detector_facesDataset
	 */
	public DataNode setDetector_faces(IDataset detector_facesDataset);

	/**
	 * List of pairs of index in the "faces" dataset and detector id. Face IDs in
	 * the first column, and corresponding detector IDs in the second column.
	 * This dataset should only be used only if the ``NXoff_geometry`` group is
	 * describing a detector.
	 * Note, the face indices must be in ascending order but need not be
	 * consecutive as not every face in faces need be a detecting surface or
	 * boundary of detecting volume.
	 * Can use multiple entries with the same detector id to define detector volumes.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: l; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDetector_facesScalar();

	/**
	 * List of pairs of index in the "faces" dataset and detector id. Face IDs in
	 * the first column, and corresponding detector IDs in the second column.
	 * This dataset should only be used only if the ``NXoff_geometry`` group is
	 * describing a detector.
	 * Note, the face indices must be in ascending order but need not be
	 * consecutive as not every face in faces need be a detecting surface or
	 * boundary of detecting volume.
	 * Can use multiple entries with the same detector id to define detector volumes.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: l; 2: 2;
	 * </p>
	 *
	 * @param detector_faces the detector_faces
	 */
	public DataNode setDetector_facesScalar(Long detector_facesValue);

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
