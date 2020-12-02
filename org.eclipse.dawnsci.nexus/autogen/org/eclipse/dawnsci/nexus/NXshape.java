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
 * legacy class - (used by :ref:`NXgeometry`) - the shape and size of a component.
 * This is the description of the general shape and size of a
 * component, which may be made up of ``numobj`` separate
 * elements - it is used by the :ref:`NXgeometry` class
 * 
 */
public interface NXshape extends NXobject {

	public static final String NX_SHAPE = "shape";
	public static final String NX_SIZE = "size";
	public static final String NX_DIRECTION = "direction";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * general shape of a component
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nxflat</b> </li>
	 * <li><b>nxcylinder</b> </li>
	 * <li><b>nxbox</b> </li>
	 * <li><b>nxsphere</b> </li>
	 * <li><b>nxcone</b> </li>
	 * <li><b>nxelliptical</b> </li>
	 * <li><b>nxtoroidal</b> </li>
	 * <li><b>nxparabolic</b> </li>
	 * <li><b>nxpolynomial</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getShape();
	
	/**
	 * general shape of a component
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nxflat</b> </li>
	 * <li><b>nxcylinder</b> </li>
	 * <li><b>nxbox</b> </li>
	 * <li><b>nxsphere</b> </li>
	 * <li><b>nxcone</b> </li>
	 * <li><b>nxelliptical</b> </li>
	 * <li><b>nxtoroidal</b> </li>
	 * <li><b>nxparabolic</b> </li>
	 * <li><b>nxpolynomial</b> </li></ul></p>
	 * </p>
	 * 
	 * @param shapeDataset the shapeDataset
	 */
	public DataNode setShape(IDataset shapeDataset);

	/**
	 * general shape of a component
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nxflat</b> </li>
	 * <li><b>nxcylinder</b> </li>
	 * <li><b>nxbox</b> </li>
	 * <li><b>nxsphere</b> </li>
	 * <li><b>nxcone</b> </li>
	 * <li><b>nxelliptical</b> </li>
	 * <li><b>nxtoroidal</b> </li>
	 * <li><b>nxparabolic</b> </li>
	 * <li><b>nxpolynomial</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getShapeScalar();

	/**
	 * general shape of a component
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nxflat</b> </li>
	 * <li><b>nxcylinder</b> </li>
	 * <li><b>nxbox</b> </li>
	 * <li><b>nxsphere</b> </li>
	 * <li><b>nxcone</b> </li>
	 * <li><b>nxelliptical</b> </li>
	 * <li><b>nxtoroidal</b> </li>
	 * <li><b>nxparabolic</b> </li>
	 * <li><b>nxpolynomial</b> </li></ul></p>
	 * </p>
	 * 
	 * @param shape the shape
	 */
	public DataNode setShapeScalar(String shapeValue);

	/**
	 * physical extent of the object along its local axes (after NXorientation)
	 * with the center of mass at the local origin (after NXtranslation).
	 * The meaning and location of these axes will vary according to the value
	 * of the "shape" variable.
	 * ``nshapepar`` defines how many parameters:
	 * - For "nxcylinder" type the parameters are (diameter,height) and a three value orientation vector of the cylinder.
	 * - For the "nxbox" type the parameters are (length,width,height).
	 * - For the "nxsphere" type the parameters are (diameter).
	 * - For nxcone cone half aperture
	 * - For nxelliptical, semi-major axis, semi-minor-axis, angle of major axis and pole
	 * - For nxtoroidal, major radius, minor radius
	 * - For nxparabolic, parabolic parameter a
	 * - For nxpolynomial, an array of polynom coefficients, the dimension of the array
	 * encodes the degree of the polynom
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: numobj; 2: nshapepar;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSize();
	
	/**
	 * physical extent of the object along its local axes (after NXorientation)
	 * with the center of mass at the local origin (after NXtranslation).
	 * The meaning and location of these axes will vary according to the value
	 * of the "shape" variable.
	 * ``nshapepar`` defines how many parameters:
	 * - For "nxcylinder" type the parameters are (diameter,height) and a three value orientation vector of the cylinder.
	 * - For the "nxbox" type the parameters are (length,width,height).
	 * - For the "nxsphere" type the parameters are (diameter).
	 * - For nxcone cone half aperture
	 * - For nxelliptical, semi-major axis, semi-minor-axis, angle of major axis and pole
	 * - For nxtoroidal, major radius, minor radius
	 * - For nxparabolic, parabolic parameter a
	 * - For nxpolynomial, an array of polynom coefficients, the dimension of the array
	 * encodes the degree of the polynom
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: numobj; 2: nshapepar;
	 * </p>
	 * 
	 * @param sizeDataset the sizeDataset
	 */
	public DataNode setSize(IDataset sizeDataset);

	/**
	 * physical extent of the object along its local axes (after NXorientation)
	 * with the center of mass at the local origin (after NXtranslation).
	 * The meaning and location of these axes will vary according to the value
	 * of the "shape" variable.
	 * ``nshapepar`` defines how many parameters:
	 * - For "nxcylinder" type the parameters are (diameter,height) and a three value orientation vector of the cylinder.
	 * - For the "nxbox" type the parameters are (length,width,height).
	 * - For the "nxsphere" type the parameters are (diameter).
	 * - For nxcone cone half aperture
	 * - For nxelliptical, semi-major axis, semi-minor-axis, angle of major axis and pole
	 * - For nxtoroidal, major radius, minor radius
	 * - For nxparabolic, parabolic parameter a
	 * - For nxpolynomial, an array of polynom coefficients, the dimension of the array
	 * encodes the degree of the polynom
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: numobj; 2: nshapepar;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSizeScalar();

	/**
	 * physical extent of the object along its local axes (after NXorientation)
	 * with the center of mass at the local origin (after NXtranslation).
	 * The meaning and location of these axes will vary according to the value
	 * of the "shape" variable.
	 * ``nshapepar`` defines how many parameters:
	 * - For "nxcylinder" type the parameters are (diameter,height) and a three value orientation vector of the cylinder.
	 * - For the "nxbox" type the parameters are (length,width,height).
	 * - For the "nxsphere" type the parameters are (diameter).
	 * - For nxcone cone half aperture
	 * - For nxelliptical, semi-major axis, semi-minor-axis, angle of major axis and pole
	 * - For nxtoroidal, major radius, minor radius
	 * - For nxparabolic, parabolic parameter a
	 * - For nxpolynomial, an array of polynom coefficients, the dimension of the array
	 * encodes the degree of the polynom
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: numobj; 2: nshapepar;
	 * </p>
	 * 
	 * @param size the size
	 */
	public DataNode setSizeScalar(Double sizeValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>concave</b> </li>
	 * <li><b>convex</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDirection();
	
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>concave</b> </li>
	 * <li><b>convex</b> </li></ul></p>
	 * </p>
	 * 
	 * @param directionDataset the directionDataset
	 */
	public DataNode setDirection(IDataset directionDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>concave</b> </li>
	 * <li><b>convex</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDirectionScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>concave</b> </li>
	 * <li><b>convex</b> </li></ul></p>
	 * </p>
	 * 
	 * @param direction the direction
	 */
	public DataNode setDirectionScalar(String directionValue);

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
