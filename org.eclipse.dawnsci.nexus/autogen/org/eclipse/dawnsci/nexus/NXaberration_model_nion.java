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
 * NION definitions/model for aberrations of electro-magnetic lenses.
 * See `S. J. Pennycock and P. D. Nellist <https://doi.org/10.1007/978-1-4419-7200-2>`_ (page 44ff, and page 118ff)
 * for different definitions available and further details. Table 7-2 of Ibid.
 * publication (page 305ff) documents how to convert from the NION to the
 * CEOS definitions.
 *
 */
public interface NXaberration_model_nion extends NXobject {

	public static final String NX_MODEL = "model";
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nion</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getModel();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nion</b> </li></ul></p>
	 * </p>
	 *
	 * @param modelDataset the modelDataset
	 */
	public DataNode setModel(IDataset modelDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nion</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getModelScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nion</b> </li></ul></p>
	 * </p>
	 *
	 * @param model the model
	 */
	public DataNode setModelScalar(String modelValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_1_0();

	/**
	 *
	 * @param c_1_0Group the c_1_0Group
	 */
	public void setC_1_0(NXaberration c_1_0Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_1_2_a();

	/**
	 *
	 * @param c_1_2_aGroup the c_1_2_aGroup
	 */
	public void setC_1_2_a(NXaberration c_1_2_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_1_2_b();

	/**
	 *
	 * @param c_1_2_bGroup the c_1_2_bGroup
	 */
	public void setC_1_2_b(NXaberration c_1_2_bGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_2_1_a();

	/**
	 *
	 * @param c_2_1_aGroup the c_2_1_aGroup
	 */
	public void setC_2_1_a(NXaberration c_2_1_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_2_1_b();

	/**
	 *
	 * @param c_2_1_bGroup the c_2_1_bGroup
	 */
	public void setC_2_1_b(NXaberration c_2_1_bGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_2_3_a();

	/**
	 *
	 * @param c_2_3_aGroup the c_2_3_aGroup
	 */
	public void setC_2_3_a(NXaberration c_2_3_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_2_3_b();

	/**
	 *
	 * @param c_2_3_bGroup the c_2_3_bGroup
	 */
	public void setC_2_3_b(NXaberration c_2_3_bGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_3_0();

	/**
	 *
	 * @param c_3_0Group the c_3_0Group
	 */
	public void setC_3_0(NXaberration c_3_0Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_3_2_a();

	/**
	 *
	 * @param c_3_2_aGroup the c_3_2_aGroup
	 */
	public void setC_3_2_a(NXaberration c_3_2_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_3_2_b();

	/**
	 *
	 * @param c_3_2_bGroup the c_3_2_bGroup
	 */
	public void setC_3_2_b(NXaberration c_3_2_bGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_3_4_a();

	/**
	 *
	 * @param c_3_4_aGroup the c_3_4_aGroup
	 */
	public void setC_3_4_a(NXaberration c_3_4_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_3_4_b();

	/**
	 *
	 * @param c_3_4_bGroup the c_3_4_bGroup
	 */
	public void setC_3_4_b(NXaberration c_3_4_bGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_4_1_a();

	/**
	 *
	 * @param c_4_1_aGroup the c_4_1_aGroup
	 */
	public void setC_4_1_a(NXaberration c_4_1_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_4_1_b();

	/**
	 *
	 * @param c_4_1_bGroup the c_4_1_bGroup
	 */
	public void setC_4_1_b(NXaberration c_4_1_bGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_4_3_a();

	/**
	 *
	 * @param c_4_3_aGroup the c_4_3_aGroup
	 */
	public void setC_4_3_a(NXaberration c_4_3_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_4_3_b();

	/**
	 *
	 * @param c_4_3_bGroup the c_4_3_bGroup
	 */
	public void setC_4_3_b(NXaberration c_4_3_bGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_4_5_a();

	/**
	 *
	 * @param c_4_5_aGroup the c_4_5_aGroup
	 */
	public void setC_4_5_a(NXaberration c_4_5_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_4_5_b();

	/**
	 *
	 * @param c_4_5_bGroup the c_4_5_bGroup
	 */
	public void setC_4_5_b(NXaberration c_4_5_bGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_5_0();

	/**
	 *
	 * @param c_5_0Group the c_5_0Group
	 */
	public void setC_5_0(NXaberration c_5_0Group);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_5_2_a();

	/**
	 *
	 * @param c_5_2_aGroup the c_5_2_aGroup
	 */
	public void setC_5_2_a(NXaberration c_5_2_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_5_2_b();

	/**
	 *
	 * @param c_5_2_bGroup the c_5_2_bGroup
	 */
	public void setC_5_2_b(NXaberration c_5_2_bGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_5_4_a();

	/**
	 *
	 * @param c_5_4_aGroup the c_5_4_aGroup
	 */
	public void setC_5_4_a(NXaberration c_5_4_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_5_4_b();

	/**
	 *
	 * @param c_5_4_bGroup the c_5_4_bGroup
	 */
	public void setC_5_4_b(NXaberration c_5_4_bGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_5_6_a();

	/**
	 *
	 * @param c_5_6_aGroup the c_5_6_aGroup
	 */
	public void setC_5_6_a(NXaberration c_5_6_aGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXaberration getC_5_6_b();

	/**
	 *
	 * @param c_5_6_bGroup the c_5_6_bGroup
	 */
	public void setC_5_6_b(NXaberration c_5_6_bGroup);

}
