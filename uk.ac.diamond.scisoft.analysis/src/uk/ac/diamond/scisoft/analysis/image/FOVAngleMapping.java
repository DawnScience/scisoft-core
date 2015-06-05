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

public class FOVAngleMapping {

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
	public static double getAngle(double fov, double theta) {
		double alpha = 0;
		// if fov not in the map, calculate it with linear interpolation
		// using the following equation:
		// alpha = alpha1 + (fov - fov1)*((alpha2 - alpha1)/(fov2 - fov1))
		if (!dict.containsKey(fov)){
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
			alpha = dict.get(fov);
		}
		return alpha - theta;
	}

}
