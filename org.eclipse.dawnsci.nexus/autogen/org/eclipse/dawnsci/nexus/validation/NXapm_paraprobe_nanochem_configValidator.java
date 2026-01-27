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
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXroi_process;
import org.eclipse.dawnsci.nexus.NXcg_cylinder;

/**
 * Validator for the application definition 'NXapm_paraprobe_nanochem_config'.
 */
public class NXapm_paraprobe_nanochem_configValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_nanochem_configValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_NANOCHEM_CONFIG);
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
					"NXapm_paraprobe_nanochem_config");
		}

		// validate optional child group 'delocalizationID' of type NXapm_paraprobe_tool_parameters
		if (group.getChild("delocalizationID", NXapm_paraprobe_tool_parameters.class) != null) {
			validateGroup_NXentry_delocalizationID(group.getChild("delocalizationID", NXapm_paraprobe_tool_parameters.class));
		}

		// validate optional child group 'interface_meshingID' of type NXapm_paraprobe_tool_parameters
		if (group.getChild("interface_meshingID", NXapm_paraprobe_tool_parameters.class) != null) {
			validateGroup_NXentry_interface_meshingID(group.getChild("interface_meshingID", NXapm_paraprobe_tool_parameters.class));
		}

		// validate optional child group 'oned_profileID' of type NXapm_paraprobe_tool_parameters
		if (group.getChild("oned_profileID", NXapm_paraprobe_tool_parameters.class) != null) {
			validateGroup_NXentry_oned_profileID(group.getChild("oned_profileID", NXapm_paraprobe_tool_parameters.class));
		}
	}

	/**
	 * Validate optional group 'delocalizationID' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_delocalizationID(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("delocalizationID", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
		validateFieldNotNull("method", method);
		if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
			validateFieldEnumeration("method", method,
					"compute",
					"load_existent");
		}

		// validate field 'nuclide_whitelist' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset nuclide_whitelist = group.getLazyDataset("nuclide_whitelist");
		validateFieldNotNull("nuclide_whitelist", nuclide_whitelist);
		if (nuclide_whitelist != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("nuclide_whitelist", nuclide_whitelist, NX_UINT);
			validateFieldUnits("nuclide_whitelist", group.getDataNode("nuclide_whitelist"), NX_UNITLESS);
			validateFieldDimensions("nuclide_whitelist", nuclide_whitelist, null, "n_ityp_deloc_cand", "n_ivec_max");
		}

		// validate field 'grid_resolution' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset grid_resolution = group.getLazyDataset("grid_resolution");
		validateFieldNotNull("grid_resolution", grid_resolution);
		if (grid_resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("grid_resolution", grid_resolution, NX_FLOAT);
			validateFieldUnits("grid_resolution", group.getDataNode("grid_resolution"), NX_LENGTH);
			validateFieldDimensions("grid_resolution", grid_resolution, null, "n_grid");
		}

		// validate field 'kernel_size' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset kernel_size = group.getLazyDataset("kernel_size");
		validateFieldNotNull("kernel_size", kernel_size);
		if (kernel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kernel_size", kernel_size, NX_UINT);
			validateFieldUnits("kernel_size", group.getDataNode("kernel_size"), NX_UNITLESS);
		}

		// validate field 'kernel_variance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset kernel_variance = group.getLazyDataset("kernel_variance");
		validateFieldNotNull("kernel_variance", kernel_variance);
		if (kernel_variance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kernel_variance", kernel_variance, NX_FLOAT);
			validateFieldUnits("kernel_variance", group.getDataNode("kernel_variance"), NX_LENGTH);
			validateFieldDimensions("kernel_variance", kernel_variance, null, "n_var");
		}

		// validate field 'normalization' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset normalization = group.getLazyDataset("normalization");
		validateFieldNotNull("normalization", normalization);
		if (normalization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("normalization", normalization, NX_CHAR);
			validateFieldEnumeration("normalization", normalization,
					"none",
					"composition",
					"concentration");
		}

		// validate field 'has_scalar_fields' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_scalar_fields = group.getLazyDataset("has_scalar_fields");
		validateFieldNotNull("has_scalar_fields", has_scalar_fields);
		if (has_scalar_fields != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_scalar_fields", has_scalar_fields, NX_BOOLEAN);
		}

		// validate child group 'surface' of type NXnote
		validateGroup_NXentry_delocalizationID_surface(group.getChild("surface", NXnote.class));

		// validate optional child group 'surface_distance' of type NXnote
		if (group.getChild("surface_distance", NXnote.class) != null) {
			validateGroup_NXentry_delocalizationID_surface_distance(group.getChild("surface_distance", NXnote.class));
		}

		// validate child group 'decomposition' of type NXmatch_filter
		validateGroup_NXentry_delocalizationID_decomposition(group.getChild("decomposition", NXmatch_filter.class));

		// validate child group 'input' of type NXnote
		validateGroup_NXentry_delocalizationID_input(group.getChild("input", NXnote.class));

		// validate optional child group 'isosurfacing' of type NXprocess
		if (group.getChild("isosurfacing", NXprocess.class) != null) {
			validateGroup_NXentry_delocalizationID_isosurfacing(group.getChild("isosurfacing", NXprocess.class));
		}
	}

	/**
	 * Validate group 'surface' of type NXnote.
	 */
	private void validateGroup_NXentry_delocalizationID_surface(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("surface", NXnote.class, group))) return;

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
	}

	/**
	 * Validate optional group 'surface_distance' of type NXnote.
	 */
	private void validateGroup_NXentry_delocalizationID_surface_distance(final NXnote group) {
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

	/**
	 * Validate group 'decomposition' of type NXmatch_filter.
	 */
	private void validateGroup_NXentry_delocalizationID_decomposition(final NXmatch_filter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("decomposition", NXmatch_filter.class, group))) return;

		// validate field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
		validateFieldNotNull("method", method);
		if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
			validateFieldEnumeration("method", method,
					"resolve_unknown",
					"resolve_point",
					"resolve_atom",
					"resolve_element",
					"resolve_element_charge",
					"resolve_isotope",
					"resolve_isotope_charge");
		}

		// validate field 'nuclide_whitelist' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset nuclide_whitelist = group.getLazyDataset("nuclide_whitelist");
		validateFieldNotNull("nuclide_whitelist", nuclide_whitelist);
		if (nuclide_whitelist != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("nuclide_whitelist", nuclide_whitelist, NX_UINT);
			validateFieldUnits("nuclide_whitelist", group.getDataNode("nuclide_whitelist"), NX_UNITLESS);
		}

		// validate field 'charge_state_whitelist' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset charge_state_whitelist = group.getLazyDataset("charge_state_whitelist");
		validateFieldNotNull("charge_state_whitelist", charge_state_whitelist);
		if (charge_state_whitelist != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("charge_state_whitelist", charge_state_whitelist, NX_INT);
			validateFieldUnits("charge_state_whitelist", group.getDataNode("charge_state_whitelist"), NX_DIMENSIONLESS);
		}
	}

	/**
	 * Validate group 'input' of type NXnote.
	 */
	private void validateGroup_NXentry_delocalizationID_input(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("input", NXnote.class, group))) return;

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

		// validate field 'results' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset results = group.getLazyDataset("results");
		validateFieldNotNull("results", results);
		if (results != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("results", results, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'isosurfacing' of type NXprocess.
	 */
	private void validateGroup_NXentry_delocalizationID_isosurfacing(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("isosurfacing", NXprocess.class, group))) return;

		// validate field 'edge_method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset edge_method = group.getLazyDataset("edge_method");
		validateFieldNotNull("edge_method", edge_method);
		if (edge_method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("edge_method", edge_method, NX_CHAR);
			validateFieldEnumeration("edge_method", edge_method,
					"default",
					"keep_edge_triangles");
		}

		// validate field 'edge_threshold' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset edge_threshold = group.getLazyDataset("edge_threshold");
		validateFieldNotNull("edge_threshold", edge_threshold);
		if (edge_threshold != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("edge_threshold", edge_threshold, NX_FLOAT);
			validateFieldUnits("edge_threshold", group.getDataNode("edge_threshold"), NX_LENGTH);
		}

		// validate field 'phi' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset phi = group.getLazyDataset("phi");
		validateFieldNotNull("phi", phi);
		if (phi != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("phi", phi, NX_FLOAT);
			validateFieldUnits("phi", group.getDataNode("phi"), NX_ANY);
		}

		// validate field 'has_triangle_soup' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_triangle_soup = group.getLazyDataset("has_triangle_soup");
		validateFieldNotNull("has_triangle_soup", has_triangle_soup);
		if (has_triangle_soup != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_triangle_soup", has_triangle_soup, NX_BOOLEAN);
		}

		// validate field 'has_object' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_object = group.getLazyDataset("has_object");
		validateFieldNotNull("has_object", has_object);
		if (has_object != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_object", has_object, NX_BOOLEAN);
		}

		// validate field 'has_object_geometry' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_object_geometry = group.getLazyDataset("has_object_geometry");
		validateFieldNotNull("has_object_geometry", has_object_geometry);
		if (has_object_geometry != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_object_geometry", has_object_geometry, NX_BOOLEAN);
		}

		// validate field 'has_object_properties' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_object_properties = group.getLazyDataset("has_object_properties");
		validateFieldNotNull("has_object_properties", has_object_properties);
		if (has_object_properties != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_object_properties", has_object_properties, NX_BOOLEAN);
		}

		// validate field 'has_object_obb' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_object_obb = group.getLazyDataset("has_object_obb");
		validateFieldNotNull("has_object_obb", has_object_obb);
		if (has_object_obb != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_object_obb", has_object_obb, NX_BOOLEAN);
		}

		// validate field 'has_object_ions' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_object_ions = group.getLazyDataset("has_object_ions");
		validateFieldNotNull("has_object_ions", has_object_ions);
		if (has_object_ions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_object_ions", has_object_ions, NX_BOOLEAN);
		}

		// validate field 'has_object_edge_contact' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_object_edge_contact = group.getLazyDataset("has_object_edge_contact");
		validateFieldNotNull("has_object_edge_contact", has_object_edge_contact);
		if (has_object_edge_contact != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_object_edge_contact", has_object_edge_contact, NX_BOOLEAN);
		}

		// validate field 'has_proxy' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_proxy = group.getLazyDataset("has_proxy");
		validateFieldNotNull("has_proxy", has_proxy);
		if (has_proxy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_proxy", has_proxy, NX_BOOLEAN);
		}

		// validate field 'has_proxy_geometry' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_proxy_geometry = group.getLazyDataset("has_proxy_geometry");
		validateFieldNotNull("has_proxy_geometry", has_proxy_geometry);
		if (has_proxy_geometry != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_proxy_geometry", has_proxy_geometry, NX_BOOLEAN);
		}

		// validate field 'has_proxy_properties' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_proxy_properties = group.getLazyDataset("has_proxy_properties");
		validateFieldNotNull("has_proxy_properties", has_proxy_properties);
		if (has_proxy_properties != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_proxy_properties", has_proxy_properties, NX_BOOLEAN);
		}

		// validate field 'has_proxy_obb' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_proxy_obb = group.getLazyDataset("has_proxy_obb");
		validateFieldNotNull("has_proxy_obb", has_proxy_obb);
		if (has_proxy_obb != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_proxy_obb", has_proxy_obb, NX_BOOLEAN);
		}

		// validate field 'has_proxy_ions' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_proxy_ions = group.getLazyDataset("has_proxy_ions");
		validateFieldNotNull("has_proxy_ions", has_proxy_ions);
		if (has_proxy_ions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_proxy_ions", has_proxy_ions, NX_BOOLEAN);
		}

		// validate field 'has_proxy_edge_contact' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_proxy_edge_contact = group.getLazyDataset("has_proxy_edge_contact");
		validateFieldNotNull("has_proxy_edge_contact", has_proxy_edge_contact);
		if (has_proxy_edge_contact != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_proxy_edge_contact", has_proxy_edge_contact, NX_BOOLEAN);
		}

		// validate field 'has_object_proxigram' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_object_proxigram = group.getLazyDataset("has_object_proxigram");
		validateFieldNotNull("has_object_proxigram", has_object_proxigram);
		if (has_object_proxigram != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_object_proxigram", has_object_proxigram, NX_BOOLEAN);
		}

		// validate field 'has_object_proxigram_edge_contact' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_object_proxigram_edge_contact = group.getLazyDataset("has_object_proxigram_edge_contact");
		validateFieldNotNull("has_object_proxigram_edge_contact", has_object_proxigram_edge_contact);
		if (has_object_proxigram_edge_contact != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_object_proxigram_edge_contact", has_object_proxigram_edge_contact, NX_BOOLEAN);
		}
	}

	/**
	 * Validate optional group 'interface_meshingID' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_interface_meshingID(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("interface_meshingID", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate field 'initialization' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset initialization = group.getLazyDataset("initialization");
		validateFieldNotNull("initialization", initialization);
		if (initialization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("initialization", initialization, NX_CHAR);
			validateFieldEnumeration("initialization", initialization,
					"default",
					"control_point_file");
		}

		// validate field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
		validateFieldNotNull("method", method);
		if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
			validateFieldEnumeration("method", method,
					"pca_plus_dcom");
		}

		// validate field 'number_of_iterations' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_iterations = group.getLazyDataset("number_of_iterations");
		validateFieldNotNull("number_of_iterations", number_of_iterations);
		if (number_of_iterations != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_iterations", number_of_iterations, NX_UINT);
			validateFieldUnits("number_of_iterations", group.getDataNode("number_of_iterations"), NX_UNITLESS);
		}

		// validate field 'target_edge_length' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset target_edge_length = group.getLazyDataset("target_edge_length");
		validateFieldNotNull("target_edge_length", target_edge_length);
		if (target_edge_length != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("target_edge_length", target_edge_length, NX_FLOAT);
			validateFieldUnits("target_edge_length", group.getDataNode("target_edge_length"), NX_LENGTH);
			validateFieldDimensions("target_edge_length", target_edge_length, null, "n_fct_iterations");
		}

		// validate field 'target_dcom_radius' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset target_dcom_radius = group.getLazyDataset("target_dcom_radius");
		validateFieldNotNull("target_dcom_radius", target_dcom_radius);
		if (target_dcom_radius != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("target_dcom_radius", target_dcom_radius, NX_FLOAT);
			validateFieldUnits("target_dcom_radius", group.getDataNode("target_dcom_radius"), NX_LENGTH);
			validateFieldDimensions("target_dcom_radius", target_dcom_radius, null, "n_fct_iterations");
		}

		// validate field 'target_smoothing_step' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset target_smoothing_step = group.getLazyDataset("target_smoothing_step");
		validateFieldNotNull("target_smoothing_step", target_smoothing_step);
		if (target_smoothing_step != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("target_smoothing_step", target_smoothing_step, NX_UINT);
			validateFieldUnits("target_smoothing_step", group.getDataNode("target_smoothing_step"), NX_UNITLESS);
			validateFieldDimensions("target_smoothing_step", target_smoothing_step, null, "n_fct_iterations");
		}
		// validate optional child group 'surface' of type NXnote
		if (group.getChild("surface", NXnote.class) != null) {
			validateGroup_NXentry_interface_meshingID_surface(group.getChild("surface", NXnote.class));
		}

		// validate child group 'control_point' of type NXnote
		validateGroup_NXentry_interface_meshingID_control_point(group.getChild("control_point", NXnote.class));

		// validate child group 'decoration_filter' of type NXmatch_filter
		validateGroup_NXentry_interface_meshingID_decoration_filter(group.getChild("decoration_filter", NXmatch_filter.class));
	}

	/**
	 * Validate optional group 'surface' of type NXnote.
	 */
	private void validateGroup_NXentry_interface_meshingID_surface(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("surface", NXnote.class, group))) return;

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
	}

	/**
	 * Validate group 'control_point' of type NXnote.
	 */
	private void validateGroup_NXentry_interface_meshingID_control_point(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("control_point", NXnote.class, group))) return;

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

		// validate field 'control_points' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset control_points = group.getLazyDataset("control_points");
		validateFieldNotNull("control_points", control_points);
		if (control_points != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("control_points", control_points, NX_CHAR);
		}
	}

	/**
	 * Validate group 'decoration_filter' of type NXmatch_filter.
	 */
	private void validateGroup_NXentry_interface_meshingID_decoration_filter(final NXmatch_filter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("decoration_filter", NXmatch_filter.class, group))) return;

		// validate field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
		validateFieldNotNull("method", method);
		if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
			validateFieldEnumeration("method", method,
					"whitelist");
		}

		// validate field 'match' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset match = group.getLazyDataset("match");
		validateFieldNotNull("match", match);
		if (match != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("match", match, NX_UINT);
			validateFieldUnits("match", group.getDataNode("match"), NX_UNITLESS);
			validateFieldDimensions("match", match, null, "n_fct_filter_cand", "n_ivec_max");
		}
	}

	/**
	 * Validate optional group 'oned_profileID' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_oned_profileID(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("oned_profileID", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate field 'distancing_model' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset distancing_model = group.getLazyDataset("distancing_model");
		validateFieldNotNull("distancing_model", distancing_model);
		if (distancing_model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distancing_model", distancing_model, NX_CHAR);
			validateFieldEnumeration("distancing_model", distancing_model,
					"project_to_triangle_plane");
		}

		// validate field 'roi_orientation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset roi_orientation = group.getLazyDataset("roi_orientation");
		validateFieldNotNull("roi_orientation", roi_orientation);
		if (roi_orientation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("roi_orientation", roi_orientation, NX_CHAR);
			validateFieldEnumeration("roi_orientation", roi_orientation,
					"triangle_outer_unit_normal");
		}

		// validate field 'roi_cylinder_height' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset roi_cylinder_height = group.getLazyDataset("roi_cylinder_height");
		validateFieldNotNull("roi_cylinder_height", roi_cylinder_height);
		if (roi_cylinder_height != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("roi_cylinder_height", roi_cylinder_height, NX_FLOAT);
			validateFieldUnits("roi_cylinder_height", group.getDataNode("roi_cylinder_height"), NX_LENGTH);
		}

		// validate field 'roi_cylinder_radius' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset roi_cylinder_radius = group.getLazyDataset("roi_cylinder_radius");
		validateFieldNotNull("roi_cylinder_radius", roi_cylinder_radius);
		if (roi_cylinder_radius != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("roi_cylinder_radius", roi_cylinder_radius, NX_FLOAT);
			validateFieldUnits("roi_cylinder_radius", group.getDataNode("roi_cylinder_radius"), NX_LENGTH);
		}
		// validate optional child group 'surface' of type NXnote
		if (group.getChild("surface", NXnote.class) != null) {
			validateGroup_NXentry_oned_profileID_surface(group.getChild("surface", NXnote.class));
		}

		// validate optional child group 'surface_distance' of type NXnote
		if (group.getChild("surface_distance", NXnote.class) != null) {
			validateGroup_NXentry_oned_profileID_surface_distance(group.getChild("surface_distance", NXnote.class));
		}

		// validate optional child group 'feature' of type NXnote
		if (group.getChild("feature", NXnote.class) != null) {
			validateGroup_NXentry_oned_profileID_feature(group.getChild("feature", NXnote.class));
		}

		// validate optional child group 'feature_distance' of type NXnote
		if (group.getChild("feature_distance", NXnote.class) != null) {
			validateGroup_NXentry_oned_profileID_feature_distance(group.getChild("feature_distance", NXnote.class));
		}

		// validate optional child group 'user_defined_roi' of type NXroi_process
		if (group.getChild("user_defined_roi", NXroi_process.class) != null) {
			validateGroup_NXentry_oned_profileID_user_defined_roi(group.getChild("user_defined_roi", NXroi_process.class));
		}
	}

	/**
	 * Validate optional group 'surface' of type NXnote.
	 */
	private void validateGroup_NXentry_oned_profileID_surface(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("surface", NXnote.class, group))) return;

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
	}

	/**
	 * Validate optional group 'surface_distance' of type NXnote.
	 */
	private void validateGroup_NXentry_oned_profileID_surface_distance(final NXnote group) {
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

	/**
	 * Validate optional group 'feature' of type NXnote.
	 */
	private void validateGroup_NXentry_oned_profileID_feature(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("feature", NXnote.class, group))) return;

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

		// validate field 'facet_normals' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset facet_normals = group.getLazyDataset("facet_normals");
		validateFieldNotNull("facet_normals", facet_normals);
		if (facet_normals != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("facet_normals", facet_normals, NX_CHAR);
		}

		// validate field 'vertex_normals' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset vertex_normals = group.getLazyDataset("vertex_normals");
		validateFieldNotNull("vertex_normals", vertex_normals);
		if (vertex_normals != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertex_normals", vertex_normals, NX_CHAR);
		}

		// validate optional child group 'patch_filter' of type NXmatch_filter
		if (group.getChild("patch_filter", NXmatch_filter.class) != null) {
			validateGroup_NXentry_oned_profileID_feature_patch_filter(group.getChild("patch_filter", NXmatch_filter.class));
		}
	}

	/**
	 * Validate optional group 'patch_filter' of type NXmatch_filter.
	 */
	private void validateGroup_NXentry_oned_profileID_feature_patch_filter(final NXmatch_filter group) {
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

	/**
	 * Validate optional group 'feature_distance' of type NXnote.
	 */
	private void validateGroup_NXentry_oned_profileID_feature_distance(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("feature_distance", NXnote.class, group))) return;

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

	/**
	 * Validate optional group 'user_defined_roi' of type NXroi_process.
	 */
	private void validateGroup_NXentry_oned_profileID_user_defined_roi(final NXroi_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("user_defined_roi", NXroi_process.class, group))) return;

		// validate child group 'cylinder_set' of type NXcg_cylinder
		validateGroup_NXentry_oned_profileID_user_defined_roi_cylinder_set(group.getChild("cylinder_set", NXcg_cylinder.class));
	}

	/**
	 * Validate group 'cylinder_set' of type NXcg_cylinder.
	 */
	private void validateGroup_NXentry_oned_profileID_user_defined_roi_cylinder_set(final NXcg_cylinder group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cylinder_set", NXcg_cylinder.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset = group.getLazyDataset("index_offset");
		validateFieldNotNull("index_offset", index_offset);
		if (index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset", index_offset, NX_INT);
		}

		// validate field 'center' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset center = group.getLazyDataset("center");
		validateFieldNotNull("center", center);
		if (center != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center", center, NX_NUMBER);
			validateFieldDimensions("center", center, null, "n_rois", 3);
		}

		// validate field 'height' of type NX_NUMBER.
		final ILazyDataset height = group.getLazyDataset("height");
		validateFieldNotNull("height", height);
		if (height != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("height", height, NX_NUMBER);
			validateFieldUnits("height", group.getDataNode("height"), NX_LENGTH);
			validateFieldRank("height", height, 2);
			validateFieldDimensions("height", height, null, "n_rois", 3);
		}

		// validate field 'radii' of type NX_NUMBER.
		final ILazyDataset radii = group.getLazyDataset("radii");
		validateFieldNotNull("radii", radii);
		if (radii != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("radii", radii, NX_NUMBER);
			validateFieldUnits("radii", group.getDataNode("radii"), NX_LENGTH);
			validateFieldRank("radii", radii, 1);
			validateFieldDimensions("radii", radii, null, "n_rois");
		}
	}
}
