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
 * Geometry and logical description of a detector module. When used, child group to NXdetector.
 * Many detectors consist of multiple
 * smaller modules. Sometimes it is important to know the exact position of such
 * modules.
 * This is the purpose of this group. It is a child group to NXdetector.
 * Note, the pixel size is given as values in the array fast_pixel_direction and slow_pixel_direction.
 * 
 */
public interface NXdetector_module extends NXobject {

	public static final String NX_DATA_ORIGIN = "data_origin";
	public static final String NX_DATA_SIZE = "data_size";
	public static final String NX_MODULE_OFFSET = "module_offset";
	public static final String NX_MODULE_OFFSET_ATTRIBUTE_TRANSFORMATION_TYPE = "transformation_type";
	public static final String NX_MODULE_OFFSET_ATTRIBUTE_VECTOR = "vector";
	public static final String NX_MODULE_OFFSET_ATTRIBUTE_OFFSET = "offset";
	public static final String NX_MODULE_OFFSET_ATTRIBUTE_OFFSET_UNITS = "offset_units";
	public static final String NX_MODULE_OFFSET_ATTRIBUTE_DEPENDS_ON = "depends_on";
	public static final String NX_FAST_PIXEL_DIRECTION = "fast_pixel_direction";
	public static final String NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_TRANSFORMATION_TYPE = "transformation_type";
	public static final String NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_VECTOR = "vector";
	public static final String NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_OFFSET = "offset";
	public static final String NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_OFFSET_UNITS = "offset_units";
	public static final String NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_DEPENDS_ON = "depends_on";
	public static final String NX_SLOW_PIXEL_DIRECTION = "slow_pixel_direction";
	public static final String NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_TRANSFORMATION_TYPE = "transformation_type";
	public static final String NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_VECTOR = "vector";
	public static final String NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_OFFSET = "offset";
	public static final String NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_OFFSET_UNITS = "offset_units";
	public static final String NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_DEPENDS_ON = "depends_on";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * A dimension-2 or dimension-3 field which gives the indices
	 * of the origin of the hyperslab of data for this module in the
	 * main area detector image in the parent NXdetector module.
	 * The data_origin is 0-based.
	 * The frame number dimension (np) is omitted. Thus the
	 * data_origin field for a dimension-2 dataset with indices (np, i, j)
	 * will be an array with indices (i, j), and for a dimension-3
	 * dataset with indices (np, i, j, k) will be an array with indices
	 * (i, j, k).
	 * The :ref:`order <Design-ArrayStorageOrder>` of indices (i, j or i, j, k) is slow to fast.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getData_origin();
	
	/**
	 * A dimension-2 or dimension-3 field which gives the indices
	 * of the origin of the hyperslab of data for this module in the
	 * main area detector image in the parent NXdetector module.
	 * The data_origin is 0-based.
	 * The frame number dimension (np) is omitted. Thus the
	 * data_origin field for a dimension-2 dataset with indices (np, i, j)
	 * will be an array with indices (i, j), and for a dimension-3
	 * dataset with indices (np, i, j, k) will be an array with indices
	 * (i, j, k).
	 * The :ref:`order <Design-ArrayStorageOrder>` of indices (i, j or i, j, k) is slow to fast.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param data_originDataset the data_originDataset
	 */
	public DataNode setData_origin(IDataset data_originDataset);

	/**
	 * A dimension-2 or dimension-3 field which gives the indices
	 * of the origin of the hyperslab of data for this module in the
	 * main area detector image in the parent NXdetector module.
	 * The data_origin is 0-based.
	 * The frame number dimension (np) is omitted. Thus the
	 * data_origin field for a dimension-2 dataset with indices (np, i, j)
	 * will be an array with indices (i, j), and for a dimension-3
	 * dataset with indices (np, i, j, k) will be an array with indices
	 * (i, j, k).
	 * The :ref:`order <Design-ArrayStorageOrder>` of indices (i, j or i, j, k) is slow to fast.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getData_originScalar();

	/**
	 * A dimension-2 or dimension-3 field which gives the indices
	 * of the origin of the hyperslab of data for this module in the
	 * main area detector image in the parent NXdetector module.
	 * The data_origin is 0-based.
	 * The frame number dimension (np) is omitted. Thus the
	 * data_origin field for a dimension-2 dataset with indices (np, i, j)
	 * will be an array with indices (i, j), and for a dimension-3
	 * dataset with indices (np, i, j, k) will be an array with indices
	 * (i, j, k).
	 * The :ref:`order <Design-ArrayStorageOrder>` of indices (i, j or i, j, k) is slow to fast.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param data_origin the data_origin
	 */
	public DataNode setData_originScalar(Long data_originValue);

	/**
	 * Two or three values for the size of the module in pixels in
	 * each direction. Dimensionality and order of indices is the
	 * same as for data_origin.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getData_size();
	
	/**
	 * Two or three values for the size of the module in pixels in
	 * each direction. Dimensionality and order of indices is the
	 * same as for data_origin.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param data_sizeDataset the data_sizeDataset
	 */
	public DataNode setData_size(IDataset data_sizeDataset);

	/**
	 * Two or three values for the size of the module in pixels in
	 * each direction. Dimensionality and order of indices is the
	 * same as for data_origin.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getData_sizeScalar();

	/**
	 * Two or three values for the size of the module in pixels in
	 * each direction. Dimensionality and order of indices is the
	 * same as for data_origin.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param data_size the data_size
	 */
	public DataNode setData_sizeScalar(Long data_sizeValue);

	/**
	 * Offset of the module in regards to the origin of the detector in an
	 * arbitrary direction.
	 * <p>
	 * <b>Units:</b> NX_LENGTH
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getModule_offset();
	
	/**
	 * Offset of the module in regards to the origin of the detector in an
	 * arbitrary direction.
	 * <p>
	 * <b>Units:</b> NX_LENGTH
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param module_offsetDataset the module_offsetDataset
	 */
	public DataNode setModule_offset(IDataset module_offsetDataset);

	/**
	 * Offset of the module in regards to the origin of the detector in an
	 * arbitrary direction.
	 * <p>
	 * <b>Units:</b> NX_LENGTH
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getModule_offsetScalar();

	/**
	 * Offset of the module in regards to the origin of the detector in an
	 * arbitrary direction.
	 * <p>
	 * <b>Units:</b> NX_LENGTH
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param module_offset the module_offset
	 */
	public DataNode setModule_offsetScalar(Number module_offsetValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>translation</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getModule_offsetAttributeTransformation_type();
	
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>translation</b> </li></ul></p>
	 * </p>
	 * 
	 * @param transformation_typeValue the transformation_typeValue
	 */
	public void setModule_offsetAttributeTransformation_type(String transformation_typeValue);

	/**
	 * Three values that define the axis for this transformation
	 * 
	 * @return  the value.
	 */
	public Number getModule_offsetAttributeVector();
	
	/**
	 * Three values that define the axis for this transformation
	 * 
	 * @param vectorValue the vectorValue
	 */
	public void setModule_offsetAttributeVector(Number vectorValue);

	/**
	 * A fixed offset applied before the transformation (three vector components).
	 * 
	 * @return  the value.
	 */
	public Number getModule_offsetAttributeOffset();
	
	/**
	 * A fixed offset applied before the transformation (three vector components).
	 * 
	 * @param offsetValue the offsetValue
	 */
	public void setModule_offsetAttributeOffset(Number offsetValue);

	/**
	 * Units of the offset.
	 * 
	 * @return  the value.
	 */
	public String getModule_offsetAttributeOffset_units();
	
	/**
	 * Units of the offset.
	 * 
	 * @param offset_unitsValue the offset_unitsValue
	 */
	public void setModule_offsetAttributeOffset_units(String offset_unitsValue);

	/**
	 * Points to the path of the next element in the geometry chain.
	 * 
	 * @return  the value.
	 */
	public String getModule_offsetAttributeDepends_on();
	
	/**
	 * Points to the path of the next element in the geometry chain.
	 * 
	 * @param depends_onValue the depends_onValue
	 */
	public void setModule_offsetAttributeDepends_on(String depends_onValue);

	/**
	 * Values along the direction of :ref:`fastest varying <Design-ArrayStorageOrder>` :index:`pixel direction<dimension; fastest varying>`. Each value in this
	 * array is the size of a pixel in the units specified. Alternatively, if only one
	 * value is given, all pixels in this direction have the same value. The direction
	 * itself is given through the vector attribute.
	 * <p>
	 * <b>Units:</b> NX_LENGTH
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFast_pixel_direction();
	
	/**
	 * Values along the direction of :ref:`fastest varying <Design-ArrayStorageOrder>` :index:`pixel direction<dimension; fastest varying>`. Each value in this
	 * array is the size of a pixel in the units specified. Alternatively, if only one
	 * value is given, all pixels in this direction have the same value. The direction
	 * itself is given through the vector attribute.
	 * <p>
	 * <b>Units:</b> NX_LENGTH
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param fast_pixel_directionDataset the fast_pixel_directionDataset
	 */
	public DataNode setFast_pixel_direction(IDataset fast_pixel_directionDataset);

	/**
	 * Values along the direction of :ref:`fastest varying <Design-ArrayStorageOrder>` :index:`pixel direction<dimension; fastest varying>`. Each value in this
	 * array is the size of a pixel in the units specified. Alternatively, if only one
	 * value is given, all pixels in this direction have the same value. The direction
	 * itself is given through the vector attribute.
	 * <p>
	 * <b>Units:</b> NX_LENGTH
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getFast_pixel_directionScalar();

	/**
	 * Values along the direction of :ref:`fastest varying <Design-ArrayStorageOrder>` :index:`pixel direction<dimension; fastest varying>`. Each value in this
	 * array is the size of a pixel in the units specified. Alternatively, if only one
	 * value is given, all pixels in this direction have the same value. The direction
	 * itself is given through the vector attribute.
	 * <p>
	 * <b>Units:</b> NX_LENGTH
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param fast_pixel_direction the fast_pixel_direction
	 */
	public DataNode setFast_pixel_directionScalar(Number fast_pixel_directionValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>translation</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getFast_pixel_directionAttributeTransformation_type();
	
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>translation</b> </li></ul></p>
	 * </p>
	 * 
	 * @param transformation_typeValue the transformation_typeValue
	 */
	public void setFast_pixel_directionAttributeTransformation_type(String transformation_typeValue);

	/**
	 * Three values that define the axis for this transformation
	 * 
	 * @return  the value.
	 */
	public Number getFast_pixel_directionAttributeVector();
	
	/**
	 * Three values that define the axis for this transformation
	 * 
	 * @param vectorValue the vectorValue
	 */
	public void setFast_pixel_directionAttributeVector(Number vectorValue);

	/**
	 * A fixed offset applied before the transformation (three vector components).
	 * 
	 * @return  the value.
	 */
	public Number getFast_pixel_directionAttributeOffset();
	
	/**
	 * A fixed offset applied before the transformation (three vector components).
	 * 
	 * @param offsetValue the offsetValue
	 */
	public void setFast_pixel_directionAttributeOffset(Number offsetValue);

	/**
	 * Units of the offset.
	 * 
	 * @return  the value.
	 */
	public String getFast_pixel_directionAttributeOffset_units();
	
	/**
	 * Units of the offset.
	 * 
	 * @param offset_unitsValue the offset_unitsValue
	 */
	public void setFast_pixel_directionAttributeOffset_units(String offset_unitsValue);

	/**
	 * Points to the path of the next element in the geometry chain.
	 * 
	 * @return  the value.
	 */
	public String getFast_pixel_directionAttributeDepends_on();
	
	/**
	 * Points to the path of the next element in the geometry chain.
	 * 
	 * @param depends_onValue the depends_onValue
	 */
	public void setFast_pixel_directionAttributeDepends_on(String depends_onValue);

	/**
	 * Values along the direction of :ref:`slowest varying<Design-ArrayStorageOrder>` :index:`pixel direction<dimension; slowest varying>`. Each value in this
	 * array is the size of a pixel in the units specified. Alternatively, if only one
	 * value is given, all pixels in this direction have the same value. The direction
	 * itself is given through the vector attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSlow_pixel_direction();
	
	/**
	 * Values along the direction of :ref:`slowest varying<Design-ArrayStorageOrder>` :index:`pixel direction<dimension; slowest varying>`. Each value in this
	 * array is the size of a pixel in the units specified. Alternatively, if only one
	 * value is given, all pixels in this direction have the same value. The direction
	 * itself is given through the vector attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param slow_pixel_directionDataset the slow_pixel_directionDataset
	 */
	public DataNode setSlow_pixel_direction(IDataset slow_pixel_directionDataset);

	/**
	 * Values along the direction of :ref:`slowest varying<Design-ArrayStorageOrder>` :index:`pixel direction<dimension; slowest varying>`. Each value in this
	 * array is the size of a pixel in the units specified. Alternatively, if only one
	 * value is given, all pixels in this direction have the same value. The direction
	 * itself is given through the vector attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getSlow_pixel_directionScalar();

	/**
	 * Values along the direction of :ref:`slowest varying<Design-ArrayStorageOrder>` :index:`pixel direction<dimension; slowest varying>`. Each value in this
	 * array is the size of a pixel in the units specified. Alternatively, if only one
	 * value is given, all pixels in this direction have the same value. The direction
	 * itself is given through the vector attribute.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param slow_pixel_direction the slow_pixel_direction
	 */
	public DataNode setSlow_pixel_directionScalar(Number slow_pixel_directionValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>translation</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getSlow_pixel_directionAttributeTransformation_type();
	
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>translation</b> </li></ul></p>
	 * </p>
	 * 
	 * @param transformation_typeValue the transformation_typeValue
	 */
	public void setSlow_pixel_directionAttributeTransformation_type(String transformation_typeValue);

	/**
	 * Three values that define the axis for this transformation
	 * 
	 * @return  the value.
	 */
	public Number getSlow_pixel_directionAttributeVector();
	
	/**
	 * Three values that define the axis for this transformation
	 * 
	 * @param vectorValue the vectorValue
	 */
	public void setSlow_pixel_directionAttributeVector(Number vectorValue);

	/**
	 * A fixed offset applied before the transformation (three vector components).
	 * 
	 * @return  the value.
	 */
	public Number getSlow_pixel_directionAttributeOffset();
	
	/**
	 * A fixed offset applied before the transformation (three vector components).
	 * 
	 * @param offsetValue the offsetValue
	 */
	public void setSlow_pixel_directionAttributeOffset(Number offsetValue);

	/**
	 * Units of the offset.
	 * 
	 * @return  the value.
	 */
	public String getSlow_pixel_directionAttributeOffset_units();
	
	/**
	 * Units of the offset.
	 * 
	 * @param offset_unitsValue the offset_unitsValue
	 */
	public void setSlow_pixel_directionAttributeOffset_units(String offset_unitsValue);

	/**
	 * Points to the path of the next element in the geometry chain.
	 * 
	 * @return  the value.
	 */
	public String getSlow_pixel_directionAttributeDepends_on();
	
	/**
	 * Points to the path of the next element in the geometry chain.
	 * 
	 * @param depends_onValue the depends_onValue
	 */
	public void setSlow_pixel_directionAttributeDepends_on(String depends_onValue);

	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @return  the value.
	 */
	public String getAttributeDefault();
	
	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @param defaultValue the defaultValue
	 */
	public void setAttributeDefault(String defaultValue);

}
