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
	 * @return true if windows
	 */
	static public boolean isWindowsOS() {
		return (System.getProperty("os.name").indexOf("Windows") == 0);
	}
	/**
	 * @return true if XP
	 */
	static public boolean isWindowsXP() {
		if (!isWindowsOS()) return false;
		return (System.getProperty("os.name").indexOf("Windows XP") == 0);
	}
	/**
	 * @return true if vista
	 */
	static public boolean isWindowsVista() {
		if (!isWindowsOS()) return false;
		return "Windows Vista".equalsIgnoreCase(System.getProperty("os.name"));
	}
	/**
	 * @return true if linux
	 */
	public static boolean isLinuxOS() {
		String os = System.getProperty("os.name");
		return os != null && os.startsWith("Linux");
	}
	/**
	 * @return true if 32-bit JVM
	 * CAUTION: this does not seem to be particularly portable (the system property is specific to the Sun (now Oracle) JVM)
	 */
	public static boolean is32bitJVM() {
		String os = System.getProperty("sun.arch.data.model");
		return os != null && os.equals("32");
	}
	/**
	 * @return true if 64-bit JVM
	 * CAUTION: this does not seem to be particularly portable (the system property is specific to the Sun (now Oracle) JVM)
	 */
	public static boolean is64bitJVM() {
		String os = System.getProperty("sun.arch.data.model");
		return os != null && os.equals("64");
	}

}
