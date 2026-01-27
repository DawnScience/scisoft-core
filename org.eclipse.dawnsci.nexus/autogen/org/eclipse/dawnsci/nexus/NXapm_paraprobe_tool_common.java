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

import java.util.Date;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Base class documenting organizational metadata used by all tools of the
 * paraprobe-toolbox.
 *
 */
public interface NXapm_paraprobe_tool_common extends NXobject {

	public static final String NX_STATUS = "status";
	public static final String NX_IDENTIFIER_ANALYSIS = "identifier_analysis";
	/**
	 * A statement whether the tool executable managed to process the analysis
	 * or whether this failed. Status is written to the results file after the
	 * end_time beyond which point in time the tool must no longer compute
	 * any further analysis results but exit.
	 * Only when this status message is present and its value is `success`,
	 * one should consider the results of the tool. In all other cases it might
	 * be that the tool has terminated prematurely or another error occurred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>success</b> </li>
	 * <li><b>failure</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStatus();

	/**
	 * A statement whether the tool executable managed to process the analysis
	 * or whether this failed. Status is written to the results file after the
	 * end_time beyond which point in time the tool must no longer compute
	 * any further analysis results but exit.
	 * Only when this status message is present and its value is `success`,
	 * one should consider the results of the tool. In all other cases it might
	 * be that the tool has terminated prematurely or another error occurred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>success</b> </li>
	 * <li><b>failure</b> </li></ul></p>
	 * </p>
	 *
	 * @param statusDataset the statusDataset
	 */
	public DataNode setStatus(IDataset statusDataset);

	/**
	 * A statement whether the tool executable managed to process the analysis
	 * or whether this failed. Status is written to the results file after the
	 * end_time beyond which point in time the tool must no longer compute
	 * any further analysis results but exit.
	 * Only when this status message is present and its value is `success`,
	 * one should consider the results of the tool. In all other cases it might
	 * be that the tool has terminated prematurely or another error occurred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>success</b> </li>
	 * <li><b>failure</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getStatusScalar();

	/**
	 * A statement whether the tool executable managed to process the analysis
	 * or whether this failed. Status is written to the results file after the
	 * end_time beyond which point in time the tool must no longer compute
	 * any further analysis results but exit.
	 * Only when this status message is present and its value is `success`,
	 * one should consider the results of the tool. In all other cases it might
	 * be that the tool has terminated prematurely or another error occurred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>success</b> </li>
	 * <li><b>failure</b> </li></ul></p>
	 * </p>
	 *
	 * @param status the status
	 */
	public DataNode setStatusScalar(String statusValue);

	/**
	 * Internal identifier used by the tool to refer to an analysis.
	 * Simulation ID is an alias.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_analysis();

	/**
	 * Internal identifier used by the tool to refer to an analysis.
	 * Simulation ID is an alias.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_analysisDataset the identifier_analysisDataset
	 */
	public DataNode setIdentifier_analysis(IDataset identifier_analysisDataset);

	/**
	 * Internal identifier used by the tool to refer to an analysis.
	 * Simulation ID is an alias.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifier_analysisScalar();

	/**
	 * Internal identifier used by the tool to refer to an analysis.
	 * Simulation ID is an alias.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_analysis the identifier_analysis
	 */
	public DataNode setIdentifier_analysisScalar(Long identifier_analysisValue);

	/**
	 * The configuration file that was used to parameterize
	 * the algorithms that this tool has executed.
	 *
	 * @return  the value.
	 */
	public NXnote getConfig();

	/**
	 * The configuration file that was used to parameterize
	 * the algorithms that this tool has executed.
	 *
	 * @param configGroup the configGroup
	 */
	public void setConfig(NXnote configGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXprogram getProgramid();

	/**
	 *
	 * @param programidGroup the programidGroup
	 */
	public void setProgramid(NXprogram programidGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXcs_profiling getProfiling();

	/**
	 *
	 * @param profilingGroup the profilingGroup
	 */
	public void setProfiling(NXcs_profiling profilingGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXuser getUserid();

	/**
	 *
	 * @param useridGroup the useridGroup
	 */
	public void setUserid(NXuser useridGroup);

	/**
	 * Details about coordinate systems (reference frames) used. In atom probe several coordinate
	 * systems have to be distinguished. Names of instances of such :ref:`NXcoordinate_system`
	 * should be documented explicitly and doing so by picking from the
	 * following controlled set of names:
	 * * paraprobe_reference_frame
	 * * lab_reference_frame
	 * * specimen_reference_frame
	 * * laser_reference_frame
	 * * instrument_reference_frame
	 * * detector_reference_frame
	 * * reconstruction_reference_frame
	 * The aim of this convention is to support users with contextualizing which reference frame
	 * each instance (coordinate system) is. If needed, instances of :ref:`NXtransformations`
	 * are used to detail the explicit affine transformations whereby one can convert
	 * representations between different reference frames.
	 * Inspect :ref:`NXtransformations` for further details.
	 *
	 * @return  the value.
	 */
	public NXcoordinate_system getNamed_reference_frameid();

	/**
	 * Details about coordinate systems (reference frames) used. In atom probe several coordinate
	 * systems have to be distinguished. Names of instances of such :ref:`NXcoordinate_system`
	 * should be documented explicitly and doing so by picking from the
	 * following controlled set of names:
	 * * paraprobe_reference_frame
	 * * lab_reference_frame
	 * * specimen_reference_frame
	 * * laser_reference_frame
	 * * instrument_reference_frame
	 * * detector_reference_frame
	 * * reconstruction_reference_frame
	 * The aim of this convention is to support users with contextualizing which reference frame
	 * each instance (coordinate system) is. If needed, instances of :ref:`NXtransformations`
	 * are used to detail the explicit affine transformations whereby one can convert
	 * representations between different reference frames.
	 * Inspect :ref:`NXtransformations` for further details.
	 *
	 * @param named_reference_frameidGroup the named_reference_frameidGroup
	 */
	public void setNamed_reference_frameid(NXcoordinate_system named_reference_frameidGroup);

}
