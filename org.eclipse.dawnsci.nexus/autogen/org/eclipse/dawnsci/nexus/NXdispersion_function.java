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
 * This describes a dispersion function for a material or layer
 * <p><b>Symbols:</b><ul>
 * <li><b>n_repetitions</b>
 * The number of repetitions for the repeated parameters</li></ul></p>
 *
 */
public interface NXdispersion_function extends NXobject {

	public static final String NX_MODEL_NAME = "model_name";
	public static final String NX_FORMULA = "formula";
	public static final String NX_CONVENTION = "convention";
	public static final String NX_ENERGY_IDENTIFIER = "energy_identifier";
	public static final String NX_ENERGY_MIN = "energy_min";
	public static final String NX_ENERGY_MAX = "energy_max";
	public static final String NX_ENERGY_UNIT = "energy_unit";
	public static final String NX_WAVELENGTH_IDENTIFIER = "wavelength_identifier";
	public static final String NX_WAVELENGTH_UNIT = "wavelength_unit";
	public static final String NX_WAVELENGTH_MIN = "wavelength_min";
	public static final String NX_WAVELENGTH_MAX = "wavelength_max";
	public static final String NX_REPRESENTATION = "representation";
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
	 * This should be a python parsable function.
	 * Here we should provide which keywords are available
	 * and a BNF of valid grammar.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getFormula();

	/**
	 * This should be a python parsable function.
	 * Here we should provide which keywords are available
	 * and a BNF of valid grammar.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param formulaDataset the formulaDataset
	 */
	public DataNode setFormula(IDataset formulaDataset);

	/**
	 * This should be a python parsable function.
	 * Here we should provide which keywords are available
	 * and a BNF of valid grammar.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getFormulaScalar();

	/**
	 * This should be a python parsable function.
	 * Here we should provide which keywords are available
	 * and a BNF of valid grammar.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param formula the formula
	 */
	public DataNode setFormulaScalar(String formulaValue);

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
	 * The identifier used to represent energy
	 * in the formula. It is recommended to use `E`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getEnergy_identifier();

	/**
	 * The identifier used to represent energy
	 * in the formula. It is recommended to use `E`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param energy_identifierDataset the energy_identifierDataset
	 */
	public DataNode setEnergy_identifier(IDataset energy_identifierDataset);

	/**
	 * The identifier used to represent energy
	 * in the formula. It is recommended to use `E`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getEnergy_identifierScalar();

	/**
	 * The identifier used to represent energy
	 * in the formula. It is recommended to use `E`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param energy_identifier the energy_identifier
	 */
	public DataNode setEnergy_identifierScalar(String energy_identifierValue);

	/**
	 * The minimum energy value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getEnergy_min();

	/**
	 * The minimum energy value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param energy_minDataset the energy_minDataset
	 */
	public DataNode setEnergy_min(IDataset energy_minDataset);

	/**
	 * The minimum energy value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getEnergy_minScalar();

	/**
	 * The minimum energy value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param energy_min the energy_min
	 */
	public DataNode setEnergy_minScalar(Number energy_minValue);

	/**
	 * The maximum energy value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getEnergy_max();

	/**
	 * The maximum energy value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param energy_maxDataset the energy_maxDataset
	 */
	public DataNode setEnergy_max(IDataset energy_maxDataset);

	/**
	 * The maximum energy value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getEnergy_maxScalar();

	/**
	 * The maximum energy value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param energy_max the energy_max
	 */
	public DataNode setEnergy_maxScalar(Number energy_maxValue);

	/**
	 * The energy unit used in the formula.
	 * The field value is a scaling factor for the units attribute.
	 * It is recommeded to set the field value to 1 and carry all the unit
	 * scaling information in the units attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getEnergy_unit();

	/**
	 * The energy unit used in the formula.
	 * The field value is a scaling factor for the units attribute.
	 * It is recommeded to set the field value to 1 and carry all the unit
	 * scaling information in the units attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param energy_unitDataset the energy_unitDataset
	 */
	public DataNode setEnergy_unit(IDataset energy_unitDataset);

	/**
	 * The energy unit used in the formula.
	 * The field value is a scaling factor for the units attribute.
	 * It is recommeded to set the field value to 1 and carry all the unit
	 * scaling information in the units attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getEnergy_unitScalar();

	/**
	 * The energy unit used in the formula.
	 * The field value is a scaling factor for the units attribute.
	 * It is recommeded to set the field value to 1 and carry all the unit
	 * scaling information in the units attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param energy_unit the energy_unit
	 */
	public DataNode setEnergy_unitScalar(Number energy_unitValue);

	/**
	 * The identifier useed to represent wavelength
	 * in the formula. It is recommended to use `lambda`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWavelength_identifier();

	/**
	 * The identifier useed to represent wavelength
	 * in the formula. It is recommended to use `lambda`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param wavelength_identifierDataset the wavelength_identifierDataset
	 */
	public DataNode setWavelength_identifier(IDataset wavelength_identifierDataset);

	/**
	 * The identifier useed to represent wavelength
	 * in the formula. It is recommended to use `lambda`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getWavelength_identifierScalar();

	/**
	 * The identifier useed to represent wavelength
	 * in the formula. It is recommended to use `lambda`.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param wavelength_identifier the wavelength_identifier
	 */
	public DataNode setWavelength_identifierScalar(String wavelength_identifierValue);

	/**
	 * The wavelength unit used in the formula.
	 * The field value is a scaling factor for the units attribute.
	 * It is recommeded to set the field value to 1 and carry all the unit
	 * scaling information in the units attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWavelength_unit();

	/**
	 * The wavelength unit used in the formula.
	 * The field value is a scaling factor for the units attribute.
	 * It is recommeded to set the field value to 1 and carry all the unit
	 * scaling information in the units attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param wavelength_unitDataset the wavelength_unitDataset
	 */
	public DataNode setWavelength_unit(IDataset wavelength_unitDataset);

	/**
	 * The wavelength unit used in the formula.
	 * The field value is a scaling factor for the units attribute.
	 * It is recommeded to set the field value to 1 and carry all the unit
	 * scaling information in the units attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getWavelength_unitScalar();

	/**
	 * The wavelength unit used in the formula.
	 * The field value is a scaling factor for the units attribute.
	 * It is recommeded to set the field value to 1 and carry all the unit
	 * scaling information in the units attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param wavelength_unit the wavelength_unit
	 */
	public DataNode setWavelength_unitScalar(Number wavelength_unitValue);

	/**
	 * The minimum wavelength value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWavelength_min();

	/**
	 * The minimum wavelength value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param wavelength_minDataset the wavelength_minDataset
	 */
	public DataNode setWavelength_min(IDataset wavelength_minDataset);

	/**
	 * The minimum wavelength value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getWavelength_minScalar();

	/**
	 * The minimum wavelength value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param wavelength_min the wavelength_min
	 */
	public DataNode setWavelength_minScalar(Number wavelength_minValue);

	/**
	 * The maximum wavelength value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWavelength_max();

	/**
	 * The maximum wavelength value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param wavelength_maxDataset the wavelength_maxDataset
	 */
	public DataNode setWavelength_max(IDataset wavelength_maxDataset);

	/**
	 * The maximum wavelength value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getWavelength_maxScalar();

	/**
	 * The maximum wavelength value at which this formula is valid.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param wavelength_max the wavelength_max
	 */
	public DataNode setWavelength_maxScalar(Number wavelength_maxValue);

	/**
	 * Which representation does the formula evaluate to.
	 * This may either be n for refractive index or eps for dielectric function.
	 * The appropriate token is then to be used inside the formula.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>n</b> </li>
	 * <li><b>eps</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getRepresentation();

	/**
	 * Which representation does the formula evaluate to.
	 * This may either be n for refractive index or eps for dielectric function.
	 * The appropriate token is then to be used inside the formula.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>n</b> </li>
	 * <li><b>eps</b> </li></ul></p>
	 * </p>
	 *
	 * @param representationDataset the representationDataset
	 */
	public DataNode setRepresentation(IDataset representationDataset);

	/**
	 * Which representation does the formula evaluate to.
	 * This may either be n for refractive index or eps for dielectric function.
	 * The appropriate token is then to be used inside the formula.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>n</b> </li>
	 * <li><b>eps</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getRepresentationScalar();

	/**
	 * Which representation does the formula evaluate to.
	 * This may either be n for refractive index or eps for dielectric function.
	 * The appropriate token is then to be used inside the formula.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>n</b> </li>
	 * <li><b>eps</b> </li></ul></p>
	 * </p>
	 *
	 * @param representation the representation
	 */
	public DataNode setRepresentationScalar(String representationValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXdispersion_single_parameter getDispersion_single_parameter();

	/**
	 *
	 * @param dispersion_single_parameterGroup the dispersion_single_parameterGroup
	 */
	public void setDispersion_single_parameter(NXdispersion_single_parameter dispersion_single_parameterGroup);

	/**
	 * Get a NXdispersion_single_parameter node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdispersion_single_parameter for that node.
	 */
	public NXdispersion_single_parameter getDispersion_single_parameter(String name);

	/**
	 * Set a NXdispersion_single_parameter node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param dispersion_single_parameter the value to set
	 */
	public void setDispersion_single_parameter(String name, NXdispersion_single_parameter dispersion_single_parameter);

	/**
	 * Get all NXdispersion_single_parameter nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdispersion_single_parameter for that node.
	 */
	public Map<String, NXdispersion_single_parameter> getAllDispersion_single_parameter();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param dispersion_single_parameter the child nodes to add
	 */

	public void setAllDispersion_single_parameter(Map<String, NXdispersion_single_parameter> dispersion_single_parameter);


	/**
	 *
	 * @return  the value.
	 */
	public NXdispersion_repeated_parameter getDispersion_repeated_parameter();

	/**
	 *
	 * @param dispersion_repeated_parameterGroup the dispersion_repeated_parameterGroup
	 */
	public void setDispersion_repeated_parameter(NXdispersion_repeated_parameter dispersion_repeated_parameterGroup);

	/**
	 * Get a NXdispersion_repeated_parameter node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdispersion_repeated_parameter for that node.
	 */
	public NXdispersion_repeated_parameter getDispersion_repeated_parameter(String name);

	/**
	 * Set a NXdispersion_repeated_parameter node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param dispersion_repeated_parameter the value to set
	 */
	public void setDispersion_repeated_parameter(String name, NXdispersion_repeated_parameter dispersion_repeated_parameter);

	/**
	 * Get all NXdispersion_repeated_parameter nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdispersion_repeated_parameter for that node.
	 */
	public Map<String, NXdispersion_repeated_parameter> getAllDispersion_repeated_parameter();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param dispersion_repeated_parameter the child nodes to add
	 */

	public void setAllDispersion_repeated_parameter(Map<String, NXdispersion_repeated_parameter> dispersion_repeated_parameter);


}
