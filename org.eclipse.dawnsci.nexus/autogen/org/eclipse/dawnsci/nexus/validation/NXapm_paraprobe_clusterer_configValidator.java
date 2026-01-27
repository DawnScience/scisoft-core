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
import org.eclipse.dawnsci.nexus.NXprocess;

/**
 * Validator for the application definition 'NXapm_paraprobe_clusterer_config'.
 */
public class NXapm_paraprobe_clusterer_configValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_clusterer_configValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_CLUSTERER_CONFIG);
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
					"NXapm_paraprobe_clusterer_config");
		}

		// validate optional child group 'cameca_to_nexus' of type NXapm_paraprobe_tool_parameters
		if (group.getChild("cameca_to_nexus", NXapm_paraprobe_tool_parameters.class) != null) {
			validateGroup_NXentry_cameca_to_nexus(group.getChild("cameca_to_nexus", NXapm_paraprobe_tool_parameters.class));
		}

		// validate optional child group 'cluster_analysisID' of type NXapm_paraprobe_tool_parameters
		if (group.getChild("cluster_analysisID", NXapm_paraprobe_tool_parameters.class) != null) {
			validateGroup_NXentry_cluster_analysisID(group.getChild("cluster_analysisID", NXapm_paraprobe_tool_parameters.class));
		}
	}

	/**
	 * Validate optional group 'cameca_to_nexus' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_cameca_to_nexus(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cameca_to_nexus", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate field 'recover_evaporation_id' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset recover_evaporation_id = group.getLazyDataset("recover_evaporation_id");
		validateFieldNotNull("recover_evaporation_id", recover_evaporation_id);
		if (recover_evaporation_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("recover_evaporation_id", recover_evaporation_id, NX_BOOLEAN);
		}
		// validate child group 'reconstruction' of type NXnote
		validateGroup_NXentry_cameca_to_nexus_reconstruction(group.getChild("reconstruction", NXnote.class));

		// validate child group 'results' of type NXnote
		validateGroup_NXentry_cameca_to_nexus_results(group.getChild("results", NXnote.class));
	}

	/**
	 * Validate group 'reconstruction' of type NXnote.
	 */
	private void validateGroup_NXentry_cameca_to_nexus_reconstruction(final NXnote group) {
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
	 * Validate group 'results' of type NXnote.
	 */
	private void validateGroup_NXentry_cameca_to_nexus_results(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("results", NXnote.class, group))) return;

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
	}

	/**
	 * Validate optional group 'cluster_analysisID' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_cluster_analysisID(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cluster_analysisID", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate field 'ion_type_filter' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset ion_type_filter = group.getLazyDataset("ion_type_filter");
		validateFieldNotNull("ion_type_filter", ion_type_filter);
		if (ion_type_filter != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ion_type_filter", ion_type_filter, NX_CHAR);
			validateFieldEnumeration("ion_type_filter", ion_type_filter,
					"resolve_element");
		}

		// validate field 'ion_query_nuclide_vector' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset ion_query_nuclide_vector = group.getLazyDataset("ion_query_nuclide_vector");
		validateFieldNotNull("ion_query_nuclide_vector", ion_query_nuclide_vector);
		if (ion_query_nuclide_vector != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ion_query_nuclide_vector", ion_query_nuclide_vector, NX_UINT);
			validateFieldUnits("ion_query_nuclide_vector", group.getDataNode("ion_query_nuclide_vector"), NX_UNITLESS);
			validateFieldRank("ion_query_nuclide_vector", ion_query_nuclide_vector, 2);
			validateFieldDimensions("ion_query_nuclide_vector", ion_query_nuclide_vector, null, "n_ions", "n_ivec_max");
		}

		// validate optional child group 'surface_distance' of type NXnote
		if (group.getChild("surface_distance", NXnote.class) != null) {
			validateGroup_NXentry_cluster_analysisID_surface_distance(group.getChild("surface_distance", NXnote.class));
		}

		// validate child group 'dbscan' of type NXprocess
		validateGroup_NXentry_cluster_analysisID_dbscan(group.getChild("dbscan", NXprocess.class));

		// validate child group 'hdbscan' of type NXprocess
		validateGroup_NXentry_cluster_analysisID_hdbscan(group.getChild("hdbscan", NXprocess.class));
	}

	/**
	 * Validate optional group 'surface_distance' of type NXnote.
	 */
	private void validateGroup_NXentry_cluster_analysisID_surface_distance(final NXnote group) {
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
	 * Validate group 'dbscan' of type NXprocess.
	 */
	private void validateGroup_NXentry_cluster_analysisID_dbscan(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("dbscan", NXprocess.class, group))) return;

		// validate field 'high_throughput_method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset high_throughput_method = group.getLazyDataset("high_throughput_method");
		validateFieldNotNull("high_throughput_method", high_throughput_method);
		if (high_throughput_method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("high_throughput_method", high_throughput_method, NX_CHAR);
			validateFieldEnumeration("high_throughput_method", high_throughput_method,
					"tuple",
					"combinatorics");
		}

		// validate field 'eps' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset eps = group.getLazyDataset("eps");
		validateFieldNotNull("eps", eps);
		if (eps != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("eps", eps, NX_FLOAT);
			validateFieldUnits("eps", group.getDataNode("eps"), NX_LENGTH);
			validateFieldRank("eps", eps, 1);
			validateFieldDimensions("eps", eps, null, "i");
		}

		// validate field 'min_pts' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset min_pts = group.getLazyDataset("min_pts");
		validateFieldNotNull("min_pts", min_pts);
		if (min_pts != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min_pts", min_pts, NX_UINT);
			validateFieldUnits("min_pts", group.getDataNode("min_pts"), NX_UNITLESS);
			validateFieldRank("min_pts", min_pts, 1);
			validateFieldDimensions("min_pts", min_pts, null, "j");
		}
	}

	/**
	 * Validate group 'hdbscan' of type NXprocess.
	 */
	private void validateGroup_NXentry_cluster_analysisID_hdbscan(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("hdbscan", NXprocess.class, group))) return;

		// validate field 'high_throughput_method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset high_throughput_method = group.getLazyDataset("high_throughput_method");
		validateFieldNotNull("high_throughput_method", high_throughput_method);
		if (high_throughput_method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("high_throughput_method", high_throughput_method, NX_CHAR);
			validateFieldEnumeration("high_throughput_method", high_throughput_method,
					"tuple",
					"combinatorics");
		}

		// validate field 'min_cluster_size' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset min_cluster_size = group.getLazyDataset("min_cluster_size");
		validateFieldNotNull("min_cluster_size", min_cluster_size);
		if (min_cluster_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min_cluster_size", min_cluster_size, NX_NUMBER);
			validateFieldUnits("min_cluster_size", group.getDataNode("min_cluster_size"), NX_ANY);
			validateFieldRank("min_cluster_size", min_cluster_size, 1);
			validateFieldDimensions("min_cluster_size", min_cluster_size, null, "i");
		}

		// validate field 'min_samples' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset min_samples = group.getLazyDataset("min_samples");
		validateFieldNotNull("min_samples", min_samples);
		if (min_samples != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min_samples", min_samples, NX_NUMBER);
			validateFieldUnits("min_samples", group.getDataNode("min_samples"), NX_ANY);
			validateFieldRank("min_samples", min_samples, 1);
			validateFieldDimensions("min_samples", min_samples, null, "j");
		}

		// validate field 'cluster_selection_epsilon' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset cluster_selection_epsilon = group.getLazyDataset("cluster_selection_epsilon");
		validateFieldNotNull("cluster_selection_epsilon", cluster_selection_epsilon);
		if (cluster_selection_epsilon != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cluster_selection_epsilon", cluster_selection_epsilon, NX_NUMBER);
			validateFieldUnits("cluster_selection_epsilon", group.getDataNode("cluster_selection_epsilon"), NX_ANY);
			validateFieldRank("cluster_selection_epsilon", cluster_selection_epsilon, 1);
			validateFieldDimensions("cluster_selection_epsilon", cluster_selection_epsilon, null, "k");
		}

		// validate field 'alpha' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset alpha = group.getLazyDataset("alpha");
		validateFieldNotNull("alpha", alpha);
		if (alpha != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("alpha", alpha, NX_NUMBER);
			validateFieldUnits("alpha", group.getDataNode("alpha"), NX_ANY);
			validateFieldRank("alpha", alpha, 1);
			validateFieldDimensions("alpha", alpha, null, "m");
		}
	}
}
