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
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXxlaue'.
 */
public class NXxlaueValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXxlaueValidator() {
		super(NexusApplicationDefinition.NX_XLAUE);
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
		if (!(validateFieldNotNull("definition", definition))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("definition", definition, NX_CHAR);
		validateFieldEnumeration("definition", definition,
				"NXxlaue");

		// validate child group 'instrument' of type NXinstrument
		validateGroup_entry_instrument(group.getInstrument());
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_entry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate child group 'source' of type NXsource
		validateGroup_entry_instrument_source(group.getSource());
	}

	/**
	 * Validate group 'source' of type NXsource.
	 */
	private void validateGroup_entry_instrument_source(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("source", NXsource.class, group))) return;

		// validate child group 'distribution' of type NXdata
		validateGroup_entry_instrument_source_distribution(group.getDistribution());
	}

	/**
	 * Validate group 'distribution' of type NXdata.
	 */
	private void validateGroup_entry_instrument_source_distribution(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("distribution", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of unknown type. Note: field not defined in base class.
		final ILazyDataset data = group.getLazyDataset("data");
		if (!(validateFieldNotNull("data", data))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_CHAR);
		validateFieldRank("data", data, 1);
		validateFieldDimensions("data", data, null, "nE");

		// validate field 'wavelength' of unknown type. Note: field not defined in base class.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
		if (!(validateFieldNotNull("wavelength", wavelength))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("wavelength", wavelength, NX_CHAR);
		validateFieldUnits("wavelength", group.getDataNode("wavelength"), NX_WAVELENGTH);
		validateFieldRank("wavelength", wavelength, 1);
		validateFieldDimensions("wavelength", wavelength, null, "nE");
	}
}
