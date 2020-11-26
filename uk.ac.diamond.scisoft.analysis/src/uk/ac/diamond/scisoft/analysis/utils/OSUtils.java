/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */ 
package uk.ac.diamond.scisoft.analysis.utils;

/**
 *
 */
public class OSUtils {
	/**
	 * @return true if Windows
	 */
	static public boolean isWindowsOS() {
		return System.getProperty("os.name").startsWith("Windows");
	}

	/**
	 * @return true if Linux
	 */
	public static boolean isLinuxOS() {
		String os = System.getProperty("os.name");
		return os != null && os.startsWith("Linux");
	}

	/**
	 * @return true if macOS
	 */
	public static boolean isMacOS() {
		String os = System.getProperty("os.name");
		return os != null && os.startsWith("Mac OS X");
	}

	/**
	 * @return false
	 */
	@Deprecated
	public static boolean is32bitJVM() {
		return false; // don't run on 32-bit anymore
	}
}
