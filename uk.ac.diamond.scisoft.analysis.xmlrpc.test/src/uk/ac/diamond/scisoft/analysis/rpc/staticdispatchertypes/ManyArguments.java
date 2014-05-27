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

package uk.ac.diamond.scisoft.analysis.rpc.staticdispatchertypes;

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
