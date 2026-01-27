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
 * This describes a fit function that is used to fit data to any functional form.
 * A fit function is used to describe a set of data :math:`y_k, k = 1 ... M`, which are collected as a function
 * of one or more independent variables :math:`x` at the points :math:`x_k`. The fit function :math:`f` describes
 * these data in an approximate way as :math:`y_k \approx f(a_0, . . . a_n, x_k)`,
 * where :math:`a_i, i = 0 . . . n` are the *fit parameters* (which are stored the instances of ``NXfit_parameter``).
 *
 */
public interface NXfit_function extends NXobject {

	public static final String NX_FUNCTION_TYPE = "function_type";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_FORMULA_DESCRIPTION = "formula_description";
	/**
	 * Type of function used.
	 * Examples include "Gaussian" and "Lorentzian". In case a complicated functions, the the functional
	 * form of the function should be given by the ``formula_description`` field . The user is also encouraged
	 * to use the ``description`` field for describing the fit function in a human-readable way.
	 * Application definitions may limit the allowed fit functions by using an enumeration for the ``function_type`` field.
	 *
	 * @return  the value.
	 */
	public Dataset getFunction_type();

	/**
	 * Type of function used.
	 * Examples include "Gaussian" and "Lorentzian". In case a complicated functions, the the functional
	 * form of the function should be given by the ``formula_description`` field . The user is also encouraged
	 * to use the ``description`` field for describing the fit function in a human-readable way.
	 * Application definitions may limit the allowed fit functions by using an enumeration for the ``function_type`` field.
	 *
	 * @param function_typeDataset the function_typeDataset
	 */
	public DataNode setFunction_type(IDataset function_typeDataset);

	/**
	 * Type of function used.
	 * Examples include "Gaussian" and "Lorentzian". In case a complicated functions, the the functional
	 * form of the function should be given by the ``formula_description`` field . The user is also encouraged
	 * to use the ``description`` field for describing the fit function in a human-readable way.
	 * Application definitions may limit the allowed fit functions by using an enumeration for the ``function_type`` field.
	 *
	 * @return  the value.
	 */
	public String getFunction_typeScalar();

	/**
	 * Type of function used.
	 * Examples include "Gaussian" and "Lorentzian". In case a complicated functions, the the functional
	 * form of the function should be given by the ``formula_description`` field . The user is also encouraged
	 * to use the ``description`` field for describing the fit function in a human-readable way.
	 * Application definitions may limit the allowed fit functions by using an enumeration for the ``function_type`` field.
	 *
	 * @param function_type the function_type
	 */
	public DataNode setFunction_typeScalar(String function_typeValue);

	/**
	 * Human-readable short description of this fit function.
	 * Software tools may use this field to write their local description of the fit function.
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Human-readable short description of this fit function.
	 * Software tools may use this field to write their local description of the fit function.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Human-readable short description of this fit function.
	 * Software tools may use this field to write their local description of the fit function.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Human-readable short description of this fit function.
	 * Software tools may use this field to write their local description of the fit function.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Description of the mathematical formula of the function, taking into account the
	 * instances of ``TERM`` in ``fit_parameters``.
	 *
	 * @return  the value.
	 */
	public Dataset getFormula_description();

	/**
	 * Description of the mathematical formula of the function, taking into account the
	 * instances of ``TERM`` in ``fit_parameters``.
	 *
	 * @param formula_descriptionDataset the formula_descriptionDataset
	 */
	public DataNode setFormula_description(IDataset formula_descriptionDataset);

	/**
	 * Description of the mathematical formula of the function, taking into account the
	 * instances of ``TERM`` in ``fit_parameters``.
	 *
	 * @return  the value.
	 */
	public String getFormula_descriptionScalar();

	/**
	 * Description of the mathematical formula of the function, taking into account the
	 * instances of ``TERM`` in ``fit_parameters``.
	 *
	 * @param formula_description the formula_description
	 */
	public DataNode setFormula_descriptionScalar(String formula_descriptionValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXparameters getFit_parameters();

	/**
	 *
	 * @param fit_parametersGroup the fit_parametersGroup
	 */
	public void setFit_parameters(NXparameters fit_parametersGroup);

}
