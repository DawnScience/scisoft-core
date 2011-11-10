/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

package gda.analysis.utils.optimisation;

import static org.junit.Assert.fail;
import gda.util.TestUtils;

import java.util.Random;

import org.junit.Test;
import org.junit.Ignore;

public class GeneticAlgorithmTest {

	@Test
	@Ignore("2010/02/16 Test ignored since sometimes loops almost forever GDA-3006")
	public void testOptimiseSimple() throws Exception {

		String testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(GeneticAlgorithmTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testScratchDirectoryName);

		try {

			GeneticAlgorithm ga = new GeneticAlgorithm();

			double[] vals = {0.1,0.3,0.5,0.7};
//			ga.optimise(new simplepd(), 20, 10, testScratchDirectoryName+"test.tmp", vals);
			ga.optimise(new simplepd(), 20, 10, null, vals);

		} catch (Exception e) {
			fail("An exception occured during the optimise routine");
		}
	}

	@Test
	@Ignore("2010/02/16 Test ignored since sometimes loops almost forever GDA-3006")
	public void testFailingOptimisation() throws Exception {

		String testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(GeneticAlgorithmTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testScratchDirectoryName);

		GeneticAlgorithm ga = new GeneticAlgorithm();

		double[] vals = {0.1,0.3,0.5,0.7};
		
		try {
			ga.optimise(new failingpd(), 20, 10, testScratchDirectoryName+"test2.tmp", vals);
		} catch (Exception e) {
			// we expect this to fail
		}
		
		boolean complete = false;
		
		while (!complete) {
			
			try {
				ga.optimise(new failingpd(), 20, testScratchDirectoryName+"test2.tmp");
				//if it completes then leave to loop
				complete = true;
			} catch (Exception e) {
				// we expect this to fail
			}
		}
		
		
	}

	class simplepd implements ProblemDefinition {

		@Override
		public double eval(double[] parameters) throws Exception {
			double total = 0;
			for(int i = 0; i < parameters.length; i++) {
				total += parameters[i];
			}
			return total;
		}

		@Override
		public int getNumberOfParameters() {
			return 4;
		}

	}

	class failingpd extends simplepd {

		Random rand = new Random();
		double prob = 0.1;


		@Override
		public double eval(double[] parameters) throws Exception {

			double test = rand.nextDouble();

			if (test < prob) {
				throw new Exception("PD failed");
			}

			return super.eval(parameters);
		}

	}

}
