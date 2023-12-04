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

import java.util.Date;
import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Subclass of NXelectronanalyser to describe the spin filters in photoemission
 * experiments.
 * 
 */
public class NXspindispersionImpl extends NXobjectImpl implements NXspindispersion {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_LENS_EM);

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
	public IDataset getType() {
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
	public IDataset getFigure_of_merit() {
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
	public IDataset getShermann_function() {
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
	public IDataset getScattering_energy() {
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
	public IDataset getScattering_angle() {
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
	public IDataset getTarget() {
		return getDataset(NX_TARGET);
	}

	@Override
	public String getTargetScalar() {
		return getString(NX_TARGET);
	}

	@Override
	public DataNode setTarget(IDataset targetDataset) {
		return setDataset(NX_TARGET, targetDataset);
	}

	@Override
	public DataNode setTargetScalar(String targetValue) {
		return setString(NX_TARGET, targetValue);
	}

	@Override
	public IDataset getTarget_preparation() {
		return getDataset(NX_TARGET_PREPARATION);
	}

	@Override
	public String getTarget_preparationScalar() {
		return getString(NX_TARGET_PREPARATION);
	}

	@Override
	public DataNode setTarget_preparation(IDataset target_preparationDataset) {
		return setDataset(NX_TARGET_PREPARATION, target_preparationDataset);
	}

	@Override
	public DataNode setTarget_preparationScalar(String target_preparationValue) {
		return setString(NX_TARGET_PREPARATION, target_preparationValue);
	}

	@Override
	public IDataset getTarget_preparation_date() {
		return getDataset(NX_TARGET_PREPARATION_DATE);
	}

	@Override
	public Date getTarget_preparation_dateScalar() {
		return getDate(NX_TARGET_PREPARATION_DATE);
	}

	@Override
	public DataNode setTarget_preparation_date(IDataset target_preparation_dateDataset) {
		return setDataset(NX_TARGET_PREPARATION_DATE, target_preparation_dateDataset);
	}

	@Override
	public DataNode setTarget_preparation_dateScalar(Date target_preparation_dateValue) {
		return setDate(NX_TARGET_PREPARATION_DATE, target_preparation_dateValue);
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
