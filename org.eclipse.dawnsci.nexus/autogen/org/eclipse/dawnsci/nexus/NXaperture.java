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
 *
 */
public interface NXaperture extends NXobject {

	public static final String NX_DEPENDS_ON = "depends_on";
	public static final String NX_MATERIAL = "material";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the aperture is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the aperture pointing towards the source.
	 * In complex (asymmetic) geometries an NXoff_geometry group can be used to provide an unambiguous reference.
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
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the aperture is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the aperture pointing towards the source.
	 * In complex (asymmetic) geometries an NXoff_geometry group can be used to provide an unambiguous reference.
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
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the aperture is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the aperture pointing towards the source.
	 * In complex (asymmetic) geometries an NXoff_geometry group can be used to provide an unambiguous reference.
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
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * The reference point of the aperture is its center in the x and y axis. The reference point on the z axis is the
	 * surface of the aperture pointing towards the source.
	 * In complex (asymmetic) geometries an NXoff_geometry group can be used to provide an unambiguous reference.
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
	 * Use this group to describe the shape of the aperture
	 *
	 * @return  the value.
	 */
	public NXoff_geometry getOff_geometry();

	/**
	 * Use this group to describe the shape of the aperture
	 *
	 * @param off_geometryGroup the off_geometryGroup
	 */
	public void setOff_geometry(NXoff_geometry off_geometryGroup);

	/**
	 * Get a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * Use this group to describe the shape of the aperture</li>
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
	 * Use this group to describe the shape of the aperture</li>
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
	 * Use this group to describe the shape of the aperture</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public Map<String, NXoff_geometry> getAllOff_geometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Use this group to describe the shape of the aperture</li>
	 * </ul>
	 *
	 * @param off_geometry the child nodes to add
	 */

	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry);


	/**
	 * location and shape of aperture
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
	 * location and shape of aperture
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
	 * location and shape of aperture
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
	 * location and shape of aperture
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
	 * location and shape of aperture
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
	 * location and shape of aperture
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
	 * describe any additional information in a note
	 *
	 * @return  the value.
	 */
	public NXnote getNote();

	/**
	 * describe any additional information in a note
	 *
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * Get a NXnote node by name:
	 * <ul>
	 * <li>
	 * describe any additional information in a note</li>
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
	 * describe any additional information in a note</li>
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
	 * describe any additional information in a note</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXnote for that node.
	 */
	public Map<String, NXnote> getAllNote();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * describe any additional information in a note</li>
	 * </ul>
	 *
	 * @param note the child nodes to add
	 */

	public void setAllNote(Map<String, NXnote> note);


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
