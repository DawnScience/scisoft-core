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
import org.eclipse.dawnsci.nexus.NXcs_profiling;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXprogram;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXparameters;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXmicrostructure;
import org.eclipse.dawnsci.nexus.NXcg_grid;

/**
 * Validator for the application definition 'NXmicrostructure_score_config'.
 */
public class NXmicrostructure_score_configValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXmicrostructure_score_configValidator() {
		super(NexusApplicationDefinition.NX_MICROSTRUCTURE_SCORE_CONFIG);
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
					"NXmicrostructure_score_config");
		}

		// validate field 'identifier_simulation' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset identifier_simulation = group.getLazyDataset("identifier_simulation");
		validateFieldNotNull("identifier_simulation", identifier_simulation);
		if (identifier_simulation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("identifier_simulation", identifier_simulation, NX_UINT);
		}

		// validate optional field 'description' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate field 'start_time' of type NX_DATE_TIME.
		final ILazyDataset start_time = group.getLazyDataset("start_time");
		validateFieldNotNull("start_time", start_time);
		if (start_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("start_time", start_time, NX_DATE_TIME);
		}

		// validate optional child group 'profiling' of type NXcs_profiling
		if (group.getChild("profiling", NXcs_profiling.class) != null) {
			validateGroup_NXentry_profiling(group.getChild("profiling", NXcs_profiling.class));
		}

		// validate optional child group 'userID' of type NXuser
		if (group.getUser("userID") != null) {
			validateGroup_NXentry_userID(group.getUser("userID"));
		}

		// validate optional child group 'sample' of type NXsample
		if (group.getSample() != null) {
			validateGroup_NXentry_sample(group.getSample());
		}

		// validate optional child group 'program1' of type NXprogram
		if (group.getChild("program1", NXprogram.class) != null) {
			validateGroup_NXentry_program1(group.getChild("program1", NXprogram.class));
		}

		// validate optional child group 'environment' of type NXcollection
		if (group.getCollection("environment") != null) {
			validateGroup_NXentry_environment(group.getCollection("environment"));
		}

		// validate child group 'material' of type NXparameters
		validateGroup_NXentry_material(group.getParameters("material"));

		// validate child group 'deformation' of type NXparameters
		validateGroup_NXentry_deformation(group.getParameters("deformation"));

		// validate child group 'nucleation' of type NXparameters
		validateGroup_NXentry_nucleation(group.getParameters("nucleation"));

		// validate child group 'grain_boundary_mobility' of type NXparameters
		validateGroup_NXentry_grain_boundary_mobility(group.getParameters("grain_boundary_mobility"));

		// validate child group 'stored_energy_recovery' of type NXparameters
		validateGroup_NXentry_stored_energy_recovery(group.getParameters("stored_energy_recovery"));

		// validate child group 'dispersoid_drag' of type NXparameters
		validateGroup_NXentry_dispersoid_drag(group.getParameters("dispersoid_drag"));

		// validate child group 'component_analysis' of type NXparameters
		validateGroup_NXentry_component_analysis(group.getParameters("component_analysis"));

		// validate child group 'time_temperature' of type NXdata
		validateGroup_NXentry_time_temperature(group.getData("time_temperature"));

		// validate child group 'discretization' of type NXmicrostructure
		validateGroup_NXentry_discretization(group.getChild("discretization", NXmicrostructure.class));

		// validate child group 'numerics' of type NXparameters
		validateGroup_NXentry_numerics(group.getParameters("numerics"));

		// validate child group 'solitary_unit' of type NXparameters
		validateGroup_NXentry_solitary_unit(group.getParameters("solitary_unit"));
	}

	/**
	 * Validate optional group 'profiling' of type NXcs_profiling.
	 */
	private void validateGroup_NXentry_profiling(final NXcs_profiling group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("profiling", NXcs_profiling.class, group))) return;

	}

	/**
	 * Validate optional group 'userID' of type NXuser.
	 */
	private void validateGroup_NXentry_userID(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("userID", NXuser.class, group))) return;

	}

	/**
	 * Validate optional group 'sample' of type NXsample.
	 */
	private void validateGroup_NXentry_sample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sample", NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'dimensionality' of type NX_POSINT. Note: field not defined in base class.
		final ILazyDataset dimensionality = group.getLazyDataset("dimensionality");
		validateFieldNotNull("dimensionality", dimensionality);
		if (dimensionality != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dimensionality", dimensionality, NX_POSINT);
			validateFieldUnits("dimensionality", group.getDataNode("dimensionality"), NX_UNITLESS);
			validateFieldEnumeration("dimensionality", dimensionality,
					"1",
					"2",
					"3");
		}

		// validate field 'is_simulation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset is_simulation = group.getLazyDataset("is_simulation");
		validateFieldNotNull("is_simulation", is_simulation);
		if (is_simulation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("is_simulation", is_simulation, NX_CHAR);
			validateFieldEnumeration("is_simulation", is_simulation,
					"experiment",
					"simulation");
		}

		// validate field 'atom_types' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset atom_types = group.getLazyDataset("atom_types");
		validateFieldNotNull("atom_types", atom_types);
		if (atom_types != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("atom_types", atom_types, NX_CHAR);
		}
	}

	/**
	 * Validate optional group 'program1' of type NXprogram.
	 */
	private void validateGroup_NXentry_program1(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("program1", NXprogram.class, group))) return;

		// validate field 'program' of type NX_CHAR.
		final ILazyDataset program = group.getLazyDataset("program");
		validateFieldNotNull("program", program);
		if (program != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("program", program, NX_CHAR);
		// validate attribute 'version' of field 'program' of type NX_CHAR.
		final Attribute program_attr_version = group.getDataNode("program").getAttribute("version");
		if (!(validateAttributeNotNull("version", program_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", program_attr_version, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'environment' of type NXcollection.
	 */
	private void validateGroup_NXentry_environment(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("environment", NXcollection.class, group))) return;

		// validate child group 'programID' of type NXprogram
		validateGroup_NXentry_environment_programID(group.getChild("programID", NXprogram.class));
	}

	/**
	 * Validate group 'programID' of type NXprogram.
	 */
	private void validateGroup_NXentry_environment_programID(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("programID", NXprogram.class, group))) return;

		// validate field 'program' of type NX_CHAR.
		final ILazyDataset program = group.getLazyDataset("program");
		validateFieldNotNull("program", program);
		if (program != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("program", program, NX_CHAR);
		// validate attribute 'version' of field 'program' of type NX_CHAR.
		final Attribute program_attr_version = group.getDataNode("program").getAttribute("version");
		if (!(validateAttributeNotNull("version", program_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", program_attr_version, NX_CHAR);

		}
	}

	/**
	 * Validate group 'material' of type NXparameters.
	 */
	private void validateGroup_NXentry_material(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("material", NXparameters.class, group))) return;

		// validate field 'shear_modulus' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset shear_modulus = group.getLazyDataset("shear_modulus");
		validateFieldNotNull("shear_modulus", shear_modulus);
		if (shear_modulus != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("shear_modulus", shear_modulus, NX_FLOAT);
			validateFieldUnits("shear_modulus", group.getDataNode("shear_modulus"), NX_PRESSURE);
		}

		// validate field 'burgers_vector' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset burgers_vector = group.getLazyDataset("burgers_vector");
		validateFieldNotNull("burgers_vector", burgers_vector);
		if (burgers_vector != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("burgers_vector", burgers_vector, NX_FLOAT);
			validateFieldUnits("burgers_vector", group.getDataNode("burgers_vector"), NX_LENGTH);
		}

		// validate field 'melting_temperature' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset melting_temperature = group.getLazyDataset("melting_temperature");
		validateFieldNotNull("melting_temperature", melting_temperature);
		if (melting_temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("melting_temperature", melting_temperature, NX_FLOAT);
			validateFieldUnits("melting_temperature", group.getDataNode("melting_temperature"), NX_TEMPERATURE);
		}
	}

	/**
	 * Validate group 'deformation' of type NXparameters.
	 */
	private void validateGroup_NXentry_deformation(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("deformation", NXparameters.class, group))) return;

		// validate field 'model' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
			validateFieldEnumeration("model", model,
					"cuboidal",
					"poisson_voronoi",
					"ebsd",
					"damask");
		}

		// validate field 'extent' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset extent = group.getLazyDataset("extent");
		validateFieldNotNull("extent", extent);
		if (extent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extent", extent, NX_FLOAT);
			validateFieldUnits("extent", group.getDataNode("extent"), NX_LENGTH);
			validateFieldRank("extent", extent, 1);
			validateFieldDimensions("extent", extent, null, 3);
		}

		// validate field 'diameter' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset diameter = group.getLazyDataset("diameter");
		validateFieldNotNull("diameter", diameter);
		if (diameter != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("diameter", diameter, NX_FLOAT);
			validateFieldUnits("diameter", group.getDataNode("diameter"), NX_LENGTH);
		}

		// validate optional child group 'ensemble' of type NXparameters
		if (group.getChild("ensemble", NXparameters.class) != null) {
			validateGroup_NXentry_deformation_ensemble(group.getChild("ensemble", NXparameters.class));
		}

		// validate optional child group 'ebsd' of type NXnote
		if (group.getChild("ebsd", NXnote.class) != null) {
			validateGroup_NXentry_deformation_ebsd(group.getChild("ebsd", NXnote.class));
		}

		// validate optional child group 'damask' of type NXnote
		if (group.getChild("damask", NXnote.class) != null) {
			validateGroup_NXentry_deformation_damask(group.getChild("damask", NXnote.class));
		}
	}

	/**
	 * Validate optional group 'ensemble' of type NXparameters.
	 */
	private void validateGroup_NXentry_deformation_ensemble(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ensemble", NXparameters.class, group))) return;

		// validate field 'bunge_euler' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset bunge_euler = group.getLazyDataset("bunge_euler");
		validateFieldNotNull("bunge_euler", bunge_euler);
		if (bunge_euler != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("bunge_euler", bunge_euler, NX_FLOAT);
			validateFieldUnits("bunge_euler", group.getDataNode("bunge_euler"), NX_ANGLE);
			validateFieldRank("bunge_euler", bunge_euler, 2);
			validateFieldDimensions("bunge_euler", bunge_euler, null, "n_dg_ori", 3);
		}

		// validate field 'stored_energy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset stored_energy = group.getLazyDataset("stored_energy");
		validateFieldNotNull("stored_energy", stored_energy);
		if (stored_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("stored_energy", stored_energy, NX_FLOAT);
			validateFieldUnits("stored_energy", group.getDataNode("stored_energy"), NX_ANY);
			validateFieldRank("stored_energy", stored_energy, 1);
			validateFieldDimensions("stored_energy", stored_energy, null, "n_dg_ori");
		}
	}

	/**
	 * Validate optional group 'ebsd' of type NXnote.
	 */
	private void validateGroup_NXentry_deformation_ebsd(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ebsd", NXnote.class, group))) return;

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
		validateFieldNotNull("algorithm", algorithm);
		if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}

		// validate field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
		validateFieldNotNull("checksum", checksum);
		if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}

		// validate field 'stepsize' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset stepsize = group.getLazyDataset("stepsize");
		validateFieldNotNull("stepsize", stepsize);
		if (stepsize != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("stepsize", stepsize, NX_FLOAT);
			validateFieldUnits("stepsize", group.getDataNode("stepsize"), NX_LENGTH);
			validateFieldRank("stepsize", stepsize, 1);
			validateFieldDimensions("stepsize", stepsize, null, "d");
		}
	}

	/**
	 * Validate optional group 'damask' of type NXnote.
	 */
	private void validateGroup_NXentry_deformation_damask(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("damask", NXnote.class, group))) return;

		// validate field 'file_name' of type NX_CHAR.
		final ILazyDataset file_name = group.getLazyDataset("file_name");
		validateFieldNotNull("file_name", file_name);
		if (file_name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("file_name", file_name, NX_CHAR);
		}

		// validate field 'algorithm' of type NX_CHAR.
		final ILazyDataset algorithm = group.getLazyDataset("algorithm");
		validateFieldNotNull("algorithm", algorithm);
		if (algorithm != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("algorithm", algorithm, NX_CHAR);
		}

		// validate field 'checksum' of type NX_CHAR.
		final ILazyDataset checksum = group.getLazyDataset("checksum");
		validateFieldNotNull("checksum", checksum);
		if (checksum != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("checksum", checksum, NX_CHAR);
		}
	}

	/**
	 * Validate group 'nucleation' of type NXparameters.
	 */
	private void validateGroup_NXentry_nucleation(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("nucleation", NXparameters.class, group))) return;

		// validate field 'spatial_distribution' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset spatial_distribution = group.getLazyDataset("spatial_distribution");
		validateFieldNotNull("spatial_distribution", spatial_distribution);
		if (spatial_distribution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("spatial_distribution", spatial_distribution, NX_CHAR);
			validateFieldEnumeration("spatial_distribution", spatial_distribution,
					"csr",
					"damask");
		}

		// validate field 'incubation_time' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset incubation_time = group.getLazyDataset("incubation_time");
		validateFieldNotNull("incubation_time", incubation_time);
		if (incubation_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incubation_time", incubation_time, NX_CHAR);
			validateFieldEnumeration("incubation_time", incubation_time,
					"site_saturation");
		}

		// validate field 'orientation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset orientation = group.getLazyDataset("orientation");
		validateFieldNotNull("orientation", orientation);
		if (orientation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("orientation", orientation, NX_CHAR);
			validateFieldEnumeration("orientation", orientation,
					"ensemble",
					"random",
					"damask");
		}

		// validate child group 'ensemble' of type NXparameters
		validateGroup_NXentry_nucleation_ensemble(group.getChild("ensemble", NXparameters.class));
	}

	/**
	 * Validate group 'ensemble' of type NXparameters.
	 */
	private void validateGroup_NXentry_nucleation_ensemble(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("ensemble", NXparameters.class, group))) return;

		// validate field 'bunge_euler' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset bunge_euler = group.getLazyDataset("bunge_euler");
		validateFieldNotNull("bunge_euler", bunge_euler);
		if (bunge_euler != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("bunge_euler", bunge_euler, NX_FLOAT);
			validateFieldUnits("bunge_euler", group.getDataNode("bunge_euler"), NX_ANGLE);
			validateFieldRank("bunge_euler", bunge_euler, 2);
			validateFieldDimensions("bunge_euler", bunge_euler, null, "n_rx_ori", 3);
		}

		// validate field 'incubation_time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset incubation_time = group.getLazyDataset("incubation_time");
		validateFieldNotNull("incubation_time", incubation_time);
		if (incubation_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incubation_time", incubation_time, NX_FLOAT);
			validateFieldUnits("incubation_time", group.getDataNode("incubation_time"), NX_TIME);
			validateFieldRank("incubation_time", incubation_time, 1);
			validateFieldDimensions("incubation_time", incubation_time, null, "n_rx_ori");
		}
	}

	/**
	 * Validate group 'grain_boundary_mobility' of type NXparameters.
	 */
	private void validateGroup_NXentry_grain_boundary_mobility(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("grain_boundary_mobility", NXparameters.class, group))) return;

		// validate field 'model' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
			validateFieldEnumeration("model", model,
					"sebald_gottstein",
					"rollett_holm");
		}

		// validate optional child group 'sebald_gottstein' of type NXparameters
		if (group.getChild("sebald_gottstein", NXparameters.class) != null) {
			validateGroup_NXentry_grain_boundary_mobility_sebald_gottstein(group.getChild("sebald_gottstein", NXparameters.class));
		}

		// validate child group 'rollett_holm' of type NXparameters
		validateGroup_NXentry_grain_boundary_mobility_rollett_holm(group.getChild("rollett_holm", NXparameters.class));
	}

	/**
	 * Validate optional group 'sebald_gottstein' of type NXparameters.
	 */
	private void validateGroup_NXentry_grain_boundary_mobility_sebald_gottstein(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("sebald_gottstein", NXparameters.class, group))) return;

		// validate field 'lagb_pre_factor' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset lagb_pre_factor = group.getLazyDataset("lagb_pre_factor");
		validateFieldNotNull("lagb_pre_factor", lagb_pre_factor);
		if (lagb_pre_factor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("lagb_pre_factor", lagb_pre_factor, NX_FLOAT);
			validateFieldUnits("lagb_pre_factor", group.getDataNode("lagb_pre_factor"), NX_ANY);
		}

		// validate field 'lagb_enthalpy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset lagb_enthalpy = group.getLazyDataset("lagb_enthalpy");
		validateFieldNotNull("lagb_enthalpy", lagb_enthalpy);
		if (lagb_enthalpy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("lagb_enthalpy", lagb_enthalpy, NX_FLOAT);
			validateFieldUnits("lagb_enthalpy", group.getDataNode("lagb_enthalpy"), NX_ANY);
		}

		// validate field 'hagb_pre_factor' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset hagb_pre_factor = group.getLazyDataset("hagb_pre_factor");
		validateFieldNotNull("hagb_pre_factor", hagb_pre_factor);
		if (hagb_pre_factor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("hagb_pre_factor", hagb_pre_factor, NX_FLOAT);
			validateFieldUnits("hagb_pre_factor", group.getDataNode("hagb_pre_factor"), NX_ANY);
		}

		// validate field 'hagb_enthalpy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset hagb_enthalpy = group.getLazyDataset("hagb_enthalpy");
		validateFieldNotNull("hagb_enthalpy", hagb_enthalpy);
		if (hagb_enthalpy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("hagb_enthalpy", hagb_enthalpy, NX_FLOAT);
			validateFieldUnits("hagb_enthalpy", group.getDataNode("hagb_enthalpy"), NX_ANY);
		}

		// validate field 'special_pre_factor' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset special_pre_factor = group.getLazyDataset("special_pre_factor");
		validateFieldNotNull("special_pre_factor", special_pre_factor);
		if (special_pre_factor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("special_pre_factor", special_pre_factor, NX_FLOAT);
			validateFieldUnits("special_pre_factor", group.getDataNode("special_pre_factor"), NX_ANY);
		}

		// validate field 'special_enthalpy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset special_enthalpy = group.getLazyDataset("special_enthalpy");
		validateFieldNotNull("special_enthalpy", special_enthalpy);
		if (special_enthalpy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("special_enthalpy", special_enthalpy, NX_FLOAT);
			validateFieldUnits("special_enthalpy", group.getDataNode("special_enthalpy"), NX_ANY);
		}
	}

	/**
	 * Validate group 'rollett_holm' of type NXparameters.
	 */
	private void validateGroup_NXentry_grain_boundary_mobility_rollett_holm(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("rollett_holm", NXparameters.class, group))) return;

		// validate field 'pre_factor' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset pre_factor = group.getLazyDataset("pre_factor");
		validateFieldNotNull("pre_factor", pre_factor);
		if (pre_factor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pre_factor", pre_factor, NX_FLOAT);
			validateFieldUnits("pre_factor", group.getDataNode("pre_factor"), NX_ANY);
		}

		// validate field 'enthalpy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset enthalpy = group.getLazyDataset("enthalpy");
		validateFieldNotNull("enthalpy", enthalpy);
		if (enthalpy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("enthalpy", enthalpy, NX_FLOAT);
			validateFieldUnits("enthalpy", group.getDataNode("enthalpy"), NX_ANY);
		}

		// validate field 'c1' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset c1 = group.getLazyDataset("c1");
		validateFieldNotNull("c1", c1);
		if (c1 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("c1", c1, NX_FLOAT);
			validateFieldUnits("c1", group.getDataNode("c1"), NX_DIMENSIONLESS);
		}

		// validate field 'c2' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset c2 = group.getLazyDataset("c2");
		validateFieldNotNull("c2", c2);
		if (c2 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("c2", c2, NX_FLOAT);
			validateFieldUnits("c2", group.getDataNode("c2"), NX_UNITLESS);
		}

		// validate field 'c3' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset c3 = group.getLazyDataset("c3");
		validateFieldNotNull("c3", c3);
		if (c3 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("c3", c3, NX_FLOAT);
			validateFieldUnits("c3", group.getDataNode("c3"), NX_UNITLESS);
		}
	}

	/**
	 * Validate group 'stored_energy_recovery' of type NXparameters.
	 */
	private void validateGroup_NXentry_stored_energy_recovery(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("stored_energy_recovery", NXparameters.class, group))) return;

		// validate field 'model' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
			validateFieldEnumeration("model", model,
					"none");
		}
	}

	/**
	 * Validate group 'dispersoid_drag' of type NXparameters.
	 */
	private void validateGroup_NXentry_dispersoid_drag(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("dispersoid_drag", NXparameters.class, group))) return;

		// validate field 'model' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset model = group.getLazyDataset("model");
		validateFieldNotNull("model", model);
		if (model != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("model", model, NX_CHAR);
			validateFieldEnumeration("model", model,
					"none",
					"zener_smith");
		}

		// validate child group 'zener_smith' of type NXparameters
		validateGroup_NXentry_dispersoid_drag_zener_smith(group.getChild("zener_smith", NXparameters.class));
	}

	/**
	 * Validate group 'zener_smith' of type NXparameters.
	 */
	private void validateGroup_NXentry_dispersoid_drag_zener_smith(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("zener_smith", NXparameters.class, group))) return;

		// validate field 'pre_factor' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset pre_factor = group.getLazyDataset("pre_factor");
		validateFieldNotNull("pre_factor", pre_factor);
		if (pre_factor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pre_factor", pre_factor, NX_FLOAT);
			validateFieldUnits("pre_factor", group.getDataNode("pre_factor"), NX_UNITLESS);
		}

		// validate field 'surface_energy' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset surface_energy = group.getLazyDataset("surface_energy");
		validateFieldNotNull("surface_energy", surface_energy);
		if (surface_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("surface_energy", surface_energy, NX_FLOAT);
			validateFieldUnits("surface_energy", group.getDataNode("surface_energy"), NX_ANY);
		}

		// validate child group 'radius_evolution' of type NXdata
		validateGroup_NXentry_dispersoid_drag_zener_smith_radius_evolution(group.getChild("radius_evolution", NXdata.class));
	}

	/**
	 * Validate group 'radius_evolution' of type NXdata.
	 */
	private void validateGroup_NXentry_dispersoid_drag_zener_smith_radius_evolution(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("radius_evolution", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate attribute 'time_indices' of type NX_UINT.
		final Attribute time_indices_attr = group.getAttribute("time_indices");
		if (!(validateAttributeNotNull("time_indices", time_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("time_indices", time_indices_attr, NX_UINT);

		// validate attribute 'radius_indices' of type NX_UINT.
		final Attribute radius_indices_attr = group.getAttribute("radius_indices");
		if (!(validateAttributeNotNull("radius_indices", radius_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("radius_indices", radius_indices_attr, NX_UINT);

		// validate field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset time = group.getLazyDataset("time");
		validateFieldNotNull("time", time);
		if (time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time", time, NX_FLOAT);
			validateFieldUnits("time", group.getDataNode("time"), NX_TIME);
			validateFieldRank("time", time, 1);
			validateFieldDimensions("time", time, null, "n_drag");
		// validate attribute 'long_name' of field 'time' of type NX_CHAR.
		final Attribute time_attr_long_name = group.getDataNode("time").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", time_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", time_attr_long_name, NX_CHAR);

		}

		// validate field 'radius' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset radius = group.getLazyDataset("radius");
		validateFieldNotNull("radius", radius);
		if (radius != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("radius", radius, NX_FLOAT);
			validateFieldUnits("radius", group.getDataNode("radius"), NX_LENGTH);
			validateFieldRank("radius", radius, 1);
			validateFieldDimensions("radius", radius, null, "n_drag");
		// validate attribute 'long_name' of field 'radius' of type NX_CHAR.
		final Attribute radius_attr_long_name = group.getDataNode("radius").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", radius_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", radius_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate group 'component_analysis' of type NXparameters.
	 */
	private void validateGroup_NXentry_component_analysis(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("component_analysis", NXparameters.class, group))) return;

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
			validateFieldRank("name", name, 1);
			validateFieldDimensions("name", name, null, "n_ori");
		}

		// validate field 'bunge_euler' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset bunge_euler = group.getLazyDataset("bunge_euler");
		validateFieldNotNull("bunge_euler", bunge_euler);
		if (bunge_euler != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("bunge_euler", bunge_euler, NX_FLOAT);
			validateFieldUnits("bunge_euler", group.getDataNode("bunge_euler"), NX_ANGLE);
			validateFieldRank("bunge_euler", bunge_euler, 2);
			validateFieldDimensions("bunge_euler", bunge_euler, null, "n_ori", 3);
		}

		// validate field 'theta' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset theta = group.getLazyDataset("theta");
		validateFieldNotNull("theta", theta);
		if (theta != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("theta", theta, NX_FLOAT);
			validateFieldUnits("theta", group.getDataNode("theta"), NX_ANGLE);
			validateFieldRank("theta", theta, 1);
			validateFieldDimensions("theta", theta, null, "n_ori");
		}
	}

	/**
	 * Validate group 'time_temperature' of type NXdata.
	 */
	private void validateGroup_NXentry_time_temperature(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("time_temperature", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);

		// validate attribute 'time_indices' of type NX_UINT.
		final Attribute time_indices_attr = group.getAttribute("time_indices");
		if (!(validateAttributeNotNull("time_indices", time_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("time_indices", time_indices_attr, NX_UINT);

		// validate attribute 'temperature_indices' of type NX_UINT.
		final Attribute temperature_indices_attr = group.getAttribute("temperature_indices");
		if (!(validateAttributeNotNull("temperature_indices", temperature_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("temperature_indices", temperature_indices_attr, NX_UINT);

		// validate field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset time = group.getLazyDataset("time");
		validateFieldNotNull("time", time);
		if (time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("time", time, NX_FLOAT);
			validateFieldUnits("time", group.getDataNode("time"), NX_TIME);
			validateFieldRank("time", time, 1);
			validateFieldDimensions("time", time, null, "n_temp");
		// validate attribute 'long_name' of field 'time' of type NX_CHAR.
		final Attribute time_attr_long_name = group.getDataNode("time").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", time_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", time_attr_long_name, NX_CHAR);

		}

		// validate field 'temperature' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
		validateFieldNotNull("temperature", temperature);
		if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_FLOAT);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldRank("temperature", temperature, 1);
			validateFieldDimensions("temperature", temperature, null, "n_temp");
		// validate attribute 'long_name' of field 'temperature' of type NX_CHAR.
		final Attribute temperature_attr_long_name = group.getDataNode("temperature").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", temperature_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", temperature_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate group 'discretization' of type NXmicrostructure.
	 */
	private void validateGroup_NXentry_discretization(final NXmicrostructure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("discretization", NXmicrostructure.class, group))) return;

		// validate child group 'grid' of type NXcg_grid
		validateGroup_NXentry_discretization_grid(group.getChild("grid", NXcg_grid.class));
	}

	/**
	 * Validate group 'grid' of type NXcg_grid.
	 */
	private void validateGroup_NXentry_discretization_grid(final NXcg_grid group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("grid", NXcg_grid.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'extent' of type NX_UINT.
		final ILazyDataset extent = group.getLazyDataset("extent");
		validateFieldNotNull("extent", extent);
		if (extent != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("extent", extent, NX_UINT);
			validateFieldUnits("extent", group.getDataNode("extent"), NX_UNITLESS);
			validateFieldRank("extent", extent, 1);
			validateFieldDimensions("extent", extent, null, 3);
		}

		// validate field 'cell_dimensions' of type NX_FLOAT.
		final ILazyDataset cell_dimensions = group.getLazyDataset("cell_dimensions");
		validateFieldNotNull("cell_dimensions", cell_dimensions);
		if (cell_dimensions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cell_dimensions", cell_dimensions, NX_FLOAT);
			validateFieldUnits("cell_dimensions", group.getDataNode("cell_dimensions"), NX_LENGTH);
			validateFieldRank("cell_dimensions", cell_dimensions, 1);
			validateFieldDimensions("cell_dimensions", cell_dimensions, "NXcg_grid", "d");
		}
	}

	/**
	 * Validate group 'numerics' of type NXparameters.
	 */
	private void validateGroup_NXentry_numerics(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("numerics", NXparameters.class, group))) return;

		// validate field 'max_x' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset max_x = group.getLazyDataset("max_x");
		validateFieldNotNull("max_x", max_x);
		if (max_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max_x", max_x, NX_FLOAT);
			validateFieldUnits("max_x", group.getDataNode("max_x"), NX_DIMENSIONLESS);
		}

		// validate field 'max_time' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset max_time = group.getLazyDataset("max_time");
		validateFieldNotNull("max_time", max_time);
		if (max_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max_time", max_time, NX_FLOAT);
			validateFieldUnits("max_time", group.getDataNode("max_time"), NX_TIME);
		}

		// validate field 'max_iteration' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset max_iteration = group.getLazyDataset("max_iteration");
		validateFieldNotNull("max_iteration", max_iteration);
		if (max_iteration != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max_iteration", max_iteration, NX_UINT);
			validateFieldUnits("max_iteration", group.getDataNode("max_iteration"), NX_UNITLESS);
		}

		// validate field 'max_delta_x' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset max_delta_x = group.getLazyDataset("max_delta_x");
		validateFieldNotNull("max_delta_x", max_delta_x);
		if (max_delta_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("max_delta_x", max_delta_x, NX_FLOAT);
			validateFieldUnits("max_delta_x", group.getDataNode("max_delta_x"), NX_DIMENSIONLESS);
		}

		// validate field 'x_set' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset x_set = group.getLazyDataset("x_set");
		validateFieldNotNull("x_set", x_set);
		if (x_set != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_set", x_set, NX_FLOAT);
			validateFieldUnits("x_set", group.getDataNode("x_set"), NX_DIMENSIONLESS);
			validateFieldRank("x_set", x_set, 1);
			validateFieldDimensions("x_set", x_set, null, "n_snapshot");
		}

		// validate child group 'cell_cache' of type NXparameters
		validateGroup_NXentry_numerics_cell_cache(group.getChild("cell_cache", NXparameters.class));
	}

	/**
	 * Validate group 'cell_cache' of type NXparameters.
	 */
	private void validateGroup_NXentry_numerics_cell_cache(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("cell_cache", NXparameters.class, group))) return;

		// validate field 'initial' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset initial = group.getLazyDataset("initial");
		validateFieldNotNull("initial", initial);
		if (initial != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("initial", initial, NX_FLOAT);
			validateFieldUnits("initial", group.getDataNode("initial"), NX_UNITLESS);
		}

		// validate field 'realloc' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset realloc = group.getLazyDataset("realloc");
		validateFieldNotNull("realloc", realloc);
		if (realloc != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("realloc", realloc, NX_FLOAT);
			validateFieldUnits("realloc", group.getDataNode("realloc"), NX_UNITLESS);
		}

		// validate field 'defragment' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset defragment = group.getLazyDataset("defragment");
		validateFieldNotNull("defragment", defragment);
		if (defragment != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("defragment", defragment, NX_BOOLEAN);
		}

		// validate field 'defragment_x' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset defragment_x = group.getLazyDataset("defragment_x");
		validateFieldNotNull("defragment_x", defragment_x);
		if (defragment_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("defragment_x", defragment_x, NX_FLOAT);
			validateFieldUnits("defragment_x", group.getDataNode("defragment_x"), NX_DIMENSIONLESS);
			validateFieldRank("defragment_x", defragment_x, 1);
			validateFieldDimensions("defragment_x", defragment_x, null, "n_defrag");
		}
	}

	/**
	 * Validate group 'solitary_unit' of type NXparameters.
	 */
	private void validateGroup_NXentry_solitary_unit(final NXparameters group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("solitary_unit", NXparameters.class, group))) return;

		// validate field 'apply' of type NX_BOOLEAN. Note: field not defined in base class.
		final ILazyDataset apply = group.getLazyDataset("apply");
		validateFieldNotNull("apply", apply);
		if (apply != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("apply", apply, NX_BOOLEAN);
		}

		// validate field 'number_of_domains' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_domains = group.getLazyDataset("number_of_domains");
		validateFieldNotNull("number_of_domains", number_of_domains);
		if (number_of_domains != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_domains", number_of_domains, NX_UINT);
			validateFieldUnits("number_of_domains", group.getDataNode("number_of_domains"), NX_UNITLESS);
		}

		// validate field 'rediscretization' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset rediscretization = group.getLazyDataset("rediscretization");
		validateFieldNotNull("rediscretization", rediscretization);
		if (rediscretization != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("rediscretization", rediscretization, NX_UINT);
			validateFieldUnits("rediscretization", group.getDataNode("rediscretization"), NX_UNITLESS);
		}
	}
}
