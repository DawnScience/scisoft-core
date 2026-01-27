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
import org.eclipse.dawnsci.nexus.NXapm_paraprobe_tool_process;
import org.eclipse.dawnsci.nexus.NXcs_filter_boolean_mask;

/**
 * Validator for the application definition 'NXapm_paraprobe_distancer_results'.
 */
public class NXapm_paraprobe_distancer_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_distancer_resultsValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_DISTANCER_RESULTS);
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
					"NXapm_paraprobe_distancer_results");
		}

		// validate child group 'point_to_triangleID' of type NXapm_paraprobe_tool_process
		validateGroup_NXentry_point_to_triangleID(group.getChild("point_to_triangleID", NXapm_paraprobe_tool_process.class));
	}

	/**
	 * Validate group 'point_to_triangleID' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_point_to_triangleID(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("point_to_triangleID", NXapm_paraprobe_tool_process.class, group))) return;

		// validate field 'distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 1);
			validateFieldDimensions("distance", distance, null, "n_ions");
		}

		// validate optional field 'indices_triangle' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_triangle = group.getLazyDataset("indices_triangle");
				if (indices_triangle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_triangle", indices_triangle, NX_INT);
			validateFieldUnits("indices_triangle", group.getDataNode("indices_triangle"), NX_UNITLESS);
			validateFieldRank("indices_triangle", indices_triangle, 1);
			validateFieldDimensions("indices_triangle", indices_triangle, null, "n_ions");
		}

		// validate optional field 'indices_point' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_point = group.getLazyDataset("indices_point");
				if (indices_point != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_point", indices_point, NX_INT);
			validateFieldUnits("indices_point", group.getDataNode("indices_point"), NX_UNITLESS);
			validateFieldRank("indices_point", indices_point, 1);
			validateFieldDimensions("indices_point", indices_point, null, "n_ions");
		}

		// validate optional child group 'sign_valid' of type NXcs_filter_boolean_mask
		if (group.getChild("sign_valid", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_point_to_triangleID_sign_valid(group.getChild("sign_valid", NXcs_filter_boolean_mask.class));
		}

		// validate optional child group 'window_triangles' of type NXcs_filter_boolean_mask
		if (group.getChild("window_triangles", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_point_to_triangleID_window_triangles(group.getChild("window_triangles", NXcs_filter_boolean_mask.class));
		}
	}

	/**
	 * Validate optional group 'sign_valid' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_point_to_triangleID_sign_valid(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sign_valid", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_triangles' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_triangles = group.getLazyDataset("number_of_triangles");
		validateFieldNotNull("number_of_triangles", number_of_triangles);
		if (number_of_triangles != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_triangles", number_of_triangles, NX_UINT);
			validateFieldUnits("number_of_triangles", group.getDataNode("number_of_triangles"), NX_UNITLESS);
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
			validateFieldRank("mask", mask, 1);
			validateFieldDimensions("mask", mask, null, "n_ions");
		}
	}

	/**
	 * Validate optional group 'window_triangles' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_point_to_triangleID_window_triangles(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("window_triangles", NXcs_filter_boolean_mask.class, group))) return;

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
			validateFieldRank("mask", mask, 1);
			validateFieldDimensions("mask", mask, null, "n_tri");
		}
	}
}
