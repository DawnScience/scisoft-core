/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

/**
 * This class saves a DataHolder as a PNG image file. The maximum value for each pixel that PNG is capable of
 * handling is 65535. If the value of a measurement exceeds this value then use PNGScaledSaver instead.
 */
public class PNGSaver extends JavaImageSaver {

	/**
	 * @param FileName
	 */
	public PNGSaver(String FileName) {
		super(FileName, "png", 16, true);
	}

}
