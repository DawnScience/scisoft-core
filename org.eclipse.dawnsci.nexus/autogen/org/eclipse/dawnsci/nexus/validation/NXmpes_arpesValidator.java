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
import org.eclipse.dawnsci.nexus.NXcoordinate_system;
import org.eclipse.dawnsci.nexus.NXtransformations;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXresolution;
import org.eclipse.dawnsci.nexus.NXelectronanalyzer;
import org.eclipse.dawnsci.nexus.NXcollectioncolumn;
import org.eclipse.dawnsci.nexus.NXenergydispersion;
import org.eclipse.dawnsci.nexus.NXaperture;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXdata;

/**
 * Validator for the application definition 'NXmpes_arpes'.
 */
public class NXmpes_arpesValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXmpes_arpesValidator() {
		super(NexusApplicationDefinition.NX_MPES_ARPES);
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
					"NXmpes_arpes");
		// validate attribute 'version' of field 'definition' of type NX_CHAR.
		final Attribute definition_attr_version = group.getDataNode("definition").getAttribute("version");
		if (!(validateAttributeNotNull("version", definition_attr_version))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", definition_attr_version, NX_CHAR);

		}

		// validate optional field 'method' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset method = group.getLazyDataset("method");
				if (method != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("method", method, NX_CHAR);
		}

		// validate child group 'arpes_geometry' of type NXcoordinate_system
		validateGroup_NXentry_arpes_geometry(group.getChild("arpes_geometry", NXcoordinate_system.class));

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

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}
	}

	/**
	 * Validate group 'arpes_geometry' of type NXcoordinate_system.
	 */
	private void validateGroup_NXentry_arpes_geometry(final NXcoordinate_system group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("arpes_geometry", NXcoordinate_system.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'depends_on' of type NX_CHAR.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate unnamed child group of type NXtransformations (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXtransformations.class, false, true);
		final Map<String, NXtransformations> allTransformations = group.getAllTransformations();
		for (final NXtransformations transformations : allTransformations.values()) {
			validateGroup_NXentry_arpes_geometry_NXtransformations(transformations);
		}
		// validate NXtransformations groups (special case)
		validateTransformations(allTransformations, depends_on);
	}

	/**
	 * Validate unnamed group of type NXtransformations.
	 */
	private void validateGroup_NXentry_arpes_geometry_NXtransformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate optional child group 'angularN_resolution' of type NXresolution
		if (group.getChild("angularN_resolution", NXresolution.class) != null) {
			validateGroup_NXentry_NXinstrument_angularN_resolution(group.getChild("angularN_resolution", NXresolution.class));
		}

		// validate unnamed child group of type NXelectronanalyzer (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXelectronanalyzer.class, false, true);
		final Map<String, NXelectronanalyzer> allElectronanalyzer = group.getChildren(NXelectronanalyzer.class);
		for (final NXelectronanalyzer electronanalyzer : allElectronanalyzer.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer(electronanalyzer);
		}
	}

	/**
	 * Validate optional group 'angularN_resolution' of type NXresolution.
	 */
	private void validateGroup_NXentry_NXinstrument_angularN_resolution(final NXresolution group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("angularN_resolution", NXresolution.class, group))) return;

		// validate field 'physical_quantity' of type NX_CHAR.
		final ILazyDataset physical_quantity = group.getLazyDataset("physical_quantity");
		validateFieldNotNull("physical_quantity", physical_quantity);
		if (physical_quantity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_quantity", physical_quantity, NX_CHAR);
			validateFieldEnumeration("physical_quantity", physical_quantity,
					"angle");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"estimated",
					"derived",
					"calibrated",
					"other");
		}

		// validate field 'resolution' of type NX_FLOAT.
		final ILazyDataset resolution = group.getLazyDataset("resolution");
		validateFieldNotNull("resolution", resolution);
		if (resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("resolution", resolution, NX_FLOAT);
			validateFieldUnits("resolution", group.getDataNode("resolution"), NX_ANGLE);
		}
	}

	/**
	 * Validate unnamed group of type NXelectronanalyzer.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer(final NXelectronanalyzer group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXelectronanalyzer.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate optional child group 'angularN_resolution' of type NXresolution
		if (group.getResolution("angularN_resolution") != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_angularN_resolution(group.getResolution("angularN_resolution"));
		}

		// validate child group 'transformations' of type NXtransformations
		validateGroup_NXentry_NXinstrument_NXelectronanalyzer_transformations(group.getChild("transformations", NXtransformations.class));

		// validate unnamed child group of type NXcollectioncolumn (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXcollectioncolumn.class, false, true);
		final Map<String, NXcollectioncolumn> allCollectioncolumn = group.getAllCollectioncolumn();
		for (final NXcollectioncolumn collectioncolumn : allCollectioncolumn.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn(collectioncolumn);
		}

		// validate unnamed child group of type NXenergydispersion (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXenergydispersion.class, false, true);
		final Map<String, NXenergydispersion> allEnergydispersion = group.getAllEnergydispersion();
		for (final NXenergydispersion energydispersion : allEnergydispersion.values()) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion(energydispersion);
		}
	}

	/**
	 * Validate optional group 'angularN_resolution' of type NXresolution.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_angularN_resolution(final NXresolution group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("angularN_resolution", NXresolution.class, group))) return;

		// validate field 'physical_quantity' of type NX_CHAR.
		final ILazyDataset physical_quantity = group.getLazyDataset("physical_quantity");
		validateFieldNotNull("physical_quantity", physical_quantity);
		if (physical_quantity != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("physical_quantity", physical_quantity, NX_CHAR);
			validateFieldEnumeration("physical_quantity", physical_quantity,
					"angle");
		}

		// validate optional field 'type' of type NX_CHAR.
		final ILazyDataset type = group.getLazyDataset("type");
				if (type != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("type", type, NX_CHAR);
			validateFieldEnumeration("type", type,
					"estimated",
					"derived",
					"calibrated",
					"other");
		}

		// validate field 'resolution' of type NX_FLOAT.
		final ILazyDataset resolution = group.getLazyDataset("resolution");
		validateFieldNotNull("resolution", resolution);
		if (resolution != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("resolution", resolution, NX_FLOAT);
			validateFieldUnits("resolution", group.getDataNode("resolution"), NX_ANGLE);
		}
	}

	/**
	 * Validate group 'transformations' of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_transformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transformations", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'analyzer_rotation' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset analyzer_rotation = group.getLazyDataset("analyzer_rotation");
		validateFieldNotNull("analyzer_rotation", analyzer_rotation);
		if (analyzer_rotation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("analyzer_rotation", analyzer_rotation, NX_NUMBER);
			validateFieldUnits("analyzer_rotation", group.getDataNode("analyzer_rotation"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'analyzer_rotation' of type NX_CHAR.
		final Attribute analyzer_rotation_attr_transformation_type = group.getDataNode("analyzer_rotation").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", analyzer_rotation_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", analyzer_rotation_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", analyzer_rotation_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'analyzer_rotation' of type NX_NUMBER.
		final Attribute analyzer_rotation_attr_vector = group.getDataNode("analyzer_rotation").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", analyzer_rotation_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", analyzer_rotation_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", analyzer_rotation_attr_vector,
				"[0, 0, 1]");

		// validate attribute 'depends_on' of field 'analyzer_rotation' of type NX_CHAR.
		final Attribute analyzer_rotation_attr_depends_on = group.getDataNode("analyzer_rotation").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", analyzer_rotation_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", analyzer_rotation_attr_depends_on, NX_CHAR);

		}

		// validate field 'analyzer_elevation' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset analyzer_elevation = group.getLazyDataset("analyzer_elevation");
		validateFieldNotNull("analyzer_elevation", analyzer_elevation);
		if (analyzer_elevation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("analyzer_elevation", analyzer_elevation, NX_NUMBER);
			validateFieldUnits("analyzer_elevation", group.getDataNode("analyzer_elevation"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'analyzer_elevation' of type NX_CHAR.
		final Attribute analyzer_elevation_attr_transformation_type = group.getDataNode("analyzer_elevation").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", analyzer_elevation_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", analyzer_elevation_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", analyzer_elevation_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'analyzer_elevation' of type NX_NUMBER.
		final Attribute analyzer_elevation_attr_vector = group.getDataNode("analyzer_elevation").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", analyzer_elevation_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", analyzer_elevation_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", analyzer_elevation_attr_vector,
				"[0, 1, 0]");

		// validate attribute 'depends_on' of field 'analyzer_elevation' of type NX_CHAR.
		final Attribute analyzer_elevation_attr_depends_on = group.getDataNode("analyzer_elevation").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", analyzer_elevation_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", analyzer_elevation_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", analyzer_elevation_attr_depends_on,
				"analyzer_dispersion");

		}

		// validate field 'analyzer_dispersion' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset analyzer_dispersion = group.getLazyDataset("analyzer_dispersion");
		validateFieldNotNull("analyzer_dispersion", analyzer_dispersion);
		if (analyzer_dispersion != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("analyzer_dispersion", analyzer_dispersion, NX_NUMBER);
			validateFieldUnits("analyzer_dispersion", group.getDataNode("analyzer_dispersion"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'analyzer_dispersion' of type NX_CHAR.
		final Attribute analyzer_dispersion_attr_transformation_type = group.getDataNode("analyzer_dispersion").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", analyzer_dispersion_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", analyzer_dispersion_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", analyzer_dispersion_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'analyzer_dispersion' of type NX_NUMBER.
		final Attribute analyzer_dispersion_attr_vector = group.getDataNode("analyzer_dispersion").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", analyzer_dispersion_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", analyzer_dispersion_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", analyzer_dispersion_attr_vector,
				"[1, 0, 0]");

		// validate attribute 'depends_on' of field 'analyzer_dispersion' of type NX_CHAR.
		final Attribute analyzer_dispersion_attr_depends_on = group.getDataNode("analyzer_dispersion").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", analyzer_dispersion_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", analyzer_dispersion_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", analyzer_dispersion_attr_depends_on,
				"analyzer_rotation");

		}
	}

	/**
	 * Validate unnamed group of type NXcollectioncolumn.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXcollectioncolumn(final NXcollectioncolumn group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcollectioncolumn.class, group))) return;

		// validate optional field 'scheme' of type NX_CHAR.
		final ILazyDataset scheme = group.getLazyDataset("scheme");
				if (scheme != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("scheme", scheme, NX_CHAR);
			validateFieldEnumeration("scheme", scheme,
					"angular dispersive",
					"non-dispersive");
		}

		// validate optional field 'angular_acceptance' of type NX_FLOAT.
		final ILazyDataset angular_acceptance = group.getLazyDataset("angular_acceptance");
				if (angular_acceptance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular_acceptance", angular_acceptance, NX_FLOAT);
			validateFieldUnits("angular_acceptance", group.getDataNode("angular_acceptance"), NX_ANGLE);
		}
	}

	/**
	 * Validate unnamed group of type NXenergydispersion.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion(final NXenergydispersion group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXenergydispersion.class, group))) return;

		// validate optional field 'diameter' of type NX_NUMBER.
		final ILazyDataset diameter = group.getLazyDataset("diameter");
				if (diameter != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("diameter", diameter, NX_NUMBER);
			validateFieldUnits("diameter", group.getDataNode("diameter"), NX_LENGTH);
		}

		// validate optional child group 'entrance_slit' of type NXaperture
		if (group.getAperture("entrance_slit") != null) {
			validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion_entrance_slit(group.getAperture("entrance_slit"));
		}
	}

	/**
	 * Validate optional group 'entrance_slit' of type NXaperture.
	 */
	private void validateGroup_NXentry_NXinstrument_NXelectronanalyzer_NXenergydispersion_entrance_slit(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("entrance_slit", NXaperture.class, group))) return;

		// validate field 'shape' of type NX_CHAR.
		final ILazyDataset shape = group.getLazyDataset("shape");
		validateFieldNotNull("shape", shape);
		if (shape != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("shape", shape, NX_CHAR);
			validateFieldEnumeration("shape", shape,
					"straight slit",
					"curved slit");
		}
	}

	/**
	 * Validate unnamed group of type NXsample.
	 */
	private void validateGroup_NXentry_NXsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'situation' of type NX_CHAR.
		final ILazyDataset situation = group.getLazyDataset("situation");
		validateFieldNotNull("situation", situation);
		if (situation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("situation", situation, NX_CHAR);
			validateFieldEnumeration("situation", situation,
					"vacuum");
		}

		// validate field 'depends_on' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset depends_on = group.getLazyDataset("depends_on");
		validateFieldNotNull("depends_on", depends_on);
		if (depends_on != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("depends_on", depends_on, NX_CHAR);
		}

		// validate child group 'transformations' of type NXtransformations
		validateGroup_NXentry_NXsample_transformations(group.getChild("transformations", NXtransformations.class));
	}

	/**
	 * Validate group 'transformations' of type NXtransformations.
	 */
	private void validateGroup_NXentry_NXsample_transformations(final NXtransformations group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("transformations", NXtransformations.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'sample_azimuth' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset sample_azimuth = group.getLazyDataset("sample_azimuth");
		validateFieldNotNull("sample_azimuth", sample_azimuth);
		if (sample_azimuth != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_azimuth", sample_azimuth, NX_NUMBER);
			validateFieldUnits("sample_azimuth", group.getDataNode("sample_azimuth"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'sample_azimuth' of type NX_CHAR.
		final Attribute sample_azimuth_attr_transformation_type = group.getDataNode("sample_azimuth").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", sample_azimuth_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", sample_azimuth_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", sample_azimuth_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'sample_azimuth' of type NX_NUMBER.
		final Attribute sample_azimuth_attr_vector = group.getDataNode("sample_azimuth").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", sample_azimuth_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", sample_azimuth_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", sample_azimuth_attr_vector,
				"[0, 0, 1]");

		// validate attribute 'depends_on' of field 'sample_azimuth' of type NX_CHAR.
		final Attribute sample_azimuth_attr_depends_on = group.getDataNode("sample_azimuth").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", sample_azimuth_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", sample_azimuth_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", sample_azimuth_attr_depends_on,
				"offset_azimuth");

		}

		// validate field 'offset_azimuth' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset offset_azimuth = group.getLazyDataset("offset_azimuth");
		validateFieldNotNull("offset_azimuth", offset_azimuth);
		if (offset_azimuth != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("offset_azimuth", offset_azimuth, NX_NUMBER);
			validateFieldUnits("offset_azimuth", group.getDataNode("offset_azimuth"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'offset_azimuth' of type NX_CHAR.
		final Attribute offset_azimuth_attr_transformation_type = group.getDataNode("offset_azimuth").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", offset_azimuth_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", offset_azimuth_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", offset_azimuth_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'offset_azimuth' of type NX_NUMBER.
		final Attribute offset_azimuth_attr_vector = group.getDataNode("offset_azimuth").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", offset_azimuth_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", offset_azimuth_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", offset_azimuth_attr_vector,
				"[0, 0, 1]");

		// validate attribute 'depends_on' of field 'offset_azimuth' of type NX_CHAR.
		final Attribute offset_azimuth_attr_depends_on = group.getDataNode("offset_azimuth").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", offset_azimuth_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", offset_azimuth_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", offset_azimuth_attr_depends_on,
				"sample_tilt");

		}

		// validate field 'sample_tilt' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset sample_tilt = group.getLazyDataset("sample_tilt");
		validateFieldNotNull("sample_tilt", sample_tilt);
		if (sample_tilt != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_tilt", sample_tilt, NX_NUMBER);
			validateFieldUnits("sample_tilt", group.getDataNode("sample_tilt"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'sample_tilt' of type NX_CHAR.
		final Attribute sample_tilt_attr_transformation_type = group.getDataNode("sample_tilt").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", sample_tilt_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", sample_tilt_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", sample_tilt_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'sample_tilt' of type NX_NUMBER.
		final Attribute sample_tilt_attr_vector = group.getDataNode("sample_tilt").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", sample_tilt_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", sample_tilt_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", sample_tilt_attr_vector,
				"[1, 0, 0]");

		// validate attribute 'depends_on' of field 'sample_tilt' of type NX_CHAR.
		final Attribute sample_tilt_attr_depends_on = group.getDataNode("sample_tilt").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", sample_tilt_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", sample_tilt_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", sample_tilt_attr_depends_on,
				"offset_tilt");

		}

		// validate field 'offset_tilt' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset offset_tilt = group.getLazyDataset("offset_tilt");
		validateFieldNotNull("offset_tilt", offset_tilt);
		if (offset_tilt != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("offset_tilt", offset_tilt, NX_NUMBER);
			validateFieldUnits("offset_tilt", group.getDataNode("offset_tilt"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'offset_tilt' of type NX_CHAR.
		final Attribute offset_tilt_attr_transformation_type = group.getDataNode("offset_tilt").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", offset_tilt_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", offset_tilt_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", offset_tilt_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'offset_tilt' of type NX_NUMBER.
		final Attribute offset_tilt_attr_vector = group.getDataNode("offset_tilt").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", offset_tilt_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", offset_tilt_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", offset_tilt_attr_vector,
				"[1, 0, 0]");

		// validate attribute 'depends_on' of field 'offset_tilt' of type NX_CHAR.
		final Attribute offset_tilt_attr_depends_on = group.getDataNode("offset_tilt").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", offset_tilt_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", offset_tilt_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", offset_tilt_attr_depends_on,
				"sample_polar");

		}

		// validate field 'sample_polar' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset sample_polar = group.getLazyDataset("sample_polar");
		validateFieldNotNull("sample_polar", sample_polar);
		if (sample_polar != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("sample_polar", sample_polar, NX_NUMBER);
			validateFieldUnits("sample_polar", group.getDataNode("sample_polar"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'sample_polar' of type NX_CHAR.
		final Attribute sample_polar_attr_transformation_type = group.getDataNode("sample_polar").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", sample_polar_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", sample_polar_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", sample_polar_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'sample_polar' of type NX_NUMBER.
		final Attribute sample_polar_attr_vector = group.getDataNode("sample_polar").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", sample_polar_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", sample_polar_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", sample_polar_attr_vector,
				"[0, 1, 0]");

		// validate attribute 'depends_on' of field 'sample_polar' of type NX_CHAR.
		final Attribute sample_polar_attr_depends_on = group.getDataNode("sample_polar").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", sample_polar_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", sample_polar_attr_depends_on, NX_CHAR);
		validateAttributeEnumeration("depends_on", sample_polar_attr_depends_on,
				"offset_polar");

		}

		// validate field 'offset_polar' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset offset_polar = group.getLazyDataset("offset_polar");
		validateFieldNotNull("offset_polar", offset_polar);
		if (offset_polar != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("offset_polar", offset_polar, NX_NUMBER);
			validateFieldUnits("offset_polar", group.getDataNode("offset_polar"), NX_ANGLE);
		// validate attribute 'transformation_type' of field 'offset_polar' of type NX_CHAR.
		final Attribute offset_polar_attr_transformation_type = group.getDataNode("offset_polar").getAttribute("transformation_type");
		if (!(validateAttributeNotNull("transformation_type", offset_polar_attr_transformation_type))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("transformation_type", offset_polar_attr_transformation_type, NX_CHAR);
		validateAttributeEnumeration("transformation_type", offset_polar_attr_transformation_type,
				"rotation");

		// validate attribute 'vector' of field 'offset_polar' of type NX_NUMBER.
		final Attribute offset_polar_attr_vector = group.getDataNode("offset_polar").getAttribute("vector");
		if (!(validateAttributeNotNull("vector", offset_polar_attr_vector))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("vector", offset_polar_attr_vector, NX_NUMBER);
		validateAttributeEnumeration("vector", offset_polar_attr_vector,
				"[0, 1, 0]");

		// validate attribute 'depends_on' of field 'offset_polar' of type NX_CHAR.
		final Attribute offset_polar_attr_depends_on = group.getDataNode("offset_polar").getAttribute("depends_on");
		if (!(validateAttributeNotNull("depends_on", offset_polar_attr_depends_on))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("depends_on", offset_polar_attr_depends_on, NX_CHAR);

		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"data");

		// validate attribute 'axes' of type NX_CHAR.
		final Attribute axes_attr = group.getAttribute("axes");
		if (!(validateAttributeNotNull("axes", axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("axes", axes_attr, NX_CHAR);
		validateAttributeEnumeration("axes", axes_attr,
				"['angular0', 'angular1', 'energy']");

		// validate attribute 'energy_indices' of type NX_INT.
		final Attribute energy_indices_attr = group.getAttribute("energy_indices");
		if (!(validateAttributeNotNull("energy_indices", energy_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("energy_indices", energy_indices_attr, NX_INT);

		// validate attribute 'angular0_indices' of type NX_INT.
		final Attribute angular0_indices_attr = group.getAttribute("angular0_indices");
		if (!(validateAttributeNotNull("angular0_indices", angular0_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("angular0_indices", angular0_indices_attr, NX_INT);

		// validate attribute 'angular1_indices' of type NX_INT.
		final Attribute angular1_indices_attr = group.getAttribute("angular1_indices");
		if (!(validateAttributeNotNull("angular1_indices", angular1_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("angular1_indices", angular1_indices_attr, NX_INT);

		// validate field 'energy' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset energy = group.getLazyDataset("energy");
		validateFieldNotNull("energy", energy);
		if (energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("energy", energy, NX_NUMBER);
			validateFieldUnits("energy", group.getDataNode("energy"), NX_ENERGY);
		}

		// validate field 'angular0' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angular0 = group.getLazyDataset("angular0");
		validateFieldNotNull("angular0", angular0);
		if (angular0 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular0", angular0, NX_NUMBER);
			validateFieldUnits("angular0", group.getDataNode("angular0"), NX_ANGLE);
		}

		// validate field 'angular1' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset angular1 = group.getLazyDataset("angular1");
		validateFieldNotNull("angular1", angular1);
		if (angular1 != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("angular1", angular1, NX_NUMBER);
			validateFieldUnits("angular1", group.getDataNode("angular1"), NX_ANGLE);
		}

		// validate field 'data' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldUnits("data", group.getDataNode("data"), NX_ANY);
		}
	}
}
