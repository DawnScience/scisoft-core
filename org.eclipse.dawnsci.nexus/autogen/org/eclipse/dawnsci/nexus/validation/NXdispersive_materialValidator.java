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
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXcite;
import org.eclipse.dawnsci.nexus.NXdispersion;
import org.eclipse.dawnsci.nexus.NXdispersion_table;
import org.eclipse.dawnsci.nexus.NXdispersion_function;
import org.eclipse.dawnsci.nexus.NXdispersion_single_parameter;
import org.eclipse.dawnsci.nexus.NXdispersion_repeated_parameter;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXdispersive_material'.
 */
public class NXdispersive_materialValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXdispersive_materialValidator() {
		super(NexusApplicationDefinition.NX_DISPERSIVE_MATERIAL);
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

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXdispersive_material");
		// validate attribute 'version' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (!(validateAttributeNotNull("version", definition_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", definition_attr_version, NX_CHAR);

		// validate attribute 'URL' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_URL = group.getDataNode("definition").getAttribute("URL");
		if (!(validateAttributeNotNull("URL", definition_attr_URL))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("URL", definition_attr_URL, NX_CHAR);

		}

		// validate optional field 'dispersion_type' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset dispersion_type = group.getLazyDataset("dispersion_type");
				if (dispersion_type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dispersion_type", dispersion_type, NX_CHAR);
			validateFieldEnumeration("dispersion_type", dispersion_type,
					"measured",
					"simulated");
		}

		// validate child group 'sample' of type NXsample
		validateGroup_NXentry_sample(group.getSample());

		// validate optional child group 'REFERENCES' of type NXcite
		if (group.getChild("REFERENCES", NXcite.class) != null) {
			validateGroup_NXentry_REFERENCES(group.getChild("REFERENCES", NXcite.class));
		}

		// validate child group 'dispersion_x' of type NXdispersion
		validateGroup_NXentry_dispersion_x(group.getChild("dispersion_x", NXdispersion.class));

		// validate optional child group 'dispersion_y' of type NXdispersion
		if (group.getChild("dispersion_y", NXdispersion.class) != null) {
			validateGroup_NXentry_dispersion_y(group.getChild("dispersion_y", NXdispersion.class));
		}

		// validate optional child group 'dispersion_z' of type NXdispersion
		if (group.getChild("dispersion_z", NXdispersion.class) != null) {
			validateGroup_NXentry_dispersion_z(group.getChild("dispersion_z", NXdispersion.class));
		}
	}

	/**
	 * Validate group 'sample' of type NXsample.
	 */
	private void validateGroup_NXentry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'chemical_formula' of type NX_CHAR.
		final ILazyDataset chemical_formula = group.getLazyDataset("chemical_formula");
		validateFieldNotNull("chemical_formula", chemical_formula);
		if (chemical_formula != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("chemical_formula", chemical_formula, NX_CHAR);
		}

		// validate optional field 'atom_types' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset atom_types = group.getLazyDataset("atom_types");
				if (atom_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("atom_types", atom_types, NX_CHAR);
		}

		// validate optional field 'colloquial_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset colloquial_name = group.getLazyDataset("colloquial_name");
				if (colloquial_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("colloquial_name", colloquial_name, NX_CHAR);
		}

		// validate optional field 'material_phase' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset material_phase = group.getLazyDataset("material_phase");
				if (material_phase != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("material_phase", material_phase, NX_CHAR);
			validateFieldEnumeration("material_phase", material_phase,
					"gas",
					"liquid",
					"solid");
		}

		// validate optional field 'material_phase_comment' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset material_phase_comment = group.getLazyDataset("material_phase_comment");
				if (material_phase_comment != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("material_phase_comment", material_phase_comment, NX_CHAR);
		}

		// validate optional field 'additional_phase_information' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset additional_phase_information = group.getLazyDataset("additional_phase_information");
				if (additional_phase_information != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("additional_phase_information", additional_phase_information, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'REFERENCES' of type NXcite.
	 */
	private void validateGroup_NXentry_REFERENCES(final NXcite group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("REFERENCES", NXcite.class, group))) return;

		// validate field 'text' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset text = group.getLazyDataset("text");
		validateFieldNotNull("text", text);
		if (text != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("text", text, NX_CHAR);
		}

		// validate field 'doi' of type NX_CHAR.
		final ILazyDataset doi = group.getLazyDataset("doi");
		validateFieldNotNull("doi", doi);
		if (doi != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("doi", doi, NX_CHAR);
		}
	}

	/**
	 * Validate group 'dispersion_x' of type NXdispersion.
	 */
	private void validateGroup_NXentry_dispersion_x(final NXdispersion group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("dispersion_x", NXdispersion.class, group))) return;

		// validate field 'model_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model_name = group.getLazyDataset("model_name");
		validateFieldNotNull("model_name", model_name);
		if (model_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model_name", model_name, NX_CHAR);
		}

		// validate unnamed child group of type NXdispersion_table (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_table.class, false, true);
		final Map<String, NXdispersion_table> allDispersion_table = group.getChildren(NXdispersion_table.class);
		for (final NXdispersion_table dispersion_table : allDispersion_table.values()) {
			validateGroup_NXentry_dispersion_x_NXdispersion_table(dispersion_table);
		}

		// validate unnamed child group of type NXdispersion_function (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_function.class, false, true);
		final Map<String, NXdispersion_function> allDispersion_function = group.getChildren(NXdispersion_function.class);
		for (final NXdispersion_function dispersion_function : allDispersion_function.values()) {
			validateGroup_NXentry_dispersion_x_NXdispersion_function(dispersion_function);
		}

		// validate optional child group 'plot' of type NXdata
		if (group.getChild("plot", NXdata.class) != null) {
			validateGroup_NXentry_dispersion_x_plot(group.getChild("plot", NXdata.class));
		}
	}

	/**
	 * Validate optional unnamed group of type NXdispersion_table.
	 */
	private void validateGroup_NXentry_dispersion_x_NXdispersion_table(final NXdispersion_table group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_table.class, group))) return;

		// validate field 'model_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model_name = group.getLazyDataset("model_name");
		validateFieldNotNull("model_name", model_name);
		if (model_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model_name", model_name, NX_CHAR);
		}

		// validate field 'convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset convention = group.getLazyDataset("convention");
		validateFieldNotNull("convention", convention);
		if (convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("convention", convention, NX_CHAR);
		}

		// validate field 'wavelength' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
		validateFieldNotNull("wavelength", wavelength);
		if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_NUMBER);
		}

		// validate optional field 'dielectric_function' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset dielectric_function = group.getLazyDataset("dielectric_function");
				if (dielectric_function != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dielectric_function", dielectric_function, NX_COMPLEX);
		}

		// validate optional field 'refractive_index' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset refractive_index = group.getLazyDataset("refractive_index");
				if (refractive_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("refractive_index", refractive_index, NX_COMPLEX);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdispersion_function.
	 */
	private void validateGroup_NXentry_dispersion_x_NXdispersion_function(final NXdispersion_function group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_function.class, group))) return;

		// validate field 'model_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model_name = group.getLazyDataset("model_name");
		validateFieldNotNull("model_name", model_name);
		if (model_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model_name", model_name, NX_CHAR);
		}

		// validate field 'formula' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset formula = group.getLazyDataset("formula");
		validateFieldNotNull("formula", formula);
		if (formula != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("formula", formula, NX_CHAR);
		}

		// validate field 'convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset convention = group.getLazyDataset("convention");
		validateFieldNotNull("convention", convention);
		if (convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("convention", convention, NX_CHAR);
		}

		// validate optional field 'energy_identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset energy_identifier = group.getLazyDataset("energy_identifier");
				if (energy_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_identifier", energy_identifier, NX_CHAR);
		}

		// validate optional field 'energy_unit' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset energy_unit = group.getLazyDataset("energy_unit");
				if (energy_unit != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_unit", energy_unit, NX_NUMBER);
		}

		// validate optional field 'wavelength_identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset wavelength_identifier = group.getLazyDataset("wavelength_identifier");
				if (wavelength_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_identifier", wavelength_identifier, NX_CHAR);
		}

		// validate optional field 'wavelength_unit' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength_unit = group.getLazyDataset("wavelength_unit");
				if (wavelength_unit != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_unit", wavelength_unit, NX_NUMBER);
		}

		// validate field 'representation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset representation = group.getLazyDataset("representation");
		validateFieldNotNull("representation", representation);
		if (representation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("representation", representation, NX_CHAR);
		}

		// validate unnamed child group of type NXdispersion_single_parameter (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_single_parameter.class, false, true);
		final Map<String, NXdispersion_single_parameter> allDispersion_single_parameter = group.getChildren(NXdispersion_single_parameter.class);
		for (final NXdispersion_single_parameter dispersion_single_parameter : allDispersion_single_parameter.values()) {
			validateGroup_NXentry_dispersion_x_NXdispersion_function_NXdispersion_single_parameter(dispersion_single_parameter);
		}

		// validate unnamed child group of type NXdispersion_repeated_parameter (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_repeated_parameter.class, false, true);
		final Map<String, NXdispersion_repeated_parameter> allDispersion_repeated_parameter = group.getChildren(NXdispersion_repeated_parameter.class);
		for (final NXdispersion_repeated_parameter dispersion_repeated_parameter : allDispersion_repeated_parameter.values()) {
			validateGroup_NXentry_dispersion_x_NXdispersion_function_NXdispersion_repeated_parameter(dispersion_repeated_parameter);
		}
	}

	/**
	 * Validate unnamed group of type NXdispersion_single_parameter.
	 */
	private void validateGroup_NXentry_dispersion_x_NXdispersion_function_NXdispersion_single_parameter(final NXdispersion_single_parameter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_single_parameter.class, group))) return;

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'value' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_NUMBER);
		}
	}

	/**
	 * Validate unnamed group of type NXdispersion_repeated_parameter.
	 */
	private void validateGroup_NXentry_dispersion_x_NXdispersion_function_NXdispersion_repeated_parameter(final NXdispersion_repeated_parameter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_repeated_parameter.class, group))) return;

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'values' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset values = group.getLazyDataset("values");
		validateFieldNotNull("values", values);
		if (values != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("values", values, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'plot' of type NXdata.
	 */
	private void validateGroup_NXentry_dispersion_x_plot(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("plot", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'dispersion_y' of type NXdispersion.
	 */
	private void validateGroup_NXentry_dispersion_y(final NXdispersion group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("dispersion_y", NXdispersion.class, group))) return;

		// validate field 'model_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model_name = group.getLazyDataset("model_name");
		validateFieldNotNull("model_name", model_name);
		if (model_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model_name", model_name, NX_CHAR);
		}

		// validate unnamed child group of type NXdispersion_table (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_table.class, false, true);
		final Map<String, NXdispersion_table> allDispersion_table = group.getChildren(NXdispersion_table.class);
		for (final NXdispersion_table dispersion_table : allDispersion_table.values()) {
			validateGroup_NXentry_dispersion_y_NXdispersion_table(dispersion_table);
		}

		// validate unnamed child group of type NXdispersion_function (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_function.class, false, true);
		final Map<String, NXdispersion_function> allDispersion_function = group.getChildren(NXdispersion_function.class);
		for (final NXdispersion_function dispersion_function : allDispersion_function.values()) {
			validateGroup_NXentry_dispersion_y_NXdispersion_function(dispersion_function);
		}

		// validate optional child group 'plot' of type NXdata
		if (group.getChild("plot", NXdata.class) != null) {
			validateGroup_NXentry_dispersion_y_plot(group.getChild("plot", NXdata.class));
		}
	}

	/**
	 * Validate optional unnamed group of type NXdispersion_table.
	 */
	private void validateGroup_NXentry_dispersion_y_NXdispersion_table(final NXdispersion_table group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_table.class, group))) return;

		// validate field 'model_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model_name = group.getLazyDataset("model_name");
		validateFieldNotNull("model_name", model_name);
		if (model_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model_name", model_name, NX_CHAR);
		}

		// validate field 'convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset convention = group.getLazyDataset("convention");
		validateFieldNotNull("convention", convention);
		if (convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("convention", convention, NX_CHAR);
		}

		// validate field 'wavelength' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
		validateFieldNotNull("wavelength", wavelength);
		if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_NUMBER);
		}

		// validate optional field 'dielectric_function' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset dielectric_function = group.getLazyDataset("dielectric_function");
				if (dielectric_function != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dielectric_function", dielectric_function, NX_COMPLEX);
		}

		// validate optional field 'refractive_index' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset refractive_index = group.getLazyDataset("refractive_index");
				if (refractive_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("refractive_index", refractive_index, NX_COMPLEX);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdispersion_function.
	 */
	private void validateGroup_NXentry_dispersion_y_NXdispersion_function(final NXdispersion_function group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_function.class, group))) return;

		// validate field 'model_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model_name = group.getLazyDataset("model_name");
		validateFieldNotNull("model_name", model_name);
		if (model_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model_name", model_name, NX_CHAR);
		}

		// validate field 'formula' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset formula = group.getLazyDataset("formula");
		validateFieldNotNull("formula", formula);
		if (formula != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("formula", formula, NX_CHAR);
		}

		// validate field 'convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset convention = group.getLazyDataset("convention");
		validateFieldNotNull("convention", convention);
		if (convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("convention", convention, NX_CHAR);
		}

		// validate optional field 'energy_identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset energy_identifier = group.getLazyDataset("energy_identifier");
				if (energy_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_identifier", energy_identifier, NX_CHAR);
		}

		// validate optional field 'energy_unit' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset energy_unit = group.getLazyDataset("energy_unit");
				if (energy_unit != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_unit", energy_unit, NX_NUMBER);
		}

		// validate optional field 'wavelength_identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset wavelength_identifier = group.getLazyDataset("wavelength_identifier");
				if (wavelength_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_identifier", wavelength_identifier, NX_CHAR);
		}

		// validate optional field 'wavelength_unit' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength_unit = group.getLazyDataset("wavelength_unit");
				if (wavelength_unit != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_unit", wavelength_unit, NX_NUMBER);
		}

		// validate field 'representation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset representation = group.getLazyDataset("representation");
		validateFieldNotNull("representation", representation);
		if (representation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("representation", representation, NX_CHAR);
		}

		// validate unnamed child group of type NXdispersion_single_parameter (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_single_parameter.class, false, true);
		final Map<String, NXdispersion_single_parameter> allDispersion_single_parameter = group.getChildren(NXdispersion_single_parameter.class);
		for (final NXdispersion_single_parameter dispersion_single_parameter : allDispersion_single_parameter.values()) {
			validateGroup_NXentry_dispersion_y_NXdispersion_function_NXdispersion_single_parameter(dispersion_single_parameter);
		}

		// validate unnamed child group of type NXdispersion_repeated_parameter (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_repeated_parameter.class, false, true);
		final Map<String, NXdispersion_repeated_parameter> allDispersion_repeated_parameter = group.getChildren(NXdispersion_repeated_parameter.class);
		for (final NXdispersion_repeated_parameter dispersion_repeated_parameter : allDispersion_repeated_parameter.values()) {
			validateGroup_NXentry_dispersion_y_NXdispersion_function_NXdispersion_repeated_parameter(dispersion_repeated_parameter);
		}
	}

	/**
	 * Validate unnamed group of type NXdispersion_single_parameter.
	 */
	private void validateGroup_NXentry_dispersion_y_NXdispersion_function_NXdispersion_single_parameter(final NXdispersion_single_parameter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_single_parameter.class, group))) return;

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'value' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_NUMBER);
		}
	}

	/**
	 * Validate unnamed group of type NXdispersion_repeated_parameter.
	 */
	private void validateGroup_NXentry_dispersion_y_NXdispersion_function_NXdispersion_repeated_parameter(final NXdispersion_repeated_parameter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_repeated_parameter.class, group))) return;

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'values' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset values = group.getLazyDataset("values");
		validateFieldNotNull("values", values);
		if (values != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("values", values, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'plot' of type NXdata.
	 */
	private void validateGroup_NXentry_dispersion_y_plot(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("plot", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate optional group 'dispersion_z' of type NXdispersion.
	 */
	private void validateGroup_NXentry_dispersion_z(final NXdispersion group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("dispersion_z", NXdispersion.class, group))) return;

		// validate field 'model_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model_name = group.getLazyDataset("model_name");
		validateFieldNotNull("model_name", model_name);
		if (model_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model_name", model_name, NX_CHAR);
		}

		// validate unnamed child group of type NXdispersion_table (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_table.class, false, true);
		final Map<String, NXdispersion_table> allDispersion_table = group.getChildren(NXdispersion_table.class);
		for (final NXdispersion_table dispersion_table : allDispersion_table.values()) {
			validateGroup_NXentry_dispersion_z_NXdispersion_table(dispersion_table);
		}

		// validate unnamed child group of type NXdispersion_function (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_function.class, false, true);
		final Map<String, NXdispersion_function> allDispersion_function = group.getChildren(NXdispersion_function.class);
		for (final NXdispersion_function dispersion_function : allDispersion_function.values()) {
			validateGroup_NXentry_dispersion_z_NXdispersion_function(dispersion_function);
		}

		// validate optional child group 'plot' of type NXdata
		if (group.getChild("plot", NXdata.class) != null) {
			validateGroup_NXentry_dispersion_z_plot(group.getChild("plot", NXdata.class));
		}
	}

	/**
	 * Validate optional unnamed group of type NXdispersion_table.
	 */
	private void validateGroup_NXentry_dispersion_z_NXdispersion_table(final NXdispersion_table group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_table.class, group))) return;

		// validate field 'model_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model_name = group.getLazyDataset("model_name");
		validateFieldNotNull("model_name", model_name);
		if (model_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model_name", model_name, NX_CHAR);
		}

		// validate field 'convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset convention = group.getLazyDataset("convention");
		validateFieldNotNull("convention", convention);
		if (convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("convention", convention, NX_CHAR);
		}

		// validate field 'wavelength' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength = group.getLazyDataset("wavelength");
		validateFieldNotNull("wavelength", wavelength);
		if (wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength", wavelength, NX_NUMBER);
		}

		// validate optional field 'dielectric_function' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset dielectric_function = group.getLazyDataset("dielectric_function");
				if (dielectric_function != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dielectric_function", dielectric_function, NX_COMPLEX);
		}

		// validate optional field 'refractive_index' of type NX_COMPLEX. Note: field not defined in base class.
		final ILazyDataset refractive_index = group.getLazyDataset("refractive_index");
				if (refractive_index != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("refractive_index", refractive_index, NX_COMPLEX);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdispersion_function.
	 */
	private void validateGroup_NXentry_dispersion_z_NXdispersion_function(final NXdispersion_function group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_function.class, group))) return;

		// validate field 'model_name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model_name = group.getLazyDataset("model_name");
		validateFieldNotNull("model_name", model_name);
		if (model_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model_name", model_name, NX_CHAR);
		}

		// validate field 'formula' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset formula = group.getLazyDataset("formula");
		validateFieldNotNull("formula", formula);
		if (formula != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("formula", formula, NX_CHAR);
		}

		// validate field 'convention' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset convention = group.getLazyDataset("convention");
		validateFieldNotNull("convention", convention);
		if (convention != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("convention", convention, NX_CHAR);
		}

		// validate optional field 'energy_identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset energy_identifier = group.getLazyDataset("energy_identifier");
				if (energy_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_identifier", energy_identifier, NX_CHAR);
		}

		// validate optional field 'energy_unit' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset energy_unit = group.getLazyDataset("energy_unit");
				if (energy_unit != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy_unit", energy_unit, NX_NUMBER);
		}

		// validate optional field 'wavelength_identifier' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset wavelength_identifier = group.getLazyDataset("wavelength_identifier");
				if (wavelength_identifier != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_identifier", wavelength_identifier, NX_CHAR);
		}

		// validate optional field 'wavelength_unit' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength_unit = group.getLazyDataset("wavelength_unit");
				if (wavelength_unit != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_unit", wavelength_unit, NX_NUMBER);
		}

		// validate field 'representation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset representation = group.getLazyDataset("representation");
		validateFieldNotNull("representation", representation);
		if (representation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("representation", representation, NX_CHAR);
		}

		// validate unnamed child group of type NXdispersion_single_parameter (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_single_parameter.class, false, true);
		final Map<String, NXdispersion_single_parameter> allDispersion_single_parameter = group.getChildren(NXdispersion_single_parameter.class);
		for (final NXdispersion_single_parameter dispersion_single_parameter : allDispersion_single_parameter.values()) {
			validateGroup_NXentry_dispersion_z_NXdispersion_function_NXdispersion_single_parameter(dispersion_single_parameter);
		}

		// validate unnamed child group of type NXdispersion_repeated_parameter (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdispersion_repeated_parameter.class, false, true);
		final Map<String, NXdispersion_repeated_parameter> allDispersion_repeated_parameter = group.getChildren(NXdispersion_repeated_parameter.class);
		for (final NXdispersion_repeated_parameter dispersion_repeated_parameter : allDispersion_repeated_parameter.values()) {
			validateGroup_NXentry_dispersion_z_NXdispersion_function_NXdispersion_repeated_parameter(dispersion_repeated_parameter);
		}
	}

	/**
	 * Validate unnamed group of type NXdispersion_single_parameter.
	 */
	private void validateGroup_NXentry_dispersion_z_NXdispersion_function_NXdispersion_single_parameter(final NXdispersion_single_parameter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_single_parameter.class, group))) return;

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'value' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset value = group.getLazyDataset("value");
		validateFieldNotNull("value", value);
		if (value != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("value", value, NX_NUMBER);
		}
	}

	/**
	 * Validate unnamed group of type NXdispersion_repeated_parameter.
	 */
	private void validateGroup_NXentry_dispersion_z_NXdispersion_function_NXdispersion_repeated_parameter(final NXdispersion_repeated_parameter group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdispersion_repeated_parameter.class, group))) return;

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate field 'values' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset values = group.getLazyDataset("values");
		validateFieldNotNull("values", values);
		if (values != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("values", values, NX_NUMBER);
		}
	}

	/**
	 * Validate optional group 'plot' of type NXdata.
	 */
	private void validateGroup_NXentry_dispersion_z_plot(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("plot", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}
}
