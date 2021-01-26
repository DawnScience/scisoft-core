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

import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;import org.eclipse.january.dataset.IDataset;
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
		final IDataset title = group.getTitle();
		if (!(validateFieldNotNull("title", title))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("title", title, NX_CHAR);

		// validate field 'experiment_identifier' of type NX_CHAR.
		final IDataset experiment_identifier = group.getExperiment_identifier();
		if (!(validateFieldNotNull("experiment_identifier", experiment_identifier))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("experiment_identifier", experiment_identifier, NX_CHAR);

		// validate field 'experiment_description' of type NX_CHAR.
		final IDataset experiment_description = group.getExperiment_description();
		if (!(validateFieldNotNull("experiment_description", experiment_description))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("experiment_description", experiment_description, NX_CHAR);

		// validate field 'collection_identifier' of type NX_CHAR.
		final IDataset collection_identifier = group.getCollection_identifier();
		if (!(validateFieldNotNull("collection_identifier", collection_identifier))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("collection_identifier", collection_identifier, NX_CHAR);

		// validate field 'collection_description' of type NX_CHAR.
		final IDataset collection_description = group.getCollection_description();
		if (!(validateFieldNotNull("collection_description", collection_description))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("collection_description", collection_description, NX_CHAR);

		// validate field 'entry_identifier' of type NX_CHAR.
		final IDataset entry_identifier = group.getEntry_identifier();
		if (!(validateFieldNotNull("entry_identifier", entry_identifier))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("entry_identifier", entry_identifier, NX_CHAR);

		// validate field 'start_time' of type NX_DATE_TIME.
		final IDataset start_time = group.getStart_time();
		if (!(validateFieldNotNull("start_time", start_time))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("start_time", start_time, NX_DATE_TIME);

		// validate field 'end_time' of type NX_DATE_TIME.
		final IDataset end_time = group.getEnd_time();
		if (!(validateFieldNotNull("end_time", end_time))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("end_time", end_time, NX_DATE_TIME);

		// validate field 'duration' of type NX_FLOAT.
		final IDataset duration = group.getDuration();
		if (!(validateFieldNotNull("duration", duration))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("duration", duration, NX_FLOAT);
		validateFieldUnits("duration", group.getDataNode("duration"), NX_TIME);

		// validate field 'collection_time' of type NX_FLOAT.
		final IDataset collection_time = group.getCollection_time();
		if (!(validateFieldNotNull("collection_time", collection_time))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("collection_time", collection_time, NX_FLOAT);
		validateFieldUnits("collection_time", group.getDataNode("collection_time"), NX_TIME);

		// validate field 'run_cycle' of type NX_CHAR.
		final IDataset run_cycle = group.getRun_cycle();
		if (!(validateFieldNotNull("run_cycle", run_cycle))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("run_cycle", run_cycle, NX_CHAR);

		// validate field 'revision' of type NX_CHAR.
		final IDataset revision = group.getRevision();
		if (!(validateFieldNotNull("revision", revision))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("revision", revision, NX_CHAR);

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		if (!(validateFieldNotNull("definition", definition))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("definition", definition, NX_CHAR);
		validateFieldEnumeration("definition", definition,
				"NXarchive");

		// validate field 'program' of type NX_CHAR. Note: field not defined in base class.
		final IDataset program = group.getDataset("program");
		if (!(validateFieldNotNull("program", program))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("program", program, NX_CHAR);
		// validate attribute 'version' of field 'program'
		final Attribute program_attr_version = group.getDataNode("program").getAttribute("version");
		if (!(validateAttributeNotNull("version", program_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", program_attr_version, NX_CHAR);


		// validate field 'release_date' of type NX_CHAR. Note: field not defined in base class.
		final IDataset release_date = group.getDataset("release_date");
		if (!(validateFieldNotNull("release_date", release_date))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("release_date", release_date, NX_CHAR);
		validateFieldUnits("release_date", group.getDataNode("release_date"), NX_TIME);

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
		final IDataset name = group.getName();
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'role' of type NX_CHAR.
		final IDataset role = group.getRole();
		if (!(validateFieldNotNull("role", role))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("role", role, NX_CHAR);

		// validate field 'facility_user_id' of type NX_CHAR.
		final IDataset facility_user_id = group.getFacility_user_id();
		if (!(validateFieldNotNull("facility_user_id", facility_user_id))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("facility_user_id", facility_user_id, NX_CHAR);
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_entry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final IDataset name = group.getName();
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'description' of type NX_CHAR. Note: field not defined in base class.
		final IDataset description = group.getDataset("description");
		if (!(validateFieldNotNull("description", description))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("description", description, NX_CHAR);
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
		final IDataset type = group.getType();
		if (!(validateFieldNotNull("type", type))) return;
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

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'probe' of unknown type.
		final IDataset probe = group.getProbe();
		if (!(validateFieldNotNull("probe", probe))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("probe", probe, NX_CHAR);
		validateFieldEnumeration("probe", probe,
				"neutron",
				"x-ray",
				"electron");
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_entry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'sample_id' of type NX_CHAR. Note: field not defined in base class.
		final IDataset sample_id = group.getDataset("sample_id");
		if (!(validateFieldNotNull("sample_id", sample_id))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("sample_id", sample_id, NX_CHAR);

		// validate field 'description' of type NX_CHAR.
		final IDataset description = group.getDescription();
		if (!(validateFieldNotNull("description", description))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("description", description, NX_CHAR);

		// validate field 'type' of type NX_CHAR.
		final IDataset type = group.getType();
		if (!(validateFieldNotNull("type", type))) return;
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

		// validate field 'chemical_formula' of type NX_CHAR.
		final IDataset chemical_formula = group.getChemical_formula();
		if (!(validateFieldNotNull("chemical_formula", chemical_formula))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("chemical_formula", chemical_formula, NX_CHAR);

		// validate field 'preparation_date' of type NX_CHAR.
		final IDataset preparation_date = group.getPreparation_date();
		if (!(validateFieldNotNull("preparation_date", preparation_date))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("preparation_date", preparation_date, NX_CHAR);
		validateFieldUnits("preparation_date", group.getDataNode("preparation_date"), NX_TIME);

		// validate field 'situation' of type NX_CHAR.
		final IDataset situation = group.getSituation();
		if (!(validateFieldNotNull("situation", situation))) return;
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

		// validate field 'temperature' of type NX_FLOAT.
		final IDataset temperature = group.getTemperature();
		if (!(validateFieldNotNull("temperature", temperature))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("temperature", temperature, NX_FLOAT);
		validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
		validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");

		// validate field 'magnetic_field' of type NX_FLOAT.
		final IDataset magnetic_field = group.getMagnetic_field();
		if (!(validateFieldNotNull("magnetic_field", magnetic_field))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("magnetic_field", magnetic_field, NX_FLOAT);
		validateFieldUnits("magnetic_field", group.getDataNode("magnetic_field"), NX_CURRENT);
		validateFieldDimensions("magnetic_field", magnetic_field, "NXsample", "n_mField");

		// validate field 'electric_field' of type NX_FLOAT.
		final IDataset electric_field = group.getElectric_field();
		if (!(validateFieldNotNull("electric_field", electric_field))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("electric_field", electric_field, NX_FLOAT);
		validateFieldUnits("electric_field", group.getDataNode("electric_field"), NX_VOLTAGE);
		validateFieldDimensions("electric_field", electric_field, "NXsample", "n_eField");

		// validate field 'stress_field' of type NX_FLOAT.
		final IDataset stress_field = group.getStress_field();
		if (!(validateFieldNotNull("stress_field", stress_field))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("stress_field", stress_field, NX_FLOAT);
		validateFieldUnits("stress_field", group.getDataNode("stress_field"), NX_UNITLESS);
		validateFieldDimensions("stress_field", stress_field, "NXsample", "n_sField");

		// validate field 'pressure' of type NX_FLOAT.
		final IDataset pressure = group.getPressure();
		if (!(validateFieldNotNull("pressure", pressure))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("pressure", pressure, NX_FLOAT);
		validateFieldUnits("pressure", group.getDataNode("pressure"), NX_PRESSURE);
		validateFieldDimensions("pressure", pressure, "NXsample", "n_pField");
	}
}
