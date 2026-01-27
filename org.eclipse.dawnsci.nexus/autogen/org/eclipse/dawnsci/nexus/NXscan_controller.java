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
 * The scan box or scan controller is a component that is used to deflect a
 * beam of charged particles in a controlled manner.
 * The scan box is instructed by (an) instance(s) of :ref:`NXprogram`, some control software,
 * which is not necessarily the same program as the one controlling other parts of the instrument.
 * The scan box directs the probe of charged particles (electrons, ions)
 * to controlled locations according to a scan scheme and plan.
 *
 */
public interface NXscan_controller extends NXcomponent {

	public static final String NX_SCAN_SCHEMA = "scan_schema";
	public static final String NX_DWELL_TIME = "dwell_time";
	public static final String NX_FLYBACK_TIME = "flyback_time";
	/**
	 * Name of the typically tech-partner-specific term that specifies an
	 * automated protocol which details how the components of the scan_box
	 * and the instrument work together to achieve a controlled
	 * scanning of the beam (over the sample surface).
	 * Oftentimes users do not need to or are not able to disentangle the intricate
	 * details of the spatiotemporal dynamics of their instrument. Instead, often
	 * they rely on the assumption that the instrument and its controlling programs
	 * work as expected. The field scan_schema can be used to add some constraints
	 * on how the beam was scanned over the surface.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getScan_schema();

	/**
	 * Name of the typically tech-partner-specific term that specifies an
	 * automated protocol which details how the components of the scan_box
	 * and the instrument work together to achieve a controlled
	 * scanning of the beam (over the sample surface).
	 * Oftentimes users do not need to or are not able to disentangle the intricate
	 * details of the spatiotemporal dynamics of their instrument. Instead, often
	 * they rely on the assumption that the instrument and its controlling programs
	 * work as expected. The field scan_schema can be used to add some constraints
	 * on how the beam was scanned over the surface.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param scan_schemaDataset the scan_schemaDataset
	 */
	public DataNode setScan_schema(IDataset scan_schemaDataset);

	/**
	 * Name of the typically tech-partner-specific term that specifies an
	 * automated protocol which details how the components of the scan_box
	 * and the instrument work together to achieve a controlled
	 * scanning of the beam (over the sample surface).
	 * Oftentimes users do not need to or are not able to disentangle the intricate
	 * details of the spatiotemporal dynamics of their instrument. Instead, often
	 * they rely on the assumption that the instrument and its controlling programs
	 * work as expected. The field scan_schema can be used to add some constraints
	 * on how the beam was scanned over the surface.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getScan_schemaScalar();

	/**
	 * Name of the typically tech-partner-specific term that specifies an
	 * automated protocol which details how the components of the scan_box
	 * and the instrument work together to achieve a controlled
	 * scanning of the beam (over the sample surface).
	 * Oftentimes users do not need to or are not able to disentangle the intricate
	 * details of the spatiotemporal dynamics of their instrument. Instead, often
	 * they rely on the assumption that the instrument and its controlling programs
	 * work as expected. The field scan_schema can be used to add some constraints
	 * on how the beam was scanned over the surface.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param scan_schema the scan_schema
	 */
	public DataNode setScan_schemaScalar(String scan_schemaValue);

	/**
	 * Time period during which the beam remains at one position.
	 * This concept is related to term `Dwell Time`_ of the EMglossary standard.
	 * .. _Dwell Time: https://purls.helmholtz-metadaten.de/emg/EMG_00000015
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDwell_time();

	/**
	 * Time period during which the beam remains at one position.
	 * This concept is related to term `Dwell Time`_ of the EMglossary standard.
	 * .. _Dwell Time: https://purls.helmholtz-metadaten.de/emg/EMG_00000015
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param dwell_timeDataset the dwell_timeDataset
	 */
	public DataNode setDwell_time(IDataset dwell_timeDataset);

	/**
	 * Time period during which the beam remains at one position.
	 * This concept is related to term `Dwell Time`_ of the EMglossary standard.
	 * .. _Dwell Time: https://purls.helmholtz-metadaten.de/emg/EMG_00000015
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDwell_timeScalar();

	/**
	 * Time period during which the beam remains at one position.
	 * This concept is related to term `Dwell Time`_ of the EMglossary standard.
	 * .. _Dwell Time: https://purls.helmholtz-metadaten.de/emg/EMG_00000015
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param dwell_time the dwell_time
	 */
	public DataNode setDwell_timeScalar(Number dwell_timeValue);

	/**
	 * Time period during which the beam moves from the final position of one scan
	 * line to the starting position of the subsequent scan line.
	 * This concept is related to term `Flyback Time`_ of the EMglossary standard.
	 * .. _Flyback Time: https://purls.helmholtz-metadaten.de/emg/EMG_00000028
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFlyback_time();

	/**
	 * Time period during which the beam moves from the final position of one scan
	 * line to the starting position of the subsequent scan line.
	 * This concept is related to term `Flyback Time`_ of the EMglossary standard.
	 * .. _Flyback Time: https://purls.helmholtz-metadaten.de/emg/EMG_00000028
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param flyback_timeDataset the flyback_timeDataset
	 */
	public DataNode setFlyback_time(IDataset flyback_timeDataset);

	/**
	 * Time period during which the beam moves from the final position of one scan
	 * line to the starting position of the subsequent scan line.
	 * This concept is related to term `Flyback Time`_ of the EMglossary standard.
	 * .. _Flyback Time: https://purls.helmholtz-metadaten.de/emg/EMG_00000028
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFlyback_timeScalar();

	/**
	 * Time period during which the beam moves from the final position of one scan
	 * line to the starting position of the subsequent scan line.
	 * This concept is related to term `Flyback Time`_ of the EMglossary standard.
	 * .. _Flyback Time: https://purls.helmholtz-metadaten.de/emg/EMG_00000028
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param flyback_time the flyback_time
	 */
	public DataNode setFlyback_timeScalar(Number flyback_timeValue);

	/**
	 * Details about components which realize the deflection technically.
	 * This concept should be used for all those components that implement
	 * the scanning of the beam, while components like beam blankers etc. should
	 * use rather the NXdeflector concept of the NXebeam_column base class.
	 *
	 * @return  the value.
	 */
	public NXdeflector getDeflector();

	/**
	 * Details about components which realize the deflection technically.
	 * This concept should be used for all those components that implement
	 * the scanning of the beam, while components like beam blankers etc. should
	 * use rather the NXdeflector concept of the NXebeam_column base class.
	 *
	 * @param deflectorGroup the deflectorGroup
	 */
	public void setDeflector(NXdeflector deflectorGroup);

	/**
	 * Get a NXdeflector node by name:
	 * <ul>
	 * <li>
	 * Details about components which realize the deflection technically.
	 * This concept should be used for all those components that implement
	 * the scanning of the beam, while components like beam blankers etc. should
	 * use rather the NXdeflector concept of the NXebeam_column base class.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public NXdeflector getDeflector(String name);

	/**
	 * Set a NXdeflector node by name:
	 * <ul>
	 * <li>
	 * Details about components which realize the deflection technically.
	 * This concept should be used for all those components that implement
	 * the scanning of the beam, while components like beam blankers etc. should
	 * use rather the NXdeflector concept of the NXebeam_column base class.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param deflector the value to set
	 */
	public void setDeflector(String name, NXdeflector deflector);

	/**
	 * Get all NXdeflector nodes:
	 * <ul>
	 * <li>
	 * Details about components which realize the deflection technically.
	 * This concept should be used for all those components that implement
	 * the scanning of the beam, while components like beam blankers etc. should
	 * use rather the NXdeflector concept of the NXebeam_column base class.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public Map<String, NXdeflector> getAllDeflector();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Details about components which realize the deflection technically.
	 * This concept should be used for all those components that implement
	 * the scanning of the beam, while components like beam blankers etc. should
	 * use rather the NXdeflector concept of the NXebeam_column base class.</li>
	 * </ul>
	 *
	 * @param deflector the child nodes to add
	 */

	public void setAllDeflector(Map<String, NXdeflector> deflector);


	/**
	 *
	 * @return  the value.
	 */
	public NXcircuit getCircuit();

	/**
	 *
	 * @param circuitGroup the circuitGroup
	 */
	public void setCircuit(NXcircuit circuitGroup);

	/**
	 * Get a NXcircuit node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcircuit for that node.
	 */
	public NXcircuit getCircuit(String name);

	/**
	 * Set a NXcircuit node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param circuit the value to set
	 */
	public void setCircuit(String name, NXcircuit circuit);

	/**
	 * Get all NXcircuit nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcircuit for that node.
	 */
	public Map<String, NXcircuit> getAllCircuit();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param circuit the child nodes to add
	 */

	public void setAllCircuit(Map<String, NXcircuit> circuit);


}
