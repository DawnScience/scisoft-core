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
import org.eclipse.dawnsci.nexus.NXcg_hexahedron;
import org.eclipse.dawnsci.nexus.NXcg_polyhedron;
import org.eclipse.dawnsci.nexus.NXcg_face_list_data_structure;
import org.eclipse.dawnsci.nexus.NXcs_filter_boolean_mask;

/**
 * Validator for the application definition 'NXapm_paraprobe_tessellator_results'.
 */
public class NXapm_paraprobe_tessellator_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_tessellator_resultsValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_TESSELLATOR_RESULTS);
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
					"NXapm_paraprobe_tessellator_results");
		}

		// validate child group 'tessellationID' of type NXapm_paraprobe_tool_process
		validateGroup_NXentry_tessellationID(group.getChild("tessellationID", NXapm_paraprobe_tool_process.class));
	}

	/**
	 * Validate group 'tessellationID' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_tessellationID(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("tessellationID", NXapm_paraprobe_tool_process.class, group))) return;

		// validate optional child group 'wall' of type NXcg_hexahedron
		if (group.getChild("wall", NXcg_hexahedron.class) != null) {
			validateGroup_NXentry_tessellationID_wall(group.getChild("wall", NXcg_hexahedron.class));
		}

		// validate optional child group 'voronoi_cells' of type NXcg_polyhedron
		if (group.getChild("voronoi_cells", NXcg_polyhedron.class) != null) {
			validateGroup_NXentry_tessellationID_voronoi_cells(group.getChild("voronoi_cells", NXcg_polyhedron.class));
		}

		// validate optional child group 'wall_contact_global' of type NXcs_filter_boolean_mask
		if (group.getChild("wall_contact_global", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_tessellationID_wall_contact_global(group.getChild("wall_contact_global", NXcs_filter_boolean_mask.class));
		}

		// validate optional child group 'wall_contact_left' of type NXcs_filter_boolean_mask
		if (group.getChild("wall_contact_left", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_tessellationID_wall_contact_left(group.getChild("wall_contact_left", NXcs_filter_boolean_mask.class));
		}

		// validate optional child group 'wall_contact_right' of type NXcs_filter_boolean_mask
		if (group.getChild("wall_contact_right", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_tessellationID_wall_contact_right(group.getChild("wall_contact_right", NXcs_filter_boolean_mask.class));
		}

		// validate optional child group 'wall_contact_front' of type NXcs_filter_boolean_mask
		if (group.getChild("wall_contact_front", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_tessellationID_wall_contact_front(group.getChild("wall_contact_front", NXcs_filter_boolean_mask.class));
		}

		// validate optional child group 'wall_contact_rear' of type NXcs_filter_boolean_mask
		if (group.getChild("wall_contact_rear", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_tessellationID_wall_contact_rear(group.getChild("wall_contact_rear", NXcs_filter_boolean_mask.class));
		}

		// validate optional child group 'wall_contact_bottom' of type NXcs_filter_boolean_mask
		if (group.getChild("wall_contact_bottom", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_tessellationID_wall_contact_bottom(group.getChild("wall_contact_bottom", NXcs_filter_boolean_mask.class));
		}

		// validate optional child group 'wall_contact_top' of type NXcs_filter_boolean_mask
		if (group.getChild("wall_contact_top", NXcs_filter_boolean_mask.class) != null) {
			validateGroup_NXentry_tessellationID_wall_contact_top(group.getChild("wall_contact_top", NXcs_filter_boolean_mask.class));
		}
	}

	/**
	 * Validate optional group 'wall' of type NXcg_hexahedron.
	 */
	private void validateGroup_NXentry_tessellationID_wall(final NXcg_hexahedron group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("wall", NXcg_hexahedron.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'closest_corner' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset closest_corner = group.getLazyDataset("closest_corner");
		validateFieldNotNull("closest_corner", closest_corner);
		if (closest_corner != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("closest_corner", closest_corner, NX_FLOAT);
			validateFieldUnits("closest_corner", group.getDataNode("closest_corner"), NX_LENGTH);
			validateFieldRank("closest_corner", closest_corner, 1);
			validateFieldDimensions("closest_corner", closest_corner, null, 3);
		}

		// validate field 'farthest_corner' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset farthest_corner = group.getLazyDataset("farthest_corner");
		validateFieldNotNull("farthest_corner", farthest_corner);
		if (farthest_corner != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("farthest_corner", farthest_corner, NX_FLOAT);
			validateFieldUnits("farthest_corner", group.getDataNode("farthest_corner"), NX_LENGTH);
			validateFieldRank("farthest_corner", farthest_corner, 1);
			validateFieldDimensions("farthest_corner", farthest_corner, null, 3);
		}
	}

	/**
	 * Validate optional group 'voronoi_cells' of type NXcg_polyhedron.
	 */
	private void validateGroup_NXentry_tessellationID_voronoi_cells(final NXcg_polyhedron group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("voronoi_cells", NXcg_polyhedron.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'dimensionality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset dimensionality = group.getLazyDataset("dimensionality");
		validateFieldNotNull("dimensionality", dimensionality);
		if (dimensionality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dimensionality", dimensionality, NX_POSINT);
			validateFieldEnumeration("dimensionality", dimensionality,
					"3");
		}

		// validate field 'cardinality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset cardinality = group.getLazyDataset("cardinality");
		validateFieldNotNull("cardinality", cardinality);
		if (cardinality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cardinality", cardinality, NX_POSINT);
		}

		// validate field 'volume' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset volume = group.getLazyDataset("volume");
		validateFieldNotNull("volume", volume);
		if (volume != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("volume", volume, NX_FLOAT);
			validateFieldUnits("volume", group.getDataNode("volume"), NX_VOLUME);
			validateFieldRank("volume", volume, 1);
			validateFieldDimensions("volume", volume, null, "n_ions");
		}

		// validate optional field 'process_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset process_id = group.getLazyDataset("process_id");
				if (process_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("process_id", process_id, NX_UINT);
			validateFieldUnits("process_id", group.getDataNode("process_id"), NX_UNITLESS);
			validateFieldRank("process_id", process_id, 1);
			validateFieldDimensions("process_id", process_id, null, "n_ions");
		}

		// validate optional field 'thread_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset thread_id = group.getLazyDataset("thread_id");
				if (thread_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("thread_id", thread_id, NX_UINT);
			validateFieldUnits("thread_id", group.getDataNode("thread_id"), NX_UNITLESS);
			validateFieldRank("thread_id", thread_id, 1);
			validateFieldDimensions("thread_id", thread_id, null, "n_ions");
		}

		// validate optional field 'number_of_faces' of type NX_UINT.
		final ILazyDataset number_of_faces = group.getLazyDataset("number_of_faces");
				if (number_of_faces != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_faces", number_of_faces, NX_UINT);
			validateFieldUnits("number_of_faces", group.getDataNode("number_of_faces"), NX_UNITLESS);
			validateFieldRank("number_of_faces", number_of_faces, 1);
			validateFieldDimensions("number_of_faces", number_of_faces, null, "n_ions");
		}

		// validate field 'index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset = group.getLazyDataset("index_offset");
		validateFieldNotNull("index_offset", index_offset);
		if (index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset", index_offset, NX_INT);
		}

		// validate field 'xdmf_topology' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset xdmf_topology = group.getLazyDataset("xdmf_topology");
		validateFieldNotNull("xdmf_topology", xdmf_topology);
		if (xdmf_topology != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_topology", xdmf_topology, NX_UINT);
			validateFieldUnits("xdmf_topology", group.getDataNode("xdmf_topology"), NX_UNITLESS);
			validateFieldRank("xdmf_topology", xdmf_topology, 1);
			validateFieldDimensions("xdmf_topology", xdmf_topology, null, "n_f_xdmf");
		}

		// validate field 'xdmf_cell_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset xdmf_cell_id = group.getLazyDataset("xdmf_cell_id");
		validateFieldNotNull("xdmf_cell_id", xdmf_cell_id);
		if (xdmf_cell_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xdmf_cell_id", xdmf_cell_id, NX_UINT);
			validateFieldUnits("xdmf_cell_id", group.getDataNode("xdmf_cell_id"), NX_UNITLESS);
			validateFieldRank("xdmf_cell_id", xdmf_cell_id, 1);
			validateFieldDimensions("xdmf_cell_id", xdmf_cell_id, null, "n_f");
		}
		// validate optional child group 'polyhedra' of type NXcg_face_list_data_structure
		if (group.getPolyhedra() != null) {
			validateGroup_NXentry_tessellationID_voronoi_cells_polyhedra(group.getPolyhedra());
		}
	}

	/**
	 * Validate optional group 'polyhedra' of type NXcg_face_list_data_structure.
	 */
	private void validateGroup_NXentry_tessellationID_voronoi_cells_polyhedra(final NXcg_face_list_data_structure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("polyhedra", NXcg_face_list_data_structure.class, group))) return;
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
			validateFieldUnits("vertices", group.getDataNode("vertices"), NX_ANY);
			validateFieldRank("vertices", vertices, 2);
			validateFieldDimensions("vertices", vertices, "NXcg_face_list_data_structure", "n_v", "d");
		}
	}

	/**
	 * Validate optional group 'wall_contact_global' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_tessellationID_wall_contact_global(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("wall_contact_global", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_objects' of type NX_UINT.
		final ILazyDataset number_of_objects = group.getLazyDataset("number_of_objects");
		validateFieldNotNull("number_of_objects", number_of_objects);
		if (number_of_objects != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_objects", number_of_objects, NX_UINT);
			validateFieldUnits("number_of_objects", group.getDataNode("number_of_objects"), NX_UNITLESS);
			validateFieldRank("number_of_objects", number_of_objects, 1);
			validateFieldDimensions("number_of_objects", number_of_objects, null, "n_ions");
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
	 * Validate optional group 'wall_contact_left' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_tessellationID_wall_contact_left(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("wall_contact_left", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_objects' of type NX_UINT.
		final ILazyDataset number_of_objects = group.getLazyDataset("number_of_objects");
		validateFieldNotNull("number_of_objects", number_of_objects);
		if (number_of_objects != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_objects", number_of_objects, NX_UINT);
			validateFieldUnits("number_of_objects", group.getDataNode("number_of_objects"), NX_UNITLESS);
			validateFieldRank("number_of_objects", number_of_objects, 1);
			validateFieldDimensions("number_of_objects", number_of_objects, null, "n_ions");
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
	 * Validate optional group 'wall_contact_right' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_tessellationID_wall_contact_right(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("wall_contact_right", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_objects' of type NX_UINT.
		final ILazyDataset number_of_objects = group.getLazyDataset("number_of_objects");
		validateFieldNotNull("number_of_objects", number_of_objects);
		if (number_of_objects != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_objects", number_of_objects, NX_UINT);
			validateFieldUnits("number_of_objects", group.getDataNode("number_of_objects"), NX_UNITLESS);
			validateFieldRank("number_of_objects", number_of_objects, 1);
			validateFieldDimensions("number_of_objects", number_of_objects, null, "n_ions");
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
	 * Validate optional group 'wall_contact_front' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_tessellationID_wall_contact_front(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("wall_contact_front", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_objects' of type NX_UINT.
		final ILazyDataset number_of_objects = group.getLazyDataset("number_of_objects");
		validateFieldNotNull("number_of_objects", number_of_objects);
		if (number_of_objects != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_objects", number_of_objects, NX_UINT);
			validateFieldUnits("number_of_objects", group.getDataNode("number_of_objects"), NX_UNITLESS);
			validateFieldRank("number_of_objects", number_of_objects, 1);
			validateFieldDimensions("number_of_objects", number_of_objects, null, "n_ions");
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
	 * Validate optional group 'wall_contact_rear' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_tessellationID_wall_contact_rear(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("wall_contact_rear", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_objects' of type NX_UINT.
		final ILazyDataset number_of_objects = group.getLazyDataset("number_of_objects");
		validateFieldNotNull("number_of_objects", number_of_objects);
		if (number_of_objects != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_objects", number_of_objects, NX_UINT);
			validateFieldUnits("number_of_objects", group.getDataNode("number_of_objects"), NX_UNITLESS);
			validateFieldRank("number_of_objects", number_of_objects, 1);
			validateFieldDimensions("number_of_objects", number_of_objects, null, "n_ions");
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
	 * Validate optional group 'wall_contact_bottom' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_tessellationID_wall_contact_bottom(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("wall_contact_bottom", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_objects' of type NX_UINT.
		final ILazyDataset number_of_objects = group.getLazyDataset("number_of_objects");
		validateFieldNotNull("number_of_objects", number_of_objects);
		if (number_of_objects != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_objects", number_of_objects, NX_UINT);
			validateFieldUnits("number_of_objects", group.getDataNode("number_of_objects"), NX_UNITLESS);
			validateFieldRank("number_of_objects", number_of_objects, 1);
			validateFieldDimensions("number_of_objects", number_of_objects, null, "n_ions");
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
	 * Validate optional group 'wall_contact_top' of type NXcs_filter_boolean_mask.
	 */
	private void validateGroup_NXentry_tessellationID_wall_contact_top(final NXcs_filter_boolean_mask group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("wall_contact_top", NXcs_filter_boolean_mask.class, group))) return;

		// validate field 'number_of_objects' of type NX_UINT.
		final ILazyDataset number_of_objects = group.getLazyDataset("number_of_objects");
		validateFieldNotNull("number_of_objects", number_of_objects);
		if (number_of_objects != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_objects", number_of_objects, NX_UINT);
			validateFieldUnits("number_of_objects", group.getDataNode("number_of_objects"), NX_UNITLESS);
			validateFieldRank("number_of_objects", number_of_objects, 1);
			validateFieldDimensions("number_of_objects", number_of_objects, null, "n_ions");
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
}
