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
 * Base class for describing a peak, its functional form, and support values
 * i.e., the discretization points at which the function has been evaluated.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>dimRank</b>
 * Rank of the dependent and independent data arrays
 * (for multivariate scalar-valued fit).</li></ul></p>
 *
 */
public interface NXpeak extends NXobject {

	public static final String NX_LABEL = "label";
	public static final String NX_TOTAL_AREA = "total_area";
	/**
	 * Human-readable label which specifies which concept/entity
	 * the peak represents/identifies.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLabel();

	/**
	 * Human-readable label which specifies which concept/entity
	 * the peak represents/identifies.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param labelDataset the labelDataset
	 */
	public DataNode setLabel(IDataset labelDataset);

	/**
	 * Human-readable label which specifies which concept/entity
	 * the peak represents/identifies.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getLabelScalar();

	/**
	 * Human-readable label which specifies which concept/entity
	 * the peak represents/identifies.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param label the label
	 */
	public DataNode setLabelScalar(String labelValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXdata getData();

	/**
	 *
	 * @param dataGroup the dataGroup
	 */
	public void setData(NXdata dataGroup);

	/**
	 * The functional form of the peak. This could be a Gaussian, Lorentzian, Voigt,
	 * etc.
	 *
	 * @return  the value.
	 */
	public NXfit_function getFunction();

	/**
	 * The functional form of the peak. This could be a Gaussian, Lorentzian, Voigt,
	 * etc.
	 *
	 * @param functionGroup the functionGroup
	 */
	public void setFunction(NXfit_function functionGroup);

	/**
	 * Total area under the curve.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTotal_area();

	/**
	 * Total area under the curve.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param total_areaDataset the total_areaDataset
	 */
	public DataNode setTotal_area(IDataset total_areaDataset);

	/**
	 * Total area under the curve.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTotal_areaScalar();

	/**
	 * Total area under the curve.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param total_area the total_area
	 */
	public DataNode setTotal_areaScalar(Number total_areaValue);

}
