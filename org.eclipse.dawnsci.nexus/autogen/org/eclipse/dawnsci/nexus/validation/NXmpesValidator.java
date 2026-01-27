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
import org.eclipse.dawnsci.nexus.NXcoordinate_system;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXresolution;
import org.eclipse.dawnsci.nexus.NXfabrication;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXelectronanalyzer;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXcollectioncolumn;
import org.eclipse.dawnsci.nexus.NXaperture;
import org.eclipse.dawnsci.nexus.NXenergydispersion;
import org.eclipse.dawnsci.nexus.NXelectron_detector;
import org.eclipse.dawnsci.nexus.NXmanipulator;
import org.eclipse.dawnsci.nexus.NXsensor;
import org.eclipse.dawnsci.nexus.NXactuator;
import org.eclipse.dawnsci.nexus.NXpid_controller;
import org.eclipse.dawnsci.nexus.NXlog;
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXinsertion_device;
import org.eclipse.dawnsci.nexus.NXhistory;
import org.eclipse.dawnsci.nexus.NXcalibration;
import org.eclipse.dawnsci.nexus.NXregistration;
import org.eclipse.dawnsci.nexus.NXdistortion;
import org.eclipse.dawnsci.nexus.NXfit;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXactivity;
import org.eclipse.dawnsci.nexus.NXenvironment;

/**
 * Validator for the application definition 'NXmpes'.
 */
public class NXmpesValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXmpesValidator() {
		super(NexusApplicationDefinition.NX_MPES);
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

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXmpes");
		// validate attribute 'version' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (!(validateAttributeNotNull("version", definition_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", definition_attr_version, NX_CHAR);

		}

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

		// validate optional field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
				if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
		}

		// validate optional field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
				if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
		}

		// validate optional field 'transitions' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset transitions = group.getLazyDataset("transitions");
				if (transitions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("transitions", transitions, NX_CHAR);
		}

		// validate unnamed child group of type NXcoordinate_system (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcoordinate_system.class, false, true);
		final Map<String, NXcoordinate_system> allCoordinate_system = group.getChildren(NXcoordinate_system.class);
		for (final NXcoordinate_system coordinate_system : allCoordinate_system.values()) {
			validateGroup_NXentry_NXcoordinate_system(coordinate_system);
		}

		// validate unnamed child group of type NXuser (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXuser.class, false, true);
		final Map<String, NXuser> allUser = group.getAllUser();
		for (final NXuser user : allUser.values()) {
			validateGroup_NXentry_NXuser(user);
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate optional child group 'energy_axis_calibration' of type NXcalibration
		if (group.getChild("energy_axis_calibration", NXcalibration.class) != null) {
			validateGroup_NXentry_energy_axis_calibration(group.getChild("energy_axis_calibration", NXcalibration.class));
		}

		// validate optional child group 'AXIS_axis_calibration' of type NXcalibration
		if (group.getChild("AXIS_axis_calibration", NXcalibration.class) != null) {
			validateGroup_NXentry_AXIS_axis_calibration(group.getChild("AXIS_axis_calibration", NXcalibration.class));
		}

		// validate optional child group 'energy_referencing' of type NXcalibration
		if (group.getChild("energy_referencing", NXcalibration.class) != null) {
			validateGroup_NXentry_energy_referencing(group.getChild("energy_referencing", NXcalibration.class));
		}

		// validate optional child group 'transmission_correction' of type NXcalibration
		if (group.getChild("transmission_correction", NXcalibration.class) != null) {
			validateGroup_NXentry_transmission_correction(group.getChild("transmission_correction", NXcalibration.class));
		}

		// validate unnamed child group of type NXregistration (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXregistration.class, false, true);
		final Map<String, NXregistration> allRegistration = group.getChildren(NXregistration.class);
		for (final NXregistration registration : allRegistration.values()) {
			validateGroup_NXentry_NXregistration(registration);
		}

		// validate unnamed child group of type NXdistortion (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdistortion.class, false, true);
		final Map<String, NXdistortion> allDistortion = group.getChildren(NXdistortion.class);
		for (final NXdistortion distortion : allDistortion.values()) {
			validateGroup_NXentry_NXdistortion(distortion);
		}

		// validate unnamed child group of type NXcalibration (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcalibration.class, false, true);
		final Map<String, NXcalibration> allCalibration = group.getChildren(NXcalibration.class);
		for (final NXcalibration calibration : allCalibration.values()) {
			validateGroup_NXentry_NXcalibration(calibration);
		}

		// validate unnamed child group of type NXfit (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXfit.class, false, true);
		final Map<String, NXfit> allFit = group.getChildren(NXfit.class);
		for (final NXfit fit : allFit.values()) {
			validateGroup_NXentry_NXfit(fit);
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
	 * Validate optional unnamed group of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_NXcoordinate_system(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXuser.
	 */
	private void validateGroup_NXentry_NXuser(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXuser.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'affiliation' of type NX_CHAR.
		final ILazyDataset affiliation = group.getLazyDataset("affiliation");
		validateFieldNotNull("affiliation", affiliation);
		if (affiliation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("affiliation", affiliation, NX_CHAR);
		}
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate optional child group 'energy_resolution' of type NXresolution
		if (group.getChild("energy_resolution", NXresolution.class) != null) {
			validateGroup_NXentry_NXinstrument_energy_resolution(group.getChild("energy_resolution", NXresolution.class));
		}

		// validate unnamed child group of type NXresolution (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXresolution.class, false, true);
		final Map<String, NXresolution> allResolution = group.getChildren(NXresolution.class);
		for (final NXresolution resolution : allResolution.values()) {
			validateGroup_NXentry_NXinstrument_NXresolution(resolution);
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getFabrication("device_information") != null) {
			validateGroup_NXentry_NXinstrument_device_information(group.getFabrication("device_information"));
		}

		// validate optional child group 'source_probe' of type NXsource
		if (group.getSource("source_probe") != null) {
			validateGroup_NXentry_NXinstrument_source_probe(group.getSource("source_probe"));
		}

		// validate optional child group 'source_pump' of type NXsource
		if (group.getSource("source_pump") != null) {
			validateGroup_NXentry_NXinstrument_source_pump(group.getSource("source_pump"));
		}

		// validate optional child group 'source_TYPE' of type NXsource
		if (group.getSource("source_TYPE") != null) {
			validateGroup_NXentry_NXinstrument_source_TYPE(group.getSource("source_TYPE"));
		}

		// validate child group 'beam_probe' of type NXbeam
		validateGroup_NXentry_NXinstrument_beam_probe(group.getBeam("beam_probe"));

		// validate optional child group 'beam_pump' of type NXbeam
		if (group.getBeam("beam_pump") != null) {
			validateGroup_NXentry_NXinstrument_beam_pump(group.getBeam("beam_pump"));
		}

		// validate optional child group 'beam_TYPE' of type NXbeam
		if (group.getBeam("beam_TYPE") != null) {
			validateGroup_NXentry_NXinstrument_beam_TYPE(group.getBeam("beam_TYPE"));
		}

		// validate unnamed child group of type NXelectronanalyzer (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXelectronanalyzer.class, false, true);
		final Map<String, NXelectronanalyzer> allElectronanalyzer = group.getChildren(NXelectronanalyzer.class);
		for (final NXelectronanalyzer electronanalyzer : allElectronanalyzer.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer(electronanalyzer);
		}

		// validate unnamed child group of type NXmanipulator (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmanipulator.class, false, true);
		final Map<String, NXmanipulator> allManipulator = group.getChildren(NXmanipulator.class);
		for (final NXmanipulator manipulator : allManipulator.values()) {
			validateGroup_NXentry_NXinstrument_NXmanipulator(manipulator);
		}

		// validate optional child group 'pressure_gauge' of type NXsensor
		if (group.getSensor("pressure_gauge") != null) {
			validateGroup_NXentry_NXinstrument_pressure_gauge(group.getSensor("pressure_gauge"));
		}

		// validate optional child group 'flood_gun' of type NXactuator
		if (group.getActuator("flood_gun") != null) {
			validateGroup_NXentry_NXinstrument_flood_gun(group.getActuator("flood_gun"));
		}

		// validate optional child group 'monochromator_TYPE' of type NXmonochromator
		if (group.getMonochromator("monochromator_TYPE") != null) {
			validateGroup_NXentry_NXinstrument_monochromator_TYPE(group.getMonochromator("monochromator_TYPE"));
		}

		// validate unnamed child group of type NXinsertion_device (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinsertion_device.class, false, true);
		final Map<String, NXinsertion_device> allInsertion_device = group.getAllInsertion_device();
		for (final NXinsertion_device insertion_device : allInsertion_device.values()) {
			validateGroup_NXentry_NXinstrument_NXinsertion_device(insertion_device);
		}

		// validate optional child group 'history' of type NXhistory
		if (group.getHistory() != null) {
			validateGroup_NXentry_NXinstrument_history(group.getHistory());
		}
	}

	/**
	 * Validate optional group 'energy_resolution' of type NXresolution.
	 */
	private void validateGroup_NXentry_NXinstrument_energy_resolution(final NXresolution group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("energy_resolution", NXresolution.class, group))) return;

		// validate field 'physical_quantity' of type NX_CHAR.
		final ILazyDataset physical_quantity = group.getLazyDataset("physical_quantity");
		validateFieldNotNull("physical_quantity", physical_quantity);
		if (physical_quantity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_quantity", physical_quantity, NX_CHAR);
			validateFieldEnumeration("physical_quantity", physical_quantity,
					"energy");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"estimated",
					"derived",
					"calibrated",
					"other");
		}

		// validate field 'resolution' of type NX_FLOAT.
		final ILazyDataset resolution = group.getLazyDataset("resolution");
		validateFieldNotNull("resolution", resolution);
		if (resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("resolution", resolution, NX_FLOAT);
			validateFieldUnits("resolution", group.getDataNode("resolution"), NX_ENERGY);
		}

		// validate optional field 'relative_resolution' of type NX_FLOAT.
		final ILazyDataset relative_resolution = group.getLazyDataset("relative_resolution");
				if (relative_resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("relative_resolution", relative_resolution, NX_FLOAT);
			validateFieldUnits("relative_resolution", group.getDataNode("relative_resolution"), NX_ANY);
		}
	}

	/**
	 * Validate optional unnamed group of type NXresolution.
	 */
	private void validateGroup_NXentry_NXinstrument_NXresolution(final NXresolution group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXresolution.class, group))) return;

	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

		// validate optional field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
				if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate optional field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
				if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'source_probe' of type NXsource.
	 */
	private void validateGroup_NXentry_NXinstrument_source_probe(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source_probe", NXsource.class, group))) return;

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"Synchrotron X-ray Source",
					"Rotating Anode X-ray",
					"Fixed Tube X-ray",
					"UV Laser",
					"Free-Electron Laser",
					"Optical Laser",
					"UV Plasma Source",
					"Metal Jet X-ray",
					"HHG laser",
					"UV lamp",
					"Monochromatized electron source");
		}

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'probe' of type NX_CHAR.
		final ILazyDataset probe = group.getLazyDataset("probe");
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

		// validate field 'associated_beam' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset associated_beam = group.getLazyDataset("associated_beam");
		validateFieldNotNull("associated_beam", associated_beam);
		if (associated_beam != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("associated_beam", associated_beam, NX_CHAR);
		}
		// validate optional child group 'device_information' of type NXfabrication
		if (group.getFabrication("device_information") != null) {
			validateGroup_NXentry_NXinstrument_source_probe_device_information(group.getFabrication("device_information"));
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_source_probe_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

		// validate optional field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
				if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate optional field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
				if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'source_pump' of type NXsource.
	 */
	private void validateGroup_NXentry_NXinstrument_source_pump(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source_pump", NXsource.class, group))) return;

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

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'probe' of type NX_CHAR.
		final ILazyDataset probe = group.getLazyDataset("probe");
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

		// validate field 'associated_beam' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset associated_beam = group.getLazyDataset("associated_beam");
		validateFieldNotNull("associated_beam", associated_beam);
		if (associated_beam != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("associated_beam", associated_beam, NX_CHAR);
		}
		// validate optional child group 'device_information' of type NXfabrication
		if (group.getFabrication("device_information") != null) {
			validateGroup_NXentry_NXinstrument_source_pump_device_information(group.getFabrication("device_information"));
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_source_pump_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

		// validate optional field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
				if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate optional field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
				if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'source_TYPE' of type NXsource.
	 */
	private void validateGroup_NXentry_NXinstrument_source_TYPE(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source_TYPE", NXsource.class, group))) return;

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

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'probe' of type NX_CHAR.
		final ILazyDataset probe = group.getLazyDataset("probe");
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

		// validate field 'associated_beam' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset associated_beam = group.getLazyDataset("associated_beam");
		validateFieldNotNull("associated_beam", associated_beam);
		if (associated_beam != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("associated_beam", associated_beam, NX_CHAR);
		}
		// validate optional child group 'device_information' of type NXfabrication
		if (group.getFabrication("device_information") != null) {
			validateGroup_NXentry_NXinstrument_source_TYPE_device_information(group.getFabrication("device_information"));
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_source_TYPE_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

		// validate optional field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
				if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate optional field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
				if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}
	}

	/**
	 * Validate group 'beam_probe' of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_beam_probe(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_probe", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
				if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		}

		// validate field 'incident_energy' of type NX_FLOAT.
		final ILazyDataset incident_energy = group.getLazyDataset("incident_energy");
		validateFieldNotNull("incident_energy", incident_energy);
		if (incident_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_energy", incident_energy, NX_FLOAT);
			validateFieldUnits("incident_energy", group.getDataNode("incident_energy"), NX_ENERGY);
			validateFieldRank("incident_energy", incident_energy, 1);
			validateFieldDimensions("incident_energy", incident_energy, "NXbeam", "m");
		}

		// validate optional field 'incident_energy_spread' of type NX_NUMBER.
		final ILazyDataset incident_energy_spread = group.getLazyDataset("incident_energy_spread");
				if (incident_energy_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_energy_spread", incident_energy_spread, NX_NUMBER);
			validateFieldUnits("incident_energy_spread", group.getDataNode("incident_energy_spread"), NX_ENERGY);
		}

		// validate optional field 'incident_polarization' of type NX_NUMBER.
		final ILazyDataset incident_polarization = group.getLazyDataset("incident_polarization");
				if (incident_polarization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_polarization", incident_polarization, NX_NUMBER);
			validateFieldUnits("incident_polarization", group.getDataNode("incident_polarization"), NX_ANY);
			validateFieldRank("incident_polarization", incident_polarization, 2);
			validateFieldDimensions("incident_polarization", incident_polarization, "NXbeam", "nP", 2);
		}

		// validate optional field 'extent' of type NX_FLOAT.
		final ILazyDataset extent = group.getLazyDataset("extent");
				if (extent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extent", extent, NX_FLOAT);
			validateFieldUnits("extent", group.getDataNode("extent"), NX_LENGTH);
			validateFieldRank("extent", extent, 2);
			validateFieldDimensions("extent", extent, "NXbeam", "nP", 2);
		}

		// validate optional field 'associated_source' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset associated_source = group.getLazyDataset("associated_source");
				if (associated_source != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("associated_source", associated_source, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'beam_pump' of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_beam_pump(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_pump", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
				if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		}

		// validate field 'incident_energy' of type NX_FLOAT.
		final ILazyDataset incident_energy = group.getLazyDataset("incident_energy");
		validateFieldNotNull("incident_energy", incident_energy);
		if (incident_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_energy", incident_energy, NX_FLOAT);
			validateFieldUnits("incident_energy", group.getDataNode("incident_energy"), NX_ENERGY);
			validateFieldRank("incident_energy", incident_energy, 1);
			validateFieldDimensions("incident_energy", incident_energy, "NXbeam", "m");
		}

		// validate optional field 'incident_energy_spread' of type NX_NUMBER.
		final ILazyDataset incident_energy_spread = group.getLazyDataset("incident_energy_spread");
				if (incident_energy_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_energy_spread", incident_energy_spread, NX_NUMBER);
			validateFieldUnits("incident_energy_spread", group.getDataNode("incident_energy_spread"), NX_ENERGY);
		}

		// validate optional field 'incident_polarization' of type NX_NUMBER.
		final ILazyDataset incident_polarization = group.getLazyDataset("incident_polarization");
				if (incident_polarization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_polarization", incident_polarization, NX_NUMBER);
			validateFieldUnits("incident_polarization", group.getDataNode("incident_polarization"), NX_ANY);
			validateFieldRank("incident_polarization", incident_polarization, 2);
			validateFieldDimensions("incident_polarization", incident_polarization, "NXbeam", "nP", 2);
		}

		// validate optional field 'extent' of type NX_FLOAT.
		final ILazyDataset extent = group.getLazyDataset("extent");
				if (extent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extent", extent, NX_FLOAT);
			validateFieldUnits("extent", group.getDataNode("extent"), NX_LENGTH);
			validateFieldRank("extent", extent, 2);
			validateFieldDimensions("extent", extent, "NXbeam", "nP", 2);
		}

		// validate optional field 'associated_source' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset associated_source = group.getLazyDataset("associated_source");
				if (associated_source != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("associated_source", associated_source, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'beam_TYPE' of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_beam_TYPE(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_TYPE", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
				if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		}

		// validate field 'incident_energy' of type NX_FLOAT.
		final ILazyDataset incident_energy = group.getLazyDataset("incident_energy");
		validateFieldNotNull("incident_energy", incident_energy);
		if (incident_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_energy", incident_energy, NX_FLOAT);
			validateFieldUnits("incident_energy", group.getDataNode("incident_energy"), NX_ENERGY);
			validateFieldRank("incident_energy", incident_energy, 1);
			validateFieldDimensions("incident_energy", incident_energy, "NXbeam", "m");
		}

		// validate optional field 'incident_energy_spread' of type NX_NUMBER.
		final ILazyDataset incident_energy_spread = group.getLazyDataset("incident_energy_spread");
				if (incident_energy_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_energy_spread", incident_energy_spread, NX_NUMBER);
			validateFieldUnits("incident_energy_spread", group.getDataNode("incident_energy_spread"), NX_ENERGY);
		}

		// validate optional field 'incident_polarization' of type NX_NUMBER.
		final ILazyDataset incident_polarization = group.getLazyDataset("incident_polarization");
				if (incident_polarization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_polarization", incident_polarization, NX_NUMBER);
			validateFieldUnits("incident_polarization", group.getDataNode("incident_polarization"), NX_ANY);
			validateFieldRank("incident_polarization", incident_polarization, 2);
			validateFieldDimensions("incident_polarization", incident_polarization, "NXbeam", "nP", 2);
		}

		// validate optional field 'extent' of type NX_FLOAT.
		final ILazyDataset extent = group.getLazyDataset("extent");
				if (extent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extent", extent, NX_FLOAT);
			validateFieldUnits("extent", group.getDataNode("extent"), NX_LENGTH);
			validateFieldRank("extent", extent, 2);
			validateFieldDimensions("extent", extent, "NXbeam", "nP", 2);
		}

		// validate optional field 'associated_source' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset associated_source = group.getLazyDataset("associated_source");
				if (associated_source != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("associated_source", associated_source, NX_CHAR);
		}
	}

	/**
	 * Validate unnamed group of type NXelectronanalyzer.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer(final NXelectronanalyzer group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXelectronanalyzer.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'work_function' of type NX_FLOAT.
		final ILazyDataset work_function = group.getLazyDataset("work_function");
				if (work_function != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("work_function", work_function, NX_FLOAT);
			validateFieldUnits("work_function", group.getDataNode("work_function"), NX_ENERGY);
		}

		// validate optional field 'fast_axes' of type NX_CHAR.
		final ILazyDataset fast_axes = group.getLazyDataset("fast_axes");
				if (fast_axes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fast_axes", fast_axes, NX_CHAR);
			validateFieldRank("fast_axes", fast_axes, 1);
			validateFieldDimensions("fast_axes", fast_axes, "NXelectronanalyzer", "nfa");
		}

		// validate optional field 'slow_axes' of type NX_CHAR.
		final ILazyDataset slow_axes = group.getLazyDataset("slow_axes");
				if (slow_axes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("slow_axes", slow_axes, NX_CHAR);
			validateFieldRank("slow_axes", slow_axes, 1);
			validateFieldDimensions("slow_axes", slow_axes, "NXelectronanalyzer", "nsa");
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getFabrication("device_information") != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_device_information(group.getFabrication("device_information"));
		}

		// validate optional child group 'energy_resolution' of type NXresolution
		if (group.getEnergy_resolution() != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_energy_resolution(group.getEnergy_resolution());
		}

		// validate optional child group 'transmission_function' of type NXdata
		if (group.getTransmission_function() != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_transmission_function(group.getTransmission_function());
		}

		// validate unnamed child group of type NXcollectioncolumn (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcollectioncolumn.class, false, true);
		final Map<String, NXcollectioncolumn> allCollectioncolumn = group.getAllCollectioncolumn();
		for (final NXcollectioncolumn collectioncolumn : allCollectioncolumn.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn(collectioncolumn);
		}

		// validate unnamed child group of type NXenergydispersion (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXenergydispersion.class, false, true);
		final Map<String, NXenergydispersion> allEnergydispersion = group.getAllEnergydispersion();
		for (final NXenergydispersion energydispersion : allEnergydispersion.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion(energydispersion);
		}

		// validate unnamed child group of type NXelectron_detector (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXelectron_detector.class, false, true);
		final Map<String, NXelectron_detector> allElectron_detector = group.getChildren(NXelectron_detector.class);
		for (final NXelectron_detector electron_detector : allElectron_detector.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector(electron_detector);
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

		// validate optional field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
				if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate optional field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
				if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'energy_resolution' of type NXresolution.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_energy_resolution(final NXresolution group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("energy_resolution", NXresolution.class, group))) return;

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"estimated",
					"derived",
					"calibrated",
					"other");
		}

		// validate field 'physical_quantity' of type NX_CHAR.
		final ILazyDataset physical_quantity = group.getLazyDataset("physical_quantity");
		validateFieldNotNull("physical_quantity", physical_quantity);
		if (physical_quantity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_quantity", physical_quantity, NX_CHAR);
			validateFieldEnumeration("physical_quantity", physical_quantity,
					"energy");
		}

		// validate field 'resolution' of type NX_FLOAT.
		final ILazyDataset resolution = group.getLazyDataset("resolution");
		validateFieldNotNull("resolution", resolution);
		if (resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("resolution", resolution, NX_FLOAT);
			validateFieldUnits("resolution", group.getDataNode("resolution"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'transmission_function' of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_transmission_function(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transmission_function", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXcollectioncolumn.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn(final NXcollectioncolumn group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcollectioncolumn.class, group))) return;

		// validate field 'scheme' of type NX_CHAR.
		final ILazyDataset scheme = group.getLazyDataset("scheme");
		validateFieldNotNull("scheme", scheme);
		if (scheme != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scheme", scheme, NX_CHAR);
			validateFieldEnumeration("scheme", scheme,
					"angular dispersive",
					"spatial dispersive",
					"momentum dispersive",
					"non-dispersive",
					"selective area",
					"deflector",
					"PEEM",
					"PEEM dark-field",
					"LEEM",
					"LEEM dark-field",
					"LEED",
					"dispersive plane",
					"momentum microscope");
		}

		// validate optional field 'lens_mode' of type NX_CHAR.
		final ILazyDataset lens_mode = group.getLazyDataset("lens_mode");
				if (lens_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("lens_mode", lens_mode, NX_CHAR);
		}

		// validate optional field 'projection' of type NX_CHAR.
		final ILazyDataset projection = group.getLazyDataset("projection");
				if (projection != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("projection", projection, NX_CHAR);
			validateFieldEnumeration("projection", projection,
					"real",
					"reciprocal",
					"energy");
		}

		// validate optional field 'angular_acceptance' of type NX_FLOAT.
		final ILazyDataset angular_acceptance = group.getLazyDataset("angular_acceptance");
				if (angular_acceptance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular_acceptance", angular_acceptance, NX_FLOAT);
			validateFieldUnits("angular_acceptance", group.getDataNode("angular_acceptance"), NX_ANGLE);
		}

		// validate optional field 'spatial_acceptance' of type NX_FLOAT.
		final ILazyDataset spatial_acceptance = group.getLazyDataset("spatial_acceptance");
				if (spatial_acceptance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("spatial_acceptance", spatial_acceptance, NX_FLOAT);
			validateFieldUnits("spatial_acceptance", group.getDataNode("spatial_acceptance"), NX_LENGTH);
		}

		// validate optional child group 'field_aperture' of type NXaperture
		if (group.getAperture("field_aperture") != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn_field_aperture(group.getAperture("field_aperture"));
		}

		// validate optional child group 'contrast_aperture' of type NXaperture
		if (group.getAperture("contrast_aperture") != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn_contrast_aperture(group.getAperture("contrast_aperture"));
		}

		// validate optional child group 'iris' of type NXaperture
		if (group.getAperture("iris") != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn_iris(group.getAperture("iris"));
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getChild("device_information", NXfabrication.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn_device_information(group.getChild("device_information", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'field_aperture' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn_field_aperture(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("field_aperture", NXaperture.class, group))) return;

	}

	/**
	 * Validate optional group 'contrast_aperture' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn_contrast_aperture(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("contrast_aperture", NXaperture.class, group))) return;

	}

	/**
	 * Validate optional group 'iris' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn_iris(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("iris", NXaperture.class, group))) return;

	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

		// validate optional field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
				if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate optional field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
				if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}
	}

	/**
	 * Validate unnamed group of type NXenergydispersion.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion(final NXenergydispersion group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXenergydispersion.class, group))) return;

		// validate field 'scheme' of type NX_CHAR.
		final ILazyDataset scheme = group.getLazyDataset("scheme");
		validateFieldNotNull("scheme", scheme);
		if (scheme != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scheme", scheme, NX_CHAR);
			validateFieldEnumeration("scheme", scheme,
					"tof",
					"hemispherical",
					"double hemispherical",
					"cylindrical mirror",
					"display mirror",
					"retarding grid");
		}

		// validate optional field 'pass_energy' of type NX_FLOAT.
		final ILazyDataset pass_energy = group.getLazyDataset("pass_energy");
				if (pass_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pass_energy", pass_energy, NX_FLOAT);
			validateFieldUnits("pass_energy", group.getDataNode("pass_energy"), NX_ENERGY);
		}

		// validate optional field 'drift_energy' of type NX_FLOAT.
		final ILazyDataset drift_energy = group.getLazyDataset("drift_energy");
				if (drift_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("drift_energy", drift_energy, NX_FLOAT);
			validateFieldUnits("drift_energy", group.getDataNode("drift_energy"), NX_ENERGY);
		}

		// validate optional field 'energy_scan_mode' of type NX_CHAR.
		final ILazyDataset energy_scan_mode = group.getLazyDataset("energy_scan_mode");
				if (energy_scan_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_scan_mode", energy_scan_mode, NX_CHAR);
			validateFieldEnumeration("energy_scan_mode", energy_scan_mode,
					"fixed_analyzer_transmission",
					"fixed_retardation_ratio",
					"fixed_energy",
					"snapshot",
					"dither");
		}

		// validate optional child group 'entrance_slit' of type NXaperture
		if (group.getAperture("entrance_slit") != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion_entrance_slit(group.getAperture("entrance_slit"));
		}

		// validate optional child group 'exit_slit' of type NXaperture
		if (group.getAperture("exit_slit") != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion_exit_slit(group.getAperture("exit_slit"));
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getFabrication("device_information") != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion_device_information(group.getFabrication("device_information"));
		}
	}

	/**
	 * Validate optional group 'entrance_slit' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion_entrance_slit(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("entrance_slit", NXaperture.class, group))) return;

	}

	/**
	 * Validate optional group 'exit_slit' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion_exit_slit(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("exit_slit", NXaperture.class, group))) return;

	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

		// validate optional field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
				if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate optional field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
				if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}
	}

	/**
	 * Validate unnamed group of type NXelectron_detector.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector(final NXelectron_detector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXelectron_detector.class, group))) return;

		// validate optional field 'amplifier_type' of type NX_CHAR.
		final ILazyDataset amplifier_type = group.getLazyDataset("amplifier_type");
				if (amplifier_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("amplifier_type", amplifier_type, NX_CHAR);
			validateFieldEnumeration("amplifier_type", amplifier_type,
					"MCP",
					"channeltron");
		}

		// validate optional field 'detector_type' of type NX_CHAR.
		final ILazyDataset detector_type = group.getLazyDataset("detector_type");
				if (detector_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("detector_type", detector_type, NX_CHAR);
			validateFieldEnumeration("detector_type", detector_type,
					"DLD",
					"Phosphor+CCD",
					"Phosphor+CMOS",
					"ECMOS",
					"Anode",
					"Multi-anode");
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getChild("device_information", NXfabrication.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector_device_information(group.getChild("device_information", NXfabrication.class));
		}

		// validate optional child group 'raw_data' of type NXdata
		if (group.getChild("raw_data", NXdata.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector_raw_data(group.getChild("raw_data", NXdata.class));
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

		// validate optional field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
				if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate optional field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
				if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'raw_data' of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector_raw_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("raw_data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"raw");

		// validate field 'raw' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset raw = group.getLazyDataset("raw");
		validateFieldNotNull("raw", raw);
		if (raw != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("raw", raw, NX_NUMBER);
		}

		// validate optional field 'pixel_x' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset pixel_x = group.getLazyDataset("pixel_x");
				if (pixel_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pixel_x", pixel_x, NX_POSINT);
		}

		// validate optional field 'pixel_y' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset pixel_y = group.getLazyDataset("pixel_y");
				if (pixel_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pixel_y", pixel_y, NX_POSINT);
		}

		// validate optional field 'energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset energy = group.getLazyDataset("energy");
				if (energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy", energy, NX_NUMBER);
			validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
		// validate attribute 'type' of field 'energy' of type NX_CHAR.
		final Attribute energy_attr_type = group.getDataNode("energy").getAttribute("type");
		if (!(validateAttributeNotNull("type", energy_attr_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("type", energy_attr_type, NX_CHAR);
		validateAttributeEnumeration("type", energy_attr_type,
				"kinetic",
				"binding");

		}

		// validate optional field 'photon_energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset photon_energy = group.getLazyDataset("photon_energy");
				if (photon_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("photon_energy", photon_energy, NX_NUMBER);
			validateFieldUnits("photon_energy", group.getDataNode("photon_energy"), NX_ENERGY);
		}

		// validate optional field 'kx' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset kx = group.getLazyDataset("kx");
				if (kx != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kx", kx, NX_NUMBER);
			validateFieldUnits("kx", group.getDataNode("kx"), NX_WAVENUMBER);
		}

		// validate optional field 'ky' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset ky = group.getLazyDataset("ky");
				if (ky != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ky", ky, NX_NUMBER);
			validateFieldUnits("ky", group.getDataNode("ky"), NX_WAVENUMBER);
		}

		// validate optional field 'kz' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset kz = group.getLazyDataset("kz");
				if (kz != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kz", kz, NX_NUMBER);
			validateFieldUnits("kz", group.getDataNode("kz"), NX_WAVENUMBER);
		}

		// validate optional field 'k_parallel' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset k_parallel = group.getLazyDataset("k_parallel");
				if (k_parallel != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("k_parallel", k_parallel, NX_NUMBER);
			validateFieldUnits("k_parallel", group.getDataNode("k_parallel"), NX_WAVENUMBER);
		}

		// validate optional field 'k_perpendicular' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset k_perpendicular = group.getLazyDataset("k_perpendicular");
				if (k_perpendicular != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("k_perpendicular", k_perpendicular, NX_NUMBER);
			validateFieldUnits("k_perpendicular", group.getDataNode("k_perpendicular"), NX_WAVENUMBER);
		}

		// validate optional field 'angular0' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angular0 = group.getLazyDataset("angular0");
				if (angular0 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular0", angular0, NX_NUMBER);
			validateFieldUnits("angular0", group.getDataNode("angular0"), NX_ANGLE);
		}

		// validate optional field 'angular1' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angular1 = group.getLazyDataset("angular1");
				if (angular1 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular1", angular1, NX_NUMBER);
			validateFieldUnits("angular1", group.getDataNode("angular1"), NX_ANGLE);
		}

		// validate optional field 'spatial0' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset spatial0 = group.getLazyDataset("spatial0");
				if (spatial0 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("spatial0", spatial0, NX_NUMBER);
			validateFieldUnits("spatial0", group.getDataNode("spatial0"), NX_LENGTH);
		}

		// validate optional field 'spatial1' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset spatial1 = group.getLazyDataset("spatial1");
				if (spatial1 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("spatial1", spatial1, NX_NUMBER);
			validateFieldUnits("spatial1", group.getDataNode("spatial1"), NX_LENGTH);
		}

		// validate optional field 'delay' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset delay = group.getLazyDataset("delay");
				if (delay != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("delay", delay, NX_NUMBER);
			validateFieldUnits("delay", group.getDataNode("delay"), NX_TIME);
		}

		// validate optional field 'temperature' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
				if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_NUMBER);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TIME);
		}
	}

	/**
	 * Validate optional unnamed group of type NXmanipulator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmanipulator.class, group))) return;

		// validate optional child group 'temperature_sensor' of type NXsensor
		if (group.getTemperature_sensor() != null) {
			validateGroup_NXentry_NXinstrument_NXmanipulator_temperature_sensor(group.getTemperature_sensor());
		}

		// validate optional child group 'sample_heater' of type NXactuator
		if (group.getSample_heater() != null) {
			validateGroup_NXentry_NXinstrument_NXmanipulator_sample_heater(group.getSample_heater());
		}

		// validate optional child group 'cryostat' of type NXactuator
		if (group.getCryostat() != null) {
			validateGroup_NXentry_NXinstrument_NXmanipulator_cryostat(group.getCryostat());
		}

		// validate optional child group 'drain_current_ammeter' of type NXsensor
		if (group.getDrain_current_ammeter() != null) {
			validateGroup_NXentry_NXinstrument_NXmanipulator_drain_current_ammeter(group.getDrain_current_ammeter());
		}

		// validate optional child group 'sample_bias_voltmeter' of type NXsensor
		if (group.getSample_bias_voltmeter() != null) {
			validateGroup_NXentry_NXinstrument_NXmanipulator_sample_bias_voltmeter(group.getSample_bias_voltmeter());
		}

		// validate optional child group 'sample_bias_potentiostat' of type NXactuator
		if (group.getSample_bias_potentiostat() != null) {
			validateGroup_NXentry_NXinstrument_NXmanipulator_sample_bias_potentiostat(group.getSample_bias_potentiostat());
		}

		// validate optional child group 'device_information' of type NXfabrication
		if (group.getChild("device_information", NXfabrication.class) != null) {
			validateGroup_NXentry_NXinstrument_NXmanipulator_device_information(group.getChild("device_information", NXfabrication.class));
		}
	}

	/**
	 * Validate optional group 'temperature_sensor' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator_temperature_sensor(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("temperature_sensor", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'measurement' of type NX_CHAR.
		final ILazyDataset measurement = group.getLazyDataset("measurement");
		validateFieldNotNull("measurement", measurement);
		if (measurement != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measurement", measurement, NX_CHAR);
			validateFieldEnumeration("measurement", measurement,
					"temperature");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_ANY);
			validateFieldDimensions("value", value, "NXsensor", "n");
		}
	}

	/**
	 * Validate optional group 'sample_heater' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator_sample_heater(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_heater", NXactuator.class, group))) return;

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'actuation_target' of type NX_CHAR.
		final ILazyDataset actuation_target = group.getLazyDataset("actuation_target");
		validateFieldNotNull("actuation_target", actuation_target);
		if (actuation_target != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("actuation_target", actuation_target, NX_CHAR);
			validateFieldEnumeration("actuation_target", actuation_target,
					"temperature");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate optional field 'output_heater_power' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset output_heater_power = group.getLazyDataset("output_heater_power");
				if (output_heater_power != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("output_heater_power", output_heater_power, NX_FLOAT);
		}

		// validate unnamed child group of type NXpid_controller (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXpid_controller.class, false, true);
		final Map<String, NXpid_controller> allPid_controller = group.getAllPid_controller();
		for (final NXpid_controller pid_controller : allPid_controller.values()) {
			validateGroup_NXentry_NXinstrument_NXmanipulator_sample_heater_NXpid_controller(pid_controller);
		}
	}

	/**
	 * Validate optional unnamed group of type NXpid_controller.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator_sample_heater_NXpid_controller(final NXpid_controller group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXpid_controller.class, group))) return;

		// validate optional field 'setpoint' of type NX_FLOAT.
		final ILazyDataset setpoint = group.getLazyDataset("setpoint");
				if (setpoint != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("setpoint", setpoint, NX_FLOAT);
			validateFieldUnits("setpoint", group.getDataNode("setpoint"), NX_TEMPERATURE);
		}
	}

	/**
	 * Validate optional group 'cryostat' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator_cryostat(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cryostat", NXactuator.class, group))) return;

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'actuation_target' of type NX_CHAR.
		final ILazyDataset actuation_target = group.getLazyDataset("actuation_target");
		validateFieldNotNull("actuation_target", actuation_target);
		if (actuation_target != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("actuation_target", actuation_target, NX_CHAR);
			validateFieldEnumeration("actuation_target", actuation_target,
					"temperature");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate unnamed child group of type NXpid_controller (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXpid_controller.class, false, true);
		final Map<String, NXpid_controller> allPid_controller = group.getAllPid_controller();
		for (final NXpid_controller pid_controller : allPid_controller.values()) {
			validateGroup_NXentry_NXinstrument_NXmanipulator_cryostat_NXpid_controller(pid_controller);
		}
	}

	/**
	 * Validate unnamed group of type NXpid_controller.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator_cryostat_NXpid_controller(final NXpid_controller group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXpid_controller.class, group))) return;

		// validate optional field 'setpoint' of type NX_FLOAT.
		final ILazyDataset setpoint = group.getLazyDataset("setpoint");
				if (setpoint != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("setpoint", setpoint, NX_FLOAT);
			validateFieldUnits("setpoint", group.getDataNode("setpoint"), NX_TEMPERATURE);
		}
	}

	/**
	 * Validate optional group 'drain_current_ammeter' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator_drain_current_ammeter(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("drain_current_ammeter", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'measurement' of type NX_CHAR.
		final ILazyDataset measurement = group.getLazyDataset("measurement");
		validateFieldNotNull("measurement", measurement);
		if (measurement != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measurement", measurement, NX_CHAR);
			validateFieldEnumeration("measurement", measurement,
					"current");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_ANY);
			validateFieldDimensions("value", value, "NXsensor", "n");
		}
	}

	/**
	 * Validate optional group 'sample_bias_voltmeter' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator_sample_bias_voltmeter(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_bias_voltmeter", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'measurement' of type NX_CHAR.
		final ILazyDataset measurement = group.getLazyDataset("measurement");
		validateFieldNotNull("measurement", measurement);
		if (measurement != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measurement", measurement, NX_CHAR);
			validateFieldEnumeration("measurement", measurement,
					"voltage");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_ANY);
			validateFieldDimensions("value", value, "NXsensor", "n");
		}
	}

	/**
	 * Validate optional group 'sample_bias_potentiostat' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator_sample_bias_potentiostat(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_bias_potentiostat", NXactuator.class, group))) return;

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'actuation_target' of type NX_CHAR.
		final ILazyDataset actuation_target = group.getLazyDataset("actuation_target");
		validateFieldNotNull("actuation_target", actuation_target);
		if (actuation_target != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("actuation_target", actuation_target, NX_CHAR);
			validateFieldEnumeration("actuation_target", actuation_target,
					"voltage");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate unnamed child group of type NXpid_controller (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXpid_controller.class, false, true);
		final Map<String, NXpid_controller> allPid_controller = group.getAllPid_controller();
		for (final NXpid_controller pid_controller : allPid_controller.values()) {
			validateGroup_NXentry_NXinstrument_NXmanipulator_sample_bias_potentiostat_NXpid_controller(pid_controller);
		}
	}

	/**
	 * Validate optional unnamed group of type NXpid_controller.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator_sample_bias_potentiostat_NXpid_controller(final NXpid_controller group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXpid_controller.class, group))) return;

		// validate optional field 'setpoint' of type NX_FLOAT.
		final ILazyDataset setpoint = group.getLazyDataset("setpoint");
				if (setpoint != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("setpoint", setpoint, NX_FLOAT);
			validateFieldUnits("setpoint", group.getDataNode("setpoint"), NX_VOLTAGE);
		}
	}

	/**
	 * Validate optional group 'device_information' of type NXfabrication.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator_device_information(final NXfabrication group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("device_information", NXfabrication.class, group))) return;

		// validate optional field 'vendor' of type NX_CHAR.
		final ILazyDataset vendor = group.getLazyDataset("vendor");
				if (vendor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("vendor", vendor, NX_CHAR);
		}

		// validate optional field 'model' of type NX_CHAR.
		final ILazyDataset model = group.getLazyDataset("model");
				if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'pressure_gauge' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXinstrument_pressure_gauge(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pressure_gauge", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'measurement' of type NX_CHAR.
		final ILazyDataset measurement = group.getLazyDataset("measurement");
		validateFieldNotNull("measurement", measurement);
		if (measurement != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measurement", measurement, NX_CHAR);
			validateFieldEnumeration("measurement", measurement,
					"pressure");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_PRESSURE);
			validateFieldDimensions("value", value, "NXsensor", "n");
		}

		// validate optional child group 'value_log' of type NXlog
		if (group.getValue_log() != null) {
			validateGroup_NXentry_NXinstrument_pressure_gauge_value_log(group.getValue_log());
		}
	}

	/**
	 * Validate optional group 'value_log' of type NXlog.
	 */
	private void validateGroup_NXentry_NXinstrument_pressure_gauge_value_log(final NXlog group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("value_log", NXlog.class, group))) return;

		// validate field 'value' of type NX_NUMBER.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_NUMBER);
			validateFieldUnits("value", group.getDataNode("value"), NX_PRESSURE);
		}
	}

	/**
	 * Validate optional group 'flood_gun' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXinstrument_flood_gun(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("flood_gun", NXactuator.class, group))) return;

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'actuation_target' of type NX_CHAR.
		final ILazyDataset actuation_target = group.getLazyDataset("actuation_target");
		validateFieldNotNull("actuation_target", actuation_target);
		if (actuation_target != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("actuation_target", actuation_target, NX_CHAR);
			validateFieldEnumeration("actuation_target", actuation_target,
					"current");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate optional field 'current' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset current = group.getLazyDataset("current");
				if (current != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("current", current, NX_FLOAT);
			validateFieldUnits("current", group.getDataNode("current"), NX_CURRENT);
		}

		// validate optional child group 'current_log' of type NXlog
		if (group.getChild("current_log", NXlog.class) != null) {
			validateGroup_NXentry_NXinstrument_flood_gun_current_log(group.getChild("current_log", NXlog.class));
		}
	}

	/**
	 * Validate optional group 'current_log' of type NXlog.
	 */
	private void validateGroup_NXentry_NXinstrument_flood_gun_current_log(final NXlog group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("current_log", NXlog.class, group))) return;

		// validate field 'value' of type NX_NUMBER.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_NUMBER);
			validateFieldUnits("value", group.getDataNode("value"), NX_CURRENT);
		}
	}

	/**
	 * Validate optional group 'monochromator_TYPE' of type NXmonochromator.
	 */
	private void validateGroup_NXentry_NXinstrument_monochromator_TYPE(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("monochromator_TYPE", NXmonochromator.class, group))) return;

		// validate optional field 'energy' of type NX_FLOAT.
		final ILazyDataset energy = group.getLazyDataset("energy");
				if (energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy", energy, NX_FLOAT);
			validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
		}

		// validate field 'associated_beam' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset associated_beam = group.getLazyDataset("associated_beam");
		validateFieldNotNull("associated_beam", associated_beam);
		if (associated_beam != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("associated_beam", associated_beam, NX_CHAR);
		}
	}

	/**
	 * Validate optional unnamed group of type NXinsertion_device.
	 */
	private void validateGroup_NXentry_NXinstrument_NXinsertion_device(final NXinsertion_device group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinsertion_device.class, group))) return;

	}

	/**
	 * Validate optional group 'history' of type NXhistory.
	 */
	private void validateGroup_NXentry_NXinstrument_history(final NXhistory group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("history", NXhistory.class, group))) return;

	}

	/**
	 * Validate optional group 'energy_axis_calibration' of type NXcalibration.
	 */
	private void validateGroup_NXentry_energy_axis_calibration(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("energy_axis_calibration", NXcalibration.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'physical_quantity' of type NX_CHAR.
		final ILazyDataset physical_quantity = group.getLazyDataset("physical_quantity");
		validateFieldNotNull("physical_quantity", physical_quantity);
		if (physical_quantity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_quantity", physical_quantity, NX_CHAR);
			validateFieldEnumeration("physical_quantity", physical_quantity,
					"energy");
		}

		// validate field 'calibrated_axis' of type NX_FLOAT.
		final ILazyDataset calibrated_axis = group.getLazyDataset("calibrated_axis");
		validateFieldNotNull("calibrated_axis", calibrated_axis);
		if (calibrated_axis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibrated_axis", calibrated_axis, NX_FLOAT);
			validateFieldUnits("calibrated_axis", group.getDataNode("calibrated_axis"), NX_ENERGY);
			validateFieldRank("calibrated_axis", calibrated_axis, 1);
			validateFieldDimensions("calibrated_axis", calibrated_axis, "NXcalibration", "ncal");
		}
	}

	/**
	 * Validate optional group 'AXIS_axis_calibration' of type NXcalibration.
	 */
	private void validateGroup_NXentry_AXIS_axis_calibration(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("AXIS_axis_calibration", NXcalibration.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'calibrated_axis' of type NX_FLOAT.
		final ILazyDataset calibrated_axis = group.getLazyDataset("calibrated_axis");
				if (calibrated_axis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibrated_axis", calibrated_axis, NX_FLOAT);
			validateFieldUnits("calibrated_axis", group.getDataNode("calibrated_axis"), NX_ANY);
			validateFieldRank("calibrated_axis", calibrated_axis, 1);
			validateFieldDimensions("calibrated_axis", calibrated_axis, "NXcalibration", "ncal");
		}
	}

	/**
	 * Validate optional group 'energy_referencing' of type NXcalibration.
	 */
	private void validateGroup_NXentry_energy_referencing(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("energy_referencing", NXcalibration.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'physical_quantity' of type NX_CHAR.
		final ILazyDataset physical_quantity = group.getLazyDataset("physical_quantity");
		validateFieldNotNull("physical_quantity", physical_quantity);
		if (physical_quantity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_quantity", physical_quantity, NX_CHAR);
			validateFieldEnumeration("physical_quantity", physical_quantity,
					"energy");
		}

		// validate optional field 'level' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset level = group.getLazyDataset("level");
				if (level != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("level", level, NX_CHAR);
		}

		// validate optional field 'reference_peak' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset reference_peak = group.getLazyDataset("reference_peak");
				if (reference_peak != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("reference_peak", reference_peak, NX_CHAR);
		}

		// validate optional field 'binding_energy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset binding_energy = group.getLazyDataset("binding_energy");
				if (binding_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("binding_energy", binding_energy, NX_FLOAT);
			validateFieldUnits("binding_energy", group.getDataNode("binding_energy"), NX_ENERGY);
		}

		// validate optional field 'offset' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset offset = group.getLazyDataset("offset");
				if (offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("offset", offset, NX_FLOAT);
			validateFieldUnits("offset", group.getDataNode("offset"), NX_ENERGY);
		}

		// validate optional field 'calibrated_axis' of type NX_FLOAT.
		final ILazyDataset calibrated_axis = group.getLazyDataset("calibrated_axis");
				if (calibrated_axis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibrated_axis", calibrated_axis, NX_FLOAT);
			validateFieldUnits("calibrated_axis", group.getDataNode("calibrated_axis"), NX_ENERGY);
			validateFieldRank("calibrated_axis", calibrated_axis, 1);
			validateFieldDimensions("calibrated_axis", calibrated_axis, "NXcalibration", "ncal");
		}
	}

	/**
	 * Validate optional group 'transmission_correction' of type NXcalibration.
	 */
	private void validateGroup_NXentry_transmission_correction(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transmission_correction", NXcalibration.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional child group 'transmission_function' of type NXdata
		if (group.getData("transmission_function") != null) {
			validateGroup_NXentry_transmission_correction_transmission_function(group.getData("transmission_function"));
		}
	}

	/**
	 * Validate optional group 'transmission_function' of type NXdata.
	 */
	private void validateGroup_NXentry_transmission_correction_transmission_function(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transmission_function", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"relative_intensity");

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);
		validateAttributeEnumeration("axes", axes_attr,
				"['kinetic_energy']");

		// validate field 'kinetic_energy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset kinetic_energy = group.getLazyDataset("kinetic_energy");
		validateFieldNotNull("kinetic_energy", kinetic_energy);
		if (kinetic_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kinetic_energy", kinetic_energy, NX_FLOAT);
			validateFieldUnits("kinetic_energy", group.getDataNode("kinetic_energy"), NX_ENERGY);
			validateFieldRank("kinetic_energy", kinetic_energy, 1);
			validateFieldDimensions("kinetic_energy", kinetic_energy, null, "n_transmission_function");
		}

		// validate field 'relative_intensity' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset relative_intensity = group.getLazyDataset("relative_intensity");
		validateFieldNotNull("relative_intensity", relative_intensity);
		if (relative_intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("relative_intensity", relative_intensity, NX_FLOAT);
			validateFieldUnits("relative_intensity", group.getDataNode("relative_intensity"), NX_UNITLESS);
			validateFieldRank("relative_intensity", relative_intensity, 1);
			validateFieldDimensions("relative_intensity", relative_intensity, null, "n_transmission_function");
		}
	}

	/**
	 * Validate optional unnamed group of type NXregistration.
	 */
	private void validateGroup_NXentry_NXregistration(final NXregistration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXregistration.class, group))) return;

	}

	/**
	 * Validate optional unnamed group of type NXdistortion.
	 */
	private void validateGroup_NXentry_NXdistortion(final NXdistortion group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdistortion.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXcalibration.
	 */
	private void validateGroup_NXentry_NXcalibration(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcalibration.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXfit.
	 */
	private void validateGroup_NXentry_NXfit(final NXfit group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXfit.class, group))) return;

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
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
				if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}

		// validate optional field 'chemical_formula' of type NX_CHAR.
		final ILazyDataset chemical_formula = group.getLazyDataset("chemical_formula");
				if (chemical_formula != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("chemical_formula", chemical_formula, NX_CHAR);
		}

		// validate optional field 'atom_types' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset atom_types = group.getLazyDataset("atom_types");
				if (atom_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("atom_types", atom_types, NX_CHAR);
		}

		// validate optional field 'physical_form' of type NX_CHAR.
		final ILazyDataset physical_form = group.getLazyDataset("physical_form");
				if (physical_form != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_form", physical_form, NX_CHAR);
		}

		// validate optional field 'situation' of type NX_CHAR.
		final ILazyDataset situation = group.getLazyDataset("situation");
				if (situation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("situation", situation, NX_CHAR);
			validateFieldEnumeration("situation", situation,
					"vacuum",
					"inert atmosphere",
					"oxidizing atmosphere",
					"reducing atmosphere");
		}

		// validate optional child group 'history' of type NXhistory
		if (group.getHistory() != null) {
			validateGroup_NXentry_NXsample_history(group.getHistory());
		}

		// validate optional child group 'temperature_env' of type NXenvironment
		if (group.getTemperature_env() != null) {
			validateGroup_NXentry_NXsample_temperature_env(group.getTemperature_env());
		}

		// validate optional child group 'gas_pressure_env' of type NXenvironment
		if (group.getEnvironment("gas_pressure_env") != null) {
			validateGroup_NXentry_NXsample_gas_pressure_env(group.getEnvironment("gas_pressure_env"));
		}

		// validate optional child group 'bias_env' of type NXenvironment
		if (group.getEnvironment("bias_env") != null) {
			validateGroup_NXentry_NXsample_bias_env(group.getEnvironment("bias_env"));
		}

		// validate optional child group 'drain_current_env' of type NXenvironment
		if (group.getEnvironment("drain_current_env") != null) {
			validateGroup_NXentry_NXsample_drain_current_env(group.getEnvironment("drain_current_env"));
		}

		// validate optional child group 'flood_gun_current_env' of type NXenvironment
		if (group.getEnvironment("flood_gun_current_env") != null) {
			validateGroup_NXentry_NXsample_flood_gun_current_env(group.getEnvironment("flood_gun_current_env"));
		}
	}

	/**
	 * Validate optional group 'history' of type NXhistory.
	 */
	private void validateGroup_NXentry_NXsample_history(final NXhistory group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("history", NXhistory.class, group))) return;

		// validate optional child group 'sample_preparation' of type NXactivity
		if (group.getActivity("sample_preparation") != null) {
			validateGroup_NXentry_NXsample_history_sample_preparation(group.getActivity("sample_preparation"));
		}
	}

	/**
	 * Validate optional group 'sample_preparation' of type NXactivity.
	 */
	private void validateGroup_NXentry_NXsample_history_sample_preparation(final NXactivity group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_preparation", NXactivity.class, group))) return;

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		validateFieldNotNull("start_time", start_time);
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

		// validate optional field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
				if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'temperature_env' of type NXenvironment.
	 */
	private void validateGroup_NXentry_NXsample_temperature_env(final NXenvironment group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("temperature_env", NXenvironment.class, group))) return;

		// validate optional field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
				if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_TEMPERATURE);
		}
		// validate optional child group 'temperature_sensor' of type NXsensor
		if (group.getSensor("temperature_sensor") != null) {
			validateGroup_NXentry_NXsample_temperature_env_temperature_sensor(group.getSensor("temperature_sensor"));
		}

		// validate optional child group 'sample_heater' of type NXactuator
		if (group.getActuator("sample_heater") != null) {
			validateGroup_NXentry_NXsample_temperature_env_sample_heater(group.getActuator("sample_heater"));
		}

		// validate optional child group 'cryostat' of type NXactuator
		if (group.getActuator("cryostat") != null) {
			validateGroup_NXentry_NXsample_temperature_env_cryostat(group.getActuator("cryostat"));
		}
	}

	/**
	 * Validate optional group 'temperature_sensor' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXsample_temperature_env_temperature_sensor(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("temperature_sensor", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'sample_heater' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXsample_temperature_env_sample_heater(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_heater", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional group 'cryostat' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXsample_temperature_env_cryostat(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cryostat", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional group 'gas_pressure_env' of type NXenvironment.
	 */
	private void validateGroup_NXentry_NXsample_gas_pressure_env(final NXenvironment group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("gas_pressure_env", NXenvironment.class, group))) return;

		// validate optional field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
				if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_PRESSURE);
		}
		// validate optional child group 'pressure_gauge' of type NXsensor
		if (group.getSensor("pressure_gauge") != null) {
			validateGroup_NXentry_NXsample_gas_pressure_env_pressure_gauge(group.getSensor("pressure_gauge"));
		}
	}

	/**
	 * Validate optional group 'pressure_gauge' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXsample_gas_pressure_env_pressure_gauge(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("pressure_gauge", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'bias_env' of type NXenvironment.
	 */
	private void validateGroup_NXentry_NXsample_bias_env(final NXenvironment group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("bias_env", NXenvironment.class, group))) return;

		// validate optional field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
				if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_VOLTAGE);
		}
		// validate optional child group 'voltmeter' of type NXsensor
		if (group.getSensor("voltmeter") != null) {
			validateGroup_NXentry_NXsample_bias_env_voltmeter(group.getSensor("voltmeter"));
		}

		// validate optional child group 'potentiostat' of type NXactuator
		if (group.getActuator("potentiostat") != null) {
			validateGroup_NXentry_NXsample_bias_env_potentiostat(group.getActuator("potentiostat"));
		}
	}

	/**
	 * Validate optional group 'voltmeter' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXsample_bias_env_voltmeter(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("voltmeter", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'potentiostat' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXsample_bias_env_potentiostat(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("potentiostat", NXactuator.class, group))) return;

	}

	/**
	 * Validate optional group 'drain_current_env' of type NXenvironment.
	 */
	private void validateGroup_NXentry_NXsample_drain_current_env(final NXenvironment group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("drain_current_env", NXenvironment.class, group))) return;

		// validate optional field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
				if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_CURRENT);
		}
		// validate optional child group 'ammeter' of type NXsensor
		if (group.getSensor("ammeter") != null) {
			validateGroup_NXentry_NXsample_drain_current_env_ammeter(group.getSensor("ammeter"));
		}
	}

	/**
	 * Validate optional group 'ammeter' of type NXsensor.
	 */
	private void validateGroup_NXentry_NXsample_drain_current_env_ammeter(final NXsensor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ammeter", NXsensor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'flood_gun_current_env' of type NXenvironment.
	 */
	private void validateGroup_NXentry_NXsample_flood_gun_current_env(final NXenvironment group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("flood_gun_current_env", NXenvironment.class, group))) return;

		// validate optional field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
				if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_CURRENT);
		}
		// validate optional child group 'flood_gun' of type NXactuator
		if (group.getActuator("flood_gun") != null) {
			validateGroup_NXentry_NXsample_flood_gun_current_env_flood_gun(group.getActuator("flood_gun"));
		}
	}

	/**
	 * Validate optional group 'flood_gun' of type NXactuator.
	 */
	private void validateGroup_NXentry_NXsample_flood_gun_current_env_flood_gun(final NXactuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("flood_gun", NXactuator.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"data");

		// validate field 'data' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
		}

		// validate optional field 'energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset energy = group.getLazyDataset("energy");
				if (energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy", energy, NX_NUMBER);
			validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
		// validate attribute 'type' of field 'energy' of type NX_CHAR.
		final Attribute energy_attr_type = group.getDataNode("energy").getAttribute("type");
		if (!(validateAttributeNotNull("type", energy_attr_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("type", energy_attr_type, NX_CHAR);
		validateAttributeEnumeration("type", energy_attr_type,
				"kinetic",
				"binding");

		}

		// validate attribute 'energy_indices' of type NX_INT.
		final Attribute energy_indices_attr = group.getAttribute("energy_indices");
		if (!(validateAttributeNotNull("energy_indices", energy_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("energy_indices", energy_indices_attr, NX_INT);

		// validate optional field 'photon_energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset photon_energy = group.getLazyDataset("photon_energy");
				if (photon_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("photon_energy", photon_energy, NX_NUMBER);
			validateFieldUnits("photon_energy", group.getDataNode("photon_energy"), NX_ENERGY);
		}

		// validate optional field 'kx' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset kx = group.getLazyDataset("kx");
				if (kx != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kx", kx, NX_NUMBER);
			validateFieldUnits("kx", group.getDataNode("kx"), NX_WAVENUMBER);
		}

		// validate optional field 'ky' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset ky = group.getLazyDataset("ky");
				if (ky != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ky", ky, NX_NUMBER);
			validateFieldUnits("ky", group.getDataNode("ky"), NX_WAVENUMBER);
		}

		// validate optional field 'kz' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset kz = group.getLazyDataset("kz");
				if (kz != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kz", kz, NX_NUMBER);
			validateFieldUnits("kz", group.getDataNode("kz"), NX_WAVENUMBER);
		}

		// validate optional field 'k_parallel' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset k_parallel = group.getLazyDataset("k_parallel");
				if (k_parallel != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("k_parallel", k_parallel, NX_NUMBER);
			validateFieldUnits("k_parallel", group.getDataNode("k_parallel"), NX_WAVENUMBER);
		}

		// validate optional field 'k_perpendicular' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset k_perpendicular = group.getLazyDataset("k_perpendicular");
				if (k_perpendicular != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("k_perpendicular", k_perpendicular, NX_NUMBER);
			validateFieldUnits("k_perpendicular", group.getDataNode("k_perpendicular"), NX_WAVENUMBER);
		}

		// validate optional field 'angular0' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angular0 = group.getLazyDataset("angular0");
				if (angular0 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular0", angular0, NX_NUMBER);
			validateFieldUnits("angular0", group.getDataNode("angular0"), NX_ANGLE);
		}

		// validate optional field 'angular1' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angular1 = group.getLazyDataset("angular1");
				if (angular1 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular1", angular1, NX_NUMBER);
			validateFieldUnits("angular1", group.getDataNode("angular1"), NX_ANGLE);
		}

		// validate optional field 'spatial0' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset spatial0 = group.getLazyDataset("spatial0");
				if (spatial0 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("spatial0", spatial0, NX_NUMBER);
			validateFieldUnits("spatial0", group.getDataNode("spatial0"), NX_LENGTH);
		}

		// validate optional field 'spatial1' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset spatial1 = group.getLazyDataset("spatial1");
				if (spatial1 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("spatial1", spatial1, NX_NUMBER);
			validateFieldUnits("spatial1", group.getDataNode("spatial1"), NX_LENGTH);
		}

		// validate optional field 'delay' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset delay = group.getLazyDataset("delay");
				if (delay != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("delay", delay, NX_NUMBER);
			validateFieldUnits("delay", group.getDataNode("delay"), NX_TIME);
		}

		// validate optional field 'temperature' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
				if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_NUMBER);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TIME);
		}
	}
}
