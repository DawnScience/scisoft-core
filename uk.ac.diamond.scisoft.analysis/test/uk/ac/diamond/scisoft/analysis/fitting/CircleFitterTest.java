/*
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.Random;

public class CircleFitterTest {

	@Test
	public void testQuickCircle() {
		double[] original = new double[] { 344.2, 0.2, -12.3};

		DoubleDataset theta;
//		theta = new DoubleDataset(new double[] {0.1, 0.2, 0.25, 0.33, 0.35, 0.37, 0.43, });
		Random.seed(1277);
		theta = Random.rand(0, 2*Math.PI, 10);

		AbstractDataset[] coords = CircleFitter.generateCoordinates(theta, original);

		AbstractDataset x;
		AbstractDataset y;
//		x = new DoubleDataset(new double[] {242.34, 188.08, 300.04, 188.90, 300.97, 103.80, 157.67, 141.81, 302.64, 266.58});
//		y = new DoubleDataset(new double[] {-262.478, 147.192, -107.673, 136.293, -118.735, 217.387, 166.996, 192.521, -55.201, 17.826});
		x = Maths.add(coords[0], Random.randn(0.0, 10.2, theta.getShape()));
		y = Maths.add(coords[1], Random.randn(0.0, 10.2, theta.getShape()));
		System.err.println(x);
		System.err.println(y);

		CircleFitter fitter = new CircleFitter();

		fitter.algebraicFit(x, y);
		double[] result = fitter.getParameters();

		double[] tols = new double[] {8e-2, 8, -4e-1};
		for (int i = 0; i < original.length; i++) {
			Assert.assertEquals("Algebraic fit: " + i, original[i], result[i], original[i]*tols[i]);
		}
		System.err.println(Arrays.toString(original));
		System.err.println(Arrays.toString(result));

		fitter.geometricFit(x, y, result);
		result = fitter.getParameters();
		System.err.println(Arrays.toString(result));
		for (int i = 0; i < original.length; i++) {
			Assert.assertEquals("Geometric fit: " + i, original[i], result[i], original[i]*tols[i]);
		}
	}

}

