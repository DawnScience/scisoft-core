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
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXsample;

/**
 * Validator for the application definition 'NXxrd_pan'.
 */
public class NXxrd_panValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXxrd_panValidator() {
		super(NexusApplicationDefinition.NX_XRD_PAN);
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

		// validate optional field 'data_file' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset data_file = group.getLazyDataset("data_file");
				if (data_file != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data_file", data_file, NX_CHAR);
		}

		// validate field 'measurement_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset measurement_type = group.getLazyDataset("measurement_type");
		validateFieldNotNull("measurement_type", measurement_type);
		if (measurement_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measurement_type", measurement_type, NX_CHAR);
		}

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXxrd_pan");
		}

		// validate field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
		validateFieldNotNull("method", method);
		if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
			validateFieldEnumeration("method", method,
					"X-Ray Diffraction (XRD)");
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate optional child group 'experiment_config' of type NXobject
		if (group.getChild("experiment_config", NXobject.class) != null) {
			validateGroup_NXentry_experiment_config(group.getChild("experiment_config", NXobject.class));
		}

		// validate child group 'experiment_result' of type NXdata
		validateGroup_NXentry_experiment_result(group.getData("experiment_result"));

		// validate optional child group 'q_data' of type NXdata
		if (group.getData("q_data") != null) {
			validateGroup_NXentry_q_data(group.getData("q_data"));
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
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

		// validate unnamed child group of type NXdetector (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector.class, false, true);
		final Map<String, NXdetector> allDetector = group.getAllDetector();
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector(detector);
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_NXentry_NXinstrument_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate field 'xray_tube_material' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset xray_tube_material = group.getLazyDataset("xray_tube_material");
		validateFieldNotNull("xray_tube_material", xray_tube_material);
		if (xray_tube_material != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xray_tube_material", xray_tube_material, NX_CHAR);
			validateFieldEnumeration("xray_tube_material", xray_tube_material,
					"Cu",
					"Cr",
					"Mo",
					"Fe",
					"Ag",
					"In",
					"Ga");
		}

		// validate field 'xray_tube_current' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset xray_tube_current = group.getLazyDataset("xray_tube_current");
		validateFieldNotNull("xray_tube_current", xray_tube_current);
		if (xray_tube_current != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xray_tube_current", xray_tube_current, NX_FLOAT);
			validateFieldUnits("xray_tube_current", group.getDataNode("xray_tube_current"), NX_CURRENT);
		}

		// validate field 'xray_tube_voltage' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset xray_tube_voltage = group.getLazyDataset("xray_tube_voltage");
		validateFieldNotNull("xray_tube_voltage", xray_tube_voltage);
		if (xray_tube_voltage != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("xray_tube_voltage", xray_tube_voltage, NX_FLOAT);
			validateFieldUnits("xray_tube_voltage", group.getDataNode("xray_tube_voltage"), NX_VOLTAGE);
		}

		// validate field 'k_alpha_one' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset k_alpha_one = group.getLazyDataset("k_alpha_one");
		validateFieldNotNull("k_alpha_one", k_alpha_one);
		if (k_alpha_one != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("k_alpha_one", k_alpha_one, NX_FLOAT);
			validateFieldUnits("k_alpha_one", group.getDataNode("k_alpha_one"), NX_WAVELENGTH);
		// validate attribute 'units' of field 'k_alpha_one' of type NX_CHAR.
		final Attribute k_alpha_one_attr_units = group.getDataNode("k_alpha_one").getAttribute("units");
		if (!(validateAttributeNotNull("units", k_alpha_one_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", k_alpha_one_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", k_alpha_one_attr_units,
				"angstrom");

		}

		// validate field 'k_alpha_two' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset k_alpha_two = group.getLazyDataset("k_alpha_two");
		validateFieldNotNull("k_alpha_two", k_alpha_two);
		if (k_alpha_two != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("k_alpha_two", k_alpha_two, NX_FLOAT);
			validateFieldUnits("k_alpha_two", group.getDataNode("k_alpha_two"), NX_WAVELENGTH);
		// validate attribute 'units' of field 'k_alpha_two' of type NX_CHAR.
		final Attribute k_alpha_two_attr_units = group.getDataNode("k_alpha_two").getAttribute("units");
		if (!(validateAttributeNotNull("units", k_alpha_two_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", k_alpha_two_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", k_alpha_two_attr_units,
				"angstrom");

		}

		// validate field 'ratio_k_alphatwo_k_alphaone' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset ratio_k_alphatwo_k_alphaone = group.getLazyDataset("ratio_k_alphatwo_k_alphaone");
		validateFieldNotNull("ratio_k_alphatwo_k_alphaone", ratio_k_alphatwo_k_alphaone);
		if (ratio_k_alphatwo_k_alphaone != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ratio_k_alphatwo_k_alphaone", ratio_k_alphatwo_k_alphaone, NX_FLOAT);
			validateFieldUnits("ratio_k_alphatwo_k_alphaone", group.getDataNode("ratio_k_alphatwo_k_alphaone"), NX_DIMENSIONLESS);
		}

		// validate optional field 'kbeta' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset kbeta = group.getLazyDataset("kbeta");
				if (kbeta != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kbeta", kbeta, NX_FLOAT);
			validateFieldUnits("kbeta", group.getDataNode("kbeta"), NX_WAVELENGTH);
		// validate attribute 'units' of field 'kbeta' of type NX_CHAR.
		final Attribute kbeta_attr_units = group.getDataNode("kbeta").getAttribute("units");
		if (!(validateAttributeNotNull("units", kbeta_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", kbeta_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", kbeta_attr_units,
				"angstrom");

		}

		// validate optional field 'source_peak_wavelength' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset source_peak_wavelength = group.getLazyDataset("source_peak_wavelength");
				if (source_peak_wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("source_peak_wavelength", source_peak_wavelength, NX_FLOAT);
			validateFieldUnits("source_peak_wavelength", group.getDataNode("source_peak_wavelength"), NX_WAVELENGTH);
		}
	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'scan_axis' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset scan_axis = group.getLazyDataset("scan_axis");
		validateFieldNotNull("scan_axis", scan_axis);
		if (scan_axis != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scan_axis", scan_axis, NX_CHAR);
		}

		// validate field 'scan_mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset scan_mode = group.getLazyDataset("scan_mode");
		validateFieldNotNull("scan_mode", scan_mode);
		if (scan_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scan_mode", scan_mode, NX_CHAR);
		}

		// validate optional field 'integration_time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset integration_time = group.getLazyDataset("integration_time");
				if (integration_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("integration_time", integration_time, NX_FLOAT);
			validateFieldUnits("integration_time", group.getDataNode("integration_time"), NX_TIME);
		}
	}

	/**
	 * Validate optional group 'experiment_config' of type NXobject.
	 */
	private void validateGroup_NXentry_experiment_config(final NXobject group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("experiment_config", NXobject.class, group))) return;

		// validate field 'beam_attenuation_factors' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset beam_attenuation_factors = group.getLazyDataset("beam_attenuation_factors");
		validateFieldNotNull("beam_attenuation_factors", beam_attenuation_factors);
		if (beam_attenuation_factors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_attenuation_factors", beam_attenuation_factors, NX_CHAR);
		}

		// validate optional field 'goniometer_x' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset goniometer_x = group.getLazyDataset("goniometer_x");
				if (goniometer_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("goniometer_x", goniometer_x, NX_FLOAT);
			validateFieldUnits("goniometer_x", group.getDataNode("goniometer_x"), NX_LENGTH);
		}

		// validate optional field 'goniometer_y' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset goniometer_y = group.getLazyDataset("goniometer_y");
				if (goniometer_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("goniometer_y", goniometer_y, NX_FLOAT);
			validateFieldUnits("goniometer_y", group.getDataNode("goniometer_y"), NX_LENGTH);
		}

		// validate optional field 'goniometer_z' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset goniometer_z = group.getLazyDataset("goniometer_z");
				if (goniometer_z != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("goniometer_z", goniometer_z, NX_FLOAT);
			validateFieldUnits("goniometer_z", group.getDataNode("goniometer_z"), NX_LENGTH);
		}

		// validate field 'count_time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset count_time = group.getLazyDataset("count_time");
		validateFieldNotNull("count_time", count_time);
		if (count_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("count_time", count_time, NX_FLOAT);
			validateFieldUnits("count_time", group.getDataNode("count_time"), NX_TIME);
		}
		// validate child group 'two_theta' of type NXobject
		validateGroup_NXentry_experiment_config_two_theta(group.getChild("two_theta", NXobject.class));

		// validate child group 'omega' of type NXobject
		validateGroup_NXentry_experiment_config_omega(group.getChild("omega", NXobject.class));
	}

	/**
	 * Validate group 'two_theta' of type NXobject.
	 */
	private void validateGroup_NXentry_experiment_config_two_theta(final NXobject group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("two_theta", NXobject.class, group))) return;

		// validate field 'start' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset start = group.getLazyDataset("start");
		validateFieldNotNull("start", start);
		if (start != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start", start, NX_FLOAT);
			validateFieldUnits("start", group.getDataNode("start"), NX_ANGLE);
		}

		// validate field 'end' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset end = group.getLazyDataset("end");
		validateFieldNotNull("end", end);
		if (end != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end", end, NX_FLOAT);
			validateFieldUnits("end", group.getDataNode("end"), NX_ANGLE);
		}

		// validate field 'step' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset step = group.getLazyDataset("step");
		validateFieldNotNull("step", step);
		if (step != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("step", step, NX_FLOAT);
			validateFieldUnits("step", group.getDataNode("step"), NX_ANGLE);
		}
	}

	/**
	 * Validate group 'omega' of type NXobject.
	 */
	private void validateGroup_NXentry_experiment_config_omega(final NXobject group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("omega", NXobject.class, group))) return;

		// validate field 'start' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset start = group.getLazyDataset("start");
		validateFieldNotNull("start", start);
		if (start != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start", start, NX_FLOAT);
			validateFieldUnits("start", group.getDataNode("start"), NX_ANGLE);
		}

		// validate field 'end' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset end = group.getLazyDataset("end");
		validateFieldNotNull("end", end);
		if (end != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end", end, NX_FLOAT);
			validateFieldUnits("end", group.getDataNode("end"), NX_ANGLE);
		}

		// validate field 'step' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset step = group.getLazyDataset("step");
		validateFieldNotNull("step", step);
		if (step != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("step", step, NX_FLOAT);
			validateFieldUnits("step", group.getDataNode("step"), NX_ANGLE);
		}
	}

	/**
	 * Validate group 'experiment_result' of type NXdata.
	 */
	private void validateGroup_NXentry_experiment_result(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("experiment_result", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'intensity' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
		validateFieldNotNull("intensity", intensity);
		if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_FLOAT);
			validateFieldRank("intensity", intensity, 1);
			validateFieldDimensions("intensity", intensity, null, "nDet");
		}

		// validate field 'two_theta' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset two_theta = group.getLazyDataset("two_theta");
		validateFieldNotNull("two_theta", two_theta);
		if (two_theta != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("two_theta", two_theta, NX_FLOAT);
			validateFieldUnits("two_theta", group.getDataNode("two_theta"), NX_ANGLE);
			validateFieldRank("two_theta", two_theta, 1);
			validateFieldDimensions("two_theta", two_theta, null, "nDet");
		}

		// validate optional field 'omega' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset omega = group.getLazyDataset("omega");
				if (omega != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("omega", omega, NX_FLOAT);
			validateFieldUnits("omega", group.getDataNode("omega"), NX_ANGLE);
			validateFieldRank("omega", omega, 1);
			validateFieldDimensions("omega", omega, null, "nDet");
		}

		// validate optional field 'phi' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset phi = group.getLazyDataset("phi");
				if (phi != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("phi", phi, NX_FLOAT);
			validateFieldUnits("phi", group.getDataNode("phi"), NX_ANGLE);
			validateFieldRank("phi", phi, 1);
			validateFieldDimensions("phi", phi, null, "nDet");
		}

		// validate optional field 'chi' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset chi = group.getLazyDataset("chi");
				if (chi != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("chi", chi, NX_FLOAT);
			validateFieldUnits("chi", group.getDataNode("chi"), NX_ANGLE);
			validateFieldRank("chi", chi, 1);
			validateFieldDimensions("chi", chi, null, "nDet");
		}

		// validate optional field 'q_parallel' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset q_parallel = group.getLazyDataset("q_parallel");
				if (q_parallel != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("q_parallel", q_parallel, NX_FLOAT);
			validateFieldUnits("q_parallel", group.getDataNode("q_parallel"), NX_ANY);
		}

		// validate optional field 'q_perpendicular' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset q_perpendicular = group.getLazyDataset("q_perpendicular");
				if (q_perpendicular != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("q_perpendicular", q_perpendicular, NX_FLOAT);
			validateFieldUnits("q_perpendicular", group.getDataNode("q_perpendicular"), NX_ANY);
		}

		// validate optional field 'q_norm' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset q_norm = group.getLazyDataset("q_norm");
				if (q_norm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("q_norm", q_norm, NX_FLOAT);
			validateFieldUnits("q_norm", group.getDataNode("q_norm"), NX_ANY);
		}
	}

	/**
	 * Validate optional group 'q_data' of type NXdata.
	 */
	private void validateGroup_NXentry_q_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("q_data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'q' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset q = group.getLazyDataset("q");
				if (q != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("q", q, NX_FLOAT);
		}

		// validate optional field 'intensity' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset intensity = group.getLazyDataset("intensity");
				if (intensity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("intensity", intensity, NX_FLOAT);
		}

		// validate optional field 'q_parallel' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset q_parallel = group.getLazyDataset("q_parallel");
				if (q_parallel != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("q_parallel", q_parallel, NX_FLOAT);
		}

		// validate optional field 'q_perpendicular' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset q_perpendicular = group.getLazyDataset("q_perpendicular");
				if (q_perpendicular != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("q_perpendicular", q_perpendicular, NX_FLOAT);
		}
	}

	/**
	 * Validate optional unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'sample_mode' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset sample_mode = group.getLazyDataset("sample_mode");
		validateFieldNotNull("sample_mode", sample_mode);
		if (sample_mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_mode", sample_mode, NX_CHAR);
		}

		// validate field 'sample_id' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset sample_id = group.getLazyDataset("sample_id");
		validateFieldNotNull("sample_id", sample_id);
		if (sample_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_id", sample_id, NX_CHAR);
		}

		// validate field 'sample_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset sample_name = group.getLazyDataset("sample_name");
		validateFieldNotNull("sample_name", sample_name);
		if (sample_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_name", sample_name, NX_CHAR);
		}
	}
}
