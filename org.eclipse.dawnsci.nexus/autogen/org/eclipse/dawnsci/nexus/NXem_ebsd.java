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
import org.eclipse.january.dataset.Dataset;

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
 * <p><b>Symbols:</b><ul>
 * <li><b>n_op</b>
 * Number of arguments per orientation for given parameterization.</li>
 * <li><b>n_sc</b>
 * Number of scan points.</li>
 * <li><b>n_z</b>
 * Number of pixel along the slowest changing dimension for a rediscretized,
 * i.e. standardized default plot orientation mapping.</li>
 * <li><b>n_y</b>
 * Number of pixel along slow changing dimension for a rediscretized i.e.
 * standardized default plot orientation mapping.</li>
 * <li><b>n_x</b>
 * Number of pixel along fast changing dimension for a rediscretized i.e.
 * standardized default plot orientation mapping.</li>
 * <li><b>n_solutions</b>
 * Number of phase solutions</li>
 * <li><b>n_hkl</b>
 * Number of reflectors (Miller crystallographic plane triplets).</li></ul></p>
 *
 */
public interface NXem_ebsd extends NXprocess {

	/**
	 * Details about the gnomonic (projection) reference frame.
	 * It is assumed that the configuration is inspected by looking towards the sample surface.
	 * If a detector is involved, it is assumed that the configuration is inspected from a position
	 * that is located behind this detector.
	 * If any of these assumptions are not met, the user is required to explicitly state this.
	 * Reference `<https://doi.org/10.1016/j.matchar.2016.04.008>`_ suggests to label the
	 * base vectors of this coordinate system as :math:`X_g, Y_g, Z_g`.
	 *
	 * @return  the value.
	 */
	public NXcoordinate_system getGnomonic_reference_frame();

	/**
	 * Details about the gnomonic (projection) reference frame.
	 * It is assumed that the configuration is inspected by looking towards the sample surface.
	 * If a detector is involved, it is assumed that the configuration is inspected from a position
	 * that is located behind this detector.
	 * If any of these assumptions are not met, the user is required to explicitly state this.
	 * Reference `<https://doi.org/10.1016/j.matchar.2016.04.008>`_ suggests to label the
	 * base vectors of this coordinate system as :math:`X_g, Y_g, Z_g`.
	 *
	 * @param gnomonic_reference_frameGroup the gnomonic_reference_frameGroup
	 */
	public void setGnomonic_reference_frame(NXcoordinate_system gnomonic_reference_frameGroup);

	/**
	 * Details about the definition of the pattern center as a special point in the
	 * gnomonic_reference_frame.
	 * Typically the gnomonic space is embedded in the detector space.
	 * Specifically, the XgYg plane is defined such that it is laying inside the
	 * XdYd plane (of the detector reference frame).
	 * When the normalization direction is the same as e.g. the detector x-axis direction
	 * one effectively normalizes in fractions of the width of the detector.
	 * The issue with terms like width and height, though, is that these become degenerated
	 * if the detector region-of-interest is square-shaped. This is why instead of referring to
	 * width and height it is better to state explicitly which direction is considered positive
	 * when measuring distances.
	 * For the concepts used to specify the boundary_convention it is assumed that the
	 * region-of-interest is defined by a rectangle, referring to the direction of outer-unit
	 * normals to the respective edges of this rectangle.
	 *
	 * @return  the value.
	 */
	public NXprocess getPattern_center();

	/**
	 * Details about the definition of the pattern center as a special point in the
	 * gnomonic_reference_frame.
	 * Typically the gnomonic space is embedded in the detector space.
	 * Specifically, the XgYg plane is defined such that it is laying inside the
	 * XdYd plane (of the detector reference frame).
	 * When the normalization direction is the same as e.g. the detector x-axis direction
	 * one effectively normalizes in fractions of the width of the detector.
	 * The issue with terms like width and height, though, is that these become degenerated
	 * if the detector region-of-interest is square-shaped. This is why instead of referring to
	 * width and height it is better to state explicitly which direction is considered positive
	 * when measuring distances.
	 * For the concepts used to specify the boundary_convention it is assumed that the
	 * region-of-interest is defined by a rectangle, referring to the direction of outer-unit
	 * normals to the respective edges of this rectangle.
	 *
	 * @param pattern_centerGroup the pattern_centerGroup
	 */
	public void setPattern_center(NXprocess pattern_centerGroup);

	/**
	 * This group documents relevant details about the conditions and the
	 * tools for measuring diffraction patterns with an electron microscope.
	 * The most frequently collected EBSD data are captured for rectangular
	 * regions-of-interest using a discretization into square or hexagon tiles.
	 *
	 * @return  the value.
	 */
	public NXprocess getMeasurement();

	/**
	 * This group documents relevant details about the conditions and the
	 * tools for measuring diffraction patterns with an electron microscope.
	 * The most frequently collected EBSD data are captured for rectangular
	 * regions-of-interest using a discretization into square or hexagon tiles.
	 *
	 * @param measurementGroup the measurementGroup
	 */
	public void setMeasurement(NXprocess measurementGroup);
	// Unprocessed group:source

	/**
	 * This group documents relevant details about the conditions and the tools
	 * used for simulating diffraction patterns with some physical model.
	 * This group should be used if (e.g. instead of a measurement) the patterns
	 * were simulated (possibly awaiting indexing).
	 * In many practical cases where patterns are analyzed on-the-fly and dictionary
	 * indexing strategies used, so-called master pattern(s) are used to compare
	 * measured or simulated patterns with the master patterns.
	 *
	 * @return  the value.
	 */
	public NXprocess getSimulation();

	/**
	 * This group documents relevant details about the conditions and the tools
	 * used for simulating diffraction patterns with some physical model.
	 * This group should be used if (e.g. instead of a measurement) the patterns
	 * were simulated (possibly awaiting indexing).
	 * In many practical cases where patterns are analyzed on-the-fly and dictionary
	 * indexing strategies used, so-called master pattern(s) are used to compare
	 * measured or simulated patterns with the master patterns.
	 *
	 * @param simulationGroup the simulationGroup
	 */
	public void setSimulation(NXprocess simulationGroup);
	// Unprocessed group:source

	/**
	 * The EBSD system, including components like the electron gun, pole-piece,
	 * stage tilt, EBSD detector, and the gnomonic projection have to be
	 * calibrated to achieve reliable, precise, and accurate scientific results.
	 * Specifically, the gnomonic projection has to be calibrated.
	 * Typically, standard specimens made from silicon or quartz crystals
	 * in specific orientations are used for this purpose.
	 * Considering that a system used is already calibrated well-enough is much
	 * more frequently the case in practice than that users perform the calibration
	 * themselves (with above-mentioned standard specimens).
	 * In the first case, the user assumes that the principle geometry of the
	 * hardware components and the settings in the control and EBSD pattern
	 * acquisition software has been calibrated already. Consequently, users pick from
	 * an existent library of phase candidates, i.e. :ref:`NXunit_cell` instances.
	 * Examples are reflector models as stored in CRY files (HKL/Channel 5/Flamenco).
	 * In the second case, users calibrate the system during the session
	 * using standards (silicon, quartz, or other common specimens).
	 * There is usually one person in each lab responsible for doing such
	 * calibrations. Often this person or technician is also in charge of
	 * configuring the graphical user interface and software with which most
	 * users control and perform their analyses.
	 * For EBSD this has key implications: Taking TSL OIM/EDAX as an example,
	 * the conventions how orientations are stored is affected by how the
	 * reference frames are configured and how this setup in the GUI.
	 * Unfortunately, these pieces of information are not necessarily stored
	 * in the results files. In effect, key conventions become disconnected
	 * from the data so it remains the users' obligation to remember these
	 * settings or write these down in a lab notebook. Otherwise, these metadata
	 * get lost. All these issues are a motivation and problem which :ref:`NXem_ebsd`
	 * solves in that all conventions can be specified explicitly.
	 *
	 * @return  the value.
	 */
	public NXprocess getCalibration();

	/**
	 * The EBSD system, including components like the electron gun, pole-piece,
	 * stage tilt, EBSD detector, and the gnomonic projection have to be
	 * calibrated to achieve reliable, precise, and accurate scientific results.
	 * Specifically, the gnomonic projection has to be calibrated.
	 * Typically, standard specimens made from silicon or quartz crystals
	 * in specific orientations are used for this purpose.
	 * Considering that a system used is already calibrated well-enough is much
	 * more frequently the case in practice than that users perform the calibration
	 * themselves (with above-mentioned standard specimens).
	 * In the first case, the user assumes that the principle geometry of the
	 * hardware components and the settings in the control and EBSD pattern
	 * acquisition software has been calibrated already. Consequently, users pick from
	 * an existent library of phase candidates, i.e. :ref:`NXunit_cell` instances.
	 * Examples are reflector models as stored in CRY files (HKL/Channel 5/Flamenco).
	 * In the second case, users calibrate the system during the session
	 * using standards (silicon, quartz, or other common specimens).
	 * There is usually one person in each lab responsible for doing such
	 * calibrations. Often this person or technician is also in charge of
	 * configuring the graphical user interface and software with which most
	 * users control and perform their analyses.
	 * For EBSD this has key implications: Taking TSL OIM/EDAX as an example,
	 * the conventions how orientations are stored is affected by how the
	 * reference frames are configured and how this setup in the GUI.
	 * Unfortunately, these pieces of information are not necessarily stored
	 * in the results files. In effect, key conventions become disconnected
	 * from the data so it remains the users' obligation to remember these
	 * settings or write these down in a lab notebook. Otherwise, these metadata
	 * get lost. All these issues are a motivation and problem which :ref:`NXem_ebsd`
	 * solves in that all conventions can be specified explicitly.
	 *
	 * @param calibrationGroup the calibrationGroup
	 */
	public void setCalibration(NXprocess calibrationGroup);
	// Unprocessed group:source

	/**
	 * Indexing is a data processing step performed either after or while (aka on-the-fly)
	 * the beam scans the specimen. The resulting method is also
	 * known as orientation imaging microscopy (OIM).
	 * Different algorithms can be used to index EBSP. Common to them is the
	 * computational step where simulated or theoretically assumed patterns
	 * are compared with the measured ones. These latter patterns are referred
	 * to via the measurement or simulation groups of this base class respectively.
	 * Quality descriptors are defined based on which an indexing algorithm
	 * yields a quantitative measure of how similar measured and reference
	 * patterns are, and thus if no, one, or multiple so-called solutions were found.
	 * Assumed or simulated patterns are simulated using kinematical or dynamical
	 * theory of electron diffraction delivering master patterns.
	 * The Hough transform, one of the most frequently used traditional method for indexing
	 * EBSP is essentially a discretized Radon transform (for details see `M. van Ginkel et al. <https://www.semanticscholar.org/paper/A-short-introduction-to-the-Radon-and-Hough-and-how-Ginkel/fb6226f606cad489a15e38ed961c419037ccc858>`_). Recently, dictionary-based and artificial intelligence-based methods
	 * find more widespread usage for indexing.
	 *
	 * @return  the value.
	 */
	public NXprocess getIndexing();

	/**
	 * Indexing is a data processing step performed either after or while (aka on-the-fly)
	 * the beam scans the specimen. The resulting method is also
	 * known as orientation imaging microscopy (OIM).
	 * Different algorithms can be used to index EBSP. Common to them is the
	 * computational step where simulated or theoretically assumed patterns
	 * are compared with the measured ones. These latter patterns are referred
	 * to via the measurement or simulation groups of this base class respectively.
	 * Quality descriptors are defined based on which an indexing algorithm
	 * yields a quantitative measure of how similar measured and reference
	 * patterns are, and thus if no, one, or multiple so-called solutions were found.
	 * Assumed or simulated patterns are simulated using kinematical or dynamical
	 * theory of electron diffraction delivering master patterns.
	 * The Hough transform, one of the most frequently used traditional method for indexing
	 * EBSP is essentially a discretized Radon transform (for details see `M. van Ginkel et al. <https://www.semanticscholar.org/paper/A-short-introduction-to-the-Radon-and-Hough-and-how-Ginkel/fb6226f606cad489a15e38ed961c419037ccc858>`_). Recently, dictionary-based and artificial intelligence-based methods
	 * find more widespread usage for indexing.
	 *
	 * @param indexingGroup the indexingGroup
	 */
	public void setIndexing(NXprocess indexingGroup);
	// Unprocessed group:source
	// Unprocessed group:background_correction
	// Unprocessed group:binning
	// Unprocessed group:parameter
	// Unprocessed group:phaseID
	// Unprocessed group:rotation
	// Unprocessed group:roi

}
