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
 * A device that blocks the beam completely, usually to protect a detector.
 * Beamstops and their positions are important for SANS
 * and SAXS experiments.
 *
 */
public interface NXbeam_stop extends NXobject {

	public static final String NX_DESCRIPTION = "description";
	public static final String NX_SIZE = "size";
	public static final String NX_X = "x";
	public static final String NX_Y = "y";
	public static final String NX_DISTANCE_TO_DETECTOR = "distance_to_detector";
	public static final String NX_STATUS = "status";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * engineering shape, orientation and position of the beam stop.
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the beamstop and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 * engineering shape, orientation and position of the beam stop.
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the beamstop and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * engineering shape, orientation and position of the beam stop.</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the beamstop and NXoff_geometry to describe its shape instead
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public NXgeometry getGeometry(String name);

	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * engineering shape, orientation and position of the beam stop.</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the beamstop and NXoff_geometry to describe its shape instead
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry);

	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li>
	 * engineering shape, orientation and position of the beam stop.</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the beamstop and NXoff_geometry to describe its shape instead
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * engineering shape, orientation and position of the beam stop.</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the beamstop and NXoff_geometry to describe its shape instead
	 * @param geometry the child nodes to add
	 */

	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry);


	/**
	 * description of beamstop
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>circular</b> </li>
	 * <li><b>rectangular</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * description of beamstop
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>circular</b> </li>
	 * <li><b>rectangular</b> </li></ul></p>
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * description of beamstop
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>circular</b> </li>
	 * <li><b>rectangular</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * description of beamstop
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>circular</b> </li>
	 * <li><b>rectangular</b> </li></ul></p>
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * This group describes the shape of the beam line component
	 *
	 * @return  the value.
	 */
	public NXoff_geometry getOff_geometry();

	/**
	 * This group describes the shape of the beam line component
	 *
	 * @param off_geometryGroup the off_geometryGroup
	 */
	public void setOff_geometry(NXoff_geometry off_geometryGroup);

	/**
	 * Get a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public NXoff_geometry getOff_geometry(String name);

	/**
	 * Set a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param off_geometry the value to set
	 */
	public void setOff_geometry(String name, NXoff_geometry off_geometry);

	/**
	 * Get all NXoff_geometry nodes:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public Map<String, NXoff_geometry> getAllOff_geometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param off_geometry the child nodes to add
	 */

	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry);


	/**
	 * This group is an alternative to NXoff_geometry for describing the shape
	 * of the beam stop.
	 *
	 * @return  the value.
	 */
	public NXcylindrical_geometry getCylindrical_geometry();

	/**
	 * This group is an alternative to NXoff_geometry for describing the shape
	 * of the beam stop.
	 *
	 * @param cylindrical_geometryGroup the cylindrical_geometryGroup
	 */
	public void setCylindrical_geometry(NXcylindrical_geometry cylindrical_geometryGroup);

	/**
	 * Get a NXcylindrical_geometry node by name:
	 * <ul>
	 * <li>
	 * This group is an alternative to NXoff_geometry for describing the shape
	 * of the beam stop.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcylindrical_geometry for that node.
	 */
	public NXcylindrical_geometry getCylindrical_geometry(String name);

	/**
	 * Set a NXcylindrical_geometry node by name:
	 * <ul>
	 * <li>
	 * This group is an alternative to NXoff_geometry for describing the shape
	 * of the beam stop.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cylindrical_geometry the value to set
	 */
	public void setCylindrical_geometry(String name, NXcylindrical_geometry cylindrical_geometry);

	/**
	 * Get all NXcylindrical_geometry nodes:
	 * <ul>
	 * <li>
	 * This group is an alternative to NXoff_geometry for describing the shape
	 * of the beam stop.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcylindrical_geometry for that node.
	 */
	public Map<String, NXcylindrical_geometry> getAllCylindrical_geometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This group is an alternative to NXoff_geometry for describing the shape
	 * of the beam stop.</li>
	 * </ul>
	 *
	 * @param cylindrical_geometry the child nodes to add
	 */

	public void setAllCylindrical_geometry(Map<String, NXcylindrical_geometry> cylindrical_geometry);


	/**
	 * Size of beamstop. If this is not sufficient to describe the beam stop use
	 * NXoff_geometry instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSize();

	/**
	 * Size of beamstop. If this is not sufficient to describe the beam stop use
	 * NXoff_geometry instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param sizeDataset the sizeDataset
	 */
	public DataNode setSize(IDataset sizeDataset);

	/**
	 * Size of beamstop. If this is not sufficient to describe the beam stop use
	 * NXoff_geometry instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSizeScalar();

	/**
	 * Size of beamstop. If this is not sufficient to describe the beam stop use
	 * NXoff_geometry instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param size the size
	 */
	public DataNode setSizeScalar(Double sizeValue);

	/**
	 * x position of the beamstop in relation to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getX();

	/**
	 * x position of the beamstop in relation to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param xDataset the xDataset
	 */
	public DataNode setX(IDataset xDataset);

	/**
	 * x position of the beamstop in relation to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getXScalar();

	/**
	 * x position of the beamstop in relation to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param x the x
	 */
	public DataNode setXScalar(Double xValue);

	/**
	 * y position of the beamstop in relation to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getY();

	/**
	 * y position of the beamstop in relation to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param yDataset the yDataset
	 */
	public DataNode setY(IDataset yDataset);

	/**
	 * y position of the beamstop in relation to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getYScalar();

	/**
	 * y position of the beamstop in relation to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param y the y
	 */
	public DataNode setYScalar(Double yValue);

	/**
	 * distance of the beamstop to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDistance_to_detector();

	/**
	 * distance of the beamstop to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param distance_to_detectorDataset the distance_to_detectorDataset
	 */
	public DataNode setDistance_to_detector(IDataset distance_to_detectorDataset);

	/**
	 * distance of the beamstop to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDistance_to_detectorScalar();

	/**
	 * distance of the beamstop to the detector.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param distance_to_detector the distance_to_detector
	 */
	public DataNode setDistance_to_detectorScalar(Double distance_to_detectorValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>in</b> </li>
	 * <li><b>out</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStatus();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>in</b> </li>
	 * <li><b>out</b> </li></ul></p>
	 * </p>
	 *
	 * @param statusDataset the statusDataset
	 */
	public DataNode setStatus(IDataset statusDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>in</b> </li>
	 * <li><b>out</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getStatusScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>in</b> </li>
	 * <li><b>out</b> </li></ul></p>
	 * </p>
	 *
	 * @param status the status
	 */
	public DataNode setStatusScalar(String statusValue);

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
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the beam stop is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the beam stop pointing towards the source.
	 * .. image:: beam_stop/beam_stop.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the beam stop is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the beam stop pointing towards the source.
	 * .. image:: beam_stop/beam_stop.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the beam stop is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the beam stop pointing towards the source.
	 * .. image:: beam_stop/beam_stop.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the beam stop is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the beam stop pointing towards the source.
	 * .. image:: beam_stop/beam_stop.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

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
