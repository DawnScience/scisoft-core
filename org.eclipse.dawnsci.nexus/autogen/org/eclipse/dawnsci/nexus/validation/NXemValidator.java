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
import org.eclipse.dawnsci.nexus.NXcite;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXparameters;
import org.eclipse.dawnsci.nexus.NXcoordinate_system;
import org.eclipse.dawnsci.nexus.NXem_measurement;
import org.eclipse.dawnsci.nexus.NXem_instrument;
import org.eclipse.dawnsci.nexus.NXfabrication;
import org.eclipse.dawnsci.nexus.NXebeam_column;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXelectromagnetic_lens;
import org.eclipse.dawnsci.nexus.NXaperture;
import org.eclipse.dawnsci.nexus.NXdeflector;
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXcorrector_cs;
import org.eclipse.dawnsci.nexus.NXcomponent;
import org.eclipse.dawnsci.nexus.NXsensor;
import org.eclipse.dawnsci.nexus.NXactuator;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXscan_controller;
import org.eclipse.dawnsci.nexus.NXibeam_column;
import org.eclipse.dawnsci.nexus.NXatom;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXmanipulator;
import org.eclipse.dawnsci.nexus.NXpump;
import org.eclipse.dawnsci.nexus.NXem_event_data;
import org.eclipse.dawnsci.nexus.NXimage;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXspectrum;
import org.eclipse.dawnsci.nexus.NXaberration;
import org.eclipse.dawnsci.nexus.NXem_optical_system;
import org.eclipse.dawnsci.nexus.NXem_simulation;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXem_interaction_volume;
import org.eclipse.dawnsci.nexus.NXroi_process;
import org.eclipse.dawnsci.nexus.NXem_img;
import org.eclipse.dawnsci.nexus.NXem_ebsd;
import org.eclipse.dawnsci.nexus.NXphase;
import org.eclipse.dawnsci.nexus.NXunit_cell;
import org.eclipse.dawnsci.nexus.NXem_eds;
import org.eclipse.dawnsci.nexus.NXem_eels;

/**
 * Validator for the application definition 'NXem'.
 */
public class NXemValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXemValidator() {
		super(NexusApplicationDefinition.NX_EM);
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
					"NXem");
		}

		// validate optional field 'identifier_experiment' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier_experiment = group.getLazyDataset("identifier_experiment");
				if (identifier_experiment != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_experiment", identifier_experiment, NX_CHAR);
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

		// validate child group 'sampleID' of type NXsample
		validateGroup_NXentry_sampleID(group.getSample("sampleID"));

		// validate optional child group 'consistent_rotations' of type NXparameters
		if (group.getParameters("consistent_rotations") != null) {
			validateGroup_NXentry_consistent_rotations(group.getParameters("consistent_rotations"));
		}

		// validate optional child group 'NAMED_reference_frameID' of type NXcoordinate_system
		if (group.getChild("NAMED_reference_frameID", NXcoordinate_system.class) != null) {
			validateGroup_NXentry_NAMED_reference_frameID(group.getChild("NAMED_reference_frameID", NXcoordinate_system.class));
		}

		// validate optional child group 'processing_reference_frame' of type NXcoordinate_system
		if (group.getChild("processing_reference_frame", NXcoordinate_system.class) != null) {
			validateGroup_NXentry_processing_reference_frame(group.getChild("processing_reference_frame", NXcoordinate_system.class));
		}

		// validate optional child group 'sample_reference_frame' of type NXcoordinate_system
		if (group.getChild("sample_reference_frame", NXcoordinate_system.class) != null) {
			validateGroup_NXentry_sample_reference_frame(group.getChild("sample_reference_frame", NXcoordinate_system.class));
		}

		// validate optional child group 'detector_reference_frameID' of type NXcoordinate_system
		if (group.getChild("detector_reference_frameID", NXcoordinate_system.class) != null) {
			validateGroup_NXentry_detector_reference_frameID(group.getChild("detector_reference_frameID", NXcoordinate_system.class));
		}

		// validate optional child group 'measurement' of type NXem_measurement
		if (group.getChild("measurement", NXem_measurement.class) != null) {
			validateGroup_NXentry_measurement(group.getChild("measurement", NXem_measurement.class));
		}

		// validate optional child group 'simulation' of type NXem_simulation
		if (group.getChild("simulation", NXem_simulation.class) != null) {
			validateGroup_NXentry_simulation(group.getChild("simulation", NXem_simulation.class));
		}

		// validate optional child group 'roiID' of type NXroi_process
		if (group.getChild("roiID", NXroi_process.class) != null) {
			validateGroup_NXentry_roiID(group.getChild("roiID", NXroi_process.class));
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

		// validate optional field 'telephone_number' of type NX_CHAR.
		final ILazyDataset telephone_number = group.getLazyDataset("telephone_number");
				if (telephone_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("telephone_number", telephone_number, NX_CHAR);
		}

		// validate optional field 'role' of type NX_CHAR.
		final ILazyDataset role = group.getLazyDataset("role");
				if (role != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("role", role, NX_CHAR);
		}
	}

	/**
	 * Validate group 'sampleID' of type NXsample.
	 */
	private void validateGroup_NXentry_sampleID(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sampleID", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'is_simulation' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_simulation = group.getLazyDataset("is_simulation");
		validateFieldNotNull("is_simulation", is_simulation);
		if (is_simulation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_simulation", is_simulation, NX_BOOLEAN);
		}

		// validate optional field 'physical_form' of type NX_CHAR.
		final ILazyDataset physical_form = group.getLazyDataset("physical_form");
				if (physical_form != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_form", physical_form, NX_CHAR);
			validateFieldEnumeration("physical_form", physical_form,
					"bulk",
					"foil",
					"thin_film",
					"powder");
		}

		// validate optional field 'identifier_sample' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier_sample = group.getLazyDataset("identifier_sample");
				if (identifier_sample != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_sample", identifier_sample, NX_CHAR);
		// validate attribute 'type' of field 'identifier_sample' of type NX_CHAR.
		final Attribute identifier_sample_attr_type = group.getDataNode("identifier_sample").getAttribute("type");
		if (!(validateAttributeNotNull("type", identifier_sample_attr_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("type", identifier_sample_attr_type, NX_CHAR);

		}

		// validate optional field 'identifier_parent' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier_parent = group.getLazyDataset("identifier_parent");
				if (identifier_parent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_parent", identifier_parent, NX_CHAR);
		// validate attribute 'type' of field 'identifier_parent' of type NX_CHAR.
		final Attribute identifier_parent_attr_type = group.getDataNode("identifier_parent").getAttribute("type");
		if (!(validateAttributeNotNull("type", identifier_parent_attr_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("type", identifier_parent_attr_type, NX_CHAR);

		}

		// validate field 'preparation_date' of type NX_DATE_TIME.
		final ILazyDataset preparation_date = group.getLazyDataset("preparation_date");
		validateFieldNotNull("preparation_date", preparation_date);
		if (preparation_date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("preparation_date", preparation_date, NX_DATE_TIME);
		}

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
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

		// validate optional field 'thickness' of type NX_NUMBER.
		final ILazyDataset thickness = group.getLazyDataset("thickness");
				if (thickness != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("thickness", thickness, NX_NUMBER);
			validateFieldUnits("thickness", group.getDataNode("thickness"), NX_LENGTH);
		}

		// validate optional field 'density' of type NX_NUMBER.
		final ILazyDataset density = group.getLazyDataset("density");
				if (density != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("density", density, NX_NUMBER);
			validateFieldUnits("density", group.getDataNode("density"), NX_ANY);
			validateFieldRank("density", density, 1);
			validateFieldDimensions("density", density, "NXsample", "n_comp");
		}

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
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
	 * Validate optional group 'NAMED_reference_frameID' of type NXcoordinate_system.
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
	 * Validate optional group 'processing_reference_frame' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_processing_reference_frame(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("processing_reference_frame", NXcoordinate_system.class, group))) return;
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
			validateFieldEnumeration("origin", origin,
					"front_top_left",
					"front_top_right",
					"front_bottom_right",
					"front_bottom_left",
					"back_top_left",
					"back_top_right",
					"back_bottom_right",
					"back_bottom_left");
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
			validateFieldEnumeration("x_direction", x_direction,
					"north",
					"east",
					"south",
					"west",
					"in",
					"out");
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
			validateFieldEnumeration("y_direction", y_direction,
					"north",
					"east",
					"south",
					"west",
					"in",
					"out");
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
			validateFieldEnumeration("z_direction", z_direction,
					"north",
					"east",
					"south",
					"west",
					"in",
					"out");
		}
	}

	/**
	 * Validate optional group 'sample_reference_frame' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_sample_reference_frame(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_reference_frame", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'depends_on' of type NX_CHAR.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
				if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

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
			validateFieldEnumeration("origin", origin,
					"front_top_left",
					"front_top_right",
					"front_bottom_right",
					"front_bottom_left",
					"back_top_left",
					"back_top_right",
					"back_bottom_right",
					"back_bottom_left");
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
			validateFieldEnumeration("x_direction", x_direction,
					"north",
					"east",
					"south",
					"west",
					"in",
					"out");
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
			validateFieldEnumeration("y_direction", y_direction,
					"north",
					"east",
					"south",
					"west",
					"in",
					"out");
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
			validateFieldEnumeration("z_direction", z_direction,
					"north",
					"east",
					"south",
					"west",
					"in",
					"out");
		}
	}

	/**
	 * Validate optional group 'detector_reference_frameID' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_detector_reference_frameID(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("detector_reference_frameID", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'depends_on' of type NX_CHAR.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
				if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

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
			validateFieldEnumeration("origin", origin,
					"front_top_left",
					"front_top_right",
					"front_bottom_right",
					"front_bottom_left",
					"back_top_left",
					"back_top_right",
					"back_bottom_right",
					"back_bottom_left");
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
			validateFieldEnumeration("x_direction", x_direction,
					"north",
					"east",
					"south",
					"west",
					"in",
					"out");
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
			validateFieldEnumeration("y_direction", y_direction,
					"north",
					"east",
					"south",
					"west",
					"in",
					"out");
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
			validateFieldEnumeration("z_direction", z_direction,
					"north",
					"east",
					"south",
					"west",
					"in",
					"out");
		}
	}

	/**
	 * Validate optional group 'measurement' of type NXem_measurement.
	 */
	private void validateGroup_NXentry_measurement(final NXem_measurement group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("measurement", NXem_measurement.class, group))) return;

		// validate child group 'instrument' of type NXem_instrument
		validateGroup_NXentry_measurement_instrument(group.getInstrument());

		// validate optional child group 'eventID' of type NXem_event_data
		if (group.getEventid() != null) {
			validateGroup_NXentry_measurement_eventID(group.getEventid());
		}
	}

	/**
	 * Validate group 'instrument' of type NXem_instrument.
	 */
	private void validateGroup_NXentry_measurement_instrument(final NXem_instrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXem_instrument.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'location' of type NX_CHAR.
		final ILazyDataset location = group.getLazyDataset("location");
				if (location != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("location", location, NX_CHAR);
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"sem",
					"fib",
					"tem");
		}

		// validate child group 'fabrication' of type NXfabrication
		validateGroup_NXentry_measurement_instrument_fabrication(group.getFabrication());

		// validate optional child group 'programID' of type NXprogram
		if (group.getChild("programID", NXprogram.class) != null) {
			validateGroup_NXentry_measurement_instrument_programID(group.getChild("programID", NXprogram.class));
		}

		// validate child group 'ebeam_column' of type NXebeam_column
		validateGroup_NXentry_measurement_instrument_ebeam_column(group.getEbeam_column());

		// validate optional child group 'ibeam_column' of type NXibeam_column
		if (group.getIbeam_column() != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column(group.getIbeam_column());
		}

		// validate optional child group 'detectorID' of type NXdetector
		if (group.getDetector("detectorID") != null) {
			validateGroup_NXentry_measurement_instrument_detectorID(group.getDetector("detectorID"));
		}

		// validate optional child group 'gas_injector' of type NXcomponent
		if (group.getGas_injector() != null) {
			validateGroup_NXentry_measurement_instrument_gas_injector(group.getGas_injector());
		}

		// validate optional child group 'stageID' of type NXmanipulator
		if (group.getStageid() != null) {
			validateGroup_NXentry_measurement_instrument_stageID(group.getStageid());
		}

		// validate optional child group 'nanoprobeID' of type NXmanipulator
		if (group.getNanoprobeid() != null) {
			validateGroup_NXentry_measurement_instrument_nanoprobeID(group.getNanoprobeid());
		}

		// validate optional child group 'pumpID' of type NXpump
		if (group.getPump("pumpID") != null) {
			validateGroup_NXentry_measurement_instrument_pumpID(group.getPump("pumpID"));
		}

		// validate optional child group 'sensorID' of type NXsensor
		if (group.getSensor("sensorID") != null) {
			validateGroup_NXentry_measurement_instrument_sensorID(group.getSensor("sensorID"));
		}

		// validate optional child group 'actuatorID' of type NXactuator
		if (group.getActuator("actuatorID") != null) {
			validateGroup_NXentry_measurement_instrument_actuatorID(group.getActuator("actuatorID"));
		}
	}

	/**
	 * Validate group 'fabrication' of type NXfabrication.
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
	 * Validate optional group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_measurement_instrument_programID(final NXprogram group) {
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
	 * Validate group 'ebeam_column' of type NXebeam_column.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column(final NXebeam_column group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ebeam_column", NXebeam_column.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_fabrication(group.getChild("fabrication", NXfabrication.class));
		}

		// validate optional child group 'electron_source' of type NXsource
		if (group.getElectron_source() != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_electron_source(group.getElectron_source());
		}

		// validate optional child group 'lensID' of type NXelectromagnetic_lens
		if (group.getElectromagnetic_lens("lensID") != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_lensID(group.getElectromagnetic_lens("lensID"));
		}

		// validate optional child group 'apertureID' of type NXaperture
		if (group.getAperture("apertureID") != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_apertureID(group.getAperture("apertureID"));
		}

		// validate optional child group 'deflectorID' of type NXdeflector
		if (group.getDeflector("deflectorID") != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_deflectorID(group.getDeflector("deflectorID"));
		}

		// validate optional child group 'blankerID' of type NXdeflector
		if (group.getBlankerid() != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_blankerID(group.getBlankerid());
		}

		// validate optional child group 'monochromatorID' of type NXmonochromator
		if (group.getMonochromator("monochromatorID") != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_monochromatorID(group.getMonochromator("monochromatorID"));
		}

		// validate optional child group 'corrector_csID' of type NXcorrector_cs
		if (group.getCorrector_cs("corrector_csID") != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_corrector_csID(group.getCorrector_cs("corrector_csID"));
		}

		// validate optional child group 'corrector_ax' of type NXcomponent
		if (group.getCorrector_ax() != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_corrector_ax(group.getCorrector_ax());
		}

		// validate optional child group 'biprismID' of type NXcomponent
		if (group.getBiprismid() != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_biprismID(group.getBiprismid());
		}

		// validate optional child group 'phaseplateID' of type NXcomponent
		if (group.getPhaseplateid() != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_phaseplateID(group.getPhaseplateid());
		}

		// validate optional child group 'sensorID' of type NXsensor
		if (group.getSensor("sensorID") != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_sensorID(group.getSensor("sensorID"));
		}

		// validate optional child group 'actuatorID' of type NXactuator
		if (group.getActuator("actuatorID") != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_actuatorID(group.getActuator("actuatorID"));
		}

		// validate optional child group 'beamID' of type NXbeam
		if (group.getBeam("beamID") != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_beamID(group.getBeam("beamID"));
		}

		// validate optional child group 'scan_controller' of type NXscan_controller
		if (group.getScan_controller() != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_scan_controller(group.getScan_controller());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'electron_source' of type NXsource.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_electron_source(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("electron_source", NXsource.class, group))) return;

		// validate field 'emitter_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset emitter_type = group.getLazyDataset("emitter_type");
		validateFieldNotNull("emitter_type", emitter_type);
		if (emitter_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("emitter_type", emitter_type, NX_CHAR);
		}

		// validate optional field 'probe' of type NX_CHAR.
		final ILazyDataset probe = group.getLazyDataset("probe");
				if (probe != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("probe", probe, NX_CHAR);
			validateFieldEnumeration("probe", probe,
					"neutron",
					"photon",
					"x-ray",
					"muon",
					"electron",
					"ultraviolet",
					"visible light",
					"positron",
					"proton");
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_electron_source_fabrication(group.getFabrication());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_electron_source_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'lensID' of type NXelectromagnetic_lens.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_lensID(final NXelectromagnetic_lens group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("lensID", NXelectromagnetic_lens.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_lensID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_lensID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'apertureID' of type NXaperture.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_apertureID(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("apertureID", NXaperture.class, group))) return;

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_apertureID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_apertureID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'deflectorID' of type NXdeflector.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_deflectorID(final NXdeflector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("deflectorID", NXdeflector.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_deflectorID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_deflectorID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'blankerID' of type NXdeflector.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_blankerID(final NXdeflector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("blankerID", NXdeflector.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_blankerID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_blankerID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'monochromatorID' of type NXmonochromator.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_monochromatorID(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("monochromatorID", NXmonochromator.class, group))) return;

		// validate field 'type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_monochromatorID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_monochromatorID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'corrector_csID' of type NXcorrector_cs.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_corrector_csID(final NXcorrector_cs group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("corrector_csID", NXcorrector_cs.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_corrector_csID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_corrector_csID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'corrector_ax' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_corrector_ax(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("corrector_ax", NXcomponent.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_corrector_ax_fabrication(group.getFabrication());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_corrector_ax_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'biprismID' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_biprismID(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("biprismID", NXcomponent.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_biprismID_fabrication(group.getFabrication());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_biprismID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'phaseplateID' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_phaseplateID(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("phaseplateID", NXcomponent.class, group))) return;

		// validate field 'type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_phaseplateID_fabrication(group.getFabrication());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_phaseplateID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'sensorID' of type NXsensor.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_sensorID(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sensorID", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'actuatorID' of type NXactuator.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_actuatorID(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("actuatorID", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional group 'beamID' of type NXbeam.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_beamID(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beamID", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'scan_controller' of type NXscan_controller.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_scan_controller(final NXscan_controller group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("scan_controller", NXscan_controller.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ebeam_column_scan_controller_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ebeam_column_scan_controller_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'ibeam_column' of type NXibeam_column.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column(final NXibeam_column group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ibeam_column", NXibeam_column.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_fabrication(group.getChild("fabrication", NXfabrication.class));
		}

		// validate child group 'ion_source' of type NXsource
		validateGroup_NXentry_measurement_instrument_ibeam_column_ion_source(group.getIon_source());

		// validate optional child group 'lensID' of type NXelectromagnetic_lens
		if (group.getElectromagnetic_lens("lensID") != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_lensID(group.getElectromagnetic_lens("lensID"));
		}

		// validate optional child group 'apertureID' of type NXaperture
		if (group.getAperture("apertureID") != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_apertureID(group.getAperture("apertureID"));
		}

		// validate optional child group 'deflectorID' of type NXdeflector
		if (group.getDeflector("deflectorID") != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_deflectorID(group.getDeflector("deflectorID"));
		}

		// validate optional child group 'blankerID' of type NXdeflector
		if (group.getBlankerid() != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_blankerID(group.getBlankerid());
		}

		// validate optional child group 'monochromatorID' of type NXmonochromator
		if (group.getMonochromator("monochromatorID") != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_monochromatorID(group.getMonochromator("monochromatorID"));
		}

		// validate optional child group 'sensorID' of type NXsensor
		if (group.getSensor("sensorID") != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_sensorID(group.getSensor("sensorID"));
		}

		// validate optional child group 'actuatorID' of type NXactuator
		if (group.getActuator("actuatorID") != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_actuatorID(group.getActuator("actuatorID"));
		}

		// validate optional child group 'beamID' of type NXbeam
		if (group.getBeam("beamID") != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_beamID(group.getBeam("beamID"));
		}

		// validate optional child group 'scan_controller' of type NXscan_controller
		if (group.getScan_controller() != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_scan_controller(group.getScan_controller());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_fabrication(final NXfabrication group) {
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
	 * Validate group 'ion_source' of type NXsource.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_ion_source(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ion_source", NXsource.class, group))) return;

		// validate field 'emitter_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset emitter_type = group.getLazyDataset("emitter_type");
		validateFieldNotNull("emitter_type", emitter_type);
		if (emitter_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("emitter_type", emitter_type, NX_CHAR);
		}

		// validate child group 'probe' of type NXatom
		validateGroup_NXentry_measurement_instrument_ibeam_column_ion_source_probe(group.getChild("probe", NXatom.class));

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_ion_source_fabrication(group.getFabrication());
		}
	}

	/**
	 * Validate group 'probe' of type NXatom.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_ion_source_probe(final NXatom group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("probe", NXatom.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_ion_source_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'lensID' of type NXelectromagnetic_lens.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_lensID(final NXelectromagnetic_lens group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("lensID", NXelectromagnetic_lens.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_lensID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_lensID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'apertureID' of type NXaperture.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_apertureID(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("apertureID", NXaperture.class, group))) return;

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_apertureID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_apertureID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'deflectorID' of type NXdeflector.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_deflectorID(final NXdeflector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("deflectorID", NXdeflector.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_deflectorID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_deflectorID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'blankerID' of type NXdeflector.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_blankerID(final NXdeflector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("blankerID", NXdeflector.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_blankerID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_blankerID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'monochromatorID' of type NXmonochromator.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_monochromatorID(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("monochromatorID", NXmonochromator.class, group))) return;

		// validate field 'type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_monochromatorID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_monochromatorID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'sensorID' of type NXsensor.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_sensorID(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sensorID", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'actuatorID' of type NXactuator.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_actuatorID(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("actuatorID", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional group 'beamID' of type NXbeam.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_beamID(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beamID", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'scan_controller' of type NXscan_controller.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_scan_controller(final NXscan_controller group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("scan_controller", NXscan_controller.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_ibeam_column_scan_controller_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_ibeam_column_scan_controller_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'detectorID' of type NXdetector.
	 */
	private void validateGroup_NXentry_measurement_instrument_detectorID(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("detectorID", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_detectorID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_detectorID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'gas_injector' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_instrument_gas_injector(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("gas_injector", NXcomponent.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getFabrication() != null) {
			validateGroup_NXentry_measurement_instrument_gas_injector_fabrication(group.getFabrication());
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_gas_injector_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'stageID' of type NXmanipulator.
	 */
	private void validateGroup_NXentry_measurement_instrument_stageID(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stageID", NXmanipulator.class, group))) return;

		// validate optional field 'design' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset design = group.getLazyDataset("design");
				if (design != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("design", design, NX_CHAR);
		}

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_stageID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_stageID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'nanoprobeID' of type NXmanipulator.
	 */
	private void validateGroup_NXentry_measurement_instrument_nanoprobeID(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("nanoprobeID", NXmanipulator.class, group))) return;

		// validate optional child group 'fabrication' of type NXfabrication
		if (group.getChild("fabrication", NXfabrication.class) != null) {
			validateGroup_NXentry_measurement_instrument_nanoprobeID_fabrication(group.getChild("fabrication", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'fabrication' of type NXfabrication.
	 */
	private void validateGroup_NXentry_measurement_instrument_nanoprobeID_fabrication(final NXfabrication group) {
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
	 * Validate optional group 'pumpID' of type NXpump.
	 */
	private void validateGroup_NXentry_measurement_instrument_pumpID(final NXpump group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pumpID", NXpump.class, group))) return;

		// validate field 'design' of type NX_CHAR.
		final ILazyDataset design = group.getLazyDataset("design");
		validateFieldNotNull("design", design);
		if (design != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("design", design, NX_CHAR);
			validateFieldEnumeration("design", design,
					"membrane",
					"rotary_vane",
					"roots",
					"turbo_molecular",
					"ion",
					"cryo",
					"diffusion",
					"scroll");
		}
	}

	/**
	 * Validate optional group 'sensorID' of type NXsensor.
	 */
	private void validateGroup_NXentry_measurement_instrument_sensorID(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sensorID", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'actuatorID' of type NXactuator.
	 */
	private void validateGroup_NXentry_measurement_instrument_actuatorID(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("actuatorID", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional group 'eventID' of type NXem_event_data.
	 */
	private void validateGroup_NXentry_measurement_eventID(final NXem_event_data group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("eventID", NXem_event_data.class, group))) return;

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

		// validate optional field 'identifier_sample' of type NX_CHAR.
		final ILazyDataset identifier_sample = group.getLazyDataset("identifier_sample");
				if (identifier_sample != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_sample", identifier_sample, NX_CHAR);
			validateFieldUnits("identifier_sample", group.getDataNode("identifier_sample"), NX_UNITLESS);
		}

		// validate optional child group 'imageID' of type NXimage
		if (group.getImage("imageID") != null) {
			validateGroup_NXentry_measurement_eventID_imageID(group.getImage("imageID"));
		}

		// validate optional child group 'spectrumID' of type NXspectrum
		if (group.getSpectrum("spectrumID") != null) {
			validateGroup_NXentry_measurement_eventID_spectrumID(group.getSpectrum("spectrumID"));
		}

		// validate optional child group 'instrument' of type NXem_instrument
		if (group.getEm_instrument("instrument") != null) {
			validateGroup_NXentry_measurement_eventID_instrument(group.getEm_instrument("instrument"));
		}
	}

	/**
	 * Validate optional group 'imageID' of type NXimage.
	 */
	private void validateGroup_NXentry_measurement_eventID_imageID(final NXimage group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("imageID", NXimage.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate unnamed child group of type NXprocess (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXprocess.class, false, true);
		final Map<String, NXprocess> allProcess = group.getAllProcess();
		for (final NXprocess process : allProcess.values()) {
			validateGroup_NXentry_measurement_eventID_imageID_NXprocess(process);
		}

		// validate optional child group 'image_1d' of type NXdata
		if (group.getImage_1d() != null) {
			validateGroup_NXentry_measurement_eventID_imageID_image_1d(group.getImage_1d());
		}

		// validate optional child group 'image_2d' of type NXdata
		if (group.getImage_2d() != null) {
			validateGroup_NXentry_measurement_eventID_imageID_image_2d(group.getImage_2d());
		}

		// validate optional child group 'image_3d' of type NXdata
		if (group.getImage_3d() != null) {
			validateGroup_NXentry_measurement_eventID_imageID_image_3d(group.getImage_3d());
		}

		// validate optional child group 'image_4d' of type NXdata
		if (group.getImage_4d() != null) {
			validateGroup_NXentry_measurement_eventID_imageID_image_4d(group.getImage_4d());
		}

		// validate optional child group 'stack_1d' of type NXdata
		if (group.getStack_1d() != null) {
			validateGroup_NXentry_measurement_eventID_imageID_stack_1d(group.getStack_1d());
		}

		// validate optional child group 'stack_2d' of type NXdata
		if (group.getStack_2d() != null) {
			validateGroup_NXentry_measurement_eventID_imageID_stack_2d(group.getStack_2d());
		}

		// validate optional child group 'stack_3d' of type NXdata
		if (group.getStack_3d() != null) {
			validateGroup_NXentry_measurement_eventID_imageID_stack_3d(group.getStack_3d());
		}
	}

	/**
	 * Validate optional unnamed group of type NXprocess.
	 */
	private void validateGroup_NXentry_measurement_eventID_imageID_NXprocess(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprocess.class, group))) return;

		// validate field 'detector_identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset detector_identifier = group.getLazyDataset("detector_identifier");
		validateFieldNotNull("detector_identifier", detector_identifier);
		if (detector_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("detector_identifier", detector_identifier, NX_CHAR);
		}
		// validate optional child group 'input' of type NXnote
		if (group.getNote("input") != null) {
			validateGroup_NXentry_measurement_eventID_imageID_NXprocess_input(group.getNote("input"));
		}
	}

	/**
	 * Validate optional group 'input' of type NXnote.
	 */
	private void validateGroup_NXentry_measurement_eventID_imageID_NXprocess_input(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("input", NXnote.class, group))) return;

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
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

		// validate field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
		validateFieldNotNull("checksum", checksum);
		if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
		validateFieldNotNull("algorithm", algorithm);
		if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}

		// validate field 'context' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset context = group.getLazyDataset("context");
		validateFieldNotNull("context", context);
		if (context != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("context", context, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'image_1d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_imageID_image_1d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("image_1d", NXdata.class, group))) return;
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

		// validate field 'real' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset real = group.getLazyDataset("real");
		validateFieldNotNull("real", real);
		if (real != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("real", real, NX_NUMBER);
		// validate attribute 'long_name' of field 'real' of type NX_CHAR.
		final Attribute real_attr_long_name = group.getDataNode("real").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", real_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", real_attr_long_name, NX_CHAR);

		}

		// validate optional field 'imag' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset imag = group.getLazyDataset("imag");
				if (imag != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("imag", imag, NX_NUMBER);
		// validate attribute 'long_name' of field 'imag' of type NX_CHAR.
		final Attribute imag_attr_long_name = group.getDataNode("imag").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", imag_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", imag_attr_long_name, NX_CHAR);

		}

		// validate optional field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
				if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate optional field 'complex' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset complex = group.getLazyDataset("complex");
				if (complex != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("complex", complex, NX_COMPLEX);
		// validate attribute 'long_name' of field 'complex' of type NX_CHAR.
		final Attribute complex_attr_long_name = group.getDataNode("complex").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", complex_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", complex_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_i' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_i = group.getLazyDataset("axis_i");
		validateFieldNotNull("axis_i", axis_i);
		if (axis_i != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_i", axis_i, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'image_2d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_imageID_image_2d(final NXdata group) {
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

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'real' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset real = group.getLazyDataset("real");
		validateFieldNotNull("real", real);
		if (real != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("real", real, NX_NUMBER);
		// validate attribute 'long_name' of field 'real' of type NX_CHAR.
		final Attribute real_attr_long_name = group.getDataNode("real").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", real_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", real_attr_long_name, NX_CHAR);

		}

		// validate optional field 'imag' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset imag = group.getLazyDataset("imag");
				if (imag != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("imag", imag, NX_NUMBER);
		// validate attribute 'long_name' of field 'imag' of type NX_CHAR.
		final Attribute imag_attr_long_name = group.getDataNode("imag").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", imag_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", imag_attr_long_name, NX_CHAR);

		}

		// validate optional field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
				if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate optional field 'magnitude' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
				if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_COMPLEX);
		// validate attribute 'long_name' of field 'magnitude' of type NX_CHAR.
		final Attribute magnitude_attr_long_name = group.getDataNode("magnitude").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", magnitude_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", magnitude_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
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
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'image_3d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_imageID_image_3d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("image_3d", NXdata.class, group))) return;
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

		// validate field 'real' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset real = group.getLazyDataset("real");
		validateFieldNotNull("real", real);
		if (real != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("real", real, NX_NUMBER);
		// validate attribute 'long_name' of field 'real' of type NX_CHAR.
		final Attribute real_attr_long_name = group.getDataNode("real").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", real_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", real_attr_long_name, NX_CHAR);

		}

		// validate optional field 'imag' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset imag = group.getLazyDataset("imag");
				if (imag != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("imag", imag, NX_NUMBER);
		// validate attribute 'long_name' of field 'imag' of type NX_CHAR.
		final Attribute imag_attr_long_name = group.getDataNode("imag").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", imag_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", imag_attr_long_name, NX_CHAR);

		}

		// validate optional field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
				if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate optional field 'complex' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset complex = group.getLazyDataset("complex");
				if (complex != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("complex", complex, NX_COMPLEX);
		// validate attribute 'long_name' of field 'complex' of type NX_CHAR.
		final Attribute complex_attr_long_name = group.getDataNode("complex").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", complex_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", complex_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_k' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_k = group.getLazyDataset("axis_k");
		validateFieldNotNull("axis_k", axis_k);
		if (axis_k != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_k", axis_k, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_k' of type NX_CHAR.
		final Attribute axis_k_attr_long_name = group.getDataNode("axis_k").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_k_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_k_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
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
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'image_4d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_imageID_image_4d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("image_4d", NXdata.class, group))) return;
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

		// validate field 'real' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset real = group.getLazyDataset("real");
		validateFieldNotNull("real", real);
		if (real != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("real", real, NX_NUMBER);
		// validate attribute 'long_name' of field 'real' of type NX_CHAR.
		final Attribute real_attr_long_name = group.getDataNode("real").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", real_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", real_attr_long_name, NX_CHAR);

		}

		// validate optional field 'imag' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset imag = group.getLazyDataset("imag");
				if (imag != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("imag", imag, NX_NUMBER);
		// validate attribute 'long_name' of field 'imag' of type NX_CHAR.
		final Attribute imag_attr_long_name = group.getDataNode("imag").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", imag_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", imag_attr_long_name, NX_CHAR);

		}

		// validate optional field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
				if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate optional field 'complex' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset complex = group.getLazyDataset("complex");
				if (complex != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("complex", complex, NX_COMPLEX);
		// validate attribute 'long_name' of field 'complex' of type NX_CHAR.
		final Attribute complex_attr_long_name = group.getDataNode("complex").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", complex_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", complex_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_m' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_m = group.getLazyDataset("axis_m");
		validateFieldNotNull("axis_m", axis_m);
		if (axis_m != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_m", axis_m, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_m' of type NX_CHAR.
		final Attribute axis_m_attr_long_name = group.getDataNode("axis_m").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_m_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_m_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_k' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_k = group.getLazyDataset("axis_k");
		validateFieldNotNull("axis_k", axis_k);
		if (axis_k != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_k", axis_k, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_k' of type NX_CHAR.
		final Attribute axis_k_attr_long_name = group.getDataNode("axis_k").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_k_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_k_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
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
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'stack_1d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_imageID_stack_1d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stack_1d", NXdata.class, group))) return;
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

		// validate field 'real' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset real = group.getLazyDataset("real");
		validateFieldNotNull("real", real);
		if (real != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("real", real, NX_NUMBER);
		// validate attribute 'long_name' of field 'real' of type NX_CHAR.
		final Attribute real_attr_long_name = group.getDataNode("real").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", real_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", real_attr_long_name, NX_CHAR);

		}

		// validate optional field 'imag' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset imag = group.getLazyDataset("imag");
				if (imag != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("imag", imag, NX_NUMBER);
		// validate attribute 'long_name' of field 'imag' of type NX_CHAR.
		final Attribute imag_attr_long_name = group.getDataNode("imag").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", imag_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", imag_attr_long_name, NX_CHAR);

		}

		// validate optional field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
				if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate optional field 'complex' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset complex = group.getLazyDataset("complex");
				if (complex != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("complex", complex, NX_COMPLEX);
		// validate attribute 'long_name' of field 'complex' of type NX_CHAR.
		final Attribute complex_attr_long_name = group.getDataNode("complex").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", complex_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", complex_attr_long_name, NX_CHAR);

		}

		// validate optional field 'indices_group' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_group = group.getLazyDataset("indices_group");
				if (indices_group != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_group", indices_group, NX_INT);
		// validate attribute 'long_name' of field 'indices_group' of type NX_CHAR.
		final Attribute indices_group_attr_long_name = group.getDataNode("indices_group").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_group_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_group_attr_long_name, NX_CHAR);

		}

		// validate field 'indices_image' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_image = group.getLazyDataset("indices_image");
		validateFieldNotNull("indices_image", indices_image);
		if (indices_image != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_image", indices_image, NX_INT);
		// validate attribute 'long_name' of field 'indices_image' of type NX_CHAR.
		final Attribute indices_image_attr_long_name = group.getDataNode("indices_image").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_image_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_image_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_i' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_i = group.getLazyDataset("axis_i");
		validateFieldNotNull("axis_i", axis_i);
		if (axis_i != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_i", axis_i, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'stack_2d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_imageID_stack_2d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stack_2d", NXdata.class, group))) return;
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

		// validate field 'real' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset real = group.getLazyDataset("real");
		validateFieldNotNull("real", real);
		if (real != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("real", real, NX_NUMBER);
		// validate attribute 'long_name' of field 'real' of type NX_CHAR.
		final Attribute real_attr_long_name = group.getDataNode("real").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", real_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", real_attr_long_name, NX_CHAR);

		}

		// validate optional field 'imag' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset imag = group.getLazyDataset("imag");
				if (imag != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("imag", imag, NX_NUMBER);
		// validate attribute 'long_name' of field 'imag' of type NX_CHAR.
		final Attribute imag_attr_long_name = group.getDataNode("imag").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", imag_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", imag_attr_long_name, NX_CHAR);

		}

		// validate optional field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
				if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate optional field 'complex' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset complex = group.getLazyDataset("complex");
				if (complex != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("complex", complex, NX_COMPLEX);
		// validate attribute 'long_name' of field 'complex' of type NX_CHAR.
		final Attribute complex_attr_long_name = group.getDataNode("complex").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", complex_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", complex_attr_long_name, NX_CHAR);

		}

		// validate optional field 'indices_group' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_group = group.getLazyDataset("indices_group");
				if (indices_group != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_group", indices_group, NX_INT);
		// validate attribute 'long_name' of field 'indices_group' of type NX_CHAR.
		final Attribute indices_group_attr_long_name = group.getDataNode("indices_group").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_group_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_group_attr_long_name, NX_CHAR);

		}

		// validate field 'indices_image' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_image = group.getLazyDataset("indices_image");
		validateFieldNotNull("indices_image", indices_image);
		if (indices_image != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_image", indices_image, NX_INT);
		// validate attribute 'long_name' of field 'indices_image' of type NX_CHAR.
		final Attribute indices_image_attr_long_name = group.getDataNode("indices_image").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_image_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_image_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
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
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'stack_3d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_imageID_stack_3d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stack_3d", NXdata.class, group))) return;
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

		// validate field 'real' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset real = group.getLazyDataset("real");
		validateFieldNotNull("real", real);
		if (real != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("real", real, NX_NUMBER);
		// validate attribute 'long_name' of field 'real' of type NX_CHAR.
		final Attribute real_attr_long_name = group.getDataNode("real").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", real_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", real_attr_long_name, NX_CHAR);

		}

		// validate optional field 'imag' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset imag = group.getLazyDataset("imag");
				if (imag != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("imag", imag, NX_NUMBER);
		// validate attribute 'long_name' of field 'imag' of type NX_CHAR.
		final Attribute imag_attr_long_name = group.getDataNode("imag").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", imag_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", imag_attr_long_name, NX_CHAR);

		}

		// validate optional field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
				if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate optional field 'complex' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset complex = group.getLazyDataset("complex");
				if (complex != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("complex", complex, NX_COMPLEX);
		// validate attribute 'long_name' of field 'complex' of type NX_CHAR.
		final Attribute complex_attr_long_name = group.getDataNode("complex").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", complex_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", complex_attr_long_name, NX_CHAR);

		}

		// validate optional field 'indices_group' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_group = group.getLazyDataset("indices_group");
				if (indices_group != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_group", indices_group, NX_INT);
		// validate attribute 'long_name' of field 'indices_group' of type NX_CHAR.
		final Attribute indices_group_attr_long_name = group.getDataNode("indices_group").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_group_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_group_attr_long_name, NX_CHAR);

		}

		// validate field 'indices_image' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_image = group.getLazyDataset("indices_image");
		validateFieldNotNull("indices_image", indices_image);
		if (indices_image != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_image", indices_image, NX_INT);
		// validate attribute 'long_name' of field 'indices_image' of type NX_CHAR.
		final Attribute indices_image_attr_long_name = group.getDataNode("indices_image").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_image_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_image_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_k' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_k = group.getLazyDataset("axis_k");
		validateFieldNotNull("axis_k", axis_k);
		if (axis_k != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_k", axis_k, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_k' of type NX_CHAR.
		final Attribute axis_k_attr_long_name = group.getDataNode("axis_k").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_k_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_k_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
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
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'spectrumID' of type NXspectrum.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID(final NXspectrum group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spectrumID", NXspectrum.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate unnamed child group of type NXprocess (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXprocess.class, false, true);
		final Map<String, NXprocess> allProcess = group.getAllProcess();
		for (final NXprocess process : allProcess.values()) {
			validateGroup_NXentry_measurement_eventID_spectrumID_NXprocess(process);
		}

		// validate optional child group 'spectrum_0d' of type NXdata
		if (group.getSpectrum_0d() != null) {
			validateGroup_NXentry_measurement_eventID_spectrumID_spectrum_0d(group.getSpectrum_0d());
		}

		// validate optional child group 'spectrum_1d' of type NXdata
		if (group.getSpectrum_1d() != null) {
			validateGroup_NXentry_measurement_eventID_spectrumID_spectrum_1d(group.getSpectrum_1d());
		}

		// validate optional child group 'spectrum_2d' of type NXdata
		if (group.getSpectrum_2d() != null) {
			validateGroup_NXentry_measurement_eventID_spectrumID_spectrum_2d(group.getSpectrum_2d());
		}

		// validate optional child group 'spectrum_3d' of type NXdata
		if (group.getSpectrum_3d() != null) {
			validateGroup_NXentry_measurement_eventID_spectrumID_spectrum_3d(group.getSpectrum_3d());
		}

		// validate optional child group 'stack_0d' of type NXdata
		if (group.getStack_0d() != null) {
			validateGroup_NXentry_measurement_eventID_spectrumID_stack_0d(group.getStack_0d());
		}

		// validate optional child group 'stack_1d' of type NXdata
		if (group.getChild("stack_1d", NXdata.class) != null) {
			validateGroup_NXentry_measurement_eventID_spectrumID_stack_1d(group.getChild("stack_1d", NXdata.class));
		}

		// validate optional child group 'stack_2d' of type NXdata
		if (group.getStack_2d() != null) {
			validateGroup_NXentry_measurement_eventID_spectrumID_stack_2d(group.getStack_2d());
		}

		// validate optional child group 'stack_3d' of type NXdata
		if (group.getStack_3d() != null) {
			validateGroup_NXentry_measurement_eventID_spectrumID_stack_3d(group.getStack_3d());
		}
	}

	/**
	 * Validate optional unnamed group of type NXprocess.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID_NXprocess(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprocess.class, group))) return;

		// validate field 'detector_identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset detector_identifier = group.getLazyDataset("detector_identifier");
		validateFieldNotNull("detector_identifier", detector_identifier);
		if (detector_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("detector_identifier", detector_identifier, NX_CHAR);
		}
		// validate optional child group 'input' of type NXnote
		if (group.getNote("input") != null) {
			validateGroup_NXentry_measurement_eventID_spectrumID_NXprocess_input(group.getNote("input"));
		}
	}

	/**
	 * Validate optional group 'input' of type NXnote.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID_NXprocess_input(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("input", NXnote.class, group))) return;

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
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

		// validate field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
		validateFieldNotNull("checksum", checksum);
		if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
		validateFieldNotNull("algorithm", algorithm);
		if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}

		// validate field 'context' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset context = group.getLazyDataset("context");
		validateFieldNotNull("context", context);
		if (context != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("context", context, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'spectrum_0d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID_spectrum_0d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spectrum_0d", NXdata.class, group))) return;
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
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_energy = group.getLazyDataset("axis_energy");
		validateFieldNotNull("axis_energy", axis_energy);
		if (axis_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_energy", axis_energy, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_energy' of type NX_CHAR.
		final Attribute axis_energy_attr_long_name = group.getDataNode("axis_energy").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_energy_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_energy_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'spectrum_1d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID_spectrum_1d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spectrum_1d", NXdata.class, group))) return;
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
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_i' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_i = group.getLazyDataset("axis_i");
		validateFieldNotNull("axis_i", axis_i);
		if (axis_i != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_i", axis_i, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_energy = group.getLazyDataset("axis_energy");
		validateFieldNotNull("axis_energy", axis_energy);
		if (axis_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_energy", axis_energy, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_energy' of type NX_CHAR.
		final Attribute axis_energy_attr_long_name = group.getDataNode("axis_energy").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_energy_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_energy_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'spectrum_2d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID_spectrum_2d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spectrum_2d", NXdata.class, group))) return;
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
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
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
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_energy = group.getLazyDataset("axis_energy");
		validateFieldNotNull("axis_energy", axis_energy);
		if (axis_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_energy", axis_energy, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_energy' of type NX_CHAR.
		final Attribute axis_energy_attr_long_name = group.getDataNode("axis_energy").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_energy_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_energy_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'spectrum_3d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID_spectrum_3d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spectrum_3d", NXdata.class, group))) return;
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
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_k' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_k = group.getLazyDataset("axis_k");
		validateFieldNotNull("axis_k", axis_k);
		if (axis_k != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_k", axis_k, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_k' of type NX_CHAR.
		final Attribute axis_k_attr_long_name = group.getDataNode("axis_k").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_k_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_k_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
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
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_energy = group.getLazyDataset("axis_energy");
		validateFieldNotNull("axis_energy", axis_energy);
		if (axis_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_energy", axis_energy, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_energy' of type NX_CHAR.
		final Attribute axis_energy_attr_long_name = group.getDataNode("axis_energy").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_energy_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_energy_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'stack_0d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID_stack_0d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stack_0d", NXdata.class, group))) return;
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
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate field 'indices_spectrum' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_spectrum = group.getLazyDataset("indices_spectrum");
		validateFieldNotNull("indices_spectrum", indices_spectrum);
		if (indices_spectrum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_spectrum", indices_spectrum, NX_INT);
		// validate attribute 'long_name' of field 'indices_spectrum' of type NX_CHAR.
		final Attribute indices_spectrum_attr_long_name = group.getDataNode("indices_spectrum").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_spectrum_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_spectrum_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_energy = group.getLazyDataset("axis_energy");
		validateFieldNotNull("axis_energy", axis_energy);
		if (axis_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_energy", axis_energy, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_energy' of type NX_CHAR.
		final Attribute axis_energy_attr_long_name = group.getDataNode("axis_energy").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_energy_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_energy_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'stack_1d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID_stack_1d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stack_1d", NXdata.class, group))) return;
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
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate field 'indices_spectrum' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_spectrum = group.getLazyDataset("indices_spectrum");
		validateFieldNotNull("indices_spectrum", indices_spectrum);
		if (indices_spectrum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_spectrum", indices_spectrum, NX_INT);
		// validate attribute 'long_name' of field 'indices_spectrum' of type NX_CHAR.
		final Attribute indices_spectrum_attr_long_name = group.getDataNode("indices_spectrum").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_spectrum_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_spectrum_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_i' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_i = group.getLazyDataset("axis_i");
		validateFieldNotNull("axis_i", axis_i);
		if (axis_i != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_i", axis_i, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_energy = group.getLazyDataset("axis_energy");
		validateFieldNotNull("axis_energy", axis_energy);
		if (axis_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_energy", axis_energy, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_energy' of type NX_CHAR.
		final Attribute axis_energy_attr_long_name = group.getDataNode("axis_energy").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_energy_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_energy_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'stack_2d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID_stack_2d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stack_2d", NXdata.class, group))) return;
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
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate field 'indices_spectrum' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_spectrum = group.getLazyDataset("indices_spectrum");
		validateFieldNotNull("indices_spectrum", indices_spectrum);
		if (indices_spectrum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_spectrum", indices_spectrum, NX_INT);
		// validate attribute 'long_name' of field 'indices_spectrum' of type NX_CHAR.
		final Attribute indices_spectrum_attr_long_name = group.getDataNode("indices_spectrum").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_spectrum_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_spectrum_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
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
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_energy = group.getLazyDataset("axis_energy");
		validateFieldNotNull("axis_energy", axis_energy);
		if (axis_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_energy", axis_energy, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_energy' of type NX_CHAR.
		final Attribute axis_energy_attr_long_name = group.getDataNode("axis_energy").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_energy_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_energy_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'stack_3d' of type NXdata.
	 */
	private void validateGroup_NXentry_measurement_eventID_spectrumID_stack_3d(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stack_3d", NXdata.class, group))) return;
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
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate field 'indices_spectrum' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_spectrum = group.getLazyDataset("indices_spectrum");
		validateFieldNotNull("indices_spectrum", indices_spectrum);
		if (indices_spectrum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_spectrum", indices_spectrum, NX_INT);
		// validate attribute 'long_name' of field 'indices_spectrum' of type NX_CHAR.
		final Attribute indices_spectrum_attr_long_name = group.getDataNode("indices_spectrum").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_spectrum_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_spectrum_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_k' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_k = group.getLazyDataset("axis_k");
		validateFieldNotNull("axis_k", axis_k);
		if (axis_k != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_k", axis_k, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_k' of type NX_CHAR.
		final Attribute axis_k_attr_long_name = group.getDataNode("axis_k").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_k_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_k_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
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
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_energy = group.getLazyDataset("axis_energy");
		validateFieldNotNull("axis_energy", axis_energy);
		if (axis_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_energy", axis_energy, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_energy' of type NX_CHAR.
		final Attribute axis_energy_attr_long_name = group.getDataNode("axis_energy").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_energy_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_energy_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'instrument' of type NXem_instrument.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument(final NXem_instrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXem_instrument.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate child group 'ebeam_column' of type NXebeam_column
		validateGroup_NXentry_measurement_eventID_instrument_ebeam_column(group.getEbeam_column());

		// validate optional child group 'ibeam_column' of type NXibeam_column
		if (group.getIbeam_column() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ibeam_column(group.getIbeam_column());
		}

		// validate optional child group 'optics' of type NXem_optical_system
		if (group.getEm_optical_system("optics") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_optics(group.getEm_optical_system("optics"));
		}

		// validate optional child group 'detectorID' of type NXdetector
		if (group.getDetector("detectorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_detectorID(group.getDetector("detectorID"));
		}

		// validate optional child group 'stageID' of type NXmanipulator
		if (group.getStageid() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_stageID(group.getStageid());
		}

		// validate optional child group 'nanoprobeID' of type NXmanipulator
		if (group.getNanoprobeid() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_nanoprobeID(group.getNanoprobeid());
		}

		// validate optional child group 'gas_injector' of type NXcomponent
		if (group.getGas_injector() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_gas_injector(group.getGas_injector());
		}

		// validate optional child group 'pumpID' of type NXpump
		if (group.getPump("pumpID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_pumpID(group.getPump("pumpID"));
		}

		// validate optional child group 'sensorID' of type NXsensor
		if (group.getSensor("sensorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_sensorID(group.getSensor("sensorID"));
		}

		// validate optional child group 'actuatorID' of type NXactuator
		if (group.getActuator("actuatorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_actuatorID(group.getActuator("actuatorID"));
		}
	}

	/**
	 * Validate group 'ebeam_column' of type NXebeam_column.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column(final NXebeam_column group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ebeam_column", NXebeam_column.class, group))) return;

		// validate optional field 'operation_mode' of type NX_CHAR.
		final ILazyDataset operation_mode = group.getLazyDataset("operation_mode");
				if (operation_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("operation_mode", operation_mode, NX_CHAR);
		}

		// validate optional child group 'electron_source' of type NXsource
		if (group.getElectron_source() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_electron_source(group.getElectron_source());
		}

		// validate optional child group 'lensID' of type NXelectromagnetic_lens
		if (group.getElectromagnetic_lens("lensID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_lensID(group.getElectromagnetic_lens("lensID"));
		}

		// validate optional child group 'apertureID' of type NXaperture
		if (group.getAperture("apertureID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_apertureID(group.getAperture("apertureID"));
		}

		// validate optional child group 'deflectorID' of type NXdeflector
		if (group.getDeflector("deflectorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_deflectorID(group.getDeflector("deflectorID"));
		}

		// validate optional child group 'blankerID' of type NXdeflector
		if (group.getBlankerid() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_blankerID(group.getBlankerid());
		}

		// validate optional child group 'monochromatorID' of type NXmonochromator
		if (group.getMonochromator("monochromatorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_monochromatorID(group.getMonochromator("monochromatorID"));
		}

		// validate optional child group 'corrector_csID' of type NXcorrector_cs
		if (group.getCorrector_cs("corrector_csID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID(group.getCorrector_cs("corrector_csID"));
		}

		// validate optional child group 'corrector_ax' of type NXcomponent
		if (group.getCorrector_ax() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_ax(group.getCorrector_ax());
		}

		// validate optional child group 'biprismID' of type NXcomponent
		if (group.getBiprismid() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_biprismID(group.getBiprismid());
		}

		// validate optional child group 'phaseplateID' of type NXcomponent
		if (group.getPhaseplateid() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_phaseplateID(group.getPhaseplateid());
		}

		// validate optional child group 'sensorID' of type NXsensor
		if (group.getSensor("sensorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_sensorID(group.getSensor("sensorID"));
		}

		// validate optional child group 'actuatorID' of type NXactuator
		if (group.getActuator("actuatorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_actuatorID(group.getActuator("actuatorID"));
		}

		// validate optional child group 'beamID' of type NXbeam
		if (group.getBeam("beamID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_beamID(group.getBeam("beamID"));
		}

		// validate optional child group 'scan_controller' of type NXscan_controller
		if (group.getScan_controller() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_scan_controller(group.getScan_controller());
		}
	}

	/**
	 * Validate optional group 'electron_source' of type NXsource.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_electron_source(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("electron_source", NXsource.class, group))) return;

		// validate field 'voltage' of type NX_NUMBER.
		final ILazyDataset voltage = group.getLazyDataset("voltage");
		validateFieldNotNull("voltage", voltage);
		if (voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("voltage", voltage, NX_NUMBER);
			validateFieldUnits("voltage", group.getDataNode("voltage"), NX_VOLTAGE);
		}

		// validate optional field 'extraction_voltage' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset extraction_voltage = group.getLazyDataset("extraction_voltage");
				if (extraction_voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extraction_voltage", extraction_voltage, NX_NUMBER);
		}

		// validate optional field 'emission_current' of type NX_NUMBER.
		final ILazyDataset emission_current = group.getLazyDataset("emission_current");
				if (emission_current != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("emission_current", emission_current, NX_NUMBER);
			validateFieldUnits("emission_current", group.getDataNode("emission_current"), NX_CURRENT);
		}

		// validate optional field 'filament_current' of type NX_NUMBER.
		final ILazyDataset filament_current = group.getLazyDataset("filament_current");
				if (filament_current != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("filament_current", filament_current, NX_NUMBER);
			validateFieldUnits("filament_current", group.getDataNode("filament_current"), NX_CURRENT);
		}
	}

	/**
	 * Validate optional group 'lensID' of type NXelectromagnetic_lens.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_lensID(final NXelectromagnetic_lens group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("lensID", NXelectromagnetic_lens.class, group))) return;

		// validate field 'power_setting' of type NX_CHAR_OR_NUMBER.
		final ILazyDataset power_setting = group.getLazyDataset("power_setting");
		validateFieldNotNull("power_setting", power_setting);
		if (power_setting != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("power_setting", power_setting, NX_CHAR_OR_NUMBER);
			validateFieldUnits("power_setting", group.getDataNode("power_setting"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'apertureID' of type NXaperture.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_apertureID(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("apertureID", NXaperture.class, group))) return;

		// validate optional field 'setting' of type NX_CHAR_OR_NUMBER. Note: field not defined in base class.
		final ILazyDataset setting = group.getLazyDataset("setting");
				if (setting != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("setting", setting, NX_CHAR_OR_NUMBER);
		}
	}

	/**
	 * Validate optional group 'deflectorID' of type NXdeflector.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_deflectorID(final NXdeflector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("deflectorID", NXdeflector.class, group))) return;

	}

	/**
	 * Validate optional group 'blankerID' of type NXdeflector.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_blankerID(final NXdeflector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("blankerID", NXdeflector.class, group))) return;

	}

	/**
	 * Validate optional group 'monochromatorID' of type NXmonochromator.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_monochromatorID(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("monochromatorID", NXmonochromator.class, group))) return;

		// validate field 'applied' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset applied = group.getLazyDataset("applied");
		validateFieldNotNull("applied", applied);
		if (applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("applied", applied, NX_BOOLEAN);
		}

		// validate optional field 'dispersion' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset dispersion = group.getLazyDataset("dispersion");
				if (dispersion != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dispersion", dispersion, NX_NUMBER);
		}

		// validate optional field 'voltage' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset voltage = group.getLazyDataset("voltage");
				if (voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("voltage", voltage, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'corrector_csID' of type NXcorrector_cs.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID(final NXcorrector_cs group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("corrector_csID", NXcorrector_cs.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'applied' of type NX_BOOLEAN.
		final ILazyDataset applied = group.getLazyDataset("applied");
				if (applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("applied", applied, NX_BOOLEAN);
		}

		// validate child group 'tableauID' of type NXprocess
		validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID(group.getTableauid());
	}

	/**
	 * Validate group 'tableauID' of type NXprocess.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("tableauID", NXprocess.class, group))) return;

		// validate optional child group 'c_1' of type NXaberration
		if (group.getChild("c_1", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_1(group.getChild("c_1", NXaberration.class));
		}

		// validate optional child group 'a_1' of type NXaberration
		if (group.getChild("a_1", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_a_1(group.getChild("a_1", NXaberration.class));
		}

		// validate optional child group 'b_2' of type NXaberration
		if (group.getChild("b_2", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_b_2(group.getChild("b_2", NXaberration.class));
		}

		// validate optional child group 'a_2' of type NXaberration
		if (group.getChild("a_2", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_a_2(group.getChild("a_2", NXaberration.class));
		}

		// validate optional child group 'c_3' of type NXaberration
		if (group.getChild("c_3", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3(group.getChild("c_3", NXaberration.class));
		}

		// validate optional child group 's_3' of type NXaberration
		if (group.getChild("s_3", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_s_3(group.getChild("s_3", NXaberration.class));
		}

		// validate optional child group 'a_3' of type NXaberration
		if (group.getChild("a_3", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_a_3(group.getChild("a_3", NXaberration.class));
		}

		// validate optional child group 'b_4' of type NXaberration
		if (group.getChild("b_4", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_b_4(group.getChild("b_4", NXaberration.class));
		}

		// validate optional child group 'd_4' of type NXaberration
		if (group.getChild("d_4", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_d_4(group.getChild("d_4", NXaberration.class));
		}

		// validate optional child group 'a_4' of type NXaberration
		if (group.getChild("a_4", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_a_4(group.getChild("a_4", NXaberration.class));
		}

		// validate optional child group 'c_5' of type NXaberration
		if (group.getChild("c_5", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5(group.getChild("c_5", NXaberration.class));
		}

		// validate optional child group 's_5' of type NXaberration
		if (group.getChild("s_5", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_s_5(group.getChild("s_5", NXaberration.class));
		}

		// validate optional child group 'r_5' of type NXaberration
		if (group.getChild("r_5", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_r_5(group.getChild("r_5", NXaberration.class));
		}

		// validate optional child group 'a_6' of type NXaberration
		if (group.getChild("a_6", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_a_6(group.getChild("a_6", NXaberration.class));
		}

		// validate optional child group 'c_1_0' of type NXaberration
		if (group.getChild("c_1_0", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_1_0(group.getChild("c_1_0", NXaberration.class));
		}

		// validate optional child group 'c_1_2_a' of type NXaberration
		if (group.getChild("c_1_2_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_1_2_a(group.getChild("c_1_2_a", NXaberration.class));
		}

		// validate optional child group 'c_1_2_b' of type NXaberration
		if (group.getChild("c_1_2_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_1_2_b(group.getChild("c_1_2_b", NXaberration.class));
		}

		// validate optional child group 'c_2_1_a' of type NXaberration
		if (group.getChild("c_2_1_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_2_1_a(group.getChild("c_2_1_a", NXaberration.class));
		}

		// validate optional child group 'c_2_1_b' of type NXaberration
		if (group.getChild("c_2_1_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_2_1_b(group.getChild("c_2_1_b", NXaberration.class));
		}

		// validate optional child group 'c_2_3_a' of type NXaberration
		if (group.getChild("c_2_3_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_2_3_a(group.getChild("c_2_3_a", NXaberration.class));
		}

		// validate optional child group 'c_2_3_b' of type NXaberration
		if (group.getChild("c_2_3_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_2_3_b(group.getChild("c_2_3_b", NXaberration.class));
		}

		// validate optional child group 'c_3_0' of type NXaberration
		if (group.getChild("c_3_0", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3_0(group.getChild("c_3_0", NXaberration.class));
		}

		// validate optional child group 'c_3_2_a' of type NXaberration
		if (group.getChild("c_3_2_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3_2_a(group.getChild("c_3_2_a", NXaberration.class));
		}

		// validate optional child group 'c_3_2_b' of type NXaberration
		if (group.getChild("c_3_2_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3_2_b(group.getChild("c_3_2_b", NXaberration.class));
		}

		// validate optional child group 'c_3_4_a' of type NXaberration
		if (group.getChild("c_3_4_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3_4_a(group.getChild("c_3_4_a", NXaberration.class));
		}

		// validate optional child group 'c_3_4_b' of type NXaberration
		if (group.getChild("c_3_4_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3_4_b(group.getChild("c_3_4_b", NXaberration.class));
		}

		// validate optional child group 'c_4_1_a' of type NXaberration
		if (group.getChild("c_4_1_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_1_a(group.getChild("c_4_1_a", NXaberration.class));
		}

		// validate optional child group 'c_4_1_b' of type NXaberration
		if (group.getChild("c_4_1_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_1_b(group.getChild("c_4_1_b", NXaberration.class));
		}

		// validate optional child group 'c_4_3_a' of type NXaberration
		if (group.getChild("c_4_3_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_3_a(group.getChild("c_4_3_a", NXaberration.class));
		}

		// validate optional child group 'c_4_3_b' of type NXaberration
		if (group.getChild("c_4_3_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_3_b(group.getChild("c_4_3_b", NXaberration.class));
		}

		// validate optional child group 'c_4_5_a' of type NXaberration
		if (group.getChild("c_4_5_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_5_a(group.getChild("c_4_5_a", NXaberration.class));
		}

		// validate optional child group 'c_4_5_b' of type NXaberration
		if (group.getChild("c_4_5_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_5_b(group.getChild("c_4_5_b", NXaberration.class));
		}

		// validate optional child group 'c_5_0' of type NXaberration
		if (group.getChild("c_5_0", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_0(group.getChild("c_5_0", NXaberration.class));
		}

		// validate optional child group 'c_5_2_a' of type NXaberration
		if (group.getChild("c_5_2_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_2_a(group.getChild("c_5_2_a", NXaberration.class));
		}

		// validate optional child group 'c_5_2_b' of type NXaberration
		if (group.getChild("c_5_2_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_2_b(group.getChild("c_5_2_b", NXaberration.class));
		}

		// validate optional child group 'c_5_4_a' of type NXaberration
		if (group.getChild("c_5_4_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_4_a(group.getChild("c_5_4_a", NXaberration.class));
		}

		// validate optional child group 'c_5_4_b' of type NXaberration
		if (group.getChild("c_5_4_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_4_b(group.getChild("c_5_4_b", NXaberration.class));
		}

		// validate optional child group 'c_5_6_a' of type NXaberration
		if (group.getChild("c_5_6_a", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_6_a(group.getChild("c_5_6_a", NXaberration.class));
		}

		// validate optional child group 'c_5_6_b' of type NXaberration
		if (group.getChild("c_5_6_b", NXaberration.class) != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_6_b(group.getChild("c_5_6_b", NXaberration.class));
		}
	}

	/**
	 * Validate optional group 'c_1' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_1(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_1", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'a_1' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_a_1(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("a_1", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'b_2' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_b_2(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("b_2", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'a_2' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_a_2(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("a_2", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_3' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_3", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 's_3' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_s_3(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("s_3", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'a_3' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_a_3(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("a_3", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'b_4' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_b_4(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("b_4", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'd_4' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_d_4(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("d_4", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'a_4' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_a_4(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("a_4", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_5' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_5", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 's_5' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_s_5(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("s_5", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'r_5' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_r_5(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("r_5", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'a_6' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_a_6(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("a_6", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_1_0' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_1_0(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_1_0", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_1_2_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_1_2_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_1_2_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_1_2_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_1_2_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_1_2_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_2_1_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_2_1_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_2_1_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_2_1_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_2_1_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_2_1_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_2_3_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_2_3_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_2_3_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_2_3_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_2_3_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_2_3_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_3_0' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3_0(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_3_0", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_3_2_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3_2_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_3_2_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_3_2_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3_2_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_3_2_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_3_4_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3_4_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_3_4_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_3_4_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_3_4_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_3_4_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_4_1_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_1_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_4_1_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_4_1_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_1_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_4_1_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_4_3_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_3_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_4_3_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_4_3_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_3_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_4_3_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_4_5_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_5_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_4_5_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_4_5_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_4_5_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_4_5_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_5_0' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_0(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_5_0", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_5_2_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_2_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_5_2_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_5_2_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_2_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_5_2_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_5_4_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_4_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_5_4_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_5_4_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_4_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_5_4_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_5_6_a' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_6_a(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_5_6_a", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'c_5_6_b' of type NXaberration.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_csID_tableauID_c_5_6_b(final NXaberration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("c_5_6_b", NXaberration.class, group))) return;

		// validate field 'magnitude' of type NX_NUMBER.
		final ILazyDataset magnitude = group.getLazyDataset("magnitude");
		validateFieldNotNull("magnitude", magnitude);
		if (magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnitude", magnitude, NX_NUMBER);
			validateFieldUnits("magnitude", group.getDataNode("magnitude"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'corrector_ax' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_corrector_ax(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("corrector_ax", NXcomponent.class, group))) return;

		// validate field 'applied' of type NX_BOOLEAN.
		final ILazyDataset applied = group.getLazyDataset("applied");
		validateFieldNotNull("applied", applied);
		if (applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("applied", applied, NX_BOOLEAN);
		}

		// validate field 'value_x' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset value_x = group.getLazyDataset("value_x");
		validateFieldNotNull("value_x", value_x);
		if (value_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value_x", value_x, NX_NUMBER);
		}

		// validate field 'value_y' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset value_y = group.getLazyDataset("value_y");
		validateFieldNotNull("value_y", value_y);
		if (value_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value_y", value_y, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'biprismID' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_biprismID(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("biprismID", NXcomponent.class, group))) return;

	}

	/**
	 * Validate optional group 'phaseplateID' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_phaseplateID(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("phaseplateID", NXcomponent.class, group))) return;

	}

	/**
	 * Validate optional group 'sensorID' of type NXsensor.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_sensorID(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sensorID", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'actuatorID' of type NXactuator.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_actuatorID(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("actuatorID", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional group 'beamID' of type NXbeam.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_beamID(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beamID", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'scan_controller' of type NXscan_controller.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ebeam_column_scan_controller(final NXscan_controller group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("scan_controller", NXscan_controller.class, group))) return;

		// validate field 'scan_schema' of type NX_CHAR.
		final ILazyDataset scan_schema = group.getLazyDataset("scan_schema");
		validateFieldNotNull("scan_schema", scan_schema);
		if (scan_schema != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scan_schema", scan_schema, NX_CHAR);
		}

		// validate field 'dwell_time' of type NX_NUMBER.
		final ILazyDataset dwell_time = group.getLazyDataset("dwell_time");
		validateFieldNotNull("dwell_time", dwell_time);
		if (dwell_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dwell_time", dwell_time, NX_NUMBER);
			validateFieldUnits("dwell_time", group.getDataNode("dwell_time"), NX_TIME);
		}
	}

	/**
	 * Validate optional group 'ibeam_column' of type NXibeam_column.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column(final NXibeam_column group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ibeam_column", NXibeam_column.class, group))) return;

		// validate optional field 'operation_mode' of type NX_CHAR.
		final ILazyDataset operation_mode = group.getLazyDataset("operation_mode");
				if (operation_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("operation_mode", operation_mode, NX_CHAR);
		}

		// validate child group 'ion_source' of type NXsource
		validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_ion_source(group.getIon_source());

		// validate optional child group 'lensID' of type NXelectromagnetic_lens
		if (group.getElectromagnetic_lens("lensID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_lensID(group.getElectromagnetic_lens("lensID"));
		}

		// validate optional child group 'apertureID' of type NXaperture
		if (group.getAperture("apertureID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_apertureID(group.getAperture("apertureID"));
		}

		// validate optional child group 'deflectorID' of type NXdeflector
		if (group.getDeflector("deflectorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_deflectorID(group.getDeflector("deflectorID"));
		}

		// validate optional child group 'blankerID' of type NXdeflector
		if (group.getBlankerid() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_blankerID(group.getBlankerid());
		}

		// validate optional child group 'monochromatorID' of type NXmonochromator
		if (group.getMonochromator("monochromatorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_monochromatorID(group.getMonochromator("monochromatorID"));
		}

		// validate optional child group 'sensorID' of type NXsensor
		if (group.getSensor("sensorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_sensorID(group.getSensor("sensorID"));
		}

		// validate optional child group 'actuatorID' of type NXactuator
		if (group.getActuator("actuatorID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_actuatorID(group.getActuator("actuatorID"));
		}

		// validate optional child group 'beamID' of type NXbeam
		if (group.getBeam("beamID") != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_beamID(group.getBeam("beamID"));
		}

		// validate optional child group 'scan_controller' of type NXscan_controller
		if (group.getScan_controller() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_scan_controller(group.getScan_controller());
		}
	}

	/**
	 * Validate group 'ion_source' of type NXsource.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_ion_source(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ion_source", NXsource.class, group))) return;

		// validate field 'voltage' of type NX_NUMBER.
		final ILazyDataset voltage = group.getLazyDataset("voltage");
		validateFieldNotNull("voltage", voltage);
		if (voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("voltage", voltage, NX_NUMBER);
			validateFieldUnits("voltage", group.getDataNode("voltage"), NX_VOLTAGE);
		}

		// validate field 'flux' of type NX_NUMBER.
		final ILazyDataset flux = group.getLazyDataset("flux");
		validateFieldNotNull("flux", flux);
		if (flux != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flux", flux, NX_NUMBER);
			validateFieldUnits("flux", group.getDataNode("flux"), NX_FLUX);
		}
		// validate child group 'probe' of type NXatom
		validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_ion_source_probe(group.getChild("probe", NXatom.class));
	}

	/**
	 * Validate group 'probe' of type NXatom.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_ion_source_probe(final NXatom group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("probe", NXatom.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'lensID' of type NXelectromagnetic_lens.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_lensID(final NXelectromagnetic_lens group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("lensID", NXelectromagnetic_lens.class, group))) return;

		// validate field 'power_setting' of type NX_CHAR_OR_NUMBER.
		final ILazyDataset power_setting = group.getLazyDataset("power_setting");
		validateFieldNotNull("power_setting", power_setting);
		if (power_setting != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("power_setting", power_setting, NX_CHAR_OR_NUMBER);
			validateFieldUnits("power_setting", group.getDataNode("power_setting"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'apertureID' of type NXaperture.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_apertureID(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("apertureID", NXaperture.class, group))) return;

		// validate field 'setting' of type NX_CHAR_OR_NUMBER. Note: field not defined in base class.
		final ILazyDataset setting = group.getLazyDataset("setting");
		validateFieldNotNull("setting", setting);
		if (setting != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("setting", setting, NX_CHAR_OR_NUMBER);
		}
	}

	/**
	 * Validate optional group 'deflectorID' of type NXdeflector.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_deflectorID(final NXdeflector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("deflectorID", NXdeflector.class, group))) return;

	}

	/**
	 * Validate optional group 'blankerID' of type NXdeflector.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_blankerID(final NXdeflector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("blankerID", NXdeflector.class, group))) return;

	}

	/**
	 * Validate optional group 'monochromatorID' of type NXmonochromator.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_monochromatorID(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("monochromatorID", NXmonochromator.class, group))) return;

		// validate field 'applied' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset applied = group.getLazyDataset("applied");
		validateFieldNotNull("applied", applied);
		if (applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("applied", applied, NX_BOOLEAN);
		}
	}

	/**
	 * Validate optional group 'sensorID' of type NXsensor.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_sensorID(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sensorID", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'actuatorID' of type NXactuator.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_actuatorID(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("actuatorID", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional group 'beamID' of type NXbeam.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_beamID(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beamID", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'scan_controller' of type NXscan_controller.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_ibeam_column_scan_controller(final NXscan_controller group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("scan_controller", NXscan_controller.class, group))) return;

		// validate field 'scan_schema' of type NX_CHAR.
		final ILazyDataset scan_schema = group.getLazyDataset("scan_schema");
		validateFieldNotNull("scan_schema", scan_schema);
		if (scan_schema != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scan_schema", scan_schema, NX_CHAR);
		}

		// validate field 'dwell_time' of type NX_NUMBER.
		final ILazyDataset dwell_time = group.getLazyDataset("dwell_time");
		validateFieldNotNull("dwell_time", dwell_time);
		if (dwell_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dwell_time", dwell_time, NX_NUMBER);
			validateFieldUnits("dwell_time", group.getDataNode("dwell_time"), NX_TIME);
		}
	}

	/**
	 * Validate optional group 'optics' of type NXem_optical_system.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_optics(final NXem_optical_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("optics", NXem_optical_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'detectorID' of type NXdetector.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_detectorID(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("detectorID", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'operation_mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset operation_mode = group.getLazyDataset("operation_mode");
		validateFieldNotNull("operation_mode", operation_mode);
		if (operation_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("operation_mode", operation_mode, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'stageID' of type NXmanipulator.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_stageID(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stageID", NXmanipulator.class, group))) return;

		// validate optional field 'design' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset design = group.getLazyDataset("design");
				if (design != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("design", design, NX_CHAR);
		}

		// validate field 'tilt1' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset tilt1 = group.getLazyDataset("tilt1");
		validateFieldNotNull("tilt1", tilt1);
		if (tilt1 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("tilt1", tilt1, NX_NUMBER);
		}

		// validate field 'tilt2' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset tilt2 = group.getLazyDataset("tilt2");
		validateFieldNotNull("tilt2", tilt2);
		if (tilt2 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("tilt2", tilt2, NX_NUMBER);
		}

		// validate field 'rotation' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset rotation = group.getLazyDataset("rotation");
		validateFieldNotNull("rotation", rotation);
		if (rotation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("rotation", rotation, NX_NUMBER);
		}

		// validate field 'position' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset position = group.getLazyDataset("position");
		validateFieldNotNull("position", position);
		if (position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("position", position, NX_NUMBER);
		}

		// validate optional child group 'sample_heater' of type NXactuator
		if (group.getSample_heater() != null) {
			validateGroup_NXentry_measurement_eventID_instrument_stageID_sample_heater(group.getSample_heater());
		}
	}

	/**
	 * Validate optional group 'sample_heater' of type NXactuator.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_stageID_sample_heater(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_heater", NXactuator.class, group))) return;

		// validate field 'physical_quantity' of type NX_CHAR.
		final ILazyDataset physical_quantity = group.getLazyDataset("physical_quantity");
		validateFieldNotNull("physical_quantity", physical_quantity);
		if (physical_quantity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_quantity", physical_quantity, NX_CHAR);
		}

		// validate optional field 'heater_current' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset heater_current = group.getLazyDataset("heater_current");
				if (heater_current != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("heater_current", heater_current, NX_NUMBER);
			validateFieldUnits("heater_current", group.getDataNode("heater_current"), NX_CURRENT);
		}

		// validate optional field 'heater_voltage' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset heater_voltage = group.getLazyDataset("heater_voltage");
				if (heater_voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("heater_voltage", heater_voltage, NX_NUMBER);
			validateFieldUnits("heater_voltage", group.getDataNode("heater_voltage"), NX_VOLTAGE);
		}

		// validate field 'heater_power' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset heater_power = group.getLazyDataset("heater_power");
		validateFieldNotNull("heater_power", heater_power);
		if (heater_power != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("heater_power", heater_power, NX_NUMBER);
			validateFieldUnits("heater_power", group.getDataNode("heater_power"), NX_POWER);
		}
	}

	/**
	 * Validate optional group 'nanoprobeID' of type NXmanipulator.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_nanoprobeID(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("nanoprobeID", NXmanipulator.class, group))) return;

	}

	/**
	 * Validate optional group 'gas_injector' of type NXcomponent.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_gas_injector(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("gas_injector", NXcomponent.class, group))) return;

	}

	/**
	 * Validate optional group 'pumpID' of type NXpump.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_pumpID(final NXpump group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pumpID", NXpump.class, group))) return;

	}

	/**
	 * Validate optional group 'sensorID' of type NXsensor.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_sensorID(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sensorID", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'actuatorID' of type NXactuator.
	 */
	private void validateGroup_NXentry_measurement_eventID_instrument_actuatorID(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("actuatorID", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional group 'simulation' of type NXem_simulation.
	 */
	private void validateGroup_NXentry_simulation(final NXem_simulation group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("simulation", NXem_simulation.class, group))) return;

		// validate optional child group 'programID' of type NXprogram
		if (group.getProgram("programID") != null) {
			validateGroup_NXentry_simulation_programID(group.getProgram("programID"));
		}

		// validate optional child group 'environment' of type NXcollection
		if (group.getChild("environment", NXcollection.class) != null) {
			validateGroup_NXentry_simulation_environment(group.getChild("environment", NXcollection.class));
		}

		// validate optional child group 'config' of type NXparameters
		if (group.getParameters("config") != null) {
			validateGroup_NXentry_simulation_config(group.getParameters("config"));
		}

		// validate optional child group 'results' of type NXprocess
		if (group.getProcess("results") != null) {
			validateGroup_NXentry_simulation_results(group.getProcess("results"));
		}
	}

	/**
	 * Validate optional group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_simulation_programID(final NXprogram group) {
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
	private void validateGroup_NXentry_simulation_environment(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("environment", NXcollection.class, group))) return;

		// validate unnamed child group of type NXprogram (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXprogram.class, false, true);
		final Map<String, NXprogram> allProgram = group.getChildren(NXprogram.class);
		for (final NXprogram program : allProgram.values()) {
			validateGroup_NXentry_simulation_environment_NXprogram(program);
		}
	}

	/**
	 * Validate unnamed group of type NXprogram.
	 */
	private void validateGroup_NXentry_simulation_environment_NXprogram(final NXprogram group) {
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
	 * Validate optional group 'config' of type NXparameters.
	 */
	private void validateGroup_NXentry_simulation_config(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("config", NXparameters.class, group))) return;

	}

	/**
	 * Validate optional group 'results' of type NXprocess.
	 */
	private void validateGroup_NXentry_simulation_results(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("results", NXprocess.class, group))) return;

		// validate unnamed child group of type NXimage (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXimage.class, false, true);
		final Map<String, NXimage> allImage = group.getChildren(NXimage.class);
		for (final NXimage image : allImage.values()) {
			validateGroup_NXentry_simulation_results_NXimage(image);
		}

		// validate unnamed child group of type NXspectrum (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXspectrum.class, false, true);
		final Map<String, NXspectrum> allSpectrum = group.getChildren(NXspectrum.class);
		for (final NXspectrum spectrum : allSpectrum.values()) {
			validateGroup_NXentry_simulation_results_NXspectrum(spectrum);
		}

		// validate optional child group 'interaction_volumeID' of type NXem_interaction_volume
		if (group.getChild("interaction_volumeID", NXem_interaction_volume.class) != null) {
			validateGroup_NXentry_simulation_results_interaction_volumeID(group.getChild("interaction_volumeID", NXem_interaction_volume.class));
		}
	}

	/**
	 * Validate optional unnamed group of type NXimage.
	 */
	private void validateGroup_NXentry_simulation_results_NXimage(final NXimage group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXimage.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXspectrum.
	 */
	private void validateGroup_NXentry_simulation_results_NXspectrum(final NXspectrum group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXspectrum.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'interaction_volumeID' of type NXem_interaction_volume.
	 */
	private void validateGroup_NXentry_simulation_results_interaction_volumeID(final NXem_interaction_volume group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("interaction_volumeID", NXem_interaction_volume.class, group))) return;

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_simulation_results_interaction_volumeID_NXdata(data);
		}

		// validate unnamed child group of type NXprocess (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXprocess.class, false, true);
		final Map<String, NXprocess> allProcess = group.getAllProcess();
		for (final NXprocess process : allProcess.values()) {
			validateGroup_NXentry_simulation_results_interaction_volumeID_NXprocess(process);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_simulation_results_interaction_volumeID_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXprocess.
	 */
	private void validateGroup_NXentry_simulation_results_interaction_volumeID_NXprocess(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprocess.class, group))) return;

	}

	/**
	 * Validate optional group 'roiID' of type NXroi_process.
	 */
	private void validateGroup_NXentry_roiID(final NXroi_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("roiID", NXroi_process.class, group))) return;

		// validate optional child group 'img' of type NXem_img
		if (group.getChild("img", NXem_img.class) != null) {
			validateGroup_NXentry_roiID_img(group.getChild("img", NXem_img.class));
		}

		// validate optional child group 'ebsd' of type NXem_ebsd
		if (group.getChild("ebsd", NXem_ebsd.class) != null) {
			validateGroup_NXentry_roiID_ebsd(group.getChild("ebsd", NXem_ebsd.class));
		}

		// validate optional child group 'eds' of type NXem_eds
		if (group.getChild("eds", NXem_eds.class) != null) {
			validateGroup_NXentry_roiID_eds(group.getChild("eds", NXem_eds.class));
		}

		// validate optional child group 'eels' of type NXem_eels
		if (group.getChild("eels", NXem_eels.class) != null) {
			validateGroup_NXentry_roiID_eels(group.getChild("eels", NXem_eels.class));
		}
	}

	/**
	 * Validate optional group 'img' of type NXem_img.
	 */
	private void validateGroup_NXentry_roiID_img(final NXem_img group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("img", NXem_img.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate child group 'imageID' of type NXimage
		validateGroup_NXentry_roiID_img_imageID(group.getImage("imageID"));
	}

	/**
	 * Validate group 'imageID' of type NXimage.
	 */
	private void validateGroup_NXentry_roiID_img_imageID(final NXimage group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("imageID", NXimage.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'imaging_mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset imaging_mode = group.getLazyDataset("imaging_mode");
		validateFieldNotNull("imaging_mode", imaging_mode);
		if (imaging_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("imaging_mode", imaging_mode, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'ebsd' of type NXem_ebsd.
	 */
	private void validateGroup_NXentry_roiID_ebsd(final NXem_ebsd group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ebsd", NXem_ebsd.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional child group 'gnomonic_reference_frame' of type NXcoordinate_system
		if (group.getGnomonic_reference_frame() != null) {
			validateGroup_NXentry_roiID_ebsd_gnomonic_reference_frame(group.getGnomonic_reference_frame());
		}

		// validate optional child group 'pattern_center' of type NXprocess
		if (group.getPattern_center() != null) {
			validateGroup_NXentry_roiID_ebsd_pattern_center(group.getPattern_center());
		}

		// validate optional child group 'measurement' of type NXprocess
		if (group.getMeasurement() != null) {
			validateGroup_NXentry_roiID_ebsd_measurement(group.getMeasurement());
		}

		// validate optional child group 'simulation' of type NXprocess
		if (group.getSimulation() != null) {
			validateGroup_NXentry_roiID_ebsd_simulation(group.getSimulation());
		}

		// validate optional child group 'calibration' of type NXprocess
		if (group.getCalibration() != null) {
			validateGroup_NXentry_roiID_ebsd_calibration(group.getCalibration());
		}

		// validate optional child group 'indexing' of type NXprocess
		if (group.getIndexing() != null) {
			validateGroup_NXentry_roiID_ebsd_indexing(group.getIndexing());
		}
	}

	/**
	 * Validate optional group 'gnomonic_reference_frame' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_roiID_ebsd_gnomonic_reference_frame(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("gnomonic_reference_frame", NXcoordinate_system.class, group))) return;
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

		// validate field 'origin' of type NX_CHAR.
		final ILazyDataset origin = group.getLazyDataset("origin");
		validateFieldNotNull("origin", origin);
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
	 * Validate optional group 'pattern_center' of type NXprocess.
	 */
	private void validateGroup_NXentry_roiID_ebsd_pattern_center(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pattern_center", NXprocess.class, group))) return;

		// validate field 'x_boundary_convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset x_boundary_convention = group.getLazyDataset("x_boundary_convention");
		validateFieldNotNull("x_boundary_convention", x_boundary_convention);
		if (x_boundary_convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_boundary_convention", x_boundary_convention, NX_CHAR);
		}

		// validate field 'x_normalization_direction' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset x_normalization_direction = group.getLazyDataset("x_normalization_direction");
		validateFieldNotNull("x_normalization_direction", x_normalization_direction);
		if (x_normalization_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_normalization_direction", x_normalization_direction, NX_CHAR);
		}

		// validate field 'y_boundary_convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset y_boundary_convention = group.getLazyDataset("y_boundary_convention");
		validateFieldNotNull("y_boundary_convention", y_boundary_convention);
		if (y_boundary_convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_boundary_convention", y_boundary_convention, NX_CHAR);
		}

		// validate field 'y_normalization_direction' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset y_normalization_direction = group.getLazyDataset("y_normalization_direction");
		validateFieldNotNull("y_normalization_direction", y_normalization_direction);
		if (y_normalization_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_normalization_direction", y_normalization_direction, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'measurement' of type NXprocess.
	 */
	private void validateGroup_NXentry_roiID_ebsd_measurement(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("measurement", NXprocess.class, group))) return;

		// validate field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate child group 'source' of type NXnote
		validateGroup_NXentry_roiID_ebsd_measurement_source(group.getNote("source"));
	}

	/**
	 * Validate group 'source' of type NXnote.
	 */
	private void validateGroup_NXentry_roiID_ebsd_measurement_source(final NXnote group) {
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
	 * Validate optional group 'simulation' of type NXprocess.
	 */
	private void validateGroup_NXentry_roiID_ebsd_simulation(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("simulation", NXprocess.class, group))) return;

		// validate field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate child group 'source' of type NXnote
		validateGroup_NXentry_roiID_ebsd_simulation_source(group.getNote("source"));
	}

	/**
	 * Validate group 'source' of type NXnote.
	 */
	private void validateGroup_NXentry_roiID_ebsd_simulation_source(final NXnote group) {
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
	 * Validate optional group 'calibration' of type NXprocess.
	 */
	private void validateGroup_NXentry_roiID_ebsd_calibration(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("calibration", NXprocess.class, group))) return;

	}

	/**
	 * Validate optional group 'indexing' of type NXprocess.
	 */
	private void validateGroup_NXentry_roiID_ebsd_indexing(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("indexing", NXprocess.class, group))) return;

		// validate field 'number_of_scan_points' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_scan_points = group.getLazyDataset("number_of_scan_points");
		validateFieldNotNull("number_of_scan_points", number_of_scan_points);
		if (number_of_scan_points != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_scan_points", number_of_scan_points, NX_UINT);
		}

		// validate optional field 'indexing_rate' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset indexing_rate = group.getLazyDataset("indexing_rate");
				if (indexing_rate != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indexing_rate", indexing_rate, NX_NUMBER);
		}

		// validate optional child group 'source' of type NXnote
		if (group.getNote("source") != null) {
			validateGroup_NXentry_roiID_ebsd_indexing_source(group.getNote("source"));
		}

		// validate optional child group 'phaseID' of type NXphase
		if (group.getChild("phaseID", NXphase.class) != null) {
			validateGroup_NXentry_roiID_ebsd_indexing_phaseID(group.getChild("phaseID", NXphase.class));
		}

		// validate optional child group 'roi' of type NXdata
		if (group.getData("roi") != null) {
			validateGroup_NXentry_roiID_ebsd_indexing_roi(group.getData("roi"));
		}
	}

	/**
	 * Validate optional group 'source' of type NXnote.
	 */
	private void validateGroup_NXentry_roiID_ebsd_indexing_source(final NXnote group) {
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
	 * Validate optional group 'phaseID' of type NXphase.
	 */
	private void validateGroup_NXentry_roiID_ebsd_indexing_phaseID(final NXphase group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("phaseID", NXphase.class, group))) return;

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'number_of_scan_points' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_scan_points = group.getLazyDataset("number_of_scan_points");
		validateFieldNotNull("number_of_scan_points", number_of_scan_points);
		if (number_of_scan_points != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_scan_points", number_of_scan_points, NX_UINT);
		}

		// validate child group 'unit_cell' of type NXunit_cell
		validateGroup_NXentry_roiID_ebsd_indexing_phaseID_unit_cell(group.getUnit_cell());
	}

	/**
	 * Validate group 'unit_cell' of type NXunit_cell.
	 */
	private void validateGroup_NXentry_roiID_ebsd_indexing_phaseID_unit_cell(final NXunit_cell group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("unit_cell", NXunit_cell.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'a' of type NX_NUMBER.
		final ILazyDataset a = group.getLazyDataset("a");
		validateFieldNotNull("a", a);
		if (a != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("a", a, NX_NUMBER);
			validateFieldUnits("a", group.getDataNode("a"), NX_LENGTH);
		}

		// validate field 'b' of type NX_NUMBER.
		final ILazyDataset b = group.getLazyDataset("b");
		validateFieldNotNull("b", b);
		if (b != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("b", b, NX_NUMBER);
			validateFieldUnits("b", group.getDataNode("b"), NX_LENGTH);
		}

		// validate field 'c' of type NX_NUMBER.
		final ILazyDataset c = group.getLazyDataset("c");
		validateFieldNotNull("c", c);
		if (c != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("c", c, NX_NUMBER);
			validateFieldUnits("c", group.getDataNode("c"), NX_LENGTH);
		}

		// validate field 'alpha' of type NX_NUMBER.
		final ILazyDataset alpha = group.getLazyDataset("alpha");
		validateFieldNotNull("alpha", alpha);
		if (alpha != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("alpha", alpha, NX_NUMBER);
			validateFieldUnits("alpha", group.getDataNode("alpha"), NX_ANGLE);
		}

		// validate field 'beta' of type NX_NUMBER.
		final ILazyDataset beta = group.getLazyDataset("beta");
		validateFieldNotNull("beta", beta);
		if (beta != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beta", beta, NX_NUMBER);
			validateFieldUnits("beta", group.getDataNode("beta"), NX_ANGLE);
		}

		// validate field 'gamma' of type NX_NUMBER.
		final ILazyDataset gamma = group.getLazyDataset("gamma");
		validateFieldNotNull("gamma", gamma);
		if (gamma != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("gamma", gamma, NX_NUMBER);
			validateFieldUnits("gamma", group.getDataNode("gamma"), NX_ANGLE);
		}

		// validate field 'space_group' of type NX_CHAR.
		final ILazyDataset space_group = group.getLazyDataset("space_group");
		validateFieldNotNull("space_group", space_group);
		if (space_group != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("space_group", space_group, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'roi' of type NXdata.
	 */
	private void validateGroup_NXentry_roiID_ebsd_indexing_roi(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("roi", NXdata.class, group))) return;
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

		// validate optional field 'descriptor' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset descriptor = group.getLazyDataset("descriptor");
				if (descriptor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("descriptor", descriptor, NX_CHAR);
		}

		// validate field 'data' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
		}

		// validate optional field 'axis_z' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_z = group.getLazyDataset("axis_z");
				if (axis_z != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_z", axis_z, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_z' of type NX_CHAR.
		final Attribute axis_z_attr_long_name = group.getDataNode("axis_z").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_z_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_z_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_y' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_y = group.getLazyDataset("axis_y");
		validateFieldNotNull("axis_y", axis_y);
		if (axis_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_y", axis_y, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_y' of type NX_CHAR.
		final Attribute axis_y_attr_long_name = group.getDataNode("axis_y").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_y_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_y_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_x' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_x = group.getLazyDataset("axis_x");
		validateFieldNotNull("axis_x", axis_x);
		if (axis_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_x", axis_x, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_x' of type NX_CHAR.
		final Attribute axis_x_attr_long_name = group.getDataNode("axis_x").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_x_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_x_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'eds' of type NXem_eds.
	 */
	private void validateGroup_NXentry_roiID_eds(final NXem_eds group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("eds", NXem_eds.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate child group 'indexing' of type NXprocess
		validateGroup_NXentry_roiID_eds_indexing(group.getIndexing());
	}

	/**
	 * Validate group 'indexing' of type NXprocess.
	 */
	private void validateGroup_NXentry_roiID_eds_indexing(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("indexing", NXprocess.class, group))) return;

		// validate field 'atom_types' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset atom_types = group.getLazyDataset("atom_types");
		validateFieldNotNull("atom_types", atom_types);
		if (atom_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("atom_types", atom_types, NX_CHAR);
		}

		// validate optional child group 'summary' of type NXdata
		if (group.getData("summary") != null) {
			validateGroup_NXentry_roiID_eds_indexing_summary(group.getData("summary"));
		}

		// validate optional child group 'ELEMENT_SPECIFIC_MAP' of type NXimage
		if (group.getChild("ELEMENT_SPECIFIC_MAP", NXimage.class) != null) {
			validateGroup_NXentry_roiID_eds_indexing_ELEMENT_SPECIFIC_MAP(group.getChild("ELEMENT_SPECIFIC_MAP", NXimage.class));
		}
	}

	/**
	 * Validate optional group 'summary' of type NXdata.
	 */
	private void validateGroup_NXentry_roiID_eds_indexing_summary(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("summary", NXdata.class, group))) return;
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
		}

		// validate field 'axis_energy' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset axis_energy = group.getLazyDataset("axis_energy");
		validateFieldNotNull("axis_energy", axis_energy);
		if (axis_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_energy", axis_energy, NX_CHAR);
		// validate attribute 'long_name' of field 'axis_energy' of type NX_CHAR.
		final Attribute axis_energy_attr_long_name = group.getDataNode("axis_energy").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_energy_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_energy_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'ELEMENT_SPECIFIC_MAP' of type NXimage.
	 */
	private void validateGroup_NXentry_roiID_eds_indexing_ELEMENT_SPECIFIC_MAP(final NXimage group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ELEMENT_SPECIFIC_MAP", NXimage.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'iupac_line_candidates' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset iupac_line_candidates = group.getLazyDataset("iupac_line_candidates");
				if (iupac_line_candidates != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("iupac_line_candidates", iupac_line_candidates, NX_CHAR);
		}

		// validate field 'energy_range' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset energy_range = group.getLazyDataset("energy_range");
		validateFieldNotNull("energy_range", energy_range);
		if (energy_range != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_range", energy_range, NX_NUMBER);
		}

		// validate child group 'image_2d' of type NXdata
		validateGroup_NXentry_roiID_eds_indexing_ELEMENT_SPECIFIC_MAP_image_2d(group.getImage_2d());
	}

	/**
	 * Validate group 'image_2d' of type NXdata.
	 */
	private void validateGroup_NXentry_roiID_eds_indexing_ELEMENT_SPECIFIC_MAP_image_2d(final NXdata group) {
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
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_i' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_i = group.getLazyDataset("axis_i");
		validateFieldNotNull("axis_i", axis_i);
		if (axis_i != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_i", axis_i, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_i' of type NX_CHAR.
		final Attribute axis_i_attr_long_name = group.getDataNode("axis_i").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_i_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_i_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_j' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset axis_j = group.getLazyDataset("axis_j");
		validateFieldNotNull("axis_j", axis_j);
		if (axis_j != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_j", axis_j, NX_NUMBER);
		// validate attribute 'long_name' of field 'axis_j' of type NX_CHAR.
		final Attribute axis_j_attr_long_name = group.getDataNode("axis_j").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_j_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_j_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'eels' of type NXem_eels.
	 */
	private void validateGroup_NXentry_roiID_eels(final NXem_eels group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("eels", NXem_eels.class, group))) return;

	}
}
