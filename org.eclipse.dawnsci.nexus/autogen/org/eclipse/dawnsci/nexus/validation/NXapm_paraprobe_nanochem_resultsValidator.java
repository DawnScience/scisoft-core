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

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXdelocalization;
import org.eclipse.dawnsci.nexus.NXcs_filter_boolean_mask;
import org.eclipse.dawnsci.nexus.NXcg_grid;
import org.eclipse.dawnsci.nexus.NXcg_hexahedron;
import org.eclipse.dawnsci.nexus.NXcg_face_list_data_structure;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXisocontour;
import org.eclipse.dawnsci.nexus.NXcg_triangle;
import org.eclipse.dawnsci.nexus.NXcg_unit_normal;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXcg_polyhedron;
import org.eclipse.dawnsci.nexus.NXchemical_composition;
import org.eclipse.dawnsci.nexus.NXatom;
import org.eclipse.dawnsci.nexus.NXapm_paraprobe_tool_process;
import org.eclipse.dawnsci.nexus.NXcg_roi;

/**
 * Validator for the application definition 'NXapm_paraprobe_nanochem_results'.
 */
public class NXapm_paraprobe_nanochem_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_nanochem_resultsValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_NANOCHEM_RESULTS);
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
					"NXapm_paraprobe_nanochem_results");
		}

		// validate optional child group 'delocalizationID' of type NXdelocalization
		if (group.getChild("delocalizationID", NXdelocalization.class) != null) {
			validateGroup_NXentry_delocalizationID(group.getChild("delocalizationID", NXdelocalization.class));
		}

		// validate optional child group 'interface_meshingID' of type NXapm_paraprobe_tool_process
		if (group.getChild("interface_meshingID", NXapm_paraprobe_tool_process.class) != null) {
			validateGroup_NXentry_interface_meshingID(group.getChild("interface_meshingID", NXapm_paraprobe_tool_process.class));
		}

		// validate optional child group 'oned_profileID' of type NXapm_paraprobe_tool_process
		if (group.getChild("oned_profileID", NXapm_paraprobe_tool_process.class) != null) {
			validateGroup_NXentry_oned_profileID(group.getChild("oned_profileID", NXapm_paraprobe_tool_process.class));
		}
	}

	/**
	 * Validate optional group 'delocalizationID' of type NXdelocalization.
	 */
	private void validateGroup_NXentry_delocalizationID(final NXdelocalization group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("delocalizationID", NXdelocalization.class, group))) return;

		// validate child group 'window' of type NXcs_filter_boolean_mask
		validateGroup_NXentry_delocalizationID_window(group.getChild("window", NXcs_filter_boolean_mask.class));

		// validate child group 'grid' of type NXcg_grid
		validateGroup_NXentry_delocalizationID_grid(group.getChild("grid", NXcg_grid.class));
	}

	/**
	 * Validate group 'window' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_delocalizationID_window(final NXcs_filter_boolean_mask group) {
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
	 * Validate group 'grid' of type NXcg_grid.
	 */
	private void validateGroup_NXentry_delocalizationID_grid(final NXcg_grid group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("grid", NXcg_grid.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'dimensionality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset dimensionality = group.getLazyDataset("dimensionality");
		validateFieldNotNull("dimensionality", dimensionality);
		if (dimensionality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dimensionality", dimensionality, NX_POSINT);
			validateFieldEnumeration("dimensionality", dimensionality,
					"1",
					"2",
					"3");
		}

		// validate field 'cardinality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset cardinality = group.getLazyDataset("cardinality");
		validateFieldNotNull("cardinality", cardinality);
		if (cardinality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cardinality", cardinality, NX_POSINT);
		}

		// validate field 'origin' of type NX_NUMBER.
		final ILazyDataset origin = group.getLazyDataset("origin");
		validateFieldNotNull("origin", origin);
		if (origin != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("origin", origin, NX_NUMBER);
			validateFieldUnits("origin", group.getDataNode("origin"), NX_ANY);
			validateFieldRank("origin", origin, 1);
			validateFieldDimensions("origin", origin, null, "d");
		}

		// validate field 'symmetry' of type NX_CHAR.
		final ILazyDataset symmetry = group.getLazyDataset("symmetry");
		validateFieldNotNull("symmetry", symmetry);
		if (symmetry != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("symmetry", symmetry, NX_CHAR);
			validateFieldEnumeration("symmetry", symmetry,
					"cubic");
		}

		// validate field 'cell_dimensions' of type NX_NUMBER.
		final ILazyDataset cell_dimensions = group.getLazyDataset("cell_dimensions");
		validateFieldNotNull("cell_dimensions", cell_dimensions);
		if (cell_dimensions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cell_dimensions", cell_dimensions, NX_NUMBER);
			validateFieldUnits("cell_dimensions", group.getDataNode("cell_dimensions"), NX_LENGTH);
			validateFieldRank("cell_dimensions", cell_dimensions, 1);
			validateFieldDimensions("cell_dimensions", cell_dimensions, null, "d");
		}

		// validate field 'extent' of type NX_POSINT.
		final ILazyDataset extent = group.getLazyDataset("extent");
		validateFieldNotNull("extent", extent);
		if (extent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extent", extent, NX_POSINT);
			validateFieldUnits("extent", group.getDataNode("extent"), NX_UNITLESS);
			validateFieldRank("extent", extent, 1);
			validateFieldDimensions("extent", extent, null, "d");
		}

		// validate field 'index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset = group.getLazyDataset("index_offset");
		validateFieldNotNull("index_offset", index_offset);
		if (index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset", index_offset, NX_INT);
			validateFieldUnits("index_offset", group.getDataNode("index_offset"), NX_UNITLESS);
		}

		// validate field 'kernel_size' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset kernel_size = group.getLazyDataset("kernel_size");
		validateFieldNotNull("kernel_size", kernel_size);
		if (kernel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kernel_size", kernel_size, NX_POSINT);
			validateFieldUnits("kernel_size", group.getDataNode("kernel_size"), NX_DIMENSIONLESS);
			validateFieldRank("kernel_size", kernel_size, 1);
			validateFieldDimensions("kernel_size", kernel_size, null, 3);
		}

		// validate field 'kernel_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset kernel_type = group.getLazyDataset("kernel_type");
		validateFieldNotNull("kernel_type", kernel_type);
		if (kernel_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kernel_type", kernel_type, NX_CHAR);
			validateFieldEnumeration("kernel_type", kernel_type,
					"gaussian");
		}

		// validate field 'kernel_sigma' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset kernel_sigma = group.getLazyDataset("kernel_sigma");
		validateFieldNotNull("kernel_sigma", kernel_sigma);
		if (kernel_sigma != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kernel_sigma", kernel_sigma, NX_FLOAT);
			validateFieldUnits("kernel_sigma", group.getDataNode("kernel_sigma"), NX_LENGTH);
			validateFieldRank("kernel_sigma", kernel_sigma, 1);
			validateFieldDimensions("kernel_sigma", kernel_sigma, null, 3);
		}

		// validate field 'kernel_mu' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset kernel_mu = group.getLazyDataset("kernel_mu");
		validateFieldNotNull("kernel_mu", kernel_mu);
		if (kernel_mu != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kernel_mu", kernel_mu, NX_FLOAT);
			validateFieldUnits("kernel_mu", group.getDataNode("kernel_mu"), NX_LENGTH);
			validateFieldRank("kernel_mu", kernel_mu, 1);
			validateFieldDimensions("kernel_mu", kernel_mu, null, 3);
		}

		// validate field 'normalization' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset normalization = group.getLazyDataset("normalization");
		validateFieldNotNull("normalization", normalization);
		if (normalization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("normalization", normalization, NX_CHAR);
			validateFieldEnumeration("normalization", normalization,
					"total",
					"candidates",
					"composition",
					"concentration");
		}

		// validate child group 'bounding_box' of type NXcg_hexahedron
		validateGroup_NXentry_delocalizationID_grid_bounding_box(group.getChild("bounding_box", NXcg_hexahedron.class));

		// validate optional child group 'scalar_field_magn_SUFFIX' of type NXdata
		if (group.getChild("scalar_field_magn_SUFFIX", NXdata.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_scalar_field_magn_SUFFIX(group.getChild("scalar_field_magn_SUFFIX", NXdata.class));
		}

		// validate optional child group 'scalar_field_grad_SUFFIX' of type NXdata
		if (group.getChild("scalar_field_grad_SUFFIX", NXdata.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_scalar_field_grad_SUFFIX(group.getChild("scalar_field_grad_SUFFIX", NXdata.class));
		}

		// validate optional child group 'iso_surfaceID' of type NXisocontour
		if (group.getChild("iso_surfaceID", NXisocontour.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID(group.getChild("iso_surfaceID", NXisocontour.class));
		}
	}

	/**
	 * Validate group 'bounding_box' of type NXcg_hexahedron.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_bounding_box(final NXcg_hexahedron group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("bounding_box", NXcg_hexahedron.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'is_axis_aligned' of type NX_BOOLEAN.
		final ILazyDataset is_axis_aligned = group.getLazyDataset("is_axis_aligned");
		validateFieldNotNull("is_axis_aligned", is_axis_aligned);
		if (is_axis_aligned != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_axis_aligned", is_axis_aligned, NX_BOOLEAN);
			validateFieldRank("is_axis_aligned", is_axis_aligned, 1);
			validateFieldDimensions("is_axis_aligned", is_axis_aligned, "NXcg_hexahedron", "c");
		}

		// validate field 'index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset = group.getLazyDataset("index_offset");
		validateFieldNotNull("index_offset", index_offset);
		if (index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset", index_offset, NX_INT);
			validateFieldUnits("index_offset", group.getDataNode("index_offset"), NX_UNITLESS);
		}

		// validate child group 'hexahedron' of type NXcg_face_list_data_structure
		validateGroup_NXentry_delocalizationID_grid_bounding_box_hexahedron(group.getChild("hexahedron", NXcg_face_list_data_structure.class));
	}

	/**
	 * Validate group 'hexahedron' of type NXcg_face_list_data_structure.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_bounding_box_hexahedron(final NXcg_face_list_data_structure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("hexahedron", NXcg_face_list_data_structure.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'vertex_index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset vertex_index_offset = group.getLazyDataset("vertex_index_offset");
		validateFieldNotNull("vertex_index_offset", vertex_index_offset);
		if (vertex_index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertex_index_offset", vertex_index_offset, NX_INT);
			validateFieldUnits("vertex_index_offset", group.getDataNode("vertex_index_offset"), NX_UNITLESS);
		}

		// validate field 'face_index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset face_index_offset = group.getLazyDataset("face_index_offset");
		validateFieldNotNull("face_index_offset", face_index_offset);
		if (face_index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("face_index_offset", face_index_offset, NX_INT);
			validateFieldUnits("face_index_offset", group.getDataNode("face_index_offset"), NX_UNITLESS);
		}

		// validate field 'vertices' of type NX_NUMBER.
		final ILazyDataset vertices = group.getLazyDataset("vertices");
		validateFieldNotNull("vertices", vertices);
		if (vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertices", vertices, NX_NUMBER);
			validateFieldUnits("vertices", group.getDataNode("vertices"), NX_LENGTH);
			validateFieldRank("vertices", vertices, 2);
			validateFieldDimensions("vertices", vertices, null, 8, 3);
		}

		// validate field 'faces' of type NX_NUMBER.
		final ILazyDataset faces = group.getLazyDataset("faces");
		validateFieldNotNull("faces", faces);
		if (faces != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("faces", faces, NX_NUMBER);
			validateFieldUnits("faces", group.getDataNode("faces"), NX_UNITLESS);
			validateFieldRank("faces", faces, 2);
			validateFieldDimensions("faces", faces, null, 6, 4);
		}

		// validate field 'xdmf_topology' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset xdmf_topology = group.getLazyDataset("xdmf_topology");
		validateFieldNotNull("xdmf_topology", xdmf_topology);
		if (xdmf_topology != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_topology", xdmf_topology, NX_UINT);
			validateFieldUnits("xdmf_topology", group.getDataNode("xdmf_topology"), NX_UNITLESS);
			validateFieldRank("xdmf_topology", xdmf_topology, 1);
			validateFieldDimensions("xdmf_topology", xdmf_topology, null, 36);
		}

		// validate optional field 'number_of_boundaries' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset number_of_boundaries = group.getLazyDataset("number_of_boundaries");
				if (number_of_boundaries != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_boundaries", number_of_boundaries, NX_POSINT);
			validateFieldUnits("number_of_boundaries", group.getDataNode("number_of_boundaries"), NX_UNITLESS);
		}

		// validate optional field 'boundaries' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset boundaries = group.getLazyDataset("boundaries");
				if (boundaries != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("boundaries", boundaries, NX_CHAR);
			validateFieldRank("boundaries", boundaries, 1);
			validateFieldDimensions("boundaries", boundaries, null, 6);
		}

		// validate optional field 'boundary_conditions' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset boundary_conditions = group.getLazyDataset("boundary_conditions");
				if (boundary_conditions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("boundary_conditions", boundary_conditions, NX_INT);
			validateFieldUnits("boundary_conditions", group.getDataNode("boundary_conditions"), NX_UNITLESS);
			validateFieldRank("boundary_conditions", boundary_conditions, 1);
			validateFieldDimensions("boundary_conditions", boundary_conditions, null, 6);
		}
	}

	/**
	 * Validate optional group 'scalar_field_magn_SUFFIX' of type NXdata.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_scalar_field_magn_SUFFIX(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("scalar_field_magn_SUFFIX", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'xdmf_intensity' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset xdmf_intensity = group.getLazyDataset("xdmf_intensity");
				if (xdmf_intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_intensity", xdmf_intensity, NX_FLOAT);
			validateFieldUnits("xdmf_intensity", group.getDataNode("xdmf_intensity"), NX_ANY);
			validateFieldRank("xdmf_intensity", xdmf_intensity, 1);
			validateFieldDimensions("xdmf_intensity", xdmf_intensity, null, "n_xyz");
		}

		// validate optional field 'xdmf_xyz' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset xdmf_xyz = group.getLazyDataset("xdmf_xyz");
				if (xdmf_xyz != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_xyz", xdmf_xyz, NX_FLOAT);
			validateFieldUnits("xdmf_xyz", group.getDataNode("xdmf_xyz"), NX_LENGTH);
			validateFieldRank("xdmf_xyz", xdmf_xyz, 2);
			validateFieldDimensions("xdmf_xyz", xdmf_xyz, null, "n_xyz", 3);
		}

		// validate optional field 'xdmf_topology' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset xdmf_topology = group.getLazyDataset("xdmf_topology");
				if (xdmf_topology != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_topology", xdmf_topology, NX_NUMBER);
			validateFieldUnits("xdmf_topology", group.getDataNode("xdmf_topology"), NX_UNITLESS);
			validateFieldRank("xdmf_topology", xdmf_topology, 1);
			validateFieldDimensions("xdmf_topology", xdmf_topology, null, "i");
		}
	}

	/**
	 * Validate optional group 'scalar_field_grad_SUFFIX' of type NXdata.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_scalar_field_grad_SUFFIX(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("scalar_field_grad_SUFFIX", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'xdmf_gradient' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset xdmf_gradient = group.getLazyDataset("xdmf_gradient");
				if (xdmf_gradient != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_gradient", xdmf_gradient, NX_FLOAT);
			validateFieldUnits("xdmf_gradient", group.getDataNode("xdmf_gradient"), NX_ANY);
			validateFieldRank("xdmf_gradient", xdmf_gradient, 2);
			validateFieldDimensions("xdmf_gradient", xdmf_gradient, null, "n_xyz", 3);
		}

		// validate optional field 'xdmf_xyz' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset xdmf_xyz = group.getLazyDataset("xdmf_xyz");
				if (xdmf_xyz != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_xyz", xdmf_xyz, NX_FLOAT);
			validateFieldUnits("xdmf_xyz", group.getDataNode("xdmf_xyz"), NX_LENGTH);
			validateFieldRank("xdmf_xyz", xdmf_xyz, 2);
			validateFieldDimensions("xdmf_xyz", xdmf_xyz, null, "n_xyz", 3);
		}

		// validate optional field 'xdmf_topology' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset xdmf_topology = group.getLazyDataset("xdmf_topology");
				if (xdmf_topology != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_topology", xdmf_topology, NX_NUMBER);
			validateFieldUnits("xdmf_topology", group.getDataNode("xdmf_topology"), NX_UNITLESS);
			validateFieldRank("xdmf_topology", xdmf_topology, 1);
			validateFieldDimensions("xdmf_topology", xdmf_topology, null, "i");
		}
	}

	/**
	 * Validate optional group 'iso_surfaceID' of type NXisocontour.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID(final NXisocontour group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("iso_surfaceID", NXisocontour.class, group))) return;

		// validate field 'dimensionality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset dimensionality = group.getLazyDataset("dimensionality");
		validateFieldNotNull("dimensionality", dimensionality);
		if (dimensionality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dimensionality", dimensionality, NX_POSINT);
		}

		// validate field 'isovalue' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset isovalue = group.getLazyDataset("isovalue");
		validateFieldNotNull("isovalue", isovalue);
		if (isovalue != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("isovalue", isovalue, NX_NUMBER);
			validateFieldUnits("isovalue", group.getDataNode("isovalue"), NX_ANY);
		}

		// validate optional field 'marching_cubes' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset marching_cubes = group.getLazyDataset("marching_cubes");
				if (marching_cubes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("marching_cubes", marching_cubes, NX_CHAR);
		}

		// validate optional child group 'triangle_soup' of type NXcg_triangle
		if (group.getChild("triangle_soup", NXcg_triangle.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup(group.getChild("triangle_soup", NXcg_triangle.class));
		}
	}

	/**
	 * Validate optional group 'triangle_soup' of type NXcg_triangle.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup(final NXcg_triangle group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("triangle_soup", NXcg_triangle.class, group))) return;
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

		// validate child group 'triangles' of type NXcg_face_list_data_structure
		validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles(group.getTriangles());
	}

	/**
	 * Validate group 'triangles' of type NXcg_face_list_data_structure.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles(final NXcg_face_list_data_structure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("triangles", NXcg_face_list_data_structure.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'number_of_vertices' of type NX_POSINT.
		final ILazyDataset number_of_vertices = group.getLazyDataset("number_of_vertices");
		validateFieldNotNull("number_of_vertices", number_of_vertices);
		if (number_of_vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_vertices", number_of_vertices, NX_POSINT);
			validateFieldUnits("number_of_vertices", group.getDataNode("number_of_vertices"), NX_UNITLESS);
			validateFieldRank("number_of_vertices", number_of_vertices, 1);
			validateFieldDimensions("number_of_vertices", number_of_vertices, "NXcg_face_list_data_structure", "n_f");
		}

		// validate field 'number_of_faces' of type NX_POSINT.
		final ILazyDataset number_of_faces = group.getLazyDataset("number_of_faces");
		validateFieldNotNull("number_of_faces", number_of_faces);
		if (number_of_faces != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_faces", number_of_faces, NX_POSINT);
			validateFieldUnits("number_of_faces", group.getDataNode("number_of_faces"), NX_UNITLESS);
		}

		// validate field 'vertex_index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset vertex_index_offset = group.getLazyDataset("vertex_index_offset");
		validateFieldNotNull("vertex_index_offset", vertex_index_offset);
		if (vertex_index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertex_index_offset", vertex_index_offset, NX_INT);
		}

		// validate field 'face_index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset face_index_offset = group.getLazyDataset("face_index_offset");
		validateFieldNotNull("face_index_offset", face_index_offset);
		if (face_index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("face_index_offset", face_index_offset, NX_INT);
		}

		// validate field 'vertices' of type NX_NUMBER.
		final ILazyDataset vertices = group.getLazyDataset("vertices");
		validateFieldNotNull("vertices", vertices);
		if (vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertices", vertices, NX_NUMBER);
			validateFieldUnits("vertices", group.getDataNode("vertices"), NX_LENGTH);
			validateFieldRank("vertices", vertices, 2);
			validateFieldDimensions("vertices", vertices, null, "i", 3);
		}

		// validate field 'faces' of type NX_INT.
		final ILazyDataset faces = group.getLazyDataset("faces");
		validateFieldNotNull("faces", faces);
		if (faces != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("faces", faces, NX_INT);
			validateFieldUnits("faces", group.getDataNode("faces"), NX_UNITLESS);
			validateFieldRank("faces", faces, 1);
			validateFieldDimensions("faces", faces, null, "j");
		}

		// validate field 'xdmf_topology' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset xdmf_topology = group.getLazyDataset("xdmf_topology");
		validateFieldNotNull("xdmf_topology", xdmf_topology);
		if (xdmf_topology != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_topology", xdmf_topology, NX_UINT);
			validateFieldUnits("xdmf_topology", group.getDataNode("xdmf_topology"), NX_UNITLESS);
			validateFieldRank("xdmf_topology", xdmf_topology, 1);
			validateFieldDimensions("xdmf_topology", xdmf_topology, null, "n_f_tri_xdmf");
		}

		// validate optional field 'area' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset area = group.getLazyDataset("area");
				if (area != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("area", area, NX_NUMBER);
			validateFieldUnits("area", group.getDataNode("area"), NX_AREA);
			validateFieldRank("area", area, 1);
			validateFieldDimensions("area", area, null, "j");
		}

		// validate optional field 'edge_length' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset edge_length = group.getLazyDataset("edge_length");
				if (edge_length != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("edge_length", edge_length, NX_NUMBER);
			validateFieldUnits("edge_length", group.getDataNode("edge_length"), NX_LENGTH);
			validateFieldRank("edge_length", edge_length, 2);
			validateFieldDimensions("edge_length", edge_length, null, "k", 3);
		}

		// validate optional field 'interior_angle' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset interior_angle = group.getLazyDataset("interior_angle");
				if (interior_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("interior_angle", interior_angle, NX_NUMBER);
			validateFieldUnits("interior_angle", group.getDataNode("interior_angle"), NX_ANGLE);
			validateFieldRank("interior_angle", interior_angle, 2);
			validateFieldDimensions("interior_angle", interior_angle, null, "j", 4);
		}

		// validate optional field 'center' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset center = group.getLazyDataset("center");
				if (center != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center", center, NX_NUMBER);
			validateFieldUnits("center", group.getDataNode("center"), NX_LENGTH);
			validateFieldRank("center", center, 2);
			validateFieldDimensions("center", center, null, "j", 3);
		}

		// validate optional child group 'vertex_normal' of type NXcg_unit_normal
		if (group.getChild("vertex_normal", NXcg_unit_normal.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_vertex_normal(group.getChild("vertex_normal", NXcg_unit_normal.class));
		}

		// validate optional child group 'face_normal' of type NXcg_unit_normal
		if (group.getChild("face_normal", NXcg_unit_normal.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_face_normal(group.getChild("face_normal", NXcg_unit_normal.class));
		}

		// validate optional child group 'volumetric_features' of type NXprocess
		if (group.getChild("volumetric_features", NXprocess.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features(group.getChild("volumetric_features", NXprocess.class));
		}
	}

	/**
	 * Validate optional group 'vertex_normal' of type NXcg_unit_normal.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_vertex_normal(final NXcg_unit_normal group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("vertex_normal", NXcg_unit_normal.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'normals' of type NX_FLOAT.
		final ILazyDataset normals = group.getLazyDataset("normals");
		validateFieldNotNull("normals", normals);
		if (normals != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("normals", normals, NX_FLOAT);
			validateFieldUnits("normals", group.getDataNode("normals"), NX_DIMENSIONLESS);
			validateFieldRank("normals", normals, 2);
			validateFieldDimensions("normals", normals, null, "j", 3);
		}

		// validate optional field 'orientation' of type NX_UINT.
		final ILazyDataset orientation = group.getLazyDataset("orientation");
				if (orientation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("orientation", orientation, NX_UINT);
			validateFieldUnits("orientation", group.getDataNode("orientation"), NX_UNITLESS);
			validateFieldRank("orientation", orientation, 1);
			validateFieldDimensions("orientation", orientation, null, "j");
		}
	}

	/**
	 * Validate optional group 'face_normal' of type NXcg_unit_normal.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_face_normal(final NXcg_unit_normal group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("face_normal", NXcg_unit_normal.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'normals' of type NX_FLOAT.
		final ILazyDataset normals = group.getLazyDataset("normals");
		validateFieldNotNull("normals", normals);
		if (normals != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("normals", normals, NX_FLOAT);
			validateFieldUnits("normals", group.getDataNode("normals"), NX_DIMENSIONLESS);
			validateFieldRank("normals", normals, 2);
			validateFieldDimensions("normals", normals, null, "k", 3);
		}

		// validate optional field 'orientation' of type NX_UINT.
		final ILazyDataset orientation = group.getLazyDataset("orientation");
				if (orientation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("orientation", orientation, NX_UINT);
			validateFieldUnits("orientation", group.getDataNode("orientation"), NX_UNITLESS);
			validateFieldRank("orientation", orientation, 1);
			validateFieldDimensions("orientation", orientation, null, "k");
		}

		// validate field 'gradient_guide_magnitude' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset gradient_guide_magnitude = group.getLazyDataset("gradient_guide_magnitude");
		validateFieldNotNull("gradient_guide_magnitude", gradient_guide_magnitude);
		if (gradient_guide_magnitude != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("gradient_guide_magnitude", gradient_guide_magnitude, NX_FLOAT);
			validateFieldUnits("gradient_guide_magnitude", group.getDataNode("gradient_guide_magnitude"), NX_ANY);
			validateFieldRank("gradient_guide_magnitude", gradient_guide_magnitude, 1);
			validateFieldDimensions("gradient_guide_magnitude", gradient_guide_magnitude, null, "k");
		}

		// validate field 'gradient_guide_projection' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset gradient_guide_projection = group.getLazyDataset("gradient_guide_projection");
		validateFieldNotNull("gradient_guide_projection", gradient_guide_projection);
		if (gradient_guide_projection != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("gradient_guide_projection", gradient_guide_projection, NX_FLOAT);
			validateFieldUnits("gradient_guide_projection", group.getDataNode("gradient_guide_projection"), NX_ANY);
			validateFieldRank("gradient_guide_projection", gradient_guide_projection, 1);
			validateFieldDimensions("gradient_guide_projection", gradient_guide_projection, null, "k");
		}
	}

	/**
	 * Validate optional group 'volumetric_features' of type NXprocess.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("volumetric_features", NXprocess.class, group))) return;

		// validate field 'indices_triangle_cluster' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset indices_triangle_cluster = group.getLazyDataset("indices_triangle_cluster");
		validateFieldNotNull("indices_triangle_cluster", indices_triangle_cluster);
		if (indices_triangle_cluster != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_triangle_cluster", indices_triangle_cluster, NX_UINT);
			validateFieldUnits("indices_triangle_cluster", group.getDataNode("indices_triangle_cluster"), NX_UNITLESS);
			validateFieldRank("indices_triangle_cluster", indices_triangle_cluster, 1);
			validateFieldDimensions("indices_triangle_cluster", indices_triangle_cluster, null, "n_v_feat");
		}

		// validate optional field 'feature_type_dict_keyword' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset feature_type_dict_keyword = group.getLazyDataset("feature_type_dict_keyword");
				if (feature_type_dict_keyword != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("feature_type_dict_keyword", feature_type_dict_keyword, NX_UINT);
			validateFieldUnits("feature_type_dict_keyword", group.getDataNode("feature_type_dict_keyword"), NX_UNITLESS);
			validateFieldRank("feature_type_dict_keyword", feature_type_dict_keyword, 1);
			validateFieldDimensions("feature_type_dict_keyword", feature_type_dict_keyword, null, "n_feature_dict");
		}

		// validate optional field 'feature_type_dict_value' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset feature_type_dict_value = group.getLazyDataset("feature_type_dict_value");
				if (feature_type_dict_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("feature_type_dict_value", feature_type_dict_value, NX_CHAR);
			validateFieldRank("feature_type_dict_value", feature_type_dict_value, 1);
			validateFieldDimensions("feature_type_dict_value", feature_type_dict_value, null, "n_feature_dict");
		}

		// validate field 'feature_type' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset feature_type = group.getLazyDataset("feature_type");
		validateFieldNotNull("feature_type", feature_type);
		if (feature_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("feature_type", feature_type, NX_UINT);
			validateFieldUnits("feature_type", group.getDataNode("feature_type"), NX_UNITLESS);
			validateFieldRank("feature_type", feature_type, 1);
			validateFieldDimensions("feature_type", feature_type, null, "n_v_feat");
		}

		// validate field 'indices_feature' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_feature = group.getLazyDataset("indices_feature");
		validateFieldNotNull("indices_feature", indices_feature);
		if (indices_feature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_feature", indices_feature, NX_INT);
			validateFieldUnits("indices_feature", group.getDataNode("indices_feature"), NX_UNITLESS);
			validateFieldRank("indices_feature", indices_feature, 1);
			validateFieldDimensions("indices_feature", indices_feature, null, "n_v_feat");
		}

		// validate optional child group 'FEATURE' of type NXprocess
		if (group.getChild("FEATURE", NXprocess.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE(group.getChild("FEATURE", NXprocess.class));
		}
	}

	/**
	 * Validate optional group 'FEATURE' of type NXprocess.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("FEATURE", NXprocess.class, group))) return;

		// validate field 'indices_feature' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_feature = group.getLazyDataset("indices_feature");
		validateFieldNotNull("indices_feature", indices_feature);
		if (indices_feature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_feature", indices_feature, NX_INT);
			validateFieldUnits("indices_feature", group.getDataNode("indices_feature"), NX_UNITLESS);
			validateFieldRank("indices_feature", indices_feature, 1);
			validateFieldDimensions("indices_feature", indices_feature, null, "i");
		}

		// validate field 'volume' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset volume = group.getLazyDataset("volume");
		validateFieldNotNull("volume", volume);
		if (volume != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("volume", volume, NX_FLOAT);
			validateFieldUnits("volume", group.getDataNode("volume"), NX_VOLUME);
			validateFieldRank("volume", volume, 1);
			validateFieldDimensions("volume", volume, null, "i");
		}

		// validate optional child group 'obb' of type NXcg_hexahedron
		if (group.getChild("obb", NXcg_hexahedron.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_obb(group.getChild("obb", NXcg_hexahedron.class));
		}

		// validate optional child group 'objectID' of type NXcg_polyhedron
		if (group.getChild("objectID", NXcg_polyhedron.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_objectID(group.getChild("objectID", NXcg_polyhedron.class));
		}

		// validate optional child group 'composition' of type NXchemical_composition
		if (group.getChild("composition", NXchemical_composition.class) != null) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_composition(group.getChild("composition", NXchemical_composition.class));
		}
	}

	/**
	 * Validate optional group 'obb' of type NXcg_hexahedron.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_obb(final NXcg_hexahedron group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("obb", NXcg_hexahedron.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'size' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset size = group.getLazyDataset("size");
				if (size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("size", size, NX_FLOAT);
			validateFieldUnits("size", group.getDataNode("size"), NX_LENGTH);
			validateFieldRank("size", size, 2);
			validateFieldDimensions("size", size, null, "i", 3);
		}

		// validate optional field 'aspect' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset aspect = group.getLazyDataset("aspect");
				if (aspect != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("aspect", aspect, NX_FLOAT);
			validateFieldUnits("aspect", group.getDataNode("aspect"), NX_DIMENSIONLESS);
			validateFieldRank("aspect", aspect, 2);
			validateFieldDimensions("aspect", aspect, null, "i", 2);
		}

		// validate optional field 'center' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset center = group.getLazyDataset("center");
				if (center != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center", center, NX_NUMBER);
			validateFieldUnits("center", group.getDataNode("center"), NX_LENGTH);
			validateFieldRank("center", center, 2);
			validateFieldDimensions("center", center, null, "i", 3);
		}

		// validate optional child group 'hexahedra' of type NXcg_face_list_data_structure
		if (group.getHexahedra() != null) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_obb_hexahedra(group.getHexahedra());
		}
	}

	/**
	 * Validate optional group 'hexahedra' of type NXcg_face_list_data_structure.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_obb_hexahedra(final NXcg_face_list_data_structure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("hexahedra", NXcg_face_list_data_structure.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'vertices' of type NX_NUMBER.
		final ILazyDataset vertices = group.getLazyDataset("vertices");
		validateFieldNotNull("vertices", vertices);
		if (vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertices", vertices, NX_NUMBER);
			validateFieldUnits("vertices", group.getDataNode("vertices"), NX_LENGTH);
			validateFieldRank("vertices", vertices, 2);
			validateFieldDimensions("vertices", vertices, null, "k", 3);
		}

		// validate field 'xdmf_topology' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset xdmf_topology = group.getLazyDataset("xdmf_topology");
		validateFieldNotNull("xdmf_topology", xdmf_topology);
		if (xdmf_topology != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_topology", xdmf_topology, NX_INT);
			validateFieldUnits("xdmf_topology", group.getDataNode("xdmf_topology"), NX_UNITLESS);
			validateFieldRank("xdmf_topology", xdmf_topology, 1);
			validateFieldDimensions("xdmf_topology", xdmf_topology, null, "k");
		}

		// validate field 'indices_feature_xdmf' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_feature_xdmf = group.getLazyDataset("indices_feature_xdmf");
		validateFieldNotNull("indices_feature_xdmf", indices_feature_xdmf);
		if (indices_feature_xdmf != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_feature_xdmf", indices_feature_xdmf, NX_INT);
			validateFieldUnits("indices_feature_xdmf", group.getDataNode("indices_feature_xdmf"), NX_UNITLESS);
			validateFieldRank("indices_feature_xdmf", indices_feature_xdmf, 1);
			validateFieldDimensions("indices_feature_xdmf", indices_feature_xdmf, null, "k");
		}
	}

	/**
	 * Validate optional group 'objectID' of type NXcg_polyhedron.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_objectID(final NXcg_polyhedron group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("objectID", NXcg_polyhedron.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate child group 'polyhedron' of type NXcg_face_list_data_structure
		validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_objectID_polyhedron(group.getChild("polyhedron", NXcg_face_list_data_structure.class));
	}

	/**
	 * Validate group 'polyhedron' of type NXcg_face_list_data_structure.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_objectID_polyhedron(final NXcg_face_list_data_structure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("polyhedron", NXcg_face_list_data_structure.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'vertices' of type NX_FLOAT.
		final ILazyDataset vertices = group.getLazyDataset("vertices");
		validateFieldNotNull("vertices", vertices);
		if (vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertices", vertices, NX_FLOAT);
			validateFieldUnits("vertices", group.getDataNode("vertices"), NX_LENGTH);
			validateFieldRank("vertices", vertices, 2);
			validateFieldDimensions("vertices", vertices, null, "n_v", 3);
		}

		// validate field 'faces' of type NX_UINT.
		final ILazyDataset faces = group.getLazyDataset("faces");
		validateFieldNotNull("faces", faces);
		if (faces != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("faces", faces, NX_UINT);
			validateFieldUnits("faces", group.getDataNode("faces"), NX_UNITLESS);
			validateFieldRank("faces", faces, 2);
			validateFieldDimensions("faces", faces, null, "n_f", 3);
		}

		// validate field 'face_normals' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset face_normals = group.getLazyDataset("face_normals");
		validateFieldNotNull("face_normals", face_normals);
		if (face_normals != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("face_normals", face_normals, NX_FLOAT);
			validateFieldUnits("face_normals", group.getDataNode("face_normals"), NX_LENGTH);
			validateFieldRank("face_normals", face_normals, 2);
			validateFieldDimensions("face_normals", face_normals, null, "n_f", 3);
		}

		// validate optional field 'xdmf_topology' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset xdmf_topology = group.getLazyDataset("xdmf_topology");
				if (xdmf_topology != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_topology", xdmf_topology, NX_INT);
			validateFieldUnits("xdmf_topology", group.getDataNode("xdmf_topology"), NX_UNITLESS);
			validateFieldRank("xdmf_topology", xdmf_topology, 1);
			validateFieldDimensions("xdmf_topology", xdmf_topology, null, "k");
		}

		// validate optional field 'xdmf_indices_feature' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset xdmf_indices_feature = group.getLazyDataset("xdmf_indices_feature");
				if (xdmf_indices_feature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_indices_feature", xdmf_indices_feature, NX_INT);
			validateFieldUnits("xdmf_indices_feature", group.getDataNode("xdmf_indices_feature"), NX_UNITLESS);
			validateFieldRank("xdmf_indices_feature", xdmf_indices_feature, 1);
			validateFieldDimensions("xdmf_indices_feature", xdmf_indices_feature, null, "k");
		}

		// validate optional field 'ion_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset ion_id = group.getLazyDataset("ion_id");
				if (ion_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ion_id", ion_id, NX_UINT);
			validateFieldUnits("ion_id", group.getDataNode("ion_id"), NX_UNITLESS);
			validateFieldRank("ion_id", ion_id, 1);
			validateFieldDimensions("ion_id", ion_id, null, "m");
		}
	}

	/**
	 * Validate optional group 'composition' of type NXchemical_composition.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_composition(final NXchemical_composition group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("composition", NXchemical_composition.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'total' of type NX_NUMBER.
		final ILazyDataset total = group.getLazyDataset("total");
		validateFieldNotNull("total", total);
		if (total != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total", total, NX_NUMBER);
			validateFieldUnits("total", group.getDataNode("total"), NX_UNITLESS);
			validateFieldRank("total", total, 1);
			validateFieldDimensions("total", total, null, "i");
		}

		// validate unnamed child group of type NXatom (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXatom.class, false, true);
		final Map<String, NXatom> allAtom = group.getChildren(NXatom.class);
		for (final NXatom atom : allAtom.values()) {
			validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_composition_NXatom(atom);
		}
	}

	/**
	 * Validate optional unnamed group of type NXatom.
	 */
	private void validateGroup_NXentry_delocalizationID_grid_iso_surfaceID_triangle_soup_triangles_volumetric_features_FEATURE_composition_NXatom(final NXatom group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXatom.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'charge_state' of type NX_INT.
		final ILazyDataset charge_state = group.getLazyDataset("charge_state");
		validateFieldNotNull("charge_state", charge_state);
		if (charge_state != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("charge_state", charge_state, NX_INT);
			validateFieldUnits("charge_state", group.getDataNode("charge_state"), NX_UNITLESS);
		}

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

		// validate field 'nuclide_list' of type NX_UINT.
		final ILazyDataset nuclide_list = group.getLazyDataset("nuclide_list");
		validateFieldNotNull("nuclide_list", nuclide_list);
		if (nuclide_list != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("nuclide_list", nuclide_list, NX_UINT);
			validateFieldUnits("nuclide_list", group.getDataNode("nuclide_list"), NX_UNITLESS);
			validateFieldRank("nuclide_list", nuclide_list, 2);
			validateFieldDimensions("nuclide_list", nuclide_list, "NXatom", "n_ivec_max", 2);
		}

		// validate field 'count' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset count = group.getLazyDataset("count");
		validateFieldNotNull("count", count);
		if (count != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("count", count, NX_NUMBER);
			validateFieldUnits("count", group.getDataNode("count"), NX_UNITLESS);
			validateFieldRank("count", count, 1);
			validateFieldDimensions("count", count, null, "i");
		}
	}

	/**
	 * Validate optional group 'interface_meshingID' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_interface_meshingID(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("interface_meshingID", NXapm_paraprobe_tool_process.class, group))) return;

		// validate optional field 'ion_multiplicity' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset ion_multiplicity = group.getLazyDataset("ion_multiplicity");
				if (ion_multiplicity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ion_multiplicity", ion_multiplicity, NX_UINT);
			validateFieldUnits("ion_multiplicity", group.getDataNode("ion_multiplicity"), NX_UNITLESS);
			validateFieldRank("ion_multiplicity", ion_multiplicity, 1);
			validateFieldDimensions("ion_multiplicity", ion_multiplicity, null, "n_ions");
		}

		// validate optional field 'decorator_multiplicity' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset decorator_multiplicity = group.getLazyDataset("decorator_multiplicity");
				if (decorator_multiplicity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("decorator_multiplicity", decorator_multiplicity, NX_UINT);
			validateFieldUnits("decorator_multiplicity", group.getDataNode("decorator_multiplicity"), NX_UNITLESS);
			validateFieldRank("decorator_multiplicity", decorator_multiplicity, 1);
			validateFieldDimensions("decorator_multiplicity", decorator_multiplicity, null, "n_ions");
		}

		// validate optional child group 'initial_interface' of type NXprocess
		if (group.getChild("initial_interface", NXprocess.class) != null) {
			validateGroup_NXentry_interface_meshingID_initial_interface(group.getChild("initial_interface", NXprocess.class));
		}

		// validate optional child group 'mesh_stateID' of type NXcg_triangle
		if (group.getChild("mesh_stateID", NXcg_triangle.class) != null) {
			validateGroup_NXentry_interface_meshingID_mesh_stateID(group.getChild("mesh_stateID", NXcg_triangle.class));
		}
	}

	/**
	 * Validate optional group 'initial_interface' of type NXprocess.
	 */
	private void validateGroup_NXentry_interface_meshingID_initial_interface(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("initial_interface", NXprocess.class, group))) return;

		// validate field 'point_normal_form' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset point_normal_form = group.getLazyDataset("point_normal_form");
		validateFieldNotNull("point_normal_form", point_normal_form);
		if (point_normal_form != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("point_normal_form", point_normal_form, NX_FLOAT);
			validateFieldUnits("point_normal_form", group.getDataNode("point_normal_form"), NX_LENGTH);
			validateFieldRank("point_normal_form", point_normal_form, 1);
			validateFieldDimensions("point_normal_form", point_normal_form, null, 4);
		}
	}

	/**
	 * Validate optional group 'mesh_stateID' of type NXcg_triangle.
	 */
	private void validateGroup_NXentry_interface_meshingID_mesh_stateID(final NXcg_triangle group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("mesh_stateID", NXcg_triangle.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'state' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset state = group.getLazyDataset("state");
		validateFieldNotNull("state", state);
		if (state != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("state", state, NX_CHAR);
			validateFieldEnumeration("state", state,
					"before",
					"after");
		}

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

		// validate field 'area' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset area = group.getLazyDataset("area");
		validateFieldNotNull("area", area);
		if (area != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("area", area, NX_NUMBER);
			validateFieldUnits("area", group.getDataNode("area"), NX_AREA);
			validateFieldRank("area", area, 1);
			validateFieldDimensions("area", area, null, "c");
		}

		// validate field 'edge_length' of type NX_NUMBER.
		final ILazyDataset edge_length = group.getLazyDataset("edge_length");
		validateFieldNotNull("edge_length", edge_length);
		if (edge_length != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("edge_length", edge_length, NX_NUMBER);
			validateFieldUnits("edge_length", group.getDataNode("edge_length"), NX_LENGTH);
			validateFieldRank("edge_length", edge_length, 2);
			validateFieldDimensions("edge_length", edge_length, null, "c", 3);
		}

		// validate field 'interior_angle' of type NX_NUMBER.
		final ILazyDataset interior_angle = group.getLazyDataset("interior_angle");
		validateFieldNotNull("interior_angle", interior_angle);
		if (interior_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("interior_angle", interior_angle, NX_NUMBER);
			validateFieldUnits("interior_angle", group.getDataNode("interior_angle"), NX_ANGLE);
			validateFieldRank("interior_angle", interior_angle, 2);
			validateFieldDimensions("interior_angle", interior_angle, null, "c", 4);
		}
		// validate child group 'triangles' of type NXcg_face_list_data_structure
		validateGroup_NXentry_interface_meshingID_mesh_stateID_triangles(group.getTriangles());
	}

	/**
	 * Validate group 'triangles' of type NXcg_face_list_data_structure.
	 */
	private void validateGroup_NXentry_interface_meshingID_mesh_stateID_triangles(final NXcg_face_list_data_structure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("triangles", NXcg_face_list_data_structure.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'dimensionality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset dimensionality = group.getLazyDataset("dimensionality");
		validateFieldNotNull("dimensionality", dimensionality);
		if (dimensionality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dimensionality", dimensionality, NX_POSINT);
		}

		// validate field 'number_of_vertices' of type NX_UINT.
		final ILazyDataset number_of_vertices = group.getLazyDataset("number_of_vertices");
		validateFieldNotNull("number_of_vertices", number_of_vertices);
		if (number_of_vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_vertices", number_of_vertices, NX_UINT);
			validateFieldUnits("number_of_vertices", group.getDataNode("number_of_vertices"), NX_UNITLESS);
			validateFieldRank("number_of_vertices", number_of_vertices, 1);
			validateFieldDimensions("number_of_vertices", number_of_vertices, "NXcg_face_list_data_structure", "n_f");
		}

		// validate field 'number_of_faces' of type NX_UINT.
		final ILazyDataset number_of_faces = group.getLazyDataset("number_of_faces");
		validateFieldNotNull("number_of_faces", number_of_faces);
		if (number_of_faces != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_faces", number_of_faces, NX_UINT);
			validateFieldUnits("number_of_faces", group.getDataNode("number_of_faces"), NX_UNITLESS);
		}

		// validate field 'index_offset_vertex' of type NX_INT.
		final ILazyDataset index_offset_vertex = group.getLazyDataset("index_offset_vertex");
		validateFieldNotNull("index_offset_vertex", index_offset_vertex);
		if (index_offset_vertex != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset_vertex", index_offset_vertex, NX_INT);
			validateFieldUnits("index_offset_vertex", group.getDataNode("index_offset_vertex"), NX_UNITLESS);
		}

		// validate field 'index_offset_edge' of type NX_INT.
		final ILazyDataset index_offset_edge = group.getLazyDataset("index_offset_edge");
		validateFieldNotNull("index_offset_edge", index_offset_edge);
		if (index_offset_edge != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset_edge", index_offset_edge, NX_INT);
			validateFieldUnits("index_offset_edge", group.getDataNode("index_offset_edge"), NX_UNITLESS);
		}

		// validate field 'index_offset_face' of type NX_INT.
		final ILazyDataset index_offset_face = group.getLazyDataset("index_offset_face");
		validateFieldNotNull("index_offset_face", index_offset_face);
		if (index_offset_face != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset_face", index_offset_face, NX_INT);
			validateFieldUnits("index_offset_face", group.getDataNode("index_offset_face"), NX_UNITLESS);
		}

		// validate field 'indices_face' of type NX_INT.
		final ILazyDataset indices_face = group.getLazyDataset("indices_face");
		validateFieldNotNull("indices_face", indices_face);
		if (indices_face != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_face", indices_face, NX_INT);
			validateFieldUnits("indices_face", group.getDataNode("indices_face"), NX_UNITLESS);
			validateFieldRank("indices_face", indices_face, 1);
			validateFieldDimensions("indices_face", indices_face, null, "j");
		}

		// validate field 'vertices' of type NX_NUMBER.
		final ILazyDataset vertices = group.getLazyDataset("vertices");
		validateFieldNotNull("vertices", vertices);
		if (vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertices", vertices, NX_NUMBER);
			validateFieldUnits("vertices", group.getDataNode("vertices"), NX_LENGTH);
			validateFieldRank("vertices", vertices, 2);
			validateFieldDimensions("vertices", vertices, null, "i", 3);
		}

		// validate field 'vertex_normal' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset vertex_normal = group.getLazyDataset("vertex_normal");
		validateFieldNotNull("vertex_normal", vertex_normal);
		if (vertex_normal != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertex_normal", vertex_normal, NX_FLOAT);
			validateFieldUnits("vertex_normal", group.getDataNode("vertex_normal"), NX_LENGTH);
			validateFieldRank("vertex_normal", vertex_normal, 2);
			validateFieldDimensions("vertex_normal", vertex_normal, null, "i", 3);
		}

		// validate field 'vertex_normal_orientation' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset vertex_normal_orientation = group.getLazyDataset("vertex_normal_orientation");
		validateFieldNotNull("vertex_normal_orientation", vertex_normal_orientation);
		if (vertex_normal_orientation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertex_normal_orientation", vertex_normal_orientation, NX_UINT);
			validateFieldUnits("vertex_normal_orientation", group.getDataNode("vertex_normal_orientation"), NX_UNITLESS);
			validateFieldRank("vertex_normal_orientation", vertex_normal_orientation, 1);
			validateFieldDimensions("vertex_normal_orientation", vertex_normal_orientation, null, "i");
		}

		// validate field 'faces' of type NX_UINT.
		final ILazyDataset faces = group.getLazyDataset("faces");
		validateFieldNotNull("faces", faces);
		if (faces != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("faces", faces, NX_UINT);
			validateFieldUnits("faces", group.getDataNode("faces"), NX_UNITLESS);
			validateFieldRank("faces", faces, 2);
			validateFieldDimensions("faces", faces, null, "j", 3);
		}

		// validate field 'face_normal' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset face_normal = group.getLazyDataset("face_normal");
		validateFieldNotNull("face_normal", face_normal);
		if (face_normal != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("face_normal", face_normal, NX_FLOAT);
			validateFieldUnits("face_normal", group.getDataNode("face_normal"), NX_LENGTH);
			validateFieldRank("face_normal", face_normal, 2);
			validateFieldDimensions("face_normal", face_normal, null, "j", 3);
		}

		// validate field 'face_normal_orientation' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset face_normal_orientation = group.getLazyDataset("face_normal_orientation");
		validateFieldNotNull("face_normal_orientation", face_normal_orientation);
		if (face_normal_orientation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("face_normal_orientation", face_normal_orientation, NX_UINT);
			validateFieldUnits("face_normal_orientation", group.getDataNode("face_normal_orientation"), NX_UNITLESS);
			validateFieldRank("face_normal_orientation", face_normal_orientation, 1);
			validateFieldDimensions("face_normal_orientation", face_normal_orientation, null, "j");
		}

		// validate field 'xdmf_topology' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset xdmf_topology = group.getLazyDataset("xdmf_topology");
		validateFieldNotNull("xdmf_topology", xdmf_topology);
		if (xdmf_topology != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_topology", xdmf_topology, NX_UINT);
			validateFieldUnits("xdmf_topology", group.getDataNode("xdmf_topology"), NX_UNITLESS);
			validateFieldRank("xdmf_topology", xdmf_topology, 1);
			validateFieldDimensions("xdmf_topology", xdmf_topology, null, "k");
		}
	}

	/**
	 * Validate optional group 'oned_profileID' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_oned_profileID(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("oned_profileID", NXapm_paraprobe_tool_process.class, group))) return;

		// validate child group 'xdmf_cylinder' of type NXcg_polyhedron
		validateGroup_NXentry_oned_profileID_xdmf_cylinder(group.getChild("xdmf_cylinder", NXcg_polyhedron.class));
	}

	/**
	 * Validate group 'xdmf_cylinder' of type NXcg_polyhedron.
	 */
	private void validateGroup_NXentry_oned_profileID_xdmf_cylinder(final NXcg_polyhedron group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("xdmf_cylinder", NXcg_polyhedron.class, group))) return;
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

		// validate field 'center' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset center = group.getLazyDataset("center");
		validateFieldNotNull("center", center);
		if (center != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center", center, NX_NUMBER);
			validateFieldUnits("center", group.getDataNode("center"), NX_LENGTH);
			validateFieldRank("center", center, 2);
			validateFieldDimensions("center", center, null, "i", 3);
		}

		// validate field 'orientation' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset orientation = group.getLazyDataset("orientation");
		validateFieldNotNull("orientation", orientation);
		if (orientation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("orientation", orientation, NX_FLOAT);
			validateFieldUnits("orientation", group.getDataNode("orientation"), NX_LENGTH);
			validateFieldRank("orientation", orientation, 2);
			validateFieldDimensions("orientation", orientation, null, "i", 3);
		}

		// validate optional field 'roi_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset roi_id = group.getLazyDataset("roi_id");
				if (roi_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("roi_id", roi_id, NX_UINT);
			validateFieldUnits("roi_id", group.getDataNode("roi_id"), NX_UNITLESS);
			validateFieldRank("roi_id", roi_id, 1);
			validateFieldDimensions("roi_id", roi_id, null, "j");
		}

		// validate optional field 'edge_contact' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset edge_contact = group.getLazyDataset("edge_contact");
				if (edge_contact != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("edge_contact", edge_contact, NX_UINT);
			validateFieldUnits("edge_contact", group.getDataNode("edge_contact"), NX_UNITLESS);
			validateFieldRank("edge_contact", edge_contact, 1);
			validateFieldDimensions("edge_contact", edge_contact, null, "j");
		}

		// validate optional field 'number_of_atoms' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_atoms = group.getLazyDataset("number_of_atoms");
				if (number_of_atoms != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_atoms", number_of_atoms, NX_UINT);
			validateFieldUnits("number_of_atoms", group.getDataNode("number_of_atoms"), NX_UNITLESS);
			validateFieldRank("number_of_atoms", number_of_atoms, 1);
			validateFieldDimensions("number_of_atoms", number_of_atoms, null, "j");
		}

		// validate optional field 'number_of_ions' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_ions = group.getLazyDataset("number_of_ions");
				if (number_of_ions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_ions", number_of_ions, NX_UINT);
			validateFieldUnits("number_of_ions", group.getDataNode("number_of_ions"), NX_UNITLESS);
			validateFieldRank("number_of_ions", number_of_ions, 1);
			validateFieldDimensions("number_of_ions", number_of_ions, null, "j");
		}

		// validate child group 'rois_far_from_edge' of type NXprocess
		validateGroup_NXentry_oned_profileID_xdmf_cylinder_rois_far_from_edge(group.getChild("rois_far_from_edge", NXprocess.class));
	}

	/**
	 * Validate group 'rois_far_from_edge' of type NXprocess.
	 */
	private void validateGroup_NXentry_oned_profileID_xdmf_cylinder_rois_far_from_edge(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("rois_far_from_edge", NXprocess.class, group))) return;

		// validate optional child group 'roiID' of type NXcg_roi
		if (group.getChild("roiID", NXcg_roi.class) != null) {
			validateGroup_NXentry_oned_profileID_xdmf_cylinder_rois_far_from_edge_roiID(group.getChild("roiID", NXcg_roi.class));
		}
	}

	/**
	 * Validate optional group 'roiID' of type NXcg_roi.
	 */
	private void validateGroup_NXentry_oned_profileID_xdmf_cylinder_rois_far_from_edge_roiID(final NXcg_roi group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("roiID", NXcg_roi.class, group))) return;

		// validate field 'signed_distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset signed_distance = group.getLazyDataset("signed_distance");
		validateFieldNotNull("signed_distance", signed_distance);
		if (signed_distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("signed_distance", signed_distance, NX_FLOAT);
			validateFieldUnits("signed_distance", group.getDataNode("signed_distance"), NX_LENGTH);
			validateFieldRank("signed_distance", signed_distance, 1);
			validateFieldDimensions("signed_distance", signed_distance, null, "k");
		}

		// validate field 'nuclide_hash' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset nuclide_hash = group.getLazyDataset("nuclide_hash");
		validateFieldNotNull("nuclide_hash", nuclide_hash);
		if (nuclide_hash != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("nuclide_hash", nuclide_hash, NX_UINT);
			validateFieldUnits("nuclide_hash", group.getDataNode("nuclide_hash"), NX_UNITLESS);
			validateFieldRank("nuclide_hash", nuclide_hash, 1);
			validateFieldDimensions("nuclide_hash", nuclide_hash, null, "k");
		}
	}
}
