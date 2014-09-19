/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.instancedispatcher;

public class ManyArguments {
	public int call(int a, int b, int c, int d, int e, int f, int g, int h) {
		return 8;
	}

	public int call(int a, int b, int c, int d, int e, int f, int g) {
		return 7;
	}

	public int call(int a, int b, int c, int d, int e, int f) {
		return 6;
	}

	public int call(int a, int b, int c, int d, int e) {
		return 5;
	}

	public int call(int a, int b, int c, int d) {
		return 4;
	}

	public int call(int a, int b, int c) {
		return 3;
	}

	public int call(int a, int b) {
		return 2;
	}

	public int call(int a) {
		return 1;
	}

	public int call() {
		return 0;
	}
}
