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
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXxbase'.
 */
public class NXxbaseValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXxbaseValidator() {
		super(NexusApplicationDefinition.NX_XBASE);
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

		// validate field 'definition' of unknown type.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXxbase");
		}

		// validate child group 'instrument' of type NXinstrument
		validateGroup_entry_instrument(group.getInstrument());

		// validate child group 'sample' of type NXsample
		validateGroup_entry_sample(group.getSample());

		// validate child group 'control' of type NXmonitor
		validateGroup_entry_control(group.getMonitor("control"));

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_entry_NXdata(data);
		}
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_entry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate child group 'source' of type NXsource
		validateGroup_entry_instrument_source(group.getSource());

		// validate child group 'monochromator' of type NXmonochromator
		validateGroup_entry_instrument_monochromator(group.getMonochromator());

		// validate child group 'detector' of type NXdetector
		validateGroup_entry_instrument_detector(group.getDetector());
	}

	/**
	 * Validate group 'source' of type NXsource.
	 */
	private void validateGroup_entry_instrument_source(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source", NXsource.class, group))) return;

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
	 * Validate group 'monochromator' of type NXmonochromator.
	 */
	private void validateGroup_entry_instrument_monochromator(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("monochromator", NXmonochromator.class, group))) return;

		// validate field 'wavelength' of type NX_FLOAT.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
		validateFieldNotNull("wavelength", wavelength);
		if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_FLOAT);
			validateFieldUnits("wavelength", group.getDataNode("wavelength"), NX_WAVELENGTH);
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
			validateFieldDimensions("data", data, null, "nP", "nXPixels", "nYPixels");
		// validate attribute 'signal' of field 'data'
		final Attribute data_attr_signal = group.getDataNode("data").getAttribute("signal");
		if (!(validateAttributeNotNull("signal", data_attr_signal))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", data_attr_signal, NX_POSINT);
		validateAttributeEnumeration("signal", data_attr_signal,
				"1");

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

		// validate field 'frame_start_number' of type NX_INT.
		final ILazyDataset frame_start_number = group.getLazyDataset("frame_start_number");
		validateFieldNotNull("frame_start_number", frame_start_number);
		if (frame_start_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("frame_start_number", frame_start_number, NX_INT);
		}
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_entry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'orientation_matrix' of type NX_FLOAT.
		final ILazyDataset orientation_matrix = group.getLazyDataset("orientation_matrix");
		validateFieldNotNull("orientation_matrix", orientation_matrix);
		if (orientation_matrix != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("orientation_matrix", orientation_matrix, NX_FLOAT);
			validateFieldRank("orientation_matrix", orientation_matrix, 2);
			validateFieldDimensions("orientation_matrix", orientation_matrix, null, 3, 3);
		}

		// validate field 'unit_cell' of type NX_FLOAT.
		final ILazyDataset unit_cell = group.getLazyDataset("unit_cell");
		validateFieldNotNull("unit_cell", unit_cell);
		if (unit_cell != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("unit_cell", unit_cell, NX_FLOAT);
			validateFieldUnits("unit_cell", group.getDataNode("unit_cell"), NX_LENGTH);
			validateFieldRank("unit_cell", unit_cell, 1);
			validateFieldDimensions("unit_cell", unit_cell, null, 6);
		}

		// validate field 'temperature' of type NX_FLOAT.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
		validateFieldNotNull("temperature", temperature);
		if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_FLOAT);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldRank("temperature", temperature, 1);
			validateFieldDimensions("temperature", temperature, null, "nP");
		}

		// validate field 'x_translation' of type NX_FLOAT.
		final ILazyDataset x_translation = group.getLazyDataset("x_translation");
		validateFieldNotNull("x_translation", x_translation);
		if (x_translation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_translation", x_translation, NX_FLOAT);
			validateFieldUnits("x_translation", group.getDataNode("x_translation"), NX_LENGTH);
		}

		// validate field 'y_translation' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset y_translation = group.getLazyDataset("y_translation");
		validateFieldNotNull("y_translation", y_translation);
		if (y_translation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_translation", y_translation, NX_FLOAT);
			validateFieldUnits("y_translation", group.getDataNode("y_translation"), NX_LENGTH);
		}

		// validate field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		}
	}

	/**
	 * Validate group 'control' of type NXmonitor.
	 */
	private void validateGroup_entry_control(final NXmonitor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("control", NXmonitor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'mode' of unknown type.
		final ILazyDataset mode = group.getLazyDataset("mode");
		validateFieldNotNull("mode", mode);
		if (mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mode", mode, NX_CHAR);
			validateFieldEnumeration("mode", mode,
					"monitor",
					"timer");
		}

		// validate field 'preset' of type NX_FLOAT.
		final ILazyDataset preset = group.getLazyDataset("preset");
		validateFieldNotNull("preset", preset);
		if (preset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("preset", preset, NX_FLOAT);
			validateFieldUnits("preset", group.getDataNode("preset"), NX_ANY);
		}

		// validate field 'integral' of type NX_FLOAT.
		final ILazyDataset integral = group.getLazyDataset("integral");
		validateFieldNotNull("integral", integral);
		if (integral != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("integral", integral, NX_FLOAT);
			validateFieldUnits("integral", group.getDataNode("integral"), NX_ANY);
		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_entry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'data' to location '/NXentry/NXinstrument/NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/NXdetector/data");

	}
}
