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

	@Override
	public void validate(NXroot root) throws NexusValidationException {
		// validate unnamed child group of type NXentry (possibly multiple)
		final Map<String, NXentry> allEntry = root.getAllEntry();
		for (final NXentry entry : allEntry.values()) {
			validateGroup_NXentry(entry);
		}
	}

	@Override
	public void validate(NXentry entry) throws NexusValidationException {
		validateGroup_NXentry(entry);
	}

	@Override
	public void validate(NXsubentry subentry) throws NexusValidationException {
		validateGroup_NXentry(subentry);
	}


	/**
	 * Validate unnamed group of type NXentry.
	 */
	private void validateGroup_NXentry(final NXsubentry group) throws NexusValidationException {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		validateGroupNotNull(null, NXentry.class, group);

		// validate attribute 'entry'
		final Attribute entry_attr = group.getAttribute("entry");
		validateAttributeNotNull("entry", entry_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration

		// validate field 'title' of unknown type.
		final IDataset title = group.getTitle();
		validateFieldNotNull("title", title);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		validateFieldNotNull("definition", definition);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldEnumeration("definition", definition,
				"NXsqom");

		// validate child group 'instrument' of type NXinstrument
		validateGroup_NXentry_instrument(group.getInstrument());

		// validate unnamed child group of type NXsample (possibly multiple)
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}

		// validate child group 'reduction' of type NXprocess
		validateGroup_NXentry_reduction(group.getProcess());

		// validate unnamed child group of type NXdata (possibly multiple)
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}
	}

	/**
	 * Validate group 'instrument' of type NXinstrument.
	 */
	private void validateGroup_NXentry_instrument(final NXinstrument group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("instrument", NXinstrument.class, group);

		// validate field 'name' of type NX_CHAR.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);
		// validate unnamed child group of type NXsource (possibly multiple)
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_NXentry_instrument_NXsource(source);
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_NXentry_instrument_NXsource(final NXsource group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXsource.class, group);

		// validate field 'type' of unknown type.
		final IDataset type = group.getType();
		validateFieldNotNull("type", type);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
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
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions

		// validate field 'probe' of unknown type.
		final IDataset probe = group.getProbe();
		validateFieldNotNull("probe", probe);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldEnumeration("probe", probe,
				"neutron",
				"x-ray",
				"electron");
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXsample.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
	}

	/**
	 * Validate group 'reduction' of type NXprocess.
	 */
	private void validateGroup_NXentry_reduction(final NXprocess group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("reduction", NXprocess.class, group);

		// validate field 'program' of type NX_CHAR.
		final IDataset program = group.getProgram();
		validateFieldNotNull("program", program);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("program", program, NX_CHAR);

		// validate field 'version' of type NX_CHAR.
		final IDataset version = group.getVersion();
		validateFieldNotNull("version", version);
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
	private void validateGroup_NXentry_reduction_input(final NXparameters group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("input", NXparameters.class, group);

		// validate field 'filenames' of type NX_CHAR. Note: field not defined in base class.
		final IDataset filenames = group.getDataset("filenames");
		validateFieldNotNull("filenames", filenames);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("filenames", filenames, NX_CHAR);
	}

	/**
	 * Validate group 'output' of type NXparameters.
	 */
	private void validateGroup_NXentry_reduction_output(final NXparameters group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("output", NXparameters.class, group);

	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXdata.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT. Note: field not defined in base class.
		final IDataset data = group.getDataset("data");
		validateFieldNotNull("data", data);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_INT);
		validateFieldRank("data", data, 1);
		validateFieldDimensions("data", data, null, "nP");

		// validate field 'qx' of unknown type. Note: field not defined in base class.
		final IDataset qx = group.getDataset("qx");
		validateFieldNotNull("qx", qx);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldUnits("qx", group.getDataNode("qx"), NX_WAVENUMBER);
		validateFieldRank("qx", qx, 1);
		validateFieldDimensions("qx", qx, null, "nP");

		// validate field 'qy' of unknown type. Note: field not defined in base class.
		final IDataset qy = group.getDataset("qy");
		validateFieldNotNull("qy", qy);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldUnits("qy", group.getDataNode("qy"), NX_WAVENUMBER);
		validateFieldRank("qy", qy, 1);
		validateFieldDimensions("qy", qy, null, "nP");

		// validate field 'qz' of unknown type. Note: field not defined in base class.
		final IDataset qz = group.getDataset("qz");
		validateFieldNotNull("qz", qz);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldUnits("qz", group.getDataNode("qz"), NX_WAVENUMBER);
		validateFieldRank("qz", qz, 1);
		validateFieldDimensions("qz", qz, null, "nP");

		// validate field 'en' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset en = group.getDataset("en");
		validateFieldNotNull("en", en);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("en", en, NX_FLOAT);
		validateFieldUnits("en", group.getDataNode("en"), NX_ENERGY);
		validateFieldRank("en", en, 1);
		validateFieldDimensions("en", en, null, "nP");
	}
}
