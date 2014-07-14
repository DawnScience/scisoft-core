/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset;

/**
 * Interface to represent a binary operation for implementations over different output domains
 */
public interface BinaryOperation {
	/**
	 * @param a
	 * @param b
	 * @return a op b
	 */
	boolean booleanOperate(long a, long b);

	/**
	 * @param a
	 * @param b
	 * @return a op b
	 */
	long longOperate(long a, long b);

	/**
	 * @param a
	 * @param b
	 * @return a op b
	 */
	double doubleOperate(double a, double b);

	/**
	 * @param ra
	 * @param ia
	 * @param rb
	 * @param ib
	 * @return Re(a op b)
	 */
	double realComplexOperate(double ra, double ia, double rb, double ib);

	/**
	 * @param ra
	 * @param ia
	 * @param rb
	 * @param ib
	 * @return Im(a op b)
	 */
	double imagComplexOperate(double ra, double ia, double rb, double ib);

	@Override
	String toString();
}

class Stub implements BinaryOperation {
	/**
	 * override this
	 */
	@Override
	public double doubleOperate(double a, double b) {
		return 0;
	}

	@Override
	public boolean booleanOperate(long a, long b) {
		return doubleOperate(a, b) != 0;
	}

	private static long toLong(double d) {
		if (Double.isInfinite(d) || Double.isNaN(d))
			return 0;
		return (long) d;
	}

	@Override
	public long longOperate(long a, long b) {
		return toLong(doubleOperate(a, b));
	}

	/**
	 * override this
	 */
	@Override
	public double realComplexOperate(double ra, double ia, double rb, double ib) {
		return 0;
	}

	/**
	 * override this
	 */
	@Override
	public double imagComplexOperate(double ra, double ia, double rb, double ib) {
		return 0;
	}
}
