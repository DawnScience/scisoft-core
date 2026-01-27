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
 * Contains data structures of an experimental optical setup (i.e., multiple
 * transfer matrix tables). These data structures are used to relate physical
 * properties of two beams (NXbeam) which have one common optical component
 * (NXcomponent) (one specific transfer matrix).
 * One of these beams is an input beam and the other one is an output beam.
 * The data describes the change of beam properties, e.g. the intensity of a
 * beam is reduced because the transmission coefficient of the beam device is
 * lower than 1.

 */
public class NXbeam_transfer_matrix_tableImpl extends NXobjectImpl implements NXbeam_transfer_matrix_table {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXbeam_transfer_matrix_tableImpl() {
		super();
	}

	public NXbeam_transfer_matrix_tableImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXbeam_transfer_matrix_table.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_BEAM_TRANSFER_MATRIX_TABLE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDatatype_n() {
		return getDataset(NX_DATATYPE_N);
	}

	@Override
	public String getDatatype_nScalar() {
		return getString(NX_DATATYPE_N);
	}

	@Override
	public DataNode setDatatype_n(IDataset datatype_nDataset) {
		return setDataset(NX_DATATYPE_N, datatype_nDataset);
	}

	@Override
	public DataNode setDatatype_nScalar(String datatype_nValue) {
		return setString(NX_DATATYPE_N, datatype_nValue);
	}

	@Override
	public Dataset getMatrix_elements() {
		return getDataset(NX_MATRIX_ELEMENTS);
	}

	@Override
	public String getMatrix_elementsScalar() {
		return getString(NX_MATRIX_ELEMENTS);
	}

	@Override
	public DataNode setMatrix_elements(IDataset matrix_elementsDataset) {
		return setDataset(NX_MATRIX_ELEMENTS, matrix_elementsDataset);
	}

	@Override
	public DataNode setMatrix_elementsScalar(String matrix_elementsValue) {
		return setString(NX_MATRIX_ELEMENTS, matrix_elementsValue);
	}

	@Override
	public Dataset getMatrix(String transfer) {
		return getDataset(transfer + NX_MATRIX_SUFFIX);
	}

	@Override
	public Number getMatrixScalar(String transfer) {
		return getNumber(transfer + NX_MATRIX_SUFFIX);
	}

	@Override
	public DataNode setMatrix(String transfer, IDataset matrixDataset) {
		return setDataset(transfer + NX_MATRIX_SUFFIX, matrixDataset);
	}

	@Override
	public DataNode setMatrixScalar(String transfer, Number matrixValue) {
		return setField(transfer + NX_MATRIX_SUFFIX, matrixValue);
	}

	@Override
	public Map<String, Dataset> getAllMatrix() {
		return getAllDatasets(); // note: returns all datasets in the group!
	}

	@Override
	public String getMatrixAttributeInput(String transfer) {
		return getAttrString(transfer + NX_MATRIX_SUFFIX, NX_MATRIX_SUFFIX_ATTRIBUTE_INPUT);
	}

	@Override
	public void setMatrixAttributeInput(String transfer, String inputValue) {
		setAttribute(transfer + NX_MATRIX_SUFFIX, NX_MATRIX_SUFFIX_ATTRIBUTE_INPUT, inputValue);
	}

	@Override
	public String getMatrixAttributeOutput(String transfer) {
		return getAttrString(transfer + NX_MATRIX_SUFFIX, NX_MATRIX_SUFFIX_ATTRIBUTE_OUTPUT);
	}

	@Override
	public void setMatrixAttributeOutput(String transfer, String outputValue) {
		setAttribute(transfer + NX_MATRIX_SUFFIX, NX_MATRIX_SUFFIX_ATTRIBUTE_OUTPUT, outputValue);
	}

}
