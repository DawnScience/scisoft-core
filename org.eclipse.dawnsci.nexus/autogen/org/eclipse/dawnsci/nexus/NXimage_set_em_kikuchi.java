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

package org.eclipse.dawnsci.nexus;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

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
 * <p><b>Symbols:</b><ul>
 * <li><b>n_sc</b>
 * Number of scanned points. Scan point may have none, one, or more pattern.</li>
 * <li><b>n_p</b>
 * Number of diffraction pattern.</li>
 * <li><b>n_y</b>
 * Number of pixel per Kikuchi pattern in the slow direction.</li>
 * <li><b>n_x</b>
 * Number of pixel per Kikuchi pattern in the fast direction.</li></ul></p>
 *
 */
public interface NXimage_set_em_kikuchi extends NXobject {

	/**
	 * Details how Kikuchi pattern were processed from the detector readings.
	 * Scientists interested in EBSD should inspect the respective NXem_ebsd
	 * application definition which can be used as a partner application definition
	 * to detail substantially more details to this processing.
	 *
	 * @return  the value.
	 */
	public NXprocess getProcess();

	/**
	 * Details how Kikuchi pattern were processed from the detector readings.
	 * Scientists interested in EBSD should inspect the respective NXem_ebsd
	 * application definition which can be used as a partner application definition
	 * to detail substantially more details to this processing.
	 *
	 * @param processGroup the processGroup
	 */
	public void setProcess(NXprocess processGroup);

	/**
	 * Get a NXprocess node by name:
	 * <ul>
	 * <li>
	 * Details how Kikuchi pattern were processed from the detector readings.
	 * Scientists interested in EBSD should inspect the respective NXem_ebsd
	 * application definition which can be used as a partner application definition
	 * to detail substantially more details to this processing.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXprocess for that node.
	 */
	public NXprocess getProcess(String name);

	/**
	 * Set a NXprocess node by name:
	 * <ul>
	 * <li>
	 * Details how Kikuchi pattern were processed from the detector readings.
	 * Scientists interested in EBSD should inspect the respective NXem_ebsd
	 * application definition which can be used as a partner application definition
	 * to detail substantially more details to this processing.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param process the value to set
	 */
	public void setProcess(String name, NXprocess process);

	/**
	 * Get all NXprocess nodes:
	 * <ul>
	 * <li>
	 * Details how Kikuchi pattern were processed from the detector readings.
	 * Scientists interested in EBSD should inspect the respective NXem_ebsd
	 * application definition which can be used as a partner application definition
	 * to detail substantially more details to this processing.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXprocess for that node.
	 */
	public Map<String, NXprocess> getAllProcess();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Details how Kikuchi pattern were processed from the detector readings.
	 * Scientists interested in EBSD should inspect the respective NXem_ebsd
	 * application definition which can be used as a partner application definition
	 * to detail substantially more details to this processing.</li>
	 * </ul>
	 *
	 * @param process the child nodes to add
	 */

	public void setAllProcess(Map<String, NXprocess> process);


	/**
	 * Collected Kikuchi pattern as an image stack. As raw and closest to the
	 * first retrievable measured data as possible, i.e. do not use this
	 * container to store already averaged, filtered or whatever post-processed
	 * pattern unless these are generated unmodifiably by the instrument
	 * given the way how the instrument and control software was configured
	 * for your microscope session.
	 *
	 * @return  the value.
	 */
	public NXdata getStack();

	/**
	 * Collected Kikuchi pattern as an image stack. As raw and closest to the
	 * first retrievable measured data as possible, i.e. do not use this
	 * container to store already averaged, filtered or whatever post-processed
	 * pattern unless these are generated unmodifiably by the instrument
	 * given the way how the instrument and control software was configured
	 * for your microscope session.
	 *
	 * @param stackGroup the stackGroup
	 */
	public void setStack(NXdata stackGroup);

}
