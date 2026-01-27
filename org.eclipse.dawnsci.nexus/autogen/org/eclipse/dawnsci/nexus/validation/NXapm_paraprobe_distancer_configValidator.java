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

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXapm_paraprobe_tool_parameters;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXmatch_filter;

/**
 * Validator for the application definition 'NXapm_paraprobe_distancer_config'.
 */
public class NXapm_paraprobe_distancer_configValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_distancer_configValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_DISTANCER_CONFIG);
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
					"NXapm_paraprobe_distancer_config");
		}

		// validate child group 'point_to_triangleID' of type NXapm_paraprobe_tool_parameters
		validateGroup_NXentry_point_to_triangleID(group.getChild("point_to_triangleID", NXapm_paraprobe_tool_parameters.class));
	}

	/**
	 * Validate group 'point_to_triangleID' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_point_to_triangleID(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("point_to_triangleID", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
		validateFieldNotNull("method", method);
		if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
			validateFieldEnumeration("method", method,
					"default",
					"skin");
		}

		// validate optional field 'threshold_distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset threshold_distance = group.getLazyDataset("threshold_distance");
				if (threshold_distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("threshold_distance", threshold_distance, NX_FLOAT);
			validateFieldUnits("threshold_distance", group.getDataNode("threshold_distance"), NX_LENGTH);
		}

		// validate field 'number_of_triangle_sets' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_triangle_sets = group.getLazyDataset("number_of_triangle_sets");
		validateFieldNotNull("number_of_triangle_sets", number_of_triangle_sets);
		if (number_of_triangle_sets != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_triangle_sets", number_of_triangle_sets, NX_UINT);
			validateFieldUnits("number_of_triangle_sets", group.getDataNode("number_of_triangle_sets"), NX_UNITLESS);
		}

		// validate child group 'triangle_setID' of type NXnote
		validateGroup_NXentry_point_to_triangleID_triangle_setID(group.getChild("triangle_setID", NXnote.class));
	}

	/**
	 * Validate group 'triangle_setID' of type NXnote.
	 */
	private void validateGroup_NXentry_point_to_triangleID_triangle_setID(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("triangle_setID", NXnote.class, group))) return;

		// validate field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
		validateFieldNotNull("algorithm", algorithm);
		if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}

		// validate field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
		validateFieldNotNull("checksum", checksum);
		if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate field 'vertices' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset vertices = group.getLazyDataset("vertices");
		validateFieldNotNull("vertices", vertices);
		if (vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertices", vertices, NX_CHAR);
		}

		// validate field 'indices' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset indices = group.getLazyDataset("indices");
		validateFieldNotNull("indices", indices);
		if (indices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices", indices, NX_CHAR);
		}

		// validate optional field 'vertex_normals' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset vertex_normals = group.getLazyDataset("vertex_normals");
				if (vertex_normals != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertex_normals", vertex_normals, NX_CHAR);
		}

		// validate optional field 'face_normals' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset face_normals = group.getLazyDataset("face_normals");
				if (face_normals != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("face_normals", face_normals, NX_CHAR);
		}

		// validate optional field 'indices_patch' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset indices_patch = group.getLazyDataset("indices_patch");
				if (indices_patch != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_patch", indices_patch, NX_CHAR);
		}

		// validate optional child group 'patch_filter' of type NXmatch_filter
		if (group.getChild("patch_filter", NXmatch_filter.class) != null) {
			validateGroup_NXentry_point_to_triangleID_triangle_setID_patch_filter(group.getChild("patch_filter", NXmatch_filter.class));
		}
	}

	/**
	 * Validate optional group 'patch_filter' of type NXmatch_filter.
	 */
	private void validateGroup_NXentry_point_to_triangleID_triangle_setID_patch_filter(final NXmatch_filter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("patch_filter", NXmatch_filter.class, group))) return;

		// validate field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
		validateFieldNotNull("method", method);
		if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
		}

		// validate field 'match' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset match = group.getLazyDataset("match");
		validateFieldNotNull("match", match);
		if (match != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("match", match, NX_NUMBER);
		}
	}
}
