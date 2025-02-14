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
 * Measured set of electron backscatter diffraction data, aka Kikuchi pattern.
 * Kikuchi pattern are the raw data for computational workflows in the field
 * of orientation (imaging) microscopy. The technique is especially used in
 * materials science and materials engineering.
 * Based on a fuse of the `M. A. Jackson et al. <https://doi.org/10.1186/2193-9772-3-4>`_
 * of the DREAM.3D community and the open H5OINA format of Oxford Instruments
 * `P. Pinard et al. <https://doi.org/10.1017/S1431927621006103>`_
 * EBSD can be used, usually with FIB/SEM microscopes, for three-dimensional
 * orientation microscopy. So-called serial section analyses. For a detailed
 * overview of these techniques see e.g.
 * * `M. A. Groeber et al. <https://doi.org/10.1186/2193-9772-3-5>`_
 * * `A. J. Schwartz et al. <https://doi.org/10.1007/978-1-4757-3205-4>`_
 * * `P. A. Rottman et al. <https://doi.org/10.1016/j.mattod.2021.05.003>`_
 * With serial-sectioning this involves however always a sequence of measuring,
 * milling. In this regard, each serial section (measuring) and milling
 * is an own NXevent_data_em instance and thus there such a three-dimensional
 * characterization should be stored as a set of two-dimensional data,
 * with as many NXevent_data_em instances as sections were measured.
 * These measured serial sectioning images need virtually always post-processing
 * to arrive at the aligned and cleaned image stack before a respective digital
 * model of the inspected microstructure can be analyzed. The resulting volume
 * is often termed a so-called (representative) volume element (RVE).
 * Several software packages are available for performing this post-processing.
 * For now we do not consider metadata of these post-processing steps
 * as a part of this base class because the connection between the large variety
 * of such post-processing steps and the measured electron microscopy data
 * is usually very small.
 * If we envision a (knowledge) graph for EBSD it consists of individual
 * sub-graphs which convey information about the specimen preparation,
 * the measurement of the specimen in the electron microscope,
 * the indexing of the collected Kikuchi pattern stack,
 * eventual post-processing of the indexed orientation image
 * via similarity grouping algorithms to yield (grains, texture).
 * Conceptually these post-processing steps are most frequently
 * serving the idea to reconstruct quantitatively so-called
 * microstructural features (grains, phases, interfaces). Materials scientists
 * use these features according to the multi-scale materials modeling paradigm
 * to infer material properties. They do so by quantifying correlations between
 * the spatial arrangement of the features, their individual properties,
 * and (macroscopic) properties of materials.

 */
public class NXimage_set_em_kikuchiImpl extends NXobjectImpl implements NXimage_set_em_kikuchi {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_DATA);

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
	public NXprocess getProcess() {
		// dataNodeName = NX_PROCESS
		return getChild("process", NXprocess.class);
	}

	@Override
	public void setProcess(NXprocess processGroup) {
		putChild("process", processGroup);
	}

	@Override
	public NXprocess getProcess(String name) {
		return getChild(name, NXprocess.class);
	}

	@Override
	public void setProcess(String name, NXprocess process) {
		putChild(name, process);
	}

	@Override
	public Map<String, NXprocess> getAllProcess() {
		return getChildren(NXprocess.class);
	}

	@Override
	public void setAllProcess(Map<String, NXprocess> process) {
		setChildren(process);
	}

	@Override
	public NXdata getStack() {
		// dataNodeName = NX_STACK
		return getChild("stack", NXdata.class);
	}

	@Override
	public void setStack(NXdata stackGroup) {
		putChild("stack", stackGroup);
	}

}
