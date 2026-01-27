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
import org.eclipse.dawnsci.nexus.NXcoordinate_system;
import org.eclipse.dawnsci.nexus.NXtransformations;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXfabrication;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXcomponent;
import org.eclipse.dawnsci.nexus.NXoptical_lens;
import org.eclipse.dawnsci.nexus.NXwaveplate;
import org.eclipse.dawnsci.nexus.NXoptical_window;
import org.eclipse.dawnsci.nexus.NXbeam_transfer_matrix_table;
import org.eclipse.dawnsci.nexus.NXmanipulator;
import org.eclipse.dawnsci.nexus.NXsensor;
import org.eclipse.dawnsci.nexus.NXactuator;
import org.eclipse.dawnsci.nexus.NXpid_controller;
import org.eclipse.dawnsci.nexus.NXprogram;
import org.eclipse.dawnsci.nexus.NXcalibration;
import org.eclipse.dawnsci.nexus.NXresolution;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXhistory;
import org.eclipse.dawnsci.nexus.NXenvironment;
import org.eclipse.dawnsci.nexus.NXprocess;

/**
 * Validator for the application definition 'NXoptical_spectroscopy'.
 */
public class NXoptical_spectroscopyValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXoptical_spectroscopyValidator() {
		super(NexusApplicationDefinition.NX_OPTICAL_SPECTROSCOPY);
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
					"NXoptical_spectroscopy");
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

		// validate optional field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
				if (start_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start_time", start_time, NX_DATE_TIME);
		}

		// validate optional field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
				if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
		}

		// validate optional field 'identifier_experiment' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier_experiment = group.getLazyDataset("identifier_experiment");
				if (identifier_experiment != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_experiment", identifier_experiment, NX_CHAR);
		}

		// validate optional field 'experiment_description' of type NX_CHAR.
		final ILazyDataset experiment_description = group.getLazyDataset("experiment_description");
				if (experiment_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_description", experiment_description, NX_CHAR);
		}

		// validate field 'experiment_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset experiment_type = group.getLazyDataset("experiment_type");
		validateFieldNotNull("experiment_type", experiment_type);
		if (experiment_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_type", experiment_type, NX_CHAR);
			validateFieldEnumeration("experiment_type", experiment_type,
					"photoluminescence",
					"transmission spectroscopy",
					"reflection spectroscopy");
		}

		// validate optional field 'experiment_sub_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset experiment_sub_type = group.getLazyDataset("experiment_sub_type");
				if (experiment_sub_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_sub_type", experiment_sub_type, NX_CHAR);
			validateFieldEnumeration("experiment_sub_type", experiment_sub_type,
					"time resolved",
					"imaging",
					"pump-probe");
		}

		// validate optional child group 'beam_ref_frame' of type NXcoordinate_system
		if (group.getChild("beam_ref_frame", NXcoordinate_system.class) != null) {
			validateGroup_NXentry_beam_ref_frame(group.getChild("beam_ref_frame", NXcoordinate_system.class));
		}

		// validate optional child group 'sample_normal_ref_frame' of type NXcoordinate_system
		if (group.getChild("sample_normal_ref_frame", NXcoordinate_system.class) != null) {
			validateGroup_NXentry_sample_normal_ref_frame(group.getChild("sample_normal_ref_frame", NXcoordinate_system.class));
		}

		// validate unnamed child group of type NXuser (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXuser.class, false, true);
		final Map<String, NXuser> allUser = group.getAllUser();
		for (final NXuser user : allUser.values()) {
			validateGroup_NXentry_NXuser(user);
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

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}

		// validate optional child group 'measurement_data_calibration_TYPE' of type NXprocess
		if (group.getProcess("measurement_data_calibration_TYPE") != null) {
			validateGroup_NXentry_measurement_data_calibration_TYPE(group.getProcess("measurement_data_calibration_TYPE"));
		}

		// validate optional child group 'derived_parameters' of type NXprocess
		if (group.getProcess("derived_parameters") != null) {
			validateGroup_NXentry_derived_parameters(group.getProcess("derived_parameters"));
		}
	}

	/**
	 * Validate optional group 'beam_ref_frame' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_beam_ref_frame(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_ref_frame", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'depends_on' of type NX_CHAR.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate unnamed child group of type NXtransformations (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXtransformations.class, false, true);
		final Map<String, NXtransformations> allTransformations = group.getAllTransformations();
		for (final NXtransformations transformations : allTransformations.values()) {
			validateGroup_NXentry_beam_ref_frame_NXtransformations(transformations);
		}
		// validate NXtransformations groups (special case)
		validateTransformations(allTransformations, depends_on);
	}

	/**
	 * Validate unnamed group of type NXtransformations.
	 */
	private void validateGroup_NXentry_beam_ref_frame_NXtransformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'sample_normal_ref_frame' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_sample_normal_ref_frame(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_normal_ref_frame", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'depends_on' of type NX_CHAR.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate unnamed child group of type NXtransformations (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXtransformations.class, false, true);
		final Map<String, NXtransformations> allTransformations = group.getAllTransformations();
		for (final NXtransformations transformations : allTransformations.values()) {
			validateGroup_NXentry_sample_normal_ref_frame_NXtransformations(transformations);
		}
		// validate NXtransformations groups (special case)
		validateTransformations(allTransformations, depends_on);
	}

	/**
	 * Validate unnamed group of type NXtransformations.
	 */
	private void validateGroup_NXentry_sample_normal_ref_frame_NXtransformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXuser.
	 */
	private void validateGroup_NXentry_NXuser(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXuser.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate optional field 'angle_reference_frame' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset angle_reference_frame = group.getLazyDataset("angle_reference_frame");
				if (angle_reference_frame != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angle_reference_frame", angle_reference_frame, NX_CHAR);
			validateFieldEnumeration("angle_reference_frame", angle_reference_frame,
					"beam centered",
					"sample-normal centered");
		}

		// validate optional field 'omega' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset omega = group.getLazyDataset("omega");
				if (omega != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("omega", omega, NX_NUMBER);
			validateFieldUnits("omega", group.getDataNode("omega"), NX_ANGLE);
		}

		// validate optional field 'twotheta' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset twotheta = group.getLazyDataset("twotheta");
				if (twotheta != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("twotheta", twotheta, NX_NUMBER);
			validateFieldUnits("twotheta", group.getDataNode("twotheta"), NX_ANGLE);
		}

		// validate optional field 'chi' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset chi = group.getLazyDataset("chi");
				if (chi != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("chi", chi, NX_NUMBER);
			validateFieldUnits("chi", group.getDataNode("chi"), NX_ANGLE);
		}

		// validate optional field 'phi' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset phi = group.getLazyDataset("phi");
				if (phi != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("phi", phi, NX_NUMBER);
			validateFieldUnits("phi", group.getDataNode("phi"), NX_ANGLE);
		}

		// validate optional field 'angle_of_incidence' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angle_of_incidence = group.getLazyDataset("angle_of_incidence");
				if (angle_of_incidence != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angle_of_incidence", angle_of_incidence, NX_NUMBER);
			validateFieldUnits("angle_of_incidence", group.getDataNode("angle_of_incidence"), NX_ANGLE);
		}

		// validate optional field 'angle_of_detection' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angle_of_detection = group.getLazyDataset("angle_of_detection");
				if (angle_of_detection != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angle_of_detection", angle_of_detection, NX_NUMBER);
			validateFieldUnits("angle_of_detection", group.getDataNode("angle_of_detection"), NX_ANGLE);
		}

		// validate optional field 'angle_of_incident_and_detection_beam' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angle_of_incident_and_detection_beam = group.getLazyDataset("angle_of_incident_and_detection_beam");
				if (angle_of_incident_and_detection_beam != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angle_of_incident_and_detection_beam", angle_of_incident_and_detection_beam, NX_NUMBER);
			validateFieldUnits("angle_of_incident_and_detection_beam", group.getDataNode("angle_of_incident_and_detection_beam"), NX_ANGLE);
		}

		// validate optional field 'angle_of_in_plane_sample_rotation' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angle_of_in_plane_sample_rotation = group.getLazyDataset("angle_of_in_plane_sample_rotation");
				if (angle_of_in_plane_sample_rotation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angle_of_in_plane_sample_rotation", angle_of_in_plane_sample_rotation, NX_NUMBER);
			validateFieldUnits("angle_of_in_plane_sample_rotation", group.getDataNode("angle_of_in_plane_sample_rotation"), NX_ANGLE);
		}

		// validate optional field 'lateral_focal_point_offset' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset lateral_focal_point_offset = group.getLazyDataset("lateral_focal_point_offset");
				if (lateral_focal_point_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("lateral_focal_point_offset", lateral_focal_point_offset, NX_NUMBER);
			validateFieldUnits("lateral_focal_point_offset", group.getDataNode("lateral_focal_point_offset"), NX_LENGTH);
		}

		// validate child group 'beam_TYPE' of type NXbeam
		validateGroup_NXentry_NXinstrument_beam_TYPE(group.getBeam("beam_TYPE"));

		// validate child group 'detector_TYPE' of type NXdetector
		validateGroup_NXentry_NXinstrument_detector_TYPE(group.getDetector("detector_TYPE"));

		// validate optional child group 'source_TYPE' of type NXsource
		if (group.getSource("source_TYPE") != null) {
			validateGroup_NXentry_NXinstrument_source_TYPE(group.getSource("source_TYPE"));
		}

		// validate unnamed child group of type NXmonochromator (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmonochromator.class, false, true);
		final Map<String, NXmonochromator> allMonochromator = group.getAllMonochromator();
		for (final NXmonochromator monochromator : allMonochromator.values()) {
			validateGroup_NXentry_NXinstrument_NXmonochromator(monochromator);
		}

		// validate optional child group 'generic_beam_sample_angle_TYPE' of type NXtransformations
		if (group.getChild("generic_beam_sample_angle_TYPE", NXtransformations.class) != null) {
			validateGroup_NXentry_NXinstrument_generic_beam_sample_angle_TYPE(group.getChild("generic_beam_sample_angle_TYPE", NXtransformations.class));
		}

		// validate unnamed child group of type NXcomponent (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcomponent.class, false, true);
		final Map<String, NXcomponent> allComponent = group.getChildren(NXcomponent.class);
		for (final NXcomponent component : allComponent.values()) {
			validateGroup_NXentry_NXinstrument_NXcomponent(component);
		}

		// validate unnamed child group of type NXoptical_lens (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXoptical_lens.class, false, true);
		final Map<String, NXoptical_lens> allOptical_lens = group.getChildren(NXoptical_lens.class);
		for (final NXoptical_lens optical_lens : allOptical_lens.values()) {
			validateGroup_NXentry_NXinstrument_NXoptical_lens(optical_lens);
		}

		// validate unnamed child group of type NXwaveplate (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXwaveplate.class, false, true);
		final Map<String, NXwaveplate> allWaveplate = group.getChildren(NXwaveplate.class);
		for (final NXwaveplate waveplate : allWaveplate.values()) {
			validateGroup_NXentry_NXinstrument_NXwaveplate(waveplate);
		}

		// validate unnamed child group of type NXoptical_window (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXoptical_window.class, false, true);
		final Map<String, NXoptical_window> allOptical_window = group.getChildren(NXoptical_window.class);
		for (final NXoptical_window optical_window : allOptical_window.values()) {
			validateGroup_NXentry_NXinstrument_NXoptical_window(optical_window);
		}

		// validate optional child group 'polfilter_TYPE' of type NXcomponent
		if (group.getChild("polfilter_TYPE", NXcomponent.class) != null) {
			validateGroup_NXentry_NXinstrument_polfilter_TYPE(group.getChild("polfilter_TYPE", NXcomponent.class));
		}

		// validate optional child group 'spectralfilter_TYPE' of type NXcomponent
		if (group.getChild("spectralfilter_TYPE", NXcomponent.class) != null) {
			validateGroup_NXentry_NXinstrument_spectralfilter_TYPE(group.getChild("spectralfilter_TYPE", NXcomponent.class));
		}

		// validate unnamed child group of type NXbeam_transfer_matrix_table (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXbeam_transfer_matrix_table.class, false, true);
		final Map<String, NXbeam_transfer_matrix_table> allBeam_transfer_matrix_table = group.getChildren(NXbeam_transfer_matrix_table.class);
		for (final NXbeam_transfer_matrix_table beam_transfer_matrix_table : allBeam_transfer_matrix_table.values()) {
			validateGroup_NXentry_NXinstrument_NXbeam_transfer_matrix_table(beam_transfer_matrix_table);
		}

		// validate optional child group 'sample_stage' of type NXmanipulator
		if (group.getChild("sample_stage", NXmanipulator.class) != null) {
			validateGroup_NXentry_NXinstrument_sample_stage(group.getChild("sample_stage", NXmanipulator.class));
		}

		// validate optional child group 'temperature_sensor' of type NXsensor
		if (group.getSensor("temperature_sensor") != null) {
			validateGroup_NXentry_NXinstrument_temperature_sensor(group.getSensor("temperature_sensor"));
		}

		// validate optional child group 'temp_control_TYPE' of type NXactuator
		if (group.getActuator("temp_control_TYPE") != null) {
			validateGroup_NXentry_NXinstrument_temp_control_TYPE(group.getActuator("temp_control_TYPE"));
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getFabrication("device_information") != null) {
			validateGroup_NXentry_NXinstrument_device_information(group.getFabrication("device_information"));
		}

		// validate optional child group 'software_TYPE' of type NXprogram
		if (group.getChild("software_TYPE", NXprogram.class) != null) {
			validateGroup_NXentry_NXinstrument_software_TYPE(group.getChild("software_TYPE", NXprogram.class));
		}

		// validate optional child group 'instrument_calibration_DEVICE' of type NXcalibration
		if (group.getChild("instrument_calibration_DEVICE", NXcalibration.class) != null) {
			validateGroup_NXentry_NXinstrument_instrument_calibration_DEVICE(group.getChild("instrument_calibration_DEVICE", NXcalibration.class));
		}

		// validate optional child group 'wavelength_resolution' of type NXresolution
		if (group.getChild("wavelength_resolution", NXresolution.class) != null) {
			validateGroup_NXentry_NXinstrument_wavelength_resolution(group.getChild("wavelength_resolution", NXresolution.class));
		}
	}

	/**
	 * Validate group 'beam_TYPE' of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_beam_TYPE(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_TYPE", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'parameter_reliability' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset parameter_reliability = group.getLazyDataset("parameter_reliability");
		validateFieldNotNull("parameter_reliability", parameter_reliability);
		if (parameter_reliability != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("parameter_reliability", parameter_reliability, NX_CHAR);
			validateFieldEnumeration("parameter_reliability", parameter_reliability,
					"measured",
					"nominal");
		}

		// validate optional field 'incident_wavelength' of type NX_NUMBER.
		final ILazyDataset incident_wavelength = group.getLazyDataset("incident_wavelength");
				if (incident_wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength", incident_wavelength, NX_NUMBER);
			validateFieldUnits("incident_wavelength", group.getDataNode("incident_wavelength"), NX_WAVELENGTH);
		}

		// validate optional field 'incident_wavelength_spread' of type NX_NUMBER.
		final ILazyDataset incident_wavelength_spread = group.getLazyDataset("incident_wavelength_spread");
				if (incident_wavelength_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength_spread", incident_wavelength_spread, NX_NUMBER);
			validateFieldUnits("incident_wavelength_spread", group.getDataNode("incident_wavelength_spread"), NX_WAVELENGTH);
			validateFieldRank("incident_wavelength_spread", incident_wavelength_spread, 1);
			validateFieldDimensions("incident_wavelength_spread", incident_wavelength_spread, "NXbeam", "nP");
		}

		// validate optional field 'incident_polarization' of type NX_NUMBER.
		final ILazyDataset incident_polarization = group.getLazyDataset("incident_polarization");
				if (incident_polarization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_polarization", incident_polarization, NX_NUMBER);
			validateFieldUnits("incident_polarization", group.getDataNode("incident_polarization"), NX_ANY);
			validateFieldRank("incident_polarization", incident_polarization, 2);
			validateFieldDimensions("incident_polarization", incident_polarization, "NXbeam", "nP", 2);
		}

		// validate optional field 'extent' of type NX_FLOAT.
		final ILazyDataset extent = group.getLazyDataset("extent");
				if (extent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extent", extent, NX_FLOAT);
			validateFieldUnits("extent", group.getDataNode("extent"), NX_LENGTH);
			validateFieldRank("extent", extent, 2);
			validateFieldDimensions("extent", extent, "NXbeam", "nP", 2);
		}

		// validate optional field 'associated_source' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset associated_source = group.getLazyDataset("associated_source");
				if (associated_source != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("associated_source", associated_source, NX_CHAR);
		}

		// validate optional field 'beam_polarization_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset beam_polarization_type = group.getLazyDataset("beam_polarization_type");
				if (beam_polarization_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_polarization_type", beam_polarization_type, NX_CHAR);
			validateFieldEnumeration("beam_polarization_type", beam_polarization_type,
					"linear",
					"circular",
					"elliptically",
					"unpolarized");
		}

		// validate optional field 'linear_beam_sample_polarization' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset linear_beam_sample_polarization = group.getLazyDataset("linear_beam_sample_polarization");
				if (linear_beam_sample_polarization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("linear_beam_sample_polarization", linear_beam_sample_polarization, NX_NUMBER);
			validateFieldUnits("linear_beam_sample_polarization", group.getDataNode("linear_beam_sample_polarization"), NX_ANGLE);
		}
	}

	/**
	 * Validate group 'detector_TYPE' of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_detector_TYPE(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("detector_TYPE", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'detector_channel_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset detector_channel_type = group.getLazyDataset("detector_channel_type");
		validateFieldNotNull("detector_channel_type", detector_channel_type);
		if (detector_channel_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("detector_channel_type", detector_channel_type, NX_CHAR);
			validateFieldEnumeration("detector_channel_type", detector_channel_type,
					"single-channel",
					"multichannel");
		}

		// validate optional field 'detector_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset detector_type = group.getLazyDataset("detector_type");
				if (detector_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("detector_type", detector_type, NX_CHAR);
			validateFieldEnumeration("detector_type", detector_type,
					"CCD",
					"photomultiplier",
					"photodiode",
					"avalanche-photodiode",
					"streak camera",
					"bolometer",
					"golay detectors",
					"pyroelectric detector",
					"deuterated triglycine sulphate");
		}

		// validate optional field 'additional_detector_hardware' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset additional_detector_hardware = group.getLazyDataset("additional_detector_hardware");
				if (additional_detector_hardware != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("additional_detector_hardware", additional_detector_hardware, NX_CHAR);
		}

		// validate optional child group 'raw_data' of type NXdata
		if (group.getChild("raw_data", NXdata.class) != null) {
			validateGroup_NXentry_NXinstrument_detector_TYPE_raw_data(group.getChild("raw_data", NXdata.class));
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getChild("device_information", NXfabrication.class) != null) {
			validateGroup_NXentry_NXinstrument_detector_TYPE_device_information(group.getChild("device_information", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'raw_data' of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_detector_TYPE_raw_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("raw_data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"raw");

		// validate field 'raw' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset raw = group.getLazyDataset("raw");
		validateFieldNotNull("raw", raw);
		if (raw != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("raw", raw, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_detector_TYPE_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

	}

	/**
	 * Validate optional group 'source_TYPE' of type NXsource.
	 */
	private void validateGroup_NXentry_NXinstrument_source_TYPE(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source_TYPE", NXsource.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"Synchrotron X-ray Source",
					"Rotating Anode X-ray",
					"Fixed Tube X-ray",
					"UV Laser",
					"Optical Laser",
					"Laser",
					"Dye-Laser",
					"Broadband Tunable Light Source",
					"Halogen lamp",
					"LED",
					"Mercury Cadmium Telluride",
					"Deuterium Lamp",
					"Xenon Lamp",
					"Globar");
		}

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'standard' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset standard = group.getLazyDataset("standard");
				if (standard != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("standard", standard, NX_CHAR);
		}

		// validate optional field 'associated_beam' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset associated_beam = group.getLazyDataset("associated_beam");
				if (associated_beam != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("associated_beam", associated_beam, NX_CHAR);
		}
		// validate optional child group 'device_information' of type NXfabrication
		if (group.getFabrication("device_information") != null) {
			validateGroup_NXentry_NXinstrument_source_TYPE_device_information(group.getFabrication("device_information"));
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_source_TYPE_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

	}

	/**
	 * Validate optional unnamed group of type NXmonochromator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmonochromator(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmonochromator.class, group))) return;

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getChild("device_information", NXfabrication.class) != null) {
			validateGroup_NXentry_NXinstrument_NXmonochromator_device_information(group.getChild("device_information", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmonochromator_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

	}

	/**
	 * Validate optional group 'generic_beam_sample_angle_TYPE' of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXinstrument_generic_beam_sample_angle_TYPE(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("generic_beam_sample_angle_TYPE", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"incident beam",
					"detection beam",
					"sample");
		}

		// validate field 'polar' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset polar = group.getLazyDataset("polar");
		validateFieldNotNull("polar", polar);
		if (polar != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("polar", polar, NX_NUMBER);
			validateFieldUnits("polar", group.getDataNode("polar"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'polar' of type NX_CHAR.
		final Attribute polar_attr_transformation_type = group.getDataNode("polar").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", polar_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", polar_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", polar_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'polar' of type NX_CHAR.
		final Attribute polar_attr_vector = group.getDataNode("polar").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", polar_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", polar_attr_vector, NX_CHAR);
		validateAttributeEnumeration("vector", polar_attr_vector,
				"[0, 1, 0]");

		// validate attribute 'depends_on' of field 'polar' of type NX_CHAR.
		final Attribute polar_attr_depends_on = group.getDataNode("polar").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", polar_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", polar_attr_depends_on, NX_CHAR);

		}

		// validate field 'azimuth' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset azimuth = group.getLazyDataset("azimuth");
		validateFieldNotNull("azimuth", azimuth);
		if (azimuth != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("azimuth", azimuth, NX_NUMBER);
			validateFieldUnits("azimuth", group.getDataNode("azimuth"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'azimuth' of type NX_CHAR.
		final Attribute azimuth_attr_transformation_type = group.getDataNode("azimuth").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", azimuth_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", azimuth_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", azimuth_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'azimuth' of type NX_CHAR.
		final Attribute azimuth_attr_vector = group.getDataNode("azimuth").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", azimuth_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", azimuth_attr_vector, NX_CHAR);
		validateAttributeEnumeration("vector", azimuth_attr_vector,
				"[0, 0, 1]");

		// validate attribute 'depends_on' of field 'azimuth' of type NX_CHAR.
		final Attribute azimuth_attr_depends_on = group.getDataNode("azimuth").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", azimuth_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", azimuth_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", azimuth_attr_depends_on,
				"offset_tilt");

		}
	}

	/**
	 * Validate optional unnamed group of type NXcomponent.
	 */
	private void validateGroup_NXentry_NXinstrument_NXcomponent(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcomponent.class, group))) return;

	}

	/**
	 * Validate optional unnamed group of type NXoptical_lens.
	 */
	private void validateGroup_NXentry_NXinstrument_NXoptical_lens(final NXoptical_lens group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXoptical_lens.class, group))) return;
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

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getChild("device_information", NXfabrication.class) != null) {
			validateGroup_NXentry_NXinstrument_NXoptical_lens_device_information(group.getChild("device_information", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_NXoptical_lens_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

	}

	/**
	 * Validate optional unnamed group of type NXwaveplate.
	 */
	private void validateGroup_NXentry_NXinstrument_NXwaveplate(final NXwaveplate group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXwaveplate.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXoptical_window.
	 */
	private void validateGroup_NXentry_NXinstrument_NXoptical_window(final NXoptical_window group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXoptical_window.class, group))) return;

	}

	/**
	 * Validate optional group 'polfilter_TYPE' of type NXcomponent.
	 */
	private void validateGroup_NXentry_NXinstrument_polfilter_TYPE(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("polfilter_TYPE", NXcomponent.class, group))) return;

		// validate optional field 'filter_mechanism' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset filter_mechanism = group.getLazyDataset("filter_mechanism");
				if (filter_mechanism != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("filter_mechanism", filter_mechanism, NX_CHAR);
			validateFieldEnumeration("filter_mechanism", filter_mechanism,
					"polarization by Fresnel reflection",
					"birefringent polarizers",
					"thin film polarizers",
					"wire-grid polarizers");
		}

		// validate optional field 'specific_polarization_filter_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset specific_polarization_filter_type = group.getLazyDataset("specific_polarization_filter_type");
				if (specific_polarization_filter_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("specific_polarization_filter_type", specific_polarization_filter_type, NX_CHAR);
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getFabrication("device_information") != null) {
			validateGroup_NXentry_NXinstrument_polfilter_TYPE_device_information(group.getFabrication("device_information"));
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_polfilter_TYPE_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

	}

	/**
	 * Validate optional group 'spectralfilter_TYPE' of type NXcomponent.
	 */
	private void validateGroup_NXentry_NXinstrument_spectralfilter_TYPE(final NXcomponent group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spectralfilter_TYPE", NXcomponent.class, group))) return;

		// validate optional field 'filter_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset filter_type = group.getLazyDataset("filter_type");
				if (filter_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("filter_type", filter_type, NX_CHAR);
			validateFieldEnumeration("filter_type", filter_type,
					"long-pass filter",
					"short-pass filter",
					"notch filter",
					"reflection filter",
					"neutral density filter");
		}

		// validate optional field 'intended_use' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset intended_use = group.getLazyDataset("intended_use");
				if (intended_use != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intended_use", intended_use, NX_CHAR);
			validateFieldEnumeration("intended_use", intended_use,
					"laser line cleanup",
					"raylight line removal",
					"spectral filtering",
					"intensity manipulation");
		}

		// validate optional child group 'filter_characteristics' of type NXdata
		if (group.getChild("filter_characteristics", NXdata.class) != null) {
			validateGroup_NXentry_NXinstrument_spectralfilter_TYPE_filter_characteristics(group.getChild("filter_characteristics", NXdata.class));
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getFabrication("device_information") != null) {
			validateGroup_NXentry_NXinstrument_spectralfilter_TYPE_device_information(group.getFabrication("device_information"));
		}
	}

	/**
	 * Validate optional group 'filter_characteristics' of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_spectralfilter_TYPE_filter_characteristics(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("filter_characteristics", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional attribute 'characteristics_type' of type NX_CHAR.
		final Attribute characteristics_type_attr = group.getAttribute("characteristics_type");
		if (characteristics_type_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("characteristics_type", characteristics_type_attr, NX_CHAR);
			validateAttributeEnumeration("characteristics_type", characteristics_type_attr,
					"transmission",
					"reflection");
		}

	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_spectralfilter_TYPE_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

	}

	/**
	 * Validate optional unnamed group of type NXbeam_transfer_matrix_table.
	 */
	private void validateGroup_NXentry_NXinstrument_NXbeam_transfer_matrix_table(final NXbeam_transfer_matrix_table group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXbeam_transfer_matrix_table.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'sample_stage' of type NXmanipulator.
	 */
	private void validateGroup_NXentry_NXinstrument_sample_stage(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_stage", NXmanipulator.class, group))) return;

		// validate optional field 'stage_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset stage_type = group.getLazyDataset("stage_type");
				if (stage_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("stage_type", stage_type, NX_CHAR);
			validateFieldEnumeration("stage_type", stage_type,
					"manual stage",
					"scanning stage",
					"liquid stage",
					"gas cell",
					"cryostat",
					"heater");
		}

		// validate optional field 'beam_sample_relation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset beam_sample_relation = group.getLazyDataset("beam_sample_relation");
				if (beam_sample_relation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_sample_relation", beam_sample_relation, NX_CHAR);
		}

		// validate optional child group 'transformations' of type NXtransformations
		if (group.getChild("transformations", NXtransformations.class) != null) {
			validateGroup_NXentry_NXinstrument_sample_stage_transformations(group.getChild("transformations", NXtransformations.class));
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getChild("device_information", NXfabrication.class) != null) {
			validateGroup_NXentry_NXinstrument_sample_stage_device_information(group.getChild("device_information", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'transformations' of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXinstrument_sample_stage_transformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transformations", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_sample_stage_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

	}

	/**
	 * Validate optional group 'temperature_sensor' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXinstrument_temperature_sensor(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("temperature_sensor", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'measurement' of type NX_CHAR.
		final ILazyDataset measurement = group.getLazyDataset("measurement");
		validateFieldNotNull("measurement", measurement);
		if (measurement != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measurement", measurement, NX_CHAR);
			validateFieldEnumeration("measurement", measurement,
					"temperature");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_ANY);
			validateFieldDimensions("value", value, "NXsensor", "n");
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getChild("device_information", NXfabrication.class) != null) {
			validateGroup_NXentry_NXinstrument_temperature_sensor_device_information(group.getChild("device_information", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_temperature_sensor_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

	}

	/**
	 * Validate optional group 'temp_control_TYPE' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXinstrument_temp_control_TYPE(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("temp_control_TYPE", NXactuator.class, group))) return;

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'physical_quantity' of type NX_CHAR.
		final ILazyDataset physical_quantity = group.getLazyDataset("physical_quantity");
		validateFieldNotNull("physical_quantity", physical_quantity);
		if (physical_quantity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_quantity", physical_quantity, NX_CHAR);
			validateFieldEnumeration("physical_quantity", physical_quantity,
					"temperature");
		}

		// validate optional field 'cooler_or_heater' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset cooler_or_heater = group.getLazyDataset("cooler_or_heater");
				if (cooler_or_heater != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cooler_or_heater", cooler_or_heater, NX_CHAR);
			validateFieldEnumeration("cooler_or_heater", cooler_or_heater,
					"cooler",
					"heater");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate unnamed child group of type NXpid_controller (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXpid_controller.class, false, true);
		final Map<String, NXpid_controller> allPid_controller = group.getAllPid_controller();
		for (final NXpid_controller pid_controller : allPid_controller.values()) {
			validateGroup_NXentry_NXinstrument_temp_control_TYPE_NXpid_controller(pid_controller);
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getChild("device_information", NXfabrication.class) != null) {
			validateGroup_NXentry_NXinstrument_temp_control_TYPE_device_information(group.getChild("device_information", NXfabrication.class));
		}
	}

	/**
	 * Validate optional unnamed group of type NXpid_controller.
	 */
	private void validateGroup_NXentry_NXinstrument_temp_control_TYPE_NXpid_controller(final NXpid_controller group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXpid_controller.class, group))) return;

		// validate optional field 'setpoint' of type NX_FLOAT.
		final ILazyDataset setpoint = group.getLazyDataset("setpoint");
				if (setpoint != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("setpoint", setpoint, NX_FLOAT);
			validateFieldUnits("setpoint", group.getDataNode("setpoint"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_temp_control_TYPE_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

		// validate optional field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
				if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate optional field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
				if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}

		// validate optional field 'construction_year' of type NX_DATE_TIME. Note: field not defined in base class.
		final ILazyDataset construction_year = group.getLazyDataset("construction_year");
				if (construction_year != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("construction_year", construction_year, NX_DATE_TIME);
		}
	}

	/**
	 * Validate optional group 'software_TYPE' of type NXprogram.
	 */
	private void validateGroup_NXentry_NXinstrument_software_TYPE(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("software_TYPE", NXprogram.class, group))) return;

		// validate field 'program' of type NX_CHAR.
		final ILazyDataset program = group.getLazyDataset("program");
		validateFieldNotNull("program", program);
		if (program != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("program", program, NX_CHAR);
		// validate attribute 'version' of field 'program' of type NX_CHAR.
		final Attribute program_attr_version = group.getDataNode("program").getAttribute("version");
		if (!(validateAttributeNotNull("version", program_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", program_attr_version, NX_CHAR);

		// validate optional attribute 'URL' of field 'program' of type NX_CHAR.
		final Attribute program_attr_URL = group.getDataNode("program").getAttribute("URL");
		if (program_attr_URL != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("URL", program_attr_URL, NX_CHAR);
		}

		}
	}

	/**
	 * Validate optional group 'instrument_calibration_DEVICE' of type NXcalibration.
	 */
	private void validateGroup_NXentry_NXinstrument_instrument_calibration_DEVICE(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument_calibration_DEVICE", NXcalibration.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'device_path' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset device_path = group.getLazyDataset("device_path");
				if (device_path != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("device_path", device_path, NX_CHAR);
		}

		// validate optional field 'calibration_status' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset calibration_status = group.getLazyDataset("calibration_status");
				if (calibration_status != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibration_status", calibration_status, NX_CHAR);
			validateFieldEnumeration("calibration_status", calibration_status,
					"calibration time provided",
					"no calibration",
					"within 1 hour",
					"within 1 day",
					"within 1 week");
		}

		// validate optional field 'calibration_time' of type NX_DATE_TIME. Note: field not defined in base class.
		final ILazyDataset calibration_time = group.getLazyDataset("calibration_time");
				if (calibration_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibration_time", calibration_time, NX_DATE_TIME);
		}

		// validate optional child group 'calibration_accuracy' of type NXdata
		if (group.getData("calibration_accuracy") != null) {
			validateGroup_NXentry_NXinstrument_instrument_calibration_DEVICE_calibration_accuracy(group.getData("calibration_accuracy"));
		}

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXinstrument_instrument_calibration_DEVICE_NXdata(data);
		}
	}

	/**
	 * Validate optional group 'calibration_accuracy' of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_instrument_calibration_DEVICE_calibration_accuracy(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("calibration_accuracy", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_instrument_calibration_DEVICE_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'wavelength_resolution' of type NXresolution.
	 */
	private void validateGroup_NXentry_NXinstrument_wavelength_resolution(final NXresolution group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("wavelength_resolution", NXresolution.class, group))) return;

		// validate field 'physical_quantity' of type NX_CHAR.
		final ILazyDataset physical_quantity = group.getLazyDataset("physical_quantity");
		validateFieldNotNull("physical_quantity", physical_quantity);
		if (physical_quantity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_quantity", physical_quantity, NX_CHAR);
			validateFieldEnumeration("physical_quantity", physical_quantity,
					"wavelength");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"estimated",
					"derived",
					"calibrated",
					"other");
		}

		// validate field 'resolution' of type NX_FLOAT.
		final ILazyDataset resolution = group.getLazyDataset("resolution");
		validateFieldNotNull("resolution", resolution);
		if (resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("resolution", resolution, NX_FLOAT);
			validateFieldUnits("resolution", group.getDataNode("resolution"), NX_WAVELENGTH);
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

		// validate optional field 'sample_id' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset sample_id = group.getLazyDataset("sample_id");
				if (sample_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_id", sample_id, NX_CHAR);
		}

		// validate optional field 'physical_form' of type NX_CHAR.
		final ILazyDataset physical_form = group.getLazyDataset("physical_form");
				if (physical_form != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_form", physical_form, NX_CHAR);
		}

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'chemical_formula' of type NX_CHAR.
		final ILazyDataset chemical_formula = group.getLazyDataset("chemical_formula");
				if (chemical_formula != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("chemical_formula", chemical_formula, NX_CHAR);
		}

		// validate optional field 'atom_types' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset atom_types = group.getLazyDataset("atom_types");
				if (atom_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("atom_types", atom_types, NX_CHAR);
		}

		// validate optional field 'preparation_date' of type NX_DATE_TIME.
		final ILazyDataset preparation_date = group.getLazyDataset("preparation_date");
				if (preparation_date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("preparation_date", preparation_date, NX_DATE_TIME);
		}

		// validate optional field 'thickness' of type NX_NUMBER.
		final ILazyDataset thickness = group.getLazyDataset("thickness");
				if (thickness != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("thickness", thickness, NX_NUMBER);
			validateFieldUnits("thickness", group.getDataNode("thickness"), NX_LENGTH);
		}

		// validate optional field 'thickness_determination' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset thickness_determination = group.getLazyDataset("thickness_determination");
				if (thickness_determination != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("thickness_determination", thickness_determination, NX_CHAR);
		}

		// validate optional field 'layer_structure' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset layer_structure = group.getLazyDataset("layer_structure");
				if (layer_structure != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("layer_structure", layer_structure, NX_CHAR);
		}

		// validate optional field 'sample_orientation' of type NX_FLOAT.
		final ILazyDataset sample_orientation = group.getLazyDataset("sample_orientation");
				if (sample_orientation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_orientation", sample_orientation, NX_FLOAT);
			validateFieldUnits("sample_orientation", group.getDataNode("sample_orientation"), NX_ANGLE);
			validateFieldRank("sample_orientation", sample_orientation, 1);
			validateFieldDimensions("sample_orientation", sample_orientation, "NXsample", 3);
		}

		// validate optional field 'substrate' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset substrate = group.getLazyDataset("substrate");
				if (substrate != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("substrate", substrate, NX_CHAR);
		}
		// validate optional child group 'history' of type NXhistory
		if (group.getHistory() != null) {
			validateGroup_NXentry_NXsample_history(group.getHistory());
		}

		// validate optional child group 'temperature_env' of type NXenvironment
		if (group.getTemperature_env() != null) {
			validateGroup_NXentry_NXsample_temperature_env(group.getTemperature_env());
		}

		// validate unnamed child group of type NXenvironment (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXenvironment.class, false, true);
		final Map<String, NXenvironment> allEnvironment = group.getAllEnvironment();
		for (final NXenvironment environment : allEnvironment.values()) {
			validateGroup_NXentry_NXsample_NXenvironment(environment);
		}
	}

	/**
	 * Validate optional group 'history' of type NXhistory.
	 */
	private void validateGroup_NXentry_NXsample_history(final NXhistory group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("history", NXhistory.class, group))) return;

	}

	/**
	 * Validate optional group 'temperature_env' of type NXenvironment.
	 */
	private void validateGroup_NXentry_NXsample_temperature_env(final NXenvironment group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("temperature_env", NXenvironment.class, group))) return;

		// validate optional field 'temperature_nominal' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset temperature_nominal = group.getLazyDataset("temperature_nominal");
				if (temperature_nominal != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature_nominal", temperature_nominal, NX_CHAR);
			validateFieldEnumeration("temperature_nominal", temperature_nominal,
					"room temperature",
					"liquid helium temperature",
					"liquid nitrogen temperature");
		}

		// validate optional child group 'temperature_sensor' of type NXsensor
		if (group.getSensor("temperature_sensor") != null) {
			validateGroup_NXentry_NXsample_temperature_env_temperature_sensor(group.getSensor("temperature_sensor"));
		}

		// validate optional child group 'sample_heater' of type NXactuator
		if (group.getActuator("sample_heater") != null) {
			validateGroup_NXentry_NXsample_temperature_env_sample_heater(group.getActuator("sample_heater"));
		}

		// validate optional child group 'sample_cooler' of type NXactuator
		if (group.getActuator("sample_cooler") != null) {
			validateGroup_NXentry_NXsample_temperature_env_sample_cooler(group.getActuator("sample_cooler"));
		}
	}

	/**
	 * Validate optional group 'temperature_sensor' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXsample_temperature_env_temperature_sensor(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("temperature_sensor", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'sample_heater' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXsample_temperature_env_sample_heater(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_heater", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional group 'sample_cooler' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXsample_temperature_env_sample_cooler(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_cooler", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional unnamed group of type NXenvironment.
	 */
	private void validateGroup_NXentry_NXsample_NXenvironment(final NXenvironment group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXenvironment.class, group))) return;

		// validate optional field 'sample_medium' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset sample_medium = group.getLazyDataset("sample_medium");
				if (sample_medium != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_medium", sample_medium, NX_CHAR);
			validateFieldEnumeration("sample_medium", sample_medium,
					"air",
					"vacuum",
					"inert atmosphere",
					"oxidising atmosphere",
					"reducing atmosphere",
					"sealed can",
					"water");
		}

		// validate optional field 'sample_medium_refractive_indices' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset sample_medium_refractive_indices = group.getLazyDataset("sample_medium_refractive_indices");
				if (sample_medium_refractive_indices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_medium_refractive_indices", sample_medium_refractive_indices, NX_FLOAT);
			validateFieldUnits("sample_medium_refractive_indices", group.getDataNode("sample_medium_refractive_indices"), NX_UNITLESS);
			validateFieldRank("sample_medium_refractive_indices", sample_medium_refractive_indices, 2);
			validateFieldDimensions("sample_medium_refractive_indices", sample_medium_refractive_indices, null, 2, "N_spectrum");
		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);

	}

	/**
	 * Validate optional group 'measurement_data_calibration_TYPE' of type NXprocess.
	 */
	private void validateGroup_NXentry_measurement_data_calibration_TYPE(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("measurement_data_calibration_TYPE", NXprocess.class, group))) return;

		// validate optional child group 'wavelength_calibration' of type NXcalibration
		if (group.getChild("wavelength_calibration", NXcalibration.class) != null) {
			validateGroup_NXentry_measurement_data_calibration_TYPE_wavelength_calibration(group.getChild("wavelength_calibration", NXcalibration.class));
		}
	}

	/**
	 * Validate optional group 'wavelength_calibration' of type NXcalibration.
	 */
	private void validateGroup_NXentry_measurement_data_calibration_TYPE_wavelength_calibration(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("wavelength_calibration", NXcalibration.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'calibrated_axis' of type NX_FLOAT.
		final ILazyDataset calibrated_axis = group.getLazyDataset("calibrated_axis");
				if (calibrated_axis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibrated_axis", calibrated_axis, NX_FLOAT);
			validateFieldUnits("calibrated_axis", group.getDataNode("calibrated_axis"), NX_ANY);
			validateFieldRank("calibrated_axis", calibrated_axis, 1);
			validateFieldDimensions("calibrated_axis", calibrated_axis, "NXcalibration", "ncal");
		}
	}

	/**
	 * Validate optional group 'derived_parameters' of type NXprocess.
	 */
	private void validateGroup_NXentry_derived_parameters(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("derived_parameters", NXprocess.class, group))) return;

		// validate optional field 'depolarization' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset depolarization = group.getLazyDataset("depolarization");
				if (depolarization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depolarization", depolarization, NX_NUMBER);
			validateFieldUnits("depolarization", group.getDataNode("depolarization"), NX_UNITLESS);
			validateFieldRank("depolarization", depolarization, 3);
			validateFieldDimensions("depolarization", depolarization, null, "N_measurements", 1, "N_spectrum");
		}

		// validate optional field 'jones_quality_factor' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset jones_quality_factor = group.getLazyDataset("jones_quality_factor");
				if (jones_quality_factor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("jones_quality_factor", jones_quality_factor, NX_NUMBER);
			validateFieldUnits("jones_quality_factor", group.getDataNode("jones_quality_factor"), NX_UNITLESS);
			validateFieldRank("jones_quality_factor", jones_quality_factor, 3);
			validateFieldDimensions("jones_quality_factor", jones_quality_factor, null, "N_measurements", 1, "N_spectrum");
		}

		// validate optional field 'reflectivity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset reflectivity = group.getLazyDataset("reflectivity");
				if (reflectivity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("reflectivity", reflectivity, NX_NUMBER);
			validateFieldUnits("reflectivity", group.getDataNode("reflectivity"), NX_UNITLESS);
			validateFieldRank("reflectivity", reflectivity, 3);
			validateFieldDimensions("reflectivity", reflectivity, null, "N_measurements", 1, "N_spectrum");
		}

		// validate optional field 'transmittance' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset transmittance = group.getLazyDataset("transmittance");
				if (transmittance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("transmittance", transmittance, NX_NUMBER);
			validateFieldUnits("transmittance", group.getDataNode("transmittance"), NX_UNITLESS);
			validateFieldRank("transmittance", transmittance, 3);
			validateFieldDimensions("transmittance", transmittance, null, "N_measurements", 1, "N_spectrum");
		}

		// validate optional child group 'ANALYSIS_program' of type NXprogram
		if (group.getChild("ANALYSIS_program", NXprogram.class) != null) {
			validateGroup_NXentry_derived_parameters_ANALYSIS_program(group.getChild("ANALYSIS_program", NXprogram.class));
		}
	}

	/**
	 * Validate optional group 'ANALYSIS_program' of type NXprogram.
	 */
	private void validateGroup_NXentry_derived_parameters_ANALYSIS_program(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ANALYSIS_program", NXprogram.class, group))) return;

		// validate field 'program' of type NX_CHAR.
		final ILazyDataset program = group.getLazyDataset("program");
		validateFieldNotNull("program", program);
		if (program != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("program", program, NX_CHAR);
		// validate attribute 'version' of field 'program' of type NX_CHAR.
		final Attribute program_attr_version = group.getDataNode("program").getAttribute("version");
		if (!(validateAttributeNotNull("version", program_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", program_attr_version, NX_CHAR);

		}
	}
}
