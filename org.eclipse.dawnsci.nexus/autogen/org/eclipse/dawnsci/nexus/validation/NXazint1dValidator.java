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
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXparameters;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXazint1d'.
 */
public class NXazint1dValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXazint1dValidator() {
		super(NexusApplicationDefinition.NX_AZINT1D);
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

		// validate optional attribute 'default' of type NX_CHAR.
		final Attribute default_attr = group.getAttribute("default");
		if (default_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("default", default_attr, NX_CHAR);
		}

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXazint1d");
		}

		// validate field 'solid_angle_applied' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset solid_angle_applied = group.getLazyDataset("solid_angle_applied");
		validateFieldNotNull("solid_angle_applied", solid_angle_applied);
		if (solid_angle_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("solid_angle_applied", solid_angle_applied, NX_BOOLEAN);
		}

		// validate field 'polarization_applied' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset polarization_applied = group.getLazyDataset("polarization_applied");
		validateFieldNotNull("polarization_applied", polarization_applied);
		if (polarization_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("polarization_applied", polarization_applied, NX_BOOLEAN);
		}

		// validate field 'normalization_applied' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset normalization_applied = group.getLazyDataset("normalization_applied");
		validateFieldNotNull("normalization_applied", normalization_applied);
		if (normalization_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("normalization_applied", normalization_applied, NX_BOOLEAN);
		}

		// validate optional field 'monitor_applied' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset monitor_applied = group.getLazyDataset("monitor_applied");
				if (monitor_applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("monitor_applied", monitor_applied, NX_BOOLEAN);
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate child group 'reduction' of type NXprocess
		validateGroup_NXentry_reduction(group.getProcess("reduction"));

		// validate unnamed child group of type NXmonitor (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmonitor.class, false, true);
		final Map<String, NXmonitor> allMonitor = group.getAllMonitor();
		for (final NXmonitor monitor : allMonitor.values()) {
			validateGroup_NXentry_NXmonitor(monitor);
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

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate unnamed child group of type NXmonochromator (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmonochromator.class, false, true);
		final Map<String, NXmonochromator> allMonochromator = group.getAllMonochromator();
		for (final NXmonochromator monochromator : allMonochromator.values()) {
			validateGroup_NXentry_NXinstrument_NXmonochromator(monochromator);
		}

		// validate unnamed child group of type NXsource (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_NXentry_NXinstrument_NXsource(source);
		}
	}

	/**
	 * Validate unnamed group of type NXmonochromator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmonochromator(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmonochromator.class, group))) return;

		// validate field 'wavelength' of type NX_FLOAT.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
		validateFieldNotNull("wavelength", wavelength);
		if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_FLOAT);
			validateFieldUnits("wavelength", group.getDataNode("wavelength"), "angstrom");
		}

		// validate field 'energy' of type NX_FLOAT.
		final ILazyDataset energy = group.getLazyDataset("energy");
		validateFieldNotNull("energy", energy);
		if (energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy", energy, NX_FLOAT);
			validateFieldUnits("energy", group.getDataNode("energy"), "keV");
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_NXentry_NXinstrument_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

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

		// validate field 'probe' of type NX_CHAR.
		final ILazyDataset probe = group.getLazyDataset("probe");
		validateFieldNotNull("probe", probe);
		if (probe != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("probe", probe, NX_CHAR);
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
	}

	/**
	 * Validate group 'reduction' of type NXprocess.
	 */
	private void validateGroup_NXentry_reduction(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("reduction", NXprocess.class, group))) return;

		// validate field 'program' of type NX_CHAR.
		final ILazyDataset program = group.getLazyDataset("program");
		validateFieldNotNull("program", program);
		if (program != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("program", program, NX_CHAR);
		}

		// validate field 'version' of type NX_CHAR.
		final ILazyDataset version = group.getLazyDataset("version");
		validateFieldNotNull("version", version);
		if (version != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("version", version, NX_CHAR);
		}

		// validate field 'date' of type NX_DATE_TIME.
		final ILazyDataset date = group.getLazyDataset("date");
		validateFieldNotNull("date", date);
		if (date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("date", date, NX_DATE_TIME);
		}

		// validate field 'reference' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset reference = group.getLazyDataset("reference");
		validateFieldNotNull("reference", reference);
		if (reference != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("reference", reference, NX_CHAR);
		}

		// validate optional field 'note' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset note = group.getLazyDataset("note");
				if (note != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("note", note, NX_CHAR);
		}

		// validate child group 'input' of type NXparameters
		validateGroup_NXentry_reduction_input(group.getParameters("input"));
	}

	/**
	 * Validate group 'input' of type NXparameters.
	 */
	private void validateGroup_NXentry_reduction_input(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("input", NXparameters.class, group))) return;

	}

	/**
	 * Validate optional unnamed group of type NXmonitor.
	 */
	private void validateGroup_NXentry_NXmonitor(final NXmonitor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmonitor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_NUMBER.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 1);
			validateFieldDimensions("data", data, null, "nImg");
		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);
		validateAttributeEnumeration("axes", axes_attr,
				"['.', 'radial_axis']");

		// validate attribute 'interpretation' of type NX_CHAR.
		final Attribute interpretation_attr = group.getAttribute("interpretation");
		if (!(validateAttributeNotNull("interpretation", interpretation_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("interpretation", interpretation_attr, NX_CHAR);
		validateAttributeEnumeration("interpretation", interpretation_attr,
				"spectrum");

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"I");

		// validate field 'I' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset I = group.getLazyDataset("I");
		validateFieldNotNull("I", I);
		if (I != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("I", I, NX_NUMBER);
			validateFieldRank("I", I, 2);
			validateFieldDimensions("I", I, null, "nImg", "nRad");
		// validate attribute 'long_name' of field 'I' of type NX_CHAR.
		final Attribute I_attr_long_name = group.getDataNode("I").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", I_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", I_attr_long_name, NX_CHAR);
		validateAttributeEnumeration("long_name", I_attr_long_name,
				"intensity");

		// validate attribute 'units' of field 'I' of type NX_CHAR.
		final Attribute I_attr_units = group.getDataNode("I").getAttribute("units");
		if (!(validateAttributeNotNull("units", I_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", I_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", I_attr_units,
				"arbitrary units");

		}

		// validate optional field 'I_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset I_errors = group.getLazyDataset("I_errors");
				if (I_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("I_errors", I_errors, NX_NUMBER);
			validateFieldRank("I_errors", I_errors, 2);
			validateFieldDimensions("I_errors", I_errors, null, "nImg", "nRad");
		// validate optional attribute 'long_name' of field 'I_errors' of type NX_CHAR.
		final Attribute I_errors_attr_long_name = group.getDataNode("I_errors").getAttribute("long_name");
		if (I_errors_attr_long_name != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("long_name", I_errors_attr_long_name, NX_CHAR);
			validateAttributeEnumeration("long_name", I_errors_attr_long_name,
					"estimated intensity error");
		}

		// validate optional attribute 'units' of field 'I_errors' of type NX_CHAR.
		final Attribute I_errors_attr_units = group.getDataNode("I_errors").getAttribute("units");
		if (I_errors_attr_units != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("units", I_errors_attr_units, NX_CHAR);
			validateAttributeEnumeration("units", I_errors_attr_units,
					"arbitrary units");
		}

		}

		// validate field 'radial_axis' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset radial_axis = group.getLazyDataset("radial_axis");
		validateFieldNotNull("radial_axis", radial_axis);
		if (radial_axis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("radial_axis", radial_axis, NX_NUMBER);
			validateFieldRank("radial_axis", radial_axis, 1);
			validateFieldDimensions("radial_axis", radial_axis, null, "nRad");
		// validate attribute 'long_name' of field 'radial_axis' of type NX_CHAR.
		final Attribute radial_axis_attr_long_name = group.getDataNode("radial_axis").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", radial_axis_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", radial_axis_attr_long_name, NX_CHAR);
		validateAttributeEnumeration("long_name", radial_axis_attr_long_name,
				"q",
				"2theta");

		// validate attribute 'units' of field 'radial_axis' of type NX_CHAR.
		final Attribute radial_axis_attr_units = group.getDataNode("radial_axis").getAttribute("units");
		if (!(validateAttributeNotNull("units", radial_axis_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", radial_axis_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", radial_axis_attr_units,
				"NX_PER_LENGTH",
				"NX_WAVENUMBER",
				"NX_ANGLE");

		}

		// validate optional field 'radial_axis_edges' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset radial_axis_edges = group.getLazyDataset("radial_axis_edges");
				if (radial_axis_edges != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("radial_axis_edges", radial_axis_edges, NX_NUMBER);
			validateFieldRank("radial_axis_edges", radial_axis_edges, 1);
			validateFieldDimensions("radial_axis_edges", radial_axis_edges, null, "nRadEdge");
		// validate attribute 'long_name' of field 'radial_axis_edges' of type NX_CHAR.
		final Attribute radial_axis_edges_attr_long_name = group.getDataNode("radial_axis_edges").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", radial_axis_edges_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", radial_axis_edges_attr_long_name, NX_CHAR);
		validateAttributeEnumeration("long_name", radial_axis_edges_attr_long_name,
				"q bin edges",
				"2theta bin edges");

		// validate attribute 'units' of field 'radial_axis_edges' of type NX_CHAR.
		final Attribute radial_axis_edges_attr_units = group.getDataNode("radial_axis_edges").getAttribute("units");
		if (!(validateAttributeNotNull("units", radial_axis_edges_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", radial_axis_edges_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", radial_axis_edges_attr_units,
				"NX_PER_LENGTH",
				"NX_WAVENUMBER",
				"NX_ANGLE");

		}

		// validate optional field 'norm' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset norm = group.getLazyDataset("norm");
				if (norm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("norm", norm, NX_NUMBER);
			validateFieldRank("norm", norm, 1);
			validateFieldDimensions("norm", norm, null, "nRad");
		// validate attribute 'long_name' of field 'norm' of type NX_CHAR.
		final Attribute norm_attr_long_name = group.getDataNode("norm").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", norm_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", norm_attr_long_name, NX_CHAR);
		validateAttributeEnumeration("long_name", norm_attr_long_name,
				"effective number of pixels contributing to the corresponding bin");

		// validate attribute 'units' of field 'norm' of type NX_CHAR.
		final Attribute norm_attr_units = group.getDataNode("norm").getAttribute("units");
		if (!(validateAttributeNotNull("units", norm_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", norm_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", norm_attr_units,
				"arbitrary units");

		}
	}
}
