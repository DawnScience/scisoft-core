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
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Base class method-specific for Electron Backscatter Diffraction (EBSD).
 * The general procedure of an EBSD experiment is as follows:
 * Users load the specimen, collect first a coarse image of the surface.
 * Next, they set an approximate value for the calibrated working distance
 * and tilt the stage into diffraction conditions.
 * Users then typically configure the microscope for collecting quality data.
 * The EBSD detector is pushed in (if retractable). Subsequently, they fine tune
 * the illumination and aberration corrector settings and select one or multiple ROIs
 * for the microscope to machine off automatically. They configure on-the-fly
 * indexing parameter and then typically start the measurement queue.
 * From this point onwards typically the microscope runs automatically.
 * Diffraction pattern get collected until the queue finishes or gets interrupted by
 * either errors or arrival at the end of the users' allocated time slot at the instrument.
 * Kikuchi pattern (EBSP) are usually indexed on-the-fly. These patterns are the raw data.
 * Once indexed, these patterns are often not stored.
 * Results are stored in files, which afterwards are typically copied
 * automatically or manually for archival purposes to certain storage
 * locations for further consumption. The result of such an EBSD
 * measurement/experiment is a set of usually proprietary or open files
 * from technology partners.
 * This :ref:`NXem_ebsd` base class is a proposal how to represent method-specific
 * data, metadata, and connections between these for the research field of
 * electron microscopy exemplified here for electron backscatter diffraction (EBSD).
 * The base class solves two key documentation issues within the EBSD community:
 * Firstly, an instance of NXem_ebsd (such as a NeXus/HDF5 file that is formatted
 * according to NXem_ebsd) stores the connection between the microscope session and
 * the key datasets which are considered typically results of the afore-mentioned
 * steps involved in an EBSD experiment.
 * Different groups in NXem_ebsd make connections to data artifacts which were collected
 * when working with electron microscopes via the NXem application definition.
 * Using a file which stores information according to the NXem application definition
 * has the benefit that it connects the sample, references to the sample processing,
 * the user operating the microscope, details about the microscope session,
 * and details about the acquisition and eventual indexing of Kikuchi patterns,
 * associated overview images, like secondary electron or backscattered electron
 * images of the region-of-interest probed, and many more (meta)data.
 * Secondly, NXem_ebsd connects and stores the conventions and reference frames
 * which were used and which are the key to a correct mathematical interpretation
 * of every experiment or simulation using EBSD.
 * Otherwise, results would be ripped out of their context like it is the current situation
 * with many traditional studies where EBSD data were indexed on-the-fly and shared
 * with the community only via sharing the strongly processed files with results in some
 * formatting but without communicating all conventions used or just relying on the assumptions
 * that colleagues likely know these conventions even though
 * multiple definitions are possible.
 * NXem_ebsd covers experiments with one-, two-dimensional, and so-called three-
 * dimensional EBSD datasets. The third dimension is either time (in the case of
 * quasi in-situ experiments) or space (in the case of serial-sectioning) experiments
 * where a combination of repetitive removal of material from the surface layer to measure
 * otherwise the same region-of-interest at different depth increments. Material removal
 * can be achieved with mechanical, electron, or ion polishing, using manual steps or
 * automated equipment like a robot system `S. Tsai et al. <https://doi.org/10.1063/5.0087945>`_.
 * Three-dimensional experiments require to follow a sequence of specimen, surface
 * preparation, and data collection steps. By virtue of design, these methods are destructive
 * either because of the necessary material removal or surface degradation due to e.g.
 * contamination or other electron-matter interaction.
 * For three-dimensional EBSD, multiple two-dimensional EBSD orientation mappings
 * are combined into one reconstructed stack via a computational workflow. Users collect
 * data for each serial sectioning step via an experiment. This assures that data for associated
 * microscope sessions and steps of data processing stay contextualized and connected.
 * Eventual tomography methods also use such a workflow because first diffraction
 * images are collected (e.g. with X-ray) and then these images are indexed to process
 * a 3D orientation mapping. Therefore, the here proposed base class can be a blueprint
 * also for future classes to embrace our colleagues from X-ray-based techniques be it 3DXRD or HEDM.
 * This concept is related to term `Electron Backscatter Diffraction`_ of the EMglossary standard.
 * .. _Electron Backscatter Diffraction: https://purls.helmholtz-metadaten.de/emg/EMG_00000019

 */
public class NXem_ebsdImpl extends NXprocessImpl implements NXem_ebsd {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_COORDINATE_SYSTEM,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS);

	public NXem_ebsdImpl() {
		super();
	}

	public NXem_ebsdImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXem_ebsd.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EM_EBSD;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXcoordinate_system getGnomonic_reference_frame() {
		// dataNodeName = NX_GNOMONIC_REFERENCE_FRAME
		return getChild("gnomonic_reference_frame", NXcoordinate_system.class);
	}

	@Override
	public void setGnomonic_reference_frame(NXcoordinate_system gnomonic_reference_frameGroup) {
		putChild("gnomonic_reference_frame", gnomonic_reference_frameGroup);
	}

	@Override
	public NXprocess getPattern_center() {
		// dataNodeName = NX_PATTERN_CENTER
		return getChild("pattern_center", NXprocess.class);
	}

	@Override
	public void setPattern_center(NXprocess pattern_centerGroup) {
		putChild("pattern_center", pattern_centerGroup);
	}

	@Override
	public NXprocess getMeasurement() {
		// dataNodeName = NX_MEASUREMENT
		return getChild("measurement", NXprocess.class);
	}

	@Override
	public void setMeasurement(NXprocess measurementGroup) {
		putChild("measurement", measurementGroup);
	}
	// Unprocessed group: source

	@Override
	public NXprocess getSimulation() {
		// dataNodeName = NX_SIMULATION
		return getChild("simulation", NXprocess.class);
	}

	@Override
	public void setSimulation(NXprocess simulationGroup) {
		putChild("simulation", simulationGroup);
	}
	// Unprocessed group: source

	@Override
	public NXprocess getCalibration() {
		// dataNodeName = NX_CALIBRATION
		return getChild("calibration", NXprocess.class);
	}

	@Override
	public void setCalibration(NXprocess calibrationGroup) {
		putChild("calibration", calibrationGroup);
	}
	// Unprocessed group: source

	@Override
	public NXprocess getIndexing() {
		// dataNodeName = NX_INDEXING
		return getChild("indexing", NXprocess.class);
	}

	@Override
	public void setIndexing(NXprocess indexingGroup) {
		putChild("indexing", indexingGroup);
	}
	// Unprocessed group: source
	// Unprocessed group: background_correction
	// Unprocessed group: binning
	// Unprocessed group: parameter
	// Unprocessed group: phaseID
	// Unprocessed group: rotation
	// Unprocessed group: roi

}
