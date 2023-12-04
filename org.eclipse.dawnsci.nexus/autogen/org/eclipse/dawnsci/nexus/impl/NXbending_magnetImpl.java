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
 * A bending magnet
 * 
 */
public class NXbending_magnetImpl extends NXobjectImpl implements NXbending_magnet {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_OFF_GEOMETRY,
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXbending_magnetImpl() {
		super();
	}

	public NXbending_magnetImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXbending_magnet.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_BENDING_MAGNET;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getCritical_energy() {
		return getDataset(NX_CRITICAL_ENERGY);
	}

	@Override
	public Double getCritical_energyScalar() {
		return getDouble(NX_CRITICAL_ENERGY);
	}

	@Override
	public DataNode setCritical_energy(IDataset critical_energyDataset) {
		return setDataset(NX_CRITICAL_ENERGY, critical_energyDataset);
	}

	@Override
	public DataNode setCritical_energyScalar(Double critical_energyValue) {
		return setField(NX_CRITICAL_ENERGY, critical_energyValue);
	}

	@Override
	public IDataset getBending_radius() {
		return getDataset(NX_BENDING_RADIUS);
	}

	@Override
	public Double getBending_radiusScalar() {
		return getDouble(NX_BENDING_RADIUS);
	}

	@Override
	public DataNode setBending_radius(IDataset bending_radiusDataset) {
		return setDataset(NX_BENDING_RADIUS, bending_radiusDataset);
	}

	@Override
	public DataNode setBending_radiusScalar(Double bending_radiusValue) {
		return setField(NX_BENDING_RADIUS, bending_radiusValue);
	}

	@Override
	public IDataset getMagnetic_field() {
		return getDataset(NX_MAGNETIC_FIELD);
	}

	@Override
	public Double getMagnetic_fieldScalar() {
		return getDouble(NX_MAGNETIC_FIELD);
	}

	@Override
	public DataNode setMagnetic_field(IDataset magnetic_fieldDataset) {
		return setDataset(NX_MAGNETIC_FIELD, magnetic_fieldDataset);
	}

	@Override
	public DataNode setMagnetic_fieldScalar(Double magnetic_fieldValue) {
		return setField(NX_MAGNETIC_FIELD, magnetic_fieldValue);
	}

	@Override
	public IDataset getAccepted_photon_beam_divergence() {
		return getDataset(NX_ACCEPTED_PHOTON_BEAM_DIVERGENCE);
	}

	@Override
	public Double getAccepted_photon_beam_divergenceScalar() {
		return getDouble(NX_ACCEPTED_PHOTON_BEAM_DIVERGENCE);
	}

	@Override
	public DataNode setAccepted_photon_beam_divergence(IDataset accepted_photon_beam_divergenceDataset) {
		return setDataset(NX_ACCEPTED_PHOTON_BEAM_DIVERGENCE, accepted_photon_beam_divergenceDataset);
	}

	@Override
	public DataNode setAccepted_photon_beam_divergenceScalar(Double accepted_photon_beam_divergenceValue) {
		return setField(NX_ACCEPTED_PHOTON_BEAM_DIVERGENCE, accepted_photon_beam_divergenceValue);
	}

	@Override
	public IDataset getSource_distance_x() {
		return getDataset(NX_SOURCE_DISTANCE_X);
	}

	@Override
	public Double getSource_distance_xScalar() {
		return getDouble(NX_SOURCE_DISTANCE_X);
	}

	@Override
	public DataNode setSource_distance_x(IDataset source_distance_xDataset) {
		return setDataset(NX_SOURCE_DISTANCE_X, source_distance_xDataset);
	}

	@Override
	public DataNode setSource_distance_xScalar(Double source_distance_xValue) {
		return setField(NX_SOURCE_DISTANCE_X, source_distance_xValue);
	}

	@Override
	public IDataset getSource_distance_y() {
		return getDataset(NX_SOURCE_DISTANCE_Y);
	}

	@Override
	public Double getSource_distance_yScalar() {
		return getDouble(NX_SOURCE_DISTANCE_Y);
	}

	@Override
	public DataNode setSource_distance_y(IDataset source_distance_yDataset) {
		return setDataset(NX_SOURCE_DISTANCE_Y, source_distance_yDataset);
	}

	@Override
	public DataNode setSource_distance_yScalar(Double source_distance_yValue) {
		return setField(NX_SOURCE_DISTANCE_Y, source_distance_yValue);
	}

	@Override
	public IDataset getDivergence_x_plus() {
		return getDataset(NX_DIVERGENCE_X_PLUS);
	}

	@Override
	public Double getDivergence_x_plusScalar() {
		return getDouble(NX_DIVERGENCE_X_PLUS);
	}

	@Override
	public DataNode setDivergence_x_plus(IDataset divergence_x_plusDataset) {
		return setDataset(NX_DIVERGENCE_X_PLUS, divergence_x_plusDataset);
	}

	@Override
	public DataNode setDivergence_x_plusScalar(Double divergence_x_plusValue) {
		return setField(NX_DIVERGENCE_X_PLUS, divergence_x_plusValue);
	}

	@Override
	public IDataset getDivergence_x_minus() {
		return getDataset(NX_DIVERGENCE_X_MINUS);
	}

	@Override
	public Double getDivergence_x_minusScalar() {
		return getDouble(NX_DIVERGENCE_X_MINUS);
	}

	@Override
	public DataNode setDivergence_x_minus(IDataset divergence_x_minusDataset) {
		return setDataset(NX_DIVERGENCE_X_MINUS, divergence_x_minusDataset);
	}

	@Override
	public DataNode setDivergence_x_minusScalar(Double divergence_x_minusValue) {
		return setField(NX_DIVERGENCE_X_MINUS, divergence_x_minusValue);
	}

	@Override
	public IDataset getDivergence_y_plus() {
		return getDataset(NX_DIVERGENCE_Y_PLUS);
	}

	@Override
	public Double getDivergence_y_plusScalar() {
		return getDouble(NX_DIVERGENCE_Y_PLUS);
	}

	@Override
	public DataNode setDivergence_y_plus(IDataset divergence_y_plusDataset) {
		return setDataset(NX_DIVERGENCE_Y_PLUS, divergence_y_plusDataset);
	}

	@Override
	public DataNode setDivergence_y_plusScalar(Double divergence_y_plusValue) {
		return setField(NX_DIVERGENCE_Y_PLUS, divergence_y_plusValue);
	}

	@Override
	public IDataset getDivergence_y_minus() {
		return getDataset(NX_DIVERGENCE_Y_MINUS);
	}

	@Override
	public Double getDivergence_y_minusScalar() {
		return getDouble(NX_DIVERGENCE_Y_MINUS);
	}

	@Override
	public DataNode setDivergence_y_minus(IDataset divergence_y_minusDataset) {
		return setDataset(NX_DIVERGENCE_Y_MINUS, divergence_y_minusDataset);
	}

	@Override
	public DataNode setDivergence_y_minusScalar(Double divergence_y_minusValue) {
		return setField(NX_DIVERGENCE_Y_MINUS, divergence_y_minusValue);
	}

	@Override
	public NXdata getSpectrum() {
		// dataNodeName = NX_SPECTRUM
		return getChild("spectrum", NXdata.class);
	}

	@Override
	public void setSpectrum(NXdata spectrumGroup) {
		putChild("spectrum", spectrumGroup);
	}

	@Override
	@Deprecated
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	@Deprecated
	public NXgeometry getGeometry(String name) {
		return getChild(name, NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry) {
		putChild(name, geometry);
	}

	@Override
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry() {
		return getChildren(NXgeometry.class);
	}
	
	@Override
	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry) {
		setChildren(geometry);
	}

	@Override
	public NXoff_geometry getOff_geometry() {
		// dataNodeName = NX_OFF_GEOMETRY
		return getChild("off_geometry", NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(NXoff_geometry off_geometryGroup) {
		putChild("off_geometry", off_geometryGroup);
	}

	@Override
	public NXoff_geometry getOff_geometry(String name) {
		return getChild(name, NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(String name, NXoff_geometry off_geometry) {
		putChild(name, off_geometry);
	}

	@Override
	public Map<String, NXoff_geometry> getAllOff_geometry() {
		return getChildren(NXoff_geometry.class);
	}
	
	@Override
	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry) {
		setChildren(off_geometry);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
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

}
