/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.plotting.api.region;


/**
 * Interface to mark regions that can be locked to be only translatable
 * i.e. no longer be resized/rotated by dragging
 * 
 */
public interface ILockTranslatable  {

	void translateOnly(boolean translateOnly);
	
}
