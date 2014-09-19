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
 * This class saves a DataHolder as a JPEG image file. The maximum value for each pixel that JPEG is capable of
 * handling is 255. If the value of a measurement exceeds this value then use JPEGScaledSaver instead.
 */
public class JPEGSaver extends JavaImageSaver {

	/**
	 * @param FileName
	 */
	public JPEGSaver(String FileName) {
		super(FileName, "jpeg", 8, true);
	}

}
