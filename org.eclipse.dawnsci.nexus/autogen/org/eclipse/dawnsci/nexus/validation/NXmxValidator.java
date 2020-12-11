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

import org.eclipse.january.dataset.IDataset;
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

	@Override
	public void validate(NXroot root) throws NexusValidationException {
		// validate unnamed child group of type NXentry (possibly multiple)
		final Map<String, NXentry> allEntry = root.getAllEntry();
		for (final NXentry entry : allEntry.values()) {
			validateGroup_NXentry(entry);
		}
	}

	@Override
	public void validate(NXentry entry) throws NexusValidationException {
		validateGroup_NXentry(entry);
	}

	@Override
	public void validate(NXsubentry subentry) throws NexusValidationException {
		validateGroup_NXentry(subentry);
	}


	/**
	 * Validate unnamed group of type NXentry.
	 */
	private void validateGroup_NXentry(final NXsubentry group) throws NexusValidationException {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		validateGroupNotNull(null, NXentry.class, group);

		// validate optional attribute 'version'
		final Attribute version_attr = group.getAttribute("version");
		if (version_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeEnumeration("version", version_attr,
					"1.0");
		}

		// validate optional field 'title' of type NX_CHAR.
		final IDataset title = group.getTitle();
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'start_time' of type NX_DATE_TIME.
		final IDataset start_time = group.getStart_time();
		validateFieldNotNull("start_time", start_time);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("start_time", start_time, NX_DATE_TIME);

		// validate optional field 'end_time' of type NX_DATE_TIME.
		final IDataset end_time = group.getEnd_time();
		if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
		}

		// validate field 'end_time_estimated' of type NX_DATE_TIME. Note: field not defined in base class.
		final IDataset end_time_estimated = group.getDataset("end_time_estimated");
		validateFieldNotNull("end_time_estimated", end_time_estimated);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("end_time_estimated", end_time_estimated, NX_DATE_TIME);

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		validateFieldNotNull("definition", definition);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldEnumeration("definition", definition,
				"NXmx");

		// validate unnamed child group of type NXdata (possibly multiple)
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXsource (possibly multiple)
		final Map<String, NXsource> allSource = group.getChildren(NXsource.class);
		for (final NXsource source : allSource.values()) {
			validateGroup_NXentry_NXsource(source);
		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXdata.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'data' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset data = group.getDataset("data");
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldDimensions("data", data, null, "nP", "i", "j", "k");
		}
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXsample.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of type NX_CHAR.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final IDataset depends_on = group.getDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("depends_on", depends_on, NX_CHAR);

		// validate optional field 'temperature' of type NX_NUMBER.
		final IDataset temperature = group.getTemperature();
		if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_NUMBER);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");
		}
		// validate NXtransformations groups (special case)
		final Map<String, NXtransformations> allTransformations = group.getChildren(NXtransformations.class);
		validateTransformations(allTransformations, depends_on.getString(0));
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXinstrument.class, group);

		// validate field 'name' of type NX_CHAR.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);
		// validate attribute 'short_name' of field 'name'
		final Attribute name_attr_short_name = group.getDataNode("name").getAttribute("short_name");
		validateAttributeNotNull("short_name", name_attr_short_name);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration


		// validate optional field 'time_zone' of type NX_DATE_TIME. Note: field not defined in base class.
		final IDataset time_zone = group.getDataset("time_zone");
		if (time_zone != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time_zone", time_zone, NX_DATE_TIME);
		}

		// validate unnamed child group of type NXattenuator (possibly multiple)
		final Map<String, NXattenuator> allAttenuator = group.getAllAttenuator();
		for (final NXattenuator attenuator : allAttenuator.values()) {
			validateGroup_NXentry_NXinstrument_NXattenuator(attenuator);
		}

		// validate unnamed child group of type NXdetector_group (possibly multiple)
		final Map<String, NXdetector_group> allDetector_group = group.getAllDetector_group();
		for (final NXdetector_group detector_group : allDetector_group.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector_group(detector_group);
		}

		// validate unnamed child group of type NXdetector (possibly multiple)
		final Map<String, NXdetector> allDetector = group.getAllDetector();
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector(detector);
		}

		// validate unnamed child group of type NXbeam (possibly multiple)
		final Map<String, NXbeam> allBeam = group.getAllBeam();
		for (final NXbeam beam : allBeam.values()) {
			validateGroup_NXentry_NXinstrument_NXbeam(beam);
		}
	}

	/**
	 * Validate optional unnamed group of type NXattenuator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXattenuator(final NXattenuator group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXattenuator.class, group);

		// validate optional field 'attenuator_transmission' of type NX_NUMBER.
		final IDataset attenuator_transmission = group.getAttenuator_transmission();
		if (attenuator_transmission != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("attenuator_transmission", attenuator_transmission, NX_NUMBER);
			validateFieldUnits("attenuator_transmission", group.getDataNode("attenuator_transmission"), NX_UNITLESS);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdetector_group.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector_group(final NXdetector_group group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXdetector_group.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'group_names' of type NX_CHAR.
		final IDataset group_names = group.getGroup_names();
		validateFieldNotNull("group_names", group_names);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("group_names", group_names, NX_CHAR);

		// validate field 'group_index' of type NX_INT.
		final IDataset group_index = group.getGroup_index();
		validateFieldNotNull("group_index", group_index);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("group_index", group_index, NX_INT);
		validateFieldRank("group_index", group_index, 1);
		validateFieldDimensions("group_index", group_index, null, "i");

		// validate field 'group_parent' of type NX_INT.
		final IDataset group_parent = group.getGroup_parent();
		validateFieldNotNull("group_parent", group_parent);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("group_parent", group_parent, NX_INT);
		validateFieldRank("group_parent", group_parent, 1);
		validateFieldDimensions("group_parent", group_parent, null, "groupIndex");
	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector(final NXdetector group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXdetector.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final IDataset depends_on = group.getDataset("depends_on");
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate optional field 'data' of type NX_NUMBER.
		final IDataset data = group.getData();
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldDimensions("data", data, null, "nP", "i", "j", "k");
		}

		// validate optional field 'description' of unknown type.
		final IDataset description = group.getDescription();
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		}

		// validate optional field 'time_per_channel' of unknown type. Note: field not defined in base class.
		final IDataset time_per_channel = group.getDataset("time_per_channel");
		if (time_per_channel != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldUnits("time_per_channel", group.getDataNode("time_per_channel"), NX_TIME);
		}

		// validate optional field 'distance' of type NX_FLOAT.
		final IDataset distance = group.getDistance();
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 3);
			validateFieldDimensions("distance", distance, "NXdetector", "np", "i", "j");
		}

		// validate optional field 'distance_derived' of type NX_BOOLEAN. Note: field not defined in base class.
		final IDataset distance_derived = group.getDataset("distance_derived");
		if (distance_derived != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance_derived", distance_derived, NX_BOOLEAN);
		}

		// validate optional field 'dead_time' of type NX_FLOAT.
		final IDataset dead_time = group.getDead_time();
		if (dead_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dead_time", dead_time, NX_FLOAT);
			validateFieldUnits("dead_time", group.getDataNode("dead_time"), NX_TIME);
			validateFieldRank("dead_time", dead_time, 3);
			validateFieldDimensions("dead_time", dead_time, "NXdetector", "np", "i", "j");
		}

		// validate optional field 'count_time' of type NX_NUMBER.
		final IDataset count_time = group.getCount_time();
		if (count_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("count_time", count_time, NX_NUMBER);
			validateFieldUnits("count_time", group.getDataNode("count_time"), NX_TIME);
			validateFieldRank("count_time", count_time, 1);
			validateFieldDimensions("count_time", count_time, "NXdetector", "np");
		}

		// validate optional field 'beam_center_derived' of type NX_BOOLEAN. Note: field not defined in base class.
		final IDataset beam_center_derived = group.getDataset("beam_center_derived");
		if (beam_center_derived != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_derived", beam_center_derived, NX_BOOLEAN);
		}

		// validate optional field 'beam_center_x' of type NX_FLOAT.
		final IDataset beam_center_x = group.getBeam_center_x();
		if (beam_center_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_x", beam_center_x, NX_FLOAT);
			validateFieldUnits("beam_center_x", group.getDataNode("beam_center_x"), NX_LENGTH);
		}

		// validate optional field 'beam_center_y' of type NX_FLOAT.
		final IDataset beam_center_y = group.getBeam_center_y();
		if (beam_center_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_y", beam_center_y, NX_FLOAT);
			validateFieldUnits("beam_center_y", group.getDataNode("beam_center_y"), NX_LENGTH);
		}

		// validate optional field 'angular_calibration_applied' of type NX_BOOLEAN.
		final IDataset angular_calibration_applied = group.getAngular_calibration_applied();
		if (angular_calibration_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular_calibration_applied", angular_calibration_applied, NX_BOOLEAN);
		}

		// validate optional field 'angular_calibration' of type NX_FLOAT.
		final IDataset angular_calibration = group.getAngular_calibration();
		if (angular_calibration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular_calibration", angular_calibration, NX_FLOAT);
			validateFieldDimensions("angular_calibration", angular_calibration, null, "i", "j", "k");
		}

		// validate optional field 'flatfield_applied' of type NX_BOOLEAN.
		final IDataset flatfield_applied = group.getFlatfield_applied();
		if (flatfield_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flatfield_applied", flatfield_applied, NX_BOOLEAN);
		}

		// validate optional field 'flatfield' of type NX_NUMBER.
		final IDataset flatfield = group.getFlatfield();
		if (flatfield != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flatfield", flatfield, NX_NUMBER);
			validateFieldDimensions("flatfield", flatfield, null, "i", "j", "k");
		}

		// validate optional field 'flatfield_error' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset flatfield_error = group.getDataset("flatfield_error");
		if (flatfield_error != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flatfield_error", flatfield_error, NX_NUMBER);
			validateFieldDimensions("flatfield_error", flatfield_error, null, "i", "j", "k");
		}

		// validate optional field 'flatfield_errors' of type NX_NUMBER.
		final IDataset flatfield_errors = group.getFlatfield_errors();
		if (flatfield_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flatfield_errors", flatfield_errors, NX_NUMBER);
			validateFieldDimensions("flatfield_errors", flatfield_errors, null, "i", "j", "k");
		}

		// validate optional field 'pixel_mask_applied' of type NX_BOOLEAN.
		final IDataset pixel_mask_applied = group.getPixel_mask_applied();
		if (pixel_mask_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pixel_mask_applied", pixel_mask_applied, NX_BOOLEAN);
		}

		// validate optional field 'pixel_mask' of type NX_INT.
		final IDataset pixel_mask = group.getPixel_mask();
		if (pixel_mask != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pixel_mask", pixel_mask, NX_INT);
			validateFieldRank("pixel_mask", pixel_mask, 2);
			validateFieldDimensions("pixel_mask", pixel_mask, null, "i", "j");
		}

		// validate optional field 'countrate_correction_applied' of type NX_BOOLEAN.
		final IDataset countrate_correction_applied = group.getCountrate_correction_applied();
		if (countrate_correction_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("countrate_correction_applied", countrate_correction_applied, NX_BOOLEAN);
		}

		// validate optional field 'bit_depth_readout' of type NX_INT.
		final IDataset bit_depth_readout = group.getBit_depth_readout();
		if (bit_depth_readout != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("bit_depth_readout", bit_depth_readout, NX_INT);
		}

		// validate optional field 'detector_readout_time' of type NX_FLOAT.
		final IDataset detector_readout_time = group.getDetector_readout_time();
		if (detector_readout_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("detector_readout_time", detector_readout_time, NX_FLOAT);
			validateFieldUnits("detector_readout_time", group.getDataNode("detector_readout_time"), NX_TIME);
		}

		// validate optional field 'frame_time' of type NX_FLOAT.
		final IDataset frame_time = group.getFrame_time();
		if (frame_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("frame_time", frame_time, NX_FLOAT);
			validateFieldUnits("frame_time", group.getDataNode("frame_time"), NX_TIME);
			validateFieldRank("frame_time", frame_time, 1);
			validateFieldDimensions("frame_time", frame_time, "NXdetector", "NP");
		}

		// validate optional field 'gain_setting' of type NX_CHAR.
		final IDataset gain_setting = group.getGain_setting();
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
		final IDataset saturation_value = group.getSaturation_value();
		if (saturation_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("saturation_value", saturation_value, NX_INT);
		}

		// validate optional field 'underload_value' of type NX_INT.
		final IDataset underload_value = group.getUnderload_value();
		if (underload_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("underload_value", underload_value, NX_INT);
		}

		// validate field 'sensor_material' of type NX_CHAR.
		final IDataset sensor_material = group.getSensor_material();
		validateFieldNotNull("sensor_material", sensor_material);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("sensor_material", sensor_material, NX_CHAR);

		// validate field 'sensor_thickness' of type NX_FLOAT.
		final IDataset sensor_thickness = group.getSensor_thickness();
		validateFieldNotNull("sensor_thickness", sensor_thickness);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("sensor_thickness", sensor_thickness, NX_FLOAT);
		validateFieldUnits("sensor_thickness", group.getDataNode("sensor_thickness"), NX_LENGTH);

		// validate optional field 'threshold_energy' of type NX_FLOAT.
		final IDataset threshold_energy = group.getThreshold_energy();
		if (threshold_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("threshold_energy", threshold_energy, NX_FLOAT);
			validateFieldUnits("threshold_energy", group.getDataNode("threshold_energy"), NX_ENERGY);
		}

		// validate optional field 'type' of unknown type.
		final IDataset type = group.getType();
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		}
		// validate NXtransformations groups (special case)
		final Map<String, NXtransformations> allTransformations = group.getChildren(NXtransformations.class);
		validateTransformations(allTransformations, depends_on.getString(0));

		// validate unnamed child group of type NXcollection (possibly multiple)
		final Map<String, NXcollection> allCollection = group.getAllCollection();
		for (final NXcollection collection : allCollection.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector_NXcollection(collection);
		}

		// validate unnamed child group of type NXdetector_module (possibly multiple)
		final Map<String, NXdetector_module> allDetector_module = group.getAllDetector_module();
		for (final NXdetector_module detector_module : allDetector_module.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector_NXdetector_module(detector_module);
		}
	}

	/**
	 * Validate optional unnamed group of type NXcollection.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector_NXcollection(final NXcollection group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXcollection.class, group);

	}

	/**
	 * Validate unnamed group of type NXdetector_module.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector_NXdetector_module(final NXdetector_module group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXdetector_module.class, group);

		// validate field 'data_origin' of type NX_INT.
		final IDataset data_origin = group.getData_origin();
		validateFieldNotNull("data_origin", data_origin);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data_origin", data_origin, NX_INT);

		// validate field 'data_size' of type NX_INT.
		final IDataset data_size = group.getData_size();
		validateFieldNotNull("data_size", data_size);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data_size", data_size, NX_INT);

		// validate optional field 'data_stride' of type NX_INT. Note: field not defined in base class.
		final IDataset data_stride = group.getDataset("data_stride");
		if (data_stride != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data_stride", data_stride, NX_INT);
		}

		// validate optional field 'module_offset' of type NX_NUMBER.
		final IDataset module_offset = group.getModule_offset();
		if (module_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("module_offset", module_offset, NX_NUMBER);
			validateFieldUnits("module_offset", group.getDataNode("module_offset"), NX_LENGTH);
		// validate attribute 'transformation_type' of field 'module_offset'
		final Attribute module_offset_attr_transformation_type = group.getDataNode("module_offset").getAttribute("transformation_type");
		validateAttributeNotNull("transformation_type", module_offset_attr_transformation_type);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("transformation_type", module_offset_attr_transformation_type,
				"translation");

		// validate attribute 'vector' of field 'module_offset'
		final Attribute module_offset_attr_vector = group.getDataNode("module_offset").getAttribute("vector");
		validateAttributeNotNull("vector", module_offset_attr_vector);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", module_offset_attr_vector, NX_NUMBER);

		// validate attribute 'offset' of field 'module_offset'
		final Attribute module_offset_attr_offset = group.getDataNode("module_offset").getAttribute("offset");
		validateAttributeNotNull("offset", module_offset_attr_offset);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("offset", module_offset_attr_offset, NX_NUMBER);

		// validate attribute 'depends_on' of field 'module_offset'
		final Attribute module_offset_attr_depends_on = group.getDataNode("module_offset").getAttribute("depends_on");
		validateAttributeNotNull("depends_on", module_offset_attr_depends_on);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration

		}

		// validate field 'fast_pixel_direction' of type NX_NUMBER.
		final IDataset fast_pixel_direction = group.getFast_pixel_direction();
		validateFieldNotNull("fast_pixel_direction", fast_pixel_direction);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("fast_pixel_direction", fast_pixel_direction, NX_NUMBER);
		validateFieldUnits("fast_pixel_direction", group.getDataNode("fast_pixel_direction"), NX_LENGTH);
		// validate attribute 'transformation_type' of field 'fast_pixel_direction'
		final Attribute fast_pixel_direction_attr_transformation_type = group.getDataNode("fast_pixel_direction").getAttribute("transformation_type");
		validateAttributeNotNull("transformation_type", fast_pixel_direction_attr_transformation_type);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("transformation_type", fast_pixel_direction_attr_transformation_type,
				"translation");

		// validate attribute 'vector' of field 'fast_pixel_direction'
		final Attribute fast_pixel_direction_attr_vector = group.getDataNode("fast_pixel_direction").getAttribute("vector");
		validateAttributeNotNull("vector", fast_pixel_direction_attr_vector);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", fast_pixel_direction_attr_vector, NX_NUMBER);

		// validate attribute 'offset' of field 'fast_pixel_direction'
		final Attribute fast_pixel_direction_attr_offset = group.getDataNode("fast_pixel_direction").getAttribute("offset");
		validateAttributeNotNull("offset", fast_pixel_direction_attr_offset);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("offset", fast_pixel_direction_attr_offset, NX_NUMBER);

		// validate attribute 'depends_on' of field 'fast_pixel_direction'
		final Attribute fast_pixel_direction_attr_depends_on = group.getDataNode("fast_pixel_direction").getAttribute("depends_on");
		validateAttributeNotNull("depends_on", fast_pixel_direction_attr_depends_on);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration


		// validate field 'slow_pixel_direction' of type NX_NUMBER.
		final IDataset slow_pixel_direction = group.getSlow_pixel_direction();
		validateFieldNotNull("slow_pixel_direction", slow_pixel_direction);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("slow_pixel_direction", slow_pixel_direction, NX_NUMBER);
		validateFieldUnits("slow_pixel_direction", group.getDataNode("slow_pixel_direction"), NX_LENGTH);
		// validate attribute 'transformation_type' of field 'slow_pixel_direction'
		final Attribute slow_pixel_direction_attr_transformation_type = group.getDataNode("slow_pixel_direction").getAttribute("transformation_type");
		validateAttributeNotNull("transformation_type", slow_pixel_direction_attr_transformation_type);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("transformation_type", slow_pixel_direction_attr_transformation_type,
				"translation");

		// validate attribute 'vector' of field 'slow_pixel_direction'
		final Attribute slow_pixel_direction_attr_vector = group.getDataNode("slow_pixel_direction").getAttribute("vector");
		validateAttributeNotNull("vector", slow_pixel_direction_attr_vector);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", slow_pixel_direction_attr_vector, NX_NUMBER);

		// validate attribute 'offset' of field 'slow_pixel_direction'
		final Attribute slow_pixel_direction_attr_offset = group.getDataNode("slow_pixel_direction").getAttribute("offset");
		validateAttributeNotNull("offset", slow_pixel_direction_attr_offset);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("offset", slow_pixel_direction_attr_offset, NX_NUMBER);

		// validate attribute 'depends_on' of field 'slow_pixel_direction'
		final Attribute slow_pixel_direction_attr_depends_on = group.getDataNode("slow_pixel_direction").getAttribute("depends_on");
		validateAttributeNotNull("depends_on", slow_pixel_direction_attr_depends_on);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration

	}

	/**
	 * Validate unnamed group of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_NXbeam(final NXbeam group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXbeam.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'incident_wavelength' of type NX_FLOAT.
		final IDataset incident_wavelength = group.getIncident_wavelength();
		validateFieldNotNull("incident_wavelength", incident_wavelength);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("incident_wavelength", incident_wavelength, NX_FLOAT);
		validateFieldUnits("incident_wavelength", group.getDataNode("incident_wavelength"), NX_WAVELENGTH);
		validateFieldRank("incident_wavelength", incident_wavelength, 1);
		validateFieldDimensions("incident_wavelength", incident_wavelength, "NXbeam", "i");

		// validate optional field 'incident_wavelength_weight' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset incident_wavelength_weight = group.getDataset("incident_wavelength_weight");
		if (incident_wavelength_weight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength_weight", incident_wavelength_weight, NX_FLOAT);
		}

		// validate optional field 'incident_wavelength_weights' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset incident_wavelength_weights = group.getDataset("incident_wavelength_weights");
		if (incident_wavelength_weights != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength_weights", incident_wavelength_weights, NX_FLOAT);
		}

		// validate optional field 'incident_wavelength_spread' of type NX_FLOAT.
		final IDataset incident_wavelength_spread = group.getIncident_wavelength_spread();
		if (incident_wavelength_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength_spread", incident_wavelength_spread, NX_FLOAT);
			validateFieldUnits("incident_wavelength_spread", group.getDataNode("incident_wavelength_spread"), NX_WAVELENGTH);
			validateFieldRank("incident_wavelength_spread", incident_wavelength_spread, 1);
			validateFieldDimensions("incident_wavelength_spread", incident_wavelength_spread, "NXbeam", "i");
		}

		// validate optional field 'flux' of type NX_FLOAT.
		final IDataset flux = group.getFlux();
		if (flux != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flux", flux, NX_FLOAT);
			validateFieldUnits("flux", group.getDataNode("flux"), NX_FLUX);
			validateFieldRank("flux", flux, 1);
			validateFieldDimensions("flux", flux, "NXbeam", "i");
		}

		// validate field 'total_flux' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset total_flux = group.getDataset("total_flux");
		validateFieldNotNull("total_flux", total_flux);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("total_flux", total_flux, NX_FLOAT);
		validateFieldUnits("total_flux", group.getDataNode("total_flux"), NX_FREQUENCY);

		// validate optional field 'incident_beam_size' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset incident_beam_size = group.getDataset("incident_beam_size");
		if (incident_beam_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_beam_size", incident_beam_size, NX_FLOAT);
			validateFieldUnits("incident_beam_size", group.getDataNode("incident_beam_size"), NX_LENGTH);
			validateFieldRank("incident_beam_size", incident_beam_size, 1);
			validateFieldDimensions("incident_beam_size", incident_beam_size, null, 2);
		}

		// validate optional field 'profile' of type NX_CHAR. Note: field not defined in base class.
		final IDataset profile = group.getDataset("profile");
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
		final IDataset incident_polarisation_stokes = group.getDataset("incident_polarisation_stokes");
		if (incident_polarisation_stokes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldRank("incident_polarisation_stokes", incident_polarisation_stokes, 2);
			validateFieldDimensions("incident_polarisation_stokes", incident_polarisation_stokes, null, "nP", 4);
		}
		// validate optional child group 'incident_wavelength_spectrum' of type NXdata
		if (group.getData() != null) {
			validateGroup_NXentry_NXinstrument_NXbeam_incident_wavelength_spectrum(group.getData());
		}
	}

	/**
	 * Validate optional group 'incident_wavelength_spectrum' of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_NXbeam_incident_wavelength_spectrum(final NXdata group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("incident_wavelength_spectrum", NXdata.class, group);
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_NXentry_NXsource(final NXsource group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXsource.class, group);

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		// validate optional attribute 'short_name' of field 'name'
		final Attribute name_attr_short_name = group.getDataNode("name").getAttribute("short_name");
		if (name_attr_short_name != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
		}

	}
}
