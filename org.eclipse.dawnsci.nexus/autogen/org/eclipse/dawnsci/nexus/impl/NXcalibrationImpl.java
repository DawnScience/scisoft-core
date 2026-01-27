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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Subclass of NXprocess to describe post-processing calibrations.

 */
public class NXcalibrationImpl extends NXprocessImpl implements NXcalibration {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_PARAMETERS,
		NexusBaseClass.NX_PARAMETERS,
		NexusBaseClass.NX_DATA);

	public NXcalibrationImpl() {
		super();
	}

	public NXcalibrationImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcalibration.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CALIBRATION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDescription() {
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
	public Dataset getPhysical_quantity() {
		return getDataset(NX_PHYSICAL_QUANTITY);
	}

	@Override
	public String getPhysical_quantityScalar() {
		return getString(NX_PHYSICAL_QUANTITY);
	}

	@Override
	public DataNode setPhysical_quantity(IDataset physical_quantityDataset) {
		return setDataset(NX_PHYSICAL_QUANTITY, physical_quantityDataset);
	}

	@Override
	public DataNode setPhysical_quantityScalar(String physical_quantityValue) {
		return setString(NX_PHYSICAL_QUANTITY, physical_quantityValue);
	}

	@Override
	public Dataset getIdentifier_calibration_method() {
		return getDataset(NX_IDENTIFIER_CALIBRATION_METHOD);
	}

	@Override
	public String getIdentifier_calibration_methodScalar() {
		return getString(NX_IDENTIFIER_CALIBRATION_METHOD);
	}

	@Override
	public DataNode setIdentifier_calibration_method(IDataset identifier_calibration_methodDataset) {
		return setDataset(NX_IDENTIFIER_CALIBRATION_METHOD, identifier_calibration_methodDataset);
	}

	@Override
	public DataNode setIdentifier_calibration_methodScalar(String identifier_calibration_methodValue) {
		return setString(NX_IDENTIFIER_CALIBRATION_METHOD, identifier_calibration_methodValue);
	}

	@Override
	public Dataset getIdentifier_calibration_reference() {
		return getDataset(NX_IDENTIFIER_CALIBRATION_REFERENCE);
	}

	@Override
	public String getIdentifier_calibration_referenceScalar() {
		return getString(NX_IDENTIFIER_CALIBRATION_REFERENCE);
	}

	@Override
	public DataNode setIdentifier_calibration_reference(IDataset identifier_calibration_referenceDataset) {
		return setDataset(NX_IDENTIFIER_CALIBRATION_REFERENCE, identifier_calibration_referenceDataset);
	}

	@Override
	public DataNode setIdentifier_calibration_referenceScalar(String identifier_calibration_referenceValue) {
		return setString(NX_IDENTIFIER_CALIBRATION_REFERENCE, identifier_calibration_referenceValue);
	}

	@Override
	public NXnote getCalibration_object() {
		// dataNodeName = NX_CALIBRATION_OBJECT
		return getChild("calibration_object", NXnote.class);
	}

	@Override
	public void setCalibration_object(NXnote calibration_objectGroup) {
		putChild("calibration_object", calibration_objectGroup);
	}

	@Override
	public Dataset getLast_process() {
		return getDataset(NX_LAST_PROCESS);
	}

	@Override
	public String getLast_processScalar() {
		return getString(NX_LAST_PROCESS);
	}

	@Override
	public DataNode setLast_process(IDataset last_processDataset) {
		return setDataset(NX_LAST_PROCESS, last_processDataset);
	}

	@Override
	public DataNode setLast_processScalar(String last_processValue) {
		return setString(NX_LAST_PROCESS, last_processValue);
	}

	@Override
	public Dataset getApplied() {
		return getDataset(NX_APPLIED);
	}

	@Override
	public Boolean getAppliedScalar() {
		return getBoolean(NX_APPLIED);
	}

	@Override
	public DataNode setApplied(IDataset appliedDataset) {
		return setDataset(NX_APPLIED, appliedDataset);
	}

	@Override
	public DataNode setAppliedScalar(Boolean appliedValue) {
		return setField(NX_APPLIED, appliedValue);
	}

	@Override
	public Dataset getOriginal_axis() {
		return getDataset(NX_ORIGINAL_AXIS);
	}

	@Override
	public Double getOriginal_axisScalar() {
		return getDouble(NX_ORIGINAL_AXIS);
	}

	@Override
	public DataNode setOriginal_axis(IDataset original_axisDataset) {
		return setDataset(NX_ORIGINAL_AXIS, original_axisDataset);
	}

	@Override
	public DataNode setOriginal_axisScalar(Double original_axisValue) {
		return setField(NX_ORIGINAL_AXIS, original_axisValue);
	}

	@Override
	public String getOriginal_axisAttributeSymbol() {
		return getAttrString(NX_ORIGINAL_AXIS, NX_ORIGINAL_AXIS_ATTRIBUTE_SYMBOL);
	}

	@Override
	public void setOriginal_axisAttributeSymbol(String symbolValue) {
		setAttribute(NX_ORIGINAL_AXIS, NX_ORIGINAL_AXIS_ATTRIBUTE_SYMBOL, symbolValue);
	}

	@Override
	public String getOriginal_axisAttributeInput_path() {
		return getAttrString(NX_ORIGINAL_AXIS, NX_ORIGINAL_AXIS_ATTRIBUTE_INPUT_PATH);
	}

	@Override
	public void setOriginal_axisAttributeInput_path(String input_pathValue) {
		setAttribute(NX_ORIGINAL_AXIS, NX_ORIGINAL_AXIS_ATTRIBUTE_INPUT_PATH, input_pathValue);
	}

	@Override
	public NXparameters getFit_formula_inputs() {
		// dataNodeName = NX_FIT_FORMULA_INPUTS
		return getChild("fit_formula_inputs", NXparameters.class);
	}

	@Override
	public void setFit_formula_inputs(NXparameters fit_formula_inputsGroup) {
		putChild("fit_formula_inputs", fit_formula_inputsGroup);
	}

	@Override
	public NXparameters getCalibration_parameters() {
		// dataNodeName = NX_CALIBRATION_PARAMETERS
		return getChild("calibration_parameters", NXparameters.class);
	}

	@Override
	public void setCalibration_parameters(NXparameters calibration_parametersGroup) {
		putChild("calibration_parameters", calibration_parametersGroup);
	}

	@Override
	public Dataset getFit_formula_description() {
		return getDataset(NX_FIT_FORMULA_DESCRIPTION);
	}

	@Override
	public String getFit_formula_descriptionScalar() {
		return getString(NX_FIT_FORMULA_DESCRIPTION);
	}

	@Override
	public DataNode setFit_formula_description(IDataset fit_formula_descriptionDataset) {
		return setDataset(NX_FIT_FORMULA_DESCRIPTION, fit_formula_descriptionDataset);
	}

	@Override
	public DataNode setFit_formula_descriptionScalar(String fit_formula_descriptionValue) {
		return setString(NX_FIT_FORMULA_DESCRIPTION, fit_formula_descriptionValue);
	}

	@Override
	public Dataset getMapping_mapping() {
		return getDataset(NX_MAPPING_MAPPING);
	}

	@Override
	public Double getMapping_mappingScalar() {
		return getDouble(NX_MAPPING_MAPPING);
	}

	@Override
	public DataNode setMapping_mapping(IDataset mapping_mappingDataset) {
		return setDataset(NX_MAPPING_MAPPING, mapping_mappingDataset);
	}

	@Override
	public DataNode setMapping_mappingScalar(Double mapping_mappingValue) {
		return setField(NX_MAPPING_MAPPING, mapping_mappingValue);
	}

	@Override
	public Dataset getCalibrated_axis() {
		return getDataset(NX_CALIBRATED_AXIS);
	}

	@Override
	public Double getCalibrated_axisScalar() {
		return getDouble(NX_CALIBRATED_AXIS);
	}

	@Override
	public DataNode setCalibrated_axis(IDataset calibrated_axisDataset) {
		return setDataset(NX_CALIBRATED_AXIS, calibrated_axisDataset);
	}

	@Override
	public DataNode setCalibrated_axisScalar(Double calibrated_axisValue) {
		return setField(NX_CALIBRATED_AXIS, calibrated_axisValue);
	}

	@Override
	public NXdata getData() {
		// dataNodeName = NX_DATA
		return getChild("data", NXdata.class);
	}

	@Override
	public void setData(NXdata dataGroup) {
		putChild("data", dataGroup);
	}

	@Override
	public NXdata getData(String name) {
		return getChild(name, NXdata.class);
	}

	@Override
	public void setData(String name, NXdata data) {
		putChild(name, data);
	}

	@Override
	public Map<String, NXdata> getAllData() {
		return getChildren(NXdata.class);
	}

	@Override
	public void setAllData(Map<String, NXdata> data) {
		setChildren(data);
	}

}
