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
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXinsertion_device;
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXtransformations;
import org.eclipse.dawnsci.nexus.NXoptical_system_em;
import org.eclipse.dawnsci.nexus.NXelectronanalyser;
import org.eclipse.dawnsci.nexus.NXcollectioncolumn;
import org.eclipse.dawnsci.nexus.NXlens_em;
import org.eclipse.dawnsci.nexus.NXebeam_column;
import org.eclipse.dawnsci.nexus.NXaperture_em;
import org.eclipse.dawnsci.nexus.NXenergydispersion;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXdetector_module;
import org.eclipse.dawnsci.nexus.NXmanipulator;
import org.eclipse.dawnsci.nexus.NXpositioner;
import org.eclipse.dawnsci.nexus.NXcoordinate_system_set;
import org.eclipse.dawnsci.nexus.NXsample;

/**
 * Validator for the application definition 'NXmpes_peem'.
 */
public class NXmpes_peemValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXmpes_peemValidator() {
		super(NexusApplicationDefinition.NX_MPES_PEEM);
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
					"NXmpes_peem");
		// validate attribute 'version' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (!(validateAttributeNotNull("version", definition_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", definition_attr_version, NX_CHAR);

		}

		// validate unnamed child group of type NXmonitor (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmonitor.class, false, true);
		final Map<String, NXmonitor> allMonitor = group.getAllMonitor();
		for (final NXmonitor monitor : allMonitor.values()) {
			validateGroup_NXentry_NXmonitor(monitor);
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate child group 'coordinate_system_set' of type NXcoordinate_system_set
		validateGroup_NXentry_coordinate_system_set(group.getChild("coordinate_system_set", NXcoordinate_system_set.class));

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}
	}

	/**
	 * Validate optional unnamed group of type NXmonitor.
	 */
	private void validateGroup_NXentry_NXmonitor(final NXmonitor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmonitor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate unnamed child group of type NXinsertion_device (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinsertion_device.class, false, true);
		final Map<String, NXinsertion_device> allInsertion_device = group.getAllInsertion_device();
		for (final NXinsertion_device insertion_device : allInsertion_device.values()) {
			validateGroup_NXentry_NXinstrument_NXinsertion_device(insertion_device);
		}

		// validate unnamed child group of type NXmonochromator (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmonochromator.class, false, true);
		final Map<String, NXmonochromator> allMonochromator = group.getAllMonochromator();
		for (final NXmonochromator monochromator : allMonochromator.values()) {
			validateGroup_NXentry_NXinstrument_NXmonochromator(monochromator);
		}

		// validate optional child group 'beam_probe' of type NXbeam
		if (group.getBeam("beam_probe") != null) {
			validateGroup_NXentry_NXinstrument_beam_probe(group.getBeam("beam_probe"));
		}

		// validate unnamed child group of type NXoptical_system_em (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXoptical_system_em.class, false, true);
		final Map<String, NXoptical_system_em> allOptical_system_em = group.getChildren(NXoptical_system_em.class);
		for (final NXoptical_system_em optical_system_em : allOptical_system_em.values()) {
			validateGroup_NXentry_NXinstrument_NXoptical_system_em(optical_system_em);
		}

		// validate unnamed child group of type NXelectronanalyser (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXelectronanalyser.class, false, true);
		final Map<String, NXelectronanalyser> allElectronanalyser = group.getChildren(NXelectronanalyser.class);
		for (final NXelectronanalyser electronanalyser : allElectronanalyser.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser(electronanalyser);
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
	 * Validate optional unnamed group of type NXmonochromator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXmonochromator(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmonochromator.class, group))) return;

	}

	/**
	 * Validate optional group 'beam_probe' of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_beam_probe(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_probe", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'flux' of type NX_CHAR.
		final Attribute flux_attr = group.getAttribute("flux");
		if (!(validateAttributeNotNull("flux", flux_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("flux", flux_attr, NX_CHAR);

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

		// validate field 'incident_polarization_stokes' of type NX_NUMBER.
		final ILazyDataset incident_polarization_stokes = group.getLazyDataset("incident_polarization_stokes");
		validateFieldNotNull("incident_polarization_stokes", incident_polarization_stokes);
		if (incident_polarization_stokes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_polarization_stokes", incident_polarization_stokes, NX_NUMBER);
			validateFieldUnits("incident_polarization_stokes", group.getDataNode("incident_polarization_stokes"), NX_ANY);
			validateFieldRank("incident_polarization_stokes", incident_polarization_stokes, 2);
			validateFieldDimensions("incident_polarization_stokes", incident_polarization_stokes, "NXbeam", "nP", 4);
		}

		// validate field 'depends_on' of type NX_CHAR.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate unnamed child group of type NXtransformations (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXtransformations.class, false, true);
		final Map<String, NXtransformations> allTransformations = group.getAllTransformations();
		for (final NXtransformations transformations : allTransformations.values()) {
			validateGroup_NXentry_NXinstrument_beam_probe_NXtransformations(transformations);
		}
		// validate NXtransformations groups (special case)
		validateTransformations(allTransformations, depends_on);
	}

	/**
	 * Validate unnamed group of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXinstrument_beam_probe_NXtransformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'direction' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset direction = group.getLazyDataset("direction");
		validateFieldNotNull("direction", direction);
		if (direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("direction", direction, NX_NUMBER);
		}

		// validate field 'reference_plane' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset reference_plane = group.getLazyDataset("reference_plane");
		validateFieldNotNull("reference_plane", reference_plane);
		if (reference_plane != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("reference_plane", reference_plane, NX_NUMBER);
		}
	}

	/**
	 * Validate unnamed group of type NXoptical_system_em.
	 */
	private void validateGroup_NXentry_NXinstrument_NXoptical_system_em(final NXoptical_system_em group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXoptical_system_em.class, group))) return;

		// validate field 'field_of_view' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset field_of_view = group.getLazyDataset("field_of_view");
		validateFieldNotNull("field_of_view", field_of_view);
		if (field_of_view != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("field_of_view", field_of_view, NX_FLOAT);
		// validate attribute 'units' of field 'field_of_view' of type NX_CHAR.
		final Attribute field_of_view_attr_units = group.getDataNode("field_of_view").getAttribute("units");
		if (!(validateAttributeNotNull("units", field_of_view_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", field_of_view_attr_units, NX_CHAR);

		}

		// validate field 'working_distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset working_distance = group.getLazyDataset("working_distance");
		validateFieldNotNull("working_distance", working_distance);
		if (working_distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("working_distance", working_distance, NX_FLOAT);
		}

		// validate field 'magnification' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset magnification = group.getLazyDataset("magnification");
		validateFieldNotNull("magnification", magnification);
		if (magnification != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnification", magnification, NX_FLOAT);
		}
	}

	/**
	 * Validate unnamed group of type NXelectronanalyser.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser(final NXelectronanalyser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXelectronanalyser.class, group))) return;

		// validate unnamed child group of type NXcollectioncolumn (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcollectioncolumn.class, false, true);
		final Map<String, NXcollectioncolumn> allCollectioncolumn = group.getChildren(NXcollectioncolumn.class);
		for (final NXcollectioncolumn collectioncolumn : allCollectioncolumn.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn(collectioncolumn);
		}

		// validate optional child group 'electron_gun' of type NXebeam_column
		if (group.getChild("electron_gun", NXebeam_column.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_electron_gun(group.getChild("electron_gun", NXebeam_column.class));
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

		// validate unnamed child group of type NXmanipulator (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmanipulator.class, false, true);
		final Map<String, NXmanipulator> allManipulator = group.getChildren(NXmanipulator.class);
		for (final NXmanipulator manipulator : allManipulator.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXmanipulator(manipulator);
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
					"standard",
					"angular dispersive",
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

		// validate optional field 'mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset mode = group.getLazyDataset("mode");
				if (mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mode", mode, NX_CHAR);
		}

		// validate field 'projection' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset projection = group.getLazyDataset("projection");
		validateFieldNotNull("projection", projection);
		if (projection != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("projection", projection, NX_CHAR);
			validateFieldEnumeration("projection", projection,
					"real",
					"reciprocal",
					"energy");
		}

		// validate child group 'extractor_voltage' of type NXlens_em
		validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn_extractor_voltage(group.getChild("extractor_voltage", NXlens_em.class));

		// validate child group 'objective_lens' of type NXlens_em
		validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn_objective_lens(group.getChild("objective_lens", NXlens_em.class));
	}

	/**
	 * Validate group 'extractor_voltage' of type NXlens_em.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn_extractor_voltage(final NXlens_em group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("extractor_voltage", NXlens_em.class, group))) return;

	}

	/**
	 * Validate group 'objective_lens' of type NXlens_em.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXcollectioncolumn_objective_lens(final NXlens_em group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("objective_lens", NXlens_em.class, group))) return;

	}

	/**
	 * Validate optional group 'electron_gun' of type NXebeam_column.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_electron_gun(final NXebeam_column group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("electron_gun", NXebeam_column.class, group))) return;

		// validate child group 'field_aperture' of type NXaperture_em
		validateGroup_NXentry_NXinstrument_NXelectronanalyser_electron_gun_field_aperture(group.getChild("field_aperture", NXaperture_em.class));

		// validate child group 'beam' of type NXbeam
		validateGroup_NXentry_NXinstrument_NXelectronanalyser_electron_gun_beam(group.getChild("beam", NXbeam.class));
	}

	/**
	 * Validate group 'field_aperture' of type NXaperture_em.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_electron_gun_field_aperture(final NXaperture_em group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("field_aperture", NXaperture_em.class, group))) return;

	}

	/**
	 * Validate group 'beam' of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_electron_gun_beam(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXenergydispersion.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion(final NXenergydispersion group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXenergydispersion.class, group))) return;

		// validate field 'energy_interval' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset energy_interval = group.getLazyDataset("energy_interval");
		validateFieldNotNull("energy_interval", energy_interval);
		if (energy_interval != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_interval", energy_interval, NX_FLOAT);
		}

		// validate field 'center_energy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset center_energy = group.getLazyDataset("center_energy");
		validateFieldNotNull("center_energy", center_energy);
		if (center_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center_energy", center_energy, NX_FLOAT);
		}
		// validate optional child group 'entrance_slit' of type NXaperture_em
		if (group.getChild("entrance_slit", NXaperture_em.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion_entrance_slit(group.getChild("entrance_slit", NXaperture_em.class));
		}

		// validate optional child group 'exit_slit' of type NXaperture_em
		if (group.getChild("exit_slit", NXaperture_em.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion_exit_slit(group.getChild("exit_slit", NXaperture_em.class));
		}
	}

	/**
	 * Validate optional group 'entrance_slit' of type NXaperture_em.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion_entrance_slit(final NXaperture_em group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("entrance_slit", NXaperture_em.class, group))) return;

	}

	/**
	 * Validate optional group 'exit_slit' of type NXaperture_em.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXenergydispersion_exit_slit(final NXaperture_em group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("exit_slit", NXaperture_em.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'depends_on' of type NX_CHAR.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate field 'dark_image' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset dark_image = group.getLazyDataset("dark_image");
		validateFieldNotNull("dark_image", dark_image);
		if (dark_image != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dark_image", dark_image, NX_NUMBER);
		}

		// validate field 'flatfield_image' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset flatfield_image = group.getLazyDataset("flatfield_image");
		validateFieldNotNull("flatfield_image", flatfield_image);
		if (flatfield_image != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flatfield_image", flatfield_image, NX_NUMBER);
		}

		// validate field 'data' of type NX_NUMBER.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 4);
			validateFieldDimensions("data", data, "NXdetector", "nP", "i", "j", "tof");
		}
		// validate child group 'transformations' of type NXtransformations
		validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXdetector_transformations(group.getTransformations());

		// validate unnamed child group of type NXdetector_module (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector_module.class, false, true);
		final Map<String, NXdetector_module> allDetector_module = group.getAllDetector_module();
		for (final NXdetector_module detector_module : allDetector_module.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXdetector_NXdetector_module(detector_module);
		}
	}

	/**
	 * Validate group 'transformations' of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXdetector_transformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transformations", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXdetector_module.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXdetector_NXdetector_module(final NXdetector_module group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector_module.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXmanipulator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXmanipulator(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmanipulator.class, group))) return;

		// validate field 'sample_bias' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset sample_bias = group.getLazyDataset("sample_bias");
		validateFieldNotNull("sample_bias", sample_bias);
		if (sample_bias != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_bias", sample_bias, NX_FLOAT);
		}
		// validate unnamed child group of type NXpositioner (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXpositioner.class, false, true);
		final Map<String, NXpositioner> allPositioner = group.getChildren(NXpositioner.class);
		for (final NXpositioner positioner : allPositioner.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXmanipulator_NXpositioner(positioner);
		}
	}

	/**
	 * Validate unnamed group of type NXpositioner.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyser_NXmanipulator_NXpositioner(final NXpositioner group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXpositioner.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate group 'coordinate_system_set' of type NXcoordinate_system_set.
	 */
	private void validateGroup_NXentry_coordinate_system_set(final NXcoordinate_system_set group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("coordinate_system_set", NXcoordinate_system_set.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'depends_on' of type NX_CHAR.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate unnamed child group of type NXtransformations (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXtransformations.class, false, true);
		final Map<String, NXtransformations> allTransformations = group.getAllTransformations();
		for (final NXtransformations transformations : allTransformations.values()) {
			validateGroup_NXentry_NXsample_NXtransformations(transformations);
		}
		// validate NXtransformations groups (special case)
		validateTransformations(allTransformations, depends_on);
	}

	/**
	 * Validate unnamed group of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXsample_NXtransformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}
}
