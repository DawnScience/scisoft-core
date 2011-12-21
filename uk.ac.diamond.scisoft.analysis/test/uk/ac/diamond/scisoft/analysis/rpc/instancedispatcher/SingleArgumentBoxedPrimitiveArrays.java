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
@SuppressWarnings("unused")

public class SingleArgumentBoxedPrimitiveArrays {
	public Class<Boolean[]> call(Boolean[] param) {
		return Boolean[].class;
	}
	public Class<Byte[]> call(Byte[] param) {
		return Byte[].class;
	}
	public Class<Character[]> call(Character[] param) {
		return Character[].class;
	}
	public Class<Double[]> call(Double[] param) {
		return Double[].class;
	}
	public Class<Float[]> call(Float[] param) {
		return Float[].class;
	}
	public Class<Integer[]> call(Integer[] param) {
		return Integer[].class;
	}
	public Class<Long[]> call(Long[] param) {
		return Long[].class;
	}
	public Class<Short[]> call(Short[] param) {
		return Short[].class;
	}
}
