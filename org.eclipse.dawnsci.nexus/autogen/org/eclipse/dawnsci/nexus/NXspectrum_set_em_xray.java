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
 * Container for reporting a set of energy-dispersive X-ray spectra.
 * Virtually the most important case is that spectra are collected in
 * a scanning microscope (SEM or STEM) for a collection of points.
 * The majority of cases use simple d-dimensional regular scan pattern,
 * such as single point, line profiles, or (rectangular) surface mappings.
 * The latter pattern is the most frequently used.
 * For now the base class provides for scans where the settings,
 * binning, and energy resolution is the same for each scan point.
 * `IUPAC instead of Siegbahn notation <https://doi.org/10.1002/xrs.1300200308>`_ should be used.
 * <p><b>Symbols:</b> <ul>
 * <li><b>n_p</b> 
 * Number of scan points</li>
 * <li><b>n_y</b> 
 * Number of pixel per Kikuchi pattern in the slow direction</li>
 * <li><b>n_x</b> 
 * Number of pixel per Kikuchi pattern in the fast direction</li>
 * <li><b>n_photon_energy</b> 
 * Number of X-ray photon energy (bins)</li>
 * <li><b>n_elements</b> 
 * Number of identified elements</li>
 * <li><b>n_peaks</b> 
 * Number of peaks</li></ul></p>
 * 
 */
public interface NXspectrum_set_em_xray extends NXobject {

	/**
	 * Collected X-ray counts chunked based on rectangular images.
	 * This representation supports only rectangular scan pattern.
	 * 
	 * @return  the value.
	 */
	public NXdata getData();
	
	/**
	 * Collected X-ray counts chunked based on rectangular images.
	 * This representation supports only rectangular scan pattern.
	 * 
	 * @param dataGroup the dataGroup
	 */
	public void setData(NXdata dataGroup);

	/**
	 * Get a NXdata node by name:
	 * <ul>
	 * <li>
	 * Collected X-ray counts chunked based on rectangular images.
	 * This representation supports only rectangular scan pattern.</li>
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
	 * Collected X-ray counts chunked based on rectangular images.
	 * This representation supports only rectangular scan pattern.</li>
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
	 * Collected X-ray counts chunked based on rectangular images.
	 * This representation supports only rectangular scan pattern.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXdata for that node.
	 */
	public Map<String, NXdata> getAllData();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Collected X-ray counts chunked based on rectangular images.
	 * This representation supports only rectangular scan pattern.</li>
	 * </ul>
	 * 
	 * @param data the child nodes to add 
	 */
	
	public void setAllData(Map<String, NXdata> data);
	

	/**
	 * Details about computational steps how peaks were indexed as elements.
	 * 
	 * @return  the value.
	 */
	public NXprocess getIndexing();
	
	/**
	 * Details about computational steps how peaks were indexed as elements.
	 * 
	 * @param indexingGroup the indexingGroup
	 */
	public void setIndexing(NXprocess indexingGroup);
	// Unprocessed group: 
	// Unprocessed group: composition_map

}
