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
 * Geometry and logical description of a region of data in a parent group. When used, it could be a child group to, say, :ref:`NXdetector`.
 * This can be used to describe a subset of data used to create downsampled data or to derive
 * some data from that subset.
 * Note, the fields for the rectangular region specifiers follow HDF5’s dataspace hyperslab parameters
 * (see https://portal.hdfgroup.org/display/HDF5/H5S_SELECT_HYPERSLAB). Note when **block** :math:`= 1`,
 * then **stride** :math:`\equiv` **step** in Python slicing.
 * For example, a ROI sum of an area starting at index of [20,50] and shape [220,120] in image data::
 * detector:NXdetector/
 * data[60,256,512]
 * region:NXregion/
 * @region_type = "rectangular"
 * parent = "data"
 * start = [20,50]
 * count = [220,120]
 * statistics:NXdata/
 * @signal = "sum"
 * sum[60]
 * the ``sum`` dataset contains the summed areas in each frame.
 * Another example, a hyperspectral image downsampled 16-fold in energy::
 * detector:NXdetector/
 * data[128,128,4096]
 * region:NXregion/
 * @region_type = "rectangular"
 * parent = "data"
 * start = [2]
 * count = [20]
 * stride = [32]
 * block = [16]
 * downsampled:NXdata/
 * @signal = "maximum"
 * @auxiliary_signals = "copy"
 * maximum[128,128,20]
 * copy[128,128,320]
 * the ``copy`` dataset selects 20 16-channel blocks that start 32 channels apart,
 * the ``maximum`` dataset will show maximum values in each 16-channel block
 * in every spectra.
 * 
 */
public class NXregionImpl extends NXobjectImpl implements NXregion {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA);

	public NXregionImpl() {
		super();
	}

	public NXregionImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXregion.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_REGION;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public String getAttributeRegion_type() {
		return getAttrString(null, NX_ATTRIBUTE_REGION_TYPE);
	}

	@Override
	public void setAttributeRegion_type(String region_typeValue) {
		setAttribute(null, NX_ATTRIBUTE_REGION_TYPE, region_typeValue);
	}

	@Override
	public IDataset getParent() {
		return getDataset(NX_PARENT);
	}

	@Override
	public String getParentScalar() {
		return getString(NX_PARENT);
	}

	@Override
	public DataNode setParent(IDataset parentDataset) {
		return setDataset(NX_PARENT, parentDataset);
	}

	@Override
	public DataNode setParentScalar(String parentValue) {
		return setString(NX_PARENT, parentValue);
	}

	@Override
	public IDataset getParent_mask() {
		return getDataset(NX_PARENT_MASK);
	}

	@Override
	public String getParent_maskScalar() {
		return getString(NX_PARENT_MASK);
	}

	@Override
	public DataNode setParent_mask(IDataset parent_maskDataset) {
		return setDataset(NX_PARENT_MASK, parent_maskDataset);
	}

	@Override
	public DataNode setParent_maskScalar(String parent_maskValue) {
		return setString(NX_PARENT_MASK, parent_maskValue);
	}

	@Override
	public IDataset getStart() {
		return getDataset(NX_START);
	}

	@Override
	public Number getStartScalar() {
		return getNumber(NX_START);
	}

	@Override
	public DataNode setStart(IDataset startDataset) {
		return setDataset(NX_START, startDataset);
	}

	@Override
	public DataNode setStartScalar(Number startValue) {
		return setField(NX_START, startValue);
	}

	@Override
	public IDataset getCount() {
		return getDataset(NX_COUNT);
	}

	@Override
	public Long getCountScalar() {
		return getLong(NX_COUNT);
	}

	@Override
	public DataNode setCount(IDataset countDataset) {
		return setDataset(NX_COUNT, countDataset);
	}

	@Override
	public DataNode setCountScalar(Long countValue) {
		return setField(NX_COUNT, countValue);
	}

	@Override
	public IDataset getStride() {
		return getDataset(NX_STRIDE);
	}

	@Override
	public Long getStrideScalar() {
		return getLong(NX_STRIDE);
	}

	@Override
	public DataNode setStride(IDataset strideDataset) {
		return setDataset(NX_STRIDE, strideDataset);
	}

	@Override
	public DataNode setStrideScalar(Long strideValue) {
		return setField(NX_STRIDE, strideValue);
	}

	@Override
	public IDataset getBlock() {
		return getDataset(NX_BLOCK);
	}

	@Override
	public Long getBlockScalar() {
		return getLong(NX_BLOCK);
	}

	@Override
	public DataNode setBlock(IDataset blockDataset) {
		return setDataset(NX_BLOCK, blockDataset);
	}

	@Override
	public DataNode setBlockScalar(Long blockValue) {
		return setField(NX_BLOCK, blockValue);
	}

	@Override
	public IDataset getScale() {
		return getDataset(NX_SCALE);
	}

	@Override
	public Number getScaleScalar() {
		return getNumber(NX_SCALE);
	}

	@Override
	public DataNode setScale(IDataset scaleDataset) {
		return setDataset(NX_SCALE, scaleDataset);
	}

	@Override
	public DataNode setScaleScalar(Number scaleValue) {
		return setField(NX_SCALE, scaleValue);
	}

	@Override
	public NXdata getDownsampled() {
		// dataNodeName = NX_DOWNSAMPLED
		return getChild("downsampled", NXdata.class);
	}

	@Override
	public void setDownsampled(NXdata downsampledGroup) {
		putChild("downsampled", downsampledGroup);
	}

	@Override
	public NXdata getStatistics() {
		// dataNodeName = NX_STATISTICS
		return getChild("statistics", NXdata.class);
	}

	@Override
	public void setStatistics(NXdata statisticsGroup) {
		putChild("statistics", statisticsGroup);
	}

}
