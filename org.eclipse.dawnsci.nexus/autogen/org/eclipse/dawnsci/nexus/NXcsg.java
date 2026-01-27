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
 * Constructive Solid Geometry (CSG) base class.
 * Offers concepts for combining the definitions of leaf and
 * branching nodes of a CSG tree.
 *
 */
public interface NXcsg extends NXobject {

	public static final String NX_OPERATION = "operation";
	public static final String NX_GEOMETRY = "geometry";
	/**
	 * One of the standard construction solid geometry set operations,
	 * or statement IS_QUADRIC or IS_MESH if the CSG is a pointer to an
	 * instance of a geometry class. Takes values:
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>UNION</b> </li>
	 * <li><b>INTERSECTION</b> </li>
	 * <li><b>DIFFERENCE</b> </li>
	 * <li><b>COMPLEMENT</b> </li>
	 * <li><b>IS_QUADRIC</b> </li>
	 * <li><b>IS_MESH</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOperation();

	/**
	 * One of the standard construction solid geometry set operations,
	 * or statement IS_QUADRIC or IS_MESH if the CSG is a pointer to an
	 * instance of a geometry class. Takes values:
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>UNION</b> </li>
	 * <li><b>INTERSECTION</b> </li>
	 * <li><b>DIFFERENCE</b> </li>
	 * <li><b>COMPLEMENT</b> </li>
	 * <li><b>IS_QUADRIC</b> </li>
	 * <li><b>IS_MESH</b> </li></ul></p>
	 * </p>
	 *
	 * @param operationDataset the operationDataset
	 */
	public DataNode setOperation(IDataset operationDataset);

	/**
	 * One of the standard construction solid geometry set operations,
	 * or statement IS_QUADRIC or IS_MESH if the CSG is a pointer to an
	 * instance of a geometry class. Takes values:
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>UNION</b> </li>
	 * <li><b>INTERSECTION</b> </li>
	 * <li><b>DIFFERENCE</b> </li>
	 * <li><b>COMPLEMENT</b> </li>
	 * <li><b>IS_QUADRIC</b> </li>
	 * <li><b>IS_MESH</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getOperationScalar();

	/**
	 * One of the standard construction solid geometry set operations,
	 * or statement IS_QUADRIC or IS_MESH if the CSG is a pointer to an
	 * instance of a geometry class. Takes values:
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>UNION</b> </li>
	 * <li><b>INTERSECTION</b> </li>
	 * <li><b>DIFFERENCE</b> </li>
	 * <li><b>COMPLEMENT</b> </li>
	 * <li><b>IS_QUADRIC</b> </li>
	 * <li><b>IS_MESH</b> </li></ul></p>
	 * </p>
	 *
	 * @param operation the operation
	 */
	public DataNode setOperationScalar(String operationValue);

	/**
	 * The first operand of constructive solid geometry operation.
	 * Compulsory if 'operation' is UNION, INTERSECTION,
	 * DIFFERENCE or COMPLEMENT.
	 *
	 * @return  the value.
	 */
	public NXcsg getA();

	/**
	 * The first operand of constructive solid geometry operation.
	 * Compulsory if 'operation' is UNION, INTERSECTION,
	 * DIFFERENCE or COMPLEMENT.
	 *
	 * @param aGroup the aGroup
	 */
	public void setA(NXcsg aGroup);

	/**
	 * The second operand of constructive solid geometry operation.
	 * Compulsory if 'operation' is UNION, INTERSECTION or
	 * DIFFERENCE.
	 *
	 * @return  the value.
	 */
	public NXcsg getB();

	/**
	 * The second operand of constructive solid geometry operation.
	 * Compulsory if 'operation' is UNION, INTERSECTION or
	 * DIFFERENCE.
	 *
	 * @param bGroup the bGroup
	 */
	public void setB(NXcsg bGroup);

	/**
	 * Path to a field that is an instance of one of several possible geometry
	 * classes: Specifically, :ref:`NXquadric` if 'operation' is IS_QUADRIC,
	 * :ref:`NXoff_geometry`, or other primitive based base classes
	 * if 'operation' is IS_MESH.
	 * The instance defines the surface making up the constructive solid
	 * geometry component. This field is compulsory if 'operation' is
	 * IS_QUADRIC or IS_MESH.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getGeometry();

	/**
	 * Path to a field that is an instance of one of several possible geometry
	 * classes: Specifically, :ref:`NXquadric` if 'operation' is IS_QUADRIC,
	 * :ref:`NXoff_geometry`, or other primitive based base classes
	 * if 'operation' is IS_MESH.
	 * The instance defines the surface making up the constructive solid
	 * geometry component. This field is compulsory if 'operation' is
	 * IS_QUADRIC or IS_MESH.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param geometryDataset the geometryDataset
	 */
	public DataNode setGeometry(IDataset geometryDataset);

	/**
	 * Path to a field that is an instance of one of several possible geometry
	 * classes: Specifically, :ref:`NXquadric` if 'operation' is IS_QUADRIC,
	 * :ref:`NXoff_geometry`, or other primitive based base classes
	 * if 'operation' is IS_MESH.
	 * The instance defines the surface making up the constructive solid
	 * geometry component. This field is compulsory if 'operation' is
	 * IS_QUADRIC or IS_MESH.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getGeometryScalar();

	/**
	 * Path to a field that is an instance of one of several possible geometry
	 * classes: Specifically, :ref:`NXquadric` if 'operation' is IS_QUADRIC,
	 * :ref:`NXoff_geometry`, or other primitive based base classes
	 * if 'operation' is IS_MESH.
	 * The instance defines the surface making up the constructive solid
	 * geometry component. This field is compulsory if 'operation' is
	 * IS_QUADRIC or IS_MESH.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param geometry the geometry
	 */
	public DataNode setGeometryScalar(String geometryValue);

}
