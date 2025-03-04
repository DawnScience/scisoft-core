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
 * Models for aberrations of electro-magnetic lenses in electron microscopy.
 * See `S. J. Pennycock and P. D. Nellist <https://doi.org/10.1007/978-1-4419-7200-2>`_ (page 44ff, and page 118ff)
 * for different definitions available and further details. Table 7-2 of Ibid.
 * publication (page 305ff) documents how to convert from the NION to the
 * CEOS definitions.
 *
 */
public interface NXaberration_model extends NXobject {

	public static final String NX_MODEL = "model";
	public static final String NX_C_1_0 = "c_1_0";
	public static final String NX_C_1_2_A = "c_1_2_a";
	public static final String NX_C_1_2_B = "c_1_2_b";
	public static final String NX_C_2_1_A = "c_2_1_a";
	public static final String NX_C_2_1_B = "c_2_1_b";
	public static final String NX_C_2_3_A = "c_2_3_a";
	public static final String NX_C_2_3_B = "c_2_3_b";
	public static final String NX_C_3_0 = "c_3_0";
	public static final String NX_C_3_2_A = "c_3_2_a";
	public static final String NX_C_3_2_B = "c_3_2_b";
	public static final String NX_C_3_4_A = "c_3_4_a";
	public static final String NX_C_3_4_B = "c_3_4_b";
	public static final String NX_C_5_0 = "c_5_0";
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ceos</b> </li>
	 * <li><b>nion</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getModel();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ceos</b> </li>
	 * <li><b>nion</b> </li></ul></p>
	 * </p>
	 *
	 * @param modelDataset the modelDataset
	 */
	public DataNode setModel(IDataset modelDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ceos</b> </li>
	 * <li><b>nion</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getModelScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ceos</b> </li>
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
	public NXaberration getAberration();

	/**
	 *
	 * @param aberrationGroup the aberrationGroup
	 */
	public void setAberration(NXaberration aberrationGroup);

	/**
	 * Get a NXaberration node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXaberration for that node.
	 */
	public NXaberration getAberration(String name);

	/**
	 * Set a NXaberration node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param aberration the value to set
	 */
	public void setAberration(String name, NXaberration aberration);

	/**
	 * Get all NXaberration nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXaberration for that node.
	 */
	public Map<String, NXaberration> getAllAberration();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param aberration the child nodes to add
	 */

	public void setAllAberration(Map<String, NXaberration> aberration);


	/**
	 * Defocus
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_1_0();

	/**
	 * Defocus
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_1_0Dataset the c_1_0Dataset
	 */
	public DataNode setC_1_0(IDataset c_1_0Dataset);

	/**
	 * Defocus
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_1_0Scalar();

	/**
	 * Defocus
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_1_0 the c_1_0
	 */
	public DataNode setC_1_0Scalar(Double c_1_0Value);

	/**
	 * Two-fold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_1_2_a();

	/**
	 * Two-fold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_1_2_aDataset the c_1_2_aDataset
	 */
	public DataNode setC_1_2_a(IDataset c_1_2_aDataset);

	/**
	 * Two-fold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_1_2_aScalar();

	/**
	 * Two-fold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_1_2_a the c_1_2_a
	 */
	public DataNode setC_1_2_aScalar(Double c_1_2_aValue);

	/**
	 * Two-fold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_1_2_b();

	/**
	 * Two-fold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_1_2_bDataset the c_1_2_bDataset
	 */
	public DataNode setC_1_2_b(IDataset c_1_2_bDataset);

	/**
	 * Two-fold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_1_2_bScalar();

	/**
	 * Two-fold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_1_2_b the c_1_2_b
	 */
	public DataNode setC_1_2_bScalar(Double c_1_2_bValue);

	/**
	 * Second-order axial coma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_2_1_a();

	/**
	 * Second-order axial coma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_2_1_aDataset the c_2_1_aDataset
	 */
	public DataNode setC_2_1_a(IDataset c_2_1_aDataset);

	/**
	 * Second-order axial coma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_2_1_aScalar();

	/**
	 * Second-order axial coma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_2_1_a the c_2_1_a
	 */
	public DataNode setC_2_1_aScalar(Double c_2_1_aValue);

	/**
	 * Second-order axial coma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_2_1_b();

	/**
	 * Second-order axial coma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_2_1_bDataset the c_2_1_bDataset
	 */
	public DataNode setC_2_1_b(IDataset c_2_1_bDataset);

	/**
	 * Second-order axial coma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_2_1_bScalar();

	/**
	 * Second-order axial coma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_2_1_b the c_2_1_b
	 */
	public DataNode setC_2_1_bScalar(Double c_2_1_bValue);

	/**
	 * Threefold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_2_3_a();

	/**
	 * Threefold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_2_3_aDataset the c_2_3_aDataset
	 */
	public DataNode setC_2_3_a(IDataset c_2_3_aDataset);

	/**
	 * Threefold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_2_3_aScalar();

	/**
	 * Threefold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_2_3_a the c_2_3_a
	 */
	public DataNode setC_2_3_aScalar(Double c_2_3_aValue);

	/**
	 * Threefold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_2_3_b();

	/**
	 * Threefold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_2_3_bDataset the c_2_3_bDataset
	 */
	public DataNode setC_2_3_b(IDataset c_2_3_bDataset);

	/**
	 * Threefold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_2_3_bScalar();

	/**
	 * Threefold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_2_3_b the c_2_3_b
	 */
	public DataNode setC_2_3_bScalar(Double c_2_3_bValue);

	/**
	 * Spherical aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_3_0();

	/**
	 * Spherical aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_3_0Dataset the c_3_0Dataset
	 */
	public DataNode setC_3_0(IDataset c_3_0Dataset);

	/**
	 * Spherical aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_3_0Scalar();

	/**
	 * Spherical aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_3_0 the c_3_0
	 */
	public DataNode setC_3_0Scalar(Double c_3_0Value);

	/**
	 * Star aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_3_2_a();

	/**
	 * Star aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_3_2_aDataset the c_3_2_aDataset
	 */
	public DataNode setC_3_2_a(IDataset c_3_2_aDataset);

	/**
	 * Star aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_3_2_aScalar();

	/**
	 * Star aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_3_2_a the c_3_2_a
	 */
	public DataNode setC_3_2_aScalar(Double c_3_2_aValue);

	/**
	 * Star aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_3_2_b();

	/**
	 * Star aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_3_2_bDataset the c_3_2_bDataset
	 */
	public DataNode setC_3_2_b(IDataset c_3_2_bDataset);

	/**
	 * Star aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_3_2_bScalar();

	/**
	 * Star aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_3_2_b the c_3_2_b
	 */
	public DataNode setC_3_2_bScalar(Double c_3_2_bValue);

	/**
	 * Fourfold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_3_4_a();

	/**
	 * Fourfold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_3_4_aDataset the c_3_4_aDataset
	 */
	public DataNode setC_3_4_a(IDataset c_3_4_aDataset);

	/**
	 * Fourfold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_3_4_aScalar();

	/**
	 * Fourfold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_3_4_a the c_3_4_a
	 */
	public DataNode setC_3_4_aScalar(Double c_3_4_aValue);

	/**
	 * Fourfold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_3_4_b();

	/**
	 * Fourfold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_3_4_bDataset the c_3_4_bDataset
	 */
	public DataNode setC_3_4_b(IDataset c_3_4_bDataset);

	/**
	 * Fourfold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_3_4_bScalar();

	/**
	 * Fourfold astigmatism
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_3_4_b the c_3_4_b
	 */
	public DataNode setC_3_4_bScalar(Double c_3_4_bValue);

	/**
	 * Fifth-order spherical aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC_5_0();

	/**
	 * Fifth-order spherical aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_5_0Dataset the c_5_0Dataset
	 */
	public DataNode setC_5_0(IDataset c_5_0Dataset);

	/**
	 * Fifth-order spherical aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getC_5_0Scalar();

	/**
	 * Fifth-order spherical aberration
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c_5_0 the c_5_0
	 */
	public DataNode setC_5_0Scalar(Double c_5_0Value);

}
