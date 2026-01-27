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
import org.eclipse.dawnsci.nexus.NXcs_prng;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXprocess;

/**
 * Validator for the application definition 'NXapm_paraprobe_spatstat_config'.
 */
public class NXapm_paraprobe_spatstat_configValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_spatstat_configValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_SPATSTAT_CONFIG);
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
					"NXapm_paraprobe_spatstat_config");
		}

		// validate child group 'spatial_statisticsID' of type NXapm_paraprobe_tool_parameters
		validateGroup_NXentry_spatial_statisticsID(group.getChild("spatial_statisticsID", NXapm_paraprobe_tool_parameters.class));
	}

	/**
	 * Validate group 'spatial_statisticsID' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_spatial_statisticsID(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spatial_statisticsID", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate field 'randomize_iontypes' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset randomize_iontypes = group.getLazyDataset("randomize_iontypes");
		validateFieldNotNull("randomize_iontypes", randomize_iontypes);
		if (randomize_iontypes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("randomize_iontypes", randomize_iontypes, NX_BOOLEAN);
		}

		// validate field 'ion_query_type_source' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset ion_query_type_source = group.getLazyDataset("ion_query_type_source");
		validateFieldNotNull("ion_query_type_source", ion_query_type_source);
		if (ion_query_type_source != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ion_query_type_source", ion_query_type_source, NX_CHAR);
			validateFieldEnumeration("ion_query_type_source", ion_query_type_source,
					"resolve_all",
					"resolve_unknown",
					"resolve_ion",
					"resolve_element",
					"resolve_isotope");
		}

		// validate field 'ion_query_nuclide_source' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset ion_query_nuclide_source = group.getLazyDataset("ion_query_nuclide_source");
		validateFieldNotNull("ion_query_nuclide_source", ion_query_nuclide_source);
		if (ion_query_nuclide_source != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ion_query_nuclide_source", ion_query_nuclide_source, NX_UINT);
			validateFieldUnits("ion_query_nuclide_source", group.getDataNode("ion_query_nuclide_source"), NX_UNITLESS);
			validateFieldRank("ion_query_nuclide_source", ion_query_nuclide_source, 2);
			validateFieldDimensions("ion_query_nuclide_source", ion_query_nuclide_source, null, "n_ion_source", "n_ivec_max");
		}

		// validate field 'ion_query_type_target' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset ion_query_type_target = group.getLazyDataset("ion_query_type_target");
		validateFieldNotNull("ion_query_type_target", ion_query_type_target);
		if (ion_query_type_target != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ion_query_type_target", ion_query_type_target, NX_CHAR);
			validateFieldEnumeration("ion_query_type_target", ion_query_type_target,
					"resolve_all",
					"resolve_unknown",
					"resolve_ion",
					"resolve_element",
					"resolve_isotope");
		}

		// validate field 'ion_query_nuclide_target' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset ion_query_nuclide_target = group.getLazyDataset("ion_query_nuclide_target");
		validateFieldNotNull("ion_query_nuclide_target", ion_query_nuclide_target);
		if (ion_query_nuclide_target != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ion_query_nuclide_target", ion_query_nuclide_target, NX_UINT);
			validateFieldUnits("ion_query_nuclide_target", group.getDataNode("ion_query_nuclide_target"), NX_UNITLESS);
			validateFieldRank("ion_query_nuclide_target", ion_query_nuclide_target, 2);
			validateFieldDimensions("ion_query_nuclide_target", ion_query_nuclide_target, null, "n_ion_target", "n_ivec_max");
		}

		// validate optional child group 'surface_distance' of type NXnote
		if (group.getChild("surface_distance", NXnote.class) != null) {
			validateGroup_NXentry_spatial_statisticsID_surface_distance(group.getChild("surface_distance", NXnote.class));
		}

		// validate optional child group 'feature_distance' of type NXnote
		if (group.getChild("feature_distance", NXnote.class) != null) {
			validateGroup_NXentry_spatial_statisticsID_feature_distance(group.getChild("feature_distance", NXnote.class));
		}

		// validate optional child group 'random_number_generator' of type NXcs_prng
		if (group.getChild("random_number_generator", NXcs_prng.class) != null) {
			validateGroup_NXentry_spatial_statisticsID_random_number_generator(group.getChild("random_number_generator", NXcs_prng.class));
		}

		// validate child group 'statistics' of type NXprocess
		validateGroup_NXentry_spatial_statisticsID_statistics(group.getChild("statistics", NXprocess.class));
	}

	/**
	 * Validate optional group 'surface_distance' of type NXnote.
	 */
	private void validateGroup_NXentry_spatial_statisticsID_surface_distance(final NXnote group) {
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

		// validate field 'edge_distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset edge_distance = group.getLazyDataset("edge_distance");
		validateFieldNotNull("edge_distance", edge_distance);
		if (edge_distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("edge_distance", edge_distance, NX_FLOAT);
			validateFieldUnits("edge_distance", group.getDataNode("edge_distance"), NX_LENGTH);
		}
	}

	/**
	 * Validate optional group 'feature_distance' of type NXnote.
	 */
	private void validateGroup_NXentry_spatial_statisticsID_feature_distance(final NXnote group) {
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

		// validate field 'feature_distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset feature_distance = group.getLazyDataset("feature_distance");
		validateFieldNotNull("feature_distance", feature_distance);
		if (feature_distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("feature_distance", feature_distance, NX_FLOAT);
			validateFieldUnits("feature_distance", group.getDataNode("feature_distance"), NX_LENGTH);
		}
	}

	/**
	 * Validate optional group 'random_number_generator' of type NXcs_prng.
	 */
	private void validateGroup_NXentry_spatial_statisticsID_random_number_generator(final NXcs_prng group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("random_number_generator", NXcs_prng.class, group))) return;

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"physical",
					"system_clock",
					"mt19937",
					"other");
		}

		// validate field 'seed' of type NX_NUMBER.
		final ILazyDataset seed = group.getLazyDataset("seed");
		validateFieldNotNull("seed", seed);
		if (seed != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("seed", seed, NX_NUMBER);
			validateFieldUnits("seed", group.getDataNode("seed"), NX_UNITLESS);
		}

		// validate field 'warmup' of type NX_NUMBER.
		final ILazyDataset warmup = group.getLazyDataset("warmup");
		validateFieldNotNull("warmup", warmup);
		if (warmup != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("warmup", warmup, NX_NUMBER);
			validateFieldUnits("warmup", group.getDataNode("warmup"), NX_UNITLESS);
		}
	}

	/**
	 * Validate group 'statistics' of type NXprocess.
	 */
	private void validateGroup_NXentry_spatial_statisticsID_statistics(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("statistics", NXprocess.class, group))) return;

		// validate optional child group 'knn' of type NXprocess
		if (group.getChild("knn", NXprocess.class) != null) {
			validateGroup_NXentry_spatial_statisticsID_statistics_knn(group.getChild("knn", NXprocess.class));
		}

		// validate optional child group 'rdf' of type NXprocess
		if (group.getChild("rdf", NXprocess.class) != null) {
			validateGroup_NXentry_spatial_statisticsID_statistics_rdf(group.getChild("rdf", NXprocess.class));
		}
	}

	/**
	 * Validate optional group 'knn' of type NXprocess.
	 */
	private void validateGroup_NXentry_spatial_statisticsID_statistics_knn(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("knn", NXprocess.class, group))) return;

		// validate field 'kth' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset kth = group.getLazyDataset("kth");
		validateFieldNotNull("kth", kth);
		if (kth != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kth", kth, NX_UINT);
			validateFieldUnits("kth", group.getDataNode("kth"), NX_UNITLESS);
		}

		// validate field 'min' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset min = group.getLazyDataset("min");
		validateFieldNotNull("min", min);
		if (min != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min", min, NX_FLOAT);
			validateFieldUnits("min", group.getDataNode("min"), NX_LENGTH);
		}

		// validate field 'increment' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset increment = group.getLazyDataset("increment");
		validateFieldNotNull("increment", increment);
		if (increment != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("increment", increment, NX_FLOAT);
			validateFieldUnits("increment", group.getDataNode("increment"), NX_LENGTH);
		}

		// validate field 'max' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset max = group.getLazyDataset("max");
		validateFieldNotNull("max", max);
		if (max != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max", max, NX_FLOAT);
			validateFieldUnits("max", group.getDataNode("max"), NX_LENGTH);
		}
	}

	/**
	 * Validate optional group 'rdf' of type NXprocess.
	 */
	private void validateGroup_NXentry_spatial_statisticsID_statistics_rdf(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("rdf", NXprocess.class, group))) return;

		// validate field 'min' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset min = group.getLazyDataset("min");
		validateFieldNotNull("min", min);
		if (min != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min", min, NX_FLOAT);
			validateFieldUnits("min", group.getDataNode("min"), NX_LENGTH);
		}

		// validate field 'increment' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset increment = group.getLazyDataset("increment");
		validateFieldNotNull("increment", increment);
		if (increment != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("increment", increment, NX_FLOAT);
			validateFieldUnits("increment", group.getDataNode("increment"), NX_LENGTH);
		}

		// validate field 'max' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset max = group.getLazyDataset("max");
		validateFieldNotNull("max", max);
		if (max != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max", max, NX_FLOAT);
			validateFieldUnits("max", group.getDataNode("max"), NX_LENGTH);
		}
	}
}
