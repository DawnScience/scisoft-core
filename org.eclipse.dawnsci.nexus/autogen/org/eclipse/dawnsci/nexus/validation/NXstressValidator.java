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
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXtransformations;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXparameters;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXreflections;

/**
 * Validator for the application definition 'NXstress'.
 */
public class NXstressValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXstressValidator() {
		super(NexusApplicationDefinition.NX_STRESS);
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
					"NXstress");
		}

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate optional field 'experiment_identifier' of type NX_CHAR.
		final ILazyDataset experiment_identifier = group.getLazyDataset("experiment_identifier");
				if (experiment_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_identifier", experiment_identifier, NX_CHAR);
		}

		// validate optional field 'experiment_description' of type NX_CHAR.
		final ILazyDataset experiment_description = group.getLazyDataset("experiment_description");
				if (experiment_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_description", experiment_description, NX_CHAR);
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

		// validate optional field 'collection_identifier' of type NX_CHAR.
		final ILazyDataset collection_identifier = group.getLazyDataset("collection_identifier");
				if (collection_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("collection_identifier", collection_identifier, NX_CHAR);
		}

		// validate optional field 'collection_description' of type NX_CHAR.
		final ILazyDataset collection_description = group.getLazyDataset("collection_description");
				if (collection_description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("collection_description", collection_description, NX_CHAR);
		}

		// validate field 'processing_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset processing_type = group.getLazyDataset("processing_type");
		validateFieldNotNull("processing_type", processing_type);
		if (processing_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("processing_type", processing_type, NX_CHAR);
			validateFieldEnumeration("processing_type", processing_type,
					"two-theta",
					"energy",
					"d-spacing",
					"time-of-flight",
					"sin2psi");
		}

		// validate optional field 'measurement_direction' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset measurement_direction = group.getLazyDataset("measurement_direction");
				if (measurement_direction != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("measurement_direction", measurement_direction, NX_CHAR);
			validateFieldEnumeration("measurement_direction", measurement_direction,
					"radial",
					"longitudinal",
					"normal",
					"tangential",
					"multiple");
		}

		// validate optional child group 'experiment_responsible' of type NXuser
		if (group.getUser("experiment_responsible") != null) {
			validateGroup_NXentry_experiment_responsible(group.getUser("experiment_responsible"));
		}

		// validate child group 'instrument' of type NXinstrument
		validateGroup_NXentry_instrument(group.getInstrument());

		// validate child group 'SAMPLE_DESCRIPTION' of type NXsample
		validateGroup_NXentry_SAMPLE_DESCRIPTION(group.getSample("SAMPLE_DESCRIPTION"));

		// validate child group 'FIT' of type NXprocess
		validateGroup_NXentry_FIT(group.getProcess("FIT"));

		// validate optional child group 'NOTES' of type NXnote
		if (group.getChild("NOTES", NXnote.class) != null) {
			validateGroup_NXentry_NOTES(group.getChild("NOTES", NXnote.class));
		}

		// validate child group 'peaks' of type NXreflections
		validateGroup_NXentry_peaks(group.getChild("peaks", NXreflections.class));
	}

	/**
	 * Validate optional group 'experiment_responsible' of type NXuser.
	 */
	private void validateGroup_NXentry_experiment_responsible(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("experiment_responsible", NXuser.class, group))) return;

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'role' of type NX_CHAR.
		final ILazyDataset role = group.getLazyDataset("role");
				if (role != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("role", role, NX_CHAR);
		}
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_NXentry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		// validate optional attribute 'short_name' of field 'name' of type NX_CHAR.
		final Attribute name_attr_short_name = group.getDataNode("name").getAttribute("short_name");
		if (name_attr_short_name != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("short_name", name_attr_short_name, NX_CHAR);
		}

		}

		// validate optional child group 'CALIBRATION' of type NXnote
		if (group.getChild("CALIBRATION", NXnote.class) != null) {
			validateGroup_NXentry_instrument_CALIBRATION(group.getChild("CALIBRATION", NXnote.class));
		}

		// validate child group 'SOURCE' of type NXsource
		validateGroup_NXentry_instrument_SOURCE(group.getSource("SOURCE"));

		// validate child group 'DETECTOR' of type NXdetector
		validateGroup_NXentry_instrument_DETECTOR(group.getDetector("DETECTOR"));

		// validate child group 'beam_intensity_profile' of type NXbeam
		validateGroup_NXentry_instrument_beam_intensity_profile(group.getBeam("beam_intensity_profile"));
	}

	/**
	 * Validate optional group 'CALIBRATION' of type NXnote.
	 */
	private void validateGroup_NXentry_instrument_CALIBRATION(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("CALIBRATION", NXnote.class, group))) return;

		// validate optional field 'calibration_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset calibration_type = group.getLazyDataset("calibration_type");
				if (calibration_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("calibration_type", calibration_type, NX_CHAR);
		}

		// validate optional field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
				if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate optional field 'data' of type NX_BINARY.
		final ILazyDataset data = group.getLazyDataset("data");
				if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_BINARY);
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate optional field 'author' of type NX_CHAR.
		final ILazyDataset author = group.getLazyDataset("author");
				if (author != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("author", author, NX_CHAR);
		}

		// validate optional field 'date' of type NX_DATE_TIME.
		final ILazyDataset date = group.getLazyDataset("date");
				if (date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("date", date, NX_DATE_TIME);
		}
	}

	/**
	 * Validate group 'SOURCE' of type NXsource.
	 */
	private void validateGroup_NXentry_instrument_SOURCE(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("SOURCE", NXsource.class, group))) return;

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
					"Rotating Anode X-ray",
					"Fixed Tube X-ray",
					"Metal Jet X-ray");
		}

		// validate field 'probe' of type NX_CHAR.
		final ILazyDataset probe = group.getLazyDataset("probe");
		validateFieldNotNull("probe", probe);
		if (probe != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("probe", probe, NX_CHAR);
			validateFieldEnumeration("probe", probe,
					"neutron",
					"X-ray");
		}
	}

	/**
	 * Validate group 'DETECTOR' of type NXdetector.
	 */
	private void validateGroup_NXentry_instrument_DETECTOR(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("DETECTOR", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate optional field 'distance' of type NX_NUMBER.
		final ILazyDataset distance = group.getLazyDataset("distance");
				if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_NUMBER);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 3);
			validateFieldDimensions("distance", distance, null, "nP", "i", "j");
		}

		// validate optional field 'efficiency' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset efficiency = group.getLazyDataset("efficiency");
				if (efficiency != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("efficiency", efficiency, NX_FLOAT);
			validateFieldUnits("efficiency", group.getDataNode("efficiency"), NX_DIMENSIONLESS);
			validateFieldRank("efficiency", efficiency, 2);
			validateFieldDimensions("efficiency", efficiency, null, "i", "j");
		}

		// validate optional field 'wavelength' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
				if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_FLOAT);
			validateFieldUnits("wavelength", group.getDataNode("wavelength"), NX_WAVELENGTH);
			validateFieldRank("wavelength", wavelength, 2);
			validateFieldDimensions("wavelength", wavelength, null, "i", "j");
		}

		// validate optional field 'dead_time' of type NX_FLOAT.
		final ILazyDataset dead_time = group.getLazyDataset("dead_time");
				if (dead_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dead_time", dead_time, NX_FLOAT);
			validateFieldUnits("dead_time", group.getDataNode("dead_time"), NX_TIME);
			validateFieldRank("dead_time", dead_time, 3);
			validateFieldDimensions("dead_time", dead_time, null, "nP", "i", "j");
		}

		// validate optional field 'count_time' of type NX_NUMBER.
		final ILazyDataset count_time = group.getLazyDataset("count_time");
				if (count_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("count_time", count_time, NX_NUMBER);
			validateFieldUnits("count_time", group.getDataNode("count_time"), NX_TIME);
			validateFieldRank("count_time", count_time, 1);
			validateFieldDimensions("count_time", count_time, null, "nP");
		}

		// validate optional field 'depends_on' of type NX_CHAR.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
				if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate optional unnamed child group of type NXtransformations
		validateUnnamedGroupOccurrences(group, NXtransformations.class, true, false);
		final NXtransformations transformations = getFirstGroupOrNull(group.getChildren(NXtransformations.class));
		if (transformations != null) {
			validateGroup_NXentry_instrument_DETECTOR_NXtransformations(transformations);
		}
	}

	/**
	 * Validate optional unnamed group of type NXtransformations.
	 */
	private void validateGroup_NXentry_instrument_DETECTOR_NXtransformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate group 'beam_intensity_profile' of type NXbeam.
	 */
	private void validateGroup_NXentry_instrument_beam_intensity_profile(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("beam_intensity_profile", NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate optional field 'beam_evaluation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset beam_evaluation = group.getLazyDataset("beam_evaluation");
				if (beam_evaluation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_evaluation", beam_evaluation, NX_CHAR);
		}

		// validate optional field 'primary_vertical_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset primary_vertical_type = group.getLazyDataset("primary_vertical_type");
				if (primary_vertical_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_vertical_type", primary_vertical_type, NX_CHAR);
		}

		// validate optional field 'primary_vertical_source_width' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset primary_vertical_source_width = group.getLazyDataset("primary_vertical_source_width");
				if (primary_vertical_source_width != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_vertical_source_width", primary_vertical_source_width, NX_NUMBER);
		}

		// validate optional field 'primary_vertical_sample_width' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset primary_vertical_sample_width = group.getLazyDataset("primary_vertical_sample_width");
				if (primary_vertical_sample_width != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_vertical_sample_width", primary_vertical_sample_width, NX_NUMBER);
		}

		// validate optional field 'primary_vertical_distance' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset primary_vertical_distance = group.getLazyDataset("primary_vertical_distance");
				if (primary_vertical_distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_vertical_distance", primary_vertical_distance, NX_NUMBER);
		}

		// validate optional field 'primary_vertical_evaluation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset primary_vertical_evaluation = group.getLazyDataset("primary_vertical_evaluation");
				if (primary_vertical_evaluation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_vertical_evaluation", primary_vertical_evaluation, NX_CHAR);
		}

		// validate optional field 'primary_horizontal_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset primary_horizontal_type = group.getLazyDataset("primary_horizontal_type");
				if (primary_horizontal_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_horizontal_type", primary_horizontal_type, NX_CHAR);
		}

		// validate optional field 'primary_horizontal_source_width' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset primary_horizontal_source_width = group.getLazyDataset("primary_horizontal_source_width");
				if (primary_horizontal_source_width != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_horizontal_source_width", primary_horizontal_source_width, NX_NUMBER);
		}

		// validate optional field 'primary_horizontal_sample_width' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset primary_horizontal_sample_width = group.getLazyDataset("primary_horizontal_sample_width");
				if (primary_horizontal_sample_width != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_horizontal_sample_width", primary_horizontal_sample_width, NX_NUMBER);
		}

		// validate optional field 'primary_horizontal_distance' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset primary_horizontal_distance = group.getLazyDataset("primary_horizontal_distance");
				if (primary_horizontal_distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_horizontal_distance", primary_horizontal_distance, NX_NUMBER);
		}

		// validate optional field 'primary_horizontal_evaluation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset primary_horizontal_evaluation = group.getLazyDataset("primary_horizontal_evaluation");
				if (primary_horizontal_evaluation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("primary_horizontal_evaluation", primary_horizontal_evaluation, NX_CHAR);
		}

		// validate optional field 'secondary_horizontal_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset secondary_horizontal_type = group.getLazyDataset("secondary_horizontal_type");
				if (secondary_horizontal_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("secondary_horizontal_type", secondary_horizontal_type, NX_CHAR);
		}

		// validate optional field 'secondary_horizontal_detector_width' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset secondary_horizontal_detector_width = group.getLazyDataset("secondary_horizontal_detector_width");
				if (secondary_horizontal_detector_width != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("secondary_horizontal_detector_width", secondary_horizontal_detector_width, NX_NUMBER);
		}

		// validate optional field 'secondary_horizontal_sample_width' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset secondary_horizontal_sample_width = group.getLazyDataset("secondary_horizontal_sample_width");
				if (secondary_horizontal_sample_width != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("secondary_horizontal_sample_width", secondary_horizontal_sample_width, NX_NUMBER);
		}

		// validate optional field 'secondary_horizontal_distance' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset secondary_horizontal_distance = group.getLazyDataset("secondary_horizontal_distance");
				if (secondary_horizontal_distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("secondary_horizontal_distance", secondary_horizontal_distance, NX_NUMBER);
		}

		// validate optional field 'secondary_horizontal_evaluation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset secondary_horizontal_evaluation = group.getLazyDataset("secondary_horizontal_evaluation");
				if (secondary_horizontal_evaluation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("secondary_horizontal_evaluation", secondary_horizontal_evaluation, NX_CHAR);
		}

		// validate optional field 'incident_energy' of type NX_FLOAT.
		final ILazyDataset incident_energy = group.getLazyDataset("incident_energy");
				if (incident_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_energy", incident_energy, NX_FLOAT);
			validateFieldUnits("incident_energy", group.getDataNode("incident_energy"), NX_ENERGY);
			validateFieldRank("incident_energy", incident_energy, 1);
			validateFieldDimensions("incident_energy", incident_energy, "NXbeam", "m");
		}

		// validate optional field 'incident_wavelength' of type NX_FLOAT.
		final ILazyDataset incident_wavelength = group.getLazyDataset("incident_wavelength");
				if (incident_wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength", incident_wavelength, NX_FLOAT);
			validateFieldUnits("incident_wavelength", group.getDataNode("incident_wavelength"), NX_WAVELENGTH);
		}
	}

	/**
	 * Validate group 'SAMPLE_DESCRIPTION' of type NXsample.
	 */
	private void validateGroup_NXentry_SAMPLE_DESCRIPTION(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("SAMPLE_DESCRIPTION", NXsample.class, group))) return;
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

		// validate optional field 'temperature' of type NX_FLOAT.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
				if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_FLOAT);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldDimensions("temperature", temperature, null, "n_Temp");
		}

		// validate optional field 'stress_field' of type NX_FLOAT.
		final ILazyDataset stress_field = group.getLazyDataset("stress_field");
				if (stress_field != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("stress_field", stress_field, NX_FLOAT);
			validateFieldUnits("stress_field", group.getDataNode("stress_field"), NX_ANY);
			validateFieldDimensions("stress_field", stress_field, null, "n_sField");
		// validate attribute 'direction' of field 'stress_field' of type NX_CHAR.
		final Attribute stress_field_attr_direction = group.getDataNode("stress_field").getAttribute("direction");
		if (!(validateAttributeNotNull("direction", stress_field_attr_direction))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("direction", stress_field_attr_direction, NX_CHAR);
		validateAttributeEnumeration("direction", stress_field_attr_direction,
				"x",
				"y",
				"z");

		}

		// validate optional field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
				if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate optional child group 'gauge_volume' of type NXparameters
		if (group.getChild("gauge_volume", NXparameters.class) != null) {
			validateGroup_NXentry_SAMPLE_DESCRIPTION_gauge_volume(group.getChild("gauge_volume", NXparameters.class));
		}

		// validate optional unnamed child group of type NXtransformations
		validateUnnamedGroupOccurrences(group, NXtransformations.class, true, false);
		final NXtransformations transformations = getFirstGroupOrNull(group.getChildren(NXtransformations.class));
		if (transformations != null) {
			validateGroup_NXentry_SAMPLE_DESCRIPTION_NXtransformations(transformations);
		}
	}

	/**
	 * Validate optional group 'gauge_volume' of type NXparameters.
	 */
	private void validateGroup_NXentry_SAMPLE_DESCRIPTION_gauge_volume(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("gauge_volume", NXparameters.class, group))) return;

		// validate optional field 'a' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset a = group.getLazyDataset("a");
				if (a != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("a", a, NX_FLOAT);
			validateFieldUnits("a", group.getDataNode("a"), NX_LENGTH);
		}

		// validate optional field 'b' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset b = group.getLazyDataset("b");
				if (b != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("b", b, NX_FLOAT);
			validateFieldUnits("b", group.getDataNode("b"), NX_LENGTH);
		}

		// validate optional field 'c' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset c = group.getLazyDataset("c");
				if (c != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("c", c, NX_FLOAT);
			validateFieldUnits("c", group.getDataNode("c"), NX_LENGTH);
		}

		// validate optional field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
				if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate optional unnamed child group of type NXtransformations
		validateUnnamedGroupOccurrences(group, NXtransformations.class, true, false);
		final NXtransformations transformations = getFirstGroupOrNull(group.getChildren(NXtransformations.class));
		if (transformations != null) {
			validateGroup_NXentry_SAMPLE_DESCRIPTION_gauge_volume_NXtransformations(transformations);
		}
	}

	/**
	 * Validate optional unnamed group of type NXtransformations.
	 */
	private void validateGroup_NXentry_SAMPLE_DESCRIPTION_gauge_volume_NXtransformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXtransformations.
	 */
	private void validateGroup_NXentry_SAMPLE_DESCRIPTION_NXtransformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate group 'FIT' of type NXprocess.
	 */
	private void validateGroup_NXentry_FIT(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("FIT", NXprocess.class, group))) return;

		// validate field 'raw_data_file' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset raw_data_file = group.getLazyDataset("raw_data_file");
		validateFieldNotNull("raw_data_file", raw_data_file);
		if (raw_data_file != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("raw_data_file", raw_data_file, NX_CHAR);
		}

		// validate field 'date' of type NX_DATE_TIME.
		final ILazyDataset date = group.getLazyDataset("date");
		validateFieldNotNull("date", date);
		if (date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("date", date, NX_DATE_TIME);
		}

		// validate field 'program' of type NX_CHAR.
		final ILazyDataset program = group.getLazyDataset("program");
		validateFieldNotNull("program", program);
		if (program != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("program", program, NX_CHAR);
		}

		// validate optional field 'integration_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset integration_type = group.getLazyDataset("integration_type");
				if (integration_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("integration_type", integration_type, NX_CHAR);
		}

		// validate optional field 'bins' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset bins = group.getLazyDataset("bins");
				if (bins != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("bins", bins, NX_CHAR);
		}

		// validate optional field 'fit_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset fit_type = group.getLazyDataset("fit_type");
				if (fit_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fit_type", fit_type, NX_CHAR);
		}

		// validate optional field 'fit_range' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset fit_range = group.getLazyDataset("fit_range");
				if (fit_range != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fit_range", fit_range, NX_CHAR);
		}

		// validate optional field 'goodness_of_fit' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset goodness_of_fit = group.getLazyDataset("goodness_of_fit");
				if (goodness_of_fit != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("goodness_of_fit", goodness_of_fit, NX_CHAR);
		}

		// validate optional field 'normalization' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset normalization = group.getLazyDataset("normalization");
				if (normalization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("normalization", normalization, NX_CHAR);
		}

		// validate optional child group 'data_reduction_responsible' of type NXuser
		if (group.getChild("data_reduction_responsible", NXuser.class) != null) {
			validateGroup_NXentry_FIT_data_reduction_responsible(group.getChild("data_reduction_responsible", NXuser.class));
		}

		// validate child group 'DESCRIPTION' of type NXnote
		validateGroup_NXentry_FIT_DESCRIPTION(group.getNote("DESCRIPTION"));

		// validate child group 'peak_parameters' of type NXparameters
		validateGroup_NXentry_FIT_peak_parameters(group.getParameters("peak_parameters"));

		// validate child group 'background_parameters' of type NXparameters
		validateGroup_NXentry_FIT_background_parameters(group.getParameters("background_parameters"));

		// validate child group 'DIFFRACTOGRAM' of type NXdata
		validateGroup_NXentry_FIT_DIFFRACTOGRAM(group.getData("DIFFRACTOGRAM"));
	}

	/**
	 * Validate optional group 'data_reduction_responsible' of type NXuser.
	 */
	private void validateGroup_NXentry_FIT_data_reduction_responsible(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data_reduction_responsible", NXuser.class, group))) return;

		// validate optional field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'role' of type NX_CHAR.
		final ILazyDataset role = group.getLazyDataset("role");
				if (role != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("role", role, NX_CHAR);
		}
	}

	/**
	 * Validate group 'DESCRIPTION' of type NXnote.
	 */
	private void validateGroup_NXentry_FIT_DESCRIPTION(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("DESCRIPTION", NXnote.class, group))) return;

	}

	/**
	 * Validate group 'peak_parameters' of type NXparameters.
	 */
	private void validateGroup_NXentry_FIT_peak_parameters(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("peak_parameters", NXparameters.class, group))) return;

		// validate field 'title' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
			validateFieldEnumeration("title", title,
					"gaussian",
					"lorentzian",
					"voigt",
					"pseudo-voigt",
					"split pseudo-voigt",
					"pearson VII");
		}

		// validate optional field 'area' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset area = group.getLazyDataset("area");
				if (area != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("area", area, NX_NUMBER);
			validateFieldUnits("area", group.getDataNode("area"), NX_ANY);
			validateFieldRank("area", area, 1);
			validateFieldDimensions("area", area, null, "n_Peaks");
		// validate attribute 'units' of field 'area' of type NX_CHAR.
		final Attribute area_attr_units = group.getDataNode("area").getAttribute("units");
		if (!(validateAttributeNotNull("units", area_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", area_attr_units, NX_CHAR);

		}

		// validate optional field 'area_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset area_errors = group.getLazyDataset("area_errors");
				if (area_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("area_errors", area_errors, NX_NUMBER);
			validateFieldUnits("area_errors", group.getDataNode("area_errors"), NX_DIMENSIONLESS);
			validateFieldRank("area_errors", area_errors, 1);
			validateFieldDimensions("area_errors", area_errors, null, "n_Peaks");
		}

		// validate field 'center' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset center = group.getLazyDataset("center");
		validateFieldNotNull("center", center);
		if (center != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center", center, NX_NUMBER);
			validateFieldUnits("center", group.getDataNode("center"), NX_ANY);
			validateFieldRank("center", center, 1);
			validateFieldDimensions("center", center, null, "n_Peaks");
		// validate attribute 'units' of field 'center' of type NX_CHAR.
		final Attribute center_attr_units = group.getDataNode("center").getAttribute("units");
		if (!(validateAttributeNotNull("units", center_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", center_attr_units, NX_CHAR);

		}

		// validate optional field 'center_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset center_errors = group.getLazyDataset("center_errors");
				if (center_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center_errors", center_errors, NX_NUMBER);
			validateFieldUnits("center_errors", group.getDataNode("center_errors"), NX_DIMENSIONLESS);
			validateFieldRank("center_errors", center_errors, 1);
			validateFieldDimensions("center_errors", center_errors, null, "n_Peaks");
		}

		// validate optional field 'height' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset height = group.getLazyDataset("height");
				if (height != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("height", height, NX_NUMBER);
			validateFieldUnits("height", group.getDataNode("height"), NX_ANY);
			validateFieldRank("height", height, 1);
			validateFieldDimensions("height", height, null, "n_Peaks");
		// validate attribute 'units' of field 'height' of type NX_CHAR.
		final Attribute height_attr_units = group.getDataNode("height").getAttribute("units");
		if (!(validateAttributeNotNull("units", height_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", height_attr_units, NX_CHAR);

		}

		// validate optional field 'height_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset height_errors = group.getLazyDataset("height_errors");
				if (height_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("height_errors", height_errors, NX_NUMBER);
			validateFieldUnits("height_errors", group.getDataNode("height_errors"), NX_DIMENSIONLESS);
			validateFieldRank("height_errors", height_errors, 1);
			validateFieldDimensions("height_errors", height_errors, null, "n_Peaks");
		}

		// validate optional field 'fwhm' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset fwhm = group.getLazyDataset("fwhm");
				if (fwhm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fwhm", fwhm, NX_NUMBER);
			validateFieldUnits("fwhm", group.getDataNode("fwhm"), NX_ANY);
			validateFieldRank("fwhm", fwhm, 1);
			validateFieldDimensions("fwhm", fwhm, null, "n_Peaks");
		// validate attribute 'units' of field 'fwhm' of type NX_CHAR.
		final Attribute fwhm_attr_units = group.getDataNode("fwhm").getAttribute("units");
		if (!(validateAttributeNotNull("units", fwhm_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", fwhm_attr_units, NX_CHAR);

		}

		// validate optional field 'fwhm_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset fwhm_errors = group.getLazyDataset("fwhm_errors");
				if (fwhm_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fwhm_errors", fwhm_errors, NX_NUMBER);
			validateFieldUnits("fwhm_errors", group.getDataNode("fwhm_errors"), NX_DIMENSIONLESS);
			validateFieldRank("fwhm_errors", fwhm_errors, 1);
			validateFieldDimensions("fwhm_errors", fwhm_errors, null, "n_Peaks");
		}

		// validate optional field 'fwhm_left' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset fwhm_left = group.getLazyDataset("fwhm_left");
				if (fwhm_left != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fwhm_left", fwhm_left, NX_NUMBER);
			validateFieldUnits("fwhm_left", group.getDataNode("fwhm_left"), NX_ANY);
			validateFieldRank("fwhm_left", fwhm_left, 1);
			validateFieldDimensions("fwhm_left", fwhm_left, null, "n_Peaks");
		// validate attribute 'units' of field 'fwhm_left' of type NX_CHAR.
		final Attribute fwhm_left_attr_units = group.getDataNode("fwhm_left").getAttribute("units");
		if (!(validateAttributeNotNull("units", fwhm_left_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", fwhm_left_attr_units, NX_CHAR);

		}

		// validate optional field 'fwhm_left_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset fwhm_left_errors = group.getLazyDataset("fwhm_left_errors");
				if (fwhm_left_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fwhm_left_errors", fwhm_left_errors, NX_NUMBER);
			validateFieldUnits("fwhm_left_errors", group.getDataNode("fwhm_left_errors"), NX_DIMENSIONLESS);
			validateFieldRank("fwhm_left_errors", fwhm_left_errors, 1);
			validateFieldDimensions("fwhm_left_errors", fwhm_left_errors, null, "n_Peaks");
		}

		// validate optional field 'fwhm_right' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset fwhm_right = group.getLazyDataset("fwhm_right");
				if (fwhm_right != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fwhm_right", fwhm_right, NX_NUMBER);
			validateFieldUnits("fwhm_right", group.getDataNode("fwhm_right"), NX_ANY);
			validateFieldRank("fwhm_right", fwhm_right, 1);
			validateFieldDimensions("fwhm_right", fwhm_right, null, "n_Peaks");
		// validate attribute 'units' of field 'fwhm_right' of type NX_CHAR.
		final Attribute fwhm_right_attr_units = group.getDataNode("fwhm_right").getAttribute("units");
		if (!(validateAttributeNotNull("units", fwhm_right_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", fwhm_right_attr_units, NX_CHAR);

		}

		// validate optional field 'fwhm_right_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset fwhm_right_errors = group.getLazyDataset("fwhm_right_errors");
				if (fwhm_right_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fwhm_right_errors", fwhm_right_errors, NX_NUMBER);
			validateFieldUnits("fwhm_right_errors", group.getDataNode("fwhm_right_errors"), NX_DIMENSIONLESS);
			validateFieldRank("fwhm_right_errors", fwhm_right_errors, 1);
			validateFieldDimensions("fwhm_right_errors", fwhm_right_errors, null, "n_Peaks");
		}

		// validate optional field 'form_factor' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset form_factor = group.getLazyDataset("form_factor");
				if (form_factor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("form_factor", form_factor, NX_NUMBER);
			validateFieldUnits("form_factor", group.getDataNode("form_factor"), NX_DIMENSIONLESS);
			validateFieldRank("form_factor", form_factor, 1);
			validateFieldDimensions("form_factor", form_factor, null, "n_Peaks");
		}

		// validate optional field 'form_factor_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset form_factor_errors = group.getLazyDataset("form_factor_errors");
				if (form_factor_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("form_factor_errors", form_factor_errors, NX_NUMBER);
			validateFieldUnits("form_factor_errors", group.getDataNode("form_factor_errors"), NX_DIMENSIONLESS);
			validateFieldRank("form_factor_errors", form_factor_errors, 1);
			validateFieldDimensions("form_factor_errors", form_factor_errors, null, "n_Peaks");
		}

		// validate optional field 'azimuth' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset azimuth = group.getLazyDataset("azimuth");
				if (azimuth != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("azimuth", azimuth, NX_NUMBER);
			validateFieldUnits("azimuth", group.getDataNode("azimuth"), NX_ANGLE);
			validateFieldRank("azimuth", azimuth, 1);
			validateFieldDimensions("azimuth", azimuth, null, "n_Peaks");
		}
	}

	/**
	 * Validate group 'background_parameters' of type NXparameters.
	 */
	private void validateGroup_NXentry_FIT_background_parameters(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("background_parameters", NXparameters.class, group))) return;

		// validate field 'title' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate optional field 'A' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset A = group.getLazyDataset("A");
				if (A != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("A", A, NX_NUMBER);
			validateFieldUnits("A", group.getDataNode("A"), NX_DIMENSIONLESS);
			validateFieldRank("A", A, 1);
			validateFieldDimensions("A", A, null, "n_Peaks");
		}

		// validate optional field 'as' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset as = group.getLazyDataset("as");
				if (as != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("as", as, NX_NUMBER);
			validateFieldUnits("as", group.getDataNode("as"), NX_DIMENSIONLESS);
			validateFieldRank("as", as, 1);
			validateFieldDimensions("as", as, null, "n_Peaks");
		}

		// validate optional field 'as_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset as_errors = group.getLazyDataset("as_errors");
				if (as_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("as_errors", as_errors, NX_NUMBER);
			validateFieldUnits("as_errors", group.getDataNode("as_errors"), NX_DIMENSIONLESS);
			validateFieldRank("as_errors", as_errors, 1);
			validateFieldDimensions("as_errors", as_errors, null, "n_Peaks");
		}

		// validate optional field 'b' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset b = group.getLazyDataset("b");
				if (b != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("b", b, NX_NUMBER);
			validateFieldUnits("b", group.getDataNode("b"), NX_DIMENSIONLESS);
			validateFieldRank("b", b, 1);
			validateFieldDimensions("b", b, null, "n_Peaks");
		}

		// validate optional field 'b_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset b_errors = group.getLazyDataset("b_errors");
				if (b_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("b_errors", b_errors, NX_NUMBER);
			validateFieldUnits("b_errors", group.getDataNode("b_errors"), NX_DIMENSIONLESS);
			validateFieldRank("b_errors", b_errors, 1);
			validateFieldDimensions("b_errors", b_errors, null, "n_Peaks");
		}

		// validate optional field 'o' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset o = group.getLazyDataset("o");
				if (o != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("o", o, NX_NUMBER);
			validateFieldUnits("o", group.getDataNode("o"), NX_DIMENSIONLESS);
			validateFieldRank("o", o, 1);
			validateFieldDimensions("o", o, null, "n_Peaks");
		}

		// validate optional field 'o_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset o_errors = group.getLazyDataset("o_errors");
				if (o_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("o_errors", o_errors, NX_NUMBER);
			validateFieldUnits("o_errors", group.getDataNode("o_errors"), NX_DIMENSIONLESS);
			validateFieldRank("o_errors", o_errors, 1);
			validateFieldDimensions("o_errors", o_errors, null, "n_Peaks");
		}

		// validate optional field 'background_area' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset background_area = group.getLazyDataset("background_area");
				if (background_area != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("background_area", background_area, NX_NUMBER);
			validateFieldUnits("background_area", group.getDataNode("background_area"), NX_ANY);
			validateFieldRank("background_area", background_area, 1);
			validateFieldDimensions("background_area", background_area, null, "n_Peaks");
		// validate attribute 'units' of field 'background_area' of type NX_CHAR.
		final Attribute background_area_attr_units = group.getDataNode("background_area").getAttribute("units");
		if (!(validateAttributeNotNull("units", background_area_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", background_area_attr_units, NX_CHAR);

		}

		// validate optional field 'background_area_interval' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset background_area_interval = group.getLazyDataset("background_area_interval");
				if (background_area_interval != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("background_area_interval", background_area_interval, NX_NUMBER);
			validateFieldUnits("background_area_interval", group.getDataNode("background_area_interval"), NX_DIMENSIONLESS);
		}
	}

	/**
	 * Validate group 'DIFFRACTOGRAM' of type NXdata.
	 */
	private void validateGroup_NXentry_FIT_DIFFRACTOGRAM(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("DIFFRACTOGRAM", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate optional field 'DAXIS' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset DAXIS = group.getLazyDataset("DAXIS");
				if (DAXIS != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("DAXIS", DAXIS, NX_CHAR);
			validateFieldRank("DAXIS", DAXIS, 1);
			validateFieldDimensions("DAXIS", DAXIS, null, "n_D");
		}

		// validate field 'XAXIS' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset XAXIS = group.getLazyDataset("XAXIS");
		validateFieldNotNull("XAXIS", XAXIS);
		if (XAXIS != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("XAXIS", XAXIS, NX_NUMBER);
			validateFieldUnits("XAXIS", group.getDataNode("XAXIS"), NX_ANY);
			validateFieldRank("XAXIS", XAXIS, 1);
			validateFieldDimensions("XAXIS", XAXIS, null, "n_X");
		// validate attribute 'units' of field 'XAXIS' of type NX_CHAR.
		final Attribute XAXIS_attr_units = group.getDataNode("XAXIS").getAttribute("units");
		if (!(validateAttributeNotNull("units", XAXIS_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", XAXIS_attr_units, NX_CHAR);

		}

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"diffractogram");

		// validate attribute 'auxiliary_signals' of type NX_CHAR.
		final Attribute auxiliary_signals_attr = group.getAttribute("auxiliary_signals");
		if (!(validateAttributeNotNull("auxiliary_signals", auxiliary_signals_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("auxiliary_signals", auxiliary_signals_attr, NX_CHAR);

		// validate field 'diffractogram' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset diffractogram = group.getLazyDataset("diffractogram");
		validateFieldNotNull("diffractogram", diffractogram);
		if (diffractogram != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("diffractogram", diffractogram, NX_NUMBER);
			validateFieldUnits("diffractogram", group.getDataNode("diffractogram"), NX_ANY);
			validateFieldRank("diffractogram", diffractogram, 2);
			validateFieldDimensions("diffractogram", diffractogram, null, "n_D", "n_X");
		// validate attribute 'interpretation' of field 'diffractogram' of type NX_CHAR.
		final Attribute diffractogram_attr_interpretation = group.getDataNode("diffractogram").getAttribute("interpretation");
		if (!(validateAttributeNotNull("interpretation", diffractogram_attr_interpretation))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("interpretation", diffractogram_attr_interpretation, NX_CHAR);
		validateAttributeEnumeration("interpretation", diffractogram_attr_interpretation,
				"spectrum");

		// validate attribute 'units' of field 'diffractogram' of type NX_CHAR.
		final Attribute diffractogram_attr_units = group.getDataNode("diffractogram").getAttribute("units");
		if (!(validateAttributeNotNull("units", diffractogram_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", diffractogram_attr_units, NX_CHAR);

		}

		// validate field 'diffractogram_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset diffractogram_errors = group.getLazyDataset("diffractogram_errors");
		validateFieldNotNull("diffractogram_errors", diffractogram_errors);
		if (diffractogram_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("diffractogram_errors", diffractogram_errors, NX_NUMBER);
			validateFieldUnits("diffractogram_errors", group.getDataNode("diffractogram_errors"), NX_ANY);
			validateFieldRank("diffractogram_errors", diffractogram_errors, 2);
			validateFieldDimensions("diffractogram_errors", diffractogram_errors, null, "n_D", "n_X");
		// validate attribute 'interpretation' of field 'diffractogram_errors' of type NX_CHAR.
		final Attribute diffractogram_errors_attr_interpretation = group.getDataNode("diffractogram_errors").getAttribute("interpretation");
		if (!(validateAttributeNotNull("interpretation", diffractogram_errors_attr_interpretation))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("interpretation", diffractogram_errors_attr_interpretation, NX_CHAR);
		validateAttributeEnumeration("interpretation", diffractogram_errors_attr_interpretation,
				"spectrum");

		// validate attribute 'units' of field 'diffractogram_errors' of type NX_CHAR.
		final Attribute diffractogram_errors_attr_units = group.getDataNode("diffractogram_errors").getAttribute("units");
		if (!(validateAttributeNotNull("units", diffractogram_errors_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", diffractogram_errors_attr_units, NX_CHAR);

		}

		// validate field 'fit' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset fit = group.getLazyDataset("fit");
		validateFieldNotNull("fit", fit);
		if (fit != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fit", fit, NX_NUMBER);
			validateFieldRank("fit", fit, 2);
			validateFieldDimensions("fit", fit, null, "n_D", "n_X");
		// validate attribute 'interpretation' of field 'fit' of type NX_CHAR.
		final Attribute fit_attr_interpretation = group.getDataNode("fit").getAttribute("interpretation");
		if (!(validateAttributeNotNull("interpretation", fit_attr_interpretation))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("interpretation", fit_attr_interpretation, NX_CHAR);
		validateAttributeEnumeration("interpretation", fit_attr_interpretation,
				"spectrum");

		}

		// validate field 'fit_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset fit_errors = group.getLazyDataset("fit_errors");
		validateFieldNotNull("fit_errors", fit_errors);
		if (fit_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("fit_errors", fit_errors, NX_NUMBER);
			validateFieldRank("fit_errors", fit_errors, 2);
			validateFieldDimensions("fit_errors", fit_errors, null, "n_D", "n_X");
		}

		// validate optional field 'background' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset background = group.getLazyDataset("background");
				if (background != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("background", background, NX_NUMBER);
			validateFieldRank("background", background, 2);
			validateFieldDimensions("background", background, null, "n_D", "n_X");
		// validate attribute 'interpretation' of field 'background' of type NX_CHAR.
		final Attribute background_attr_interpretation = group.getDataNode("background").getAttribute("interpretation");
		if (!(validateAttributeNotNull("interpretation", background_attr_interpretation))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("interpretation", background_attr_interpretation, NX_CHAR);
		validateAttributeEnumeration("interpretation", background_attr_interpretation,
				"spectrum");

		}

		// validate optional field 'residuals' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset residuals = group.getLazyDataset("residuals");
				if (residuals != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("residuals", residuals, NX_NUMBER);
			validateFieldRank("residuals", residuals, 2);
			validateFieldDimensions("residuals", residuals, null, "n_D", "n_X");
		// validate attribute 'interpretation' of field 'residuals' of type NX_CHAR.
		final Attribute residuals_attr_interpretation = group.getDataNode("residuals").getAttribute("interpretation");
		if (!(validateAttributeNotNull("interpretation", residuals_attr_interpretation))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("interpretation", residuals_attr_interpretation, NX_CHAR);
		validateAttributeEnumeration("interpretation", residuals_attr_interpretation,
				"spectrum");

		}
	}

	/**
	 * Validate optional group 'NOTES' of type NXnote.
	 */
	private void validateGroup_NXentry_NOTES(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("NOTES", NXnote.class, group))) return;

	}

	/**
	 * Validate group 'peaks' of type NXreflections.
	 */
	private void validateGroup_NXentry_peaks(final NXreflections group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("peaks", NXreflections.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'h' of type NX_INT.
		final ILazyDataset h = group.getLazyDataset("h");
		validateFieldNotNull("h", h);
		if (h != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("h", h, NX_INT);
			validateFieldUnits("h", group.getDataNode("h"), NX_UNITLESS);
			validateFieldRank("h", h, 1);
			validateFieldDimensions("h", h, null, "n_Peaks");
		}

		// validate field 'k' of type NX_INT.
		final ILazyDataset k = group.getLazyDataset("k");
		validateFieldNotNull("k", k);
		if (k != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("k", k, NX_INT);
			validateFieldUnits("k", group.getDataNode("k"), NX_UNITLESS);
			validateFieldRank("k", k, 1);
			validateFieldDimensions("k", k, null, "n_Peaks");
		}

		// validate field 'l' of type NX_INT.
		final ILazyDataset l = group.getLazyDataset("l");
		validateFieldNotNull("l", l);
		if (l != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("l", l, NX_INT);
			validateFieldUnits("l", group.getDataNode("l"), NX_UNITLESS);
			validateFieldRank("l", l, 1);
			validateFieldDimensions("l", l, null, "n_Peaks");
		}

		// validate optional field 'lattice' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset lattice = group.getLazyDataset("lattice");
				if (lattice != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("lattice", lattice, NX_CHAR);
			validateFieldRank("lattice", lattice, 1);
			validateFieldDimensions("lattice", lattice, null, "n_Peaks");
		}

		// validate optional field 'space_group' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset space_group = group.getLazyDataset("space_group");
				if (space_group != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("space_group", space_group, NX_CHAR);
			validateFieldRank("space_group", space_group, 1);
			validateFieldDimensions("space_group", space_group, null, "n_Peaks");
		}

		// validate field 'phase_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset phase_name = group.getLazyDataset("phase_name");
		validateFieldNotNull("phase_name", phase_name);
		if (phase_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("phase_name", phase_name, NX_CHAR);
			validateFieldRank("phase_name", phase_name, 1);
			validateFieldDimensions("phase_name", phase_name, null, "n_Peaks");
		}

		// validate field 'qx' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset qx = group.getLazyDataset("qx");
		validateFieldNotNull("qx", qx);
		if (qx != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("qx", qx, NX_NUMBER);
			validateFieldUnits("qx", group.getDataNode("qx"), NX_DIMENSIONLESS);
			validateFieldRank("qx", qx, 1);
			validateFieldDimensions("qx", qx, null, "n_Peaks");
		}

		// validate field 'qy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset qy = group.getLazyDataset("qy");
		validateFieldNotNull("qy", qy);
		if (qy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("qy", qy, NX_NUMBER);
			validateFieldUnits("qy", group.getDataNode("qy"), NX_DIMENSIONLESS);
			validateFieldRank("qy", qy, 1);
			validateFieldDimensions("qy", qy, null, "n_Peaks");
		}

		// validate field 'qz' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset qz = group.getLazyDataset("qz");
		validateFieldNotNull("qz", qz);
		if (qz != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("qz", qz, NX_NUMBER);
			validateFieldUnits("qz", group.getDataNode("qz"), NX_DIMENSIONLESS);
			validateFieldRank("qz", qz, 1);
			validateFieldDimensions("qz", qz, null, "n_Peaks");
		}

		// validate field 'center' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset center = group.getLazyDataset("center");
		validateFieldNotNull("center", center);
		if (center != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center", center, NX_NUMBER);
			validateFieldUnits("center", group.getDataNode("center"), NX_ANY);
			validateFieldRank("center", center, 1);
			validateFieldDimensions("center", center, null, "n_Peaks");
		// validate attribute 'units' of field 'center' of type NX_CHAR.
		final Attribute center_attr_units = group.getDataNode("center").getAttribute("units");
		if (!(validateAttributeNotNull("units", center_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", center_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", center_attr_units,
				"degrees",
				"keV",
				"1/angstrom",
				"angstrom",
				"microseconds",
				"''");

		}

		// validate field 'center_errors' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset center_errors = group.getLazyDataset("center_errors");
		validateFieldNotNull("center_errors", center_errors);
		if (center_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center_errors", center_errors, NX_NUMBER);
			validateFieldUnits("center_errors", group.getDataNode("center_errors"), NX_ANY);
			validateFieldRank("center_errors", center_errors, 1);
			validateFieldDimensions("center_errors", center_errors, null, "n_Peaks");
		// validate attribute 'units' of field 'center_errors' of type NX_CHAR.
		final Attribute center_errors_attr_units = group.getDataNode("center_errors").getAttribute("units");
		if (!(validateAttributeNotNull("units", center_errors_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", center_errors_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", center_errors_attr_units,
				"degrees",
				"keV",
				"1/angstrom",
				"angstrom",
				"microseconds",
				"''");

		}

		// validate field 'center_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset center_type = group.getLazyDataset("center_type");
		validateFieldNotNull("center_type", center_type);
		if (center_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("center_type", center_type, NX_CHAR);
			validateFieldEnumeration("center_type", center_type,
					"two-theta",
					"energy",
					"momentum-transfer",
					"d-spacing",
					"channel",
					"time-of-flight");
		}

		// validate field 'sx' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset sx = group.getLazyDataset("sx");
		validateFieldNotNull("sx", sx);
		if (sx != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sx", sx, NX_NUMBER);
			validateFieldUnits("sx", group.getDataNode("sx"), NX_LENGTH);
			validateFieldRank("sx", sx, 1);
			validateFieldDimensions("sx", sx, null, "n_Peaks");
		}

		// validate field 'sy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset sy = group.getLazyDataset("sy");
		validateFieldNotNull("sy", sy);
		if (sy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sy", sy, NX_NUMBER);
			validateFieldUnits("sy", group.getDataNode("sy"), NX_LENGTH);
			validateFieldRank("sy", sy, 1);
			validateFieldDimensions("sy", sy, null, "n_Peaks");
		}

		// validate field 'sz' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset sz = group.getLazyDataset("sz");
		validateFieldNotNull("sz", sz);
		if (sz != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sz", sz, NX_NUMBER);
			validateFieldUnits("sz", group.getDataNode("sz"), NX_LENGTH);
			validateFieldRank("sz", sz, 1);
			validateFieldDimensions("sz", sz, null, "n_Peaks");
		}
	}
}
