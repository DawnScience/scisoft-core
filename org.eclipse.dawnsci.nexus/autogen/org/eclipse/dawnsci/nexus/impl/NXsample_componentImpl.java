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
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * One group like this per component can be recorded For a sample consisting of multiple components.
 * 
 */
public class NXsample_componentImpl extends NXobjectImpl implements NXsample_component {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA);

	public NXsample_componentImpl() {
		super();
	}

	public NXsample_componentImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXsample_component.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SAMPLE_COMPONENT;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getName() {
		return getDataset(NX_NAME);
	}

	@Override
	public String getNameScalar() {
		return getString(NX_NAME);
	}

	@Override
	public DataNode setName(IDataset nameDataset) {
		return setDataset(NX_NAME, nameDataset);
	}

	@Override
	public DataNode setNameScalar(String nameValue) {
		return setString(NX_NAME, nameValue);
	}

	@Override
	public IDataset getChemical_formula() {
		return getDataset(NX_CHEMICAL_FORMULA);
	}

	@Override
	public String getChemical_formulaScalar() {
		return getString(NX_CHEMICAL_FORMULA);
	}

	@Override
	public DataNode setChemical_formula(IDataset chemical_formulaDataset) {
		return setDataset(NX_CHEMICAL_FORMULA, chemical_formulaDataset);
	}

	@Override
	public DataNode setChemical_formulaScalar(String chemical_formulaValue) {
		return setString(NX_CHEMICAL_FORMULA, chemical_formulaValue);
	}

	@Override
	public IDataset getUnit_cell_abc() {
		return getDataset(NX_UNIT_CELL_ABC);
	}

	@Override
	public Double getUnit_cell_abcScalar() {
		return getDouble(NX_UNIT_CELL_ABC);
	}

	@Override
	public DataNode setUnit_cell_abc(IDataset unit_cell_abcDataset) {
		return setDataset(NX_UNIT_CELL_ABC, unit_cell_abcDataset);
	}

	@Override
	public DataNode setUnit_cell_abcScalar(Double unit_cell_abcValue) {
		return setField(NX_UNIT_CELL_ABC, unit_cell_abcValue);
	}

	@Override
	public IDataset getUnit_cell_alphabetagamma() {
		return getDataset(NX_UNIT_CELL_ALPHABETAGAMMA);
	}

	@Override
	public Double getUnit_cell_alphabetagammaScalar() {
		return getDouble(NX_UNIT_CELL_ALPHABETAGAMMA);
	}

	@Override
	public DataNode setUnit_cell_alphabetagamma(IDataset unit_cell_alphabetagammaDataset) {
		return setDataset(NX_UNIT_CELL_ALPHABETAGAMMA, unit_cell_alphabetagammaDataset);
	}

	@Override
	public DataNode setUnit_cell_alphabetagammaScalar(Double unit_cell_alphabetagammaValue) {
		return setField(NX_UNIT_CELL_ALPHABETAGAMMA, unit_cell_alphabetagammaValue);
	}

	@Override
	public IDataset getUnit_cell_volume() {
		return getDataset(NX_UNIT_CELL_VOLUME);
	}

	@Override
	public Double getUnit_cell_volumeScalar() {
		return getDouble(NX_UNIT_CELL_VOLUME);
	}

	@Override
	public DataNode setUnit_cell_volume(IDataset unit_cell_volumeDataset) {
		return setDataset(NX_UNIT_CELL_VOLUME, unit_cell_volumeDataset);
	}

	@Override
	public DataNode setUnit_cell_volumeScalar(Double unit_cell_volumeValue) {
		return setField(NX_UNIT_CELL_VOLUME, unit_cell_volumeValue);
	}

	@Override
	public IDataset getSample_orientation() {
		return getDataset(NX_SAMPLE_ORIENTATION);
	}

	@Override
	public Double getSample_orientationScalar() {
		return getDouble(NX_SAMPLE_ORIENTATION);
	}

	@Override
	public DataNode setSample_orientation(IDataset sample_orientationDataset) {
		return setDataset(NX_SAMPLE_ORIENTATION, sample_orientationDataset);
	}

	@Override
	public DataNode setSample_orientationScalar(Double sample_orientationValue) {
		return setField(NX_SAMPLE_ORIENTATION, sample_orientationValue);
	}

	@Override
	public IDataset getOrientation_matrix() {
		return getDataset(NX_ORIENTATION_MATRIX);
	}

	@Override
	public Double getOrientation_matrixScalar() {
		return getDouble(NX_ORIENTATION_MATRIX);
	}

	@Override
	public DataNode setOrientation_matrix(IDataset orientation_matrixDataset) {
		return setDataset(NX_ORIENTATION_MATRIX, orientation_matrixDataset);
	}

	@Override
	public DataNode setOrientation_matrixScalar(Double orientation_matrixValue) {
		return setField(NX_ORIENTATION_MATRIX, orientation_matrixValue);
	}

	@Override
	public IDataset getMass() {
		return getDataset(NX_MASS);
	}

	@Override
	public Double getMassScalar() {
		return getDouble(NX_MASS);
	}

	@Override
	public DataNode setMass(IDataset massDataset) {
		return setDataset(NX_MASS, massDataset);
	}

	@Override
	public DataNode setMassScalar(Double massValue) {
		return setField(NX_MASS, massValue);
	}

	@Override
	public IDataset getDensity() {
		return getDataset(NX_DENSITY);
	}

	@Override
	public Double getDensityScalar() {
		return getDouble(NX_DENSITY);
	}

	@Override
	public DataNode setDensity(IDataset densityDataset) {
		return setDataset(NX_DENSITY, densityDataset);
	}

	@Override
	public DataNode setDensityScalar(Double densityValue) {
		return setField(NX_DENSITY, densityValue);
	}

	@Override
	public IDataset getRelative_molecular_mass() {
		return getDataset(NX_RELATIVE_MOLECULAR_MASS);
	}

	@Override
	public Double getRelative_molecular_massScalar() {
		return getDouble(NX_RELATIVE_MOLECULAR_MASS);
	}

	@Override
	public DataNode setRelative_molecular_mass(IDataset relative_molecular_massDataset) {
		return setDataset(NX_RELATIVE_MOLECULAR_MASS, relative_molecular_massDataset);
	}

	@Override
	public DataNode setRelative_molecular_massScalar(Double relative_molecular_massValue) {
		return setField(NX_RELATIVE_MOLECULAR_MASS, relative_molecular_massValue);
	}

	@Override
	public IDataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getVolume_fraction() {
		return getDataset(NX_VOLUME_FRACTION);
	}

	@Override
	public Double getVolume_fractionScalar() {
		return getDouble(NX_VOLUME_FRACTION);
	}

	@Override
	public DataNode setVolume_fraction(IDataset volume_fractionDataset) {
		return setDataset(NX_VOLUME_FRACTION, volume_fractionDataset);
	}

	@Override
	public DataNode setVolume_fractionScalar(Double volume_fractionValue) {
		return setField(NX_VOLUME_FRACTION, volume_fractionValue);
	}

	@Override
	public IDataset getScattering_length_density() {
		return getDataset(NX_SCATTERING_LENGTH_DENSITY);
	}

	@Override
	public Double getScattering_length_densityScalar() {
		return getDouble(NX_SCATTERING_LENGTH_DENSITY);
	}

	@Override
	public DataNode setScattering_length_density(IDataset scattering_length_densityDataset) {
		return setDataset(NX_SCATTERING_LENGTH_DENSITY, scattering_length_densityDataset);
	}

	@Override
	public DataNode setScattering_length_densityScalar(Double scattering_length_densityValue) {
		return setField(NX_SCATTERING_LENGTH_DENSITY, scattering_length_densityValue);
	}

	@Override
	public IDataset getUnit_cell_class() {
		return getDataset(NX_UNIT_CELL_CLASS);
	}

	@Override
	public String getUnit_cell_classScalar() {
		return getString(NX_UNIT_CELL_CLASS);
	}

	@Override
	public DataNode setUnit_cell_class(IDataset unit_cell_classDataset) {
		return setDataset(NX_UNIT_CELL_CLASS, unit_cell_classDataset);
	}

	@Override
	public DataNode setUnit_cell_classScalar(String unit_cell_classValue) {
		return setString(NX_UNIT_CELL_CLASS, unit_cell_classValue);
	}

	@Override
	public IDataset getSpace_group() {
		return getDataset(NX_SPACE_GROUP);
	}

	@Override
	public String getSpace_groupScalar() {
		return getString(NX_SPACE_GROUP);
	}

	@Override
	public DataNode setSpace_group(IDataset space_groupDataset) {
		return setDataset(NX_SPACE_GROUP, space_groupDataset);
	}

	@Override
	public DataNode setSpace_groupScalar(String space_groupValue) {
		return setString(NX_SPACE_GROUP, space_groupValue);
	}

	@Override
	public IDataset getPoint_group() {
		return getDataset(NX_POINT_GROUP);
	}

	@Override
	public String getPoint_groupScalar() {
		return getString(NX_POINT_GROUP);
	}

	@Override
	public DataNode setPoint_group(IDataset point_groupDataset) {
		return setDataset(NX_POINT_GROUP, point_groupDataset);
	}

	@Override
	public DataNode setPoint_groupScalar(String point_groupValue) {
		return setString(NX_POINT_GROUP, point_groupValue);
	}

	@Override
	public NXdata getTransmission() {
		// dataNodeName = NX_TRANSMISSION
		return getChild("transmission", NXdata.class);
	}

	@Override
	public void setTransmission(NXdata transmissionGroup) {
		putChild("transmission", transmissionGroup);
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
