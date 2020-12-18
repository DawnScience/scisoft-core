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

import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;import org.eclipse.january.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXattenuator;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXxrot'.
 */
public class NXxrotValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXxrotValidator() {
		super(NexusApplicationDefinition.NX_XROT);
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

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		if (!(validateFieldNotNull("definition", definition))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("definition", definition, NX_CHAR);
		validateFieldEnumeration("definition", definition,
				"NXxrot");

		// validate child group 'instrument' of type NXinstrument
		validateGroup_entry_instrument(group.getInstrument());

		// validate child group 'sample' of type NXsample
		validateGroup_entry_sample(group.getSample());

		// validate child group 'name' of type NXdata
		validateGroup_entry_name(group.getData("name"));
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_entry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate child group 'detector' of type NXdetector
		validateGroup_entry_instrument_detector(group.getDetector());

		// validate child group 'attenuator' of type NXattenuator
		validateGroup_entry_instrument_attenuator(group.getAttenuator());
	}

	/**
	 * Validate group 'detector' of type NXdetector.
	 */
	private void validateGroup_entry_instrument_detector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("detector", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'polar_angle' of type NX_FLOAT.
		final IDataset polar_angle = group.getPolar_angle();
		if (!(validateFieldNotNull("polar_angle", polar_angle))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("polar_angle", polar_angle, NX_FLOAT);
		validateFieldUnits("polar_angle", group.getDataNode("polar_angle"), NX_ANGLE);
		validateFieldRank("polar_angle", polar_angle, 3);
		validateFieldDimensions("polar_angle", polar_angle, "NXdetector", "np", "i", "j");

		// validate field 'beam_center_x' of type NX_FLOAT.
		final IDataset beam_center_x = group.getBeam_center_x();
		if (!(validateFieldNotNull("beam_center_x", beam_center_x))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("beam_center_x", beam_center_x, NX_FLOAT);
		validateFieldUnits("beam_center_x", group.getDataNode("beam_center_x"), NX_LENGTH);

		// validate field 'beam_center_y' of type NX_FLOAT.
		final IDataset beam_center_y = group.getBeam_center_y();
		if (!(validateFieldNotNull("beam_center_y", beam_center_y))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("beam_center_y", beam_center_y, NX_FLOAT);
		validateFieldUnits("beam_center_y", group.getDataNode("beam_center_y"), NX_LENGTH);
	}

	/**
	 * Validate group 'attenuator' of type NXattenuator.
	 */
	private void validateGroup_entry_instrument_attenuator(final NXattenuator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("attenuator", NXattenuator.class, group))) return;

		// validate field 'attenuator_transmission' of type NX_FLOAT.
		final IDataset attenuator_transmission = group.getAttenuator_transmission();
		if (!(validateFieldNotNull("attenuator_transmission", attenuator_transmission))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("attenuator_transmission", attenuator_transmission, NX_FLOAT);
		validateFieldUnits("attenuator_transmission", group.getDataNode("attenuator_transmission"), NX_ANY);
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_entry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'rotation_angle' of type NX_FLOAT.
		final IDataset rotation_angle = group.getRotation_angle();
		if (!(validateFieldNotNull("rotation_angle", rotation_angle))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("rotation_angle", rotation_angle, NX_FLOAT);
		validateFieldUnits("rotation_angle", group.getDataNode("rotation_angle"), NX_ANGLE);
		validateFieldRank("rotation_angle", rotation_angle, 1);
		validateFieldDimensions("rotation_angle", rotation_angle, null, "nP");

		// validate field 'rotation_angle_step' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset rotation_angle_step = group.getDataset("rotation_angle_step");
		if (!(validateFieldNotNull("rotation_angle_step", rotation_angle_step))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("rotation_angle_step", rotation_angle_step, NX_FLOAT);
		validateFieldUnits("rotation_angle_step", group.getDataNode("rotation_angle_step"), NX_ANGLE);
		validateFieldRank("rotation_angle_step", rotation_angle_step, 1);
		validateFieldDimensions("rotation_angle_step", rotation_angle_step, null, "nP");
	}

	/**
	 * Validate group 'name' of type NXdata.
	 */
	private void validateGroup_entry_name(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("name", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'rotation_angle' to location '/NXentry/NXsample/rotation_angle
		final DataNode rotation_angle = group.getDataNode("rotation_angle");
		validateDataNodeLink("rotation_angle", rotation_angle, "/NXentry/NXsample/rotation_angle");

	}
}
