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
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXtransformations;
import org.eclipse.dawnsci.nexus.NXem_optical_system;
import org.eclipse.dawnsci.nexus.NXelectronanalyzer;
import org.eclipse.dawnsci.nexus.NXcollectioncolumn;
import org.eclipse.dawnsci.nexus.NXelectromagnetic_lens;
import org.eclipse.dawnsci.nexus.NXebeam_column;
import org.eclipse.dawnsci.nexus.NXaperture;
import org.eclipse.dawnsci.nexus.NXenergydispersion;
import org.eclipse.dawnsci.nexus.NXelectron_detector;
import org.eclipse.dawnsci.nexus.NXdetector_module;
import org.eclipse.dawnsci.nexus.NXmanipulator;
import org.eclipse.dawnsci.nexus.NXpositioner;
import org.eclipse.dawnsci.nexus.NXcoordinate_system;
import org.eclipse.dawnsci.nexus.NXsample;

/**
 * Validator for the application definition 'NXpeem'.
 */
public class NXpeemValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXpeemValidator() {
		super(NexusApplicationDefinition.NX_PEEM);
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
					"NXpeem");
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

		// validate child group 'geometries' of type NXcoordinate_system
		validateGroup_NXentry_geometries(group.getChild("geometries", NXcoordinate_system.class));

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

		// validate field 'data' of type NX_NUMBER.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
		}
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate optional child group 'monochromator_probe' of type NXmonochromator
		if (group.getMonochromator("monochromator_probe") != null) {
			validateGroup_NXentry_NXinstrument_monochromator_probe(group.getMonochromator("monochromator_probe"));
		}

		// validate optional child group 'source_probe' of type NXsource
		if (group.getSource("source_probe") != null) {
			validateGroup_NXentry_NXinstrument_source_probe(group.getSource("source_probe"));
		}

		// validate optional child group 'beam_probe' of type NXbeam
		if (group.getBeam("beam_probe") != null) {
			validateGroup_NXentry_NXinstrument_beam_probe(group.getBeam("beam_probe"));
		}

		// validate unnamed child group of type NXem_optical_system (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXem_optical_system.class, false, true);
		final Map<String, NXem_optical_system> allEm_optical_system = group.getChildren(NXem_optical_system.class);
		for (final NXem_optical_system em_optical_system : allEm_optical_system.values()) {
			validateGroup_NXentry_NXinstrument_NXem_optical_system(em_optical_system);
		}

		// validate unnamed child group of type NXelectronanalyzer (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXelectronanalyzer.class, false, true);
		final Map<String, NXelectronanalyzer> allElectronanalyzer = group.getChildren(NXelectronanalyzer.class);
		for (final NXelectronanalyzer electronanalyzer : allElectronanalyzer.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer(electronanalyzer);
		}
	}

	/**
	 * Validate optional group 'monochromator_probe' of type NXmonochromator.
	 */
	private void validateGroup_NXentry_NXinstrument_monochromator_probe(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("monochromator_probe", NXmonochromator.class, group))) return;

	}

	/**
	 * Validate optional group 'source_probe' of type NXsource.
	 */
	private void validateGroup_NXentry_NXinstrument_source_probe(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source_probe", NXsource.class, group))) return;

		// validate optional field 'associated_beam' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset associated_beam = group.getLazyDataset("associated_beam");
				if (associated_beam != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("associated_beam", associated_beam, NX_CHAR);
			validateFieldEnumeration("associated_beam", associated_beam,
					"beam_probe");
		}
	}

	/**
	 * Validate optional group 'beam_probe' of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_beam_probe(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_probe", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'flux' of type NX_FLOAT.
		final ILazyDataset flux = group.getLazyDataset("flux");
		validateFieldNotNull("flux", flux);
		if (flux != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("flux", flux, NX_FLOAT);
			validateFieldUnits("flux", group.getDataNode("flux"), NX_FLUX);
			validateFieldRank("flux", flux, 1);
			validateFieldDimensions("flux", flux, "NXbeam", "nP");
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

		// validate optional field 'incident_polarization_stokes' of type NX_NUMBER.
		final ILazyDataset incident_polarization_stokes = group.getLazyDataset("incident_polarization_stokes");
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

		// validate optional field 'direction' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset direction = group.getLazyDataset("direction");
				if (direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("direction", direction, NX_NUMBER);
		}

		// validate optional field 'reference_plane' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset reference_plane = group.getLazyDataset("reference_plane");
				if (reference_plane != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("reference_plane", reference_plane, NX_NUMBER);
		}
	}

	/**
	 * Validate unnamed group of type NXem_optical_system.
	 */
	private void validateGroup_NXentry_NXinstrument_NXem_optical_system(final NXem_optical_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXem_optical_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'field_of_view' of type NX_FLOAT.
		final ILazyDataset field_of_view = group.getLazyDataset("field_of_view");
		validateFieldNotNull("field_of_view", field_of_view);
		if (field_of_view != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("field_of_view", field_of_view, NX_FLOAT);
			validateFieldUnits("field_of_view", group.getDataNode("field_of_view"), NX_LENGTH);
		}

		// validate field 'working_distance' of type NX_FLOAT.
		final ILazyDataset working_distance = group.getLazyDataset("working_distance");
		validateFieldNotNull("working_distance", working_distance);
		if (working_distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("working_distance", working_distance, NX_FLOAT);
			validateFieldUnits("working_distance", group.getDataNode("working_distance"), NX_LENGTH);
		}

		// validate field 'magnification' of type NX_FLOAT.
		final ILazyDataset magnification = group.getLazyDataset("magnification");
		validateFieldNotNull("magnification", magnification);
		if (magnification != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnification", magnification, NX_FLOAT);
			validateFieldUnits("magnification", group.getDataNode("magnification"), NX_DIMENSIONLESS);
		}
	}

	/**
	 * Validate unnamed group of type NXelectronanalyzer.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer(final NXelectronanalyzer group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXelectronanalyzer.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate unnamed child group of type NXcollectioncolumn (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcollectioncolumn.class, false, true);
		final Map<String, NXcollectioncolumn> allCollectioncolumn = group.getAllCollectioncolumn();
		for (final NXcollectioncolumn collectioncolumn : allCollectioncolumn.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn(collectioncolumn);
		}

		// validate optional child group 'electron_gun' of type NXebeam_column
		if (group.getChild("electron_gun", NXebeam_column.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_electron_gun(group.getChild("electron_gun", NXebeam_column.class));
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

		// validate unnamed child group of type NXmanipulator (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmanipulator.class, false, true);
		final Map<String, NXmanipulator> allManipulator = group.getChildren(NXmanipulator.class);
		for (final NXmanipulator manipulator : allManipulator.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXmanipulator(manipulator);
		}
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

		// validate field 'projection' of type NX_CHAR.
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

		// validate field 'extractor_voltage' of type NX_FLOAT.
		final ILazyDataset extractor_voltage = group.getLazyDataset("extractor_voltage");
		validateFieldNotNull("extractor_voltage", extractor_voltage);
		if (extractor_voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extractor_voltage", extractor_voltage, NX_FLOAT);
			validateFieldUnits("extractor_voltage", group.getDataNode("extractor_voltage"), NX_VOLTAGE);
		}

		// validate child group 'objective_lens' of type NXelectromagnetic_lens
		validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn_objective_lens(group.getElectromagnetic_lens("objective_lens"));
	}

	/**
	 * Validate group 'objective_lens' of type NXelectromagnetic_lens.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn_objective_lens(final NXelectromagnetic_lens group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("objective_lens", NXelectromagnetic_lens.class, group))) return;

	}

	/**
	 * Validate optional group 'electron_gun' of type NXebeam_column.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_electron_gun(final NXebeam_column group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("electron_gun", NXebeam_column.class, group))) return;

		// validate child group 'field_aperture' of type NXaperture
		validateGroup_NXentry_NXinstrument_NXelectronanalyzer_electron_gun_field_aperture(group.getAperture("field_aperture"));

		// validate child group 'beam' of type NXbeam
		validateGroup_NXentry_NXinstrument_NXelectronanalyzer_electron_gun_beam(group.getBeam());
	}

	/**
	 * Validate group 'field_aperture' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_electron_gun_field_aperture(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("field_aperture", NXaperture.class, group))) return;

	}

	/**
	 * Validate group 'beam' of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_electron_gun_beam(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXenergydispersion.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion(final NXenergydispersion group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXenergydispersion.class, group))) return;

		// validate optional field 'energy_interval' of type NX_FLOAT.
		final ILazyDataset energy_interval = group.getLazyDataset("energy_interval");
				if (energy_interval != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_interval", energy_interval, NX_FLOAT);
			validateFieldUnits("energy_interval", group.getDataNode("energy_interval"), NX_ENERGY);
		}

		// validate optional field 'center_energy' of type NX_FLOAT.
		final ILazyDataset center_energy = group.getLazyDataset("center_energy");
				if (center_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center_energy", center_energy, NX_FLOAT);
			validateFieldUnits("center_energy", group.getDataNode("center_energy"), NX_ENERGY);
		}
	}

	/**
	 * Validate unnamed group of type NXelectron_detector.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector(final NXelectron_detector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXelectron_detector.class, group))) return;

		// validate field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
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

		// validate field 'raw_data' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset raw_data = group.getLazyDataset("raw_data");
		validateFieldNotNull("raw_data", raw_data);
		if (raw_data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("raw_data", raw_data, NX_NUMBER);
		}
		// validate child group 'transformations' of type NXtransformations
		validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector_transformations(group.getChild("transformations", NXtransformations.class));

		// validate unnamed child group of type NXdetector_module (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector_module.class, false, true);
		final Map<String, NXdetector_module> allDetector_module = group.getChildren(NXdetector_module.class);
		for (final NXdetector_module detector_module : allDetector_module.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector_NXdetector_module(detector_module);
		}
	}

	/**
	 * Validate group 'transformations' of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector_transformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transformations", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXdetector_module.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXelectron_detector_NXdetector_module(final NXdetector_module group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector_module.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXmanipulator.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXmanipulator(final NXmanipulator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmanipulator.class, group))) return;

		// validate field 'sample_bias_potentiostat' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset sample_bias_potentiostat = group.getLazyDataset("sample_bias_potentiostat");
		validateFieldNotNull("sample_bias_potentiostat", sample_bias_potentiostat);
		if (sample_bias_potentiostat != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_bias_potentiostat", sample_bias_potentiostat, NX_FLOAT);
			validateFieldUnits("sample_bias_potentiostat", group.getDataNode("sample_bias_potentiostat"), NX_VOLTAGE);
		}
		// validate unnamed child group of type NXpositioner (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXpositioner.class, false, true);
		final Map<String, NXpositioner> allPositioner = group.getAllPositioner();
		for (final NXpositioner positioner : allPositioner.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXmanipulator_NXpositioner(positioner);
		}
	}

	/**
	 * Validate unnamed group of type NXpositioner.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXmanipulator_NXpositioner(final NXpositioner group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXpositioner.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate group 'geometries' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_geometries(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("geometries", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate unnamed child group of type NXtransformations (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXtransformations.class, false, true);
		final Map<String, NXtransformations> allTransformations = group.getChildren(NXtransformations.class);
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
