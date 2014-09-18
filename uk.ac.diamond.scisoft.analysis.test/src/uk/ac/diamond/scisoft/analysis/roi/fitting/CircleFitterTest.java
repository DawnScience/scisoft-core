/*-
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

package uk.ac.diamond.scisoft.analysis.roi.fitting;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.roi.fitting.CircleFitter;
import org.junit.Assert;
import org.junit.Test;

public class CircleFitterTest {

	@Test
	public void testQuickCircle() {
		double[] original = new double[] { 344.2, 0.2, -12.3};

		DoubleDataset theta;
//		theta = new DoubleDataset(new double[] {0.1, 0.2, 0.25, 0.33, 0.35, 0.37, 0.43, });
		Random.seed(1277);
		theta = Random.rand(0, 2*Math.PI, 10);

		Dataset[] coords = CircleFitter.generateCoordinates(theta, original);

		Dataset x;
		Dataset y;
//		x = new DoubleDataset(new double[] {242.34, 188.08, 300.04, 188.90, 300.97, 103.80, 157.67, 141.81, 302.64, 266.58});
//		y = new DoubleDataset(new double[] {-262.478, 147.192, -107.673, 136.293, -118.735, 217.387, 166.996, 192.521, -55.201, 17.826});
		x = Maths.add(coords[0], Random.randn(0.0, 10.2, theta.getShape()));
		y = Maths.add(coords[1], Random.randn(0.0, 10.2, theta.getShape()));
		System.err.println(x);
		System.err.println(y);

		double[] tols = new double[] {8e-2, 8, 4e-1};
		CircleFitter fitter = checkQuickEllipse(x, y, original, tols);

		fitter.geometricFit(x, y, fitter.getParameters());
		double[] result = fitter.getParameters();
		System.err.println(Arrays.toString(result));
		for (int i = 0; i < original.length; i++) {
			double err = Math.abs(original[i]*tols[i]);
			Assert.assertEquals("Geometric fit: " + i, original[i], result[i], err);
		}
	}

	@Test
	public void testQuickCircle2() {
		double[] original = new double[] { 3.5384, 4.7684, 4.7729};

		Dataset x;
		Dataset y;
		x = new DoubleDataset(new double[] {1, 2, 5, 7, 9, 3, 6, 8});
		y = new DoubleDataset(new double[] {7, 6, 8, 7, 5, 7, 2, 4});
		checkQuickEllipse(x, y, original, new double[] {1e-4, 1e-4, 1e-4});
	}

	@Test
	public void testQuickExactCircle() {
		double[] original = new double[] {50, 150, -0.30};

		DoubleDataset theta;
		theta = (DoubleDataset) DatasetUtils.linSpace(0, Math.PI, 5, Dataset.FLOAT64);

		Dataset[] coords = CircleFitter.generateCoordinates(theta, original);

		checkQuickEllipse(coords[0], coords[1], original, new double[] {1e-4, 1e-4, 1e-4});
	}

	@Test
	public void testFailedCircle() {
		double[] original = new double[] { 50, 150, -0.30};

		Dataset x;
		Dataset y;
		x = new DoubleDataset(new double[] {150, 200, 250});
		y = new DoubleDataset(new double[] {100, 150, 200});

		try {
			checkQuickEllipse(x, y, original, new double[] {1e-4, 1e-4, 1e-4});
			Assert.fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			System.err.println("Correctly thrown: " + e);
		}
	}

	private CircleFitter checkQuickEllipse(Dataset x, Dataset y, double[] original, double[] tols) {
		CircleFitter fitter = new CircleFitter();

		fitter.algebraicFit(x, y);
		double[] result = fitter.getParameters();
		System.err.println(Arrays.toString(original));
		System.err.println(Arrays.toString(result));

		for (int i = 0; i < original.length; i++) {
			double err = Math.abs(original[i]*tols[i]);
			Assert.assertEquals("Algebraic fit: " + i, original[i], result[i], err);
		}
		return fitter;
	}
}
