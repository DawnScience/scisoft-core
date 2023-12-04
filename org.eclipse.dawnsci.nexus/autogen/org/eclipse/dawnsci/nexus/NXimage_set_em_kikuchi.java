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
 * <p><b>Symbols:</b> <ul>
 * <li><b>n_p</b> 
 * Number of scan points, one pattern per scan point.</li>
 * <li><b>n_y</b> 
 * Number of pixel per Kikuchi pattern in the slow direction</li>
 * <li><b>n_x</b> 
 * Number of pixel per Kikuchi pattern in the fast direction</li></ul></p>
 * 
 */
public interface NXimage_set_em_kikuchi extends NXobject {

	public static final String NX_GRID_TYPE = "grid_type";
	public static final String NX_STEP_SIZE = "step_size";
	/**
	 * Collected Kikuchi pattern as an image stack.
	 * 
	 * @return  the value.
	 */
	public NXdata getData();
	
	/**
	 * Collected Kikuchi pattern as an image stack.
	 * 
	 * @param dataGroup the dataGroup
	 */
	public void setData(NXdata dataGroup);

	/**
	 * Get a NXdata node by name:
	 * <ul>
	 * <li>
	 * Collected Kikuchi pattern as an image stack.</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdata for that node.
	 */
	public NXdata getData(String name);
	
	/**
	 * Set a NXdata node by name:
	 * <ul>
	 * <li>
	 * Collected Kikuchi pattern as an image stack.</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param data the value to set
	 */
	public void setData(String name, NXdata data);
	
	/**
	 * Get all NXdata nodes:
	 * <ul>
	 * <li>
	 * Collected Kikuchi pattern as an image stack.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXdata for that node.
	 */
	public Map<String, NXdata> getAllData();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Collected Kikuchi pattern as an image stack.</li>
	 * </ul>
	 * 
	 * @param data the child nodes to add 
	 */
	
	public void setAllData(Map<String, NXdata> data);
	

	/**
	 * Which pixel primitive shape is used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>square</b> </li>
	 * <li><b>hexagon</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getGrid_type();
	
	/**
	 * Which pixel primitive shape is used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>square</b> </li>
	 * <li><b>hexagon</b> </li></ul></p>
	 * </p>
	 * 
	 * @param grid_typeDataset the grid_typeDataset
	 */
	public DataNode setGrid_type(IDataset grid_typeDataset);

	/**
	 * Which pixel primitive shape is used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>square</b> </li>
	 * <li><b>hexagon</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getGrid_typeScalar();

	/**
	 * Which pixel primitive shape is used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>square</b> </li>
	 * <li><b>hexagon</b> </li></ul></p>
	 * </p>
	 * 
	 * @param grid_type the grid_type
	 */
	public DataNode setGrid_typeScalar(String grid_typeValue);

	/**
	 * The prescribed step size. First value is for the slow changing,
	 * second value is for the fast changing dimension.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getStep_size();
	
	/**
	 * The prescribed step size. First value is for the slow changing,
	 * second value is for the fast changing dimension.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 * 
	 * @param step_sizeDataset the step_sizeDataset
	 */
	public DataNode setStep_size(IDataset step_sizeDataset);

	/**
	 * The prescribed step size. First value is for the slow changing,
	 * second value is for the fast changing dimension.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getStep_sizeScalar();

	/**
	 * The prescribed step size. First value is for the slow changing,
	 * second value is for the fast changing dimension.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 * 
	 * @param step_size the step_size
	 */
	public DataNode setStep_sizeScalar(Number step_sizeValue);

	/**
	 * 
	 * @return  the value.
	 */
	public NXprocess getCalibration();
	
	/**
	 * 
	 * @param calibrationGroup the calibrationGroup
	 */
	public void setCalibration(NXprocess calibrationGroup);

	/**
	 * OIM, orientation imaging microscopy.
	 * Post-processing of the Kikuchi pattern to identify orientations.
	 * 
	 * @return  the value.
	 */
	public NXprocess getOim();
	
	/**
	 * OIM, orientation imaging microscopy.
	 * Post-processing of the Kikuchi pattern to identify orientations.
	 * 
	 * @param oimGroup the oimGroup
	 */
	public void setOim(NXprocess oimGroup);
	// Unprocessed group: background_correction
	// Unprocessed group: band_detection
	// Unprocessed group: indexing

	/**
	 * 
	 * @return  the value.
	 */
	public NXcollection getBinning();
	
	/**
	 * 
	 * @param binningGroup the binningGroup
	 */
	public void setBinning(NXcollection binningGroup);

	/**
	 * 
	 * @return  the value.
	 */
	public NXprocess getHough_transformation();
	
	/**
	 * 
	 * @param hough_transformationGroup the hough_transformationGroup
	 */
	public void setHough_transformation(NXprocess hough_transformationGroup);

	/**
	 * 
	 * @return  the value.
	 */
	public NXcollection getProfiling();
	
	/**
	 * 
	 * @param profilingGroup the profilingGroup
	 */
	public void setProfiling(NXcollection profilingGroup);

}
