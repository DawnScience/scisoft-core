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
 * A dispersion table denoting energy, dielectric function tabulated values.
 * <p><b>Symbols:</b>
 * The symbols in this schema to denote the dimensions<ul>
 * <li><b>n_points</b>
 * The number of energy and dielectric function points</li></ul></p>
 *
 */
public interface NXdispersion_table extends NXobject {

	public static final String NX_MODEL_NAME = "model_name";
	public static final String NX_CONVENTION = "convention";
	public static final String NX_WAVELENGTH = "wavelength";
	public static final String NX_ENERGY = "energy";
	public static final String NX_REFRACTIVE_INDEX = "refractive_index";
	public static final String NX_DIELECTRIC_FUNCTION = "dielectric_function";
	/**
	 * The name of this dispersion model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getModel_name();

	/**
	 * The name of this dispersion model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param model_nameDataset the model_nameDataset
	 */
	public DataNode setModel_name(IDataset model_nameDataset);

	/**
	 * The name of this dispersion model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getModel_nameScalar();

	/**
	 * The name of this dispersion model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param model_name the model_name
	 */
	public DataNode setModel_nameScalar(String model_nameValue);

	/**
	 * The sign convention being used (n + or - ik)
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>n + ik</b> </li>
	 * <li><b>n - ik</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getConvention();

	/**
	 * The sign convention being used (n + or - ik)
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>n + ik</b> </li>
	 * <li><b>n - ik</b> </li></ul></p>
	 * </p>
	 *
	 * @param conventionDataset the conventionDataset
	 */
	public DataNode setConvention(IDataset conventionDataset);

	/**
	 * The sign convention being used (n + or - ik)
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>n + ik</b> </li>
	 * <li><b>n - ik</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getConventionScalar();

	/**
	 * The sign convention being used (n + or - ik)
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>n + ik</b> </li>
	 * <li><b>n - ik</b> </li></ul></p>
	 * </p>
	 *
	 * @param convention the convention
	 */
	public DataNode setConventionScalar(String conventionValue);

	/**
	 * The wavelength array of the tabulated dataset.
	 * This is essentially a duplicate of the energy field.
	 * There should be one or both of them present.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWavelength();

	/**
	 * The wavelength array of the tabulated dataset.
	 * This is essentially a duplicate of the energy field.
	 * There should be one or both of them present.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @param wavelengthDataset the wavelengthDataset
	 */
	public DataNode setWavelength(IDataset wavelengthDataset);

	/**
	 * The wavelength array of the tabulated dataset.
	 * This is essentially a duplicate of the energy field.
	 * There should be one or both of them present.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getWavelengthScalar();

	/**
	 * The wavelength array of the tabulated dataset.
	 * This is essentially a duplicate of the energy field.
	 * There should be one or both of them present.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @param wavelength the wavelength
	 */
	public DataNode setWavelengthScalar(Number wavelengthValue);

	/**
	 * The energy array of the tabulated dataset.
	 * This is essentially a duplicate of the wavelength field.
	 * There should be one or both of them present.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getEnergy();

	/**
	 * The energy array of the tabulated dataset.
	 * This is essentially a duplicate of the wavelength field.
	 * There should be one or both of them present.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @param energyDataset the energyDataset
	 */
	public DataNode setEnergy(IDataset energyDataset);

	/**
	 * The energy array of the tabulated dataset.
	 * This is essentially a duplicate of the wavelength field.
	 * There should be one or both of them present.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getEnergyScalar();

	/**
	 * The energy array of the tabulated dataset.
	 * This is essentially a duplicate of the wavelength field.
	 * There should be one or both of them present.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @param energy the energy
	 */
	public DataNode setEnergyScalar(Number energyValue);

	/**
	 * The refractive index array of the tabulated dataset.
	 * <p>
	 * <b>Type:</b> NX_COMPLEX
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getRefractive_index();

	/**
	 * The refractive index array of the tabulated dataset.
	 * <p>
	 * <b>Type:</b> NX_COMPLEX
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @param refractive_indexDataset the refractive_indexDataset
	 */
	public DataNode setRefractive_index(IDataset refractive_indexDataset);

	/**
	 * The refractive index array of the tabulated dataset.
	 * <p>
	 * <b>Type:</b> NX_COMPLEX
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getRefractive_indexScalar();

	/**
	 * The refractive index array of the tabulated dataset.
	 * <p>
	 * <b>Type:</b> NX_COMPLEX
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @param refractive_index the refractive_index
	 */
	public DataNode setRefractive_indexScalar(String refractive_indexValue);

	/**
	 * The dielectric function of the tabulated dataset.
	 * <p>
	 * <b>Type:</b> NX_COMPLEX
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDielectric_function();

	/**
	 * The dielectric function of the tabulated dataset.
	 * <p>
	 * <b>Type:</b> NX_COMPLEX
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @param dielectric_functionDataset the dielectric_functionDataset
	 */
	public DataNode setDielectric_function(IDataset dielectric_functionDataset);

	/**
	 * The dielectric function of the tabulated dataset.
	 * <p>
	 * <b>Type:</b> NX_COMPLEX
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDielectric_functionScalar();

	/**
	 * The dielectric function of the tabulated dataset.
	 * <p>
	 * <b>Type:</b> NX_COMPLEX
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_points;
	 * </p>
	 *
	 * @param dielectric_function the dielectric_function
	 */
	public DataNode setDielectric_functionScalar(String dielectric_functionValue);

}
