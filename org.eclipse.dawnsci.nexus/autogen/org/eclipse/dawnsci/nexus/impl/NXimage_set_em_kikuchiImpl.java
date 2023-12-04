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
 * Electron backscatter diffraction (EBSD) Kikuchi pattern.
 * The container can also store data related to a post-processing of these
 * Kikuchi pattern, which is the backbone of orientation microscopy
 * especially in materials science and materials engineering.
 * Based on a fuse of the `M. A. Jackson et al. <https://doi.org/10.1186/2193-9772-3-4>`_
 * of the DREAM.3D community and the open H5OINA format of Oxford Instruments
 * `P. Pinard et al. <https://doi.org/10.1017/S1431927621006103>`_
 * EBSD can be used, usually with FIB/SEM microscopes, for three-dimensional
 * orientation microscopy. So-called serial section analyses. For a detailed
 * overview of these techniques see e.g.
 * * `M. A. Groeber et al. <https://doi.org/10.1186/2193-9772-3-5>`_
 * * `A. J. Schwartz et al. <https://doi.org/10.1007/978-1-4757-3205-4>`_
 * * `P. A. Rottman et al. <https://doi.org/10.1016/j.mattod.2021.05.003>`_
 * With serial-sectioning this involves however always a sequence of
 * measuring, milling. In this regard, each serial section (measuring) and milling
 * is an own NXevent_data_em instance and thus there such a three-dimensional
 * characterization should be stored as a set of two-dimensional data,
 * with as many NXevent_data_em instances as sections were measured.
 * These measured serial sectioning images need virtually always post-processing
 * to arrive at the aligned and cleaned image stack respective digital
 * microstructure representation as (a representative) volume element.
 * Several software packages are available for this post-processing.
 * For now we do not consider metadata of these post-processing steps
 * as a part of this base class.
 * 
 */
public class NXimage_set_em_kikuchiImpl extends NXobjectImpl implements NXimage_set_em_kikuchi {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_COLLECTION,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_COLLECTION);

	public NXimage_set_em_kikuchiImpl() {
		super();
	}

	public NXimage_set_em_kikuchiImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXimage_set_em_kikuchi.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_IMAGE_SET_EM_KIKUCHI;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public IDataset getGrid_type() {
		return getDataset(NX_GRID_TYPE);
	}

	@Override
	public String getGrid_typeScalar() {
		return getString(NX_GRID_TYPE);
	}

	@Override
	public DataNode setGrid_type(IDataset grid_typeDataset) {
		return setDataset(NX_GRID_TYPE, grid_typeDataset);
	}

	@Override
	public DataNode setGrid_typeScalar(String grid_typeValue) {
		return setString(NX_GRID_TYPE, grid_typeValue);
	}

	@Override
	public IDataset getStep_size() {
		return getDataset(NX_STEP_SIZE);
	}

	@Override
	public Number getStep_sizeScalar() {
		return getNumber(NX_STEP_SIZE);
	}

	@Override
	public DataNode setStep_size(IDataset step_sizeDataset) {
		return setDataset(NX_STEP_SIZE, step_sizeDataset);
	}

	@Override
	public DataNode setStep_sizeScalar(Number step_sizeValue) {
		return setField(NX_STEP_SIZE, step_sizeValue);
	}

	@Override
	public NXprocess getCalibration() {
		// dataNodeName = NX_CALIBRATION
		return getChild("calibration", NXprocess.class);
	}

	@Override
	public void setCalibration(NXprocess calibrationGroup) {
		putChild("calibration", calibrationGroup);
	}

	@Override
	public NXprocess getOim() {
		// dataNodeName = NX_OIM
		return getChild("oim", NXprocess.class);
	}

	@Override
	public void setOim(NXprocess oimGroup) {
		putChild("oim", oimGroup);
	}
	// Unprocessed group:  background_correction
	// Unprocessed group:  band_detection
	// Unprocessed group:  indexing

	@Override
	public NXcollection getBinning() {
		// dataNodeName = NX_BINNING
		return getChild("binning", NXcollection.class);
	}

	@Override
	public void setBinning(NXcollection binningGroup) {
		putChild("binning", binningGroup);
	}

	@Override
	public NXprocess getHough_transformation() {
		// dataNodeName = NX_HOUGH_TRANSFORMATION
		return getChild("hough_transformation", NXprocess.class);
	}

	@Override
	public void setHough_transformation(NXprocess hough_transformationGroup) {
		putChild("hough_transformation", hough_transformationGroup);
	}

	@Override
	public NXcollection getProfiling() {
		// dataNodeName = NX_PROFILING
		return getChild("profiling", NXcollection.class);
	}

	@Override
	public void setProfiling(NXcollection profilingGroup) {
		putChild("profiling", profilingGroup);
	}

}
