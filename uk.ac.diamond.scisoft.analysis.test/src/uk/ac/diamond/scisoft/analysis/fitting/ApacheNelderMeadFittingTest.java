/*-
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

package uk.ac.diamond.scisoft.analysis.fitting;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.optimize.ApacheNelderMead;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public class ApacheNelderMeadFittingTest extends AbstractFittingTestBase {

	@Override
	public IOptimizer createOptimizer() {
		return new ApacheNelderMead();
	}

	@Test
	public void testFWHMGaussian() {
		checkClose("Gaussian fwhm", fwhm, fittedGaussian.get(0).getPeak(0).getFWHM(), 10*delta);
	}

	@Test
	public void testFWHMLorentzian() {
		checkClose("Lorentzian fwhm", fwhm, fittedLorentzian.get(0).getPeak(0).getFWHM(), 21*delta);
	}

	@Test
	public void testFWHMPearsonVII() {
		checkClose("Pearson7 fwhm", fwhm, fittedPearsonVII.get(0).getPeak(0).getFWHM(), 11*delta);
	}

	@Test
	public void testFWHMPseudoVoigt() {
		checkClose("PseudoVoigt fwhm", fwhm, fittedPseudoVoigt.get(0).getPeak(0).getFWHM(), 20*delta);
	}

	@Test
	public void testAreaGaussian() {
		checkClose("Gaussian area", area, fittedGaussian.get(0).getPeak(0).getArea(), 19*delta);
	}

	@Test
	public void testAreaLorentzian() {
		checkClose("Lorentzian area", area, fittedLorentzian.get(0).getPeak(0).getArea(), 30*delta);
	}

	@Test
	public void testAreaPearsonVII() {
		checkClose("Pearson7 area", area, fittedPearsonVII.get(0).getPeak(0).getArea(), 25*delta);
	}

	@Test
	public void testAreaPseudoVoigt() {
		checkClose("PseudoVoigt area", area, fittedPseudoVoigt.get(0).getPeak(0).getArea(), 24*delta);
	}
}
