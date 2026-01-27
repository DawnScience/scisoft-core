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
import org.eclipse.dawnsci.nexus.NXapm_paraprobe_tool_parameters;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXspatial_filter;
import org.eclipse.dawnsci.nexus.NXcg_hexahedron;
import org.eclipse.dawnsci.nexus.NXcg_face_list_data_structure;
import org.eclipse.dawnsci.nexus.NXcg_cylinder;
import org.eclipse.dawnsci.nexus.NXcg_ellipsoid;
import org.eclipse.dawnsci.nexus.NXcg_polyhedron;
import org.eclipse.dawnsci.nexus.NXcs_filter_boolean_mask;
import org.eclipse.dawnsci.nexus.NXsubsampling_filter;
import org.eclipse.dawnsci.nexus.NXmatch_filter;
import org.eclipse.dawnsci.nexus.NXapm_paraprobe_tool_common;
import org.eclipse.dawnsci.nexus.NXprogram;
import org.eclipse.dawnsci.nexus.NXcs_profiling;

/**
 * Validator for the application definition 'NXapm_paraprobe_tool_config'.
 */
public class NXapm_paraprobe_tool_configValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_tool_configValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_TOOL_CONFIG);
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

		// validate child group 'TASKCONFIG' of type NXapm_paraprobe_tool_parameters
		validateGroup_NXentry_TASKCONFIG(group.getChild("TASKCONFIG", NXapm_paraprobe_tool_parameters.class));

		// validate child group 'common' of type NXapm_paraprobe_tool_common
		validateGroup_NXentry_common(group.getChild("common", NXapm_paraprobe_tool_common.class));
	}

	/**
	 * Validate group 'TASKCONFIG' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_TASKCONFIG(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("TASKCONFIG", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate optional field 'identifier_analysis' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset identifier_analysis = group.getLazyDataset("identifier_analysis");
				if (identifier_analysis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_analysis", identifier_analysis, NX_UINT);
		}

		// validate child group 'reconstruction' of type NXnote
		validateGroup_NXentry_TASKCONFIG_reconstruction(group.getChild("reconstruction", NXnote.class));

		// validate child group 'ranging' of type NXnote
		validateGroup_NXentry_TASKCONFIG_ranging(group.getChild("ranging", NXnote.class));

		// validate child group 'spatial_filter' of type NXspatial_filter
		validateGroup_NXentry_TASKCONFIG_spatial_filter(group.getChild("spatial_filter", NXspatial_filter.class));

		// validate optional child group 'evaporation_id_filter' of type NXsubsampling_filter
		if (group.getChild("evaporation_id_filter", NXsubsampling_filter.class) != null) {
			validateGroup_NXentry_TASKCONFIG_evaporation_id_filter(group.getChild("evaporation_id_filter", NXsubsampling_filter.class));
		}

		// validate optional child group 'iontype_filter' of type NXmatch_filter
		if (group.getChild("iontype_filter", NXmatch_filter.class) != null) {
			validateGroup_NXentry_TASKCONFIG_iontype_filter(group.getChild("iontype_filter", NXmatch_filter.class));
		}

		// validate optional child group 'hit_multiplicity_filter' of type NXmatch_filter
		if (group.getChild("hit_multiplicity_filter", NXmatch_filter.class) != null) {
			validateGroup_NXentry_TASKCONFIG_hit_multiplicity_filter(group.getChild("hit_multiplicity_filter", NXmatch_filter.class));
		}
	}

	/**
	 * Validate group 'reconstruction' of type NXnote.
	 */
	private void validateGroup_NXentry_TASKCONFIG_reconstruction(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("reconstruction", NXnote.class, group))) return;

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

		// validate field 'position' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset position = group.getLazyDataset("position");
		validateFieldNotNull("position", position);
		if (position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("position", position, NX_CHAR);
		}

		// validate field 'mass_to_charge' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset mass_to_charge = group.getLazyDataset("mass_to_charge");
		validateFieldNotNull("mass_to_charge", mass_to_charge);
		if (mass_to_charge != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mass_to_charge", mass_to_charge, NX_CHAR);
		}
	}

	/**
	 * Validate group 'ranging' of type NXnote.
	 */
	private void validateGroup_NXentry_TASKCONFIG_ranging(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ranging", NXnote.class, group))) return;

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

		// validate field 'ranging_definitions' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset ranging_definitions = group.getLazyDataset("ranging_definitions");
		validateFieldNotNull("ranging_definitions", ranging_definitions);
		if (ranging_definitions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ranging_definitions", ranging_definitions, NX_CHAR);
		}
	}

	/**
	 * Validate group 'spatial_filter' of type NXspatial_filter.
	 */
	private void validateGroup_NXentry_TASKCONFIG_spatial_filter(final NXspatial_filter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spatial_filter", NXspatial_filter.class, group))) return;

		// validate field 'windowing_method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset windowing_method = group.getLazyDataset("windowing_method");
		validateFieldNotNull("windowing_method", windowing_method);
		if (windowing_method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("windowing_method", windowing_method, NX_CHAR);
		}

		// validate optional child group 'hexahedron_set' of type NXcg_hexahedron
		if (group.getChild("hexahedron_set", NXcg_hexahedron.class) != null) {
			validateGroup_NXentry_TASKCONFIG_spatial_filter_hexahedron_set(group.getChild("hexahedron_set", NXcg_hexahedron.class));
		}

		// validate optional child group 'cylinder_set' of type NXcg_cylinder
		if (group.getChild("cylinder_set", NXcg_cylinder.class) != null) {
			validateGroup_NXentry_TASKCONFIG_spatial_filter_cylinder_set(group.getChild("cylinder_set", NXcg_cylinder.class));
		}

		// validate optional child group 'ellipsoid_set' of type NXcg_ellipsoid
		if (group.getChild("ellipsoid_set", NXcg_ellipsoid.class) != null) {
			validateGroup_NXentry_TASKCONFIG_spatial_filter_ellipsoid_set(group.getChild("ellipsoid_set", NXcg_ellipsoid.class));
		}

		// validate optional child group 'polyhedron_set' of type NXcg_polyhedron
		if (group.getChild("polyhedron_set", NXcg_polyhedron.class) != null) {
			validateGroup_NXentry_TASKCONFIG_spatial_filter_polyhedron_set(group.getChild("polyhedron_set", NXcg_polyhedron.class));
		}

		// validate optional child group 'bitmask' of type NXcs_filter_boolean_mask
		if (group.getChild("bitmask", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_TASKCONFIG_spatial_filter_bitmask(group.getChild("bitmask", NXcs_filter_boolean_mask.class));
		}
	}

	/**
	 * Validate optional group 'hexahedron_set' of type NXcg_hexahedron.
	 */
	private void validateGroup_NXentry_TASKCONFIG_spatial_filter_hexahedron_set(final NXcg_hexahedron group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("hexahedron_set", NXcg_hexahedron.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'dimensionality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset dimensionality = group.getLazyDataset("dimensionality");
		validateFieldNotNull("dimensionality", dimensionality);
		if (dimensionality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dimensionality", dimensionality, NX_POSINT);
		}

		// validate field 'cardinality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset cardinality = group.getLazyDataset("cardinality");
		validateFieldNotNull("cardinality", cardinality);
		if (cardinality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cardinality", cardinality, NX_POSINT);
		}

		// validate field 'index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset = group.getLazyDataset("index_offset");
		validateFieldNotNull("index_offset", index_offset);
		if (index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset", index_offset, NX_INT);
		}

		// validate child group 'hexahedra' of type NXcg_face_list_data_structure
		validateGroup_NXentry_TASKCONFIG_spatial_filter_hexahedron_set_hexahedra(group.getHexahedra());
	}

	/**
	 * Validate group 'hexahedra' of type NXcg_face_list_data_structure.
	 */
	private void validateGroup_NXentry_TASKCONFIG_spatial_filter_hexahedron_set_hexahedra(final NXcg_face_list_data_structure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("hexahedra", NXcg_face_list_data_structure.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'vertices' of type NX_UINT.
		final ILazyDataset vertices = group.getLazyDataset("vertices");
		validateFieldNotNull("vertices", vertices);
		if (vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertices", vertices, NX_UINT);
			validateFieldUnits("vertices", group.getDataNode("vertices"), NX_ANY);
			validateFieldRank("vertices", vertices, 2);
			validateFieldDimensions("vertices", vertices, "NXcg_face_list_data_structure", "n_v", "d");
		}
	}

	/**
	 * Validate optional group 'cylinder_set' of type NXcg_cylinder.
	 */
	private void validateGroup_NXentry_TASKCONFIG_spatial_filter_cylinder_set(final NXcg_cylinder group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cylinder_set", NXcg_cylinder.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'dimensionality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset dimensionality = group.getLazyDataset("dimensionality");
		validateFieldNotNull("dimensionality", dimensionality);
		if (dimensionality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dimensionality", dimensionality, NX_POSINT);
		}

		// validate field 'cardinality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset cardinality = group.getLazyDataset("cardinality");
		validateFieldNotNull("cardinality", cardinality);
		if (cardinality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cardinality", cardinality, NX_POSINT);
		}

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
		}

		// validate field 'height' of type NX_NUMBER.
		final ILazyDataset height = group.getLazyDataset("height");
		validateFieldNotNull("height", height);
		if (height != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("height", height, NX_NUMBER);
			validateFieldUnits("height", group.getDataNode("height"), NX_LENGTH);
			validateFieldRank("height", height, 2);
			validateFieldDimensions("height", height, "NXcg_cylinder", "c", "d");
		}

		// validate field 'radii' of type NX_NUMBER.
		final ILazyDataset radii = group.getLazyDataset("radii");
		validateFieldNotNull("radii", radii);
		if (radii != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("radii", radii, NX_NUMBER);
			validateFieldUnits("radii", group.getDataNode("radii"), NX_LENGTH);
			validateFieldRank("radii", radii, 1);
			validateFieldDimensions("radii", radii, "NXcg_cylinder", "c");
		}
	}

	/**
	 * Validate optional group 'ellipsoid_set' of type NXcg_ellipsoid.
	 */
	private void validateGroup_NXentry_TASKCONFIG_spatial_filter_ellipsoid_set(final NXcg_ellipsoid group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ellipsoid_set", NXcg_ellipsoid.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'dimensionality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset dimensionality = group.getLazyDataset("dimensionality");
		validateFieldNotNull("dimensionality", dimensionality);
		if (dimensionality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dimensionality", dimensionality, NX_POSINT);
		}

		// validate field 'cardinality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset cardinality = group.getLazyDataset("cardinality");
		validateFieldNotNull("cardinality", cardinality);
		if (cardinality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cardinality", cardinality, NX_POSINT);
		}

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
		}

		// validate field 'half_axes_radii' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset half_axes_radii = group.getLazyDataset("half_axes_radii");
		validateFieldNotNull("half_axes_radii", half_axes_radii);
		if (half_axes_radii != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("half_axes_radii", half_axes_radii, NX_NUMBER);
		}

		// validate field 'orientation' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset orientation = group.getLazyDataset("orientation");
		validateFieldNotNull("orientation", orientation);
		if (orientation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("orientation", orientation, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'polyhedron_set' of type NXcg_polyhedron.
	 */
	private void validateGroup_NXentry_TASKCONFIG_spatial_filter_polyhedron_set(final NXcg_polyhedron group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("polyhedron_set", NXcg_polyhedron.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'bitmask' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_TASKCONFIG_spatial_filter_bitmask(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("bitmask", NXcs_filter_boolean_mask.class, group))) return;

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
	 * Validate optional group 'evaporation_id_filter' of type NXsubsampling_filter.
	 */
	private void validateGroup_NXentry_TASKCONFIG_evaporation_id_filter(final NXsubsampling_filter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("evaporation_id_filter", NXsubsampling_filter.class, group))) return;

		// validate field 'min' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset min = group.getLazyDataset("min");
		validateFieldNotNull("min", min);
		if (min != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min", min, NX_INT);
		}

		// validate field 'increment' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset increment = group.getLazyDataset("increment");
		validateFieldNotNull("increment", increment);
		if (increment != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("increment", increment, NX_INT);
		}

		// validate field 'max' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset max = group.getLazyDataset("max");
		validateFieldNotNull("max", max);
		if (max != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max", max, NX_INT);
		}
	}

	/**
	 * Validate optional group 'iontype_filter' of type NXmatch_filter.
	 */
	private void validateGroup_NXentry_TASKCONFIG_iontype_filter(final NXmatch_filter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("iontype_filter", NXmatch_filter.class, group))) return;

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
	 * Validate optional group 'hit_multiplicity_filter' of type NXmatch_filter.
	 */
	private void validateGroup_NXentry_TASKCONFIG_hit_multiplicity_filter(final NXmatch_filter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("hit_multiplicity_filter", NXmatch_filter.class, group))) return;

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

		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_common_programID(group.getChild("programID", NXprogram.class));

		// validate optional child group 'profiling' of type NXcs_profiling
		if (group.getChild("profiling", NXcs_profiling.class) != null) {
			validateGroup_NXentry_common_profiling(group.getChild("profiling", NXcs_profiling.class));
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
	}
}
