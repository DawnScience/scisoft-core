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
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXtransformations;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXsample;

/**
 * Validator for the application definition 'NXcxi_ptycho'.
 */
public class NXcxi_ptychoValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXcxi_ptychoValidator() {
		super(NexusApplicationDefinition.NX_CXI_PTYCHO);
	}

	@Override
	public ValidationReport validate(NXroot root) {
		// validate child group 'entry_1' of type NXentry
		validateGroup_entry_1(root.getEntry("entry_1"));

		// validate unnamed child group of type NXdata
		validateUnnamedGroupOccurrences(root, NXdata.class, false, false);
		final NXdata data = getFirstGroupOrNull(root.getChildren(NXdata.class));
		validateGroup_NXdata(data);

		// validate child group 'data_1' of type NXcollection
		validateGroup_data_1(root.getChild("data_1", NXcollection.class));

		// validate child group 'sample_1' of type NXsample
		validateGroup_sample_1(root.getChild("sample_1", NXsample.class));
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXentry entry) {
		validateGroup_entry_1(entry);
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXsubentry subentry) {
		validateGroup_entry_1(subentry);
		return validationReport;
	}


	/**
	 * Validate group 'entry_1' of type NXentry.
	 */
	private void validateGroup_entry_1(final NXsubentry group) {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		if (!(validateGroupNotNull("entry_1", NXentry.class, group))) return;

		// validate optional field 'title' of type NX_CHAR.
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

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXcxi_ptycho");
		}

		// validate child group 'instrument_1' of type NXinstrument
		validateGroup_entry_1_instrument_1(group.getInstrument("instrument_1"));
	}

	/**
	 * Validate group 'instrument_1' of type NXinstrument.
	 */
	private void validateGroup_entry_1_instrument_1(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument_1", NXinstrument.class, group))) return;

		// validate child group 'source_1' of type NXsource
		validateGroup_entry_1_instrument_1_source_1(group.getSource("source_1"));

		// validate child group 'beam_1' of type NXbeam
		validateGroup_entry_1_instrument_1_beam_1(group.getBeam("beam_1"));

		// validate child group 'detector_1' of type NXdetector
		validateGroup_entry_1_instrument_1_detector_1(group.getDetector("detector_1"));

		// validate optional unnamed child group of type NXmonitor
		validateUnnamedGroupOccurrences(group, NXmonitor.class, true, false);
		final NXmonitor monitor = getFirstGroupOrNull(group.getChildren(NXmonitor.class));
		if (monitor != null) {
			validateGroup_entry_1_instrument_1_NXmonitor(monitor);
		}
	}

	/**
	 * Validate group 'source_1' of type NXsource.
	 */
	private void validateGroup_entry_1_instrument_1_source_1(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source_1", NXsource.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'energy' of type NX_FLOAT.
		final ILazyDataset energy = group.getLazyDataset("energy");
		validateFieldNotNull("energy", energy);
		if (energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy", energy, NX_FLOAT);
			validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
		}

		// validate field 'probe' of type NX_FLOAT.
		final ILazyDataset probe = group.getLazyDataset("probe");
		validateFieldNotNull("probe", probe);
		if (probe != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("probe", probe, NX_FLOAT);
			validateFieldEnumeration("probe", probe,
					"neutron",
					"photon",
					"x-ray",
					"muon",
					"electron",
					"ultraviolet",
					"visible light",
					"positron",
					"proton");
		}

		// validate field 'type' of type NX_FLOAT.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_FLOAT);
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
					"UV Plasma Source",
					"Metal Jet X-ray",
					"Laser",
					"Dye Laser",
					"Broadband Tunable Light Source",
					"Halogen Lamp",
					"LED",
					"Mercury Cadmium Telluride Lamp",
					"Deuterium Lamp",
					"Xenon Lamp",
					"Globar");
		}
	}

	/**
	 * Validate group 'beam_1' of type NXbeam.
	 */
	private void validateGroup_entry_1_instrument_1_beam_1(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_1", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'energy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset energy = group.getLazyDataset("energy");
		validateFieldNotNull("energy", energy);
		if (energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy", energy, NX_FLOAT);
		// validate attribute 'units' of field 'energy' of type NX_CHAR.
		final Attribute energy_attr_units = group.getDataNode("energy").getAttribute("units");
		if (!(validateAttributeNotNull("units", energy_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", energy_attr_units, NX_CHAR);

		}

		// validate optional field 'extent' of type NX_FLOAT.
		final ILazyDataset extent = group.getLazyDataset("extent");
				if (extent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extent", extent, NX_FLOAT);
			validateFieldUnits("extent", group.getDataNode("extent"), NX_LENGTH);
			validateFieldRank("extent", extent, 2);
			validateFieldDimensions("extent", extent, "NXbeam", "nP", 2);
		// validate attribute 'units' of field 'extent' of type NX_CHAR.
		final Attribute extent_attr_units = group.getDataNode("extent").getAttribute("units");
		if (!(validateAttributeNotNull("units", extent_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", extent_attr_units, NX_CHAR);

		}

		// validate optional field 'incident_beam_divergence' of type NX_FLOAT.
		final ILazyDataset incident_beam_divergence = group.getLazyDataset("incident_beam_divergence");
				if (incident_beam_divergence != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_beam_divergence", incident_beam_divergence, NX_FLOAT);
			validateFieldUnits("incident_beam_divergence", group.getDataNode("incident_beam_divergence"), NX_ANGLE);
			validateFieldRank("incident_beam_divergence", incident_beam_divergence, 2);
			validateFieldDimensions("incident_beam_divergence", incident_beam_divergence, "NXbeam", "nP", "c");
		// validate attribute 'units' of field 'incident_beam_divergence' of type NX_CHAR.
		final Attribute incident_beam_divergence_attr_units = group.getDataNode("incident_beam_divergence").getAttribute("units");
		if (!(validateAttributeNotNull("units", incident_beam_divergence_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", incident_beam_divergence_attr_units, NX_CHAR);

		}

		// validate field 'incident_beam_energy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset incident_beam_energy = group.getLazyDataset("incident_beam_energy");
		validateFieldNotNull("incident_beam_energy", incident_beam_energy);
		if (incident_beam_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_beam_energy", incident_beam_energy, NX_FLOAT);
		// validate attribute 'units' of field 'incident_beam_energy' of type NX_CHAR.
		final Attribute incident_beam_energy_attr_units = group.getDataNode("incident_beam_energy").getAttribute("units");
		if (!(validateAttributeNotNull("units", incident_beam_energy_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", incident_beam_energy_attr_units, NX_CHAR);

		}

		// validate field 'incident_energy_spread' of type NX_FLOAT.
		final ILazyDataset incident_energy_spread = group.getLazyDataset("incident_energy_spread");
		validateFieldNotNull("incident_energy_spread", incident_energy_spread);
		if (incident_energy_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_energy_spread", incident_energy_spread, NX_FLOAT);
			validateFieldUnits("incident_energy_spread", group.getDataNode("incident_energy_spread"), NX_ENERGY);
		// validate attribute 'units' of field 'incident_energy_spread' of type NX_CHAR.
		final Attribute incident_energy_spread_attr_units = group.getDataNode("incident_energy_spread").getAttribute("units");
		if (!(validateAttributeNotNull("units", incident_energy_spread_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", incident_energy_spread_attr_units, NX_CHAR);

		}
	}

	/**
	 * Validate group 'detector_1' of type NXdetector.
	 */
	private void validateGroup_entry_1_instrument_1_detector_1(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("detector_1", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);

		// validate field 'translation' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset translation = group.getLazyDataset("translation");
		validateFieldNotNull("translation", translation);
		if (translation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("translation", translation, NX_FLOAT);
			validateFieldUnits("translation", group.getDataNode("translation"), NX_LENGTH);
		// validate attribute 'units' of field 'translation' of type NX_CHAR.
		final Attribute translation_attr_units = group.getDataNode("translation").getAttribute("units");
		if (!(validateAttributeNotNull("units", translation_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", translation_attr_units, NX_CHAR);

		// validate attribute 'axes' of field 'translation' of type NX_CHAR.
		final Attribute translation_attr_axes = group.getDataNode("translation").getAttribute("axes");
		if (!(validateAttributeNotNull("axes", translation_attr_axes))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", translation_attr_axes, NX_CHAR);

		// validate attribute 'interpretation' of field 'translation' of type NX_CHAR.
		final Attribute translation_attr_interpretation = group.getDataNode("translation").getAttribute("interpretation");
		if (!(validateAttributeNotNull("interpretation", translation_attr_interpretation))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("interpretation", translation_attr_interpretation, NX_CHAR);

		}

		// validate field 'data' of type NX_INT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_INT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldDimensions("data", data, null, "npts_x", "npts_y", "frame_size_x", "frame_size_y");
		}

		// validate link 'data_1' to location '/NXentry/NXinstrument/NXdetector/data
		final DataNode data_1 = group.getDataNode("data_1");
		validateDataNodeLink("data_1", data_1, "/NXentry/NXinstrument/NXdetector/data");

		// validate field 'x_pixel_size' of type NX_FLOAT.
		final ILazyDataset x_pixel_size = group.getLazyDataset("x_pixel_size");
		validateFieldNotNull("x_pixel_size", x_pixel_size);
		if (x_pixel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_pixel_size", x_pixel_size, NX_FLOAT);
			validateFieldUnits("x_pixel_size", group.getDataNode("x_pixel_size"), NX_LENGTH);
			validateFieldRank("x_pixel_size", x_pixel_size, 2);
			validateFieldDimensions("x_pixel_size", x_pixel_size, "NXdetector", "i", "j");
		// validate attribute 'units' of field 'x_pixel_size' of type NX_CHAR.
		final Attribute x_pixel_size_attr_units = group.getDataNode("x_pixel_size").getAttribute("units");
		if (!(validateAttributeNotNull("units", x_pixel_size_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", x_pixel_size_attr_units, NX_CHAR);

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
		// validate attribute 'units' of field 'y_pixel_size' of type NX_CHAR.
		final Attribute y_pixel_size_attr_units = group.getDataNode("y_pixel_size").getAttribute("units");
		if (!(validateAttributeNotNull("units", y_pixel_size_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", y_pixel_size_attr_units, NX_CHAR);

		}

		// validate field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 3);
			validateFieldDimensions("distance", distance, "NXdetector", "nP", "i", "j");
		// validate attribute 'units' of field 'distance' of type NX_CHAR.
		final Attribute distance_attr_units = group.getDataNode("distance").getAttribute("units");
		if (!(validateAttributeNotNull("units", distance_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", distance_attr_units, NX_CHAR);

		}

		// validate optional field 'beam_center_x' of type NX_FLOAT.
		final ILazyDataset beam_center_x = group.getLazyDataset("beam_center_x");
				if (beam_center_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_x", beam_center_x, NX_FLOAT);
			validateFieldUnits("beam_center_x", group.getDataNode("beam_center_x"), NX_LENGTH);
		// validate attribute 'units' of field 'beam_center_x' of type NX_CHAR.
		final Attribute beam_center_x_attr_units = group.getDataNode("beam_center_x").getAttribute("units");
		if (!(validateAttributeNotNull("units", beam_center_x_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", beam_center_x_attr_units, NX_CHAR);

		}

		// validate optional field 'beam_center_y' of type NX_FLOAT.
		final ILazyDataset beam_center_y = group.getLazyDataset("beam_center_y");
				if (beam_center_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_y", beam_center_y, NX_FLOAT);
			validateFieldUnits("beam_center_y", group.getDataNode("beam_center_y"), NX_LENGTH);
		// validate attribute 'units' of field 'beam_center_y' of type NX_CHAR.
		final Attribute beam_center_y_attr_units = group.getDataNode("beam_center_y").getAttribute("units");
		if (!(validateAttributeNotNull("units", beam_center_y_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", beam_center_y_attr_units, NX_CHAR);

		}
		// validate child group 'transformations' of type NXtransformations
		validateGroup_entry_1_instrument_1_detector_1_transformations(group.getChild("transformations", NXtransformations.class));
	}

	/**
	 * Validate group 'transformations' of type NXtransformations.
	 */
	private void validateGroup_entry_1_instrument_1_detector_1_transformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transformations", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'vector' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset vector = group.getLazyDataset("vector");
		validateFieldNotNull("vector", vector);
		if (vector != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vector", vector, NX_NUMBER);
		}
	}

	/**
	 * Validate optional unnamed group of type NXmonitor.
	 */
	private void validateGroup_entry_1_instrument_1_NXmonitor(final NXmonitor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmonitor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_FLOAT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_FLOAT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldDimensions("data", data, null, "npts_x", "npts_y");
		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);

		// validate link 'data' to location '/NXentry/NXinstrument/NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/NXdetector/data");

		// validate link 'x' to location '/NXentry/NXsample/NXtransformations/x
		final DataNode x = group.getDataNode("x");
		validateDataNodeLink("x", x, "/NXentry/NXsample/NXtransformations/x");

		// validate link 'y' to location '/NXentry/NXsample/NXtransformations/y
		final DataNode y = group.getDataNode("y");
		validateDataNodeLink("y", y, "/NXentry/NXsample/NXtransformations/y");

		// validate field 'x_indices' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset x_indices = group.getLazyDataset("x_indices");
		validateFieldNotNull("x_indices", x_indices);
		if (x_indices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_indices", x_indices, NX_CHAR);
		}

		// validate field 'y_indices' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset y_indices = group.getLazyDataset("y_indices");
		validateFieldNotNull("y_indices", y_indices);
		if (y_indices != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_indices", y_indices, NX_CHAR);
		}
	}

	/**
	 * Validate group 'data_1' of type NXcollection.
	 */
	private void validateGroup_data_1(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data_1", NXcollection.class, group))) return;

		// validate link 'data' to location '/NXentry/NXinstrument/NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/NXdetector/data");

		// validate link 'translation' to location '/NXentry/NXinstrument/NXdetector/translation
		final DataNode translation = group.getDataNode("translation");
		validateDataNodeLink("translation", translation, "/NXentry/NXinstrument/NXdetector/translation");

	}

	/**
	 * Validate group 'sample_1' of type NXsample.
	 */
	private void validateGroup_sample_1(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_1", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate child group 'transformations' of type NXtransformations
		validateGroup_sample_1_transformations(group.getChild("transformations", NXtransformations.class));

		// validate child group 'geometry_1' of type NXcollection
		validateGroup_sample_1_geometry_1(group.getChild("geometry_1", NXcollection.class));
	}

	/**
	 * Validate group 'transformations' of type NXtransformations.
	 */
	private void validateGroup_sample_1_transformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transformations", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'vector' of type NX_NUMBER.
		final Attribute vector_attr = group.getAttribute("vector");
		if (!(validateAttributeNotNull("vector", vector_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", vector_attr, NX_NUMBER);

	}

	/**
	 * Validate group 'geometry_1' of type NXcollection.
	 */
	private void validateGroup_sample_1_geometry_1(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("geometry_1", NXcollection.class, group))) return;

		// validate link 'translation' to location '/NXentry/NXinstrument/NXdetector/translation
		final DataNode translation = group.getDataNode("translation");
		validateDataNodeLink("translation", translation, "/NXentry/NXinstrument/NXdetector/translation");

	}
}
