/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io;

/**
 * This class saves a DataHolder as a TIFF image file.
 */
public class TIFFImageSaver extends JavaImageSaver {

	/**
	 * @param filename
	 */
	public TIFFImageSaver(String filename) {
		this(filename, false);
	}

	/**
	 * @param filename
	 * @param numBits
	 */
	public TIFFImageSaver(String filename, int numBits) {
		super(filename, "tiff", numBits, true);
	}

	/**
	 * @param filename
	 * @param numBits
	 * @param asUnsigned
	 */
	public TIFFImageSaver(String filename, int numBits, boolean asUnsigned) {
		super(filename, "tiff", numBits, asUnsigned);
	}

	/**
	 * @param filename
	 * @param asFloat
	 */
	public TIFFImageSaver(String filename, boolean asFloat) {
		super(filename, "tiff", asFloat ? 33 : 16, true);
	}
}
