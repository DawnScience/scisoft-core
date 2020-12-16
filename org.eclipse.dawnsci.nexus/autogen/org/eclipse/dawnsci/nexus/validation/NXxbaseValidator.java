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
		validateFieldType("title", title, NX_CHAR);

		// validate field 'start_time' of type NX_DATE_TIME.
		final IDataset start_time = group.getStart_time();
		validateFieldNotNull("start_time", start_time);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("start_time", start_time, NX_DATE_TIME);

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		validateFieldNotNull("definition", definition);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("definition", definition, NX_CHAR);
		validateFieldEnumeration("definition", definition,
				"NXxbase");

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
	private void validateGroup_entry_instrument(final NXinstrument group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("instrument", NXinstrument.class, group);

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
	private void validateGroup_entry_instrument_source(final NXsource group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("source", NXsource.class, group);

		// validate field 'type' of unknown type.
		final IDataset type = group.getType();
		validateFieldNotNull("type", type);
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

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'probe' of unknown type.
		final IDataset probe = group.getProbe();
		validateFieldNotNull("probe", probe);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("probe", probe, NX_CHAR);
		validateFieldEnumeration("probe", probe,
				"neutron",
				"x-ray",
				"electron");
	}

	/**
	 * Validate group 'monochromator' of type NXmonochromator.
	 */
	private void validateGroup_entry_instrument_monochromator(final NXmonochromator group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("monochromator", NXmonochromator.class, group);

		// validate field 'wavelength' of type NX_FLOAT.
		final IDataset wavelength = group.getWavelength();
		validateFieldNotNull("wavelength", wavelength);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("wavelength", wavelength, NX_FLOAT);
		validateFieldUnits("wavelength", group.getDataNode("wavelength"), NX_WAVELENGTH);
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
		validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
		validateFieldRank("data", data, 3);
		validateFieldDimensions("data", data, null, "nP", "nXPixels", "nYPixels");
		// validate attribute 'signal' of field 'data'
		final Attribute data_attr_signal = group.getDataNode("data").getAttribute("signal");
		validateAttributeNotNull("signal", data_attr_signal);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", data_attr_signal, NX_POSINT);
		validateAttributeEnumeration("signal", data_attr_signal,
				"1");


		// validate field 'x_pixel_size' of type NX_FLOAT.
		final IDataset x_pixel_size = group.getX_pixel_size();
		validateFieldNotNull("x_pixel_size", x_pixel_size);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("x_pixel_size", x_pixel_size, NX_FLOAT);
		validateFieldUnits("x_pixel_size", group.getDataNode("x_pixel_size"), NX_LENGTH);
		validateFieldRank("x_pixel_size", x_pixel_size, 2);
		validateFieldDimensions("x_pixel_size", x_pixel_size, "NXdetector", "i", "j");

		// validate field 'y_pixel_size' of type NX_FLOAT.
		final IDataset y_pixel_size = group.getY_pixel_size();
		validateFieldNotNull("y_pixel_size", y_pixel_size);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("y_pixel_size", y_pixel_size, NX_FLOAT);
		validateFieldUnits("y_pixel_size", group.getDataNode("y_pixel_size"), NX_LENGTH);
		validateFieldRank("y_pixel_size", y_pixel_size, 2);
		validateFieldDimensions("y_pixel_size", y_pixel_size, "NXdetector", "i", "j");

		// validate field 'distance' of type NX_FLOAT.
		final IDataset distance = group.getDistance();
		validateFieldNotNull("distance", distance);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("distance", distance, NX_FLOAT);
		validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		validateFieldRank("distance", distance, 3);
		validateFieldDimensions("distance", distance, "NXdetector", "np", "i", "j");

		// validate field 'frame_start_number' of type NX_INT.
		final IDataset frame_start_number = group.getFrame_start_number();
		validateFieldNotNull("frame_start_number", frame_start_number);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("frame_start_number", frame_start_number, NX_INT);
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_entry_sample(final NXsample group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("sample", NXsample.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of type NX_CHAR.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'orientation_matrix' of type NX_FLOAT.
		final IDataset orientation_matrix = group.getOrientation_matrix();
		validateFieldNotNull("orientation_matrix", orientation_matrix);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("orientation_matrix", orientation_matrix, NX_FLOAT);
		validateFieldRank("orientation_matrix", orientation_matrix, 2);
		validateFieldDimensions("orientation_matrix", orientation_matrix, null, 3, 3);

		// validate field 'unit_cell' of type NX_FLOAT.
		final IDataset unit_cell = group.getUnit_cell();
		validateFieldNotNull("unit_cell", unit_cell);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("unit_cell", unit_cell, NX_FLOAT);
		validateFieldUnits("unit_cell", group.getDataNode("unit_cell"), NX_LENGTH);
		validateFieldRank("unit_cell", unit_cell, 1);
		validateFieldDimensions("unit_cell", unit_cell, null, 6);

		// validate field 'temperature' of type NX_FLOAT.
		final IDataset temperature = group.getTemperature();
		validateFieldNotNull("temperature", temperature);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("temperature", temperature, NX_FLOAT);
		validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
		validateFieldRank("temperature", temperature, 1);
		validateFieldDimensions("temperature", temperature, null, "nP");

		// validate field 'x_translation' of type NX_FLOAT.
		final IDataset x_translation = group.getX_translation();
		validateFieldNotNull("x_translation", x_translation);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("x_translation", x_translation, NX_FLOAT);
		validateFieldUnits("x_translation", group.getDataNode("x_translation"), NX_LENGTH);

		// validate field 'y_translation' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset y_translation = group.getDataset("y_translation");
		validateFieldNotNull("y_translation", y_translation);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("y_translation", y_translation, NX_FLOAT);
		validateFieldUnits("y_translation", group.getDataNode("y_translation"), NX_LENGTH);

		// validate field 'distance' of type NX_FLOAT.
		final IDataset distance = group.getDistance();
		validateFieldNotNull("distance", distance);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("distance", distance, NX_FLOAT);
		validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
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
		validateFieldType("mode", mode, NX_CHAR);
		validateFieldEnumeration("mode", mode,
				"monitor",
				"timer");

		// validate field 'preset' of type NX_FLOAT.
		final IDataset preset = group.getPreset();
		validateFieldNotNull("preset", preset);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("preset", preset, NX_FLOAT);
		validateFieldUnits("preset", group.getDataNode("preset"), NX_ANY);

		// validate field 'integral' of type NX_FLOAT.
		final IDataset integral = group.getIntegral();
		validateFieldNotNull("integral", integral);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("integral", integral, NX_FLOAT);
		validateFieldUnits("integral", group.getDataNode("integral"), NX_ANY);
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_entry_NXdata(final NXdata group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXdata.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'data' to location '/NXentry/NXinstrument/NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/NXdetector/data");

	}
}
