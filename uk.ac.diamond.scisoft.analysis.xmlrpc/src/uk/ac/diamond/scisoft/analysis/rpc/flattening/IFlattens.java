/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

/**
 * Implementations can "flatten" themselves by implementing this interface.
 * 
 * @see IFlattener
 */
public interface IFlattens {
	/**
	 * Return the flattened form of this object, possibly using rootFlattener to flatten any contained objects.
	 * 
	 * @param rootFlattener
	 * @return the flattened form of this object
	 */
	public Object flatten(IRootFlattener rootFlattener);
}
