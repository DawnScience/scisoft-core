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
import org.eclipse.dawnsci.nexus.NXfabrication;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXslit;
import org.eclipse.dawnsci.nexus.NXattenuator;
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXresolution;
import org.eclipse.dawnsci.nexus.NXgrating;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXtransmission'.
 */
public class NXtransmissionValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXtransmissionValidator() {
		super(NexusApplicationDefinition.NX_TRANSMISSION);
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
					"NXtransmission");
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

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		validateFieldNotNull("start_time", start_time);
		if (start_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start_time", start_time, NX_DATE_TIME);
		}

		// validate field 'experiment_identifier' of type NX_CHAR.
		final ILazyDataset experiment_identifier = group.getLazyDataset("experiment_identifier");
		validateFieldNotNull("experiment_identifier", experiment_identifier);
		if (experiment_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_identifier", experiment_identifier, NX_CHAR);
		}

		// validate optional field 'experiment_description' of type NX_CHAR.
		final ILazyDataset experiment_description = group.getLazyDataset("experiment_description");
				if (experiment_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_description", experiment_description, NX_CHAR);
		}

		// validate optional child group 'acquisition_program' of type NXfabrication
		if (group.getChild("acquisition_program", NXfabrication.class) != null) {
			validateGroup_NXentry_acquisition_program(group.getChild("acquisition_program", NXfabrication.class));
		}

		// validate unnamed child group of type NXuser (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXuser.class, false, true);
		final Map<String, NXuser> allUser = group.getAllUser();
		for (final NXuser user : allUser.values()) {
			validateGroup_NXentry_NXuser(user);
		}

		// validate child group 'instrument' of type NXinstrument
		validateGroup_NXentry_instrument(group.getInstrument());

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}

		// validate child group 'data' of type NXdata
		validateGroup_NXentry_data(group.getData());
	}

	/**
	 * Validate optional group 'acquisition_program' of type NXfabrication.
	 */
	private void validateGroup_NXentry_acquisition_program(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("acquisition_program", NXfabrication.class, group))) return;

		// validate field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
		validateFieldNotNull("identifier", identifier);
		if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}
		// validate attribute 'url' of type NX_CHAR.
		final Attribute url_attr = group.getAttribute("url");
		if (!(validateAttributeNotNull("url", url_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("url", url_attr, NX_CHAR);

	}

	/**
	 * Validate unnamed group of type NXuser.
	 */
	private void validateGroup_NXentry_NXuser(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXuser.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'affiliation' of type NX_CHAR.
		final ILazyDataset affiliation = group.getLazyDataset("affiliation");
				if (affiliation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("affiliation", affiliation, NX_CHAR);
		}
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_NXentry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate field 'common_beam_depolarizer' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset common_beam_depolarizer = group.getLazyDataset("common_beam_depolarizer");
		validateFieldNotNull("common_beam_depolarizer", common_beam_depolarizer);
		if (common_beam_depolarizer != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("common_beam_depolarizer", common_beam_depolarizer, NX_BOOLEAN);
		}

		// validate field 'polarizer' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset polarizer = group.getLazyDataset("polarizer");
		validateFieldNotNull("polarizer", polarizer);
		if (polarizer != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("polarizer", polarizer, NX_NUMBER);
			validateFieldUnits("polarizer", group.getDataNode("polarizer"), NX_ANGLE);
		}

		// validate optional field 'time_points' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset time_points = group.getLazyDataset("time_points");
				if (time_points != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time_points", time_points, NX_NUMBER);
			validateFieldUnits("time_points", group.getDataNode("time_points"), NX_TIME);
			validateFieldRank("time_points", time_points, 1);
			validateFieldDimensions("time_points", time_points, null, "N_scans");
		}

		// validate field 'measured_data' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset measured_data = group.getLazyDataset("measured_data");
		validateFieldNotNull("measured_data", measured_data);
		if (measured_data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measured_data", measured_data, NX_NUMBER);
			validateFieldRank("measured_data", measured_data, 2);
			validateFieldDimensions("measured_data", measured_data, null, "N_scans", "N_wavelengths");
		}

		// validate optional child group 'manufacturer' of type NXfabrication
		if (group.getFabrication("manufacturer") != null) {
			validateGroup_NXentry_instrument_manufacturer(group.getFabrication("manufacturer"));
		}

		// validate child group 'common_beam_mask' of type NXslit
		validateGroup_NXentry_instrument_common_beam_mask(group.getChild("common_beam_mask", NXslit.class));

		// validate child group 'ref_attenuator' of type NXattenuator
		validateGroup_NXentry_instrument_ref_attenuator(group.getAttenuator("ref_attenuator"));

		// validate child group 'sample_attenuator' of type NXattenuator
		validateGroup_NXentry_instrument_sample_attenuator(group.getAttenuator("sample_attenuator"));

		// validate child group 'spectrometer' of type NXmonochromator
		validateGroup_NXentry_instrument_spectrometer(group.getMonochromator("spectrometer"));

		// validate unnamed child group of type NXdetector (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector.class, false, true);
		final Map<String, NXdetector> allDetector = group.getAllDetector();
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_NXentry_instrument_NXdetector(detector);
		}

		// validate unnamed child group of type NXsource (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_NXentry_instrument_NXsource(source);
		}
	}

	/**
	 * Validate optional group 'manufacturer' of type NXfabrication.
	 */
	private void validateGroup_NXentry_instrument_manufacturer(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("manufacturer", NXfabrication.class, group))) return;

	}

	/**
	 * Validate group 'common_beam_mask' of type NXslit.
	 */
	private void validateGroup_NXentry_instrument_common_beam_mask(final NXslit group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("common_beam_mask", NXslit.class, group))) return;

		// validate field 'y_gap' of type NX_NUMBER.
		final ILazyDataset y_gap = group.getLazyDataset("y_gap");
		validateFieldNotNull("y_gap", y_gap);
		if (y_gap != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_gap", y_gap, NX_NUMBER);
			validateFieldUnits("y_gap", group.getDataNode("y_gap"), NX_UNITLESS);
		}
	}

	/**
	 * Validate group 'ref_attenuator' of type NXattenuator.
	 */
	private void validateGroup_NXentry_instrument_ref_attenuator(final NXattenuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ref_attenuator", NXattenuator.class, group))) return;

		// validate field 'attenuator_transmission' of type NX_FLOAT.
		final ILazyDataset attenuator_transmission = group.getLazyDataset("attenuator_transmission");
		validateFieldNotNull("attenuator_transmission", attenuator_transmission);
		if (attenuator_transmission != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("attenuator_transmission", attenuator_transmission, NX_FLOAT);
			validateFieldUnits("attenuator_transmission", group.getDataNode("attenuator_transmission"), NX_DIMENSIONLESS);
		}
	}

	/**
	 * Validate group 'sample_attenuator' of type NXattenuator.
	 */
	private void validateGroup_NXentry_instrument_sample_attenuator(final NXattenuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_attenuator", NXattenuator.class, group))) return;

		// validate field 'attenuator_transmission' of type NX_FLOAT.
		final ILazyDataset attenuator_transmission = group.getLazyDataset("attenuator_transmission");
		validateFieldNotNull("attenuator_transmission", attenuator_transmission);
		if (attenuator_transmission != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("attenuator_transmission", attenuator_transmission, NX_FLOAT);
			validateFieldUnits("attenuator_transmission", group.getDataNode("attenuator_transmission"), NX_DIMENSIONLESS);
		}
	}

	/**
	 * Validate group 'spectrometer' of type NXmonochromator.
	 */
	private void validateGroup_NXentry_instrument_spectrometer(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spectrometer", NXmonochromator.class, group))) return;

		// validate field 'wavelength' of type NX_NUMBER.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
		validateFieldNotNull("wavelength", wavelength);
		if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_NUMBER);
			validateFieldUnits("wavelength", group.getDataNode("wavelength"), NX_LENGTH);
			validateFieldRank("wavelength", wavelength, 1);
			validateFieldDimensions("wavelength", wavelength, null, "N_wavelengths");
		}

		// validate optional child group 'spectral_resolution' of type NXresolution
		if (group.getChild("spectral_resolution", NXresolution.class) != null) {
			validateGroup_NXentry_instrument_spectrometer_spectral_resolution(group.getChild("spectral_resolution", NXresolution.class));
		}

		// validate unnamed child group of type NXgrating (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXgrating.class, false, true);
		final Map<String, NXgrating> allGrating = group.getAllGrating();
		for (final NXgrating grating : allGrating.values()) {
			validateGroup_NXentry_instrument_spectrometer_NXgrating(grating);
		}
	}

	/**
	 * Validate optional group 'spectral_resolution' of type NXresolution.
	 */
	private void validateGroup_NXentry_instrument_spectrometer_spectral_resolution(final NXresolution group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spectral_resolution", NXresolution.class, group))) return;

		// validate field 'resolution' of type NX_NUMBER.
		final ILazyDataset resolution = group.getLazyDataset("resolution");
		validateFieldNotNull("resolution", resolution);
		if (resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("resolution", resolution, NX_NUMBER);
			validateFieldUnits("resolution", group.getDataNode("resolution"), NX_WAVENUMBER);
		}
	}

	/**
	 * Validate optional unnamed group of type NXgrating.
	 */
	private void validateGroup_NXentry_instrument_spectrometer_NXgrating(final NXgrating group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXgrating.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'angular_dispersion' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angular_dispersion = group.getLazyDataset("angular_dispersion");
				if (angular_dispersion != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular_dispersion", angular_dispersion, NX_NUMBER);
			validateFieldUnits("angular_dispersion", group.getDataNode("angular_dispersion"), NX_DIMENSIONLESS);
		}

		// validate optional field 'blaze_wavelength' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset blaze_wavelength = group.getLazyDataset("blaze_wavelength");
				if (blaze_wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("blaze_wavelength", blaze_wavelength, NX_NUMBER);
			validateFieldUnits("blaze_wavelength", group.getDataNode("blaze_wavelength"), NX_LENGTH);
		}

		// validate field 'wavelength_range' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength_range = group.getLazyDataset("wavelength_range");
		validateFieldNotNull("wavelength_range", wavelength_range);
		if (wavelength_range != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_range", wavelength_range, NX_NUMBER);
			validateFieldUnits("wavelength_range", group.getDataNode("wavelength_range"), NX_LENGTH);
			validateFieldRank("wavelength_range", wavelength_range, 1);
			validateFieldDimensions("wavelength_range", wavelength_range, null, 2);
		}
		// validate optional child group 'spectral_resolution' of type NXresolution
		if (group.getChild("spectral_resolution", NXresolution.class) != null) {
			validateGroup_NXentry_instrument_spectrometer_NXgrating_spectral_resolution(group.getChild("spectral_resolution", NXresolution.class));
		}
	}

	/**
	 * Validate optional group 'spectral_resolution' of type NXresolution.
	 */
	private void validateGroup_NXentry_instrument_spectrometer_NXgrating_spectral_resolution(final NXresolution group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spectral_resolution", NXresolution.class, group))) return;

		// validate field 'resolution' of type NX_NUMBER.
		final ILazyDataset resolution = group.getLazyDataset("resolution");
		validateFieldNotNull("resolution", resolution);
		if (resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("resolution", resolution, NX_NUMBER);
			validateFieldUnits("resolution", group.getDataNode("resolution"), NX_WAVENUMBER);
		}
	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_NXentry_instrument_NXdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'wavelength_range' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength_range = group.getLazyDataset("wavelength_range");
		validateFieldNotNull("wavelength_range", wavelength_range);
		if (wavelength_range != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_range", wavelength_range, NX_NUMBER);
			validateFieldUnits("wavelength_range", group.getDataNode("wavelength_range"), NX_LENGTH);
			validateFieldRank("wavelength_range", wavelength_range, 1);
			validateFieldDimensions("wavelength_range", wavelength_range, null, 2);
		}

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"PMT",
					"PbS",
					"InGaAs");
		}

		// validate optional field 'response_time' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset response_time = group.getLazyDataset("response_time");
				if (response_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("response_time", response_time, NX_NUMBER);
			validateFieldUnits("response_time", group.getDataNode("response_time"), NX_TIME);
		}

		// validate optional field 'gain' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset gain = group.getLazyDataset("gain");
				if (gain != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("gain", gain, NX_NUMBER);
		}

		// validate child group 'slit' of type NXslit
		validateGroup_NXentry_instrument_NXdetector_slit(group.getChild("slit", NXslit.class));
	}

	/**
	 * Validate group 'slit' of type NXslit.
	 */
	private void validateGroup_NXentry_instrument_NXdetector_slit(final NXslit group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("slit", NXslit.class, group))) return;

		// validate field 'type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"fixed",
					"servo");
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_NXentry_instrument_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"halogen",
					"D2");
		}

		// validate optional field 'spectrum' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset spectrum = group.getLazyDataset("spectrum");
				if (spectrum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("spectrum", spectrum, NX_NUMBER);
			validateFieldRank("spectrum", spectrum, 1);
			validateFieldDimensions("spectrum", spectrum, null, "N_wavelengths");
		}

		// validate field 'wavelength_range' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength_range = group.getLazyDataset("wavelength_range");
		validateFieldNotNull("wavelength_range", wavelength_range);
		if (wavelength_range != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_range", wavelength_range, NX_NUMBER);
			validateFieldUnits("wavelength_range", group.getDataNode("wavelength_range"), NX_LENGTH);
			validateFieldRank("wavelength_range", wavelength_range, 1);
			validateFieldDimensions("wavelength_range", wavelength_range, null, 2);
		}
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_NXentry_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

	}
}
