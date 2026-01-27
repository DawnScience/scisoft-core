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


/**
 * Base class for documenting a measurement with an electron microscope.
 *
 */
public interface NXem_measurement extends NXobject {

	/**
	 *
	 * @return  the value.
	 */
	public NXem_instrument getInstrument();

	/**
	 *
	 * @param instrumentGroup the instrumentGroup
	 */
	public void setInstrument(NXem_instrument instrumentGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXem_event_data getEventid();

	/**
	 *
	 * @param eventidGroup the eventidGroup
	 */
	public void setEventid(NXem_event_data eventidGroup);

}
