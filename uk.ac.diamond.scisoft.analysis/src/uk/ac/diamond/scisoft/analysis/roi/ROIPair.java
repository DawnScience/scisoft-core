/*-
 * Copyright 2012 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.roi;
/**
 * Class to store key-value (name-RoiBase) pairs for handling ROIs
 */
public class ROIPair<A, B> {
	private final A name;
	private final B roi;

	public ROIPair(A name, B roi) {
		super();
		this.name = name;
		this.roi = roi;
	}

	@Override
	public int hashCode() {
		int hashFirst = name != null ? name.hashCode() : 0;
		int hashSecond = roi != null ? roi.hashCode() : 0;
		return (hashFirst + hashSecond) * hashSecond + hashFirst;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ROIPair) {
			ROIPair<?, ?> otherPair = (ROIPair<?, ?>) other;
			return 
					((this.name == otherPair.name ||
					(this.name != null && otherPair.name != null &&
					this.name.equals(otherPair.name))) &&
					(this.roi == otherPair.roi ||
					(this.roi != null && otherPair.roi != null &&
					this.roi.equals(otherPair.roi))) );
		}
		return false;
	}

	@Override
	public String toString(){ 
		return "(" + name + ", " + roi + ")"; 
	}

	public A getName() {
		return name;
	}

	public B getRoi() {
		return roi;
	}
}