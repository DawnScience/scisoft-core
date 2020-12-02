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
 * definition of a quadric surface.
 * 
 */
public interface NXquadric extends NXobject {

	public static final String NX_PARAMETERS = "parameters";
	public static final String NX_SURFACE_TYPE = "surface_type";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Ten real values of the matrix that defines the quadric surface
	 * in projective space. Ordered Q11, Q12, Q13, Q22, Q23, Q33, P1,
	 * P2, P3, R. Takes a units attribute of dimension reciprocal
	 * length. R is scalar. P has dimension reciprocal length, and the
	 * given units. Q has dimension reciprocal length squared, and
	 * units the square of those given.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_PER_LENGTH
	 * <b>Dimensions:</b> 1: 10;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getParameters();
	
	/**
	 * Ten real values of the matrix that defines the quadric surface
	 * in projective space. Ordered Q11, Q12, Q13, Q22, Q23, Q33, P1,
	 * P2, P3, R. Takes a units attribute of dimension reciprocal
	 * length. R is scalar. P has dimension reciprocal length, and the
	 * given units. Q has dimension reciprocal length squared, and
	 * units the square of those given.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_PER_LENGTH
	 * <b>Dimensions:</b> 1: 10;
	 * </p>
	 * 
	 * @param parametersDataset the parametersDataset
	 */
	public DataNode setParameters(IDataset parametersDataset);

	/**
	 * Ten real values of the matrix that defines the quadric surface
	 * in projective space. Ordered Q11, Q12, Q13, Q22, Q23, Q33, P1,
	 * P2, P3, R. Takes a units attribute of dimension reciprocal
	 * length. R is scalar. P has dimension reciprocal length, and the
	 * given units. Q has dimension reciprocal length squared, and
	 * units the square of those given.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_PER_LENGTH
	 * <b>Dimensions:</b> 1: 10;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getParametersScalar();

	/**
	 * Ten real values of the matrix that defines the quadric surface
	 * in projective space. Ordered Q11, Q12, Q13, Q22, Q23, Q33, P1,
	 * P2, P3, R. Takes a units attribute of dimension reciprocal
	 * length. R is scalar. P has dimension reciprocal length, and the
	 * given units. Q has dimension reciprocal length squared, and
	 * units the square of those given.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_PER_LENGTH
	 * <b>Dimensions:</b> 1: 10;
	 * </p>
	 * 
	 * @param parameters the parameters
	 */
	public DataNode setParametersScalar(Number parametersValue);

	/**
	 * An optional description of the form of the quadric surface:
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ELLIPSOID</b> </li>
	 * <li><b>ELLIPTIC_PARABOLOID</b> </li>
	 * <li><b>HYPERBOLIC_PARABOLOID</b> </li>
	 * <li><b>ELLIPTIC_HYPERBOLOID_OF_1_SHEET</b> </li>
	 * <li><b>ELLIPTIC_HYPERBOLOID_OF_2_SHEETS</b> </li>
	 * <li><b>ELLIPTIC_CONE</b> </li>
	 * <li><b>ELLIPTIC_CYLINDER</b> </li>
	 * <li><b>HYPERBOLIC_CYLINDER</b> </li>
	 * <li><b>PARABOLIC_CYLINDER</b> </li>
	 * <li><b>SPHEROID</b> </li>
	 * <li><b>SPHERE</b> </li>
	 * <li><b>PARABOLOID</b> </li>
	 * <li><b>HYPERBOLOID_1_SHEET</b> </li>
	 * <li><b>HYPERBOLOID_2_SHEET</b> </li>
	 * <li><b>CONE</b> </li>
	 * <li><b>CYLINDER</b> </li>
	 * <li><b>PLANE</b> </li>
	 * <li><b>IMAGINARY</b> </li>
	 * <li><b>UNKNOWN</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSurface_type();
	
	/**
	 * An optional description of the form of the quadric surface:
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ELLIPSOID</b> </li>
	 * <li><b>ELLIPTIC_PARABOLOID</b> </li>
	 * <li><b>HYPERBOLIC_PARABOLOID</b> </li>
	 * <li><b>ELLIPTIC_HYPERBOLOID_OF_1_SHEET</b> </li>
	 * <li><b>ELLIPTIC_HYPERBOLOID_OF_2_SHEETS</b> </li>
	 * <li><b>ELLIPTIC_CONE</b> </li>
	 * <li><b>ELLIPTIC_CYLINDER</b> </li>
	 * <li><b>HYPERBOLIC_CYLINDER</b> </li>
	 * <li><b>PARABOLIC_CYLINDER</b> </li>
	 * <li><b>SPHEROID</b> </li>
	 * <li><b>SPHERE</b> </li>
	 * <li><b>PARABOLOID</b> </li>
	 * <li><b>HYPERBOLOID_1_SHEET</b> </li>
	 * <li><b>HYPERBOLOID_2_SHEET</b> </li>
	 * <li><b>CONE</b> </li>
	 * <li><b>CYLINDER</b> </li>
	 * <li><b>PLANE</b> </li>
	 * <li><b>IMAGINARY</b> </li>
	 * <li><b>UNKNOWN</b> </li></ul></p>
	 * </p>
	 * 
	 * @param surface_typeDataset the surface_typeDataset
	 */
	public DataNode setSurface_type(IDataset surface_typeDataset);

	/**
	 * An optional description of the form of the quadric surface:
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ELLIPSOID</b> </li>
	 * <li><b>ELLIPTIC_PARABOLOID</b> </li>
	 * <li><b>HYPERBOLIC_PARABOLOID</b> </li>
	 * <li><b>ELLIPTIC_HYPERBOLOID_OF_1_SHEET</b> </li>
	 * <li><b>ELLIPTIC_HYPERBOLOID_OF_2_SHEETS</b> </li>
	 * <li><b>ELLIPTIC_CONE</b> </li>
	 * <li><b>ELLIPTIC_CYLINDER</b> </li>
	 * <li><b>HYPERBOLIC_CYLINDER</b> </li>
	 * <li><b>PARABOLIC_CYLINDER</b> </li>
	 * <li><b>SPHEROID</b> </li>
	 * <li><b>SPHERE</b> </li>
	 * <li><b>PARABOLOID</b> </li>
	 * <li><b>HYPERBOLOID_1_SHEET</b> </li>
	 * <li><b>HYPERBOLOID_2_SHEET</b> </li>
	 * <li><b>CONE</b> </li>
	 * <li><b>CYLINDER</b> </li>
	 * <li><b>PLANE</b> </li>
	 * <li><b>IMAGINARY</b> </li>
	 * <li><b>UNKNOWN</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getSurface_typeScalar();

	/**
	 * An optional description of the form of the quadric surface:
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>ELLIPSOID</b> </li>
	 * <li><b>ELLIPTIC_PARABOLOID</b> </li>
	 * <li><b>HYPERBOLIC_PARABOLOID</b> </li>
	 * <li><b>ELLIPTIC_HYPERBOLOID_OF_1_SHEET</b> </li>
	 * <li><b>ELLIPTIC_HYPERBOLOID_OF_2_SHEETS</b> </li>
	 * <li><b>ELLIPTIC_CONE</b> </li>
	 * <li><b>ELLIPTIC_CYLINDER</b> </li>
	 * <li><b>HYPERBOLIC_CYLINDER</b> </li>
	 * <li><b>PARABOLIC_CYLINDER</b> </li>
	 * <li><b>SPHEROID</b> </li>
	 * <li><b>SPHERE</b> </li>
	 * <li><b>PARABOLOID</b> </li>
	 * <li><b>HYPERBOLOID_1_SHEET</b> </li>
	 * <li><b>HYPERBOLOID_2_SHEET</b> </li>
	 * <li><b>CONE</b> </li>
	 * <li><b>CYLINDER</b> </li>
	 * <li><b>PLANE</b> </li>
	 * <li><b>IMAGINARY</b> </li>
	 * <li><b>UNKNOWN</b> </li></ul></p>
	 * </p>
	 * 
	 * @param surface_type the surface_type
	 */
	public DataNode setSurface_typeScalar(String surface_typeValue);

	/**
	 * Path to an :ref:`NXtransformations` that defining the axis on
	 * which the orientation of the surface depends.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDepends_on();
	
	/**
	 * Path to an :ref:`NXtransformations` that defining the axis on
	 * which the orientation of the surface depends.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Path to an :ref:`NXtransformations` that defining the axis on
	 * which the orientation of the surface depends.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Path to an :ref:`NXtransformations` that defining the axis on
	 * which the orientation of the surface depends.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

}
