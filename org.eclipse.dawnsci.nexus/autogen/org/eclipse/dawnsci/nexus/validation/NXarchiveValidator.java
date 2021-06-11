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
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXsample;

/**
 * Validator for the application definition 'NXarchive'.
 */
public class NXarchiveValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXarchiveValidator() {
		super(NexusApplicationDefinition.NX_ARCHIVE);
	}

	@Override
	public ValidationReport validate(NXroot root) {
		// validate child group 'entry' of type NXentry
		validateGroup_entry(root.getEntry());
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXentry entry) {
		validateGroup_entry(entry);
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXsubentry subentry) {
		validateGroup_entry(subentry);
		return validationReport;
	}


	/**
	 * Validate group 'entry' of type NXentry.
	 */
	private void validateGroup_entry(final NXsubentry group) {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		if (!(validateGroupNotNull("entry", NXentry.class, group))) return;

		// validate attribute 'index'
		final Attribute index_attr = group.getAttribute("index");
		if (!(validateAttributeNotNull("index", index_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("index", index_attr, NX_CHAR);

		// validate field 'title' of unknown type.
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'experiment_identifier' of type NX_CHAR.
		final ILazyDataset experiment_identifier = group.getLazyDataset("experiment_identifier");
		validateFieldNotNull("experiment_identifier", experiment_identifier);
		if (experiment_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_identifier", experiment_identifier, NX_CHAR);
		}

		// validate field 'experiment_description' of type NX_CHAR.
		final ILazyDataset experiment_description = group.getLazyDataset("experiment_description");
		validateFieldNotNull("experiment_description", experiment_description);
		if (experiment_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_description", experiment_description, NX_CHAR);
		}

		// validate field 'collection_identifier' of type NX_CHAR.
		final ILazyDataset collection_identifier = group.getLazyDataset("collection_identifier");
		validateFieldNotNull("collection_identifier", collection_identifier);
		if (collection_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("collection_identifier", collection_identifier, NX_CHAR);
		}

		// validate field 'collection_description' of type NX_CHAR.
		final ILazyDataset collection_description = group.getLazyDataset("collection_description");
		validateFieldNotNull("collection_description", collection_description);
		if (collection_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("collection_description", collection_description, NX_CHAR);
		}

		// validate field 'entry_identifier' of type NX_CHAR.
		final ILazyDataset entry_identifier = group.getLazyDataset("entry_identifier");
		validateFieldNotNull("entry_identifier", entry_identifier);
		if (entry_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("entry_identifier", entry_identifier, NX_CHAR);
		}

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		validateFieldNotNull("start_time", start_time);
		if (start_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start_time", start_time, NX_DATE_TIME);
		}

		// validate field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
		validateFieldNotNull("end_time", end_time);
		if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
		}

		// validate field 'duration' of type NX_FLOAT.
		final ILazyDataset duration = group.getLazyDataset("duration");
		validateFieldNotNull("duration", duration);
		if (duration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("duration", duration, NX_FLOAT);
			validateFieldUnits("duration", group.getDataNode("duration"), NX_TIME);
		}

		// validate field 'collection_time' of type NX_FLOAT.
		final ILazyDataset collection_time = group.getLazyDataset("collection_time");
		validateFieldNotNull("collection_time", collection_time);
		if (collection_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("collection_time", collection_time, NX_FLOAT);
			validateFieldUnits("collection_time", group.getDataNode("collection_time"), NX_TIME);
		}

		// validate field 'run_cycle' of type NX_CHAR.
		final ILazyDataset run_cycle = group.getLazyDataset("run_cycle");
		validateFieldNotNull("run_cycle", run_cycle);
		if (run_cycle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("run_cycle", run_cycle, NX_CHAR);
		}

		// validate field 'revision' of type NX_CHAR.
		final ILazyDataset revision = group.getLazyDataset("revision");
		validateFieldNotNull("revision", revision);
		if (revision != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("revision", revision, NX_CHAR);
		}

		// validate field 'definition' of unknown type.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXarchive");
		}

		// validate field 'program' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset program = group.getLazyDataset("program");
		validateFieldNotNull("program", program);
		if (program != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("program", program, NX_CHAR);
		// validate attribute 'version' of field 'program'
		final Attribute program_attr_version = group.getDataNode("program").getAttribute("version");
		if (!(validateAttributeNotNull("version", program_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", program_attr_version, NX_CHAR);

		}

		// validate field 'release_date' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset release_date = group.getLazyDataset("release_date");
		validateFieldNotNull("release_date", release_date);
		if (release_date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("release_date", release_date, NX_CHAR);
			validateFieldUnits("release_date", group.getDataNode("release_date"), NX_TIME);
		}

		// validate child group 'user' of type NXuser
		validateGroup_entry_user(group.getUser());

		// validate child group 'instrument' of type NXinstrument
		validateGroup_entry_instrument(group.getInstrument());

		// validate child group 'sample' of type NXsample
		validateGroup_entry_sample(group.getSample());
	}

	/**
	 * Validate group 'user' of type NXuser.
	 */
	private void validateGroup_entry_user(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("user", NXuser.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'role' of type NX_CHAR.
		final ILazyDataset role = group.getLazyDataset("role");
		validateFieldNotNull("role", role);
		if (role != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("role", role, NX_CHAR);
		}

		// validate field 'facility_user_id' of type NX_CHAR.
		final ILazyDataset facility_user_id = group.getLazyDataset("facility_user_id");
		validateFieldNotNull("facility_user_id", facility_user_id);
		if (facility_user_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("facility_user_id", facility_user_id, NX_CHAR);
		}
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_entry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'description' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}
		// validate unnamed child group of type NXsource (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_entry_instrument_NXsource(source);
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_entry_instrument_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"Spallation Neutron Source",
					"Pulsed Reactor Neutron Source",
					"Reactor Neutron Source",
					"Synchrotron X-Ray Source",
					"Pulsed Muon Source",
					"Rotating Anode X-Ray",
					"Fixed Tube X-Ray");
		}

		// validate field 'name' of unknown type.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'probe' of unknown type.
		final ILazyDataset probe = group.getLazyDataset("probe");
		validateFieldNotNull("probe", probe);
		if (probe != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("probe", probe, NX_CHAR);
			validateFieldEnumeration("probe", probe,
					"neutron",
					"x-ray",
					"electron");
		}
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_entry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of unknown type.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'sample_id' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset sample_id = group.getLazyDataset("sample_id");
		validateFieldNotNull("sample_id", sample_id);
		if (sample_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_id", sample_id, NX_CHAR);
		}

		// validate field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"sample",
					"sample+can",
					"calibration sample",
					"normalisation sample",
					"simulated data",
					"none",
					"sample_environment");
		}

		// validate field 'chemical_formula' of type NX_CHAR.
		final ILazyDataset chemical_formula = group.getLazyDataset("chemical_formula");
		validateFieldNotNull("chemical_formula", chemical_formula);
		if (chemical_formula != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("chemical_formula", chemical_formula, NX_CHAR);
		}

		// validate field 'preparation_date' of type NX_CHAR.
		final ILazyDataset preparation_date = group.getLazyDataset("preparation_date");
		validateFieldNotNull("preparation_date", preparation_date);
		if (preparation_date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("preparation_date", preparation_date, NX_CHAR);
			validateFieldUnits("preparation_date", group.getDataNode("preparation_date"), NX_TIME);
		}

		// validate field 'situation' of type NX_CHAR.
		final ILazyDataset situation = group.getLazyDataset("situation");
		validateFieldNotNull("situation", situation);
		if (situation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("situation", situation, NX_CHAR);
			validateFieldEnumeration("situation", situation,
					"air",
					"vacuum",
					"inert atmosphere",
					"oxidising atmosphere",
					"reducing atmosphere",
					"sealed can",
					"other");
		}

		// validate field 'temperature' of type NX_FLOAT.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
		validateFieldNotNull("temperature", temperature);
		if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_FLOAT);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");
		}

		// validate field 'magnetic_field' of type NX_FLOAT.
		final ILazyDataset magnetic_field = group.getLazyDataset("magnetic_field");
		validateFieldNotNull("magnetic_field", magnetic_field);
		if (magnetic_field != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnetic_field", magnetic_field, NX_FLOAT);
			validateFieldUnits("magnetic_field", group.getDataNode("magnetic_field"), NX_CURRENT);
			validateFieldDimensions("magnetic_field", magnetic_field, "NXsample", "n_mField");
		}

		// validate field 'electric_field' of type NX_FLOAT.
		final ILazyDataset electric_field = group.getLazyDataset("electric_field");
		validateFieldNotNull("electric_field", electric_field);
		if (electric_field != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("electric_field", electric_field, NX_FLOAT);
			validateFieldUnits("electric_field", group.getDataNode("electric_field"), NX_VOLTAGE);
			validateFieldDimensions("electric_field", electric_field, "NXsample", "n_eField");
		}

		// validate field 'stress_field' of type NX_FLOAT.
		final ILazyDataset stress_field = group.getLazyDataset("stress_field");
		validateFieldNotNull("stress_field", stress_field);
		if (stress_field != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("stress_field", stress_field, NX_FLOAT);
			validateFieldUnits("stress_field", group.getDataNode("stress_field"), NX_UNITLESS);
			validateFieldDimensions("stress_field", stress_field, "NXsample", "n_sField");
		}

		// validate field 'pressure' of type NX_FLOAT.
		final ILazyDataset pressure = group.getLazyDataset("pressure");
		validateFieldNotNull("pressure", pressure);
		if (pressure != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pressure", pressure, NX_FLOAT);
			validateFieldUnits("pressure", group.getDataNode("pressure"), NX_PRESSURE);
			validateFieldDimensions("pressure", pressure, "NXsample", "n_pField");
		}
	}
}
