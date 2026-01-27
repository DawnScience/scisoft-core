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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * This describes a fit function that is used to fit data to any functional form.
 * A fit function is used to describe a set of data :math:`y_k, k = 1 ... M`, which are collected as a function
 * of one or more independent variables :math:`x` at the points :math:`x_k`. The fit function :math:`f` describes
 * these data in an approximate way as :math:`y_k \approx f(a_0, . . . a_n, x_k)`,
 * where :math:`a_i, i = 0 . . . n` are the *fit parameters* (which are stored the instances of ``NXfit_parameter``).

 */
public class NXfit_functionImpl extends NXobjectImpl implements NXfit_function {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PARAMETERS);

	public NXfit_functionImpl() {
		super();
	}

	public NXfit_functionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXfit_function.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_FIT_FUNCTION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getFunction_type() {
		return getDataset(NX_FUNCTION_TYPE);
	}

	@Override
	public String getFunction_typeScalar() {
		return getString(NX_FUNCTION_TYPE);
	}

	@Override
	public DataNode setFunction_type(IDataset function_typeDataset) {
		return setDataset(NX_FUNCTION_TYPE, function_typeDataset);
	}

	@Override
	public DataNode setFunction_typeScalar(String function_typeValue) {
		return setString(NX_FUNCTION_TYPE, function_typeValue);
	}

	@Override
	public Dataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
	}

	@Override
	public Dataset getFormula_description() {
		return getDataset(NX_FORMULA_DESCRIPTION);
	}

	@Override
	public String getFormula_descriptionScalar() {
		return getString(NX_FORMULA_DESCRIPTION);
	}

	@Override
	public DataNode setFormula_description(IDataset formula_descriptionDataset) {
		return setDataset(NX_FORMULA_DESCRIPTION, formula_descriptionDataset);
	}

	@Override
	public DataNode setFormula_descriptionScalar(String formula_descriptionValue) {
		return setString(NX_FORMULA_DESCRIPTION, formula_descriptionValue);
	}

	@Override
	public NXparameters getFit_parameters() {
		// dataNodeName = NX_FIT_PARAMETERS
		return getChild("fit_parameters", NXparameters.class);
	}

	@Override
	public void setFit_parameters(NXparameters fit_parametersGroup) {
		putChild("fit_parameters", fit_parametersGroup);
	}

}
