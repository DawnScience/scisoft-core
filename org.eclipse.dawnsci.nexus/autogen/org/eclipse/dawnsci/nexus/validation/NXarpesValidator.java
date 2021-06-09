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
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXarpes'.
 */
public class NXarpesValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXarpesValidator() {
		super(NexusApplicationDefinition.NX_ARPES);
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

		// validate field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
		if (!(validateFieldNotNull("title", title))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("title", title, NX_CHAR);

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		if (!(validateFieldNotNull("start_time", start_time))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("start_time", start_time, NX_DATE_TIME);

		// validate field 'definition' of unknown type.
		final ILazyDataset definition = group.getLazyDataset("definition");
		if (!(validateFieldNotNull("definition", definition))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("definition", definition, NX_CHAR);
		validateFieldEnumeration("definition", definition,
				"NXarpes");

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate unnamed child group of type NXsource (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_NXentry_NXinstrument_NXsource(source);
		}

		// validate child group 'monochromator' of type NXmonochromator
		validateGroup_NXentry_NXinstrument_monochromator(group.getMonochromator());

		// validate child group 'analyser' of type NXdetector
		validateGroup_NXentry_NXinstrument_analyser(group.getDetector("analyser"));
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_NXentry_NXinstrument_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		if (!(validateFieldNotNull("type", type))) return;
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

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'probe' of unknown type.
		final ILazyDataset probe = group.getLazyDataset("probe");
		if (!(validateFieldNotNull("probe", probe))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("probe", probe, NX_CHAR);
		validateFieldEnumeration("probe", probe,
				"x-ray");
	}

	/**
	 * Validate group 'monochromator' of type NXmonochromator.
	 */
	private void validateGroup_NXentry_NXinstrument_monochromator(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("monochromator", NXmonochromator.class, group))) return;

		// validate field 'energy' of type NX_NUMBER.
		final ILazyDataset energy = group.getLazyDataset("energy");
		if (!(validateFieldNotNull("energy", energy))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energy", energy, NX_NUMBER);
		validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
	}

	/**
	 * Validate group 'analyser' of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_analyser(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("analyser", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_NUMBER.
		final ILazyDataset data = group.getLazyDataset("data");
		if (!(validateFieldNotNull("data", data))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_NUMBER);
		validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
		validateFieldRank("data", data, 4);
		validateFieldDimensions("data", data, "NXdetector", "np", "i", "j", "tof");

		// validate field 'lens_mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset lens_mode = group.getLazyDataset("lens_mode");
		if (!(validateFieldNotNull("lens_mode", lens_mode))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("lens_mode", lens_mode, NX_CHAR);

		// validate field 'acquisition_mode' of unknown type.
		final ILazyDataset acquisition_mode = group.getLazyDataset("acquisition_mode");
		if (!(validateFieldNotNull("acquisition_mode", acquisition_mode))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("acquisition_mode", acquisition_mode, NX_CHAR);
		validateFieldEnumeration("acquisition_mode", acquisition_mode,
				"swept",
				"fixed");

		// validate field 'entrance_slit_shape' of unknown type. Note: field not defined in base class.
		final ILazyDataset entrance_slit_shape = group.getLazyDataset("entrance_slit_shape");
		if (!(validateFieldNotNull("entrance_slit_shape", entrance_slit_shape))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("entrance_slit_shape", entrance_slit_shape, NX_CHAR);
		validateFieldEnumeration("entrance_slit_shape", entrance_slit_shape,
				"curved",
				"straight");

		// validate field 'entrance_slit_setting' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset entrance_slit_setting = group.getLazyDataset("entrance_slit_setting");
		if (!(validateFieldNotNull("entrance_slit_setting", entrance_slit_setting))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("entrance_slit_setting", entrance_slit_setting, NX_NUMBER);
		validateFieldUnits("entrance_slit_setting", group.getDataNode("entrance_slit_setting"), NX_ANY);

		// validate field 'entrance_slit_size' of unknown type. Note: field not defined in base class.
		final ILazyDataset entrance_slit_size = group.getLazyDataset("entrance_slit_size");
		if (!(validateFieldNotNull("entrance_slit_size", entrance_slit_size))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("entrance_slit_size", entrance_slit_size, NX_CHAR);
		validateFieldUnits("entrance_slit_size", group.getDataNode("entrance_slit_size"), NX_LENGTH);

		// validate field 'pass_energy' of unknown type. Note: field not defined in base class.
		final ILazyDataset pass_energy = group.getLazyDataset("pass_energy");
		if (!(validateFieldNotNull("pass_energy", pass_energy))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("pass_energy", pass_energy, NX_CHAR);
		validateFieldUnits("pass_energy", group.getDataNode("pass_energy"), NX_ENERGY);

		// validate field 'time_per_channel' of unknown type. Note: field not defined in base class.
		final ILazyDataset time_per_channel = group.getLazyDataset("time_per_channel");
		if (!(validateFieldNotNull("time_per_channel", time_per_channel))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("time_per_channel", time_per_channel, NX_CHAR);
		validateFieldUnits("time_per_channel", group.getDataNode("time_per_channel"), NX_TIME);

		// validate field 'angles' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angles = group.getLazyDataset("angles");
		if (!(validateFieldNotNull("angles", angles))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("angles", angles, NX_NUMBER);
		validateFieldUnits("angles", group.getDataNode("angles"), NX_ANGLE);

		// validate field 'energies' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset energies = group.getLazyDataset("energies");
		if (!(validateFieldNotNull("energies", energies))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energies", energies, NX_NUMBER);
		validateFieldUnits("energies", group.getDataNode("energies"), NX_ENERGY);

		// validate field 'sensor_size' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset sensor_size = group.getLazyDataset("sensor_size");
		if (!(validateFieldNotNull("sensor_size", sensor_size))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("sensor_size", sensor_size, NX_INT);
		validateFieldRank("sensor_size", sensor_size, 1);
		validateFieldDimensions("sensor_size", sensor_size, null, 2);

		// validate field 'region_origin' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset region_origin = group.getLazyDataset("region_origin");
		if (!(validateFieldNotNull("region_origin", region_origin))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("region_origin", region_origin, NX_INT);
		validateFieldRank("region_origin", region_origin, 1);
		validateFieldDimensions("region_origin", region_origin, null, 2);

		// validate field 'region_size' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset region_size = group.getLazyDataset("region_size");
		if (!(validateFieldNotNull("region_size", region_size))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("region_size", region_size, NX_INT);
		validateFieldRank("region_size", region_size, 1);
		validateFieldDimensions("region_size", region_size, null, 2);
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'temperature' of type NX_NUMBER.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
		if (!(validateFieldNotNull("temperature", temperature))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("temperature", temperature, NX_NUMBER);
		validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
		validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}
}
