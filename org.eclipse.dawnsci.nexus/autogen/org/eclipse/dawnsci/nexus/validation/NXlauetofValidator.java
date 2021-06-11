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
import org.eclipse.dawnsci.analysis.api.tree.Attribute;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXlauetof'.
 */
public class NXlauetofValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXlauetofValidator() {
		super(NexusApplicationDefinition.NX_LAUETOF);
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

		// validate field 'definition' of unknown type.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXlauetof");
		}

		// validate child group 'instrument' of type NXinstrument
		validateGroup_entry_instrument(group.getInstrument());

		// validate child group 'sample' of type NXsample
		validateGroup_entry_sample(group.getSample());

		// validate child group 'control' of type NXmonitor
		validateGroup_entry_control(group.getMonitor("control"));

		// validate child group 'name' of type NXdata
		validateGroup_entry_name(group.getData("name"));
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

		// validate field 'polar_angle' of type NX_FLOAT.
		final ILazyDataset polar_angle = group.getLazyDataset("polar_angle");
		validateFieldNotNull("polar_angle", polar_angle);
		if (polar_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("polar_angle", polar_angle, NX_FLOAT);
			validateFieldUnits("polar_angle", group.getDataNode("polar_angle"), NX_ANGLE);
			validateFieldRank("polar_angle", polar_angle, 3);
			validateFieldDimensions("polar_angle", polar_angle, "NXdetector", "np", "i", "j");
		}

		// validate field 'azimuthal_angle' of type NX_FLOAT.
		final ILazyDataset azimuthal_angle = group.getLazyDataset("azimuthal_angle");
		validateFieldNotNull("azimuthal_angle", azimuthal_angle);
		if (azimuthal_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("azimuthal_angle", azimuthal_angle, NX_FLOAT);
			validateFieldUnits("azimuthal_angle", group.getDataNode("azimuthal_angle"), NX_ANGLE);
			validateFieldRank("azimuthal_angle", azimuthal_angle, 3);
			validateFieldDimensions("azimuthal_angle", azimuthal_angle, "NXdetector", "np", "i", "j");
		}

		// validate field 'data' of type NX_INT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_INT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 3);
			validateFieldDimensions("data", data, null, "nXPixels", "nYPixels", "nTOF");
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

		// validate field 'time_of_flight' of type NX_FLOAT.
		final ILazyDataset time_of_flight = group.getLazyDataset("time_of_flight");
		validateFieldNotNull("time_of_flight", time_of_flight);
		if (time_of_flight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time_of_flight", time_of_flight, NX_FLOAT);
			validateFieldUnits("time_of_flight", group.getDataNode("time_of_flight"), NX_TIME_OF_FLIGHT);
			validateFieldRank("time_of_flight", time_of_flight, 1);
			validateFieldDimensions("time_of_flight", time_of_flight, null, "nTOF");
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

		// validate field 'data' of type NX_INT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_INT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 1);
			validateFieldDimensions("data", data, null, "nTOF");
		}

		// validate field 'time_of_flight' of type NX_FLOAT.
		final ILazyDataset time_of_flight = group.getLazyDataset("time_of_flight");
		validateFieldNotNull("time_of_flight", time_of_flight);
		if (time_of_flight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time_of_flight", time_of_flight, NX_FLOAT);
			validateFieldUnits("time_of_flight", group.getDataNode("time_of_flight"), NX_TIME_OF_FLIGHT);
			validateFieldRank("time_of_flight", time_of_flight, 1);
			validateFieldDimensions("time_of_flight", time_of_flight, null, "nTOF");
		}
	}

	/**
	 * Validate group 'name' of type NXdata.
	 */
	private void validateGroup_entry_name(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("name", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'data' to location '/NXentry/NXinstrument/NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/NXdetector/data");

		// validate link 'time_of_flight' to location '/NXentry/NXinstrument/NXdetector/time_of_flight
		final DataNode time_of_flight = group.getDataNode("time_of_flight");
		validateDataNodeLink("time_of_flight", time_of_flight, "/NXentry/NXinstrument/NXdetector/time_of_flight");

	}
}
