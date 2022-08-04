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
package org.eclipse.dawnsci.plotting.api.region;

import org.eclipse.swt.graphics.Color;

/**
 * A collection of color-related constants.
 */
public interface ColorConstants {
	/*
	 * Misc. colors
	 */
	/** One of the pre-defined colors */
	Color white = new Color(null, 255, 255, 255);
	/** One of the pre-defined colors */
	Color lightGray = new Color(null, 192, 192, 192);
	/** One of the pre-defined colors */
	Color gray = new Color(null, 128, 128, 128);
	/** One of the pre-defined colors */
	Color darkGray = new Color(null, 64, 64, 64);
	/** One of the pre-defined colors */
	Color black = new Color(null, 0, 0, 0);
	/** One of the pre-defined colors */
	Color red = new Color(null, 255, 0, 0);
	/** One of the pre-defined colors */
	Color orange = new Color(null, 255, 196, 0);
	/** One of the pre-defined colors */
	Color yellow = new Color(null, 255, 255, 0);
	/** One of the pre-defined colors */
	Color green = new Color(null, 0, 255, 0);
	/** One of the pre-defined colors */
	Color lightGreen = new Color(null, 96, 255, 96);
	/** One of the pre-defined colors */
	Color darkGreen = new Color(null, 0, 127, 0);
	/** One of the pre-defined colors */
	Color cyan = new Color(null, 0, 255, 255);
	/** One of the pre-defined colors */
	Color lightBlue = new Color(null, 127, 127, 255);
	/** One of the pre-defined colors */
	Color blue = new Color(null, 0, 0, 255);
	/** One of the pre-defined colors */
	Color darkBlue = new Color(null, 0, 0, 127);

}
