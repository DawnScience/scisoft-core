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
 * Validator for the application definition 'NXapm_paraprobe_spatstat_results'.
 */
public class NXapm_paraprobe_spatstat_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_spatstat_resultsValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_SPATSTAT_RESULTS);
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
					"NXapm_paraprobe_spatstat_results");
		}

		// validate child group 'spatial_statisticsID' of type NXapm_paraprobe_tool_process
		validateGroup_NXentry_spatial_statisticsID(group.getChild("spatial_statisticsID", NXapm_paraprobe_tool_process.class));
	}

	/**
	 * Validate group 'spatial_statisticsID' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_spatial_statisticsID(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spatial_statisticsID", NXapm_paraprobe_tool_process.class, group))) return;

		// validate field 'iontypes_randomized' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset iontypes_randomized = group.getLazyDataset("iontypes_randomized");
		validateFieldNotNull("iontypes_randomized", iontypes_randomized);
		if (iontypes_randomized != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("iontypes_randomized", iontypes_randomized, NX_UINT);
			validateFieldUnits("iontypes_randomized", group.getDataNode("iontypes_randomized"), NX_UNITLESS);
			validateFieldRank("iontypes_randomized", iontypes_randomized, 1);
			validateFieldDimensions("iontypes_randomized", iontypes_randomized, null, "n_ions");
		}

		// validate optional child group 'knn' of type NXprocess
		if (group.getChild("knn", NXprocess.class) != null) {
			validateGroup_NXentry_spatial_statisticsID_knn(group.getChild("knn", NXprocess.class));
		}

		// validate optional child group 'rdf' of type NXprocess
		if (group.getChild("rdf", NXprocess.class) != null) {
			validateGroup_NXentry_spatial_statisticsID_rdf(group.getChild("rdf", NXprocess.class));
		}
	}

	/**
	 * Validate optional group 'knn' of type NXprocess.
	 */
	private void validateGroup_NXentry_spatial_statisticsID_knn(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("knn", NXprocess.class, group))) return;

		// validate field 'distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 1);
			validateFieldDimensions("distance", distance, null, "n_knn");
		}

		// validate field 'probability_mass' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset probability_mass = group.getLazyDataset("probability_mass");
		validateFieldNotNull("probability_mass", probability_mass);
		if (probability_mass != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("probability_mass", probability_mass, NX_FLOAT);
			validateFieldUnits("probability_mass", group.getDataNode("probability_mass"), NX_DIMENSIONLESS);
			validateFieldRank("probability_mass", probability_mass, 1);
			validateFieldDimensions("probability_mass", probability_mass, null, "n_knn");
		}

		// validate field 'cumulated' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset cumulated = group.getLazyDataset("cumulated");
		validateFieldNotNull("cumulated", cumulated);
		if (cumulated != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cumulated", cumulated, NX_FLOAT);
			validateFieldUnits("cumulated", group.getDataNode("cumulated"), NX_UNITLESS);
			validateFieldRank("cumulated", cumulated, 1);
			validateFieldDimensions("cumulated", cumulated, null, "n_knn");
		}

		// validate field 'cumulated_normalized' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset cumulated_normalized = group.getLazyDataset("cumulated_normalized");
		validateFieldNotNull("cumulated_normalized", cumulated_normalized);
		if (cumulated_normalized != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cumulated_normalized", cumulated_normalized, NX_FLOAT);
			validateFieldUnits("cumulated_normalized", group.getDataNode("cumulated_normalized"), NX_DIMENSIONLESS);
			validateFieldRank("cumulated_normalized", cumulated_normalized, 1);
			validateFieldDimensions("cumulated_normalized", cumulated_normalized, null, "n_knn");
		}
	}

	/**
	 * Validate optional group 'rdf' of type NXprocess.
	 */
	private void validateGroup_NXentry_spatial_statisticsID_rdf(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("rdf", NXprocess.class, group))) return;

		// validate field 'distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 1);
			validateFieldDimensions("distance", distance, null, "n_rdf");
		}

		// validate field 'probability_mass' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset probability_mass = group.getLazyDataset("probability_mass");
		validateFieldNotNull("probability_mass", probability_mass);
		if (probability_mass != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("probability_mass", probability_mass, NX_FLOAT);
			validateFieldUnits("probability_mass", group.getDataNode("probability_mass"), NX_DIMENSIONLESS);
			validateFieldRank("probability_mass", probability_mass, 1);
			validateFieldDimensions("probability_mass", probability_mass, null, "n_rdf");
		}

		// validate field 'cumulated' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset cumulated = group.getLazyDataset("cumulated");
		validateFieldNotNull("cumulated", cumulated);
		if (cumulated != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cumulated", cumulated, NX_FLOAT);
			validateFieldUnits("cumulated", group.getDataNode("cumulated"), NX_UNITLESS);
			validateFieldRank("cumulated", cumulated, 1);
			validateFieldDimensions("cumulated", cumulated, null, "n_rdf");
		}

		// validate field 'cumulated_normalized' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset cumulated_normalized = group.getLazyDataset("cumulated_normalized");
		validateFieldNotNull("cumulated_normalized", cumulated_normalized);
		if (cumulated_normalized != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cumulated_normalized", cumulated_normalized, NX_FLOAT);
			validateFieldUnits("cumulated_normalized", group.getDataNode("cumulated_normalized"), NX_DIMENSIONLESS);
			validateFieldRank("cumulated_normalized", cumulated_normalized, 1);
			validateFieldDimensions("cumulated_normalized", cumulated_normalized, null, "n_rdf");
		}
	}
}
