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
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXfermi_chopper;
import org.eclipse.dawnsci.nexus.NXsample;

/**
 * Validator for the application definition 'NXspe'.
 */
public class NXspeValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXspeValidator() {
		super(NexusApplicationDefinition.NX_SPE);
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

		// validate field 'program_name' of unknown type.
		final IDataset program_name = group.getProgram_name();
		if (!(validateFieldNotNull("program_name", program_name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("program_name", program_name, NX_CHAR);

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		if (!(validateFieldNotNull("definition", definition))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("definition", definition, NX_CHAR);
		validateFieldEnumeration("definition", definition,
				"NXSPE",
				"NXspe");
		// validate attribute 'version' of field 'definition'
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (!(validateAttributeNotNull("version", definition_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", definition_attr_version, NX_CHAR);


		// validate child group 'NXSPE_info' of type NXcollection
		validateGroup_NXentry_NXSPE_info(group.getCollection("NXSPE_info"));

		// validate child group 'data' of type NXdata
		validateGroup_NXentry_data(group.getData());

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXsample (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSample = group.getAllSample();
		for (final NXsample sample : allSample.values()) {
			validateGroup_NXentry_NXsample(sample);
		}
	}

	/**
	 * Validate group 'NXSPE_info' of type NXcollection.
	 */
	private void validateGroup_NXentry_NXSPE_info(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("NXSPE_info", NXcollection.class, group))) return;

		// validate field 'fixed_energy' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset fixed_energy = group.getDataset("fixed_energy");
		if (!(validateFieldNotNull("fixed_energy", fixed_energy))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("fixed_energy", fixed_energy, NX_FLOAT);
		validateFieldUnits("fixed_energy", group.getDataNode("fixed_energy"), NX_ENERGY);

		// validate field 'ki_over_kf_scaling' of type NX_BOOLEAN. Note: field not defined in base class.
		final IDataset ki_over_kf_scaling = group.getDataset("ki_over_kf_scaling");
		if (!(validateFieldNotNull("ki_over_kf_scaling", ki_over_kf_scaling))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("ki_over_kf_scaling", ki_over_kf_scaling, NX_BOOLEAN);

		// validate field 'psi' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset psi = group.getDataset("psi");
		if (!(validateFieldNotNull("psi", psi))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("psi", psi, NX_FLOAT);
		validateFieldUnits("psi", group.getDataNode("psi"), NX_ANGLE);
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_NXentry_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'azimuthal' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset azimuthal = group.getDataset("azimuthal");
		if (!(validateFieldNotNull("azimuthal", azimuthal))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("azimuthal", azimuthal, NX_FLOAT);
		validateFieldUnits("azimuthal", group.getDataNode("azimuthal"), NX_ANGLE);

		// validate field 'azimuthal_width' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset azimuthal_width = group.getDataset("azimuthal_width");
		if (!(validateFieldNotNull("azimuthal_width", azimuthal_width))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("azimuthal_width", azimuthal_width, NX_FLOAT);
		validateFieldUnits("azimuthal_width", group.getDataNode("azimuthal_width"), NX_ANGLE);

		// validate field 'polar' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset polar = group.getDataset("polar");
		if (!(validateFieldNotNull("polar", polar))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("polar", polar, NX_FLOAT);
		validateFieldUnits("polar", group.getDataNode("polar"), NX_ANGLE);

		// validate field 'polar_width' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset polar_width = group.getDataset("polar_width");
		if (!(validateFieldNotNull("polar_width", polar_width))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("polar_width", polar_width, NX_FLOAT);
		validateFieldUnits("polar_width", group.getDataNode("polar_width"), NX_ANGLE);

		// validate field 'distance' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset distance = group.getDataset("distance");
		if (!(validateFieldNotNull("distance", distance))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("distance", distance, NX_FLOAT);
		validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);

		// validate field 'data' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset data = group.getDataset("data");
		if (!(validateFieldNotNull("data", data))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_NUMBER);

		// validate field 'error' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset error = group.getDataset("error");
		if (!(validateFieldNotNull("error", error))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("error", error, NX_NUMBER);

		// validate field 'energy' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset energy = group.getDataset("energy");
		if (!(validateFieldNotNull("energy", energy))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energy", energy, NX_FLOAT);
		validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate field 'name' of type NX_CHAR.
		final IDataset name = group.getName();
		if (!(validateFieldNotNull("name", name))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("name", name, NX_CHAR);

		// validate unnamed child group of type NXfermi_chopper (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXfermi_chopper.class, false, true);
		final Map<String, NXfermi_chopper> allFermi_chopper = group.getAllFermi_chopper();
		for (final NXfermi_chopper fermi_chopper : allFermi_chopper.values()) {
			validateGroup_NXentry_NXinstrument_NXfermi_chopper(fermi_chopper);
		}
	}

	/**
	 * Validate unnamed group of type NXfermi_chopper.
	 */
	private void validateGroup_NXentry_NXinstrument_NXfermi_chopper(final NXfermi_chopper group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXfermi_chopper.class, group))) return;

		// validate field 'energy' of type NX_NUMBER.
		final IDataset energy = group.getEnergy();
		if (!(validateFieldNotNull("energy", energy))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energy", energy, NX_NUMBER);
		validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'rotation_angle' of type NX_NUMBER.
		final IDataset rotation_angle = group.getRotation_angle();
		if (!(validateFieldNotNull("rotation_angle", rotation_angle))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("rotation_angle", rotation_angle, NX_NUMBER);
		validateFieldUnits("rotation_angle", group.getDataNode("rotation_angle"), NX_ANGLE);

		// validate field 'seblock' of type NX_CHAR. Note: field not defined in base class.
		final IDataset seblock = group.getDataset("seblock");
		if (!(validateFieldNotNull("seblock", seblock))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("seblock", seblock, NX_CHAR);

		// validate field 'temperature' of type NX_NUMBER.
		final IDataset temperature = group.getTemperature();
		if (!(validateFieldNotNull("temperature", temperature))) return;
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("temperature", temperature, NX_NUMBER);
		validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
		validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");
	}
}
