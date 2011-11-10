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
public class ManyArguments {
	public static int call(int a, int b, int c, int d, int e, int f, int g, int h) {
		return 8;
	}

	public static int call(int a, int b, int c, int d, int e, int f, int g) {
		return 7;
	}

	public static int call(int a, int b, int c, int d, int e, int f) {
		return 6;
	}

	public static int call(int a, int b, int c, int d, int e) {
		return 5;
	}

	public static int call(int a, int b, int c, int d) {
		return 4;
	}

	public static int call(int a, int b, int c) {
		return 3;
	}

	public static int call(int a, int b) {
		return 2;
	}

	public static int call(int a) {
		return 1;
	}

	public static int call() {
		return 0;
	}
}
