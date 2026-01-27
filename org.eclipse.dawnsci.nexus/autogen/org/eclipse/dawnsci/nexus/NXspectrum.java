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
 * Base class container for reporting a set of spectra.
 * The mostly commonly used scanning methods are supported. That is one-,
 * two-, three-dimensional ROIs discretized using regular Euclidean tilings.
 * Use stack for all other tilings.
 * <p><b>Symbols:</b><ul>
 * <li><b>n_spc</b>
 * Number of spectra in the stack, for stacks the slowest dimension.</li>
 * <li><b>n_k</b>
 * Number of image points along the slower dimension (k equivalent to z).</li>
 * <li><b>n_j</b>
 * Number of image points along the slow dimension (j equivalent to y).</li>
 * <li><b>n_i</b>
 * Number of image points along the fast dimension (i equivalent to x).</li>
 * <li><b>n_energy</b>
 * Number of energy bins (always the fastest dimension).</li></ul></p>
 *
 */
public interface NXspectrum extends NXobject {

	/**
	 * Details how spectra were processed from the detector readings.
	 *
	 * @return  the value.
	 */
	public NXprocess getProcess();

	/**
	 * Details how spectra were processed from the detector readings.
	 *
	 * @param processGroup the processGroup
	 */
	public void setProcess(NXprocess processGroup);

	/**
	 * Get a NXprocess node by name:
	 * <ul>
	 * <li>
	 * Details how spectra were processed from the detector readings.</li>
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
	 * Details how spectra were processed from the detector readings.</li>
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
	 * Details how spectra were processed from the detector readings.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXprocess for that node.
	 */
	public Map<String, NXprocess> getAllProcess();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Details how spectra were processed from the detector readings.</li>
	 * </ul>
	 *
	 * @param process the child nodes to add
	 */

	public void setAllProcess(Map<String, NXprocess> process);

	// Unprocessed group:input
	// Unprocessed group:

	/**
	 * One spectrum for a point of a 0d ROI. Also known as spot measurement.
	 *
	 * @return  the value.
	 */
	public NXdata getSpectrum_0d();

	/**
	 * One spectrum for a point of a 0d ROI. Also known as spot measurement.
	 *
	 * @param spectrum_0dGroup the spectrum_0dGroup
	 */
	public void setSpectrum_0d(NXdata spectrum_0dGroup);

	/**
	 * One spectrum for each point of a 1d ROI.
	 *
	 * @return  the value.
	 */
	public NXdata getSpectrum_1d();

	/**
	 * One spectrum for each point of a 1d ROI.
	 *
	 * @param spectrum_1dGroup the spectrum_1dGroup
	 */
	public void setSpectrum_1d(NXdata spectrum_1dGroup);

	/**
	 * One spectrum for each scan point of 2d ROI.
	 *
	 * @return  the value.
	 */
	public NXdata getSpectrum_2d();

	/**
	 * One spectrum for each scan point of 2d ROI.
	 *
	 * @param spectrum_2dGroup the spectrum_2dGroup
	 */
	public void setSpectrum_2d(NXdata spectrum_2dGroup);

	/**
	 * One spectrum for point of a 3d ROI.
	 *
	 * @return  the value.
	 */
	public NXdata getSpectrum_3d();

	/**
	 * One spectrum for point of a 3d ROI.
	 *
	 * @param spectrum_3dGroup the spectrum_3dGroup
	 */
	public void setSpectrum_3d(NXdata spectrum_3dGroup);

	/**
	 * Multiple instances of spectrum_0d.
	 *
	 * @return  the value.
	 */
	public NXdata getStack_0d();

	/**
	 * Multiple instances of spectrum_0d.
	 *
	 * @param stack_0dGroup the stack_0dGroup
	 */
	public void setStack_0d(NXdata stack_0dGroup);

	/**
	 * Multiple instances of spectrum_2d.
	 *
	 * @return  the value.
	 */
	public NXdata getStack_2d();

	/**
	 * Multiple instances of spectrum_2d.
	 *
	 * @param stack_2dGroup the stack_2dGroup
	 */
	public void setStack_2d(NXdata stack_2dGroup);

	/**
	 * Multiple instances of spectrum_3d.
	 *
	 * @return  the value.
	 */
	public NXdata getStack_3d();

	/**
	 * Multiple instances of spectrum_3d.
	 *
	 * @param stack_3dGroup the stack_3dGroup
	 */
	public void setStack_3d(NXdata stack_3dGroup);

}
