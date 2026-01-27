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
import org.eclipse.dawnsci.nexus.NXtransformations;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXelectronanalyzer;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXcollectioncolumn;
import org.eclipse.dawnsci.nexus.NXenergydispersion;
import org.eclipse.dawnsci.nexus.NXcalibration;
import org.eclipse.dawnsci.nexus.NXfit;
import org.eclipse.dawnsci.nexus.NXpeak;
import org.eclipse.dawnsci.nexus.NXfit_function;
import org.eclipse.dawnsci.nexus.NXparameters;
import org.eclipse.dawnsci.nexus.NXsample;

/**
 * Validator for the application definition 'NXxps'.
 */
public class NXxpsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXxpsValidator() {
		super(NexusApplicationDefinition.NX_XPS);
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
					"NXxps");
		}

		// validate field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
		validateFieldNotNull("method", method);
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

		// validate optional child group 'xps_coordinate_system' of type NXcoordinate_system
		if (group.getChild("xps_coordinate_system", NXcoordinate_system.class) != null) {
			validateGroup_NXentry_xps_coordinate_system(group.getChild("xps_coordinate_system", NXcoordinate_system.class));
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate optional child group 'energy_referencing' of type NXcalibration
		if (group.getChild("energy_referencing", NXcalibration.class) != null) {
			validateGroup_NXentry_energy_referencing(group.getChild("energy_referencing", NXcalibration.class));
		}

		// validate optional child group 'transmission_correction' of type NXcalibration
		if (group.getChild("transmission_correction", NXcalibration.class) != null) {
			validateGroup_NXentry_transmission_correction(group.getChild("transmission_correction", NXcalibration.class));
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
	 * Validate optional group 'xps_coordinate_system' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_xps_coordinate_system(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("xps_coordinate_system", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'origin' of type NX_CHAR.
		final ILazyDataset origin = group.getLazyDataset("origin");
		validateFieldNotNull("origin", origin);
		if (origin != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("origin", origin, NX_CHAR);
			validateFieldEnumeration("origin", origin,
					"sample stage");
		}

		// validate field 'z_direction' of type NX_CHAR.
		final ILazyDataset z_direction = group.getLazyDataset("z_direction");
		validateFieldNotNull("z_direction", z_direction);
		if (z_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z_direction", z_direction, NX_CHAR);
			validateFieldEnumeration("z_direction", z_direction,
					"sample stage normal");
		}

		// validate field 'x' of type NX_NUMBER.
		final ILazyDataset x = group.getLazyDataset("x");
		validateFieldNotNull("x", x);
		if (x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x", x, NX_NUMBER);
			validateFieldUnits("x", group.getDataNode("x"), NX_LENGTH);
			validateFieldEnumeration("x", x,
					"[-1, 0, 0]");
			validateFieldRank("x", x, 1);
			validateFieldDimensions("x", x, "NXcoordinate_system", 3);
		}

		// validate field 'y' of type NX_NUMBER.
		final ILazyDataset y = group.getLazyDataset("y");
		validateFieldNotNull("y", y);
		if (y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y", y, NX_NUMBER);
			validateFieldUnits("y", group.getDataNode("y"), NX_LENGTH);
			validateFieldEnumeration("y", y,
					"[0, 1, 0]");
			validateFieldRank("y", y, 1);
			validateFieldDimensions("y", y, "NXcoordinate_system", 3);
		}

		// validate field 'z' of type NX_NUMBER.
		final ILazyDataset z = group.getLazyDataset("z");
		validateFieldNotNull("z", z);
		if (z != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z", z, NX_NUMBER);
			validateFieldUnits("z", group.getDataNode("z"), NX_LENGTH);
			validateFieldEnumeration("z", z,
					"[0, 0, 1]");
			validateFieldRank("z", z, 1);
			validateFieldDimensions("z", z, "NXcoordinate_system", 3);
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
			validateGroup_NXentry_xps_coordinate_system_NXtransformations(transformations);
		}
		// validate NXtransformations groups (special case)
		validateTransformations(allTransformations, depends_on);
	}

	/**
	 * Validate optional unnamed group of type NXtransformations.
	 */
	private void validateGroup_NXentry_xps_coordinate_system_NXtransformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate optional child group 'source_probe' of type NXsource
		if (group.getSource("source_probe") != null) {
			validateGroup_NXentry_NXinstrument_source_probe(group.getSource("source_probe"));
		}

		// validate child group 'beam_probe' of type NXbeam
		validateGroup_NXentry_NXinstrument_beam_probe(group.getBeam("beam_probe"));

		// validate unnamed child group of type NXelectronanalyzer (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXelectronanalyzer.class, false, true);
		final Map<String, NXelectronanalyzer> allElectronanalyzer = group.getChildren(NXelectronanalyzer.class);
		for (final NXelectronanalyzer electronanalyzer : allElectronanalyzer.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer(electronanalyzer);
		}
	}

	/**
	 * Validate optional group 'source_probe' of type NXsource.
	 */
	private void validateGroup_NXentry_NXinstrument_source_probe(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source_probe", NXsource.class, group))) return;

		// validate optional field 'power' of type NX_FLOAT.
		final ILazyDataset power = group.getLazyDataset("power");
				if (power != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("power", power, NX_FLOAT);
			validateFieldUnits("power", group.getDataNode("power"), NX_POWER);
		}
	}

	/**
	 * Validate group 'beam_probe' of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_beam_probe(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_probe", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'depends_on' of type NX_CHAR.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
				if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate optional child group 'transformations' of type NXtransformations
		if (group.getTransformations() != null) {
			validateGroup_NXentry_NXinstrument_beam_probe_transformations(group.getTransformations());
		}
	}

	/**
	 * Validate optional group 'transformations' of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXinstrument_beam_probe_transformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transformations", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'beam_direction' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset beam_direction = group.getLazyDataset("beam_direction");
		validateFieldNotNull("beam_direction", beam_direction);
		if (beam_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_direction", beam_direction, NX_NUMBER);
			validateFieldUnits("beam_direction", group.getDataNode("beam_direction"), NX_UNITLESS);
		// validate attribute 'vector' of field 'beam_direction' of type NX_NUMBER.
		final Attribute beam_direction_attr_vector = group.getDataNode("beam_direction").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", beam_direction_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", beam_direction_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", beam_direction_attr_vector,
				"[0, 0, -1]");

		// validate attribute 'depends_on' of field 'beam_direction' of type NX_CHAR.
		final Attribute beam_direction_attr_depends_on = group.getDataNode("beam_direction").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", beam_direction_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", beam_direction_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", beam_direction_attr_depends_on,
				"beam_polar_angle_of_incidence");

		}

		// validate field 'beam_polar_angle_of_incidence' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset beam_polar_angle_of_incidence = group.getLazyDataset("beam_polar_angle_of_incidence");
		validateFieldNotNull("beam_polar_angle_of_incidence", beam_polar_angle_of_incidence);
		if (beam_polar_angle_of_incidence != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_polar_angle_of_incidence", beam_polar_angle_of_incidence, NX_NUMBER);
			validateFieldUnits("beam_polar_angle_of_incidence", group.getDataNode("beam_polar_angle_of_incidence"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'beam_polar_angle_of_incidence' of type NX_CHAR.
		final Attribute beam_polar_angle_of_incidence_attr_transformation_type = group.getDataNode("beam_polar_angle_of_incidence").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", beam_polar_angle_of_incidence_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", beam_polar_angle_of_incidence_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", beam_polar_angle_of_incidence_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'beam_polar_angle_of_incidence' of type NX_NUMBER.
		final Attribute beam_polar_angle_of_incidence_attr_vector = group.getDataNode("beam_polar_angle_of_incidence").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", beam_polar_angle_of_incidence_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", beam_polar_angle_of_incidence_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", beam_polar_angle_of_incidence_attr_vector,
				"[-1, 0, 0]");

		// validate attribute 'depends_on' of field 'beam_polar_angle_of_incidence' of type NX_CHAR.
		final Attribute beam_polar_angle_of_incidence_attr_depends_on = group.getDataNode("beam_polar_angle_of_incidence").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", beam_polar_angle_of_incidence_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", beam_polar_angle_of_incidence_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", beam_polar_angle_of_incidence_attr_depends_on,
				"beam_azimuth_angle");

		}

		// validate field 'beam_azimuth_angle' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset beam_azimuth_angle = group.getLazyDataset("beam_azimuth_angle");
		validateFieldNotNull("beam_azimuth_angle", beam_azimuth_angle);
		if (beam_azimuth_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_azimuth_angle", beam_azimuth_angle, NX_NUMBER);
			validateFieldUnits("beam_azimuth_angle", group.getDataNode("beam_azimuth_angle"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'beam_azimuth_angle' of type NX_CHAR.
		final Attribute beam_azimuth_angle_attr_transformation_type = group.getDataNode("beam_azimuth_angle").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", beam_azimuth_angle_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", beam_azimuth_angle_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", beam_azimuth_angle_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'beam_azimuth_angle' of type NX_NUMBER.
		final Attribute beam_azimuth_angle_attr_vector = group.getDataNode("beam_azimuth_angle").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", beam_azimuth_angle_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", beam_azimuth_angle_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", beam_azimuth_angle_attr_vector,
				"[0, 0, 1]");

		// validate attribute 'depends_on' of field 'beam_azimuth_angle' of type NX_CHAR.
		final Attribute beam_azimuth_angle_attr_depends_on = group.getDataNode("beam_azimuth_angle").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", beam_azimuth_angle_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", beam_azimuth_angle_attr_depends_on, NX_CHAR);

		}
	}

	/**
	 * Validate unnamed group of type NXelectronanalyzer.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer(final NXelectronanalyzer group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXelectronanalyzer.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'work_function' of type NX_FLOAT.
		final ILazyDataset work_function = group.getLazyDataset("work_function");
		validateFieldNotNull("work_function", work_function);
		if (work_function != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("work_function", work_function, NX_FLOAT);
			validateFieldUnits("work_function", group.getDataNode("work_function"), NX_ENERGY);
		}

		// validate optional field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
				if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
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

		// validate optional child group 'transformations' of type NXtransformations
		if (group.getChild("transformations", NXtransformations.class) != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_transformations(group.getChild("transformations", NXtransformations.class));
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

		// validate optional field 'magnification' of type NX_FLOAT.
		final ILazyDataset magnification = group.getLazyDataset("magnification");
				if (magnification != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("magnification", magnification, NX_FLOAT);
			validateFieldUnits("magnification", group.getDataNode("magnification"), NX_DIMENSIONLESS);
		}
	}

	/**
	 * Validate unnamed group of type NXenergydispersion.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion(final NXenergydispersion group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXenergydispersion.class, group))) return;

		// validate optional field 'radius' of type NX_NUMBER.
		final ILazyDataset radius = group.getLazyDataset("radius");
				if (radius != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("radius", radius, NX_NUMBER);
			validateFieldUnits("radius", group.getDataNode("radius"), NX_LENGTH);
		}

		// validate field 'energy_scan_mode' of type NX_CHAR.
		final ILazyDataset energy_scan_mode = group.getLazyDataset("energy_scan_mode");
		validateFieldNotNull("energy_scan_mode", energy_scan_mode);
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
	}

	/**
	 * Validate optional group 'transformations' of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_transformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transformations", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'analyzer_take_off_polar_angle' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset analyzer_take_off_polar_angle = group.getLazyDataset("analyzer_take_off_polar_angle");
		validateFieldNotNull("analyzer_take_off_polar_angle", analyzer_take_off_polar_angle);
		if (analyzer_take_off_polar_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("analyzer_take_off_polar_angle", analyzer_take_off_polar_angle, NX_NUMBER);
			validateFieldUnits("analyzer_take_off_polar_angle", group.getDataNode("analyzer_take_off_polar_angle"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'analyzer_take_off_polar_angle' of type NX_CHAR.
		final Attribute analyzer_take_off_polar_angle_attr_transformation_type = group.getDataNode("analyzer_take_off_polar_angle").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", analyzer_take_off_polar_angle_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", analyzer_take_off_polar_angle_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", analyzer_take_off_polar_angle_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'analyzer_take_off_polar_angle' of type NX_NUMBER.
		final Attribute analyzer_take_off_polar_angle_attr_vector = group.getDataNode("analyzer_take_off_polar_angle").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", analyzer_take_off_polar_angle_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", analyzer_take_off_polar_angle_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", analyzer_take_off_polar_angle_attr_vector,
				"[-1, 0, 0]");

		// validate attribute 'depends_on' of field 'analyzer_take_off_polar_angle' of type NX_CHAR.
		final Attribute analyzer_take_off_polar_angle_attr_depends_on = group.getDataNode("analyzer_take_off_polar_angle").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", analyzer_take_off_polar_angle_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", analyzer_take_off_polar_angle_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", analyzer_take_off_polar_angle_attr_depends_on,
				"analyzer_take_off_azimuth_angle");

		}

		// validate field 'analyzer_take_off_azimuth_angle' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset analyzer_take_off_azimuth_angle = group.getLazyDataset("analyzer_take_off_azimuth_angle");
		validateFieldNotNull("analyzer_take_off_azimuth_angle", analyzer_take_off_azimuth_angle);
		if (analyzer_take_off_azimuth_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("analyzer_take_off_azimuth_angle", analyzer_take_off_azimuth_angle, NX_NUMBER);
			validateFieldUnits("analyzer_take_off_azimuth_angle", group.getDataNode("analyzer_take_off_azimuth_angle"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'analyzer_take_off_azimuth_angle' of type NX_CHAR.
		final Attribute analyzer_take_off_azimuth_angle_attr_transformation_type = group.getDataNode("analyzer_take_off_azimuth_angle").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", analyzer_take_off_azimuth_angle_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", analyzer_take_off_azimuth_angle_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", analyzer_take_off_azimuth_angle_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'analyzer_take_off_azimuth_angle' of type NX_NUMBER.
		final Attribute analyzer_take_off_azimuth_angle_attr_vector = group.getDataNode("analyzer_take_off_azimuth_angle").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", analyzer_take_off_azimuth_angle_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", analyzer_take_off_azimuth_angle_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", analyzer_take_off_azimuth_angle_attr_vector,
				"[0, 0, 1]");

		// validate attribute 'depends_on' of field 'analyzer_take_off_azimuth_angle' of type NX_CHAR.
		final Attribute analyzer_take_off_azimuth_angle_attr_depends_on = group.getDataNode("analyzer_take_off_azimuth_angle").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", analyzer_take_off_azimuth_angle_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", analyzer_take_off_azimuth_angle_attr_depends_on, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'energy_referencing' of type NXcalibration.
	 */
	private void validateGroup_NXentry_energy_referencing(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("energy_referencing", NXcalibration.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'transmission_correction' of type NXcalibration.
	 */
	private void validateGroup_NXentry_transmission_correction(final NXcalibration group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transmission_correction", NXcalibration.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXfit.
	 */
	private void validateGroup_NXentry_NXfit(final NXfit group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXfit.class, group))) return;

		// validate field 'label' of type NX_CHAR.
		final ILazyDataset label = group.getLazyDataset("label");
		validateFieldNotNull("label", label);
		if (label != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("label", label, NX_CHAR);
		}

		// validate optional field 'figure_of_meritMETRIC' of type NX_NUMBER.
		final ILazyDataset figure_of_meritMETRIC = group.getLazyDataset("figure_of_meritMETRIC");
				if (figure_of_meritMETRIC != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("figure_of_meritMETRIC", figure_of_meritMETRIC, NX_NUMBER);
			validateFieldUnits("figure_of_meritMETRIC", group.getDataNode("figure_of_meritMETRIC"), NX_UNITLESS);
		// validate attribute 'metric' of field 'figure_of_meritMETRIC' of type NX_CHAR.
		final Attribute figure_of_meritMETRIC_attr_metric = group.getDataNode("figure_of_meritMETRIC").getAttribute("metric");
		if (!(validateAttributeNotNull("metric", figure_of_meritMETRIC_attr_metric))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("metric", figure_of_meritMETRIC_attr_metric, NX_CHAR);

		}
		// validate child group 'data' of type NXdata
		validateGroup_NXentry_NXfit_data(group.getData());

		// validate child group 'peakPEAK' of type NXpeak
		validateGroup_NXentry_NXfit_peakPEAK(group.getPeakpeak());

		// validate child group 'backgroundBACKGROUND' of type NXpeak
		validateGroup_NXentry_NXfit_backgroundBACKGROUND(group.getBackgroundbackground());

		// validate optional child group 'global_fit_function' of type NXfit_function
		if (group.getGlobal_fit_function() != null) {
			validateGroup_NXentry_NXfit_global_fit_function(group.getGlobal_fit_function());
		}

		// validate optional child group 'error_function' of type NXfit_function
		if (group.getError_function() != null) {
			validateGroup_NXentry_NXfit_error_function(group.getError_function());
		}
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_NXentry_NXfit_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'input_dependent' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset input_dependent = group.getLazyDataset("input_dependent");
		validateFieldNotNull("input_dependent", input_dependent);
		if (input_dependent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("input_dependent", input_dependent, NX_NUMBER);
			validateFieldUnits("input_dependent", group.getDataNode("input_dependent"), NX_ANY);
		}

		// validate field 'input_independent' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset input_independent = group.getLazyDataset("input_independent");
		validateFieldNotNull("input_independent", input_independent);
		if (input_independent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("input_independent", input_independent, NX_NUMBER);
			validateFieldUnits("input_independent", group.getDataNode("input_independent"), NX_ENERGY);
		}

		// validate field 'fit_sum' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset fit_sum = group.getLazyDataset("fit_sum");
		validateFieldNotNull("fit_sum", fit_sum);
		if (fit_sum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fit_sum", fit_sum, NX_NUMBER);
			validateFieldUnits("fit_sum", group.getDataNode("fit_sum"), NX_ANY);
		}

		// validate optional field 'residual' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset residual = group.getLazyDataset("residual");
				if (residual != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("residual", residual, NX_NUMBER);
			validateFieldUnits("residual", group.getDataNode("residual"), NX_ANY);
		}
	}

	/**
	 * Validate group 'peakPEAK' of type NXpeak.
	 */
	private void validateGroup_NXentry_NXfit_peakPEAK(final NXpeak group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("peakPEAK", NXpeak.class, group))) return;

		// validate field 'label' of type NX_CHAR.
		final ILazyDataset label = group.getLazyDataset("label");
		validateFieldNotNull("label", label);
		if (label != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("label", label, NX_CHAR);
		}

		// validate optional field 'total_area' of type NX_NUMBER.
		final ILazyDataset total_area = group.getLazyDataset("total_area");
				if (total_area != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_area", total_area, NX_NUMBER);
			validateFieldUnits("total_area", group.getDataNode("total_area"), NX_ANY);
		}

		// validate optional field 'relative_atomic_concentration' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset relative_atomic_concentration = group.getLazyDataset("relative_atomic_concentration");
				if (relative_atomic_concentration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("relative_atomic_concentration", relative_atomic_concentration, NX_FLOAT);
			validateFieldUnits("relative_atomic_concentration", group.getDataNode("relative_atomic_concentration"), NX_ANY);
		}
		// validate child group 'data' of type NXdata
		validateGroup_NXentry_NXfit_peakPEAK_data(group.getData());

		// validate optional child group 'function' of type NXfit_function
		if (group.getFunction() != null) {
			validateGroup_NXentry_NXfit_peakPEAK_function(group.getFunction());
		}
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_NXentry_NXfit_peakPEAK_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'position' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset position = group.getLazyDataset("position");
		validateFieldNotNull("position", position);
		if (position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("position", position, NX_NUMBER);
			validateFieldUnits("position", group.getDataNode("position"), NX_ENERGY);
		}

		// validate field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
		validateFieldNotNull("intensity", intensity);
		if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'function' of type NXfit_function.
	 */
	private void validateGroup_NXentry_NXfit_peakPEAK_function(final NXfit_function group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("function", NXfit_function.class, group))) return;

		// validate field 'function_type' of type NX_CHAR.
		final ILazyDataset function_type = group.getLazyDataset("function_type");
		validateFieldNotNull("function_type", function_type);
		if (function_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("function_type", function_type, NX_CHAR);
			validateFieldEnumeration("function_type", function_type,
					"Gaussian",
					"Lorentzian",
					"Voigt",
					"Gaussian-Lorentzian Sum",
					"Gaussian-Lorentzian Product",
					"Asymmetric Lorentzian",
					"Doniach-Sunjic",
					"Asymmetric Finite");
		}

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'formula_description' of type NX_CHAR.
		final ILazyDataset formula_description = group.getLazyDataset("formula_description");
				if (formula_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("formula_description", formula_description, NX_CHAR);
		}

		// validate optional child group 'fit_parameters' of type NXparameters
		if (group.getFit_parameters() != null) {
			validateGroup_NXentry_NXfit_peakPEAK_function_fit_parameters(group.getFit_parameters());
		}
	}

	/**
	 * Validate optional group 'fit_parameters' of type NXparameters.
	 */
	private void validateGroup_NXentry_NXfit_peakPEAK_function_fit_parameters(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fit_parameters", NXparameters.class, group))) return;

		// validate optional field 'area' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset area = group.getLazyDataset("area");
				if (area != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("area", area, NX_NUMBER);
			validateFieldUnits("area", group.getDataNode("area"), NX_ANY);
		}

		// validate optional field 'width' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset width = group.getLazyDataset("width");
				if (width != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("width", width, NX_NUMBER);
			validateFieldUnits("width", group.getDataNode("width"), NX_ENERGY);
		}

		// validate optional field 'position' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset position = group.getLazyDataset("position");
				if (position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("position", position, NX_NUMBER);
			validateFieldUnits("position", group.getDataNode("position"), NX_ENERGY);
		}
	}

	/**
	 * Validate group 'backgroundBACKGROUND' of type NXpeak.
	 */
	private void validateGroup_NXentry_NXfit_backgroundBACKGROUND(final NXpeak group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("backgroundBACKGROUND", NXpeak.class, group))) return;

		// validate optional field 'label' of type NX_CHAR.
		final ILazyDataset label = group.getLazyDataset("label");
				if (label != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("label", label, NX_CHAR);
		}

		// validate child group 'data' of type NXdata
		validateGroup_NXentry_NXfit_backgroundBACKGROUND_data(group.getData());

		// validate optional child group 'function' of type NXfit_function
		if (group.getFunction() != null) {
			validateGroup_NXentry_NXfit_backgroundBACKGROUND_function(group.getFunction());
		}
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_NXentry_NXfit_backgroundBACKGROUND_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'position' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset position = group.getLazyDataset("position");
		validateFieldNotNull("position", position);
		if (position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("position", position, NX_NUMBER);
			validateFieldUnits("position", group.getDataNode("position"), NX_ENERGY);
		}

		// validate field 'intensity' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
		validateFieldNotNull("intensity", intensity);
		if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'function' of type NXfit_function.
	 */
	private void validateGroup_NXentry_NXfit_backgroundBACKGROUND_function(final NXfit_function group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("function", NXfit_function.class, group))) return;

		// validate field 'function_type' of type NX_CHAR.
		final ILazyDataset function_type = group.getLazyDataset("function_type");
		validateFieldNotNull("function_type", function_type);
		if (function_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("function_type", function_type, NX_CHAR);
			validateFieldEnumeration("function_type", function_type,
					"Linear",
					"Shirley",
					"Tougaard",
					"Step Down",
					"Step Up");
		}

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'formula_description' of type NX_CHAR.
		final ILazyDataset formula_description = group.getLazyDataset("formula_description");
				if (formula_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("formula_description", formula_description, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'global_fit_function' of type NXfit_function.
	 */
	private void validateGroup_NXentry_NXfit_global_fit_function(final NXfit_function group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("global_fit_function", NXfit_function.class, group))) return;

		// validate optional field 'function_type' of type NX_CHAR.
		final ILazyDataset function_type = group.getLazyDataset("function_type");
				if (function_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("function_type", function_type, NX_CHAR);
		}

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'formula_description' of type NX_CHAR.
		final ILazyDataset formula_description = group.getLazyDataset("formula_description");
				if (formula_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("formula_description", formula_description, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'error_function' of type NXfit_function.
	 */
	private void validateGroup_NXentry_NXfit_error_function(final NXfit_function group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("error_function", NXfit_function.class, group))) return;

		// validate optional field 'function_type' of type NX_CHAR.
		final ILazyDataset function_type = group.getLazyDataset("function_type");
				if (function_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("function_type", function_type, NX_CHAR);
		}

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'formula_description' of type NX_CHAR.
		final ILazyDataset formula_description = group.getLazyDataset("formula_description");
				if (formula_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("formula_description", formula_description, NX_CHAR);
		}
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
				if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate optional child group 'transformations' of type NXtransformations
		if (group.getChild("transformations", NXtransformations.class) != null) {
			validateGroup_NXentry_NXsample_transformations(group.getChild("transformations", NXtransformations.class));
		}
	}

	/**
	 * Validate optional group 'transformations' of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXsample_transformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transformations", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'sample_rotation_angle' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset sample_rotation_angle = group.getLazyDataset("sample_rotation_angle");
		validateFieldNotNull("sample_rotation_angle", sample_rotation_angle);
		if (sample_rotation_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_rotation_angle", sample_rotation_angle, NX_NUMBER);
			validateFieldUnits("sample_rotation_angle", group.getDataNode("sample_rotation_angle"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'sample_rotation_angle' of type NX_CHAR.
		final Attribute sample_rotation_angle_attr_transformation_type = group.getDataNode("sample_rotation_angle").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", sample_rotation_angle_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", sample_rotation_angle_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", sample_rotation_angle_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'sample_rotation_angle' of type NX_NUMBER.
		final Attribute sample_rotation_angle_attr_vector = group.getDataNode("sample_rotation_angle").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", sample_rotation_angle_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", sample_rotation_angle_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", sample_rotation_angle_attr_vector,
				"[0, 0, 1]");

		// validate attribute 'depends_on' of field 'sample_rotation_angle' of type NX_CHAR.
		final Attribute sample_rotation_angle_attr_depends_on = group.getDataNode("sample_rotation_angle").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", sample_rotation_angle_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", sample_rotation_angle_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", sample_rotation_angle_attr_depends_on,
				"sample_normal_polar_angle_of_tilt");

		}

		// validate field 'sample_normal_polar_angle_of_tilt' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset sample_normal_polar_angle_of_tilt = group.getLazyDataset("sample_normal_polar_angle_of_tilt");
		validateFieldNotNull("sample_normal_polar_angle_of_tilt", sample_normal_polar_angle_of_tilt);
		if (sample_normal_polar_angle_of_tilt != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_normal_polar_angle_of_tilt", sample_normal_polar_angle_of_tilt, NX_NUMBER);
			validateFieldUnits("sample_normal_polar_angle_of_tilt", group.getDataNode("sample_normal_polar_angle_of_tilt"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'sample_normal_polar_angle_of_tilt' of type NX_CHAR.
		final Attribute sample_normal_polar_angle_of_tilt_attr_transformation_type = group.getDataNode("sample_normal_polar_angle_of_tilt").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", sample_normal_polar_angle_of_tilt_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", sample_normal_polar_angle_of_tilt_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", sample_normal_polar_angle_of_tilt_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'sample_normal_polar_angle_of_tilt' of type NX_NUMBER.
		final Attribute sample_normal_polar_angle_of_tilt_attr_vector = group.getDataNode("sample_normal_polar_angle_of_tilt").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", sample_normal_polar_angle_of_tilt_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", sample_normal_polar_angle_of_tilt_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", sample_normal_polar_angle_of_tilt_attr_vector,
				"[-1, 0, 0]");

		// validate attribute 'depends_on' of field 'sample_normal_polar_angle_of_tilt' of type NX_CHAR.
		final Attribute sample_normal_polar_angle_of_tilt_attr_depends_on = group.getDataNode("sample_normal_polar_angle_of_tilt").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", sample_normal_polar_angle_of_tilt_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", sample_normal_polar_angle_of_tilt_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", sample_normal_polar_angle_of_tilt_attr_depends_on,
				"sample_normal_tilt_azimuth_angle");

		}

		// validate field 'sample_normal_tilt_azimuth_angle' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset sample_normal_tilt_azimuth_angle = group.getLazyDataset("sample_normal_tilt_azimuth_angle");
		validateFieldNotNull("sample_normal_tilt_azimuth_angle", sample_normal_tilt_azimuth_angle);
		if (sample_normal_tilt_azimuth_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_normal_tilt_azimuth_angle", sample_normal_tilt_azimuth_angle, NX_NUMBER);
			validateFieldUnits("sample_normal_tilt_azimuth_angle", group.getDataNode("sample_normal_tilt_azimuth_angle"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'sample_normal_tilt_azimuth_angle' of type NX_CHAR.
		final Attribute sample_normal_tilt_azimuth_angle_attr_transformation_type = group.getDataNode("sample_normal_tilt_azimuth_angle").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", sample_normal_tilt_azimuth_angle_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", sample_normal_tilt_azimuth_angle_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", sample_normal_tilt_azimuth_angle_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'sample_normal_tilt_azimuth_angle' of type NX_NUMBER.
		final Attribute sample_normal_tilt_azimuth_angle_attr_vector = group.getDataNode("sample_normal_tilt_azimuth_angle").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", sample_normal_tilt_azimuth_angle_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", sample_normal_tilt_azimuth_angle_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", sample_normal_tilt_azimuth_angle_attr_vector,
				"[0, 0, 1]");

		// validate attribute 'depends_on' of field 'sample_normal_tilt_azimuth_angle' of type NX_CHAR.
		final Attribute sample_normal_tilt_azimuth_angle_attr_depends_on = group.getDataNode("sample_normal_tilt_azimuth_angle").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", sample_normal_tilt_azimuth_angle_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", sample_normal_tilt_azimuth_angle_attr_depends_on, NX_CHAR);

		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset energy = group.getLazyDataset("energy");
		validateFieldNotNull("energy", energy);
		if (energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy", energy, NX_NUMBER);
		}
		// validate attribute 'energy_indices' of type NX_INT.
		final Attribute energy_indices_attr = group.getAttribute("energy_indices");
		if (!(validateAttributeNotNull("energy_indices", energy_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("energy_indices", energy_indices_attr, NX_INT);

	}
}
