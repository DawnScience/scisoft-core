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

import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public class GeneticAlgFittingTest extends AbstractFittingTest {

	static final long SEED = 12357L;

	@Override
	public IOptimizer createOptimizer() {
		return new GeneticAlg(accuracy, SEED);
	}

	@Test
	public void testFWHMGaussian() {
		Assert.assertEquals(fwhm, fittedGaussian.get(0).getPeak(0).getFWHM(), delta);
	}

	@Test
	public void testFWHMLorentzian() {
		Assert.assertEquals(fwhm, fittedLorentzian.get(0).getPeak(0).getFWHM(), 4*delta);
	}

	@Test
	public void testFWHMPearsonVII() {
		Assert.assertEquals(fwhm, fittedPearsonVII.get(0).getPeak(0).getFWHM(), delta);
	}

	@Test
	public void testFWHMPseudoVoigt() {
		Assert.assertEquals(fwhm, fittedPseudoVoigt.get(0).getPeak(0).getFWHM(), 5*delta);
	}

	@Test
	public void testAreaGaussian() {
		Assert.assertEquals(area, fittedGaussian.get(0).getPeak(0).getArea(), delta);
	}

	@Test
	public void testAreaLorentzian() {
		Assert.assertEquals(area, fittedLorentzian.get(0).getPeak(0).getArea(), 6*delta);
	}

	@Test
	public void testAreaPearsonVII() {
		Assert.assertEquals(area, fittedPearsonVII.get(0).getPeak(0).getArea(), 2*delta);
	}

	@Test
	public void testAreaPseudoVoigt() {
		Assert.assertEquals(area, fittedPseudoVoigt.get(0).getPeak(0).getArea(), 9*delta);
	}
}
