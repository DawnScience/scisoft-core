/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;

import uk.ac.diamond.scisoft.analysis.io.ADSCImageLoader;
import uk.ac.diamond.scisoft.analysis.io.CBFLoader;
import uk.ac.diamond.scisoft.analysis.io.MARLoader;

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
