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

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXenvironment;
import org.eclipse.dawnsci.nexus.NXsensor;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXiv_temp'.
 */
public class NXiv_tempValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXiv_tempValidator() {
		super(NexusApplicationDefinition.NX_IV_TEMP);
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

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXiv_temp");
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}
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

		// validate field 'atom_types' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset atom_types = group.getLazyDataset("atom_types");
		validateFieldNotNull("atom_types", atom_types);
		if (atom_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("atom_types", atom_types, NX_CHAR);
		}
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

		// validate child group 'voltage_controller' of type NXsensor
		validateGroup_NXentry_NXinstrument_NXenvironment_voltage_controller(group.getSensor("voltage_controller"));

		// validate child group 'temperature_controller' of type NXsensor
		validateGroup_NXentry_NXinstrument_NXenvironment_temperature_controller(group.getSensor("temperature_controller"));

		// validate child group 'current_sensor' of type NXsensor
		validateGroup_NXentry_NXinstrument_NXenvironment_current_sensor(group.getSensor("current_sensor"));
	}

	/**
	 * Validate group 'voltage_controller' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXinstrument_NXenvironment_voltage_controller(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("voltage_controller", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate group 'temperature_controller' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXinstrument_NXenvironment_temperature_controller(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("temperature_controller", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate group 'current_sensor' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXinstrument_NXenvironment_current_sensor(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("current_sensor", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'temperature' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
		validateFieldNotNull("temperature", temperature);
		if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_NUMBER);
		}

		// validate field 'voltage' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset voltage = group.getLazyDataset("voltage");
		validateFieldNotNull("voltage", voltage);
		if (voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("voltage", voltage, NX_NUMBER);
		}

		// validate field 'current' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset current = group.getLazyDataset("current");
		validateFieldNotNull("current", current);
		if (current != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("current", current, NX_NUMBER);
			validateFieldRank("current", current, 2);
			validateFieldDimensions("current", current, null, "n_different_temperatures", "n_different_voltages");
		}
	}
}
