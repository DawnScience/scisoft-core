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
 * This class saves a DataHolder as a JPEG image file. The maximum value for each pixel that JPEG is capable of
 * handling is 255. If the value of a measurement exceeds this value then this method is recommended. This method will
 * scale the values of the datasets within a DataHolder to lie within the capabilities of JPEG.
 */
public class JPEGScaledSaver extends JavaImageScaledSaver {

	/**
	 * @param FileName
	 */
	public JPEGScaledSaver(String FileName) {
		super(FileName, "jpeg", 8, true);
	}

	/**
	 * Save as JPEG using given minimum and maximum values
	 * @param FileName
	 * @param min
	 * @param max
	 */
	public JPEGScaledSaver(String FileName, double min, double max) {
		super(FileName, "jpeg", 8, true, min, max);
	}

}
