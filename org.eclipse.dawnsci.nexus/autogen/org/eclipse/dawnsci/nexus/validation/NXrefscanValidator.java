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
 * Validator for the application definition 'NXrefscan'.
 */
public class NXrefscanValidator extends AbstractNexusValidator implements NexusApplicationValidator {

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
				"NXrefscan");

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

		// validate unnamed child group of type NXsource (possibly multiple)
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_entry_instrument_NXsource(source);
		}

		// validate child group 'monochromator' of type NXmonochromator
		validateGroup_entry_instrument_monochromator(group.getMonochromator());

		// validate unnamed child group of type NXdetector (possibly multiple)
		final Map<String, NXdetector> allDetector = group.getAllDetector();
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_entry_instrument_NXdetector(detector);
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_entry_instrument_NXsource(final NXsource group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXsource.class, group);

		// validate field 'type' of unknown type.
		final IDataset type = group.getType();
		validateFieldNotNull("type", type);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
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

		// validate field 'probe' of unknown type.
		final IDataset probe = group.getProbe();
		validateFieldNotNull("probe", probe);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
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
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_entry_instrument_NXdetector(final NXdetector group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXdetector.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT.
		final IDataset data = group.getData();
		validateFieldNotNull("data", data);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_INT);
		validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
		validateFieldRank("data", data, 1);
		validateFieldDimensions("data", data, null, "nP");

		// validate field 'polar_angle' of type NX_FLOAT.
		final IDataset polar_angle = group.getPolar_angle();
		validateFieldNotNull("polar_angle", polar_angle);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("polar_angle", polar_angle, NX_FLOAT);
		validateFieldUnits("polar_angle", group.getDataNode("polar_angle"), NX_ANGLE);
		validateFieldRank("polar_angle", polar_angle, 1);
		validateFieldDimensions("polar_angle", polar_angle, null, "nP");
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
		validateFieldUnits("rotation_angle", group.getDataNode("rotation_angle"), NX_ANGLE);
		validateFieldRank("rotation_angle", rotation_angle, 1);
		validateFieldDimensions("rotation_angle", rotation_angle, null, "nP");
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
		validateFieldUnits("preset", group.getDataNode("preset"), NX_ANY);

		// validate field 'data' of type NX_FLOAT.
		final IDataset data = group.getData();
		validateFieldNotNull("data", data);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_FLOAT);
		validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
		validateFieldRank("data", data, 1);
		validateFieldDimensions("data", data, null, "nP");
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

		// validate link 'rotation_angle' to location '/NXentry/NXsample/rotation_angle
		final DataNode rotation_angle = group.getDataNode("rotation_angle");
		validateDataNodeLink("rotation_angle", rotation_angle, "/NXentry/NXsample/rotation_angle");

		// validate link 'polar_angle' to location '/NXentry/NXinstrument/NXdetector/polar_angle
		final DataNode polar_angle = group.getDataNode("polar_angle");
		validateDataNodeLink("polar_angle", polar_angle, "/NXentry/NXinstrument/NXdetector/polar_angle");

	}
}
