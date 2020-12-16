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
import org.eclipse.january.dataset.IDataset;
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

	@Override
	public void validate(NXroot root) throws NexusValidationException {
		// validate unnamed child group of type NXentry (possibly multiple) and canSAS_class SASentry
		validateUnnamedGroupOccurrences(root, NXentry.class, false, true);
		final Map<String, NXentry> allSASentry = filterBycanSASClass(root.getAllEntry(), "SASentry");
		for (final NXentry entry : allSASentry.values()) {
			validateGroup_SASentry(entry);
		}
	}

	@Override
	public void validate(NXentry entry) throws NexusValidationException {
		validateGroup_SASentry(entry);
	}

	@Override
	public void validate(NXsubentry subentry) throws NexusValidationException {
		validateGroup_SASentry(subentry);
	}


	private <T extends NXobject> Map<String, T> filterBycanSASClass(Map<String, T> groups, String canSASclass) {
		return groups.entrySet().stream()
			.filter(entry -> canSASclass.equals(entry.getValue().getAttrString(null, "canSAS_class")))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
		
	/**
	 * Validate unnamed group of type NXentry.
	 */
	private void validateGroup_SASentry(final NXsubentry group) throws NexusValidationException {
		// set the current entry, required for validating links
		setEntry(group);

		// validate that the group is not null
		validateGroupNotNull(null, NXentry.class, group);

		// validate optional attribute 'default'
		final Attribute default_attr = group.getAttribute("default");
		if (default_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
		}

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASentry");

		// validate attribute 'version'
		final Attribute version_attr = group.getAttribute("version");
		validateAttributeNotNull("version", version_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("version", version_attr,
				"1.1");

		// validate field 'definition' of unknown type.
		final IDataset definition = group.getDefinition();
		validateFieldNotNull("definition", definition);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldEnumeration("definition", definition,
				"NXcanSAS");

		// validate field 'title' of unknown type.
		final IDataset title = group.getTitle();
		validateFieldNotNull("title", title);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions

		// validate field 'run' of unknown type. Note: field not defined in base class.
		final IDataset run = group.getDataset("run");
		validateFieldNotNull("run", run);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		// validate optional attribute 'name' of field 'run'
		final Attribute run_attr_name = group.getDataNode("run").getAttribute("name");
		if (run_attr_name != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
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

		// validate unnamed child group of type NXdata (possibly multiple) and canSAS_class SAStransmission_spectrum
		validateUnnamedGroupOccurrences(group, NXdata.class, false, true);
		final Map<String, NXdata> allSAStransmission_spectrum = filterBycanSASClass(group.getAllData(), "SAStransmission_spectrum");
		for (final NXdata data : allSAStransmission_spectrum.values()) {
			validateGroup_SASentry_SAStransmission_spectrum(data);
		}
	}

	/**
	 * Validate unnamed group of type NXdata.
	 */
	private void validateGroup_SASentry_SASdata(final NXdata group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXdata.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASdata");

		// validate attribute 'signal'
		final Attribute signal_attr = group.getAttribute("signal");
		validateAttributeNotNull("signal", signal_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"I");

		// validate attribute 'I_axes'
		final Attribute I_axes_attr = group.getAttribute("I_axes");
		validateAttributeNotNull("I_axes", I_axes_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration

		// validate attribute 'Q_indices'
		final Attribute Q_indices_attr = group.getAttribute("Q_indices");
		validateAttributeNotNull("Q_indices", Q_indices_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("Q_indices", Q_indices_attr, NX_INT);

		// validate attribute 'mask'
		final Attribute mask_attr = group.getAttribute("mask");
		validateAttributeNotNull("mask", mask_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("mask", mask_attr, NX_CHAR);

		// validate optional attribute 'Mask_indices'
		final Attribute Mask_indices_attr = group.getAttribute("Mask_indices");
		if (Mask_indices_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
		}

		// validate optional attribute 'timestamp'
		final Attribute timestamp_attr = group.getAttribute("timestamp");
		if (timestamp_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("timestamp", timestamp_attr, NX_DATE_TIME);
		}

		// validate field 'Q' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset Q = group.getDataset("Q");
		validateFieldNotNull("Q", Q);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("Q", Q, NX_NUMBER);
		validateFieldUnits("Q", group.getDataNode("Q"), NX_PER_LENGTH);
		// validate attribute 'units' of field 'Q'
		final Attribute Q_attr_units = group.getDataNode("Q").getAttribute("units");
		validateAttributeNotNull("units", Q_attr_units);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("units", Q_attr_units,
				"1/m",
				"1/nm",
				"1/angstrom");

		// validate optional attribute 'uncertainties' of field 'Q'
		final Attribute Q_attr_uncertainties = group.getDataNode("Q").getAttribute("uncertainties");
		if (Q_attr_uncertainties != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
		}

		// validate optional attribute 'resolutions' of field 'Q'
		final Attribute Q_attr_resolutions = group.getDataNode("Q").getAttribute("resolutions");
		if (Q_attr_resolutions != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("resolutions", Q_attr_resolutions, NX_CHAR);
		}

		// validate optional attribute 'resolutions_description' of field 'Q'
		final Attribute Q_attr_resolutions_description = group.getDataNode("Q").getAttribute("resolutions_description");
		if (Q_attr_resolutions_description != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("resolutions_description", Q_attr_resolutions_description, NX_CHAR);
		}


		// validate field 'I' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset I = group.getDataset("I");
		validateFieldNotNull("I", I);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("I", I, NX_NUMBER);
		// validate attribute 'units' of field 'I'
		final Attribute I_attr_units = group.getDataNode("I").getAttribute("units");
		validateAttributeNotNull("units", I_attr_units);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("units", I_attr_units,
				"1/m",
				"1/cm",
				"m2/g",
				"cm2/g",
				"arbitrary");

		// validate optional attribute 'uncertainties' of field 'I'
		final Attribute I_attr_uncertainties = group.getDataNode("I").getAttribute("uncertainties");
		if (I_attr_uncertainties != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
		}

		// validate optional attribute 'scaling_factor' of field 'I'
		final Attribute I_attr_scaling_factor = group.getDataNode("I").getAttribute("scaling_factor");
		if (I_attr_scaling_factor != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
		}


		// validate optional field 'Idev' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset Idev = group.getDataset("Idev");
		if (Idev != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("Idev", Idev, NX_NUMBER);
		// validate attribute 'units' of field 'Idev'
		final Attribute Idev_attr_units = group.getDataNode("Idev").getAttribute("units");
		validateAttributeNotNull("units", Idev_attr_units);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("units", Idev_attr_units,
				"1/m",
				"1/cm",
				"m2/g",
				"cm2/g",
				"arbitrary");

		}

		// validate optional field 'Qdev' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset Qdev = group.getDataset("Qdev");
		if (Qdev != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("Qdev", Qdev, NX_NUMBER);
			validateFieldUnits("Qdev", group.getDataNode("Qdev"), NX_PER_LENGTH);
		// validate attribute 'units' of field 'Qdev'
		final Attribute Qdev_attr_units = group.getDataNode("Qdev").getAttribute("units");
		validateAttributeNotNull("units", Qdev_attr_units);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("units", Qdev_attr_units,
				"1/m",
				"1/nm",
				"1/angstrom");

		}

		// validate optional field 'dQw' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset dQw = group.getDataset("dQw");
		if (dQw != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dQw", dQw, NX_NUMBER);
			validateFieldUnits("dQw", group.getDataNode("dQw"), NX_PER_LENGTH);
		// validate attribute 'units' of field 'dQw'
		final Attribute dQw_attr_units = group.getDataNode("dQw").getAttribute("units");
		validateAttributeNotNull("units", dQw_attr_units);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("units", dQw_attr_units,
				"1/m",
				"1/nm",
				"1/angstrom");

		}

		// validate optional field 'dQl' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset dQl = group.getDataset("dQl");
		if (dQl != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("dQl", dQl, NX_NUMBER);
			validateFieldUnits("dQl", group.getDataNode("dQl"), NX_PER_LENGTH);
		// validate attribute 'units' of field 'dQl'
		final Attribute dQl_attr_units = group.getDataNode("dQl").getAttribute("units");
		validateAttributeNotNull("units", dQl_attr_units);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("units", dQl_attr_units,
				"1/m",
				"1/nm",
				"1/angstrom");

		}

		// validate optional field 'Qmean' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset Qmean = group.getDataset("Qmean");
		if (Qmean != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("Qmean", Qmean, NX_NUMBER);
			validateFieldUnits("Qmean", group.getDataNode("Qmean"), NX_PER_LENGTH);
		// validate attribute 'units' of field 'Qmean'
		final Attribute Qmean_attr_units = group.getDataNode("Qmean").getAttribute("units");
		validateAttributeNotNull("units", Qmean_attr_units);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("units", Qmean_attr_units,
				"1/m",
				"1/nm",
				"1/angstrom");

		}

		// validate optional field 'ShadowFactor' of unknown type. Note: field not defined in base class.
		final IDataset ShadowFactor = group.getDataset("ShadowFactor");
		if (ShadowFactor != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldUnits("ShadowFactor", group.getDataNode("ShadowFactor"), NX_DIMENSIONLESS);
		}
	}

	/**
	 * Validate optional unnamed group of type NXinstrument.
	 */
	private void validateGroup_SASentry_SASinstrument(final NXinstrument group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXinstrument.class, group);

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
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
	private void validateGroup_SASentry_SASinstrument_SASaperture(final NXaperture group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXaperture.class, group);

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASaperture");

		// validate field 'shape' of unknown type. Note: field not defined in base class.
		final IDataset shape = group.getDataset("shape");
		validateFieldNotNull("shape", shape);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions

		// validate optional field 'x_gap' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset x_gap = group.getDataset("x_gap");
		if (x_gap != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_gap", x_gap, NX_NUMBER);
			validateFieldUnits("x_gap", group.getDataNode("x_gap"), NX_LENGTH);
		}

		// validate optional field 'y_gap' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset y_gap = group.getDataset("y_gap");
		if (y_gap != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_gap", y_gap, NX_NUMBER);
			validateFieldUnits("y_gap", group.getDataNode("y_gap"), NX_LENGTH);
		}
	}

	/**
	 * Validate optional unnamed group of type NXcollimator.
	 */
	private void validateGroup_SASentry_SASinstrument_SAScollimation(final NXcollimator group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXcollimator.class, group);

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SAScollimation");

		// validate optional field 'length' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset length = group.getDataset("length");
		if (length != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("length", length, NX_NUMBER);
			validateFieldUnits("length", group.getDataNode("length"), NX_LENGTH);
		}

		// validate optional field 'distance' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset distance = group.getDataset("distance");
		if (distance != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("distance", distance, NX_NUMBER);
			validateFieldUnits("distance", group.getDataNode("distance"), NX_LENGTH);
		}
	}

	/**
	 * Validate optional unnamed group of type NXdetector.
	 */
	private void validateGroup_SASentry_SASinstrument_SASdetector(final NXdetector group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXdetector.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASdetector");

		// validate field 'name' of unknown type. Note: field not defined in base class.
		final IDataset name = group.getDataset("name");
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions

		// validate optional field 'SDD' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset SDD = group.getDataset("SDD");
		if (SDD != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("SDD", SDD, NX_NUMBER);
			validateFieldUnits("SDD", group.getDataNode("SDD"), NX_LENGTH);
		}

		// validate optional field 'slit_length' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset slit_length = group.getDataset("slit_length");
		if (slit_length != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("slit_length", slit_length, NX_NUMBER);
			validateFieldUnits("slit_length", group.getDataNode("slit_length"), NX_PER_LENGTH);
		}

		// validate optional field 'x_position' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset x_position = group.getDataset("x_position");
		if (x_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_position", x_position, NX_NUMBER);
			validateFieldUnits("x_position", group.getDataNode("x_position"), NX_LENGTH);
		}

		// validate optional field 'y_position' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset y_position = group.getDataset("y_position");
		if (y_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_position", y_position, NX_NUMBER);
			validateFieldUnits("y_position", group.getDataNode("y_position"), NX_LENGTH);
		}

		// validate optional field 'roll' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset roll = group.getDataset("roll");
		if (roll != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("roll", roll, NX_NUMBER);
			validateFieldUnits("roll", group.getDataNode("roll"), NX_ANGLE);
		}

		// validate optional field 'pitch' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset pitch = group.getDataset("pitch");
		if (pitch != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pitch", pitch, NX_NUMBER);
			validateFieldUnits("pitch", group.getDataNode("pitch"), NX_ANGLE);
		}

		// validate optional field 'yaw' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset yaw = group.getDataset("yaw");
		if (yaw != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("yaw", yaw, NX_NUMBER);
			validateFieldUnits("yaw", group.getDataNode("yaw"), NX_ANGLE);
		}

		// validate optional field 'beam_center_x' of type NX_FLOAT.
		final IDataset beam_center_x = group.getBeam_center_x();
		if (beam_center_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_x", beam_center_x, NX_FLOAT);
			validateFieldUnits("beam_center_x", group.getDataNode("beam_center_x"), NX_LENGTH);
		}

		// validate optional field 'beam_center_y' of type NX_FLOAT.
		final IDataset beam_center_y = group.getBeam_center_y();
		if (beam_center_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_center_y", beam_center_y, NX_FLOAT);
			validateFieldUnits("beam_center_y", group.getDataNode("beam_center_y"), NX_LENGTH);
		}

		// validate optional field 'x_pixel_size' of type NX_FLOAT.
		final IDataset x_pixel_size = group.getX_pixel_size();
		if (x_pixel_size != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_pixel_size", x_pixel_size, NX_FLOAT);
			validateFieldUnits("x_pixel_size", group.getDataNode("x_pixel_size"), NX_LENGTH);
			validateFieldRank("x_pixel_size", x_pixel_size, 2);
			validateFieldDimensions("x_pixel_size", x_pixel_size, "NXdetector", "i", "j");
		}

		// validate optional field 'y_pixel_size' of type NX_FLOAT.
		final IDataset y_pixel_size = group.getY_pixel_size();
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
	private void validateGroup_SASentry_SASinstrument_SASsource(final NXsource group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXsource.class, group);

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASsource");

		// validate field 'radiation' of unknown type. Note: field not defined in base class.
		final IDataset radiation = group.getDataset("radiation");
		validateFieldNotNull("radiation", radiation);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
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

		// validate optional field 'beam_shape' of unknown type. Note: field not defined in base class.
		final IDataset beam_shape = group.getDataset("beam_shape");
		if (beam_shape != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		}

		// validate optional field 'incident_wavelength' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset incident_wavelength = group.getDataset("incident_wavelength");
		if (incident_wavelength != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength", incident_wavelength, NX_NUMBER);
			validateFieldUnits("incident_wavelength", group.getDataNode("incident_wavelength"), NX_WAVELENGTH);
		}

		// validate optional field 'wavelength_min' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset wavelength_min = group.getDataset("wavelength_min");
		if (wavelength_min != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_min", wavelength_min, NX_NUMBER);
			validateFieldUnits("wavelength_min", group.getDataNode("wavelength_min"), NX_WAVELENGTH);
		}

		// validate optional field 'wavelength_max' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset wavelength_max = group.getDataset("wavelength_max");
		if (wavelength_max != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("wavelength_max", wavelength_max, NX_NUMBER);
			validateFieldUnits("wavelength_max", group.getDataNode("wavelength_max"), NX_WAVELENGTH);
		}

		// validate optional field 'incident_wavelength_spread' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset incident_wavelength_spread = group.getDataset("incident_wavelength_spread");
		if (incident_wavelength_spread != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("incident_wavelength_spread", incident_wavelength_spread, NX_NUMBER);
			validateFieldUnits("incident_wavelength_spread", group.getDataNode("incident_wavelength_spread"), NX_WAVELENGTH);
		}

		// validate optional field 'beam_size_x' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset beam_size_x = group.getDataset("beam_size_x");
		if (beam_size_x != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_size_x", beam_size_x, NX_NUMBER);
			validateFieldUnits("beam_size_x", group.getDataNode("beam_size_x"), NX_LENGTH);
		}

		// validate optional field 'beam_size_y' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset beam_size_y = group.getDataset("beam_size_y");
		if (beam_size_y != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("beam_size_y", beam_size_y, NX_NUMBER);
			validateFieldUnits("beam_size_y", group.getDataNode("beam_size_y"), NX_LENGTH);
		}
	}

	/**
	 * Validate optional unnamed group of type NXsample.
	 */
	private void validateGroup_SASentry_SASsample(final NXsample group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXsample.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASsample");

		// validate field 'name' of unknown type.
		final IDataset name = group.getName();
		validateFieldNotNull("name", name);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions

		// validate optional field 'thickness' of type NX_FLOAT.
		final IDataset thickness = group.getThickness();
		if (thickness != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("thickness", thickness, NX_FLOAT);
			validateFieldUnits("thickness", group.getDataNode("thickness"), NX_LENGTH);
		}

		// validate optional field 'transmission' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset transmission = group.getDataset("transmission");
		if (transmission != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("transmission", transmission, NX_NUMBER);
			validateFieldUnits("transmission", group.getDataNode("transmission"), NX_DIMENSIONLESS);
		}

		// validate optional field 'temperature' of type NX_NUMBER.
		final IDataset temperature = group.getTemperature();
		if (temperature != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("temperature", temperature, NX_NUMBER);
			validateFieldUnits("temperature", group.getDataNode("temperature"), NX_TEMPERATURE);
			validateFieldDimensions("temperature", temperature, "NXsample", "n_Temp");
		}

		// validate optional field 'details' of unknown type. Note: field not defined in base class.
		final IDataset details = group.getDataset("details");
		if (details != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		}

		// validate optional field 'x_position' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset x_position = group.getDataset("x_position");
		if (x_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("x_position", x_position, NX_NUMBER);
			validateFieldUnits("x_position", group.getDataNode("x_position"), NX_LENGTH);
		}

		// validate optional field 'y_position' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset y_position = group.getDataset("y_position");
		if (y_position != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("y_position", y_position, NX_NUMBER);
			validateFieldUnits("y_position", group.getDataNode("y_position"), NX_LENGTH);
		}

		// validate optional field 'roll' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset roll = group.getDataset("roll");
		if (roll != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("roll", roll, NX_NUMBER);
			validateFieldUnits("roll", group.getDataNode("roll"), NX_ANGLE);
		}

		// validate optional field 'pitch' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset pitch = group.getDataset("pitch");
		if (pitch != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("pitch", pitch, NX_NUMBER);
			validateFieldUnits("pitch", group.getDataNode("pitch"), NX_ANGLE);
		}

		// validate optional field 'yaw' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset yaw = group.getDataset("yaw");
		if (yaw != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("yaw", yaw, NX_NUMBER);
			validateFieldUnits("yaw", group.getDataNode("yaw"), NX_ANGLE);
		}
	}

	/**
	 * Validate optional unnamed group of type NXprocess.
	 */
	private void validateGroup_SASentry_SASprocess(final NXprocess group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXprocess.class, group);

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASprocess");

		// validate optional field 'name' of unknown type. Note: field not defined in base class.
		final IDataset name = group.getDataset("name");
		if (name != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		}

		// validate optional field 'date' of type NX_DATE_TIME.
		final IDataset date = group.getDate();
		if (date != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
			validateFieldType("date", date, NX_DATE_TIME);
		}

		// validate optional field 'description' of unknown type. Note: field not defined in base class.
		final IDataset description = group.getDataset("description");
		if (description != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		}

		// validate optional field 'term' of unknown type. Note: field not defined in base class.
		final IDataset term = group.getDataset("term");
		if (term != null) {
			// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
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
	private void validateGroup_SASentry_SASprocess_NXnote(final NXnote group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXnote.class, group);

	}

	/**
	 * Validate optional unnamed group of type NXcollection.
	 */
	private void validateGroup_SASentry_SASprocess_SASprocessnote(final NXcollection group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXcollection.class, group);

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASprocessnote");

	}

	/**
	 * Validate optional unnamed group of type NXcollection.
	 */
	private void validateGroup_SASentry_SASnote(final NXcollection group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXcollection.class, group);

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SASnote");

	}

	/**
	 * Validate optional unnamed group of type NXdata.
	 */
	private void validateGroup_SASentry_SAStransmission_spectrum(final NXdata group) throws NexusValidationException {
		// validate that the group is not null
		validateGroupNotNull(null, NXdata.class, group);
		clearLocalGroupDimensionPlaceholderValues();

		// validate attribute 'canSAS_class'
		final Attribute canSAS_class_attr = group.getAttribute("canSAS_class");
		validateAttributeNotNull("canSAS_class", canSAS_class_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("canSAS_class", canSAS_class_attr,
				"SAStransmission_spectrum");

		// validate attribute 'signal'
		final Attribute signal_attr = group.getAttribute("signal");
		validateAttributeNotNull("signal", signal_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeType("signal", signal_attr, NX_CHAR);
		validateAttributeEnumeration("signal", signal_attr,
				"T");

		// validate attribute 'T_axes'
		final Attribute T_axes_attr = group.getAttribute("T_axes");
		validateAttributeNotNull("T_axes", T_axes_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration
		validateAttributeEnumeration("T_axes", T_axes_attr,
				"T");

		// validate attribute 'name'
		final Attribute name_attr = group.getAttribute("name");
		validateAttributeNotNull("name", name_attr);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration

		// validate optional attribute 'timestamp'
		final Attribute timestamp_attr = group.getAttribute("timestamp");
		if (timestamp_attr != null) {
			// validate any properties of this attribute specified in the NXDL file: type, enumeration
			validateAttributeType("timestamp", timestamp_attr, NX_DATE_TIME);
		}

		// validate field 'lambda' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset lambda = group.getDataset("lambda");
		validateFieldNotNull("lambda", lambda);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("lambda", lambda, NX_NUMBER);
		validateFieldUnits("lambda", group.getDataNode("lambda"), NX_WAVELENGTH);

		// validate field 'T' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset T = group.getDataset("T");
		validateFieldNotNull("T", T);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("T", T, NX_NUMBER);
		validateFieldUnits("T", group.getDataNode("T"), NX_DIMENSIONLESS);
		// validate attribute 'uncertainties' of field 'T'
		final Attribute T_attr_uncertainties = group.getDataNode("T").getAttribute("uncertainties");
		validateAttributeNotNull("uncertainties", T_attr_uncertainties);
		// validate any properties of this attribute specified in the NXDL file: type, enumeration


		// validate field 'Tdev' of type NX_NUMBER. Note: field not defined in base class.
		final IDataset Tdev = group.getDataset("Tdev");
		validateFieldNotNull("Tdev", Tdev);
		// validate any properties of this field specified in the NXDL file: type, units, enumeration, dimensions
		validateFieldType("Tdev", Tdev, NX_NUMBER);
		validateFieldUnits("Tdev", group.getDataNode("Tdev"), NX_DIMENSIONLESS);
	}
}
