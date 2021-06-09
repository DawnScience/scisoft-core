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
import org.eclipse.dawnsci.nexus.NXfermi_chopper;
import org.eclipse.dawnsci.nexus.NXdisk_chopper;

/**
 * Validator for the application definition 'NXdirecttof'.
 */
public class NXdirecttofValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXdirecttofValidator() {
		super(NexusApplicationDefinition.NX_DIRECTTOF);
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

		// validate field 'title' of unknown type.
		final ILazyDataset title = group.getLazyDataset("title");
		if (!(validateFieldNotNull("title", title))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("title", title, NX_CHAR);

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		if (!(validateFieldNotNull("start_time", start_time))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("start_time", start_time, NX_DATE_TIME);

		// validate field 'definition' of unknown type.
		final ILazyDataset definition = group.getLazyDataset("definition");
		if (!(validateFieldNotNull("definition", definition))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("definition", definition, NX_CHAR);
		validateFieldEnumeration("definition", definition,
				"NXdirecttof");

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_entry_NXinstrument(instrument);
		}
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_entry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

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
	private void validateGroup_entry_NXinstrument_fermi_chopper(final NXfermi_chopper group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("fermi_chopper", NXfermi_chopper.class, group))) return;

		// validate field 'rotation_speed' of type NX_FLOAT.
		final ILazyDataset rotation_speed = group.getLazyDataset("rotation_speed");
		if (!(validateFieldNotNull("rotation_speed", rotation_speed))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("rotation_speed", rotation_speed, NX_FLOAT);
		validateFieldUnits("rotation_speed", group.getDataNode("rotation_speed"), NX_FREQUENCY);

		// validate field 'energy' of type NX_FLOAT.
		final ILazyDataset energy = group.getLazyDataset("energy");
		if (!(validateFieldNotNull("energy", energy))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energy", energy, NX_FLOAT);
		validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
	}

	/**
	 * Validate optional group 'disk_chopper' of type NXdisk_chopper.
	 */
	private void validateGroup_entry_NXinstrument_disk_chopper(final NXdisk_chopper group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("disk_chopper", NXdisk_chopper.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'rotation_speed' of type NX_FLOAT.
		final ILazyDataset rotation_speed = group.getLazyDataset("rotation_speed");
		if (!(validateFieldNotNull("rotation_speed", rotation_speed))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("rotation_speed", rotation_speed, NX_FLOAT);
		validateFieldUnits("rotation_speed", group.getDataNode("rotation_speed"), NX_FREQUENCY);

		// validate field 'energy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset energy = group.getLazyDataset("energy");
		if (!(validateFieldNotNull("energy", energy))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energy", energy, NX_FLOAT);
		validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
	}
}
