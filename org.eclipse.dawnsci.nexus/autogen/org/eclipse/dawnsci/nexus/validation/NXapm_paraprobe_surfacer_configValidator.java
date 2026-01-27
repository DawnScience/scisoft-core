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
import org.eclipse.dawnsci.nexus.NXparameters;

/**
 * Validator for the application definition 'NXapm_paraprobe_surfacer_config'.
 */
public class NXapm_paraprobe_surfacer_configValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_surfacer_configValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_SURFACER_CONFIG);
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
					"NXapm_paraprobe_surfacer_config");
		}

		// validate child group 'surface_meshingID' of type NXapm_paraprobe_tool_parameters
		validateGroup_NXentry_surface_meshingID(group.getChild("surface_meshingID", NXapm_paraprobe_tool_parameters.class));
	}

	/**
	 * Validate group 'surface_meshingID' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_surface_meshingID(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("surface_meshingID", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate field 'alpha_value_choice' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset alpha_value_choice = group.getLazyDataset("alpha_value_choice");
		validateFieldNotNull("alpha_value_choice", alpha_value_choice);
		if (alpha_value_choice != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("alpha_value_choice", alpha_value_choice, NX_CHAR);
			validateFieldEnumeration("alpha_value_choice", alpha_value_choice,
					"convex_hull_naive",
					"convex_hull_refine",
					"smallest_solid",
					"cgal_optimal",
					"set_of_values",
					"set_of_alpha_wrappings");
		}

		// validate field 'alpha_values' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset alpha_values = group.getLazyDataset("alpha_values");
		validateFieldNotNull("alpha_values", alpha_values);
		if (alpha_values != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("alpha_values", alpha_values, NX_FLOAT);
			validateFieldUnits("alpha_values", group.getDataNode("alpha_values"), NX_ANY);
			validateFieldRank("alpha_values", alpha_values, 1);
			validateFieldDimensions("alpha_values", alpha_values, null, "n_alpha_values");
		}

		// validate field 'offset_values' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset offset_values = group.getLazyDataset("offset_values");
		validateFieldNotNull("offset_values", offset_values);
		if (offset_values != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("offset_values", offset_values, NX_FLOAT);
			validateFieldUnits("offset_values", group.getDataNode("offset_values"), NX_LENGTH);
			validateFieldRank("offset_values", offset_values, 1);
			validateFieldDimensions("offset_values", offset_values, null, "n_alpha_values");
		}

		// validate field 'has_exterior_facets' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_exterior_facets = group.getLazyDataset("has_exterior_facets");
		validateFieldNotNull("has_exterior_facets", has_exterior_facets);
		if (has_exterior_facets != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_exterior_facets", has_exterior_facets, NX_BOOLEAN);
		}

		// validate field 'has_closure' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_closure = group.getLazyDataset("has_closure");
		validateFieldNotNull("has_closure", has_closure);
		if (has_closure != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_closure", has_closure, NX_BOOLEAN);
		}

		// validate field 'has_interior_tetrahedra' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_interior_tetrahedra = group.getLazyDataset("has_interior_tetrahedra");
		validateFieldNotNull("has_interior_tetrahedra", has_interior_tetrahedra);
		if (has_interior_tetrahedra != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_interior_tetrahedra", has_interior_tetrahedra, NX_BOOLEAN);
		}
		// validate child group 'preprocessing' of type NXparameters
		validateGroup_NXentry_surface_meshingID_preprocessing(group.getChild("preprocessing", NXparameters.class));
	}

	/**
	 * Validate group 'preprocessing' of type NXparameters.
	 */
	private void validateGroup_NXentry_surface_meshingID_preprocessing(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("preprocessing", NXparameters.class, group))) return;

		// validate field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
		validateFieldNotNull("method", method);
		if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
			validateFieldEnumeration("method", method,
					"default",
					"percolation");
		}

		// validate field 'kernel_width' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset kernel_width = group.getLazyDataset("kernel_width");
		validateFieldNotNull("kernel_width", kernel_width);
		if (kernel_width != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kernel_width", kernel_width, NX_UINT);
			validateFieldUnits("kernel_width", group.getDataNode("kernel_width"), NX_UNITLESS);
		}
	}
}
