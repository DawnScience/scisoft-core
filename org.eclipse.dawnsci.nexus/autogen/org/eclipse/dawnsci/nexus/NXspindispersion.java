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
 * Class to describe spin filters in photoemission experiments.
 *
 */
public interface NXspindispersion extends NXcomponent {

	public static final String NX_TYPE = "type";
	public static final String NX_FIGURE_OF_MERIT = "figure_of_merit";
	public static final String NX_SHERMANN_FUNCTION = "shermann_function";
	public static final String NX_SCATTERING_ENERGY = "scattering_energy";
	public static final String NX_SCATTERING_ANGLE = "scattering_angle";
	public static final String NX_SCATTERING_TARGET = "scattering_target";
	/**
	 * Type of spin detector, VLEED, SPLEED, Mott, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Type of spin detector, VLEED, SPLEED, Mott, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type of spin detector, VLEED, SPLEED, Mott, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type of spin detector, VLEED, SPLEED, Mott, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Figure of merit of the spin detector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFigure_of_merit();

	/**
	 * Figure of merit of the spin detector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param figure_of_meritDataset the figure_of_meritDataset
	 */
	public DataNode setFigure_of_merit(IDataset figure_of_meritDataset);

	/**
	 * Figure of merit of the spin detector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getFigure_of_meritScalar();

	/**
	 * Figure of merit of the spin detector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param figure_of_merit the figure_of_merit
	 */
	public DataNode setFigure_of_meritScalar(Double figure_of_meritValue);

	/**
	 * Effective Shermann function, calibrated spin selectivity factor
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getShermann_function();

	/**
	 * Effective Shermann function, calibrated spin selectivity factor
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param shermann_functionDataset the shermann_functionDataset
	 */
	public DataNode setShermann_function(IDataset shermann_functionDataset);

	/**
	 * Effective Shermann function, calibrated spin selectivity factor
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getShermann_functionScalar();

	/**
	 * Effective Shermann function, calibrated spin selectivity factor
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param shermann_function the shermann_function
	 */
	public DataNode setShermann_functionScalar(Double shermann_functionValue);

	/**
	 * Energy of the spin-selective scattering
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getScattering_energy();

	/**
	 * Energy of the spin-selective scattering
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param scattering_energyDataset the scattering_energyDataset
	 */
	public DataNode setScattering_energy(IDataset scattering_energyDataset);

	/**
	 * Energy of the spin-selective scattering
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getScattering_energyScalar();

	/**
	 * Energy of the spin-selective scattering
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param scattering_energy the scattering_energy
	 */
	public DataNode setScattering_energyScalar(Double scattering_energyValue);

	/**
	 * Angle of the spin-selective scattering
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getScattering_angle();

	/**
	 * Angle of the spin-selective scattering
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param scattering_angleDataset the scattering_angleDataset
	 */
	public DataNode setScattering_angle(IDataset scattering_angleDataset);

	/**
	 * Angle of the spin-selective scattering
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getScattering_angleScalar();

	/**
	 * Angle of the spin-selective scattering
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param scattering_angle the scattering_angle
	 */
	public DataNode setScattering_angleScalar(Double scattering_angleValue);

	/**
	 * Name of the target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getScattering_target();

	/**
	 * Name of the target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param scattering_targetDataset the scattering_targetDataset
	 */
	public DataNode setScattering_target(IDataset scattering_targetDataset);

	/**
	 * Name of the target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getScattering_targetScalar();

	/**
	 * Name of the target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param scattering_target the scattering_target
	 */
	public DataNode setScattering_targetScalar(String scattering_targetValue);

	/**
	 * A set of activities that occurred to the ``scattering_target`` prior to/during the.
	 * experiment. For example, this group can be used to describe the preparation of the
	 * ``scattering_target``.
	 *
	 * @return  the value.
	 */
	public NXhistory getScattering_target_history();

	/**
	 * A set of activities that occurred to the ``scattering_target`` prior to/during the.
	 * experiment. For example, this group can be used to describe the preparation of the
	 * ``scattering_target``.
	 *
	 * @param scattering_target_historyGroup the scattering_target_historyGroup
	 */
	public void setScattering_target_history(NXhistory scattering_target_historyGroup);
	// Unprocessed group:preparation

	/**
	 * Deflectors in the spin dispersive section
	 *
	 * @return  the value.
	 */
	public NXdeflector getDeflector();

	/**
	 * Deflectors in the spin dispersive section
	 *
	 * @param deflectorGroup the deflectorGroup
	 */
	public void setDeflector(NXdeflector deflectorGroup);

	/**
	 * Get a NXdeflector node by name:
	 * <ul>
	 * <li>
	 * Deflectors in the spin dispersive section</li>
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
	 * Deflectors in the spin dispersive section</li>
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
	 * Deflectors in the spin dispersive section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public Map<String, NXdeflector> getAllDeflector();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Deflectors in the spin dispersive section</li>
	 * </ul>
	 *
	 * @param deflector the child nodes to add
	 */

	public void setAllDeflector(Map<String, NXdeflector> deflector);


	/**
	 * Individual lenses in the spin dispersive section
	 *
	 * @return  the value.
	 */
	public NXelectromagnetic_lens getElectromagnetic_lens();

	/**
	 * Individual lenses in the spin dispersive section
	 *
	 * @param electromagnetic_lensGroup the electromagnetic_lensGroup
	 */
	public void setElectromagnetic_lens(NXelectromagnetic_lens electromagnetic_lensGroup);

	/**
	 * Get a NXelectromagnetic_lens node by name:
	 * <ul>
	 * <li>
	 * Individual lenses in the spin dispersive section</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXelectromagnetic_lens for that node.
	 */
	public NXelectromagnetic_lens getElectromagnetic_lens(String name);

	/**
	 * Set a NXelectromagnetic_lens node by name:
	 * <ul>
	 * <li>
	 * Individual lenses in the spin dispersive section</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param electromagnetic_lens the value to set
	 */
	public void setElectromagnetic_lens(String name, NXelectromagnetic_lens electromagnetic_lens);

	/**
	 * Get all NXelectromagnetic_lens nodes:
	 * <ul>
	 * <li>
	 * Individual lenses in the spin dispersive section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXelectromagnetic_lens for that node.
	 */
	public Map<String, NXelectromagnetic_lens> getAllElectromagnetic_lens();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual lenses in the spin dispersive section</li>
	 * </ul>
	 *
	 * @param electromagnetic_lens the child nodes to add
	 */

	public void setAllElectromagnetic_lens(Map<String, NXelectromagnetic_lens> electromagnetic_lens);


}
