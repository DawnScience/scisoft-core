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

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXtomophase'.
 */
public class NXtomophaseValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXtomophaseValidator() {
		super(NexusApplicationDefinition.NX_TOMOPHASE);
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
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
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

		// validate field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
		validateFieldNotNull("end_time", end_time);
		if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
		}

		// validate field 'definition' of unknown type.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXtomophase");
		}

		// validate child group 'instrument' of type NXinstrument
		validateGroup_entry_instrument(group.getInstrument());

		// validate child group 'sample' of type NXsample
		validateGroup_entry_sample(group.getSample());

		// validate child group 'control' of type NXmonitor
		validateGroup_entry_control(group.getMonitor("control"));

		// validate child group 'data' of type NXdata
		validateGroup_entry_data(group.getData());
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_entry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate unnamed child group of type NXsource (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_entry_instrument_NXsource(source);
		}

		// validate child group 'bright_field' of type NXdetector
		validateGroup_entry_instrument_bright_field(group.getDetector("bright_field"));

		// validate child group 'dark_field' of type NXdetector
		validateGroup_entry_instrument_dark_field(group.getDetector("dark_field"));

		// validate child group 'sample' of type NXdetector
		validateGroup_entry_instrument_sample(group.getDetector("sample"));
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_entry_instrument_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate field 'type' of unknown type.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"Spallation Neutron Source",
					"Pulsed Reactor Neutron Source",
					"Reactor Neutron Source",
					"Synchrotron X-ray Source",
					"Pulsed Muon Source",
					"Rotating Anode X-ray",
					"Fixed Tube X-ray",
					"UV Laser",
					"Free-Electron Laser",
					"Optical Laser",
					"Ion Source",
					"UV Plasma Source");
		}

		// validate field 'name' of unknown type.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'probe' of unknown type.
		final ILazyDataset probe = group.getLazyDataset("probe");
		validateFieldNotNull("probe", probe);
		if (probe != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("probe", probe, NX_CHAR);
			validateFieldEnumeration("probe", probe,
					"neutron",
					"x-ray",
					"electron");
		}
	}

	/**
	 * Validate group 'bright_field' of type NXdetector.
	 */
	private void validateGroup_entry_instrument_bright_field(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("bright_field", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_INT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 3);
			validateFieldDimensions("data", data, null, "nBrightFrames", "xSize", "ySize");
		}

		// validate field 'sequence_number' of type NX_INT.
		final ILazyDataset sequence_number = group.getLazyDataset("sequence_number");
		validateFieldNotNull("sequence_number", sequence_number);
		if (sequence_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_number", sequence_number, NX_INT);
			validateFieldRank("sequence_number", sequence_number, 1);
			validateFieldDimensions("sequence_number", sequence_number, null, "nBrightFrames");
		}
	}

	/**
	 * Validate group 'dark_field' of type NXdetector.
	 */
	private void validateGroup_entry_instrument_dark_field(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("dark_field", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_INT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 3);
			validateFieldDimensions("data", data, null, "nDarkFrames", "xSize", "ySize");
		}

		// validate field 'sequence_number' of type NX_INT.
		final ILazyDataset sequence_number = group.getLazyDataset("sequence_number");
		validateFieldNotNull("sequence_number", sequence_number);
		if (sequence_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_number", sequence_number, NX_INT);
			validateFieldRank("sequence_number", sequence_number, 1);
			validateFieldDimensions("sequence_number", sequence_number, null, "nDarkFrames");
		}
	}

	/**
	 * Validate group 'sample' of type NXdetector.
	 */
	private void validateGroup_entry_instrument_sample(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_INT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 4);
			validateFieldDimensions("data", data, null, "nSampleFrames", "nPhase", "xSize", "ySize");
		}

		// validate field 'sequence_number' of type NX_INT.
		final ILazyDataset sequence_number = group.getLazyDataset("sequence_number");
		validateFieldNotNull("sequence_number", sequence_number);
		if (sequence_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sequence_number", sequence_number, NX_INT);
			validateFieldRank("sequence_number", sequence_number, 2);
			validateFieldDimensions("sequence_number", sequence_number, null, "nSampleFrames", "nPhase");
		}

		// validate field 'x_pixel_size' of type NX_FLOAT.
		final ILazyDataset x_pixel_size = group.getLazyDataset("x_pixel_size");
		validateFieldNotNull("x_pixel_size", x_pixel_size);
		if (x_pixel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_pixel_size", x_pixel_size, NX_FLOAT);
			validateFieldUnits("x_pixel_size", group.getDataNode("x_pixel_size"), NX_LENGTH);
			validateFieldRank("x_pixel_size", x_pixel_size, 2);
			validateFieldDimensions("x_pixel_size", x_pixel_size, "NXdetector", "i", "j");
		}

		// validate field 'y_pixel_size' of type NX_FLOAT.
		final ILazyDataset y_pixel_size = group.getLazyDataset("y_pixel_size");
		validateFieldNotNull("y_pixel_size", y_pixel_size);
		if (y_pixel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_pixel_size", y_pixel_size, NX_FLOAT);
			validateFieldUnits("y_pixel_size", group.getDataNode("y_pixel_size"), NX_LENGTH);
			validateFieldRank("y_pixel_size", y_pixel_size, 2);
			validateFieldDimensions("y_pixel_size", y_pixel_size, "NXdetector", "i", "j");
		}

		// validate field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 3);
			validateFieldDimensions("distance", distance, "NXdetector", "np", "i", "j");
		}
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_entry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of unknown type.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'rotation_angle' of type NX_FLOAT.
		final ILazyDataset rotation_angle = group.getLazyDataset("rotation_angle");
		validateFieldNotNull("rotation_angle", rotation_angle);
		if (rotation_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("rotation_angle", rotation_angle, NX_FLOAT);
			validateFieldUnits("rotation_angle", group.getDataNode("rotation_angle"), NX_ANGLE);
			validateFieldRank("rotation_angle", rotation_angle, 1);
			validateFieldDimensions("rotation_angle", rotation_angle, null, "nSampleFrames");
		}

		// validate field 'x_translation' of type NX_FLOAT.
		final ILazyDataset x_translation = group.getLazyDataset("x_translation");
		validateFieldNotNull("x_translation", x_translation);
		if (x_translation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_translation", x_translation, NX_FLOAT);
			validateFieldUnits("x_translation", group.getDataNode("x_translation"), NX_LENGTH);
			validateFieldRank("x_translation", x_translation, 1);
			validateFieldDimensions("x_translation", x_translation, null, "nSampleFrames");
		}

		// validate field 'y_translation' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset y_translation = group.getLazyDataset("y_translation");
		validateFieldNotNull("y_translation", y_translation);
		if (y_translation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_translation", y_translation, NX_FLOAT);
			validateFieldUnits("y_translation", group.getDataNode("y_translation"), NX_LENGTH);
			validateFieldRank("y_translation", y_translation, 1);
			validateFieldDimensions("y_translation", y_translation, null, "nSampleFrames");
		}

		// validate field 'z_translation' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset z_translation = group.getLazyDataset("z_translation");
		validateFieldNotNull("z_translation", z_translation);
		if (z_translation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z_translation", z_translation, NX_FLOAT);
			validateFieldUnits("z_translation", group.getDataNode("z_translation"), NX_LENGTH);
			validateFieldRank("z_translation", z_translation, 1);
			validateFieldDimensions("z_translation", z_translation, null, "nSampleFrames");
		}
	}

	/**
	 * Validate group 'control' of type NXmonitor.
	 */
	private void validateGroup_entry_control(final NXmonitor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("control", NXmonitor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'integral' of type NX_FLOAT.
		final ILazyDataset integral = group.getLazyDataset("integral");
		validateFieldNotNull("integral", integral);
		if (integral != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("integral", integral, NX_FLOAT);
			validateFieldUnits("integral", group.getDataNode("integral"), NX_ANY);
			validateFieldRank("integral", integral, 1);
			validateFieldDimensions("integral", integral, null, "nDarkFrames + nBrightFrames + nSampleFrame");
		}
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_entry_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'data' to location '/NXentry/NXinstrument/sample:NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/sample:NXdetector/data");

		// validate link 'rotation_angle' to location '/NXentry/NXsample/rotation_angle
		final DataNode rotation_angle = group.getDataNode("rotation_angle");
		validateDataNodeLink("rotation_angle", rotation_angle, "/NXentry/NXsample/rotation_angle");

	}
}
