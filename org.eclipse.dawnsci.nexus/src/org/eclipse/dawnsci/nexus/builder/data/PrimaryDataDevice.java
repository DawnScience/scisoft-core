/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.dawnsci.nexus.builder.data;

import java.util.List;

import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXobject;

/**
 * Represents the primary data device to add to an {@link NexusDataBuilder} in order to
 * build an {@link NXdata} group. This primary device is the one that contains the
 * <code>@signal</code> field.
 *
 * @param <N> the sub-interface of {@link NXobject} that the nexus object was created from 
 * 
 * @author Matthew Dickie
 */
public interface PrimaryDataDevice<N extends NXobject> extends DataDevice<N> {
	
	/**
	 * The name of the field to make the signal field of the {@link NXdata} group, 
	 * i.e. the name of the main plottable dataset.
	 * @return
	 */
	public String getSignalFieldSourceName();
	
	/**
	 * The names of any fields to make auxiliary signal fields of the {@link NXdata} group,
	 * i.e. additional plottable datasets.
	 * @return
	 */
	public List<String> getAuxiliarySignalFieldSourceNames();
	
	/**
	 * Overrides the {@link DataDevice#isPrimary()} to return <code>true</code> always, as this
	 * is a primary device. 
	 * 
	 * @see org.eclipse.dawnsci.nexus.builder.data.DataDevice#isPrimary()
	 * @return <code>true</code>, always
	 */
	public default boolean isPrimary() {
		return true; // returns true always 
	}
	
}