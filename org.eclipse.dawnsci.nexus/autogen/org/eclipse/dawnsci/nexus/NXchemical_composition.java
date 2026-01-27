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
 * Chemical composition of a sample or a set of things.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n</b>
 * The number of samples or things.</li></ul></p>
 *
 */
public interface NXchemical_composition extends NXobject {

	public static final String NX_NORMALIZATION = "normalization";
	public static final String NX_TOTAL = "total";
	/**
	 * Reporting compositions as atom and weight percent yields both
	 * dimensionless quantities but their conceptual interpretation differs.
	 * A normalization based on atom_percent counts relative to the
	 * total number of atoms which are of a particular type.
	 * By contrast, weight_percent normalization factorizes in the
	 * respective mass of the elements. Software libraries that work with
	 * units, like pint in Python, are challenged by these differences as
	 * at.-% and wt.-% are both fractional quantities.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>atom_percent</b> </li>
	 * <li><b>weight_percent</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNormalization();

	/**
	 * Reporting compositions as atom and weight percent yields both
	 * dimensionless quantities but their conceptual interpretation differs.
	 * A normalization based on atom_percent counts relative to the
	 * total number of atoms which are of a particular type.
	 * By contrast, weight_percent normalization factorizes in the
	 * respective mass of the elements. Software libraries that work with
	 * units, like pint in Python, are challenged by these differences as
	 * at.-% and wt.-% are both fractional quantities.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>atom_percent</b> </li>
	 * <li><b>weight_percent</b> </li></ul></p>
	 * </p>
	 *
	 * @param normalizationDataset the normalizationDataset
	 */
	public DataNode setNormalization(IDataset normalizationDataset);

	/**
	 * Reporting compositions as atom and weight percent yields both
	 * dimensionless quantities but their conceptual interpretation differs.
	 * A normalization based on atom_percent counts relative to the
	 * total number of atoms which are of a particular type.
	 * By contrast, weight_percent normalization factorizes in the
	 * respective mass of the elements. Software libraries that work with
	 * units, like pint in Python, are challenged by these differences as
	 * at.-% and wt.-% are both fractional quantities.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>atom_percent</b> </li>
	 * <li><b>weight_percent</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNormalizationScalar();

	/**
	 * Reporting compositions as atom and weight percent yields both
	 * dimensionless quantities but their conceptual interpretation differs.
	 * A normalization based on atom_percent counts relative to the
	 * total number of atoms which are of a particular type.
	 * By contrast, weight_percent normalization factorizes in the
	 * respective mass of the elements. Software libraries that work with
	 * units, like pint in Python, are challenged by these differences as
	 * at.-% and wt.-% are both fractional quantities.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>atom_percent</b> </li>
	 * <li><b>weight_percent</b> </li></ul></p>
	 * </p>
	 *
	 * @param normalization the normalization
	 */
	public DataNode setNormalizationScalar(String normalizationValue);

	/**
	 * Total formula mass or number of atoms, depending on the
	 * normalization stated in the normalization field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTotal();

	/**
	 * Total formula mass or number of atoms, depending on the
	 * normalization stated in the normalization field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param totalDataset the totalDataset
	 */
	public DataNode setTotal(IDataset totalDataset);

	/**
	 * Total formula mass or number of atoms, depending on the
	 * normalization stated in the normalization field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTotalScalar();

	/**
	 * Total formula mass or number of atoms, depending on the
	 * normalization stated in the normalization field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param total the total
	 */
	public DataNode setTotalScalar(Number totalValue);

	/**
	 * If this group is used to report the composition of elements from the periodic table,
	 * the group should use the chemical symbol of that element. For other case the
	 * group name is unconstrained.
	 *
	 * @return  the value.
	 */
	public NXatom getElement();

	/**
	 * If this group is used to report the composition of elements from the periodic table,
	 * the group should use the chemical symbol of that element. For other case the
	 * group name is unconstrained.
	 *
	 * @param elementGroup the elementGroup
	 */
	public void setElement(NXatom elementGroup);

}
