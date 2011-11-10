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

public class SingleArgumentBoxedPrimitives {
	public Class<Boolean> call(Boolean param) {
		return Boolean.class;
	}
	public Class<Byte> call(Byte param) {
		return Byte.class;
	}
	public Class<Character> call(Character param) {
		return Character.class;
	}
	public Class<Double> call(Double param) {
		return Double.class;
	}
	public Class<Float> call(Float param) {
		return Float.class;
	}
	public Class<Integer> call(Integer param) {
		return Integer.class;
	}
	public Class<Long> call(Long param) {
		return Long.class;
	}
	public Class<Short> call(Short param) {
		return Short.class;
	}
}
