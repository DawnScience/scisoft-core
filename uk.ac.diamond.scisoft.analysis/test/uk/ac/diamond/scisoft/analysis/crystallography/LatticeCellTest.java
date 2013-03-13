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

package uk.ac.diamond.scisoft.analysis.crystallography;

import javax.vecmath.Vector3d;

import org.junit.Assert;
import org.junit.Test;

public class LatticeCellTest {

	@Test
	public void testCell() {
		Vector3d a = new Vector3d(1, 0, 0);
		Vector3d b = new Vector3d(0, 1, 0);
		Vector3d c = new Vector3d(0, 0, 1);
		LatticeCell lc = new LatticeCell(a, b, c);

		Assert.assertEquals(a, lc.getA());
		Assert.assertEquals(b, lc.getB());
		Assert.assertEquals(c, lc.getC());
		Assert.assertEquals(1.0, lc.volume(), 1e-14);

		Vector3d d = new Vector3d(1, 2, 0);
		lc.setB(d);
		Assert.assertEquals(d, lc.getB());
		Assert.assertEquals(2.0, lc.volume(), 1e-14);
	}
}
