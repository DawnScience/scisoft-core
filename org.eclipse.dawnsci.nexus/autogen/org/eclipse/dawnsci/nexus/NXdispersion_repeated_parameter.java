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
 * A repeated parameter for a dispersion function
 * <p><b>Symbols:</b><ul>
 * <li><b>n_repetitions</b>
 * The number of parameter repetitions</li></ul></p>
 *
 */
public interface NXdispersion_repeated_parameter extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_PARAMETER_UNITS = "parameter_units";
	public static final String NX_VALUES = "values";
	/**
	 * The name of the parameter
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getName();

	/**
	 * The name of the parameter
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * The name of the parameter
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * The name of the parameter
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * A description of what this parameter represents
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDescription();

	/**
	 * A description of what this parameter represents
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * A description of what this parameter represents
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * A description of what this parameter represents
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * A unit array associating a unit with each parameter.
	 * The first element should be equal to values/@unit.
	 * The values should be SI interpretable standard units
	 * with common prefixes (e.g. mikro, nano etc.) or their
	 * short-hand notation (e.g. nm, mm, kHz etc.).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_repetitions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getParameter_units();

	/**
	 * A unit array associating a unit with each parameter.
	 * The first element should be equal to values/@unit.
	 * The values should be SI interpretable standard units
	 * with common prefixes (e.g. mikro, nano etc.) or their
	 * short-hand notation (e.g. nm, mm, kHz etc.).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_repetitions;
	 * </p>
	 *
	 * @param parameter_unitsDataset the parameter_unitsDataset
	 */
	public DataNode setParameter_units(IDataset parameter_unitsDataset);

	/**
	 * A unit array associating a unit with each parameter.
	 * The first element should be equal to values/@unit.
	 * The values should be SI interpretable standard units
	 * with common prefixes (e.g. mikro, nano etc.) or their
	 * short-hand notation (e.g. nm, mm, kHz etc.).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_repetitions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getParameter_unitsScalar();

	/**
	 * A unit array associating a unit with each parameter.
	 * The first element should be equal to values/@unit.
	 * The values should be SI interpretable standard units
	 * with common prefixes (e.g. mikro, nano etc.) or their
	 * short-hand notation (e.g. nm, mm, kHz etc.).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_repetitions;
	 * </p>
	 *
	 * @param parameter_units the parameter_units
	 */
	public DataNode setParameter_unitsScalar(String parameter_unitsValue);

	/**
	 * The value of the parameter
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_repetitions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getValues();

	/**
	 * The value of the parameter
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_repetitions;
	 * </p>
	 *
	 * @param valuesDataset the valuesDataset
	 */
	public DataNode setValues(IDataset valuesDataset);

	/**
	 * The value of the parameter
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_repetitions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getValuesScalar();

	/**
	 * The value of the parameter
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_repetitions;
	 * </p>
	 *
	 * @param values the values
	 */
	public DataNode setValuesScalar(Number valuesValue);

}
