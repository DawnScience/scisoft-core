/*-
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.python;

import java.io.File;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.jython.JythonPath;

/**
 * Class to hold methods to find files and directories to put on the PYTHONPATH for CPython
 */
public class CPythonPath {

	private CPythonPath() {}
	
	private static Logger logger = LoggerFactory.getLogger(CPythonPath.class);

	private static final String CPYTHON_BUNDLE;// = "uk.ac.diamond.cpython"; // this may need to be changed to something platform dependent
	private static final String CPYTHON_MAJOR_VERSION = "2";
	private static final String CPYTHON_MINOR_VERSION = "7";
	private static final String CPYTHON_VERSION = CPYTHON_MAJOR_VERSION + "." + CPYTHON_MINOR_VERSION;
	private static final String CPYTHON_EXEC;
	static {
		String arch = System.getProperty("os.arch");
		if (!arch.endsWith("64")) {
			CPYTHON_BUNDLE = "uk.ac.diamond.cpython.unsupported_arch";
			CPYTHON_EXEC = "non-existent-python";
		} else  {
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.contains("windows")) {
				CPYTHON_BUNDLE = "uk.ac.diamond.cpython.win32.x86_64";
				CPYTHON_EXEC = "python.exe";
			} else if (osName.contains("linux")) {
				CPYTHON_BUNDLE = "uk.ac.diamond.cpython.linux.x86_64";
				CPYTHON_EXEC = "python" + CPYTHON_VERSION;
			} else if (osName.contains("mac os")) {
				CPYTHON_BUNDLE = "uk.ac.diamond.cpython.macosx.x86_64";
				CPYTHON_EXEC = "python" + CPYTHON_VERSION;
			} else {
				CPYTHON_BUNDLE = "uk.ac.diamond.cpython.unsupported_os.x86_64";
				CPYTHON_EXEC = "non-existent-python";
			}
		}
	}
	private static final String CPYTHON_BUNDLE_LOC = CPYTHON_BUNDLE + ".location";
	private static final String CPYTHON_DIR = "cpython" + CPYTHON_VERSION;

	/**
	 * Gets the interpreter directory using the bundle location
	 * @return directory path 
	 * @throws Exception when CPYTHON_BUNDLE_LOC is not set (and no CPython bundle found)
	 */
	public static File getInterpreterDirectory() throws Exception {
		File cpyBundleLoc = null;
		
		if (System.getProperty(CPYTHON_BUNDLE_LOC)!=null) {
			cpyBundleLoc = new File(System.getProperty(CPYTHON_BUNDLE_LOC));
		}

		if (cpyBundleLoc == null) {
			try {
				cpyBundleLoc = JythonPath.getBundleLocation(CPYTHON_BUNDLE);
			} catch (Exception ignored) {
			}
		}
		
		if (cpyBundleLoc == null) {
			throw new Exception("Please set the property '" + CPYTHON_BUNDLE_LOC + "' for this test to work!");
		}
		
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows")) {
			cpyBundleLoc = new File(cpyBundleLoc, CPYTHON_DIR);
		} else {
			cpyBundleLoc = new File(cpyBundleLoc, Paths.get(CPYTHON_DIR, "bin").toString());
		}

		logger.info("CPython interpreter directory found at: {}", cpyBundleLoc);
		return cpyBundleLoc;
	}

	/**
	 * @return name of CPython executable
	 */
	public static String getCPythonExecutableName() {
		return CPYTHON_EXEC;
	}

	/**
	 * @return major version number of CPython interpreter
	 */
	public static String getCPythonMajorVersion() {
		return CPYTHON_MAJOR_VERSION;
	}
	
	/**
	 * @return version number of CPython interpreter
	 */
	public static String getCPythonVersion() {
		return CPYTHON_VERSION;
	}
}
