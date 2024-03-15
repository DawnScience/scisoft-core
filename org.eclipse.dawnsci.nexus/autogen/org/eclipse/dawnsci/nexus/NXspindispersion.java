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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Subclass of NXelectronanalyser to describe the spin filters in photoemission
 * experiments.
 *
 */
public interface NXspindispersion extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_FIGURE_OF_MERIT = "figure_of_merit";
	public static final String NX_SHERMANN_FUNCTION = "shermann_function";
	public static final String NX_SCATTERING_ENERGY = "scattering_energy";
	public static final String NX_SCATTERING_ANGLE = "scattering_angle";
	public static final String NX_TARGET = "target";
	public static final String NX_TARGET_PREPARATION = "target_preparation";
	public static final String NX_TARGET_PREPARATION_DATE = "target_preparation_date";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Type of spin detector, VLEED, SPLEED, Mott, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getType();

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
	public IDataset getFigure_of_merit();

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
	public IDataset getShermann_function();

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
	public IDataset getScattering_energy();

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
	public IDataset getScattering_angle();

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
	public IDataset getTarget();

	/**
	 * Name of the target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param targetDataset the targetDataset
	 */
	public DataNode setTarget(IDataset targetDataset);

	/**
	 * Name of the target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTargetScalar();

	/**
	 * Name of the target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param target the target
	 */
	public DataNode setTargetScalar(String targetValue);

	/**
	 * Preparation procedure of the spin target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTarget_preparation();

	/**
	 * Preparation procedure of the spin target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param target_preparationDataset the target_preparationDataset
	 */
	public DataNode setTarget_preparation(IDataset target_preparationDataset);

	/**
	 * Preparation procedure of the spin target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTarget_preparationScalar();

	/**
	 * Preparation procedure of the spin target
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param target_preparation the target_preparation
	 */
	public DataNode setTarget_preparationScalar(String target_preparationValue);

	/**
	 * Date of last preparation of the spin target
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTarget_preparation_date();

	/**
	 * Date of last preparation of the spin target
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param target_preparation_dateDataset the target_preparation_dateDataset
	 */
	public DataNode setTarget_preparation_date(IDataset target_preparation_dateDataset);

	/**
	 * Date of last preparation of the spin target
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getTarget_preparation_dateScalar();

	/**
	 * Date of last preparation of the spin target
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param target_preparation_date the target_preparation_date
	 */
	public DataNode setTarget_preparation_dateScalar(Date target_preparation_dateValue);

	/**
	 * Specifies the position of the lens by pointing to the last transformation in the
	 * transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDepends_on();

	/**
	 * Specifies the position of the lens by pointing to the last transformation in the
	 * transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Specifies the position of the lens by pointing to the last transformation in the
	 * transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Specifies the position of the lens by pointing to the last transformation in the
	 * transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the deflector as a component in the instrument. Conventions from the
	 * NXtransformations base class are used. In principle, the McStas coordinate
	 * system is used. The first transformation has to point either to another
	 * component of the system or . (for pointing to the reference frame) to relate it
	 * relative to the experimental setup. Typically, the components of a system should
	 * all be related relative to each other and only one component should relate to
	 * the reference coordinate system.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


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
	public NXlens_em getLens_em();

	/**
	 * Individual lenses in the spin dispersive section
	 *
	 * @param lens_emGroup the lens_emGroup
	 */
	public void setLens_em(NXlens_em lens_emGroup);

	/**
	 * Get a NXlens_em node by name:
	 * <ul>
	 * <li>
	 * Individual lenses in the spin dispersive section</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public NXlens_em getLens_em(String name);

	/**
	 * Set a NXlens_em node by name:
	 * <ul>
	 * <li>
	 * Individual lenses in the spin dispersive section</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param lens_em the value to set
	 */
	public void setLens_em(String name, NXlens_em lens_em);

	/**
	 * Get all NXlens_em nodes:
	 * <ul>
	 * <li>
	 * Individual lenses in the spin dispersive section</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public Map<String, NXlens_em> getAllLens_em();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual lenses in the spin dispersive section</li>
	 * </ul>
	 *
	 * @param lens_em the child nodes to add
	 */

	public void setAllLens_em(Map<String, NXlens_em> lens_em);


}
