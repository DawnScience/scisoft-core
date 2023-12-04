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
 * Models for aberrations of electro-magnetic lenses in electron microscopy.
 * The notation follows `O. Krivanek et al. (1999) <https://doi.org/10.1016/S0304-3991(99)00013-3>`_
 * and `O. Krivanek et al. (2003) <https://doi.org/10.1016/S0304-3991(03)00090-1>`_
 * See also `S. J. Pennycock and P. D. Nellist <https://doi.org/10.1007/978-1-4419-7200-2>`_ (page 44ff, and page 118ff)
 * for further details, additional literature, and the unit of the coefficients.
 * Consult Table 7-2 of Ibid. publication on how to convert between
 * conventions of different groups/vendors.
 * 
 */
public class NXaberrationImpl extends NXobjectImpl implements NXaberration {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXaberrationImpl() {
		super();
	}

	public NXaberrationImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXaberration.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ABERRATION;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getC_1_0() {
		return getDataset(NX_C_1_0);
	}

	@Override
	public Double getC_1_0Scalar() {
		return getDouble(NX_C_1_0);
	}

	@Override
	public DataNode setC_1_0(IDataset c_1_0Dataset) {
		return setDataset(NX_C_1_0, c_1_0Dataset);
	}

	@Override
	public DataNode setC_1_0Scalar(Double c_1_0Value) {
		return setField(NX_C_1_0, c_1_0Value);
	}

	@Override
	public IDataset getC_1_2_a() {
		return getDataset(NX_C_1_2_A);
	}

	@Override
	public Double getC_1_2_aScalar() {
		return getDouble(NX_C_1_2_A);
	}

	@Override
	public DataNode setC_1_2_a(IDataset c_1_2_aDataset) {
		return setDataset(NX_C_1_2_A, c_1_2_aDataset);
	}

	@Override
	public DataNode setC_1_2_aScalar(Double c_1_2_aValue) {
		return setField(NX_C_1_2_A, c_1_2_aValue);
	}

	@Override
	public IDataset getC_1_2_b() {
		return getDataset(NX_C_1_2_B);
	}

	@Override
	public Double getC_1_2_bScalar() {
		return getDouble(NX_C_1_2_B);
	}

	@Override
	public DataNode setC_1_2_b(IDataset c_1_2_bDataset) {
		return setDataset(NX_C_1_2_B, c_1_2_bDataset);
	}

	@Override
	public DataNode setC_1_2_bScalar(Double c_1_2_bValue) {
		return setField(NX_C_1_2_B, c_1_2_bValue);
	}

	@Override
	public IDataset getC_2_1_a() {
		return getDataset(NX_C_2_1_A);
	}

	@Override
	public Double getC_2_1_aScalar() {
		return getDouble(NX_C_2_1_A);
	}

	@Override
	public DataNode setC_2_1_a(IDataset c_2_1_aDataset) {
		return setDataset(NX_C_2_1_A, c_2_1_aDataset);
	}

	@Override
	public DataNode setC_2_1_aScalar(Double c_2_1_aValue) {
		return setField(NX_C_2_1_A, c_2_1_aValue);
	}

	@Override
	public IDataset getC_2_1_b() {
		return getDataset(NX_C_2_1_B);
	}

	@Override
	public Double getC_2_1_bScalar() {
		return getDouble(NX_C_2_1_B);
	}

	@Override
	public DataNode setC_2_1_b(IDataset c_2_1_bDataset) {
		return setDataset(NX_C_2_1_B, c_2_1_bDataset);
	}

	@Override
	public DataNode setC_2_1_bScalar(Double c_2_1_bValue) {
		return setField(NX_C_2_1_B, c_2_1_bValue);
	}

	@Override
	public IDataset getC_2_3_a() {
		return getDataset(NX_C_2_3_A);
	}

	@Override
	public Double getC_2_3_aScalar() {
		return getDouble(NX_C_2_3_A);
	}

	@Override
	public DataNode setC_2_3_a(IDataset c_2_3_aDataset) {
		return setDataset(NX_C_2_3_A, c_2_3_aDataset);
	}

	@Override
	public DataNode setC_2_3_aScalar(Double c_2_3_aValue) {
		return setField(NX_C_2_3_A, c_2_3_aValue);
	}

	@Override
	public IDataset getC_2_3_b() {
		return getDataset(NX_C_2_3_B);
	}

	@Override
	public Double getC_2_3_bScalar() {
		return getDouble(NX_C_2_3_B);
	}

	@Override
	public DataNode setC_2_3_b(IDataset c_2_3_bDataset) {
		return setDataset(NX_C_2_3_B, c_2_3_bDataset);
	}

	@Override
	public DataNode setC_2_3_bScalar(Double c_2_3_bValue) {
		return setField(NX_C_2_3_B, c_2_3_bValue);
	}

	@Override
	public IDataset getC_3_0() {
		return getDataset(NX_C_3_0);
	}

	@Override
	public Double getC_3_0Scalar() {
		return getDouble(NX_C_3_0);
	}

	@Override
	public DataNode setC_3_0(IDataset c_3_0Dataset) {
		return setDataset(NX_C_3_0, c_3_0Dataset);
	}

	@Override
	public DataNode setC_3_0Scalar(Double c_3_0Value) {
		return setField(NX_C_3_0, c_3_0Value);
	}

	@Override
	public IDataset getC_3_2_a() {
		return getDataset(NX_C_3_2_A);
	}

	@Override
	public Double getC_3_2_aScalar() {
		return getDouble(NX_C_3_2_A);
	}

	@Override
	public DataNode setC_3_2_a(IDataset c_3_2_aDataset) {
		return setDataset(NX_C_3_2_A, c_3_2_aDataset);
	}

	@Override
	public DataNode setC_3_2_aScalar(Double c_3_2_aValue) {
		return setField(NX_C_3_2_A, c_3_2_aValue);
	}

	@Override
	public IDataset getC_3_2_b() {
		return getDataset(NX_C_3_2_B);
	}

	@Override
	public Double getC_3_2_bScalar() {
		return getDouble(NX_C_3_2_B);
	}

	@Override
	public DataNode setC_3_2_b(IDataset c_3_2_bDataset) {
		return setDataset(NX_C_3_2_B, c_3_2_bDataset);
	}

	@Override
	public DataNode setC_3_2_bScalar(Double c_3_2_bValue) {
		return setField(NX_C_3_2_B, c_3_2_bValue);
	}

	@Override
	public IDataset getC_3_4_a() {
		return getDataset(NX_C_3_4_A);
	}

	@Override
	public Double getC_3_4_aScalar() {
		return getDouble(NX_C_3_4_A);
	}

	@Override
	public DataNode setC_3_4_a(IDataset c_3_4_aDataset) {
		return setDataset(NX_C_3_4_A, c_3_4_aDataset);
	}

	@Override
	public DataNode setC_3_4_aScalar(Double c_3_4_aValue) {
		return setField(NX_C_3_4_A, c_3_4_aValue);
	}

	@Override
	public IDataset getC_3_4_b() {
		return getDataset(NX_C_3_4_B);
	}

	@Override
	public Double getC_3_4_bScalar() {
		return getDouble(NX_C_3_4_B);
	}

	@Override
	public DataNode setC_3_4_b(IDataset c_3_4_bDataset) {
		return setDataset(NX_C_3_4_B, c_3_4_bDataset);
	}

	@Override
	public DataNode setC_3_4_bScalar(Double c_3_4_bValue) {
		return setField(NX_C_3_4_B, c_3_4_bValue);
	}

	@Override
	public IDataset getC_5_0() {
		return getDataset(NX_C_5_0);
	}

	@Override
	public Double getC_5_0Scalar() {
		return getDouble(NX_C_5_0);
	}

	@Override
	public DataNode setC_5_0(IDataset c_5_0Dataset) {
		return setDataset(NX_C_5_0, c_5_0Dataset);
	}

	@Override
	public DataNode setC_5_0Scalar(Double c_5_0Value) {
		return setField(NX_C_5_0, c_5_0Value);
	}

}
