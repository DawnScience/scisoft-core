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
 * Class to describe spin filters in photoemission experiments.

 */
public class NXspindispersionImpl extends NXcomponentImpl implements NXspindispersion {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_HISTORY,
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_ELECTROMAGNETIC_LENS);

	public NXspindispersionImpl() {
		super();
	}

	public NXspindispersionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXspindispersion.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SPINDISPERSION;
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
	public Dataset getFigure_of_merit() {
		return getDataset(NX_FIGURE_OF_MERIT);
	}

	@Override
	public Double getFigure_of_meritScalar() {
		return getDouble(NX_FIGURE_OF_MERIT);
	}

	@Override
	public DataNode setFigure_of_merit(IDataset figure_of_meritDataset) {
		return setDataset(NX_FIGURE_OF_MERIT, figure_of_meritDataset);
	}

	@Override
	public DataNode setFigure_of_meritScalar(Double figure_of_meritValue) {
		return setField(NX_FIGURE_OF_MERIT, figure_of_meritValue);
	}

	@Override
	public Dataset getShermann_function() {
		return getDataset(NX_SHERMANN_FUNCTION);
	}

	@Override
	public Double getShermann_functionScalar() {
		return getDouble(NX_SHERMANN_FUNCTION);
	}

	@Override
	public DataNode setShermann_function(IDataset shermann_functionDataset) {
		return setDataset(NX_SHERMANN_FUNCTION, shermann_functionDataset);
	}

	@Override
	public DataNode setShermann_functionScalar(Double shermann_functionValue) {
		return setField(NX_SHERMANN_FUNCTION, shermann_functionValue);
	}

	@Override
	public Dataset getScattering_energy() {
		return getDataset(NX_SCATTERING_ENERGY);
	}

	@Override
	public Double getScattering_energyScalar() {
		return getDouble(NX_SCATTERING_ENERGY);
	}

	@Override
	public DataNode setScattering_energy(IDataset scattering_energyDataset) {
		return setDataset(NX_SCATTERING_ENERGY, scattering_energyDataset);
	}

	@Override
	public DataNode setScattering_energyScalar(Double scattering_energyValue) {
		return setField(NX_SCATTERING_ENERGY, scattering_energyValue);
	}

	@Override
	public Dataset getScattering_angle() {
		return getDataset(NX_SCATTERING_ANGLE);
	}

	@Override
	public Double getScattering_angleScalar() {
		return getDouble(NX_SCATTERING_ANGLE);
	}

	@Override
	public DataNode setScattering_angle(IDataset scattering_angleDataset) {
		return setDataset(NX_SCATTERING_ANGLE, scattering_angleDataset);
	}

	@Override
	public DataNode setScattering_angleScalar(Double scattering_angleValue) {
		return setField(NX_SCATTERING_ANGLE, scattering_angleValue);
	}

	@Override
	public Dataset getScattering_target() {
		return getDataset(NX_SCATTERING_TARGET);
	}

	@Override
	public String getScattering_targetScalar() {
		return getString(NX_SCATTERING_TARGET);
	}

	@Override
	public DataNode setScattering_target(IDataset scattering_targetDataset) {
		return setDataset(NX_SCATTERING_TARGET, scattering_targetDataset);
	}

	@Override
	public DataNode setScattering_targetScalar(String scattering_targetValue) {
		return setString(NX_SCATTERING_TARGET, scattering_targetValue);
	}

	@Override
	public NXhistory getScattering_target_history() {
		// dataNodeName = NX_SCATTERING_TARGET_HISTORY
		return getChild("scattering_target_history", NXhistory.class);
	}

	@Override
	public void setScattering_target_history(NXhistory scattering_target_historyGroup) {
		putChild("scattering_target_history", scattering_target_historyGroup);
	}
	// Unprocessed group: preparation

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
