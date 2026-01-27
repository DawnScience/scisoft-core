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
 * Base class for components of an instrument - real ones or simulated ones.
 *
 */
public interface NXcomponent extends NXobject {

	public static final String NX_APPLIED = "applied";
	public static final String NX_NAME = "name";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_INPUTS = "inputs";
	public static final String NX_OUTPUTS = "outputs";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Was the component used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getApplied();

	/**
	 * Was the component used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param appliedDataset the appliedDataset
	 */
	public DataNode setApplied(IDataset appliedDataset);

	/**
	 * Was the component used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getAppliedScalar();

	/**
	 * Was the component used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param applied the applied
	 */
	public DataNode setAppliedScalar(Boolean appliedValue);

	/**
	 * Name of the component.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Name of the component.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Name of the component.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Name of the component.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Ideally, use instances of ``identifierNAME`` to point to a resource
	 * that provides further details.
	 * If such a resource does not exist or should not be used, use this free text,
	 * although it is not recommended.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Ideally, use instances of ``identifierNAME`` to point to a resource
	 * that provides further details.
	 * If such a resource does not exist or should not be used, use this free text,
	 * although it is not recommended.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Ideally, use instances of ``identifierNAME`` to point to a resource
	 * that provides further details.
	 * If such a resource does not exist or should not be used, use this free text,
	 * although it is not recommended.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Ideally, use instances of ``identifierNAME`` to point to a resource
	 * that provides further details.
	 * If such a resource does not exist or should not be used, use this free text,
	 * although it is not recommended.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Instance or list of instances of ``NXcomponent`` (or base classes
	 * extending ``NXcomponent``) or ``NXbeam`` that act as input(s) to this
	 * component.
	 * Each input should point to the path of the group acting as input.
	 * An example usage would be to chain components and/or beams together to describe
	 * the beam path in an experiment.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getInputs();

	/**
	 * Instance or list of instances of ``NXcomponent`` (or base classes
	 * extending ``NXcomponent``) or ``NXbeam`` that act as input(s) to this
	 * component.
	 * Each input should point to the path of the group acting as input.
	 * An example usage would be to chain components and/or beams together to describe
	 * the beam path in an experiment.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param inputsDataset the inputsDataset
	 */
	public DataNode setInputs(IDataset inputsDataset);

	/**
	 * Instance or list of instances of ``NXcomponent`` (or base classes
	 * extending ``NXcomponent``) or ``NXbeam`` that act as input(s) to this
	 * component.
	 * Each input should point to the path of the group acting as input.
	 * An example usage would be to chain components and/or beams together to describe
	 * the beam path in an experiment.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getInputsScalar();

	/**
	 * Instance or list of instances of ``NXcomponent`` (or base classes
	 * extending ``NXcomponent``) or ``NXbeam`` that act as input(s) to this
	 * component.
	 * Each input should point to the path of the group acting as input.
	 * An example usage would be to chain components and/or beams together to describe
	 * the beam path in an experiment.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param inputs the inputs
	 */
	public DataNode setInputsScalar(String inputsValue);

	/**
	 * Instance or list of instances of ``NXcomponent`` (or base classes
	 * extending ``NXcomponent``) or ``NXbeam`` that act as output(s) of this
	 * component.
	 * For more information, see :ref:`inputs </NXcomponent/inputs-field>`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOutputs();

	/**
	 * Instance or list of instances of ``NXcomponent`` (or base classes
	 * extending ``NXcomponent``) or ``NXbeam`` that act as output(s) of this
	 * component.
	 * For more information, see :ref:`inputs </NXcomponent/inputs-field>`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param outputsDataset the outputsDataset
	 */
	public DataNode setOutputs(IDataset outputsDataset);

	/**
	 * Instance or list of instances of ``NXcomponent`` (or base classes
	 * extending ``NXcomponent``) or ``NXbeam`` that act as output(s) of this
	 * component.
	 * For more information, see :ref:`inputs </NXcomponent/inputs-field>`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getOutputsScalar();

	/**
	 * Instance or list of instances of ``NXcomponent`` (or base classes
	 * extending ``NXcomponent``) or ``NXbeam`` that act as output(s) of this
	 * component.
	 * For more information, see :ref:`inputs </NXcomponent/inputs-field>`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param outputs the outputs
	 */
	public DataNode setOutputsScalar(String outputsValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXfabrication getFabrication();

	/**
	 *
	 * @param fabricationGroup the fabricationGroup
	 */
	public void setFabrication(NXfabrication fabricationGroup);

	/**
	 * Get a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public NXfabrication getFabrication(String name);

	/**
	 * Set a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param fabrication the value to set
	 */
	public void setFabrication(String name, NXfabrication fabrication);

	/**
	 * Get all NXfabrication nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public Map<String, NXfabrication> getAllFabrication();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param fabrication the child nodes to add
	 */

	public void setAllFabrication(Map<String, NXfabrication> fabrication);


	/**
	 *
	 * @return  the value.
	 */
	public NXprogram getProgram();

	/**
	 *
	 * @param programGroup the programGroup
	 */
	public void setProgram(NXprogram programGroup);

	/**
	 * Get a NXprogram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXprogram for that node.
	 */
	public NXprogram getProgram(String name);

	/**
	 * Set a NXprogram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param program the value to set
	 */
	public void setProgram(String name, NXprogram program);

	/**
	 * Get all NXprogram nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXprogram for that node.
	 */
	public Map<String, NXprogram> getAllProgram();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param program the child nodes to add
	 */

	public void setAllProgram(Map<String, NXprogram> program);


	/**
	 * Specifies the position of the component by pointing to the last
	 * transformation in the transformation chain that is defined
	 * via the NXtransformations group.
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * Specifies the position of the component by pointing to the last
	 * transformation in the transformation chain that is defined
	 * via the NXtransformations group.
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Specifies the position of the component by pointing to the last
	 * transformation in the transformation chain that is defined
	 * via the NXtransformations group.
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Specifies the position of the component by pointing to the last
	 * transformation in the transformation chain that is defined
	 * via the NXtransformations group.
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the component in the instrument. The dependency
	 * chain may however traverse similar groups in other component groups.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the component in the instrument. The dependency
	 * chain may however traverse similar groups in other component groups.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the component in the instrument. The dependency
	 * chain may however traverse similar groups in other component groups.</li>
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
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the component in the instrument. The dependency
	 * chain may however traverse similar groups in other component groups.</li>
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
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the component in the instrument. The dependency
	 * chain may however traverse similar groups in other component groups.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the component in the instrument. The dependency
	 * chain may however traverse similar groups in other component groups.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


}
