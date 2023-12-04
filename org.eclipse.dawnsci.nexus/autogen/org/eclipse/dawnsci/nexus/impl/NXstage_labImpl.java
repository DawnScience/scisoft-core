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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

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
public class NXstage_labImpl extends NXobjectImpl implements NXstage_lab {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_MANUFACTURER,
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_POSITIONER);

	public NXstage_labImpl() {
		super();
	}

	public NXstage_labImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXstage_lab.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_STAGE_LAB;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getDesign() {
		return getDataset(NX_DESIGN);
	}

	@Override
	public String getDesignScalar() {
		return getString(NX_DESIGN);
	}

	@Override
	public DataNode setDesign(IDataset designDataset) {
		return setDataset(NX_DESIGN, designDataset);
	}

	@Override
	public DataNode setDesignScalar(String designValue) {
		return setString(NX_DESIGN, designValue);
	}

	@Override
	public IDataset getName() {
		return getDataset(NX_NAME);
	}

	@Override
	public String getNameScalar() {
		return getString(NX_NAME);
	}

	@Override
	public DataNode setName(IDataset nameDataset) {
		return setDataset(NX_NAME, nameDataset);
	}

	@Override
	public DataNode setNameScalar(String nameValue) {
		return setString(NX_NAME, nameValue);
	}

	@Override
	public NXmanufacturer getManufacturer() {
		// dataNodeName = NX_MANUFACTURER
		return getChild("manufacturer", NXmanufacturer.class);
	}

	@Override
	public void setManufacturer(NXmanufacturer manufacturerGroup) {
		putChild("manufacturer", manufacturerGroup);
	}

	@Override
	public NXmanufacturer getManufacturer(String name) {
		return getChild(name, NXmanufacturer.class);
	}

	@Override
	public void setManufacturer(String name, NXmanufacturer manufacturer) {
		putChild(name, manufacturer);
	}

	@Override
	public Map<String, NXmanufacturer> getAllManufacturer() {
		return getChildren(NXmanufacturer.class);
	}
	
	@Override
	public void setAllManufacturer(Map<String, NXmanufacturer> manufacturer) {
		setChildren(manufacturer);
	}

	@Override
	public IDataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getTilt_1() {
		return getDataset(NX_TILT_1);
	}

	@Override
	public Double getTilt_1Scalar() {
		return getDouble(NX_TILT_1);
	}

	@Override
	public DataNode setTilt_1(IDataset tilt_1Dataset) {
		return setDataset(NX_TILT_1, tilt_1Dataset);
	}

	@Override
	public DataNode setTilt_1Scalar(Double tilt_1Value) {
		return setField(NX_TILT_1, tilt_1Value);
	}

	@Override
	public IDataset getTilt_2() {
		return getDataset(NX_TILT_2);
	}

	@Override
	public Double getTilt_2Scalar() {
		return getDouble(NX_TILT_2);
	}

	@Override
	public DataNode setTilt_2(IDataset tilt_2Dataset) {
		return setDataset(NX_TILT_2, tilt_2Dataset);
	}

	@Override
	public DataNode setTilt_2Scalar(Double tilt_2Value) {
		return setField(NX_TILT_2, tilt_2Value);
	}

	@Override
	public IDataset getRotation() {
		return getDataset(NX_ROTATION);
	}

	@Override
	public Double getRotationScalar() {
		return getDouble(NX_ROTATION);
	}

	@Override
	public DataNode setRotation(IDataset rotationDataset) {
		return setDataset(NX_ROTATION, rotationDataset);
	}

	@Override
	public DataNode setRotationScalar(Double rotationValue) {
		return setField(NX_ROTATION, rotationValue);
	}

	@Override
	public IDataset getPosition() {
		return getDataset(NX_POSITION);
	}

	@Override
	public Double getPositionScalar() {
		return getDouble(NX_POSITION);
	}

	@Override
	public DataNode setPosition(IDataset positionDataset) {
		return setDataset(NX_POSITION, positionDataset);
	}

	@Override
	public DataNode setPositionScalar(Double positionValue) {
		return setField(NX_POSITION, positionValue);
	}

	@Override
	public IDataset getBias_voltage() {
		return getDataset(NX_BIAS_VOLTAGE);
	}

	@Override
	public Double getBias_voltageScalar() {
		return getDouble(NX_BIAS_VOLTAGE);
	}

	@Override
	public DataNode setBias_voltage(IDataset bias_voltageDataset) {
		return setDataset(NX_BIAS_VOLTAGE, bias_voltageDataset);
	}

	@Override
	public DataNode setBias_voltageScalar(Double bias_voltageValue) {
		return setField(NX_BIAS_VOLTAGE, bias_voltageValue);
	}

	@Override
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}
	
	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

	@Override
	public NXpositioner getPositioner() {
		// dataNodeName = NX_POSITIONER
		return getChild("positioner", NXpositioner.class);
	}

	@Override
	public void setPositioner(NXpositioner positionerGroup) {
		putChild("positioner", positionerGroup);
	}

	@Override
	public NXpositioner getPositioner(String name) {
		return getChild(name, NXpositioner.class);
	}

	@Override
	public void setPositioner(String name, NXpositioner positioner) {
		putChild(name, positioner);
	}

	@Override
	public Map<String, NXpositioner> getAllPositioner() {
		return getChildren(NXpositioner.class);
	}
	
	@Override
	public void setAllPositioner(Map<String, NXpositioner> positioner) {
		setChildren(positioner);
	}

}
