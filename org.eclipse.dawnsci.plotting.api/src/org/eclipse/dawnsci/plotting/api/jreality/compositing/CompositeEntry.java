/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.plotting.api.jreality.compositing;

import uk.ac.diamond.daq.util.logging.deprecation.DeprecationLogger;

/**
 *
 */
@Deprecated(since="at least 2015")
public class CompositeEntry {

	private static final DeprecationLogger logger = DeprecationLogger.getLogger(CompositeEntry.class);
	private String name;
	private float weight;
	private CompositeOp operation;
	private byte channelMask;
	
	public CompositeEntry(String name, float weight, CompositeOp op,
						  byte channelMask) {
		logger.deprecatedClass();
		this.name = name;
		this.weight = weight;
		this.operation = op;
		this.channelMask = channelMask;
	}
	
	public final String getName() {
		return name;
	}
	
	public final float getWeight() {
		return weight;
	}
	
	public final CompositeOp getOperation() {
		return operation;
	}
	
	public final byte getChannelMask() {
		return channelMask;
	}
}
