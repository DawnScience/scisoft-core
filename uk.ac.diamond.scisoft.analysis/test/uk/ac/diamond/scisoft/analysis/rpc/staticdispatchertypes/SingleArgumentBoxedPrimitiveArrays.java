/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.rpc.staticdispatchertypes;

@SuppressWarnings("unused")
public class SingleArgumentBoxedPrimitiveArrays {
	public static Class<Boolean[]> call(Boolean[] param) {
		return Boolean[].class;
	}
	public static Class<Byte[]> call(Byte[] param) {
		return Byte[].class;
	}
	public static Class<Character[]> call(Character[] param) {
		return Character[].class;
	}
	public static Class<Double[]> call(Double[] param) {
		return Double[].class;
	}
	public static Class<Float[]> call(Float[] param) {
		return Float[].class;
	}
	public static Class<Integer[]> call(Integer[] param) {
		return Integer[].class;
	}
	public static Class<Long[]> call(Long[] param) {
		return Long[].class;
	}
	public static Class<Short[]> call(Short[] param) {
		return Short[].class;
	}
}
