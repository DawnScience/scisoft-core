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
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXfermi_chopper;
import org.eclipse.dawnsci.nexus.NXsample;

/**
 * Validator for the application definition 'NXspe'.
 */
public class NXspeValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	@Override
	public void validate(NXroot root) throws NexusValidationException {
		// validate unnamed child group of type NXentry (possibly multiple)
		validateUnnamedGroupOccurrences(root, NXentry.class, false, true);
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

		// validate field 'program_name' of unknown type.
		final IDataset program_name = group.getProgram_name();
		validateFieldNotNull("program_name", program_name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("program_name", program_name, NX_CHAR);

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		validateFieldNotNull("definition", definition);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("definition", definition, NX_CHAR);
		validateFieldEnumeration("definition", definition,
				"NXSPE",
				"NXspe");
		// validate attribute 'version' of field 'definition'
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		validateAttributeNotNull("version", definition_attr_version);
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
	private void validateGroup_NXentry_NXSPE_info(final NXcollection group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("NXSPE_info", NXcollection.class, group);

		// validate field 'fixed_energy' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset fixed_energy = group.getDataset("fixed_energy");
		validateFieldNotNull("fixed_energy", fixed_energy);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("fixed_energy", fixed_energy, NX_FLOAT);
		validateFieldUnits("fixed_energy", group.getDataNode("fixed_energy"), NX_ENERGY);

		// validate field 'ki_over_kf_scaling' of type NX_BOOLEAN. Note: field not defined in base class.
		final IDataset ki_over_kf_scaling = group.getDataset("ki_over_kf_scaling");
		validateFieldNotNull("ki_over_kf_scaling", ki_over_kf_scaling);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("ki_over_kf_scaling", ki_over_kf_scaling, NX_BOOLEAN);

		// validate field 'psi' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset psi = group.getDataset("psi");
		validateFieldNotNull("psi", psi);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("psi", psi, NX_FLOAT);
		validateFieldUnits("psi", group.getDataNode("psi"), NX_ANGLE);
	}

	/**
	 * Validate group 'data' of type NXdata.
	 */
	private void validateGroup_NXentry_data(final NXdata group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull("data", NXdata.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'azimuthal' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset azimuthal = group.getDataset("azimuthal");
		validateFieldNotNull("azimuthal", azimuthal);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("azimuthal", azimuthal, NX_FLOAT);
		validateFieldUnits("azimuthal", group.getDataNode("azimuthal"), NX_ANGLE);

		// validate field 'azimuthal_width' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset azimuthal_width = group.getDataset("azimuthal_width");
		validateFieldNotNull("azimuthal_width", azimuthal_width);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("azimuthal_width", azimuthal_width, NX_FLOAT);
		validateFieldUnits("azimuthal_width", group.getDataNode("azimuthal_width"), NX_ANGLE);

		// validate field 'polar' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset polar = group.getDataset("polar");
		validateFieldNotNull("polar", polar);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("polar", polar, NX_FLOAT);
		validateFieldUnits("polar", group.getDataNode("polar"), NX_ANGLE);

		// validate field 'polar_width' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset polar_width = group.getDataset("polar_width");
		validateFieldNotNull("polar_width", polar_width);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("polar_width", polar_width, NX_FLOAT);
		validateFieldUnits("polar_width", group.getDataNode("polar_width"), NX_ANGLE);

		// validate field 'distance' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset distance = group.getDataset("distance");
		validateFieldNotNull("distance", distance);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("distance", distance, NX_FLOAT);
		validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);

		// validate field 'data' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset data = group.getDataset("data");
		validateFieldNotNull("data", data);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("data", data, NX_NUMBER);

		// validate field 'error' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset error = group.getDataset("error");
		validateFieldNotNull("error", error);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("error", error, NX_NUMBER);

		// validate field 'energy' of type NX_FLOAT. Note: field not defined in base class.
		final IDataset energy = group.getDataset("energy");
		validateFieldNotNull("energy", energy);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energy", energy, NX_FLOAT);
		validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXinstrument.class, group);

		// validate field 'name' of type NX_CHAR.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
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
	private void validateGroup_NXentry_NXinstrument_NXfermi_chopper(final NXfermi_chopper group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXfermi_chopper.class, group);

		// validate field 'energy' of type NX_NUMBER.
		final IDataset energy = group.getEnergy();
		validateFieldNotNull("energy", energy);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("energy", energy, NX_NUMBER);
		validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXsample.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'rotation_angle' of type NX_NUMBER.
		final IDataset rotation_angle = group.getRotation_angle();
		validateFieldNotNull("rotation_angle", rotation_angle);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("rotation_angle", rotation_angle, NX_NUMBER);
		validateFieldUnits("rotation_angle", group.getDataNode("rotation_angle"), NX_ANGLE);

		// validate field 'seblock' of type NX_CHAR. Note: field not defined in base class.
		final IDataset seblock = group.getDataset("seblock");
		validateFieldNotNull("seblock", seblock);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("seblock", seblock, NX_CHAR);

		// validate field 'temperature' of type NX_NUMBER.
		final IDataset temperature = group.getTemperature();
		validateFieldNotNull("temperature", temperature);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("temperature", temperature, NX_NUMBER);
		validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
		validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");
	}
}
