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
import org.eclipse.dawnsci.nexus.NXapm_paraprobe_tool_process;

/**
 * Validator for the application definition 'NXapm_paraprobe_ranger_results'.
 */
public class NXapm_paraprobe_ranger_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXapm_paraprobe_ranger_resultsValidator() {
		super(NexusApplicationDefinition.NX_APM_PARAPROBE_RANGER_RESULTS);
	}

	@Override
	public ValidationReport validate(NXroot root) {
		// validate unnamed child group of type NXentry
		validateUnnamedGroupOccurrences(root, NXentry.class, false, false);
		final NXentry entry = getFirstGroupOrNull(root.getAllEntry());
		validateGroup_NXentry(entry);
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
					"NXapm_paraprobe_ranger_results");
		}

		// validate child group 'iontypesID' of type NXapm_paraprobe_tool_process
		validateGroup_NXentry_iontypesID(group.getChild("iontypesID", NXapm_paraprobe_tool_process.class));
	}

	/**
	 * Validate group 'iontypesID' of type NXapm_paraprobe_tool_process.
	 */
	private void validateGroup_NXentry_iontypesID(final NXapm_paraprobe_tool_process group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("iontypesID", NXapm_paraprobe_tool_process.class, group))) return;

		// validate field 'iontypes' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset iontypes = group.getLazyDataset("iontypes");
		validateFieldNotNull("iontypes", iontypes);
		if (iontypes != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("iontypes", iontypes, NX_UINT);
			validateFieldUnits("iontypes", group.getDataNode("iontypes"), NX_UNITLESS);
			validateFieldRank("iontypes", iontypes, 1);
			validateFieldDimensions("iontypes", iontypes, null, "n_ions");
		}
	}
}
