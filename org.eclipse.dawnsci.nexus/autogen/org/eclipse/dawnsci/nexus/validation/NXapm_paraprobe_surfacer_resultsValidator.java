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
import org.eclipse.dawnsci.nexus.NXcg_alpha_complex;
import org.eclipse.dawnsci.nexus.NXcs_filter_boolean_mask;
import org.eclipse.dawnsci.nexus.NXcg_triangle;
import org.eclipse.dawnsci.nexus.NXcg_face_list_data_structure;
import org.eclipse.dawnsci.nexus.NXcg_tetrahedron;

/**
 * Validator for the application definition 'NXapm_paraprobe_surfacer_results'.
 */
public class NXapm_paraprobe_surfacer_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_surfacer_resultsValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_SURFACER_RESULTS);
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
					"NXapm_paraprobe_surfacer_results");
		}

		// validate child group 'point_set_wrappingID' of type NXapm_paraprobe_tool_process
		validateGroup_NXentry_point_set_wrappingID(group.getChild("point_set_wrappingID", NXapm_paraprobe_tool_process.class));
	}

	/**
	 * Validate group 'point_set_wrappingID' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_point_set_wrappingID(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("point_set_wrappingID", NXapm_paraprobe_tool_process.class, group))) return;

		// validate optional child group 'alpha_complexID' of type NXcg_alpha_complex
		if (group.getChild("alpha_complexID", NXcg_alpha_complex.class) != null) {
			validateGroup_NXentry_point_set_wrappingID_alpha_complexID(group.getChild("alpha_complexID", NXcg_alpha_complex.class));
		}
	}

	/**
	 * Validate optional group 'alpha_complexID' of type NXcg_alpha_complex.
	 */
	private void validateGroup_NXentry_point_set_wrappingID_alpha_complexID(final NXcg_alpha_complex group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("alpha_complexID", NXcg_alpha_complex.class, group))) return;

		// validate field 'dimensionality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset dimensionality = group.getLazyDataset("dimensionality");
		validateFieldNotNull("dimensionality", dimensionality);
		if (dimensionality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dimensionality", dimensionality, NX_POSINT);
			validateFieldEnumeration("dimensionality", dimensionality,
					"2",
					"3");
		}

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"convex_hull",
					"alpha_shape",
					"alpha_wrapping",
					"other",
					"undefined");
		}

		// validate field 'mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset mode = group.getLazyDataset("mode");
		validateFieldNotNull("mode", mode);
		if (mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mode", mode, NX_CHAR);
			validateFieldEnumeration("mode", mode,
					"general",
					"regularized");
		}

		// validate field 'alpha' of type NX_NUMBER.
		final ILazyDataset alpha = group.getLazyDataset("alpha");
		validateFieldNotNull("alpha", alpha);
		if (alpha != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("alpha", alpha, NX_NUMBER);
			validateFieldUnits("alpha", group.getDataNode("alpha"), NX_ANY);
		}

		// validate optional field 'offset' of type NX_NUMBER.
		final ILazyDataset offset = group.getLazyDataset("offset");
				if (offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("offset", offset, NX_NUMBER);
			validateFieldUnits("offset", group.getDataNode("offset"), NX_LENGTH);
		}

		// validate child group 'window' of type NXcs_filter_boolean_mask
		validateGroup_NXentry_point_set_wrappingID_alpha_complexID_window(group.getChild("window", NXcs_filter_boolean_mask.class));

		// validate optional child group 'triangle_set' of type NXcg_triangle
		if (group.getChild("triangle_set", NXcg_triangle.class) != null) {
			validateGroup_NXentry_point_set_wrappingID_alpha_complexID_triangle_set(group.getChild("triangle_set", NXcg_triangle.class));
		}

		// validate optional child group 'interior_tetrahedra' of type NXcg_tetrahedron
		if (group.getChild("interior_tetrahedra", NXcg_tetrahedron.class) != null) {
			validateGroup_NXentry_point_set_wrappingID_alpha_complexID_interior_tetrahedra(group.getChild("interior_tetrahedra", NXcg_tetrahedron.class));
		}
	}

	/**
	 * Validate group 'window' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_point_set_wrappingID_alpha_complexID_window(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("window", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_ions' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_ions = group.getLazyDataset("number_of_ions");
		validateFieldNotNull("number_of_ions", number_of_ions);
		if (number_of_ions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_ions", number_of_ions, NX_UINT);
			validateFieldUnits("number_of_ions", group.getDataNode("number_of_ions"), NX_UNITLESS);
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
	 * Validate optional group 'triangle_set' of type NXcg_triangle.
	 */
	private void validateGroup_NXentry_point_set_wrappingID_alpha_complexID_triangle_set(final NXcg_triangle group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("triangle_set", NXcg_triangle.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset = group.getLazyDataset("index_offset");
		validateFieldNotNull("index_offset", index_offset);
		if (index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset", index_offset, NX_INT);
		}

		// validate child group 'triangles' of type NXcg_face_list_data_structure
		validateGroup_NXentry_point_set_wrappingID_alpha_complexID_triangle_set_triangles(group.getTriangles());
	}

	/**
	 * Validate group 'triangles' of type NXcg_face_list_data_structure.
	 */
	private void validateGroup_NXentry_point_set_wrappingID_alpha_complexID_triangle_set_triangles(final NXcg_face_list_data_structure group) {
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

		// validate field 'indices_offset_vertex' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_offset_vertex = group.getLazyDataset("indices_offset_vertex");
		validateFieldNotNull("indices_offset_vertex", indices_offset_vertex);
		if (indices_offset_vertex != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_offset_vertex", indices_offset_vertex, NX_INT);
		}

		// validate field 'indices_offset_face' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_offset_face = group.getLazyDataset("indices_offset_face");
		validateFieldNotNull("indices_offset_face", indices_offset_face);
		if (indices_offset_face != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_offset_face", indices_offset_face, NX_INT);
		}

		// validate field 'vertices' of type NX_FLOAT.
		final ILazyDataset vertices = group.getLazyDataset("vertices");
		validateFieldNotNull("vertices", vertices);
		if (vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertices", vertices, NX_FLOAT);
			validateFieldUnits("vertices", group.getDataNode("vertices"), NX_LENGTH);
			validateFieldRank("vertices", vertices, 2);
			validateFieldDimensions("vertices", vertices, null, "n_v_tri", 3);
		}

		// validate field 'faces' of type NX_UINT.
		final ILazyDataset faces = group.getLazyDataset("faces");
		validateFieldNotNull("faces", faces);
		if (faces != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("faces", faces, NX_UINT);
			validateFieldUnits("faces", group.getDataNode("faces"), NX_UNITLESS);
			validateFieldRank("faces", faces, 2);
			validateFieldDimensions("faces", faces, null, "n_f_tri", 3);
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

		// validate optional field 'is_watertight' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_watertight = group.getLazyDataset("is_watertight");
				if (is_watertight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_watertight", is_watertight, NX_BOOLEAN);
		}

		// validate optional field 'volume' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset volume = group.getLazyDataset("volume");
				if (volume != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("volume", volume, NX_FLOAT);
			validateFieldUnits("volume", group.getDataNode("volume"), NX_VOLUME);
		}
	}

	/**
	 * Validate optional group 'interior_tetrahedra' of type NXcg_tetrahedron.
	 */
	private void validateGroup_NXentry_point_set_wrappingID_alpha_complexID_interior_tetrahedra(final NXcg_tetrahedron group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("interior_tetrahedra", NXcg_tetrahedron.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset = group.getLazyDataset("index_offset");
		validateFieldNotNull("index_offset", index_offset);
		if (index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset", index_offset, NX_INT);
		}

		// validate optional field 'volume' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset volume = group.getLazyDataset("volume");
				if (volume != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("volume", volume, NX_FLOAT);
			validateFieldUnits("volume", group.getDataNode("volume"), NX_VOLUME);
		}

		// validate optional child group 'tetrahedra' of type NXcg_face_list_data_structure
		if (group.getTetrahedra() != null) {
			validateGroup_NXentry_point_set_wrappingID_alpha_complexID_interior_tetrahedra_tetrahedra(group.getTetrahedra());
		}
	}

	/**
	 * Validate optional group 'tetrahedra' of type NXcg_face_list_data_structure.
	 */
	private void validateGroup_NXentry_point_set_wrappingID_alpha_complexID_interior_tetrahedra_tetrahedra(final NXcg_face_list_data_structure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("tetrahedra", NXcg_face_list_data_structure.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

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

		// validate field 'indices_offset_vertex' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_offset_vertex = group.getLazyDataset("indices_offset_vertex");
		validateFieldNotNull("indices_offset_vertex", indices_offset_vertex);
		if (indices_offset_vertex != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_offset_vertex", indices_offset_vertex, NX_INT);
		}

		// validate field 'indices_offset_face' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_offset_face = group.getLazyDataset("indices_offset_face");
		validateFieldNotNull("indices_offset_face", indices_offset_face);
		if (indices_offset_face != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_offset_face", indices_offset_face, NX_INT);
		}

		// validate field 'vertices' of type NX_FLOAT.
		final ILazyDataset vertices = group.getLazyDataset("vertices");
		validateFieldNotNull("vertices", vertices);
		if (vertices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vertices", vertices, NX_FLOAT);
			validateFieldUnits("vertices", group.getDataNode("vertices"), NX_LENGTH);
			validateFieldRank("vertices", vertices, 2);
			validateFieldDimensions("vertices", vertices, null, "n_v_tet", 3);
		}

		// validate field 'xdmf_topology' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset xdmf_topology = group.getLazyDataset("xdmf_topology");
		validateFieldNotNull("xdmf_topology", xdmf_topology);
		if (xdmf_topology != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_topology", xdmf_topology, NX_UINT);
			validateFieldUnits("xdmf_topology", group.getDataNode("xdmf_topology"), NX_UNITLESS);
			validateFieldRank("xdmf_topology", xdmf_topology, 1);
			validateFieldDimensions("xdmf_topology", xdmf_topology, null, "n_f_tet_xdmf");
		}
	}
}
