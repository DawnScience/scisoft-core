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
import org.eclipse.dawnsci.nexus.NXcrystal;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXmonopd'.
 */
public class NXmonopdValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXmonopdValidator() {
		super(NexusApplicationDefinition.NX_MONOPD);
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

		// validate field 'title' of type NX_CHAR.
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

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXmonopd");
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_entry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_entry_NXsample(sample);
		}

		// validate unnamed child group of type NXmonitor (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmonitor.class, false, true);
		final Map<String, NXmonitor> allMonitor = group.getAllMonitor();
		for (final NXmonitor monitor : allMonitor.values()) {
			validateGroup_entry_NXmonitor(monitor);
		}

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_entry_NXdata(data);
		}
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_entry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate unnamed child group of type NXsource (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_entry_NXinstrument_NXsource(source);
		}

		// validate unnamed child group of type NXcrystal (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcrystal.class, false, true);
		final Map<String, NXcrystal> allCrystal = group.getAllCrystal();
		for (final NXcrystal crystal : allCrystal.values()) {
			validateGroup_entry_NXinstrument_NXcrystal(crystal);
		}

		// validate unnamed child group of type NXdetector (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector.class, false, true);
		final Map<String, NXdetector> allDetector = group.getAllDetector();
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_entry_NXinstrument_NXdetector(detector);
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_entry_NXinstrument_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate field 'type' of type NX_CHAR.
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
					"UV Plasma Source",
					"Metal Jet X-ray");
		}

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'probe' of type NX_CHAR.
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
	 * Validate unnamed group of type NXcrystal.
	 */
	private void validateGroup_entry_NXinstrument_NXcrystal(final NXcrystal group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcrystal.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'wavelength' of type NX_FLOAT.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
		validateFieldNotNull("wavelength", wavelength);
		if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_FLOAT);
			validateFieldUnits("wavelength", group.getDataNode("wavelength"), NX_WAVELENGTH);
			validateFieldRank("wavelength", wavelength, 1);
			validateFieldDimensions("wavelength", wavelength, null, "i");
		}
	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_entry_NXinstrument_NXdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'polar_angle' of type NX_FLOAT.
		final ILazyDataset polar_angle = group.getLazyDataset("polar_angle");
		validateFieldNotNull("polar_angle", polar_angle);
		if (polar_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("polar_angle", polar_angle, NX_FLOAT);
			validateFieldUnits("polar_angle", group.getDataNode("polar_angle"), NX_ANGLE);
			validateFieldRank("polar_angle", polar_angle, 1);
			validateFieldDimensions("polar_angle", polar_angle, null, "nDet");
		}

		// validate field 'data' of type NX_INT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_INT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 1);
			validateFieldDimensions("data", data, null, "nDet");
		}
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_entry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of type NX_CHAR.
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
		}
	}

	/**
	 * Validate unnamed group of type NXmonitor.
	 */
	private void validateGroup_entry_NXmonitor(final NXmonitor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmonitor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'mode' of type NX_CHAR.
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

		// validate link 'polar_angle' to location '/NXentry/NXinstrument/NXdetector/polar_angle
		final DataNode polar_angle = group.getDataNode("polar_angle");
		validateDataNodeLink("polar_angle", polar_angle, "/NXentry/NXinstrument/NXdetector/polar_angle");

		// validate link 'data' to location '/NXentry/NXinstrument/NXdetector/data
		final DataNode data = group.getDataNode("data");
		validateDataNodeLink("data", data, "/NXentry/NXinstrument/NXdetector/data");

	}
}
