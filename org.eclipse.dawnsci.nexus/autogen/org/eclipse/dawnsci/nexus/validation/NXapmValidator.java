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
import org.eclipse.dawnsci.nexus.NXcs_profiling;
import org.eclipse.dawnsci.nexus.NXprogram;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXcite;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXchemical_composition;
import org.eclipse.dawnsci.nexus.NXatom;
import org.eclipse.dawnsci.nexus.NXparameters;
import org.eclipse.dawnsci.nexus.NXcoordinate_system;
import org.eclipse.dawnsci.nexus.NXapm_measurement;
import org.eclipse.dawnsci.nexus.NXapm_instrument;
import org.eclipse.dawnsci.nexus.NXfabrication;
import org.eclipse.dawnsci.nexus.NXcomponent;
import org.eclipse.dawnsci.nexus.NXelectromagnetic_lens;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXmanipulator;
import org.eclipse.dawnsci.nexus.NXpump;
import org.eclipse.dawnsci.nexus.NXapm_event_data;
import org.eclipse.dawnsci.nexus.NXsensor;
import org.eclipse.dawnsci.nexus.NXapm_simulation;
import org.eclipse.dawnsci.nexus.NXroi_process;
import org.eclipse.dawnsci.nexus.NXimage;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXcs_filter_boolean_mask;
import org.eclipse.dawnsci.nexus.NXapm_reconstruction;
import org.eclipse.dawnsci.nexus.NXapm_ranging;
import org.eclipse.dawnsci.nexus.NXpeak;
import org.eclipse.dawnsci.nexus.NXapm_charge_state_analysis;

/**
 * Validator for the application definition 'NXapm'.
 */
public class NXapmValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapmValidator() {
		super(NexusApplicationDefinition.NX_APM);
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
					"NXapm");
		// validate optional attribute 'version' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (definition_attr_version != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("version", definition_attr_version, NX_CHAR);
		}

		}

		// validate optional field 'run_number' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset run_number = group.getLazyDataset("run_number");
				if (run_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("run_number", run_number, NX_UINT);
			validateFieldUnits("run_number", group.getDataNode("run_number"), NX_UNITLESS);
		}

		// validate optional field 'experiment_alias' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset experiment_alias = group.getLazyDataset("experiment_alias");
				if (experiment_alias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_alias", experiment_alias, NX_CHAR);
		}

		// validate optional field 'experiment_description' of type NX_CHAR.
		final ILazyDataset experiment_description = group.getLazyDataset("experiment_description");
				if (experiment_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_description", experiment_description, NX_CHAR);
		}

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		validateFieldNotNull("start_time", start_time);
		if (start_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start_time", start_time, NX_DATE_TIME);
		}

		// validate optional field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
				if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
		}

		// validate optional field 'elapsed_time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset elapsed_time = group.getLazyDataset("elapsed_time");
				if (elapsed_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("elapsed_time", elapsed_time, NX_FLOAT);
			validateFieldUnits("elapsed_time", group.getDataNode("elapsed_time"), NX_TIME);
		}

		// validate field 'operation_mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset operation_mode = group.getLazyDataset("operation_mode");
		validateFieldNotNull("operation_mode", operation_mode);
		if (operation_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("operation_mode", operation_mode, NX_CHAR);
			validateFieldEnumeration("operation_mode", operation_mode,
					"apt",
					"fim",
					"apt_fim");
		}

		// validate optional child group 'profiling' of type NXcs_profiling
		if (group.getChild("profiling", NXcs_profiling.class) != null) {
			validateGroup_NXentry_profiling(group.getChild("profiling", NXcs_profiling.class));
		}

		// validate optional child group 'citeID' of type NXcite
		if (group.getChild("citeID", NXcite.class) != null) {
			validateGroup_NXentry_citeID(group.getChild("citeID", NXcite.class));
		}

		// validate optional child group 'noteID' of type NXnote
		if (group.getChild("noteID", NXnote.class) != null) {
			validateGroup_NXentry_noteID(group.getChild("noteID", NXnote.class));
		}

		// validate optional child group 'userID' of type NXuser
		if (group.getUser("userID") != null) {
			validateGroup_NXentry_userID(group.getUser("userID"));
		}

		// validate optional child group 'sample' of type NXsample
		if (group.getSample() != null) {
			validateGroup_NXentry_sample(group.getSample());
		}

		// validate child group 'specimen' of type NXsample
		validateGroup_NXentry_specimen(group.getSample("specimen"));

		// validate optional child group 'consistent_rotations' of type NXparameters
		if (group.getParameters("consistent_rotations") != null) {
			validateGroup_NXentry_consistent_rotations(group.getParameters("consistent_rotations"));
		}

		// validate child group 'NAMED_reference_frameID' of type NXcoordinate_system
		validateGroup_NXentry_NAMED_reference_frameID(group.getChild("NAMED_reference_frameID", NXcoordinate_system.class));

		// validate optional child group 'measurement' of type NXapm_measurement
		if (group.getChild("measurement", NXapm_measurement.class) != null) {
			validateGroup_NXentry_measurement(group.getChild("measurement", NXapm_measurement.class));
		}

		// validate optional child group 'simulation' of type NXapm_simulation
		if (group.getChild("simulation", NXapm_simulation.class) != null) {
			validateGroup_NXentry_simulation(group.getChild("simulation", NXapm_simulation.class));
		}

		// validate optional child group 'atom_probeID' of type NXroi_process
		if (group.getChild("atom_probeID", NXroi_process.class) != null) {
			validateGroup_NXentry_atom_probeID(group.getChild("atom_probeID", NXroi_process.class));
		}
	}

	/**
	 * Validate optional group 'profiling' of type NXcs_profiling.
	 */
	private void validateGroup_NXentry_profiling(final NXcs_profiling group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("profiling", NXcs_profiling.class, group))) return;

		// validate optional child group 'programID' of type NXprogram
		if (group.getChild("programID", NXprogram.class) != null) {
			validateGroup_NXentry_profiling_programID(group.getChild("programID", NXprogram.class));
		}

		// validate optional child group 'environment' of type NXcollection
		if (group.getChild("environment", NXcollection.class) != null) {
			validateGroup_NXentry_profiling_environment(group.getChild("environment", NXcollection.class));
		}
	}

	/**
	 * Validate optional group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_profiling_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'environment' of type NXcollection.
	 */
	private void validateGroup_NXentry_profiling_environment(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("environment", NXcollection.class, group))) return;

		// validate unnamed child group of type NXprogram (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXprogram.class, false, true);
		final Map<String, NXprogram> allProgram = group.getChildren(NXprogram.class);
		for (final NXprogram program : allProgram.values()) {
			validateGroup_NXentry_profiling_environment_NXprogram(program);
		}
	}

	/**
	 * Validate unnamed group of type NXprogram.
	 */
	private void validateGroup_NXentry_profiling_environment_NXprogram(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'citeID' of type NXcite.
	 */
	private void validateGroup_NXentry_citeID(final NXcite group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("citeID", NXcite.class, group))) return;

		// validate optional field 'author' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset author = group.getLazyDataset("author");
				if (author != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("author", author, NX_CHAR);
		}

		// validate field 'doi' of type NX_CHAR.
		final ILazyDataset doi = group.getLazyDataset("doi");
		validateFieldNotNull("doi", doi);
		if (doi != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("doi", doi, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'noteID' of type NXnote.
	 */
	private void validateGroup_NXentry_noteID(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("noteID", NXnote.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'userID' of type NXuser.
	 */
	private void validateGroup_NXentry_userID(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("userID", NXuser.class, group))) return;

		// validate optional field 'identifierNAME' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifierNAME = group.getLazyDataset("identifierNAME");
				if (identifierNAME != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifierNAME", identifierNAME, NX_CHAR);
		// validate attribute 'type' of field 'identifierNAME' of type NX_CHAR.
		final Attribute identifierNAME_attr_type = group.getDataNode("identifierNAME").getAttribute("type");
		if (!(validateAttributeNotNull("type", identifierNAME_attr_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("type", identifierNAME_attr_type, NX_CHAR);

		}

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'sample' of type NXsample.
	 */
	private void validateGroup_NXentry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'identifierNAME' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifierNAME = group.getLazyDataset("identifierNAME");
				if (identifierNAME != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifierNAME", identifierNAME, NX_CHAR);
		}

		// validate field 'is_simulation' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_simulation = group.getLazyDataset("is_simulation");
		validateFieldNotNull("is_simulation", is_simulation);
		if (is_simulation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_simulation", is_simulation, NX_BOOLEAN);
		}

		// validate field 'alias' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset alias = group.getLazyDataset("alias");
		validateFieldNotNull("alias", alias);
		if (alias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("alias", alias, NX_CHAR);
		}

		// validate optional field 'grain_diameter' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset grain_diameter = group.getLazyDataset("grain_diameter");
				if (grain_diameter != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("grain_diameter", grain_diameter, NX_FLOAT);
			validateFieldUnits("grain_diameter", group.getDataNode("grain_diameter"), NX_LENGTH);
		}

		// validate optional field 'grain_diameter_errors' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset grain_diameter_errors = group.getLazyDataset("grain_diameter_errors");
				if (grain_diameter_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("grain_diameter_errors", grain_diameter_errors, NX_FLOAT);
			validateFieldUnits("grain_diameter_errors", group.getDataNode("grain_diameter_errors"), NX_LENGTH);
		}

		// validate optional field 'heat_treatment_time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset heat_treatment_time = group.getLazyDataset("heat_treatment_time");
				if (heat_treatment_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("heat_treatment_time", heat_treatment_time, NX_FLOAT);
			validateFieldUnits("heat_treatment_time", group.getDataNode("heat_treatment_time"), NX_TIME);
		}

		// validate optional field 'heat_treatment_temperature' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset heat_treatment_temperature = group.getLazyDataset("heat_treatment_temperature");
				if (heat_treatment_temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("heat_treatment_temperature", heat_treatment_temperature, NX_FLOAT);
			validateFieldUnits("heat_treatment_temperature", group.getDataNode("heat_treatment_temperature"), NX_TEMPERATURE);
		}

		// validate optional field 'heat_treatment_temperature_errors' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset heat_treatment_temperature_errors = group.getLazyDataset("heat_treatment_temperature_errors");
				if (heat_treatment_temperature_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("heat_treatment_temperature_errors", heat_treatment_temperature_errors, NX_FLOAT);
			validateFieldUnits("heat_treatment_temperature_errors", group.getDataNode("heat_treatment_temperature_errors"), NX_TEMPERATURE);
		}

		// validate optional field 'heat_treatment_quenching_rate' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset heat_treatment_quenching_rate = group.getLazyDataset("heat_treatment_quenching_rate");
				if (heat_treatment_quenching_rate != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("heat_treatment_quenching_rate", heat_treatment_quenching_rate, NX_FLOAT);
			validateFieldUnits("heat_treatment_quenching_rate", group.getDataNode("heat_treatment_quenching_rate"), NX_ANY);
		}

		// validate optional field 'heat_treatment_quenching_rate_errors' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset heat_treatment_quenching_rate_errors = group.getLazyDataset("heat_treatment_quenching_rate_errors");
				if (heat_treatment_quenching_rate_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("heat_treatment_quenching_rate_errors", heat_treatment_quenching_rate_errors, NX_FLOAT);
			validateFieldUnits("heat_treatment_quenching_rate_errors", group.getDataNode("heat_treatment_quenching_rate_errors"), NX_ANY);
		}

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional child group 'chemical_composition' of type NXchemical_composition
		if (group.getChild("chemical_composition", NXchemical_composition.class) != null) {
			validateGroup_NXentry_sample_chemical_composition(group.getChild("chemical_composition", NXchemical_composition.class));
		}
	}

	/**
	 * Validate optional group 'chemical_composition' of type NXchemical_composition.
	 */
	private void validateGroup_NXentry_sample_chemical_composition(final NXchemical_composition group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("chemical_composition", NXchemical_composition.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'normalization' of type NX_CHAR.
		final ILazyDataset normalization = group.getLazyDataset("normalization");
		validateFieldNotNull("normalization", normalization);
		if (normalization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("normalization", normalization, NX_CHAR);
			validateFieldEnumeration("normalization", normalization,
					"atom_percent",
					"weight_percent");
		}

		// validate child group 'ELEMENT' of type NXatom
		validateGroup_NXentry_sample_chemical_composition_ELEMENT(group.getElement());
	}

	/**
	 * Validate group 'ELEMENT' of type NXatom.
	 */
	private void validateGroup_NXentry_sample_chemical_composition_ELEMENT(final NXatom group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ELEMENT", NXatom.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'chemical_symbol' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset chemical_symbol = group.getLazyDataset("chemical_symbol");
		validateFieldNotNull("chemical_symbol", chemical_symbol);
		if (chemical_symbol != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("chemical_symbol", chemical_symbol, NX_CHAR);
		}

		// validate field 'composition' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset composition = group.getLazyDataset("composition");
		validateFieldNotNull("composition", composition);
		if (composition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("composition", composition, NX_FLOAT);
		}

		// validate optional field 'composition_errors' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset composition_errors = group.getLazyDataset("composition_errors");
				if (composition_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("composition_errors", composition_errors, NX_FLOAT);
		}
	}

	/**
	 * Validate group 'specimen' of type NXsample.
	 */
	private void validateGroup_NXentry_specimen(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("specimen", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'identifierNAME' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifierNAME = group.getLazyDataset("identifierNAME");
				if (identifierNAME != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifierNAME", identifierNAME, NX_CHAR);
		}

		// validate field 'is_simulation' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_simulation = group.getLazyDataset("is_simulation");
		validateFieldNotNull("is_simulation", is_simulation);
		if (is_simulation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_simulation", is_simulation, NX_BOOLEAN);
		}

		// validate optional field 'alias' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset alias = group.getLazyDataset("alias");
				if (alias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("alias", alias, NX_CHAR);
		}

		// validate optional field 'identifier_parent' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier_parent = group.getLazyDataset("identifier_parent");
				if (identifier_parent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_parent", identifier_parent, NX_CHAR);
		}

		// validate optional field 'preparation_date' of type NX_DATE_TIME.
		final ILazyDataset preparation_date = group.getLazyDataset("preparation_date");
				if (preparation_date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("preparation_date", preparation_date, NX_DATE_TIME);
		}

		// validate field 'atom_types' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset atom_types = group.getLazyDataset("atom_types");
		validateFieldNotNull("atom_types", atom_types);
		if (atom_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("atom_types", atom_types, NX_CHAR);
		}

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'is_polycrystalline' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_polycrystalline = group.getLazyDataset("is_polycrystalline");
				if (is_polycrystalline != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_polycrystalline", is_polycrystalline, NX_BOOLEAN);
		}

		// validate optional field 'is_amorphous' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_amorphous = group.getLazyDataset("is_amorphous");
				if (is_amorphous != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_amorphous", is_amorphous, NX_BOOLEAN);
		}

		// validate optional field 'initial_radius' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset initial_radius = group.getLazyDataset("initial_radius");
				if (initial_radius != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("initial_radius", initial_radius, NX_FLOAT);
			validateFieldUnits("initial_radius", group.getDataNode("initial_radius"), NX_LENGTH);
		}

		// validate optional field 'shank_angle' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset shank_angle = group.getLazyDataset("shank_angle");
				if (shank_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("shank_angle", shank_angle, NX_FLOAT);
			validateFieldUnits("shank_angle", group.getDataNode("shank_angle"), NX_ANGLE);
		}
	}

	/**
	 * Validate optional group 'consistent_rotations' of type NXparameters.
	 */
	private void validateGroup_NXentry_consistent_rotations(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("consistent_rotations", NXparameters.class, group))) return;

		// validate field 'rotation_handedness' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset rotation_handedness = group.getLazyDataset("rotation_handedness");
		validateFieldNotNull("rotation_handedness", rotation_handedness);
		if (rotation_handedness != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("rotation_handedness", rotation_handedness, NX_CHAR);
			validateFieldEnumeration("rotation_handedness", rotation_handedness,
					"counter_clockwise",
					"clockwise");
		}

		// validate field 'rotation_convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset rotation_convention = group.getLazyDataset("rotation_convention");
		validateFieldNotNull("rotation_convention", rotation_convention);
		if (rotation_convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("rotation_convention", rotation_convention, NX_CHAR);
			validateFieldEnumeration("rotation_convention", rotation_convention,
					"passive",
					"active");
		}

		// validate field 'euler_angle_convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset euler_angle_convention = group.getLazyDataset("euler_angle_convention");
		validateFieldNotNull("euler_angle_convention", euler_angle_convention);
		if (euler_angle_convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("euler_angle_convention", euler_angle_convention, NX_CHAR);
			validateFieldEnumeration("euler_angle_convention", euler_angle_convention,
					"zxz",
					"xyx",
					"yzy",
					"zyz",
					"xzx",
					"yxy",
					"xyz",
					"yzx",
					"zxy",
					"xzy",
					"zyx",
					"yxz");
		}

		// validate field 'axis_angle_convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset axis_angle_convention = group.getLazyDataset("axis_angle_convention");
		validateFieldNotNull("axis_angle_convention", axis_angle_convention);
		if (axis_angle_convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_angle_convention", axis_angle_convention, NX_CHAR);
			validateFieldEnumeration("axis_angle_convention", axis_angle_convention,
					"rotation_angle_on_interval_zero_to_pi");
		}

		// validate field 'sign_convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset sign_convention = group.getLazyDataset("sign_convention");
		validateFieldNotNull("sign_convention", sign_convention);
		if (sign_convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sign_convention", sign_convention, NX_CHAR);
			validateFieldEnumeration("sign_convention", sign_convention,
					"p_plus_one",
					"p_minus_one");
		}
	}

	/**
	 * Validate group 'NAMED_reference_frameID' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_NAMED_reference_frameID(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("NAMED_reference_frameID", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'alias' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset alias = group.getLazyDataset("alias");
				if (alias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("alias", alias, NX_CHAR);
		}

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"undefined",
					"cartesian");
		}

		// validate optional field 'origin' of type NX_CHAR.
		final ILazyDataset origin = group.getLazyDataset("origin");
				if (origin != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("origin", origin, NX_CHAR);
		}

		// validate field 'x' of type NX_NUMBER.
		final ILazyDataset x = group.getLazyDataset("x");
		validateFieldNotNull("x", x);
		if (x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x", x, NX_NUMBER);
			validateFieldUnits("x", group.getDataNode("x"), NX_LENGTH);
			validateFieldRank("x", x, 1);
			validateFieldDimensions("x", x, "NXcoordinate_system", 3);
		}

		// validate optional field 'x_direction' of type NX_CHAR.
		final ILazyDataset x_direction = group.getLazyDataset("x_direction");
				if (x_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_direction", x_direction, NX_CHAR);
		}

		// validate field 'y' of type NX_NUMBER.
		final ILazyDataset y = group.getLazyDataset("y");
		validateFieldNotNull("y", y);
		if (y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y", y, NX_NUMBER);
			validateFieldUnits("y", group.getDataNode("y"), NX_LENGTH);
			validateFieldRank("y", y, 1);
			validateFieldDimensions("y", y, "NXcoordinate_system", 3);
		}

		// validate optional field 'y_direction' of type NX_CHAR.
		final ILazyDataset y_direction = group.getLazyDataset("y_direction");
				if (y_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_direction", y_direction, NX_CHAR);
		}

		// validate field 'z' of type NX_NUMBER.
		final ILazyDataset z = group.getLazyDataset("z");
		validateFieldNotNull("z", z);
		if (z != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z", z, NX_NUMBER);
			validateFieldUnits("z", group.getDataNode("z"), NX_LENGTH);
			validateFieldRank("z", z, 1);
			validateFieldDimensions("z", z, "NXcoordinate_system", 3);
		}

		// validate optional field 'z_direction' of type NX_CHAR.
		final ILazyDataset z_direction = group.getLazyDataset("z_direction");
				if (z_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z_direction", z_direction, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'measurement' of type NXapm_measurement.
	 */
	private void validateGroup_NXentry_measurement(final NXapm_measurement group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("measurement", NXapm_measurement.class, group))) return;

		// validate optional field 'status' of type NX_CHAR.
		final ILazyDataset status = group.getLazyDataset("status");
				if (status != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("status", status, NX_CHAR);
			validateFieldEnumeration("status", status,
					"success",
					"aborted");
		}

		// validate optional field 'quality' of type NX_CHAR.
		final ILazyDataset quality = group.getLazyDataset("quality");
				if (quality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("quality", quality, NX_CHAR);
		}

		// validate child group 'instrument' of type NXapm_instrument
		validateGroup_NXentry_measurement_instrument(group.getApm_instrument("instrument"));

		// validate optional child group 'eventID' of type NXapm_event_data
		if (group.getApm_event_data("eventID") != null) {
			validateGroup_NXentry_measurement_eventID(group.getApm_event_data("eventID"));
		}
	}

	/**
	 * Validate group 'instrument' of type NXapm_instrument.
	 */
	private void validateGroup_NXentry_measurement_instrument(final NXapm_instrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXapm_instrument.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"Inspico",
					"3DAP",
					"LAWATAP",
					"LEAP 3000 Si",
					"LEAP 3000X Si",
					"LEAP 3000 HR",
					"LEAP 3000X HR",
					"LEAP 4000 Si",
					"LEAP 4000X Si",
					"LEAP 4000 HR",
					"LEAP 4000X HR",
					"LEAP 5000 XS",
					"LEAP 5000 XR",
					"LEAP 5000 R",
					"EIKOS",
					"EIKOS-UV",
					"LEAP 6000 XR",
					"LEAP INVIZO",
					"Photonic AP",
					"TeraSAT",
					"TAPHR",
					"Modular AP",
					"Titanium APT",
					"Extreme UV APT");
		}

		// validate optional field 'location' of type NX_CHAR.
		final ILazyDataset location = group.getLazyDataset("location");
				if (location != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("location", location, NX_CHAR);
		}

		// validate optional field 'flight_path' of type NX_FLOAT.
		final ILazyDataset flight_path = group.getLazyDataset("flight_path");
				if (flight_path != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flight_path", flight_path, NX_FLOAT);
			validateFieldUnits("flight_path", group.getDataNode("flight_path"), NX_LENGTH);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_fabrication(group.getFabrication());
		}

		// validate optional child group 'reflectron' of type NXcomponent
		if (group.getReflectron() != null) {
			validateGroup_NXentry_measurement_instrument_reflectron(group.getReflectron());
		}

		// validate optional child group 'local_electrode' of type NXelectromagnetic_lens
		if (group.getChild("local_electrode", NXelectromagnetic_lens.class) != null) {
			validateGroup_NXentry_measurement_instrument_local_electrode(group.getChild("local_electrode", NXelectromagnetic_lens.class));
		}

		// validate optional child group 'ion_detector' of type NXdetector
		if (group.getIon_detector() != null) {
			validateGroup_NXentry_measurement_instrument_ion_detector(group.getIon_detector());
		}

		// validate optional child group 'pulser' of type NXcomponent
		if (group.getPulser() != null) {
			validateGroup_NXentry_measurement_instrument_pulser(group.getPulser());
		}

		// validate optional child group 'stage' of type NXmanipulator
		if (group.getStage() != null) {
			validateGroup_NXentry_measurement_instrument_stage(group.getStage());
		}

		// validate optional child group 'analysis_chamber' of type NXcomponent
		if (group.getAnalysis_chamber() != null) {
			validateGroup_NXentry_measurement_instrument_analysis_chamber(group.getAnalysis_chamber());
		}

		// validate optional child group 'buffer_chamber' of type NXcomponent
		if (group.getBuffer_chamber() != null) {
			validateGroup_NXentry_measurement_instrument_buffer_chamber(group.getBuffer_chamber());
		}

		// validate optional child group 'load_lock_chamber' of type NXcomponent
		if (group.getLoad_lock_chamber() != null) {
			validateGroup_NXentry_measurement_instrument_load_lock_chamber(group.getLoad_lock_chamber());
		}

		// validate optional child group 'getter_pump' of type NXpump
		if (group.getGetter_pump() != null) {
			validateGroup_NXentry_measurement_instrument_getter_pump(group.getGetter_pump());
		}

		// validate optional child group 'roughening_pump' of type NXpump
		if (group.getRoughening_pump() != null) {
			validateGroup_NXentry_measurement_instrument_roughening_pump(group.getRoughening_pump());
		}

		// validate optional child group 'turbomolecular_pump' of type NXpump
		if (group.getTurbomolecular_pump() != null) {
			validateGroup_NXentry_measurement_instrument_turbomolecular_pump(group.getTurbomolecular_pump());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'reflectron' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_instrument_reflectron(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("reflectron", NXcomponent.class, group))) return;

		// validate field 'applied' of type NX_BOOLEAN.
		final ILazyDataset applied = group.getLazyDataset("applied");
		validateFieldNotNull("applied", applied);
		if (applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("applied", applied, NX_BOOLEAN);
		}
	}

	/**
	 * Validate optional group 'local_electrode' of type NXelectromagnetic_lens.
	 */
	private void validateGroup_NXentry_measurement_instrument_local_electrode(final NXelectromagnetic_lens group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("local_electrode", NXelectromagnetic_lens.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'aperture_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset aperture_type = group.getLazyDataset("aperture_type");
				if (aperture_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("aperture_type", aperture_type, NX_CHAR);
		}
		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_local_electrode_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_local_electrode_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'ion_detector' of type NXdetector.
	 */
	private void validateGroup_NXentry_measurement_instrument_ion_detector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ion_detector", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ion_detector_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ion_detector_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'pulser' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_instrument_pulser(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pulser", NXcomponent.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_pulser_fabrication(group.getFabrication());
		}

		// validate optional child group 'sourceID' of type NXsource
		if (group.getChild("sourceID", NXsource.class) != null) {
			validateGroup_NXentry_measurement_instrument_pulser_sourceID(group.getChild("sourceID", NXsource.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_pulser_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'sourceID' of type NXsource.
	 */
	private void validateGroup_NXentry_measurement_instrument_pulser_sourceID(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sourceID", NXsource.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_pulser_sourceID_fabrication(group.getFabrication());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_pulser_sourceID_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'stage' of type NXmanipulator.
	 */
	private void validateGroup_NXentry_measurement_instrument_stage(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stage", NXmanipulator.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_stage_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_stage_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'analysis_chamber' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_instrument_analysis_chamber(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("analysis_chamber", NXcomponent.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_analysis_chamber_fabrication(group.getFabrication());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_analysis_chamber_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'buffer_chamber' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_instrument_buffer_chamber(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("buffer_chamber", NXcomponent.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_buffer_chamber_fabrication(group.getFabrication());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_buffer_chamber_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'load_lock_chamber' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_instrument_load_lock_chamber(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("load_lock_chamber", NXcomponent.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_load_lock_chamber_fabrication(group.getFabrication());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_load_lock_chamber_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'getter_pump' of type NXpump.
	 */
	private void validateGroup_NXentry_measurement_instrument_getter_pump(final NXpump group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("getter_pump", NXpump.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_getter_pump_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_getter_pump_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'roughening_pump' of type NXpump.
	 */
	private void validateGroup_NXentry_measurement_instrument_roughening_pump(final NXpump group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("roughening_pump", NXpump.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_roughening_pump_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_roughening_pump_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'turbomolecular_pump' of type NXpump.
	 */
	private void validateGroup_NXentry_measurement_instrument_turbomolecular_pump(final NXpump group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("turbomolecular_pump", NXpump.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_turbomolecular_pump_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_turbomolecular_pump_fabrication(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fabrication", NXfabrication.class, group))) return;

		// validate field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
		validateFieldNotNull("vendor", vendor);
		if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'serial_number' of type NX_CHAR.
		final ILazyDataset serial_number = group.getLazyDataset("serial_number");
				if (serial_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("serial_number", serial_number, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'eventID' of type NXapm_event_data.
	 */
	private void validateGroup_NXentry_measurement_eventID(final NXapm_event_data group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("eventID", NXapm_event_data.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
				if (start_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start_time", start_time, NX_DATE_TIME);
		}

		// validate optional field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
				if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
		}

		// validate optional child group 'instrument' of type NXapm_instrument
		if (group.getInstrument() != null) {
			validateGroup_NXentry_measurement_eventID_instrument(group.getInstrument());
		}
	}

	/**
	 * Validate optional group 'instrument' of type NXapm_instrument.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument(final NXapm_instrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXapm_instrument.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional child group 'reflectron' of type NXcomponent
		if (group.getReflectron() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_reflectron(group.getReflectron());
		}

		// validate optional child group 'local_electrode' of type NXelectromagnetic_lens
		if (group.getChild("local_electrode", NXelectromagnetic_lens.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_local_electrode(group.getChild("local_electrode", NXelectromagnetic_lens.class));
		}

		// validate optional child group 'pulser' of type NXcomponent
		if (group.getPulser() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_pulser(group.getPulser());
		}

		// validate child group 'stage' of type NXmanipulator
		validateGroup_NXentry_measurement_eventID_instrument_stage(group.getStage());

		// validate child group 'analysis_chamber' of type NXcomponent
		validateGroup_NXentry_measurement_eventID_instrument_analysis_chamber(group.getAnalysis_chamber());

		// validate optional child group 'control' of type NXparameters
		if (group.getControl() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_control(group.getControl());
		}
	}

	/**
	 * Validate optional group 'reflectron' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_reflectron(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("reflectron", NXcomponent.class, group))) return;

		// validate field 'voltage' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset voltage = group.getLazyDataset("voltage");
		validateFieldNotNull("voltage", voltage);
		if (voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("voltage", voltage, NX_FLOAT);
		}
	}

	/**
	 * Validate optional group 'local_electrode' of type NXelectromagnetic_lens.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_local_electrode(final NXelectromagnetic_lens group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("local_electrode", NXelectromagnetic_lens.class, group))) return;

		// validate field 'voltage' of type NX_FLOAT.
		final ILazyDataset voltage = group.getLazyDataset("voltage");
		validateFieldNotNull("voltage", voltage);
		if (voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("voltage", voltage, NX_FLOAT);
			validateFieldUnits("voltage", group.getDataNode("voltage"), NX_VOLTAGE);
		}
	}

	/**
	 * Validate optional group 'pulser' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_pulser(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pulser", NXcomponent.class, group))) return;

		// validate field 'pulse_mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset pulse_mode = group.getLazyDataset("pulse_mode");
		validateFieldNotNull("pulse_mode", pulse_mode);
		if (pulse_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pulse_mode", pulse_mode, NX_CHAR);
		}

		// validate field 'pulse_frequency' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset pulse_frequency = group.getLazyDataset("pulse_frequency");
		validateFieldNotNull("pulse_frequency", pulse_frequency);
		if (pulse_frequency != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pulse_frequency", pulse_frequency, NX_FLOAT);
		}

		// validate field 'pulse_fraction' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset pulse_fraction = group.getLazyDataset("pulse_fraction");
		validateFieldNotNull("pulse_fraction", pulse_fraction);
		if (pulse_fraction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pulse_fraction", pulse_fraction, NX_FLOAT);
		}

		// validate optional field 'pulse_voltage' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset pulse_voltage = group.getLazyDataset("pulse_voltage");
				if (pulse_voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pulse_voltage", pulse_voltage, NX_FLOAT);
			validateFieldRank("pulse_voltage", pulse_voltage, 1);
			validateFieldDimensions("pulse_voltage", pulse_voltage, null, "n");
		}

		// validate optional field 'pulse_number' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset pulse_number = group.getLazyDataset("pulse_number");
				if (pulse_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pulse_number", pulse_number, NX_UINT);
			validateFieldRank("pulse_number", pulse_number, 1);
			validateFieldDimensions("pulse_number", pulse_number, null, "n");
		}

		// validate optional field 'standing_voltage' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset standing_voltage = group.getLazyDataset("standing_voltage");
				if (standing_voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("standing_voltage", standing_voltage, NX_FLOAT);
			validateFieldRank("standing_voltage", standing_voltage, 1);
			validateFieldDimensions("standing_voltage", standing_voltage, null, "n");
		}

		// validate optional child group 'sourceID' of type NXsource
		if (group.getChild("sourceID", NXsource.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_pulser_sourceID(group.getChild("sourceID", NXsource.class));
		}
	}

	/**
	 * Validate optional group 'sourceID' of type NXsource.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_pulser_sourceID(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sourceID", NXsource.class, group))) return;

		// validate optional field 'wavelength' of type NX_FLOAT.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
				if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_FLOAT);
			validateFieldUnits("wavelength", group.getDataNode("wavelength"), NX_WAVELENGTH);
		}

		// validate field 'power' of type NX_FLOAT.
		final ILazyDataset power = group.getLazyDataset("power");
		validateFieldNotNull("power", power);
		if (power != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("power", power, NX_FLOAT);
			validateFieldUnits("power", group.getDataNode("power"), NX_POWER);
		}

		// validate field 'pulse_energy' of type NX_FLOAT.
		final ILazyDataset pulse_energy = group.getLazyDataset("pulse_energy");
		validateFieldNotNull("pulse_energy", pulse_energy);
		if (pulse_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pulse_energy", pulse_energy, NX_FLOAT);
			validateFieldUnits("pulse_energy", group.getDataNode("pulse_energy"), NX_ENERGY);
			validateFieldRank("pulse_energy", pulse_energy, 1);
			validateFieldDimensions("pulse_energy", pulse_energy, null, "n");
		}
	}

	/**
	 * Validate group 'stage' of type NXmanipulator.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_stage(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stage", NXmanipulator.class, group))) return;

		// validate child group 'temperature_sensor' of type NXsensor
		validateGroup_NXentry_measurement_eventID_instrument_stage_temperature_sensor(group.getTemperature_sensor());
	}

	/**
	 * Validate group 'temperature_sensor' of type NXsensor.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_stage_temperature_sensor(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("temperature_sensor", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'measurement' of type NX_CHAR.
		final ILazyDataset measurement = group.getLazyDataset("measurement");
		validateFieldNotNull("measurement", measurement);
		if (measurement != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measurement", measurement, NX_CHAR);
			validateFieldEnumeration("measurement", measurement,
					"temperature",
					"pH",
					"magnetic_field",
					"electric_field",
					"current",
					"conductivity",
					"resistance",
					"voltage",
					"pressure",
					"flow",
					"stress",
					"strain",
					"shear",
					"surface_pressure");
		}

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_ANY);
			validateFieldDimensions("value", value, "NXsensor", "n");
		}
	}

	/**
	 * Validate group 'analysis_chamber' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_analysis_chamber(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("analysis_chamber", NXcomponent.class, group))) return;

		// validate child group 'pressure_sensor' of type NXsensor
		validateGroup_NXentry_measurement_eventID_instrument_analysis_chamber_pressure_sensor(group.getChild("pressure_sensor", NXsensor.class));
	}

	/**
	 * Validate group 'pressure_sensor' of type NXsensor.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_analysis_chamber_pressure_sensor(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pressure_sensor", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'measurement' of type NX_CHAR.
		final ILazyDataset measurement = group.getLazyDataset("measurement");
		validateFieldNotNull("measurement", measurement);
		if (measurement != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measurement", measurement, NX_CHAR);
			validateFieldEnumeration("measurement", measurement,
					"temperature",
					"pH",
					"magnetic_field",
					"electric_field",
					"current",
					"conductivity",
					"resistance",
					"voltage",
					"pressure",
					"flow",
					"stress",
					"strain",
					"shear",
					"surface_pressure");
		}

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_ANY);
			validateFieldDimensions("value", value, "NXsensor", "n");
		}
	}

	/**
	 * Validate optional group 'control' of type NXparameters.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_control(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("control", NXparameters.class, group))) return;

		// validate field 'evaporation_control' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset evaporation_control = group.getLazyDataset("evaporation_control");
		validateFieldNotNull("evaporation_control", evaporation_control);
		if (evaporation_control != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("evaporation_control", evaporation_control, NX_CHAR);
		}

		// validate field 'target_detection_rate' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset target_detection_rate = group.getLazyDataset("target_detection_rate");
		validateFieldNotNull("target_detection_rate", target_detection_rate);
		if (target_detection_rate != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("target_detection_rate", target_detection_rate, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'simulation' of type NXapm_simulation.
	 */
	private void validateGroup_NXentry_simulation(final NXapm_simulation group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("simulation", NXapm_simulation.class, group))) return;

	}

	/**
	 * Validate optional group 'atom_probeID' of type NXroi_process.
	 */
	private void validateGroup_NXentry_atom_probeID(final NXroi_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("atom_probeID", NXroi_process.class, group))) return;

		// validate optional child group 'initial_specimen' of type NXimage
		if (group.getChild("initial_specimen", NXimage.class) != null) {
			validateGroup_NXentry_atom_probeID_initial_specimen(group.getChild("initial_specimen", NXimage.class));
		}

		// validate optional child group 'final_specimen' of type NXimage
		if (group.getChild("final_specimen", NXimage.class) != null) {
			validateGroup_NXentry_atom_probeID_final_specimen(group.getChild("final_specimen", NXimage.class));
		}

		// validate optional child group 'raw_data' of type NXprocess
		if (group.getProcess("raw_data") != null) {
			validateGroup_NXentry_atom_probeID_raw_data(group.getProcess("raw_data"));
		}

		// validate optional child group 'hit_finding' of type NXprocess
		if (group.getProcess("hit_finding") != null) {
			validateGroup_NXentry_atom_probeID_hit_finding(group.getProcess("hit_finding"));
		}

		// validate optional child group 'hit_spatial_filtering' of type NXprocess
		if (group.getProcess("hit_spatial_filtering") != null) {
			validateGroup_NXentry_atom_probeID_hit_spatial_filtering(group.getProcess("hit_spatial_filtering"));
		}

		// validate optional child group 'voltage_and_bowl' of type NXprocess
		if (group.getProcess("voltage_and_bowl") != null) {
			validateGroup_NXentry_atom_probeID_voltage_and_bowl(group.getProcess("voltage_and_bowl"));
		}

		// validate optional child group 'mass_to_charge_conversion' of type NXprocess
		if (group.getProcess("mass_to_charge_conversion") != null) {
			validateGroup_NXentry_atom_probeID_mass_to_charge_conversion(group.getProcess("mass_to_charge_conversion"));
		}

		// validate optional child group 'reconstruction' of type NXapm_reconstruction
		if (group.getChild("reconstruction", NXapm_reconstruction.class) != null) {
			validateGroup_NXentry_atom_probeID_reconstruction(group.getChild("reconstruction", NXapm_reconstruction.class));
		}

		// validate optional child group 'ranging' of type NXapm_ranging
		if (group.getChild("ranging", NXapm_ranging.class) != null) {
			validateGroup_NXentry_atom_probeID_ranging(group.getChild("ranging", NXapm_ranging.class));
		}
	}

	/**
	 * Validate optional group 'initial_specimen' of type NXimage.
	 */
	private void validateGroup_NXentry_atom_probeID_initial_specimen(final NXimage group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("initial_specimen", NXimage.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate child group 'image_2d' of type NXdata
		validateGroup_NXentry_atom_probeID_initial_specimen_image_2d(group.getImage_2d());
	}

	/**
	 * Validate group 'image_2d' of type NXdata.
	 */
	private void validateGroup_NXentry_atom_probeID_initial_specimen_image_2d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("image_2d", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate attribute 'AXISNAME_indices' of type NX_UINT.
		final Attribute AXISNAME_indices_attr = group.getAttribute("AXISNAME_indices");
		if (!(validateAttributeNotNull("AXISNAME_indices", AXISNAME_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("AXISNAME_indices", AXISNAME_indices_attr, NX_UINT);

		// validate field 'real' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset real = group.getLazyDataset("real");
		validateFieldNotNull("real", real);
		if (real != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("real", real, NX_NUMBER);
		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
			validateFieldRank("axis_j", axis_j, 1);
			validateFieldDimensions("axis_j", axis_j, null, "n_j");
		// validate attribute 'long_name' of field 'axis_j' of type NX_CHAR.
		final Attribute axis_j_attr_long_name = group.getDataNode("axis_j").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_j_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_j_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_i' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_i = group.getLazyDataset("axis_i");
		validateFieldNotNull("axis_i", axis_i);
		if (axis_i != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_i", axis_i, NX_NUMBER);
			validateFieldRank("axis_i", axis_i, 1);
			validateFieldDimensions("axis_i", axis_i, null, "n_i");
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'final_specimen' of type NXimage.
	 */
	private void validateGroup_NXentry_atom_probeID_final_specimen(final NXimage group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("final_specimen", NXimage.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate child group 'image_2d' of type NXdata
		validateGroup_NXentry_atom_probeID_final_specimen_image_2d(group.getImage_2d());
	}

	/**
	 * Validate group 'image_2d' of type NXdata.
	 */
	private void validateGroup_NXentry_atom_probeID_final_specimen_image_2d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("image_2d", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate attribute 'AXISNAME_indices' of type NX_UINT.
		final Attribute AXISNAME_indices_attr = group.getAttribute("AXISNAME_indices");
		if (!(validateAttributeNotNull("AXISNAME_indices", AXISNAME_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("AXISNAME_indices", AXISNAME_indices_attr, NX_UINT);

		// validate field 'real' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset real = group.getLazyDataset("real");
		validateFieldNotNull("real", real);
		if (real != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("real", real, NX_NUMBER);
		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
			validateFieldRank("axis_j", axis_j, 1);
			validateFieldDimensions("axis_j", axis_j, null, "n_j");
		// validate attribute 'long_name' of field 'axis_j' of type NX_CHAR.
		final Attribute axis_j_attr_long_name = group.getDataNode("axis_j").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_j_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_j_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_i' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_i = group.getLazyDataset("axis_i");
		validateFieldNotNull("axis_i", axis_i);
		if (axis_i != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_i", axis_i, NX_NUMBER);
			validateFieldRank("axis_i", axis_i, 1);
			validateFieldDimensions("axis_i", axis_i, null, "n_i");
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'raw_data' of type NXprocess.
	 */
	private void validateGroup_NXentry_atom_probeID_raw_data(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("raw_data", NXprocess.class, group))) return;

		// validate optional field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate optional field 'number_of_dld_wires' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_dld_wires = group.getLazyDataset("number_of_dld_wires");
				if (number_of_dld_wires != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_dld_wires", number_of_dld_wires, NX_UINT);
			validateFieldUnits("number_of_dld_wires", group.getDataNode("number_of_dld_wires"), NX_UNITLESS);
			validateFieldEnumeration("number_of_dld_wires", number_of_dld_wires,
					"1",
					"2",
					"3");
		}

		// validate optional field 'dld_wire_names' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset dld_wire_names = group.getLazyDataset("dld_wire_names");
				if (dld_wire_names != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dld_wire_names", dld_wire_names, NX_CHAR);
			validateFieldRank("dld_wire_names", dld_wire_names, 2);
			validateFieldDimensions("dld_wire_names", dld_wire_names, null, "n_dld", 2);
		}

		// validate optional field 'arrival_time_pairs' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset arrival_time_pairs = group.getLazyDataset("arrival_time_pairs");
				if (arrival_time_pairs != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("arrival_time_pairs", arrival_time_pairs, NX_NUMBER);
			validateFieldUnits("arrival_time_pairs", group.getDataNode("arrival_time_pairs"), NX_TIME);
			validateFieldRank("arrival_time_pairs", arrival_time_pairs, 3);
			validateFieldDimensions("arrival_time_pairs", arrival_time_pairs, null, "p", "n_dld", 2);
		}
		// validate optional child group 'programID' of type NXprogram
		if (group.getChild("programID", NXprogram.class) != null) {
			validateGroup_NXentry_atom_probeID_raw_data_programID(group.getChild("programID", NXprogram.class));
		}

		// validate optional child group 'source' of type NXnote
		if (group.getNote("source") != null) {
			validateGroup_NXentry_atom_probeID_raw_data_source(group.getNote("source"));
		}
	}

	/**
	 * Validate optional group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_raw_data_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'source' of type NXnote.
	 */
	private void validateGroup_NXentry_atom_probeID_raw_data_source(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source", NXnote.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'hit_finding' of type NXprocess.
	 */
	private void validateGroup_NXentry_atom_probeID_hit_finding(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("hit_finding", NXprocess.class, group))) return;

		// validate optional field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate optional field 'hit_positions' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset hit_positions = group.getLazyDataset("hit_positions");
				if (hit_positions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("hit_positions", hit_positions, NX_NUMBER);
			validateFieldUnits("hit_positions", group.getDataNode("hit_positions"), NX_LENGTH);
			validateFieldRank("hit_positions", hit_positions, 2);
			validateFieldDimensions("hit_positions", hit_positions, null, "p_out", 2);
		// validate attribute 'depends_on' of field 'hit_positions' of type NX_CHAR.
		final Attribute hit_positions_attr_depends_on = group.getDataNode("hit_positions").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", hit_positions_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", hit_positions_attr_depends_on, NX_CHAR);

		}

		// validate optional field 'total_event_golden' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset total_event_golden = group.getLazyDataset("total_event_golden");
				if (total_event_golden != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_event_golden", total_event_golden, NX_UINT);
			validateFieldUnits("total_event_golden", group.getDataNode("total_event_golden"), NX_UNITLESS);
		}

		// validate optional field 'total_event_incomplete' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset total_event_incomplete = group.getLazyDataset("total_event_incomplete");
				if (total_event_incomplete != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_event_incomplete", total_event_incomplete, NX_UINT);
			validateFieldUnits("total_event_incomplete", group.getDataNode("total_event_incomplete"), NX_UNITLESS);
		}

		// validate optional field 'total_event_multiple' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset total_event_multiple = group.getLazyDataset("total_event_multiple");
				if (total_event_multiple != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_event_multiple", total_event_multiple, NX_UINT);
			validateFieldUnits("total_event_multiple", group.getDataNode("total_event_multiple"), NX_UNITLESS);
		}

		// validate optional field 'total_event_partials' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset total_event_partials = group.getLazyDataset("total_event_partials");
				if (total_event_partials != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_event_partials", total_event_partials, NX_UINT);
			validateFieldUnits("total_event_partials", group.getDataNode("total_event_partials"), NX_UNITLESS);
		}

		// validate optional field 'total_event_record' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset total_event_record = group.getLazyDataset("total_event_record");
				if (total_event_record != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_event_record", total_event_record, NX_UINT);
			validateFieldUnits("total_event_record", group.getDataNode("total_event_record"), NX_UNITLESS);
		}

		// validate optional field 'hit_quality_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset hit_quality_type = group.getLazyDataset("hit_quality_type");
				if (hit_quality_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("hit_quality_type", hit_quality_type, NX_CHAR);
			validateFieldRank("hit_quality_type", hit_quality_type, 1);
			validateFieldDimensions("hit_quality_type", hit_quality_type, null, "n_ht");
		}

		// validate optional field 'hit_quality' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset hit_quality = group.getLazyDataset("hit_quality");
				if (hit_quality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("hit_quality", hit_quality, NX_UINT);
			validateFieldUnits("hit_quality", group.getDataNode("hit_quality"), NX_UNITLESS);
			validateFieldRank("hit_quality", hit_quality, 1);
			validateFieldDimensions("hit_quality", hit_quality, null, "p_out");
		}

		// validate optional field 'hit_multiplicity' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset hit_multiplicity = group.getLazyDataset("hit_multiplicity");
				if (hit_multiplicity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("hit_multiplicity", hit_multiplicity, NX_UINT);
			validateFieldUnits("hit_multiplicity", group.getDataNode("hit_multiplicity"), NX_UNITLESS);
			validateFieldRank("hit_multiplicity", hit_multiplicity, 1);
			validateFieldDimensions("hit_multiplicity", hit_multiplicity, null, "p_out");
		}
		// validate optional child group 'programID' of type NXprogram
		if (group.getChild("programID", NXprogram.class) != null) {
			validateGroup_NXentry_atom_probeID_hit_finding_programID(group.getChild("programID", NXprogram.class));
		}

		// validate optional child group 'config' of type NXnote
		if (group.getNote("config") != null) {
			validateGroup_NXentry_atom_probeID_hit_finding_config(group.getNote("config"));
		}
	}

	/**
	 * Validate optional group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_hit_finding_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'config' of type NXnote.
	 */
	private void validateGroup_NXentry_atom_probeID_hit_finding_config(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("config", NXnote.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'hit_spatial_filtering' of type NXprocess.
	 */
	private void validateGroup_NXentry_atom_probeID_hit_spatial_filtering(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("hit_spatial_filtering", NXprocess.class, group))) return;

		// validate optional field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate field 'evaporation_id_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset evaporation_id_offset = group.getLazyDataset("evaporation_id_offset");
		validateFieldNotNull("evaporation_id_offset", evaporation_id_offset);
		if (evaporation_id_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("evaporation_id_offset", evaporation_id_offset, NX_INT);
			validateFieldUnits("evaporation_id_offset", group.getDataNode("evaporation_id_offset"), NX_UNITLESS);
		}

		// validate field 'evaporation_id' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset evaporation_id = group.getLazyDataset("evaporation_id");
		validateFieldNotNull("evaporation_id", evaporation_id);
		if (evaporation_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("evaporation_id", evaporation_id, NX_INT);
			validateFieldUnits("evaporation_id", group.getDataNode("evaporation_id"), NX_UNITLESS);
			validateFieldRank("evaporation_id", evaporation_id, 1);
			validateFieldDimensions("evaporation_id", evaporation_id, null, "n");
		}

		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_atom_probeID_hit_spatial_filtering_programID(group.getChild("programID", NXprogram.class));

		// validate optional child group 'source' of type NXnote
		if (group.getNote("source") != null) {
			validateGroup_NXentry_atom_probeID_hit_spatial_filtering_source(group.getNote("source"));
		}

		// validate optional child group 'hit_filter' of type NXcs_filter_boolean_mask
		if (group.getChild("hit_filter", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_atom_probeID_hit_spatial_filtering_hit_filter(group.getChild("hit_filter", NXcs_filter_boolean_mask.class));
		}
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_hit_spatial_filtering_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'source' of type NXnote.
	 */
	private void validateGroup_NXentry_atom_probeID_hit_spatial_filtering_source(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source", NXnote.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'hit_filter' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_atom_probeID_hit_spatial_filtering_hit_filter(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("hit_filter", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_objects' of type NX_UINT.
		final ILazyDataset number_of_objects = group.getLazyDataset("number_of_objects");
		validateFieldNotNull("number_of_objects", number_of_objects);
		if (number_of_objects != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_objects", number_of_objects, NX_UINT);
			validateFieldUnits("number_of_objects", group.getDataNode("number_of_objects"), NX_UNITLESS);
		}

		// validate field 'bitdepth' of type NX_UINT.
		final ILazyDataset bitdepth = group.getLazyDataset("bitdepth");
		validateFieldNotNull("bitdepth", bitdepth);
		if (bitdepth != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("bitdepth", bitdepth, NX_UINT);
			validateFieldUnits("bitdepth", group.getDataNode("bitdepth"), NX_UNITLESS);
		}

		// validate field 'mask' of type NX_UINT.
		final ILazyDataset mask = group.getLazyDataset("mask");
		validateFieldNotNull("mask", mask);
		if (mask != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mask", mask, NX_UINT);
			validateFieldUnits("mask", group.getDataNode("mask"), NX_UNITLESS);
		}
	}

	/**
	 * Validate optional group 'voltage_and_bowl' of type NXprocess.
	 */
	private void validateGroup_NXentry_atom_probeID_voltage_and_bowl(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("voltage_and_bowl", NXprocess.class, group))) return;

		// validate optional field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate optional field 'raw_tof' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset raw_tof = group.getLazyDataset("raw_tof");
				if (raw_tof != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("raw_tof", raw_tof, NX_FLOAT);
			validateFieldRank("raw_tof", raw_tof, 1);
			validateFieldDimensions("raw_tof", raw_tof, null, "n");
		}

		// validate optional field 'tof_zero_estimate' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset tof_zero_estimate = group.getLazyDataset("tof_zero_estimate");
				if (tof_zero_estimate != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("tof_zero_estimate", tof_zero_estimate, NX_FLOAT);
			validateFieldUnits("tof_zero_estimate", group.getDataNode("tof_zero_estimate"), NX_TIME);
		}

		// validate optional field 'calibrated_tof' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset calibrated_tof = group.getLazyDataset("calibrated_tof");
				if (calibrated_tof != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibrated_tof", calibrated_tof, NX_FLOAT);
			validateFieldRank("calibrated_tof", calibrated_tof, 1);
			validateFieldDimensions("calibrated_tof", calibrated_tof, null, "n");
		}
		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_atom_probeID_voltage_and_bowl_programID(group.getChild("programID", NXprogram.class));

		// validate optional child group 'source' of type NXnote
		if (group.getNote("source") != null) {
			validateGroup_NXentry_atom_probeID_voltage_and_bowl_source(group.getNote("source"));
		}

		// validate child group 'config' of type NXparameters
		validateGroup_NXentry_atom_probeID_voltage_and_bowl_config(group.getParameters("config"));
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_voltage_and_bowl_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'source' of type NXnote.
	 */
	private void validateGroup_NXentry_atom_probeID_voltage_and_bowl_source(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source", NXnote.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}
	}

	/**
	 * Validate group 'config' of type NXparameters.
	 */
	private void validateGroup_NXentry_atom_probeID_voltage_and_bowl_config(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("config", NXparameters.class, group))) return;

		// validate optional field 'correction_peak' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset correction_peak = group.getLazyDataset("correction_peak");
				if (correction_peak != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("correction_peak", correction_peak, NX_FLOAT);
			validateFieldUnits("correction_peak", group.getDataNode("correction_peak"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'mass_to_charge_conversion' of type NXprocess.
	 */
	private void validateGroup_NXentry_atom_probeID_mass_to_charge_conversion(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("mass_to_charge_conversion", NXprocess.class, group))) return;

		// validate optional field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate field 'mass_to_charge' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset mass_to_charge = group.getLazyDataset("mass_to_charge");
		validateFieldNotNull("mass_to_charge", mass_to_charge);
		if (mass_to_charge != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mass_to_charge", mass_to_charge, NX_FLOAT);
			validateFieldUnits("mass_to_charge", group.getDataNode("mass_to_charge"), NX_ANY);
			validateFieldRank("mass_to_charge", mass_to_charge, 1);
			validateFieldDimensions("mass_to_charge", mass_to_charge, null, "n");
		}
		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_atom_probeID_mass_to_charge_conversion_programID(group.getChild("programID", NXprogram.class));

		// validate optional child group 'source' of type NXnote
		if (group.getNote("source") != null) {
			validateGroup_NXentry_atom_probeID_mass_to_charge_conversion_source(group.getNote("source"));
		}

		// validate optional child group 'config' of type NXparameters
		if (group.getParameters("config") != null) {
			validateGroup_NXentry_atom_probeID_mass_to_charge_conversion_config(group.getParameters("config"));
		}
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_mass_to_charge_conversion_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'source' of type NXnote.
	 */
	private void validateGroup_NXentry_atom_probeID_mass_to_charge_conversion_source(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source", NXnote.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'config' of type NXparameters.
	 */
	private void validateGroup_NXentry_atom_probeID_mass_to_charge_conversion_config(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("config", NXparameters.class, group))) return;

		// validate optional field 'mass_calibration' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset mass_calibration = group.getLazyDataset("mass_calibration");
				if (mass_calibration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mass_calibration", mass_calibration, NX_FLOAT);
			validateFieldUnits("mass_calibration", group.getDataNode("mass_calibration"), NX_ANY);
		}

		// validate optional field 'mass_resolution' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset mass_resolution = group.getLazyDataset("mass_resolution");
				if (mass_resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mass_resolution", mass_resolution, NX_FLOAT);
			validateFieldUnits("mass_resolution", group.getDataNode("mass_resolution"), NX_ANY);
			validateFieldRank("mass_resolution", mass_resolution, 1);
			validateFieldDimensions("mass_resolution", mass_resolution, null, "m_r");
		}

		// validate optional field 'mass_resolution_fw' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset mass_resolution_fw = group.getLazyDataset("mass_resolution_fw");
				if (mass_resolution_fw != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mass_resolution_fw", mass_resolution_fw, NX_FLOAT);
			validateFieldUnits("mass_resolution_fw", group.getDataNode("mass_resolution_fw"), NX_ANY);
			validateFieldRank("mass_resolution_fw", mass_resolution_fw, 1);
			validateFieldDimensions("mass_resolution_fw", mass_resolution_fw, null, "m_r");
		}

		// validate optional child group 'mass_resolutionION' of type NXatom
		if (group.getChild("mass_resolutionION", NXatom.class) != null) {
			validateGroup_NXentry_atom_probeID_mass_to_charge_conversion_config_mass_resolutionION(group.getChild("mass_resolutionION", NXatom.class));
		}
	}

	/**
	 * Validate optional group 'mass_resolutionION' of type NXatom.
	 */
	private void validateGroup_NXentry_atom_probeID_mass_to_charge_conversion_config_mass_resolutionION(final NXatom group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("mass_resolutionION", NXatom.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'nuclide_hash' of type NX_UINT.
		final ILazyDataset nuclide_hash = group.getLazyDataset("nuclide_hash");
				if (nuclide_hash != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("nuclide_hash", nuclide_hash, NX_UINT);
			validateFieldUnits("nuclide_hash", group.getDataNode("nuclide_hash"), NX_UNITLESS);
			validateFieldRank("nuclide_hash", nuclide_hash, 1);
			validateFieldDimensions("nuclide_hash", nuclide_hash, "NXatom", "n_ivec_max");
		}

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'reconstruction' of type NXapm_reconstruction.
	 */
	private void validateGroup_NXentry_atom_probeID_reconstruction(final NXapm_reconstruction group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("reconstruction", NXapm_reconstruction.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'sequence_index' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate field 'reconstructed_positions' of type NX_FLOAT.
		final ILazyDataset reconstructed_positions = group.getLazyDataset("reconstructed_positions");
		validateFieldNotNull("reconstructed_positions", reconstructed_positions);
		if (reconstructed_positions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("reconstructed_positions", reconstructed_positions, NX_FLOAT);
			validateFieldUnits("reconstructed_positions", group.getDataNode("reconstructed_positions"), NX_LENGTH);
			validateFieldRank("reconstructed_positions", reconstructed_positions, 2);
			validateFieldDimensions("reconstructed_positions", reconstructed_positions, null, "n", 3);
		}

		// validate optional field 'volume' of type NX_FLOAT.
		final ILazyDataset volume = group.getLazyDataset("volume");
				if (volume != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("volume", volume, NX_FLOAT);
			validateFieldUnits("volume", group.getDataNode("volume"), NX_VOLUME);
		}

		// validate optional field 'field_of_view' of type NX_FLOAT.
		final ILazyDataset field_of_view = group.getLazyDataset("field_of_view");
				if (field_of_view != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("field_of_view", field_of_view, NX_FLOAT);
			validateFieldUnits("field_of_view", group.getDataNode("field_of_view"), NX_LENGTH);
		}
		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_atom_probeID_reconstruction_programID(group.getNXProgram("programID"));

		// validate optional child group 'source' of type NXnote
		if (group.getNote("source") != null) {
			validateGroup_NXentry_atom_probeID_reconstruction_source(group.getNote("source"));
		}

		// validate optional child group 'results' of type NXnote
		if (group.getNote("results") != null) {
			validateGroup_NXentry_atom_probeID_reconstruction_results(group.getNote("results"));
		}

		// validate optional child group 'config' of type NXparameters
		if (group.getConfig() != null) {
			validateGroup_NXentry_atom_probeID_reconstruction_config(group.getConfig());
		}

		// validate child group 'naive_discretization' of type NXprocess
		validateGroup_NXentry_atom_probeID_reconstruction_naive_discretization(group.getNaive_discretization());
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_reconstruction_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'source' of type NXnote.
	 */
	private void validateGroup_NXentry_atom_probeID_reconstruction_source(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source", NXnote.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'results' of type NXnote.
	 */
	private void validateGroup_NXentry_atom_probeID_reconstruction_results(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("results", NXnote.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'config' of type NXparameters.
	 */
	private void validateGroup_NXentry_atom_probeID_reconstruction_config(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("config", NXparameters.class, group))) return;

		// validate optional field 'voltage_filter_initial' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset voltage_filter_initial = group.getLazyDataset("voltage_filter_initial");
				if (voltage_filter_initial != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("voltage_filter_initial", voltage_filter_initial, NX_FLOAT);
		}

		// validate optional field 'voltage_filter_final' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset voltage_filter_final = group.getLazyDataset("voltage_filter_final");
				if (voltage_filter_final != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("voltage_filter_final", voltage_filter_final, NX_FLOAT);
		}

		// validate optional field 'protocol_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset protocol_name = group.getLazyDataset("protocol_name");
				if (protocol_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("protocol_name", protocol_name, NX_CHAR);
		}

		// validate optional field 'primary_element' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset primary_element = group.getLazyDataset("primary_element");
				if (primary_element != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_element", primary_element, NX_CHAR);
		}

		// validate optional field 'efficiency' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset efficiency = group.getLazyDataset("efficiency");
				if (efficiency != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("efficiency", efficiency, NX_FLOAT);
		}

		// validate optional field 'flight_path' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset flight_path = group.getLazyDataset("flight_path");
				if (flight_path != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flight_path", flight_path, NX_FLOAT);
		}

		// validate optional field 'evaporation_field' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset evaporation_field = group.getLazyDataset("evaporation_field");
				if (evaporation_field != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("evaporation_field", evaporation_field, NX_CHAR);
		}

		// validate optional field 'image_compression' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset image_compression = group.getLazyDataset("image_compression");
				if (image_compression != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("image_compression", image_compression, NX_FLOAT);
		}

		// validate optional field 'kfactor' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset kfactor = group.getLazyDataset("kfactor");
				if (kfactor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kfactor", kfactor, NX_FLOAT);
		}

		// validate optional field 'shank_angle' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset shank_angle = group.getLazyDataset("shank_angle");
				if (shank_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("shank_angle", shank_angle, NX_FLOAT);
		}

		// validate optional field 'ion_volume' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset ion_volume = group.getLazyDataset("ion_volume");
				if (ion_volume != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ion_volume", ion_volume, NX_FLOAT);
		}

		// validate optional field 'crystallographic_calibration' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset crystallographic_calibration = group.getLazyDataset("crystallographic_calibration");
				if (crystallographic_calibration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("crystallographic_calibration", crystallographic_calibration, NX_CHAR);
		}

		// validate optional field 'comment' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset comment = group.getLazyDataset("comment");
				if (comment != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("comment", comment, NX_CHAR);
		}
	}

	/**
	 * Validate group 'naive_discretization' of type NXprocess.
	 */
	private void validateGroup_NXentry_atom_probeID_reconstruction_naive_discretization(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("naive_discretization", NXprocess.class, group))) return;

		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_atom_probeID_reconstruction_naive_discretization_programID(group.getChild("programID", NXprogram.class));

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_atom_probeID_reconstruction_naive_discretization_NXdata(data);
		}
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_reconstruction_naive_discretization_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_atom_probeID_reconstruction_naive_discretization_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate attribute 'AXISNAME_indices' of type NX_UINT.
		final Attribute AXISNAME_indices_attr = group.getAttribute("AXISNAME_indices");
		if (!(validateAttributeNotNull("AXISNAME_indices", AXISNAME_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("AXISNAME_indices", AXISNAME_indices_attr, NX_UINT);

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
		validateFieldNotNull("intensity", intensity);
		if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
			validateFieldRank("intensity", intensity, 3);
			validateFieldDimensions("intensity", intensity, null, "n_z", "n_y", "n_x");
		}

		// validate field 'axis_z' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset axis_z = group.getLazyDataset("axis_z");
		validateFieldNotNull("axis_z", axis_z);
		if (axis_z != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_z", axis_z, NX_FLOAT);
			validateFieldRank("axis_z", axis_z, 1);
			validateFieldDimensions("axis_z", axis_z, null, "n_z");
		// validate attribute 'long_name' of field 'axis_z' of type NX_CHAR.
		final Attribute axis_z_attr_long_name = group.getDataNode("axis_z").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_z_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_z_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_y' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset axis_y = group.getLazyDataset("axis_y");
		validateFieldNotNull("axis_y", axis_y);
		if (axis_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_y", axis_y, NX_FLOAT);
			validateFieldRank("axis_y", axis_y, 1);
			validateFieldDimensions("axis_y", axis_y, null, "n_y");
		// validate attribute 'long_name' of field 'axis_y' of type NX_CHAR.
		final Attribute axis_y_attr_long_name = group.getDataNode("axis_y").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_y_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_y_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_x' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset axis_x = group.getLazyDataset("axis_x");
		validateFieldNotNull("axis_x", axis_x);
		if (axis_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_x", axis_x, NX_FLOAT);
			validateFieldRank("axis_x", axis_x, 1);
			validateFieldDimensions("axis_x", axis_x, null, "n_x");
		// validate attribute 'long_name' of field 'axis_x' of type NX_CHAR.
		final Attribute axis_x_attr_long_name = group.getDataNode("axis_x").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_x_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_x_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'ranging' of type NXapm_ranging.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging(final NXapm_ranging group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ranging", NXapm_ranging.class, group))) return;

		// validate optional field 'sequence_index' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_atom_probeID_ranging_programID(group.getNXProgram("programID"));

		// validate optional child group 'source' of type NXnote
		if (group.getNote("source") != null) {
			validateGroup_NXentry_atom_probeID_ranging_source(group.getNote("source"));
		}

		// validate optional child group 'mass_to_charge_distribution' of type NXprocess
		if (group.getMass_to_charge_distribution() != null) {
			validateGroup_NXentry_atom_probeID_ranging_mass_to_charge_distribution(group.getMass_to_charge_distribution());
		}

		// validate optional child group 'background_quantification' of type NXprocess
		if (group.getBackground_quantification() != null) {
			validateGroup_NXentry_atom_probeID_ranging_background_quantification(group.getBackground_quantification());
		}

		// validate optional child group 'peak_search' of type NXprocess
		if (group.getChild("peak_search", NXprocess.class) != null) {
			validateGroup_NXentry_atom_probeID_ranging_peak_search(group.getChild("peak_search", NXprocess.class));
		}

		// validate optional child group 'peak_identification' of type NXprocess
		if (group.getPeak_identification() != null) {
			validateGroup_NXentry_atom_probeID_ranging_peak_identification(group.getPeak_identification());
		}
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'source' of type NXnote.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_source(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source", NXnote.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'mass_to_charge_distribution' of type NXprocess.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_mass_to_charge_distribution(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("mass_to_charge_distribution", NXprocess.class, group))) return;

		// validate optional field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate field 'min_mass_to_charge' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset min_mass_to_charge = group.getLazyDataset("min_mass_to_charge");
		validateFieldNotNull("min_mass_to_charge", min_mass_to_charge);
		if (min_mass_to_charge != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min_mass_to_charge", min_mass_to_charge, NX_FLOAT);
		}

		// validate field 'max_mass_to_charge' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset max_mass_to_charge = group.getLazyDataset("max_mass_to_charge");
		validateFieldNotNull("max_mass_to_charge", max_mass_to_charge);
		if (max_mass_to_charge != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max_mass_to_charge", max_mass_to_charge, NX_FLOAT);
		}

		// validate field 'n_mass_to_charge' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset n_mass_to_charge = group.getLazyDataset("n_mass_to_charge");
		validateFieldNotNull("n_mass_to_charge", n_mass_to_charge);
		if (n_mass_to_charge != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("n_mass_to_charge", n_mass_to_charge, NX_POSINT);
		}

		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_atom_probeID_ranging_mass_to_charge_distribution_programID(group.getChild("programID", NXprogram.class));

		// validate child group 'mass_spectrum' of type NXdata
		validateGroup_NXentry_atom_probeID_ranging_mass_to_charge_distribution_mass_spectrum(group.getData("mass_spectrum"));
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_mass_to_charge_distribution_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate group 'mass_spectrum' of type NXdata.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_mass_to_charge_distribution_mass_spectrum(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("mass_spectrum", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate attribute 'AXISNAME_indices' of type NX_UINT.
		final Attribute AXISNAME_indices_attr = group.getAttribute("AXISNAME_indices");
		if (!(validateAttributeNotNull("AXISNAME_indices", AXISNAME_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("AXISNAME_indices", AXISNAME_indices_attr, NX_UINT);

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
		validateFieldNotNull("intensity", intensity);
		if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
			validateFieldRank("intensity", intensity, 1);
			validateFieldDimensions("intensity", intensity, null, "n_bins");
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_mass_to_charge' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset axis_mass_to_charge = group.getLazyDataset("axis_mass_to_charge");
		validateFieldNotNull("axis_mass_to_charge", axis_mass_to_charge);
		if (axis_mass_to_charge != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_mass_to_charge", axis_mass_to_charge, NX_FLOAT);
			validateFieldRank("axis_mass_to_charge", axis_mass_to_charge, 1);
			validateFieldDimensions("axis_mass_to_charge", axis_mass_to_charge, null, "n_bins");
		// validate attribute 'long_name' of field 'axis_mass_to_charge' of type NX_CHAR.
		final Attribute axis_mass_to_charge_attr_long_name = group.getDataNode("axis_mass_to_charge").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_mass_to_charge_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_mass_to_charge_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'background_quantification' of type NXprocess.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_background_quantification(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("background_quantification", NXprocess.class, group))) return;

		// validate optional field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate optional field 'background' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset background = group.getLazyDataset("background");
				if (background != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("background", background, NX_FLOAT);
			validateFieldUnits("background", group.getDataNode("background"), NX_ANY);
		}

		// validate optional field 'mrp_value' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset mrp_value = group.getLazyDataset("mrp_value");
				if (mrp_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mrp_value", mrp_value, NX_FLOAT);
			validateFieldUnits("mrp_value", group.getDataNode("mrp_value"), NX_DIMENSIONLESS);
		}

		// validate optional field 'mrp_mass_to_charge' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset mrp_mass_to_charge = group.getLazyDataset("mrp_mass_to_charge");
				if (mrp_mass_to_charge != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mrp_mass_to_charge", mrp_mass_to_charge, NX_FLOAT);
			validateFieldUnits("mrp_mass_to_charge", group.getDataNode("mrp_mass_to_charge"), NX_ANY);
		}

		// validate optional field 'mrp_voltage' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset mrp_voltage = group.getLazyDataset("mrp_voltage");
				if (mrp_voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mrp_voltage", mrp_voltage, NX_FLOAT);
			validateFieldUnits("mrp_voltage", group.getDataNode("mrp_voltage"), NX_VOLTAGE);
		}

		// validate optional field 'mrp_flight_path_length' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset mrp_flight_path_length = group.getLazyDataset("mrp_flight_path_length");
				if (mrp_flight_path_length != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mrp_flight_path_length", mrp_flight_path_length, NX_FLOAT);
			validateFieldUnits("mrp_flight_path_length", group.getDataNode("mrp_flight_path_length"), NX_LENGTH);
		}
		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_atom_probeID_ranging_background_quantification_programID(group.getChild("programID", NXprogram.class));
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_background_quantification_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'peak_search' of type NXprocess.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_peak_search(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("peak_search", NXprocess.class, group))) return;

		// validate optional field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_atom_probeID_ranging_peak_search_programID(group.getChild("programID", NXprogram.class));

		// validate optional child group 'peakID' of type NXpeak
		if (group.getChild("peakID", NXpeak.class) != null) {
			validateGroup_NXentry_atom_probeID_ranging_peak_search_peakID(group.getChild("peakID", NXpeak.class));
		}
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_peak_search_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate optional group 'peakID' of type NXpeak.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_peak_search_peakID(final NXpeak group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("peakID", NXpeak.class, group))) return;

		// validate optional field 'label' of type NX_CHAR.
		final ILazyDataset label = group.getLazyDataset("label");
				if (label != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("label", label, NX_CHAR);
		}

		// validate optional field 'description' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'category' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset category = group.getLazyDataset("category");
				if (category != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("category", category, NX_CHAR);
			validateFieldEnumeration("category", category,
					"0",
					"1",
					"2",
					"3",
					"4",
					"5");
		}

		// validate field 'position' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset position = group.getLazyDataset("position");
		validateFieldNotNull("position", position);
		if (position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("position", position, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'peak_identification' of type NXprocess.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_peak_identification(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("peak_identification", NXprocess.class, group))) return;

		// validate optional field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
				if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate field 'number_of_ion_types' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_ion_types = group.getLazyDataset("number_of_ion_types");
		validateFieldNotNull("number_of_ion_types", number_of_ion_types);
		if (number_of_ion_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_ion_types", number_of_ion_types, NX_UINT);
		}

		// validate field 'maximum_number_of_atoms_per_molecular_ion' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset maximum_number_of_atoms_per_molecular_ion = group.getLazyDataset("maximum_number_of_atoms_per_molecular_ion");
		validateFieldNotNull("maximum_number_of_atoms_per_molecular_ion", maximum_number_of_atoms_per_molecular_ion);
		if (maximum_number_of_atoms_per_molecular_ion != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("maximum_number_of_atoms_per_molecular_ion", maximum_number_of_atoms_per_molecular_ion, NX_UINT);
		}

		// validate optional field 'iontypes' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset iontypes = group.getLazyDataset("iontypes");
				if (iontypes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("iontypes", iontypes, NX_UINT);
			validateFieldUnits("iontypes", group.getDataNode("iontypes"), NX_UNITLESS);
			validateFieldRank("iontypes", iontypes, 1);
			validateFieldDimensions("iontypes", iontypes, null, "n");
		}
		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_atom_probeID_ranging_peak_identification_programID(group.getChild("programID", NXprogram.class));

		// validate child group 'ionID' of type NXatom
		validateGroup_NXentry_atom_probeID_ranging_peak_identification_ionID(group.getChild("ionID", NXatom.class));
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_peak_identification_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

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

		}
	}

	/**
	 * Validate group 'ionID' of type NXatom.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_peak_identification_ionID(final NXatom group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ionID", NXatom.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'nuclide_hash' of type NX_UINT.
		final ILazyDataset nuclide_hash = group.getLazyDataset("nuclide_hash");
		validateFieldNotNull("nuclide_hash", nuclide_hash);
		if (nuclide_hash != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("nuclide_hash", nuclide_hash, NX_UINT);
			validateFieldUnits("nuclide_hash", group.getDataNode("nuclide_hash"), NX_UNITLESS);
			validateFieldRank("nuclide_hash", nuclide_hash, 1);
			validateFieldDimensions("nuclide_hash", nuclide_hash, "NXatom", "n_ivec_max");
		}

		// validate field 'charge_state' of type NX_INT.
		final ILazyDataset charge_state = group.getLazyDataset("charge_state");
		validateFieldNotNull("charge_state", charge_state);
		if (charge_state != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("charge_state", charge_state, NX_INT);
			validateFieldUnits("charge_state", group.getDataNode("charge_state"), NX_UNITLESS);
		}

		// validate field 'mass_to_charge_range' of type NX_FLOAT.
		final ILazyDataset mass_to_charge_range = group.getLazyDataset("mass_to_charge_range");
		validateFieldNotNull("mass_to_charge_range", mass_to_charge_range);
		if (mass_to_charge_range != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mass_to_charge_range", mass_to_charge_range, NX_FLOAT);
			validateFieldUnits("mass_to_charge_range", group.getDataNode("mass_to_charge_range"), NX_ANY);
			validateFieldRank("mass_to_charge_range", mass_to_charge_range, 2);
			validateFieldDimensions("mass_to_charge_range", mass_to_charge_range, "NXatom", "n_ranges", 2);
		}

		// validate optional field 'nuclide_list' of type NX_UINT.
		final ILazyDataset nuclide_list = group.getLazyDataset("nuclide_list");
				if (nuclide_list != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("nuclide_list", nuclide_list, NX_UINT);
			validateFieldUnits("nuclide_list", group.getDataNode("nuclide_list"), NX_UNITLESS);
			validateFieldRank("nuclide_list", nuclide_list, 2);
			validateFieldDimensions("nuclide_list", nuclide_list, "NXatom", "n_ivec_max", 2);
		}

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}
		// validate optional child group 'charge_state_analysis' of type NXapm_charge_state_analysis
		if (group.getChild("charge_state_analysis", NXapm_charge_state_analysis.class) != null) {
			validateGroup_NXentry_atom_probeID_ranging_peak_identification_ionID_charge_state_analysis(group.getChild("charge_state_analysis", NXapm_charge_state_analysis.class));
		}
	}

	/**
	 * Validate optional group 'charge_state_analysis' of type NXapm_charge_state_analysis.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_peak_identification_ionID_charge_state_analysis(final NXapm_charge_state_analysis group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("charge_state_analysis", NXapm_charge_state_analysis.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'charge_state' of type NX_INT.
		final ILazyDataset charge_state = group.getLazyDataset("charge_state");
		validateFieldNotNull("charge_state", charge_state);
		if (charge_state != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("charge_state", charge_state, NX_INT);
			validateFieldUnits("charge_state", group.getDataNode("charge_state"), NX_UNITLESS);
			validateFieldRank("charge_state", charge_state, 1);
			validateFieldDimensions("charge_state", charge_state, "NXapm_charge_state_analysis", "n_cand");
		}

		// validate field 'nuclide_hash' of type NX_UINT.
		final ILazyDataset nuclide_hash = group.getLazyDataset("nuclide_hash");
		validateFieldNotNull("nuclide_hash", nuclide_hash);
		if (nuclide_hash != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("nuclide_hash", nuclide_hash, NX_UINT);
			validateFieldUnits("nuclide_hash", group.getDataNode("nuclide_hash"), NX_UNITLESS);
			validateFieldRank("nuclide_hash", nuclide_hash, 2);
			validateFieldDimensions("nuclide_hash", nuclide_hash, "NXapm_charge_state_analysis", "n_cand", "n_ivec_max");
		}

		// validate field 'mass' of type NX_FLOAT.
		final ILazyDataset mass = group.getLazyDataset("mass");
		validateFieldNotNull("mass", mass);
		if (mass != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mass", mass, NX_FLOAT);
			validateFieldUnits("mass", group.getDataNode("mass"), NX_MASS);
			validateFieldRank("mass", mass, 1);
			validateFieldDimensions("mass", mass, "NXapm_charge_state_analysis", "n_cand");
		}

		// validate field 'natural_abundance_product' of type NX_FLOAT.
		final ILazyDataset natural_abundance_product = group.getLazyDataset("natural_abundance_product");
		validateFieldNotNull("natural_abundance_product", natural_abundance_product);
		if (natural_abundance_product != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("natural_abundance_product", natural_abundance_product, NX_FLOAT);
			validateFieldUnits("natural_abundance_product", group.getDataNode("natural_abundance_product"), NX_DIMENSIONLESS);
			validateFieldRank("natural_abundance_product", natural_abundance_product, 1);
			validateFieldDimensions("natural_abundance_product", natural_abundance_product, "NXapm_charge_state_analysis", "n_cand");
		}

		// validate field 'shortest_half_life' of type NX_FLOAT.
		final ILazyDataset shortest_half_life = group.getLazyDataset("shortest_half_life");
		validateFieldNotNull("shortest_half_life", shortest_half_life);
		if (shortest_half_life != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("shortest_half_life", shortest_half_life, NX_FLOAT);
			validateFieldUnits("shortest_half_life", group.getDataNode("shortest_half_life"), NX_TIME);
			validateFieldRank("shortest_half_life", shortest_half_life, 1);
			validateFieldDimensions("shortest_half_life", shortest_half_life, "NXapm_charge_state_analysis", "n_cand");
		}
		// validate child group 'config' of type NXparameters
		validateGroup_NXentry_atom_probeID_ranging_peak_identification_ionID_charge_state_analysis_config(group.getConfig());
	}

	/**
	 * Validate group 'config' of type NXparameters.
	 */
	private void validateGroup_NXentry_atom_probeID_ranging_peak_identification_ionID_charge_state_analysis_config(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("config", NXparameters.class, group))) return;

		// validate field 'nuclides' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset nuclides = group.getLazyDataset("nuclides");
		validateFieldNotNull("nuclides", nuclides);
		if (nuclides != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("nuclides", nuclides, NX_UINT);
		}

		// validate field 'mass_to_charge_range' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset mass_to_charge_range = group.getLazyDataset("mass_to_charge_range");
		validateFieldNotNull("mass_to_charge_range", mass_to_charge_range);
		if (mass_to_charge_range != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mass_to_charge_range", mass_to_charge_range, NX_FLOAT);
		}

		// validate field 'min_half_life' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset min_half_life = group.getLazyDataset("min_half_life");
		validateFieldNotNull("min_half_life", min_half_life);
		if (min_half_life != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min_half_life", min_half_life, NX_FLOAT);
		}

		// validate field 'min_abundance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset min_abundance = group.getLazyDataset("min_abundance");
		validateFieldNotNull("min_abundance", min_abundance);
		if (min_abundance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min_abundance", min_abundance, NX_FLOAT);
		}

		// validate field 'sacrifice_isotopic_uniqueness' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset sacrifice_isotopic_uniqueness = group.getLazyDataset("sacrifice_isotopic_uniqueness");
		validateFieldNotNull("sacrifice_isotopic_uniqueness", sacrifice_isotopic_uniqueness);
		if (sacrifice_isotopic_uniqueness != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sacrifice_isotopic_uniqueness", sacrifice_isotopic_uniqueness, NX_BOOLEAN);
		}
	}
}
