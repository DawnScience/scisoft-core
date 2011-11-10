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

package uk.ac.diamond.scisoft.analysis.rpc.staticdispatchertypes;

@SuppressWarnings("unused")
public class SingleArgumentPrimitives {
	public static Class<Boolean> call(boolean param) {
		return Boolean.TYPE;
	}
	public static Class<Byte> call(byte param) {
		return Byte.TYPE;
	}
	public static Class<Character> call(char param) {
		return Character.TYPE;
	}
	public static Class<Double> call(double param) {
		return Double.TYPE;
	}
	public static Class<Float> call(float param) {
		return Float.TYPE;
	}
	public static Class<Integer> call(int param) {
		return Integer.TYPE;
	}
	public static Class<Long> call(long param) {
		return Long.TYPE;
	}
	public static Class<Short> call(short param) {
		return Short.TYPE;
	}
}
