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
 * Crystal structure phase models used for indexing Kikuchi pattern.
 * This base class contains key metadata relevant to every physical
 * kinematic or dynamic diffraction model to be used for simulating
 * Kikuchi diffraction pattern.
 * The actual indexing of Kikuchi pattern however maybe use different
 * algorithms which build on these simulation results but evaluate different
 * workflows of comparing simulated and measured Kikuchi pattern to make
 * suggestions which orientation is the most likely (if any) for each
 * scan point investigated.
 * Traditionally Hough transform based indexing has been the most frequently
 * used algorithm. More and more dictionary based alternatives are used.
 * Either way both algorithm need a crystal structure model.

 */
public class NXem_ebsd_crystal_structure_modelImpl extends NXobjectImpl implements NXem_ebsd_crystal_structure_model {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXem_ebsd_crystal_structure_modelImpl() {
		super();
	}

	public NXem_ebsd_crystal_structure_modelImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXem_ebsd_crystal_structure_model.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EM_EBSD_CRYSTAL_STRUCTURE_MODEL;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getCrystallographic_database_identifier() {
		return getDataset(NX_CRYSTALLOGRAPHIC_DATABASE_IDENTIFIER);
	}

	@Override
	public String getCrystallographic_database_identifierScalar() {
		return getString(NX_CRYSTALLOGRAPHIC_DATABASE_IDENTIFIER);
	}

	@Override
	public DataNode setCrystallographic_database_identifier(IDataset crystallographic_database_identifierDataset) {
		return setDataset(NX_CRYSTALLOGRAPHIC_DATABASE_IDENTIFIER, crystallographic_database_identifierDataset);
	}

	@Override
	public DataNode setCrystallographic_database_identifierScalar(String crystallographic_database_identifierValue) {
		return setString(NX_CRYSTALLOGRAPHIC_DATABASE_IDENTIFIER, crystallographic_database_identifierValue);
	}

	@Override
	public IDataset getCrystallographic_database() {
		return getDataset(NX_CRYSTALLOGRAPHIC_DATABASE);
	}

	@Override
	public String getCrystallographic_databaseScalar() {
		return getString(NX_CRYSTALLOGRAPHIC_DATABASE);
	}

	@Override
	public DataNode setCrystallographic_database(IDataset crystallographic_databaseDataset) {
		return setDataset(NX_CRYSTALLOGRAPHIC_DATABASE, crystallographic_databaseDataset);
	}

	@Override
	public DataNode setCrystallographic_databaseScalar(String crystallographic_databaseValue) {
		return setString(NX_CRYSTALLOGRAPHIC_DATABASE, crystallographic_databaseValue);
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
	public IDataset getIs_centrosymmetric() {
		return getDataset(NX_IS_CENTROSYMMETRIC);
	}

	@Override
	public Boolean getIs_centrosymmetricScalar() {
		return getBoolean(NX_IS_CENTROSYMMETRIC);
	}

	@Override
	public DataNode setIs_centrosymmetric(IDataset is_centrosymmetricDataset) {
		return setDataset(NX_IS_CENTROSYMMETRIC, is_centrosymmetricDataset);
	}

	@Override
	public DataNode setIs_centrosymmetricScalar(Boolean is_centrosymmetricValue) {
		return setField(NX_IS_CENTROSYMMETRIC, is_centrosymmetricValue);
	}

	@Override
	public IDataset getIs_chiral() {
		return getDataset(NX_IS_CHIRAL);
	}

	@Override
	public Boolean getIs_chiralScalar() {
		return getBoolean(NX_IS_CHIRAL);
	}

	@Override
	public DataNode setIs_chiral(IDataset is_chiralDataset) {
		return setDataset(NX_IS_CHIRAL, is_chiralDataset);
	}

	@Override
	public DataNode setIs_chiralScalar(Boolean is_chiralValue) {
		return setField(NX_IS_CHIRAL, is_chiralValue);
	}

	@Override
	public IDataset getLaue_group() {
		return getDataset(NX_LAUE_GROUP);
	}

	@Override
	public String getLaue_groupScalar() {
		return getString(NX_LAUE_GROUP);
	}

	@Override
	public DataNode setLaue_group(IDataset laue_groupDataset) {
		return setDataset(NX_LAUE_GROUP, laue_groupDataset);
	}

	@Override
	public DataNode setLaue_groupScalar(String laue_groupValue) {
		return setString(NX_LAUE_GROUP, laue_groupValue);
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
	public IDataset getPhase_identifier() {
		return getDataset(NX_PHASE_IDENTIFIER);
	}

	@Override
	public Long getPhase_identifierScalar() {
		return getLong(NX_PHASE_IDENTIFIER);
	}

	@Override
	public DataNode setPhase_identifier(IDataset phase_identifierDataset) {
		return setDataset(NX_PHASE_IDENTIFIER, phase_identifierDataset);
	}

	@Override
	public DataNode setPhase_identifierScalar(Long phase_identifierValue) {
		return setField(NX_PHASE_IDENTIFIER, phase_identifierValue);
	}

	@Override
	public IDataset getPhase_name() {
		return getDataset(NX_PHASE_NAME);
	}

	@Override
	public String getPhase_nameScalar() {
		return getString(NX_PHASE_NAME);
	}

	@Override
	public DataNode setPhase_name(IDataset phase_nameDataset) {
		return setDataset(NX_PHASE_NAME, phase_nameDataset);
	}

	@Override
	public DataNode setPhase_nameScalar(String phase_nameValue) {
		return setString(NX_PHASE_NAME, phase_nameValue);
	}

	@Override
	public IDataset getAtom_identifier() {
		return getDataset(NX_ATOM_IDENTIFIER);
	}

	@Override
	public String getAtom_identifierScalar() {
		return getString(NX_ATOM_IDENTIFIER);
	}

	@Override
	public DataNode setAtom_identifier(IDataset atom_identifierDataset) {
		return setDataset(NX_ATOM_IDENTIFIER, atom_identifierDataset);
	}

	@Override
	public DataNode setAtom_identifierScalar(String atom_identifierValue) {
		return setString(NX_ATOM_IDENTIFIER, atom_identifierValue);
	}

	@Override
	public IDataset getAtom() {
		return getDataset(NX_ATOM);
	}

	@Override
	public Long getAtomScalar() {
		return getLong(NX_ATOM);
	}

	@Override
	public DataNode setAtom(IDataset atomDataset) {
		return setDataset(NX_ATOM, atomDataset);
	}

	@Override
	public DataNode setAtomScalar(Long atomValue) {
		return setField(NX_ATOM, atomValue);
	}

	@Override
	public IDataset getAtom_positions() {
		return getDataset(NX_ATOM_POSITIONS);
	}

	@Override
	public Double getAtom_positionsScalar() {
		return getDouble(NX_ATOM_POSITIONS);
	}

	@Override
	public DataNode setAtom_positions(IDataset atom_positionsDataset) {
		return setDataset(NX_ATOM_POSITIONS, atom_positionsDataset);
	}

	@Override
	public DataNode setAtom_positionsScalar(Double atom_positionsValue) {
		return setField(NX_ATOM_POSITIONS, atom_positionsValue);
	}

	@Override
	public IDataset getAtom_occupancy() {
		return getDataset(NX_ATOM_OCCUPANCY);
	}

	@Override
	public Double getAtom_occupancyScalar() {
		return getDouble(NX_ATOM_OCCUPANCY);
	}

	@Override
	public DataNode setAtom_occupancy(IDataset atom_occupancyDataset) {
		return setDataset(NX_ATOM_OCCUPANCY, atom_occupancyDataset);
	}

	@Override
	public DataNode setAtom_occupancyScalar(Double atom_occupancyValue) {
		return setField(NX_ATOM_OCCUPANCY, atom_occupancyValue);
	}

	@Override
	public IDataset getNumber_of_planes() {
		return getDataset(NX_NUMBER_OF_PLANES);
	}

	@Override
	public Long getNumber_of_planesScalar() {
		return getLong(NX_NUMBER_OF_PLANES);
	}

	@Override
	public DataNode setNumber_of_planes(IDataset number_of_planesDataset) {
		return setDataset(NX_NUMBER_OF_PLANES, number_of_planesDataset);
	}

	@Override
	public DataNode setNumber_of_planesScalar(Long number_of_planesValue) {
		return setField(NX_NUMBER_OF_PLANES, number_of_planesValue);
	}

	@Override
	public IDataset getPlane_miller() {
		return getDataset(NX_PLANE_MILLER);
	}

	@Override
	public Number getPlane_millerScalar() {
		return getNumber(NX_PLANE_MILLER);
	}

	@Override
	public DataNode setPlane_miller(IDataset plane_millerDataset) {
		return setDataset(NX_PLANE_MILLER, plane_millerDataset);
	}

	@Override
	public DataNode setPlane_millerScalar(Number plane_millerValue) {
		return setField(NX_PLANE_MILLER, plane_millerValue);
	}

	@Override
	public IDataset getDspacing() {
		return getDataset(NX_DSPACING);
	}

	@Override
	public Double getDspacingScalar() {
		return getDouble(NX_DSPACING);
	}

	@Override
	public DataNode setDspacing(IDataset dspacingDataset) {
		return setDataset(NX_DSPACING, dspacingDataset);
	}

	@Override
	public DataNode setDspacingScalar(Double dspacingValue) {
		return setField(NX_DSPACING, dspacingValue);
	}

	@Override
	public IDataset getRelative_intensity() {
		return getDataset(NX_RELATIVE_INTENSITY);
	}

	@Override
	public Double getRelative_intensityScalar() {
		return getDouble(NX_RELATIVE_INTENSITY);
	}

	@Override
	public DataNode setRelative_intensity(IDataset relative_intensityDataset) {
		return setDataset(NX_RELATIVE_INTENSITY, relative_intensityDataset);
	}

	@Override
	public DataNode setRelative_intensityScalar(Double relative_intensityValue) {
		return setField(NX_RELATIVE_INTENSITY, relative_intensityValue);
	}

}
