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
 * A stage lab can be used to hold, align, orient, and prepare a specimen.
 * Modern stages are multi-functional devices. Many of which offer a controlled
 * environment around (a part) of the specimen. Stages enable experimentalists
 * to apply stimuli. A stage_lab is a multi-purpose/-functional tools which
 * can have multiple actuators, sensors, and other components.
 * With such stages comes the need for storing various (meta)data
 * that are generated while manipulating the sample.
 * Modern stages realize a hierarchy of components: For example the specimen
 * might be mounted on a multi-axial tilt rotation holder. This holder is
 * fixed in the support unit which connects the holder to the rest of the
 * microscope.
 * In other examples, taken from atom probe microscopy, researchers may work
 * with wire samples which are clipped into a larger fixing unit for
 * convenience and enable for a more careful specimen handling.
 * This fixture unit is known in atom probe jargon as a stub.
 * Stubs in turn are positioned onto pucks.
 * Pucks are then loaded onto carousels.
 * A carousel is a carrier unit with which eventually entire sets of specimens
 * can be moved in between parts of the microscope.
 * An NXstage_lab instance reflects this hierarchical design. The stage is the
 * root of the hierarchy. A stage carries the holder.
 * In the case that it is not practical to distinguish these two layers,
 * the holder should be given preference.
 * Some examples for stage_labs in applications:
 * * A nanoparticle on a copper grid. The copper grid is the holder.
 * The grid itself is fixed to the stage.
 * * An atom probe specimen fixed in a stub. In this case the stub can be
 * considered the holder, while the cryostat temperature control unit is
 * a component of the stage.
 * * Samples with arrays of specimens, like a microtip on a microtip array
 * is an example of a three-layer hierarchy commonly employed for
 * efficient sequential processing of atom probe experiments.
 * * With one entry of an application definition only one microtip should be
 * described. Therefore, the microtip is the specimen,
 * the array is the holder and the remaining mounting unit
 * that is attached to the cryo-controller is the stage.
 * * For in-situ experiments with e.g. chips with read-out electronics
 * as actuators, the chips are again placed in a larger unit.
 * * Other examples are (quasi) in-situ experiments where experimentalists
 * anneal or deform the specimen via e.g. in-situ tensile testing machines
 * which are mounted on the specimen holder.
 * To cover for an as flexible design of complex stages, users should nest
 * multiple instances of NXstage_lab objects according to their needs to reflect
 * the differences between what they consider as the holder and what
 * they consider is the stage.
 * Instances should be named with integers starting from 1 as the top level unit.
 * In the microtip example stage_lab_1 for the stage, stage_lab_2 for the holder
 * (microtip array), stage_lab_3 for the microtip specimen, respectively.
 * The depends_on keyword should be used with relative or absolute naming inside
 * the file to specify how different stage_lab instances build a hierarchy
 * if this is not obvious from numbered identifiers like the stage_lab_1 to
 * stage_lab 3 example. The lower it is the number the higher it is the
 * rank in the hierarchy.
 * For specific details and inspiration about stages in electron microscopes:
 * * `Holders with multiple axes <https://www.nanotechnik.com/e5as.html>`_
 * * `Chip-based designs <https://www.protochips.com/products/fusion/fusion-select-components/>`_
 * * `Further chip-based designs <https://www.nanoprobetech.com/about>`_
 * * `Stages in transmission electron microscopy <https://doi.org/10.1007/978-3-662-14824-2>`_ (page 103, table 4.2)
 * * `Further stages in transmission electron microscopy <https://doi.org/10.1007/978-1-4757-2519-3>`_ (page 124ff)
 * * `Specimens in atom probe <https://doi.org/10.1007/978-1-4614-8721-0>`_ (page 47ff)
 * * `Exemplar micro-manipulators <https://nano.oxinst.com/products/omniprobe/omniprobe-200>`_
 *
 */
public interface NXstage_lab extends NXobject {

	public static final String NX_DESIGN = "design";
	public static final String NX_NAME = "name";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_TILT_1 = "tilt_1";
	public static final String NX_TILT_2 = "tilt_2";
	public static final String NX_ROTATION = "rotation";
	public static final String NX_POSITION = "position";
	public static final String NX_BIAS_VOLTAGE = "bias_voltage";
	/**
	 * Principal design of the stage.
	 * Exemplar terms could be side_entry, top_entry,
	 * single_tilt, quick_change, multiple_specimen,
	 * bulk_specimen, double_tilt, tilt_rotate,
	 * heating_chip, atmosphere_chip,
	 * electrical_biasing_chip, liquid_cell_chip
	 *
	 * @return  the value.
	 */
	public IDataset getDesign();

	/**
	 * Principal design of the stage.
	 * Exemplar terms could be side_entry, top_entry,
	 * single_tilt, quick_change, multiple_specimen,
	 * bulk_specimen, double_tilt, tilt_rotate,
	 * heating_chip, atmosphere_chip,
	 * electrical_biasing_chip, liquid_cell_chip
	 *
	 * @param designDataset the designDataset
	 */
	public DataNode setDesign(IDataset designDataset);

	/**
	 * Principal design of the stage.
	 * Exemplar terms could be side_entry, top_entry,
	 * single_tilt, quick_change, multiple_specimen,
	 * bulk_specimen, double_tilt, tilt_rotate,
	 * heating_chip, atmosphere_chip,
	 * electrical_biasing_chip, liquid_cell_chip
	 *
	 * @return  the value.
	 */
	public String getDesignScalar();

	/**
	 * Principal design of the stage.
	 * Exemplar terms could be side_entry, top_entry,
	 * single_tilt, quick_change, multiple_specimen,
	 * bulk_specimen, double_tilt, tilt_rotate,
	 * heating_chip, atmosphere_chip,
	 * electrical_biasing_chip, liquid_cell_chip
	 *
	 * @param design the design
	 */
	public DataNode setDesignScalar(String designValue);

	/**
	 * Given name/alias for the components making the stage.
	 *
	 * @return  the value.
	 */
	public IDataset getName();

	/**
	 * Given name/alias for the components making the stage.
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name/alias for the components making the stage.
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name/alias for the components making the stage.
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

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
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details.
	 *
	 * @return  the value.
	 */
	public IDataset getDescription();

	/**
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTilt_1();

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param tilt_1Dataset the tilt_1Dataset
	 */
	public DataNode setTilt_1(IDataset tilt_1Dataset);

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTilt_1Scalar();

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param tilt_1 the tilt_1
	 */
	public DataNode setTilt_1Scalar(Double tilt_1Value);

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTilt_2();

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param tilt_2Dataset the tilt_2Dataset
	 */
	public DataNode setTilt_2(IDataset tilt_2Dataset);

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTilt_2Scalar();

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param tilt_2 the tilt_2
	 */
	public DataNode setTilt_2Scalar(Double tilt_2Value);

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getRotation();

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param rotationDataset the rotationDataset
	 */
	public DataNode setRotation(IDataset rotationDataset);

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRotationScalar();

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param rotation the rotation
	 */
	public DataNode setRotationScalar(Double rotationValue);

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPosition();

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param positionDataset the positionDataset
	 */
	public DataNode setPosition(IDataset positionDataset);

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPositionScalar();

	/**
	 * Should be defined by the application definition.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param position the position
	 */
	public DataNode setPositionScalar(Double positionValue);

	/**
	 * Voltage applied to the stage to decelerate electrons.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getBias_voltage();

	/**
	 * Voltage applied to the stage to decelerate electrons.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param bias_voltageDataset the bias_voltageDataset
	 */
	public DataNode setBias_voltage(IDataset bias_voltageDataset);

	/**
	 * Voltage applied to the stage to decelerate electrons.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBias_voltageScalar();

	/**
	 * Voltage applied to the stage to decelerate electrons.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param bias_voltage the bias_voltage
	 */
	public DataNode setBias_voltageScalar(Double bias_voltageValue);

	/**
	 * The rotation, tilt and position of stage components can be specified
	 * either via NXtransformations or via the tilt_1, tilt_2, rotation,
	 * and position fields.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * The rotation, tilt and position of stage components can be specified
	 * either via NXtransformations or via the tilt_1, tilt_2, rotation,
	 * and position fields.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * The rotation, tilt and position of stage components can be specified
	 * either via NXtransformations or via the tilt_1, tilt_2, rotation,
	 * and position fields.</li>
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
	 * The rotation, tilt and position of stage components can be specified
	 * either via NXtransformations or via the tilt_1, tilt_2, rotation,
	 * and position fields.</li>
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
	 * The rotation, tilt and position of stage components can be specified
	 * either via NXtransformations or via the tilt_1, tilt_2, rotation,
	 * and position fields.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * The rotation, tilt and position of stage components can be specified
	 * either via NXtransformations or via the tilt_1, tilt_2, rotation,
	 * and position fields.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


	/**
	 *
	 * @return  the value.
	 */
	public NXpositioner getPositioner();

	/**
	 *
	 * @param positionerGroup the positionerGroup
	 */
	public void setPositioner(NXpositioner positionerGroup);

	/**
	 * Get a NXpositioner node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXpositioner for that node.
	 */
	public NXpositioner getPositioner(String name);

	/**
	 * Set a NXpositioner node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param positioner the value to set
	 */
	public void setPositioner(String name, NXpositioner positioner);

	/**
	 * Get all NXpositioner nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXpositioner for that node.
	 */
	public Map<String, NXpositioner> getAllPositioner();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param positioner the child nodes to add
	 */

	public void setAllPositioner(Map<String, NXpositioner> positioner);


}
