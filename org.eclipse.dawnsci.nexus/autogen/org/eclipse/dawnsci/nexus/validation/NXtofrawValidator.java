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

import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;import org.eclipse.january.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXtofraw'.
 */
public class NXtofrawValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXtofrawValidator() {
		super(NexusApplicationDefinition.NX_TOFRAW);
	}

	@Override
	public ValidationReport validate(NXroot root) {
		// validate child group 'entry' of type NXentry
		validateGroup_entry(root.getEntry());
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXentry entry) {
		validateGroup_entry(entry);
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXsubentry subentry) {
		validateGroup_entry(subentry);
		return validationReport;
	}


	/**
	 * Validate group 'entry' of type NXentry.
	 */
	private void validateGroup_entry(final NXsubentry group) {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		if (!(validateGroupNotNull("entry", NXentry.class, group))) return;

		// validate field 'title' of unknown type.
		final IDataset title = group.getTitle();
		if (!(validateFieldNotNull("title", title))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("title", title, NX_CHAR);

		// validate field 'start_time' of type NX_DATE_TIME.
		final IDataset start_time = group.getStart_time();
		if (!(validateFieldNotNull("start_time", start_time))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("start_time", start_time, NX_DATE_TIME);

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		if (!(validateFieldNotNull("definition", definition))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("definition", definition, NX_CHAR);
		validateFieldEnumeration("definition", definition,
				"NXtofraw");

		// validate field 'duration' of type NX_FLOAT.
		final IDataset duration = group.getDuration();
		if (!(validateFieldNotNull("duration", duration))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("duration", duration, NX_FLOAT);
		validateFieldUnits("duration", group.getDataNode("duration"), NX_TIME);

		// validate field 'run_number' of type NX_INT. Note: field not defined in base class.
		final IDataset run_number = group.getDataset("run_number");
		if (!(validateFieldNotNull("run_number", run_number))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("run_number", run_number, NX_INT);

		// validate field 'pre_sample_flightpath' of type NX_FLOAT.
		final IDataset pre_sample_flightpath = group.getPre_sample_flightpath();
		if (!(validateFieldNotNull("pre_sample_flightpath", pre_sample_flightpath))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("pre_sample_flightpath", pre_sample_flightpath, NX_FLOAT);
		validateFieldUnits("pre_sample_flightpath", group.getDataNode("pre_sample_flightpath"), NX_LENGTH);

		// validate child group 'user' of type NXuser
		validateGroup_entry_user(group.getUser());

		// validate child group 'instrument' of type NXinstrument
		validateGroup_entry_instrument(group.getInstrument());

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_entry_NXsample(sample);
		}

		// validate unnamed child group of type NXmonitor (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmonitor.class, false, true);
		final Map<String, NXmonitor> allMonitor = group.getAllMonitor();
		for (final NXmonitor monitor : allMonitor.values()) {
			validateGroup_entry_NXmonitor(monitor);
		}

		// validate child group 'data' of type NXdata
		validateGroup_entry_data(group.getData());
	}

	/**
	 * Validate group 'user' of type NXuser.
	 */
	private void validateGroup_entry_user(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("user", NXuser.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final IDataset name = group.getName();
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_entry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate child group 'detector' of type NXdetector
		validateGroup_entry_instrument_detector(group.getDetector());
	}

	/**
	 * Validate group 'detector' of type NXdetector.
	 */
	private void validateGroup_entry_instrument_detector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("detector", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT.
		final IDataset data = group.getData();
		if (!(validateFieldNotNull("data", data))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_INT);
		validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
		validateFieldRank("data", data, 2);
		validateFieldDimensions("data", data, null, "nDet", "nTimeChan");

		// validate field 'detector_number' of type NX_INT.
		final IDataset detector_number = group.getDetector_number();
		if (!(validateFieldNotNull("detector_number", detector_number))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("detector_number", detector_number, NX_INT);
		validateFieldRank("detector_number", detector_number, 1);
		validateFieldDimensions("detector_number", detector_number, null, "nDet");

		// validate field 'distance' of type NX_FLOAT.
		final IDataset distance = group.getDistance();
		if (!(validateFieldNotNull("distance", distance))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("distance", distance, NX_FLOAT);
		validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		validateFieldRank("distance", distance, 1);
		validateFieldDimensions("distance", distance, null, "nDet");

		// validate field 'time_of_flight' of type NX_FLOAT.
		final IDataset time_of_flight = group.getTime_of_flight();
		if (!(validateFieldNotNull("time_of_flight", time_of_flight))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("time_of_flight", time_of_flight, NX_FLOAT);
		validateFieldUnits("time_of_flight", group.getDataNode("time_of_flight"), NX_TIME_OF_FLIGHT);
		validateFieldRank("time_of_flight", time_of_flight, 1);
		validateFieldDimensions("time_of_flight", time_of_flight, null, "nTimeChan");

		// validate field 'polar_angle' of type NX_FLOAT.
		final IDataset polar_angle = group.getPolar_angle();
		if (!(validateFieldNotNull("polar_angle", polar_angle))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("polar_angle", polar_angle, NX_FLOAT);
		validateFieldUnits("polar_angle", group.getDataNode("polar_angle"), NX_ANGLE);
		validateFieldRank("polar_angle", polar_angle, 1);
		validateFieldDimensions("polar_angle", polar_angle, null, "nDet");

		// validate field 'azimuthal_angle' of type NX_FLOAT.
		final IDataset azimuthal_angle = group.getAzimuthal_angle();
		if (!(validateFieldNotNull("azimuthal_angle", azimuthal_angle))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("azimuthal_angle", azimuthal_angle, NX_FLOAT);
		validateFieldUnits("azimuthal_angle", group.getDataNode("azimuthal_angle"), NX_ANGLE);
		validateFieldRank("azimuthal_angle", azimuthal_angle, 1);
		validateFieldDimensions("azimuthal_angle", azimuthal_angle, null, "nDet");
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_entry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'nature' of type NX_CHAR. Note: field not defined in base class.
		final IDataset nature = group.getDataset("nature");
		if (!(validateFieldNotNull("nature", nature))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("nature", nature, NX_CHAR);
		validateFieldEnumeration("nature", nature,
				"powder",
				"liquid",
				"single crystal");
	}

	/**
	 * Validate unnamed group of type NXmonitor.
	 */
	private void validateGroup_entry_NXmonitor(final NXmonitor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmonitor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'mode' of unknown type.
		final IDataset mode = group.getMode();
		if (!(validateFieldNotNull("mode", mode))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("mode", mode, NX_CHAR);
		validateFieldEnumeration("mode", mode,
				"monitor",
				"timer");

		// validate field 'preset' of type NX_FLOAT.
		final IDataset preset = group.getPreset();
		if (!(validateFieldNotNull("preset", preset))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("preset", preset, NX_FLOAT);
		validateFieldUnits("preset", group.getDataNode("preset"), NX_ANY);

		// validate field 'distance' of type NX_FLOAT.
		final IDataset distance = group.getDistance();
		if (!(validateFieldNotNull("distance", distance))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("distance", distance, NX_FLOAT);
		validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);

		// validate field 'data' of type NX_INT.
		final IDataset data = group.getData();
		if (!(validateFieldNotNull("data", data))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_INT);
		validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
		validateFieldRank("data", data, 1);
		validateFieldDimensions("data", data, null, "nTimeChan");

		// validate field 'time_of_flight' of type NX_FLOAT.
		final IDataset time_of_flight = group.getTime_of_flight();
		if (!(validateFieldNotNull("time_of_flight", time_of_flight))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("time_of_flight", time_of_flight, NX_FLOAT);
		validateFieldUnits("time_of_flight", group.getDataNode("time_of_flight"), NX_TIME_OF_FLIGHT);
		validateFieldRank("time_of_flight", time_of_flight, 1);
		validateFieldDimensions("time_of_flight", time_of_flight, null, "nTimeChan");

		// validate field 'integral_counts' of type NX_INT. Note: field not defined in base class.
		final IDataset integral_counts = group.getDataset("integral_counts");
		if (!(validateFieldNotNull("integral_counts", integral_counts))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("integral_counts", integral_counts, NX_INT);
		validateFieldUnits("integral_counts", group.getDataNode("integral_counts"), NX_UNITLESS);
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_entry_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'data' to location '/NXentry/NXinstrument/NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/NXdetector/data");

		// validate link 'detector_number' to location '/NXentry/NXinstrument/NXdetector/detector_number
		final DataNode detector_number = group.getDataNode("detector_number");
		validateDataNodeLink("detector_number", detector_number, "/NXentry/NXinstrument/NXdetector/detector_number");

		// validate link 'time_of_flight' to location '/NXentry/NXinstrument/NXdetector/time_of_flight
		final DataNode time_of_flight = group.getDataNode("time_of_flight");
		validateDataNodeLink("time_of_flight", time_of_flight, "/NXentry/NXinstrument/NXdetector/time_of_flight");

	}
}
