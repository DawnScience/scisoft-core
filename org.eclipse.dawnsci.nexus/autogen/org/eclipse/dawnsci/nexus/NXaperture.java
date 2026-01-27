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
 * A beamline aperture.
 * Note, the group was incorrectly documented as deprecated, but it is not and it is in common use.
 * You can specify the geometry of the aperture using either NXoff_geometry or for simpler geometry shape and size.
 *
 */
public interface NXaperture extends NXcomponent {

	public static final String NX_MATERIAL = "material";
	public static final String NX_SHAPE = "shape";
	public static final String NX_SIZE = "size";
	/**
	 * The reference point of the aperture is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the aperture pointing towards the source.
	 * In complex (asymmetric) geometries an NXoff_geometry group can be used to provide an unambiguous reference.
	 * .. image:: aperture/aperture.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * The reference point of the aperture is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the aperture pointing towards the source.
	 * In complex (asymmetric) geometries an NXoff_geometry group can be used to provide an unambiguous reference.
	 * .. image:: aperture/aperture.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * The reference point of the aperture is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the aperture pointing towards the source.
	 * In complex (asymmetric) geometries an NXoff_geometry group can be used to provide an unambiguous reference.
	 * .. image:: aperture/aperture.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * The reference point of the aperture is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the aperture pointing towards the source.
	 * In complex (asymmetric) geometries an NXoff_geometry group can be used to provide an unambiguous reference.
	 * .. image:: aperture/aperture.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Use this group to describe the shape of the aperture.
	 *
	 * @return  the value.
	 */
	public NXoff_geometry getOff_geometry();

	/**
	 * Use this group to describe the shape of the aperture.
	 *
	 * @param off_geometryGroup the off_geometryGroup
	 */
	public void setOff_geometry(NXoff_geometry off_geometryGroup);

	/**
	 * Get a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * Use this group to describe the shape of the aperture.</li>
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
	 * Use this group to describe the shape of the aperture.</li>
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
	 * Use this group to describe the shape of the aperture.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public Map<String, NXoff_geometry> getAllOff_geometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Use this group to describe the shape of the aperture.</li>
	 * </ul>
	 *
	 * @param off_geometry the child nodes to add
	 */

	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry);


	/**
	 * Stores the raw positions of aperture motors.
	 *
	 * @return  the value.
	 */
	public NXpositioner getPositioner();

	/**
	 * Stores the raw positions of aperture motors.
	 *
	 * @param positionerGroup the positionerGroup
	 */
	public void setPositioner(NXpositioner positionerGroup);

	/**
	 * Get a NXpositioner node by name:
	 * <ul>
	 * <li>
	 * Stores the raw positions of aperture motors.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXpositioner for that node.
	 */
	public NXpositioner getPositioner(String name);

	/**
	 * Set a NXpositioner node by name:
	 * <ul>
	 * <li>
	 * Stores the raw positions of aperture motors.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param positioner the value to set
	 */
	public void setPositioner(String name, NXpositioner positioner);

	/**
	 * Get all NXpositioner nodes:
	 * <ul>
	 * <li>
	 * Stores the raw positions of aperture motors.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXpositioner for that node.
	 */
	public Map<String, NXpositioner> getAllPositioner();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Stores the raw positions of aperture motors.</li>
	 * </ul>
	 *
	 * @param positioner the child nodes to add
	 */

	public void setAllPositioner(Map<String, NXpositioner> positioner);


	/**
	 * Location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the aperture and :ref:`NXoff_geometry` to describe its shape
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 * Location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the aperture and :ref:`NXoff_geometry` to describe its shape
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * Location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?</li>
	 * <li>
	 * location and shape of each blade</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the aperture and :ref:`NXoff_geometry` to describe its shape
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public NXgeometry getGeometry(String name);

	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * Location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?</li>
	 * <li>
	 * location and shape of each blade</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the aperture and :ref:`NXoff_geometry` to describe its shape
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry);

	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li>
	 * Location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?</li>
	 * <li>
	 * location and shape of each blade</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the aperture and :ref:`NXoff_geometry` to describe its shape
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?</li>
	 * <li>
	 * location and shape of each blade</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the aperture and :ref:`NXoff_geometry` to describe its shape
	 * @param geometry the child nodes to add
	 */

	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry);


	/**
	 * location and shape of each blade
	 *
	 * @deprecated Use :ref:`NXoff_geometry` instead to describe the shape of the aperture
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getBlade_geometry();

	/**
	 * location and shape of each blade
	 *
	 * @deprecated Use :ref:`NXoff_geometry` instead to describe the shape of the aperture
	 * @param blade_geometryGroup the blade_geometryGroup
	 */
	@Deprecated
	public void setBlade_geometry(NXgeometry blade_geometryGroup);

	/**
	 * Absorbing material of the aperture
	 *
	 * @return  the value.
	 */
	public Dataset getMaterial();

	/**
	 * Absorbing material of the aperture
	 *
	 * @param materialDataset the materialDataset
	 */
	public DataNode setMaterial(IDataset materialDataset);

	/**
	 * Absorbing material of the aperture
	 *
	 * @return  the value.
	 */
	public String getMaterialScalar();

	/**
	 * Absorbing material of the aperture
	 *
	 * @param material the material
	 */
	public DataNode setMaterialScalar(String materialValue);

	/**
	 * Description of aperture
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Description of aperture
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Description of aperture
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Description of aperture
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Shape of the aperture.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>straight slit</b> </li>
	 * <li><b>curved slit</b> </li>
	 * <li><b>pinhole</b> </li>
	 * <li><b>circle</b> </li>
	 * <li><b>square</b> </li>
	 * <li><b>hexagon</b> </li>
	 * <li><b>octagon</b> </li>
	 * <li><b>bladed</b> </li>
	 * <li><b>open</b> </li>
	 * <li><b>grid</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getShape();

	/**
	 * Shape of the aperture.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>straight slit</b> </li>
	 * <li><b>curved slit</b> </li>
	 * <li><b>pinhole</b> </li>
	 * <li><b>circle</b> </li>
	 * <li><b>square</b> </li>
	 * <li><b>hexagon</b> </li>
	 * <li><b>octagon</b> </li>
	 * <li><b>bladed</b> </li>
	 * <li><b>open</b> </li>
	 * <li><b>grid</b> </li></ul></p>
	 * </p>
	 *
	 * @param shapeDataset the shapeDataset
	 */
	public DataNode setShape(IDataset shapeDataset);

	/**
	 * Shape of the aperture.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>straight slit</b> </li>
	 * <li><b>curved slit</b> </li>
	 * <li><b>pinhole</b> </li>
	 * <li><b>circle</b> </li>
	 * <li><b>square</b> </li>
	 * <li><b>hexagon</b> </li>
	 * <li><b>octagon</b> </li>
	 * <li><b>bladed</b> </li>
	 * <li><b>open</b> </li>
	 * <li><b>grid</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getShapeScalar();

	/**
	 * Shape of the aperture.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>straight slit</b> </li>
	 * <li><b>curved slit</b> </li>
	 * <li><b>pinhole</b> </li>
	 * <li><b>circle</b> </li>
	 * <li><b>square</b> </li>
	 * <li><b>hexagon</b> </li>
	 * <li><b>octagon</b> </li>
	 * <li><b>bladed</b> </li>
	 * <li><b>open</b> </li>
	 * <li><b>grid</b> </li></ul></p>
	 * </p>
	 *
	 * @param shape the shape
	 */
	public DataNode setShapeScalar(String shapeValue);

	/**
	 * The relevant dimension for the aperture, i.e. slit width, pinhole and iris
	 * diameter
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSize();

	/**
	 * The relevant dimension for the aperture, i.e. slit width, pinhole and iris
	 * diameter
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param sizeDataset the sizeDataset
	 */
	public DataNode setSize(IDataset sizeDataset);

	/**
	 * The relevant dimension for the aperture, i.e. slit width, pinhole and iris
	 * diameter
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSizeScalar();

	/**
	 * The relevant dimension for the aperture, i.e. slit width, pinhole and iris
	 * diameter
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param size the size
	 */
	public DataNode setSizeScalar(Number sizeValue);

	/**
	 * Describe any additional information in a note.
	 *
	 * @return  the value.
	 */
	public NXnote getNote();

	/**
	 * Describe any additional information in a note.
	 *
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * Get a NXnote node by name:
	 * <ul>
	 * <li>
	 * Describe any additional information in a note.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXnote for that node.
	 */
	public NXnote getNote(String name);

	/**
	 * Set a NXnote node by name:
	 * <ul>
	 * <li>
	 * Describe any additional information in a note.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param note the value to set
	 */
	public void setNote(String name, NXnote note);

	/**
	 * Get all NXnote nodes:
	 * <ul>
	 * <li>
	 * Describe any additional information in a note.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXnote for that node.
	 */
	public Map<String, NXnote> getAllNote();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Describe any additional information in a note.</li>
	 * </ul>
	 *
	 * @param note the child nodes to add
	 */

	public void setAllNote(Map<String, NXnote> note);


}
