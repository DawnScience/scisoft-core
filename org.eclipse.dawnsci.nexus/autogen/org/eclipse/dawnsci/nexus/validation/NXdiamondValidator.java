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

import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXinsertion_device;
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXdiamond'.
 */
public class NXdiamondValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXdiamondValidator() {
		super(NexusApplicationDefinition.NX_DIAMOND);
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

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		if (!(validateFieldNotNull("start_time", start_time))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("start_time", start_time, NX_DATE_TIME);

		// validate field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
		if (!(validateFieldNotNull("end_time", end_time))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("end_time", end_time, NX_DATE_TIME);

		// validate field 'program_name' of type NX_CHAR.
		final ILazyDataset program_name = group.getLazyDataset("program_name");
		if (!(validateFieldNotNull("program_name", program_name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("program_name", program_name, NX_CHAR);

		// validate child group 'instrument' of type NXinstrument
		validateGroup_entry_instrument(group.getInstrument());

		// validate unnamed child group of type NXuser (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXuser.class, false, true);
		final Map<String, NXuser> allUser = group.getAllUser();
		for (final NXuser user : allUser.values()) {
			validateGroup_entry_NXuser(user);
		}

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_entry_NXdata(data);
		}
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_entry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate child group 'source' of type NXsource
		validateGroup_entry_instrument_source(group.getSource());

		// validate unnamed child group of type NXinsertion_device (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinsertion_device.class, false, true);
		final Map<String, NXinsertion_device> allInsertion_device = group.getAllInsertion_device();
		for (final NXinsertion_device insertion_device : allInsertion_device.values()) {
			validateGroup_entry_instrument_NXinsertion_device(insertion_device);
		}

		// validate unnamed child group of type NXmonochromator (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXmonochromator.class, false, true);
		final Map<String, NXmonochromator> allMonochromator = group.getAllMonochromator();
		for (final NXmonochromator monochromator : allMonochromator.values()) {
			validateGroup_entry_instrument_NXmonochromator(monochromator);
		}
	}

	/**
	 * Validate group 'source' of type NXsource.
	 */
	private void validateGroup_entry_instrument_source(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source", NXsource.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);
		validateFieldEnumeration("name", name,
				"Diamond Light Source");

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		if (!(validateFieldNotNull("type", type))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("type", type, NX_CHAR);
		validateFieldEnumeration("type", type,
				"Synchrotron X-ray Source");

		// validate field 'probe' of type NX_CHAR.
		final ILazyDataset probe = group.getLazyDataset("probe");
		if (!(validateFieldNotNull("probe", probe))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("probe", probe, NX_CHAR);
		validateFieldEnumeration("probe", probe,
				"x-ray");
	}

	/**
	 * Validate unnamed group of type NXinsertion_device.
	 */
	private void validateGroup_entry_instrument_NXinsertion_device(final NXinsertion_device group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinsertion_device.class, group))) return;

		// validate field 'type' of unknown type.
		final ILazyDataset type = group.getLazyDataset("type");
		if (!(validateFieldNotNull("type", type))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("type", type, NX_CHAR);
		validateFieldEnumeration("type", type,
				"undulator",
				"wiggler");

		// validate field 'gap' of type NX_FLOAT.
		final ILazyDataset gap = group.getLazyDataset("gap");
		if (!(validateFieldNotNull("gap", gap))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("gap", gap, NX_FLOAT);
		validateFieldUnits("gap", group.getDataNode("gap"), NX_LENGTH);

		// validate field 'taper' of type NX_FLOAT.
		final ILazyDataset taper = group.getLazyDataset("taper");
		if (!(validateFieldNotNull("taper", taper))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("taper", taper, NX_FLOAT);
		validateFieldUnits("taper", group.getDataNode("taper"), NX_ANGLE);

		// validate field 'harmonic' of type NX_INT.
		final ILazyDataset harmonic = group.getLazyDataset("harmonic");
		if (!(validateFieldNotNull("harmonic", harmonic))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("harmonic", harmonic, NX_INT);
		validateFieldUnits("harmonic", group.getDataNode("harmonic"), NX_UNITLESS);
	}

	/**
	 * Validate unnamed group of type NXmonochromator.
	 */
	private void validateGroup_entry_instrument_NXmonochromator(final NXmonochromator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXmonochromator.class, group))) return;

		// validate field 'energy' of type NX_FLOAT.
		final ILazyDataset energy = group.getLazyDataset("energy");
		if (!(validateFieldNotNull("energy", energy))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energy", energy, NX_FLOAT);
		validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
	}

	/**
	 * Validate unnamed group of type NXuser.
	 */
	private void validateGroup_entry_NXuser(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXuser.class, group))) return;

		// validate field 'name' of unknown type.
		final ILazyDataset name = group.getLazyDataset("name");
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'facility_user_id' of unknown type.
		final ILazyDataset facility_user_id = group.getLazyDataset("facility_user_id");
		if (!(validateFieldNotNull("facility_user_id", facility_user_id))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("facility_user_id", facility_user_id, NX_CHAR);
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_entry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}
}
