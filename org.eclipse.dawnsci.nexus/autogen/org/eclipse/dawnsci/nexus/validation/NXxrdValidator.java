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
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXprocess;

/**
 * Validator for the application definition 'NXxrd'.
 */
public class NXxrdValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXxrdValidator() {
		super(NexusApplicationDefinition.NX_XRD);
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
					"NXxrd");
		}

		// validate unnamed child group of type NXinstrument (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allInstrument = group.getAllInstrument();
		for (final NXinstrument instrument : allInstrument.values()) {
			validateGroup_NXentry_NXinstrument(instrument);
		}

		// validate unnamed child group of type NXdata (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allData = group.getAllData();
		for (final NXdata data : allData.values()) {
			validateGroup_NXentry_NXdata(data);
		}

		// validate unnamed child group of type NXprocess (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXprocess.class, false, true);
		final Map<String, NXprocess> allProcess = group.getAllProcess();
		for (final NXprocess process : allProcess.values()) {
			validateGroup_NXentry_NXprocess(process);
		}
	}

	/**
	 * Validate optional unnamed group of type NXinstrument.
	 */
	private void validateGroup_NXentry_NXinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate unnamed child group of type NXbeam (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXbeam.class, false, true);
		final Map<String, NXbeam> allBeam = group.getAllBeam();
		for (final NXbeam beam : allBeam.values()) {
			validateGroup_NXentry_NXinstrument_NXbeam(beam);
		}

		// validate unnamed child group of type NXdetector (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXdetector.class, false, true);
		final Map<String, NXdetector> allDetector = group.getAllDetector();
		for (final NXdetector detector : allDetector.values()) {
			validateGroup_NXentry_NXinstrument_NXdetector(detector);
		}
	}

	/**
	 * Validate unnamed group of type NXbeam.
	 */
	private void validateGroup_NXentry_NXinstrument_NXbeam(final NXbeam group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXbeam.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'incident_energy' of type NX_FLOAT.
		final ILazyDataset incident_energy = group.getLazyDataset("incident_energy");
		validateFieldNotNull("incident_energy", incident_energy);
		if (incident_energy != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_energy", incident_energy, NX_FLOAT);
			validateFieldUnits("incident_energy", group.getDataNode("incident_energy"), NX_ENERGY);
			validateFieldRank("incident_energy", incident_energy, 1);
			validateFieldDimensions("incident_energy", incident_energy, "NXbeam", "m");
		}
	}

	/**
	 * Validate unnamed group of type NXdetector.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'polar_angle' of type NX_FLOAT.
		final ILazyDataset polar_angle = group.getLazyDataset("polar_angle");
		validateFieldNotNull("polar_angle", polar_angle);
		if (polar_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("polar_angle", polar_angle, NX_FLOAT);
			validateFieldUnits("polar_angle", group.getDataNode("polar_angle"), NX_ANGLE);
			validateFieldRank("polar_angle", polar_angle, 1);
			validateFieldDimensions("polar_angle", polar_angle, null, "nDet");
		// validate attribute 'units' of field 'polar_angle' of type NX_CHAR.
		final Attribute polar_angle_attr_units = group.getDataNode("polar_angle").getAttribute("units");
		if (!(validateAttributeNotNull("units", polar_angle_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", polar_angle_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", polar_angle_attr_units,
				"deg");

		}
		// validate optional child group 'raw_data' of type NXdata
		if (group.getChild("raw_data", NXdata.class) != null) {
			validateGroup_NXentry_NXinstrument_NXdetector_raw_data(group.getChild("raw_data", NXdata.class));
		}
	}

	/**
	 * Validate optional group 'raw_data' of type NXdata.
	 */
	private void validateGroup_NXentry_NXinstrument_NXdetector_raw_data(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("raw_data", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_NXentry_NXdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate field 'polar_angle' of type NX_FLOAT. Note: field not defined in base class.
		final ILazyDataset polar_angle = group.getLazyDataset("polar_angle");
		validateFieldNotNull("polar_angle", polar_angle);
		if (polar_angle != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("polar_angle", polar_angle, NX_FLOAT);
			validateFieldRank("polar_angle", polar_angle, 1);
			validateFieldDimensions("polar_angle", polar_angle, null, "nDet");
		}

		// validate field 'data' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset data = group.getLazyDataset("data");
		validateFieldNotNull("data", data);
		if (data != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("data", data, NX_NUMBER);
			validateFieldRank("data", data, 1);
			validateFieldDimensions("data", data, null, "nDet");
		}
	}

	/**
	 * Validate optional unnamed group of type NXprocess.
	 */
	private void validateGroup_NXentry_NXprocess(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprocess.class, group))) return;

	}
}
