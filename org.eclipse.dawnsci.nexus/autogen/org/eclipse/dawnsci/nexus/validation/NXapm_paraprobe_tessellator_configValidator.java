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

/**
 * Validator for the application definition 'NXapm_paraprobe_tessellator_config'.
 */
public class NXapm_paraprobe_tessellator_configValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_tessellator_configValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_TESSELLATOR_CONFIG);
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
					"NXapm_paraprobe_tessellator_config");
		}

		// validate child group 'tessellateID' of type NXapm_paraprobe_tool_parameters
		validateGroup_NXentry_tessellateID(group.getChild("tessellateID", NXapm_paraprobe_tool_parameters.class));
	}

	/**
	 * Validate group 'tessellateID' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_tessellateID(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("tessellateID", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
		validateFieldNotNull("method", method);
		if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
			validateFieldEnumeration("method", method,
					"default");
		}

		// validate field 'has_cell_volume' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_cell_volume = group.getLazyDataset("has_cell_volume");
		validateFieldNotNull("has_cell_volume", has_cell_volume);
		if (has_cell_volume != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_cell_volume", has_cell_volume, NX_BOOLEAN);
		}

		// validate field 'has_cell_neighbors' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_cell_neighbors = group.getLazyDataset("has_cell_neighbors");
		validateFieldNotNull("has_cell_neighbors", has_cell_neighbors);
		if (has_cell_neighbors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_cell_neighbors", has_cell_neighbors, NX_BOOLEAN);
		}

		// validate field 'has_cell_geometry' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_cell_geometry = group.getLazyDataset("has_cell_geometry");
		validateFieldNotNull("has_cell_geometry", has_cell_geometry);
		if (has_cell_geometry != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_cell_geometry", has_cell_geometry, NX_BOOLEAN);
		}

		// validate field 'has_cell_edge_detection' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_cell_edge_detection = group.getLazyDataset("has_cell_edge_detection");
		validateFieldNotNull("has_cell_edge_detection", has_cell_edge_detection);
		if (has_cell_edge_detection != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_cell_edge_detection", has_cell_edge_detection, NX_BOOLEAN);
		}
		// validate optional child group 'surface_distance' of type NXnote
		if (group.getChild("surface_distance", NXnote.class) != null) {
			validateGroup_NXentry_tessellateID_surface_distance(group.getChild("surface_distance", NXnote.class));
		}
	}

	/**
	 * Validate optional group 'surface_distance' of type NXnote.
	 */
	private void validateGroup_NXentry_tessellateID_surface_distance(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("surface_distance", NXnote.class, group))) return;

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

		// validate field 'distance' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_CHAR);
		}
	}
}
