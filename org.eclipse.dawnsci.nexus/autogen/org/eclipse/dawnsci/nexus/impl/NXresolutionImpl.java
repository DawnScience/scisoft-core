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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Describes the resolution of a physical quantity.

 */
public class NXresolutionImpl extends NXobjectImpl implements NXresolution {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_PARAMETERS,
		NexusBaseClass.NX_CALIBRATION);

	public NXresolutionImpl() {
		super();
	}

	public NXresolutionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXresolution.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_RESOLUTION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getPhysical_quantity() {
		return getDataset(NX_PHYSICAL_QUANTITY);
	}

	@Override
	public String getPhysical_quantityScalar() {
		return getString(NX_PHYSICAL_QUANTITY);
	}

	@Override
	public DataNode setPhysical_quantity(IDataset physical_quantityDataset) {
		return setDataset(NX_PHYSICAL_QUANTITY, physical_quantityDataset);
	}

	@Override
	public DataNode setPhysical_quantityScalar(String physical_quantityValue) {
		return setString(NX_PHYSICAL_QUANTITY, physical_quantityValue);
	}

	@Override
	public Dataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public NXnote getNote() {
		// dataNodeName = NX_NOTE
		return getChild("note", NXnote.class);
	}

	@Override
	public void setNote(NXnote noteGroup) {
		putChild("note", noteGroup);
	}

	@Override
	public Dataset getResolution() {
		return getDataset(NX_RESOLUTION);
	}

	@Override
	public Double getResolutionScalar() {
		return getDouble(NX_RESOLUTION);
	}

	@Override
	public DataNode setResolution(IDataset resolutionDataset) {
		return setDataset(NX_RESOLUTION, resolutionDataset);
	}

	@Override
	public DataNode setResolutionScalar(Double resolutionValue) {
		return setField(NX_RESOLUTION, resolutionValue);
	}

	@Override
	public Dataset getResolution_errors() {
		return getDataset(NX_RESOLUTION_ERRORS);
	}

	@Override
	public Double getResolution_errorsScalar() {
		return getDouble(NX_RESOLUTION_ERRORS);
	}

	@Override
	public DataNode setResolution_errors(IDataset resolution_errorsDataset) {
		return setDataset(NX_RESOLUTION_ERRORS, resolution_errorsDataset);
	}

	@Override
	public DataNode setResolution_errorsScalar(Double resolution_errorsValue) {
		return setField(NX_RESOLUTION_ERRORS, resolution_errorsValue);
	}

	@Override
	public Dataset getRelative_resolution() {
		return getDataset(NX_RELATIVE_RESOLUTION);
	}

	@Override
	public Double getRelative_resolutionScalar() {
		return getDouble(NX_RELATIVE_RESOLUTION);
	}

	@Override
	public DataNode setRelative_resolution(IDataset relative_resolutionDataset) {
		return setDataset(NX_RELATIVE_RESOLUTION, relative_resolutionDataset);
	}

	@Override
	public DataNode setRelative_resolutionScalar(Double relative_resolutionValue) {
		return setField(NX_RELATIVE_RESOLUTION, relative_resolutionValue);
	}

	@Override
	public Dataset getRelative_resolution_errors() {
		return getDataset(NX_RELATIVE_RESOLUTION_ERRORS);
	}

	@Override
	public Double getRelative_resolution_errorsScalar() {
		return getDouble(NX_RELATIVE_RESOLUTION_ERRORS);
	}

	@Override
	public DataNode setRelative_resolution_errors(IDataset relative_resolution_errorsDataset) {
		return setDataset(NX_RELATIVE_RESOLUTION_ERRORS, relative_resolution_errorsDataset);
	}

	@Override
	public DataNode setRelative_resolution_errorsScalar(Double relative_resolution_errorsValue) {
		return setField(NX_RELATIVE_RESOLUTION_ERRORS, relative_resolution_errorsValue);
	}

	@Override
	public NXdata getResponse_function() {
		// dataNodeName = NX_RESPONSE_FUNCTION
		return getChild("response_function", NXdata.class);
	}

	@Override
	public void setResponse_function(NXdata response_functionGroup) {
		putChild("response_function", response_functionGroup);
	}

	@Override
	public NXparameters getFormula_symbols() {
		// dataNodeName = NX_FORMULA_SYMBOLS
		return getChild("formula_symbols", NXparameters.class);
	}

	@Override
	public void setFormula_symbols(NXparameters formula_symbolsGroup) {
		putChild("formula_symbols", formula_symbolsGroup);
	}

	@Override
	public Dataset getResolution_formula_description() {
		return getDataset(NX_RESOLUTION_FORMULA_DESCRIPTION);
	}

	@Override
	public String getResolution_formula_descriptionScalar() {
		return getString(NX_RESOLUTION_FORMULA_DESCRIPTION);
	}

	@Override
	public DataNode setResolution_formula_description(IDataset resolution_formula_descriptionDataset) {
		return setDataset(NX_RESOLUTION_FORMULA_DESCRIPTION, resolution_formula_descriptionDataset);
	}

	@Override
	public DataNode setResolution_formula_descriptionScalar(String resolution_formula_descriptionValue) {
		return setString(NX_RESOLUTION_FORMULA_DESCRIPTION, resolution_formula_descriptionValue);
	}

	@Override
	public NXcalibration getCalibration() {
		// dataNodeName = NX_CALIBRATION
		return getChild("calibration", NXcalibration.class);
	}

	@Override
	public void setCalibration(NXcalibration calibrationGroup) {
		putChild("calibration", calibrationGroup);
	}

	@Override
	public NXcalibration getCalibration(String name) {
		return getChild(name, NXcalibration.class);
	}

	@Override
	public void setCalibration(String name, NXcalibration calibration) {
		putChild(name, calibration);
	}

	@Override
	public Map<String, NXcalibration> getAllCalibration() {
		return getChildren(NXcalibration.class);
	}

	@Override
	public void setAllCalibration(Map<String, NXcalibration> calibration) {
		setChildren(calibration);
	}

}
