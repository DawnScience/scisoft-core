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
 * CEOS definitions/model for aberrations of electro-magnetic lenses.
 * See `S. J. Pennycock and P. D. Nellist <https://doi.org/10.1007/978-1-4419-7200-2>`_ (page 44ff, and page 118ff)
 * for different definitions available and further details. Table 7-2 of Ibid.
 * publication (page 305ff) documents how to convert from the NION to the
 * CEOS definitions.
 *
 */
public interface NXaberration_model_ceos extends NXobject {

	public static final String NX_MODEL = "model";
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ceos</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getModel();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ceos</b> </li></ul></p>
	 * </p>
	 *
	 * @param modelDataset the modelDataset
	 */
	public DataNode setModel(IDataset modelDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ceos</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getModelScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ceos</b> </li></ul></p>
	 * </p>
	 *
	 * @param model the model
	 */
	public DataNode setModelScalar(String modelValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_1();

	/**
	 *
	 * @param c_1Group the c_1Group
	 */
	public void setC_1(NXaberration c_1Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getA_1();

	/**
	 *
	 * @param a_1Group the a_1Group
	 */
	public void setA_1(NXaberration a_1Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getB_2();

	/**
	 *
	 * @param b_2Group the b_2Group
	 */
	public void setB_2(NXaberration b_2Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getA_2();

	/**
	 *
	 * @param a_2Group the a_2Group
	 */
	public void setA_2(NXaberration a_2Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_3();

	/**
	 *
	 * @param c_3Group the c_3Group
	 */
	public void setC_3(NXaberration c_3Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getS_3();

	/**
	 *
	 * @param s_3Group the s_3Group
	 */
	public void setS_3(NXaberration s_3Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getA_3();

	/**
	 *
	 * @param a_3Group the a_3Group
	 */
	public void setA_3(NXaberration a_3Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getB_4();

	/**
	 *
	 * @param b_4Group the b_4Group
	 */
	public void setB_4(NXaberration b_4Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getD_4();

	/**
	 *
	 * @param d_4Group the d_4Group
	 */
	public void setD_4(NXaberration d_4Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getA_4();

	/**
	 *
	 * @param a_4Group the a_4Group
	 */
	public void setA_4(NXaberration a_4Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_5();

	/**
	 *
	 * @param c_5Group the c_5Group
	 */
	public void setC_5(NXaberration c_5Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getS_5();

	/**
	 *
	 * @param s_5Group the s_5Group
	 */
	public void setS_5(NXaberration s_5Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getR_5();

	/**
	 *
	 * @param r_5Group the r_5Group
	 */
	public void setR_5(NXaberration r_5Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getA_6();

	/**
	 *
	 * @param a_6Group the a_6Group
	 */
	public void setA_6(NXaberration a_6Group);

}
