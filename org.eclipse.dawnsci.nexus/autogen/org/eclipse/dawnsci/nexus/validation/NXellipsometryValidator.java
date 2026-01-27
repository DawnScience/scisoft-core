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
import org.eclipse.dawnsci.nexus.NXoptical_lens;
import org.eclipse.dawnsci.nexus.NXfabrication;
import org.eclipse.dawnsci.nexus.NXwaveplate;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXprogram;

/**
 * Validator for the application definition 'NXellipsometry'.
 */
public class NXellipsometryValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXellipsometryValidator() {
		super(NexusApplicationDefinition.NX_ELLIPSOMETRY);
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
					"NXellipsometry");
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
					"ellipsometry");
		}

		// validate field 'ellipsometry_experiment_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset ellipsometry_experiment_type = group.getLazyDataset("ellipsometry_experiment_type");
		validateFieldNotNull("ellipsometry_experiment_type", ellipsometry_experiment_type);
		if (ellipsometry_experiment_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ellipsometry_experiment_type", ellipsometry_experiment_type, NX_CHAR);
			validateFieldEnumeration("ellipsometry_experiment_type", ellipsometry_experiment_type,
					"in situ spectroscopic ellipsometry",
					"THz spectroscopic ellipsometry",
					"infrared spectroscopic ellipsometry",
					"ultraviolet spectroscopic ellipsometry",
					"uv-vis spectroscopic ellipsometry",
					"NIR-Vis-UV spectroscopic ellipsometry");
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}

		// validate optional child group 'data_collection' of type NXdata
		if (group.getData("data_collection") != null) {
			validateGroup_NXentry_data_collection(group.getData("data_collection"));
		}
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate field 'ellipsometer_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset ellipsometer_type = group.getLazyDataset("ellipsometer_type");
		validateFieldNotNull("ellipsometer_type", ellipsometer_type);
		if (ellipsometer_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ellipsometer_type", ellipsometer_type, NX_CHAR);
			validateFieldEnumeration("ellipsometer_type", ellipsometer_type,
					"rotating analyzer",
					"rotating analyzer with analyzer compensator",
					"rotating analyzer with polarizer compensator",
					"rotating polarizer",
					"rotating compensator on polarizer side",
					"rotating compensator on analyzer side",
					"modulator on polarizer side",
					"modulator on analyzer side",
					"dual compensator",
					"phase modulation",
					"imaging ellipsometry",
					"null ellipsometry");
		}

		// validate optional child group 'focusing_probes' of type NXoptical_lens
		if (group.getChild("focusing_probes", NXoptical_lens.class) != null) {
			validateGroup_NXentry_NXinstrument_focusing_probes(group.getChild("focusing_probes", NXoptical_lens.class));
		}

		// validate child group 'rotating_element' of type NXwaveplate
		validateGroup_NXentry_NXinstrument_rotating_element(group.getChild("rotating_element", NXwaveplate.class));
	}

	/**
	 * Validate optional group 'focusing_probes' of type NXoptical_lens.
	 */
	private void validateGroup_NXentry_NXinstrument_focusing_probes(final NXoptical_lens group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("focusing_probes", NXoptical_lens.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"objective",
					"lens",
					"glass fiber",
					"none");
		}

		// validate optional field 'data_correction' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset data_correction = group.getLazyDataset("data_correction");
				if (data_correction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data_correction", data_correction, NX_BOOLEAN);
		}

		// validate optional field 'angular_spread' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angular_spread = group.getLazyDataset("angular_spread");
				if (angular_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular_spread", angular_spread, NX_NUMBER);
			validateFieldUnits("angular_spread", group.getDataNode("angular_spread"), NX_ANGLE);
		}
		// validate optional child group 'device_information' of type NXfabrication
		if (group.getChild("device_information", NXfabrication.class) != null) {
			validateGroup_NXentry_NXinstrument_focusing_probes_device_information(group.getChild("device_information", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_focusing_probes_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

	}

	/**
	 * Validate group 'rotating_element' of type NXwaveplate.
	 */
	private void validateGroup_NXentry_NXinstrument_rotating_element(final NXwaveplate group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("rotating_element", NXwaveplate.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'rotating_element_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset rotating_element_type = group.getLazyDataset("rotating_element_type");
		validateFieldNotNull("rotating_element_type", rotating_element_type);
		if (rotating_element_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("rotating_element_type", rotating_element_type, NX_CHAR);
			validateFieldEnumeration("rotating_element_type", rotating_element_type,
					"polarizer (source side)",
					"analyzer (detector side)",
					"compensator (source side)",
					"compensator (detector side)");
		}

		// validate optional field 'revolutions' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset revolutions = group.getLazyDataset("revolutions");
				if (revolutions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("revolutions", revolutions, NX_NUMBER);
			validateFieldUnits("revolutions", group.getDataNode("revolutions"), NX_COUNT);
		}

		// validate optional field 'fixed_revolutions' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset fixed_revolutions = group.getLazyDataset("fixed_revolutions");
				if (fixed_revolutions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fixed_revolutions", fixed_revolutions, NX_NUMBER);
			validateFieldUnits("fixed_revolutions", group.getDataNode("fixed_revolutions"), NX_COUNT);
		}

		// validate optional field 'max_revolutions' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset max_revolutions = group.getLazyDataset("max_revolutions");
				if (max_revolutions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max_revolutions", max_revolutions, NX_NUMBER);
			validateFieldUnits("max_revolutions", group.getDataNode("max_revolutions"), NX_COUNT);
		}
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'backside_roughness' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset backside_roughness = group.getLazyDataset("backside_roughness");
				if (backside_roughness != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("backside_roughness", backside_roughness, NX_BOOLEAN);
		}
	}

	/**
	 * Validate optional group 'data_collection' of type NXdata.
	 */
	private void validateGroup_NXentry_data_collection(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data_collection", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'data_identifier' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset data_identifier = group.getLazyDataset("data_identifier");
				if (data_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data_identifier", data_identifier, NX_NUMBER);
		}

		// validate optional field 'data_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset data_type = group.getLazyDataset("data_type");
				if (data_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data_type", data_type, NX_CHAR);
			validateFieldEnumeration("data_type", data_type,
					"intensity",
					"reflectivity",
					"transmittance",
					"Psi/Delta",
					"tan(Psi)/cos(Delta)",
					"Mueller matrix",
					"Jones matrix",
					"N/C/S",
					"raw data");
		}

		// validate optional field 'NAME_spectrum' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset NAME_spectrum = group.getLazyDataset("NAME_spectrum");
				if (NAME_spectrum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("NAME_spectrum", NAME_spectrum, NX_FLOAT);
			validateFieldUnits("NAME_spectrum", group.getDataNode("NAME_spectrum"), NX_ANY);
			validateFieldRank("NAME_spectrum", NAME_spectrum, 1);
			validateFieldDimensions("NAME_spectrum", NAME_spectrum, null, "N_spectrum");
		// validate optional attribute 'units' of field 'NAME_spectrum' of type NX_CHAR.
		final Attribute NAME_spectrum_attr_units = group.getDataNode("NAME_spectrum").getAttribute("units");
		if (NAME_spectrum_attr_units != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("units", NAME_spectrum_attr_units, NX_CHAR);
		}

		}

		// validate field 'measured_data' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset measured_data = group.getLazyDataset("measured_data");
		validateFieldNotNull("measured_data", measured_data);
		if (measured_data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measured_data", measured_data, NX_FLOAT);
			validateFieldUnits("measured_data", group.getDataNode("measured_data"), NX_ANY);
			validateFieldRank("measured_data", measured_data, 3);
			validateFieldDimensions("measured_data", measured_data, null, "N_measurements", "N_observables", "N_spectrum");
		// validate optional attribute 'units' of field 'measured_data' of type NX_CHAR.
		final Attribute measured_data_attr_units = group.getDataNode("measured_data").getAttribute("units");
		if (measured_data_attr_units != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("units", measured_data_attr_units, NX_CHAR);
		}

		}

		// validate optional field 'measured_data_errors' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset measured_data_errors = group.getLazyDataset("measured_data_errors");
				if (measured_data_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measured_data_errors", measured_data_errors, NX_FLOAT);
			validateFieldUnits("measured_data_errors", group.getDataNode("measured_data_errors"), NX_ANY);
			validateFieldRank("measured_data_errors", measured_data_errors, 3);
			validateFieldDimensions("measured_data_errors", measured_data_errors, null, "N_measurements", "N_observables", "N_spectrum");
		// validate optional attribute 'units' of field 'measured_data_errors' of type NX_CHAR.
		final Attribute measured_data_errors_attr_units = group.getDataNode("measured_data_errors").getAttribute("units");
		if (measured_data_errors_attr_units != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("units", measured_data_errors_attr_units, NX_CHAR);
		}

		}

		// validate optional field 'varied_parameter_link' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset varied_parameter_link = group.getLazyDataset("varied_parameter_link");
				if (varied_parameter_link != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("varied_parameter_link", varied_parameter_link, NX_CHAR);
			validateFieldRank("varied_parameter_link", varied_parameter_link, 1);
			validateFieldDimensions("varied_parameter_link", varied_parameter_link, null, "N_sensors");
		}

		// validate optional field 'reference_data_link' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset reference_data_link = group.getLazyDataset("reference_data_link");
				if (reference_data_link != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("reference_data_link", reference_data_link, NX_CHAR);
		}

		// validate optional child group 'data_software' of type NXprogram
		if (group.getChild("data_software", NXprogram.class) != null) {
			validateGroup_NXentry_data_collection_data_software(group.getChild("data_software", NXprogram.class));
		}
	}

	/**
	 * Validate optional group 'data_software' of type NXprogram.
	 */
	private void validateGroup_NXentry_data_collection_data_software(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data_software", NXprogram.class, group))) return;

		// validate optional field 'program' of type NX_CHAR.
		final ILazyDataset program = group.getLazyDataset("program");
				if (program != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("program", program, NX_CHAR);
		}

		// validate optional field 'version' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset version = group.getLazyDataset("version");
				if (version != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("version", version, NX_CHAR);
		}
		// validate optional attribute 'URL' of type NX_CHAR.
		final Attribute URL_attr = group.getAttribute("URL");
		if (URL_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("URL", URL_attr, NX_CHAR);
		}

	}
}
