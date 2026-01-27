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
 * Electron collection column of an electron analyzer.

 */
public class NXcollectioncolumnImpl extends NXcomponentImpl implements NXcollectioncolumn {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_APERTURE,
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_ELECTROMAGNETIC_LENS);

	public NXcollectioncolumnImpl() {
		super();
	}

	public NXcollectioncolumnImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcollectioncolumn.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_COLLECTIONCOLUMN;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getScheme() {
		return getDataset(NX_SCHEME);
	}

	@Override
	public String getSchemeScalar() {
		return getString(NX_SCHEME);
	}

	@Override
	public DataNode setScheme(IDataset schemeDataset) {
		return setDataset(NX_SCHEME, schemeDataset);
	}

	@Override
	public DataNode setSchemeScalar(String schemeValue) {
		return setString(NX_SCHEME, schemeValue);
	}

	@Override
	public Dataset getExtractor_voltage() {
		return getDataset(NX_EXTRACTOR_VOLTAGE);
	}

	@Override
	public Double getExtractor_voltageScalar() {
		return getDouble(NX_EXTRACTOR_VOLTAGE);
	}

	@Override
	public DataNode setExtractor_voltage(IDataset extractor_voltageDataset) {
		return setDataset(NX_EXTRACTOR_VOLTAGE, extractor_voltageDataset);
	}

	@Override
	public DataNode setExtractor_voltageScalar(Double extractor_voltageValue) {
		return setField(NX_EXTRACTOR_VOLTAGE, extractor_voltageValue);
	}

	@Override
	public Dataset getExtractor_current() {
		return getDataset(NX_EXTRACTOR_CURRENT);
	}

	@Override
	public Double getExtractor_currentScalar() {
		return getDouble(NX_EXTRACTOR_CURRENT);
	}

	@Override
	public DataNode setExtractor_current(IDataset extractor_currentDataset) {
		return setDataset(NX_EXTRACTOR_CURRENT, extractor_currentDataset);
	}

	@Override
	public DataNode setExtractor_currentScalar(Double extractor_currentValue) {
		return setField(NX_EXTRACTOR_CURRENT, extractor_currentValue);
	}

	@Override
	public Dataset getWorking_distance() {
		return getDataset(NX_WORKING_DISTANCE);
	}

	@Override
	public Double getWorking_distanceScalar() {
		return getDouble(NX_WORKING_DISTANCE);
	}

	@Override
	public DataNode setWorking_distance(IDataset working_distanceDataset) {
		return setDataset(NX_WORKING_DISTANCE, working_distanceDataset);
	}

	@Override
	public DataNode setWorking_distanceScalar(Double working_distanceValue) {
		return setField(NX_WORKING_DISTANCE, working_distanceValue);
	}

	@Override
	public Dataset getLens_mode() {
		return getDataset(NX_LENS_MODE);
	}

	@Override
	public String getLens_modeScalar() {
		return getString(NX_LENS_MODE);
	}

	@Override
	public DataNode setLens_mode(IDataset lens_modeDataset) {
		return setDataset(NX_LENS_MODE, lens_modeDataset);
	}

	@Override
	public DataNode setLens_modeScalar(String lens_modeValue) {
		return setString(NX_LENS_MODE, lens_modeValue);
	}

	@Override
	public Dataset getProjection() {
		return getDataset(NX_PROJECTION);
	}

	@Override
	public String getProjectionScalar() {
		return getString(NX_PROJECTION);
	}

	@Override
	public DataNode setProjection(IDataset projectionDataset) {
		return setDataset(NX_PROJECTION, projectionDataset);
	}

	@Override
	public DataNode setProjectionScalar(String projectionValue) {
		return setString(NX_PROJECTION, projectionValue);
	}

	@Override
	public Dataset getAngular_acceptance() {
		return getDataset(NX_ANGULAR_ACCEPTANCE);
	}

	@Override
	public Double getAngular_acceptanceScalar() {
		return getDouble(NX_ANGULAR_ACCEPTANCE);
	}

	@Override
	public DataNode setAngular_acceptance(IDataset angular_acceptanceDataset) {
		return setDataset(NX_ANGULAR_ACCEPTANCE, angular_acceptanceDataset);
	}

	@Override
	public DataNode setAngular_acceptanceScalar(Double angular_acceptanceValue) {
		return setField(NX_ANGULAR_ACCEPTANCE, angular_acceptanceValue);
	}

	@Override
	public Dataset getSpatial_acceptance() {
		return getDataset(NX_SPATIAL_ACCEPTANCE);
	}

	@Override
	public Double getSpatial_acceptanceScalar() {
		return getDouble(NX_SPATIAL_ACCEPTANCE);
	}

	@Override
	public DataNode setSpatial_acceptance(IDataset spatial_acceptanceDataset) {
		return setDataset(NX_SPATIAL_ACCEPTANCE, spatial_acceptanceDataset);
	}

	@Override
	public DataNode setSpatial_acceptanceScalar(Double spatial_acceptanceValue) {
		return setField(NX_SPATIAL_ACCEPTANCE, spatial_acceptanceValue);
	}

	@Override
	public Dataset getMagnification() {
		return getDataset(NX_MAGNIFICATION);
	}

	@Override
	public Double getMagnificationScalar() {
		return getDouble(NX_MAGNIFICATION);
	}

	@Override
	public DataNode setMagnification(IDataset magnificationDataset) {
		return setDataset(NX_MAGNIFICATION, magnificationDataset);
	}

	@Override
	public DataNode setMagnificationScalar(Double magnificationValue) {
		return setField(NX_MAGNIFICATION, magnificationValue);
	}

	@Override
	public NXaperture getAperture() {
		// dataNodeName = NX_APERTURE
		return getChild("aperture", NXaperture.class);
	}

	@Override
	public void setAperture(NXaperture apertureGroup) {
		putChild("aperture", apertureGroup);
	}

	@Override
	public NXaperture getAperture(String name) {
		return getChild(name, NXaperture.class);
	}

	@Override
	public void setAperture(String name, NXaperture aperture) {
		putChild(name, aperture);
	}

	@Override
	public Map<String, NXaperture> getAllAperture() {
		return getChildren(NXaperture.class);
	}

	@Override
	public void setAllAperture(Map<String, NXaperture> aperture) {
		setChildren(aperture);
	}

	@Override
	public NXdeflector getDeflector() {
		// dataNodeName = NX_DEFLECTOR
		return getChild("deflector", NXdeflector.class);
	}

	@Override
	public void setDeflector(NXdeflector deflectorGroup) {
		putChild("deflector", deflectorGroup);
	}

	@Override
	public NXdeflector getDeflector(String name) {
		return getChild(name, NXdeflector.class);
	}

	@Override
	public void setDeflector(String name, NXdeflector deflector) {
		putChild(name, deflector);
	}

	@Override
	public Map<String, NXdeflector> getAllDeflector() {
		return getChildren(NXdeflector.class);
	}

	@Override
	public void setAllDeflector(Map<String, NXdeflector> deflector) {
		setChildren(deflector);
	}

	@Override
	public NXelectromagnetic_lens getElectromagnetic_lens() {
		// dataNodeName = NX_ELECTROMAGNETIC_LENS
		return getChild("electromagnetic_lens", NXelectromagnetic_lens.class);
	}

	@Override
	public void setElectromagnetic_lens(NXelectromagnetic_lens electromagnetic_lensGroup) {
		putChild("electromagnetic_lens", electromagnetic_lensGroup);
	}

	@Override
	public NXelectromagnetic_lens getElectromagnetic_lens(String name) {
		return getChild(name, NXelectromagnetic_lens.class);
	}

	@Override
	public void setElectromagnetic_lens(String name, NXelectromagnetic_lens electromagnetic_lens) {
		putChild(name, electromagnetic_lens);
	}

	@Override
	public Map<String, NXelectromagnetic_lens> getAllElectromagnetic_lens() {
		return getChildren(NXelectromagnetic_lens.class);
	}

	@Override
	public void setAllElectromagnetic_lens(Map<String, NXelectromagnetic_lens> electromagnetic_lens) {
		setChildren(electromagnetic_lens);
	}

}
