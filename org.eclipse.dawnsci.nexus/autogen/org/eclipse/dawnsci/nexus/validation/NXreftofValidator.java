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

import org.eclipse.january.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXdisk_chopper;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXreftof'.
 */
public class NXreftofValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	@Override
	public void validate(NXroot root) throws NexusValidationException {
		// validate child group 'entry' of type NXentry
		validateGroup_entry(root.getEntry());
	}

	@Override
	public void validate(NXentry entry) throws NexusValidationException {
		validateGroup_entry(entry);
	}

	@Override
	public void validate(NXsubentry subentry) throws NexusValidationException {
		validateGroup_entry(subentry);
	}


	/**
	 * Validate group 'entry' of type NXentry.
	 */
	private void validateGroup_entry(final NXsubentry group) throws NexusValidationException {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		validateGroupNotNull("entry", NXentry.class, group);

		// validate field 'title' of unknown type.
		final IDataset title = group.getTitle();
		validateFieldNotNull("title", title);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions

		// validate field 'start_time' of type NX_DATE_TIME.
		final IDataset start_time = group.getStart_time();
		validateFieldNotNull("start_time", start_time);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("start_time", start_time, NX_DATE_TIME);

		// validate field 'end_time' of type NX_DATE_TIME.
		final IDataset end_time = group.getEnd_time();
		validateFieldNotNull("end_time", end_time);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("end_time", end_time, NX_DATE_TIME);

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		validateFieldNotNull("definition", definition);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldEnumeration("definition", definition,
				"NXreftof");

		// validate child group 'instrument' of type NXinstrument
		validateGroup_entry_instrument(group.getInstrument());

		// validate child group 'sample' of type NXsample
		validateGroup_entry_sample(group.getSample());

		// validate child group 'control' of type NXmonitor
		validateGroup_entry_control(group.getMonitor());

		// validate child group 'data' of type NXdata
		validateGroup_entry_data(group.getData());
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_entry_instrument(final NXinstrument group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("instrument", NXinstrument.class, group);

		// validate field 'name' of type NX_CHAR.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate child group 'chopper' of type NXdisk_chopper
		validateGroup_entry_instrument_chopper(group.getDisk_chopper());

		// validate child group 'detector' of type NXdetector
		validateGroup_entry_instrument_detector(group.getDetector());
	}

	/**
	 * Validate group 'chopper' of type NXdisk_chopper.
	 */
	private void validateGroup_entry_instrument_chopper(final NXdisk_chopper group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("chopper", NXdisk_chopper.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'distance' of type NX_FLOAT.
		final IDataset distance = group.getDistance();
		validateFieldNotNull("distance", distance);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("distance", distance, NX_FLOAT);
		validateFieldUnits("distance", distance, NX_LENGTH);
	}

	/**
	 * Validate group 'detector' of type NXdetector.
	 */
	private void validateGroup_entry_instrument_detector(final NXdetector group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("detector", NXdetector.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT.
		final IDataset data = group.getData();
		validateFieldNotNull("data", data);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_INT);
		validateFieldUnits("data", data, NX_ANY);
		validateFieldRank("data", data, 3);
		validateFieldDimensions("data", data, null, "xSize", "ySize", "nTOF");

		// validate field 'time_of_flight' of type NX_FLOAT.
		final IDataset time_of_flight = group.getTime_of_flight();
		validateFieldNotNull("time_of_flight", time_of_flight);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("time_of_flight", time_of_flight, NX_FLOAT);
		validateFieldUnits("time_of_flight", time_of_flight, NX_TIME_OF_FLIGHT);
		validateFieldRank("time_of_flight", time_of_flight, 1);
		validateFieldDimensions("time_of_flight", time_of_flight, null, "nTOF");

		// validate field 'distance' of type NX_FLOAT.
		final IDataset distance = group.getDistance();
		validateFieldNotNull("distance", distance);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("distance", distance, NX_FLOAT);
		validateFieldUnits("distance", distance, NX_LENGTH);
		validateFieldRank("distance", distance, 3);
		validateFieldDimensions("distance", distance, "NXdetector", "np", "i", "j");

		// validate field 'polar_angle' of type NX_FLOAT.
		final IDataset polar_angle = group.getPolar_angle();
		validateFieldNotNull("polar_angle", polar_angle);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("polar_angle", polar_angle, NX_FLOAT);
		validateFieldUnits("polar_angle", polar_angle, NX_ANGLE);
		validateFieldRank("polar_angle", polar_angle, 3);
		validateFieldDimensions("polar_angle", polar_angle, "NXdetector", "np", "i", "j");

		// validate field 'x_pixel_size' of type NX_FLOAT.
		final IDataset x_pixel_size = group.getX_pixel_size();
		validateFieldNotNull("x_pixel_size", x_pixel_size);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("x_pixel_size", x_pixel_size, NX_FLOAT);
		validateFieldUnits("x_pixel_size", x_pixel_size, NX_LENGTH);
		validateFieldRank("x_pixel_size", x_pixel_size, 2);
		validateFieldDimensions("x_pixel_size", x_pixel_size, "NXdetector", "i", "j");

		// validate field 'y_pixel_size' of type NX_FLOAT.
		final IDataset y_pixel_size = group.getY_pixel_size();
		validateFieldNotNull("y_pixel_size", y_pixel_size);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("y_pixel_size", y_pixel_size, NX_FLOAT);
		validateFieldUnits("y_pixel_size", y_pixel_size, NX_LENGTH);
		validateFieldRank("y_pixel_size", y_pixel_size, 2);
		validateFieldDimensions("y_pixel_size", y_pixel_size, "NXdetector", "i", "j");
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_entry_sample(final NXsample group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("sample", NXsample.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions

		// validate field 'rotation_angle' of type NX_FLOAT.
		final IDataset rotation_angle = group.getRotation_angle();
		validateFieldNotNull("rotation_angle", rotation_angle);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("rotation_angle", rotation_angle, NX_FLOAT);
		validateFieldUnits("rotation_angle", rotation_angle, NX_ANGLE);
	}

	/**
	 * Validate group 'control' of type NXmonitor.
	 */
	private void validateGroup_entry_control(final NXmonitor group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("control", NXmonitor.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'mode' of unknown type.
		final IDataset mode = group.getMode();
		validateFieldNotNull("mode", mode);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldEnumeration("mode", mode,
				"monitor",
				"timer");

		// validate field 'preset' of type NX_FLOAT.
		final IDataset preset = group.getPreset();
		validateFieldNotNull("preset", preset);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("preset", preset, NX_FLOAT);
		validateFieldUnits("preset", preset, NX_ANY);

		// validate field 'integral' of type NX_INT.
		final IDataset integral = group.getIntegral();
		validateFieldNotNull("integral", integral);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("integral", integral, NX_INT);
		validateFieldUnits("integral", integral, NX_ANY);

		// validate field 'time_of_flight' of type NX_FLOAT.
		final IDataset time_of_flight = group.getTime_of_flight();
		validateFieldNotNull("time_of_flight", time_of_flight);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("time_of_flight", time_of_flight, NX_FLOAT);
		validateFieldUnits("time_of_flight", time_of_flight, NX_TIME_OF_FLIGHT);
		validateFieldDimensions("time_of_flight", time_of_flight, "NXmonitor", "");

		// validate field 'data' of type NX_INT.
		final IDataset data = group.getData();
		validateFieldNotNull("data", data);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_INT);
		validateFieldUnits("data", data, NX_ANY);
		validateFieldDimensions("data", data, "NXmonitor", "n");
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_entry_data(final NXdata group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("data", NXdata.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'data' to location '/NXentry/NXinstrument/NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/NXdetector/data");

		// validate link 'time_binning' to location '/NXentry/NXinstrument/NXdetector/time_binning
		final DataNode time_binning = group.getDataNode("time_binning");
		validateDataNodeLink("time_binning", time_binning, "/NXentry/NXinstrument/NXdetector/time_binning");

	}
}
