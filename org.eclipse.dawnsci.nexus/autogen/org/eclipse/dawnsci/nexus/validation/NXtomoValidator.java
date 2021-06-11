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
 * Validator for the application definition 'NXtomo'.
 */
public class NXtomoValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXtomoValidator() {
		super(NexusApplicationDefinition.NX_TOMO);
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

		// validate optional field 'title' of unknown type.
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

		// validate field 'definition' of unknown type.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXtomo");
		}

		// validate child group 'instrument' of type NXinstrument
		validateGroup_entry_instrument(group.getInstrument());

		// validate child group 'sample' of type NXsample
		validateGroup_entry_sample(group.getSample());

		// validate optional child group 'control' of type NXmonitor
		if (group.getMonitor("control") != null) {
			validateGroup_entry_control(group.getMonitor("control"));
		}

		// validate child group 'data' of type NXdata
		validateGroup_entry_data(group.getData());
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_entry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate optional unnamed child group of type NXsource
		validateUnnamedGroupOccurrences(group, NXsource.class, true, false);
		final NXsource source = getFirstGroupOrNull(group.getAllSource());
		if (source != null) {
			validateGroup_entry_instrument_NXsource(source);
		}

		// validate child group 'detector' of type NXdetector
		validateGroup_entry_instrument_detector(group.getDetector());
	}

	/**
	 * Validate optional unnamed group of type NXsource.
	 */
	private void validateGroup_entry_instrument_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate optional field 'type' of unknown type.
		final ILazyDataset type = group.getLazyDataset("type");
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

		// validate optional field 'name' of unknown type.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'probe' of unknown type.
		final ILazyDataset probe = group.getLazyDataset("probe");
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
	 * Validate group 'detector' of type NXdetector.
	 */
	private void validateGroup_entry_instrument_detector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("detector", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_INT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 3);
			validateFieldDimensions("data", data, null, "nFrames", "xSize", "ySize");
		}

		// validate field 'image_key' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset image_key = group.getLazyDataset("image_key");
		validateFieldNotNull("image_key", image_key);
		if (image_key != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("image_key", image_key, NX_INT);
			validateFieldRank("image_key", image_key, 1);
			validateFieldDimensions("image_key", image_key, null, "nFrames");
		}

		// validate optional field 'x_pixel_size' of type NX_FLOAT.
		final ILazyDataset x_pixel_size = group.getLazyDataset("x_pixel_size");
				if (x_pixel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_pixel_size", x_pixel_size, NX_FLOAT);
			validateFieldUnits("x_pixel_size", group.getDataNode("x_pixel_size"), NX_LENGTH);
			validateFieldRank("x_pixel_size", x_pixel_size, 2);
			validateFieldDimensions("x_pixel_size", x_pixel_size, "NXdetector", "i", "j");
		}

		// validate optional field 'y_pixel_size' of type NX_FLOAT.
		final ILazyDataset y_pixel_size = group.getLazyDataset("y_pixel_size");
				if (y_pixel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_pixel_size", y_pixel_size, NX_FLOAT);
			validateFieldUnits("y_pixel_size", group.getDataNode("y_pixel_size"), NX_LENGTH);
			validateFieldRank("y_pixel_size", y_pixel_size, 2);
			validateFieldDimensions("y_pixel_size", y_pixel_size, "NXdetector", "i", "j");
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

		// validate optional field 'x_rotation_axis_pixel_position' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset x_rotation_axis_pixel_position = group.getLazyDataset("x_rotation_axis_pixel_position");
				if (x_rotation_axis_pixel_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_rotation_axis_pixel_position", x_rotation_axis_pixel_position, NX_FLOAT);
		}

		// validate optional field 'y_rotation_axis_pixel_position' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset y_rotation_axis_pixel_position = group.getLazyDataset("y_rotation_axis_pixel_position");
				if (y_rotation_axis_pixel_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_rotation_axis_pixel_position", y_rotation_axis_pixel_position, NX_FLOAT);
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
			validateFieldDimensions("rotation_angle", rotation_angle, null, "nFrames");
		}

		// validate optional field 'x_translation' of type NX_FLOAT.
		final ILazyDataset x_translation = group.getLazyDataset("x_translation");
				if (x_translation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_translation", x_translation, NX_FLOAT);
			validateFieldUnits("x_translation", group.getDataNode("x_translation"), NX_LENGTH);
			validateFieldRank("x_translation", x_translation, 1);
			validateFieldDimensions("x_translation", x_translation, null, "nFrames");
		}

		// validate optional field 'y_translation' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset y_translation = group.getLazyDataset("y_translation");
				if (y_translation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_translation", y_translation, NX_FLOAT);
			validateFieldUnits("y_translation", group.getDataNode("y_translation"), NX_LENGTH);
			validateFieldRank("y_translation", y_translation, 1);
			validateFieldDimensions("y_translation", y_translation, null, "nFrames");
		}

		// validate optional field 'z_translation' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset z_translation = group.getLazyDataset("z_translation");
				if (z_translation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z_translation", z_translation, NX_FLOAT);
			validateFieldUnits("z_translation", group.getDataNode("z_translation"), NX_LENGTH);
			validateFieldRank("z_translation", z_translation, 1);
			validateFieldDimensions("z_translation", z_translation, null, "nFrames");
		}
	}

	/**
	 * Validate optional group 'control' of type NXmonitor.
	 */
	private void validateGroup_entry_control(final NXmonitor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("control", NXmonitor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_FLOAT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_FLOAT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 1);
			validateFieldDimensions("data", data, null, "nFrames");
		}
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_entry_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'data' to location '/NXentry/NXinstrument/detector:NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/detector:NXdetector/data");

		// validate link 'rotation_angle' to location '/NXentry/NXsample/rotation_angle
		final DataNode rotation_angle = group.getDataNode("rotation_angle");
		validateDataNodeLink("rotation_angle", rotation_angle, "/NXentry/NXsample/rotation_angle");

		// validate link 'image_key' to location '/NXentry/NXinstrument/detector:NXdetector/image_key
		final DataNode image_key = group.getDataNode("image_key");
		validateDataNodeLink("image_key", image_key, "/NXentry/NXinstrument/detector:NXdetector/image_key");

	}
}
