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
 * This class loads a PNG image file
 */
public class PNGLoader extends JavaImageLoader {

	public PNGLoader() {
		this(null, false);
	}
	/**
	 * @param FileName
	 */
	public PNGLoader(String FileName) {
		this(FileName, false);
	}

	/**
	 * @param FileName
	 * @param convertToGrey
	 */
	public PNGLoader(String FileName, boolean convertToGrey) {
		super(FileName, "png", convertToGrey);
	}
	
	/**
	 * @param FileName
	 * @param convertToGrey
	 * @param keepBitWidth
	 */
	public PNGLoader(String FileName, boolean convertToGrey, boolean keepBitWidth) {
		super(FileName, "png", convertToGrey, keepBitWidth);
	}
}
