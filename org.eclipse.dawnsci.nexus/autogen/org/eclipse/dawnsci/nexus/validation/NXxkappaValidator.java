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

import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXxkappa'.
 */
public class NXxkappaValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXxkappaValidator() {
		super(NexusApplicationDefinition.NX_XKAPPA);
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
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXxkappa");
		}

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
	}

	/**
	 * Validate group 'detector' of type NXdetector.
	 */
	private void validateGroup_entry_instrument_detector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("detector", NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'polar_angle' of type NX_FLOAT.
		final ILazyDataset polar_angle = group.getLazyDataset("polar_angle");
		validateFieldNotNull("polar_angle", polar_angle);
		if (polar_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("polar_angle", polar_angle, NX_FLOAT);
			validateFieldUnits("polar_angle", group.getDataNode("polar_angle"), NX_ANGLE);
			validateFieldRank("polar_angle", polar_angle, 1);
			validateFieldDimensions("polar_angle", polar_angle, null, "nP");
		}
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_entry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'rotation_angle' of type NX_FLOAT.
		final ILazyDataset rotation_angle = group.getLazyDataset("rotation_angle");
		validateFieldNotNull("rotation_angle", rotation_angle);
		if (rotation_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("rotation_angle", rotation_angle, NX_FLOAT);
			validateFieldUnits("rotation_angle", group.getDataNode("rotation_angle"), NX_ANGLE);
			validateFieldRank("rotation_angle", rotation_angle, 1);
			validateFieldDimensions("rotation_angle", rotation_angle, null, "nP");
		}

		// validate field 'kappa' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset kappa = group.getLazyDataset("kappa");
		validateFieldNotNull("kappa", kappa);
		if (kappa != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("kappa", kappa, NX_FLOAT);
			validateFieldUnits("kappa", group.getDataNode("kappa"), NX_ANGLE);
			validateFieldRank("kappa", kappa, 1);
			validateFieldDimensions("kappa", kappa, null, "nP");
		}

		// validate field 'phi' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset phi = group.getLazyDataset("phi");
		validateFieldNotNull("phi", phi);
		if (phi != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("phi", phi, NX_FLOAT);
			validateFieldUnits("phi", group.getDataNode("phi"), NX_ANGLE);
			validateFieldRank("phi", phi, 1);
			validateFieldDimensions("phi", phi, null, "nP");
		}

		// validate field 'alpha' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset alpha = group.getLazyDataset("alpha");
		validateFieldNotNull("alpha", alpha);
		if (alpha != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("alpha", alpha, NX_FLOAT);
			validateFieldUnits("alpha", group.getDataNode("alpha"), NX_ANGLE);
		}
	}

	/**
	 * Validate group 'name' of type NXdata.
	 */
	private void validateGroup_entry_name(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("name", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate link 'polar_angle' to location '/NXentry/NXinstrument/NXdetector/polar_angle
		final DataNode polar_angle = group.getDataNode("polar_angle");
		validateDataNodeLink("polar_angle", polar_angle, "/NXentry/NXinstrument/NXdetector/polar_angle");

		// validate link 'rotation_angle' to location '/NXentry/NXsample/rotation_angle
		final DataNode rotation_angle = group.getDataNode("rotation_angle");
		validateDataNodeLink("rotation_angle", rotation_angle, "/NXentry/NXsample/rotation_angle");

		// validate link 'kappa' to location '/NXentry/NXsample/kappa
		final DataNode kappa = group.getDataNode("kappa");
		validateDataNodeLink("kappa", kappa, "/NXentry/NXsample/kappa");

		// validate link 'phi' to location '/NXentry/NXsample/phi
		final DataNode phi = group.getDataNode("phi");
		validateDataNodeLink("phi", phi, "/NXentry/NXsample/phi");

	}
}
