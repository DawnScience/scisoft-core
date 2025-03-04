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
 * Details about individual orientations of a set of objects.
 * For a more detailed insight into the discussion of parameterizing
 * orientations in materials science see:
 * * https://doi.org/10.1016/j.matchar.2016.04.008
 * * https://doi.org/10.1088/0965-0393/23/8/083501
 * * https://doi.org/10.1007/978-3-662-09156-2 group-theory of rotations
 * * https://doi.org/10.1016/C2013-0-11769-2 the classical book of H.-J. Bunge
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality of the reference space/coordinate system.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of orientations.</li>
 * <li><b>n_p</b>
 * Number of parameters for the chosen parameterization.</li></ul></p>
 *
 */
public interface NXorientation_set extends NXobject {

	public static final String NX_PARAMETERIZATION = "parameterization";
	public static final String NX_OBJECTS = "objects";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	public static final String NX_ORIENTATION = "orientation";
	/**
	 * Reference to or definition of a coordinate system with
	 * which the definitions are interpretable.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Reference to or definition of a coordinate system with
	 * which the definitions are interpretable.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the definitions are interpretable.</li>
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
	 * Reference to or definition of a coordinate system with
	 * which the definitions are interpretable.</li>
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
	 * Reference to or definition of a coordinate system with
	 * which the definitions are interpretable.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the definitions are interpretable.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>bunge-euler (ZXZ)</b> </li>
	 * <li><b>quaternion</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getParameterization();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>bunge-euler (ZXZ)</b> </li>
	 * <li><b>quaternion</b> </li></ul></p>
	 * </p>
	 *
	 * @param parameterizationDataset the parameterizationDataset
	 */
	public DataNode setParameterization(IDataset parameterizationDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>bunge-euler (ZXZ)</b> </li>
	 * <li><b>quaternion</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getParameterizationScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>bunge-euler (ZXZ)</b> </li>
	 * <li><b>quaternion</b> </li></ul></p>
	 * </p>
	 *
	 * @param parameterization the parameterization
	 */
	public DataNode setParameterizationScalar(String parameterizationValue);

	/**
	 * A link or reference to the objects whose identifier are referred to in
	 * identifier to resolve which row tuple is the orientation of each object
	 * by reading orientations.
	 *
	 * @return  the value.
	 */
	public Dataset getObjects();

	/**
	 * A link or reference to the objects whose identifier are referred to in
	 * identifier to resolve which row tuple is the orientation of each object
	 * by reading orientations.
	 *
	 * @param objectsDataset the objectsDataset
	 */
	public DataNode setObjects(IDataset objectsDataset);

	/**
	 * A link or reference to the objects whose identifier are referred to in
	 * identifier to resolve which row tuple is the orientation of each object
	 * by reading orientations.
	 *
	 * @return  the value.
	 */
	public String getObjectsScalar();

	/**
	 * A link or reference to the objects whose identifier are referred to in
	 * identifier to resolve which row tuple is the orientation of each object
	 * by reading orientations.
	 *
	 * @param objects the objects
	 */
	public DataNode setObjectsScalar(String objectsValue);

	/**
	 * Integer which specifies which orientation (row of array orientation) matches
	 * to which object.e first index to be used for distinguishing
	 * hexahedra. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_offset();

	/**
	 * Integer which specifies which orientation (row of array orientation) matches
	 * to which object.e first index to be used for distinguishing
	 * hexahedra. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offsetDataset the identifier_offsetDataset
	 */
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset);

	/**
	 * Integer which specifies which orientation (row of array orientation) matches
	 * to which object.e first index to be used for distinguishing
	 * hexahedra. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifier_offsetScalar();

	/**
	 * Integer which specifies which orientation (row of array orientation) matches
	 * to which object.e first index to be used for distinguishing
	 * hexahedra. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offset the identifier_offset
	 */
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue);

	/**
	 * Integer used to distinguish how a row in orientation describes a specific
	 * object with an explicit identifier that can be queried via inspecting the
	 * list of available objects in objects.
	 * The rational behind having such a more complicated pattern is that not
	 * all objects referred when following the link in objects may still exists
	 * or are still tracked when the orientation set was characterized.
	 * This design enables to also use NXorientation_set in situations where
	 * the orientation of objects change as a function in time.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier();

	/**
	 * Integer used to distinguish how a row in orientation describes a specific
	 * object with an explicit identifier that can be queried via inspecting the
	 * list of available objects in objects.
	 * The rational behind having such a more complicated pattern is that not
	 * all objects referred when following the link in objects may still exists
	 * or are still tracked when the orientation set was characterized.
	 * This design enables to also use NXorientation_set in situations where
	 * the orientation of objects change as a function in time.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param identifierDataset the identifierDataset
	 */
	public DataNode setIdentifier(IDataset identifierDataset);

	/**
	 * Integer used to distinguish how a row in orientation describes a specific
	 * object with an explicit identifier that can be queried via inspecting the
	 * list of available objects in objects.
	 * The rational behind having such a more complicated pattern is that not
	 * all objects referred when following the link in objects may still exists
	 * or are still tracked when the orientation set was characterized.
	 * This design enables to also use NXorientation_set in situations where
	 * the orientation of objects change as a function in time.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifierScalar();

	/**
	 * Integer used to distinguish how a row in orientation describes a specific
	 * object with an explicit identifier that can be queried via inspecting the
	 * list of available objects in objects.
	 * The rational behind having such a more complicated pattern is that not
	 * all objects referred when following the link in objects may still exists
	 * or are still tracked when the orientation set was characterized.
	 * This design enables to also use NXorientation_set in situations where
	 * the orientation of objects change as a function in time.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param identifier the identifier
	 */
	public DataNode setIdentifierScalar(Long identifierValue);

	/**
	 * Parameterized orientations.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: n_p;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrientation();

	/**
	 * Parameterized orientations.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: n_p;
	 * </p>
	 *
	 * @param orientationDataset the orientationDataset
	 */
	public DataNode setOrientation(IDataset orientationDataset);

	/**
	 * Parameterized orientations.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: n_p;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOrientationScalar();

	/**
	 * Parameterized orientations.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: n_p;
	 * </p>
	 *
	 * @param orientation the orientation
	 */
	public DataNode setOrientationScalar(Number orientationValue);

}
