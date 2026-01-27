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
import org.eclipse.dawnsci.analysis.api.tree.Attribute;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXparameters;

/**
 * Validator for the application definition 'NXapm_compositionspace_config'.
 */
public class NXapm_compositionspace_configValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_compositionspace_configValidator() {
		super(NexusApplicationDefinition.NX_APM_COMPOSITIONSPACE_CONFIG);
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
					"NXapm_compositionspace_config");
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

		// validate child group 'reconstruction' of type NXnote
		validateGroup_NXentry_reconstruction(group.getChild("reconstruction", NXnote.class));

		// validate child group 'ranging' of type NXnote
		validateGroup_NXentry_ranging(group.getChild("ranging", NXnote.class));

		// validate child group 'voxelization' of type NXprocess
		validateGroup_NXentry_voxelization(group.getProcess("voxelization"));

		// validate child group 'segmentation' of type NXprocess
		validateGroup_NXentry_segmentation(group.getProcess("segmentation"));

		// validate child group 'clustering' of type NXprocess
		validateGroup_NXentry_clustering(group.getProcess("clustering"));
	}

	/**
	 * Validate group 'reconstruction' of type NXnote.
	 */
	private void validateGroup_NXentry_reconstruction(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("reconstruction", NXnote.class, group))) return;

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
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

		// validate optional field 'mass_to_charge' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset mass_to_charge = group.getLazyDataset("mass_to_charge");
				if (mass_to_charge != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mass_to_charge", mass_to_charge, NX_CHAR);
		}
	}

	/**
	 * Validate group 'ranging' of type NXnote.
	 */
	private void validateGroup_NXentry_ranging(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ranging", NXnote.class, group))) return;

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
				if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate optional field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
				if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}

		// validate field 'ranging_definitions' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset ranging_definitions = group.getLazyDataset("ranging_definitions");
		validateFieldNotNull("ranging_definitions", ranging_definitions);
		if (ranging_definitions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ranging_definitions", ranging_definitions, NX_CHAR);
		}
	}

	/**
	 * Validate group 'voxelization' of type NXprocess.
	 */
	private void validateGroup_NXentry_voxelization(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("voxelization", NXprocess.class, group))) return;

		// validate field 'edge_length' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset edge_length = group.getLazyDataset("edge_length");
		validateFieldNotNull("edge_length", edge_length);
		if (edge_length != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("edge_length", edge_length, NX_NUMBER);
			validateFieldUnits("edge_length", group.getDataNode("edge_length"), NX_LENGTH);
		}

		// validate child group 'autophase' of type NXprocess
		validateGroup_NXentry_voxelization_autophase(group.getChild("autophase", NXprocess.class));
	}

	/**
	 * Validate group 'autophase' of type NXprocess.
	 */
	private void validateGroup_NXentry_voxelization_autophase(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("autophase", NXprocess.class, group))) return;

		// validate field 'use' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset use = group.getLazyDataset("use");
		validateFieldNotNull("use", use);
		if (use != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("use", use, NX_BOOLEAN);
		}

		// validate field 'initial_guess' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset initial_guess = group.getLazyDataset("initial_guess");
		validateFieldNotNull("initial_guess", initial_guess);
		if (initial_guess != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("initial_guess", initial_guess, NX_POSINT);
			validateFieldUnits("initial_guess", group.getDataNode("initial_guess"), NX_UNITLESS);
		}

		// validate field 'trunc_species' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset trunc_species = group.getLazyDataset("trunc_species");
		validateFieldNotNull("trunc_species", trunc_species);
		if (trunc_species != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("trunc_species", trunc_species, NX_POSINT);
			validateFieldUnits("trunc_species", group.getDataNode("trunc_species"), NX_UNITLESS);
		}

		// validate optional child group 'random_forest_classifier' of type NXprocess
		if (group.getChild("random_forest_classifier", NXprocess.class) != null) {
			validateGroup_NXentry_voxelization_autophase_random_forest_classifier(group.getChild("random_forest_classifier", NXprocess.class));
		}
	}

	/**
	 * Validate optional group 'random_forest_classifier' of type NXprocess.
	 */
	private void validateGroup_NXentry_voxelization_autophase_random_forest_classifier(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("random_forest_classifier", NXprocess.class, group))) return;

	}

	/**
	 * Validate group 'segmentation' of type NXprocess.
	 */
	private void validateGroup_NXentry_segmentation(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("segmentation", NXprocess.class, group))) return;

		// validate optional child group 'pca' of type NXprocess
		if (group.getChild("pca", NXprocess.class) != null) {
			validateGroup_NXentry_segmentation_pca(group.getChild("pca", NXprocess.class));
		}

		// validate child group 'ic_opt' of type NXprocess
		validateGroup_NXentry_segmentation_ic_opt(group.getChild("ic_opt", NXprocess.class));
	}

	/**
	 * Validate optional group 'pca' of type NXprocess.
	 */
	private void validateGroup_NXentry_segmentation_pca(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pca", NXprocess.class, group))) return;

	}

	/**
	 * Validate group 'ic_opt' of type NXprocess.
	 */
	private void validateGroup_NXentry_segmentation_ic_opt(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ic_opt", NXprocess.class, group))) return;

		// validate field 'n_max_ic_cluster' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset n_max_ic_cluster = group.getLazyDataset("n_max_ic_cluster");
		validateFieldNotNull("n_max_ic_cluster", n_max_ic_cluster);
		if (n_max_ic_cluster != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("n_max_ic_cluster", n_max_ic_cluster, NX_POSINT);
			validateFieldUnits("n_max_ic_cluster", group.getDataNode("n_max_ic_cluster"), NX_UNITLESS);
		}

		// validate optional child group 'gaussian_mixture' of type NXprocess
		if (group.getChild("gaussian_mixture", NXprocess.class) != null) {
			validateGroup_NXentry_segmentation_ic_opt_gaussian_mixture(group.getChild("gaussian_mixture", NXprocess.class));
		}
	}

	/**
	 * Validate optional group 'gaussian_mixture' of type NXprocess.
	 */
	private void validateGroup_NXentry_segmentation_ic_opt_gaussian_mixture(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("gaussian_mixture", NXprocess.class, group))) return;

	}

	/**
	 * Validate group 'clustering' of type NXprocess.
	 */
	private void validateGroup_NXentry_clustering(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("clustering", NXprocess.class, group))) return;

		// validate child group 'dbscan' of type NXparameters
		validateGroup_NXentry_clustering_dbscan(group.getParameters("dbscan"));
	}

	/**
	 * Validate group 'dbscan' of type NXparameters.
	 */
	private void validateGroup_NXentry_clustering_dbscan(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("dbscan", NXparameters.class, group))) return;

		// validate field 'eps' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset eps = group.getLazyDataset("eps");
		validateFieldNotNull("eps", eps);
		if (eps != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("eps", eps, NX_FLOAT);
			validateFieldUnits("eps", group.getDataNode("eps"), NX_LENGTH);
		}

		// validate field 'min_samples' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset min_samples = group.getLazyDataset("min_samples");
		validateFieldNotNull("min_samples", min_samples);
		if (min_samples != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("min_samples", min_samples, NX_UINT);
			validateFieldUnits("min_samples", group.getDataNode("min_samples"), NX_UNITLESS);
		}
	}
}
