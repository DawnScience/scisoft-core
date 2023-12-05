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
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXelectronanalyser;
import org.eclipse.dawnsci.nexus.NXcollectioncolumn;
import org.eclipse.dawnsci.nexus.NXaperture;
import org.eclipse.dawnsci.nexus.NXenergydispersion;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXmanipulator;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXcalibration;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXnote;

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
					"NXmpes");
		// validate attribute 'version' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (!(validateAttributeNotNull("version", definition_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", definition_attr_version, NX_CHAR);

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

		// validate unnamed child group of type NXprocess (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXprocess.class, false, true);
		final Map<String, NXprocess> allProcess = group.getAllProcess();
		for (final NXprocess process : allProcess.values()) {
			validateGroup_NXentry_NXprocess(process);
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
	 * Validate unnamed group of type NXuser.
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

		// validate optional field 'affiliation' of type NX_CHAR.
		final ILazyDataset affiliation = group.getLazyDataset("affiliation");
				if (affiliation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("affiliation", affiliation, NX_CHAR);
		}

		// validate optional field 'address' of type NX_CHAR.
		final ILazyDataset address = group.getLazyDataset("address");
				if (address != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("address", address, NX_CHAR);
		}

		// validate field 'email' of type NX_CHAR.
		final ILazyDataset email = group.getLazyDataset("email");
		validateFieldNotNull("email", email);
		if (email != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("email", email, NX_CHAR);
		}

		// validate optional field 'orcid' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset orcid = group.getLazyDataset("orcid");
				if (orcid != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("orcid", orcid, NX_CHAR);
		}
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate field 'energy_resolution' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset energy_resolution = group.getLazyDataset("energy_resolution");
		validateFieldNotNull("energy_resolution", energy_resolution);
		if (energy_resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_resolution", energy_resolution, NX_FLOAT);
			validateFieldUnits("energy_resolution", group.getDataNode("energy_resolution"), NX_ENERGY);
		}

		// validate unnamed child group of type NXsource (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_NXentry_NXinstrument_NXsource(source);
		}

		// validate unnamed child group of type NXbeam (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXbeam.class, false, true);
		final Map<String, NXbeam> allBeam = group.getAllBeam();
		for (final NXbeam beam : allBeam.values()) {
			validateGroup_NXentry_NXinstrument_NXbeam(beam);
		}

		// validate unnamed child group of type NXelectronanalyser (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXelectronanalyser.class, false, true);
		final Map<String, NXelectronanalyser> allElectronanalyser = group.getChildren(NXelectronanalyser.class);
		for (final NXelectronanalyser electronanalyser : allElectronanalyser.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser(electronanalyser);
		}

		// validate unnamed child group of type NXmanipulator (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmanipulator.class, false, true);
		final Map<String, NXmanipulator> allManipulator = group.getChildren(NXmanipulator.class);
		for (final NXmanipulator manipulator : allManipulator.values()) {
			validateGroup_NXentry_NXinstrument_NXmanipulator(manipulator);
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
					"Synchrotron X-ray Source",
					"Rotating Anode X-ray",
					"Fixed Tube X-ray",
					"UV Laser",
					"Free-Electron Laser",
					"Optical Laser",
					"UV Plasma Source",
					"Metal Jet X-ray",
					"HHG laser");
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
					"x-ray",
					"ultraviolet",
					"visible light");
		}
	}

	/**
	 * Validate unnamed group of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_NXbeam(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'distance' of type NX_NUMBER.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_NUMBER);
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

		// validate optional field 'incident_energy_spread' of type NX_NUMBER. Note: field not defined in base class.
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
	}

	/**
	 * Validate unnamed group of type NXelectronanalyser.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser(final NXelectronanalyser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXelectronanalyser.class, group))) return;

		// validate field 'description' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'energy_resolution' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset energy_resolution = group.getLazyDataset("energy_resolution");
				if (energy_resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_resolution", energy_resolution, NX_FLOAT);
			validateFieldUnits("energy_resolution", group.getDataNode("energy_resolution"), NX_ENERGY);
		}

		// validate optional field 'fast_axes' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset fast_axes = group.getLazyDataset("fast_axes");
				if (fast_axes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fast_axes", fast_axes, NX_CHAR);
		}

		// validate optional field 'slow_axes' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset slow_axes = group.getLazyDataset("slow_axes");
				if (slow_axes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("slow_axes", slow_axes, NX_CHAR);
		}

		// validate unnamed child group of type NXcollectioncolumn (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcollectioncolumn.class, false, true);
		final Map<String, NXcollectioncolumn> allCollectioncolumn = group.getChildren(NXcollectioncolumn.class);
		for (final NXcollectioncolumn collectioncolumn : allCollectioncolumn.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn(collectioncolumn);
		}

		// validate unnamed child group of type NXenergydispersion (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXenergydispersion.class, false, true);
		final Map<String, NXenergydispersion> allEnergydispersion = group.getChildren(NXenergydispersion.class);
		for (final NXenergydispersion energydispersion : allEnergydispersion.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion(energydispersion);
		}

		// validate unnamed child group of type NXdetector (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector.class, false, true);
		final Map<String, NXdetector> allDetector = group.getChildren(NXdetector.class);
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXdetector(detector);
		}
	}

	/**
	 * Validate unnamed group of type NXcollectioncolumn.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn(final NXcollectioncolumn group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcollectioncolumn.class, group))) return;

		// validate field 'scheme' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset scheme = group.getLazyDataset("scheme");
		validateFieldNotNull("scheme", scheme);
		if (scheme != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scheme", scheme, NX_CHAR);
			validateFieldEnumeration("scheme", scheme,
					"Standard",
					"Angular dispersive",
					"Selective area",
					"Deflector",
					"PEEM",
					"Momentum Microscope");
		}

		// validate optional field 'mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset mode = group.getLazyDataset("mode");
				if (mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mode", mode, NX_CHAR);
		}

		// validate optional field 'projection' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset projection = group.getLazyDataset("projection");
				if (projection != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("projection", projection, NX_CHAR);
		}

		// validate optional child group 'field_aperture' of type NXaperture
		if (group.getChild("field_aperture", NXaperture.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn_field_aperture(group.getChild("field_aperture", NXaperture.class));
		}

		// validate optional child group 'contrast_aperture' of type NXaperture
		if (group.getChild("contrast_aperture", NXaperture.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn_contrast_aperture(group.getChild("contrast_aperture", NXaperture.class));
		}
	}

	/**
	 * Validate optional group 'field_aperture' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn_field_aperture(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("field_aperture", NXaperture.class, group))) return;

	}

	/**
	 * Validate optional group 'contrast_aperture' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn_contrast_aperture(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("contrast_aperture", NXaperture.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXenergydispersion.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion(final NXenergydispersion group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXenergydispersion.class, group))) return;

		// validate field 'scheme' of type NX_CHAR. Note: field not defined in base class.
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

		// validate field 'pass_energy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset pass_energy = group.getLazyDataset("pass_energy");
		validateFieldNotNull("pass_energy", pass_energy);
		if (pass_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pass_energy", pass_energy, NX_FLOAT);
			validateFieldUnits("pass_energy", group.getDataNode("pass_energy"), NX_ENERGY);
		}

		// validate field 'energy_scan_mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset energy_scan_mode = group.getLazyDataset("energy_scan_mode");
		validateFieldNotNull("energy_scan_mode", energy_scan_mode);
		if (energy_scan_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_scan_mode", energy_scan_mode, NX_CHAR);
		}

		// validate optional child group 'entrance_slit' of type NXaperture
		if (group.getChild("entrance_slit", NXaperture.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion_entrance_slit(group.getChild("entrance_slit", NXaperture.class));
		}

		// validate optional child group 'exit_slit' of type NXaperture
		if (group.getChild("exit_slit", NXaperture.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion_exit_slit(group.getChild("exit_slit", NXaperture.class));
		}
	}

	/**
	 * Validate optional group 'entrance_slit' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion_entrance_slit(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("entrance_slit", NXaperture.class, group))) return;

	}

	/**
	 * Validate optional group 'exit_slit' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion_exit_slit(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("exit_slit", NXaperture.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'amplifier_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset amplifier_type = group.getLazyDataset("amplifier_type");
				if (amplifier_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("amplifier_type", amplifier_type, NX_CHAR);
			validateFieldEnumeration("amplifier_type", amplifier_type,
					"MCP",
					"channeltron");
		}

		// validate optional field 'detector_type' of type NX_CHAR. Note: field not defined in base class.
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

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getChildren(NXdata.class);
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXdetector_NXdata(data);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXdetector_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
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
	}

	/**
	 * Validate optional unnamed group of type NXmanipulator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmanipulator(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmanipulator.class, group))) return;

		// validate optional field 'sample_temperature' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset sample_temperature = group.getLazyDataset("sample_temperature");
				if (sample_temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_temperature", sample_temperature, NX_FLOAT);
			validateFieldUnits("sample_temperature", group.getDataNode("sample_temperature"), NX_TEMPERATURE);
		}

		// validate optional field 'drain_current' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset drain_current = group.getLazyDataset("drain_current");
				if (drain_current != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("drain_current", drain_current, NX_FLOAT);
			validateFieldUnits("drain_current", group.getDataNode("drain_current"), NX_CURRENT);
		}

		// validate optional field 'sample_bias' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset sample_bias = group.getLazyDataset("sample_bias");
				if (sample_bias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_bias", sample_bias, NX_FLOAT);
			validateFieldUnits("sample_bias", group.getDataNode("sample_bias"), NX_CURRENT);
		}
	}

	/**
	 * Validate unnamed group of type NXprocess.
	 */
	private void validateGroup_NXentry_NXprocess(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprocess.class, group))) return;

		// validate optional child group 'energy_calibration' of type NXcalibration
		if (group.getChild("energy_calibration", NXcalibration.class) != null) {
			validateGroup_NXentry_NXprocess_energy_calibration(group.getChild("energy_calibration", NXcalibration.class));
		}

		// validate optional child group 'angular_calibration' of type NXcalibration
		if (group.getChild("angular_calibration", NXcalibration.class) != null) {
			validateGroup_NXentry_NXprocess_angular_calibration(group.getChild("angular_calibration", NXcalibration.class));
		}

		// validate optional child group 'spatial_calibration' of type NXcalibration
		if (group.getChild("spatial_calibration", NXcalibration.class) != null) {
			validateGroup_NXentry_NXprocess_spatial_calibration(group.getChild("spatial_calibration", NXcalibration.class));
		}

		// validate optional child group 'momentum_calibration' of type NXcalibration
		if (group.getChild("momentum_calibration", NXcalibration.class) != null) {
			validateGroup_NXentry_NXprocess_momentum_calibration(group.getChild("momentum_calibration", NXcalibration.class));
		}
	}

	/**
	 * Validate optional group 'energy_calibration' of type NXcalibration.
	 */
	private void validateGroup_NXentry_NXprocess_energy_calibration(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("energy_calibration", NXcalibration.class, group))) return;

		// validate field 'applied' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset applied = group.getLazyDataset("applied");
		validateFieldNotNull("applied", applied);
		if (applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("applied", applied, NX_BOOLEAN);
		}

		// validate optional field 'calibrated_axis' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset calibrated_axis = group.getLazyDataset("calibrated_axis");
				if (calibrated_axis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibrated_axis", calibrated_axis, NX_FLOAT);
		}
	}

	/**
	 * Validate optional group 'angular_calibration' of type NXcalibration.
	 */
	private void validateGroup_NXentry_NXprocess_angular_calibration(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("angular_calibration", NXcalibration.class, group))) return;

		// validate field 'applied' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset applied = group.getLazyDataset("applied");
		validateFieldNotNull("applied", applied);
		if (applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("applied", applied, NX_BOOLEAN);
		}

		// validate optional field 'calibrated_axis' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset calibrated_axis = group.getLazyDataset("calibrated_axis");
				if (calibrated_axis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibrated_axis", calibrated_axis, NX_FLOAT);
		}
	}

	/**
	 * Validate optional group 'spatial_calibration' of type NXcalibration.
	 */
	private void validateGroup_NXentry_NXprocess_spatial_calibration(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("spatial_calibration", NXcalibration.class, group))) return;

		// validate field 'applied' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset applied = group.getLazyDataset("applied");
		validateFieldNotNull("applied", applied);
		if (applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("applied", applied, NX_BOOLEAN);
		}

		// validate optional field 'calibrated_axis' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset calibrated_axis = group.getLazyDataset("calibrated_axis");
				if (calibrated_axis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibrated_axis", calibrated_axis, NX_FLOAT);
		}
	}

	/**
	 * Validate optional group 'momentum_calibration' of type NXcalibration.
	 */
	private void validateGroup_NXentry_NXprocess_momentum_calibration(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("momentum_calibration", NXcalibration.class, group))) return;

		// validate field 'applied' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset applied = group.getLazyDataset("applied");
		validateFieldNotNull("applied", applied);
		if (applied != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("applied", applied, NX_BOOLEAN);
		}

		// validate optional field 'calibrated_axis' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset calibrated_axis = group.getLazyDataset("calibrated_axis");
				if (calibrated_axis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibrated_axis", calibrated_axis, NX_FLOAT);
		}
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

		// validate optional field 'preparation_date' of type NX_DATE_TIME.
		final ILazyDataset preparation_date = group.getLazyDataset("preparation_date");
				if (preparation_date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("preparation_date", preparation_date, NX_DATE_TIME);
		}

		// validate field 'temperature' of type NX_FLOAT.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
		validateFieldNotNull("temperature", temperature);
		if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_FLOAT);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");
		}

		// validate field 'situation' of type NX_CHAR.
		final ILazyDataset situation = group.getLazyDataset("situation");
		validateFieldNotNull("situation", situation);
		if (situation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("situation", situation, NX_CHAR);
			validateFieldEnumeration("situation", situation,
					"vacuum",
					"inert atmosphere",
					"oxidising atmosphere",
					"reducing atmosphere");
		}

		// validate field 'gas_pressure' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset gas_pressure = group.getLazyDataset("gas_pressure");
		validateFieldNotNull("gas_pressure", gas_pressure);
		if (gas_pressure != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("gas_pressure", gas_pressure, NX_FLOAT);
			validateFieldUnits("gas_pressure", group.getDataNode("gas_pressure"), NX_PRESSURE);
		}
		// validate optional child group 'sample_history' of type NXnote
		if (group.getChild("sample_history", NXnote.class) != null) {
			validateGroup_NXentry_NXsample_sample_history(group.getChild("sample_history", NXnote.class));
		}

		// validate child group 'preparation_description' of type NXnote
		validateGroup_NXentry_NXsample_preparation_description(group.getChild("preparation_description", NXnote.class));
	}

	/**
	 * Validate optional group 'sample_history' of type NXnote.
	 */
	private void validateGroup_NXentry_NXsample_sample_history(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample_history", NXnote.class, group))) return;

	}

	/**
	 * Validate group 'preparation_description' of type NXnote.
	 */
	private void validateGroup_NXentry_NXsample_preparation_description(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("preparation_description", NXnote.class, group))) return;

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
	}
}
