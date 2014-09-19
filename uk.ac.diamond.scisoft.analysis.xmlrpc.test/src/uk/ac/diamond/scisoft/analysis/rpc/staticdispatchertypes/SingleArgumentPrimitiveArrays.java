/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.staticdispatchertypes;

public class SingleArgumentPrimitiveArrays {
	public static Class<boolean[]> call(boolean[] param) {
		return boolean[].class;
	}
	public static Class<byte[]> call(byte[] param) {
		return byte[].class;
	}
	public static Class<char[]> call(char[] param) {
		return char[].class;
	}
	public static Class<double[]> call(double[] param) {
		return double[].class;
	}
	public static Class<float[]> call(float[] param) {
		return float[].class;
	}
	public static Class<int[]> call(int[] param) {
		return int[].class;
	}
	public static Class<long[]> call(long[] param) {
		return long[].class;
	}
	public static Class<short[]> call(short[] param) {
		return short[].class;
	}
}
