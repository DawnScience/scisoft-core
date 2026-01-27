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
import org.eclipse.dawnsci.nexus.NXprocess;

/**
 * Validator for the application definition 'NXapm_paraprobe_intersector_results'.
 */
public class NXapm_paraprobe_intersector_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_intersector_resultsValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_INTERSECTOR_RESULTS);
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
					"NXapm_paraprobe_intersector_results");
		}

		// validate optional child group 'v_v_spatial_correlationID' of type NXapm_paraprobe_tool_process
		if (group.getChild("v_v_spatial_correlationID", NXapm_paraprobe_tool_process.class) != null) {
			validateGroup_NXentry_v_v_spatial_correlationID(group.getChild("v_v_spatial_correlationID", NXapm_paraprobe_tool_process.class));
		}
	}

	/**
	 * Validate optional group 'v_v_spatial_correlationID' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_v_v_spatial_correlationID(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("v_v_spatial_correlationID", NXapm_paraprobe_tool_process.class, group))) return;

		// validate field 'current_to_next_link' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset current_to_next_link = group.getLazyDataset("current_to_next_link");
		validateFieldNotNull("current_to_next_link", current_to_next_link);
		if (current_to_next_link != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("current_to_next_link", current_to_next_link, NX_UINT);
			validateFieldUnits("current_to_next_link", group.getDataNode("current_to_next_link"), NX_UNITLESS);
			validateFieldRank("current_to_next_link", current_to_next_link, 2);
			validateFieldDimensions("current_to_next_link", current_to_next_link, null, "n_c2n", 2);
		}

		// validate field 'current_to_next_link_type' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset current_to_next_link_type = group.getLazyDataset("current_to_next_link_type");
		validateFieldNotNull("current_to_next_link_type", current_to_next_link_type);
		if (current_to_next_link_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("current_to_next_link_type", current_to_next_link_type, NX_UINT);
			validateFieldUnits("current_to_next_link_type", group.getDataNode("current_to_next_link_type"), NX_UNITLESS);
			validateFieldRank("current_to_next_link_type", current_to_next_link_type, 1);
			validateFieldDimensions("current_to_next_link_type", current_to_next_link_type, null, "n_c2n");
		}

		// validate optional field 'next_to_current_link' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset next_to_current_link = group.getLazyDataset("next_to_current_link");
				if (next_to_current_link != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("next_to_current_link", next_to_current_link, NX_UINT);
			validateFieldUnits("next_to_current_link", group.getDataNode("next_to_current_link"), NX_UNITLESS);
			validateFieldRank("next_to_current_link", next_to_current_link, 2);
			validateFieldDimensions("next_to_current_link", next_to_current_link, null, "n_n2c", 2);
		}

		// validate optional field 'next_to_current_link_type' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset next_to_current_link_type = group.getLazyDataset("next_to_current_link_type");
				if (next_to_current_link_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("next_to_current_link_type", next_to_current_link_type, NX_UINT);
			validateFieldUnits("next_to_current_link_type", group.getDataNode("next_to_current_link_type"), NX_UNITLESS);
			validateFieldRank("next_to_current_link_type", next_to_current_link_type, 1);
			validateFieldDimensions("next_to_current_link_type", next_to_current_link_type, null, "n_n2c");
		}

		// validate optional field 'intersection_volume' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset intersection_volume = group.getLazyDataset("intersection_volume");
				if (intersection_volume != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intersection_volume", intersection_volume, NX_FLOAT);
			validateFieldUnits("intersection_volume", group.getDataNode("intersection_volume"), NX_VOLUME);
			validateFieldRank("intersection_volume", intersection_volume, 1);
			validateFieldDimensions("intersection_volume", intersection_volume, null, "n_c2n");
		}

		// validate optional child group 'coprecipitation_analysis' of type NXprocess
		if (group.getChild("coprecipitation_analysis", NXprocess.class) != null) {
			validateGroup_NXentry_v_v_spatial_correlationID_coprecipitation_analysis(group.getChild("coprecipitation_analysis", NXprocess.class));
		}
	}

	/**
	 * Validate optional group 'coprecipitation_analysis' of type NXprocess.
	 */
	private void validateGroup_NXentry_v_v_spatial_correlationID_coprecipitation_analysis(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("coprecipitation_analysis", NXprocess.class, group))) return;

		// validate field 'current_set_feature_to_cluster' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset current_set_feature_to_cluster = group.getLazyDataset("current_set_feature_to_cluster");
		validateFieldNotNull("current_set_feature_to_cluster", current_set_feature_to_cluster);
		if (current_set_feature_to_cluster != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("current_set_feature_to_cluster", current_set_feature_to_cluster, NX_UINT);
			validateFieldUnits("current_set_feature_to_cluster", group.getDataNode("current_set_feature_to_cluster"), NX_UNITLESS);
			validateFieldRank("current_set_feature_to_cluster", current_set_feature_to_cluster, 2);
			validateFieldDimensions("current_set_feature_to_cluster", current_set_feature_to_cluster, null, "n_features_curr", 2);
		}

		// validate field 'next_set_feature_to_cluster' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset next_set_feature_to_cluster = group.getLazyDataset("next_set_feature_to_cluster");
		validateFieldNotNull("next_set_feature_to_cluster", next_set_feature_to_cluster);
		if (next_set_feature_to_cluster != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("next_set_feature_to_cluster", next_set_feature_to_cluster, NX_UINT);
			validateFieldUnits("next_set_feature_to_cluster", group.getDataNode("next_set_feature_to_cluster"), NX_UNITLESS);
			validateFieldRank("next_set_feature_to_cluster", next_set_feature_to_cluster, 2);
			validateFieldDimensions("next_set_feature_to_cluster", next_set_feature_to_cluster, null, "n_features_next", 2);
		}

		// validate field 'cluster_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset cluster_id = group.getLazyDataset("cluster_id");
		validateFieldNotNull("cluster_id", cluster_id);
		if (cluster_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cluster_id", cluster_id, NX_UINT);
			validateFieldUnits("cluster_id", group.getDataNode("cluster_id"), NX_UNITLESS);
			validateFieldRank("cluster_id", cluster_id, 1);
			validateFieldDimensions("cluster_id", cluster_id, null, "n_cluster");
		}

		// validate field 'cluster_composition' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset cluster_composition = group.getLazyDataset("cluster_composition");
		validateFieldNotNull("cluster_composition", cluster_composition);
		if (cluster_composition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cluster_composition", cluster_composition, NX_UINT);
			validateFieldUnits("cluster_composition", group.getDataNode("cluster_composition"), NX_UNITLESS);
			validateFieldRank("cluster_composition", cluster_composition, 2);
			validateFieldDimensions("cluster_composition", cluster_composition, null, "n_cluster", 3);
		}

		// validate field 'cluster_statistics' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset cluster_statistics = group.getLazyDataset("cluster_statistics");
		validateFieldNotNull("cluster_statistics", cluster_statistics);
		if (cluster_statistics != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cluster_statistics", cluster_statistics, NX_UINT);
			validateFieldUnits("cluster_statistics", group.getDataNode("cluster_statistics"), NX_UNITLESS);
			validateFieldRank("cluster_statistics", cluster_statistics, 2);
			validateFieldDimensions("cluster_statistics", cluster_statistics, null, "n_total", 2);
		}
	}
}
