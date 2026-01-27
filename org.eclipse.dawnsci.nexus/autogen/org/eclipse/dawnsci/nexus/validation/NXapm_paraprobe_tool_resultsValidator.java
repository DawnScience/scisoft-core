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
import org.eclipse.dawnsci.nexus.NXapm_paraprobe_tool_process;
import org.eclipse.dawnsci.nexus.NXcs_filter_boolean_mask;
import org.eclipse.dawnsci.nexus.NXapm_paraprobe_tool_common;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXprogram;
import org.eclipse.dawnsci.nexus.NXcs_profiling;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXcoordinate_system;

/**
 * Validator for the application definition 'NXapm_paraprobe_tool_results'.
 */
public class NXapm_paraprobe_tool_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_tool_resultsValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_TOOL_RESULTS);
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
		// validate attribute 'version' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (!(validateAttributeNotNull("version", definition_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", definition_attr_version, NX_CHAR);

		}

		// validate child group 'TASKPROCESSED' of type NXapm_paraprobe_tool_process
		validateGroup_NXentry_TASKPROCESSED(group.getChild("TASKPROCESSED", NXapm_paraprobe_tool_process.class));

		// validate child group 'common' of type NXapm_paraprobe_tool_common
		validateGroup_NXentry_common(group.getChild("common", NXapm_paraprobe_tool_common.class));
	}

	/**
	 * Validate group 'TASKPROCESSED' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_TASKPROCESSED(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("TASKPROCESSED", NXapm_paraprobe_tool_process.class, group))) return;

		// validate child group 'window' of type NXcs_filter_boolean_mask
		validateGroup_NXentry_TASKPROCESSED_window(group.getChild("window", NXcs_filter_boolean_mask.class));
	}

	/**
	 * Validate group 'window' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_TASKPROCESSED_window(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("window", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_ions' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_ions = group.getLazyDataset("number_of_ions");
		validateFieldNotNull("number_of_ions", number_of_ions);
		if (number_of_ions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_ions", number_of_ions, NX_UINT);
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
	 * Validate group 'common' of type NXapm_paraprobe_tool_common.
	 */
	private void validateGroup_NXentry_common(final NXapm_paraprobe_tool_common group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("common", NXapm_paraprobe_tool_common.class, group))) return;

		// validate field 'status' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset status = group.getLazyDataset("status");
		validateFieldNotNull("status", status);
		if (status != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("status", status, NX_CHAR);
		}

		// validate field 'identifier_analysis' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset identifier_analysis = group.getLazyDataset("identifier_analysis");
		validateFieldNotNull("identifier_analysis", identifier_analysis);
		if (identifier_analysis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_analysis", identifier_analysis, NX_UINT);
		}

		// validate child group 'config' of type NXnote
		validateGroup_NXentry_common_config(group.getChild("config", NXnote.class));

		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_common_programID(group.getChild("programID", NXprogram.class));

		// validate optional child group 'profiling' of type NXcs_profiling
		if (group.getChild("profiling", NXcs_profiling.class) != null) {
			validateGroup_NXentry_common_profiling(group.getChild("profiling", NXcs_profiling.class));
		}

		// validate optional child group 'userID' of type NXuser
		if (group.getChild("userID", NXuser.class) != null) {
			validateGroup_NXentry_common_userID(group.getChild("userID", NXuser.class));
		}

		// validate child group 'paraprobe_reference_frame' of type NXcoordinate_system
		validateGroup_NXentry_common_paraprobe_reference_frame(group.getChild("paraprobe_reference_frame", NXcoordinate_system.class));
	}

	/**
	 * Validate group 'config' of type NXnote.
	 */
	private void validateGroup_NXentry_common_config(final NXnote group) {
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
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_common_programID(final NXprogram group) {
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
	 * Validate optional group 'profiling' of type NXcs_profiling.
	 */
	private void validateGroup_NXentry_common_profiling(final NXcs_profiling group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("profiling", NXcs_profiling.class, group))) return;

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

		// validate field 'total_elapsed_time' of type NX_FLOAT.
		final ILazyDataset total_elapsed_time = group.getLazyDataset("total_elapsed_time");
		validateFieldNotNull("total_elapsed_time", total_elapsed_time);
		if (total_elapsed_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_elapsed_time", total_elapsed_time, NX_FLOAT);
			validateFieldUnits("total_elapsed_time", group.getDataNode("total_elapsed_time"), NX_TIME);
		}

		// validate field 'max_processes' of type NX_UINT.
		final ILazyDataset max_processes = group.getLazyDataset("max_processes");
		validateFieldNotNull("max_processes", max_processes);
		if (max_processes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max_processes", max_processes, NX_UINT);
			validateFieldUnits("max_processes", group.getDataNode("max_processes"), NX_UNITLESS);
		}

		// validate field 'max_threads' of type NX_UINT.
		final ILazyDataset max_threads = group.getLazyDataset("max_threads");
		validateFieldNotNull("max_threads", max_threads);
		if (max_threads != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max_threads", max_threads, NX_UINT);
			validateFieldUnits("max_threads", group.getDataNode("max_threads"), NX_UNITLESS);
		}

		// validate field 'max_gpus' of type NX_UINT.
		final ILazyDataset max_gpus = group.getLazyDataset("max_gpus");
		validateFieldNotNull("max_gpus", max_gpus);
		if (max_gpus != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max_gpus", max_gpus, NX_UINT);
			validateFieldUnits("max_gpus", group.getDataNode("max_gpus"), NX_UNITLESS);
		}
	}

	/**
	 * Validate optional group 'userID' of type NXuser.
	 */
	private void validateGroup_NXentry_common_userID(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("userID", NXuser.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}
	}

	/**
	 * Validate group 'paraprobe_reference_frame' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_common_paraprobe_reference_frame(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("paraprobe_reference_frame", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

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

		// validate field 'x' of type NX_NUMBER.
		final ILazyDataset x = group.getLazyDataset("x");
		validateFieldNotNull("x", x);
		if (x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x", x, NX_NUMBER);
			validateFieldUnits("x", group.getDataNode("x"), NX_LENGTH);
			validateFieldRank("x", x, 1);
			validateFieldDimensions("x", x, null, 3);
		}

		// validate field 'x_alias' of type NX_CHAR.
		final ILazyDataset x_alias = group.getLazyDataset("x_alias");
		validateFieldNotNull("x_alias", x_alias);
		if (x_alias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_alias", x_alias, NX_CHAR);
		}

		// validate field 'y' of type NX_NUMBER.
		final ILazyDataset y = group.getLazyDataset("y");
		validateFieldNotNull("y", y);
		if (y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y", y, NX_NUMBER);
			validateFieldUnits("y", group.getDataNode("y"), NX_LENGTH);
			validateFieldRank("y", y, 1);
			validateFieldDimensions("y", y, null, 3);
		}

		// validate field 'y_alias' of type NX_CHAR.
		final ILazyDataset y_alias = group.getLazyDataset("y_alias");
		validateFieldNotNull("y_alias", y_alias);
		if (y_alias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_alias", y_alias, NX_CHAR);
		}

		// validate field 'z' of type NX_NUMBER.
		final ILazyDataset z = group.getLazyDataset("z");
		validateFieldNotNull("z", z);
		if (z != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z", z, NX_NUMBER);
			validateFieldUnits("z", group.getDataNode("z"), NX_LENGTH);
			validateFieldRank("z", z, 1);
			validateFieldDimensions("z", z, null, 3);
		}

		// validate field 'z_alias' of type NX_CHAR.
		final ILazyDataset z_alias = group.getLazyDataset("z_alias");
		validateFieldNotNull("z_alias", z_alias);
		if (z_alias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z_alias", z_alias, NX_CHAR);
		}
	}
}
