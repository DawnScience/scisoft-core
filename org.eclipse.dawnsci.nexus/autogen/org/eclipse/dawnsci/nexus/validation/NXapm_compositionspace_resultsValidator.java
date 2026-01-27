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
import org.eclipse.dawnsci.nexus.NXcs_profiling;
import org.eclipse.dawnsci.nexus.NXprogram;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXcg_grid;
import org.eclipse.dawnsci.nexus.NXatom;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXapm_compositionspace_results'.
 */
public class NXapm_compositionspace_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_compositionspace_resultsValidator() {
		super(NexusApplicationDefinition.NX_APM_COMPOSITIONSPACE_RESULTS);
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
					"NXapm_compositionspace_results");
		// validate optional attribute 'version' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (definition_attr_version != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("version", definition_attr_version, NX_CHAR);
		}

		}

		// validate optional field 'identifier_analysis' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset identifier_analysis = group.getLazyDataset("identifier_analysis");
				if (identifier_analysis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_analysis", identifier_analysis, NX_UINT);
		}

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

		// validate child group 'config' of type NXnote
		validateGroup_NXentry_config(group.getChild("config", NXnote.class));

		// validate optional child group 'userID' of type NXuser
		if (group.getUser("userID") != null) {
			validateGroup_NXentry_userID(group.getUser("userID"));
		}

		// validate optional child group 'specimen' of type NXsample
		if (group.getSample("specimen") != null) {
			validateGroup_NXentry_specimen(group.getSample("specimen"));
		}

		// validate optional child group 'voxelization' of type NXprocess
		if (group.getProcess("voxelization") != null) {
			validateGroup_NXentry_voxelization(group.getProcess("voxelization"));
		}

		// validate optional child group 'autophase' of type NXprocess
		if (group.getProcess("autophase") != null) {
			validateGroup_NXentry_autophase(group.getProcess("autophase"));
		}

		// validate optional child group 'segmentation' of type NXprocess
		if (group.getProcess("segmentation") != null) {
			validateGroup_NXentry_segmentation(group.getProcess("segmentation"));
		}

		// validate optional child group 'clustering' of type NXprocess
		if (group.getProcess("clustering") != null) {
			validateGroup_NXentry_clustering(group.getProcess("clustering"));
		}
	}

	/**
	 * Validate optional group 'profiling' of type NXcs_profiling.
	 */
	private void validateGroup_NXentry_profiling(final NXcs_profiling group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("profiling", NXcs_profiling.class, group))) return;

		// validate optional field 'current_working_directory' of type NX_CHAR.
		final ILazyDataset current_working_directory = group.getLazyDataset("current_working_directory");
				if (current_working_directory != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("current_working_directory", current_working_directory, NX_CHAR);
		}

		// validate optional field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
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

		// validate field 'total_elapsed_time' of type NX_NUMBER.
		final ILazyDataset total_elapsed_time = group.getLazyDataset("total_elapsed_time");
		validateFieldNotNull("total_elapsed_time", total_elapsed_time);
		if (total_elapsed_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_elapsed_time", total_elapsed_time, NX_NUMBER);
			validateFieldUnits("total_elapsed_time", group.getDataNode("total_elapsed_time"), NX_TIME);
		}
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

		// validate unnamed child group of type NXprogram (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXprogram.class, false, true);
		final Map<String, NXprogram> allProgram = group.getChildren(NXprogram.class);
		for (final NXprogram program : allProgram.values()) {
			validateGroup_NXentry_environment_NXprogram(program);
		}
	}

	/**
	 * Validate unnamed group of type NXprogram.
	 */
	private void validateGroup_NXentry_environment_NXprogram(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprogram.class, group))) return;

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

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'userID' of type NXuser.
	 */
	private void validateGroup_NXentry_userID(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("userID", NXuser.class, group))) return;

	}

	/**
	 * Validate optional group 'specimen' of type NXsample.
	 */
	private void validateGroup_NXentry_specimen(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("specimen", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'is_simulation' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_simulation = group.getLazyDataset("is_simulation");
		validateFieldNotNull("is_simulation", is_simulation);
		if (is_simulation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_simulation", is_simulation, NX_BOOLEAN);
		}

		// validate field 'atom_types' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset atom_types = group.getLazyDataset("atom_types");
		validateFieldNotNull("atom_types", atom_types);
		if (atom_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("atom_types", atom_types, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'voxelization' of type NXprocess.
	 */
	private void validateGroup_NXentry_voxelization(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("voxelization", NXprocess.class, group))) return;

		// validate field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
		validateFieldNotNull("sequence_index", sequence_index);
		if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
			validateFieldEnumeration("sequence_index", sequence_index,
					"1");
		}

		// validate field 'weight' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset weight = group.getLazyDataset("weight");
		validateFieldNotNull("weight", weight);
		if (weight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("weight", weight, NX_NUMBER);
			validateFieldUnits("weight", group.getDataNode("weight"), NX_UNITLESS);
			validateFieldDimensions("weight", weight, null, "n_voxels");
		}

		// validate child group 'grid' of type NXcg_grid
		validateGroup_NXentry_voxelization_grid(group.getChild("grid", NXcg_grid.class));

		// validate child group 'ionID' of type NXatom
		validateGroup_NXentry_voxelization_ionID(group.getChild("ionID", NXatom.class));
	}

	/**
	 * Validate group 'grid' of type NXcg_grid.
	 */
	private void validateGroup_NXentry_voxelization_grid(final NXcg_grid group) {
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
			validateFieldDimensions("origin", origin, null, "grid_dim");
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
			validateFieldDimensions("cell_dimensions", cell_dimensions, null, "grid_dim");
		}

		// validate field 'extent' of type NX_POSINT.
		final ILazyDataset extent = group.getLazyDataset("extent");
		validateFieldNotNull("extent", extent);
		if (extent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extent", extent, NX_POSINT);
			validateFieldUnits("extent", group.getDataNode("extent"), NX_UNITLESS);
			validateFieldRank("extent", extent, 1);
			validateFieldDimensions("extent", extent, null, "grid_dim");
		}

		// validate field 'index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset = group.getLazyDataset("index_offset");
		validateFieldNotNull("index_offset", index_offset);
		if (index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset", index_offset, NX_INT);
			validateFieldUnits("index_offset", group.getDataNode("index_offset"), NX_UNITLESS);
		}

		// validate field 'position' of type NX_NUMBER.
		final ILazyDataset position = group.getLazyDataset("position");
		validateFieldNotNull("position", position);
		if (position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("position", position, NX_NUMBER);
			validateFieldUnits("position", group.getDataNode("position"), NX_LENGTH);
			validateFieldRank("position", position, 2);
			validateFieldDimensions("position", position, null, "n_voxels", "grid_dim");
		}

		// validate field 'coordinate' of type NX_INT.
		final ILazyDataset coordinate = group.getLazyDataset("coordinate");
		validateFieldNotNull("coordinate", coordinate);
		if (coordinate != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("coordinate", coordinate, NX_INT);
			validateFieldUnits("coordinate", group.getDataNode("coordinate"), NX_DIMENSIONLESS);
			validateFieldRank("coordinate", coordinate, 2);
			validateFieldDimensions("coordinate", coordinate, null, "n_voxels", "grid_dim");
		}

		// validate field 'indices_voxel' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_voxel = group.getLazyDataset("indices_voxel");
		validateFieldNotNull("indices_voxel", indices_voxel);
		if (indices_voxel != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_voxel", indices_voxel, NX_INT);
			validateFieldUnits("indices_voxel", group.getDataNode("indices_voxel"), NX_UNITLESS);
			validateFieldDimensions("indices_voxel", indices_voxel, null, "n_ions");
		}
	}

	/**
	 * Validate group 'ionID' of type NXatom.
	 */
	private void validateGroup_NXentry_voxelization_ionID(final NXatom group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ionID", NXatom.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'weight' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset weight = group.getLazyDataset("weight");
		validateFieldNotNull("weight", weight);
		if (weight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("weight", weight, NX_NUMBER);
			validateFieldUnits("weight", group.getDataNode("weight"), NX_UNITLESS);
			validateFieldDimensions("weight", weight, null, "n_voxels");
		}
	}

	/**
	 * Validate optional group 'autophase' of type NXprocess.
	 */
	private void validateGroup_NXentry_autophase(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("autophase", NXprocess.class, group))) return;

		// validate field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
		validateFieldNotNull("sequence_index", sequence_index);
		if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
			validateFieldEnumeration("sequence_index", sequence_index,
					"2");
		}

		// validate child group 'result' of type NXdata
		validateGroup_NXentry_autophase_result(group.getData("result"));
	}

	/**
	 * Validate group 'result' of type NXdata.
	 */
	private void validateGroup_NXentry_autophase_result(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("result", NXdata.class, group))) return;
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

		// validate attribute 'AXISNAME_indices' of type NX_UINT.
		final Attribute AXISNAME_indices_attr = group.getAttribute("AXISNAME_indices");
		if (!(validateAttributeNotNull("AXISNAME_indices", AXISNAME_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("AXISNAME_indices", AXISNAME_indices_attr, NX_UINT);

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'axis_feature_indices' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset axis_feature_indices = group.getLazyDataset("axis_feature_indices");
		validateFieldNotNull("axis_feature_indices", axis_feature_indices);
		if (axis_feature_indices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_feature_indices", axis_feature_indices, NX_UINT);
			validateFieldUnits("axis_feature_indices", group.getDataNode("axis_feature_indices"), NX_DIMENSIONLESS);
			validateFieldDimensions("axis_feature_indices", axis_feature_indices, null, "i");
		// validate attribute 'long_name' of field 'axis_feature_indices' of type NX_CHAR.
		final Attribute axis_feature_indices_attr_long_name = group.getDataNode("axis_feature_indices").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_feature_indices_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_feature_indices_attr_long_name, NX_CHAR);

		}

		// validate field 'axis_feature_importance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset axis_feature_importance = group.getLazyDataset("axis_feature_importance");
		validateFieldNotNull("axis_feature_importance", axis_feature_importance);
		if (axis_feature_importance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_feature_importance", axis_feature_importance, NX_FLOAT);
			validateFieldUnits("axis_feature_importance", group.getDataNode("axis_feature_importance"), NX_DIMENSIONLESS);
			validateFieldDimensions("axis_feature_importance", axis_feature_importance, null, "i");
		// validate attribute 'long_name' of field 'axis_feature_importance' of type NX_CHAR.
		final Attribute axis_feature_importance_attr_long_name = group.getDataNode("axis_feature_importance").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", axis_feature_importance_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", axis_feature_importance_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'segmentation' of type NXprocess.
	 */
	private void validateGroup_NXentry_segmentation(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("segmentation", NXprocess.class, group))) return;

		// validate child group 'pca' of type NXprocess
		validateGroup_NXentry_segmentation_pca(group.getChild("pca", NXprocess.class));

		// validate child group 'ic_opt' of type NXprocess
		validateGroup_NXentry_segmentation_ic_opt(group.getChild("ic_opt", NXprocess.class));
	}

	/**
	 * Validate group 'pca' of type NXprocess.
	 */
	private void validateGroup_NXentry_segmentation_pca(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pca", NXprocess.class, group))) return;

		// validate field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
		validateFieldNotNull("sequence_index", sequence_index);
		if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
			validateFieldEnumeration("sequence_index", sequence_index,
					"2",
					"3");
		}

		// validate child group 'result' of type NXdata
		validateGroup_NXentry_segmentation_pca_result(group.getData("result"));
	}

	/**
	 * Validate group 'result' of type NXdata.
	 */
	private void validateGroup_NXentry_segmentation_pca_result(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("result", NXdata.class, group))) return;
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

		// validate attribute 'AXISNAME_indices' of type NX_UINT.
		final Attribute AXISNAME_indices_attr = group.getAttribute("AXISNAME_indices");
		if (!(validateAttributeNotNull("AXISNAME_indices", AXISNAME_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("AXISNAME_indices", AXISNAME_indices_attr, NX_UINT);

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'axis_explained_variance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset axis_explained_variance = group.getLazyDataset("axis_explained_variance");
		validateFieldNotNull("axis_explained_variance", axis_explained_variance);
		if (axis_explained_variance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_explained_variance", axis_explained_variance, NX_FLOAT);
			validateFieldUnits("axis_explained_variance", group.getDataNode("axis_explained_variance"), NX_DIMENSIONLESS);
			validateFieldDimensions("axis_explained_variance", axis_explained_variance, null, "i");
		}

		// validate field 'axis_pca_dimension' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset axis_pca_dimension = group.getLazyDataset("axis_pca_dimension");
		validateFieldNotNull("axis_pca_dimension", axis_pca_dimension);
		if (axis_pca_dimension != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_pca_dimension", axis_pca_dimension, NX_UINT);
			validateFieldUnits("axis_pca_dimension", group.getDataNode("axis_pca_dimension"), NX_UNITLESS);
			validateFieldDimensions("axis_pca_dimension", axis_pca_dimension, null, "i");
		}
	}

	/**
	 * Validate group 'ic_opt' of type NXprocess.
	 */
	private void validateGroup_NXentry_segmentation_ic_opt(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ic_opt", NXprocess.class, group))) return;

		// validate field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
		validateFieldNotNull("sequence_index", sequence_index);
		if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
			validateFieldEnumeration("sequence_index", sequence_index,
					"3",
					"4");
		}

		// validate child group 'cluster_analysisID' of type NXprocess
		validateGroup_NXentry_segmentation_ic_opt_cluster_analysisID(group.getChild("cluster_analysisID", NXprocess.class));

		// validate child group 'result' of type NXdata
		validateGroup_NXentry_segmentation_ic_opt_result(group.getData("result"));
	}

	/**
	 * Validate group 'cluster_analysisID' of type NXprocess.
	 */
	private void validateGroup_NXentry_segmentation_ic_opt_cluster_analysisID(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cluster_analysisID", NXprocess.class, group))) return;

		// validate field 'n_ic_cluster' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset n_ic_cluster = group.getLazyDataset("n_ic_cluster");
		validateFieldNotNull("n_ic_cluster", n_ic_cluster);
		if (n_ic_cluster != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("n_ic_cluster", n_ic_cluster, NX_UINT);
			validateFieldUnits("n_ic_cluster", group.getDataNode("n_ic_cluster"), NX_UNITLESS);
		}

		// validate field 'y_pred' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset y_pred = group.getLazyDataset("y_pred");
		validateFieldNotNull("y_pred", y_pred);
		if (y_pred != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_pred", y_pred, NX_UINT);
			validateFieldUnits("y_pred", group.getDataNode("y_pred"), NX_UNITLESS);
			validateFieldDimensions("y_pred", y_pred, null, "n_voxels");
		}
	}

	/**
	 * Validate group 'result' of type NXdata.
	 */
	private void validateGroup_NXentry_segmentation_ic_opt_result(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("result", NXdata.class, group))) return;
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

		// validate attribute 'AXISNAME_indices' of type NX_UINT.
		final Attribute AXISNAME_indices_attr = group.getAttribute("AXISNAME_indices");
		if (!(validateAttributeNotNull("AXISNAME_indices", AXISNAME_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("AXISNAME_indices", AXISNAME_indices_attr, NX_UINT);

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate optional field 'axis_aic' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset axis_aic = group.getLazyDataset("axis_aic");
				if (axis_aic != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_aic", axis_aic, NX_FLOAT);
			validateFieldUnits("axis_aic", group.getDataNode("axis_aic"), NX_ANY);
			validateFieldDimensions("axis_aic", axis_aic, null, "i");
		}

		// validate field 'axis_bic' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset axis_bic = group.getLazyDataset("axis_bic");
		validateFieldNotNull("axis_bic", axis_bic);
		if (axis_bic != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_bic", axis_bic, NX_FLOAT);
			validateFieldUnits("axis_bic", group.getDataNode("axis_bic"), NX_UNITLESS);
			validateFieldDimensions("axis_bic", axis_bic, null, "i");
		}

		// validate field 'axis_dimension' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset axis_dimension = group.getLazyDataset("axis_dimension");
		validateFieldNotNull("axis_dimension", axis_dimension);
		if (axis_dimension != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("axis_dimension", axis_dimension, NX_UINT);
			validateFieldUnits("axis_dimension", group.getDataNode("axis_dimension"), NX_UNITLESS);
			validateFieldDimensions("axis_dimension", axis_dimension, null, "i");
		}
	}

	/**
	 * Validate optional group 'clustering' of type NXprocess.
	 */
	private void validateGroup_NXentry_clustering(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("clustering", NXprocess.class, group))) return;

		// validate field 'sequence_index' of type NX_POSINT.
		final ILazyDataset sequence_index = group.getLazyDataset("sequence_index");
		validateFieldNotNull("sequence_index", sequence_index);
		if (sequence_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_index", sequence_index, NX_POSINT);
			validateFieldEnumeration("sequence_index", sequence_index,
					"4",
					"5");
		}

		// validate child group 'ic_opt' of type NXprocess
		validateGroup_NXentry_clustering_ic_opt(group.getChild("ic_opt", NXprocess.class));
	}

	/**
	 * Validate group 'ic_opt' of type NXprocess.
	 */
	private void validateGroup_NXentry_clustering_ic_opt(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ic_opt", NXprocess.class, group))) return;

		// validate optional child group 'cluster_analysisID' of type NXprocess
		if (group.getChild("cluster_analysisID", NXprocess.class) != null) {
			validateGroup_NXentry_clustering_ic_opt_cluster_analysisID(group.getChild("cluster_analysisID", NXprocess.class));
		}
	}

	/**
	 * Validate optional group 'cluster_analysisID' of type NXprocess.
	 */
	private void validateGroup_NXentry_clustering_ic_opt_cluster_analysisID(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cluster_analysisID", NXprocess.class, group))) return;

		// validate child group 'dbscanID' of type NXprocess
		validateGroup_NXentry_clustering_ic_opt_cluster_analysisID_dbscanID(group.getChild("dbscanID", NXprocess.class));
	}

	/**
	 * Validate group 'dbscanID' of type NXprocess.
	 */
	private void validateGroup_NXentry_clustering_ic_opt_cluster_analysisID_dbscanID(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("dbscanID", NXprocess.class, group))) return;

		// validate field 'epsilon' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset epsilon = group.getLazyDataset("epsilon");
		validateFieldNotNull("epsilon", epsilon);
		if (epsilon != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("epsilon", epsilon, NX_FLOAT);
			validateFieldUnits("epsilon", group.getDataNode("epsilon"), NX_LENGTH);
		}

		// validate field 'min_samples' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset min_samples = group.getLazyDataset("min_samples");
		validateFieldNotNull("min_samples", min_samples);
		if (min_samples != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min_samples", min_samples, NX_UINT);
			validateFieldUnits("min_samples", group.getDataNode("min_samples"), NX_UNITLESS);
		}

		// validate field 'label' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset label = group.getLazyDataset("label");
		validateFieldNotNull("label", label);
		if (label != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("label", label, NX_INT);
			validateFieldUnits("label", group.getDataNode("label"), NX_UNITLESS);
			validateFieldDimensions("label", label, null, "k");
		}

		// validate field 'voxel' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset voxel = group.getLazyDataset("voxel");
		validateFieldNotNull("voxel", voxel);
		if (voxel != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("voxel", voxel, NX_UINT);
			validateFieldUnits("voxel", group.getDataNode("voxel"), NX_UNITLESS);
			validateFieldDimensions("voxel", voxel, null, "n_voxels");
		}
	}
}
