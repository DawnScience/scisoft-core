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
import org.eclipse.dawnsci.nexus.NXprogram;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXmicrostructure;
import org.eclipse.dawnsci.nexus.NXcg_grid;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXmicrostructure_feature;

/**
 * Validator for the application definition 'NXmicrostructure_kanapy_results'.
 */
public class NXmicrostructure_kanapy_resultsValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXmicrostructure_kanapy_resultsValidator() {
		super(NexusApplicationDefinition.NX_MICROSTRUCTURE_KANAPY_RESULTS);
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
					"NXmicrostructure_kanapy_results");
		}

		// validate field 'description' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset description = group.getLazyDataset("description");
		validateFieldNotNull("description", description);
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

		// validate optional field 'end_time' of type NX_DATE_TIME.
		final ILazyDataset end_time = group.getLazyDataset("end_time");
				if (end_time != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("end_time", end_time, NX_DATE_TIME);
		}

		// validate optional child group 'profiling' of type NXcs_profiling
		if (group.getChild("profiling", NXcs_profiling.class) != null) {
			validateGroup_NXentry_profiling(group.getChild("profiling", NXcs_profiling.class));
		}

		// validate unnamed child group of type NXuser (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXuser.class, false, true);
		final Map<String, NXuser> allUser = group.getAllUser();
		for (final NXuser user : allUser.values()) {
			validateGroup_NXentry_NXuser(user);
		}

		// validate child group 'program1' of type NXprogram
		validateGroup_NXentry_program1(group.getChild("program1", NXprogram.class));

		// validate child group 'program2' of type NXprogram
		validateGroup_NXentry_program2(group.getChild("program2", NXprogram.class));

		// validate optional child group 'environment' of type NXcollection
		if (group.getCollection("environment") != null) {
			validateGroup_NXentry_environment(group.getCollection("environment"));
		}

		// validate child group 'microstructureID' of type NXmicrostructure
		validateGroup_NXentry_microstructureID(group.getChild("microstructureID", NXmicrostructure.class));
	}

	/**
	 * Validate optional group 'profiling' of type NXcs_profiling.
	 */
	private void validateGroup_NXentry_profiling(final NXcs_profiling group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("profiling", NXcs_profiling.class, group))) return;

	}

	/**
	 * Validate optional unnamed group of type NXuser.
	 */
	private void validateGroup_NXentry_NXuser(final NXuser group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXuser.class, group))) return;

	}

	/**
	 * Validate group 'program1' of type NXprogram.
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

		// validate attribute 'url' of field 'program' of type NX_CHAR.
		final Attribute program_attr_url = group.getDataNode("program").getAttribute("url");
		if (!(validateAttributeNotNull("url", program_attr_url))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("url", program_attr_url, NX_CHAR);

		}
	}

	/**
	 * Validate group 'program2' of type NXprogram.
	 */
	private void validateGroup_NXentry_program2(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("program2", NXprogram.class, group))) return;

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

		// validate attribute 'url' of field 'program' of type NX_CHAR.
		final Attribute program_attr_url = group.getDataNode("program").getAttribute("url");
		if (!(validateAttributeNotNull("url", program_attr_url))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("url", program_attr_url, NX_CHAR);

		}
	}

	/**
	 * Validate optional group 'environment' of type NXcollection.
	 */
	private void validateGroup_NXentry_environment(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("environment", NXcollection.class, group))) return;

		// validate unnamed child group of type NXprogram (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXprogram.class, false, true);
		final Map<String, NXprogram> allProgram = group.getChildren(NXprogram.class);
		for (final NXprogram program : allProgram.values()) {
			validateGroup_NXentry_environment_NXprogram(program);
		}
	}

	/**
	 * Validate unnamed group of type NXprogram.
	 */
	private void validateGroup_NXentry_environment_NXprogram(final NXprogram group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprogram.class, group))) return;

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
	 * Validate group 'microstructureID' of type NXmicrostructure.
	 */
	private void validateGroup_NXentry_microstructureID(final NXmicrostructure group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("microstructureID", NXmicrostructure.class, group))) return;

		// validate child group 'grid' of type NXcg_grid
		validateGroup_NXentry_microstructureID_grid(group.getChild("grid", NXcg_grid.class));

		// validate child group 'crystals' of type NXmicrostructure_feature
		validateGroup_NXentry_microstructureID_crystals(group.getChild("crystals", NXmicrostructure_feature.class));
	}

	/**
	 * Validate group 'grid' of type NXcg_grid.
	 */
	private void validateGroup_NXentry_microstructureID_grid(final NXcg_grid group) {
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
			validateFieldDimensions("extent", extent, "NXcg_grid", "d");
		}

		// validate field 'cell_dimensions' of type NX_NUMBER.
		final ILazyDataset cell_dimensions = group.getLazyDataset("cell_dimensions");
		validateFieldNotNull("cell_dimensions", cell_dimensions);
		if (cell_dimensions != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("cell_dimensions", cell_dimensions, NX_NUMBER);
			validateFieldUnits("cell_dimensions", group.getDataNode("cell_dimensions"), NX_LENGTH);
			validateFieldRank("cell_dimensions", cell_dimensions, 1);
			validateFieldDimensions("cell_dimensions", cell_dimensions, "NXcg_grid", "d");
		}

		// validate field 'origin' of type NX_NUMBER.
		final ILazyDataset origin = group.getLazyDataset("origin");
		validateFieldNotNull("origin", origin);
		if (origin != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("origin", origin, NX_NUMBER);
			validateFieldUnits("origin", group.getDataNode("origin"), NX_ANY);
			validateFieldRank("origin", origin, 1);
			validateFieldDimensions("origin", origin, "NXcg_grid", "d");
		}

		// validate field 'symmetry' of type NX_CHAR.
		final ILazyDataset symmetry = group.getLazyDataset("symmetry");
		validateFieldNotNull("symmetry", symmetry);
		if (symmetry != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("symmetry", symmetry, NX_CHAR);
			validateFieldEnumeration("symmetry", symmetry,
					"cubic");
		}

		// validate child group 'structure' of type NXdata
		validateGroup_NXentry_microstructureID_grid_structure(group.getChild("structure", NXdata.class));
	}

	/**
	 * Validate group 'structure' of type NXdata.
	 */
	private void validateGroup_NXentry_microstructureID_grid_structure(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("structure", NXdata.class, group))) return;
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

		// validate attribute 'AXISNAME_indices' of type NX_UINT.
		final Attribute AXISNAME_indices_attr = group.getAttribute("AXISNAME_indices");
		if (!(validateAttributeNotNull("AXISNAME_indices", AXISNAME_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("AXISNAME_indices", AXISNAME_indices_attr, NX_UINT);

		// validate field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'indices_crystal' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_crystal = group.getLazyDataset("indices_crystal");
		validateFieldNotNull("indices_crystal", indices_crystal);
		if (indices_crystal != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_crystal", indices_crystal, NX_INT);
			validateFieldUnits("indices_crystal", group.getDataNode("indices_crystal"), NX_UNITLESS);
		}

		// validate optional field 'z' of type NX_NUMBER.
		final ILazyDataset z = group.getLazyDataset("z");
				if (z != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("z", z, NX_NUMBER);
			validateFieldUnits("z", group.getDataNode("z"), NX_LENGTH);
			validateFieldRank("z", z, 1);
			validateFieldDimensions("z", z, null, "n_y");
		// validate attribute 'long_name' of field 'z' of type NX_CHAR.
		final Attribute z_attr_long_name = group.getDataNode("z").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", z_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", z_attr_long_name, NX_CHAR);

		}

		// validate field 'y' of type NX_NUMBER.
		final ILazyDataset y = group.getLazyDataset("y");
		validateFieldNotNull("y", y);
		if (y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y", y, NX_NUMBER);
			validateFieldUnits("y", group.getDataNode("y"), NX_LENGTH);
			validateFieldRank("y", y, 1);
			validateFieldDimensions("y", y, null, "n_y");
		// validate attribute 'long_name' of field 'y' of type NX_CHAR.
		final Attribute y_attr_long_name = group.getDataNode("y").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", y_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", y_attr_long_name, NX_CHAR);

		}

		// validate field 'x' of type NX_NUMBER.
		final ILazyDataset x = group.getLazyDataset("x");
		validateFieldNotNull("x", x);
		if (x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x", x, NX_NUMBER);
			validateFieldUnits("x", group.getDataNode("x"), NX_LENGTH);
			validateFieldRank("x", x, 1);
			validateFieldDimensions("x", x, null, "n_x");
		// validate attribute 'long_name' of field 'x' of type NX_CHAR.
		final Attribute x_attr_long_name = group.getDataNode("x").getAttribute("long_name");
		if (!(validateAttributeNotNull("long_name", x_attr_long_name))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("long_name", x_attr_long_name, NX_CHAR);

		}
	}

	/**
	 * Validate group 'crystals' of type NXmicrostructure_feature.
	 */
	private void validateGroup_NXentry_microstructureID_crystals(final NXmicrostructure_feature group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("crystals", NXmicrostructure_feature.class, group))) return;

		// validate field 'reference' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset reference = group.getLazyDataset("reference");
		validateFieldNotNull("reference", reference);
		if (reference != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("reference", reference, NX_CHAR);
		}

		// validate field 'number_of_crystals' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_crystals = group.getLazyDataset("number_of_crystals");
		validateFieldNotNull("number_of_crystals", number_of_crystals);
		if (number_of_crystals != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_crystals", number_of_crystals, NX_UINT);
		}

		// validate field 'number_of_phases' of type NX_UINT. Note: field not defined in base class.
		final ILazyDataset number_of_phases = group.getLazyDataset("number_of_phases");
		validateFieldNotNull("number_of_phases", number_of_phases);
		if (number_of_phases != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("number_of_phases", number_of_phases, NX_UINT);
		}

		// validate field 'indices_crystal' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_crystal = group.getLazyDataset("indices_crystal");
		validateFieldNotNull("indices_crystal", indices_crystal);
		if (indices_crystal != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_crystal", indices_crystal, NX_INT);
			validateFieldRank("indices_crystal", indices_crystal, 1);
			validateFieldDimensions("indices_crystal", indices_crystal, null, "c");
		}

		// validate field 'indices_phase' of type NX_INT. Note: field not defined in base class.
		final ILazyDataset indices_phase = group.getLazyDataset("indices_phase");
		validateFieldNotNull("indices_phase", indices_phase);
		if (indices_phase != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("indices_phase", indices_phase, NX_INT);
			validateFieldRank("indices_phase", indices_phase, 1);
			validateFieldDimensions("indices_phase", indices_phase, null, "c");
		}

		// validate optional field 'area' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset area = group.getLazyDataset("area");
				if (area != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("area", area, NX_NUMBER);
			validateFieldRank("area", area, 1);
			validateFieldDimensions("area", area, null, "c");
		}

		// validate optional field 'volume' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset volume = group.getLazyDataset("volume");
				if (volume != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("volume", volume, NX_NUMBER);
			validateFieldRank("volume", volume, 1);
			validateFieldDimensions("volume", volume, null, "c");
		}

		// validate field 'bunge_euler' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset bunge_euler = group.getLazyDataset("bunge_euler");
		validateFieldNotNull("bunge_euler", bunge_euler);
		if (bunge_euler != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("bunge_euler", bunge_euler, NX_FLOAT);
			validateFieldUnits("bunge_euler", group.getDataNode("bunge_euler"), NX_ANGLE);
			validateFieldRank("bunge_euler", bunge_euler, 2);
			validateFieldDimensions("bunge_euler", bunge_euler, null, "c", 3);
		}
	}
}
