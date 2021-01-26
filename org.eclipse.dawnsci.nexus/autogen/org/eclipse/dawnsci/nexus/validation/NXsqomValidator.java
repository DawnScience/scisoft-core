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
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXparameters;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXsqom'.
 */
public class NXsqomValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXsqomValidator() {
		super(NexusApplicationDefinition.NX_SQOM);
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

		// validate attribute 'entry'
		final Attribute entry_attr = group.getAttribute("entry");
		if (!(validateAttributeNotNull("entry", entry_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("entry", entry_attr, NX_CHAR);

		// validate field 'title' of unknown type.
		final IDataset title = group.getTitle();
		if (!(validateFieldNotNull("title", title))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("title", title, NX_CHAR);

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		if (!(validateFieldNotNull("definition", definition))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("definition", definition, NX_CHAR);
		validateFieldEnumeration("definition", definition,
				"NXsqom");

		// validate child group 'instrument' of type NXinstrument
		validateGroup_NXentry_instrument(group.getInstrument());

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}

		// validate child group 'reduction' of type NXprocess
		validateGroup_NXentry_reduction(group.getProcess("reduction"));

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_NXentry_instrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("instrument", NXinstrument.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final IDataset name = group.getName();
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);
		// validate unnamed child group of type NXsource (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_NXentry_instrument_NXsource(source);
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_NXentry_instrument_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate field 'type' of unknown type.
		final IDataset type = group.getType();
		if (!(validateFieldNotNull("type", type))) return;
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
				"UV Plasma Source");

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate field 'probe' of unknown type.
		final IDataset probe = group.getProbe();
		if (!(validateFieldNotNull("probe", probe))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("probe", probe, NX_CHAR);
		validateFieldEnumeration("probe", probe,
				"neutron",
				"x-ray",
				"electron");
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);
	}

	/**
	 * Validate group 'reduction' of type NXprocess.
	 */
	private void validateGroup_NXentry_reduction(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("reduction", NXprocess.class, group))) return;

		// validate field 'program' of type NX_CHAR.
		final IDataset program = group.getProgram();
		if (!(validateFieldNotNull("program", program))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("program", program, NX_CHAR);

		// validate field 'version' of type NX_CHAR.
		final IDataset version = group.getVersion();
		if (!(validateFieldNotNull("version", version))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("version", version, NX_CHAR);

		// validate child group 'input' of type NXparameters
		validateGroup_NXentry_reduction_input(group.getChild("input", NXparameters.class));

		// validate child group 'output' of type NXparameters
		validateGroup_NXentry_reduction_output(group.getChild("output", NXparameters.class));
	}

	/**
	 * Validate group 'input' of type NXparameters.
	 */
	private void validateGroup_NXentry_reduction_input(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("input", NXparameters.class, group))) return;

		// validate field 'filenames' of type NX_CHAR. Note: field not defined in base class.
		final IDataset filenames = group.getDataset("filenames");
		if (!(validateFieldNotNull("filenames", filenames))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("filenames", filenames, NX_CHAR);
	}

	/**
	 * Validate group 'output' of type NXparameters.
	 */
	private void validateGroup_NXentry_reduction_output(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("output", NXparameters.class, group))) return;

	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT. Note: field not defined in base class.
		final IDataset data = group.getDataset("data");
		if (!(validateFieldNotNull("data", data))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_INT);
		validateFieldRank("data", data, 1);
		validateFieldDimensions("data", data, null, "nP");

		// validate field 'qx' of unknown type. Note: field not defined in base class.
		final IDataset qx = group.getDataset("qx");
		if (!(validateFieldNotNull("qx", qx))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("qx", qx, NX_CHAR);
		validateFieldUnits("qx", group.getDataNode("qx"), NX_WAVENUMBER);
		validateFieldRank("qx", qx, 1);
		validateFieldDimensions("qx", qx, null, "nP");

		// validate field 'qy' of unknown type. Note: field not defined in base class.
		final IDataset qy = group.getDataset("qy");
		if (!(validateFieldNotNull("qy", qy))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("qy", qy, NX_CHAR);
		validateFieldUnits("qy", group.getDataNode("qy"), NX_WAVENUMBER);
		validateFieldRank("qy", qy, 1);
		validateFieldDimensions("qy", qy, null, "nP");

		// validate field 'qz' of unknown type. Note: field not defined in base class.
		final IDataset qz = group.getDataset("qz");
		if (!(validateFieldNotNull("qz", qz))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("qz", qz, NX_CHAR);
		validateFieldUnits("qz", group.getDataNode("qz"), NX_WAVENUMBER);
		validateFieldRank("qz", qz, 1);
		validateFieldDimensions("qz", qz, null, "nP");

		// validate field 'en' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset en = group.getDataset("en");
		if (!(validateFieldNotNull("en", en))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("en", en, NX_FLOAT);
		validateFieldUnits("en", group.getDataNode("en"), NX_ENERGY);
		validateFieldRank("en", en, 1);
		validateFieldDimensions("en", en, null, "nP");
	}
}
