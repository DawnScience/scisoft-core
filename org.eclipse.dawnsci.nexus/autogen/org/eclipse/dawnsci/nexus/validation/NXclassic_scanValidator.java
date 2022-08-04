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
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXclassic_scan'.
 */
public class NXclassic_scanValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXclassic_scanValidator() {
		super(NexusApplicationDefinition.NX_CLASSIC_SCAN);
	}

	@Override
	public ValidationReport validate(NXroot root) {
		// validate child group 'entry' of type NXentry
		validateGroup_entry(root.getEntry());
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXentry entry) {
		validateGroup_entry(entry);
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXsubentry subentry) {
		validateGroup_entry(subentry);
		return validationReport;
	}


	/**
	 * Validate group 'entry' of type NXentry.
	 */
	private void validateGroup_entry(final NXsubentry group) {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		if (!(validateGroupNotNull("entry", NXentry.class, group))) return;

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXclassic_scan");
		}

		// validate optional attribute 'default' of type NX_CHAR.
		final Attribute default_attr = group.getAttribute("default");
		if (default_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("default", default_attr, NX_CHAR);
		}

		// validate optional field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
				if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate optional field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
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

		// validate field 'scan_command' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset scan_command = group.getLazyDataset("scan_command");
		validateFieldNotNull("scan_command", scan_command);
		if (scan_command != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scan_command", scan_command, NX_CHAR);
		}

		// validate field 'scan_fields' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset scan_fields = group.getLazyDataset("scan_fields");
		validateFieldNotNull("scan_fields", scan_fields);
		if (scan_fields != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scan_fields", scan_fields, NX_CHAR);
		}

		// validate optional field 'scan_header' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset scan_header = group.getLazyDataset("scan_header");
				if (scan_header != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scan_header", scan_header, NX_CHAR);
		}

		// validate optional field 'called_by' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset called_by = group.getLazyDataset("called_by");
				if (called_by != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("called_by", called_by, NX_CHAR);
		}

		// validate optional child group 'before_scan' of type NXcollection
		if (group.getCollection("before_scan") != null) {
			validateGroup_entry_before_scan(group.getCollection("before_scan"));
		}

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_entry_NXdata(data);
		}
	}

	/**
	 * Validate optional group 'before_scan' of type NXcollection.
	 */
	private void validateGroup_entry_before_scan(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("before_scan", NXcollection.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_entry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldDimensions("data", data, null, "i", "j", "k");
		}
	}
}
