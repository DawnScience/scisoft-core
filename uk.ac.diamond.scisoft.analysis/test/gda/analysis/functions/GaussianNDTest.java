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

package gda.analysis.functions;

import static org.junit.Assert.assertEquals;
import gda.analysis.DataSet;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

public class GaussianNDTest {

	private static double abstol = 1e-8;

	@Test
	public void testG2D() {
		double[] min = {0, 0};
		double[] max = {8, 12};
		GaussianND g2 = new GaussianND(2., min, max, 20.);

		g2.setParameterValues(new double[] {4, 6, 1, 4, 4, 0});
		DataSet axis0 = DataSet.arange(8);
		DataSet axis1 = DataSet.arange(12);
		DataSet d2 = g2.makeDataSet(axis0, axis1);
		d2.disp();

		double[] gx = { 5.98199448e-05,   2.36592471e-04,   7.28756116e-04,
		         1.74819504e-03,   3.26605832e-03,   4.75208682e-03,
		         5.38481983e-03,   4.75208682e-03,   3.26605832e-03,
		         1.74819504e-03,   7.28756116e-04,   2.36592471e-04,
		         1.43500588e-04,   5.67555834e-04,   1.74819504e-03,
		         4.19370190e-03,   7.83486662e-03,   1.13996637e-02,
		         1.29175112e-02,   1.13996637e-02,   7.83486662e-03,
		         4.19370190e-03,   1.74819504e-03,   5.67555834e-04,
		         2.68094393e-04,   1.06033389e-03,   3.26605832e-03,
		         7.83486662e-03,   1.46374579e-02,   2.12973755e-02,
		         2.41330882e-02,   2.12973755e-02,   1.46374579e-02,
		         7.83486662e-03,   3.26605832e-03,   1.06033389e-03,
		         3.90075040e-04,   1.54277671e-03,   4.75208682e-03,
		         1.13996637e-02,   2.12973755e-02,   3.09874986e-02,
		         3.51134361e-02,   3.09874986e-02,   2.12973755e-02,
		         1.13996637e-02,   4.75208682e-03,   1.54277671e-03,
		         4.42012928e-04,   1.74819504e-03,   5.38481983e-03,
		         1.29175112e-02,   2.41330882e-02,   3.51134361e-02,
		         3.97887358e-02,   3.51134361e-02,   2.41330882e-02,
		         1.29175112e-02,   5.38481983e-03,   1.74819504e-03,
		         3.90075040e-04,   1.54277671e-03,   4.75208682e-03,
		         1.13996637e-02,   2.12973755e-02,   3.09874986e-02,
		         3.51134361e-02,   3.09874986e-02,   2.12973755e-02,
		         1.13996637e-02,   4.75208682e-03,   1.54277671e-03,
		         2.68094393e-04,   1.06033389e-03,   3.26605832e-03,
		         7.83486662e-03,   1.46374579e-02,   2.12973755e-02,
		         2.41330882e-02,   2.12973755e-02,   1.46374579e-02,
		         7.83486662e-03,   3.26605832e-03,   1.06033389e-03,
		         1.43500588e-04,   5.67555834e-04,   1.74819504e-03,
		         4.19370190e-03,   7.83486662e-03,   1.13996637e-02,
		         1.29175112e-02,   1.13996637e-02,   7.83486662e-03,
		         4.19370190e-03,   1.74819504e-03,   5.67555834e-04 };

		IndexIterator it;
		double[] dx;
		it = d2.getIterator();
		dx = d2.getData();
		int i = 0;
		while (it.hasNext()) {
			assertEquals("2D Gaussian: ", gx[i], dx[it.index], abstol);
			i++;
		}
	}
}
