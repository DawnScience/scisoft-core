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

import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;
import org.eclipse.january.dataset.ILazyDataset;
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

	public NXtomoprocValidator() {
		super(NexusApplicationDefinition.NX_TOMOPROC);
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

		// validate field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXtomoproc");
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_entry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_entry_NXsample(sample);
		}

		// validate child group 'reconstruction' of type NXprocess
		validateGroup_entry_reconstruction(group.getProcess("reconstruction"));

		// validate child group 'data' of type NXdata
		validateGroup_entry_data(group.getData());
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_entry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate unnamed child group of type NXsource (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSource = group.getAllSource();
		for (final NXsource source : allSource.values()) {
			validateGroup_entry_NXinstrument_NXsource(source);
		}
	}

	/**
	 * Validate unnamed group of type NXsource.
	 */
	private void validateGroup_entry_NXinstrument_NXsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
		validateFieldNotNull("type", type);
		if (type != null) {
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
		}

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'probe' of type NX_CHAR.
		final ILazyDataset probe = group.getLazyDataset("probe");
		validateFieldNotNull("probe", probe);
		if (probe != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("probe", probe, NX_CHAR);
			validateFieldEnumeration("probe", probe,
					"neutron",
					"x-ray",
					"electron");
		}
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_entry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}
	}

	/**
	 * Validate group 'reconstruction' of type NXprocess.
	 */
	private void validateGroup_entry_reconstruction(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("reconstruction", NXprocess.class, group))) return;

		// validate field 'program' of type NX_CHAR.
		final ILazyDataset program = group.getLazyDataset("program");
		validateFieldNotNull("program", program);
		if (program != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("program", program, NX_CHAR);
		}

		// validate field 'version' of type NX_CHAR.
		final ILazyDataset version = group.getLazyDataset("version");
		validateFieldNotNull("version", version);
		if (version != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("version", version, NX_CHAR);
		}

		// validate field 'date' of type NX_DATE_TIME.
		final ILazyDataset date = group.getLazyDataset("date");
		validateFieldNotNull("date", date);
		if (date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("date", date, NX_DATE_TIME);
		}

		// validate child group 'parameters' of type NXparameters
		validateGroup_entry_reconstruction_parameters(group.getChild("parameters", NXparameters.class));
	}

	/**
	 * Validate group 'parameters' of type NXparameters.
	 */
	private void validateGroup_entry_reconstruction_parameters(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("parameters", NXparameters.class, group))) return;

		// validate field 'raw_file' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset raw_file = group.getLazyDataset("raw_file");
		validateFieldNotNull("raw_file", raw_file);
		if (raw_file != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("raw_file", raw_file, NX_CHAR);
		}
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_entry_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'data' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_INT);
			validateFieldRank("data", data, 3);
			validateFieldDimensions("data", data, null, "nX", "nX", "nZ");
		// validate attribute 'transform' of field 'data' of type NX_CHAR.
		final Attribute data_attr_transform = group.getDataNode("data").getAttribute("transform");
		if (!(validateAttributeNotNull("transform", data_attr_transform))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transform", data_attr_transform, NX_CHAR);

		// validate attribute 'offset' of field 'data' of type NX_CHAR.
		final Attribute data_attr_offset = group.getDataNode("data").getAttribute("offset");
		if (!(validateAttributeNotNull("offset", data_attr_offset))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("offset", data_attr_offset, NX_CHAR);

		// validate attribute 'scaling' of field 'data' of type NX_CHAR.
		final Attribute data_attr_scaling = group.getDataNode("data").getAttribute("scaling");
		if (!(validateAttributeNotNull("scaling", data_attr_scaling))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("scaling", data_attr_scaling, NX_CHAR);

		}

		// validate field 'x' of type NX_FLOAT.
		final ILazyDataset x = group.getLazyDataset("x");
		validateFieldNotNull("x", x);
		if (x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x", x, NX_FLOAT);
			validateFieldUnits("x", group.getDataNode("x"), NX_ANY);
			validateFieldRank("x", x, 1);
			validateFieldDimensions("x", x, null, "nX");
		}

		// validate field 'y' of type NX_FLOAT.
		final ILazyDataset y = group.getLazyDataset("y");
		validateFieldNotNull("y", y);
		if (y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y", y, NX_FLOAT);
			validateFieldUnits("y", group.getDataNode("y"), NX_ANY);
			validateFieldRank("y", y, 1);
			validateFieldDimensions("y", y, null, "nY");
		}

		// validate field 'z' of type NX_FLOAT.
		final ILazyDataset z = group.getLazyDataset("z");
		validateFieldNotNull("z", z);
		if (z != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z", z, NX_FLOAT);
			validateFieldUnits("z", group.getDataNode("z"), NX_ANY);
			validateFieldRank("z", z, 1);
			validateFieldDimensions("z", z, null, "nZ");
		}
	}
}
