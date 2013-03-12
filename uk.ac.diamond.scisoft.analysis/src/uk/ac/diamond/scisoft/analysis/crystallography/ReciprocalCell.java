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

package uk.ac.diamond.scisoft.analysis.crystallography;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;


/**
 * Reciprocal cell that is dual to a unit cell
 */
public class ReciprocalCell extends UnitCell {
	/**
	 * create a reciprocal cell from a unit cell
	 */
	public ReciprocalCell(UnitCell l) {
		a = new Vector3d(); // a*
		a.cross(l.b, l.c);
		double volumeInv = 1. / a.dot(l.a);
		a.scale(volumeInv);

		b = new Vector3d(); // b*
		b.cross(l.c, l.a);
		b.scale(volumeInv);

		c = new Vector3d(); // c*
		c.cross(l.a, l.b);
		c.scale(volumeInv);

		calculateAll();
	}

	/**
	 * @return orthogonalization matrix
	 */
	public Matrix3d orthogonalization() {
		Matrix3d B = new Matrix3d();
		B.setColumn(0, a);
		B.setColumn(1, b);
		B.setColumn(2, c);
		return B;
	}
}
