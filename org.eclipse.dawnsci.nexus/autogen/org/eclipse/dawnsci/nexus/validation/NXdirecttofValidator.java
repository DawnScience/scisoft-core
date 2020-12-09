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

import org.eclipse.january.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXfermi_chopper;
import org.eclipse.dawnsci.nexus.NXdisk_chopper;

/**
 * Validator for the application definition 'NXdirecttof'.
 */
public class NXdirecttofValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	@Override
	public void validate(NXroot root) throws NexusValidationException {
		// validate child group 'entry' of type NXentry
		validateGroup_entry(root.getEntry());
	}

	@Override
	public void validate(NXentry entry) throws NexusValidationException {
		validateGroup_entry(entry);
	}

	@Override
	public void validate(NXsubentry subentry) throws NexusValidationException {
		validateGroup_entry(subentry);
	}


	/**
	 * Validate group 'entry' of type NXentry.
	 */
	private void validateGroup_entry(final NXsubentry group) throws NexusValidationException {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		validateGroupNotNull("entry", NXentry.class, group);

		// validate field 'title' of unknown type.
		final IDataset title = group.getTitle();
		validateFieldNotNull("title", title);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions

		// validate field 'start_time' of type NX_DATE_TIME.
		final IDataset start_time = group.getStart_time();
		validateFieldNotNull("start_time", start_time);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("start_time", start_time, NX_DATE_TIME);

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		validateFieldNotNull("definition", definition);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldEnumeration("definition", definition,
				"NXdirecttof");

		// validate unnamed child group of type NXinstrument (possibly multiple)
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_entry_NXinstrument(instrument);
		}
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_entry_NXinstrument(final NXinstrument group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXinstrument.class, group);

		// validate optional child group 'fermi_chopper' of type NXfermi_chopper
		if (group.getFermi_chopper() != null) {
			validateGroup_entry_NXinstrument_fermi_chopper(group.getFermi_chopper());
		}

		// validate optional child group 'disk_chopper' of type NXdisk_chopper
		if (group.getDisk_chopper() != null) {
			validateGroup_entry_NXinstrument_disk_chopper(group.getDisk_chopper());
		}
	}

	/**
	 * Validate optional group 'fermi_chopper' of type NXfermi_chopper.
	 */
	private void validateGroup_entry_NXinstrument_fermi_chopper(final NXfermi_chopper group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("fermi_chopper", NXfermi_chopper.class, group);

		// validate field 'rotation_speed' of type NX_FLOAT.
		final IDataset rotation_speed = group.getRotation_speed();
		validateFieldNotNull("rotation_speed", rotation_speed);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("rotation_speed", rotation_speed, NX_FLOAT);
		validateFieldUnits("rotation_speed", rotation_speed, NX_FREQUENCY);

		// validate field 'energy' of type NX_FLOAT.
		final IDataset energy = group.getEnergy();
		validateFieldNotNull("energy", energy);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energy", energy, NX_FLOAT);
		validateFieldUnits("energy", energy, NX_ENERGY);
	}

	/**
	 * Validate optional group 'disk_chopper' of type NXdisk_chopper.
	 */
	private void validateGroup_entry_NXinstrument_disk_chopper(final NXdisk_chopper group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("disk_chopper", NXdisk_chopper.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'rotation_speed' of type NX_FLOAT.
		final IDataset rotation_speed = group.getRotation_speed();
		validateFieldNotNull("rotation_speed", rotation_speed);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("rotation_speed", rotation_speed, NX_FLOAT);
		validateFieldUnits("rotation_speed", rotation_speed, NX_FREQUENCY);

		// validate field 'energy' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset energy = group.getDataset("energy");
		validateFieldNotNull("energy", energy);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energy", energy, NX_FLOAT);
		validateFieldUnits("energy", energy, NX_ENERGY);
	}
}
