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

package uk.ac.diamond.scisoft.analysis.fitting;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.optimize.ApacheConjugateGradient;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public class ApacheConjugateGradientFittingTest extends AbstractFittingTestBase {

	@Override
	public IOptimizer createOptimizer() {
		return new ApacheConjugateGradient();
	}

	@Override
	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testNumberOfPeaksFoundGaussian() {
		Assert.assertEquals(1, fittedGaussian.size());
	}

	@Override
	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testNumberOfPeaksFoundLorentzian() {
		Assert.assertEquals(1, fittedLorentzian.size());
	}

	@Override
	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testPeakPosGaussian() {
		Assert.assertEquals(pos, fittedGaussian.get(0).getPeak(0).getPosition(), delta);
	}

	@Override
	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testPeakPosLorentzian() {
		Assert.assertEquals(pos, fittedLorentzian.get(0).getPeak(0).getPosition(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testFWHMGaussian() {
		checkClose("Gaussian fwhm", fwhm, fittedGaussian.get(0).getPeak(0).getFWHM(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testFWHMLorentzian() {
		checkClose("Lorentzian fwhm", fwhm, fittedLorentzian.get(0).getPeak(0).getFWHM(), delta);
	}

	@Test
	public void testFWHMPearsonVII() {
		checkClose("Pearson7 fwhm", fwhm, fittedPearsonVII.get(0).getPeak(0).getFWHM(), 20*delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testFWHMPseudoVoigt() {
		checkClose("PseudoVoigt fwhm", fwhm, fittedPseudoVoigt.get(0).getPeak(0).getFWHM(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaGaussian() {
		checkClose("Gaussian area", area, fittedGaussian.get(0).getPeak(0).getArea(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaLorentzian() {
		checkClose("Lorentzian area", area, fittedLorentzian.get(0).getPeak(0).getArea(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaPearsonVII() {
		checkClose("Pearson7 area", area, fittedPearsonVII.get(0).getPeak(0).getArea(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaPseudoVoigt() {
		checkClose("PseudoVoigt area", area, fittedPseudoVoigt.get(0).getPeak(0).getArea(), delta);
	}
}
