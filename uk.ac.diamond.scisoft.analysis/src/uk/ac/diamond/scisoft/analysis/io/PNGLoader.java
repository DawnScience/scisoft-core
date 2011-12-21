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
