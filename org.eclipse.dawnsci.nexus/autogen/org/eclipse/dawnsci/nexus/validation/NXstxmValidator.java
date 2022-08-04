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
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXmonitor;

/**
 * Validator for the application definition 'NXstxm'.
 */
public class NXstxmValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXstxmValidator() {
		super(NexusApplicationDefinition.NX_STXM);
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

		// validate field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
		validateFieldNotNull("end_time", end_time);
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
					"NXstxm");
		}

		// validate unnamed child group of type NXinstrument
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, false);
		final NXinstrument instrument = getFirstGroupOrNull(group.getAllInstrument());
		validateGroup_NXentry_NXinstrument(instrument);

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

		// validate optional child group 'control' of type NXmonitor
		if (group.getMonitor("control") != null) {
			validateGroup_NXentry_control(group.getMonitor("control"));
		}
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate unnamed child group of type NXsource
		validateUnnamedGroupOccurrences(group, NXsource.class, false, false);
		final NXsource source = getFirstGroupOrNull(group.getAllSource());
		validateGroup_NXentry_NXinstrument_NXsource(source);

		// validate child group 'monochromator' of type NXmonochromator
		validateGroup_NXentry_NXinstrument_monochromator(group.getMonochromator());

		// validate unnamed child group of type NXdetector (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector.class, false, true);
		final Map<String, NXdetector> allDetector = group.getAllDetector();
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector(detector);
		}

		// validate optional child group 'sample_x' of type NXdetector
		if (group.getDetector("sample_x") != null) {
			validateGroup_NXentry_NXinstrument_sample_x(group.getDetector("sample_x"));
		}

		// validate optional child group 'sample_y' of type NXdetector
		if (group.getDetector("sample_y") != null) {
			validateGroup_NXentry_NXinstrument_sample_y(group.getDetector("sample_y"));
		}

		// validate optional child group 'sample_z' of type NXdetector
		if (group.getDetector("sample_z") != null) {
			validateGroup_NXentry_NXinstrument_sample_z(group.getDetector("sample_z"));
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_NXentry_NXinstrument_NXsource(final NXsource group) {
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
					"muon",
					"electron",
					"ultraviolet",
					"visible light",
					"positron",
					"proton");
		}
	}

	/**
	 * Validate group 'monochromator' of type NXmonochromator.
	 */
	private void validateGroup_NXentry_NXinstrument_monochromator(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("monochromator", NXmonochromator.class, group))) return;

		// validate field 'energy' of type NX_FLOAT.
		final ILazyDataset energy = group.getLazyDataset("energy");
		validateFieldNotNull("energy", energy);
		if (energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy", energy, NX_FLOAT);
			validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
			validateFieldRank("energy", energy, 1);
			validateFieldDimensions("energy", energy, null, "nP");
		}
	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_NUMBER.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldDimensions("data", data, null, "nP");
		}
	}

	/**
	 * Validate optional group 'sample_x' of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_sample_x(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_x", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_FLOAT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_FLOAT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 1);
			validateFieldDimensions("data", data, null, "nP");
		}
	}

	/**
	 * Validate optional group 'sample_y' of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_sample_y(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_y", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_FLOAT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_FLOAT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 1);
			validateFieldDimensions("data", data, null, "nP");
		}
	}

	/**
	 * Validate optional group 'sample_z' of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_sample_z(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_z", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_FLOAT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_FLOAT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 1);
			validateFieldDimensions("data", data, null, "nP");
		}
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

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
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'stxm_scan_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset stxm_scan_type = group.getLazyDataset("stxm_scan_type");
		validateFieldNotNull("stxm_scan_type", stxm_scan_type);
		if (stxm_scan_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("stxm_scan_type", stxm_scan_type, NX_CHAR);
			validateFieldEnumeration("stxm_scan_type", stxm_scan_type,
					"sample point spectrum",
					"sample line spectrum",
					"sample image",
					"sample image stack",
					"sample focus",
					"osa image",
					"osa focus",
					"detector image",
					"generic scan");
		}

		// validate field 'data' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
		}

		// validate field 'energy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset energy = group.getLazyDataset("energy");
		validateFieldNotNull("energy", energy);
		if (energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy", energy, NX_FLOAT);
			validateFieldRank("energy", energy, 1);
			validateFieldDimensions("energy", energy, null, "nE");
		}

		// validate field 'sample_y' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset sample_y = group.getLazyDataset("sample_y");
		validateFieldNotNull("sample_y", sample_y);
		if (sample_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_y", sample_y, NX_FLOAT);
			validateFieldRank("sample_y", sample_y, 1);
			validateFieldDimensions("sample_y", sample_y, null, "nY");
		}

		// validate field 'sample_x' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset sample_x = group.getLazyDataset("sample_x");
		validateFieldNotNull("sample_x", sample_x);
		if (sample_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_x", sample_x, NX_FLOAT);
			validateFieldRank("sample_x", sample_x, 1);
			validateFieldDimensions("sample_x", sample_x, null, "nX");
		}
	}

	/**
	 * Validate optional group 'control' of type NXmonitor.
	 */
	private void validateGroup_NXentry_control(final NXmonitor group) {
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
			validateFieldDimensions("data", data, "NXmonitor", "n");
		}
	}
}
