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

/**
 * Extension of NXpositioner to include fields to describe the use of manipulators
 * in photoemission experiments.
 *
 */
public interface NXmanipulator extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_TYPE = "type";
	public static final String NX_CRYOCOOLANT = "cryocoolant";
	public static final String NX_CRYOSTAT_TEMPERATURE = "cryostat_temperature";
	public static final String NX_HEATER_POWER = "heater_power";
	public static final String NX_SAMPLE_TEMPERATURE = "sample_temperature";
	public static final String NX_DRAIN_CURRENT = "drain_current";
	public static final String NX_SAMPLE_BIAS = "sample_bias";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Name of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getName();

	/**
	 * Name of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Name of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Name of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * A description of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDescription();

	/**
	 * A description of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * A description of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * A description of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Type of manipulator, Hexapod, Rod, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getType();

	/**
	 * Type of manipulator, Hexapod, Rod, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type of manipulator, Hexapod, Rod, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type of manipulator, Hexapod, Rod, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Is cryocoolant flowing through the manipulator?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCryocoolant();

	/**
	 * Is cryocoolant flowing through the manipulator?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param cryocoolantDataset the cryocoolantDataset
	 */
	public DataNode setCryocoolant(IDataset cryocoolantDataset);

	/**
	 * Is cryocoolant flowing through the manipulator?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getCryocoolantScalar();

	/**
	 * Is cryocoolant flowing through the manipulator?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param cryocoolant the cryocoolant
	 */
	public DataNode setCryocoolantScalar(Boolean cryocoolantValue);

	/**
	 * Temperature of the cryostat (coldest point)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCryostat_temperature();

	/**
	 * Temperature of the cryostat (coldest point)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @param cryostat_temperatureDataset the cryostat_temperatureDataset
	 */
	public DataNode setCryostat_temperature(IDataset cryostat_temperatureDataset);

	/**
	 * Temperature of the cryostat (coldest point)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCryostat_temperatureScalar();

	/**
	 * Temperature of the cryostat (coldest point)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @param cryostat_temperature the cryostat_temperature
	 */
	public DataNode setCryostat_temperatureScalar(Double cryostat_temperatureValue);

	/**
	 * Power in the heater for temperature control.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_POWER
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getHeater_power();

	/**
	 * Power in the heater for temperature control.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_POWER
	 * </p>
	 *
	 * @param heater_powerDataset the heater_powerDataset
	 */
	public DataNode setHeater_power(IDataset heater_powerDataset);

	/**
	 * Power in the heater for temperature control.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_POWER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getHeater_powerScalar();

	/**
	 * Power in the heater for temperature control.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_POWER
	 * </p>
	 *
	 * @param heater_power the heater_power
	 */
	public DataNode setHeater_powerScalar(Double heater_powerValue);

	/**
	 * Temperature at the closest point to the sample. This field may also be found in
	 * NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSample_temperature();

	/**
	 * Temperature at the closest point to the sample. This field may also be found in
	 * NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @param sample_temperatureDataset the sample_temperatureDataset
	 */
	public DataNode setSample_temperature(IDataset sample_temperatureDataset);

	/**
	 * Temperature at the closest point to the sample. This field may also be found in
	 * NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSample_temperatureScalar();

	/**
	 * Temperature at the closest point to the sample. This field may also be found in
	 * NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @param sample_temperature the sample_temperature
	 */
	public DataNode setSample_temperatureScalar(Double sample_temperatureValue);

	/**
	 * Current to neutralize the photoemission current. This field may also be found in
	 * NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDrain_current();

	/**
	 * Current to neutralize the photoemission current. This field may also be found in
	 * NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param drain_currentDataset the drain_currentDataset
	 */
	public DataNode setDrain_current(IDataset drain_currentDataset);

	/**
	 * Current to neutralize the photoemission current. This field may also be found in
	 * NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDrain_currentScalar();

	/**
	 * Current to neutralize the photoemission current. This field may also be found in
	 * NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param drain_current the drain_current
	 */
	public DataNode setDrain_currentScalar(Double drain_currentValue);

	/**
	 * Possible bias of the sample with trespect to analyser ground. This field may
	 * also be found in NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSample_bias();

	/**
	 * Possible bias of the sample with trespect to analyser ground. This field may
	 * also be found in NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param sample_biasDataset the sample_biasDataset
	 */
	public DataNode setSample_bias(IDataset sample_biasDataset);

	/**
	 * Possible bias of the sample with trespect to analyser ground. This field may
	 * also be found in NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSample_biasScalar();

	/**
	 * Possible bias of the sample with trespect to analyser ground. This field may
	 * also be found in NXsample if present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param sample_bias the sample_bias
	 */
	public DataNode setSample_biasScalar(Double sample_biasValue);

	/**
	 * Class to describe the motors that are used in the manipulator
	 *
	 * @return  the value.
	 */
	public NXpositioner getPositioner();

	/**
	 * Class to describe the motors that are used in the manipulator
	 *
	 * @param positionerGroup the positionerGroup
	 */
	public void setPositioner(NXpositioner positionerGroup);

	/**
	 * Get a NXpositioner node by name:
	 * <ul>
	 * <li>
	 * Class to describe the motors that are used in the manipulator</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXpositioner for that node.
	 */
	public NXpositioner getPositioner(String name);

	/**
	 * Set a NXpositioner node by name:
	 * <ul>
	 * <li>
	 * Class to describe the motors that are used in the manipulator</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param positioner the value to set
	 */
	public void setPositioner(String name, NXpositioner positioner);

	/**
	 * Get all NXpositioner nodes:
	 * <ul>
	 * <li>
	 * Class to describe the motors that are used in the manipulator</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXpositioner for that node.
	 */
	public Map<String, NXpositioner> getAllPositioner();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Class to describe the motors that are used in the manipulator</li>
	 * </ul>
	 *
	 * @param positioner the child nodes to add
	 */

	public void setAllPositioner(Map<String, NXpositioner> positioner);


	/**
	 * Refers to the last transformation specifying the positon of the manipulator in
	 * the NXtransformations chain.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDepends_on();

	/**
	 * Refers to the last transformation specifying the positon of the manipulator in
	 * the NXtransformations chain.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Refers to the last transformation specifying the positon of the manipulator in
	 * the NXtransformations chain.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Refers to the last transformation specifying the positon of the manipulator in
	 * the NXtransformations chain.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Collection of axis-based translations and rotations to describe the location and
	 * geometry of the manipulator as a component in the instrument. Conventions from
	 * the NXtransformations base class are used. In principle, the McStas coordinate
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
	 * geometry of the manipulator as a component in the instrument. Conventions from
	 * the NXtransformations base class are used. In principle, the McStas coordinate
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
	 * geometry of the manipulator as a component in the instrument. Conventions from
	 * the NXtransformations base class are used. In principle, the McStas coordinate
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
	 * geometry of the manipulator as a component in the instrument. Conventions from
	 * the NXtransformations base class are used. In principle, the McStas coordinate
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
	 * geometry of the manipulator as a component in the instrument. Conventions from
	 * the NXtransformations base class are used. In principle, the McStas coordinate
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
	 * geometry of the manipulator as a component in the instrument. Conventions from
	 * the NXtransformations base class are used. In principle, the McStas coordinate
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


}
