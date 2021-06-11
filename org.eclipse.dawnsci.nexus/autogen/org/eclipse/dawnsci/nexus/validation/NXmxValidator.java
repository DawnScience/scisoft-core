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
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXtransformations;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXattenuator;
import org.eclipse.dawnsci.nexus.NXdetector_group;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdetector_module;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXsource;

/**
 * Validator for the application definition 'NXmx'.
 */
public class NXmxValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXmxValidator() {
		super(NexusApplicationDefinition.NX_MX);
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

		// validate optional attribute 'version'
		final Attribute version_attr = group.getAttribute("version");
		if (version_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("version", version_attr, NX_CHAR);
			validateAttributeEnumeration("version", version_attr,
					"1.0");
		}

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		validateFieldNotNull("start_time", start_time);
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

		// validate field 'end_time_estimated' of type NX_DATE_TIME. Note: field not defined in base class.
		final ILazyDataset end_time_estimated = group.getLazyDataset("end_time_estimated");
		validateFieldNotNull("end_time_estimated", end_time_estimated);
		if (end_time_estimated != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time_estimated", end_time_estimated, NX_DATE_TIME);
		}

		// validate field 'definition' of unknown type.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXmx");
		}

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXsource (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSource = group.getChildren(NXsource.class);
		for (final NXsource source : allSource.values()) {
			validateGroup_NXentry_NXsource(source);
		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'data' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset data = group.getLazyDataset("data");
				if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldDimensions("data", data, null, "nP", "i", "j", "k");
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

		// validate field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate optional field 'temperature' of type NX_NUMBER.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
				if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_NUMBER);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");
		}
		// validate NXtransformations groups (special case)
		final Map<String, NXtransformations> allTransformations = group.getChildren(NXtransformations.class);
		validateTransformations(allTransformations, depends_on);
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		// validate attribute 'short_name' of field 'name'
		final Attribute name_attr_short_name = group.getDataNode("name").getAttribute("short_name");
		if (!(validateAttributeNotNull("short_name", name_attr_short_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("short_name", name_attr_short_name, NX_CHAR);

		}

		// validate optional field 'time_zone' of type NX_DATE_TIME. Note: field not defined in base class.
		final ILazyDataset time_zone = group.getLazyDataset("time_zone");
				if (time_zone != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time_zone", time_zone, NX_DATE_TIME);
		}

		// validate unnamed child group of type NXattenuator (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXattenuator.class, false, true);
		final Map<String, NXattenuator> allAttenuator = group.getAllAttenuator();
		for (final NXattenuator attenuator : allAttenuator.values()) {
			validateGroup_NXentry_NXinstrument_NXattenuator(attenuator);
		}

		// validate unnamed child group of type NXdetector_group (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector_group.class, false, true);
		final Map<String, NXdetector_group> allDetector_group = group.getAllDetector_group();
		for (final NXdetector_group detector_group : allDetector_group.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector_group(detector_group);
		}

		// validate unnamed child group of type NXdetector (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector.class, false, true);
		final Map<String, NXdetector> allDetector = group.getAllDetector();
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector(detector);
		}

		// validate unnamed child group of type NXbeam (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXbeam.class, false, true);
		final Map<String, NXbeam> allBeam = group.getAllBeam();
		for (final NXbeam beam : allBeam.values()) {
			validateGroup_NXentry_NXinstrument_NXbeam(beam);
		}
	}

	/**
	 * Validate optional unnamed group of type NXattenuator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXattenuator(final NXattenuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXattenuator.class, group))) return;

		// validate optional field 'attenuator_transmission' of type NX_NUMBER.
		final ILazyDataset attenuator_transmission = group.getLazyDataset("attenuator_transmission");
				if (attenuator_transmission != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("attenuator_transmission", attenuator_transmission, NX_NUMBER);
			validateFieldUnits("attenuator_transmission", group.getDataNode("attenuator_transmission"), NX_UNITLESS);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdetector_group.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector_group(final NXdetector_group group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector_group.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'group_names' of type NX_CHAR.
		final ILazyDataset group_names = group.getLazyDataset("group_names");
		validateFieldNotNull("group_names", group_names);
		if (group_names != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("group_names", group_names, NX_CHAR);
		}

		// validate field 'group_index' of type NX_INT.
		final ILazyDataset group_index = group.getLazyDataset("group_index");
		validateFieldNotNull("group_index", group_index);
		if (group_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("group_index", group_index, NX_INT);
			validateFieldRank("group_index", group_index, 1);
			validateFieldDimensions("group_index", group_index, null, "i");
		}

		// validate field 'group_parent' of type NX_INT.
		final ILazyDataset group_parent = group.getLazyDataset("group_parent");
		validateFieldNotNull("group_parent", group_parent);
		if (group_parent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("group_parent", group_parent, NX_INT);
			validateFieldRank("group_parent", group_parent, 1);
			validateFieldDimensions("group_parent", group_parent, null, "groupIndex");
		}
	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
				if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate optional field 'data' of type NX_NUMBER.
		final ILazyDataset data = group.getLazyDataset("data");
				if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldDimensions("data", data, null, "nP", "i", "j", "k");
		}

		// validate optional field 'description' of unknown type.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'time_per_channel' of unknown type. Note: field not defined in base class.
		final ILazyDataset time_per_channel = group.getLazyDataset("time_per_channel");
				if (time_per_channel != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time_per_channel", time_per_channel, NX_CHAR);
			validateFieldUnits("time_per_channel", group.getDataNode("time_per_channel"), NX_TIME);
		}

		// validate optional field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
				if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 3);
			validateFieldDimensions("distance", distance, "NXdetector", "np", "i", "j");
		}

		// validate optional field 'distance_derived' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset distance_derived = group.getLazyDataset("distance_derived");
				if (distance_derived != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance_derived", distance_derived, NX_BOOLEAN);
		}

		// validate optional field 'dead_time' of type NX_FLOAT.
		final ILazyDataset dead_time = group.getLazyDataset("dead_time");
				if (dead_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dead_time", dead_time, NX_FLOAT);
			validateFieldUnits("dead_time", group.getDataNode("dead_time"), NX_TIME);
			validateFieldRank("dead_time", dead_time, 3);
			validateFieldDimensions("dead_time", dead_time, "NXdetector", "np", "i", "j");
		}

		// validate optional field 'count_time' of type NX_NUMBER.
		final ILazyDataset count_time = group.getLazyDataset("count_time");
				if (count_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("count_time", count_time, NX_NUMBER);
			validateFieldUnits("count_time", group.getDataNode("count_time"), NX_TIME);
			validateFieldRank("count_time", count_time, 1);
			validateFieldDimensions("count_time", count_time, "NXdetector", "np");
		}

		// validate optional field 'beam_center_derived' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset beam_center_derived = group.getLazyDataset("beam_center_derived");
				if (beam_center_derived != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_derived", beam_center_derived, NX_BOOLEAN);
		}

		// validate optional field 'beam_center_x' of type NX_FLOAT.
		final ILazyDataset beam_center_x = group.getLazyDataset("beam_center_x");
				if (beam_center_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_x", beam_center_x, NX_FLOAT);
			validateFieldUnits("beam_center_x", group.getDataNode("beam_center_x"), NX_LENGTH);
		}

		// validate optional field 'beam_center_y' of type NX_FLOAT.
		final ILazyDataset beam_center_y = group.getLazyDataset("beam_center_y");
				if (beam_center_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_y", beam_center_y, NX_FLOAT);
			validateFieldUnits("beam_center_y", group.getDataNode("beam_center_y"), NX_LENGTH);
		}

		// validate optional field 'angular_calibration_applied' of type NX_BOOLEAN.
		final ILazyDataset angular_calibration_applied = group.getLazyDataset("angular_calibration_applied");
				if (angular_calibration_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular_calibration_applied", angular_calibration_applied, NX_BOOLEAN);
		}

		// validate optional field 'angular_calibration' of type NX_FLOAT.
		final ILazyDataset angular_calibration = group.getLazyDataset("angular_calibration");
				if (angular_calibration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular_calibration", angular_calibration, NX_FLOAT);
			validateFieldDimensions("angular_calibration", angular_calibration, null, "i", "j", "k");
		}

		// validate optional field 'flatfield_applied' of type NX_BOOLEAN.
		final ILazyDataset flatfield_applied = group.getLazyDataset("flatfield_applied");
				if (flatfield_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flatfield_applied", flatfield_applied, NX_BOOLEAN);
		}

		// validate optional field 'flatfield' of type NX_NUMBER.
		final ILazyDataset flatfield = group.getLazyDataset("flatfield");
				if (flatfield != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flatfield", flatfield, NX_NUMBER);
			validateFieldDimensions("flatfield", flatfield, null, "i", "j", "k");
		}

		// validate optional field 'flatfield_error' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset flatfield_error = group.getLazyDataset("flatfield_error");
				if (flatfield_error != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flatfield_error", flatfield_error, NX_NUMBER);
			validateFieldDimensions("flatfield_error", flatfield_error, null, "i", "j", "k");
		}

		// validate optional field 'flatfield_errors' of type NX_NUMBER.
		final ILazyDataset flatfield_errors = group.getLazyDataset("flatfield_errors");
				if (flatfield_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flatfield_errors", flatfield_errors, NX_NUMBER);
			validateFieldDimensions("flatfield_errors", flatfield_errors, null, "i", "j", "k");
		}

		// validate optional field 'pixel_mask_applied' of type NX_BOOLEAN.
		final ILazyDataset pixel_mask_applied = group.getLazyDataset("pixel_mask_applied");
				if (pixel_mask_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pixel_mask_applied", pixel_mask_applied, NX_BOOLEAN);
		}

		// validate optional field 'pixel_mask' of type NX_INT.
		final ILazyDataset pixel_mask = group.getLazyDataset("pixel_mask");
				if (pixel_mask != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pixel_mask", pixel_mask, NX_INT);
			validateFieldRank("pixel_mask", pixel_mask, 2);
			validateFieldDimensions("pixel_mask", pixel_mask, null, "i", "j");
		}

		// validate optional field 'countrate_correction_applied' of type NX_BOOLEAN.
		final ILazyDataset countrate_correction_applied = group.getLazyDataset("countrate_correction_applied");
				if (countrate_correction_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("countrate_correction_applied", countrate_correction_applied, NX_BOOLEAN);
		}

		// validate optional field 'bit_depth_readout' of type NX_INT.
		final ILazyDataset bit_depth_readout = group.getLazyDataset("bit_depth_readout");
				if (bit_depth_readout != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("bit_depth_readout", bit_depth_readout, NX_INT);
		}

		// validate optional field 'detector_readout_time' of type NX_FLOAT.
		final ILazyDataset detector_readout_time = group.getLazyDataset("detector_readout_time");
				if (detector_readout_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("detector_readout_time", detector_readout_time, NX_FLOAT);
			validateFieldUnits("detector_readout_time", group.getDataNode("detector_readout_time"), NX_TIME);
		}

		// validate optional field 'frame_time' of type NX_FLOAT.
		final ILazyDataset frame_time = group.getLazyDataset("frame_time");
				if (frame_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("frame_time", frame_time, NX_FLOAT);
			validateFieldUnits("frame_time", group.getDataNode("frame_time"), NX_TIME);
			validateFieldRank("frame_time", frame_time, 1);
			validateFieldDimensions("frame_time", frame_time, "NXdetector", "NP");
		}

		// validate optional field 'gain_setting' of type NX_CHAR.
		final ILazyDataset gain_setting = group.getLazyDataset("gain_setting");
				if (gain_setting != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("gain_setting", gain_setting, NX_CHAR);
			validateFieldEnumeration("gain_setting", gain_setting,
					"high",
					"standard",
					"fast",
					"auto");
		}

		// validate optional field 'saturation_value' of type NX_INT.
		final ILazyDataset saturation_value = group.getLazyDataset("saturation_value");
				if (saturation_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("saturation_value", saturation_value, NX_INT);
		}

		// validate optional field 'underload_value' of type NX_INT.
		final ILazyDataset underload_value = group.getLazyDataset("underload_value");
				if (underload_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("underload_value", underload_value, NX_INT);
		}

		// validate field 'sensor_material' of type NX_CHAR.
		final ILazyDataset sensor_material = group.getLazyDataset("sensor_material");
		validateFieldNotNull("sensor_material", sensor_material);
		if (sensor_material != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sensor_material", sensor_material, NX_CHAR);
		}

		// validate field 'sensor_thickness' of type NX_FLOAT.
		final ILazyDataset sensor_thickness = group.getLazyDataset("sensor_thickness");
		validateFieldNotNull("sensor_thickness", sensor_thickness);
		if (sensor_thickness != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sensor_thickness", sensor_thickness, NX_FLOAT);
			validateFieldUnits("sensor_thickness", group.getDataNode("sensor_thickness"), NX_LENGTH);
		}

		// validate optional field 'threshold_energy' of type NX_FLOAT.
		final ILazyDataset threshold_energy = group.getLazyDataset("threshold_energy");
				if (threshold_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("threshold_energy", threshold_energy, NX_FLOAT);
			validateFieldUnits("threshold_energy", group.getDataNode("threshold_energy"), NX_ENERGY);
		}

		// validate optional field 'type' of unknown type.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}
		// validate NXtransformations groups (special case)
		final Map<String, NXtransformations> allTransformations = group.getChildren(NXtransformations.class);
		validateTransformations(allTransformations, depends_on);

		// validate unnamed child group of type NXcollection (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcollection.class, false, true);
		final Map<String, NXcollection> allCollection = group.getAllCollection();
		for (final NXcollection collection : allCollection.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector_NXcollection(collection);
		}

		// validate unnamed child group of type NXdetector_module (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector_module.class, false, true);
		final Map<String, NXdetector_module> allDetector_module = group.getAllDetector_module();
		for (final NXdetector_module detector_module : allDetector_module.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector_NXdetector_module(detector_module);
		}
	}

	/**
	 * Validate optional unnamed group of type NXcollection.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector_NXcollection(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcollection.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXdetector_module.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector_NXdetector_module(final NXdetector_module group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector_module.class, group))) return;

		// validate field 'data_origin' of type NX_INT.
		final ILazyDataset data_origin = group.getLazyDataset("data_origin");
		validateFieldNotNull("data_origin", data_origin);
		if (data_origin != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data_origin", data_origin, NX_INT);
		}

		// validate field 'data_size' of type NX_INT.
		final ILazyDataset data_size = group.getLazyDataset("data_size");
		validateFieldNotNull("data_size", data_size);
		if (data_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data_size", data_size, NX_INT);
		}

		// validate optional field 'data_stride' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset data_stride = group.getLazyDataset("data_stride");
				if (data_stride != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data_stride", data_stride, NX_INT);
		}

		// validate optional field 'module_offset' of type NX_NUMBER.
		final ILazyDataset module_offset = group.getLazyDataset("module_offset");
				if (module_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("module_offset", module_offset, NX_NUMBER);
			validateFieldUnits("module_offset", group.getDataNode("module_offset"), NX_LENGTH);
		// validate attribute 'transformation_type' of field 'module_offset'
		final Attribute module_offset_attr_transformation_type = group.getDataNode("module_offset").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", module_offset_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", module_offset_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", module_offset_attr_transformation_type,
				"translation");

		// validate attribute 'vector' of field 'module_offset'
		final Attribute module_offset_attr_vector = group.getDataNode("module_offset").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", module_offset_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", module_offset_attr_vector, NX_NUMBER);

		// validate attribute 'offset' of field 'module_offset'
		final Attribute module_offset_attr_offset = group.getDataNode("module_offset").getAttribute("offset");
		if (!(validateAttributeNotNull("offset", module_offset_attr_offset))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("offset", module_offset_attr_offset, NX_NUMBER);

		// validate attribute 'depends_on' of field 'module_offset'
		final Attribute module_offset_attr_depends_on = group.getDataNode("module_offset").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", module_offset_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", module_offset_attr_depends_on, NX_CHAR);

		}

		// validate field 'fast_pixel_direction' of type NX_NUMBER.
		final ILazyDataset fast_pixel_direction = group.getLazyDataset("fast_pixel_direction");
		validateFieldNotNull("fast_pixel_direction", fast_pixel_direction);
		if (fast_pixel_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fast_pixel_direction", fast_pixel_direction, NX_NUMBER);
			validateFieldUnits("fast_pixel_direction", group.getDataNode("fast_pixel_direction"), NX_LENGTH);
		// validate attribute 'transformation_type' of field 'fast_pixel_direction'
		final Attribute fast_pixel_direction_attr_transformation_type = group.getDataNode("fast_pixel_direction").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", fast_pixel_direction_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", fast_pixel_direction_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", fast_pixel_direction_attr_transformation_type,
				"translation");

		// validate attribute 'vector' of field 'fast_pixel_direction'
		final Attribute fast_pixel_direction_attr_vector = group.getDataNode("fast_pixel_direction").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", fast_pixel_direction_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", fast_pixel_direction_attr_vector, NX_NUMBER);

		// validate attribute 'offset' of field 'fast_pixel_direction'
		final Attribute fast_pixel_direction_attr_offset = group.getDataNode("fast_pixel_direction").getAttribute("offset");
		if (!(validateAttributeNotNull("offset", fast_pixel_direction_attr_offset))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("offset", fast_pixel_direction_attr_offset, NX_NUMBER);

		// validate attribute 'depends_on' of field 'fast_pixel_direction'
		final Attribute fast_pixel_direction_attr_depends_on = group.getDataNode("fast_pixel_direction").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", fast_pixel_direction_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", fast_pixel_direction_attr_depends_on, NX_CHAR);

		}

		// validate field 'slow_pixel_direction' of type NX_NUMBER.
		final ILazyDataset slow_pixel_direction = group.getLazyDataset("slow_pixel_direction");
		validateFieldNotNull("slow_pixel_direction", slow_pixel_direction);
		if (slow_pixel_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("slow_pixel_direction", slow_pixel_direction, NX_NUMBER);
			validateFieldUnits("slow_pixel_direction", group.getDataNode("slow_pixel_direction"), NX_LENGTH);
		// validate attribute 'transformation_type' of field 'slow_pixel_direction'
		final Attribute slow_pixel_direction_attr_transformation_type = group.getDataNode("slow_pixel_direction").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", slow_pixel_direction_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", slow_pixel_direction_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", slow_pixel_direction_attr_transformation_type,
				"translation");

		// validate attribute 'vector' of field 'slow_pixel_direction'
		final Attribute slow_pixel_direction_attr_vector = group.getDataNode("slow_pixel_direction").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", slow_pixel_direction_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", slow_pixel_direction_attr_vector, NX_NUMBER);

		// validate attribute 'offset' of field 'slow_pixel_direction'
		final Attribute slow_pixel_direction_attr_offset = group.getDataNode("slow_pixel_direction").getAttribute("offset");
		if (!(validateAttributeNotNull("offset", slow_pixel_direction_attr_offset))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("offset", slow_pixel_direction_attr_offset, NX_NUMBER);

		// validate attribute 'depends_on' of field 'slow_pixel_direction'
		final Attribute slow_pixel_direction_attr_depends_on = group.getDataNode("slow_pixel_direction").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", slow_pixel_direction_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", slow_pixel_direction_attr_depends_on, NX_CHAR);

		}
	}

	/**
	 * Validate unnamed group of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_NXbeam(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'incident_wavelength' of type NX_FLOAT.
		final ILazyDataset incident_wavelength = group.getLazyDataset("incident_wavelength");
		validateFieldNotNull("incident_wavelength", incident_wavelength);
		if (incident_wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength", incident_wavelength, NX_FLOAT);
			validateFieldUnits("incident_wavelength", group.getDataNode("incident_wavelength"), NX_WAVELENGTH);
			validateFieldRank("incident_wavelength", incident_wavelength, 1);
			validateFieldDimensions("incident_wavelength", incident_wavelength, "NXbeam", "i");
		}

		// validate optional field 'incident_wavelength_weight' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset incident_wavelength_weight = group.getLazyDataset("incident_wavelength_weight");
				if (incident_wavelength_weight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength_weight", incident_wavelength_weight, NX_FLOAT);
		}

		// validate optional field 'incident_wavelength_weights' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset incident_wavelength_weights = group.getLazyDataset("incident_wavelength_weights");
				if (incident_wavelength_weights != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength_weights", incident_wavelength_weights, NX_FLOAT);
		}

		// validate optional field 'incident_wavelength_spread' of type NX_FLOAT.
		final ILazyDataset incident_wavelength_spread = group.getLazyDataset("incident_wavelength_spread");
				if (incident_wavelength_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength_spread", incident_wavelength_spread, NX_FLOAT);
			validateFieldUnits("incident_wavelength_spread", group.getDataNode("incident_wavelength_spread"), NX_WAVELENGTH);
			validateFieldRank("incident_wavelength_spread", incident_wavelength_spread, 1);
			validateFieldDimensions("incident_wavelength_spread", incident_wavelength_spread, "NXbeam", "i");
		}

		// validate optional field 'flux' of type NX_FLOAT.
		final ILazyDataset flux = group.getLazyDataset("flux");
				if (flux != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flux", flux, NX_FLOAT);
			validateFieldUnits("flux", group.getDataNode("flux"), NX_FLUX);
			validateFieldRank("flux", flux, 1);
			validateFieldDimensions("flux", flux, "NXbeam", "i");
		}

		// validate field 'total_flux' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset total_flux = group.getLazyDataset("total_flux");
		validateFieldNotNull("total_flux", total_flux);
		if (total_flux != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_flux", total_flux, NX_FLOAT);
			validateFieldUnits("total_flux", group.getDataNode("total_flux"), NX_FREQUENCY);
		}

		// validate optional field 'incident_beam_size' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset incident_beam_size = group.getLazyDataset("incident_beam_size");
				if (incident_beam_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_beam_size", incident_beam_size, NX_FLOAT);
			validateFieldUnits("incident_beam_size", group.getDataNode("incident_beam_size"), NX_LENGTH);
			validateFieldRank("incident_beam_size", incident_beam_size, 1);
			validateFieldDimensions("incident_beam_size", incident_beam_size, null, 2);
		}

		// validate optional field 'profile' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset profile = group.getLazyDataset("profile");
				if (profile != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("profile", profile, NX_CHAR);
			validateFieldEnumeration("profile", profile,
					"Gaussian",
					"Airy",
					"top-hat",
					"rectangular");
		}

		// validate optional field 'incident_polarisation_stokes' of unknown type. Note: field not defined in base class.
		final ILazyDataset incident_polarisation_stokes = group.getLazyDataset("incident_polarisation_stokes");
				if (incident_polarisation_stokes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_polarisation_stokes", incident_polarisation_stokes, NX_CHAR);
			validateFieldRank("incident_polarisation_stokes", incident_polarisation_stokes, 2);
			validateFieldDimensions("incident_polarisation_stokes", incident_polarisation_stokes, null, "nP", 4);
		}
		// validate optional child group 'incident_wavelength_spectrum' of type NXdata
		if (group.getData("incident_wavelength_spectrum") != null) {
			validateGroup_NXentry_NXinstrument_NXbeam_incident_wavelength_spectrum(group.getData("incident_wavelength_spectrum"));
		}
	}

	/**
	 * Validate optional group 'incident_wavelength_spectrum' of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_NXbeam_incident_wavelength_spectrum(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("incident_wavelength_spectrum", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_NXentry_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate field 'name' of unknown type.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		// validate optional attribute 'short_name' of field 'name'
		final Attribute name_attr_short_name = group.getDataNode("name").getAttribute("short_name");
		if (name_attr_short_name != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("short_name", name_attr_short_name, NX_CHAR);
		}

		}
	}
}
