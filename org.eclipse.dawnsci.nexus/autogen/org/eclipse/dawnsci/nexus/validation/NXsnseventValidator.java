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
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXlog;
import org.eclipse.dawnsci.nexus.NXpositioner;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXevent_data;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXgeometry;
import org.eclipse.dawnsci.nexus.NXorientation;
import org.eclipse.dawnsci.nexus.NXshape;
import org.eclipse.dawnsci.nexus.NXtranslation;
import org.eclipse.dawnsci.nexus.NXdisk_chopper;
import org.eclipse.dawnsci.nexus.NXmoderator;
import org.eclipse.dawnsci.nexus.NXaperture;
import org.eclipse.dawnsci.nexus.NXattenuator;
import org.eclipse.dawnsci.nexus.NXpolarizer;
import org.eclipse.dawnsci.nexus.NXcrystal;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXuser;

/**
 * Validator for the application definition 'NXsnsevent'.
 */
public class NXsnseventValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXsnseventValidator() {
		super(NexusApplicationDefinition.NX_SNSEVENT);
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

		// validate field 'collection_identifier' of type NX_CHAR.
		final ILazyDataset collection_identifier = group.getLazyDataset("collection_identifier");
		validateFieldNotNull("collection_identifier", collection_identifier);
		if (collection_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("collection_identifier", collection_identifier, NX_CHAR);
		}

		// validate field 'collection_title' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset collection_title = group.getLazyDataset("collection_title");
		validateFieldNotNull("collection_title", collection_title);
		if (collection_title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("collection_title", collection_title, NX_CHAR);
		}

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXsnsevent");
		}

		// validate field 'duration' of type NX_FLOAT.
		final ILazyDataset duration = group.getLazyDataset("duration");
		validateFieldNotNull("duration", duration);
		if (duration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("duration", duration, NX_FLOAT);
			validateFieldUnits("duration", group.getDataNode("duration"), NX_TIME);
		}

		// validate field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
		validateFieldNotNull("end_time", end_time);
		if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
		}

		// validate field 'entry_identifier' of type NX_CHAR.
		final ILazyDataset entry_identifier = group.getLazyDataset("entry_identifier");
		validateFieldNotNull("entry_identifier", entry_identifier);
		if (entry_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("entry_identifier", entry_identifier, NX_CHAR);
		}

		// validate field 'experiment_identifier' of type NX_CHAR.
		final ILazyDataset experiment_identifier = group.getLazyDataset("experiment_identifier");
		validateFieldNotNull("experiment_identifier", experiment_identifier);
		if (experiment_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("experiment_identifier", experiment_identifier, NX_CHAR);
		}

		// validate field 'notes' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset notes = group.getLazyDataset("notes");
		validateFieldNotNull("notes", notes);
		if (notes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("notes", notes, NX_CHAR);
		}

		// validate field 'proton_charge' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset proton_charge = group.getLazyDataset("proton_charge");
		validateFieldNotNull("proton_charge", proton_charge);
		if (proton_charge != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("proton_charge", proton_charge, NX_FLOAT);
			validateFieldUnits("proton_charge", group.getDataNode("proton_charge"), NX_CHARGE);
		}

		// validate field 'raw_frames' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset raw_frames = group.getLazyDataset("raw_frames");
		validateFieldNotNull("raw_frames", raw_frames);
		if (raw_frames != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("raw_frames", raw_frames, NX_INT);
		}

		// validate field 'run_number' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset run_number = group.getLazyDataset("run_number");
		validateFieldNotNull("run_number", run_number);
		if (run_number != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("run_number", run_number, NX_CHAR);
		}

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		validateFieldNotNull("start_time", start_time);
		if (start_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start_time", start_time, NX_DATE_TIME);
		}

		// validate field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'total_counts' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset total_counts = group.getLazyDataset("total_counts");
		validateFieldNotNull("total_counts", total_counts);
		if (total_counts != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_counts", total_counts, NX_UINT);
			validateFieldUnits("total_counts", group.getDataNode("total_counts"), NX_UNITLESS);
		}

		// validate field 'total_uncounted_counts' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset total_uncounted_counts = group.getLazyDataset("total_uncounted_counts");
		validateFieldNotNull("total_uncounted_counts", total_uncounted_counts);
		if (total_uncounted_counts != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_uncounted_counts", total_uncounted_counts, NX_UINT);
			validateFieldUnits("total_uncounted_counts", group.getDataNode("total_uncounted_counts"), NX_UNITLESS);
		}

		// validate child group 'DASlogs' of type NXcollection
		validateGroup_NXentry_DASlogs(group.getCollection("DASlogs"));

		// validate child group 'SNSHistoTool' of type NXnote
		validateGroup_NXentry_SNSHistoTool(group.getChild("SNSHistoTool", NXnote.class));

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}

		// validate unnamed child group of type NXevent_data (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXevent_data.class, false, true);
		final Map<String, NXevent_data> allEvent_data = group.getChildren(NXevent_data.class);
		for (final NXevent_data event_data : allEvent_data.values()) {
			validateGroup_NXentry_NXevent_data(event_data);
		}

		// validate child group 'instrument' of type NXinstrument
		validateGroup_NXentry_instrument(group.getInstrument());

		// validate unnamed child group of type NXmonitor (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmonitor.class, false, true);
		final Map<String, NXmonitor> allMonitor = group.getAllMonitor();
		for (final NXmonitor monitor : allMonitor.values()) {
			validateGroup_NXentry_NXmonitor(monitor);
		}

		// validate child group 'sample' of type NXsample
		validateGroup_NXentry_sample(group.getSample());

		// validate unnamed child group of type NXuser (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXuser.class, false, true);
		final Map<String, NXuser> allUser = group.getAllUser();
		for (final NXuser user : allUser.values()) {
			validateGroup_NXentry_NXuser(user);
		}
	}

	/**
	 * Validate group 'DASlogs' of type NXcollection.
	 */
	private void validateGroup_NXentry_DASlogs(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("DASlogs", NXcollection.class, group))) return;

		// validate unnamed child group of type NXlog (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXlog.class, false, true);
		final Map<String, NXlog> allLog = group.getChildren(NXlog.class);
		for (final NXlog log : allLog.values()) {
			validateGroup_NXentry_DASlogs_NXlog(log);
		}

		// validate unnamed child group of type NXpositioner (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXpositioner.class, false, true);
		final Map<String, NXpositioner> allPositioner = group.getChildren(NXpositioner.class);
		for (final NXpositioner positioner : allPositioner.values()) {
			validateGroup_NXentry_DASlogs_NXpositioner(positioner);
		}
	}

	/**
	 * Validate unnamed group of type NXlog.
	 */
	private void validateGroup_NXentry_DASlogs_NXlog(final NXlog group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXlog.class, group))) return;

		// validate field 'average_value' of type NX_FLOAT.
		final ILazyDataset average_value = group.getLazyDataset("average_value");
		validateFieldNotNull("average_value", average_value);
		if (average_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("average_value", average_value, NX_FLOAT);
			validateFieldUnits("average_value", group.getDataNode("average_value"), NX_ANY);
		}

		// validate optional field 'average_value_error' of type NX_FLOAT.
		final ILazyDataset average_value_error = group.getLazyDataset("average_value_error");
				if (average_value_error != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("average_value_error", average_value_error, NX_FLOAT);
			validateFieldUnits("average_value_error", group.getDataNode("average_value_error"), NX_ANY);
		}

		// validate field 'average_value_errors' of type NX_FLOAT.
		final ILazyDataset average_value_errors = group.getLazyDataset("average_value_errors");
		validateFieldNotNull("average_value_errors", average_value_errors);
		if (average_value_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("average_value_errors", average_value_errors, NX_FLOAT);
			validateFieldUnits("average_value_errors", group.getDataNode("average_value_errors"), NX_ANY);
		}

		// validate field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate field 'duration' of type NX_FLOAT.
		final ILazyDataset duration = group.getLazyDataset("duration");
		validateFieldNotNull("duration", duration);
		if (duration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("duration", duration, NX_FLOAT);
			validateFieldUnits("duration", group.getDataNode("duration"), NX_ANY);
		}

		// validate field 'maximum_value' of type NX_FLOAT.
		final ILazyDataset maximum_value = group.getLazyDataset("maximum_value");
		validateFieldNotNull("maximum_value", maximum_value);
		if (maximum_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("maximum_value", maximum_value, NX_FLOAT);
			validateFieldUnits("maximum_value", group.getDataNode("maximum_value"), NX_ANY);
		}

		// validate field 'minimum_value' of type NX_FLOAT.
		final ILazyDataset minimum_value = group.getLazyDataset("minimum_value");
		validateFieldNotNull("minimum_value", minimum_value);
		if (minimum_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("minimum_value", minimum_value, NX_FLOAT);
			validateFieldUnits("minimum_value", group.getDataNode("minimum_value"), NX_ANY);
		}

		// validate field 'time' of type NX_FLOAT.
		final ILazyDataset time = group.getLazyDataset("time");
		validateFieldNotNull("time", time);
		if (time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time", time, NX_FLOAT);
			validateFieldUnits("time", group.getDataNode("time"), NX_TIME);
			validateFieldRank("time", time, 1);
			validateFieldDimensions("time", time, null, "nvalue");
		}

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_ANY);
			validateFieldRank("value", value, 1);
			validateFieldDimensions("value", value, null, "nvalue");
		}
	}

	/**
	 * Validate optional unnamed group of type NXpositioner.
	 */
	private void validateGroup_NXentry_DASlogs_NXpositioner(final NXpositioner group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXpositioner.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'average_value' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset average_value = group.getLazyDataset("average_value");
		validateFieldNotNull("average_value", average_value);
		if (average_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("average_value", average_value, NX_FLOAT);
		}

		// validate optional field 'average_value_error' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset average_value_error = group.getLazyDataset("average_value_error");
				if (average_value_error != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("average_value_error", average_value_error, NX_FLOAT);
		}

		// validate field 'average_value_errors' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset average_value_errors = group.getLazyDataset("average_value_errors");
		validateFieldNotNull("average_value_errors", average_value_errors);
		if (average_value_errors != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("average_value_errors", average_value_errors, NX_FLOAT);
		}

		// validate field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate field 'duration' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset duration = group.getLazyDataset("duration");
		validateFieldNotNull("duration", duration);
		if (duration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("duration", duration, NX_FLOAT);
		}

		// validate field 'maximum_value' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset maximum_value = group.getLazyDataset("maximum_value");
		validateFieldNotNull("maximum_value", maximum_value);
		if (maximum_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("maximum_value", maximum_value, NX_FLOAT);
		}

		// validate field 'minimum_value' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset minimum_value = group.getLazyDataset("minimum_value");
		validateFieldNotNull("minimum_value", minimum_value);
		if (minimum_value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("minimum_value", minimum_value, NX_FLOAT);
		}

		// validate field 'time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset time = group.getLazyDataset("time");
		validateFieldNotNull("time", time);
		if (time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time", time, NX_FLOAT);
			validateFieldRank("time", time, 1);
			validateFieldDimensions("time", time, null, "numvalue");
		}

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_ANY);
			validateFieldRank("value", value, 1);
			validateFieldDimensions("value", value, null, "numvalue");
		}
	}

	/**
	 * Validate group 'SNSHistoTool' of type NXnote.
	 */
	private void validateGroup_NXentry_SNSHistoTool(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("SNSHistoTool", NXnote.class, group))) return;

		// validate field 'SNSbanking_file_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset SNSbanking_file_name = group.getLazyDataset("SNSbanking_file_name");
		validateFieldNotNull("SNSbanking_file_name", SNSbanking_file_name);
		if (SNSbanking_file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("SNSbanking_file_name", SNSbanking_file_name, NX_CHAR);
		}

		// validate field 'SNSmapping_file_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset SNSmapping_file_name = group.getLazyDataset("SNSmapping_file_name");
		validateFieldNotNull("SNSmapping_file_name", SNSmapping_file_name);
		if (SNSmapping_file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("SNSmapping_file_name", SNSmapping_file_name, NX_CHAR);
		}

		// validate field 'author' of type NX_CHAR.
		final ILazyDataset author = group.getLazyDataset("author");
		validateFieldNotNull("author", author);
		if (author != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("author", author, NX_CHAR);
		}

		// validate field 'command1' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset command1 = group.getLazyDataset("command1");
		validateFieldNotNull("command1", command1);
		if (command1 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("command1", command1, NX_CHAR);
		}

		// validate field 'date' of type NX_DATE_TIME.
		final ILazyDataset date = group.getLazyDataset("date");
		validateFieldNotNull("date", date);
		if (date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("date", date, NX_DATE_TIME);
		}

		// validate field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate field 'version' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset version = group.getLazyDataset("version");
		validateFieldNotNull("version", version);
		if (version != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("version", version, NX_CHAR);
		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'data_x_y' to location '/NXentry/NXinstrument/NXdetector/data_x_y
		final DataNode data_x_y = group.getDataNode("data_x_y");
		validateDataNodeLink("data_x_y", data_x_y, "/NXentry/NXinstrument/NXdetector/data_x_y");

		// validate link 'x_pixel_offset' to location '/NXentry/NXinstrument/NXdetector/x_pixel_offset
		final DataNode x_pixel_offset = group.getDataNode("x_pixel_offset");
		validateDataNodeLink("x_pixel_offset", x_pixel_offset, "/NXentry/NXinstrument/NXdetector/x_pixel_offset");

		// validate link 'y_pixel_offset' to location '/NXentry/NXinstrument/NXdetector/y_pixel_offset
		final DataNode y_pixel_offset = group.getDataNode("y_pixel_offset");
		validateDataNodeLink("y_pixel_offset", y_pixel_offset, "/NXentry/NXinstrument/NXdetector/y_pixel_offset");

	}

	/**
	 * Validate unnamed group of type NXevent_data.
	 */
	private void validateGroup_NXentry_NXevent_data(final NXevent_data group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXevent_data.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'event_index' to location '/NXentry/NXinstrument/NXdetector/event_index
		final DataNode event_index = group.getDataNode("event_index");
		validateDataNodeLink("event_index", event_index, "/NXentry/NXinstrument/NXdetector/event_index");

		// validate link 'event_pixel_id' to location '/NXentry/NXinstrument/NXdetector/event_pixel_id
		final DataNode event_pixel_id = group.getDataNode("event_pixel_id");
		validateDataNodeLink("event_pixel_id", event_pixel_id, "/NXentry/NXinstrument/NXdetector/event_pixel_id");

		// validate link 'event_time_of_flight' to location '/NXentry/NXinstrument/NXdetector/event_time_of_flight
		final DataNode event_time_of_flight = group.getDataNode("event_time_of_flight");
		validateDataNodeLink("event_time_of_flight", event_time_of_flight, "/NXentry/NXinstrument/NXdetector/event_time_of_flight");

		// validate link 'pulse_time' to location '/NXentry/NXinstrument/NXdetector/pulse_time
		final DataNode pulse_time = group.getDataNode("pulse_time");
		validateDataNodeLink("pulse_time", pulse_time, "/NXentry/NXinstrument/NXdetector/pulse_time");

	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_NXentry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate field 'SNSdetector_calibration_id' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset SNSdetector_calibration_id = group.getLazyDataset("SNSdetector_calibration_id");
		validateFieldNotNull("SNSdetector_calibration_id", SNSdetector_calibration_id);
		if (SNSdetector_calibration_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("SNSdetector_calibration_id", SNSdetector_calibration_id, NX_CHAR);
		}

		// validate field 'SNSgeometry_file_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset SNSgeometry_file_name = group.getLazyDataset("SNSgeometry_file_name");
		validateFieldNotNull("SNSgeometry_file_name", SNSgeometry_file_name);
		if (SNSgeometry_file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("SNSgeometry_file_name", SNSgeometry_file_name, NX_CHAR);
		}

		// validate field 'SNStranslation_service' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset SNStranslation_service = group.getLazyDataset("SNStranslation_service");
		validateFieldNotNull("SNStranslation_service", SNStranslation_service);
		if (SNStranslation_service != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("SNStranslation_service", SNStranslation_service, NX_CHAR);
		}

		// validate field 'beamline' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset beamline = group.getLazyDataset("beamline");
		validateFieldNotNull("beamline", beamline);
		if (beamline != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beamline", beamline, NX_CHAR);
		}

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate child group 'SNS' of type NXsource
		validateGroup_NXentry_instrument_SNS(group.getSource("SNS"));

		// validate unnamed child group of type NXdetector (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector.class, false, true);
		final Map<String, NXdetector> allDetector = group.getAllDetector();
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_NXentry_instrument_NXdetector(detector);
		}

		// validate unnamed child group of type NXdisk_chopper (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdisk_chopper.class, false, true);
		final Map<String, NXdisk_chopper> allDisk_chopper = group.getAllDisk_chopper();
		for (final NXdisk_chopper disk_chopper : allDisk_chopper.values()) {
			validateGroup_NXentry_instrument_NXdisk_chopper(disk_chopper);
		}

		// validate child group 'moderator' of type NXmoderator
		validateGroup_NXentry_instrument_moderator(group.getModerator());

		// validate unnamed child group of type NXaperture (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXaperture.class, false, true);
		final Map<String, NXaperture> allAperture = group.getAllAperture();
		for (final NXaperture aperture : allAperture.values()) {
			validateGroup_NXentry_instrument_NXaperture(aperture);
		}

		// validate unnamed child group of type NXattenuator (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXattenuator.class, false, true);
		final Map<String, NXattenuator> allAttenuator = group.getAllAttenuator();
		for (final NXattenuator attenuator : allAttenuator.values()) {
			validateGroup_NXentry_instrument_NXattenuator(attenuator);
		}

		// validate unnamed child group of type NXpolarizer (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXpolarizer.class, false, true);
		final Map<String, NXpolarizer> allPolarizer = group.getAllPolarizer();
		for (final NXpolarizer polarizer : allPolarizer.values()) {
			validateGroup_NXentry_instrument_NXpolarizer(polarizer);
		}

		// validate unnamed child group of type NXcrystal (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcrystal.class, false, true);
		final Map<String, NXcrystal> allCrystal = group.getAllCrystal();
		for (final NXcrystal crystal : allCrystal.values()) {
			validateGroup_NXentry_instrument_NXcrystal(crystal);
		}
	}

	/**
	 * Validate group 'SNS' of type NXsource.
	 */
	private void validateGroup_NXentry_instrument_SNS(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("SNS", NXsource.class, group))) return;

		// validate field 'frequency' of type NX_FLOAT.
		final ILazyDataset frequency = group.getLazyDataset("frequency");
		validateFieldNotNull("frequency", frequency);
		if (frequency != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("frequency", frequency, NX_FLOAT);
			validateFieldUnits("frequency", group.getDataNode("frequency"), NX_FREQUENCY);
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
					"photon",
					"x-ray",
					"muon",
					"electron",
					"ultraviolet",
					"visible light",
					"positron",
					"proton");
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
	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_NXentry_instrument_NXdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'azimuthal_angle' of type NX_FLOAT.
		final ILazyDataset azimuthal_angle = group.getLazyDataset("azimuthal_angle");
		validateFieldNotNull("azimuthal_angle", azimuthal_angle);
		if (azimuthal_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("azimuthal_angle", azimuthal_angle, NX_FLOAT);
			validateFieldUnits("azimuthal_angle", group.getDataNode("azimuthal_angle"), NX_ANGLE);
			validateFieldRank("azimuthal_angle", azimuthal_angle, 2);
			validateFieldDimensions("azimuthal_angle", azimuthal_angle, null, "numx", "numy");
		}

		// validate field 'data_x_y' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset data_x_y = group.getLazyDataset("data_x_y");
		validateFieldNotNull("data_x_y", data_x_y);
		if (data_x_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data_x_y", data_x_y, NX_UINT);
			validateFieldRank("data_x_y", data_x_y, 2);
			validateFieldDimensions("data_x_y", data_x_y, null, "numx", "numy");
		}

		// validate field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 2);
			validateFieldDimensions("distance", distance, null, "numx", "numy");
		}

		// validate field 'event_index' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset event_index = group.getLazyDataset("event_index");
		validateFieldNotNull("event_index", event_index);
		if (event_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("event_index", event_index, NX_UINT);
			validateFieldRank("event_index", event_index, 1);
			validateFieldDimensions("event_index", event_index, null, "numpulses");
		}

		// validate field 'event_pixel_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset event_pixel_id = group.getLazyDataset("event_pixel_id");
		validateFieldNotNull("event_pixel_id", event_pixel_id);
		if (event_pixel_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("event_pixel_id", event_pixel_id, NX_UINT);
			validateFieldRank("event_pixel_id", event_pixel_id, 1);
			validateFieldDimensions("event_pixel_id", event_pixel_id, null, "numevents");
		}

		// validate field 'event_time_of_flight' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset event_time_of_flight = group.getLazyDataset("event_time_of_flight");
		validateFieldNotNull("event_time_of_flight", event_time_of_flight);
		if (event_time_of_flight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("event_time_of_flight", event_time_of_flight, NX_FLOAT);
			validateFieldUnits("event_time_of_flight", group.getDataNode("event_time_of_flight"), NX_TIME_OF_FLIGHT);
			validateFieldRank("event_time_of_flight", event_time_of_flight, 1);
			validateFieldDimensions("event_time_of_flight", event_time_of_flight, null, "numevents");
		}

		// validate field 'pixel_id' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset pixel_id = group.getLazyDataset("pixel_id");
		validateFieldNotNull("pixel_id", pixel_id);
		if (pixel_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pixel_id", pixel_id, NX_UINT);
			validateFieldRank("pixel_id", pixel_id, 2);
			validateFieldDimensions("pixel_id", pixel_id, null, "numx", "numy");
		}

		// validate field 'polar_angle' of type NX_FLOAT.
		final ILazyDataset polar_angle = group.getLazyDataset("polar_angle");
		validateFieldNotNull("polar_angle", polar_angle);
		if (polar_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("polar_angle", polar_angle, NX_FLOAT);
			validateFieldUnits("polar_angle", group.getDataNode("polar_angle"), NX_ANGLE);
			validateFieldRank("polar_angle", polar_angle, 2);
			validateFieldDimensions("polar_angle", polar_angle, null, "numx", "numy");
		}

		// validate field 'pulse_time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset pulse_time = group.getLazyDataset("pulse_time");
		validateFieldNotNull("pulse_time", pulse_time);
		if (pulse_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pulse_time", pulse_time, NX_FLOAT);
			validateFieldUnits("pulse_time", group.getDataNode("pulse_time"), NX_TIME);
			validateFieldRank("pulse_time", pulse_time, 1);
			validateFieldDimensions("pulse_time", pulse_time, null, "numpulses");
		}

		// validate field 'total_counts' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset total_counts = group.getLazyDataset("total_counts");
		validateFieldNotNull("total_counts", total_counts);
		if (total_counts != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("total_counts", total_counts, NX_UINT);
		}

		// validate field 'x_pixel_offset' of type NX_FLOAT.
		final ILazyDataset x_pixel_offset = group.getLazyDataset("x_pixel_offset");
		validateFieldNotNull("x_pixel_offset", x_pixel_offset);
		if (x_pixel_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_pixel_offset", x_pixel_offset, NX_FLOAT);
			validateFieldUnits("x_pixel_offset", group.getDataNode("x_pixel_offset"), NX_LENGTH);
			validateFieldRank("x_pixel_offset", x_pixel_offset, 1);
			validateFieldDimensions("x_pixel_offset", x_pixel_offset, null, "numx");
		}

		// validate field 'y_pixel_offset' of type NX_FLOAT.
		final ILazyDataset y_pixel_offset = group.getLazyDataset("y_pixel_offset");
		validateFieldNotNull("y_pixel_offset", y_pixel_offset);
		if (y_pixel_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_pixel_offset", y_pixel_offset, NX_FLOAT);
			validateFieldUnits("y_pixel_offset", group.getDataNode("y_pixel_offset"), NX_LENGTH);
			validateFieldRank("y_pixel_offset", y_pixel_offset, 1);
			validateFieldDimensions("y_pixel_offset", y_pixel_offset, null, "numy");
		}
		// validate child group 'origin' of type NXgeometry
		validateGroup_NXentry_instrument_NXdetector_origin(group.getGeometry("origin"));
	}

	/**
	 * Validate group 'origin' of type NXgeometry.
	 */
	private void validateGroup_NXentry_instrument_NXdetector_origin(final NXgeometry group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("origin", NXgeometry.class, group))) return;

		// validate child group 'orientation' of type NXorientation
		validateGroup_NXentry_instrument_NXdetector_origin_orientation(group.getOrientation());

		// validate child group 'shape' of type NXshape
		validateGroup_NXentry_instrument_NXdetector_origin_shape(group.getShape());

		// validate child group 'translation' of type NXtranslation
		validateGroup_NXentry_instrument_NXdetector_origin_translation(group.getTranslation());
	}

	/**
	 * Validate group 'orientation' of type NXorientation.
	 */
	private void validateGroup_NXentry_instrument_NXdetector_origin_orientation(final NXorientation group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("orientation", NXorientation.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_UNITLESS);
			validateFieldRank("value", value, 1);
			validateFieldDimensions("value", value, null, 6);
		}
	}

	/**
	 * Validate group 'shape' of type NXshape.
	 */
	private void validateGroup_NXentry_instrument_NXdetector_origin_shape(final NXshape group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("shape", NXshape.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'description' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate field 'shape' of type NX_CHAR.
		final ILazyDataset shape = group.getLazyDataset("shape");
		validateFieldNotNull("shape", shape);
		if (shape != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("shape", shape, NX_CHAR);
			validateFieldEnumeration("shape", shape,
					"nxflat",
					"nxcylinder",
					"nxbox",
					"nxsphere",
					"nxcone",
					"nxelliptical",
					"nxtoroidal",
					"nxparabolic",
					"nxpolynomial");
		}

		// validate field 'size' of type NX_FLOAT.
		final ILazyDataset size = group.getLazyDataset("size");
		validateFieldNotNull("size", size);
		if (size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("size", size, NX_FLOAT);
			validateFieldUnits("size", group.getDataNode("size"), NX_LENGTH);
			validateFieldRank("size", size, 1);
			validateFieldDimensions("size", size, null, 3);
		}
	}

	/**
	 * Validate group 'translation' of type NXtranslation.
	 */
	private void validateGroup_NXentry_instrument_NXdetector_origin_translation(final NXtranslation group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("translation", NXtranslation.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 1);
			validateFieldDimensions("distance", distance, null, 3);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdisk_chopper.
	 */
	private void validateGroup_NXentry_instrument_NXdisk_chopper(final NXdisk_chopper group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdisk_chopper.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		}
	}

	/**
	 * Validate group 'moderator' of type NXmoderator.
	 */
	private void validateGroup_NXentry_instrument_moderator(final NXmoderator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("moderator", NXmoderator.class, group))) return;

		// validate field 'coupling_material' of type NX_CHAR.
		final ILazyDataset coupling_material = group.getLazyDataset("coupling_material");
		validateFieldNotNull("coupling_material", coupling_material);
		if (coupling_material != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("coupling_material", coupling_material, NX_CHAR);
		}

		// validate field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		}

		// validate field 'temperature' of type NX_FLOAT.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
		validateFieldNotNull("temperature", temperature);
		if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_FLOAT);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
		}

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"H20",
					"D20",
					"Liquid H2",
					"Liquid CH4",
					"Liquid D2",
					"Solid D2",
					"C",
					"Solid CH4",
					"Solid H2");
		}
	}

	/**
	 * Validate optional unnamed group of type NXaperture.
	 */
	private void validateGroup_NXentry_instrument_NXaperture(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXaperture.class, group))) return;

		// validate field 'x_pixel_offset' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset x_pixel_offset = group.getLazyDataset("x_pixel_offset");
		validateFieldNotNull("x_pixel_offset", x_pixel_offset);
		if (x_pixel_offset != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_pixel_offset", x_pixel_offset, NX_FLOAT);
			validateFieldUnits("x_pixel_offset", group.getDataNode("x_pixel_offset"), NX_LENGTH);
		}
		// validate child group 'origin' of type NXgeometry
		validateGroup_NXentry_instrument_NXaperture_origin(group.getGeometry("origin"));
	}

	/**
	 * Validate group 'origin' of type NXgeometry.
	 */
	private void validateGroup_NXentry_instrument_NXaperture_origin(final NXgeometry group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("origin", NXgeometry.class, group))) return;

		// validate child group 'orientation' of type NXorientation
		validateGroup_NXentry_instrument_NXaperture_origin_orientation(group.getOrientation());

		// validate child group 'shape' of type NXshape
		validateGroup_NXentry_instrument_NXaperture_origin_shape(group.getShape());

		// validate child group 'translation' of type NXtranslation
		validateGroup_NXentry_instrument_NXaperture_origin_translation(group.getTranslation());
	}

	/**
	 * Validate group 'orientation' of type NXorientation.
	 */
	private void validateGroup_NXentry_instrument_NXaperture_origin_orientation(final NXorientation group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("orientation", NXorientation.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_UNITLESS);
			validateFieldRank("value", value, 1);
			validateFieldDimensions("value", value, null, 6);
		}
	}

	/**
	 * Validate group 'shape' of type NXshape.
	 */
	private void validateGroup_NXentry_instrument_NXaperture_origin_shape(final NXshape group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("shape", NXshape.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'description' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate field 'shape' of type NX_CHAR.
		final ILazyDataset shape = group.getLazyDataset("shape");
		validateFieldNotNull("shape", shape);
		if (shape != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("shape", shape, NX_CHAR);
			validateFieldEnumeration("shape", shape,
					"nxflat",
					"nxcylinder",
					"nxbox",
					"nxsphere",
					"nxcone",
					"nxelliptical",
					"nxtoroidal",
					"nxparabolic",
					"nxpolynomial");
		}

		// validate field 'size' of type NX_FLOAT.
		final ILazyDataset size = group.getLazyDataset("size");
		validateFieldNotNull("size", size);
		if (size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("size", size, NX_FLOAT);
			validateFieldUnits("size", group.getDataNode("size"), NX_LENGTH);
			validateFieldRank("size", size, 1);
			validateFieldDimensions("size", size, null, 3);
		}
	}

	/**
	 * Validate group 'translation' of type NXtranslation.
	 */
	private void validateGroup_NXentry_instrument_NXaperture_origin_translation(final NXtranslation group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("translation", NXtranslation.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 1);
			validateFieldDimensions("distance", distance, null, 3);
		}
	}

	/**
	 * Validate optional unnamed group of type NXattenuator.
	 */
	private void validateGroup_NXentry_instrument_NXattenuator(final NXattenuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXattenuator.class, group))) return;

		// validate field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		}
	}

	/**
	 * Validate optional unnamed group of type NXpolarizer.
	 */
	private void validateGroup_NXentry_instrument_NXpolarizer(final NXpolarizer group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXpolarizer.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional unnamed group of type NXcrystal.
	 */
	private void validateGroup_NXentry_instrument_NXcrystal(final NXcrystal group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcrystal.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
		}

		// validate field 'wavelength' of type NX_FLOAT.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
		validateFieldNotNull("wavelength", wavelength);
		if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_FLOAT);
			validateFieldUnits("wavelength", group.getDataNode("wavelength"), NX_WAVELENGTH);
			validateFieldDimensions("wavelength", wavelength, "NXcrystal", "i");
		}
		// validate child group 'origin' of type NXgeometry
		validateGroup_NXentry_instrument_NXcrystal_origin(group.getGeometry("origin"));
	}

	/**
	 * Validate group 'origin' of type NXgeometry.
	 */
	private void validateGroup_NXentry_instrument_NXcrystal_origin(final NXgeometry group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("origin", NXgeometry.class, group))) return;

		// validate field 'description' of type NX_CHAR.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate child group 'orientation' of type NXorientation
		validateGroup_NXentry_instrument_NXcrystal_origin_orientation(group.getOrientation());

		// validate child group 'shape' of type NXshape
		validateGroup_NXentry_instrument_NXcrystal_origin_shape(group.getShape());

		// validate child group 'translation' of type NXtranslation
		validateGroup_NXentry_instrument_NXcrystal_origin_translation(group.getTranslation());
	}

	/**
	 * Validate group 'orientation' of type NXorientation.
	 */
	private void validateGroup_NXentry_instrument_NXcrystal_origin_orientation(final NXorientation group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("orientation", NXorientation.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'value' of type NX_FLOAT.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_FLOAT);
			validateFieldUnits("value", group.getDataNode("value"), NX_UNITLESS);
			validateFieldRank("value", value, 1);
			validateFieldDimensions("value", value, null, 6);
		}
	}

	/**
	 * Validate group 'shape' of type NXshape.
	 */
	private void validateGroup_NXentry_instrument_NXcrystal_origin_shape(final NXshape group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("shape", NXshape.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'description' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate field 'shape' of type NX_CHAR.
		final ILazyDataset shape = group.getLazyDataset("shape");
		validateFieldNotNull("shape", shape);
		if (shape != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("shape", shape, NX_CHAR);
			validateFieldEnumeration("shape", shape,
					"nxflat",
					"nxcylinder",
					"nxbox",
					"nxsphere",
					"nxcone",
					"nxelliptical",
					"nxtoroidal",
					"nxparabolic",
					"nxpolynomial");
		}

		// validate field 'size' of type NX_FLOAT.
		final ILazyDataset size = group.getLazyDataset("size");
		validateFieldNotNull("size", size);
		if (size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("size", size, NX_FLOAT);
			validateFieldUnits("size", group.getDataNode("size"), NX_LENGTH);
			validateFieldDimensions("size", size, "NXshape", "numobj", "nshapepar");
		}
	}

	/**
	 * Validate group 'translation' of type NXtranslation.
	 */
	private void validateGroup_NXentry_instrument_NXcrystal_origin_translation(final NXtranslation group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("translation", NXtranslation.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'distance' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
			validateFieldRank("distance", distance, 1);
			validateFieldDimensions("distance", distance, null, 3);
		}
	}

	/**
	 * Validate optional unnamed group of type NXmonitor.
	 */
	private void validateGroup_NXentry_NXmonitor(final NXmonitor group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmonitor.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_UINT.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_UINT);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
			validateFieldRank("data", data, 1);
			validateFieldDimensions("data", data, null, "numtimechannels");
		}

		// validate field 'distance' of type NX_FLOAT.
		final ILazyDataset distance = group.getLazyDataset("distance");
		validateFieldNotNull("distance", distance);
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_FLOAT);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		}

		// validate field 'mode' of type NX_CHAR.
		final ILazyDataset mode = group.getLazyDataset("mode");
		validateFieldNotNull("mode", mode);
		if (mode != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("mode", mode, NX_CHAR);
			validateFieldEnumeration("mode", mode,
					"monitor",
					"timer");
		}

		// validate field 'time_of_flight' of type NX_FLOAT.
		final ILazyDataset time_of_flight = group.getLazyDataset("time_of_flight");
		validateFieldNotNull("time_of_flight", time_of_flight);
		if (time_of_flight != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time_of_flight", time_of_flight, NX_FLOAT);
			validateFieldUnits("time_of_flight", group.getDataNode("time_of_flight"), NX_TIME);
			validateFieldRank("time_of_flight", time_of_flight, 1);
			validateFieldDimensions("time_of_flight", time_of_flight, null, "numtimechannels + 1");
		}
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_NXentry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'changer_position' of type NX_INT.
		final ILazyDataset changer_position = group.getLazyDataset("changer_position");
		validateFieldNotNull("changer_position", changer_position);
		if (changer_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("changer_position", changer_position, NX_INT);
			validateFieldUnits("changer_position", group.getDataNode("changer_position"), NX_UNITLESS);
		}

		// validate field 'holder' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset holder = group.getLazyDataset("holder");
		validateFieldNotNull("holder", holder);
		if (holder != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("holder", holder, NX_CHAR);
		}

		// validate field 'identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset identifier = group.getLazyDataset("identifier");
		validateFieldNotNull("identifier", identifier);
		if (identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier", identifier, NX_CHAR);
		}

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'nature' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset nature = group.getLazyDataset("nature");
		validateFieldNotNull("nature", nature);
		if (nature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("nature", nature, NX_CHAR);
		}
	}

	/**
	 * Validate unnamed group of type NXuser.
	 */
	private void validateGroup_NXentry_NXuser(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXuser.class, group))) return;

		// validate field 'facility_user_id' of type NX_CHAR.
		final ILazyDataset facility_user_id = group.getLazyDataset("facility_user_id");
		validateFieldNotNull("facility_user_id", facility_user_id);
		if (facility_user_id != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("facility_user_id", facility_user_id, NX_CHAR);
		}

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'role' of type NX_CHAR.
		final ILazyDataset role = group.getLazyDataset("role");
		validateFieldNotNull("role", role);
		if (role != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("role", role, NX_CHAR);
		}
	}
}
