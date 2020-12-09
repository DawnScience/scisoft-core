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
 * Validator for the application definition 'NXtomoproc'.
 */
public class NXtomoprocValidator extends AbstractNexusValidator implements NexusApplicationValidator {

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

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		validateFieldNotNull("definition", definition);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldEnumeration("definition", definition,
				"NXtomoproc");

		// validate unnamed child group of type NXinstrument (possibly multiple)
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_entry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_entry_NXsample(sample);
		}

		// validate child group 'reconstruction' of type NXprocess
		validateGroup_entry_reconstruction(group.getProcess());

		// validate child group 'data' of type NXdata
		validateGroup_entry_data(group.getData());
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_entry_NXinstrument(final NXinstrument group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXinstrument.class, group);

		// validate unnamed child group of type NXsource (possibly multiple)
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_entry_NXinstrument_NXsource(source);
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_entry_NXinstrument_NXsource(final NXsource group) throws NexusValidationException {
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
	private void validateGroup_entry_NXsample(final NXsample group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXsample.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
	}

	/**
	 * Validate group 'reconstruction' of type NXprocess.
	 */
	private void validateGroup_entry_reconstruction(final NXprocess group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("reconstruction", NXprocess.class, group);

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

		// validate field 'date' of type NX_DATE_TIME.
		final IDataset date = group.getDate();
		validateFieldNotNull("date", date);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("date", date, NX_DATE_TIME);

		// validate child group 'parameters' of type NXparameters
		validateGroup_entry_reconstruction_parameters(group.getChild("parameters", NXparameters.class));
	}

	/**
	 * Validate group 'parameters' of type NXparameters.
	 */
	private void validateGroup_entry_reconstruction_parameters(final NXparameters group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("parameters", NXparameters.class, group);

		// validate field 'raw_file' of type NX_CHAR. Note: field not defined in base class.
		final IDataset raw_file = group.getDataset("raw_file");
		validateFieldNotNull("raw_file", raw_file);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("raw_file", raw_file, NX_CHAR);
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_entry_data(final NXdata group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("data", NXdata.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT. Note: field not defined in base class.
		final IDataset data = group.getDataset("data");
		validateFieldNotNull("data", data);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_INT);
		validateFieldRank("data", data, 3);
		validateFieldDimensions("data", data, null, "nX", "nX", "nZ");
		// validate attribute 'transform' of field 'data'
		final Attribute data_attr_transform = group.getDataNode("data").getAttribute("transform");
		validateAttributeNotNull("transform", data_attr_transform);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration

		// validate attribute 'offset' of field 'data'
		final Attribute data_attr_offset = group.getDataNode("data").getAttribute("offset");
		validateAttributeNotNull("offset", data_attr_offset);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration

		// validate attribute 'scaling' of field 'data'
		final Attribute data_attr_scaling = group.getDataNode("data").getAttribute("scaling");
		validateAttributeNotNull("scaling", data_attr_scaling);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration


		// validate field 'x' of type NX_FLOAT.
		final IDataset x = group.getX();
		validateFieldNotNull("x", x);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("x", x, NX_FLOAT);
		validateFieldUnits("x", x, NX_ANY);
		validateFieldRank("x", x, 1);
		validateFieldDimensions("x", x, null, "nX");

		// validate field 'y' of type NX_FLOAT.
		final IDataset y = group.getY();
		validateFieldNotNull("y", y);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("y", y, NX_FLOAT);
		validateFieldUnits("y", y, NX_ANY);
		validateFieldRank("y", y, 1);
		validateFieldDimensions("y", y, null, "nY");

		// validate field 'z' of type NX_FLOAT.
		final IDataset z = group.getZ();
		validateFieldNotNull("z", z);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("z", z, NX_FLOAT);
		validateFieldUnits("z", z, NX_ANY);
		validateFieldRank("z", z, 1);
		validateFieldDimensions("z", z, null, "nZ");
	}
}
