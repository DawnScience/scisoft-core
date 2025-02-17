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

import java.util.stream.Collectors;
import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;

import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXaperture;
import org.eclipse.dawnsci.nexus.NXcollimator;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXprocess;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXcollection;

/**
 * Validator for the application definition 'NXcanSAS'.
 */
public class NXcanSASValidator extends AbstractNexusValidator implements NexusApplicationValidator {

	public NXcanSASValidator() {
		super(NexusApplicationDefinition.NX_CANSAS);
	}

	@Override
	public ValidationReport validate(NXroot root) {
		// validate unnamed child group of type NXentry (possibly multiple) and canSAS_class SASentry
		validateUnnamedGroupOccurrences(root, NXentry.class, false, true);
		final Map<String, NXentry> allSASentry = filterBycanSASClass(root.getAllEntry(), "SASentry");
		for (final NXentry entry : allSASentry.values()) {
			validateGroup_SASentry(entry);
		}
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXentry entry) {
		validateGroup_SASentry(entry);
		return validationReport;
	}

	@Override
	public ValidationReport validate(NXsubentry subentry) {
		validateGroup_SASentry(subentry);
		return validationReport;
	}


	private <T extends NXobject> Map<String, T> filterBycanSASClass(Map<String, T> groups, String canSASclass) {
		return groups.entrySet().stream()
			.filter(entry -> canSASclass.equals(entry.getValue().getAttrString(null, "canSAS_class")))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
		
	/**
	 * Validate unnamed group of type NXentry.
	 */
	private void validateGroup_SASentry(final NXsubentry group) {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXentry.class, group))) return;

		// validate optional attribute 'default' of type NX_CHAR.
		final Attribute default_attr = group.getAttribute("default");
		if (default_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("default", default_attr, NX_CHAR);
		}

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASentry");

		// validate attribute 'version' of type NX_CHAR.
		final Attribute version_attr = group.getAttribute("version");
		if (!(validateAttributeNotNull("version", version_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("version", version_attr, NX_CHAR);
		validateAttributeEnumeration("version", version_attr,
				"1.1");

		// validate field 'definition' of type NX_CHAR.
		final ILazyDataset definition = group.getLazyDataset("definition");
		validateFieldNotNull("definition", definition);
		if (definition != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("definition", definition, NX_CHAR);
			validateFieldEnumeration("definition", definition,
					"NXcanSAS");
		}

		// validate field 'title' of type NX_CHAR.
		final ILazyDataset title = group.getLazyDataset("title");
		validateFieldNotNull("title", title);
		if (title != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("title", title, NX_CHAR);
		}

		// validate field 'run' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset run = group.getLazyDataset("run");
		validateFieldNotNull("run", run);
		if (run != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("run", run, NX_CHAR);
		// validate optional attribute 'name' of field 'run' of type NX_CHAR.
		final Attribute run_attr_name = group.getDataNode("run").getAttribute("name");
		if (run_attr_name != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("name", run_attr_name, NX_CHAR);
		}

		}

		// validate unnamed child group of type NXdata (possibly multiple) and canSAS_class SASdata
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allSASdata = filterBycanSASClass(group.getAllData(), "SASdata");
		for (final NXdata data : allSASdata.values()) {
			validateGroup_SASentry_SASdata(data);
		}

		// validate unnamed child group of type NXinstrument (possibly multiple) and canSAS_class SASinstrument
		validateUnnamedGroupOccurrences(group, NXinstrument.class, false, true);
		final Map<String, NXinstrument> allSASinstrument = filterBycanSASClass(group.getAllInstrument(), "SASinstrument");
		for (final NXinstrument instrument : allSASinstrument.values()) {
			validateGroup_SASentry_SASinstrument(instrument);
		}

		// validate unnamed child group of type NXsample (possibly multiple) and canSAS_class SASsample
		validateUnnamedGroupOccurrences(group, NXsample.class, false, true);
		final Map<String, NXsample> allSASsample = filterBycanSASClass(group.getAllSample(), "SASsample");
		for (final NXsample sample : allSASsample.values()) {
			validateGroup_SASentry_SASsample(sample);
		}

		// validate unnamed child group of type NXprocess (possibly multiple) and canSAS_class SASprocess
		validateUnnamedGroupOccurrences(group, NXprocess.class, false, true);
		final Map<String, NXprocess> allSASprocess = filterBycanSASClass(group.getAllProcess(), "SASprocess");
		for (final NXprocess process : allSASprocess.values()) {
			validateGroup_SASentry_SASprocess(process);
		}

		// validate unnamed child group of type NXcollection (possibly multiple) and canSAS_class SASnote
		validateUnnamedGroupOccurrences(group, NXcollection.class, false, true);
		final Map<String, NXcollection> allSASnote = filterBycanSASClass(group.getAllCollection(), "SASnote");
		for (final NXcollection collection : allSASnote.values()) {
			validateGroup_SASentry_SASnote(collection);
		}

		// validate optional child group 'TRANSMISSION_SPECTRUM' of type NXdata and canSAS_class SAStransmission_spectrum
		if (group.getData("TRANSMISSION_SPECTRUM") != null) {
			validateGroup_SASentry_TRANSMISSION_SPECTRUM(group.getData("TRANSMISSION_SPECTRUM"));
		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_SASentry_SASdata(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASdata");

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"I");

		// validate attribute 'I_axes' of type NX_CHAR.
		final Attribute I_axes_attr = group.getAttribute("I_axes");
		if (!(validateAttributeNotNull("I_axes", I_axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("I_axes", I_axes_attr, NX_CHAR);

		// validate attribute 'Q_indices' of type NX_INT.
		final Attribute Q_indices_attr = group.getAttribute("Q_indices");
		if (!(validateAttributeNotNull("Q_indices", Q_indices_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("Q_indices", Q_indices_attr, NX_INT);

		// validate attribute 'mask' of type NX_CHAR.
		final Attribute mask_attr = group.getAttribute("mask");
		if (!(validateAttributeNotNull("mask", mask_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("mask", mask_attr, NX_CHAR);

		// validate optional attribute 'Mask_indices' of type NX_CHAR.
		final Attribute Mask_indices_attr = group.getAttribute("Mask_indices");
		if (Mask_indices_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("Mask_indices", Mask_indices_attr, NX_CHAR);
		}

		// validate optional attribute 'timestamp' of type NX_DATE_TIME.
		final Attribute timestamp_attr = group.getAttribute("timestamp");
		if (timestamp_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("timestamp", timestamp_attr, NX_DATE_TIME);
		}

		// validate field 'Q' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset Q = group.getLazyDataset("Q");
		validateFieldNotNull("Q", Q);
		if (Q != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("Q", Q, NX_NUMBER);
			validateFieldUnits("Q", group.getDataNode("Q"), NX_PER_LENGTH);
		// validate attribute 'units' of field 'Q' of type NX_CHAR.
		final Attribute Q_attr_units = group.getDataNode("Q").getAttribute("units");
		if (!(validateAttributeNotNull("units", Q_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", Q_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", Q_attr_units,
				"1/m",
				"1/nm",
				"1/angstrom");

		// validate optional attribute 'uncertainties' of field 'Q' of type NX_CHAR.
		final Attribute Q_attr_uncertainties = group.getDataNode("Q").getAttribute("uncertainties");
		if (Q_attr_uncertainties != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("uncertainties", Q_attr_uncertainties, NX_CHAR);
		}

		// validate optional attribute 'resolutions' of field 'Q' of type NX_CHAR.
		final Attribute Q_attr_resolutions = group.getDataNode("Q").getAttribute("resolutions");
		if (Q_attr_resolutions != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("resolutions", Q_attr_resolutions, NX_CHAR);
		}

		// validate optional attribute 'resolutions_description' of field 'Q' of type NX_CHAR.
		final Attribute Q_attr_resolutions_description = group.getDataNode("Q").getAttribute("resolutions_description");
		if (Q_attr_resolutions_description != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("resolutions_description", Q_attr_resolutions_description, NX_CHAR);
		}

		}

		// validate field 'I' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset I = group.getLazyDataset("I");
		validateFieldNotNull("I", I);
		if (I != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("I", I, NX_NUMBER);
		// validate attribute 'units' of field 'I' of type NX_CHAR.
		final Attribute I_attr_units = group.getDataNode("I").getAttribute("units");
		if (!(validateAttributeNotNull("units", I_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", I_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", I_attr_units,
				"1/m",
				"1/cm",
				"m2/g",
				"cm2/g",
				"arbitrary");

		// validate optional attribute 'uncertainties' of field 'I' of type NX_CHAR.
		final Attribute I_attr_uncertainties = group.getDataNode("I").getAttribute("uncertainties");
		if (I_attr_uncertainties != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("uncertainties", I_attr_uncertainties, NX_CHAR);
		}

		// validate optional attribute 'scaling_factor' of field 'I' of type NX_CHAR.
		final Attribute I_attr_scaling_factor = group.getDataNode("I").getAttribute("scaling_factor");
		if (I_attr_scaling_factor != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("scaling_factor", I_attr_scaling_factor, NX_CHAR);
		}

		}

		// validate optional field 'Idev' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset Idev = group.getLazyDataset("Idev");
				if (Idev != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("Idev", Idev, NX_NUMBER);
		// validate attribute 'units' of field 'Idev' of type NX_CHAR.
		final Attribute Idev_attr_units = group.getDataNode("Idev").getAttribute("units");
		if (!(validateAttributeNotNull("units", Idev_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", Idev_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", Idev_attr_units,
				"1/m",
				"1/cm",
				"m2/g",
				"cm2/g",
				"arbitrary");

		}

		// validate optional field 'Qdev' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset Qdev = group.getLazyDataset("Qdev");
				if (Qdev != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("Qdev", Qdev, NX_NUMBER);
			validateFieldUnits("Qdev", group.getDataNode("Qdev"), NX_PER_LENGTH);
		// validate attribute 'units' of field 'Qdev' of type NX_CHAR.
		final Attribute Qdev_attr_units = group.getDataNode("Qdev").getAttribute("units");
		if (!(validateAttributeNotNull("units", Qdev_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", Qdev_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", Qdev_attr_units,
				"1/m",
				"1/nm",
				"1/angstrom");

		}

		// validate optional field 'dQw' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset dQw = group.getLazyDataset("dQw");
				if (dQw != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dQw", dQw, NX_NUMBER);
			validateFieldUnits("dQw", group.getDataNode("dQw"), NX_PER_LENGTH);
		// validate attribute 'units' of field 'dQw' of type NX_CHAR.
		final Attribute dQw_attr_units = group.getDataNode("dQw").getAttribute("units");
		if (!(validateAttributeNotNull("units", dQw_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", dQw_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", dQw_attr_units,
				"1/m",
				"1/nm",
				"1/angstrom");

		}

		// validate optional field 'dQl' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset dQl = group.getLazyDataset("dQl");
				if (dQl != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dQl", dQl, NX_NUMBER);
			validateFieldUnits("dQl", group.getDataNode("dQl"), NX_PER_LENGTH);
		// validate attribute 'units' of field 'dQl' of type NX_CHAR.
		final Attribute dQl_attr_units = group.getDataNode("dQl").getAttribute("units");
		if (!(validateAttributeNotNull("units", dQl_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", dQl_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", dQl_attr_units,
				"1/m",
				"1/nm",
				"1/angstrom");

		}

		// validate optional field 'Qmean' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset Qmean = group.getLazyDataset("Qmean");
				if (Qmean != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("Qmean", Qmean, NX_NUMBER);
			validateFieldUnits("Qmean", group.getDataNode("Qmean"), NX_PER_LENGTH);
		// validate attribute 'units' of field 'Qmean' of type NX_CHAR.
		final Attribute Qmean_attr_units = group.getDataNode("Qmean").getAttribute("units");
		if (!(validateAttributeNotNull("units", Qmean_attr_units))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("units", Qmean_attr_units, NX_CHAR);
		validateAttributeEnumeration("units", Qmean_attr_units,
				"1/m",
				"1/nm",
				"1/angstrom");

		}

		// validate optional field 'ShadowFactor' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset ShadowFactor = group.getLazyDataset("ShadowFactor");
				if (ShadowFactor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("ShadowFactor", ShadowFactor, NX_CHAR);
			validateFieldUnits("ShadowFactor", group.getDataNode("ShadowFactor"), NX_DIMENSIONLESS);
		}
	}

	/**
	 * Validate optional unnamed group of type NXinstrument.
	 */
	private void validateGroup_SASentry_SASinstrument(final NXinstrument group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXinstrument.class, group))) return;

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASinstrument");

		// validate unnamed child group of type NXaperture (possibly multiple) and canSAS_class SASaperture
		validateUnnamedGroupOccurrences(group, NXaperture.class, false, true);
		final Map<String, NXaperture> allSASaperture = filterBycanSASClass(group.getAllAperture(), "SASaperture");
		for (final NXaperture aperture : allSASaperture.values()) {
			validateGroup_SASentry_SASinstrument_SASaperture(aperture);
		}

		// validate unnamed child group of type NXcollimator (possibly multiple) and canSAS_class SAScollimation
		validateUnnamedGroupOccurrences(group, NXcollimator.class, false, true);
		final Map<String, NXcollimator> allSAScollimation = filterBycanSASClass(group.getAllCollimator(), "SAScollimation");
		for (final NXcollimator collimator : allSAScollimation.values()) {
			validateGroup_SASentry_SASinstrument_SAScollimation(collimator);
		}

		// validate unnamed child group of type NXdetector (possibly multiple) and canSAS_class SASdetector
		validateUnnamedGroupOccurrences(group, NXdetector.class, false, true);
		final Map<String, NXdetector> allSASdetector = filterBycanSASClass(group.getAllDetector(), "SASdetector");
		for (final NXdetector detector : allSASdetector.values()) {
			validateGroup_SASentry_SASinstrument_SASdetector(detector);
		}

		// validate unnamed child group of type NXsource (possibly multiple) and canSAS_class SASsource
		validateUnnamedGroupOccurrences(group, NXsource.class, false, true);
		final Map<String, NXsource> allSASsource = filterBycanSASClass(group.getAllSource(), "SASsource");
		for (final NXsource source : allSASsource.values()) {
			validateGroup_SASentry_SASinstrument_SASsource(source);
		}
	}

	/**
	 * Validate optional unnamed group of type NXaperture.
	 */
	private void validateGroup_SASentry_SASinstrument_SASaperture(final NXaperture group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXaperture.class, group))) return;

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASaperture");

		// validate field 'shape' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset shape = group.getLazyDataset("shape");
		validateFieldNotNull("shape", shape);
		if (shape != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("shape", shape, NX_CHAR);
		}

		// validate optional field 'x_gap' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset x_gap = group.getLazyDataset("x_gap");
				if (x_gap != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_gap", x_gap, NX_NUMBER);
			validateFieldUnits("x_gap", group.getDataNode("x_gap"), NX_LENGTH);
		}

		// validate optional field 'y_gap' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset y_gap = group.getLazyDataset("y_gap");
				if (y_gap != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_gap", y_gap, NX_NUMBER);
			validateFieldUnits("y_gap", group.getDataNode("y_gap"), NX_LENGTH);
		}
	}

	/**
	 * Validate optional unnamed group of type NXcollimator.
	 */
	private void validateGroup_SASentry_SASinstrument_SAScollimation(final NXcollimator group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcollimator.class, group))) return;

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SAScollimation");

		// validate optional field 'length' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset length = group.getLazyDataset("length");
				if (length != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("length", length, NX_NUMBER);
			validateFieldUnits("length", group.getDataNode("length"), NX_LENGTH);
		}

		// validate optional field 'distance' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset distance = group.getLazyDataset("distance");
				if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_NUMBER);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdetector.
	 */
	private void validateGroup_SASentry_SASinstrument_SASdetector(final NXdetector group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXdetector.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASdetector");

		// validate field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'SDD' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset SDD = group.getLazyDataset("SDD");
				if (SDD != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("SDD", SDD, NX_NUMBER);
			validateFieldUnits("SDD", group.getDataNode("SDD"), NX_LENGTH);
		}

		// validate optional field 'slit_length' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset slit_length = group.getLazyDataset("slit_length");
				if (slit_length != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("slit_length", slit_length, NX_NUMBER);
			validateFieldUnits("slit_length", group.getDataNode("slit_length"), NX_PER_LENGTH);
		}

		// validate optional field 'x_position' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset x_position = group.getLazyDataset("x_position");
				if (x_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_position", x_position, NX_NUMBER);
			validateFieldUnits("x_position", group.getDataNode("x_position"), NX_LENGTH);
		}

		// validate optional field 'y_position' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset y_position = group.getLazyDataset("y_position");
				if (y_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_position", y_position, NX_NUMBER);
			validateFieldUnits("y_position", group.getDataNode("y_position"), NX_LENGTH);
		}

		// validate optional field 'roll' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset roll = group.getLazyDataset("roll");
				if (roll != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("roll", roll, NX_NUMBER);
			validateFieldUnits("roll", group.getDataNode("roll"), NX_ANGLE);
		}

		// validate optional field 'pitch' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset pitch = group.getLazyDataset("pitch");
				if (pitch != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pitch", pitch, NX_NUMBER);
			validateFieldUnits("pitch", group.getDataNode("pitch"), NX_ANGLE);
		}

		// validate optional field 'yaw' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset yaw = group.getLazyDataset("yaw");
				if (yaw != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("yaw", yaw, NX_NUMBER);
			validateFieldUnits("yaw", group.getDataNode("yaw"), NX_ANGLE);
		}

		// validate optional field 'beam_center_x' of type NX_FLOAT.
		final ILazyDataset beam_center_x = group.getLazyDataset("beam_center_x");
				if (beam_center_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_x", beam_center_x, NX_FLOAT);
			validateFieldUnits("beam_center_x", group.getDataNode("beam_center_x"), NX_LENGTH);
		}

		// validate optional field 'beam_center_y' of type NX_FLOAT.
		final ILazyDataset beam_center_y = group.getLazyDataset("beam_center_y");
				if (beam_center_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_y", beam_center_y, NX_FLOAT);
			validateFieldUnits("beam_center_y", group.getDataNode("beam_center_y"), NX_LENGTH);
		}

		// validate optional field 'x_pixel_size' of type NX_FLOAT.
		final ILazyDataset x_pixel_size = group.getLazyDataset("x_pixel_size");
				if (x_pixel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_pixel_size", x_pixel_size, NX_FLOAT);
			validateFieldUnits("x_pixel_size", group.getDataNode("x_pixel_size"), NX_LENGTH);
			validateFieldRank("x_pixel_size", x_pixel_size, 2);
			validateFieldDimensions("x_pixel_size", x_pixel_size, "NXdetector", "i", "j");
		}

		// validate optional field 'y_pixel_size' of type NX_FLOAT.
		final ILazyDataset y_pixel_size = group.getLazyDataset("y_pixel_size");
				if (y_pixel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_pixel_size", y_pixel_size, NX_FLOAT);
			validateFieldUnits("y_pixel_size", group.getDataNode("y_pixel_size"), NX_LENGTH);
			validateFieldRank("y_pixel_size", y_pixel_size, 2);
			validateFieldDimensions("y_pixel_size", y_pixel_size, "NXdetector", "i", "j");
		}
	}

	/**
	 * Validate optional unnamed group of type NXsource.
	 */
	private void validateGroup_SASentry_SASinstrument_SASsource(final NXsource group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsource.class, group))) return;

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASsource");

		// validate optional field 'radiation' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset radiation = group.getLazyDataset("radiation");
				if (radiation != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("radiation", radiation, NX_CHAR);
			validateFieldEnumeration("radiation", radiation,
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
					"UV Plasma Source",
					"neutron",
					"x-ray",
					"muon",
					"electron",
					"ultraviolet",
					"visible light",
					"positron",
					"proton");
		}

		// validate optional field 'beam_shape' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset beam_shape = group.getLazyDataset("beam_shape");
				if (beam_shape != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_shape", beam_shape, NX_CHAR);
		}

		// validate optional field 'incident_wavelength' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset incident_wavelength = group.getLazyDataset("incident_wavelength");
				if (incident_wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength", incident_wavelength, NX_NUMBER);
			validateFieldUnits("incident_wavelength", group.getDataNode("incident_wavelength"), NX_WAVELENGTH);
		}

		// validate optional field 'wavelength_min' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength_min = group.getLazyDataset("wavelength_min");
				if (wavelength_min != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_min", wavelength_min, NX_NUMBER);
			validateFieldUnits("wavelength_min", group.getDataNode("wavelength_min"), NX_WAVELENGTH);
		}

		// validate optional field 'wavelength_max' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset wavelength_max = group.getLazyDataset("wavelength_max");
				if (wavelength_max != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_max", wavelength_max, NX_NUMBER);
			validateFieldUnits("wavelength_max", group.getDataNode("wavelength_max"), NX_WAVELENGTH);
		}

		// validate optional field 'incident_wavelength_spread' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset incident_wavelength_spread = group.getLazyDataset("incident_wavelength_spread");
				if (incident_wavelength_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength_spread", incident_wavelength_spread, NX_NUMBER);
			validateFieldUnits("incident_wavelength_spread", group.getDataNode("incident_wavelength_spread"), NX_WAVELENGTH);
		}

		// validate optional field 'beam_size_x' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset beam_size_x = group.getLazyDataset("beam_size_x");
				if (beam_size_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_size_x", beam_size_x, NX_NUMBER);
			validateFieldUnits("beam_size_x", group.getDataNode("beam_size_x"), NX_LENGTH);
		}

		// validate optional field 'beam_size_y' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset beam_size_y = group.getLazyDataset("beam_size_y");
				if (beam_size_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_size_y", beam_size_y, NX_NUMBER);
			validateFieldUnits("beam_size_y", group.getDataNode("beam_size_y"), NX_LENGTH);
		}
	}

	/**
	 * Validate optional unnamed group of type NXsample.
	 */
	private void validateGroup_SASentry_SASsample(final NXsample group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXsample.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASsample");

		// validate field 'name' of type NX_CHAR.
		final ILazyDataset name = group.getLazyDataset("name");
		validateFieldNotNull("name", name);
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'thickness' of type NX_FLOAT.
		final ILazyDataset thickness = group.getLazyDataset("thickness");
				if (thickness != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("thickness", thickness, NX_FLOAT);
			validateFieldUnits("thickness", group.getDataNode("thickness"), NX_LENGTH);
		}

		// validate optional field 'transmission' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset transmission = group.getLazyDataset("transmission");
				if (transmission != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("transmission", transmission, NX_NUMBER);
			validateFieldUnits("transmission", group.getDataNode("transmission"), NX_DIMENSIONLESS);
		}

		// validate optional field 'temperature' of type NX_NUMBER.
		final ILazyDataset temperature = group.getLazyDataset("temperature");
				if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_NUMBER);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");
		}

		// validate optional field 'details' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset details = group.getLazyDataset("details");
				if (details != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("details", details, NX_CHAR);
		}

		// validate optional field 'x_position' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset x_position = group.getLazyDataset("x_position");
				if (x_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_position", x_position, NX_NUMBER);
			validateFieldUnits("x_position", group.getDataNode("x_position"), NX_LENGTH);
		}

		// validate optional field 'y_position' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset y_position = group.getLazyDataset("y_position");
				if (y_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_position", y_position, NX_NUMBER);
			validateFieldUnits("y_position", group.getDataNode("y_position"), NX_LENGTH);
		}

		// validate optional field 'roll' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset roll = group.getLazyDataset("roll");
				if (roll != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("roll", roll, NX_NUMBER);
			validateFieldUnits("roll", group.getDataNode("roll"), NX_ANGLE);
		}

		// validate optional field 'pitch' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset pitch = group.getLazyDataset("pitch");
				if (pitch != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pitch", pitch, NX_NUMBER);
			validateFieldUnits("pitch", group.getDataNode("pitch"), NX_ANGLE);
		}

		// validate optional field 'yaw' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset yaw = group.getLazyDataset("yaw");
				if (yaw != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("yaw", yaw, NX_NUMBER);
			validateFieldUnits("yaw", group.getDataNode("yaw"), NX_ANGLE);
		}
	}

	/**
	 * Validate optional unnamed group of type NXprocess.
	 */
	private void validateGroup_SASentry_SASprocess(final NXprocess group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXprocess.class, group))) return;

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASprocess");

		// validate optional field 'name' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset name = group.getLazyDataset("name");
				if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("name", name, NX_CHAR);
		}

		// validate optional field 'date' of type NX_DATE_TIME.
		final ILazyDataset date = group.getLazyDataset("date");
				if (date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("date", date, NX_DATE_TIME);
		}

		// validate optional field 'description' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset description = group.getLazyDataset("description");
				if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("description", description, NX_CHAR);
		}

		// validate optional field 'term' of type NX_CHAR. Note: field not defined in base class.
		final ILazyDataset term = group.getLazyDataset("term");
				if (term != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("term", term, NX_CHAR);
		}

		// validate unnamed child group of type NXnote (possibly multiple)
		validateUnnamedGroupOccurrences(group, NXnote.class, false, true);
		final Map<String, NXnote> allNote = group.getAllNote();
		for (final NXnote note : allNote.values()) {
			validateGroup_SASentry_SASprocess_NXnote(note);
		}

		// validate unnamed child group of type NXcollection (possibly multiple) and canSAS_class SASprocessnote
		validateUnnamedGroupOccurrences(group, NXcollection.class, false, true);
		final Map<String, NXcollection> allSASprocessnote = filterBycanSASClass(group.getChildren(NXcollection.class), "SASprocessnote");
		for (final NXcollection collection : allSASprocessnote.values()) {
			validateGroup_SASentry_SASprocess_SASprocessnote(collection);
		}
	}

	/**
	 * Validate optional unnamed group of type NXnote.
	 */
	private void validateGroup_SASentry_SASprocess_NXnote(final NXnote group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXnote.class, group))) return;

	}

	/**
	 * Validate optional unnamed group of type NXcollection.
	 */
	private void validateGroup_SASentry_SASprocess_SASprocessnote(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcollection.class, group))) return;

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASprocessnote");

	}

	/**
	 * Validate optional unnamed group of type NXcollection.
	 */
	private void validateGroup_SASentry_SASnote(final NXcollection group) {
		// validate that the group is not null
		if (!(validateGroupNotNull(null, NXcollection.class, group))) return;

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASnote");

	}

	/**
	 * Validate optional group 'TRANSMISSION_SPECTRUM' of type NXdata.
	 */
	private void validateGroup_SASentry_TRANSMISSION_SPECTRUM(final NXdata group) {
		// validate that the group is not null
		if (!(validateGroupNotNull("TRANSMISSION_SPECTRUM", NXdata.class, group))) return;
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'canSAS_class' of type NX_CHAR.
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		if (!(validateAttributeNotNull("canSAS_class", canSAS_class_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("canSAS_class", canSAS_class_attr, NX_CHAR);
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SAStransmission_spectrum");

		// validate attribute 'signal' of type NX_CHAR.
		final Attribute signal_attr = group.getAttribute("signal");
		if (!(validateAttributeNotNull("signal", signal_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"T");

		// validate attribute 'T_axes' of type NX_CHAR.
		final Attribute T_axes_attr = group.getAttribute("T_axes");
		if (!(validateAttributeNotNull("T_axes", T_axes_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("T_axes", T_axes_attr, NX_CHAR);
		validateAttributeEnumeration("T_axes", T_axes_attr,
				"T");

		// validate attribute 'name' of type NX_CHAR.
		final Attribute name_attr = group.getAttribute("name");
		if (!(validateAttributeNotNull("name", name_attr))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("name", name_attr, NX_CHAR);

		// validate optional attribute 'timestamp' of type NX_DATE_TIME.
		final Attribute timestamp_attr = group.getAttribute("timestamp");
		if (timestamp_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("timestamp", timestamp_attr, NX_DATE_TIME);
		}

		// validate field 'lambda' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset lambda = group.getLazyDataset("lambda");
		validateFieldNotNull("lambda", lambda);
		if (lambda != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("lambda", lambda, NX_NUMBER);
			validateFieldUnits("lambda", group.getDataNode("lambda"), NX_WAVELENGTH);
		}

		// validate field 'T' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset T = group.getLazyDataset("T");
		validateFieldNotNull("T", T);
		if (T != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("T", T, NX_NUMBER);
			validateFieldUnits("T", group.getDataNode("T"), NX_DIMENSIONLESS);
		// validate attribute 'uncertainties' of field 'T' of type NX_CHAR.
		final Attribute T_attr_uncertainties = group.getDataNode("T").getAttribute("uncertainties");
		if (!(validateAttributeNotNull("uncertainties", T_attr_uncertainties))) return;
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("uncertainties", T_attr_uncertainties, NX_CHAR);

		}

		// validate field 'Tdev' of type NX_NUMBER. Note: field not defined in base class.
		final ILazyDataset Tdev = group.getLazyDataset("Tdev");
		validateFieldNotNull("Tdev", Tdev);
		if (Tdev != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("Tdev", Tdev, NX_NUMBER);
			validateFieldUnits("Tdev", group.getDataNode("Tdev"), NX_DIMENSIONLESS);
		}
	}
}
