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
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXcite;
import org.eclipse.dawnsci.nexus.NXcoordinate_system;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXem_calorimetry'.
 */
public class NXem_calorimetryValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXem_calorimetryValidator() {
		super(NexusApplicationDefinition.NX_EM_CALORIMETRY);
	}

	@Override
	public ValidationReport validate(NXroot root) {
		// validate unnamed child group of type NXentry
		validateUnnamedGroupOccurrences(root, NXentry.class, false, false);
		final NXentry entry = getFirstGroupOrNull(root.getAllEntry());
		validateGroup_NXentry(entry);
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
					"NXem_calorimetry");
		}

		// validate optional field 'identifier_analysis' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier_analysis = group.getLazyDataset("identifier_analysis");
				if (identifier_analysis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_analysis", identifier_analysis, NX_CHAR);
		}

		// validate optional child group 'profiling' of type NXcs_profiling
		if (group.getChild("profiling", NXcs_profiling.class) != null) {
			validateGroup_NXentry_profiling(group.getChild("profiling", NXcs_profiling.class));
		}

		// validate optional child group 'program1' of type NXprogram
		if (group.getChild("program1", NXprogram.class) != null) {
			validateGroup_NXentry_program1(group.getChild("program1", NXprogram.class));
		}

		// validate optional child group 'environment' of type NXcollection
		if (group.getCollection("environment") != null) {
			validateGroup_NXentry_environment(group.getCollection("environment"));
		}

		// validate optional child group 'userID' of type NXuser
		if (group.getUser("userID") != null) {
			validateGroup_NXentry_userID(group.getUser("userID"));
		}

		// validate optional child group 'sample' of type NXsample
		if (group.getSample() != null) {
			validateGroup_NXentry_sample(group.getSample());
		}

		// validate optional child group 'citeID' of type NXcite
		if (group.getChild("citeID", NXcite.class) != null) {
			validateGroup_NXentry_citeID(group.getChild("citeID", NXcite.class));
		}

		// validate optional child group 'diffraction_space' of type NXcoordinate_system
		if (group.getChild("diffraction_space", NXcoordinate_system.class) != null) {
			validateGroup_NXentry_diffraction_space(group.getChild("diffraction_space", NXcoordinate_system.class));
		}

		// validate child group 'diffraction' of type NXnote
		validateGroup_NXentry_diffraction(group.getChild("diffraction", NXnote.class));

		// validate child group 'actuator' of type NXnote
		validateGroup_NXentry_actuator(group.getChild("actuator", NXnote.class));

		// validate child group 'config' of type NXnote
		validateGroup_NXentry_config(group.getChild("config", NXnote.class));

		// validate child group 'synchronization' of type NXprocess
		validateGroup_NXentry_synchronization(group.getProcess("synchronization"));

		// validate child group 'pattern_center' of type NXprocess
		validateGroup_NXentry_pattern_center(group.getProcess("pattern_center"));

		// validate optional child group 'distortion_correction' of type NXprocess
		if (group.getProcess("distortion_correction") != null) {
			validateGroup_NXentry_distortion_correction(group.getProcess("distortion_correction"));
		}

		// validate child group 'integration' of type NXprocess
		validateGroup_NXentry_integration(group.getProcess("integration"));

		// validate optional child group 'background_subtraction' of type NXprocess
		if (group.getProcess("background_subtraction") != null) {
			validateGroup_NXentry_background_subtraction(group.getProcess("background_subtraction"));
		}
	}

	/**
	 * Validate optional group 'profiling' of type NXcs_profiling.
	 */
	private void validateGroup_NXentry_profiling(final NXcs_profiling group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("profiling", NXcs_profiling.class, group))) return;

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

		// validate field 'total_elapsed_time' of type NX_NUMBER.
		final ILazyDataset total_elapsed_time = group.getLazyDataset("total_elapsed_time");
		validateFieldNotNull("total_elapsed_time", total_elapsed_time);
		if (total_elapsed_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_elapsed_time", total_elapsed_time, NX_NUMBER);
			validateFieldUnits("total_elapsed_time", group.getDataNode("total_elapsed_time"), NX_TIME);
		}
	}

	/**
	 * Validate optional group 'program1' of type NXprogram.
	 */
	private void validateGroup_NXentry_program1(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("program1", NXprogram.class, group))) return;

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
	private void validateGroup_NXentry_environment(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("environment", NXcollection.class, group))) return;

		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_environment_programID(group.getChild("programID", NXprogram.class));
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_environment_programID(final NXprogram group) {
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
	 * Validate optional group 'userID' of type NXuser.
	 */
	private void validateGroup_NXentry_userID(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("userID", NXuser.class, group))) return;

	}

	/**
	 * Validate optional group 'sample' of type NXsample.
	 */
	private void validateGroup_NXentry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'is_simulation' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_simulation = group.getLazyDataset("is_simulation");
		validateFieldNotNull("is_simulation", is_simulation);
		if (is_simulation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_simulation", is_simulation, NX_BOOLEAN);
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
	 * Validate optional group 'citeID' of type NXcite.
	 */
	private void validateGroup_NXentry_citeID(final NXcite group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("citeID", NXcite.class, group))) return;

	}

	/**
	 * Validate optional group 'diffraction_space' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_diffraction_space(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("diffraction_space", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate group 'diffraction' of type NXnote.
	 */
	private void validateGroup_NXentry_diffraction(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("diffraction", NXnote.class, group))) return;

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
	}

	/**
	 * Validate group 'actuator' of type NXnote.
	 */
	private void validateGroup_NXentry_actuator(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("actuator", NXnote.class, group))) return;

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
	}

	/**
	 * Validate group 'config' of type NXnote.
	 */
	private void validateGroup_NXentry_config(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("config", NXnote.class, group))) return;

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
	}

	/**
	 * Validate group 'synchronization' of type NXprocess.
	 */
	private void validateGroup_NXentry_synchronization(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("synchronization", NXprocess.class, group))) return;

		// validate field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
		validateFieldNotNull("sequence_index", sequence_index);
		if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate field 'start_time' of type NX_DATE_TIME. Note: field not defined in base class.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		validateFieldNotNull("start_time", start_time);
		if (start_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start_time", start_time, NX_DATE_TIME);
		}

		// validate field 'indices_pattern' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_pattern = group.getLazyDataset("indices_pattern");
		validateFieldNotNull("indices_pattern", indices_pattern);
		if (indices_pattern != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_pattern", indices_pattern, NX_INT);
			validateFieldUnits("indices_pattern", group.getDataNode("indices_pattern"), NX_UNITLESS);
			validateFieldRank("indices_pattern", indices_pattern, 1);
			validateFieldDimensions("indices_pattern", indices_pattern, null, "n_p");
		}

		// validate field 'delta_time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset delta_time = group.getLazyDataset("delta_time");
		validateFieldNotNull("delta_time", delta_time);
		if (delta_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("delta_time", delta_time, NX_FLOAT);
			validateFieldUnits("delta_time", group.getDataNode("delta_time"), NX_TIME);
			validateFieldRank("delta_time", delta_time, 1);
			validateFieldDimensions("delta_time", delta_time, null, "n_p");
		}
	}

	/**
	 * Validate group 'pattern_center' of type NXprocess.
	 */
	private void validateGroup_NXentry_pattern_center(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pattern_center", NXprocess.class, group))) return;

		// validate field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
		validateFieldNotNull("sequence_index", sequence_index);
		if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate field 'position' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset position = group.getLazyDataset("position");
		validateFieldNotNull("position", position);
		if (position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("position", position, NX_FLOAT);
			validateFieldUnits("position", group.getDataNode("position"), NX_LENGTH);
			validateFieldRank("position", position, 2);
			validateFieldDimensions("position", position, null, "n_p", 2);
		}
	}

	/**
	 * Validate optional group 'distortion_correction' of type NXprocess.
	 */
	private void validateGroup_NXentry_distortion_correction(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("distortion_correction", NXprocess.class, group))) return;

		// validate field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
		validateFieldNotNull("sequence_index", sequence_index);
		if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate field 'center' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset center = group.getLazyDataset("center");
		validateFieldNotNull("center", center);
		if (center != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center", center, NX_NUMBER);
			validateFieldUnits("center", group.getDataNode("center"), NX_LENGTH);
			validateFieldRank("center", center, 2);
			validateFieldDimensions("center", center, null, "n_p", 2);
		}
	}

	/**
	 * Validate group 'integration' of type NXprocess.
	 */
	private void validateGroup_NXentry_integration(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("integration", NXprocess.class, group))) return;

		// validate field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
		validateFieldNotNull("sequence_index", sequence_index);
		if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}

		// validate optional child group 'resultBACKGROUND' of type NXdata
		if (group.getData("resultBACKGROUND") != null) {
			validateGroup_NXentry_integration_resultBACKGROUND(group.getData("resultBACKGROUND"));
		}
	}

	/**
	 * Validate optional group 'resultBACKGROUND' of type NXdata.
	 */
	private void validateGroup_NXentry_integration_resultBACKGROUND(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("resultBACKGROUND", NXdata.class, group))) return;
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

		// validate field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'intensity' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
		validateFieldNotNull("intensity", intensity);
		if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_FLOAT);
			validateFieldUnits("intensity", group.getDataNode("intensity"), NX_UNITLESS);
			validateFieldRank("intensity", intensity, 2);
			validateFieldDimensions("intensity", intensity, null, "n_p", "n_f");
		// validate attribute 'long_name' of field 'intensity' of type NX_CHAR.
		final Attribute intensity_attr_long_name = group.getDataNode("intensity").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", intensity_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", intensity_attr_long_name, NX_CHAR);

		}

		// validate optional field 'indices_pattern' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_pattern = group.getLazyDataset("indices_pattern");
				if (indices_pattern != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_pattern", indices_pattern, NX_INT);
			validateFieldUnits("indices_pattern", group.getDataNode("indices_pattern"), NX_UNITLESS);
			validateFieldRank("indices_pattern", indices_pattern, 1);
			validateFieldDimensions("indices_pattern", indices_pattern, null, "n_p");
		// validate attribute 'long_name' of field 'indices_pattern' of type NX_CHAR.
		final Attribute indices_pattern_attr_long_name = group.getDataNode("indices_pattern").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", indices_pattern_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", indices_pattern_attr_long_name, NX_CHAR);

		}

		// validate field 's' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset s = group.getLazyDataset("s");
		validateFieldNotNull("s", s);
		if (s != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("s", s, NX_FLOAT);
			validateFieldUnits("s", group.getDataNode("s"), NX_ANY);
			validateFieldRank("s", s, 1);
			validateFieldDimensions("s", s, null, "n_f");
		// validate attribute 'long_name' of field 's' of type NX_CHAR.
		final Attribute s_attr_long_name = group.getDataNode("s").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", s_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", s_attr_long_name, NX_CHAR);

		}

		// validate field 'time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset time = group.getLazyDataset("time");
		validateFieldNotNull("time", time);
		if (time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time", time, NX_FLOAT);
			validateFieldUnits("time", group.getDataNode("time"), NX_TIME);
			validateFieldRank("time", time, 1);
			validateFieldDimensions("time", time, null, "n_p");
		}
	}

	/**
	 * Validate optional group 'background_subtraction' of type NXprocess.
	 */
	private void validateGroup_NXentry_background_subtraction(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("background_subtraction", NXprocess.class, group))) return;

		// validate field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
		validateFieldNotNull("sequence_index", sequence_index);
		if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
		}
	}
}
