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
 * Base class for reporting a set of images representing specializations of NXdata.
 * The most commonly used scanning methods are supported. That is one-,
 * two-, three-dimensional ROIs discretized using regular Euclidean tilings.
 * Colloquially, an image is understood as a discretized representation of intensity distribution
 * detected or simulated for some ROI. When discretized with regular Euclidean tilings, the terms
 * pixel and voxel identify the smallest discretization unit. In this case, pixel and voxel are polygonal
 * or polyhedral unit cells respectively of the underlying tiling of the ROI within the reference space.
 * For all other tilings e.g. non-equispaced, the shape and size of pixel and voxel differs. Using the term
 * image point is eventually more appropriate when working with such tilings.
 * Therefore, all docstrings in this base class refer to points. Points are considered
 * exact synonyms for pixel and voxel, which are terms used for regular tilings.
 * Point coordinates identify the location of the barycenter.
 * For images in reciprocal space in practice, complex numbers are encoded via some formatted pair of real values.
 * Typically, fast algorithms for computing Fourier transformations (FFT) are used to encode images in reciprocal
 * (frequency) space. FFT libraries are used for implementing the key functionalities of these mathematical operations.
 * Different libraries use different representations and encoding of the images.
 * Details can be found in the respective sections of the typical FFT libraries documentations:
 * * `FFTW by M. Frigo and S. G. Johnson <https://www.fftw.org/fftw3_doc/Tutorial.html#Tutorial>`_
 * * `Intel MKL by the Intel Co. <https://www.intel.com/content/www/us/en/docs/onemkl/developer-reference-c/2024-2/fourier-transform-functions.html>`_
 * * `cuFFT by the NVidia Co. <https://docs.nvidia.com/cuda/cufft/index.html>`_
 * * `NFFT by the TU Chemnitz group <https://www-user.tu-chemnitz.de/~potts/nfft/>`_ for non-equispaced computations
 * Users are strongly advised to inspect carefully which specific conventions their library uses
 * to enable storing and modifying the implementation of their code such that the serialized
 * representations as they are detailed here for NeXus match.
 * It is often the case that several images are combined using processing. In this case,
 * the number of images which are combined into collections is not necessarily the same
 * for each collection. The NXimage base class addresses this logical distinction
 * through the notation of indices_image and indices_group concepts.
 * That is indices_image are always counting from offset in increments of one
 * as each image is its own entity. By contrast, a group may contain no, or several images.
 * Consequently, indices_group are not required to be contiguous.
 * Classically, images depict objects in real space. Such usage of NXimage essentially is equivalent to
 * storing pictures. For this purpose the image_1d, image_2d, or image_3d NXdata instances respectively
 * should be used such that all their axes axis_i, axis_j, axis_k are constrained to NeXus Unit Category NX_LENGTH.
 * Imaging modes in electron microscopy are typically more versatile, specifically for use cases
 * in scanning transmission electron microscopy, so-called 4DSTEM. In this case, one two-dimensional
 * diffraction image is taken for each point that gets scanned in real space. Consequently,
 * image_3d and image_4d NXdata instances should be used for these cases with axis_k and axis_m
 * respectively of NeXus Unit Category NX_LENGTH and axis_i and axis_j respectively of
 * NeXus Unit Category NX_WAVENUMBER or NX_UNITLESS.

 */
public class NXimageImpl extends NXobjectImpl implements NXimage {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA);

	public NXimageImpl() {
		super();
	}

	public NXimageImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXimage.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_IMAGE;
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
	// Unprocessed group: input
	// Unprocessed group:

	@Override
	public NXdata getImage_1d() {
		// dataNodeName = NX_IMAGE_1D
		return getChild("image_1d", NXdata.class);
	}

	@Override
	public void setImage_1d(NXdata image_1dGroup) {
		putChild("image_1d", image_1dGroup);
	}

	@Override
	public NXdata getImage_2d() {
		// dataNodeName = NX_IMAGE_2D
		return getChild("image_2d", NXdata.class);
	}

	@Override
	public void setImage_2d(NXdata image_2dGroup) {
		putChild("image_2d", image_2dGroup);
	}

	@Override
	public NXdata getImage_3d() {
		// dataNodeName = NX_IMAGE_3D
		return getChild("image_3d", NXdata.class);
	}

	@Override
	public void setImage_3d(NXdata image_3dGroup) {
		putChild("image_3d", image_3dGroup);
	}

	@Override
	public NXdata getImage_4d() {
		// dataNodeName = NX_IMAGE_4D
		return getChild("image_4d", NXdata.class);
	}

	@Override
	public void setImage_4d(NXdata image_4dGroup) {
		putChild("image_4d", image_4dGroup);
	}

	@Override
	public NXdata getStack_1d() {
		// dataNodeName = NX_STACK_1D
		return getChild("stack_1d", NXdata.class);
	}

	@Override
	public void setStack_1d(NXdata stack_1dGroup) {
		putChild("stack_1d", stack_1dGroup);
	}

	@Override
	public NXdata getStack_2d() {
		// dataNodeName = NX_STACK_2D
		return getChild("stack_2d", NXdata.class);
	}

	@Override
	public void setStack_2d(NXdata stack_2dGroup) {
		putChild("stack_2d", stack_2dGroup);
	}

	@Override
	public NXdata getStack_3d() {
		// dataNodeName = NX_STACK_3D
		return getChild("stack_3d", NXdata.class);
	}

	@Override
	public void setStack_3d(NXdata stack_3dGroup) {
		putChild("stack_3d", stack_3dGroup);
	}

}
