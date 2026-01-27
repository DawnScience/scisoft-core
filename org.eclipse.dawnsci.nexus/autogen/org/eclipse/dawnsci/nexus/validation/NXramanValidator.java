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
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXbeam;

/**
 * Validator for the application definition 'NXraman'.
 */
public class NXramanValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXramanValidator() {
		super(NexusApplicationDefinition.NX_RAMAN);
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
					"NXraman");
		// validate attribute 'version' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (!(validateAttributeNotNull("version", definition_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", definition_attr_version, NX_CHAR);

		// validate attribute 'URL' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_URL = group.getDataNode("definition").getAttribute("URL");
		if (!(validateAttributeNotNull("URL", definition_attr_URL))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("URL", definition_attr_URL, NX_CHAR);

		}

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'experiment_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset experiment_type = group.getLazyDataset("experiment_type");
		validateFieldNotNull("experiment_type", experiment_type);
		if (experiment_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_type", experiment_type, NX_CHAR);
			validateFieldEnumeration("experiment_type", experiment_type,
					"Raman spectroscopy");
		}

		// validate field 'raman_experiment_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset raman_experiment_type = group.getLazyDataset("raman_experiment_type");
		validateFieldNotNull("raman_experiment_type", raman_experiment_type);
		if (raman_experiment_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("raman_experiment_type", raman_experiment_type, NX_CHAR);
			validateFieldEnumeration("raman_experiment_type", raman_experiment_type,
					"in situ Raman spectroscopy",
					"resonant Raman spectroscopy",
					"non-resonant Raman spectroscopy",
					"Raman imaging",
					"tip-enhanced Raman spectroscopy (TERS)",
					"surface-enhanced Raman spectroscopy (SERS)",
					"surface plasmon polariton enhanced Raman scattering (SPPERS)",
					"hyper Raman spectroscopy (HRS)",
					"stimulated Raman spectroscopy (SRS)",
					"inverse Raman spectroscopy (IRS)",
					"coherent anti-Stokes Raman spectroscopy (CARS)");
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate field 'scattering_configuration' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset scattering_configuration = group.getLazyDataset("scattering_configuration");
		validateFieldNotNull("scattering_configuration", scattering_configuration);
		if (scattering_configuration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scattering_configuration", scattering_configuration, NX_CHAR);
		// validate attribute 'porto_notation_vectors' of field 'scattering_configuration' of type NX_NUMBER.
		final Attribute scattering_configuration_attr_porto_notation_vectors = group.getDataNode("scattering_configuration").getAttribute("porto_notation_vectors");
		if (!(validateAttributeNotNull("porto_notation_vectors", scattering_configuration_attr_porto_notation_vectors))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("porto_notation_vectors", scattering_configuration_attr_porto_notation_vectors, NX_NUMBER);

		}

		// validate child group 'beam_incident' of type NXbeam
		validateGroup_NXentry_NXinstrument_beam_incident(group.getBeam("beam_incident"));
	}

	/**
	 * Validate group 'beam_incident' of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_beam_incident(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_incident", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'wavelength' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
		validateFieldNotNull("wavelength", wavelength);
		if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_NUMBER);
		}
	}
}
