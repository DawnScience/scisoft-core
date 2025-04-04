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
 * An X-ray lens, typically at a synchrotron X-ray beam line.
 * Based on information provided by Gerd Wellenreuther (DESY).

 */
public class NXxraylensImpl extends NXobjectImpl implements NXxraylens {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_OFF_GEOMETRY,
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXxraylensImpl() {
		super();
	}

	public NXxraylensImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXxraylens.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_XRAYLENS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getLens_geometry() {
		return getDataset(NX_LENS_GEOMETRY);
	}

	@Override
	public String getLens_geometryScalar() {
		return getString(NX_LENS_GEOMETRY);
	}

	@Override
	public DataNode setLens_geometry(IDataset lens_geometryDataset) {
		return setDataset(NX_LENS_GEOMETRY, lens_geometryDataset);
	}

	@Override
	public DataNode setLens_geometryScalar(String lens_geometryValue) {
		return setString(NX_LENS_GEOMETRY, lens_geometryValue);
	}

	@Override
	public Dataset getSymmetric() {
		return getDataset(NX_SYMMETRIC);
	}

	@Override
	public Boolean getSymmetricScalar() {
		return getBoolean(NX_SYMMETRIC);
	}

	@Override
	public DataNode setSymmetric(IDataset symmetricDataset) {
		return setDataset(NX_SYMMETRIC, symmetricDataset);
	}

	@Override
	public DataNode setSymmetricScalar(Boolean symmetricValue) {
		return setField(NX_SYMMETRIC, symmetricValue);
	}

	@Override
	public Dataset getCylindrical() {
		return getDataset(NX_CYLINDRICAL);
	}

	@Override
	public Boolean getCylindricalScalar() {
		return getBoolean(NX_CYLINDRICAL);
	}

	@Override
	public DataNode setCylindrical(IDataset cylindricalDataset) {
		return setDataset(NX_CYLINDRICAL, cylindricalDataset);
	}

	@Override
	public DataNode setCylindricalScalar(Boolean cylindricalValue) {
		return setField(NX_CYLINDRICAL, cylindricalValue);
	}

	@Override
	public NXnote getCylinder_orientation() {
		// dataNodeName = NX_CYLINDER_ORIENTATION
		return getChild("cylinder_orientation", NXnote.class);
	}

	@Override
	public void setCylinder_orientation(NXnote cylinder_orientationGroup) {
		putChild("cylinder_orientation", cylinder_orientationGroup);
	}

	@Override
	public Dataset getFocus_type() {
		return getDataset(NX_FOCUS_TYPE);
	}

	@Override
	public String getFocus_typeScalar() {
		return getString(NX_FOCUS_TYPE);
	}

	@Override
	public DataNode setFocus_type(IDataset focus_typeDataset) {
		return setDataset(NX_FOCUS_TYPE, focus_typeDataset);
	}

	@Override
	public DataNode setFocus_typeScalar(String focus_typeValue) {
		return setString(NX_FOCUS_TYPE, focus_typeValue);
	}

	@Override
	public Dataset getLens_thickness() {
		return getDataset(NX_LENS_THICKNESS);
	}

	@Override
	public Double getLens_thicknessScalar() {
		return getDouble(NX_LENS_THICKNESS);
	}

	@Override
	public DataNode setLens_thickness(IDataset lens_thicknessDataset) {
		return setDataset(NX_LENS_THICKNESS, lens_thicknessDataset);
	}

	@Override
	public DataNode setLens_thicknessScalar(Double lens_thicknessValue) {
		return setField(NX_LENS_THICKNESS, lens_thicknessValue);
	}

	@Override
	public Dataset getLens_length() {
		return getDataset(NX_LENS_LENGTH);
	}

	@Override
	public Double getLens_lengthScalar() {
		return getDouble(NX_LENS_LENGTH);
	}

	@Override
	public DataNode setLens_length(IDataset lens_lengthDataset) {
		return setDataset(NX_LENS_LENGTH, lens_lengthDataset);
	}

	@Override
	public DataNode setLens_lengthScalar(Double lens_lengthValue) {
		return setField(NX_LENS_LENGTH, lens_lengthValue);
	}

	@Override
	public Dataset getCurvature() {
		return getDataset(NX_CURVATURE);
	}

	@Override
	public Double getCurvatureScalar() {
		return getDouble(NX_CURVATURE);
	}

	@Override
	public DataNode setCurvature(IDataset curvatureDataset) {
		return setDataset(NX_CURVATURE, curvatureDataset);
	}

	@Override
	public DataNode setCurvatureScalar(Double curvatureValue) {
		return setField(NX_CURVATURE, curvatureValue);
	}

	@Override
	public Dataset getAperture() {
		return getDataset(NX_APERTURE);
	}

	@Override
	public Double getApertureScalar() {
		return getDouble(NX_APERTURE);
	}

	@Override
	public DataNode setAperture(IDataset apertureDataset) {
		return setDataset(NX_APERTURE, apertureDataset);
	}

	@Override
	public DataNode setApertureScalar(Double apertureValue) {
		return setField(NX_APERTURE, apertureValue);
	}

	@Override
	public Dataset getNumber_of_lenses() {
		return getDataset(NX_NUMBER_OF_LENSES);
	}

	@Override
	public Long getNumber_of_lensesScalar() {
		return getLong(NX_NUMBER_OF_LENSES);
	}

	@Override
	public DataNode setNumber_of_lenses(IDataset number_of_lensesDataset) {
		return setDataset(NX_NUMBER_OF_LENSES, number_of_lensesDataset);
	}

	@Override
	public DataNode setNumber_of_lensesScalar(Long number_of_lensesValue) {
		return setField(NX_NUMBER_OF_LENSES, number_of_lensesValue);
	}

	@Override
	public Dataset getLens_material() {
		return getDataset(NX_LENS_MATERIAL);
	}

	@Override
	public String getLens_materialScalar() {
		return getString(NX_LENS_MATERIAL);
	}

	@Override
	public DataNode setLens_material(IDataset lens_materialDataset) {
		return setDataset(NX_LENS_MATERIAL, lens_materialDataset);
	}

	@Override
	public DataNode setLens_materialScalar(String lens_materialValue) {
		return setString(NX_LENS_MATERIAL, lens_materialValue);
	}

	@Override
	public Dataset getGas() {
		return getDataset(NX_GAS);
	}

	@Override
	public String getGasScalar() {
		return getString(NX_GAS);
	}

	@Override
	public DataNode setGas(IDataset gasDataset) {
		return setDataset(NX_GAS, gasDataset);
	}

	@Override
	public DataNode setGasScalar(String gasValue) {
		return setString(NX_GAS, gasValue);
	}

	@Override
	public Dataset getGas_pressure() {
		return getDataset(NX_GAS_PRESSURE);
	}

	@Override
	public Double getGas_pressureScalar() {
		return getDouble(NX_GAS_PRESSURE);
	}

	@Override
	public DataNode setGas_pressure(IDataset gas_pressureDataset) {
		return setDataset(NX_GAS_PRESSURE, gas_pressureDataset);
	}

	@Override
	public DataNode setGas_pressureScalar(Double gas_pressureValue) {
		return setField(NX_GAS_PRESSURE, gas_pressureValue);
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
