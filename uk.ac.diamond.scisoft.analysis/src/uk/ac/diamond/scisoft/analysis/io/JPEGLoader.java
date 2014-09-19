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
 * This class loads a JPEG image file
 */
public class JPEGLoader extends JavaImageLoader {

	public JPEGLoader() {
		this(null, false);
	}
	/**
	 * @param FileName
	 */
	public JPEGLoader(String FileName) {
		this(FileName, false);
	}

	/**
	 * @param FileName
	 * @param convertToGrey
	 */
	public JPEGLoader(String FileName, boolean convertToGrey) {
		super(FileName, "jpeg", convertToGrey);
	}

	/**
	 * @param FileName
	 * @param convertToGrey
	 * @param keepBitWidth
	 */
	public JPEGLoader(String FileName, boolean convertToGrey, boolean keepBitWidth) {
		super(FileName, "jpeg", convertToGrey, keepBitWidth);
	}
	
}
