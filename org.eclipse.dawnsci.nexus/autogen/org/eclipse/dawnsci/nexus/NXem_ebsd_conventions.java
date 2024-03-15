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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Conventions for rotations and coordinate systems to interpret EBSD data.
 * This is the main issue which currently is not in all cases documented
 * and thus limits the interoperability and value of collected EBSD data.
 * Not communicating EBSD data with such contextual pieces of information
 * and the use of file formats which do not store this information is the
 * key unsolved problem.
 *
 */
public interface NXem_ebsd_conventions extends NXobject {

	/**
	 * Mathematical conventions and materials-science-specific conventions
	 * required for interpreting every collection of orientation data.
	 *
	 * @return  the value.
	 */
	public NXprocess getRotation_conventions();

	/**
	 * Mathematical conventions and materials-science-specific conventions
	 * required for interpreting every collection of orientation data.
	 *
	 * @param rotation_conventionsGroup the rotation_conventionsGroup
	 */
	public void setRotation_conventions(NXprocess rotation_conventionsGroup);

	/**
	 * Details about eventually relevant named directions that may
	 * give reasons for anisotropies. The classical example is cold-rolling
	 * where one has to specify which directions (rolling, transverse, and normal)
	 * align how with the direction of the base vectors of the sample_reference_frame.
	 *
	 * @return  the value.
	 */
	public NXprocess getProcessing_reference_frame();

	/**
	 * Details about eventually relevant named directions that may
	 * give reasons for anisotropies. The classical example is cold-rolling
	 * where one has to specify which directions (rolling, transverse, and normal)
	 * align how with the direction of the base vectors of the sample_reference_frame.
	 *
	 * @param processing_reference_frameGroup the processing_reference_frameGroup
	 */
	public void setProcessing_reference_frame(NXprocess processing_reference_frameGroup);

	/**
	 * Details about the sample/specimen reference frame.
	 *
	 * @return  the value.
	 */
	public NXprocess getSample_reference_frame();

	/**
	 * Details about the sample/specimen reference frame.
	 *
	 * @param sample_reference_frameGroup the sample_reference_frameGroup
	 */
	public void setSample_reference_frame(NXprocess sample_reference_frameGroup);

	/**
	 * Details about the detector reference frame.
	 *
	 * @return  the value.
	 */
	public NXprocess getDetector_reference_frame();

	/**
	 * Details about the detector reference frame.
	 *
	 * @param detector_reference_frameGroup the detector_reference_frameGroup
	 */
	public void setDetector_reference_frame(NXprocess detector_reference_frameGroup);

	/**
	 * Details about the gnomonic projection reference frame.
	 *
	 * @return  the value.
	 */
	public NXprocess getGnomonic_projection_reference_frame();

	/**
	 * Details about the gnomonic projection reference frame.
	 *
	 * @param gnomonic_projection_reference_frameGroup the gnomonic_projection_reference_frameGroup
	 */
	public void setGnomonic_projection_reference_frame(NXprocess gnomonic_projection_reference_frameGroup);

	/**
	 * Details about the definition of the pattern centre
	 * as a special point in the gnomonic projection reference frame.
	 *
	 * @return  the value.
	 */
	public NXprocess getPattern_centre();

	/**
	 * Details about the definition of the pattern centre
	 * as a special point in the gnomonic projection reference frame.
	 *
	 * @param pattern_centreGroup the pattern_centreGroup
	 */
	public void setPattern_centre(NXprocess pattern_centreGroup);

}
