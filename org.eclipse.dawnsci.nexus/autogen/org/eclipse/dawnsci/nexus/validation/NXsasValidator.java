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
import org.eclipse.dawnsci.nexus.NXcollimator;
import org.eclipse.dawnsci.nexus.NXgeometry;
import org.eclipse.dawnsci.nexus.NXshape;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXsas'.
 */
public class NXsasValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXsasValidator() {
		super(NexusApplicationDefinition.NX_SAS);
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

		// validate attribute 'entry'
		final Attribute entry_attr = group.getAttribute("entry");
		if (!(validateAttributeNotNull("entry", entry_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("entry", entry_attr, NX_CHAR);

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
					"NXsas");
		}

		// validate child group 'instrument' of type NXinstrument
		validateGroup_NXentry_instrument(group.getInstrument());

		// validate child group 'sample' of type NXsample
		validateGroup_NXentry_sample(group.getSample());

		// validate child group 'control' of type NXmonitor
		validateGroup_NXentry_control(group.getMonitor("control"));

		// validate child group 'data' of type NXdata
		validateGroup_NXentry_data(group.getData());
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_NXentry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}
		// validate child group 'source' of type NXsource
		validateGroup_NXentry_instrument_source(group.getSource());

		// validate child group 'monochromator' of type NXmonochromator
		validateGroup_NXentry_instrument_monochromator(group.getMonochromator());

		// validate child group 'collimator' of type NXcollimator
		validateGroup_NXentry_instrument_collimator(group.getCollimator());

		// validate child group 'detector' of type NXdetector
		validateGroup_NXentry_instrument_detector(group.getDetector());
	}

	/**
	 * Validate group 'source' of type NXsource.
	 */
	private void validateGroup_NXentry_instrument_source(final NXsource group) {
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
					"x-ray");
		}
	}

	/**
	 * Validate group 'monochromator' of type NXmonochromator.
	 */
	private void validateGroup_NXentry_instrument_monochromator(final NXmonochromator group) {
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

		// validate field 'wavelength_spread' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset wavelength_spread = group.getLazyDataset("wavelength_spread");
		validateFieldNotNull("wavelength_spread", wavelength_spread);
		if (wavelength_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_spread", wavelength_spread, NX_FLOAT);
		}
	}

	/**
	 * Validate group 'collimator' of type NXcollimator.
	 */
	private void validateGroup_NXentry_instrument_collimator(final NXcollimator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("collimator", NXcollimator.class, group))) return;

		// validate child group 'geometry' of type NXgeometry
		validateGroup_NXentry_instrument_collimator_geometry(group.getGeometry());
	}

	/**
	 * Validate group 'geometry' of type NXgeometry.
	 */
	private void validateGroup_NXentry_instrument_collimator_geometry(final NXgeometry group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("geometry", NXgeometry.class, group))) return;

		// validate child group 'shape' of type NXshape
		validateGroup_NXentry_instrument_collimator_geometry_shape(group.getShape());
	}

	/**
	 * Validate group 'shape' of type NXshape.
	 */
	private void validateGroup_NXentry_instrument_collimator_geometry_shape(final NXshape group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("shape", NXshape.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'shape' of type NX_CHAR.
		final ILazyDataset shape = group.getLazyDataset("shape");
		validateFieldNotNull("shape", shape);
		if (shape != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("shape", shape, NX_CHAR);
			validateFieldEnumeration("shape", shape,
					"nxcylinder",
					"nxbox");
		}

		// validate field 'size' of type NX_FLOAT.
		final ILazyDataset size = group.getLazyDataset("size");
		validateFieldNotNull("size", size);
		if (size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("size", size, NX_FLOAT);
			validateFieldUnits("size", group.getDataNode("size"), NX_LENGTH);
			validateFieldDimensions("size", size, "NXshape", "numobj", "nshapepar");
		}
	}

	/**
	 * Validate group 'detector' of type NXdetector.
	 */
	private void validateGroup_NXentry_instrument_detector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("detector", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_NUMBER.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 2);
			validateFieldDimensions("data", data, null, "nXPixel", "nYPixel");
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

		// validate field 'rotation_angle' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset rotation_angle = group.getLazyDataset("rotation_angle");
		validateFieldNotNull("rotation_angle", rotation_angle);
		if (rotation_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("rotation_angle", rotation_angle, NX_FLOAT);
			validateFieldUnits("rotation_angle", group.getDataNode("rotation_angle"), NX_ANGLE);
		}

		// validate field 'aequatorial_angle' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset aequatorial_angle = group.getLazyDataset("aequatorial_angle");
		validateFieldNotNull("aequatorial_angle", aequatorial_angle);
		if (aequatorial_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("aequatorial_angle", aequatorial_angle, NX_FLOAT);
			validateFieldUnits("aequatorial_angle", group.getDataNode("aequatorial_angle"), NX_ANGLE);
		}

		// validate field 'beam_center_x' of type NX_FLOAT.
		final ILazyDataset beam_center_x = group.getLazyDataset("beam_center_x");
		validateFieldNotNull("beam_center_x", beam_center_x);
		if (beam_center_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_x", beam_center_x, NX_FLOAT);
			validateFieldUnits("beam_center_x", group.getDataNode("beam_center_x"), NX_LENGTH);
		}

		// validate field 'beam_center_y' of type NX_FLOAT.
		final ILazyDataset beam_center_y = group.getLazyDataset("beam_center_y");
		validateFieldNotNull("beam_center_y", beam_center_y);
		if (beam_center_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_y", beam_center_y, NX_FLOAT);
			validateFieldUnits("beam_center_y", group.getDataNode("beam_center_y"), NX_LENGTH);
		}
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_NXentry_sample(final NXsample group) {
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

		// validate field 'aequatorial_angle' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset aequatorial_angle = group.getLazyDataset("aequatorial_angle");
		validateFieldNotNull("aequatorial_angle", aequatorial_angle);
		if (aequatorial_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("aequatorial_angle", aequatorial_angle, NX_FLOAT);
			validateFieldUnits("aequatorial_angle", group.getDataNode("aequatorial_angle"), NX_ANGLE);
		}
	}

	/**
	 * Validate group 'control' of type NXmonitor.
	 */
	private void validateGroup_NXentry_control(final NXmonitor group) {
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
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_NXentry_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'data' to location '/NXentry/NXinstrument/NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/NXdetector/data");

	}
}
