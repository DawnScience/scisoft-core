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

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.io.ADSCImageLoader;
import uk.ac.diamond.scisoft.analysis.io.CBFLoader;
import uk.ac.diamond.scisoft.analysis.io.IDataHolder;
import uk.ac.diamond.scisoft.analysis.io.IFileLoader;
import uk.ac.diamond.scisoft.analysis.io.MARLoader;
import uk.ac.diamond.scisoft.analysis.io.ScanFileHolderException;

/**
 * Class to hold methods to handle diffraction images and associated metadata
 */
@Deprecated
public class DiffractionImage {

	// Probably no need to use class loading here - these classes are part of this plugin!
	private static Class<?>[] loaders = { ADSCImageLoader.class, 
		                                  CBFLoader.class,
		                                  MARLoader.class};

	public static IDataset[] loadImages(String filename) {
		
		IDataHolder dh = null;

		for (Class<?> clazz : loaders) {
			try {
				// Find a constructor with a single string for the path
                final Constructor<?> singleStringConstructor = clazz.getConstructor(String.class);
				IFileLoader loader = (IFileLoader)singleStringConstructor.newInstance(filename);
				dh = loader.loadFile();
				break;
			} catch (SecurityException e) {
				e.printStackTrace();			
			} catch (NoSuchMethodException e) {
					e.printStackTrace();
			} catch (ScanFileHolderException e) {
				continue; // can try another loader
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		if (dh == null)
			return null;

		int numImages = dh.size();
		if (numImages < 1) {
			return null;
		}
		IDataset[] allImages = new IDataset[numImages];

		for (int i = 0; i < numImages; i++) {
			allImages[i] = dh.getDataset(i);
		}
		return allImages;
	}
}
