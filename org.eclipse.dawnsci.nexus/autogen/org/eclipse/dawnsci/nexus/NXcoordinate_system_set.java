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


/**
 * Container to hold different coordinate systems conventions.
 * It is the purpose of this base class to define these conventions and
 * offer a place to store mappings between different coordinate systems
 * which are relevant for the interpretation of the data described
 * by the application definition and base class instances.
 * For each Cartesian coordinate system users should use a set of
 * NXtransformations:
 * * These should define the three base vectors.
 * * The location of the origin.
 * * The affine transformations which bring each axis of this coordinate system
 * into registration with the McStas coordinate system.
 * * Equally, affine transformations should be given for the inverse mapping.
 * As an example one may take an experiment or computer simulation where
 * there is a laboratory (lab) coordinate system, a sample/specimen coordinate
 * system, a crystal coordinate system, and additional coordinate systems,
 * which are eventually attached to components of the instrument.
 * If no additional transformation is specified in this group or if an
 * instance of an NXcoordinate_system_set is absent it should be assumed
 * the so-called McStas coordinate system is used.
 * Many application definitions in NeXus refer to this `McStas <https://mailman2.mcstas.org/pipermail/mcstas-users/2021q2/001431.html>`_ coordinate system.
 * This is a Cartesian coordinate system whose z axis points along the neutron
 * propagation axis. The systems y axis is vertical up, while the x axis points
 * left when looking along the z-axis. Thus, McStas is a right-handed coordinate system.
 * Within each NXtransformations a depends_on section is required. The depends_on
 * field specifies if the coordinate system is the root/reference
 * (which is indicated by writing "." in the depends_on section.)
 * 
 */
public interface NXcoordinate_system_set extends NXobject {

	/**
	 * A group of transformations which specify:
	 * * Three base vectors of the coordinate system.
	 * * Origin of the coordinate system.
	 * * A depends_on keyword. Its value can be "." or the name of an
	 * NXtransformations instance which needs to exist in the
	 * NXcoordinate_system_set instance.
	 * * If the coordinate system is the reference one it has to be named
	 * reference.
	 * In case of having more than one NXtransformations there has to be for
	 * each additional coordinate system, i.e. the one not the reference:
	 * * A set of translations and rotations which map each base vector to the reference.
	 * * A set of translations and rotations which map each reference base vector
	 * to the coordinate system.
	 * The NXtransformations for these mappings need to be formatted
	 * according to the descriptions in NXtransformations.
	 * 
	 * @return  the value.
	 */
	public NXtransformations getTransformations();
	
	/**
	 * A group of transformations which specify:
	 * * Three base vectors of the coordinate system.
	 * * Origin of the coordinate system.
	 * * A depends_on keyword. Its value can be "." or the name of an
	 * NXtransformations instance which needs to exist in the
	 * NXcoordinate_system_set instance.
	 * * If the coordinate system is the reference one it has to be named
	 * reference.
	 * In case of having more than one NXtransformations there has to be for
	 * each additional coordinate system, i.e. the one not the reference:
	 * * A set of translations and rotations which map each base vector to the reference.
	 * * A set of translations and rotations which map each reference base vector
	 * to the coordinate system.
	 * The NXtransformations for these mappings need to be formatted
	 * according to the descriptions in NXtransformations.
	 * 
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * A group of transformations which specify:
	 * * Three base vectors of the coordinate system.
	 * * Origin of the coordinate system.
	 * * A depends_on keyword. Its value can be "." or the name of an
	 * NXtransformations instance which needs to exist in the
	 * NXcoordinate_system_set instance.
	 * * If the coordinate system is the reference one it has to be named
	 * reference.
	 * In case of having more than one NXtransformations there has to be for
	 * each additional coordinate system, i.e. the one not the reference:
	 * * A set of translations and rotations which map each base vector to the reference.
	 * * A set of translations and rotations which map each reference base vector
	 * to the coordinate system.
	 * The NXtransformations for these mappings need to be formatted
	 * according to the descriptions in NXtransformations.</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);
	
	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * A group of transformations which specify:
	 * * Three base vectors of the coordinate system.
	 * * Origin of the coordinate system.
	 * * A depends_on keyword. Its value can be "." or the name of an
	 * NXtransformations instance which needs to exist in the
	 * NXcoordinate_system_set instance.
	 * * If the coordinate system is the reference one it has to be named
	 * reference.
	 * In case of having more than one NXtransformations there has to be for
	 * each additional coordinate system, i.e. the one not the reference:
	 * * A set of translations and rotations which map each base vector to the reference.
	 * * A set of translations and rotations which map each reference base vector
	 * to the coordinate system.
	 * The NXtransformations for these mappings need to be formatted
	 * according to the descriptions in NXtransformations.</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);
	
	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * A group of transformations which specify:
	 * * Three base vectors of the coordinate system.
	 * * Origin of the coordinate system.
	 * * A depends_on keyword. Its value can be "." or the name of an
	 * NXtransformations instance which needs to exist in the
	 * NXcoordinate_system_set instance.
	 * * If the coordinate system is the reference one it has to be named
	 * reference.
	 * In case of having more than one NXtransformations there has to be for
	 * each additional coordinate system, i.e. the one not the reference:
	 * * A set of translations and rotations which map each base vector to the reference.
	 * * A set of translations and rotations which map each reference base vector
	 * to the coordinate system.
	 * The NXtransformations for these mappings need to be formatted
	 * according to the descriptions in NXtransformations.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A group of transformations which specify:
	 * * Three base vectors of the coordinate system.
	 * * Origin of the coordinate system.
	 * * A depends_on keyword. Its value can be "." or the name of an
	 * NXtransformations instance which needs to exist in the
	 * NXcoordinate_system_set instance.
	 * * If the coordinate system is the reference one it has to be named
	 * reference.
	 * In case of having more than one NXtransformations there has to be for
	 * each additional coordinate system, i.e. the one not the reference:
	 * * A set of translations and rotations which map each base vector to the reference.
	 * * A set of translations and rotations which map each reference base vector
	 * to the coordinate system.
	 * The NXtransformations for these mappings need to be formatted
	 * according to the descriptions in NXtransformations.</li>
	 * </ul>
	 * 
	 * @param transformations the child nodes to add 
	 */
	
	public void setAllTransformations(Map<String, NXtransformations> transformations);
	

}
