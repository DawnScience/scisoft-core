/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.rpc.instancedispatcher;
@SuppressWarnings("unused")

public class SingleArgumentPrimitiveArrays {
	public Class<boolean[]> call(boolean[] param) {
		return boolean[].class;
	}
	public Class<byte[]> call(byte[] param) {
		return byte[].class;
	}
	public Class<char[]> call(char[] param) {
		return char[].class;
	}
	public Class<double[]> call(double[] param) {
		return double[].class;
	}
	public Class<float[]> call(float[] param) {
		return float[].class;
	}
	public Class<int[]> call(int[] param) {
		return int[].class;
	}
	public Class<long[]> call(long[] param) {
		return long[].class;
	}
	public Class<short[]> call(short[] param) {
		return short[].class;
	}
}
