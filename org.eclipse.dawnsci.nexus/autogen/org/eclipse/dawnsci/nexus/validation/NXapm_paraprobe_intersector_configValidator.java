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
import org.eclipse.dawnsci.nexus.NXparameters;
import org.eclipse.dawnsci.nexus.NXnote;

/**
 * Validator for the application definition 'NXapm_paraprobe_intersector_config'.
 */
public class NXapm_paraprobe_intersector_configValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_intersector_configValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_INTERSECTOR_CONFIG);
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
					"NXapm_paraprobe_intersector_config");
		}

		// validate child group 'v_v_spatial_correlationID' of type NXapm_paraprobe_tool_parameters
		validateGroup_NXentry_v_v_spatial_correlationID(group.getChild("v_v_spatial_correlationID", NXapm_paraprobe_tool_parameters.class));
	}

	/**
	 * Validate group 'v_v_spatial_correlationID' of type NXapm_paraprobe_tool_parameters.
	 */
	private void validateGroup_NXentry_v_v_spatial_correlationID(final NXapm_paraprobe_tool_parameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("v_v_spatial_correlationID", NXapm_paraprobe_tool_parameters.class, group))) return;

		// validate field 'intersection_detection_method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset intersection_detection_method = group.getLazyDataset("intersection_detection_method");
		validateFieldNotNull("intersection_detection_method", intersection_detection_method);
		if (intersection_detection_method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intersection_detection_method", intersection_detection_method, NX_CHAR);
			validateFieldEnumeration("intersection_detection_method", intersection_detection_method,
					"shared_ion");
		}

		// validate field 'analyze_intersection' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset analyze_intersection = group.getLazyDataset("analyze_intersection");
		validateFieldNotNull("analyze_intersection", analyze_intersection);
		if (analyze_intersection != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("analyze_intersection", analyze_intersection, NX_BOOLEAN);
		}

		// validate field 'analyze_proximity' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset analyze_proximity = group.getLazyDataset("analyze_proximity");
		validateFieldNotNull("analyze_proximity", analyze_proximity);
		if (analyze_proximity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("analyze_proximity", analyze_proximity, NX_BOOLEAN);
		}

		// validate field 'analyze_coprecipitation' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset analyze_coprecipitation = group.getLazyDataset("analyze_coprecipitation");
		validateFieldNotNull("analyze_coprecipitation", analyze_coprecipitation);
		if (analyze_coprecipitation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("analyze_coprecipitation", analyze_coprecipitation, NX_BOOLEAN);
		}

		// validate field 'threshold_proximity' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset threshold_proximity = group.getLazyDataset("threshold_proximity");
		validateFieldNotNull("threshold_proximity", threshold_proximity);
		if (threshold_proximity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("threshold_proximity", threshold_proximity, NX_FLOAT);
			validateFieldUnits("threshold_proximity", group.getDataNode("threshold_proximity"), NX_LENGTH);
		}

		// validate field 'has_current_to_next_links' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_current_to_next_links = group.getLazyDataset("has_current_to_next_links");
		validateFieldNotNull("has_current_to_next_links", has_current_to_next_links);
		if (has_current_to_next_links != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_current_to_next_links", has_current_to_next_links, NX_BOOLEAN);
		}

		// validate field 'has_next_to_current_links' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset has_next_to_current_links = group.getLazyDataset("has_next_to_current_links");
		validateFieldNotNull("has_next_to_current_links", has_next_to_current_links);
		if (has_next_to_current_links != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("has_next_to_current_links", has_next_to_current_links, NX_BOOLEAN);
		}

		// validate child group 'current_set' of type NXparameters
		validateGroup_NXentry_v_v_spatial_correlationID_current_set(group.getChild("current_set", NXparameters.class));

		// validate child group 'next_set' of type NXparameters
		validateGroup_NXentry_v_v_spatial_correlationID_next_set(group.getChild("next_set", NXparameters.class));
	}

	/**
	 * Validate group 'current_set' of type NXparameters.
	 */
	private void validateGroup_NXentry_v_v_spatial_correlationID_current_set(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("current_set", NXparameters.class, group))) return;

		// validate field 'set_identifier' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset set_identifier = group.getLazyDataset("set_identifier");
		validateFieldNotNull("set_identifier", set_identifier);
		if (set_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("set_identifier", set_identifier, NX_UINT);
			validateFieldUnits("set_identifier", group.getDataNode("set_identifier"), NX_ANY);
		}

		// validate field 'number_of_feature_types' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_feature_types = group.getLazyDataset("number_of_feature_types");
		validateFieldNotNull("number_of_feature_types", number_of_feature_types);
		if (number_of_feature_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_feature_types", number_of_feature_types, NX_UINT);
			validateFieldUnits("number_of_feature_types", group.getDataNode("number_of_feature_types"), NX_UNITLESS);
		}

		// validate child group 'objectID' of type NXnote
		validateGroup_NXentry_v_v_spatial_correlationID_current_set_objectID(group.getChild("objectID", NXnote.class));
	}

	/**
	 * Validate group 'objectID' of type NXnote.
	 */
	private void validateGroup_NXentry_v_v_spatial_correlationID_current_set_objectID(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("objectID", NXnote.class, group))) return;

		// validate field 'feature_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset feature_type = group.getLazyDataset("feature_type");
		validateFieldNotNull("feature_type", feature_type);
		if (feature_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("feature_type", feature_type, NX_CHAR);
			validateFieldEnumeration("feature_type", feature_type,
					"objects_far_from_edge",
					"objects_close_to_edge",
					"proxies_far_from_edge",
					"proxies_close_to_edge",
					"other");
		}

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

		// validate field 'geometry' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset geometry = group.getLazyDataset("geometry");
		validateFieldNotNull("geometry", geometry);
		if (geometry != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("geometry", geometry, NX_CHAR);
		}

		// validate field 'indices_feature' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_feature = group.getLazyDataset("indices_feature");
		validateFieldNotNull("indices_feature", indices_feature);
		if (indices_feature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_feature", indices_feature, NX_INT);
			validateFieldUnits("indices_feature", group.getDataNode("indices_feature"), NX_UNITLESS);
			validateFieldRank("indices_feature", indices_feature, 1);
			validateFieldDimensions("indices_feature", indices_feature, null, "n_variable");
		}
	}

	/**
	 * Validate group 'next_set' of type NXparameters.
	 */
	private void validateGroup_NXentry_v_v_spatial_correlationID_next_set(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("next_set", NXparameters.class, group))) return;

		// validate field 'set_identifier' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset set_identifier = group.getLazyDataset("set_identifier");
		validateFieldNotNull("set_identifier", set_identifier);
		if (set_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("set_identifier", set_identifier, NX_UINT);
			validateFieldUnits("set_identifier", group.getDataNode("set_identifier"), NX_ANY);
		}

		// validate field 'number_of_feature_types' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_feature_types = group.getLazyDataset("number_of_feature_types");
		validateFieldNotNull("number_of_feature_types", number_of_feature_types);
		if (number_of_feature_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_feature_types", number_of_feature_types, NX_UINT);
			validateFieldUnits("number_of_feature_types", group.getDataNode("number_of_feature_types"), NX_UNITLESS);
		}

		// validate child group 'objectID' of type NXnote
		validateGroup_NXentry_v_v_spatial_correlationID_next_set_objectID(group.getChild("objectID", NXnote.class));
	}

	/**
	 * Validate group 'objectID' of type NXnote.
	 */
	private void validateGroup_NXentry_v_v_spatial_correlationID_next_set_objectID(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("objectID", NXnote.class, group))) return;

		// validate field 'feature_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset feature_type = group.getLazyDataset("feature_type");
		validateFieldNotNull("feature_type", feature_type);
		if (feature_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("feature_type", feature_type, NX_CHAR);
			validateFieldEnumeration("feature_type", feature_type,
					"objects_far_from_edge",
					"objects_close_to_edge",
					"proxies_far_from_edge",
					"proxies_close_to_edge");
		}

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

		// validate field 'geometry' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset geometry = group.getLazyDataset("geometry");
		validateFieldNotNull("geometry", geometry);
		if (geometry != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("geometry", geometry, NX_CHAR);
		}

		// validate field 'indices_feature' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_feature = group.getLazyDataset("indices_feature");
		validateFieldNotNull("indices_feature", indices_feature);
		if (indices_feature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_feature", indices_feature, NX_INT);
			validateFieldUnits("indices_feature", group.getDataNode("indices_feature"), NX_UNITLESS);
			validateFieldRank("indices_feature", indices_feature, 1);
			validateFieldDimensions("indices_feature", indices_feature, null, "n_variable");
		}
	}
}
