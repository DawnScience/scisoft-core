/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import gda.analysis.io.IFileLoader;
import gda.analysis.io.ScanFileHolderException;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.ADSCImageLoader;
import uk.ac.diamond.scisoft.analysis.io.CBFLoader;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
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

	public static AbstractDataset[] loadImages(String filename) {
		
		DataHolder dh = null;

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
		AbstractDataset[] allImages = new AbstractDataset[numImages];

		for (int i = 0; i < numImages; i++) {
			allImages[i] = dh.getDataset(i);
		}
		return allImages;
	}

	public static DetectorProperties createDetectorProperties(AbstractDataset image) throws IllegalArgumentException {
		Map<String, ? extends Serializable> metadata = image.getMetadataMap();
		if (metadata == null)
			throw new IllegalArgumentException("No metadata at all");

		double [] detectorLocation;
		Vector3d detectorOrigin;
		double[] detectorShape;
		int heightPx;
		int widthPx;
		double pxHeight;
		double pxWidth;
		Matrix3d orientation;

		Serializable s;
		s = metadata.get("NXdetector:NXgeometery:NXtranslation");
		if (s == null)
			throw new IllegalArgumentException("No metadata for detector translation");
		detectorLocation = (double[]) s;

		detectorOrigin = new Vector3d(detectorLocation);
		s = metadata.get("NXdetector:x_pixel_size");
		if (s == null)
			throw new IllegalArgumentException("No metadata for detector x pixel size");
		pxHeight = (Double) s;

		s = metadata.get("NXdetector:y_pixel_size");
		if (s == null)
			throw new IllegalArgumentException("No metadata for detector y pixel size");
		pxWidth = (Double) s;

		s = metadata.get("NXdetector:NXgeometery:NXshape");
		if (s == null)
			throw new IllegalArgumentException("No metadata for detector shape");
		detectorShape = (double[]) s;

		heightPx = (int) (detectorShape[0] / pxHeight);
		widthPx = (int) (detectorShape[1] / pxWidth);
		s = metadata.get("NXdetector:NXgeometery:NXorientation");
		if (s == null)
			throw new IllegalArgumentException("No metadata for detector orientation");
		double[] oriMat = (double[]) s;

		Vector3d z = new Vector3d();
		z.cross(new Vector3d(oriMat[0], oriMat[1], oriMat[2]), new Vector3d(oriMat[3], oriMat[4], oriMat[5]));
		orientation = new Matrix3d(oriMat[0], oriMat[1], oriMat[2], oriMat[3], oriMat[4], oriMat[5], z
				.dot(new Vector3d(1, 0, 0)), z.dot(new Vector3d(0, 1, 0)), z.dot(new Vector3d(0, 0, 1)));
		DetectorProperties detProps = new DetectorProperties(detectorOrigin, heightPx, widthPx, pxHeight, pxWidth,
				orientation);
		return detProps;
	}

	public static DiffractionCrystalEnvironment createDiffractionCrystalEnvironment(AbstractDataset image) throws IllegalArgumentException {
		Map<String, ? extends Serializable> metadata = image.getMetadataMap();
		if (metadata == null)
			throw new IllegalArgumentException("No metadata at all");

		double wavelength;
		Serializable s = metadata.get("NXmonochromator:wavelength");
		if (s == null)
			throw new IllegalArgumentException("No metadata for experiment wavelength");
		wavelength = Double.parseDouble(s.toString());
		DiffractionCrystalEnvironment dce = new DiffractionCrystalEnvironment(wavelength);
		return dce;
	}
}