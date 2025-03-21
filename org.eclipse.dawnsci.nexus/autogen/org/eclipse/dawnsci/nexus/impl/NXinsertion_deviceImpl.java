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
 * An insertion device, as used in a synchrotron light source.

 */
public class NXinsertion_deviceImpl extends NXobjectImpl implements NXinsertion_device {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_OFF_GEOMETRY,
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXinsertion_deviceImpl() {
		super();
	}

	public NXinsertion_deviceImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXinsertion_device.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_INSERTION_DEVICE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public Dataset getGap() {
		return getDataset(NX_GAP);
	}

	@Override
	public Double getGapScalar() {
		return getDouble(NX_GAP);
	}

	@Override
	public DataNode setGap(IDataset gapDataset) {
		return setDataset(NX_GAP, gapDataset);
	}

	@Override
	public DataNode setGapScalar(Double gapValue) {
		return setField(NX_GAP, gapValue);
	}

	@Override
	public Dataset getTaper() {
		return getDataset(NX_TAPER);
	}

	@Override
	public Double getTaperScalar() {
		return getDouble(NX_TAPER);
	}

	@Override
	public DataNode setTaper(IDataset taperDataset) {
		return setDataset(NX_TAPER, taperDataset);
	}

	@Override
	public DataNode setTaperScalar(Double taperValue) {
		return setField(NX_TAPER, taperValue);
	}

	@Override
	public Dataset getPhase() {
		return getDataset(NX_PHASE);
	}

	@Override
	public Double getPhaseScalar() {
		return getDouble(NX_PHASE);
	}

	@Override
	public DataNode setPhase(IDataset phaseDataset) {
		return setDataset(NX_PHASE, phaseDataset);
	}

	@Override
	public DataNode setPhaseScalar(Double phaseValue) {
		return setField(NX_PHASE, phaseValue);
	}

	@Override
	public Dataset getPoles() {
		return getDataset(NX_POLES);
	}

	@Override
	public Long getPolesScalar() {
		return getLong(NX_POLES);
	}

	@Override
	public DataNode setPoles(IDataset polesDataset) {
		return setDataset(NX_POLES, polesDataset);
	}

	@Override
	public DataNode setPolesScalar(Long polesValue) {
		return setField(NX_POLES, polesValue);
	}

	@Override
	public Dataset getMagnetic_wavelength() {
		return getDataset(NX_MAGNETIC_WAVELENGTH);
	}

	@Override
	public Double getMagnetic_wavelengthScalar() {
		return getDouble(NX_MAGNETIC_WAVELENGTH);
	}

	@Override
	public DataNode setMagnetic_wavelength(IDataset magnetic_wavelengthDataset) {
		return setDataset(NX_MAGNETIC_WAVELENGTH, magnetic_wavelengthDataset);
	}

	@Override
	public DataNode setMagnetic_wavelengthScalar(Double magnetic_wavelengthValue) {
		return setField(NX_MAGNETIC_WAVELENGTH, magnetic_wavelengthValue);
	}

	@Override
	public Dataset getK() {
		return getDataset(NX_K);
	}

	@Override
	public Double getKScalar() {
		return getDouble(NX_K);
	}

	@Override
	public DataNode setK(IDataset kDataset) {
		return setDataset(NX_K, kDataset);
	}

	@Override
	public DataNode setKScalar(Double kValue) {
		return setField(NX_K, kValue);
	}

	@Override
	public Dataset getLength() {
		return getDataset(NX_LENGTH);
	}

	@Override
	public Double getLengthScalar() {
		return getDouble(NX_LENGTH);
	}

	@Override
	public DataNode setLength(IDataset lengthDataset) {
		return setDataset(NX_LENGTH, lengthDataset);
	}

	@Override
	public DataNode setLengthScalar(Double lengthValue) {
		return setField(NX_LENGTH, lengthValue);
	}

	@Override
	public Dataset getPower() {
		return getDataset(NX_POWER);
	}

	@Override
	public Double getPowerScalar() {
		return getDouble(NX_POWER);
	}

	@Override
	public DataNode setPower(IDataset powerDataset) {
		return setDataset(NX_POWER, powerDataset);
	}

	@Override
	public DataNode setPowerScalar(Double powerValue) {
		return setField(NX_POWER, powerValue);
	}

	@Override
	public Dataset getEnergy() {
		return getDataset(NX_ENERGY);
	}

	@Override
	public Double getEnergyScalar() {
		return getDouble(NX_ENERGY);
	}

	@Override
	public DataNode setEnergy(IDataset energyDataset) {
		return setDataset(NX_ENERGY, energyDataset);
	}

	@Override
	public DataNode setEnergyScalar(Double energyValue) {
		return setField(NX_ENERGY, energyValue);
	}

	@Override
	public Dataset getBandwidth() {
		return getDataset(NX_BANDWIDTH);
	}

	@Override
	public Double getBandwidthScalar() {
		return getDouble(NX_BANDWIDTH);
	}

	@Override
	public DataNode setBandwidth(IDataset bandwidthDataset) {
		return setDataset(NX_BANDWIDTH, bandwidthDataset);
	}

	@Override
	public DataNode setBandwidthScalar(Double bandwidthValue) {
		return setField(NX_BANDWIDTH, bandwidthValue);
	}

	@Override
	public Dataset getHarmonic() {
		return getDataset(NX_HARMONIC);
	}

	@Override
	public Long getHarmonicScalar() {
		return getLong(NX_HARMONIC);
	}

	@Override
	public DataNode setHarmonic(IDataset harmonicDataset) {
		return setDataset(NX_HARMONIC, harmonicDataset);
	}

	@Override
	public DataNode setHarmonicScalar(Long harmonicValue) {
		return setField(NX_HARMONIC, harmonicValue);
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
	public Dataset getDepends_on() {
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
