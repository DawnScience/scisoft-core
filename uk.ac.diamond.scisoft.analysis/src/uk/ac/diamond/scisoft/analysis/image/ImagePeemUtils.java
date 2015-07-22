/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;

/**
 * Peem analysis class with useful methods to calculate angle rotation of an image given Peem specific detector
 * properties
 */
public class ImagePeemUtils {

	private static Map<Integer, Integer> dict;
	static {
		dict = new HashMap<Integer, Integer>();
		dict.put(10, -15);
		dict.put(20, 20);
		dict.put(40, 45);
		dict.put(50, 51);
		dict.put(80, 65);
	}

	/**
	 * Calculates the rotation angle of an image given a field of view value and rotation of a peem manipulator (theta)
	 * The resulting rotation angle is the difference between the rotation of the microscope magnetic lenses (alpha) and
	 * theta. The alpha angle can be deduced from the FOV, using the lookup table with a set of FOV/alpha corresponding
	 * values.<br>
	 * If a FOV entered is not in the table, then a linear interpolation is used to deduct the corresponding alpha
	 * angle.
	 * 
	 * @param fov
	 * @param theta
	 * @return angle
	 */
	public static double getAngleFromFOV(double fov, double theta) {
		double alpha = 0;
		// if fov not in the map, calculate it with linear interpolation
		// using the following equation:
		// alpha = alpha1 + (fov - fov1)*((alpha2 - alpha1)/(fov2 - fov1))
		if (!dict.containsKey((int)fov)){
			List<Integer> fovs = new ArrayList<Integer>(dict.keySet());
			Collections.sort(fovs);
			for (int i = 0; i < fovs.size(); i++) {
				if (i + 1 < fovs.size() && fov > fovs.get(i) && fov < fovs.get(i + 1)) {
					double alpha1 = dict.get(fovs.get(i));
					double fov1 = fovs.get(i);
					double alpha2 = dict.get(fovs.get(i + 1));
					double fov2 = fovs.get(i + 1);
					alpha = alpha1 + ((fov - fov1) * ((alpha2 - alpha1) / (fov2 - fov1)));
					break;
				}
			}
		} else {
			alpha = dict.get((int)fov);
		}
		return alpha - theta;
	}

	/**
	 * Given two 1 dimensional datasets containing a list of X positions and Y positions, this method returns a list of
	 * steps between a X/Y position and its predecessor
	 * 
	 * @param psx
	 * @param psy
	 * @return translations in microns
	 */
	public static List<double[]> getMotorTranslations(IDataset psx, IDataset psy) {
		int[] matrixSize = getColumnAndRowNumber(psx, psy);
		List<double[]> translations = new ArrayList<double[]>();
		int count = 0;
		int xshift = matrixSize[0];
		for (int i = 0; i < matrixSize[0]; i++) {
			for (int j = 0; j < matrixSize[1]; j++) {
				double currentY = psy.getDouble(count);
				double previousY = 0;
				if (count >= 1)
					previousY = psy.getDouble(count - 1);
				if (j == 0) {
					currentY = 0;
					previousY = 0;
				}

				double currentX = psx.getDouble(count);
				double previousX = 0;
				if (i == 0) {
					currentX = 0;
					previousX = 0;
				}

				if (count >= xshift)
					previousX = psx.getDouble(count - xshift);
				double translX = (currentX - previousX) * 1000;
				double translY = (currentY - previousY) * 1000;
				if (translX == 0 && translY != 0)
					translX = translY;
				else if (translY == 0 && translX != 0)
					translY = translX;
				translations.add(new double[] { translX, translY });
				count++;
			}
		}
		return translations;
	}

	/**
	 * Returns the number of columns and rows given a list of X/Y positions by counting the number of times a
	 * particular position is identical to its predecessor.
	 * 
	 * @param psx
	 * @param psy
	 * @return column and row array
	 */
	@SuppressWarnings("unused")
	public static int[] getColumnAndRowNumber(IDataset psx, IDataset psy) {
		int columns = 0, rows = 0, yIndex = 0;
		// just return the integer value of the square root
		int[] shape = psx.getShape();
		rows = (int)Math.sqrt(shape[0]);
		columns = shape[0] / rows;
		return new int[] { columns, rows};
	}
}
