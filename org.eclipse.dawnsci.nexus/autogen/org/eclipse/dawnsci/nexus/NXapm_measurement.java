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
import org.eclipse.january.dataset.Dataset;

/**
 * Base class for collecting a run with a real or a simulated atom probe or field-ion microscope.
 * The term run is understood as an exact synonym for session, i.e. the usage of a real or simulated
 * tomograph or microscope for a certain amount of time during which one characterizes a single specimen.
 * Research workflows for experiments and simulations of atom probe and related field-evaporation
 * evolve continuously and become increasingly connected with other methods used for material
 * characterization specifically electron microscopy. A few examples in this direction are:
 * * `T. Kelly et al. <https://doi.org/10.1017/S1431927620022205>`_
 * * `C. Fleischmann et al. <https://doi.org/10.1016/j.ultramic.2018.08.010>`_
 * * `W. Windl et al. <https://doi.org/10.1093/micmic/ozad067.294>`_
 * * `C. Freysoldt et al. <https://doi.org/10.1103/PhysRevLett.124.176801>`_
 * * `G. da Costa et al. <https://doi.org/10.1038/s41467-024-54169-2>`_
 * The majority of atom probe research is performed using the so-called Local Electrode Atom Probe (LEAP) instruments
 * from AMETEK/Cameca. In addition, several research groups have built their own instruments and shared different
 * aspects of the technical specifications and approaches including how these groups apply data processing e.g.:
 * * `M. Monajem et al. <https://doi.org/10.1017/S1431927622003397>`_
 * * `P. Stender et al. <https://doi.org/10.1017/S1431927621013982>`_
 * * `I. Dimkou et al. <https://doi.org/10.1093/micmic/ozac051>`_
 * to name but a few.
 *
 */
public interface NXapm_measurement extends NXobject {

	public static final String NX_STATUS = "status";
	public static final String NX_QUALITY = "quality";
	/**
	 * A statement whether the measurement completed successfully, or was aborted.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>success</b> </li>
	 * <li><b>aborted</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStatus();

	/**
	 * A statement whether the measurement completed successfully, or was aborted.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>success</b> </li>
	 * <li><b>aborted</b> </li></ul></p>
	 * </p>
	 *
	 * @param statusDataset the statusDataset
	 */
	public DataNode setStatus(IDataset statusDataset);

	/**
	 * A statement whether the measurement completed successfully, or was aborted.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>success</b> </li>
	 * <li><b>aborted</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getStatusScalar();

	/**
	 * A statement whether the measurement completed successfully, or was aborted.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>success</b> </li>
	 * <li><b>aborted</b> </li></ul></p>
	 * </p>
	 *
	 * @param status the status
	 */
	public DataNode setStatusScalar(String statusValue);

	/**
	 * Statement about the quality of the measurement.
	 * The value can be extracted from the CAnalysis.CResults.fQuality
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getQuality();

	/**
	 * Statement about the quality of the measurement.
	 * The value can be extracted from the CAnalysis.CResults.fQuality
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param qualityDataset the qualityDataset
	 */
	public DataNode setQuality(IDataset qualityDataset);

	/**
	 * Statement about the quality of the measurement.
	 * The value can be extracted from the CAnalysis.CResults.fQuality
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getQualityScalar();

	/**
	 * Statement about the quality of the measurement.
	 * The value can be extracted from the CAnalysis.CResults.fQuality
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param quality the quality
	 */
	public DataNode setQualityScalar(String qualityValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXapm_instrument getApm_instrument();

	/**
	 *
	 * @param apm_instrumentGroup the apm_instrumentGroup
	 */
	public void setApm_instrument(NXapm_instrument apm_instrumentGroup);

	/**
	 * Get a NXapm_instrument node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXapm_instrument for that node.
	 */
	public NXapm_instrument getApm_instrument(String name);

	/**
	 * Set a NXapm_instrument node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param apm_instrument the value to set
	 */
	public void setApm_instrument(String name, NXapm_instrument apm_instrument);

	/**
	 * Get all NXapm_instrument nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXapm_instrument for that node.
	 */
	public Map<String, NXapm_instrument> getAllApm_instrument();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param apm_instrument the child nodes to add
	 */

	public void setAllApm_instrument(Map<String, NXapm_instrument> apm_instrument);


	/**
	 *
	 * @return  the value.
	 */
	public NXapm_event_data getApm_event_data();

	/**
	 *
	 * @param apm_event_dataGroup the apm_event_dataGroup
	 */
	public void setApm_event_data(NXapm_event_data apm_event_dataGroup);

	/**
	 * Get a NXapm_event_data node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXapm_event_data for that node.
	 */
	public NXapm_event_data getApm_event_data(String name);

	/**
	 * Set a NXapm_event_data node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param apm_event_data the value to set
	 */
	public void setApm_event_data(String name, NXapm_event_data apm_event_data);

	/**
	 * Get all NXapm_event_data nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXapm_event_data for that node.
	 */
	public Map<String, NXapm_event_data> getAllApm_event_data();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param apm_event_data the child nodes to add
	 */

	public void setAllApm_event_data(Map<String, NXapm_event_data> apm_event_data);


}
