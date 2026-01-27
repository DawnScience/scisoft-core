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
 * Base class for a corrector reducing (spherical) aberrations of an electron optical setup.
 * Different technology partners use different conventions and
 * models for quantifying the aberration coefficients.
 * Aberration correction components are especially important for (scanning)
 * transmission electron microscopy. Composed of multiple lenses and multipole stigmators,
 * their technical details are specific for the technology partner as well as
 * the microscope and instrument. Most technical details are proprietary knowledge.
 * If one component corrects for multiple types of aberrations (like it is the case
 * reported here `CEOS <https://www.ceos-gmbh.de/en/research/electrostat>`_) follow this
 * design when using corrector and monochromator in an application definition:
 * * Use :ref:`NXcorrector_cs` for spherical aberration
 * * Use :ref:`NXmonochromator` for energy filtering or chromatic aberration
 * * Use the group corrector_ax in :ref:`NXem` for axial astigmatism aberration
 * Although this base class currently provides concepts that are foremost used in
 * the field of electron microscopy using this base class is not restricted to this
 * research field. NXcorrector_cs can also serve as a container to detail, in
 * combination with :ref:`NXaberration`, about measured aberrations in classical optics.
 * In optics, though, the difference is that the design of the :ref:NXoptical_lens`
 * itself (e.g., using aspheric lenses or combinations of lenses) enables to
 * reduce spherical aberrations.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_img</b>
 * Number of images taken, at least one.</li></ul></p>
 *
 */
public interface NXcorrector_cs extends NXcomponent {

	/**
	 * Was the corrector used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getApplied();

	/**
	 * Was the corrector used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param appliedDataset the appliedDataset
	 */
	public DataNode setApplied(IDataset appliedDataset);

	/**
	 * Was the corrector used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getAppliedScalar();

	/**
	 * Was the corrector used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param applied the applied
	 */
	public DataNode setAppliedScalar(Boolean appliedValue);

	/**
	 * Specific information about the alignment procedure. This is a process during which
	 * the corrector is configured to enable calibrated usage of the instrument.
	 * This :ref:`NXprocess` group should also be used when one describes in a computer
	 * simulation the specific details about the modeled or assumed aberrations.
	 *
	 * @return  the value.
	 */
	public NXprocess getTableauid();

	/**
	 * Specific information about the alignment procedure. This is a process during which
	 * the corrector is configured to enable calibrated usage of the instrument.
	 * This :ref:`NXprocess` group should also be used when one describes in a computer
	 * simulation the specific details about the modeled or assumed aberrations.
	 *
	 * @param tableauidGroup the tableauidGroup
	 */
	public void setTableauid(NXprocess tableauidGroup);
	// Unprocessed group:imageID
	// Unprocessed group:c_1
	// Unprocessed group:a_1
	// Unprocessed group:b_2
	// Unprocessed group:a_2
	// Unprocessed group:c_3
	// Unprocessed group:s_3
	// Unprocessed group:a_3
	// Unprocessed group:b_4
	// Unprocessed group:d_4
	// Unprocessed group:a_4
	// Unprocessed group:c_5
	// Unprocessed group:s_5
	// Unprocessed group:r_5
	// Unprocessed group:a_6
	// Unprocessed group:c_1_0
	// Unprocessed group:c_1_2_a
	// Unprocessed group:c_1_2_b
	// Unprocessed group:c_2_1_a
	// Unprocessed group:c_2_1_b
	// Unprocessed group:c_2_3_a
	// Unprocessed group:c_2_3_b
	// Unprocessed group:c_3_0
	// Unprocessed group:c_3_2_a
	// Unprocessed group:c_3_2_b
	// Unprocessed group:c_3_4_a
	// Unprocessed group:c_3_4_b
	// Unprocessed group:c_4_1_a
	// Unprocessed group:c_4_1_b
	// Unprocessed group:c_4_3_a
	// Unprocessed group:c_4_3_b
	// Unprocessed group:c_4_5_a
	// Unprocessed group:c_4_5_b
	// Unprocessed group:c_5_0
	// Unprocessed group:c_5_2_a
	// Unprocessed group:c_5_2_b
	// Unprocessed group:c_5_4_a
	// Unprocessed group:c_5_4_b
	// Unprocessed group:c_5_6_a
	// Unprocessed group:c_5_6_b

	/**
	 *
	 * @return  the value.
	 */
	public NXelectromagnetic_lens getElectromagnetic_lens();

	/**
	 *
	 * @param electromagnetic_lensGroup the electromagnetic_lensGroup
	 */
	public void setElectromagnetic_lens(NXelectromagnetic_lens electromagnetic_lensGroup);

	/**
	 * Get a NXelectromagnetic_lens node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXelectromagnetic_lens for that node.
	 */
	public NXelectromagnetic_lens getElectromagnetic_lens(String name);

	/**
	 * Set a NXelectromagnetic_lens node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param electromagnetic_lens the value to set
	 */
	public void setElectromagnetic_lens(String name, NXelectromagnetic_lens electromagnetic_lens);

	/**
	 * Get all NXelectromagnetic_lens nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXelectromagnetic_lens for that node.
	 */
	public Map<String, NXelectromagnetic_lens> getAllElectromagnetic_lens();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param electromagnetic_lens the child nodes to add
	 */

	public void setAllElectromagnetic_lens(Map<String, NXelectromagnetic_lens> electromagnetic_lens);


	/**
	 *
	 * @return  the value.
	 */
	public NXoptical_lens getOptical_lens();

	/**
	 *
	 * @param optical_lensGroup the optical_lensGroup
	 */
	public void setOptical_lens(NXoptical_lens optical_lensGroup);

	/**
	 * Get a NXoptical_lens node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXoptical_lens for that node.
	 */
	public NXoptical_lens getOptical_lens(String name);

	/**
	 * Set a NXoptical_lens node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param optical_lens the value to set
	 */
	public void setOptical_lens(String name, NXoptical_lens optical_lens);

	/**
	 * Get all NXoptical_lens nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXoptical_lens for that node.
	 */
	public Map<String, NXoptical_lens> getAllOptical_lens();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param optical_lens the child nodes to add
	 */

	public void setAllOptical_lens(Map<String, NXoptical_lens> optical_lens);


	/**
	 *
	 * @return  the value.
	 */
	public NXaperture getAperture();

	/**
	 *
	 * @param apertureGroup the apertureGroup
	 */
	public void setAperture(NXaperture apertureGroup);

	/**
	 * Get a NXaperture node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXaperture for that node.
	 */
	public NXaperture getAperture(String name);

	/**
	 * Set a NXaperture node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param aperture the value to set
	 */
	public void setAperture(String name, NXaperture aperture);

	/**
	 * Get all NXaperture nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXaperture for that node.
	 */
	public Map<String, NXaperture> getAllAperture();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param aperture the child nodes to add
	 */

	public void setAllAperture(Map<String, NXaperture> aperture);


	/**
	 *
	 * @return  the value.
	 */
	public NXdeflector getDeflector();

	/**
	 *
	 * @param deflectorGroup the deflectorGroup
	 */
	public void setDeflector(NXdeflector deflectorGroup);

	/**
	 * Get a NXdeflector node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public NXdeflector getDeflector(String name);

	/**
	 * Set a NXdeflector node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param deflector the value to set
	 */
	public void setDeflector(String name, NXdeflector deflector);

	/**
	 * Get all NXdeflector nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public Map<String, NXdeflector> getAllDeflector();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param deflector the child nodes to add
	 */

	public void setAllDeflector(Map<String, NXdeflector> deflector);


}
