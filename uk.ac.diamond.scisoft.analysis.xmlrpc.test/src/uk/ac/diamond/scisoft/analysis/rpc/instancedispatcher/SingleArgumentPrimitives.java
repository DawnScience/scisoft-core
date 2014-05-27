/*
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

package uk.ac.diamond.scisoft.analysis.rpc.instancedispatcher;

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
