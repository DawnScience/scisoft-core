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
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXpositioner;
import org.eclipse.dawnsci.nexus.NXprocess;

/**
 * Validator for the application definition 'NXxpcs'.
 */
public class NXxpcsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXxpcsValidator() {
		super(NexusApplicationDefinition.NX_XPCS);
	}

	@Override
	public ValidationReport validate(NXroot root) {
		// validate unnamed child group of type NXentry (possibly multiple)
		validateUnnamedGroupOccurrences(root, NXentry.class, false, true);
		final Map<String, NXentry> allEntry = root.getAllEntry();
		for (final NXentry entry : allEntry.values()) {
			validateGroup_NXentry(entry);
		}

		// validate unnamed child group of type NXprocess (possibly multiple)
		validateUnnamedGroupOccurrences(root, NXprocess.class, false, true);
		final Map<String, NXprocess> allProcess = root.getChildren(NXprocess.class);
		for (final NXprocess process : allProcess.values()) {
			validateGroup_NXprocess(process);
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
					"NXxpcs");
		}

		// validate field 'entry_identifier' of type NX_CHAR.
		final ILazyDataset entry_identifier = group.getLazyDataset("entry_identifier");
		validateFieldNotNull("entry_identifier", entry_identifier);
		if (entry_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("entry_identifier", entry_identifier, NX_CHAR);
		}

		// validate optional field 'entry_identifier_uuid' of type NX_CHAR.
		final ILazyDataset entry_identifier_uuid = group.getLazyDataset("entry_identifier_uuid");
				if (entry_identifier_uuid != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("entry_identifier_uuid", entry_identifier_uuid, NX_CHAR);
		}

		// validate field 'scan_number' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset scan_number = group.getLazyDataset("scan_number");
		validateFieldNotNull("scan_number", scan_number);
		if (scan_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scan_number", scan_number, NX_INT);
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

		// validate child group 'data' of type NXdata
		validateGroup_NXentry_data(group.getData());

		// validate optional child group 'twotime' of type NXdata
		if (group.getData("twotime") != null) {
			validateGroup_NXentry_twotime(group.getData("twotime"));
		}

		// validate child group 'instrument' of type NXinstrument
		validateGroup_NXentry_instrument(group.getInstrument());

		// validate optional child group 'sample' of type NXsample
		if (group.getSample() != null) {
			validateGroup_NXentry_sample(group.getSample());
		}

		// validate optional child group 'NOTE' of type NXnote
		if (group.getChild("NOTE", NXnote.class) != null) {
			validateGroup_NXentry_NOTE(group.getChild("NOTE", NXnote.class));
		}
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_NXentry_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'frame_sum' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset frame_sum = group.getLazyDataset("frame_sum");
				if (frame_sum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("frame_sum", frame_sum, NX_NUMBER);
			validateFieldUnits("frame_sum", group.getDataNode("frame_sum"), NX_COUNT);
		}

		// validate optional field 'frame_average' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset frame_average = group.getLazyDataset("frame_average");
				if (frame_average != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("frame_average", frame_average, NX_NUMBER);
			validateFieldUnits("frame_average", group.getDataNode("frame_average"), NX_COUNT);
		}

		// validate optional field 'g2' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset g2 = group.getLazyDataset("g2");
				if (g2 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("g2", g2, NX_NUMBER);
			validateFieldUnits("g2", group.getDataNode("g2"), NX_DIMENSIONLESS);
		// validate attribute 'storage_mode' of field 'g2' of type NX_CHAR.
		final Attribute g2_attr_storage_mode = group.getDataNode("g2").getAttribute("storage_mode");
		if (!(validateAttributeNotNull("storage_mode", g2_attr_storage_mode))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("storage_mode", g2_attr_storage_mode, NX_CHAR);
		validateAttributeEnumeration("storage_mode", g2_attr_storage_mode,
				"one_array",
				"data_exchange_keys",
				"other");

		}

		// validate optional field 'g2_derr' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset g2_derr = group.getLazyDataset("g2_derr");
				if (g2_derr != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("g2_derr", g2_derr, NX_NUMBER);
			validateFieldUnits("g2_derr", group.getDataNode("g2_derr"), NX_DIMENSIONLESS);
		// validate attribute 'storage_mode' of field 'g2_derr' of type NX_CHAR.
		final Attribute g2_derr_attr_storage_mode = group.getDataNode("g2_derr").getAttribute("storage_mode");
		if (!(validateAttributeNotNull("storage_mode", g2_derr_attr_storage_mode))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("storage_mode", g2_derr_attr_storage_mode, NX_CHAR);
		validateAttributeEnumeration("storage_mode", g2_derr_attr_storage_mode,
				"one_array",
				"data_exchange_keys",
				"other");

		}

		// validate optional field 'G2_unnormalized' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset G2_unnormalized = group.getLazyDataset("G2_unnormalized");
				if (G2_unnormalized != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("G2_unnormalized", G2_unnormalized, NX_NUMBER);
			validateFieldUnits("G2_unnormalized", group.getDataNode("G2_unnormalized"), NX_ANY);
		// validate attribute 'storage_mode' of field 'G2_unnormalized' of type NX_CHAR.
		final Attribute G2_unnormalized_attr_storage_mode = group.getDataNode("G2_unnormalized").getAttribute("storage_mode");
		if (!(validateAttributeNotNull("storage_mode", G2_unnormalized_attr_storage_mode))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("storage_mode", G2_unnormalized_attr_storage_mode, NX_CHAR);
		validateAttributeEnumeration("storage_mode", G2_unnormalized_attr_storage_mode,
				"one_array",
				"data_exchange_keys",
				"other");

		}

		// validate optional field 'delay_difference' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset delay_difference = group.getLazyDataset("delay_difference");
				if (delay_difference != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("delay_difference", delay_difference, NX_INT);
			validateFieldUnits("delay_difference", group.getDataNode("delay_difference"), NX_COUNT);
		// validate attribute 'storage_mode' of field 'delay_difference' of type NX_CHAR.
		final Attribute delay_difference_attr_storage_mode = group.getDataNode("delay_difference").getAttribute("storage_mode");
		if (!(validateAttributeNotNull("storage_mode", delay_difference_attr_storage_mode))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("storage_mode", delay_difference_attr_storage_mode, NX_CHAR);
		validateAttributeEnumeration("storage_mode", delay_difference_attr_storage_mode,
				"one_array",
				"data_exchange_keys",
				"other");

		}
	}

	/**
	 * Validate optional group 'twotime' of type NXdata.
	 */
	private void validateGroup_NXentry_twotime(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("twotime", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'two_time_corr_func' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset two_time_corr_func = group.getLazyDataset("two_time_corr_func");
				if (two_time_corr_func != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("two_time_corr_func", two_time_corr_func, NX_NUMBER);
			validateFieldUnits("two_time_corr_func", group.getDataNode("two_time_corr_func"), NX_ANY);
		// validate attribute 'storage_mode' of field 'two_time_corr_func' of type NX_CHAR.
		final Attribute two_time_corr_func_attr_storage_mode = group.getDataNode("two_time_corr_func").getAttribute("storage_mode");
		if (!(validateAttributeNotNull("storage_mode", two_time_corr_func_attr_storage_mode))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("storage_mode", two_time_corr_func_attr_storage_mode, NX_CHAR);
		validateAttributeEnumeration("storage_mode", two_time_corr_func_attr_storage_mode,
				"one_array_q_first",
				"one_array_q_last",
				"data_exchange_keys",
				"other");

		// validate attribute 'baseline_reference' of field 'two_time_corr_func' of type NX_INT.
		final Attribute two_time_corr_func_attr_baseline_reference = group.getDataNode("two_time_corr_func").getAttribute("baseline_reference");
		if (!(validateAttributeNotNull("baseline_reference", two_time_corr_func_attr_baseline_reference))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("baseline_reference", two_time_corr_func_attr_baseline_reference, NX_INT);
		validateAttributeEnumeration("baseline_reference", two_time_corr_func_attr_baseline_reference,
				"0",
				"1");

		// validate attribute 'time_origin_location' of field 'two_time_corr_func' of type NX_CHAR.
		final Attribute two_time_corr_func_attr_time_origin_location = group.getDataNode("two_time_corr_func").getAttribute("time_origin_location");
		if (!(validateAttributeNotNull("time_origin_location", two_time_corr_func_attr_time_origin_location))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("time_origin_location", two_time_corr_func_attr_time_origin_location, NX_CHAR);
		validateAttributeEnumeration("time_origin_location", two_time_corr_func_attr_time_origin_location,
				"upper_left",
				"lower_left");

		// validate attribute 'populated_elements' of field 'two_time_corr_func' of type NX_CHAR.
		final Attribute two_time_corr_func_attr_populated_elements = group.getDataNode("two_time_corr_func").getAttribute("populated_elements");
		if (!(validateAttributeNotNull("populated_elements", two_time_corr_func_attr_populated_elements))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("populated_elements", two_time_corr_func_attr_populated_elements, NX_CHAR);
		validateAttributeEnumeration("populated_elements", two_time_corr_func_attr_populated_elements,
				"all",
				"upper_half",
				"lower_half");

		}

		// validate optional field 'g2_from_two_time_corr_func' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset g2_from_two_time_corr_func = group.getLazyDataset("g2_from_two_time_corr_func");
				if (g2_from_two_time_corr_func != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("g2_from_two_time_corr_func", g2_from_two_time_corr_func, NX_NUMBER);
			validateFieldUnits("g2_from_two_time_corr_func", group.getDataNode("g2_from_two_time_corr_func"), NX_DIMENSIONLESS);
		// validate attribute 'storage_mode' of field 'g2_from_two_time_corr_func' of type NX_CHAR.
		final Attribute g2_from_two_time_corr_func_attr_storage_mode = group.getDataNode("g2_from_two_time_corr_func").getAttribute("storage_mode");
		if (!(validateAttributeNotNull("storage_mode", g2_from_two_time_corr_func_attr_storage_mode))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("storage_mode", g2_from_two_time_corr_func_attr_storage_mode, NX_CHAR);
		validateAttributeEnumeration("storage_mode", g2_from_two_time_corr_func_attr_storage_mode,
				"one_array_q_first",
				"one_array_q_last",
				"data_exchange_keys",
				"other");

		// validate attribute 'baseline_reference' of field 'g2_from_two_time_corr_func' of type NX_INT.
		final Attribute g2_from_two_time_corr_func_attr_baseline_reference = group.getDataNode("g2_from_two_time_corr_func").getAttribute("baseline_reference");
		if (!(validateAttributeNotNull("baseline_reference", g2_from_two_time_corr_func_attr_baseline_reference))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("baseline_reference", g2_from_two_time_corr_func_attr_baseline_reference, NX_INT);
		validateAttributeEnumeration("baseline_reference", g2_from_two_time_corr_func_attr_baseline_reference,
				"0",
				"1");

		// validate attribute 'first_point_for_fit' of field 'g2_from_two_time_corr_func' of type NX_INT.
		final Attribute g2_from_two_time_corr_func_attr_first_point_for_fit = group.getDataNode("g2_from_two_time_corr_func").getAttribute("first_point_for_fit");
		if (!(validateAttributeNotNull("first_point_for_fit", g2_from_two_time_corr_func_attr_first_point_for_fit))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("first_point_for_fit", g2_from_two_time_corr_func_attr_first_point_for_fit, NX_INT);
		validateAttributeEnumeration("first_point_for_fit", g2_from_two_time_corr_func_attr_first_point_for_fit,
				"0",
				"1");

		}

		// validate optional field 'g2_err_from_two_time_corr_func' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset g2_err_from_two_time_corr_func = group.getLazyDataset("g2_err_from_two_time_corr_func");
				if (g2_err_from_two_time_corr_func != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("g2_err_from_two_time_corr_func", g2_err_from_two_time_corr_func, NX_NUMBER);
			validateFieldUnits("g2_err_from_two_time_corr_func", group.getDataNode("g2_err_from_two_time_corr_func"), NX_DIMENSIONLESS);
		// validate attribute 'storage_mode' of field 'g2_err_from_two_time_corr_func' of type NX_CHAR.
		final Attribute g2_err_from_two_time_corr_func_attr_storage_mode = group.getDataNode("g2_err_from_two_time_corr_func").getAttribute("storage_mode");
		if (!(validateAttributeNotNull("storage_mode", g2_err_from_two_time_corr_func_attr_storage_mode))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("storage_mode", g2_err_from_two_time_corr_func_attr_storage_mode, NX_CHAR);
		validateAttributeEnumeration("storage_mode", g2_err_from_two_time_corr_func_attr_storage_mode,
				"one_array_q_first",
				"one_array_q_last",
				"data_exchange_keys",
				"other");

		}

		// validate optional field 'g2_from_two_time_corr_func_partials' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset g2_from_two_time_corr_func_partials = group.getLazyDataset("g2_from_two_time_corr_func_partials");
				if (g2_from_two_time_corr_func_partials != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("g2_from_two_time_corr_func_partials", g2_from_two_time_corr_func_partials, NX_NUMBER);
			validateFieldUnits("g2_from_two_time_corr_func_partials", group.getDataNode("g2_from_two_time_corr_func_partials"), NX_DIMENSIONLESS);
		// validate attribute 'storage_mode' of field 'g2_from_two_time_corr_func_partials' of type NX_CHAR.
		final Attribute g2_from_two_time_corr_func_partials_attr_storage_mode = group.getDataNode("g2_from_two_time_corr_func_partials").getAttribute("storage_mode");
		if (!(validateAttributeNotNull("storage_mode", g2_from_two_time_corr_func_partials_attr_storage_mode))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("storage_mode", g2_from_two_time_corr_func_partials_attr_storage_mode, NX_CHAR);
		validateAttributeEnumeration("storage_mode", g2_from_two_time_corr_func_partials_attr_storage_mode,
				"one_array",
				"data_exchange_keys",
				"other");

		// validate attribute 'baseline_reference' of field 'g2_from_two_time_corr_func_partials' of type NX_INT.
		final Attribute g2_from_two_time_corr_func_partials_attr_baseline_reference = group.getDataNode("g2_from_two_time_corr_func_partials").getAttribute("baseline_reference");
		if (!(validateAttributeNotNull("baseline_reference", g2_from_two_time_corr_func_partials_attr_baseline_reference))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("baseline_reference", g2_from_two_time_corr_func_partials_attr_baseline_reference, NX_INT);
		validateAttributeEnumeration("baseline_reference", g2_from_two_time_corr_func_partials_attr_baseline_reference,
				"0",
				"1");

		}

		// validate optional field 'g2_err_from_two_time_corr_func_partials' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset g2_err_from_two_time_corr_func_partials = group.getLazyDataset("g2_err_from_two_time_corr_func_partials");
				if (g2_err_from_two_time_corr_func_partials != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("g2_err_from_two_time_corr_func_partials", g2_err_from_two_time_corr_func_partials, NX_NUMBER);
			validateFieldUnits("g2_err_from_two_time_corr_func_partials", group.getDataNode("g2_err_from_two_time_corr_func_partials"), NX_DIMENSIONLESS);
		}
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_NXentry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate child group 'incident_beam' of type NXbeam
		validateGroup_NXentry_instrument_incident_beam(group.getBeam("incident_beam"));

		// validate unnamed child group of type NXdetector (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector.class, false, true);
		final Map<String, NXdetector> allDetector = group.getAllDetector();
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_NXentry_instrument_NXdetector(detector);
		}

		// validate optional child group 'masks' of type NXnote
		if (group.getChild("masks", NXnote.class) != null) {
			validateGroup_NXentry_instrument_masks(group.getChild("masks", NXnote.class));
		}
	}

	/**
	 * Validate group 'incident_beam' of type NXbeam.
	 */
	private void validateGroup_NXentry_instrument_incident_beam(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("incident_beam", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

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

		// validate optional field 'incident_energy_spread' of type NX_FLOAT.
		final ILazyDataset incident_energy_spread = group.getLazyDataset("incident_energy_spread");
				if (incident_energy_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_energy_spread", incident_energy_spread, NX_FLOAT);
			validateFieldUnits("incident_energy_spread", group.getDataNode("incident_energy_spread"), NX_ENERGY);
		}

		// validate optional field 'incident_polarization_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset incident_polarization_type = group.getLazyDataset("incident_polarization_type");
				if (incident_polarization_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_polarization_type", incident_polarization_type, NX_CHAR);
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
	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_NXentry_instrument_NXdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'distance' of type NX_NUMBER.
		final ILazyDataset distance = group.getLazyDataset("distance");
				if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_NUMBER);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 3);
			validateFieldDimensions("distance", distance, "NXdetector", "nP", "i", "j");
		}

		// validate field 'count_time' of type NX_NUMBER.
		final ILazyDataset count_time = group.getLazyDataset("count_time");
		validateFieldNotNull("count_time", count_time);
		if (count_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("count_time", count_time, NX_NUMBER);
			validateFieldUnits("count_time", group.getDataNode("count_time"), NX_TIME);
			validateFieldRank("count_time", count_time, 1);
			validateFieldDimensions("count_time", count_time, "NXdetector", "nP");
		}

		// validate field 'frame_time' of type NX_NUMBER.
		final ILazyDataset frame_time = group.getLazyDataset("frame_time");
		validateFieldNotNull("frame_time", frame_time);
		if (frame_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("frame_time", frame_time, NX_NUMBER);
			validateFieldUnits("frame_time", group.getDataNode("frame_time"), NX_TIME);
			validateFieldRank("frame_time", frame_time, 1);
			validateFieldDimensions("frame_time", frame_time, "NXdetector", "nP");
		}

		// validate field 'beam_center_x' of type NX_NUMBER.
		final ILazyDataset beam_center_x = group.getLazyDataset("beam_center_x");
		validateFieldNotNull("beam_center_x", beam_center_x);
		if (beam_center_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_x", beam_center_x, NX_NUMBER);
			validateFieldUnits("beam_center_x", group.getDataNode("beam_center_x"), NX_LENGTH);
		}

		// validate field 'beam_center_y' of type NX_NUMBER.
		final ILazyDataset beam_center_y = group.getLazyDataset("beam_center_y");
		validateFieldNotNull("beam_center_y", beam_center_y);
		if (beam_center_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_y", beam_center_y, NX_NUMBER);
			validateFieldUnits("beam_center_y", group.getDataNode("beam_center_y"), NX_LENGTH);
		}

		// validate optional field 'x_pixel_size' of type NX_NUMBER.
		final ILazyDataset x_pixel_size = group.getLazyDataset("x_pixel_size");
				if (x_pixel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_pixel_size", x_pixel_size, NX_NUMBER);
			validateFieldUnits("x_pixel_size", group.getDataNode("x_pixel_size"), NX_LENGTH);
			validateFieldRank("x_pixel_size", x_pixel_size, 2);
			validateFieldDimensions("x_pixel_size", x_pixel_size, "NXdetector", "i", "j");
		}

		// validate optional field 'y_pixel_size' of type NX_NUMBER.
		final ILazyDataset y_pixel_size = group.getLazyDataset("y_pixel_size");
				if (y_pixel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_pixel_size", y_pixel_size, NX_NUMBER);
			validateFieldUnits("y_pixel_size", group.getDataNode("y_pixel_size"), NX_LENGTH);
			validateFieldRank("y_pixel_size", y_pixel_size, 2);
			validateFieldDimensions("y_pixel_size", y_pixel_size, "NXdetector", "i", "j");
		}
	}

	/**
	 * Validate optional group 'masks' of type NXnote.
	 */
	private void validateGroup_NXentry_instrument_masks(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("masks", NXnote.class, group))) return;

		// validate field 'dynamic_roi_map' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset dynamic_roi_map = group.getLazyDataset("dynamic_roi_map");
		validateFieldNotNull("dynamic_roi_map", dynamic_roi_map);
		if (dynamic_roi_map != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dynamic_roi_map", dynamic_roi_map, NX_NUMBER);
			validateFieldUnits("dynamic_roi_map", group.getDataNode("dynamic_roi_map"), NX_DIMENSIONLESS);
		}

		// validate optional field 'dynamic_q_list' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset dynamic_q_list = group.getLazyDataset("dynamic_q_list");
				if (dynamic_q_list != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dynamic_q_list", dynamic_q_list, NX_NUMBER);
			validateFieldUnits("dynamic_q_list", group.getDataNode("dynamic_q_list"), NX_PER_LENGTH);
		}

		// validate optional field 'dynamic_phi_list' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset dynamic_phi_list = group.getLazyDataset("dynamic_phi_list");
				if (dynamic_phi_list != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dynamic_phi_list", dynamic_phi_list, NX_NUMBER);
			validateFieldUnits("dynamic_phi_list", group.getDataNode("dynamic_phi_list"), NX_PER_LENGTH);
		}

		// validate optional field 'static_roi_map' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset static_roi_map = group.getLazyDataset("static_roi_map");
				if (static_roi_map != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("static_roi_map", static_roi_map, NX_NUMBER);
			validateFieldUnits("static_roi_map", group.getDataNode("static_roi_map"), NX_DIMENSIONLESS);
		}

		// validate optional field 'static_q_list' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset static_q_list = group.getLazyDataset("static_q_list");
				if (static_q_list != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("static_q_list", static_q_list, NX_NUMBER);
			validateFieldUnits("static_q_list", group.getDataNode("static_q_list"), NX_PER_LENGTH);
		}
	}

	/**
	 * Validate optional group 'sample' of type NXsample.
	 */
	private void validateGroup_NXentry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'temperature_set' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset temperature_set = group.getLazyDataset("temperature_set");
				if (temperature_set != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature_set", temperature_set, NX_NUMBER);
			validateFieldUnits("temperature_set", group.getDataNode("temperature_set"), NX_TEMPERATURE);
		}

		// validate optional field 'temperature' of type NX_NUMBER.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
				if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_NUMBER);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");
		}

		// validate optional child group 'position_x' of type NXpositioner
		if (group.getPositioner("position_x") != null) {
			validateGroup_NXentry_sample_position_x(group.getPositioner("position_x"));
		}

		// validate optional child group 'position_y' of type NXpositioner
		if (group.getPositioner("position_y") != null) {
			validateGroup_NXentry_sample_position_y(group.getPositioner("position_y"));
		}

		// validate optional child group 'position_z' of type NXpositioner
		if (group.getPositioner("position_z") != null) {
			validateGroup_NXentry_sample_position_z(group.getPositioner("position_z"));
		}
	}

	/**
	 * Validate optional group 'position_x' of type NXpositioner.
	 */
	private void validateGroup_NXentry_sample_position_x(final NXpositioner group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("position_x", NXpositioner.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'position_y' of type NXpositioner.
	 */
	private void validateGroup_NXentry_sample_position_y(final NXpositioner group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("position_y", NXpositioner.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'position_z' of type NXpositioner.
	 */
	private void validateGroup_NXentry_sample_position_z(final NXpositioner group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("position_z", NXpositioner.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'NOTE' of type NXnote.
	 */
	private void validateGroup_NXentry_NOTE(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("NOTE", NXnote.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXprocess.
	 */
	private void validateGroup_NXprocess(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprocess.class, group))) return;

	}
}
