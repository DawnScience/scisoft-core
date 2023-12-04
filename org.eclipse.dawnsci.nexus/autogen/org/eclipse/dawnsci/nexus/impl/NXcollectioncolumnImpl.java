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

import org.eclipse.dawnsci.nexus.*;

/**
 * Subclass of NXelectronanalyser to describe the electron collection column of a
 * photoelectron analyser.
 * 
 */
public class NXcollectioncolumnImpl extends NXobjectImpl implements NXcollectioncolumn {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_APERTURE,
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_LENS_EM);

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
	public IDataset getScheme() {
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
	public IDataset getExtractor_voltage() {
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
	public IDataset getExtractor_current() {
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
	public IDataset getWorking_distance() {
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
	public IDataset getMode() {
		return getDataset(NX_MODE);
	}

	@Override
	public String getModeScalar() {
		return getString(NX_MODE);
	}

	@Override
	public DataNode setMode(IDataset modeDataset) {
		return setDataset(NX_MODE, modeDataset);
	}

	@Override
	public DataNode setModeScalar(String modeValue) {
		return setString(NX_MODE, modeValue);
	}

	@Override
	public IDataset getProjection() {
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
	public IDataset getMagnification() {
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
	public IDataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
	}

	@Override
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}
	
	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
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
	public NXlens_em getLens_em() {
		// dataNodeName = NX_LENS_EM
		return getChild("lens_em", NXlens_em.class);
	}

	@Override
	public void setLens_em(NXlens_em lens_emGroup) {
		putChild("lens_em", lens_emGroup);
	}

	@Override
	public NXlens_em getLens_em(String name) {
		return getChild(name, NXlens_em.class);
	}

	@Override
	public void setLens_em(String name, NXlens_em lens_em) {
		putChild(name, lens_em);
	}

	@Override
	public Map<String, NXlens_em> getAllLens_em() {
		return getChildren(NXlens_em.class);
	}
	
	@Override
	public void setAllLens_em(Map<String, NXlens_em> lens_em) {
		setChildren(lens_em);
	}

}
