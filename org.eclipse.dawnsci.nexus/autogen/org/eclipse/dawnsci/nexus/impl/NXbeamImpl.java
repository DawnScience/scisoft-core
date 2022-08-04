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
 * Properties of the neutron or X-ray beam at a given location.
 * It will be referenced
 * by beamline component groups within the :ref:`NXinstrument` group or by the :ref:`NXsample` group. Note
 * that variables such as the incident energy could be scalar values or arrays. This group is
 * especially valuable in storing the results of instrument simulations in which it is useful
 * to specify the beam profile, time distribution etc. at each beamline component. Otherwise,
 * its most likely use is in the :ref:`NXsample` group in which it defines the results of the neutron
 * scattering by the sample, e.g., energy transfer, polarizations.
 * 
 */
public class NXbeamImpl extends NXobjectImpl implements NXbeam {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA);

	public NXbeamImpl() {
		super();
	}

	public NXbeamImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXbeam.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_BEAM;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getDistance() {
		return getDataset(NX_DISTANCE);
	}

	@Override
	public Double getDistanceScalar() {
		return getDouble(NX_DISTANCE);
	}

	@Override
	public DataNode setDistance(IDataset distanceDataset) {
		return setDataset(NX_DISTANCE, distanceDataset);
	}

	@Override
	public DataNode setDistanceScalar(Double distanceValue) {
		return setField(NX_DISTANCE, distanceValue);
	}

	@Override
	public IDataset getIncident_energy() {
		return getDataset(NX_INCIDENT_ENERGY);
	}

	@Override
	public Double getIncident_energyScalar() {
		return getDouble(NX_INCIDENT_ENERGY);
	}

	@Override
	public DataNode setIncident_energy(IDataset incident_energyDataset) {
		return setDataset(NX_INCIDENT_ENERGY, incident_energyDataset);
	}

	@Override
	public DataNode setIncident_energyScalar(Double incident_energyValue) {
		return setField(NX_INCIDENT_ENERGY, incident_energyValue);
	}

	@Override
	public IDataset getFinal_energy() {
		return getDataset(NX_FINAL_ENERGY);
	}

	@Override
	public Double getFinal_energyScalar() {
		return getDouble(NX_FINAL_ENERGY);
	}

	@Override
	public DataNode setFinal_energy(IDataset final_energyDataset) {
		return setDataset(NX_FINAL_ENERGY, final_energyDataset);
	}

	@Override
	public DataNode setFinal_energyScalar(Double final_energyValue) {
		return setField(NX_FINAL_ENERGY, final_energyValue);
	}

	@Override
	public IDataset getEnergy_transfer() {
		return getDataset(NX_ENERGY_TRANSFER);
	}

	@Override
	public Double getEnergy_transferScalar() {
		return getDouble(NX_ENERGY_TRANSFER);
	}

	@Override
	public DataNode setEnergy_transfer(IDataset energy_transferDataset) {
		return setDataset(NX_ENERGY_TRANSFER, energy_transferDataset);
	}

	@Override
	public DataNode setEnergy_transferScalar(Double energy_transferValue) {
		return setField(NX_ENERGY_TRANSFER, energy_transferValue);
	}

	@Override
	public IDataset getIncident_wavelength() {
		return getDataset(NX_INCIDENT_WAVELENGTH);
	}

	@Override
	public Double getIncident_wavelengthScalar() {
		return getDouble(NX_INCIDENT_WAVELENGTH);
	}

	@Override
	public DataNode setIncident_wavelength(IDataset incident_wavelengthDataset) {
		return setDataset(NX_INCIDENT_WAVELENGTH, incident_wavelengthDataset);
	}

	@Override
	public DataNode setIncident_wavelengthScalar(Double incident_wavelengthValue) {
		return setField(NX_INCIDENT_WAVELENGTH, incident_wavelengthValue);
	}

	@Override
	public IDataset getIncident_wavelength_spread() {
		return getDataset(NX_INCIDENT_WAVELENGTH_SPREAD);
	}

	@Override
	public Double getIncident_wavelength_spreadScalar() {
		return getDouble(NX_INCIDENT_WAVELENGTH_SPREAD);
	}

	@Override
	public DataNode setIncident_wavelength_spread(IDataset incident_wavelength_spreadDataset) {
		return setDataset(NX_INCIDENT_WAVELENGTH_SPREAD, incident_wavelength_spreadDataset);
	}

	@Override
	public DataNode setIncident_wavelength_spreadScalar(Double incident_wavelength_spreadValue) {
		return setField(NX_INCIDENT_WAVELENGTH_SPREAD, incident_wavelength_spreadValue);
	}

	@Override
	public IDataset getIncident_beam_divergence() {
		return getDataset(NX_INCIDENT_BEAM_DIVERGENCE);
	}

	@Override
	public Double getIncident_beam_divergenceScalar() {
		return getDouble(NX_INCIDENT_BEAM_DIVERGENCE);
	}

	@Override
	public DataNode setIncident_beam_divergence(IDataset incident_beam_divergenceDataset) {
		return setDataset(NX_INCIDENT_BEAM_DIVERGENCE, incident_beam_divergenceDataset);
	}

	@Override
	public DataNode setIncident_beam_divergenceScalar(Double incident_beam_divergenceValue) {
		return setField(NX_INCIDENT_BEAM_DIVERGENCE, incident_beam_divergenceValue);
	}

	@Override
	public IDataset getExtent() {
		return getDataset(NX_EXTENT);
	}

	@Override
	public Double getExtentScalar() {
		return getDouble(NX_EXTENT);
	}

	@Override
	public DataNode setExtent(IDataset extentDataset) {
		return setDataset(NX_EXTENT, extentDataset);
	}

	@Override
	public DataNode setExtentScalar(Double extentValue) {
		return setField(NX_EXTENT, extentValue);
	}

	@Override
	public IDataset getFinal_wavelength() {
		return getDataset(NX_FINAL_WAVELENGTH);
	}

	@Override
	public Double getFinal_wavelengthScalar() {
		return getDouble(NX_FINAL_WAVELENGTH);
	}

	@Override
	public DataNode setFinal_wavelength(IDataset final_wavelengthDataset) {
		return setDataset(NX_FINAL_WAVELENGTH, final_wavelengthDataset);
	}

	@Override
	public DataNode setFinal_wavelengthScalar(Double final_wavelengthValue) {
		return setField(NX_FINAL_WAVELENGTH, final_wavelengthValue);
	}

	@Override
	public IDataset getIncident_polarization() {
		return getDataset(NX_INCIDENT_POLARIZATION);
	}

	@Override
	public Double getIncident_polarizationScalar() {
		return getDouble(NX_INCIDENT_POLARIZATION);
	}

	@Override
	public DataNode setIncident_polarization(IDataset incident_polarizationDataset) {
		return setDataset(NX_INCIDENT_POLARIZATION, incident_polarizationDataset);
	}

	@Override
	public DataNode setIncident_polarizationScalar(Double incident_polarizationValue) {
		return setField(NX_INCIDENT_POLARIZATION, incident_polarizationValue);
	}

	@Override
	public IDataset getFinal_polarization() {
		return getDataset(NX_FINAL_POLARIZATION);
	}

	@Override
	public Double getFinal_polarizationScalar() {
		return getDouble(NX_FINAL_POLARIZATION);
	}

	@Override
	public DataNode setFinal_polarization(IDataset final_polarizationDataset) {
		return setDataset(NX_FINAL_POLARIZATION, final_polarizationDataset);
	}

	@Override
	public DataNode setFinal_polarizationScalar(Double final_polarizationValue) {
		return setField(NX_FINAL_POLARIZATION, final_polarizationValue);
	}

	@Override
	public IDataset getFinal_wavelength_spread() {
		return getDataset(NX_FINAL_WAVELENGTH_SPREAD);
	}

	@Override
	public Double getFinal_wavelength_spreadScalar() {
		return getDouble(NX_FINAL_WAVELENGTH_SPREAD);
	}

	@Override
	public DataNode setFinal_wavelength_spread(IDataset final_wavelength_spreadDataset) {
		return setDataset(NX_FINAL_WAVELENGTH_SPREAD, final_wavelength_spreadDataset);
	}

	@Override
	public DataNode setFinal_wavelength_spreadScalar(Double final_wavelength_spreadValue) {
		return setField(NX_FINAL_WAVELENGTH_SPREAD, final_wavelength_spreadValue);
	}

	@Override
	public IDataset getFinal_beam_divergence() {
		return getDataset(NX_FINAL_BEAM_DIVERGENCE);
	}

	@Override
	public Double getFinal_beam_divergenceScalar() {
		return getDouble(NX_FINAL_BEAM_DIVERGENCE);
	}

	@Override
	public DataNode setFinal_beam_divergence(IDataset final_beam_divergenceDataset) {
		return setDataset(NX_FINAL_BEAM_DIVERGENCE, final_beam_divergenceDataset);
	}

	@Override
	public DataNode setFinal_beam_divergenceScalar(Double final_beam_divergenceValue) {
		return setField(NX_FINAL_BEAM_DIVERGENCE, final_beam_divergenceValue);
	}

	@Override
	public IDataset getFlux() {
		return getDataset(NX_FLUX);
	}

	@Override
	public Double getFluxScalar() {
		return getDouble(NX_FLUX);
	}

	@Override
	public DataNode setFlux(IDataset fluxDataset) {
		return setDataset(NX_FLUX, fluxDataset);
	}

	@Override
	public DataNode setFluxScalar(Double fluxValue) {
		return setField(NX_FLUX, fluxValue);
	}

	@Override
	public NXdata getData() {
		// dataNodeName = NX_DATA
		return getChild("data", NXdata.class);
	}

	@Override
	public void setData(NXdata dataGroup) {
		putChild("data", dataGroup);
	}

	@Override
	public NXdata getData(String name) {
		return getChild(name, NXdata.class);
	}

	@Override
	public void setData(String name, NXdata data) {
		putChild(name, data);
	}

	@Override
	public Map<String, NXdata> getAllData() {
		return getChildren(NXdata.class);
	}
	
	@Override
	public void setAllData(Map<String, NXdata> data) {
		setChildren(data);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
