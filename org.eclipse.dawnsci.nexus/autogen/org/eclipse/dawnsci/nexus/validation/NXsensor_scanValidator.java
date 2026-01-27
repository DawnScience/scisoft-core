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

package org.eclipse.dawnsci.nexus.validation;

import static org.eclipse.dawnsci.nexus.validation.NexusDataType.*;
import static org.eclipse.dawnsci.nexus.validation.NexusUnitCategory.*;

import java.util.Map;

import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXenvironment;
import org.eclipse.dawnsci.nexus.NXsensor;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXpid_controller;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXhistory;

/**
 * Validator for the application definition 'NXsensor_scan'.
 */
public class NXsensor_scanValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXsensor_scanValidator() {
		super(NexusApplicationDefinition.NX_SENSOR_SCAN);
	}

	@Override
	public ValidationReport validate(NXroot root) {
		// validate unnamed child group of type NXentry (possibly multiple)
		validateUnnamedGroupOccurrences(root, NXentry.class, false, true);
		final Map<String, NXentry> allEntry = root.getAllEntry();
		for (final NXentry entry : allEntry.values()) {
			validateGroup_NXentry(entry);
		}
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXentry entry) {
		validateGroup_NXentry(entry);
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXsubentry subentry) {
		validateGroup_NXentry(subentry);
		return validationReport;
	}


	/**
	 * Validate unnamed group of type NXentry.
	 */
	private void validateGroup_NXentry(final NXsubentry group) {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXentry.class, group))) return;

		// validate attribute 'default' of type NX_CHAR.
		final Attribute default_attr = group.getAttribute("default");
		if (!(validateAttributeNotNull("default", default_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("default", default_attr, NX_CHAR);

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXsensor_scan");
		// validate attribute 'version' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (!(validateAttributeNotNull("version", definition_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", definition_attr_version, NX_CHAR);

		}

		// validate optional field 'identifier_experiment' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier_experiment = group.getLazyDataset("identifier_experiment");
				if (identifier_experiment != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_experiment", identifier_experiment, NX_CHAR);
		}

		// validate optional field 'identifier_collection' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier_collection = group.getLazyDataset("identifier_collection");
				if (identifier_collection != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_collection", identifier_collection, NX_CHAR);
		}

		// validate optional field 'experiment_description' of type NX_CHAR.
		final ILazyDataset experiment_description = group.getLazyDataset("experiment_description");
				if (experiment_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_description", experiment_description, NX_CHAR);
		}

		// validate optional field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
				if (start_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start_time", start_time, NX_DATE_TIME);
			validateFieldUnits("start_time", group.getDataNode("start_time"), NX_TIME);
		}

		// validate optional field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
				if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
			validateFieldUnits("end_time", group.getDataNode("end_time"), NX_TIME);
		}

		// validate unnamed child group of type NXprocess (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXprocess.class, false, true);
		final Map<String, NXprocess> allProcess = group.getAllProcess();
		for (final NXprocess process : allProcess.values()) {
			validateGroup_NXentry_NXprocess(process);
		}

		// validate unnamed child group of type NXuser (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXuser.class, false, true);
		final Map<String, NXuser> allUser = group.getAllUser();
		for (final NXuser user : allUser.values()) {
			validateGroup_NXentry_NXuser(user);
		}

		// validate unnamed child group of type NXnote (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXnote.class, false, true);
		final Map<String, NXnote> allNote = group.getChildren(NXnote.class);
		for (final NXnote note : allNote.values()) {
			validateGroup_NXentry_NXnote(note);
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}
	}

	/**
	 * Validate unnamed group of type NXprocess.
	 */
	private void validateGroup_NXentry_NXprocess(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprocess.class, group))) return;

		// validate field 'program' of type NX_CHAR.
		final ILazyDataset program = group.getLazyDataset("program");
		validateFieldNotNull("program", program);
		if (program != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("program", program, NX_CHAR);
		// validate attribute 'version' of field 'program' of type NX_CHAR.
		final Attribute program_attr_version = group.getDataNode("program").getAttribute("version");
		if (!(validateAttributeNotNull("version", program_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", program_attr_version, NX_CHAR);

		// validate attribute 'program_url' of field 'program' of type NX_CHAR.
		final Attribute program_attr_program_url = group.getDataNode("program").getAttribute("program_url");
		if (!(validateAttributeNotNull("program_url", program_attr_program_url))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("program_url", program_attr_program_url, NX_CHAR);

		}
	}

	/**
	 * Validate unnamed group of type NXuser.
	 */
	private void validateGroup_NXentry_NXuser(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXuser.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'affiliation' of type NX_CHAR.
		final ILazyDataset affiliation = group.getLazyDataset("affiliation");
				if (affiliation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("affiliation", affiliation, NX_CHAR);
		}

		// validate optional field 'address' of type NX_CHAR.
		final ILazyDataset address = group.getLazyDataset("address");
				if (address != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("address", address, NX_CHAR);
		}

		// validate optional field 'email' of type NX_CHAR.
		final ILazyDataset email = group.getLazyDataset("email");
				if (email != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("email", email, NX_CHAR);
		}

		// validate optional field 'orcid' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset orcid = group.getLazyDataset("orcid");
				if (orcid != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("orcid", orcid, NX_CHAR);
		}

		// validate optional field 'telephone_number' of type NX_CHAR.
		final ILazyDataset telephone_number = group.getLazyDataset("telephone_number");
				if (telephone_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("telephone_number", telephone_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional unnamed group of type NXnote.
	 */
	private void validateGroup_NXentry_NXnote(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXnote.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate unnamed child group of type NXenvironment (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXenvironment.class, false, true);
		final Map<String, NXenvironment> allEnvironment = group.getChildren(NXenvironment.class);
		for (final NXenvironment environment : allEnvironment.values()) {
			validateGroup_NXentry_NXinstrument_NXenvironment(environment);
		}
	}

	/**
	 * Validate unnamed group of type NXenvironment.
	 */
	private void validateGroup_NXentry_NXinstrument_NXenvironment(final NXenvironment group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXenvironment.class, group))) return;

		// validate optional field 'independent_controllers' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset independent_controllers = group.getLazyDataset("independent_controllers");
				if (independent_controllers != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("independent_controllers", independent_controllers, NX_CHAR);
		}

		// validate optional field 'measurement_sensors' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset measurement_sensors = group.getLazyDataset("measurement_sensors");
				if (measurement_sensors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measurement_sensors", measurement_sensors, NX_CHAR);
		}
		// validate unnamed child group of type NXsensor (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsensor.class, false, true);
		final Map<String, NXsensor> allSensor = group.getAllSensor();
		for (final NXsensor sensor : allSensor.values()) {
			validateGroup_NXentry_NXinstrument_NXenvironment_NXsensor(sensor);
		}

		// validate unnamed child group of type NXpid_controller (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXpid_controller.class, false, true);
		final Map<String, NXpid_controller> allPid_controller = group.getChildren(NXpid_controller.class);
		for (final NXpid_controller pid_controller : allPid_controller.values()) {
			validateGroup_NXentry_NXinstrument_NXenvironment_NXpid_controller(pid_controller);
		}
	}

	/**
	 * Validate optional unnamed group of type NXsensor.
	 */
	private void validateGroup_NXentry_NXinstrument_NXenvironment_NXsensor(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_ANY);
			validateFieldRank("value", value, 1);
			validateFieldDimensions("value", value, null, "N_scanpoints");
		}

		// validate optional field 'value_timestamp' of type NX_DATE_TIME. Note: field not defined in base class.
		final ILazyDataset value_timestamp = group.getLazyDataset("value_timestamp");
				if (value_timestamp != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value_timestamp", value_timestamp, NX_DATE_TIME);
		}

		// validate optional field 'run_control' of type NX_CHAR.
		final ILazyDataset run_control = group.getLazyDataset("run_control");
				if (run_control != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("run_control", run_control, NX_CHAR);
		// validate attribute 'description' of field 'run_control' of type NX_CHAR.
		final Attribute run_control_attr_description = group.getDataNode("run_control").getAttribute("description");
		if (!(validateAttributeNotNull("description", run_control_attr_description))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("description", run_control_attr_description, NX_CHAR);

		}

		// validate optional field 'calibration_time' of type NX_DATE_TIME. Note: field not defined in base class.
		final ILazyDataset calibration_time = group.getLazyDataset("calibration_time");
				if (calibration_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibration_time", calibration_time, NX_DATE_TIME);
		}
		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getChildren(NXdata.class);
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXinstrument_NXenvironment_NXsensor_NXdata(data);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_NXenvironment_NXsensor_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXpid_controller.
	 */
	private void validateGroup_NXentry_NXinstrument_NXenvironment_NXpid_controller(final NXpid_controller group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXpid_controller.class, group))) return;

	}

	/**
	 * Validate optional unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate unnamed child group of type NXhistory (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXhistory.class, false, true);
		final Map<String, NXhistory> allHistory = group.getChildren(NXhistory.class);
		for (final NXhistory history : allHistory.values()) {
			validateGroup_NXentry_NXsample_NXhistory(history);
		}
	}

	/**
	 * Validate optional unnamed group of type NXhistory.
	 */
	private void validateGroup_NXentry_NXsample_NXhistory(final NXhistory group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXhistory.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}
}
