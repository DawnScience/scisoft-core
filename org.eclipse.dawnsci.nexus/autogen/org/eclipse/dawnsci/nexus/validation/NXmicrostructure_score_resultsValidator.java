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
import org.eclipse.dawnsci.analysis.api.tree.Attribute;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXcs_profiling;
import org.eclipse.dawnsci.nexus.NXprogram;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXcoordinate_system;
import org.eclipse.dawnsci.nexus.NXmicrostructure;
import org.eclipse.dawnsci.nexus.NXcg_grid;
import org.eclipse.dawnsci.nexus.NXcg_hexahedron;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXmicrostructure_feature;

/**
 * Validator for the application definition 'NXmicrostructure_score_results'.
 */
public class NXmicrostructure_score_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXmicrostructure_score_resultsValidator() {
		super(NexusApplicationDefinition.NX_MICROSTRUCTURE_SCORE_RESULTS);
	}

	@Override
	public ValidationReport validate(NXroot root) {
		// validate unnamed child group of type NXentry (possibly multiple)
		validateUnnamedGroupOccurrences(root, NXentry.class, false, true);
		final Map<String, NXentry> allEntry = root.getAllEntry();
		for (final NXentry entry : allEntry.values()) {
			validateGroup_NXentry(entry);
		}
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
					"NXmicrostructure_score_results");
		}

		// validate field 'identifier_simulation' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset identifier_simulation = group.getLazyDataset("identifier_simulation");
		validateFieldNotNull("identifier_simulation", identifier_simulation);
		if (identifier_simulation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_simulation", identifier_simulation, NX_UINT);
		}

		// validate optional field 'description' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		validateFieldNotNull("start_time", start_time);
		if (start_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start_time", start_time, NX_DATE_TIME);
		}

		// validate optional field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
				if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
		}

		// validate child group 'config' of type NXnote
		validateGroup_NXentry_config(group.getChild("config", NXnote.class));

		// validate optional child group 'profiling' of type NXcs_profiling
		if (group.getChild("profiling", NXcs_profiling.class) != null) {
			validateGroup_NXentry_profiling(group.getChild("profiling", NXcs_profiling.class));
		}

		// validate child group 'program1' of type NXprogram
		validateGroup_NXentry_program1(group.getChild("program1", NXprogram.class));

		// validate optional child group 'environment' of type NXcollection
		if (group.getCollection("environment") != null) {
			validateGroup_NXentry_environment(group.getCollection("environment"));
		}

		// validate child group 'sample_reference_frame' of type NXcoordinate_system
		validateGroup_NXentry_sample_reference_frame(group.getChild("sample_reference_frame", NXcoordinate_system.class));

		// validate child group 'discretization' of type NXmicrostructure
		validateGroup_NXentry_discretization(group.getChild("discretization", NXmicrostructure.class));

		// validate child group 'spatiotemporalID' of type NXprocess
		validateGroup_NXentry_spatiotemporalID(group.getProcess("spatiotemporalID"));
	}

	/**
	 * Validate group 'config' of type NXnote.
	 */
	private void validateGroup_NXentry_config(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("config", NXnote.class, group))) return;

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

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
	}

	/**
	 * Validate optional group 'profiling' of type NXcs_profiling.
	 */
	private void validateGroup_NXentry_profiling(final NXcs_profiling group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("profiling", NXcs_profiling.class, group))) return;

	}

	/**
	 * Validate group 'program1' of type NXprogram.
	 */
	private void validateGroup_NXentry_program1(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("program1", NXprogram.class, group))) return;

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
	 * Validate optional group 'environment' of type NXcollection.
	 */
	private void validateGroup_NXentry_environment(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("environment", NXcollection.class, group))) return;

		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_environment_programID(group.getChild("programID", NXprogram.class));
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_environment_programID(final NXprogram group) {
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
	 * Validate group 'sample_reference_frame' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_sample_reference_frame(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_reference_frame", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"cartesian");
		}

		// validate field 'handedness' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset handedness = group.getLazyDataset("handedness");
		validateFieldNotNull("handedness", handedness);
		if (handedness != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("handedness", handedness, NX_CHAR);
			validateFieldEnumeration("handedness", handedness,
					"right_handed");
		}

		// validate field 'origin' of type NX_CHAR.
		final ILazyDataset origin = group.getLazyDataset("origin");
		validateFieldNotNull("origin", origin);
		if (origin != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("origin", origin, NX_CHAR);
			validateFieldEnumeration("origin", origin,
					"front_bottom_left");
		}

		// validate field 'x_alias' of type NX_CHAR.
		final ILazyDataset x_alias = group.getLazyDataset("x_alias");
		validateFieldNotNull("x_alias", x_alias);
		if (x_alias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_alias", x_alias, NX_CHAR);
			validateFieldEnumeration("x_alias", x_alias,
					"rolling_direction");
		}

		// validate field 'x_direction' of type NX_CHAR.
		final ILazyDataset x_direction = group.getLazyDataset("x_direction");
		validateFieldNotNull("x_direction", x_direction);
		if (x_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_direction", x_direction, NX_CHAR);
			validateFieldEnumeration("x_direction", x_direction,
					"east");
		}

		// validate field 'y_alias' of type NX_CHAR.
		final ILazyDataset y_alias = group.getLazyDataset("y_alias");
		validateFieldNotNull("y_alias", y_alias);
		if (y_alias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_alias", y_alias, NX_CHAR);
			validateFieldEnumeration("y_alias", y_alias,
					"transverse_direction");
		}

		// validate field 'y_direction' of type NX_CHAR.
		final ILazyDataset y_direction = group.getLazyDataset("y_direction");
		validateFieldNotNull("y_direction", y_direction);
		if (y_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_direction", y_direction, NX_CHAR);
			validateFieldEnumeration("y_direction", y_direction,
					"in");
		}

		// validate field 'z_alias' of type NX_CHAR.
		final ILazyDataset z_alias = group.getLazyDataset("z_alias");
		validateFieldNotNull("z_alias", z_alias);
		if (z_alias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z_alias", z_alias, NX_CHAR);
			validateFieldEnumeration("z_alias", z_alias,
					"normal direction");
		}

		// validate field 'z_direction' of type NX_CHAR.
		final ILazyDataset z_direction = group.getLazyDataset("z_direction");
		validateFieldNotNull("z_direction", z_direction);
		if (z_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z_direction", z_direction, NX_CHAR);
			validateFieldEnumeration("z_direction", z_direction,
					"north");
		}
	}

	/**
	 * Validate group 'discretization' of type NXmicrostructure.
	 */
	private void validateGroup_NXentry_discretization(final NXmicrostructure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("discretization", NXmicrostructure.class, group))) return;

		// validate child group 'grid' of type NXcg_grid
		validateGroup_NXentry_discretization_grid(group.getChild("grid", NXcg_grid.class));

		// validate child group 'boundary' of type NXcg_hexahedron
		validateGroup_NXentry_discretization_boundary(group.getChild("boundary", NXcg_hexahedron.class));
	}

	/**
	 * Validate group 'grid' of type NXcg_grid.
	 */
	private void validateGroup_NXentry_discretization_grid(final NXcg_grid group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("grid", NXcg_grid.class, group))) return;
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

		// validate field 'origin' of type NX_NUMBER.
		final ILazyDataset origin = group.getLazyDataset("origin");
		validateFieldNotNull("origin", origin);
		if (origin != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("origin", origin, NX_NUMBER);
			validateFieldUnits("origin", group.getDataNode("origin"), NX_ANY);
			validateFieldRank("origin", origin, 1);
			validateFieldDimensions("origin", origin, "NXcg_grid", "d");
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
			validateFieldDimensions("cell_dimensions", cell_dimensions, "NXcg_grid", "d");
		}

		// validate field 'extent' of type NX_UINT.
		final ILazyDataset extent = group.getLazyDataset("extent");
		validateFieldNotNull("extent", extent);
		if (extent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extent", extent, NX_UINT);
			validateFieldUnits("extent", group.getDataNode("extent"), NX_UNITLESS);
			validateFieldRank("extent", extent, 1);
			validateFieldDimensions("extent", extent, "NXcg_grid", "d");
		}

		// validate field 'index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset = group.getLazyDataset("index_offset");
		validateFieldNotNull("index_offset", index_offset);
		if (index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset", index_offset, NX_INT);
		}
	}

	/**
	 * Validate group 'boundary' of type NXcg_hexahedron.
	 */
	private void validateGroup_NXentry_discretization_boundary(final NXcg_hexahedron group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("boundary", NXcg_hexahedron.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'number_of_boundaries' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_boundaries = group.getLazyDataset("number_of_boundaries");
		validateFieldNotNull("number_of_boundaries", number_of_boundaries);
		if (number_of_boundaries != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_boundaries", number_of_boundaries, NX_UINT);
			validateFieldUnits("number_of_boundaries", group.getDataNode("number_of_boundaries"), NX_UNITLESS);
		}

		// validate field 'boundary_conditions' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset boundary_conditions = group.getLazyDataset("boundary_conditions");
		validateFieldNotNull("boundary_conditions", boundary_conditions);
		if (boundary_conditions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("boundary_conditions", boundary_conditions, NX_INT);
			validateFieldUnits("boundary_conditions", group.getDataNode("boundary_conditions"), NX_UNITLESS);
			validateFieldRank("boundary_conditions", boundary_conditions, 1);
			validateFieldDimensions("boundary_conditions", boundary_conditions, null, 6);
		}

		// validate field 'boundaries' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset boundaries = group.getLazyDataset("boundaries");
		validateFieldNotNull("boundaries", boundaries);
		if (boundaries != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("boundaries", boundaries, NX_CHAR);
			validateFieldRank("boundaries", boundaries, 1);
			validateFieldDimensions("boundaries", boundaries, null, 6);
		}
	}

	/**
	 * Validate group 'spatiotemporalID' of type NXprocess.
	 */
	private void validateGroup_NXentry_spatiotemporalID(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spatiotemporalID", NXprocess.class, group))) return;

		// validate child group 'summary_statistics' of type NXprocess
		validateGroup_NXentry_spatiotemporalID_summary_statistics(group.getChild("summary_statistics", NXprocess.class));

		// validate child group 'microstructureID' of type NXmicrostructure
		validateGroup_NXentry_spatiotemporalID_microstructureID(group.getChild("microstructureID", NXmicrostructure.class));
	}

	/**
	 * Validate group 'summary_statistics' of type NXprocess.
	 */
	private void validateGroup_NXentry_spatiotemporalID_summary_statistics(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("summary_statistics", NXprocess.class, group))) return;

		// validate optional child group 'kinetics' of type NXdata
		if (group.getData("kinetics") != null) {
			validateGroup_NXentry_spatiotemporalID_summary_statistics_kinetics(group.getData("kinetics"));
		}

		// validate optional child group 'stress' of type NXdata
		if (group.getData("stress") != null) {
			validateGroup_NXentry_spatiotemporalID_summary_statistics_stress(group.getData("stress"));
		}

		// validate optional child group 'strain' of type NXdata
		if (group.getData("strain") != null) {
			validateGroup_NXentry_spatiotemporalID_summary_statistics_strain(group.getData("strain"));
		}

		// validate optional child group 'deformation_gradient' of type NXprocess
		if (group.getChild("deformation_gradient", NXprocess.class) != null) {
			validateGroup_NXentry_spatiotemporalID_summary_statistics_deformation_gradient(group.getChild("deformation_gradient", NXprocess.class));
		}
	}

	/**
	 * Validate optional group 'kinetics' of type NXdata.
	 */
	private void validateGroup_NXentry_spatiotemporalID_summary_statistics_kinetics(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("kinetics", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate attribute 'time_indices' of type NX_UINT.
		final Attribute time_indices_attr = group.getAttribute("time_indices");
		if (!(validateAttributeNotNull("time_indices", time_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("time_indices", time_indices_attr, NX_UINT);

		// validate optional attribute 'iteration_indices' of type NX_UINT.
		final Attribute iteration_indices_attr = group.getAttribute("iteration_indices");
		if (iteration_indices_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("iteration_indices", iteration_indices_attr, NX_UINT);
		}

		// validate optional attribute 'temperature_indices' of type NX_UINT.
		final Attribute temperature_indices_attr = group.getAttribute("temperature_indices");
		if (temperature_indices_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("temperature_indices", temperature_indices_attr, NX_UINT);
		}

		// validate attribute 'x_indices' of type NX_UINT.
		final Attribute x_indices_attr = group.getAttribute("x_indices");
		if (!(validateAttributeNotNull("x_indices", x_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("x_indices", x_indices_attr, NX_UINT);

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset time = group.getLazyDataset("time");
		validateFieldNotNull("time", time);
		if (time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time", time, NX_FLOAT);
			validateFieldUnits("time", group.getDataNode("time"), NX_TIME);
			validateFieldRank("time", time, 1);
			validateFieldDimensions("time", time, null, "n_summary_stats");
		}

		// validate field 'iteration' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset iteration = group.getLazyDataset("iteration");
		validateFieldNotNull("iteration", iteration);
		if (iteration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("iteration", iteration, NX_INT);
			validateFieldUnits("iteration", group.getDataNode("iteration"), NX_UNITLESS);
			validateFieldRank("iteration", iteration, 1);
			validateFieldDimensions("iteration", iteration, null, "n_summary_stats");
		}

		// validate field 'temperature' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
		validateFieldNotNull("temperature", temperature);
		if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_FLOAT);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldRank("temperature", temperature, 1);
			validateFieldDimensions("temperature", temperature, null, "n_summary_stats");
		}

		// validate field 'x' of type NX_FLOAT.
		final ILazyDataset x = group.getLazyDataset("x");
		validateFieldNotNull("x", x);
		if (x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x", x, NX_FLOAT);
			validateFieldUnits("x", group.getDataNode("x"), NX_DIMENSIONLESS);
			validateFieldRank("x", x, 1);
			validateFieldDimensions("x", x, null, "n_summary_stats");
		}
	}

	/**
	 * Validate optional group 'stress' of type NXdata.
	 */
	private void validateGroup_NXentry_spatiotemporalID_summary_statistics_stress(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stress", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"cauchy");
		}

		// validate field 'tensor' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset tensor = group.getLazyDataset("tensor");
		validateFieldNotNull("tensor", tensor);
		if (tensor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("tensor", tensor, NX_FLOAT);
			validateFieldUnits("tensor", group.getDataNode("tensor"), NX_ANY);
			validateFieldRank("tensor", tensor, 3);
			validateFieldDimensions("tensor", tensor, null, "n_summary_stats", 3, 3);
		}
	}

	/**
	 * Validate optional group 'strain' of type NXdata.
	 */
	private void validateGroup_NXentry_spatiotemporalID_summary_statistics_strain(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("strain", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'tensor' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset tensor = group.getLazyDataset("tensor");
		validateFieldNotNull("tensor", tensor);
		if (tensor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("tensor", tensor, NX_FLOAT);
			validateFieldUnits("tensor", group.getDataNode("tensor"), NX_ANY);
			validateFieldRank("tensor", tensor, 3);
			validateFieldDimensions("tensor", tensor, null, "n_summary_stats", 3, 3);
		}
	}

	/**
	 * Validate optional group 'deformation_gradient' of type NXprocess.
	 */
	private void validateGroup_NXentry_spatiotemporalID_summary_statistics_deformation_gradient(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("deformation_gradient", NXprocess.class, group))) return;

		// validate field 'type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"piola");
		}

		// validate field 'tensor' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset tensor = group.getLazyDataset("tensor");
		validateFieldNotNull("tensor", tensor);
		if (tensor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("tensor", tensor, NX_FLOAT);
			validateFieldUnits("tensor", group.getDataNode("tensor"), NX_ANY);
			validateFieldRank("tensor", tensor, 3);
			validateFieldDimensions("tensor", tensor, null, "n_summary_stats", 3, 3);
		}
	}

	/**
	 * Validate group 'microstructureID' of type NXmicrostructure.
	 */
	private void validateGroup_NXentry_spatiotemporalID_microstructureID(final NXmicrostructure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("microstructureID", NXmicrostructure.class, group))) return;

		// validate field 'time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset time = group.getLazyDataset("time");
		validateFieldNotNull("time", time);
		if (time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time", time, NX_FLOAT);
			validateFieldUnits("time", group.getDataNode("time"), NX_TIME);
		}

		// validate field 'iteration' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset iteration = group.getLazyDataset("iteration");
		validateFieldNotNull("iteration", iteration);
		if (iteration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("iteration", iteration, NX_UINT);
			validateFieldUnits("iteration", group.getDataNode("iteration"), NX_UNITLESS);
		}

		// validate field 'temperature' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
		validateFieldNotNull("temperature", temperature);
		if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_FLOAT);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
		}

		// validate field 'x' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset x = group.getLazyDataset("x");
		validateFieldNotNull("x", x);
		if (x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x", x, NX_FLOAT);
			validateFieldUnits("x", group.getDataNode("x"), NX_DIMENSIONLESS);
		}

		// validate field 'x_set' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset x_set = group.getLazyDataset("x_set");
		validateFieldNotNull("x_set", x_set);
		if (x_set != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_set", x_set, NX_FLOAT);
			validateFieldUnits("x_set", group.getDataNode("x_set"), NX_DIMENSIONLESS);
		}

		// validate optional child group 'grid' of type NXcg_grid
		if (group.getChild("grid", NXcg_grid.class) != null) {
			validateGroup_NXentry_spatiotemporalID_microstructureID_grid(group.getChild("grid", NXcg_grid.class));
		}

		// validate child group 'crystals' of type NXmicrostructure_feature
		validateGroup_NXentry_spatiotemporalID_microstructureID_crystals(group.getChild("crystals", NXmicrostructure_feature.class));

		// validate optional child group 'recrystallization_front' of type NXmicrostructure_feature
		if (group.getChild("recrystallization_front", NXmicrostructure_feature.class) != null) {
			validateGroup_NXentry_spatiotemporalID_microstructureID_recrystallization_front(group.getChild("recrystallization_front", NXmicrostructure_feature.class));
		}
	}

	/**
	 * Validate optional group 'grid' of type NXcg_grid.
	 */
	private void validateGroup_NXentry_spatiotemporalID_microstructureID_grid(final NXcg_grid group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("grid", NXcg_grid.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'indices_crystal' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_crystal = group.getLazyDataset("indices_crystal");
				if (indices_crystal != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_crystal", indices_crystal, NX_INT);
			validateFieldUnits("indices_crystal", group.getDataNode("indices_crystal"), NX_UNITLESS);
			validateFieldRank("indices_crystal", indices_crystal, 3);
			validateFieldDimensions("indices_crystal", indices_crystal, null, "n_x", "n_y", "n_z");
		}

		// validate optional field 'thread_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset thread_id = group.getLazyDataset("thread_id");
				if (thread_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("thread_id", thread_id, NX_UINT);
			validateFieldUnits("thread_id", group.getDataNode("thread_id"), NX_UNITLESS);
			validateFieldRank("thread_id", thread_id, 3);
			validateFieldDimensions("thread_id", thread_id, null, "n_x", "n_y", "n_z");
		}
	}

	/**
	 * Validate group 'crystals' of type NXmicrostructure_feature.
	 */
	private void validateGroup_NXentry_spatiotemporalID_microstructureID_crystals(final NXmicrostructure_feature group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("crystals", NXmicrostructure_feature.class, group))) return;

		// validate optional field 'representation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset representation = group.getLazyDataset("representation");
				if (representation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("representation", representation, NX_CHAR);
		}

		// validate optional field 'number_of_crystals' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_crystals = group.getLazyDataset("number_of_crystals");
				if (number_of_crystals != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_crystals", number_of_crystals, NX_UINT);
		}

		// validate optional field 'number_of_phases' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_phases = group.getLazyDataset("number_of_phases");
				if (number_of_phases != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_phases", number_of_phases, NX_UINT);
		}

		// validate optional field 'index_offset_crystal' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset_crystal = group.getLazyDataset("index_offset_crystal");
				if (index_offset_crystal != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset_crystal", index_offset_crystal, NX_INT);
		}

		// validate optional field 'indices_crystal' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_crystal = group.getLazyDataset("indices_crystal");
				if (indices_crystal != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_crystal", indices_crystal, NX_INT);
			validateFieldRank("indices_crystal", indices_crystal, 1);
			validateFieldDimensions("indices_crystal", indices_crystal, null, "n_grains");
		}

		// validate optional field 'index_offset_phase' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset_phase = group.getLazyDataset("index_offset_phase");
				if (index_offset_phase != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset_phase", index_offset_phase, NX_INT);
		}

		// validate optional field 'indices_phase' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_phase = group.getLazyDataset("indices_phase");
				if (indices_phase != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_phase", indices_phase, NX_INT);
			validateFieldRank("indices_phase", indices_phase, 1);
			validateFieldDimensions("indices_phase", indices_phase, null, "n_grains");
		}

		// validate field 'volume' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset volume = group.getLazyDataset("volume");
		validateFieldNotNull("volume", volume);
		if (volume != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("volume", volume, NX_FLOAT);
			validateFieldUnits("volume", group.getDataNode("volume"), NX_VOLUME);
			validateFieldRank("volume", volume, 1);
			validateFieldDimensions("volume", volume, null, "n_grains");
		}

		// validate field 'bunge_euler' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset bunge_euler = group.getLazyDataset("bunge_euler");
		validateFieldNotNull("bunge_euler", bunge_euler);
		if (bunge_euler != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("bunge_euler", bunge_euler, NX_FLOAT);
			validateFieldUnits("bunge_euler", group.getDataNode("bunge_euler"), NX_ANGLE);
			validateFieldRank("bunge_euler", bunge_euler, 2);
			validateFieldDimensions("bunge_euler", bunge_euler, null, "n_grains", 3);
		}

		// validate optional field 'dislocation_density' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset dislocation_density = group.getLazyDataset("dislocation_density");
				if (dislocation_density != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dislocation_density", dislocation_density, NX_FLOAT);
			validateFieldUnits("dislocation_density", group.getDataNode("dislocation_density"), NX_ANY);
			validateFieldRank("dislocation_density", dislocation_density, 1);
			validateFieldDimensions("dislocation_density", dislocation_density, null, "n_grains");
		}

		// validate optional field 'is_deformed' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_deformed = group.getLazyDataset("is_deformed");
				if (is_deformed != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_deformed", is_deformed, NX_BOOLEAN);
			validateFieldRank("is_deformed", is_deformed, 1);
			validateFieldDimensions("is_deformed", is_deformed, null, "n_grains");
		}

		// validate optional field 'is_recrystallized' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_recrystallized = group.getLazyDataset("is_recrystallized");
				if (is_recrystallized != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_recrystallized", is_recrystallized, NX_BOOLEAN);
			validateFieldRank("is_recrystallized", is_recrystallized, 1);
			validateFieldDimensions("is_recrystallized", is_recrystallized, null, "n_grains");
		}
	}

	/**
	 * Validate optional group 'recrystallization_front' of type NXmicrostructure_feature.
	 */
	private void validateGroup_NXentry_spatiotemporalID_microstructureID_recrystallization_front(final NXmicrostructure_feature group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("recrystallization_front", NXmicrostructure_feature.class, group))) return;

		// validate optional field 'halo_region' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset halo_region = group.getLazyDataset("halo_region");
				if (halo_region != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("halo_region", halo_region, NX_UINT);
			validateFieldUnits("halo_region", group.getDataNode("halo_region"), NX_UNITLESS);
			validateFieldRank("halo_region", halo_region, 1);
			validateFieldDimensions("halo_region", halo_region, null, "n_front");
		}

		// validate optional field 'mobility_weight' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset mobility_weight = group.getLazyDataset("mobility_weight");
				if (mobility_weight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mobility_weight", mobility_weight, NX_FLOAT);
			validateFieldUnits("mobility_weight", group.getDataNode("mobility_weight"), NX_UNITLESS);
			validateFieldRank("mobility_weight", mobility_weight, 1);
			validateFieldDimensions("mobility_weight", mobility_weight, null, "n_front");
		}

		// validate optional field 'coordinate' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset coordinate = group.getLazyDataset("coordinate");
				if (coordinate != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("coordinate", coordinate, NX_NUMBER);
			validateFieldUnits("coordinate", group.getDataNode("coordinate"), NX_UNITLESS);
			validateFieldRank("coordinate", coordinate, 2);
			validateFieldDimensions("coordinate", coordinate, null, "n_front", 3);
		}

		// validate optional field 'deformed_grain_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset deformed_grain_id = group.getLazyDataset("deformed_grain_id");
				if (deformed_grain_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("deformed_grain_id", deformed_grain_id, NX_UINT);
			validateFieldUnits("deformed_grain_id", group.getDataNode("deformed_grain_id"), NX_UNITLESS);
			validateFieldRank("deformed_grain_id", deformed_grain_id, 1);
			validateFieldDimensions("deformed_grain_id", deformed_grain_id, null, "n_front");
		}

		// validate optional field 'recrystallized_grain_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset recrystallized_grain_id = group.getLazyDataset("recrystallized_grain_id");
				if (recrystallized_grain_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("recrystallized_grain_id", recrystallized_grain_id, NX_UINT);
			validateFieldUnits("recrystallized_grain_id", group.getDataNode("recrystallized_grain_id"), NX_UNITLESS);
			validateFieldRank("recrystallized_grain_id", recrystallized_grain_id, 1);
			validateFieldDimensions("recrystallized_grain_id", recrystallized_grain_id, null, "n_front");
		}

		// validate optional field 'thread_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset thread_id = group.getLazyDataset("thread_id");
				if (thread_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("thread_id", thread_id, NX_UINT);
			validateFieldUnits("thread_id", group.getDataNode("thread_id"), NX_UNITLESS);
			validateFieldRank("thread_id", thread_id, 1);
			validateFieldDimensions("thread_id", thread_id, null, "n_front");
		}

		// validate optional field 'infection_direction' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset infection_direction = group.getLazyDataset("infection_direction");
				if (infection_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("infection_direction", infection_direction, NX_UINT);
			validateFieldUnits("infection_direction", group.getDataNode("infection_direction"), NX_UNITLESS);
			validateFieldRank("infection_direction", infection_direction, 1);
			validateFieldDimensions("infection_direction", infection_direction, null, "n_front");
		}
	}
}
