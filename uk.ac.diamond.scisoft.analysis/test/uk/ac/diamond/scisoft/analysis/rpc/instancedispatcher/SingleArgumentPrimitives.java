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

package uk.ac.diamond.scisoft.analysis.rpc.instancedispatcher;
@SuppressWarnings("unused")

public class SingleArgumentPrimitives {
	public Class<Boolean> call(boolean param) {
		return Boolean.TYPE;
	}
	public Class<Byte> call(byte param) {
		return Byte.TYPE;
	}
	public Class<Character> call(char param) {
		return Character.TYPE;
	}
	public Class<Double> call(double param) {
		return Double.TYPE;
	}
	public Class<Float> call(float param) {
		return Float.TYPE;
	}
	public Class<Integer> call(int param) {
		return Integer.TYPE;
	}
	public Class<Long> call(long param) {
		return Long.TYPE;
	}
	public Class<Short> call(short param) {
		return Short.TYPE;
	}
}
