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
 * Base class to describe structural aspects of an arrangement of
 * atoms or ions including a crystallographic unit cell.
 * Following recommendations of `CIF <https://www.iucr.org/resources/cif/spec/version1.1>`_ and `International Tables of Crystallography <https://it.iucr.org/>`_.

 */
public class NXunit_cellImpl extends NXobjectImpl implements NXunit_cell {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_ATOM);

	public NXunit_cellImpl() {
		super();
	}

	public NXunit_cellImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXunit_cell.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_UNIT_CELL;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getReference_frame() {
		return getDataset(NX_REFERENCE_FRAME);
	}

	@Override
	public String getReference_frameScalar() {
		return getString(NX_REFERENCE_FRAME);
	}

	@Override
	public DataNode setReference_frame(IDataset reference_frameDataset) {
		return setDataset(NX_REFERENCE_FRAME, reference_frameDataset);
	}

	@Override
	public DataNode setReference_frameScalar(String reference_frameValue) {
		return setString(NX_REFERENCE_FRAME, reference_frameValue);
	}

	@Override
	public Dataset getDimensionality() {
		return getDataset(NX_DIMENSIONALITY);
	}

	@Override
	public Long getDimensionalityScalar() {
		return getLong(NX_DIMENSIONALITY);
	}

	@Override
	public DataNode setDimensionality(IDataset dimensionalityDataset) {
		return setDataset(NX_DIMENSIONALITY, dimensionalityDataset);
	}

	@Override
	public DataNode setDimensionalityScalar(Long dimensionalityValue) {
		return setField(NX_DIMENSIONALITY, dimensionalityValue);
	}

	@Override
	public Dataset getA_b_c() {
		return getDataset(NX_A_B_C);
	}

	@Override
	public Number getA_b_cScalar() {
		return getNumber(NX_A_B_C);
	}

	@Override
	public DataNode setA_b_c(IDataset a_b_cDataset) {
		return setDataset(NX_A_B_C, a_b_cDataset);
	}

	@Override
	public DataNode setA_b_cScalar(Number a_b_cValue) {
		return setField(NX_A_B_C, a_b_cValue);
	}

	@Override
	public Dataset getA() {
		return getDataset(NX_A);
	}

	@Override
	public Number getAScalar() {
		return getNumber(NX_A);
	}

	@Override
	public DataNode setA(IDataset aDataset) {
		return setDataset(NX_A, aDataset);
	}

	@Override
	public DataNode setAScalar(Number aValue) {
		return setField(NX_A, aValue);
	}

	@Override
	public Dataset getB() {
		return getDataset(NX_B);
	}

	@Override
	public Number getBScalar() {
		return getNumber(NX_B);
	}

	@Override
	public DataNode setB(IDataset bDataset) {
		return setDataset(NX_B, bDataset);
	}

	@Override
	public DataNode setBScalar(Number bValue) {
		return setField(NX_B, bValue);
	}

	@Override
	public Dataset getC() {
		return getDataset(NX_C);
	}

	@Override
	public Number getCScalar() {
		return getNumber(NX_C);
	}

	@Override
	public DataNode setC(IDataset cDataset) {
		return setDataset(NX_C, cDataset);
	}

	@Override
	public DataNode setCScalar(Number cValue) {
		return setField(NX_C, cValue);
	}

	@Override
	public Dataset getAlpha_beta_gamma() {
		return getDataset(NX_ALPHA_BETA_GAMMA);
	}

	@Override
	public Number getAlpha_beta_gammaScalar() {
		return getNumber(NX_ALPHA_BETA_GAMMA);
	}

	@Override
	public DataNode setAlpha_beta_gamma(IDataset alpha_beta_gammaDataset) {
		return setDataset(NX_ALPHA_BETA_GAMMA, alpha_beta_gammaDataset);
	}

	@Override
	public DataNode setAlpha_beta_gammaScalar(Number alpha_beta_gammaValue) {
		return setField(NX_ALPHA_BETA_GAMMA, alpha_beta_gammaValue);
	}

	@Override
	public Dataset getAlpha() {
		return getDataset(NX_ALPHA);
	}

	@Override
	public Number getAlphaScalar() {
		return getNumber(NX_ALPHA);
	}

	@Override
	public DataNode setAlpha(IDataset alphaDataset) {
		return setDataset(NX_ALPHA, alphaDataset);
	}

	@Override
	public DataNode setAlphaScalar(Number alphaValue) {
		return setField(NX_ALPHA, alphaValue);
	}

	@Override
	public Dataset getBeta() {
		return getDataset(NX_BETA);
	}

	@Override
	public Number getBetaScalar() {
		return getNumber(NX_BETA);
	}

	@Override
	public DataNode setBeta(IDataset betaDataset) {
		return setDataset(NX_BETA, betaDataset);
	}

	@Override
	public DataNode setBetaScalar(Number betaValue) {
		return setField(NX_BETA, betaValue);
	}

	@Override
	public Dataset getGamma() {
		return getDataset(NX_GAMMA);
	}

	@Override
	public Number getGammaScalar() {
		return getNumber(NX_GAMMA);
	}

	@Override
	public DataNode setGamma(IDataset gammaDataset) {
		return setDataset(NX_GAMMA, gammaDataset);
	}

	@Override
	public DataNode setGammaScalar(Number gammaValue) {
		return setField(NX_GAMMA, gammaValue);
	}

	@Override
	public Dataset getCrystal_system() {
		return getDataset(NX_CRYSTAL_SYSTEM);
	}

	@Override
	public String getCrystal_systemScalar() {
		return getString(NX_CRYSTAL_SYSTEM);
	}

	@Override
	public DataNode setCrystal_system(IDataset crystal_systemDataset) {
		return setDataset(NX_CRYSTAL_SYSTEM, crystal_systemDataset);
	}

	@Override
	public DataNode setCrystal_systemScalar(String crystal_systemValue) {
		return setString(NX_CRYSTAL_SYSTEM, crystal_systemValue);
	}

	@Override
	public Dataset getLaue_group() {
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
	public Dataset getPoint_group() {
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
	public Dataset getSpace_group() {
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
	public Dataset getIs_centrosymmetric() {
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
	public Dataset getIs_chiral() {
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
	public Dataset getArea() {
		return getDataset(NX_AREA);
	}

	@Override
	public Number getAreaScalar() {
		return getNumber(NX_AREA);
	}

	@Override
	public DataNode setArea(IDataset areaDataset) {
		return setDataset(NX_AREA, areaDataset);
	}

	@Override
	public DataNode setAreaScalar(Number areaValue) {
		return setField(NX_AREA, areaValue);
	}

	@Override
	public Dataset getVolume() {
		return getDataset(NX_VOLUME);
	}

	@Override
	public Number getVolumeScalar() {
		return getNumber(NX_VOLUME);
	}

	@Override
	public DataNode setVolume(IDataset volumeDataset) {
		return setDataset(NX_VOLUME, volumeDataset);
	}

	@Override
	public DataNode setVolumeScalar(Number volumeValue) {
		return setField(NX_VOLUME, volumeValue);
	}

	@Override
	public NXatom getAtom() {
		// dataNodeName = NX_ATOM
		return getChild("atom", NXatom.class);
	}

	@Override
	public void setAtom(NXatom atomGroup) {
		putChild("atom", atomGroup);
	}

	@Override
	public NXatom getAtom(String name) {
		return getChild(name, NXatom.class);
	}

	@Override
	public void setAtom(String name, NXatom atom) {
		putChild(name, atom);
	}

	@Override
	public Map<String, NXatom> getAllAtom() {
		return getChildren(NXatom.class);
	}

	@Override
	public void setAllAtom(Map<String, NXatom> atom) {
		setChildren(atom);
	}

}
