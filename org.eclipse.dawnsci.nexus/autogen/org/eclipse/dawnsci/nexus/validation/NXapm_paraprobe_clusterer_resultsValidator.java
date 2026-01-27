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
import org.eclipse.dawnsci.nexus.NXsimilarity_grouping;
import org.eclipse.dawnsci.nexus.NXprocess;

/**
 * Validator for the application definition 'NXapm_paraprobe_clusterer_results'.
 */
public class NXapm_paraprobe_clusterer_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_clusterer_resultsValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_CLUSTERER_RESULTS);
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
					"NXapm_paraprobe_clusterer_results");
		}

		// validate optional child group 'cameca_to_nexus' of type NXapm_paraprobe_tool_process
		if (group.getChild("cameca_to_nexus", NXapm_paraprobe_tool_process.class) != null) {
			validateGroup_NXentry_cameca_to_nexus(group.getChild("cameca_to_nexus", NXapm_paraprobe_tool_process.class));
		}

		// validate optional child group 'cluster_analysisID' of type NXapm_paraprobe_tool_process
		if (group.getChild("cluster_analysisID", NXapm_paraprobe_tool_process.class) != null) {
			validateGroup_NXentry_cluster_analysisID(group.getChild("cluster_analysisID", NXapm_paraprobe_tool_process.class));
		}
	}

	/**
	 * Validate optional group 'cameca_to_nexus' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_cameca_to_nexus(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cameca_to_nexus", NXapm_paraprobe_tool_process.class, group))) return;

	}

	/**
	 * Validate optional group 'cluster_analysisID' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_cluster_analysisID(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cluster_analysisID", NXapm_paraprobe_tool_process.class, group))) return;

		// validate optional child group 'dbscanID' of type NXsimilarity_grouping
		if (group.getChild("dbscanID", NXsimilarity_grouping.class) != null) {
			validateGroup_NXentry_cluster_analysisID_dbscanID(group.getChild("dbscanID", NXsimilarity_grouping.class));
		}
	}

	/**
	 * Validate optional group 'dbscanID' of type NXsimilarity_grouping.
	 */
	private void validateGroup_NXentry_cluster_analysisID_dbscanID(final NXsimilarity_grouping group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("dbscanID", NXsimilarity_grouping.class, group))) return;

		// validate field 'eps' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset eps = group.getLazyDataset("eps");
		validateFieldNotNull("eps", eps);
		if (eps != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("eps", eps, NX_FLOAT);
			validateFieldUnits("eps", group.getDataNode("eps"), NX_LENGTH);
		}

		// validate field 'min_pts' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset min_pts = group.getLazyDataset("min_pts");
		validateFieldNotNull("min_pts", min_pts);
		if (min_pts != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min_pts", min_pts, NX_UINT);
			validateFieldUnits("min_pts", group.getDataNode("min_pts"), NX_UNITLESS);
		}

		// validate field 'cardinality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset cardinality = group.getLazyDataset("cardinality");
		validateFieldNotNull("cardinality", cardinality);
		if (cardinality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cardinality", cardinality, NX_POSINT);
			validateFieldUnits("cardinality", group.getDataNode("cardinality"), NX_UNITLESS);
		}

		// validate field 'index_offset' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset index_offset = group.getLazyDataset("index_offset");
		validateFieldNotNull("index_offset", index_offset);
		if (index_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("index_offset", index_offset, NX_INT);
			validateFieldUnits("index_offset", group.getDataNode("index_offset"), NX_UNITLESS);
		}

		// validate field 'targets' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset targets = group.getLazyDataset("targets");
		validateFieldNotNull("targets", targets);
		if (targets != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("targets", targets, NX_UINT);
			validateFieldUnits("targets", group.getDataNode("targets"), NX_UNITLESS);
			validateFieldRank("targets", targets, 1);
			validateFieldDimensions("targets", targets, null, "i");
		}

		// validate optional field 'number_of_solutions' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_solutions = group.getLazyDataset("number_of_solutions");
				if (number_of_solutions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_solutions", number_of_solutions, NX_UINT);
			validateFieldUnits("number_of_solutions", group.getDataNode("number_of_solutions"), NX_UNITLESS);
			validateFieldRank("number_of_solutions", number_of_solutions, 1);
			validateFieldDimensions("number_of_solutions", number_of_solutions, null, "i");
		}

		// validate optional field 'model_label' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset model_label = group.getLazyDataset("model_label");
				if (model_label != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model_label", model_label, NX_INT);
			validateFieldUnits("model_label", group.getDataNode("model_label"), NX_UNITLESS);
			validateFieldRank("model_label", model_label, 1);
			validateFieldDimensions("model_label", model_label, null, "k");
		}

		// validate optional field 'core_sample_indices' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset core_sample_indices = group.getLazyDataset("core_sample_indices");
				if (core_sample_indices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("core_sample_indices", core_sample_indices, NX_INT);
			validateFieldUnits("core_sample_indices", group.getDataNode("core_sample_indices"), NX_UNITLESS);
			validateFieldRank("core_sample_indices", core_sample_indices, 1);
			validateFieldDimensions("core_sample_indices", core_sample_indices, null, "k");
		}

		// validate field 'numerical_label' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset numerical_label = group.getLazyDataset("numerical_label");
		validateFieldNotNull("numerical_label", numerical_label);
		if (numerical_label != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("numerical_label", numerical_label, NX_UINT);
			validateFieldUnits("numerical_label", group.getDataNode("numerical_label"), NX_UNITLESS);
			validateFieldRank("numerical_label", numerical_label, 1);
			validateFieldDimensions("numerical_label", numerical_label, null, "k");
		}

		// validate optional field 'categorical_label' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset categorical_label = group.getLazyDataset("categorical_label");
				if (categorical_label != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("categorical_label", categorical_label, NX_CHAR);
			validateFieldRank("categorical_label", categorical_label, 1);
			validateFieldDimensions("categorical_label", categorical_label, null, "k");
		}

		// validate optional field 'weight' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset weight = group.getLazyDataset("weight");
				if (weight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("weight", weight, NX_NUMBER);
			validateFieldUnits("weight", group.getDataNode("weight"), NX_UNITLESS);
			validateFieldRank("weight", weight, 1);
			validateFieldDimensions("weight", weight, null, "k");
		}

		// validate optional field 'is_noise' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_noise = group.getLazyDataset("is_noise");
				if (is_noise != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_noise", is_noise, NX_BOOLEAN);
			validateFieldRank("is_noise", is_noise, 1);
			validateFieldDimensions("is_noise", is_noise, null, "k");
		}

		// validate optional field 'is_core' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset is_core = group.getLazyDataset("is_core");
				if (is_core != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_core", is_core, NX_BOOLEAN);
			validateFieldRank("is_core", is_core, 1);
			validateFieldDimensions("is_core", is_core, null, "k");
		}

		// validate optional child group 'statistics' of type NXprocess
		if (group.getChild("statistics", NXprocess.class) != null) {
			validateGroup_NXentry_cluster_analysisID_dbscanID_statistics(group.getChild("statistics", NXprocess.class));
		}
	}

	/**
	 * Validate optional group 'statistics' of type NXprocess.
	 */
	private void validateGroup_NXentry_cluster_analysisID_dbscanID_statistics(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("statistics", NXprocess.class, group))) return;

		// validate field 'number_of_targets' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_targets = group.getLazyDataset("number_of_targets");
		validateFieldNotNull("number_of_targets", number_of_targets);
		if (number_of_targets != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_targets", number_of_targets, NX_UINT);
			validateFieldUnits("number_of_targets", group.getDataNode("number_of_targets"), NX_UNITLESS);
		}

		// validate field 'number_of_noise_members' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_noise_members = group.getLazyDataset("number_of_noise_members");
		validateFieldNotNull("number_of_noise_members", number_of_noise_members);
		if (number_of_noise_members != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_noise_members", number_of_noise_members, NX_UINT);
			validateFieldUnits("number_of_noise_members", group.getDataNode("number_of_noise_members"), NX_UNITLESS);
		}

		// validate field 'number_of_core_members' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_core_members = group.getLazyDataset("number_of_core_members");
		validateFieldNotNull("number_of_core_members", number_of_core_members);
		if (number_of_core_members != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_core_members", number_of_core_members, NX_UINT);
			validateFieldUnits("number_of_core_members", group.getDataNode("number_of_core_members"), NX_UNITLESS);
		}

		// validate field 'number_of_features' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_features = group.getLazyDataset("number_of_features");
		validateFieldNotNull("number_of_features", number_of_features);
		if (number_of_features != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_features", number_of_features, NX_UINT);
			validateFieldUnits("number_of_features", group.getDataNode("number_of_features"), NX_UNITLESS);
		}

		// validate field 'indices_feature' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_feature = group.getLazyDataset("indices_feature");
		validateFieldNotNull("indices_feature", indices_feature);
		if (indices_feature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_feature", indices_feature, NX_INT);
			validateFieldUnits("indices_feature", group.getDataNode("indices_feature"), NX_UNITLESS);
			validateFieldRank("indices_feature", indices_feature, 1);
			validateFieldDimensions("indices_feature", indices_feature, null, "n_feat");
		}

		// validate field 'number_of_members' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_members = group.getLazyDataset("number_of_members");
		validateFieldNotNull("number_of_members", number_of_members);
		if (number_of_members != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_members", number_of_members, NX_UINT);
			validateFieldUnits("number_of_members", group.getDataNode("number_of_members"), NX_UNITLESS);
			validateFieldRank("number_of_members", number_of_members, 1);
			validateFieldDimensions("number_of_members", number_of_members, null, "n_feat");
		}
	}
}
